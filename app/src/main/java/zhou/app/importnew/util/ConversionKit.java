package zhou.app.importnew.util;

import zhou.app.importnew.model.Post;
import zhou.app.importnew.model.PostItem;

/**
 * Created by zhou on 16-1-2.
 */
public class ConversionKit {

    public static PostItem converse(zhou.tool.importnewcrawler.PostItem postItem) {
        return new PostItem(postItem.thumb, postItem.thumbTitle, postItem.title, postItem.time, postItem.category, postItem.categoryTag, postItem.reply, postItem.content, postItem.href);
    }

    public static Post converse(zhou.tool.importnewcrawler.Post post) {
        return new Post(post.thumb, post.thumbTitle, post.title, post.time, post.category, post.categoryTag, post.reply, post.content, post.href, post.tag);
    }
}
