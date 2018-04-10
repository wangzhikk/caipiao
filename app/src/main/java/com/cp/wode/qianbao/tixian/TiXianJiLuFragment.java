package com.cp.wode.qianbao.tixian;

import android.view.View;
import android.widget.TextView;

import com.cp.R;
import com.cp.wode.qianbao.JiaoYiXiangQingFragment;

import java.util.ArrayList;
import java.util.List;

import utils.tjyutils.common.Common;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.TestData;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.PageControl;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class TiXianJiLuFragment extends ParentFragment implements WzRefreshLayout.LoadListDataInterface{

    WzRefreshLayout wzRefreshLayout;
    WzSimpleRecycleView recycleView;
    @Override
    public int initContentViewId() {
        return R.layout.normal_list;
    }
    @Override
    public void initData() {

        titleTool.setTitle("提现记录");
        parent.setBackgroundResource(R.color.white);
        wzRefreshLayout.bindLoadData(this,pageControl);
    }
    @Override
    public void loadData(final int page) {

        Data_extract_record.load(page, pageControl.getPageSize(), new HttpUiCallBack<Data_extract_record>() {
            @Override
            public void onSuccess(Data_extract_record data) {
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
    PageControl <Data_extract_record.PagingListBean.TiXianListBean> pageControl=new PageControl<>();
    public void initListView(final List<Data_extract_record.PagingListBean.TiXianListBean> allDatas){
        recycleView.setData(allDatas, R.layout.zhangbian_jilu_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                Data_extract_record.PagingListBean.TiXianListBean tiXianListBean=allDatas.get(position);

                TextView tv_jilu_name=itemView.findViewById(R.id.tv_jilu_name);
                if(tiXianListBean.extract_state==1){
                    UiTool.setTextColor(tv_jilu_name,R.color.tv_h1);
                }else if(tiXianListBean.extract_state==0){
                    UiTool.setTextColor(tv_jilu_name,R.color.tv_hongse);
                }else if(tiXianListBean.extract_state==-1){
                    UiTool.setTextColor(tv_jilu_name,R.color.tv_hongse);
                }

                setTextView(tv_jilu_name,tiXianListBean.getStateStr());
                setTextView(itemView,R.id.tv_jilu_time,""+tiXianListBean.extract_apply_time);
                setTextView(itemView,R.id.tv_jilu_money,""+ Common.getPriceYB(tiXianListBean.extract_amount));

                bindFragmentBtn(itemView,JiaoYiXiangQingFragment.byData(tiXianListBean));
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
