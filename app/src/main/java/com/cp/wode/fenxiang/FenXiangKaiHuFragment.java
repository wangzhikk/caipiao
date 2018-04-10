package com.cp.wode.fenxiang;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cp.R;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.QrCodeTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * Created by kk on 2017/3/23.
 */

public class FenXiangKaiHuFragment extends ParentFragment {

    TextView tv_fenxiang_lianjie,tv_fenxiang_id;
    View btn_fenxiang_fuzhi;
    ImageView img_fenxiang;
    EditText et_fenxiang_zhuce_name,et_fenxiang_zhuce_pwd,et_fenxiang_zhuce_pwd2;
    View btn_fenxiang_zhuce_queding;
    @Override
    public int initContentViewId() {
        return R.layout.fenxiang_kaihu;
    }
    @Override
    public void initData() {

        loadData();
    }
    public void loadData(){
        Data_share_link.load(new HttpUiCallBack<Data_share_link>() {
            @Override
            public void onSuccess(Data_share_link data) {
                if(data.isDataOkAndToast()){
                    setTextView(tv_fenxiang_lianjie,""+data.url);
                    setTextView(tv_fenxiang_id,""+data.uuid);
                    img_fenxiang.setImageBitmap(QrCodeTool.initQrCode(data.url,100));
                }
            }
        });

    }
    @Override
    public void initListener() {

        btn_fenxiang_fuzhi.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                ClipboardManager cm = (ClipboardManager) getActivityWz().getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tv_fenxiang_lianjie.getText());
                CommonTool.showToast("复制成功，可以发给朋友们了。");
            }
        });
        btn_fenxiang_zhuce_queding.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {


                String userName=et_fenxiang_zhuce_name.getText().toString();
                String password=et_fenxiang_zhuce_pwd.getText().toString();
                String password2=et_fenxiang_zhuce_pwd2.getText().toString();

                if(StringTool.isEmpty(userName)){
                    CommonTool.showToast(et_fenxiang_zhuce_name.getHint().toString());
                    return;
                }
                if(StringTool.isEmpty(password)){
                    CommonTool.showToast(et_fenxiang_zhuce_pwd.getHint().toString());
                    return;
                }
                if(!password.equals(password2)){
                    CommonTool.showToast("两次密码输入不一致");
                    return;
                }

                showWaitingDialog("");
                Data_register_recommendreg.load(userName, password, new HttpUiCallBack<Data_register_recommendreg>() {
                    @Override
                    public void onSuccess(Data_register_recommendreg data) {
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
