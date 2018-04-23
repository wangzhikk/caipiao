package com.cp.wode.shezhi;

import android.content.DialogInterface;
import android.view.View;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.cp.cp.Data_logout;
import com.cp.dengluzhuce.DengLuFragment;
import com.cp.wode.qianbao.yinhangka.YinHangKaBindFragment;
import com.cp.wode.shezhi.mima.XiuGaiMimaFragment;
import com.cp.wode.shezhi.mima.XiuGaiZiJinMimaFragment;
import com.cp.wode.shezhi.phone.PhoneBindFragment;
import com.cp.wode.shezhi.phone.PhoneChangeFragment;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.ImgTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.dialog.DialogTool;

/**
 * Created by kk on 2017/3/23.
 */

public class SheZhiFragment extends ParentFragment {
    View btn_login_out;
    TextView btn_qingchu_huancun;
    View btn_shezhi_xiugai_denglu_mima,btn_shezhi_xiugai_zijin_mima,btn_shezhi_bind_phone;
    @Override
    public int initContentViewId() {
        return R.layout.shezhi;
    }
    @Override
    public void initData() {

        titleTool.setTitle("设置");

        ImgTool.getCacheSize(new ImgTool.GetCacheSizeListener() {
            @Override
            public void onGetSize(long size, String showStr) {
                setTextView(btn_qingchu_huancun,showStr);
            }
        });
    }
    @Override
    public void initListener() {
       // bindFragmentBtn(btn_shezhi_yinhangka_bind,new YinHangKaBindFragment());
        bindFragmentBtn(btn_shezhi_xiugai_zijin_mima,new XiuGaiZiJinMimaFragment());
        bindFragmentBtn(btn_shezhi_xiugai_denglu_mima,new XiuGaiMimaFragment());
        bindFragmentBtn(btn_shezhi_bind_phone,new PhoneBindFragment());


        btn_login_out.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                DialogTool.initNormalQueDingDialog("", "是否退出登录?", "退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        showWaitingDialog("");
                        Data_logout.load(new HttpUiCallBack<Data_logout>() {
                            @Override
                            public void onSuccess(Data_logout data) {
                                hideWaitingDialog();
                            }
                        });
                        Data_login_validate.loginOutSuccess();
                        new DengLuFragment().go();
                    }
                },"取消").show();
            }
        });
        btn_qingchu_huancun.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                ImgTool.clearCache();
                setTextView(btn_qingchu_huancun,"");
                CommonTool.showToast("清除缓存成功");
            }
        });

    }



}
