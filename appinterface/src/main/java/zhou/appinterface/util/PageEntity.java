package zhou.appinterface.util;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhou on 15-10-18.
 */
public class PageEntity<T> implements Serializable {

    public List<T> entity;
    public Pageable pageable;

    public PageEntity(List<T> entity, Pageable pageable) {
        this.entity = entity;
        this.pageable = pageable;
    }

    public PageEntity() {
    }

    @Override
    public String toString() {
        return "PageEntity{" +
                "entity=" + entity +
                ", pageable=" + pageable +
                '}';
    }
}
