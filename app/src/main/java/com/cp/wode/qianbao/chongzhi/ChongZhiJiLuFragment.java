package com.cp.wode.qianbao.chongzhi;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.cp.R;
import com.cp.wode.qianbao.JiaoYiXiangQingFragment;

import java.util.List;

import utils.tjyutils.common.Common;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.TestData;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.PageControl;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class ChongZhiJiLuFragment extends ParentFragment implements WzRefreshLayout.LoadListDataInterface{

    WzRefreshLayout wzRefreshLayout;
    WzSimpleRecycleView recycleView;
    @Override
    public int initContentViewId() {
        return R.layout.normal_list;
    }
    @Override
    public void initData() {
        titleTool.setTitle("充值记录");
        parent.setBackgroundResource(R.color.white);
        wzRefreshLayout.bindLoadData(this,pageControl);
    }
    PageControl <Data_recharge_record.PagingListBean.ChongZhiListBean> pageControl=new PageControl<>();
    @Override
    public void loadData(final int page) {
        Data_recharge_record.load(page, pageControl.getPageSize(), new HttpUiCallBack<Data_recharge_record>() {
            @Override
            public void onSuccess(Data_recharge_record data) {
                if(data.isDataOkAndToast()){
                    pageControl.setCurrPageNum(data.pagingList.currPage,data.pagingList.resultList);
                    initListView(pageControl.getAllDatas());
                }
                wzRefreshLayout.stopRefresh(pageControl);
            }
        });
    }
    public void initListView(final List<Data_recharge_record.PagingListBean.ChongZhiListBean> allDatas){
        recycleView.setData(allDatas, R.layout.chongzhi_jilu_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                Data_recharge_record.PagingListBean.ChongZhiListBean chongZhiListBean=allDatas.get(position);

                TextView tv_jilu_name=itemView.findViewById(R.id.tv_jilu_name);

                int color=Color.parseColor(chongZhiListBean.getStateStrColor());
                tv_jilu_name.setTextColor(color);

                setTextView(tv_jilu_name,chongZhiListBean.getStateStr());
                setTextView(itemView,R.id.tv_jilu_time,""+chongZhiListBean.recharge_time);
                setTextView(itemView,R.id.tv_jilu_money, Common.getPriceYB(chongZhiListBean.recharge_amount));

                setTextView(itemView,R.id.tv_jilu_type,chongZhiListBean.recharge_channel);

                bindFragmentBtn(itemView,JiaoYiXiangQingFragment.byData(chongZhiListBean));

            }
        });
    }
    @Override
    public void setOnResume() {
        super.setOnResume();
    }

    @Override
    public void initListener() {


    }



}
