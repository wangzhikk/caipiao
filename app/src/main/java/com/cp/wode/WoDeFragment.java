package com.cp.wode;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cp.R;
import com.cp.dengluzhuce.DengLuFragment;
import com.cp.touzhu.TouZhuJiLuFragment;
import com.cp.wode.fenxiang.FenXiangFragment;
import com.cp.wode.fenxiang.WoDeShouYiFragment;
import com.cp.wode.huishui.WoDeHuiShuiFragment;
import com.cp.wode.qianbao.WoDeQianBaoFragment;
import com.cp.wode.qianbao.ZhangBianJiLuFragment;
import com.cp.wode.shezhi.GuanYuFragment;
import com.cp.wode.shezhi.SheZhiFragment;
import com.cp.wode.shezhi.SheZhiNiChengFragment;

import utils.tjyutils.common.Common;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by kk on 2017/3/23.
 */

public class WoDeFragment extends ParentFragment {

    TextView btn_wode_touzhu_jilu,btn_wode_zhangbian_jilu;
    View btn_wode_qianbao,btn_wode_huishui,btn_wode_shezhi,btn_wode_guanyu,btn_wode_shouyi,btn_wode_fenxiang;
    ImageView img_wode_touxiang;
    View btn_shezhi_nicheng;
    TextView tv_wode_nicheng,tv_wode_qianming,tv_wode_yue;
    TextView tv_wode_dengji,tv_wode_dengji_daili;
    @Override
    public int initContentViewId() {
        return R.layout.wode;
    }
    @Override
    public void initData() {

        loadData();
    }

    public void loadData(){
        Data_personinfo_query.load(new HttpUiCallBack<Data_personinfo_query>() {
            @Override
            public void onSuccess(Data_personinfo_query data) {
                if(data.isDataOkAndToast()){
                   initViews(data);
                }
            }
        });
    }
    public void initViews(Data_personinfo_query data){
        if(data==null||!data.isDataOkAndToast())return;

        loadImage(data.base_headImage,img_wode_touxiang);
        if(StringTool.isEmpty(data.base_nickname)){
            setTextView(tv_wode_nicheng,"点击设置昵称");
        }else {
            setTextView(tv_wode_nicheng,data.base_nickname);
        }
        setTextView(tv_wode_qianming,data.base_autograph);
        setTextView(tv_wode_yue,""+ Common.getPriceYB(data.wallet_remain));



        setTextView(tv_wode_dengji,data.getGradeText());
        UiTool.setCompoundDrawables(getActivity(),tv_wode_dengji,data.getGradeResImg(),0,0,0);

        setTextView(tv_wode_dengji_daili,data.getTypeText());
        UiTool.setCompoundDrawables(getActivity(),tv_wode_dengji_daili,data.getTypeResImg(),0,0,0);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public void initListener() {

      //  bindFragmentBtn(img_wode_touxiang,new DengLuFragment());

        bindFragmentBtn( btn_wode_fenxiang,new FenXiangFragment());
        bindFragmentBtn(btn_wode_touzhu_jilu,new TouZhuJiLuFragment());
        bindFragmentBtn(btn_wode_zhangbian_jilu,new ZhangBianJiLuFragment());
        bindFragmentBtn(btn_wode_qianbao,new WoDeQianBaoFragment());
        bindFragmentBtn(btn_wode_huishui,new WoDeHuiShuiFragment());
        bindFragmentBtn(btn_wode_shezhi,new SheZhiFragment());
        bindFragmentBtn(btn_wode_guanyu,new GuanYuFragment());
        bindFragmentBtn(btn_wode_shouyi,new WoDeShouYiFragment());
        bindFragmentBtn(btn_shezhi_nicheng,new SheZhiNiChengFragment());
    }



}
