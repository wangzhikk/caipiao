package com.cp.touzhu.zoushitu;

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

public class ZouShiTu_cqssc_Fragment extends ParentFragment implements WzRefreshLayout.LoadListDataInterface {

    WzRefreshLayout wzRefreshLayout;
    WzSimpleRecycleView recycleView;
    @Override
    public int initContentViewId() {
        return R.layout.zoushitu_cqssc_list;
    }
    @Override
    public void initData() {
        titleTool.setTitle("走势图");
        wzRefreshLayout.bindLoadData(this,pageControl);
    }
    PageControl<Data_cqssc_top10.CQSSCBean> pageControl=new PageControl();
    public void loadData(final int page){
        //initListView(null);
        Data_cqssc_query.load(Data_room_queryGame.YouXiEnum.CQSSC_CX,page, pageControl.getPageSize(), new HttpUiCallBack<Data_cqssc_query>() {
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
        recycleView.setData(resultList, R.layout.zoushitu_cqssc_list_item, new WzSimpleRecycleView.WzRecycleAdapter() {
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


                TextView tv_zoushi_xiao=itemView.findViewById(R.id.tv_zoushi_xiao);
                TextView tv_zoushi_da=itemView.findViewById(R.id.tv_zoushi_da);
                TextView tv_zoushi_dan=itemView.findViewById(R.id.tv_zoushi_dan);
                TextView tv_zoushi_shuang=itemView.findViewById(R.id.tv_zoushi_shuang);

                tv_zoushi_xiao.setVisibility(result<10?View.VISIBLE:View.INVISIBLE);
               // tv_zoushi_zhong.setVisibility(result>6&&result<12?View.VISIBLE:View.INVISIBLE);
                tv_zoushi_da.setVisibility(result>9?View.VISIBLE:View.INVISIBLE);

                tv_zoushi_dan.setVisibility(result%2!=0?View.VISIBLE:View.INVISIBLE);
                tv_zoushi_shuang.setVisibility(result%2==0?View.VISIBLE:View.INVISIBLE);



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


}
