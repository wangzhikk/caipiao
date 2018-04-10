package com.cp.wode.qianbao.chongzhi;

import android.view.View;

import com.cp.R;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.TestData;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class XianXiaZhiFuListFragment extends ParentFragment {
    WzRefreshLayout wzRefreshLayout;
    WzSimpleRecycleView recycleView;
    @Override
    public int initContentViewId() {
        return R.layout.normal_list;
    }
    @Override
    public void initData() {

        titleTool.setTitle("选择账号");
        initListView();
    }
    public void initListView(){
        recycleView.setDividerNormal(10);
        recycleView.setData(TestData.getTestStrList(10), R.layout.item_xianxia_zhifu, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                bindFragmentBtn(itemView,new XianXiaZhiFuDetailFragment());
            }
        });
    }


    @Override
    public void initListener() {


    }


}
