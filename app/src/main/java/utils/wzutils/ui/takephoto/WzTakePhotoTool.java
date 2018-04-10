package utils.wzutils.ui.takephoto;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import java.io.File;
import java.util.Calendar;

import utils.wzutils.common.UriTool;
import utils.wzutils.db.MapDB;

/**
 * Created by kk on 2016/7/5.
 * 使用方式 ：
 * <p>
 * takePhotoTool.showDefaultChooseDialog();
 * <p>
 * <p>
 * 数据获取：
 *
 * @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
 * super.onActivityResult(requestCode, resultCode, data);
 * Uri uri=takePhotoTool.readCurrChoosePhotoUri(data);
 * if(requestCode!=WzTakePhotoTool.requestCodeCrop){//如果需要裁剪才这样写
 * takePhotoTool.goCrop(uri,480,480);
 * }else {
 * datas.add(uri.toString());
 * initListView();
 * }
 * }
 */
public class WzTakePhotoTool {
    public static final int requestCodeCamera = 1;//去拍照
    public static final int requestCodeFile = 2;//去选择文件
    public static final int requestCodeCrop = 3;//去裁剪
    final String keyCurrPath = "keyCurrPath";
    File cacheDir;
    Activity activity;
    Fragment fragment;
    Context context;

    public WzTakePhotoTool(Activity activity) {
        this.activity = activity;
        context = activity;
        cacheDir = initTakePhotoCacheDir(activity);
    }

    public WzTakePhotoTool(Fragment fragment) {
        this.fragment = fragment;
        context = fragment.getContext();
        cacheDir = initTakePhotoCacheDir(fragment.getContext());
    }

    /***
     * 初始化缓存图片目录
     *
     * @param context
     */
    File initTakePhotoCacheDir(Context context) {
        File cacheDir = context.getCacheDir();
        try {
            File parentDir = context.getExternalCacheDir();
            if (!parentDir.canWrite()) {
                parentDir = context.getCacheDir();
            }
            cacheDir = new File(parentDir, "photo");
            if (!cacheDir.exists()) {
                cacheDir.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cacheDir;
    }


    /***
     * 生成一个临时保存拍照的文件
     *
     * @return
     */
    File newCameraOutFile() {
        Calendar calendar = Calendar.getInstance();
        String fileName = "IMG_" + calendar.get(Calendar.YEAR) + "_" + (calendar.get(Calendar.MONTH) + 1) + "_" + calendar.get(Calendar.DAY_OF_MONTH) + "_"
                + calendar.get(Calendar.HOUR_OF_DAY) + "_"
                + calendar.get(Calendar.MINUTE) + "_"
                + calendar.get(Calendar.SECOND) + "_" + calendar.get(Calendar.MILLISECOND)
                + ".jpg";
        return new File(cacheDir, fileName);
    }

    /**
     * 构建一个去相册的Intent
     *
     * @return
     */
    Intent getGoCameraIntent() {
        System.gc();
        File outPutFile = newCameraOutFile();
        saveCurrFile(outPutFile);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));
        return intent;
    }

    /**
     * 构建一个去获取图片的Intent
     *
     * @return
     */
    Intent getGoGetFileIntent() {
        System.gc();
        Intent intent = null;
        {
            intent = new Intent(Intent.ACTION_GET_CONTENT);//华为mt7 上面 这个 返回的uri 中 查询不到路径
            intent.setType("image/*");
        }
        //intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI); // 这个可能会选一张出来多张
        return intent;
    }

    /**
     * 构建一个裁剪的Intent
     *
     * @param uri
     */
    Intent getGoCropIntent(Uri uri, int outPutX, int outPutY) {
        System.gc();
        File outPutFile = newCameraOutFile();
        saveCurrFile(outPutFile);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outPutX);
        intent.putExtra("outputY", outPutY);
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));
        return intent;
    }

    /***
     * 到相册去拍照
     */
    public void goCrop(Uri srcUri, int outPutX, int outPutY) {
        Intent intent = getGoCropIntent(srcUri, outPutX, outPutY);
        if (activity != null) {
            activity.startActivityForResult(intent, requestCodeCrop);
        } else if (fragment != null) {
            fragment.startActivityForResult(intent, requestCodeCrop);
        }
    }

    /***
     * 到相册去拍照
     */
    public void goCrop(String filePath, int outPutX, int outPutY) {
        goCrop(Uri.fromFile(new File(filePath)), outPutX, outPutY);
    }


    /***
     * 到相册去拍照
     */
    public void goCamera() {
        clearCurrFile();
        Intent intent = getGoCameraIntent();
        if (activity != null) {
            activity.startActivityForResult(intent, requestCodeCamera);
        } else if (fragment != null) {
            fragment.startActivityForResult(intent, requestCodeCamera);
        }
    }


    /***
     * 选择图片
     */
    public void goFile() {
        clearCurrFile();
        Intent intent = getGoGetFileIntent();
        if (activity != null) {
            activity.startActivityForResult(intent, requestCodeFile);
        } else if (fragment != null) {
            fragment.startActivityForResult(intent, requestCodeFile);
        }
    }


    /**
     * 显示 拍照或者选文件的dialog
     */
    public void showDefaultChooseDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setItems(new String[]{"立即拍照", "选择文件"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            goCamera();
                        } else {
                            goFile();
                        }
                    }
                })
                .create();
        alertDialog.show();
    }

    /***
     * 清除本地 缓存的 当前 获取图片的路径，
     */
    public void clearCurrFile() {
        saveCurrFile(null);
    }

    /***
     * 保存当前图片的路径到本地
     *
     * @param outPutFile
     */
    void saveCurrFile(File outPutFile) {
        String path = "";
        if (outPutFile != null) {
            path = outPutFile.getAbsolutePath();
        }
        MapDB.saveObj(keyCurrPath, path);
    }

    /***
     * 从本地读取 上次 拍照的 路径
     *
     * @param data
     * @return
     */
    public String readCurrChoosePhotoUri(Intent data) {
        if (data != null && data.getData() != null) {//如果data 里面有数据就把data 里面的数据返回
            return UriTool.uriToFilePath(data.getData());
        }
        String path = MapDB.loadObj(keyCurrPath, String.class);//读本地数据
        return path;
    }
}
