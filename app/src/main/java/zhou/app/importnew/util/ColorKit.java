package zhou.app.importnew.util;

import android.graphics.Color;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.List;

/**
 * Created by zhou on 16-1-3.
 */
public class ColorKit {

    private static final String[] colorString = {
            "#e51c23", "#e91e63", "#9c27b0", "#673ab7", "#3f51b5", "#5677fc", "#03a9f4", "#00bcd4", "#009688", "#259b24",
            "#8bc34a", "#cddc39", "#ffeb3b", "#ffc107", "#ff9800", "#ff5722", "#795548", "#9e9e9e", "#607d8b", "#3f51b5"
    };

    private static final List<Integer> colors = Stream.of(colorString).map(Color::parseColor).collect(Collectors.toList());

    private static final int len = colorString.length;

    public static int getRandomColor(String str) {
        return colors.get((str == null ? 0 : str.hashCode()) % len);
    }

    public static int getRandomColor() {
        return colors.get(((int) Math.abs(Math.random() * len)));
    }

}
