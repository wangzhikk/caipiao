package utils.wzutils;

import android.content.Context;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.MathTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.ViewTool;
import utils.wzutils.img.InterfaceImgTool;
import utils.wzutils.img.implement.glide.ImgToolGlide;


/**
 * Created by coder on 15/12/24.
 * 加载图片的工具类
 */
public class ImgTool {
    /***
     * 这是一张错误图片
     */
    public static final String errorImg = "errorImg";
    public static String defaultPreStr = "";//默认前缀
    static InterfaceImgTool imgToolInterface = new ImgToolGlide();
    static int keyImageUrl = ViewTool.initKey();

    /***
     * 初始化
     *
     * @param context
     * @param loadingDrawableId 正在加载显示的图片
     * @param failureDrawableId 加载失败显示的图片
     */
    public static void init(Context context, int loadingDrawableId, int failureDrawableId) {
        try {
            imgToolInterface.init(context, loadingDrawableId, failureDrawableId);
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }

    public static void setUrlTag(Object src, ImageView imageView) {
        if (imageView != null && src != null) {
            ViewTool.setTag(imageView, src, keyImageUrl);
        }
    }

    public static Object getUrlTag(ImageView imageView) {
        if (imageView != null) {
            return ViewTool.getTag(imageView, keyImageUrl);
        }
        return null;
    }

    /**
     * 加载一张图片到imageview , 最简单的模式
     *
     * @param src       图片地址
     * @param imageView 加载给哪个控件
     */
    public static void loadImage(Object src, ImageView imageView) {
        try {
            loadImage(null, src, imageView, 0, 0);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /**
     * 加载一张图片到imageview , 指定需求宽高
     *
     * @param src       图片地址
     * @param imageView 加载给哪个控件
     * @param width     需要的宽
     * @param height    需要的高
     */
    public static void loadImage(Object src, ImageView imageView, int width, int height) {
        try {
            loadImage(null, src, imageView, width, height);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /**
     * 加载一张图片到imageview , 最简单的模式
     *
     * @param src       图片地址
     * @param imageView 加载给哪个控件
     */
    public static void loadImage(Context context, Object src, ImageView imageView) {
        try {
            loadImage(context, src, imageView, 0, 0);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    static int keyMeasure = ViewTool.initKey();

    /**
     * 加载一张图片到imageview , 指定需求宽高
     *
     * @param src       图片地址
     * @param imageView 加载给哪个控件
     * @param width     需要的宽
     * @param height    需要的高
     */
    public static void loadImage(Context context, Object src, final ImageView imageView, int width, int height) {
        try {
            if (src == null) return;
            if (src instanceof Integer) {//本地资源文件就直接设置吧。。。 有gif再说
                imageView.setImageResource((Integer) src);
                return;
            }


            {//适配context
                context = AppTool.getApplication();
//                if(context==null)context=imageView.getContext();
//                if(context==null)context=AppTool.currActivity;
//                if(context !=null&&context instanceof Activity){
//                    if(((Activity) context).isDestroyed()){
//                        LogTool.s("activity isDestroyed",context,AppTool.currActivity);
//                        context=AppTool.currActivity;
//                    }
//                }
            }


            {//适配宽高
                if (width < 1) width = imageView.getMeasuredWidth();
                if (height < 1) height = imageView.getMeasuredHeight();
                try {
                    if (width < 1 && height < 1) {
                        if (ViewTool.getTag(imageView, keyMeasure) == null) {//没有延时测量过
                            final Object finalSrc = src;
                            final Context finalContext = context;
                            imageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {//其他几种方式都可能是0 .。。。 就用predraw吧
                                @Override
                                public boolean onPreDraw() {
                                    if(ViewTool.getTag(imageView, keyMeasure) == null){//已经获取到宽高了
                                        ViewTool.setTag(imageView, true, keyMeasure);//避免死循环
                                        loadImage(finalContext, finalSrc, imageView, 0, 0);
                                        imageView.getViewTreeObserver().removeOnPreDrawListener(this);
                                    }
                                    return true;
                                }
                            });
                            return;
                        }
                    }
                } catch (Exception e) {
                    LogTool.ex(e);
                }
            }


            if (imageView == null || src == null) return;
            src = convertSrc(src);
            setUrlTag(src, imageView);//主要用于查看大图 所以后面才转换正式的
            src = convertSrc(src, width, height);
            imgToolInterface.loadImage(context, src, imageView, width, height);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static boolean isEmpty(Object src) {
        return src == null || StringTool.isEmpty("" + src);
    }


    public static Object convertSrc(Object src, int w, int h) {
        src = convertSrc(src);
        if (src instanceof String) {
            if (((String) src).toLowerCase().endsWith(".gif")) return src;
            //阿里云 降低质量
            //?x-oss-process=image/resize,w_500,h_100/format,webp/quality,q_80
            int maxWidth =Math.min(4000,(int) (CommonTool.getWindowSize().x * 1.5)) ;
            int maxHeight =Math.min (4000,(int) (CommonTool.getWindowSize().y * 1.5));
            if (w < 1 || w > maxWidth) {
                w = maxWidth;
            }
            if (h < 1 || h > maxHeight) {
                h = maxHeight;
            }
            src += "?x-oss-process=image/resize,w_" + w + ",h_" + h + "/format,webp" + "/quality,q_75";
        }
        return src;
    }

    public static Object convertSrc(Object src) {
        if (isEmpty(src)) {
            src = errorImg;
            return src;
        }
        if (src instanceof String && !errorImg.equals(src)) {
            if (!((String) src).contains(":")) {
                src = defaultPreStr + src;
            }
        }
        return src;
    }

    /**
     * 清除缓存
     */
    public static void clearCache() {
        try {
            imgToolInterface.clearCache();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /**
     * 获取缓存大小
     * handler 里面的
     * msg.arg1=1;
     * msg.arg2= (int) size;
     *
     * @return
     */
    public static void getCacheSize(GetCacheSizeListener getCacheSizeListener) {
        try {
            imgToolInterface.getCacheSize(getCacheSizeListener);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static void resume() {
        try {
            imgToolInterface.resumeRequest();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static void pause() {
        try {
            imgToolInterface.pauseRequest();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public interface GetCacheSizeListener {
        void onGetSize(long size, String showStr);
    }


    public static String getSizeStr(long size) {
        int kb = 1024;
        int mb = kb * 1024;
        int gb = mb * 1024;

        double result = 0;
        String danwei = "B";
        if (size > gb) {
            result = size * 1.0f / gb;
            danwei = "GB";
        } else if (size > mb) {
            result = size * 1.0f / mb;
            danwei = "MB";
        } else if (size > kb) {
            result = size * 1.0f / kb;
            danwei = "KB";
        } else {
            result = size;
            danwei = "B";
        }
        String resultStr = result + danwei;
        if (result % 1 > 0) {
            resultStr = MathTool.get2num(result) + danwei;
        }
        return resultStr;
    }
}
