package com.cp.wode.qianbao.yinhangka;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.cp.wode.Data_personinfo_query;
import com.cp.wode.shezhi.mima.XiuGaiZiJinMimaFragment;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.durban.Durban;

import java.io.File;
import java.util.ArrayList;

import utils.tjyutils.parent.ParentFragment;
import utils.tjyutils.ui.dizhi.XuanZheShouHuoDiZhiFragment;
import utils.wzutils.common.BroadcastReceiverTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.dialog.ListDialogTool;

/**
 * Created by kk on 2017/3/23.
 */

public class YinHangKaBindFragment extends ParentFragment {

    public static String action_yinghangka_change="action_yinghangka_change";
    View btn_yinhangka_bind;
    TextView tv_yinhangka_bind_username,tv_yinhangka_bind_yinhang_name,tv_yinhangka_bind_yinhang_number,tv_yinhangka_bind_city,tv_yinhangka_bind_subbranch,tv_yinhangka_bind_mima;
    View vg_yinhangka_name;
    @Override
    public int initContentViewId() {
        return R.layout.yinhangka_bind;
    }
    @Override
    public void initData() {
        titleTool.setTitle("银行卡");

        if(Data_login_validate.getData_login_validate().getUserInfo().base_auth_bank==1){//已经绑定银行卡
            vg_yinhangka_name.setVisibility(View.GONE);
        }else {
            vg_yinhangka_name.setVisibility(View.VISIBLE);
        }

    }

    public void tijiao(){

        String name=tv_yinhangka_bind_username.getText().toString().trim();
        String account=tv_yinhangka_bind_yinhang_number.getText().toString().trim();
        String subbranch=tv_yinhangka_bind_subbranch.getText().toString().trim();
        String password=tv_yinhangka_bind_mima.getText().toString().trim();

        if(vg_yinhangka_name.getVisibility()==View.VISIBLE){
            if(StringTool.isEmpty(name)){
                CommonTool.showToast(tv_yinhangka_bind_username.getHint());
                return;
            }
        }


        if(StringTool.isEmpty(bankCode)){
            CommonTool.showToast(tv_yinhangka_bind_yinhang_name.getHint());
            return;
        }
        if(StringTool.isEmpty(account)){
            CommonTool.showToast(tv_yinhangka_bind_yinhang_number.getHint());
            return;
        }
        if(diZhiChoose==null){
            CommonTool.showToast("请选择开户城市");
            return;
        }
        if(StringTool.isEmpty(subbranch)){
            CommonTool.showToast(tv_yinhangka_bind_subbranch.getHint());
            return;
        }
        if(StringTool.isEmpty(password)){
            CommonTool.showToast(tv_yinhangka_bind_mima.getHint());
            return;
        }


        showWaitingDialog("");
        Data_bank_replace.load(name, bankCode, account, diZhiChoose.address_province, diZhiChoose.address_city, subbranch, password, new HttpUiCallBack<Data_bank_replace>() {
            @Override
            public void onSuccess(Data_bank_replace data) {
                hideWaitingDialog();
                CommonTool.showToast(data.getMsg());
                if(data.isDataOk()){
                    BroadcastReceiverTool.sendAction(action_yinghangka_change);
                    getActivityWz().finish();
                }
            }
        });


    }
    String bankCode="";
    @Override
    public void initListener() {
        btn_yinhangka_bind.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                tijiao();
            }
        });
        tv_yinhangka_bind_yinhang_name.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                showWaitingDialog("");
                Data_bank_querybank.load(new HttpUiCallBack<Data_bank_querybank>() {
                    @Override
                    public void onSuccess(final Data_bank_querybank data) {
                        hideWaitingDialog();
                        if(data.isDataOkAndToast()){
                            final String[] strings = data.getBankNams();
                            ListDialogTool.showListDialog("请选择银行", strings, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    setTextView(tv_yinhangka_bind_yinhang_name, strings[which]);
                                    bankCode=data.getBankCode(which);

                                }
                            });
                        }
                    }
                });



            }
        });
        tv_yinhangka_bind_city.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new XuanZheShouHuoDiZhiFragment().goForResult(YinHangKaBindFragment.this,0,XuanZheShouHuoDiZhiFragment.maxDepCity);
            }
        });

    }
    XuanZheShouHuoDiZhiFragment.DiZhiChoose diZhiChoose ;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(resultCode!= Activity.RESULT_OK)return;
            if(requestCode==0){//选择地址
                 diZhiChoose=XuanZheShouHuoDiZhiFragment.getDiZhiByIntent(data);
                setTextView(tv_yinhangka_bind_city,""+diZhiChoose.address_province+" "+diZhiChoose.address_city+" "+diZhiChoose.address_area);
            }
        }catch (Exception e){
            LogTool.ex(e);
        }

    }


    @Override
    public void go() {
        if(Data_login_validate.getData_login_validate().getUserInfo().base_auth_thirdpwd==0){//没有设置支付密码， 不让进
            CommonTool.showToast("请先设置支付密码!");
            //new XiuGaiZiJinMimaFragment().go();
            return;
        }
        super.go();
    }
}
