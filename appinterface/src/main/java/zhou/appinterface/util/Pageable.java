package zhou.appinterface.util;

import java.io.Serializable;

import zhou.appinterface.model.Page;

/**
 * Created by zhou on 15-10-18.
 */
public class Pageable implements Serializable {

    public int pageNo;
    public int pageSize;

    public Pageable(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public Pageable() {
    }

    public void next() {
        pageNo++;
    }

    public void prev() {
        pageNo--;
    }

    public void change(Page page) {
        this.pageNo = page.pageNumber;
        this.pageSize = page.pageSize;
    }

    public static Pageable createDefault() {
        return new Pageable(1, 10);
    }
}
