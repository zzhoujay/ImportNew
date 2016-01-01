package zhou.app.importnew.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by zhou on 16-1-1.
 */
public class Post extends PostItem implements Parcelable {



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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.tag);
    }

    protected Post(Parcel in) {
        this.tag = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };
}
