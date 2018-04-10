package utils.wzutils.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import utils.wzutils.common.LogTool;

public class ZhuanPanView extends RelativeLayout {
    public ZhuanPanView(Context context) {
        super(context);
        init();
    }

    public ZhuanPanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZhuanPanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }
    public ImageView imageViewBg=new ImageView(getContext());
    public ImageView imageViewCenter=new ImageView(getContext());
    public void init(){
        imageViewBg.setBackgroundColor(Color.RED);
        addView(imageViewBg,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);

        imageViewCenter.setImageResource(android.R.drawable.btn_star);
        LayoutParams layoutParams=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(imageViewCenter,layoutParams);

        viewRotation=imageViewBg;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                animToAngle(100,null);
            }
        });
    }
    public void setResId(int resIdBg,int resIdCenter){
        imageViewBg.setBackgroundColor(0);
        imageViewBg.setImageResource(resIdBg);
        imageViewCenter.setImageResource(resIdCenter);
    }
    View viewRotation;
    boolean isAnimating=false;
    public void animToAngle(final int angle, final Animation.AnimationListener animationListener){
        if(isAnimating==true){
            LogTool.s("动画中，待会再点");
            return;
        }
        RotateAnimation rotate  = new RotateAnimation((float) (viewRotation.getRotation()*180/Math.PI), 360*10+angle, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setInterpolator(new AccelerateDecelerateInterpolator(){
            @Override
            public float getInterpolation(float input) {
                return (float)(Math.cos((input + 1) * Math.PI) / 2.0f) + 0.5f;
            }
        });
        rotate.setDuration(5000);//设置动画持续周期
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewRotation.setRotation((float) (angle*Math.PI/180));
                isAnimating=false;
                if(animationListener!=null){
                    animationListener.onAnimationEnd(animation);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        viewRotation.startAnimation(rotate);
        isAnimating=true;

    }
}
