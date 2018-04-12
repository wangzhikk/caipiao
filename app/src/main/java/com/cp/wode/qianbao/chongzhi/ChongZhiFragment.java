package com.cp.wode.qianbao.chongzhi;

import android.view.View;

import com.cp.R;
import com.cp.wode.qianbao.tixian.TiXianFragment;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.TestData;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class ChongZhiFragment extends ParentFragment {

    View tv_go_xianshang_zhifu,btn_zhifu_xianxia_zhifubao,btn_zhifu_xianxia_weixin,btn_zhifu_xianxia_yinhangka;
    @Override
    public int initContentViewId() {
        return R.layout.chongzhi;
    }
    @Override
    public void initData() {

        titleTool.setTitle("充值");
        titleTool.setTitleRightTv("充值记录", new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new ChongZhiJiLuFragment().go();
            }
        });
        loadData();
    }


    public void loadData(){
        Data_recharge_query.load(new HttpUiCallBack<Data_recharge_query>() {
            @Override
            public void onSuccess(Data_recharge_query data) {
                if(data.isDataOkAndToast()){
                    bindFragmentBtn(btn_zhifu_xianxia_zhifubao, XianXiaZhiFuDetailFragment.byData(data.getOfflineBeanAlipay()));
                    bindFragmentBtn(btn_zhifu_xianxia_weixin, XianXiaZhiFuDetailFragment.byData(data.getOfflineBeanWeixin()));
                    bindFragmentBtn(btn_zhifu_xianxia_yinhangka, XianXiaZhiFuDetailFragment.byData(data.getOfflineBeanBank()));
                }else if(data.code==186){
                    TiXianFragment.showTiShi(parent,data.time);
                }
            }
        });
    }



    @Override
    public void initListener() {

    }


}
