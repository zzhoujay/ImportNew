package zhou.appinterface.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by zhou on 15-10-17.
 */
public class JsonKit {

    public static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();

}
