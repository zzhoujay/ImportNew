package zhou.app.importnew;

import android.app.Application;

import com.google.gson.Gson;

import java.io.File;

import zhou.appinterface.net.NetworkManager;
import zhou.tool.importnewcrawler.Crawler;

/**
 * Created by zhou on 16-1-1.
 */
public class App extends Application {

    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        zhou.tool.importnewcrawler.NetworkManager.init(new File(getCacheDir(), "gg"), 1024 * 1024);
        NetworkManager.init(this, new Gson());
        crawler = new Crawler("http://www.importnew.com", "cat");
    }

    public static App getApp() {
        return app;
    }

    public Crawler crawler;
}
