package utils.wzutils.img.implement;//package utils.wzutils.img.implement;
//
//import android.app.Application;
//import android.content.Context;
//import android.widget.ImageView;
//
//import org.xutils.image.ImageOptions;
//import org.xutils.x;
//
//import utils.aixiangutils.imageloader.ImageLoader;
//import InterfaceImgTool;
//
///**
// * Created by coder on 15/12/24.
// */
//public class ImgToolXutils implements InterfaceImgTool {
//    ImageOptions defaultImageOptions = new ImageOptions.Builder()
//            .setFadeIn(true)
//            .build();
//
//    int loadingDrawableId;
//    int failureDrawableId;
//
//
//    @Override
//    public void init(Context context, int loadingDrawableId, int failureDrawableId) {
//        if (context instanceof Application) {
//            x.Ext.init((Application) context);
//            x.Ext.setDebug(true);
//            this.loadingDrawableId=loadingDrawableId;
//            this.failureDrawableId=failureDrawableId;
//            defaultImageOptions=new ImageOptions.Builder()
//                    .setLoadingDrawableId(loadingDrawableId)
//                    .setFailureDrawableId(failureDrawableId)
//                    .setFadeIn(true)
//                    .build();
//
//        }
//    }
//
//    @Override
//    public void loadImage(Object src, ImageView imageView) {
//        x.image().bind(imageView, "" + src, defaultImageOptions);
//    }
//
//    @Override
//    public void loadImage(Object src, ImageView imageView, int width, int height) {
//        ImageOptions imageOptions = new ImageOptions.Builder()
//                .setLoadingDrawableId(loadingDrawableId)
//                .setFailureDrawableId(failureDrawableId)
//                .setSize(width, height)
//                .setFadeIn(true)
//                .build();
//        x.image().bind(imageView, "" + src, imageOptions);
//    }
//}
