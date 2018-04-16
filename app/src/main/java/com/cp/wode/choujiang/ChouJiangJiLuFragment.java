package com.cp.wode.choujiang;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_login_validate;

import java.util.List;

import utils.tjyutils.common.Common;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.ui.CommonButtonTool;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.PageControl;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class ChouJiangJiLuFragment extends ParentFragment implements WzRefreshLayout.LoadListDataInterface {
    CompoundButton rb_choujiang_wode,rb_choujiang_quanbu;
    WzRefreshLayout wzRefresh;
    WzSimpleRecycleView recycleView;
    CommonButtonTool commonButtonTool;
    @Override
    public int initContentViewId() {
        return R.layout.choujiang_jilu;
    }


    @Override
    public void initData() {
        titleTool.setTitle("抽奖喜报");

        commonButtonTool=new CommonButtonTool();
        commonButtonTool.addCommonBtns(rb_choujiang_wode,rb_choujiang_quanbu);
        commonButtonTool.setChecked(rb_choujiang_wode);

        wzRefresh.bindLoadData(this,pageControl);

        commonButtonTool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    wzRefresh.autoRefresh();
                }
            }
        });
    }
    PageControl<Data_luck_record.PagingListBean.ChouJiangJiLuListBean> pageControl=new PageControl<>();
    @Override
    public void loadData(int page) {
        if(rb_choujiang_wode.isChecked()){
            Data_luck_record.load(page, pageControl.getPageSize(), new HttpUiCallBack<Data_luck_record>() {
                @Override
                public void onSuccess(Data_luck_record data) {
                    try {
                        if(data.isDataOkAndToast()){
                            pageControl.setCurrPageNum(data.pagingList.currPage,data.pagingList.resultList);
                            initListView();
                        }
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                    wzRefresh.stopRefresh(pageControl);
                }
            });
        }else {
            Data_luck_top50.load(new HttpUiCallBack<Data_luck_top50>() {
                @Override
                public void onSuccess(Data_luck_top50 data) {
                    try {
                        if(data.isDataOkAndToast()){
                            pageControl.setCurrPageNum(1,data.top50);
                            pageControl.setHasMoreData(false);
                            initListView();
                        }
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                    wzRefresh.stopRefresh(pageControl);
                }
            });
        }

    }

    public void initListView(){
        recycleView.setDividerNormal(10);
        final List<Data_luck_record.PagingListBean.ChouJiangJiLuListBean> chouJiangJiLuListBeans=pageControl.getAllDatas();
        recycleView.setData(chouJiangJiLuListBeans, R.layout.choujiang_jilu_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                Data_luck_record.PagingListBean.ChouJiangJiLuListBean bean=chouJiangJiLuListBeans.get(position);

                if(StringTool.isEmpty(bean.base_nickname)&&Data_login_validate.getData_login_validate().uuid.equals(bean.base_uuid)){
                    bean.base_nickname=Data_login_validate.getData_login_validate().getUserInfo().base_nickname;
                }
                setTextView(itemView,R.id.tv_choujiang_jilu_name,bean.base_nickname);
                setTextView(itemView,R.id.tv_choujiang_jilu_time,bean.luck_time);
                setTextView(itemView,R.id.tv_choujiang_jilu_amount,"喜中"+ Common.getPriceYB(bean.luck_amount));
            }
        });
    }
    @Override
    public void initListener() {


    }



}
