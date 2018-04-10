package com.cp.wode.shezhi.phone;

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
 * 修改资金密码
 */

public class PhoneChangeFragment extends ParentFragment {


    EditText et_mima_yanzhengma, et_mima_phone;
    TextView et_mima_yanzhengma_yuan, tv_mima_huoqu_yanzhengma_yuan;
    TextView tv_mima_huoqu_yanzhengma;
    View btn_mima_queding;

    TextView lb_mima_phone;

    @Override
    public int initContentViewId() {
        return R.layout.phone_change;
    }

    @Override
    public void initData() {
        UiTool.setSoftInputModeSpan(getActivity());
        titleTool.setTitle("绑定手机号");

        setTextView(lb_mima_phone, "新手机号");
    }

    @Override
    public void initListener() {
        btn_mima_queding.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                String mima_yanzhengma_yuan = et_mima_yanzhengma_yuan.getText().toString().trim();
                String mima_yanzhengma = et_mima_yanzhengma.getText().toString().trim();

                {//手机验证码验证
                    if (StringTool.isEmpty(et_mima_phone.getText().toString())) {
                        CommonTool.showToast(et_mima_phone.getHint().toString());
                        return;
                    }
                    if (StringTool.isEmpty(et_mima_yanzhengma.getText().toString())) {
                        CommonTool.showToast(et_mima_yanzhengma.getHint().toString());
                        return;
                    }
                    if (StringTool.isEmpty(et_mima_yanzhengma_yuan.getText().toString())) {
                        CommonTool.showToast(et_mima_yanzhengma_yuan.getHint().toString());
                        return;
                    }
                }


                showWaitingDialog("");
                Data_phone_replace.load(et_mima_phone.getText().toString().trim(), mima_yanzhengma_yuan, mima_yanzhengma, new HttpUiCallBack<Data_phone_replace>() {
                    @Override
                    public void onSuccess(Data_phone_replace data) {
                        hideWaitingDialog();
                        CommonTool.showToast(data.msg);
                        if (data.isDataOk()) {
                            getActivityWz().finish();
                        }
                    }
                });

            }
        });

        new DaoJiShiSimple().initDaoJiShiWithPhoneCheck(getClass().toString(), 0, et_mima_phone, tv_mima_huoqu_yanzhengma, tv_mima_huoqu_yanzhengma, "", new DaoJiShiSimple.OnHuoQuYanZhengMa() {
            @Override
            public boolean onHuoQuYanZhengMa(final DaoJiShiSimple.OnHuoQuYanZhengMaSuccess onHuoQuYanZhengMaSuccess) {
                Data_phonecaptcha_send.load("", et_mima_phone.getText().toString().trim(), new HttpUiCallBack<Data_phonecaptcha_send>() {
                    @Override
                    public void onSuccess(Data_phonecaptcha_send data) {
                        onHuoQuYanZhengMaSuccess.onHuoQuYanZhengMaSuccess(data.isDataOk(), data.msg, data.time);
                    }
                });
                return true;
            }
        });

        new DaoJiShiSimple().initDaoJiShi("", 0, tv_mima_huoqu_yanzhengma_yuan, tv_mima_huoqu_yanzhengma_yuan, "", new DaoJiShiSimple.OnHuoQuYanZhengMa() {
            @Override
            public boolean onHuoQuYanZhengMa(final DaoJiShiSimple.OnHuoQuYanZhengMaSuccess onHuoQuYanZhengMaSuccess) {
                Data_phonecaptcha_send.load("", "", new HttpUiCallBack<Data_phonecaptcha_send>() {
                    @Override
                    public void onSuccess(Data_phonecaptcha_send data) {
                        onHuoQuYanZhengMaSuccess.onHuoQuYanZhengMaSuccess(data.isDataOk(), data.msg, data.time);
                    }
                });
                return true;
            }
        });


    }


}
