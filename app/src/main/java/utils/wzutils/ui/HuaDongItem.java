package utils.wzutils.ui;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

import utils.wzutils.common.LogTool;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * Created by ishare on 2016/9/18.
 * <p>
 * 左滑删除的功能
 */
public class HuaDongItem extends HorizontalScrollView {


    View leftView;
    View rightView;
    public static List<SoftReference<HuaDongItem>> huadongItems=new ArrayList<>();
    public HuaDongItem(Context context) {
        super(context);
        init();
    }

    public HuaDongItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HuaDongItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HuaDongItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getWidth() == 0) init();

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getWidth() > 0) init();
    }

    private void init() {
        try {

            setFillViewport(true);

            setHorizontalScrollBarEnabled(false);
            setSmoothScrollingEnabled(false);
            leftView = findViewWithTag("left");
            if (leftView != null) {
                leftView.getLayoutParams().width = getWidth() > 0 ? getWidth() : getResources().getDisplayMetrics().widthPixels;
                leftView.setLayoutParams(leftView.getLayoutParams());
            }
            rightView = findViewWithTag("right");
            huadongItems.add(new SoftReference<HuaDongItem>(this));
        } catch (Exception e) {
            LogTool.ex(e);
        }


    }
    /***
     *  打开  带动画
     */
    public void open() {
        startAnimation(getScrollX(), rightView.getWidth());
    }

    /***
     *  关闭  带动画
     */
    public void close() {
        startAnimation(getScrollX(), 0);
    }

    /***
     *  关闭  不带动画
     */
    public void closeWithNoAnim(){
        scrollTo(0,0);
    }
    public void startAnimation(int scrollFrom, int scrollTo) {
        try {
            clearAnimation();
            ValueAnimator valueAnimator = new ObjectAnimator();
            valueAnimator.setTarget(this);//不要这一句 在4.4 红米上报错
            valueAnimator.setDuration(200);
            valueAnimator.setIntValues(scrollFrom, scrollTo);
            valueAnimator.setInterpolator(new DecelerateInterpolator(1));
            valueAnimator.setEvaluator(new IntEvaluator() {
                @Override
                public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
                    return startValue + (int) (fraction * (endValue - startValue));
                }
            });
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    int value = (int) animation.getAnimatedValue();
                    scrollTo(value, 0);
                }
            });
            valueAnimator.start();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (rightView != null) {
            if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
                if (getScrollX() > rightView.getWidth() / 2) {
                    open();
                } else {
                    close();
                }
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if(ev.getAction()==MotionEvent.ACTION_DOWN){//只要点击其中一个， 就把其他的关闭掉
            closeAll(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    /***
     * 关闭所有 已经打开的 ，除了  huaDongItemNotClose
     */
    public static void closeAll(HuaDongItem huaDongItemNotClose) {
        for(SoftReference<HuaDongItem> huaDongItemSoftReference:huadongItems){
            HuaDongItem huaDongItem=huaDongItemSoftReference.get();
            if(huaDongItem!=null){
                if(huaDongItemNotClose==null){
                    huaDongItem.close();
                }else if(!huaDongItemNotClose.equals(huaDongItem)){
                    huaDongItem.close();
                }
            }
        }
    }

    @Override
    public void fling(int velocityX) {
        //super.fling(9999);
    }

    public void setLeftClickListener(WzViewOnclickListener wzViewOnclickListener) {
        if(getLeftView()!=null)leftView.setOnClickListener(wzViewOnclickListener);
    }

    public View getLeftView() {
        if(leftView==null)leftView=findViewWithTag("left");
        return leftView;
    }

    public View getRightView() {
        if(rightView==null)rightView=findViewWithTag("right");
        return rightView;
    }

}
