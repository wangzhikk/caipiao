package utils.wzutils;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.db.MapDB;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.bigimage.WzBigImgListFragment;
import utils.wzutils.ui.pullrefresh.WzRefreshImp;


/**
 * Created by coder on 15/12/25.
 * 使用之前必须调用 init 初始化
 */
public class AppTool {
    /**
     * 当前界面的activity
     * 由于viewpager 里面的界面初始化可能有延迟， 如果使用这个的话会导致 可能导致 初始化的new WzImageView(curractivy)  的context 已经是下一个界面的了， 就会导致图片不显示，所以使用new WzImageView(container.getContext())
     * 类似情况都要谨慎使用 currActivity,  这个如果全局有引用会导致内存泄漏，比如toast 会引用第一个级面，，
     */
    public static Activity currActivity;
    public static Handler uiHandler;
    private static Application app;
    private static ArrayList<WeakReference<Activity>> runningActivity = new ArrayList<>();
    public static boolean isDebug;
    /**
     * 使用之前必须调用 init 初始化
     *
     * @param application
     */
    public static void init(Application application,boolean isDebugIn) {
        if(application.getPackageName().equals(CommonTool.getProcessName(application))){//避免启动多少次
            isDebug=isDebugIn;
            app = application;
            initUiHander();
            initRecycleLife();
            ImgTool.init(application, 0, 0);
            HttpTool.init(application);
            MapDB.init(isDebugIn);
        }
    }

    /**
     * 初始化uihandler
     */
    public static void initUiHander() {
        uiHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
    }

    /**
     * 获取应用程序 Application 对象
     *
     * @return Application 对象
     */
    public static Application getApplication() {
        if (app == null) {
            Log.e("wz","-----------------------------------------------------------使用AppTool前必须先调用  init(Application) 方法初始化------------------------------------------------------------------");
        }
        return app;
    }

    /***
     * 退出应用程序
     */
    public static void exitApp() {
        try {
            AppTool.currActivity.moveTaskToBack(true);
            if(true)return;
            // getApplication().sendBroadcast(new Intent("actionExit"));
            for (int i = 0; i < runningActivity.size(); i++) {
                try {
                    Activity activity=runningActivity.get(i).get();
                    if(activity!=null)activity.finish();
                } catch (Exception e) {
                    LogTool.ex(e);
                }
            }
            System.exit(0);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static ArrayList<Activity> getRunningActivitys() {
        ArrayList<Activity> result = new ArrayList<>();
        for (int i = 0; i < runningActivity.size(); i++) {
            Activity activity = runningActivity.get(i).get();
            if (activity!=null&&!activity.isFinishing()) {
                result.add(activity);
            }
        }

        return result;
    }

    /**
     * 初始化程序每个 Actvity 的生命周期回调
     */
    private static void initRecycleLife() {
        getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {

            @Override
            public void onActivityCreated(final Activity activity, Bundle bundle) {
                LogTool.printClassLine("onActivityCreated", activity);
                currActivity = activity;
                runningActivity.add(new WeakReference<Activity>(currActivity));
            }

            @Override
            public void onActivityStarted(Activity activity) {
                LogTool.printClassLine("onActivityStarted", activity);
                currActivity = activity;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                LogTool.printClassLine("onActivityResumed", activity);
                currActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                LogTool.printClassLine("onActivitySaveInstanceState", activity);
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                LogTool.printClassLine("onActivityDestroyed", activity);

            }
        });
    }


}
