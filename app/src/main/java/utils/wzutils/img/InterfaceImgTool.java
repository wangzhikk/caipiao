package utils.wzutils.img;

import android.content.Context;
import android.widget.ImageView;

import utils.wzutils.ImgTool;

/**
 * Created by coder on 15/12/24.
 */
public interface InterfaceImgTool {
    /**
     * 初始化
     *
     * @param context
     */
    void init(Context context, int loadingDrawableId, int failureDrawableId);

    /**
     * 加载一张图片到imageview , 指定需求宽高
     *
     * @param src       图片地址
     * @param imageView 加载给哪个控件
     * @param width     需要的宽
     * @param height    需要的高
     */
    void loadImage(Context context, Object src, ImageView imageView, int width, int height);


    /**
     * 清除缓存
     */
    void clearCache();

    /**
     * 获取缓存大小
     *
     * @return
     */
    void getCacheSize(ImgTool.GetCacheSizeListener getCacheSizeListener);

    void pauseRequest();
    void resumeRequest();
}
