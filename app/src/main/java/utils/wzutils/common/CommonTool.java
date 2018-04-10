package utils.wzutils.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import utils.wzutils.AppTool;

import static android.content.Context.ACTIVITY_SERVICE;


/**
 * Created by coder on 15/12/25.
 * 主要放一些常用小功能的, 注意不要添加当前项目特有的功能,也就是不能import wzutils之外的包进来
 */
public class CommonTool {
    static Toast toast;

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        float scale = 0;
        try {
            scale = AppTool.getApplication().getResources().getDisplayMetrics().density;
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = AppTool.getApplication().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断一个字符串是否有值, 有值 true , 没有false
     *
     * @param str
     * @return
     */
    public static boolean checkHasDataStr(String str) {
        boolean isNull = false;
        if (str == null) isNull = true;

        else if ("null".equals(str.toLowerCase())) isNull = true;
        else if (str.trim().length() < 1) isNull = true;
        return !isNull;

    }

    /**
     * 检查当前是否安装了某个包名
     *
     * @param packageName
     * @return
     */

    public static boolean checkApkExist(String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = AppTool.getApplication().getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 直接弹出一个Toast
     *
     * @param text
     */
    public static void showToast(final Object text) {
        UiTool.showToast("" + text);
    }

    public static void showToastLong(final Object text) {
        UiTool.showToastLong("" + text);
    }

    public static void showToast(int resId) {
        UiTool.showToast(resId);
    }


    /**
     * 根据Map 初始化字段
     *
     * @param classObj 要填充的对象
     * @param map      数据源
     */
    public static void initFieldByMap(Object classObj, Map<String, Object> map) {
        try {
            Field[] fields = classObj.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field field = fields[i];
                    field.setAccessible(true);
                    try {
                        Object object = map.get(field.getName());
                        if (object != null) {
                            field.set(classObj, object);
                        }
                    } catch (Exception e) {
                        // LogTool.ex(e);
                    }
                } catch (Exception e) {
                    // LogTool.ex(e);
                }
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /**
     * 根据一个 msp 列表 初始化一个 数据列表 **
     */
    public static void initFieldByMapList(ArrayList innerDatas,
                                          Class dataClass, ArrayList<Map<String, Object>> maps) {
        if (maps != null)
            for (int i = 0; i < maps.size(); i++) {
                Map<String, Object> temMap = maps.get(i);
                Object innerData = null;
                try {
                    innerData = dataClass.newInstance();
                    initFieldByMap(innerData, temMap);
                    innerDatas.add(innerData);
                } catch (Exception e) {
                    LogTool.ex(e);
                }
            }
    }


    /**
     * 检查当前网络是否可用
     *
     * @return
     */

    public static boolean isNetworkAvailable() {

        boolean isNetOpen = false;
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) AppTool.getApplication().getSystemService(AppTool.getApplication().CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        isNetOpen = true;
                        break;
                    }
                }
            }
        }
        if (isNetOpen) {
//            try {//不能在主现程中请求网络
//                return new Socket("www.baidu.com",80).isConnected();
//            } catch (Exception e) {
//                LogTool.ex(e);
//                LogTool.s("您的网络好像不能连接互联网");
//                return false;
//            }
        } else {
            LogTool.s("您的网络没有打开");
        }
        return isNetOpen;
    }

    /***
     * 获取设备号
     *
     * @return
     */
    public static String getDeviceId() {
        try {
            Context context = AppTool.getApplication();
            TelephonyManager tm = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
            return tm.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersion() {
        try {
            Context context = AppTool.getApplication();
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /***
     * 获取当前应用版本名称
     *
     * @return
     */
    public static String getVersionStr() {
        try {
            Context context = AppTool.getApplication();
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = null;
            try {
                pi = pm.getPackageInfo(context.getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            return pi.versionName;
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return "";

    }

    public static String getMetaData(String key){
        String result="";
        try {
            result= AppTool.getApplication().getPackageManager().getApplicationInfo(AppTool.getApplication().getPackageName(), PackageManager.GET_META_DATA).metaData.getString(key,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 判断程序是否是当前显示
    public static boolean isTopActivity(Activity activity) {
        try {
            if (activity == null) return false;

            ActivityManager activityManager = (ActivityManager) AppTool.currActivity
                    .getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager
                    .getRunningTasks(1);
            if (tasksInfo.size() > 0) {
                LogTool.s("---------------包名-----------"
                        + tasksInfo.get(0).topActivity.getPackageName());
                // 应用程序位于堆栈的顶层
                if (tasksInfo.get(0).topActivity.equals(activity.getComponentName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }

        return false;
    }

    /*****
     * 是否是前台程序
     **/
    public static boolean isForeground(Context context) {
        try {
            if (context == null) return false;
            ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                if (appProcess.processName.equals(context.getPackageName())) {
                    if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                        LogTool.s("后台");
                        return false;
                    } else {
                        LogTool.s("前台");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }

        return false;
    }


    /**
     * 获取屏幕宽高
     */
    public static Point getWindowSize() {
        Point point = new Point();
        AppTool.currActivity.getWindowManager().getDefaultDisplay().getSize(point);
        return point;
    }

    /***
     * 关闭软键盘
     */
    public static void hideSoftInput() {
        try {
            InputMethodManager inputmanger = (InputMethodManager) AppTool.currActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(AppTool.currActivity.getWindow().getDecorView().getWindowToken(), 0);
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }
    public static boolean isSoftInputIsActive(){
        try {
            InputMethodManager inputmanger = (InputMethodManager) AppTool.currActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
            return inputmanger.isActive();
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return false;
    }
    /**
     * EditText获取焦点并显示软键盘
     */
    public static void showSoftInputFromWindow(Activity activity, EditText editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputmanger = (InputMethodManager) AppTool.currActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.showSoftInput(editText,InputMethodManager.SHOW_FORCED);
        //activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    /***
     * 循环滚动的 position 计算
     *
     * @param dataSize      数据的size大小
     * @param beginInMax    无限循环的 开始 position
     * @param positionInMax 当前 无限循环的position
     * @return 实际的 position
     */
    public static int loopPosition(int dataSize, int beginInMax, int positionInMax) {
        if (dataSize < 1) return 0;

        int result = 0;
        if (positionInMax > beginInMax) {//在右边
            result = positionInMax - beginInMax;
            result = result % dataSize;
        } else {//在左边
            result = positionInMax - beginInMax;
            result = result % dataSize;
            result = dataSize + result;
        }
        if (dataSize == result) result = 0;
        return result;
    }


    /****
     * 跳转应用商店
     *
     * @param context
     */
    public static void goMarket(Context context) {

        StringBuilder localStringBuilder = new StringBuilder().append("market://details?id=");
        String str = context.getPackageName();
        localStringBuilder.append(str);
        Uri localUri = Uri.parse(localStringBuilder.toString());
        Intent intent = new Intent("android.intent.action.VIEW", localUri);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);

    }

    public static String getStrByResId(int resId) {
        try {
            return AppTool.getApplication().getResources().getString(resId);

        } catch (Exception e) {
            LogTool.ex(e);
        }
        return "";
    }

    /**
     * 获取当前程序进程名称
     * @return
     */
    public static String getProcessName(Context context){
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                    .getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }
    public static boolean notEmptyList(List list) {
        return list != null && !list.isEmpty();
    }
}

