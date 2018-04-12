package com.cp.xiaoxi;

import android.view.View;
import android.widget.CompoundButton;

import com.cp.R;
import com.cp.cp.Data_login_validate;

import java.util.List;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.TimeTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.CommonButtonTool;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.PageControl;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class XiaoXiFragment extends ParentFragment implements WzRefreshLayout.LoadListDataInterface {

    WzRefreshLayout wzRefresh;
    WzSimpleRecycleView recycleView;
    CompoundButton rb_xiaoxi_xitong,rb_xiaoxi_wode,rb_xiaoxi_daili;//        0－公告，1－代理公告，2－私有消息

    CommonButtonTool commonButtonTool;
    View vg_xiaoxi_daili;
    @Override
    public int initContentViewId() {
        return R.layout.xiaoxi;
    }
    @Override
    public void initData() {
        titleTool.setTitle("消息");
        rb_xiaoxi_xitong.setTag(0);
        rb_xiaoxi_daili.setTag(1);
        rb_xiaoxi_wode.setTag(2);
        commonButtonTool=new CommonButtonTool();
        commonButtonTool.addCommonBtns(rb_xiaoxi_xitong,rb_xiaoxi_wode,rb_xiaoxi_daili);
        commonButtonTool.setChecked(rb_xiaoxi_xitong);
        wzRefresh.bindLoadData(this,pageControl);

        commonButtonTool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    wzRefresh.autoRefresh();
                }
            }
        });

        if(Data_login_validate.getData_login_validate().getUserInfo().base_type>0){
            vg_xiaoxi_daili.setVisibility(View.VISIBLE);
        }else {
            vg_xiaoxi_daili.setVisibility(View.GONE);
        }
    }
    PageControl<Data_message_detail.MessageDetailBean> pageControl=new PageControl<>();
    public void initListView(){
        final List<Data_message_detail.MessageDetailBean> list=pageControl.getAllDatas();
        recycleView.setDividerNormal(10);
        recycleView.setData(list, R.layout.xiaoxi_xitong_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                final Data_message_detail.MessageDetailBean messageListBean=list.get(position);
                setTextView(itemView,R.id.tv_xiaoxi_title,messageListBean.message_title);
                setTextView(itemView,R.id.tv_xiaoxi_time, messageListBean.message_time);
//                final View imgv_xiaoxi_unread=itemView.findViewById(R.id.imgv_xiaoxi_unread);
//                imgv_xiaoxi_unread.setVisibility(messageListBean.read_state==0?View.VISIBLE:View.INVISIBLE);
                itemView.setOnClickListener(new WzViewOnclickListener() {
                    @Override
                    public void onClickWz(View v) {
                        XiaoXiDetailFragment.byData(messageListBean.id).go();
                        //imgv_xiaoxi_unread.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
    }

    @Override
    public void loadData(int page) {


        Data_message_query.load(page, pageControl.getPageSize(),commonButtonTool.getChecked().getTag(), new HttpUiCallBack<Data_message_query>() {
            @Override
            public void onSuccess(Data_message_query data) {
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
    }

    @Override
    public void initListener() {


    }



}
