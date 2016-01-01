package zhou.appinterface.ui.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import zhou.appinterface.util.ActivityThemeManager;

/**
 * Created by zhou on 15-10-23.
 * 主题Activity
 */
public class ThemeActivity extends StackActivity {

    private boolean hasSetTheme = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (!hasSetTheme) {
            int theme = ActivityThemeManager.getInstance().getCurrTheme();
            if (theme > 0) {
                setTheme(theme);
            }
            hasSetTheme = true;
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        if (!hasSetTheme) {
            int theme = ActivityThemeManager.getInstance().getCurrTheme();
            if (theme > 0) {
                setTheme(theme);
            }
            hasSetTheme = true;
        }
        super.onCreate(savedInstanceState, persistentState);
    }
}
