package com.cp.wode.choujiang;

import android.app.Dialog;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.cp.R;

import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.ZhuanPanView;
import utils.wzutils.ui.dialog.DialogTool;

/**
 * Created by kk on 2017/3/23.
 */

public class ChouJiangFragment extends ParentFragment {
    ZhuanPanView zhuanPan;
    @Override
    public int initContentViewId() {
        return R.layout.choujiang;
    }


    int star=1;//1到5星
    int []rotation=new int[]{-165,-195,-45,-135,-225};
    int [] result=new int[]{R.drawable.zhuanpan_result_01,R.drawable.zhuanpan_result_02,R.drawable.zhuanpan_result_03,R.drawable.zhuanpan_result_04,R.drawable.zhuanpan_result_05,};

    @Override
    public void initData() {

        titleTool.setTitle("幸运大转盘");
        zhuanPan.setResId(R.drawable.zhuanpan_img,R.drawable.zhuanpan_btn);

        titleTool.setTitleRightTv("抽奖记录", new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new ChouJiangJiLuFragment().go();
            }
        });


        zhuanPan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data_luck_remain.load(new HttpUiCallBack<Data_luck_remain>() {
                    @Override
                    public void onSuccess(Data_luck_remain data) {
                        if(data.isDataOkAndToast()){
                            LogTool.s("剩余抽奖次数："+data.remain);
                        }
                    }
                });
                showWaitingDialog("");
                Data_luck_draw.load(new HttpUiCallBack<Data_luck_draw>() {
                    @Override
                    public void onSuccess(Data_luck_draw data) {
                        hideWaitingDialog();
                        if(data.isDataOkAndToast()){
                            startZhuanPan(data.grade,data.amount);
                        }
                    }
                });
            }
        });

    }
    public void startZhuanPan(int grade, final String amount){
        star=grade;

        if(star<1)star=1;
        if(star>5)star=5;
        zhuanPan.animToAngle(rotation[star-1], new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }
            @Override
            public void onAnimationEnd(Animation animation) {
                showZhongJiangDialog(star,amount);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
    public void showZhongJiangDialog(int indexIn, String amount){
        int index=indexIn-1;
        if(index<0)index=0;
        if(index>4)index=4;


        View view= LayoutInflaterTool.getInflater(3,R.layout.choujiang_dialog).inflate();
        final Dialog dialog=DialogTool.initBottomDialog(view);

        ImageView imgv_zhuanpan_dialog=view.findViewById(R.id.imgv_zhuanpan_dialog);

        imgv_zhuanpan_dialog.setImageResource(result[index]);

        setTextView(view,R.id.tv_choujiang_dialog_amount,amount);
        view.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void setOnResume() {
        super.setOnResume();
    }

    @Override
    public void initListener() {


    }


}
