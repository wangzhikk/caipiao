package com.cp.wode.fenxiang;

import android.view.View;
import android.widget.TextView;

import com.cp.R;

import java.util.List;

import utils.tjyutils.common.Common;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.TestData;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.PageControl;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class WoDeShouYiFragment extends ParentFragment implements WzRefreshLayout.LoadListDataInterface {

    WzRefreshLayout wzRefreshLayout;
    WzSimpleRecycleView recycleView;

    @Override
    public int initContentViewId() {
        return R.layout.wode_shouyi;
    }
    @Override
    public void initData() {
        titleTool.setTitle("我的收益");
        wzRefreshLayout.bindLoadData(this,pageControl);
    }
    @Override
    public void loadData(final int page) {

        Data_share_bonus.load(page, pageControl.getPageSize(), new HttpUiCallBack<Data_share_bonus>() {
            @Override
            public void onSuccess(Data_share_bonus data) {
                try {
                    if(data.isDataOkAndToast()){
                        pageControl.setCurrPageNum(data.pagingList.currPage,data.pagingList.resultList);
                        initListView(pageControl.getAllDatas());
                    }
                }catch (Exception e){
                    LogTool.ex(e);
                }
                wzRefreshLayout.stopRefresh(pageControl);
            }
        });
    }
    PageControl<Data_share_bonus.PagingListBean.ShareBonusListBean> pageControl=new PageControl<>();
    public void initListView(final List<Data_share_bonus.PagingListBean.ShareBonusListBean> allDatas){
        recycleView.setDividerNormal(10);
        recycleView.setData(allDatas, R.layout.wode_shouyi_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);

                    Data_share_bonus.PagingListBean.ShareBonusListBean shareBonusListBean=allDatas.get(position);
                    setTextView(itemView,R.id.tv_shouyi_item_name,shareBonusListBean.remark);
                    setTextView(itemView,R.id.tv_shouyi_item_time,shareBonusListBean.spare);
                    setTextView(itemView,R.id.tv_shouyi_item_amount,"+"+ Common.getPriceYB(shareBonusListBean.cwallet_amount));

            }


        });
    }

    @Override
    public void initListener() {


    }



}
