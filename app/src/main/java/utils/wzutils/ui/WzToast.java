package utils.wzutils.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import utils.wzutils.AppTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.ViewTool;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by ishare on 2016/10/17.
 */
public class WzToast {

    final static int keyShow = ViewTool.initKey();
    volatile static Toast toast;
    static TextView tvToast;
    static WindowManager windowManager;

    public WzToast() {
    }

    public void showWzToast(final Object toastStr) {
        showWzToast(toastStr, 2000);
    }

    public void showWzToastLong(final Object toastStr) {
        showWzToast(toastStr, 3000);
    }

    public void showWzToast(final Object toastStr, final long time) {
        if (StringTool.isEmpty("" + toastStr)) return;
        if (!CommonTool.isForeground(AppTool.getApplication())) return;
        AppTool.uiHandler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    if (toast == null) {
                        toast = Toast.makeText(AppTool.getApplication(), "", Toast.LENGTH_SHORT);
                    }

                    toast.setText("" + toastStr);
                    LogTool.s("showToast:   " + toastStr);
                    Field field_mTN = Toast.class.getDeclaredField("mTN");
                    field_mTN.setAccessible(true);
                    final Object mTN = field_mTN.get(toast);

                    Field field_mNextView = mTN.getClass().getDeclaredField("mNextView");
                    field_mNextView.setAccessible(true);
                    field_mNextView.set(mTN, toast.getView());

                    {//两个版本的toast
                        try {
                            Method method_show = mTN.getClass().getMethod("show");
                            method_show.invoke(mTN);
                        } catch (Exception e) {
                            LogTool.ex(e);
                            Method method_show = mTN.getClass().getMethod("show", IBinder.class);//android 8后面改了
                            method_show.invoke(mTN, toast.getView().getWindowToken());
                        }
                    }




                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (this.equals(ViewTool.getTag(toast.getView(), keyShow))) {
                                    Method method_show = mTN.getClass().getMethod("hide");
                                    method_show.invoke(mTN);
                                }
                            } catch (Exception e) {
                                LogTool.ex(e);
                            }
                        }
                    };
                    toast.getView().postDelayed(runnable, time);
                    ViewTool.setTag(toast.getView(), runnable, keyShow);
                } catch (Exception e) {
                    LogTool.ex(e);
                    try {
                        if (toast != null) toast.show();
                    } catch (Exception e2) {
                        LogTool.ex(e2);
                    }
                }
            }
        });

    }

    /***
     * 不能跨 activit  。。。。
     * @param toastStr
     */
    @Deprecated
    public void showToast(Object toastStr) {
        try {
            Context context = AppTool.currActivity;
            try {
                if (tvToast != null && windowManager != null) {
                    windowManager.removeView(tvToast);
                }
            } catch (Exception e) {
            }


            tvToast = new TextView(context);
            tvToast.setGravity(Gravity.CENTER);
            tvToast.setTextSize(13);
            tvToast.setTextColor(Color.WHITE);
            tvToast.setBackgroundDrawable(Toast.makeText(context, "", Toast.LENGTH_SHORT).getView().getBackground());
            tvToast.setText("" + toastStr);

            windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);

            final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.TRANSLUCENT;
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION;
            params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            params.x = 0;
            params.y = 100;

            windowManager.addView(tvToast, params);

            Runnable runnableRemove = new Runnable() {
                @Override
                public void run() {
                    try {
                        if (this.equals(ViewTool.getTag(tvToast, keyShow))) {
                            windowManager.removeView(tvToast);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            ViewTool.setTag(tvToast, runnableRemove, keyShow);
            tvToast.postDelayed(runnableRemove, 3000);
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }


    /***
     * 小米不行
     * @param toastStr
     */
    @Deprecated
    public void showWzToastOld(Object toastStr) {

        if (tvToast == null) {
            tvToast = new TextView(AppTool.currActivity);
            tvToast.setGravity(Gravity.CENTER);
            tvToast.setTextSize(13);
            tvToast.setTextColor(Color.WHITE);
            tvToast.setBackgroundDrawable(Toast.makeText(AppTool.getApplication(), "", Toast.LENGTH_SHORT).getView().getBackground());


            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.format = PixelFormat.TRANSLUCENT;
            params.type = WindowManager.LayoutParams.TYPE_TOAST;
            params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            params.gravity = Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM;
            Context context = tvToast.getContext().getApplicationContext();
            windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            params.x = 0;
            params.y = 100;
            windowManager.addView(tvToast, params);
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (this.equals(ViewTool.getTag(tvToast, keyShow))) {
                    tvToast.setVisibility(View.GONE);
                }
            }
        };
        tvToast.setText("" + toastStr);
        tvToast.setVisibility(View.VISIBLE);
        tvToast.postDelayed(runnable, 2000);
        ViewTool.setTag(tvToast, runnable, keyShow);

    }
}
