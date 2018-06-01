package com.cp.wode.qianbao;

import android.view.View;
import android.widget.TextView;

import com.cp.R;
import com.cp.shouye.Data_room_queryGame;

import java.util.ArrayList;
import java.util.List;

import utils.tjyutils.common.Common;
import utils.tjyutils.common.TimeTypeChoose;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.TestData;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.PageControl;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class ZhangBianJiLuFragment extends ParentFragment implements WzRefreshLayout.LoadListDataInterface {

    WzRefreshLayout wzRefreshLayout;
    WzSimpleRecycleView recycleView;
    @Override
    public int initContentViewId() {
        return R.layout.zhangbian_jilu;
    }
    @Override
    public void initData() {

        titleTool.setTitle("帐变记录");
        parent.setBackgroundResource(R.color.white);
        initTitleChoose();
        wzRefreshLayout.bindLoadData(this,pageControl);

    }
    String type="";
    String start="";
    String end="";
    public void initTitleChoose() {
        Data_wallet_types.load(new HttpUiCallBack<Data_wallet_types>() {
            @Override
            public void onSuccess(Data_wallet_types data) {
                if(data.isDataOkAndToast()){

                    TimeTypeChoose timeTypeChoose=new TimeTypeChoose();
                    List<TimeTypeChoose.TypeData> list=new ArrayList<>();

                    list.add(new TimeTypeChoose.TypeData("全部",""));
                    for(Data_wallet_types.TypesBean typesBean:data.types){
                        list.add(new TimeTypeChoose.TypeData(typesBean.name,typesBean.value));
                    }

                    timeTypeChoose.init(parent, list, new TimeTypeChoose.OnChooseTimeTypeListener() {
                        @Override
                        public void onChoose(TimeTypeChoose.TypeData typeData, String timeStart, String timeEnd) {
                            type= ""+typeData.value;
                            start=timeStart;
                            end=timeEnd;
                            wzRefreshLayout.autoRefresh();
                        }
                    });
                    start=timeTypeChoose.tv_touzhu_jilu_choose_time_start.getText().toString();
                    end=timeTypeChoose.tv_touzhu_jilu_choose_time_end.getText().toString();
                }
            }
        });


    }

    @Override
    public void loadData(int page) {
        Data_wallet_record.load(page, pageControl.getPageSize(),type,start,end, new HttpUiCallBack<Data_wallet_record>() {
            @Override
            public void onSuccess(Data_wallet_record data) {
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
    PageControl<Data_wallet_record.PagingListBean.QianBaoListBean> pageControl=new PageControl<>();
    public void initListView(final List<Data_wallet_record.PagingListBean.QianBaoListBean> allDatas){
        recycleView.setData(allDatas, R.layout.zhangbian_jilu_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);

                Data_wallet_record.PagingListBean.QianBaoListBean qianBaoListBean=allDatas.get(position);
                TextView tv_jilu_name=itemView.findViewById(R.id.tv_jilu_name);


                setTextView(tv_jilu_name,qianBaoListBean.cwallet_type_name);
                setTextView(itemView,R.id.tv_jilu_time,""+qianBaoListBean.cwallet_time);
                setTextView(itemView,R.id.tv_jilu_yue,"余额:"+qianBaoListBean.cwallet_remain);

                if(StringTool.notEmpty(qianBaoListBean.spare)){
                    setTextView(itemView,R.id.tv_jilu_game_name," - "+qianBaoListBean.spare);
                }else {
                    setTextView(itemView,R.id.tv_jilu_game_name,"");
                }



                {//订单号
                    String orderNum="";
                    /**
                     * RECHARGE("充值", 0), EXTRACT("提现", 1), BETTING("投注", 2), BONUS("奖金", 3), REFUND("退款", 4), SHAREBONUS("分享收益", 5), LUCK("抽奖", 6), TEAMBONUS("分红", 7),REPEALOEDER("撤销",8)
                     只有充值 提现 投注、奖金、、、退款、撤销
                     */
                    if(qianBaoListBean.cwallet_type==0||qianBaoListBean.cwallet_type==1||qianBaoListBean.cwallet_type==2||qianBaoListBean.cwallet_type==3||qianBaoListBean.cwallet_type==4||qianBaoListBean.cwallet_type==8){
                        orderNum="订单号:"+qianBaoListBean.remark;
                    }

                    TextView tv_jilu_order_num=itemView.findViewById(R.id.tv_jilu_order_num);

                    if(StringTool.isEmpty(orderNum)){
                        tv_jilu_order_num.setVisibility(View.GONE);
                    }else {
                        tv_jilu_order_num.setVisibility(View.VISIBLE);
                        setTextView(itemView,R.id.tv_jilu_order_num,orderNum);
                    }


                }




                TextView tv_jilu_money=itemView.findViewById(R.id.tv_jilu_money);
                if(qianBaoListBean.cwallet_amount>0){
                    UiTool.setTextColor(tv_jilu_money,R.color.tv_hongse);
                    setTextView(itemView,R.id.tv_jilu_money,"+"+ Common.getPriceYB(qianBaoListBean.cwallet_amount));
                }else {
                    UiTool.setTextColor(tv_jilu_money,R.color.tv_lvse_sheng);
                    setTextView(itemView,R.id.tv_jilu_money, Common.getPriceYB(qianBaoListBean.cwallet_amount));
                }

                //bindFragmentBtn(itemView,JiaoYiXiangQingFragment.byData(qianBaoListBean));
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
