package com.cp.wode.qianbao.chongzhi.xianshang;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cp.R;
import com.cp.cp.Data_wallet_remain;
import com.cp.wode.qianbao.chongzhi.Data_recharge_query;

import utils.tjyutils.common.Common;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * Created by kk on 2017/3/23.
 */

public class XianShangZhiFuFragment extends ParentFragment {
    Data_recharge_query.OnLineBean onLineBean;
    View btn_zhifu_xianshang;
    EditText et_chongzhi_xianshang_amount;
    @Override
    public int initContentViewId() {
        return R.layout.zhifu_xianshang;
    }
    @Override
    public void initData() {

        onLineBean= (Data_recharge_query.OnLineBean) getArgument("onLineBean",new Data_recharge_query.OnLineBean());

        titleTool.setTitle("线上支付");

        setTextView(parent,R.id.tv_tixian_tishi,onLineBean.name);
        setTextView(parent,R.id.tv_tixian_tishi,onLineBean.desc);
        loadData();
    }
    public void loadData(){
        Data_wallet_remain.load(new HttpUiCallBack<Data_wallet_remain>() {
            @Override
            public void onSuccess(Data_wallet_remain data) {
                if(data.isDataOkAndToast()){
                    setTextView(parent,R.id.tv_chongzhi_yue, Common.getPriceYB(data.remain));
                }
            }
        });

    }

    @Override
    public void initListener() {
        btn_zhifu_xianshang.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                String amount=et_chongzhi_xianshang_amount.getText().toString();
                if(StringTool.isEmpty(amount)){
                    CommonTool.showToast(et_chongzhi_xianshang_amount.getHint());
                    return;
                }


                showWaitingDialog("");
                Data_recharge_online.load(amount, "" + onLineBean.type, new HttpUiCallBack<Data_recharge_online>() {
                    @Override
                    public void onSuccess(Data_recharge_online data) {
                        hideWaitingDialog();
                        if(data.isDataOkAndToast()){

                            if(onLineBean.type==11){
                                XianShangZhiFuSaoMaFragment.byData(data).go();
                            }else {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                Uri content_url = Uri.parse(data.url);
                                intent.setData(content_url);
                                startActivity(intent);
                            }

                        }
                    }
                });


            }
        });

    }


    public static ParentFragment byData(Data_recharge_query.OnLineBean onLineBean) {
        XianShangZhiFuFragment xianShangZhiFuFragment=new XianShangZhiFuFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("onLineBean",onLineBean);
        xianShangZhiFuFragment.setArguments(bundle);
        return xianShangZhiFuFragment;
    }
}
