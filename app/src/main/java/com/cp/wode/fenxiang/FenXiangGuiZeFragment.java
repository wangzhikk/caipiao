package com.cp.wode.fenxiang;

import android.view.View;
import android.widget.EditText;

import com.cp.R;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * Created by kk on 2017/3/23.
 */

public class FenXiangGuiZeFragment extends ParentFragment {


    @Override
    public int initContentViewId() {
        return R.layout.fenxiang_guize;
    }
    @Override
    public void initData() {

        titleTool.setTitle("代理说明");
    }

    @Override
    public void initListener() {



    }



}
