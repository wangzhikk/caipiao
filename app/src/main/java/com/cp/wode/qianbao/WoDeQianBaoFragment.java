package com.cp.wode.qianbao;

import android.view.View;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_wallet_remain;
import com.cp.wode.qianbao.chongzhi.ChongZhiFragment;
import com.cp.wode.qianbao.chongzhi.ChongZhiJiLuFragment;
import com.cp.wode.qianbao.tixian.TiXianFragment;
import com.cp.wode.qianbao.tixian.TiXianJiLuFragment;
import com.cp.wode.qianbao.yinhangka.YinHangKaGuanLiFragment;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by kk on 2017/3/23.
 */

public class WoDeQianBaoFragment extends ParentFragment {

    View tv_wode_yinhangka,tv_wode_chongzhi_jilu,tv_wode_tixian_jilu,tv_wode_tixian,tv_wode_chongzhi;
    TextView tv_qianbao_yue;
    @Override
    public int initContentViewId() {
        return R.layout.wode_qianbao;
    }
    @Override
    public void initData() {
        titleTool.setTitle("我的钱包");
        loadData();
    }


    public void loadData(){
        Data_wallet_remain.load(new HttpUiCallBack<Data_wallet_remain>() {
            @Override
            public void onSuccess(Data_wallet_remain data) {
                if(data.isDataOkAndToast()){
                    setTextView(tv_qianbao_yue,data.remain);
                }
            }
        });
    }
    @Override
    public void initListener() {





        bindFragmentBtn(tv_wode_chongzhi,new ChongZhiFragment());
        bindFragmentBtn(tv_wode_tixian,new TiXianFragment());
        bindFragmentBtn(tv_wode_yinhangka,new YinHangKaGuanLiFragment());
        bindFragmentBtn(tv_wode_chongzhi_jilu,new ChongZhiJiLuFragment());
        bindFragmentBtn( tv_wode_tixian_jilu,new TiXianJiLuFragment());

    }



}
