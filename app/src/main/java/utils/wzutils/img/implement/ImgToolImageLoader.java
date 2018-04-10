package utils.wzutils.img.implement;//package wzutils.img.implement;
//
//import android.app.Application;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.view.View;
//import android.widget.ImageView;
//
//
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;
//
//import wzutils.img.InterfaceImgTool;
//
///**
// * Created by coder on 15/12/24.
// */
//public class ImgToolImageLoader implements InterfaceImgTool {
//    DisplayImageOptions defaultOptions;
//    private ImageLoadingListener animateFirstListener =  new ImageLoadingListener(){
//        final List<String> displayedImages = Collections
//                .synchronizedList(new LinkedList<String>());
//
//        @Override
//        public void onLoadingStarted(String s, View view) {
//
//        }
//
//        @Override
//        public void onLoadingFailed(String s, View view, FailReason failReason) {
//
//        }
//
//        @Override
//        public void onLoadingComplete(String imageUri, View view,
//                Bitmap loadedImage) {
//            if (loadedImage != null) {
//                ImageView imageView = (ImageView) view;
//                // 是否第一次显示
//                boolean firstDisplay = !displayedImages.contains(imageUri);
//                if (firstDisplay) {
//                    // 图片淡入效果
//                    FadeInBitmapDisplayer.animate(imageView, 500);
//                    displayedImages.add(imageUri);
//                }
//            }
//        }
//
//        @Override
//        public void onLoadingCancelled(String s, View view) {
//
//        }
//    };
//    @Override
//    public void init(Context context) {
//        if (context instanceof Application) {
//
//        }
//        ImageLoader.getInstance().init(ImageLoaderConfiguration
//                .createDefault(AppTool.getApplication()));
//        defaultOptions = new DisplayImageOptions.Builder()
//                .showStubImage(R.mipmap.default_goods_image_240) // 设置图片下载期间显示的图片
//                .showImageForEmptyUri(R.mipmap.default_goods_image_240) // 设置图片Uri为空或是错误的时候显示的图片
//                .showImageOnFail(R.mipmap.default_goods_image_240) // 设置图片加载或解码过程中发生错误显示的图片
//                .cacheInMemory(false) // 设置下载的图片是否缓存在内存中
//                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
//                .displayer(new RoundedBitmapDisplayer(0)) // 设置成圆角图片
//                .build();
//    }
//
//    @Override
//    public void loadImage(Object src, ImageView imageView) {
//        ImageLoader.getInstance().displayImage(""+src, imageView, defaultOptions,
//                animateFirstListener);
//    }
//
//    @Override
//    public void loadImage(Object src, ImageView imageView, int width, int height) {
//
//        ImageLoader.getInstance().displayImage(""+src, imageView, defaultOptions,
//                animateFirstListener);
//    }
//}
