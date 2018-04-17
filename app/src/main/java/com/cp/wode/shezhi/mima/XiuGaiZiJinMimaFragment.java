package com.cp.wode.shezhi.mima;

import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.cp.cp.Data_phonecaptcha_send;
import com.cp.wode.shezhi.phone.PhoneBindFragment;
import com.cp.wode.shezhi.phone.PhoneChangeFragment;

import utils.tjyutils.parent.ParentFragment;
import utils.tjyutils.ui.DialogToolTem;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.daojishi.DaoJiShiSimple;
import utils.wzutils.ui.dialog.DialogTool;

/**
 * Created by kk on 2017/3/23.
 * 修改资金密码
 */

public class XiuGaiZiJinMimaFragment extends ParentFragment {


    EditText et_mima_yuanmima,et_mima_xinmima,et_mima_queren;
    EditText et_mima_yanzhengma,et_mima_phone;
    TextView tv_mima_huoqu_yanzhengma;
    View btn_mima_queding;


    View vg_zijin_mima;
    @Override
    public int initContentViewId() {
        return R.layout.mima_xiugai_zijin;
    }
    @Override
    public void initData() {
        UiTool.setSoftInputModeSpan(getActivity());

        if(!Data_login_validate.getData_login_validate().getUserInfo().hasMoneyPwd()){
            titleTool.setTitle("设置资金密码");
            vg_zijin_mima.setVisibility(View.GONE);
        }else {
            titleTool.setTitle("修改资金密码");
            vg_zijin_mima.setVisibility(View.VISIBLE);
        }

    }



    @Override
    public void initListener() {

        btn_mima_queding.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                String mima_yanzhengma=et_mima_yanzhengma.getText().toString().trim();
                String mima_xinmima=et_mima_xinmima.getText().toString().trim();
                String mima_queren=et_mima_queren.getText().toString().trim();


                if(vg_zijin_mima.getVisibility()==View.VISIBLE){//需要手机验证码
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
                }



                if(StringTool.isEmpty(mima_xinmima)){
                    CommonTool.showToast(et_mima_xinmima.getHint().toString());
                    return;
                }
                if(!mima_xinmima.equals(mima_queren)){
                    CommonTool.showToast("两次密码输入不一致");
                    return;
                }

                showWaitingDialog("");
                Data_thirdpassword_reset.load(et_mima_phone.getText().toString().trim(), mima_xinmima, mima_yanzhengma, new HttpUiCallBack<Data_thirdpassword_reset>() {
                    @Override
                    public void onSuccess(Data_thirdpassword_reset data) {
                        hideWaitingDialog();

                        if(data.isDataOk()){
                            CommonTool.showToast(data.msg);
                            getActivityWz().finish();
                        }else if(data.code==163){//未绑定手机号码
//                            DialogTool.initNormalQueDingDialog("", "您需要先绑定手机号码,是否前往绑定?", "立即绑定", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    new PhoneBindFragment().go();
//                                }
//                            },"").show();

                            DialogToolTem.showIosDialog("温馨提示", "您需要先绑定手机号码", "立即前往绑定", new WzViewOnclickListener() {
                                @Override
                                public void onClickWz(View v) {
                                    new PhoneBindFragment().go();
                                }
                            });

                        }else {
                            CommonTool.showToast(data.msg);
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



}
