package com.cp.wode.fenxiang;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cp.R;
import com.cp.wode.Data_personinfo_query;

import java.util.ArrayList;
import java.util.List;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.TestData;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.ui.TabTitleTool;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.WzViewPager;
import utils.wzutils.ui.pullrefresh.PageControl;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class WoDeFenXiangFragment extends ParentFragment implements WzRefreshLayout.LoadListDataInterface {

    WzRefreshLayout wzRefreshLayout;
    WzSimpleRecycleView recycleView;

    @Override
    public int initContentViewId() {
        return R.layout.fenxiang_user;
    }
    @Override
    public void initData() {


        wzRefreshLayout.bindLoadData(this,pageControl);

    }

    @Override
    public void loadData(int page) {

        Data_share_record.load(page, pageControl.getPageSize(), new HttpUiCallBack<Data_share_record>() {
            @Override
            public void onSuccess(Data_share_record data) {
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
    PageControl<Data_share_record.PagingListBean.ShareListBean> pageControl=new PageControl<>();


    public void initListView(final List<Data_share_record.PagingListBean.ShareListBean> allDatas){
        recycleView.setDividerNormal(10);
        recycleView.setData(allDatas, R.layout.fenxiang_user_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                Data_share_record.PagingListBean.ShareListBean shareListBean=allDatas.get(position);
                TextView tv_share_item_name=itemView.findViewById(R.id.tv_share_item_name);

                setTextView(itemView,R.id.tv_share_item_name,shareListBean.base_username);
                setTextView(itemView,R.id.tv_share_item_time,shareListBean.base_register_time);
                UiTool.setCompoundDrawables(getActivity(),tv_share_item_name,Data_personinfo_query.getGradeResImg(shareListBean.base_grade),0,0,0);
            }
        });
    }
    @Override
    public void initListener() {



    }



}
