package zhou.app.importnew.data;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action1;
import zhou.app.importnew.model.PostItem;
import zhou.app.importnew.util.NetworkKit;
import zhou.appinterface.data.PageProvider;
import zhou.appinterface.util.LogKit;
import zhou.tool.importnewcrawler.Crawler;

/**
 * Created by zhou on 16-1-1.
 */
public class PostProvider extends PageProvider<PostItem> {

    private Type type;
    private Crawler crawler;

    public PostProvider(Type type, Crawler crawler) {
        this.type = type;
        this.crawler = crawler;
    }

    @Override
    protected void loadByNetwork(Action1<List<PostItem>> action1, boolean more) {
        if (more) {
            pageable.next();
        }
        NetworkKit.loadPostListByCategory(crawler, pageable.pageNo, type.tag, new Subscriber<List<PostItem>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogKit.d("PostProvider", "error", e);
                if (more) {
                    pageable.prev();
                }
                action1.call(null);
            }

            @Override
            public void onNext(List<PostItem> postItems) {
                action1.call(postItems);
            }
        });
    }

    @Override
    public boolean needCache() {
        return true;
    }
}
