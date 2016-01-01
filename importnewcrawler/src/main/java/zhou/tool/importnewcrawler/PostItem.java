package zhou.tool.importnewcrawler;

import java.io.Serializable;

/**
 * Created by zhou on 16-1-1.
 */
public class PostItem implements Serializable {

    public String thumb;
    public String thumbTitle;
    public String title;
    public String time;
    public String category;
    public String categoryTag;
    public String reply;
    public String content;
    public String href;

    public PostItem(String thumb, String thumbTitle, String title, String time, String category, String categoryTag, String reply, String content, String href) {
        this.thumb = thumb;
        this.thumbTitle = thumbTitle;
        this.title = title;
        this.time = time;
        this.category = category;
        this.categoryTag = categoryTag;
        this.reply = reply;
        this.content = content;
        this.href = href;
    }

    public PostItem() {
    }

    @Override
    public String toString() {
        return "PostItem{" +
                "thumb='" + thumb + '\'' +
                ", thumbTitle='" + thumbTitle + '\'' +
                ", title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", category='" + category + '\'' +
                ", categoryTag='" + categoryTag + '\'' +
                ", reply='" + reply + '\'' +
                ", content='" + content + '\'' +
                ", href='" + href + '\'' +
                '}';
    }
}
