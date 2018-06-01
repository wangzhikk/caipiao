package com.cp.touzhu.zoushitu;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cp.R;
import com.cp.shouye.Data_room_queryGame;
import com.cp.touzhu.Data_cqssc_query;
import com.cp.touzhu.Data_cqssc_top10;

import java.util.List;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.pullrefresh.PageControl;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

/**
 * Created by kk on 2017/3/23.
 */

public class ZouShiTu_bj28_Fragment extends ParentFragment implements WzRefreshLayout.LoadListDataInterface {

    WzRefreshLayout wzRefreshLayout;
    WzSimpleRecycleView recycleView;
    Data_room_queryGame.YouXiEnum youXiEnum;
    @Override
    public int initContentViewId() {
        return R.layout.zoushitu_bj28_list;
    }
    @Override
    public void initData() {
         youXiEnum= (Data_room_queryGame.YouXiEnum) getArgument("youXiEnum", Data_room_queryGame.YouXiEnum.BJ28);
        titleTool.setTitle("走势图");
        wzRefreshLayout.bindLoadData(this,pageControl);
    }
    PageControl<Data_cqssc_top10.CQSSCBean> pageControl=new PageControl();
    public void loadData(final int page){
        //initListView(null);
        Data_cqssc_query.load(youXiEnum,page, pageControl.getPageSize(), new HttpUiCallBack<Data_cqssc_query>() {
            @Override
            public void onSuccess(Data_cqssc_query data) {
                if(data.isDataOkAndToast()){
                    pageControl.setCurrPageNum(page,data.pagingList.resultList);
                    initListView(pageControl.getAllDatas());
                }
                wzRefreshLayout.stopRefresh(pageControl);
            }
        });
    }
    public void initListView(final List<Data_cqssc_top10.CQSSCBean> resultList){
        recycleView.setData(resultList, R.layout.zoushitu_bj28_list_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                if(position%2==0){
                    itemView.setBackgroundResource(R.color.bg_sheng);
                }else {
                    itemView.setBackgroundResource(R.color.white);
                }
                Data_cqssc_top10.CQSSCBean cqsscBean=resultList.get(position);

                setTextView(itemView,R.id.tv_zoushi_issue,""+cqsscBean.lottery_issue);
                int result=cqsscBean.getResult();
                setTextView(itemView,R.id.tv_zoushi_num,""+result);


                TextView tv_zoushi_da=itemView.findViewById(R.id.tv_zoushi_da);
                TextView tv_zoushi_xiao=itemView.findViewById(R.id.tv_zoushi_xiao);
                TextView tv_zoushi_dan=itemView.findViewById(R.id.tv_zoushi_dan);
                TextView tv_zoushi_shuang=itemView.findViewById(R.id.tv_zoushi_shuang);

                TextView tv_zoushi_dadan=itemView.findViewById(R.id.tv_zoushi_dadan);
                TextView tv_zoushi_xiaodan=itemView.findViewById(R.id.tv_zoushi_xiaodan);
                TextView tv_zoushi_dashuang=itemView.findViewById(R.id.tv_zoushi_dashuang);
                TextView tv_zoushi_xiaoshuang=itemView.findViewById(R.id.tv_zoushi_xiaoshuang);


                tv_zoushi_da.setVisibility(result>13?View.VISIBLE:View.INVISIBLE);
                tv_zoushi_xiao.setVisibility(result<=13?View.VISIBLE:View.INVISIBLE);
                tv_zoushi_dan.setVisibility(result%2!=0?View.VISIBLE:View.INVISIBLE);
                tv_zoushi_shuang.setVisibility(result%2==0?View.VISIBLE:View.INVISIBLE);

                tv_zoushi_dadan.setVisibility(result>13&&result%2!=0?View.VISIBLE:View.INVISIBLE);
                tv_zoushi_xiaodan.setVisibility(result<=13&&result%2!=0?View.VISIBLE:View.INVISIBLE);
                tv_zoushi_dashuang.setVisibility(result>13&&result%2==0?View.VISIBLE:View.INVISIBLE);
                tv_zoushi_xiaoshuang.setVisibility(result<=13&&result%2==0?View.VISIBLE:View.INVISIBLE);


            }
        });
    }
    @Override
    public void setOnResume() {
        super.setOnResume();
    }

    @Override
    public void initListener() {


    }


    public static ParentFragment byData(Data_room_queryGame.YouXiEnum youXiEnum){
        Bundle bundle=new Bundle();
        bundle.putSerializable("youXiEnum",youXiEnum);
        ZouShiTu_bj28_Fragment zouShiTu_bj28_fragment=new ZouShiTu_bj28_Fragment();
        zouShiTu_bj28_fragment.setArguments(bundle);
        return zouShiTu_bj28_fragment;
    }
}
