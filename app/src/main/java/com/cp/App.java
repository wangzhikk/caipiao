package com.cp;

import android.app.Application;

import com.cp.im.IMTool;

import utils.wzutils.AppTool;
import utils.wzutils.ui.WzSimpleRecycleView;

/**
 * Created by wz on 2018/2/24.
 */

public class App extends Application {
    public static boolean isDebug=true;

    @Override
    public void onCreate() {
        super.onCreate();
        AppTool.init(this,isDebug);
        WzSimpleRecycleView.defaultEmptyResId=R.layout.nodata;
        IMTool.getIntance().login();//登录消息服务器， 放这里是为了 被销毁后还能重新启动
    }
}
