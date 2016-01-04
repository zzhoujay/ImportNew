package zhou.app.importnew.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by zhou on 16-1-1.
 */
public class Type implements Serializable, Parcelable {

    public static final String TYPE = "type";

    public String name;
    public String tag;

    public Type(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }

    public Type() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.tag);
    }

    protected Type(Parcel in) {
        this.name = in.readString();
        this.tag = in.readString();
    }

    public static final Parcelable.Creator<Type> CREATOR = new Parcelable.Creator<Type>() {
        public Type createFromParcel(Parcel source) {
            return new Type(source);
        }

        public Type[] newArray(int size) {
            return new Type[size];
        }
    };

    @Override
    public String toString() {
        return "Type{" +
                "name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                '}';
    }
}
