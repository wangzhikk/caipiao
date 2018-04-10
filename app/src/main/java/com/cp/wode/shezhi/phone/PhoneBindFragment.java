package com.cp.wode.shezhi.phone;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.cp.cp.Data_phonecaptcha_send;
import com.cp.wode.shezhi.mima.Data_thirdpassword_reset;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.daojishi.DaoJiShiSimple;

/**
 * Created by kk on 2017/3/23.
 * 修改资金密码
 */

public class PhoneBindFragment extends ParentFragment {


    EditText et_mima_yanzhengma,et_mima_phone;
    TextView tv_mima_huoqu_yanzhengma;
    View btn_mima_queding;


    @Override
    public int initContentViewId() {
        return R.layout.phone_bind;
    }
    @Override
    public void initData() {
        UiTool.setSoftInputModeSpan(getActivity());
        titleTool.setTitle("绑定手机号");
    }
    @Override
    public void initListener() {
        btn_mima_queding.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                String mima_yanzhengma=et_mima_yanzhengma.getText().toString().trim();

                {//手机验证码验证
                    if(StringTool.isEmpty(et_mima_phone.getText().toString())){
                        CommonTool.showToast(et_mima_phone.getHint().toString());
                        return;
                    }
                    if(StringTool.isEmpty(et_mima_yanzhengma.getText().toString())){
                        CommonTool.showToast(et_mima_yanzhengma.getHint().toString());
                        return;
                    }
                }

                showWaitingDialog("");
                Data_phone_binding.load(et_mima_phone.getText().toString().trim(), mima_yanzhengma, new HttpUiCallBack<Data_phone_binding>() {
                    @Override
                    public void onSuccess(Data_phone_binding data) {
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
                Data_phonecaptcha_send.load("", et_mima_phone.getText().toString().trim(), new HttpUiCallBack<Data_phonecaptcha_send>() {
                    @Override
                    public void onSuccess(Data_phonecaptcha_send data) {
                        onHuoQuYanZhengMaSuccess.onHuoQuYanZhengMaSuccess(data.isDataOk(),data.msg,data.time);
                    }
                });
                return true;
            }
        });


    }


    @Override
    public void go() {
        if(StringTool.isEmpty(Data_login_validate.getData_login_validate().getUserInfo().base_phone)){//没有绑定手机号
            super.go();
        }else {
            new PhoneChangeFragment().go();
        }
    }
}
