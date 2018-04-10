package com.cp.touzhu;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.cp.R;
import com.cp.shouye.Data_room_queryGame;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import utils.tjyutils.common.Common;
import utils.tjyutils.common.TimeTypeChoose;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.AppTool;
import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.TestData;
import utils.wzutils.common.TimeTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.dialog.DateTimeDialog;
import utils.wzutils.ui.dialog.DialogTool;
import utils.wzutils.ui.pullrefresh.PageControl;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;
import utils.wzutils.ui.textview.HtmlTool;

/**
 * Created by kk on 2017/3/23.
 */

public class TouZhuJiLuFragment extends ParentFragment implements WzRefreshLayout.LoadListDataInterface {

    WzRefreshLayout wzRefreshLayout;
    WzSimpleRecycleView recycleView;
    String lottery;
    String roomNo;

    View vg_touzhu_jilu_shaixuan,vg_touzhu_jilu_choose_type;
    TextView tv_touzhu_jilu_choose_type,tv_touzhu_jilu_choose_time_start, tv_touzhu_jilu_choose_time_end;

    @Override
    public int initContentViewId() {
        return R.layout.touzhu_jilu;
    }

    @Override
    public void initData() {
        titleTool.setTitle("投注记录");

        lottery = "" + getArgument("lottery", lottery);
        roomNo = "" + getArgument("roomNo", roomNo);

        wzRefreshLayout.bindLoadData(this, pageControl);


        if (StringTool.isEmpty(lottery)) {//没有传入  游戏， 就显示选择
            vg_touzhu_jilu_shaixuan.setVisibility(View.VISIBLE);
            initTitleChoose();
        } else {
            vg_touzhu_jilu_shaixuan.setVisibility(View.GONE);
        }

    }

    public void initTitleChoose() {
        TimeTypeChoose timeTypeChoose=new TimeTypeChoose();

        List<TimeTypeChoose.TypeData> list=new ArrayList<>();
        list.add(new TimeTypeChoose.TypeData("全部",""));
        list.add(new TimeTypeChoose.TypeData(Data_room_queryGame.YouXiEnum.BJ28.name, Data_room_queryGame.YouXiEnum.BJ28.value));
        list.add(new TimeTypeChoose.TypeData(Data_room_queryGame.YouXiEnum.CQSSC_CX.name, Data_room_queryGame.YouXiEnum.CQSSC_CX.value));

        timeTypeChoose.init(parent, list, new TimeTypeChoose.OnChooseTimeTypeListener() {
            @Override
            public void onChoose(TimeTypeChoose.TypeData typeData, String timeStart, String timeEnd) {
                lottery= ""+typeData.value;
                wzRefreshLayout.autoRefresh();
            }
        });

    }


    PageControl<Data_game_record.GameRecordListBean.GameRecordBean> pageControl = new PageControl();

    public void loadData(final int page) {
        Data_game_record.load(page, pageControl.getPageSize(), lottery, roomNo, tv_touzhu_jilu_choose_time_start.getText().toString(), tv_touzhu_jilu_choose_time_end.getText().toString(), new HttpUiCallBack<Data_game_record>() {
            @Override
            public void onSuccess(Data_game_record data) {
                if (data.isDataOkAndToast()) {
                    pageControl.setCurrPageNum(page, data.pagingList.resultList);
                    initListView(pageControl.getAllDatas());
                }
                wzRefreshLayout.stopRefresh(pageControl);

            }
        });
    }

    public void initListView(final List<Data_game_record.GameRecordListBean.GameRecordBean> resultList) {
        recycleView.setDividerNormal(10);
        recycleView.setData(resultList, R.layout.touzhu_jilu_item, new WzSimpleRecycleView.WzRecycleAdapter() {
            @Override
            public void initData(int position, int type, View itemView) {
                super.initData(position, type, itemView);
                Data_game_record.GameRecordListBean.GameRecordBean gameRecordBean = resultList.get(position);

                setTextView(itemView, R.id.tv_touzhu_jilu_item_title, gameRecordBean.lottery_name + "-" + gameRecordBean.order_issue + "期");
                setTextView(itemView, R.id.tv_touzhu_jilu_time, gameRecordBean.order_time);

                TextView tv_touzhu_jilu_item_result = itemView.findViewById(R.id.tv_touzhu_jilu_item_result);
                HtmlTool.setHtmlText(tv_touzhu_jilu_item_result, "开奖号码：" + Data_cqssc_top10.CQSSCBean.getShortResultStr(gameRecordBean.order_lottery_num));
                //setTextView(itemView,R.id.tv_touzhu_jilu_item_result,"开奖号码："+ Data_cqssc_top10.CQSSCBean.getShowShortStr(gameRecordBean.order_lottery_num));
                setTextView(itemView, R.id.tv_touzhu_jilu_item_type, "开奖类型：" + gameRecordBean.order_lottery_type);
                setTextView(itemView, R.id.tv_touzhu_jilu_item_touzhu_type, "投注类型：" + gameRecordBean.order_betting_type);
                setTextView(itemView, R.id.tv_touzhu_jilu_item_touzhu_jine, "投注金额：" + Common.getPriceYB(gameRecordBean.order_amount));
                setTextView(itemView, R.id.tv_touzhu_jilu_item_zhongjiang_jine, "中奖金额：" + Common.getPriceYB(gameRecordBean.order_bonus));
                setTextView(itemView, R.id.tv_touzhu_jilu_item_dingdanhao, "订单编号：" + gameRecordBean.order_serial);


                itemView.findViewById(R.id.imgv_touzhu_jilu_item_zhongjiang).setVisibility(View.GONE);//中奖盖章

                {//金额
                    TextView tv_touzhu_jilu_item_jine = itemView.findViewById(R.id.tv_touzhu_jilu_item_jine);
                    double jine = gameRecordBean.order_bonus - gameRecordBean.order_amount;
                    if (StringTool.notEmpty(gameRecordBean.order_lottery_num)) {//已经开奖了的
                        setTextView(tv_touzhu_jilu_item_jine, (jine > 0 ? "+" : "") + Common.getPriceYB(jine));
                        if (jine > 0) {
                            itemView.findViewById(R.id.imgv_touzhu_jilu_item_zhongjiang).setVisibility(View.VISIBLE);
                            UiTool.setTextColor(tv_touzhu_jilu_item_jine, R.color.tv_hongse);
                        } else {
                            UiTool.setTextColor(tv_touzhu_jilu_item_jine, R.color.tv_lvse_sheng);
                        }
                    } else {
                        UiTool.setTextColor(tv_touzhu_jilu_item_jine, R.color.tv_hongse);
                        setTextView(tv_touzhu_jilu_item_jine, "未开奖");

                        setTextView(itemView, R.id.tv_touzhu_jilu_item_type, "开奖类型：未开奖");
                    }

                }
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


    public static ParentFragment byData(String lottery, String roomNo) {
        Bundle bundle = new Bundle();
        bundle.putString("lottery", lottery);
        bundle.putString("roomNo", roomNo);
        TouZhuJiLuFragment touZhuJiLuFragment = new TouZhuJiLuFragment();
        touZhuJiLuFragment.setArguments(bundle);
        return touZhuJiLuFragment;
    }
}
