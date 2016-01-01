package zhou.app.importnew.data;

import java.util.List;

import rx.functions.Action1;
import zhou.app.importnew.model.PostItem;
import zhou.appinterface.data.PageProvider;

/**
 * Created by zhou on 16-1-1.
 */
public class PostProvider extends PageProvider<PostItem> {

    @Override
    protected void loadByNetwork(Action1<List<PostItem>> action1, boolean more) {

    }

    @Override
    public boolean needCache() {
        return false;
    }
}
