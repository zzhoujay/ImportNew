package zhou.app.importnew;

import android.app.Application;

/**
 * Created by zhou on 16-1-1.
 */
public class App extends Application {

    private static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static App getApp() {
        return app;
    }
}
