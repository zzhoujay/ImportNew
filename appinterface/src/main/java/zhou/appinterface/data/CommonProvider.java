package zhou.appinterface.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.File;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import zhou.appinterface.net.NetworkManager;
import zhou.appinterface.util.ErrorHandler;
import zhou.appinterface.util.FileKit;
import zhou.appinterface.util.LogKit;

/**
 * Created by zhou on 15-10-20.
 */
public abstract class CommonProvider<T> implements DataProvider<T> {

    protected T t;
    protected File file;
    protected String key;
    protected boolean noticeable;
    protected ErrorHandler errorHandler;

    @Override
    public void persistence() {
        if (hasLoad() && file != null) {
            Observable.create(subscriber -> FileKit.writeObject(file, t))
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(aVoid -> {
                        LogKit.d("persistence", "success");
                    }, throwable -> {
                        LogKit.d("persistence", "error", throwable);
                    });
        }
    }

    @Nullable
    @Override
    public T get() {
        return t;
    }

    @Override
    public void set(@Nullable T t, boolean more) {
        this.t = t;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadByCache(Action1<T> closure) {
        if (file != null && file.exists()) {
            Observable.create((Subscriber<? super T> subscriber) -> subscriber.onNext((T) FileKit
                    .readObject(file)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(closure, throwable -> {
                        LogKit.d("loadByCache", "error", throwable);
                    });
        } else {
            closure.call(null);
        }
    }

    @Override
    public void load(Action1<T> closure, boolean more) {
        if (NetworkManager.getInstance().isNetworkConnected()) {
            loadByNetwork(closure, more);
        } else {
            onNoNetwork();
            closure.call(null);
        }
    }

    protected abstract void loadByNetwork(Action1<T> action1, boolean more);

    protected void onNoNetwork() {

    }

    @Override
    public boolean hasLoad() {
        return t != null;
    }

    @Override
    public boolean clearCache() {
        return file != null && file.exists() && file.delete();
    }

    @NonNull
    @Override
    public String key() {
        return key;
    }

    @Override
    public void setErrorHandle(ErrorHandler closure) {
        this.errorHandler = closure;
    }

    @Override
    public void setNoticeable(boolean noticeable) {
        this.noticeable = noticeable;
    }
}
