package com.cp.xiaoxi;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cp.R;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.TestData;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.ui.CommonButtonTool;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class XiaoXiDetailFragment extends ParentFragment {

    TextView tv_xiaoxi_xiangqing_title,tv_xiaoxi_xiangqing_neirong;
    String id="";
    @Override
    public int initContentViewId() {
        return R.layout.xiaoxi_xitong_xiangqing;
    }
    @Override
    public void initData() {
        titleTool.setTitle("消息");

        id=""+getArgument("id","");

        loadData();
    }

    public void loadData(){
        showWaitingDialog("");
        Data_message_detail.load(id, new HttpUiCallBack<Data_message_detail>() {
            @Override
            public void onSuccess(Data_message_detail data) {
                hideWaitingDialog();
                if(data.isDataOkAndToast()){
                    setTextView(tv_xiaoxi_xiangqing_title,data.detail.message_title);
                    setTextView(tv_xiaoxi_xiangqing_neirong,data.detail.message_content);
                }
            }
        });
    }

    @Override
    public void initListener() {


    }


    public static ParentFragment byData(String id) {
        XiaoXiDetailFragment xiaoXiDetailFragment=new XiaoXiDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putString("id",id);
        xiaoXiDetailFragment.setArguments(bundle);
        return xiaoXiDetailFragment;
    }
}
