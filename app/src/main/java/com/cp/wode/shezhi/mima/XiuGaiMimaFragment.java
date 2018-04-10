package com.cp.wode.shezhi.mima;

import android.view.View;
import android.widget.EditText;

import com.cp.R;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * Created by kk on 2017/3/23.
 */

public class XiuGaiMimaFragment extends ParentFragment {


    EditText et_mima_yuanmima,et_mima_xinmima,et_mima_queren;
    View btn_mima_queding;
    @Override
    public int initContentViewId() {
        return R.layout.mima_xiugai;
    }
    @Override
    public void initData() {
        UiTool.setSoftInputModeSpan(getActivity());
        titleTool.setTitle("修改登录密码");
    }



    @Override
    public void initListener() {

        btn_mima_queding.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                String mima_yuanmima=et_mima_yuanmima.getText().toString().trim();
                String mima_xinmima=et_mima_xinmima.getText().toString().trim();
                String mima_queren=et_mima_queren.getText().toString().trim();

                if(StringTool.isEmpty(mima_yuanmima)){
                    CommonTool.showToast(et_mima_yuanmima.getHint().toString());
                    return;
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
                Data_firstpassword_modify.load(mima_yuanmima, mima_xinmima, new HttpUiCallBack<Data_firstpassword_modify>() {
                    @Override
                    public void onSuccess(Data_firstpassword_modify data) {
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
