package com.cp.wode.huishui;

import android.view.View;

import com.cp.R;

import utils.tjyutils.parent.ParentFragment;

/**
 * Created by kk on 2017/3/23.
 */

public class WoDeHuiShuiFragment extends ParentFragment {


    View btn_go_huishui_bj28;
    @Override
    public int initContentViewId() {
        return R.layout.wode_huishui;
    }
    @Override
    public void initData() {
        bindFragmentBtn(btn_go_huishui_bj28,new WoDeHuiShuiListFragment());
    }

    @Override
    public void initListener() {


    }



}
