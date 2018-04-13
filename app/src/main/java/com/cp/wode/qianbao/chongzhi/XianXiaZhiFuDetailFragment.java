package com.cp.wode.qianbao.chongzhi;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cp.R;

import java.io.Serializable;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.TestData;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.lunbo.LunBoTool;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class XianXiaZhiFuDetailFragment extends ParentFragment {
    Data_recharge_query.OfflineBean offlineBean;

    TextView lb_chongzhi_xianxia_bank,et_chongzhi_xianxia_bank;
    TextView lb_chongzhi_xianxia_name,et_chongzhi_xianxia_name;
    TextView lb_chongzhi_xianxia_account,et_chongzhi_xianxia_account;
    TextView lb_chongzhi_xianxia_amount,et_chongzhi_xianxia_amount;

    TextView tv_chongzhi_xianxia_server_bank,tv_chongzhi_xianxia_server_account,tv_chongzhi_xianxia_server_account_fuzhi ,tv_chongzhi_xianxia_server_user,tv_chongzhi_xianxia_server_user_fuzhi,tv_chongzhi_xianxia_server_fanwei;

    View vg_chongzhi_xianxia_buzou,btn_chongzhi_xianxia;

    TextView tv_chongzhi_xianxia_xinxi;

    TextView tv_chongzhi_xianxia_buzou;
    View vg_chongzhi_xianxia_top;
    @Override
    public int initContentViewId() {
        return R.layout.zhifu_xianxia;
    }
    @Override
    public void initData() {
        titleTool.setTitle("线下支付");

        offlineBean= (Data_recharge_query.OfflineBean) getArgument("offlineBean",new Data_recharge_query.OfflineBean());


        initFuZhi();

        if(offlineBean.isBank()){
            initBank();
        }else if(offlineBean.isWeixin()){
            initWeiXin();
        }else if(offlineBean.isAlipay()){
            initAlipay();
        }

        btn_chongzhi_xianxia.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                sendToServer();
            }
        });
        UiTool.setSoftInputModeSpan(getActivity());
    }
    public void setParentGone(View view){
        try {
            View viewParent= (View) view.getParent();
            viewParent.setVisibility(View.GONE);
        }catch (Exception e){
            LogTool.ex(e);
        }
    }
    public void initAlipay(){
        tv_chongzhi_xianxia_server_bank.setVisibility(View.GONE);
        setTextView(tv_chongzhi_xianxia_server_user,"收款户名："+offlineBean.getRecharge_name());
        setTextView(tv_chongzhi_xianxia_server_account,"收款账号："+offlineBean.recharge_account);
        setTextView(tv_chongzhi_xianxia_server_fanwei,"充值范围："+"最低"+offlineBean.recharge_min+"元,最高"+offlineBean.recharge_max+"元");


        setParentGone(et_chongzhi_xianxia_bank);
        setTextView(lb_chongzhi_xianxia_name,"户名");
        et_chongzhi_xianxia_name.setHint("请填写您此次转账使用的户名");
        setTextView(lb_chongzhi_xianxia_account,"支付账号");
        et_chongzhi_xianxia_account.setHint("请填写您此次转账使用的支付账号");
        setTextView(lb_chongzhi_xianxia_amount,"存款金额");
        et_chongzhi_xianxia_amount.setHint("请填写您转账的金额");
        LunBoTool.initAds(parent,R.id.adsContainer,R.id.vg_viewpager_btn,R.layout.include_lunbo_dot_huise_touming,R.id.cb_dot,-1, LunBoTool.LunBoData.initData(R.drawable.alipay_1,R.drawable.alipay_2,R.drawable.alipay_3));

    }
    public void initWeiXin(){
        setTextView(tv_chongzhi_xianxia_buzou,"请用微信扫描以下二维码付款充值");
        UiTool.setMargin(vg_chongzhi_xianxia_top,0,0,0,0);
        tv_chongzhi_xianxia_xinxi.setVisibility(View.GONE);
        tv_chongzhi_xianxia_server_bank.setVisibility(View.GONE);
        setParentGone(tv_chongzhi_xianxia_server_user);
        setParentGone(tv_chongzhi_xianxia_server_account);
        setTextView(tv_chongzhi_xianxia_server_fanwei,"充值范围："+"最低"+offlineBean.recharge_min+"元,最高"+offlineBean.recharge_max+"元");

        setParentGone(et_chongzhi_xianxia_bank);
        setTextView(lb_chongzhi_xianxia_name,"户名");
        et_chongzhi_xianxia_name.setHint("请填写您此次转账使用的户名");
        setTextView(lb_chongzhi_xianxia_account,"支付账号");
        et_chongzhi_xianxia_account.setHint("请填写您此次转账使用的支付账号");
        setTextView(lb_chongzhi_xianxia_amount,"存款金额");
        et_chongzhi_xianxia_amount.setHint("请填写您转账的金额");
        LunBoTool.initAds(parent,R.id.adsContainer,R.id.vg_viewpager_btn,R.layout.include_lunbo_dot_huise_touming,R.id.cb_dot,-1, LunBoTool.LunBoData.initData(offlineBean.recharge_account));
    }
    public void initBank(){
        setTextView(tv_chongzhi_xianxia_server_bank,"银行："+offlineBean.recharge_bankname);
        setTextView(tv_chongzhi_xianxia_server_user,"收款人："+offlineBean.getRecharge_name());
        setTextView(tv_chongzhi_xianxia_server_account,"账号："+offlineBean.recharge_account);
        setTextView(tv_chongzhi_xianxia_server_fanwei,"充值范围："+"最低"+offlineBean.recharge_min+"元,最高"+offlineBean.recharge_max+"元");

        setTextView(lb_chongzhi_xianxia_bank,"银行名称");
        et_chongzhi_xianxia_bank.setHint("请填写您汇款时使用银行卡所属银行");

        setTextView(lb_chongzhi_xianxia_name,"存款人姓名");
        et_chongzhi_xianxia_name.setHint("请填写您汇款时使用的银行卡的户名");


        setTextView(lb_chongzhi_xianxia_account,"银行账号");
        et_chongzhi_xianxia_account.setHint("请填写您汇款时使用的银行卡账号");

        setTextView(lb_chongzhi_xianxia_amount,"存款金额");
        et_chongzhi_xianxia_amount.setHint("请填写您转账的金额");

        vg_chongzhi_xianxia_buzou.setVisibility(View.GONE);
    }






    String bankName;
    String name;
    String account;
    String amount;
    String type;

    public void sendToServer(){
        type=offlineBean.recharge_type;

        {//银行名称
            if(offlineBean.isWeixin()){
                bankName="微信";
            }else if(offlineBean.isAlipay()){
                bankName="支付宝";
            }else if(offlineBean.isBank()){
                bankName=et_chongzhi_xianxia_bank.getText().toString().trim();
                if(StringTool.isEmpty(bankName)){
                    CommonTool.showToast(et_chongzhi_xianxia_bank.getHint());
                    return;
                }
            }
        }




        name=et_chongzhi_xianxia_name.getText().toString().trim();
        if(StringTool.isEmpty(name)){
            CommonTool.showToast(et_chongzhi_xianxia_name.getHint());
            return;
        }


        account=et_chongzhi_xianxia_account.getText().toString().trim();
        if(StringTool.isEmpty(account)){
            CommonTool.showToast(et_chongzhi_xianxia_account.getHint());
            return;
        }

        amount=et_chongzhi_xianxia_amount.getText().toString().trim();
        if(StringTool.isEmpty(amount)){
            CommonTool.showToast(et_chongzhi_xianxia_amount.getHint());
            return;
        }

        double amountDouble=Double.valueOf(amount);
        if(amountDouble<offlineBean.recharge_min||amountDouble>offlineBean.recharge_max+0.99){
            CommonTool.showToast(tv_chongzhi_xianxia_server_fanwei.getText());
            return;
        }


        showWaitingDialog("");
        Data_recharge_offline.load(amount, name, bankName, type, account, new HttpUiCallBack<Data_recharge_offline>() {
            @Override
            public void onSuccess(Data_recharge_offline data) {
                hideWaitingDialog();
                CommonTool.showToast(data.msg);
                if(data.isDataOkAndToast()){
                    getActivityWz().finish();
                }
            }
        });
    }


    public void initFuZhi(){
        UiTool.bindFuZhi(tv_chongzhi_xianxia_server_account_fuzhi,offlineBean.recharge_account);
        UiTool.bindFuZhi(tv_chongzhi_xianxia_server_user_fuzhi,offlineBean.recharge_name);
    }




    @Override
    public void initListener() {


    }

    public static ParentFragment byData(Data_recharge_query.OfflineBean offlineBean){
        XianXiaZhiFuDetailFragment xianXiaZhiFuDetailFragment=new XianXiaZhiFuDetailFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("offlineBean",offlineBean);
        xianXiaZhiFuDetailFragment.setArguments(bundle);
        return xianXiaZhiFuDetailFragment;
    }


}
