package utils.wzutils.ui.pullrefresh;

import android.animation.Animator;
import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import utils.wzutils.common.LogTool;

/***
 * Created by kk on 2016/5/19.
 * 注意：
 * 1： 默认 没有设置headerView  就没有header ， 也不能下拉， foot 同
 * 2;  如果需要不显示headerView  并且希望可以下拉 ， 可以添加一个 很高 的headerView 并且设置 不显示。。。 这样就可以下拉了， foot 同
 * 3： headerView 一定记得 多嵌套一层 layout 包裹住， 这样才能算出高来，最外层设置的高不管用。。。
 * 4:   initHeaderAndFooter 这个方法是用来初始化 wz_recycle_header  和footer 的，  需要自己覆盖重写的。。。。
 * 5:   暂时不要 作为最 外层的容器，  在内存低 重启的时候 会有显示不出来的bug ， 在最外层套一层就是了
 * 测试代码
 * <p>
 * final WzRefresh wzRefresh= (WzRefresh) findViewById(R.id.wzrefresh);
 * final View wz_recycle_header=getLayoutInflater().inflate(R.layout.wz_recycle_header,null);
 * final View footer=getLayoutInflater().inflate(R.layout.wz_recycle_header,null);
 * wzRefresh.setHeaderView(wz_recycle_header);
 * wzRefresh.setFooterView(footer);
 * final TextView tv_refresh_header= ((TextView)wz_recycle_header.findViewById(R.id.tv_refresh));
 * final TextView tv_refresh_footer= ((TextView)footer.findViewById(R.id.tv_refresh));
 * final ProgressBar progressBar_header= ((ProgressBar)wz_recycle_header.findViewById(R.id.progressBar));
 * final ProgressBar progressBar_footer= ((ProgressBar)footer.findViewById(R.id.progressBar));
 * progressBar_header.setVisibility(View.INVISIBLE);
 * progressBar_footer.setVisibility(View.INVISIBLE);
 * wzRefresh.setRefreshListener(new WzRefresh.RefreshListener() {
 *
 * @Override public void onRefreshPullDown() {
 * tv_refresh_header.setText("正在刷新");
 * progressBar_header.setVisibility(View.VISIBLE);
 * wzRefresh.postDelayed(new Runnable() {
 * @Override public void run() {
 * wzRefresh.stopRefresh();
 * progressBar_header.setVisibility(View.INVISIBLE);
 * }
 * },2000);
 * }
 * @Override public void onRefreshPullUp() {
 * tv_refresh_footer.setText("正在刷新");
 * progressBar_footer.setVisibility(View.VISIBLE);
 * wzRefresh.postDelayed(new Runnable() {
 * @Override public void run() {
 * wzRefresh.stopRefresh();
 * progressBar_footer.setVisibility(View.INVISIBLE);
 * }
 * },2000);
 * <p>
 * }
 * @Override public void onPullProgressChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
 * int distance=Math.abs(scrollY);
 * if(scrollY<0) {
 * if(wzRefresh.isReadyToRefresh()){
 * if(!"释放刷新".equals(tv_refresh_header.getText().toString()))tv_refresh_header.setText("释放刷新");
 * }else {
 * if(!"下拉刷新".equals(tv_refresh_header.getText().toString()))tv_refresh_header.setText("下拉刷新");
 * }
 * int max=wz_recycle_header.getHeight();
 * float progress=(distance*1.0f/max);
 * if(progress>1)progress=1;
 * wz_recycle_header.setScaleX(progress);
 * wz_recycle_header.setScaleY(progress);
 * wz_recycle_header.setAlpha(progress);
 * }
 * if(scrollY>0){
 * if(wzRefresh.isReadyToRefresh()){
 * if(!"释放刷新".equals(tv_refresh_footer.getText().toString()))tv_refresh_footer.setText("释放刷新");
 * }else {
 * if(!"上拉刷新".equals(tv_refresh_footer.getText().toString()))tv_refresh_footer.setText("上拉刷新");
 * }
 * <p>
 * int max=footer.getHeight();
 * float progress=(distance*1.0f/max);
 * if(progress>1)progress=1;
 * footer.setScaleX(progress);
 * footer.setScaleY(progress);
 * footer.setAlpha(progress);
 * }
 * }
 * <p>
 * });
 * <p>
 * <p>
 * <p>
 * <p>
 * footView 的布局， 主要注意高
 * <?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
xmlns:android="http://schemas.android.com/apk/res/android"
android:layout_width="match_parent"
android:layout_height="60dp"
>
<RelativeLayout
android:layout_width="match_parent"
android:layout_height="60dp">
<ProgressBar
style="@android:style/Widget.DeviceDefault.ProgressBar"
android:layout_width="30dp"
android:layout_height="30dp"
android:id="@+id/progressBar"
android:layout_gravity="center_vertical"
android:layout_marginRight="19dp"
android:layout_marginEnd="19dp"
android:layout_centerVertical="true"
android:layout_toLeftOf="@+id/tv_refresh"
android:layout_toStartOf="@+id/tv_refresh"/>
<TextView
android:id="@+id/tv_refresh"
android:text="下拉刷新"
android:textSize="14dp"
android:layout_centerInParent="true"
android:layout_width="60dp"
android:layout_height="20dp"/>

</RelativeLayout>


</RelativeLayout>

 */

/**** 测试代码
 *
 * final WzRefresh wzRefresh= (WzRefresh) findViewById(R.id.wzrefresh);
 final View wz_recycle_header=getLayoutInflater().inflate(R.layout.wz_recycle_header,null);
 final View footer=getLayoutInflater().inflate(R.layout.wz_recycle_header,null);
 wzRefresh.setHeaderView(wz_recycle_header);
 wzRefresh.setFooterView(footer);
 final TextView tv_refresh_header= ((TextView)wz_recycle_header.findViewById(R.id.tv_refresh));
 final TextView tv_refresh_footer= ((TextView)footer.findViewById(R.id.tv_refresh));
 final ProgressBar progressBar_header= ((ProgressBar)wz_recycle_header.findViewById(R.id.progressBar));
 final ProgressBar progressBar_footer= ((ProgressBar)footer.findViewById(R.id.progressBar));
 progressBar_header.setVisibility(View.INVISIBLE);
 progressBar_footer.setVisibility(View.INVISIBLE);
 wzRefresh.setRefreshListener(new WzRefresh.RefreshListener() {
@Override public void onRefreshPullDown() {
tv_refresh_header.setText("正在刷新");
progressBar_header.setVisibility(View.VISIBLE);
wzRefresh.postDelayed(new Runnable() {
@Override public void run() {
wzRefresh.stopRefresh();
progressBar_header.setVisibility(View.INVISIBLE);
}
},2000);
}

@Override public void onRefreshPullUp() {
tv_refresh_footer.setText("正在刷新");
progressBar_footer.setVisibility(View.VISIBLE);
wzRefresh.postDelayed(new Runnable() {
@Override public void run() {
wzRefresh.stopRefresh();
progressBar_footer.setVisibility(View.INVISIBLE);
}
},2000);

}
@Override public void onPullProgressChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
int distance=Math.abs(scrollY);
if(scrollY<0) {
if(wzRefresh.isReadyToRefresh()){
if(!"释放刷新".equals(tv_refresh_header.getText().toString()))tv_refresh_header.setText("释放刷新");
}else {
if(!"下拉刷新".equals(tv_refresh_header.getText().toString()))tv_refresh_header.setText("下拉刷新");
}
int max=wz_recycle_header.getHeight();
float progress=(distance*1.0f/max);
if(progress>1)progress=1;
wz_recycle_header.setScaleX(progress);
wz_recycle_header.setScaleY(progress);
wz_recycle_header.setAlpha(progress);
}
if(scrollY>0){
if(wzRefresh.isReadyToRefresh()){
if(!"释放刷新".equals(tv_refresh_footer.getText().toString()))tv_refresh_footer.setText("释放刷新");
}else {
if(!"上拉刷新".equals(tv_refresh_footer.getText().toString()))tv_refresh_footer.setText("上拉刷新");
}

int max=footer.getHeight();
float progress=(distance*1.0f/max);
if(progress>1)progress=1;
footer.setScaleX(progress);
footer.setScaleY(progress);
footer.setAlpha(progress);
}
}

});
 *
 *
 *
 */


/***
 * footView 的布局， 主要注意高
 * <?xml version="1.0" encoding="utf-8"?>
 <RelativeLayout
 xmlns:android="http://schemas.android.com/apk/res/android"
 android:layout_width="match_parent"
 android:layout_height="wrap_content"  这里设置的高度不管用
 >
 <RelativeLayout
 android:layout_width="match_parent"
 android:layout_height="60dp">  高度要设置到这里
 <ProgressBar
 style="@android:style/Widget.DeviceDefault.ProgressBar"
 android:layout_width="30dp"
 android:layout_height="30dp"
 android:id="@+id/progressBar"
 android:layout_gravity="center_vertical"
 android:layout_marginRight="19dp"
 android:layout_marginEnd="19dp"
 android:layout_centerVertical="true"
 android:layout_toLeftOf="@+id/tv_refresh"
 android:layout_toStartOf="@+id/tv_refresh" />
 <TextView
 android:id="@+id/tv_refresh"
 android:text="下拉刷新"
 android:textSize="14dp"
 android:layout_centerInParent="true"
 android:layout_width="wrap_content"
 android:layout_height="wrap_content" />

 </RelativeLayout>


 </RelativeLayout>

 */


/***  Xml 的布局 例子
 * <com.example.wz.test.WzRefresh
 android:id="@+id/wzrefresh"
 android:layout_width="match_parent"
 android:layout_height="match_parent"
 >
 <ScrollView
 android:layout_width="match_parent" android:background="#555" android:fillViewport="true"
 android:layout_height="match_parent">
 <LinearLayout
 android:orientation="vertical"
 android:layout_width="match_parent"
 android:layout_height="match_parent">
 <android.support.v4.view.ViewPager  android:background="#999"
 android:id="@+id/viewPager"
 android:layout_width="match_parent"
 android:layout_height="200dp"></android.support.v4.view.ViewPager>
 </LinearLayout>
 </ScrollView>
 // <!--<ListView-->
 // <!--android:id="@+id/listview"-->
 // <!--android:layout_width="match_parent"-->
 // <!--android:layout_height="match_parent"-->
 // <!--&gt;</ListView>-->
 </com.example.wz.test.WzRefresh>
 */
public class WzRefresh extends RelativeLayout {
    public TouchState touchState = TouchState.ready;
    public Interpolator backToRefreshInterpolator = new DecelerateInterpolator(3);
    public Interpolator backToStartInterpolator = new DecelerateInterpolator(1);
    protected View emptyView;// 没有数据的时候显示
    View contentView;//内容 content
    View headerView;// 头部控件
    View footerView;//底部控件
    boolean showEmpty = false;//是否显示 数据空的时候的样子
    int headerH = 0;
    int footerH = 0;
    int refreshDistance = 1;//下拉超过多少距离就刷新
    int pullPower = 2;//下拉力度,最终的下拉距离是 手指移动距离除以这个数
    PointF downPoint = new PointF(0, 0);//按下的位置
    boolean touchScroll = false;//是否开始滚动了
    boolean isTouch = false;//当前是否在触摸屏幕
    float touchScroll_DownY;//开始滚动的时候 当时手指的位置
    float touchScroll_BeginScrollY;//开始拖动的 原始位置
    /**
     * 滚动的 view
     */
    View scrollView;
    OnScrollChangeListenerWz onScrollChangeListener;
    RefreshListener refreshListener;
    int backToRefreshDuration = 400;//回弹动画执行时间
    int backToStartDuration = 250;//回到初值回弹动画执行时间

    public WzRefresh(Context context) {
        super(context);
    }

    public WzRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WzRefresh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        initContentView();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        try {
            if (headerView == null) {//初始化头部和底部， 需要自己实现
                initHeaderAndFooter();
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {//这个l  t  r b 只是代表父容器的位置。。。。。

        int centerH = b - t;

        if (contentView != null) contentView.layout(l, 0, r, centerH);
        if (emptyView != null) emptyView.layout(l, 0, r, centerH);

        if (headerView != null) {
            headerView.layout(l, -headerView.getMeasuredHeight(), r, 0);
            headerH = headerView.getHeight();
        } else {
            headerH = 0;
        }


        if (footerView != null) {
            footerView.layout(l, centerH, r, centerH + footerView.getMeasuredHeight());
            footerH = footerView.getHeight();
        } else {
            footerH = 0;
        }
    }

    /**
     * 给子控件一个取消事件
     *
     * @param event
     */
    void cancelChildEvent(MotionEvent event) {
        event.setAction(MotionEvent.ACTION_CANCEL);
        super.dispatchTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isTouch = true;
            downPoint.set(event.getX(), event.getY());
            clearAnimation();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            isTouch = true;
            int distanceY = (int) (event.getY() - downPoint.y);
            if (isTouchPullDown(event, downPoint)) {//向下拉
                if (!childCanScrollDown()) {
                    touchScroll(distanceY);
                    //给子控件一个取消事件
                    cancelChildEvent(event);
                    return true;
                }

                if (getTouchState() == TouchState.pullUp) {//如果在上拉过程中。。 变下拉了就不管
                    return true;
                }
            } else if (isTouchPullUp(event, downPoint)) {//向上拉
                if (!childCanScrollUp()) {
                    touchScroll(distanceY);
                    //给子控件一个取消事件
                    cancelChildEvent(event);
                    return true;
                }

                if (getTouchState() == TouchState.pullDown) {//如果在下拉过程中。。 变上拉了就不管
                    return true;
                }
            }
        } else {
            isTouch = false;
            endScroll();
        }
        super.dispatchTouchEvent(event);
        return true;
    }

    /**
     * 手指滚动
     */
    public void touchScroll(float y) {
        if (!touchScroll) {
            touchScroll_DownY = y;
            touchScroll_BeginScrollY = getScrollY();
            touchScroll = true;
            if (y > 0) {//下拉
                if (getTouchState() != TouchState.pullUp && (getTouchState() != TouchState.refreshing_pullUp))//当前不是上拉状态中
                {
                    setTouchState(TouchState.pullDown);
                }
            } else if (y < 0) {//上拉
                if (getTouchState() != TouchState.pullDown && (getTouchState() != TouchState.refreshing_pullDown))//当前不是上下拉状态中
                {
                    setTouchState(TouchState.pullUp);
                }
            }
        }
        int scrollToY = (int) (touchScroll_BeginScrollY + (touchScroll_DownY - y) / pullPower);//

        if (getTouchState() == TouchState.pullDown || (getTouchState() == TouchState.refreshing_pullDown)) {//下拉的时候, scrollToY不能大于0.。。。大于0 就是上拉了
            if (getTouchState() == TouchState.pullDown) {
                if (scrollToY > 0) scrollToY = 0;
            }
            if (getTouchState() == TouchState.refreshing_pullDown && (scrollToY > -headerH)) {//如果是刷新状态， 并且会拉 超过头部位置了， 就不能回拉。。 要保证头部 不动
                scrollToY = -headerH;
            }
            scrollTo(0, (scrollToY));
        } else if (getTouchState() == TouchState.pullUp || (getTouchState() == TouchState.refreshing_pullUp)) {//上拉的时候, scrollToY不能小于0.。。。小于0 就是下拉了
            if (getTouchState() == TouchState.pullUp) {
                if (scrollToY < 0) scrollToY = 0;
            }
            if (getTouchState() == TouchState.refreshing_pullUp && (scrollToY < footerH)) {//上拉刷新中， 保证底部始终可见
                scrollToY = footerH;
            }
            scrollTo(0, (scrollToY));
        }
    }

    /**
     * 结束滚动
     */
    public void endScroll() {
        touchScroll = false;
        playEndTouchAnimation();
    }

    /**
     * 下拉刷新中,这里就该加载数据了
     */
    public void refreshByPullDown() {
        log("下拉刷新中....");
        setTouchState(TouchState.refreshing_pullDown);
        if (getRefreshListener() != null) {
            try {
                getRefreshListener().onRefreshPullDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 上拉刷新中,这里就该加载数据了
     */
    public void refreshByPullUp() {
        log("上拉刷新中....");
        setTouchState(TouchState.refreshing_pullUp);
        if (getRefreshListener() != null) {
            try {
                getRefreshListener().onRefreshPullUp();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void startRefresh() {
        post(new Runnable() {
            @Override
            public void run() {
                int refreshH = headerH + refreshDistance;
                if (-getScrollY() < refreshH) {
                    playScrollAnimation(getScrollY(), -refreshH - 2, 1, backToRefreshInterpolator, new Runnable() {
                        @Override
                        public void run() {
                            if (scrollView != null)
                                scrollView.scrollTo(contentView.getScrollX(), 0);
                            endScroll();
                        }
                    });
                }
            }
        });

    }

    /**
     * 停止更新动画
     */
    public void stopRefresh() {
        postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!touchScroll) {//手指没有在屏幕上才可以
                    playToReadyState();
                }
            }
        }, 0);
//        if(Thread.currentThread()!=Looper.getMainLooper().getThread()){
//            try {
//                Thread.sleep(backToStartDuration);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * 获取 现在如果释放 是否 会刷新
     *
     * @return
     */
    public boolean isReadyToRefresh() {
        if (getScrollY() < 0) {//下拉
            return -getScrollY() > headerH + refreshDistance;
        } else {//上拉
            return getScrollY() > footerH + refreshDistance;
        }
    }

    /**
     * 获取 现在如果释放 是否 会回到初始位置
     *
     * @return
     */
    public boolean isReadyToBackBegin() {
        if (isTouch) {
            if (getScrollY() < 0) {//下拉
                return -getScrollY() < headerH + refreshDistance;
            } else {//上拉
                return getScrollY() < footerH + refreshDistance;
            }
        }
        return false;
    }

    /***
     * 回归动画，
     * 不再触摸屏幕的时候 的动画
     */
    void playEndTouchAnimation() {
        if (getScrollY() != 0) {
            if (getScrollY() < 0) {//下拉
                if (isReadyToRefresh()) {//头部全部露出来了
                    playScrollAnimation(getScrollY(), -headerH, backToRefreshDuration, backToRefreshInterpolator, new Runnable() {
                        @Override
                        public void run() {
                            refreshByPullDown();
                        }
                    });
                    return;
                } else if (-getScrollY() == headerH) {//可能正在刷新中。。。不要动
                    if (touchState == TouchState.refreshing_pullDown || touchState == TouchState.refreshing_pullUp) {//正在刷新才不播放回归动画
                        return;
                    }
                }
            } else {//上拉
                if (isReadyToRefresh()) {//底部全部露出来了
                    playScrollAnimation(getScrollY(), footerH, backToRefreshDuration, backToRefreshInterpolator, new Runnable() {
                        @Override
                        public void run() {
                            refreshByPullUp();
                        }
                    });
                    return;
                } else if (getScrollY() == footerH) {//可能正在刷新中。。。不要动
                    if (touchState == TouchState.refreshing_pullDown || touchState == TouchState.refreshing_pullUp) {//正在刷新才不播放回归动画
                        return;
                    }
                }
            }
            playToReadyState();
        } else {//如果本身就在原位置
            setTouchState(TouchState.ready);
        }
    }

    /**
     * 回到初始位置
     */
    void playToReadyState() {
        Runnable stopBackToReady = new Runnable() {
            @Override
            public void run() {
                setTouchState(TouchState.ready);
            }
        };
        playScrollAnimation(getScrollY(), 0, backToStartDuration, backToStartInterpolator, stopBackToReady);
    }

    /**
     * 播放一个移动动画
     *
     * @param scrollYFrom
     * @param scrollYTo
     * @param duration
     */
    void playScrollAnimation(int scrollYFrom, int scrollYTo, int duration, Interpolator interpolator, final Runnable endAnimation) {
        clearAnimation();
        ValueAnimator valueAnimator = new ObjectAnimator();
        valueAnimator.setTarget(this);//不要这一句 在4.4 红米上报错
        valueAnimator.setDuration(duration);
        valueAnimator.setIntValues(scrollYFrom, scrollYTo);
        valueAnimator.setInterpolator(interpolator);

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
                scrollTo(0, value);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (endAnimation != null) {
                    try {
                        endAnimation.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        valueAnimator.start();
    }

    TouchState getTouchState() {
        return touchState;
    }

    void setTouchState(TouchState touchState) {
        log("setTouchState:   old:  " + getTouchState() + "   new ;  " + touchState);
        this.touchState = touchState;
    }

    /**
     * 是否可以下拉
     *
     * @return
     */
    public boolean childCanScrollDown() {
        if (headerH < 1) return true;//如果没有底部高 就不让下拉
        if (scrollView != null) {
            return scrollView.canScrollVertically(-1);
        }
        return false;
    }

    /**
     * 当前手势是否 是下拉的，  可根据需要覆盖
     *
     * @param motionEvent
     * @param downPoint
     * @return
     */
    public boolean isTouchPullDown(MotionEvent motionEvent, PointF downPoint) {
        /**
         *distanceY>0;  没特殊需要这样就行了， 但为了兼顾 里面有viewpager 需要横向滑动的情况。。 所以多加了逻辑
         */
        float distanceY = motionEvent.getY() - downPoint.y;
        float distanceX = motionEvent.getX() - downPoint.x;
        return motionEvent.getY() - downPoint.y > 0 && Math.abs(distanceY) > Math.abs(distanceX);
    }

    /***
     * 当前手势是否是上拉的，   可根据需要覆盖
     * @param motionEvent
     * @param downPoint
     * @return
     */
    public boolean isTouchPullUp(MotionEvent motionEvent, PointF downPoint) {
        /**
         * distanceY<0;  没特殊需要这样就行了， 但为了兼顾 里面有viewpager 需要横向滑动的情况。。 所以多加了逻辑
         */
        float distanceY = motionEvent.getY() - downPoint.y;
        float distanceX = motionEvent.getX() - downPoint.x;
        return motionEvent.getY() - downPoint.y < 0 && Math.abs(distanceY) > Math.abs(distanceX);
    }

    /***
     * 一般不需要手动设置这个，  主要用于  WzRefresh 里面包含的不是 一个 可滚动的控件。。。， 需要自己设置 距离根据哪个来滚动
     * @param view
     */
    public void setScrollView(View view) {
        scrollView = view;
    }

    /***
     * 是否可以上拉
     * @return
     */
    public boolean childCanScrollUp() {
        if (footerH < 1) return true;//如果没有底部高 就不让上拉
        if (scrollView != null) {
            return scrollView.canScrollVertically(1);
        }
        return false;
    }

    private void log(Object o) {
       // LogTool.s(o);
    }

    /***
     * 设置头部控件
     * @param headerView
     */
    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        sortChildViews();
    }

    /***
     * 设置底部部控件
     * @param footerView
     */
    public void setFooterView(View footerView) {
        this.footerView = footerView;
        sortChildViews();
    }

    /***
     * 设置没有内容时的显示控件
     * @param emptyView
     */
    public void setEmptyView(View emptyView) {
        if (emptyView != null) {
            this.emptyView = emptyView;
            emptyView.setVisibility(GONE);
            sortChildViews();
            //showEmptyView(showEmpty);

        }
    }

    /***
     * 显示 内容为空的 显示
     * @param showEmpty  是否显示
     */
    public void showEmptyView(boolean showEmpty) {
        this.showEmpty = showEmpty;
        if (emptyView != null) {
            if (showEmpty) {
                emptyView.setVisibility(VISIBLE);
                contentView.setVisibility(GONE);
            } else {
                emptyView.setVisibility(GONE);
                contentView.setVisibility(VISIBLE);
            }
        }
    }

    public void sortChildViews() {
        initContentView();
        removeAllViews();
        if (headerView != null) addView(headerView);
        if (contentView != null) addView(contentView);
        if (emptyView != null) addView(emptyView);
        if (footerView != null) addView(footerView);
    }

    /***
     * 初始化 头部和 底部控件，  需要的话自己实现
     */
    public void initHeaderAndFooter() {

    }

    void initContentView() {
        if (getChildCount() == 1) {
            contentView = getChildAt(0);
            if (scrollView == null) scrollView = contentView;
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//        LogTool.s("滚动了："+t);
        if (getRefreshListener() != null) {
            getRefreshListener().onPullProgressChanged(l, t, oldl, oldt);
        }
        if (onScrollChangeListener != null) {
            onScrollChangeListener.onScrollChange(this, l, t, oldl, oldt);
        }

    }

    /***
     * 设置滚动监听
     * @param l
     */
    public void setOnScrollChangeListenerWz(OnScrollChangeListenerWz l) {
        this.onScrollChangeListener = l;
    }

    RefreshListener getRefreshListener() {
        return refreshListener;
    }

    /**
     * 设置监听
     *
     * @param refreshListener
     */
    public void setRefreshListener(RefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    /**
     * 触摸状态
     */
    enum TouchState {
        ready,//准备状态，也就是初始状态
        pullDown,//下拉
        pullUp,//上拉
        refreshing_pullDown,//下拉刷新中
        refreshing_pullUp//下拉刷新中
    }

    public interface OnScrollChangeListenerWz {
        void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    public interface RefreshListener {
        void onRefreshPullDown();//下拉刷新回调

        void onRefreshPullUp();//上拉刷新回调

        /***
         * 下拉或者上拉 的进度监听
         * @param scrollX  应该没用
         * @param scrollY  下拉距离，  向下拉的时候 这个是 小于0的，  向上拉的时候 是大于0的
         * @param oldScrollX
         * @param oldScrollY
         */
        void onPullProgressChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY);//滚动回调
    }


}
