package zhou.app.importnew.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zhou on 16-1-1.
 */
public class PostItem implements Serializable, Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.thumb);
        dest.writeString(this.thumbTitle);
        dest.writeString(this.title);
        dest.writeString(this.time);
        dest.writeString(this.category);
        dest.writeString(this.categoryTag);
        dest.writeString(this.reply);
        dest.writeString(this.content);
        dest.writeString(this.href);
    }

    protected PostItem(Parcel in) {
        this.thumb = in.readString();
        this.thumbTitle = in.readString();
        this.title = in.readString();
        this.time = in.readString();
        this.category = in.readString();
        this.categoryTag = in.readString();
        this.reply = in.readString();
        this.content = in.readString();
        this.href = in.readString();
    }

    public static final Parcelable.Creator<PostItem> CREATOR = new Parcelable.Creator<PostItem>() {
        public PostItem createFromParcel(Parcel source) {
            return new PostItem(source);
        }

        public PostItem[] newArray(int size) {
            return new PostItem[size];
        }
    };
}
