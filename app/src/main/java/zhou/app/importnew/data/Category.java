package zhou.app.importnew.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import zhou.app.importnew.common.Config;

/**
 * Created by zhou on 16-1-1.
 */
public class Category implements Serializable, Parcelable {

    public static final Category Java, Android, iOS, Python, Web, Design;

    static {
        Java = new Category("Java", "http://www.importnew.com", "cat", Config.javaTypes);
        Android = new Category("Android", "http://android.jobbole.com", "category", Config.androidTypes);
        iOS = new Category("iOS", "http://ios.jobbole.com", "category", Config.iosTypes);
        Python = new Category("Python", "http://python.jobbole.com", "category", Config.pythonTypes);
        Web = new Category("Web前端", "http://web.jobbole.com", "category", Config.webTypes);
        Design = new Category("设计", "http://design.jobbole.com", "category", Config.design);
    }

    public String name;
    public String siteUrl;
    public String categoryFlag;
    public List<Type> types;

    public Category(String name, String siteUrl, String categoryFlag, List<Type> types) {
        this.name = name;
        this.siteUrl = siteUrl;
        this.categoryFlag = categoryFlag;
        this.types = types;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", siteUrl='" + siteUrl + '\'' +
                ", categoryFlag='" + categoryFlag + '\'' +
                ", types=" + types +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.siteUrl);
        dest.writeString(this.categoryFlag);
        dest.writeTypedList(types);
    }

    protected Category(Parcel in) {
        this.name = in.readString();
        this.siteUrl = in.readString();
        this.categoryFlag = in.readString();
        this.types = in.createTypedArrayList(Type.CREATOR);
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
