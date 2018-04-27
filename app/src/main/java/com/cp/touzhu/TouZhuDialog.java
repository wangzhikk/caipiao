package com.cp.touzhu;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_cqssc_betting;
import com.cp.shouye.Data_room_queryGame;

import utils.tjyutils.common.Common;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.AppTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.TestData;
import utils.wzutils.common.UiTool;
import utils.wzutils.common.ViewTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.CommonButtonTool;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.WzViewPager;
import utils.wzutils.ui.dialog.DialogTool;
import utils.wzutils.ui.textview.HtmlTool;

/**
 * Created by kk on 2017/3/23.
 */

public class TouZhuDialog  {
    TouZhuFragment touZhuFragment;
    public TouZhuDialog (TouZhuFragment touZhuFragment){
        this.touZhuFragment=touZhuFragment;

    }
    CommonButtonTool commonButtonTool = new CommonButtonTool();
    final int select_key = ViewTool.initKey();
    final int parent_key = ViewTool.initKey();

    public void touzhu(final Dialog dialog,String  issue,Object amount,String type) {

        String roomNo = touZhuFragment.roomListBean.no;
        touZhuFragment.showWaitingDialog("");
        Data_cqssc_betting.load(Data_room_queryGame.YouXiEnum.valueOf(touZhuFragment.roomsBean.game),issue, roomNo, type, "" + amount, new HttpUiCallBack<Data_cqssc_betting>() {
            @Override
            public void onSuccess(Data_cqssc_betting data) {
                touZhuFragment.hideWaitingDialog();
                CommonTool.showToast(data.getMsg());
                if (data.isDataOkAndToast()) {
                    if (dialog != null) dialog.dismiss();
                    touZhuFragment.refreshYuE();
                }else {
                    if(data.code==801){
                        touZhuFragment.getActivityWz().finish();
                    }
                }
            }
        });
    }



    public void initTouZhuDiaolog(final Dialog dialog, final View view) {
        final EditText et_touzhu = view.findViewById(R.id.et_touzhu);


        {//投注按钮
            view.findViewById(R.id.btn_touzhu).setOnClickListener(new WzViewOnclickListener() {
                @Override
                public void onClickWz(View v) {

                    if(touZhuFragment.countDown==-2){
                        CommonTool.showToast("封盘中，请等待游戏开始");
                        return;
                    }else if(touZhuFragment.countDown==-3){
                        CommonTool.showToast("已停售，请等待游戏开始");
                        return;
                    }
                    String amountStr = et_touzhu.getText().toString().trim();
                    if (amountStr.length() < 1) {
                        CommonTool.showToast("请输入投注金额");
                        return;
                    }
                    double amount = Double.valueOf(amountStr);

                    if (amount < touZhuFragment.roomsBean.bettingMin) {
                        CommonTool.showToast("投注金额必须大于" + Common.getPriceYB(touZhuFragment.roomsBean.bettingMin));
                        return;
                    }
                    if (amount > touZhuFragment.roomsBean.bettingMax) {
                        CommonTool.showToast("投注金额必须小于" + Common.getPriceYB(touZhuFragment.roomsBean.bettingMax));
                        return;
                    }
                    Data_room_queryGame.InfoBean.PlayMethodsBean.MethodsBean methodsBean = (Data_room_queryGame.InfoBean.PlayMethodsBean.MethodsBean) ViewTool.getTag(commonButtonTool.getChecked(), select_key);
                    touzhu(dialog,touZhuFragment.currIssue, amount,methodsBean.type);

                }
            });
        }


        view.findViewById(R.id.vg_parent_dialog_touzhu).setClickable(true); //设置可点击

        final TextView tv_touzhu_title=view.findViewById(R.id.tv_touzhu_title);

        final WzViewPager viewPagerTouZhu = view.findViewById(R.id.viewPagerTouZhu);
        commonButtonTool = new CommonButtonTool();
        viewPagerTouZhu.setOffscreenPageLimit(5);
        viewPagerTouZhu.setAdapter(new PagerAdapter() {
            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            //有多少个切换页
            @Override
            public int getCount() {
                try {
                    return touZhuFragment.dataRoomQueryGame.info.playMethods.size();

                } catch (Exception e) {
                    LogTool.ex(e);
                }
                return 0;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                if (object != null && object instanceof View) container.removeView((View) object);
            }

            @NonNull
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                final View itemViewPager = LayoutInflaterTool.getInflater(5, R.layout.touzhu_dialog_pager_item).inflate();
                initItemView(itemViewPager, position,false);
                container.addView(itemViewPager);
                return itemViewPager;
            }



        });
        ViewPager.OnPageChangeListener pageChangeListene=new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int color = 0;
                if (position == 0) {
                    color = R.color.bg_lanse;
                } else if (position == 1) {
                    color = R.color.bg_huangse;
                } else if (position == 2) {
                    color = R.color.bg_zise;
                }
                ((View) viewPagerTouZhu.getParent()).setBackgroundResource(color);
                initItemView(viewPagerTouZhu.getChildAt(position),position,true);
                touZhuFragment.setTextView(tv_touzhu_title, touZhuFragment.roomsBean.getGameData().info.playMethods.get(position).name);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        viewPagerTouZhu.addOnPageChangeListener(pageChangeListene);
        pageChangeListene.onPageSelected(0);

//        {//赔率说明
//            View btn_touzhu_peilv = view.findViewById(R.id.btn_touzhu_peilv);
//            btn_touzhu_peilv.setOnClickListener(new WzViewOnclickListener() {
//                @Override
//                public void onClickWz(View v) {
//                    touZhuFragment.roomsBean.goPeiLv();
//                }
//            });
//
//        }
        {//最小和双倍
//            TextView tv_touzhu_zuixiao=view.findViewById(R.id.tv_touzhu_zuixiao);
//            tv_touzhu_zuixiao.setOnClickListener(new WzViewOnclickListener() {
//                @Override
//                public void onClickWz(View v) {
//                    et_touzhu.setText(""+ touZhuFragment.roomsBean.bettingMin);
//                }
//            });
            TextView tv_touzhu_shuangbei=view.findViewById(R.id.tv_touzhu_shuangbei);
            tv_touzhu_shuangbei.setOnClickListener(new WzViewOnclickListener() {
                @Override
                public void onClickWz(View v) {
                    String numStr=et_touzhu.getText().toString().trim();
                    if(numStr.length()>0){
                        int old=Integer.valueOf(numStr);
                        int curr=old*2;
                        if(curr>touZhuFragment.roomsBean.bettingMax){
                            curr=touZhuFragment.roomsBean.bettingMax;
                        }
                        et_touzhu.setText(""+curr);
                    }
                }
            });

        }

        {//快速投注按钮
            String [] kuaisuList= touZhuFragment.roomsBean.fastBettingAmount.split(",");
            if(kuaisuList.length>0)initKuaiSuTouZhu(et_touzhu, (TextView) view.findViewById(R.id.touzhu_kuaisu_1),kuaisuList[0]);
            if(kuaisuList.length>1)initKuaiSuTouZhu(et_touzhu, (TextView) view.findViewById(R.id.touzhu_kuaisu_2),kuaisuList[1]);
            if(kuaisuList.length>2)initKuaiSuTouZhu(et_touzhu, (TextView) view.findViewById(R.id.touzhu_kuaisu_3),kuaisuList[2]);
            if(kuaisuList.length>3)initKuaiSuTouZhu(et_touzhu, (TextView) view.findViewById(R.id.touzhu_kuaisu_4),kuaisuList[3]);


        }
        {//左右滑动
            View btn_touzhu_pre = view.findViewById(R.id.btn_touzhu_pre);
            View btn_touzhu_next = view.findViewById(R.id.btn_touzhu_next);

            btn_touzhu_pre.setOnClickListener(new WzViewOnclickListener() {
                @Override
                public void onClickWz(View v) {
                    int size = viewPagerTouZhu.getAdapter().getCount();
                    int position = viewPagerTouZhu.getCurrentItem();

                    position--;
                    if (position < 0) {
                        position = size - 1;
                    }
                    viewPagerTouZhu.setCurrentItem(position);
                }
            });
            btn_touzhu_next.setOnClickListener(new WzViewOnclickListener() {
                @Override
                public void onClickWz(View v) {
                    int size = viewPagerTouZhu.getAdapter().getCount();
                    int position = viewPagerTouZhu.getCurrentItem();

                    position++;
                    if (position > size - 1) {
                        position = 0;
                    }
                    viewPagerTouZhu.setCurrentItem(position);
                }
            });
        }


    }

    public void initKuaiSuTouZhu(final EditText et_touzhu, TextView touzhu_kuaisu, final String num){
        UiTool.setTextView(touzhu_kuaisu,num);
        touzhu_kuaisu.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                et_touzhu.setText(num);
            }
        });

    }

    public void initItemView(final View itemViewPager, final int position, final boolean isSelected) {
        try {
            commonButtonTool.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Data_room_queryGame.InfoBean.PlayMethodsBean.MethodsBean methodsBean = (Data_room_queryGame.InfoBean.PlayMethodsBean.MethodsBean) ViewTool.getTag(buttonView, select_key);
                        TextView tv_touzhu_des = ((View) ViewTool.getTag(buttonView, parent_key)).findViewById(R.id.tv_touzhu_des);
                        UiTool.setTextView(tv_touzhu_des, methodsBean.desc);
                    }
                }
            });


            final Data_room_queryGame.InfoBean.PlayMethodsBean playMethodsBean =  touZhuFragment.dataRoomQueryGame.info.playMethods.get(position);
            WzSimpleRecycleView recycleView_touzhu = itemViewPager.findViewById(R.id.recycleView_touzhu);

            int col = 5;
            int size = playMethodsBean.methods.size();
            if (size == 2 || size == 4) {
                col = 2;
            } else if (size == 3) {
                col = 3;
            }
            recycleView_touzhu.setLayoutManager(new StaggeredGridLayoutManager(col, StaggeredGridLayoutManager.VERTICAL));
            recycleView_touzhu.setData(playMethodsBean.methods, R.layout.touzhu_dialog_pager_item_list_item, new WzSimpleRecycleView.WzRecycleAdapter() {
                @Override
                public void initData(int positionIn, int type, View itemView) {
                    super.initData(positionIn, type, itemView);
                    CompoundButton cb_touzhu_item = itemView.findViewById(R.id.cb_touzhu_item);

                    Data_room_queryGame.InfoBean.PlayMethodsBean.MethodsBean methodsBean = playMethodsBean.methods.get(positionIn);

                    UiTool.setTextView(cb_touzhu_item, methodsBean.type + "\n" + methodsBean.oddsRate);

                    ViewTool.setTag(cb_touzhu_item, methodsBean, select_key);
                    ViewTool.setTag(cb_touzhu_item, itemViewPager, parent_key);
                    commonButtonTool.add(cb_touzhu_item);

                    if(!isSelected&&position==0&&positionIn==0){//初始化的时候
                        commonButtonTool.setChecked(cb_touzhu_item);
                    }
                    if(isSelected){
                        if (positionIn == 0) {
                            commonButtonTool.setChecked(cb_touzhu_item);
                        }
                    }
                }
            });

        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static void bindClick(final TouZhuFragment touZhuFragment, View btn_touzhu) {
        btn_touzhu.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                View view = LayoutInflaterTool.getInflater(3, R.layout.touzhu_dialog).inflate();
                final Dialog dialog = DialogTool.initBottomDialog(view);
                new TouZhuDialog(touZhuFragment).initTouZhuDiaolog(dialog,view);
                dialog.show();
            }
        });
    }
}
