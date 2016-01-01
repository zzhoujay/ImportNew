package zhou.app.importnew.util;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.io.IOException;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zhou.app.importnew.model.PostItem;
import zhou.tool.importnewcrawler.Crawler;

/**
 * Created by zhou on 16-1-2.
 */
public class NetworkKit {


    public static void loadPostListByCategory(Crawler crawler, int page, String type, Subscriber<List<PostItem>> subscriber) {
        Observable
                .create((Subscriber<? super List<PostItem>> subscriber1) -> {
                    try {
                        subscriber1.onNext(Stream.of(crawler.getPostByCategory(page, type)).map(ConversionKit::convers).collect(Collectors.toList()));
                    } catch (IOException e) {
                        subscriber1.onError(e);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
