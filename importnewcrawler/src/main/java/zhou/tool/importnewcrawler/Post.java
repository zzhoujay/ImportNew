package zhou.tool.importnewcrawler;

import java.util.List;

/**
 * Created by zhou on 16-1-1.
 */
public class Post extends PostItem {

    public List<String> tag;

    public Post() {
    }

    public Post(String thumb, String thumbTitle, String title, String time, String category, String categoryTag, String reply, String content, String href, List<String> tag) {
        super(thumb, thumbTitle, title, time, category, categoryTag, reply, content, href);
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "Post{" +
                "item="+super.toString()+
                ",tag=" + tag +
                '}';
    }

}
