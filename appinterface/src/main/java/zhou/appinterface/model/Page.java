package zhou.appinterface.model;

import com.google.gson.reflect.TypeToken;

import java.util.List;

import zhou.appinterface.util.JsonKit;

/**
 * Created by zhou on 15-10-17.
 */
public final class Page<T> extends BaseModel<Page> {

    public List<T> list;                // list result of this page
    public int pageNumber;                // page number
    public int pageSize;                // result amount of this page
    public int totalPage;                // total page
    public int totalRow;                // total row

    public boolean isFirst() {
        return pageNumber == 1;
    }

    public boolean isLast() {
        return pageNumber == totalPage;
    }

    public boolean isEmpty() {
        return list == null || list.isEmpty();
    }

    @Override
    public Page<T> fromJson(String json) {
        return JsonKit.gson.fromJson(json, new TypeToken<Page<T>>() {
        }.getType());
    }

    @Override
    public String toString() {
        return "Page{" +
                "list=" + list +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", totalPage=" + totalPage +
                ", totalRow=" + totalRow +
                '}';
    }
}
