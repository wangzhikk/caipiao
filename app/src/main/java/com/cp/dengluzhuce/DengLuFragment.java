package com.cp.dengluzhuce;

import android.graphics.Color;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.cp.wode.shezhi.mima.ZhaoHuiMimaFragment;
import com.yanzhenjie.sofia.Sofia;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * Created by kk on 2017/3/23.
 */

public class DengLuFragment extends ParentFragment {

    View btn_login_close,btn_go_zhuce,btn_wangji_mima;
    TextView btn_login;
    EditText et_login_account,et_login_pwd;
    @Override
    public int initContentViewId() {
        return R.layout.denglu;
    }
    @Override
    public void initData() {

        Sofia.with(getActivityWz()).invasionStatusBar().navigationBarBackground(Color.BLACK);

    }



    @Override
    public void initListener() {
        bindFragmentBtn(btn_go_zhuce,new ZhuCeFragment());
        bindFragmentBtn(btn_wangji_mima,new ZhaoHuiMimaFragment());
        btn_login_close.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                getActivityWz().finish();
            }
        });
        btn_login.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {

                String name=et_login_account.getText().toString().trim();
                String pwd=et_login_pwd.getText().toString().trim();

                if(name.length()<1){
                    CommonTool.showToast(et_login_account.getHint());
                    return;
                }
                if(pwd.length()<1){
                    CommonTool.showToast(et_login_pwd.getHint());
                    return;
                }

                showWaitingDialog("");
                Data_login_validate.load(name, pwd, new HttpUiCallBack<Data_login_validate>() {
                    @Override
                    public void onSuccess(Data_login_validate data) {
                        hideWaitingDialog();
                        CommonTool.showToast(data.msg);
                        if(data.isDataOk()){
                            getActivityWz().finish();
                        }
                    }
                });
            }
        });

    }



}
