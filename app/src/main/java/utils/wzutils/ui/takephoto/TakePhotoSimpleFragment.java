package utils.wzutils.ui.takephoto;

import android.app.Activity;
import android.content.Intent;

import com.yanzhenjie.album.Album;

import java.util.ArrayList;

import static com.yanzhenjie.fragment.NoFragment.RESULT_CANCELED;
import static com.yanzhenjie.fragment.NoFragment.RESULT_OK;

/**
 * abc on 2017/5/25.
 *
 *
 *     compile 'com.yanzhenjie:album:1.0.7'
 *
     new TakePhotoSimpleFragment().addToParent(supportFragmentManager, R.id.vg_dongtai_fabu_tupian, R.layout.dongtai_fabu_tupian_item, maxSize,
 new WzTakePhotoFragment.OnAddPhotoInitDataListenerImpDefault(R.id.imgv_add_photo, R.id.imgv_delete_photo, R.drawable.kk_send_picture_add, R.drawable.kk_send_picture_add));
 *
 */

public class TakePhotoSimpleFragment extends WzTakePhotoFragment {
    @Override
    public void showChooseDialog() {
//        PhotoPicker.builder()
//                .setPhotoCount(maxSize)
//                .setShowCamera(true)
//                .setShowGif(true)
//                .setPreviewEnabled(true)
//                .setSelected(getSelectPhoto())
//                .start(getContext(), this, PhotoPicker.REQUEST_CODE);




        {//使用  album//https://github.com/yanzhenjie/Album/blob/master/README-CN.md
            Album.album(this)
                    //.toolBarColor(toolbarColor) // Toolbar 颜色，默认蓝色。
                    //.statusBarColor(statusBarColor) // StatusBar 颜色，默认蓝色。
                    //.navigationBarColor(navigationBarColor) // NavigationBar 颜色，默认黑色，建议使用默认。
                    .title("图库") // 配置title。
                    .selectCount(maxSize) // 最多选择几张图片。
                    .columnCount(3) // 相册展示列数，默认是2列。
                    .camera(true) // 是否有拍照功能。
                    .checkedList(datas) // 已经选择过得图片，相册会自动选中选过的图片，并计数。
                    .start(999); // 999是请求码，返回时onActivityResult()的第一个参数。
        }



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == Activity.RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
//            if (data != null) {
//                ArrayList<String> photos =
//                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
//                datas = photos;
//                initListView();
//            }
//        }


        {//使用  album
            if(requestCode == 999) {
                if (resultCode == RESULT_OK) { // Successfully.
                    // 不要质疑您的眼睛，就是这么简单。
                    ArrayList<String> pathList = Album.parseResult(data);
                    datas=pathList;
                    initListView();
                } else if (resultCode == RESULT_CANCELED) { // User canceled.
                    // 用户取消了操作。
                }
            }
        }


    }
}
