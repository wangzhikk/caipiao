package com.cp.wode.qianbao.tixian;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cp.R;
import com.cp.wode.qianbao.yinhangka.Data_bank_query;

import utils.tjyutils.common.Common;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.TestData;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.dialog.DialogTool;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;
import utils.wzutils.ui.textview.HtmlTool;

/**
 * Created by kk on 2017/3/23.
 */

public class TiXianFragment extends ParentFragment {

    TextView tv_tixian_tishi,tv_tixian_kahao,tv_tixian_yinhang,tv_tixian_tishi_bottom,tv_tixian_yue;
    EditText et_tixian_keyong_yue,et_tixian_mima;
    View btn_tixian;
    @Override
    public int initContentViewId() {
        return R.layout.tixian;
    }
    @Override
    public void initData() {

        titleTool.setTitle("提现");



        loadData();
    }

    public void loadData(){
        Data_bank_query.load(new HttpUiCallBack<Data_bank_query>() {
            @Override
            public void onSuccess(Data_bank_query data) {
                if(data.isDataOkAndToast()){
                    setTextView(tv_tixian_kahao,data.base_name+"("+data.base_bank_account+")");
                    setTextView(tv_tixian_yinhang,data.base_bank_name);

                }
            }
        });

        Data_extract_query.load(new HttpUiCallBack<Data_extract_query>() {
            @Override
            public void onSuccess(Data_extract_query data) {
                if(data.isDataOkAndToast()){
                    String tishi=getResources().getString(R.string.tixian_tixing,data.freeTimes,data.timesRemain,data.feeRate*100f+"%");
                    HtmlTool.setHtmlText(tv_tixian_tishi,tishi);

                    setTextView(tv_tixian_yue,data.remain);

                    String tixianTiShi="最低提现"+ Common.getPriceYB(data.min)+",最高提现"+Common.getPriceYB(data.max);
                    et_tixian_keyong_yue.setHint(tixianTiShi);

                    String tishiStr="提现须知:\n1."+tixianTiShi+" \n2.提现10分钟内到账，如高峰期30分钟内到账 \n3.提现处理成功请查看你的银行帐号,未到账请联系平台客服";
                    tishiStr+="\n4.投注金额必须达到充值金额的"+((int)(data.needBetting*100))+"%才能提现\n";
                    setTextView(tv_tixian_tishi_bottom,tishiStr);
                }else if(data.code==185){//不在时间段内
                    showTiShi(parent,data.time);
                }
            }
        });
    }

    public static void showTiShi(View parent,String time){
        try {
            parent.findViewById(R.id.vg_tixian_tishi).setVisibility(View.VISIBLE);
            UiTool.setTextView(parent,R.id.tv_tishi_content,"您好，该时间段暂未提供相关服务，请在"+time+"尝试操作，感谢您的支持！");
        }catch (Exception e){
            LogTool.ex(e);
        }

    }


    @Override
    public void initListener() {

        btn_tixian.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                String amount=et_tixian_keyong_yue.getText().toString().trim();
                String pwd=et_tixian_mima.getText().toString().trim();

                if(StringTool.isEmpty(amount)){
                    CommonTool.showToast("请输入提现金额");
                    return;
                }

                if(StringTool.isEmpty(pwd)){
                    CommonTool.showToast(et_tixian_mima.getHint().toString());
                    return;
                }
                showWaitingDialog("");
                Data_extract_apply.load(amount, pwd, new HttpUiCallBack<Data_extract_apply>() {
                    @Override
                    public void onSuccess(Data_extract_apply data) {
                        hideWaitingDialog();
                        CommonTool.showToast(data.getMsg());
                        if(data.isDataOk()){
                            getActivityWz().finish();
                        }
                    }
                });

            }
        });

    }


}
