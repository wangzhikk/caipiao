package com.cp.wode.shezhi;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;

import com.cp.R;

import utils.tjyutils.http.HttpConfigAx;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.QrCodeTool;
import utils.wzutils.ui.clicp.RoundImageView;

/**
 * Created by kk on 2017/3/23.
 */

public class GuanYuFragment extends ParentFragment {

    TextView tv_app_name_version,tv_guanyu_xiazai;
    RoundImageView imgv_guanyu_logo;
    ImageView img_guanyu_ma;
    @Override
    public int initContentViewId() {
        return R.layout.shezhi_guanyu;
    }
    @Override
    public void initData() {

        titleTool.setTitle("关于");

        String appName=getResources().getString(R.string.app_name);

        String name=appName+" "+CommonTool.getVersionStr();
        setTextView(tv_app_name_version,name);

        imgv_guanyu_logo.setRoundCornerDp(10);

        setTextView(tv_guanyu_xiazai,"扫描上方二维码下载"+appName+"客户端");


        String url= HttpConfigAx.getHtmlUrl("todownload");

        Bitmap bitmap=QrCodeTool.initQrCode(url,200);
        img_guanyu_ma.setImageBitmap(bitmap);

    }
    @Override
    public void initListener() {

    }



}
