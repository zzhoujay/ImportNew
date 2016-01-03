package zhou.app.importnew.util;

/**
 * Created by zhou on 16-1-3.
 */
public class ColorKit {

    private static final int[] colors = {
            0xe51c23, 0xe91e63, 0x9c27b0, 0x673ab7, 0x3f51b5, 0x5677fc, 0x03a9f4, 0x00bcd4, 0x009688, 0x259b24,
            0x8bc34a, 0xcddc39, 0xffeb3b, 0xffc107, 0xff9800, 0xff5722, 0x795548, 0x9e9e9e, 0x607d8b, 0x3f51b5
    };

    private static final int len = colors.length;

    public static int getRandomColor(String str) {
        return colors[(str == null ? 0 : str.hashCode()) % len];
    }

    public static int getRandomColor() {
        return colors[((int) Math.abs(Math.random() * len))];
    }

}
