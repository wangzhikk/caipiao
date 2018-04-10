package com.cp.wode.qianbao;

import android.os.Bundle;
import android.view.View;

import com.cp.R;
import com.cp.wode.qianbao.chongzhi.ChongZhiFragment;
import com.cp.wode.qianbao.chongzhi.ChongZhiJiLuFragment;
import com.cp.wode.qianbao.chongzhi.Data_recharge_record;
import com.cp.wode.qianbao.tixian.Data_extract_record;
import com.cp.wode.qianbao.tixian.TiXianFragment;
import com.cp.wode.qianbao.tixian.TiXianJiLuFragment;
import com.cp.wode.qianbao.yinhangka.YinHangKaGuanLiFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.tjyutils.common.Common;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.TestData;
import utils.wzutils.ui.WzSimpleRecycleView;

/**
 * Created by kk on 2017/3/23.
 */

public class JiaoYiXiangQingFragment extends ParentFragment {

    WzSimpleRecycleView recycleView;
    @Override
    public int initContentViewId() {
        return R.layout.wode_jiaoyi_xiangqing;
    }
    @Override
    public void initData() {
        titleTool.setTitle("详情");

        recycleView.setData(list, R.layout.wode_jiaoyi_xiangqing_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                JiaoYiData jiaoYiData=list.get(position);
                setTextView(itemView,R.id.tv_jiaoyi_left,""+jiaoYiData.label);
                setTextView(itemView,R.id.tv_jiaoyi_right,""+jiaoYiData.value);
            }
        });
    }

    public ParentFragment setList(List<JiaoYiData> list) {
        this.list=list;
        return this;
    }


    public static class JiaoYiData implements Serializable{
        public String label;
        public String value;
        public JiaoYiData(String label,String value){
            this.label=label;
            this.value=value;
        }
    }

    @Override
    public void initListener() {

    }

    public List<JiaoYiData> list;


    /***
     * 提现记录
     * @param tiXianListBean
     * @return
     */
    public static ParentFragment byData(Data_extract_record.PagingListBean.TiXianListBean tiXianListBean){
        List<JiaoYiXiangQingFragment.JiaoYiData > list=new ArrayList<>();
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("订单号",""+tiXianListBean.extract_serial));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("提现状态",""+tiXianListBean.getStateStr()));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("提现金额",""+Common.getPriceYB(tiXianListBean.extract_amount)));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("到账金额",""+Common.getPriceYB(tiXianListBean.extract_amount_fact)));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("手续费",""+tiXianListBean.extract_fee));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("提现姓名",""+tiXianListBean.extract_name));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("提现银行",""+tiXianListBean.extract_bank_name));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("提现卡号",""+tiXianListBean.extract_bank_account));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("提现时间",""+tiXianListBean.extract_apply_time));
        return new JiaoYiXiangQingFragment().setList(list);
    }
    public static ParentFragment byData(Data_recharge_record.PagingListBean.ChongZhiListBean chongZhiListBean){
        List<JiaoYiXiangQingFragment.JiaoYiData > list=new ArrayList<>();
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("订单号",""+chongZhiListBean.recharge_serial));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("充值金额", Common.getPriceYB(chongZhiListBean.recharge_amount)));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("充值状态",""+chongZhiListBean.getStateStr()));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("充值时间",""+chongZhiListBean.recharge_time));
        return new JiaoYiXiangQingFragment().setList(list);
    }
    public static ParentFragment byData(Data_wallet_record.PagingListBean.QianBaoListBean qianBaoListBean){
        List<JiaoYiXiangQingFragment.JiaoYiData > list=new ArrayList<>();
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("订单号",""+qianBaoListBean.remark));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("类型",""+qianBaoListBean.cwallet_type_name));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("交易金额",""+Common.getPriceYB(qianBaoListBean.cwallet_amount)));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("钱包余额",""+Common.getPriceYB(qianBaoListBean.cwallet_remain)));
        list.add(new JiaoYiXiangQingFragment.JiaoYiData("交易时间",""+qianBaoListBean.cwallet_time));
        return new JiaoYiXiangQingFragment().setList(list);
    }

}
