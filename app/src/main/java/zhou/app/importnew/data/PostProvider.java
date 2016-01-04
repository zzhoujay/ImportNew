package zhou.app.importnew.data;

import java.io.File;

import rx.Subscriber;
import rx.functions.Action1;
import zhou.app.importnew.App;
import zhou.app.importnew.model.Post;
import zhou.app.importnew.util.NetworkKit;
import zhou.appinterface.data.CommonProvider;
import zhou.appinterface.util.HashKit;
import zhou.appinterface.util.LogKit;
import zhou.tool.importnewcrawler.Crawler;

/**
 * Created by zhou on 16-1-4.
 */
public class PostProvider extends CommonProvider<Post> {

    private Crawler crawler;
    private String url;

    public PostProvider(Crawler crawler, String url) {
        this.crawler = crawler;
        this.url = url;
        key = HashKit.md5(String.format("url:%s", url));
        file = new File(App.getApp().getCacheDir(), String.format("%s.cache", key));
    }

    @Override
    protected void loadByNetwork(Action1<Post> action1, boolean more) {
        NetworkKit.loadPost(crawler, url, new Subscriber<Post>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogKit.d("PostProvider", "error", e);
                action1.call(null);
            }

            @Override
            public void onNext(Post post) {
                action1.call(post);
            }
        });
    }

    @Override
    public boolean needCache() {
        return false;
    }
}
