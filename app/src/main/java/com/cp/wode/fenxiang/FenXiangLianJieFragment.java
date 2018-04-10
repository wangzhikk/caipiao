package com.cp.wode.fenxiang;

import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cp.R;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.QrCodeTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * Created by kk on 2017/3/23.
 */

public class FenXiangLianJieFragment extends ParentFragment {

TextView tv_fenxiang_lianjie,tv_fenxiang_id;
View btn_fenxiang_fuzhi;
ImageView img_fenxiang;
    @Override
    public int initContentViewId() {
        return R.layout.fenxiang_lianjie;
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



    }



}
