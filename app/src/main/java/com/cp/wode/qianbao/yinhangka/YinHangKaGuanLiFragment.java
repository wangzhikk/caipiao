package com.cp.wode.qianbao.yinhangka;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.cp.wode.Data_personinfo_query;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.BroadcastReceiverTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;


/**
 * Created by kk on 2017/3/23.
 */

public class YinHangKaGuanLiFragment extends ParentFragment {
    View vg_no_yinghangka_parent;
    View vg_yinghangka_parent;
    TextView tv_yinghangka,tv_yinghangka_last;
    ImageView imgv_yinghangka_logo;
    @Override
    public int initContentViewId() {
        return R.layout.wode_yinhangka_guanli;
    }
    @Override
    public void initData() {
        titleTool.setTitle("银行卡管理");
        initRightTitle("绑定银行卡");
        loadData();
        BroadcastReceiverTool.bindAction(getActivity(), new BroadcastReceiverTool.BroadCastWork() {
            @Override
            public void run() {
                loadData();
            }
        },YinHangKaBindFragment.action_yinghangka_change);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    public void loadData(){
        Data_bank_query.load(new HttpUiCallBack<Data_bank_query>() {
            @Override
            public void onSuccess(Data_bank_query data) {
                if(data.code==182){//没有绑定银行卡
                    vg_no_yinghangka_parent.setVisibility(View.VISIBLE);
                    vg_yinghangka_parent.setVisibility(View.GONE);
                }else  if(data.isDataOkAndToast()){
                    initView(data);
                }
            }
        });
    }
    public void initRightTitle(String text){
        titleTool.setTitleRightTv(text, new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new YinHangKaBindFragment().go();
            }
        });
    }
    public void initView(Data_bank_query data){
        if(data==null|!data.isDataOk())return;

        if(StringTool.notEmpty(data.base_bank_account)){
            initRightTitle("重新绑定");
            vg_no_yinghangka_parent.setVisibility(View.GONE);
            vg_yinghangka_parent.setVisibility(View.VISIBLE);
            setTextView(tv_yinghangka,data.base_bank_name);

            if(data.base_bank_account.length()>4){
                setTextView(tv_yinghangka_last,data.base_bank_account.substring(data.base_bank_account.length()-4));
            }else {
                setTextView(tv_yinghangka_last,data.base_bank_account);
            }
            int img=R.drawable.logo_cb;
            if(data.base_bank_name.contains("工商")){
                img=R.drawable.logo_icbc;
            }else if(data.base_bank_name.contains("农业")){
                img=R.drawable.logo_abc;
            }else if(data.base_bank_name.contains("建设")){
                img=R.drawable.logo_ccb;
            }
            imgv_yinghangka_logo.setImageResource(img);
        } else {
            vg_no_yinghangka_parent.setVisibility(View.VISIBLE);
            vg_yinghangka_parent.setVisibility(View.GONE);
        }


    }

    @Override
    public void initListener() {
        vg_no_yinghangka_parent.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new YinHangKaBindFragment().go();
            }
        });
    }

    @Override
    public void go() {
        if(Data_personinfo_query.checkMoneyPwdAndGo()){
            super.go();
        }
    }
}
