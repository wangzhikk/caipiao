package com.cp.wode.qianbao.chongzhi.xianshang;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_wallet_remain;
import com.cp.wode.qianbao.chongzhi.ChongZhiJiLuFragment;
import com.cp.wode.qianbao.chongzhi.Data_recharge_query;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import utils.tjyutils.common.Common;
import utils.tjyutils.parent.ParentFragment;
import utils.tjyutils.ui.DialogToolTem;
import utils.wzutils.AppTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.FileTool;
import utils.wzutils.common.ImgLocalTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.QrCodeTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * Created by kk on 2017/3/23.
 */

public class XianShangZhiFuSaoMaFragment extends ParentFragment {
    Data_recharge_online data_recharge_online;
    ImageView imgv_zhifu_xianshang_erweima;
    View btn_chongzhi_shangyibu, btn_chongzhi_lijichongzhi, btn_chongzhi_woyizhifu;

    TextView tv_zhifu_saoma_dingdan,tv_zhifu_saoma_jine;
    @Override
    public int initContentViewId() {
        return R.layout.zhifu_xianshang_saoma;
    }

    @Override
    public void initData() {
        data_recharge_online = (Data_recharge_online) getArgument("data_recharge_online", new Data_recharge_online());
        titleTool.setTitle("线上支付");

        setTextView(tv_zhifu_saoma_dingdan,"订单号: "+data_recharge_online.serial);
        setTextView(tv_zhifu_saoma_jine,"充值金额: "+data_recharge_online.amount+"元");
        loadData();
    }

    Bitmap bitmap;

    public void loadData() {
        try {
            bitmap = QrCodeTool.initQrCode(data_recharge_online.url, 0);
            imgv_zhifu_xianshang_erweima.setImageBitmap(bitmap);

        } catch (Exception e) {
            LogTool.ex(e);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            if (bitmap != null) {
                bitmap.recycle();
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }

    @Override
    public void initListener() {
        btn_chongzhi_shangyibu.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                onBackPressed();
            }
        });
        btn_chongzhi_lijichongzhi.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                DialogToolTem.showIosDialog("提示", "将为您保存二维码并打开QQ,是否立即充值?", "确定", new WzViewOnclickListener() {
                    @Override
                    public void onClickWz(View v) {
                        if (bitmap != null) {
                            try {
                                ImgLocalTool.saveBmp2Gallery(bitmap,"zhifu.png");
                                CommonTool.goApp("com.tencent.mobileqq");
                            } catch (Exception e) {
                                LogTool.ex(e);
                            }

                        }
                    }
                });
            }
        });
        bindFragmentBtn(btn_chongzhi_woyizhifu, new ChongZhiJiLuFragment());
    }


    public static ParentFragment byData(Data_recharge_online data_recharge_online) {
        XianShangZhiFuSaoMaFragment xianShangZhiFuFragment = new XianShangZhiFuSaoMaFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data_recharge_online", data_recharge_online);
        xianShangZhiFuFragment.setArguments(bundle);
        return xianShangZhiFuFragment;
    }
}
