package com.cp.wode.huishui;

import android.view.View;
import android.widget.CompoundButton;

import com.cp.R;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.TestData;
import utils.wzutils.ui.CommonButtonTool;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class WoDeHuiShuiListFragment extends ParentFragment {

    WzRefreshLayout wzRefresh_huishui;
    WzSimpleRecycleView recycleView_huishui;

    CompoundButton rb_huishui_chu,rb_huishui_zhong,rb_huishui_gao;
    CommonButtonTool commonButtonTool;
    @Override
    public int initContentViewId() {
        return R.layout.wode_huishui_list;
    }
    @Override
    public void initData() {
        titleTool.setTitle("我的回水");
        commonButtonTool=new CommonButtonTool();
        commonButtonTool.addCommonBtns(rb_huishui_chu,rb_huishui_zhong,rb_huishui_gao);
        commonButtonTool.setChecked(rb_huishui_chu);
        recycleView_huishui.setData(TestData.getTestStrList(20), R.layout.zhangbian_jilu_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
            }
        });
    }

    @Override
    public void initListener() {


    }



}
