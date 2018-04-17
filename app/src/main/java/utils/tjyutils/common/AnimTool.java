package utils.tjyutils.common;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cp.R;

import utils.wzutils.AppTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.UiTool;

public class AnimTool {

    public static AnimationSet startAnim(View view,boolean add){
        AlphaAnimation alphaAnimation=null;


        int[] location = new int[2];
        location[0]= (int) view.getX();
        location[1]= (int) view.getY();
        int fromX=location[0];
        int toX=fromX;
        int fromY=0;
        int toY=0;

        if(add){
             fromY=location[1]- CommonTool.dip2px(50);
             toY=location[1];
            alphaAnimation=new AlphaAnimation(1,0);
        }else {
            fromY=location[1];
            toY=fromY- CommonTool.dip2px(50);
            alphaAnimation=new AlphaAnimation(1,0);
        }




        AnimationSet animationSet=new AnimationSet(true);
        animationSet.setDuration(1000);
        TranslateAnimation translateAnimation=new TranslateAnimation(fromX,toX,fromY,toY);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        view.startAnimation(animationSet);
        return animationSet;
    }

    public static void startAnim(final ViewGroup parent, final TextView anchorView, double oldMoney, final double newMoney) {

        boolean add=newMoney-oldMoney>0;

        int color=add?R.color.tv_hongse:R.color.tv_lvse;

        int[]location=new int[2];
        int[]locationParent=new int[2];
        anchorView.getLocationInWindow(location);
        parent.getLocationInWindow(locationParent);

        final TextView textView=new TextView(parent.getContext());
        UiTool.setTextColor(textView,color);
        double cha=newMoney-oldMoney;
        UiTool.setTextView(textView,(cha>0?"+":"")+Common.getPriceYB(cha));

        RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin=location[0];
        lp.topMargin=location[1]-locationParent[1];

        parent.addView(textView,lp);

        startAnim(textView,add).setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AppTool.uiHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            parent.removeView(textView);
                            UiTool.setTextView(anchorView, "" + Common.getPriceYB(newMoney));
                        }catch (Exception e){
                            LogTool.ex(e);
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }
}
