package com.cp.xiaoxi;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.BroadcastReceiverTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.CommonButtonTool;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.PageControl;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class XiaoXiAllFragment extends ParentFragment{
    View vg_xiaoxi_xitong,vg_xiaoxi_daili,vg_xiaoxi_wode,line_xiaoxi_daili;
    TextView tv_xiaoxi_xitong_last,tv_xiaoxi_daili_last,tv_xiaoxi_wode_last;
    TextView tv_xiaoxi_xitong_count,tv_xiaoxi_daili_count,tv_xiaoxi_wode_count;
    WzRefreshLayout wzRefresh;
    @Override
    public int initContentViewId() {
        return R.layout.xiaoxiall;
    }
    @Override
    public void initData() {
        titleTool.setTitle("消息");
        titleTool.hideBack();

        wzRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData();
            }
        });
        wzRefresh.setEnableLoadMore(false);

        BroadcastReceiverTool.bindAction(getActivity(), new BroadcastReceiverTool.BroadCastWork() {
            @Override
            public void run() {
                loadData();
            }
        },action_refresh_xiaoxi_count);
    }

    public static String action_refresh_xiaoxi_count="action_refresh_xiaoxi_count";
    public static void refresh(){
        BroadcastReceiverTool.sendAction(action_refresh_xiaoxi_count);
    }

    public void loadData() {
        Data_message_summary.load(new HttpUiCallBack<Data_message_summary>() {
            @Override
            public void onSuccess(Data_message_summary data) {
                wzRefresh.stopRefresh(null);
                if(data.isDataOkAndToast()){

                    if(data.notice!=null){
                        setTextView(tv_xiaoxi_xitong_last,data.notice.latest);
                        setCount(tv_xiaoxi_xitong_count,data.notice.notRead);
                    }

                    if(data.agent!=null){
                        setTextView(tv_xiaoxi_daili_last,data.agent.latest);
                        setCount(tv_xiaoxi_daili_count,data.agent.notRead);
                    }

                    if(data.message!=null){
                        setTextView(tv_xiaoxi_wode_last,data.message.latest);
                        setCount(tv_xiaoxi_wode_count,data.message.notRead);
                    }

                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void setCount(TextView textView, int count){
        if(count<1)textView.setVisibility(View.INVISIBLE);
        else textView.setVisibility(View.VISIBLE);

        if(count>9){
            textView.setBackgroundResource(R.drawable.kk_tips_two);
        }else {
            textView.setBackgroundResource(R.drawable.kk_tips_one);
        }

        setTextView(textView,count);

    }
    @Override
    public void initListener() {
        bindFragmentBtn(vg_xiaoxi_xitong,XiaoXiListFragment.byData(0));
        bindFragmentBtn(vg_xiaoxi_daili,XiaoXiListFragment.byData(1));
        bindFragmentBtn(vg_xiaoxi_wode,XiaoXiListFragment.byData(2));
    }



}
