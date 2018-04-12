package com.cp.xiaoxi;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_login_validate;

import java.util.List;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.TimeTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.CommonButtonTool;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.PageControl;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class XiaoXiListFragment extends ParentFragment implements WzRefreshLayout.LoadListDataInterface {

    WzRefreshLayout wzRefresh;
    WzSimpleRecycleView recycleView;

    int type;

    @Override
    public int initContentViewId() {
        return R.layout.xiaoxi;
    }

    @Override
    public void initData() {
        type = (int) getArgument("type", 0);

        String title = "系统消息";
        if (type == 1) {
            title = "代理消息";
        } else if (type == 2) {
            title = "我的消息";
        }
        titleTool.setTitle(title);
        wzRefresh.bindLoadData(this, pageControl);


    }

    PageControl<Data_message_detail.MessageDetailBean> pageControl = new PageControl<>();

    public void initListView() {
        final List<Data_message_detail.MessageDetailBean> list = pageControl.getAllDatas();
        recycleView.setData(list, R.layout.xiaoxi_xitong_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                final Data_message_detail.MessageDetailBean messageListBean = list.get(position);
                final TextView tv_xiaoxi_chakan = itemView.findViewById(R.id.tv_xiaoxi_chakan);
                final TextView tv_xiaoxi_title = itemView.findViewById(R.id.tv_xiaoxi_title);
                setTextView(tv_xiaoxi_title, messageListBean.message_title);

                setTextView(itemView, R.id.tv_xiaoxi_content, messageListBean.message_content);
                setTextView(itemView, R.id.tv_xiaoxi_time, messageListBean.message_time);


                itemView.setOnClickListener(new WzViewOnclickListener() {
                    @Override
                    public void onClickWz(View v) {
                        XiaoXiDetailFragment.byData(messageListBean).go();

                        UiTool.setTextColor(tv_xiaoxi_title, R.color.tv_h1);
                        UiTool.setTextColor(tv_xiaoxi_chakan, R.color.tv_h3);
                    }
                });
                itemView.findViewById(R.id.v_last_jiange).setVisibility(position == list.size() - 1 ? View.VISIBLE : View.GONE);


                if (messageListBean.read_state == 0) {//未读
                    UiTool.setTextColor(tv_xiaoxi_title, R.color.tv_hongse);
                    UiTool.setTextColor(tv_xiaoxi_chakan, R.color.tv_lanse_name);
                } else {
                    UiTool.setTextColor(tv_xiaoxi_title, R.color.tv_h1);
                    UiTool.setTextColor(tv_xiaoxi_chakan, R.color.tv_h3);
                }
            }

        });
    }

    @Override
    public void loadData(int page) {


        Data_message_query.load(page, pageControl.getPageSize(), type, new HttpUiCallBack<Data_message_query>() {
            @Override
            public void onSuccess(Data_message_query data) {
                try {
                    if (data.isDataOkAndToast()) {
                        pageControl.setCurrPageNum(data.pagingList.currPage, data.pagingList.resultList);
                        initListView();
                    }
                } catch (Exception e) {
                    LogTool.ex(e);
                }
                wzRefresh.stopRefresh(pageControl);
            }
        });
    }

    @Override
    public void initListener() {


    }

    /***
     *
     * @param type  0 系统， 1， 代理， 2， 我的
     * @return
     */
    public static ParentFragment byData(int type) {
        XiaoXiListFragment xiaoXiListFragment = new XiaoXiListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        xiaoXiListFragment.setArguments(bundle);
        return xiaoXiListFragment;
    }


}
