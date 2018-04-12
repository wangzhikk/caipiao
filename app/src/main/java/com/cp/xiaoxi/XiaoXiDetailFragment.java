package com.cp.xiaoxi;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cp.R;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.TestData;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.ui.CommonButtonTool;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class XiaoXiDetailFragment extends ParentFragment {

    TextView tv_xiaoxi_xiangqing_title,tv_xiaoxi_xiangqing_neirong,tv_xiaoxi_detail_time;
    Data_message_detail.MessageDetailBean bean;
    @Override
    public int initContentViewId() {
        return R.layout.xiaoxi_xitong_xiangqing;
    }
    @Override
    public void initData() {
        titleTool.setTitle("消息");
        bean= (Data_message_detail.MessageDetailBean) getArgument("bean",new Data_message_detail.MessageDetailBean());
        initView(bean,true);
    }

    public void loadData(){
        Data_message_detail.load(bean.id, new HttpUiCallBack<Data_message_detail>() {
            @Override
            public void onSuccess(Data_message_detail data) {
                if(data.isDataOkAndToast()){
                    initView(data.detail,false);
                    XiaoXiAllFragment.refresh();
                }
            }
        });
    }
    public  void initView(Data_message_detail.MessageDetailBean data,boolean checkUnRead){
        try {
            setTextView(tv_xiaoxi_xiangqing_title,data.message_title);
            setTextView(tv_xiaoxi_xiangqing_neirong,data.message_content);
            setTextView(tv_xiaoxi_detail_time,data.message_time);
            if(checkUnRead&&data.read_state==0){//未读消息
                loadData();
            }
        }catch (Exception e){
            LogTool.ex(e);
        }

    }

    @Override
    public void initListener() {


    }


    public static ParentFragment byData(Data_message_detail.MessageDetailBean messageDetailBean) {
        XiaoXiDetailFragment xiaoXiDetailFragment=new XiaoXiDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("bean",messageDetailBean);
        xiaoXiDetailFragment.setArguments(bundle);
        return xiaoXiDetailFragment;
    }
}
