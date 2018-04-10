package com.cp.dengluzhuce;

import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.yanzhenjie.sofia.Sofia;

import java.util.regex.Pattern;

import utils.tjyutils.common.WebFragment;
import utils.tjyutils.http.HttpConfigAx;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * Created by kk on 2017/3/23.
 */

public class ZhuCeFragment extends ParentFragment {


    View btn_zhuce,btn_go_zhuce_xieyi,btn_login_close;
    CheckBox cb_zhuce_tongyi;
    EditText et_zhuce_account,et_zhuce_pwd,et_zhuce_pwd2,et_zhuce_id;
    @Override
    public int initContentViewId() {
        return R.layout.zhuce;
    }
    @Override
    public void initData() {
        Sofia.with(getActivityWz()).invasionStatusBar().navigationBarBackground(Color.BLACK);

        UiTool.setSoftInputModeSpan(getActivity());
    }



    @Override
    public void initListener() {

        btn_zhuce.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                String errMsg="";
                if(!cb_zhuce_tongyi.isChecked()){
                    errMsg="请先阅读并同意注册协议";
                }
                String account=et_zhuce_account.getText().toString().trim();
                String pwd=et_zhuce_pwd.getText().toString().trim();
                String pwd2=et_zhuce_pwd2.getText().toString().trim();
                String id=et_zhuce_id.getText().toString().trim();

                if(!Pattern.compile("^[A-Za-z][A-Za-z0-9]{5,15}$").matcher(account).matches()){
                    errMsg=et_zhuce_account.getHint().toString();
                }else if(pwd.length()<6||pwd.length()>16){
                    errMsg=et_zhuce_pwd.getHint().toString();
                }else if(!pwd.equals(pwd2)){
                    errMsg="两次密码输入不一致";
                }

                if(StringTool.notEmpty(errMsg)){
                    CommonTool.showToast(errMsg);
                    return;
                }
                showWaitingDialog("");
                Data_register_appreg.load(account, pwd, id, new HttpUiCallBack<Data_register_appreg>() {
                    @Override
                    public void onSuccess(Data_register_appreg data) {
                        hideWaitingDialog();
                        CommonTool.showToast(data.msg);
                        if(data.isDataOk()){
                            getActivityWz().finish();
                        }
                    }
                });



            }
        });
        btn_go_zhuce_xieyi.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new WebFragment().go(HttpConfigAx.getHtmlUrl("regprotocol"),"注册协议");
            }
        });
        btn_login_close.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                getActivityWz().finish();
            }
        });
    }



}
