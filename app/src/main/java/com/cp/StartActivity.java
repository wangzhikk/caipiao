package com.cp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cp.cp.Data_login_validate;
import com.cp.shouye.ShouYeFragment;
import com.cp.wode.WoDeFragment;
import com.cp.xiaoxi.XiaoXiFragment;

import utils.tjyutils.common.PreLoad;
import utils.tjyutils.parent.ParentActivity;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.AppTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.common.ViewTool;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.CommonButtonTool;

public class StartActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);
        /***
         * 如果程序从后台 切换到前台就不要这个界面了
         */
        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0){
            finish();
            return;
        }

        getWindow().getDecorView().postDelayed(new Runnable() {
            @Override
            public void run() {
                MainActivity.go();
            }
        },2000);
        PreLoad.preLoad();

    }

}
