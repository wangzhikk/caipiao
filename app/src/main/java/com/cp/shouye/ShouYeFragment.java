package com.cp.shouye;

import android.app.Dialog;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ViewFlipper;


import com.cp.KeFu.KeFuFragment;
import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.cp.im.IMTool;
import com.cp.wode.choujiang.ChouJiangFragment;
import com.cp.wode.qianbao.chongzhi.ChongZhiFragment;
import com.cp.wode.qianbao.tixian.TiXianFragment;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import utils.tjyutils.common.Common;
import utils.tjyutils.common.WebFragment;
import utils.tjyutils.http.HttpConfigAx;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.AppTool;
import utils.wzutils.common.BroadcastReceiverTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.TestData;
import utils.wzutils.common.TimeTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.dialog.DialogTool;
import utils.wzutils.ui.lunbo.LunBoTool;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class ShouYeFragment extends ParentFragment {

   WzRefreshLayout wzRefresh;
    WzSimpleRecycleView recycleView_wanfa;
    View btn_zhuanpan;
    ViewFlipper viewFlipper;
    View btn_shouye_more,btn_shouye_kefu;
    @Override
    public int initContentViewId() {
        return R.layout.shouye;
    }
    @Override
    public void initData() {

      //  wzRefreshImp.setScrollView(scrollView_shouye);

        wzRefresh.setEnableLoadMore(false);
        wzRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadData();
            }
        });
        loadData();
        initListView();
        Data_login_validate.bindLoginSuccess(getActivity(), new BroadcastReceiverTool.BroadCastWork() {
            @Override
            public void run() {
                wzRefresh.autoRefresh();
            }
        });


        initViewFlipper();


        initTitle();

    }
    public void initTitle(){
        btn_shouye_more.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                View view = LayoutInflaterTool.getInflater(3, R.layout.shouye_dialog_more).inflate();
                final Dialog dialog = new Dialog(AppTool.currActivity, R.style.dialog);
                dialog.setContentView(view);
                DialogTool.showAsDropDown(dialog,btn_shouye_more, 0, 15);
                final View btn_shouye_chongzhi = view.findViewById(R.id.btn_shouye_chongzhi);
                btn_shouye_chongzhi.setOnClickListener(new WzViewOnclickListener() {
                    @Override
                    public void onClickWz(View v) {
                        new ChongZhiFragment().go();
                        dialog.dismiss();
                    }
                });
                view.findViewById(R.id.btn_shouye_tixian).setOnClickListener(new WzViewOnclickListener() {
                    @Override
                    public void onClickWz(View v) {
                        new TiXianFragment().go();
                        dialog.dismiss();
                    }
                });
            }
        });

        bindFragmentBtn(btn_shouye_kefu,new KeFuFragment());
    }
    public void initViewFlipper(){
        viewFlipper.postDelayed(new Runnable() {
            @Override
            public void run() {
               loadGunDong();
               viewFlipper.postDelayed(this,20000);
            }
        },20000);

        viewFlipper.setInAnimation(getContext(),R.anim.in_bottomtop);//设置View进入屏幕时候使用的动画
        viewFlipper.setOutAnimation(getContext(),R.anim.out_topbottom);  //设置View退出屏幕时候使用的动画
        viewFlipper.setFlipInterval(5000);//设置自动切换的间隔时间
        viewFlipper.startFlipping();//开启切换效果
        loadGunDong();
    }
    public void loadGunDong(){
        Data_index_dynamic.load(new HttpUiCallBack<Data_index_dynamic>() {
            @Override
            public void onSuccess(Data_index_dynamic data) {
                if(data.isDataOkWithOutCheckLogin()){


                    if(data.dynamic.size()<=viewFlipper.getChildCount()){

                        for(int i=0;i<data.dynamic.size();i++){
                            Data_index_dynamic.DynamicBean dynamicBean=data.dynamic.get(i);
                            View view= viewFlipper.getChildAt(i);

                            initGunDongItem(view,dynamicBean);
                        }
                    }else {
                        viewFlipper.removeAllViews();
                        for(Data_index_dynamic.DynamicBean dynamicBean: data.dynamic){
                            View view= LayoutInflaterTool.getInflater(10,R.layout.shouye_gundong).inflate();
                            viewFlipper.addView(view);
                           initGunDongItem(view,dynamicBean);
                        }
                    }

                }
            }
        });
    }
    public void initGunDongItem(View view, Data_index_dynamic.DynamicBean dynamicBean){
        setTextView(view,R.id.tv_shouye_gundong_name,dynamicBean.nickname);
        String des= TimeTool.getAgoTime(dynamicBean.timestamp)+" 投注了"+dynamicBean.lottery;
        setTextView(view,R.id.tv_shouye_gundong_des,des);
        setTextView(view,R.id.tv_shouye_gundong_jine, Common.getPriceYB(dynamicBean.amount));
    }







    Data_index_query data_index_query=new Data_index_query();
    public void loadData(){
        Data_index_query.load(new HttpUiCallBack<Data_index_query>() {
            @Override
            public void onSuccess(Data_index_query data) {
                wzRefresh.finishRefresh();
                if(data.isDataOkAndToast()){
                    data_index_query=data;
                    initYiZhuan(data);
                    initLunBo(data.banners);
                    initListView();
                }
            }
        });
    }

    /**
     * 用户已赚多少哪里
     */
    public void initYiZhuan(Data_index_query data_index_query){
        setTextView(parent,R.id.shouye_yizhuan_tv ,Common.getPriceYB(data_index_query.totalProfit));
        setTextView(parent,R.id.shouye_touzhu_tv ,""+data_index_query.totalUser);
        setTextView(parent,R.id.shouye_shenglv_tv ,""+data_index_query.profitRate);

    }

    @Override
    public void setOnResume() {
        super.setOnResume();
    }

    @Override
    public void initListener() {

        bindFragmentBtn(btn_zhuanpan,new ChouJiangFragment());
       // bindFragmentBtn(btn_zhuanpan,new KeFuFragment());

    }

    @Override
    public void onResume() {
        super.onResume();
        IMTool.getIntance().login();//登录

    }

    public void initLunBo(List<Data_index_query.BannersBean> banners){

        List <LunBoTool.LunBoData> lunBoDataList=new ArrayList<>();
        for(final Data_index_query.BannersBean bannersBean:banners){
            LunBoTool.LunBoData lunBoData=new LunBoTool.LunBoData(bannersBean.ads_images, new LunBoTool.LunBoClickListener() {
                @Override
                public void onClickLunBo(View v, LunBoTool.LunBoData lunBoData) {
                    bannersBean.onClick();
                }
            });
            lunBoDataList.add(lunBoData);
        }
        LunBoTool.initAds(parent, R.id.adsContainer, R.id.vg_viewpager_btn, R.layout.include_lunbo_dot_huise_touming, R.id.cb_dot, 7000, lunBoDataList);
    }

    public void initListView(){

        recycleView_wanfa.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recycleView_wanfa.setData(TestData.getTestStrList(2), R.layout.shouye_wanfa_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);

                View shouye_wanfa_shuoming=itemView.findViewById(R.id.shouye_wanfa_shuoming);
                Data_room_queryGame.YouXiEnum youXiEnum= Data_room_queryGame.YouXiEnum.BJ28;

                int margin= CommonTool.dip2px(10);
                String shuoMingUrl="";
                if(position==0){
                    loadImage(R.drawable.img_index_bj,itemView,R.id.shouye_wanfa_img);
                    youXiEnum= Data_room_queryGame.YouXiEnum.BJ28;
                    if(data_index_query.isDataOk())shuoMingUrl=HttpConfigAx.getHtmlUrl(data_index_query.games.BJ28.playHtmlSrc);

                }else if(position==1){
                    loadImage(R.drawable.img_index_cq,itemView,R.id.shouye_wanfa_img);
                    youXiEnum= Data_room_queryGame.YouXiEnum.CQSSC_CX;
                    if(data_index_query.isDataOk())shuoMingUrl=HttpConfigAx.getHtmlUrl(data_index_query.games.CQSSC_CX.playHtmlSrc);
                }
                bindFragmentBtn(shouye_wanfa_shuoming,WebFragment.byData(shuoMingUrl,"玩法说明"));

                final Data_room_queryGame.YouXiEnum finalYouXiEnum = youXiEnum;
                itemView.setOnClickListener(new WzViewOnclickListener() {
                    @Override
                    public void onClickWz(View v) {
                        FangJianXuanZeDengJiFragment.byData(finalYouXiEnum).go();
                    }
                });
            }
        });
    }

}
