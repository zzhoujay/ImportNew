package zhou.appinterface.model;

import java.io.Serializable;

/**
 * Created by zhou on 15-10-17.
 */
public abstract class BaseModel<T> implements Serializable {

    public abstract T fromJson(String json);

}
