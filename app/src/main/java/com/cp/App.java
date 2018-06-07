package com.cp;

import android.app.Application;

import com.cp.cp.Data_msgserver;
import com.cp.im.IMTool;

import utils.wzutils.AppTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.ui.WzSimpleRecycleView;

/**
 * abc on 2018/2/24.
 */

public class App extends Application {
    public static boolean isDebug=false;

    @Override
    public void onCreate() {
        super.onCreate();
        AppTool.init(this,isDebug);
        WzSimpleRecycleView.defaultEmptyResId=R.layout.nodata;
        Data_msgserver.load(new HttpUiCallBack<Data_msgserver>() {
            @Override
            public void onSuccess(Data_msgserver data) {
                IMTool.getIntance().login();//登录消息服务器， 放这里是为了 被销毁后还能重新启动
            }
        });
    }
}
