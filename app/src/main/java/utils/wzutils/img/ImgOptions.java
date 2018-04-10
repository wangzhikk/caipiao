package utils.wzutils.img;

import android.graphics.Bitmap;
import android.view.animation.Animation;

/**
 * Created by coder on 15/12/24.
 */
public class ImgOptions {
    private int width = 0; // 小于0时不采样压缩. 等于0时自动识别ImageView的宽高和maxWidth.
    private int height = 0; // 小于0时不采样压缩. 等于0时自动识别ImageView的宽高和maxHeight.
    private boolean crop = false; // crop to (width, height)
    private Bitmap.Config config = Bitmap.Config.RGB_565;
    private boolean fadeIn = false;
    private Animation animation = null;
}
