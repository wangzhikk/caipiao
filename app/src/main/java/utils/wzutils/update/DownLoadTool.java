package utils.wzutils.update;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

import utils.wzutils.AppTool;
import utils.wzutils.encypt.Md5Tool;

/**
 * Created by kk on 2016/5/18.
 * 老是有bug ， 不用了
 */
@Deprecated
public class DownLoadTool {
    private static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
    private static DownloadManager downloadManager;
    private static ArrayList<DownLoadProgressListener> downLoadProgressListeners = new ArrayList<>();
    private static ArrayList<Long> idList = new ArrayList<>();
    private static BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            System.out.println("完成了： " + id);
            queryDownloadStatus(id);
        }
    };
    private static ContentObserver contentObserver = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange, uri);
            queryDownloadStatus(-1);
        }
    };

    /**
     * 查询状态，
     */
    private static void queryDownloadStatus(long completeId) {
        DownloadManager.Query query = new DownloadManager.Query();
        long[] ids = new long[idList.size()];
        for (int i = 0; i < idList.size(); i++) {
            ids[i] = idList.get(i).longValue();
        }
        query.setFilterById(ids);
        Cursor c = null;
        int allSize=0;
        try {
            c = downloadManager.query(query);
            while (c.moveToNext()) {
                long id = c.getLong(c.getColumnIndex(DownloadManager.COLUMN_ID));
                int temAllSize = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                if(temAllSize>0)allSize=temAllSize;//因为有时候完成的时候 allSize=0了， 导致后面的报错 zuk上出现的
                int currDownLoadSize = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                String filePath = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
                String url = c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI));
                Log.v("DownLoadTool", "下载： " + id + "---" + allSize + "---" + currDownLoadSize + "---" + url + "--" + filePath);
                if (downLoadProgressListeners != null) {
                    for (int i = 0; i < downLoadProgressListeners.size(); i++) {
                        DownLoadProgressListener loadProgressListener = downLoadProgressListeners.get(i);
                        if (id == loadProgressListener.id) {
                            boolean complete = completeId == loadProgressListener.id;
                            if (allSize == currDownLoadSize) {
                                complete = true;
                            }
                            loadProgressListener.onProgress(id, url, filePath, allSize, currDownLoadSize, complete);
                            if (complete) {
                                downLoadProgressListeners.remove(loadProgressListener);
                                idList.remove(new Long(id));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载指定路径文件
     *
     * @return
     */
    public static long downLoad(Context context, String url, DownLoadProgressListener downLoadProgressListener) {
        if (downloadManager == null) {
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            //监听完成的 ， 完成一个会调用一个
            context.registerReceiver(broadcastReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            //监听进度的
            context.getContentResolver().registerContentObserver(CONTENT_URI, true, contentObserver);
        }

        if (downLoadProgressListener != null && !downLoadProgressListeners.contains(downLoadProgressListener)) {
            downLoadProgressListeners.add(downLoadProgressListener);
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDestinationInExternalPublicDir(Md5Tool.md5_16(AppTool.getApplication().getPackageName()), Md5Tool.md5_16(url) + ".apk");//部分oppo 手机上 只能用apk 后缀才能安装
        long id = downloadManager.enqueue(request);
        if (downLoadProgressListener != null) {
            downLoadProgressListener.id = id;
        }
        idList.add(id);
        return id;
    }

    /***
     * 删除一个下载
     *
     * @param id
     */
    public static void removeDownLoad(long... id) {
        downloadManager.remove(id);
    }

    public static abstract class DownLoadProgressListener {
        public long id;

        public abstract void onProgress(long id, String url, String localPath, long allSize, long currDownLoadSize, boolean isComplete);
    }

}
