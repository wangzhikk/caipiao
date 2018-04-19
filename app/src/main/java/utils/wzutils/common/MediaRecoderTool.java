package utils.wzutils.common;

import android.hardware.Camera;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * abc on 2017/6/29.
 */

public class MediaRecoderTool {
    SurfaceView surfaceView;
    Camera camera;
    MediaRecorder mMediaRecorder;
    boolean isOpenCamera = false;//摄像头是否打开了
    int rotation=0;//横屏用0  竖屏用90
    /***
     * 视频录制才需要调用
     *
     * @param surfaceView
     */
    public void init(SurfaceView surfaceView) {
        this.surfaceView = surfaceView;
        surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);//必须否则崩溃
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    initCamera();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                try {
                    autoFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                stop();
            }
        });
        surfaceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoFocus();
            }
        });
    }

    /***
     * 对焦一次
     */
    void autoFocus() {
        try {
            if (camera != null) {
                camera.autoFocus(new Camera.AutoFocusCallback() {
                    @Override
                    public void onAutoFocus(boolean success, Camera camera) {
                        System.out.println("自动对焦" + success);
                        setFocus();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 初始化相机
     */
    void initCamera() {
        try {
            if (surfaceView != null) {
                if (!isOpenCamera) {
                    camera = Camera.open();
                    //camera.getParameters().setFocusMode("auto");
                    camera.setDisplayOrientation(rotation);
                    camera.setPreviewDisplay(surfaceView.getHolder());
                    setFocus();//必须在unlock之前
                    camera.unlock();
                    isOpenCamera = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 设置连续对焦，初始化的时候必须在  camera.unlock();之前调用
     */
    void setFocus() {
        Camera.Parameters parameters = camera.getParameters();
        // parameters.setPictureFormat(PixelFormat.JPEG);
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);//1连续对焦
        camera.setParameters(parameters);
        camera.startPreview();
        camera.cancelAutoFocus();// 2如果要实现连续的自动对焦，这一句必须加上
    }

    /***
     * 开始视频录制
     */
    public void startVideoRecode(String path) {
        try {
            initMedia(path);
            mMediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /***
     * 开始音频录制
     */
    public void startAudioRecode(String path) {
        try {
            initAudioRecoder(path);
            mMediaRecorder.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /***
     * 初始化视频录制
     */
     void initMedia(String path) {
        try {
            mMediaRecorder = new MediaRecorder();
            initCamera();
            if (camera != null) {
                mMediaRecorder.setCamera(camera);
            }
            mMediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());
            mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);// 视频源
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 音频源
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);// 视频输出格式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);// 音频格式
            mMediaRecorder.setVideoSize(1280, 720);// 设置分辨率：
            // mMediaRecorder.setVideoFrameRate(16);// 这个我把它去掉了，感觉没什么用
            mMediaRecorder.setVideoEncodingBitRate(4 * 1280 * 720);// 设置帧频率，然后就清晰了
            mMediaRecorder.setOrientationHint(rotation);// 输出旋转90度，保持竖屏录制,最好和摄像头保持一致
            mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);// 视频录制格式
            // mediaRecorder.setMaxDuration(Constant.MAXVEDIOTIME * 1000);
            mMediaRecorder.setOutputFile(path);
            mMediaRecorder.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 初始化音频录制
     * @param path
     */
     void initAudioRecoder(String path) {
        try {
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 音频源
            mMediaRecorder.setAudioEncodingBitRate(9999999);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);// 视频输出格式
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);// 音频格式

         //   mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);// 视频输出格式
         //   mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);// 音频格式

            mMediaRecorder.setOutputFile(path);
            mMediaRecorder.prepare();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 停止录制
     */
    public void stop() {
        try {
            isOpenCamera = false;
            mMediaRecorder.setOnErrorListener(null);
            mMediaRecorder.stop();
            mMediaRecorder.setPreviewDisplay(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static  MediaPlayer mediaPlayer=new MediaPlayer();

    /**
     * 全局共用一个media播放
     * @param path
     */
    public static MediaPlayer mediaStartPlaySingle(String path, MediaPlayer.OnCompletionListener onCompletionListener) {
        try {
           mediaStartPlaySingle(path,0,onCompletionListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }
    /**
     * 全局共用一个media播放
     * @param path
     */
    public static MediaPlayer mediaStartPlaySingle(String path,int seekDuration, MediaPlayer.OnCompletionListener onCompletionListener) {
        try {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.seekTo(seekDuration);
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(onCompletionListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }

    /***
     * 停止全局 media播放
     */
    public static void mediaStopPlaySingle() {
        try {
            mediaPlayer.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static int mediaPausePlaySingle() {
        try {
            mediaPlayer.pause();
            return mediaPlayer.getCurrentPosition();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static boolean mediaIsPlayingSingle() {
        try {
           return mediaPlayer.isPlaying();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /***
     * 测试播放音频的
     */
    public MediaPlayer testPlay(String path) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }

    public static long getDuration(String path){
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            return mediaPlayer.getDuration()/1000;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static String getDurationStr(long audioDuration){
        try {
            long fen=audioDuration/60;
            long miao=audioDuration%60;

            String fenStr=String.format("%02d",fen);
            String miaoStr=String.format("%02d",miao);

            return ""+fenStr+":"+miaoStr;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getDurationStr(String  path){
        try {
            return getDurationStr(getDuration(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
