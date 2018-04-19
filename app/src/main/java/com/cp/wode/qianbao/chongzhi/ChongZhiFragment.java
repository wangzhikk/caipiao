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

    View btn_zhifu_xianxia_zhifubao,btn_zhifu_xianxia_weixin,btn_zhifu_xianxia_yinhangka;

    WzSimpleRecycleView recycleView_xianshang_chongzhi;

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
            public void onSuccess(final Data_recharge_query data) {
                if(data.isDataOkAndToast()){
                    bindFragmentBtn(btn_zhifu_xianxia_zhifubao, XianXiaZhiFuDetailFragment.byData(data.getOfflineBeanAlipay()));
                    bindFragmentBtn(btn_zhifu_xianxia_weixin, XianXiaZhiFuDetailFragment.byData(data.getOfflineBeanWeixin()));
                    bindFragmentBtn(btn_zhifu_xianxia_yinhangka, XianXiaZhiFuDetailFragment.byData(data.getOfflineBeanBank()));


                    recycleView_xianshang_chongzhi.setData(data.online, R.layout.chongzhi_item, new WzSimpleRecycleView.WzRecycleAdapter() {
                        @Override
                        public void initData(int position, int type, View itemView) {
                            super.initData(position, type, itemView);

                            Data_recharge_query.OnLineBean onLineBean=data.online.get(position);

                            loadImage(onLineBean.image,itemView,R.id.imgv_chongzhi_item);
                            setTextView(itemView,R.id.tv_chongzhi_item_name,onLineBean.name);
                            setTextView(itemView,R.id.tv_chongzhi_item_des,onLineBean.name);

                            bindFragmentBtn(itemView,XianShangZhiFuFragment.byData(onLineBean));
                        }
                    });



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
