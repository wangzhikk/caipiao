package utils.wzutils.common;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import utils.wzutils.AppTool;

/**
 * Created by ishare on 2016/7/7.
 * <p>
 * 本地图片的一些操作工具
 */
public class ImgLocalTool {
    /***
     * 三星手机上 会自动 把拍照旋转了，  所以这里要改回来
     *
     * @param path
     * @param ops
     */
    public static void rotateReset(String path, BitmapFactory.Options ops) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
            int rotate = 0;
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
                default:
                    break;
            }
            if (rotate > 0) {
                Bitmap bitmap = BitmapFactory.decodeFile(path, ops);
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();
                Matrix mtx = new Matrix();
                mtx.setRotate(rotate, w / 2, h / 2);
                Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
                result.compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(path));
                bitmap.recycle();
                result.recycle();
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }

    private static BitmapFactory.Options getOptionsByMaxWH(String path, int maxW, int maxH) {
        BitmapFactory.Options ops = new BitmapFactory.Options();
        ops.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, ops);

        int widthRatio = 1;
        int heightRatio = 1;
        float w = maxW;
        float h = maxH;
        if (w > 0) widthRatio = (int) Math.ceil(ops.outWidth
                / w);
        if (h > 0) heightRatio = (int) Math.ceil(ops.outHeight
                / h);
        if (heightRatio > 1 || widthRatio > 1) {
            ops.inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        ops.inJustDecodeBounds = false;
        return ops;
    }

    /***
     * 将以张图  转换为 小于指定最大宽高的图片
     *
     * @param path
     * @param maxW
     * @param maxH
     * @param listener
     */
    public static void convertToSmallBitmap(final String path, final int maxW, final int maxH, final OnConvertSuccessListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File fileInput = new File(path);
                try {

                    BitmapFactory.Options ops = getOptionsByMaxWH(path, maxW, maxH);
                    rotateReset(path, ops);
                    Bitmap bitmap = BitmapFactory.decodeFile(path, ops);
                    try {
                        final File fileOut = new File(FileTool.getCacheDir("camerasmall"), fileInput.getName());
                        FileOutputStream fileOutputStream = new FileOutputStream(fileOut);
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
                        LogTool.s("缩小图片" + bitmap.getWidth() + "--" + bitmap.getHeight());
                        bitmap.recycle();
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        LogTool.s("缩小图片成功" + fileOut.getAbsolutePath() + "文件大小" + fileOut.length());
                        if (listener != null) {
                            AppTool.uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    listener.succeed(fileOut);
                                }
                            });
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    LogTool.ex(e);
                }
                LogTool.s("缩小图片失败");
                if (listener != null) listener.succeed(fileInput);
            }
        }).start();

    }
    /**
     * @param bmp     获取的bitmap数据
     * @param picName 自定义的图片名
     */
    public static void saveBmp2Gallery(Bitmap bmp, String picName) {

        try {
            String fileName = null;
            //系统相册目录
            String galleryPath = Environment.getExternalStorageDirectory()
                    + File.separator + Environment.DIRECTORY_DCIM
                    + File.separator + "Camera" + File.separator;

            // 声明文件对象
            File file = null;
            // 声明输出流
            FileOutputStream outStream = null;

            try {
                // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
                file = new File(galleryPath, picName + ".jpg");

                // 获得文件相对路径
                fileName = file.toString();
                // 获得输出流，如果文件中有内容，追加内容
                outStream = new FileOutputStream(fileName);
                if (null != outStream) {
                    bmp.compress(Bitmap.CompressFormat.PNG, 90, outStream);
                }

            } catch (Exception e) {
                e.getStackTrace();
            } finally {
                try {
                    if (outStream != null) {
                        outStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //通知相册更新
            MediaStore.Images.Media.insertImage(AppTool.getApplication().getContentResolver(),
                    bmp, fileName, null);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            AppTool.getApplication().sendBroadcast(intent);

        }catch (Exception e){
            LogTool.ex(e);
        }

    }

    public interface OnConvertSuccessListener {
        void succeed(File outFile);
    }
}
