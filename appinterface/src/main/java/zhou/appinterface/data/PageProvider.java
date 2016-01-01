package zhou.appinterface.data;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import zhou.appinterface.util.FileKit;
import zhou.appinterface.util.LogKit;
import zhou.appinterface.util.PageEntity;
import zhou.appinterface.util.Pageable;

/**
 * Created by zhou on 15-10-20.
 */
public abstract class PageProvider<T> extends CommonProvider<List<T>> {

    protected Pageable pageable;
    private List<Action1<Status>> listeners;
    private Status status;

    public PageProvider() {
        pageable = Pageable.createDefault();
        listeners = new ArrayList<>();
    }

    @Override
    public void persistence() {
        if (hasLoad() && file != null) {
            Observable.create((Subscriber<? super Void> subscriber) -> FileKit.writeObject(file, new PageEntity<>(t, pageable)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe(aVoid -> {
                        LogKit.d("persistence", "success");
                    }, throwable -> {
                        LogKit.d("persistence", "error", throwable);
                    });
        }
    }

    @Override
    public void set(@Nullable List<T> ts, boolean more) {
        if (more && t != null && hasLoad()) {
            if (ts == null) {
                ts = new ArrayList<>();
            }
            t.addAll(ts);
        } else {
            super.set(ts, more);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadByCache(Action1<List<T>> closure) {
        if (file != null && file.exists()) {
            Observable.create((Subscriber<? super PageEntity<T>> subscriber) -> subscriber.onNext((PageEntity<T>) FileKit.readObject(file)))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(tPageEntity -> {
                        this.pageable = tPageEntity.pageable;
                        closure.call(tPageEntity.entity);
                    }, throwable -> {
                        LogKit.d("loadByCache", "error", throwable);
                    });
        } else {
            closure.call(null);
        }
    }

    public void addStatusChangeListener(Action1<Status> listener) {
        listeners.add(listener);
    }

    public void removeStatusChangeListener(Action1<Status> listener) {
        listeners.remove(listener);
    }

    protected void statusChange(Status status) {
        this.status = status;
        for (Action1<Status> l : listeners) {
            l.call(status);
        }
    }

    public Status getStatus() {
        return this.status;
    }


    public enum Status {

        loading, error, empty, complete, end

    }

}
