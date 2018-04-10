package utils.wzutils.img.implement;//package utils.wzutils.img.implement;
//
//import android.app.Application;
//import android.content.Context;
//import android.net.Uri;
//import android.widget.ImageView;
//import ax.cust.android.App;
//import com.facebook.drawee.backends.pipeline.Fresco;
//import com.facebook.drawee.view.SimpleDraweeView;
//import AppTool;
//import InterfaceImgTool;
//
///**
// * Created by coder on 15/12/24.
// */
//public class ImgToolFresco implements InterfaceImgTool {
//
//    int loadingDrawableId;
//    int failureDrawableId;
//
//
//    @Override
//    public void init(Context context, int loadingDrawableId, int failureDrawableId) {
//        if (context instanceof Application) {
//
//            this.loadingDrawableId=loadingDrawableId;
//            this.failureDrawableId=failureDrawableId;
//            Fresco.initialize(AppTool.getApplication());
//        }
//    }
//
//    @Override
//    public void loadImage(Object src, ImageView imageView) {
//
//        if(imageView instanceof SimpleDraweeView){
//            imageView.setImageURI(Uri.parse(""+src));
//        }
//
//    }
//
//    @Override
//    public void loadImage(Object src, ImageView imageView, int width, int height) {
//        loadImage(src,imageView);
//    }
//}
