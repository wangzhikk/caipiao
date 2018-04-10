package com.cp.wode.shezhi;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.cp.R;
import com.cp.cp.Data_login_validate;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * Created by kk on 2017/3/23.
 */

public class SheZhiNiChengFragment extends ParentFragment {

    View btn_tijiao_nicheng;
    EditText tv_shezhi_nickname,tv_shezhi_autograph;
    @Override
    public int initContentViewId() {
        return R.layout.shezhi_nicheng;
    }
    @Override
    public void initData() {

        titleTool.setTitle("设置");

        setTextView(tv_shezhi_nickname, Data_login_validate.getData_login_validate().getUserInfo().base_nickname);
        setTextView(tv_shezhi_autograph, Data_login_validate.getData_login_validate().getUserInfo().base_autograph);
    }
    @Override
    public void initListener() {
        btn_tijiao_nicheng.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {


                String nickName=tv_shezhi_nickname.getText().toString().trim();
                if(StringTool.isEmpty(nickName)){
                    CommonTool.showToast(tv_shezhi_nickname.getHint());
                    return;
                }
                String autograph=tv_shezhi_autograph.getText().toString().trim();
                if(StringTool.isEmpty(autograph)){
                    CommonTool.showToast(tv_shezhi_autograph.getHint());
                    return;
                }

                showWaitingDialog("");
                Data_personality_set.load(nickName, autograph, new HttpUiCallBack<Data_personality_set>() {
                    @Override
                    public void onSuccess(Data_personality_set data) {
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
