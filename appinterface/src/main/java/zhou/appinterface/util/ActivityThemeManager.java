package zhou.appinterface.util;

import android.support.annotation.NonNull;

/**
 * Created by zhou on 15-11-11.
 */
public class ActivityThemeManager {

    private final int[] themes;
    private int currTheme;

    private static ActivityThemeManager activityThemeManager;

    private ActivityThemeManager(@NonNull int[] themes) {
        this.themes = themes;
        currTheme = 0;
    }

    public static void init(int[] themes) {
        activityThemeManager = new ActivityThemeManager(themes);
    }

    public static ActivityThemeManager getInstance() {
        return activityThemeManager;
    }

    public int getCurrTheme() {
        if (currTheme < 0 || currTheme >= themes.length) {
            return -1;
        }
        return themes[currTheme];
    }

    public int[] getThemes() {
        return themes;
    }

    public void setCurrTheme(int currTheme) {
        if (currTheme < 0) {
            throw new RuntimeException("can not < 0");
        } else if (currTheme >= themes.length) {
            throw new RuntimeException("can not >= themes.length");
        }
        this.currTheme = currTheme;
    }

}
