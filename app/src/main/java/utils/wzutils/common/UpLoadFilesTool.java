package utils.wzutils.common;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import utils.wzutils.AppTool;
import utils.wzutils.common.thread.ThreadTool;

/**
 * Created by wz on 2017/6/9.
 */
public class UpLoadFilesTool {
    /***
     *
     * @param upLoadData  上传数据
     * @param upLoadImp  上传的具体实现，由外部实现
     */
    public static void upImage(final UpLoadData upLoadData, final UpLoadImp upLoadImp) {
        if (upLoadData.file.length() > 300 * 1024) {//图片太大， 需要压缩
            ImgLocalTool.convertToSmallBitmap(upLoadData.file.getAbsolutePath(), 800, 1200, new ImgLocalTool.OnConvertSuccessListener() {
                @Override
                public void succeed(File outFile) {
                    upLoadData.file = outFile;
                    upLoadImp.upLoadImpl(upLoadData);
                }
            });
        } else {
            upLoadImp.upLoadImpl(upLoadData);
        }
    }

    /***
     * 多图上传
     * @param upLoadDataList
     * @param upLoadImp
     * @param upLoadFilesListener  上传监听
     */
    public static void upLoadImages(final List<UpLoadData> upLoadDataList, final UpLoadImp upLoadImp, final UpLoadFilesListener upLoadFilesListener) {
        if (upLoadDataList == null) return;
        upLoadFiles(upLoadDataList, new UpLoadImp() {
            @Override
            public void upLoadImpl(UpLoadData upLoadData) {
                upImage(upLoadData,upLoadImp);
            }
        },upLoadFilesListener);
    }


    /***
     * 多文件上传  注意是文件。。 如果是图片用上面的， 可以帮助压缩
     * @param upLoadDataList
     * @param upLoadImp
     * @param upLoadFilesListener
     */
    public static void upLoadFiles(final List<UpLoadData> upLoadDataList, final UpLoadImp upLoadImp, final UpLoadFilesListener upLoadFilesListener) {
        if (upLoadDataList == null) return;
        ThreadTool.execute(new Runnable() {
            @Override
            public void run() {
                final ArrayList<String> serverPath = new ArrayList<String>();
                final HashMap<String, String> localPathToServerPathMap = new HashMap<>();//
                final CountDownLatch countDownLatch = new CountDownLatch(upLoadDataList.size());
                for (int i = 0; i < upLoadDataList.size(); i++) {
                    try {
                        final UpLoadData upLoadData = upLoadDataList.get(i);
                        AppTool.uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                upLoadData.upLoadImpSuccess = new UpLoadImpSuccess() {
                                    @Override
                                    public void onSuccess(boolean dataOk, String serverPath) {
                                        try {
                                            if (dataOk) {
                                                if (upLoadFilesListener != null) {
                                                    upLoadFilesListener.onUpLoadFileEnd(upLoadData.file.getAbsolutePath(), serverPath);
                                                }
                                                localPathToServerPathMap.put(upLoadData.file.getAbsolutePath(), serverPath);//用这个做中间转换保证 上传顺序
                                            } else {
                                                if (upLoadFilesListener != null) {
                                                    upLoadFilesListener.onUpLoadFileEnd(upLoadData.file.getAbsolutePath(), "");
                                                }
                                            }
                                        } catch (Exception e) {
                                            LogTool.ex(e);
                                        }
                                        countDownLatch.countDown();
                                    }
                                };
                                upLoadImp.upLoadImpl(upLoadData);
                            }
                        });
                    } catch (Exception e) {
                        LogTool.ex(e);
                    }
                }
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (upLoadFilesListener != null) {

                    for (int i = 0; i < upLoadDataList.size(); i++) {
                        String serverPathTem = localPathToServerPathMap.get(upLoadDataList.get(i).file.getAbsolutePath());
                        if (!StringTool.isEmpty(serverPathTem)) {
                            serverPath.add(serverPathTem);
                        }
                    }
                    AppTool.uiHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            upLoadFilesListener.onUpLoadFileEndAll(serverPath);
                        }
                    });

                }
            }
        });
    }


    /***
     * 上传文件的具体 接口调用， 不同app 不一样
     */
    public static interface UpLoadImp {
        void upLoadImpl(UpLoadData upLoadData);
    }

    public static interface UpLoadImpSuccess {
        void onSuccess(boolean dataOk, String serverPath);
    }

    public interface UpLoadFilesListener {
        void onUpLoadFileEnd(String localPath, String serverPath);

        void onUpLoadFileEndAll(List<String> serverPaths);
    }

    public static class UpLoadData {
        public File file;//要上传的文件
        /***
         * 需要在上传成功之后调用下面这一句， 其他的 在监听器里面调用， 能保证顺序
         *  upLoadData.upLoadImpSuccess.onSuccess(data.isDataOk(),serverPath);
         *
         *
         */
        protected UpLoadImpSuccess upLoadImpSuccess;//
        public void notifyUploadEnd(boolean dataOk, String serverPath){
            try {
                if(upLoadImpSuccess!=null){
                    upLoadImpSuccess.onSuccess(dataOk,serverPath);
                }
            }catch (Exception e){
                LogTool.ex(e);
            }
        }
        public UpLoadData(File file) {
            this.file = file;
        }
    }
}
