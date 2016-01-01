package zhou.tool.importnewcrawler;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by zhou on 16-1-1.
 */
public final class NetworkManager implements Serializable {

    private static NetworkManager networkManager;

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private NetworkManager(File cache, long size) {
        client = new OkHttpClient();
        if (cache != null) {
            if (!cache.exists()) {
                cache.mkdirs();
            }
            Cache c = new Cache(cache, size);
            client.setCache(c);
        }
    }

    public static void init(File cache, long size) {
        networkManager = new NetworkManager(cache, size);
    }

    public static NetworkManager getInstance() {
        return networkManager;
    }

    private OkHttpClient client;

    public String requestString(Request request) throws IOException {
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new RuntimeException(response.message());
        }
    }

}
