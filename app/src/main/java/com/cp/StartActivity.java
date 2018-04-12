package com.cp;

import android.content.Intent;
import android.os.Bundle;

import utils.tjyutils.common.PreLoad;
import utils.tjyutils.parent.ParentActivity;

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
