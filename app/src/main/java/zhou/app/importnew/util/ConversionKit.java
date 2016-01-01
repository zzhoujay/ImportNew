package zhou.app.importnew.util;

import zhou.app.importnew.model.PostItem;

/**
 * Created by zhou on 16-1-2.
 */
public class ConversionKit {

    public static PostItem convers(zhou.tool.importnewcrawler.PostItem postItem) {
        return new PostItem(postItem.thumb, postItem.thumbTitle, postItem.title, postItem.time, postItem.category, postItem.categoryTag, postItem.reply, postItem.content, postItem.href);
    }
}
