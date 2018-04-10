package com.cp.wode.shezhi.mima;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_phonecaptcha_send;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.daojishi.DaoJiShiSimple;

/**
 * Created by kk on 2017/3/23.
 */

public class ZhaoHuiMimaFragment extends ParentFragment {


    EditText et_mima_account,et_mima_phone,et_mima_yanzhengma,et_mima_xinmima;
    TextView tv_mima_huoqu_yanzhengma;
    View btn_mima_queding;
    @Override
    public int initContentViewId() {
        return R.layout.mima_zhaohui;
    }
    @Override
    public void initData() {
        UiTool.setSoftInputModeSpan(getActivity());
        titleTool.setTitle("找回密码");
    }



    @Override
    public void initListener() {

        btn_mima_queding.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                String account=et_mima_account.getText().toString().trim();
                String phone=et_mima_phone.getText().toString().trim();
                String yanzhengma=et_mima_yanzhengma.getText().toString().trim();
                String xinmima=et_mima_xinmima.getText().toString().trim();

                if(StringTool.isEmpty(account)){
                    CommonTool.showToast(et_mima_account.getHint().toString());
                    return;
                }
                if(StringTool.isEmpty(phone)){
                    CommonTool.showToast(et_mima_phone.getHint().toString());
                    return;
                }
                if(StringTool.isEmpty(yanzhengma)){
                    CommonTool.showToast(et_mima_yanzhengma.getHint().toString());
                    return;
                }
                if(StringTool.isEmpty(xinmima)){
                    CommonTool.showToast(et_mima_xinmima.getHint().toString());
                    return;
                }
                showWaitingDialog("");
                Data_firstpassword_reset.load(account, phone, yanzhengma, xinmima, new HttpUiCallBack<Data_firstpassword_reset>() {
                    @Override
                    public void onSuccess(Data_firstpassword_reset data) {
                        hideWaitingDialog();
                        CommonTool.showToast(data.msg);
                        if(data.isDataOk()){
                            getActivityWz().finish();
                        }
                    }
                });
            }
        });

        new DaoJiShiSimple().initDaoJiShiWithPhoneCheck(getClass().toString(), 0, et_mima_phone,tv_mima_huoqu_yanzhengma, tv_mima_huoqu_yanzhengma, "", new DaoJiShiSimple.OnHuoQuYanZhengMa() {
            @Override
            public boolean onHuoQuYanZhengMa(final DaoJiShiSimple.OnHuoQuYanZhengMaSuccess onHuoQuYanZhengMaSuccess) {
                Data_phonecaptcha_send.load(et_mima_account.getText().toString().trim(), et_mima_phone.getText().toString().trim(), new HttpUiCallBack<Data_phonecaptcha_send>() {
                    @Override
                    public void onSuccess(Data_phonecaptcha_send data) {
                        onHuoQuYanZhengMaSuccess.onHuoQuYanZhengMaSuccess(data.isDataOk(),data.msg,data.time);
                    }
                });
                return true;
            }
        });

    }



}
