package com.cp.shouye;

import android.os.Bundle;
import android.view.View;

import com.cp.R;
import com.cp.cp.Data_wallet_remain;
import com.cp.im.IMTool;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.TestData;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;

/**
 * Created by kk on 2017/3/23.
 */

public class FangJianXuanZeDengJiFragment extends ParentFragment {

    Data_room_queryGame.YouXiEnum  youxi;

    WzSimpleRecycleView recycleView_fangjian_dengji;

    @Override
    public int initContentViewId() {
        return R.layout.fangjian_dengji;
    }
    @Override
    public void initData() {
        youxi= (Data_room_queryGame.YouXiEnum) getArgument("youxi", Data_room_queryGame.YouXiEnum.BJ28);
        titleTool.setTitle(youxi.name);
        titleTool.showService();
        loadData();
    }


    public void loadData(){
        Data_room_queryGame.load(youxi, new HttpUiCallBack<Data_room_queryGame>() {
            @Override
            public void onSuccess(Data_room_queryGame data) {
                if(data.isDataOkAndToast()){
                    initListView(data);
                }
            }
        });

    }

    public void initListView(final Data_room_queryGame data){

        recycleView_fangjian_dengji.setData(data.info.roomLevels, R.layout.fangjian_dengji_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);



                Data_room_queryGame.InfoBean.RoomLevelsBean roomLevelsBean=data.info.roomLevels.get(position);

                Data_room_queryGame.YouXiEnum youXiEnum= Data_room_queryGame.YouXiEnum.valueOf(roomLevelsBean.game);
                if(position==0){
                    roomLevelsBean.bg=youXiEnum.getImgBgChu();
                }else if(position==1){
                    roomLevelsBean.bg=youXiEnum.getImgBgZhong();
                }else if(position==2){
                    roomLevelsBean.bg=youXiEnum.getImgBgGao();
                }


                initFangJian(itemView,roomLevelsBean,data);

            }
        });
    }



    public void initFangJian(View itemView, final Data_room_queryGame.InfoBean.RoomLevelsBean level1Rooms, final Data_room_queryGame game){
        View fangjian_peilv=itemView.findViewById(R.id.fangjian_peilv);
        loadImage(level1Rooms.bg,itemView,R.id.fangjian_dengji_img);
        setTextView(itemView,R.id.fangjian_dengji_name,level1Rooms.name);
        setTextView(itemView,R.id.fangjian_dengji_desc,"("+level1Rooms.explain+")");
        setTextView(itemView,R.id.fangjian_dengji_num,level1Rooms.total);
        fangjian_peilv.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                level1Rooms.goPeiLv();
            }
        });

        itemView.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {

                showWaitingDialog("");
                Data_wallet_remain.load(new HttpUiCallBack<Data_wallet_remain>() {
                    @Override
                    public void onSuccess(Data_wallet_remain data) {
                        hideWaitingDialog();
                        if(data.isDataOkAndToast()){
                            if(data.remain>=level1Rooms.condition){ //进入限制判断
                                FangJianXuanZeFangJianFragment.byData(level1Rooms).go();
                            }else {
                                CommonTool.showToast("您的账户余额不足"+level1Rooms.condition+"，请充值");
                            }

                        }

                    }
                });

            }
        });

    }




    @Override
    public void initListener() {

    }


    public static FangJianXuanZeDengJiFragment byData(Data_room_queryGame.YouXiEnum youXiEnum) {
        FangJianXuanZeDengJiFragment fangJianXuanZeDengJiFragment=new FangJianXuanZeDengJiFragment();
        Bundle bundle=new Bundle();
        bundle.putSerializable("youxi",youXiEnum);
        fangJianXuanZeDengJiFragment.setArguments(bundle);
        return fangJianXuanZeDengJiFragment;
    }
}
