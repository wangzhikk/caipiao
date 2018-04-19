package com.cp.wode.qianbao.chongzhi;

import android.view.View;

import com.cp.R;
import com.cp.wode.qianbao.chongzhi.xianshang.XianShangZhiFuFragment;
import com.cp.wode.qianbao.tixian.TiXianFragment;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;

/**
 * Created by kk on 2017/3/23.
 */

public class ChongZhiFragment extends ParentFragment {

    View btn_zhifu_xianxia_zhifubao,btn_zhifu_xianxia_weixin,btn_zhifu_xianxia_yinhangka;

    WzSimpleRecycleView recycleView_xianshang_chongzhi;

    View vg_chongzhi_xianshang,vg_chongzhi_xianxia;
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
                        if(data.offline!=null&&data.offline.size()>0){//线下支付
                            vg_chongzhi_xianxia.setVisibility(View.VISIBLE);
                            bindFragmentBtn(btn_zhifu_xianxia_zhifubao, XianXiaZhiFuDetailFragment.byData(data.getOfflineBeanAlipay()));
                            bindFragmentBtn(btn_zhifu_xianxia_weixin, XianXiaZhiFuDetailFragment.byData(data.getOfflineBeanWeixin()));
                            bindFragmentBtn(btn_zhifu_xianxia_yinhangka, XianXiaZhiFuDetailFragment.byData(data.getOfflineBeanBank()));
                        }

                        if(data.online!=null&&data.online.size()>0){//线上支付
                            vg_chongzhi_xianshang.setVisibility(View.VISIBLE);
                            recycleView_xianshang_chongzhi.setData(data.online, R.layout.chongzhi_item, new WzSimpleRecycleView.WzRecycleAdapter() {
                                @Override
                                public void initData(int position, int type, View itemView) {
                                    super.initData(position, type, itemView);
                                    Data_recharge_query.OnLineBean onLineBean=data.online.get(position);
                                    loadImage(onLineBean.image,itemView,R.id.imgv_chongzhi_item);
                                    setTextView(itemView,R.id.tv_chongzhi_item_name,onLineBean.name);
                                    setTextView(itemView,R.id.tv_chongzhi_item_des,onLineBean.name);
                                    bindFragmentBtn(itemView, XianShangZhiFuFragment.byData(onLineBean));
                                }
                            });

                        }






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
