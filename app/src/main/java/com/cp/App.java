package com.cp;

import android.app.Application;

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
    }
}
