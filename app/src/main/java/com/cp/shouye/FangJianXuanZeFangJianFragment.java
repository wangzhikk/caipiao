package com.cp.shouye;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.cp.R;
import com.cp.cp.Data_wallet_remain;
import com.cp.touzhu.TouZhuFragment;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.TestData;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;

/**
 * Created by kk on 2017/3/23.
 */

public class FangJianXuanZeFangJianFragment extends ParentFragment {

    WzSimpleRecycleView recycleView_fangjian;
    Data_room_queryGame.InfoBean.RoomLevelsBean roomsBean;
    @Override
    public int initContentViewId() {
        return R.layout.fangjian_fangjianhao;
    }
    @Override
    public void initData() {
        roomsBean= (Data_room_queryGame.InfoBean.RoomLevelsBean) getArgument("roomsBean",new Data_room_queryGame.InfoBean.RoomLevelsBean());
        titleTool.setTitle("房间列表");
        titleTool.showService();
        initListView();
    }

    @Override
    public void initListener() {


    }

    public void initListView(){
        recycleView_fangjian.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recycleView_fangjian.setData(roomsBean.roomList, R.layout.fangjian_fangjianhao_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(final int position, int type, View itemView) {
                super.initData(position, type, itemView);
                final Data_room_queryGame.InfoBean.RoomLevelsBean.RoomListBean roomListBean = roomsBean.roomList.get(position);
                loadImage(roomListBean.image,itemView,R.id.room_imgv);
                setTextView(itemView,R.id.fangjian_name,roomListBean.name);
                setTextView(itemView,R.id.fangjian_num,"在线"+roomListBean.total+"人");
                itemView.setOnClickListener(new WzViewOnclickListener() {
                    @Override
                    public void onClickWz(View v) {
                        TouZhuFragment.byData(roomsBean,roomListBean).go();
                    }
                });

            }
        });
    }

    public static FangJianXuanZeFangJianFragment byData(Data_room_queryGame.InfoBean.RoomLevelsBean roomsBean) {
        Bundle bundle=new Bundle();
        bundle.putSerializable("roomsBean",roomsBean);
        FangJianXuanZeFangJianFragment fangJianXuanZeFangJianFragment=new FangJianXuanZeFangJianFragment();
        fangJianXuanZeFangJianFragment.setArguments(bundle);
        return fangJianXuanZeFangJianFragment;
    }
}
