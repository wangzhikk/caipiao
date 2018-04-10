package utils.wzutils.common;

import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import utils.wzutils.AppTool;

/**
 * Created by ishare on 2016/5/27.
 */
public class FileTool {

    /***
     * 获取缓存
     *
     * @param dirName
     * @return
     */
    public static File getCacheDir(String dirName) {
        File result;
        if (existsSdcard()) {
            File cacheDir = AppTool.getApplication().getExternalCacheDir();
            if (cacheDir == null) {
                result = new File(Environment.getExternalStorageDirectory(),
                        "Android/data/" + AppTool.getApplication().getPackageName() + "/cache/" + dirName);
            } else {
                result = new File(cacheDir, dirName);
            }
        } else {
            result = new File(AppTool.getApplication().getCacheDir(), dirName);
        }
        if (result.exists() || result.mkdirs()) {
            return result;
        } else {
            return null;
        }
    }

    /***
     * 删除缓存
     */
    public static void clearCache() {

        try {
            deleteDir(getCacheDir(""));
        }catch (Exception e){
            LogTool.ex(e);
        }

    }

    /**
     * 删除文件夹
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

    /***
     * 生成一个缓存文件路径
     * @param dirName
     * @param fileName
     * @return
     */
    public static File initCacheFile(String dirName,String fileName) {
        File result=null;
        try {
            result=new File(getCacheDir(dirName),fileName);
        }catch (Exception e){
            LogTool.ex(e);
        }
        return result;
    }
    /**
     * 检查磁盘空间是否大于10mb
     *
     * @return true 大于
     */
    public static boolean isDiskAvailable() {
        long size = getDiskAvailableSize();
        return size > 10 * 1024 * 1024; // > 10bm
    }

    /**
     * 获取磁盘可用空间
     *
     * @return byte 单位
     */
    public static long getDiskAvailableSize() {
        if (!existsSdcard()) return 0;
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getAbsolutePath());
        long blockSize = stat.getBlockSize();
        long availableBlocks = stat.getAvailableBlocks();
        return availableBlocks * blockSize;
    }

    public static Boolean existsSdcard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static long getFileOrDirSize(File file) {
        if (!file.exists()) return 0;
        if (!file.isDirectory()) return file.length();
        long length = 0;
        File[] list = file.listFiles();
        if (list != null) { // 文件夹被删除时, 子文件正在被写入, 文件属性异常返回null.
            for (File item : list) {
                length += getFileOrDirSize(item);
            }
        }
        return length;
    }


    /***
     * 读取指定流中所以字符串
     *
     * @param is
     * @return
     */
    public static String readAllString(InputStream is) {
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String tem = "";
            while (tem != null) {
                sb.append(tem);
                tem = br.readLine();
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return sb.toString();
    }


}
