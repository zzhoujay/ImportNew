package zhou.appinterface.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import zhou.appinterface.util.ErrorHandler;
import zhou.appinterface.util.LogKit;

/**
 * Created by zhou on 15-10-20.
 * 网络请求管理器
 */
public final class NetworkManager {

    private static NetworkManager networkManager;

    public static NetworkManager getInstance() {
        return networkManager;
    }

    public static void init(Context context, Gson gson) {
        networkManager = new NetworkManager(context, gson);
    }

    private OkHttpClient client;
    private Gson gson;
    private Context context;
    private ErrorHandler errorHandler;

    private NetworkManager(Context context, Gson gson) {
        client = new OkHttpClient();
        client.setCookieHandler(new CookieManager(new PersistentCookieStore(context), CookiePolicy.ACCEPT_ALL));
        client.setConnectTimeout(5, TimeUnit.SECONDS);
        client.setReadTimeout(5, TimeUnit.SECONDS);
        client.setWriteTimeout(5, TimeUnit.SECONDS);
        this.gson = gson;
        this.context = context;
    }

    public void requestString(Request r, Action1<String> action1) {
        Observable
                .create((Subscriber<? super String> subscriber) -> {
                    try {
                        subscriber.onNext(client.newCall(r).execute().body().string());
                    } catch (IOException e) {
                        subscriber.onError(e);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, throwable -> {
                    LogKit.d("request error", throwable);
                    if (errorHandler != null) {
                        action1.call(errorHandler.handleError(throwable).toString());
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public <T extends ErrorHandler> void request(Request r, Action1<T> action1, Class<T> aClass) {
        Observable
                .create((Subscriber<? super T> subscriber) -> {
                    try {
                        String body = client.newCall(r).execute().body().string();
                        LogKit.d("response success", body);
                        subscriber.onNext(gson.fromJson(body, aClass));
                    } catch (IOException e) {
                        subscriber.onError(e);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, throwable -> {
                    LogKit.d("request", r.urlString(), throwable);
                    if (errorHandler != null) {
                        action1.call((T) errorHandler.handleError(throwable));
                    }
                });
    }

    @SuppressWarnings("unchecked")
    public <T extends ErrorHandler> void request(Request r, Action1<T> action1, Type type) {
        Observable
                .create((Subscriber<? super T> subscriber) -> {
                    try {
                        String body = client.newCall(r).execute().body().string();
                        LogKit.d("request success", body);
                        subscriber.onNext((T) gson.fromJson(body, type));
                    } catch (IOException e) {
                        subscriber.onError(e);
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(action1, throwable -> {
                    LogKit.d("request", r.urlString(), throwable);
                    if (errorHandler != null) {
                        action1.call((T) errorHandler.handleError(throwable));
                    }
                });
    }

    public boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable();
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }
}
