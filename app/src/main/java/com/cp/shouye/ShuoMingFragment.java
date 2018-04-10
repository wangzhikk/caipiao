package com.cp.shouye;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.TestData;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.lunbo.LunBoTool;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class ShuoMingFragment extends ParentFragment {

    TextView shuoming_tv;

    ShuoMingData shuoMing;
    @Override
    public int initContentViewId() {
        return R.layout.shuoming;
    }
    @Override
    public void initData() {
        shuoMing= (ShuoMingData) getArgument("shuoMing",new ShuoMingData("说明",""));
        titleTool.setTitle(shuoMing.title);
        shuoming_tv.setText(shuoMing.content);
    }
    @Override
    public void initListener() {

    }
    public static class ShuoMingData implements Serializable{
        public String title;
        public String content;
        public ShuoMingData(String title,String content){
            this.title=title;
            this.content=content;
        }
    }
    public static ShuoMingFragment byData(ShuoMingData shuoMing){
        Bundle bundle=new Bundle();
        bundle.putSerializable("shuoMing",shuoMing);
        ShuoMingFragment shuoMingFragment=new ShuoMingFragment();
        shuoMingFragment.setArguments(bundle);
        return shuoMingFragment;
    }

}
