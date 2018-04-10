package utils.wzutils.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by ishare on 2016/6/13.
 */
public class WzScrollView extends NestedScrollView {
    float downY;
    float downX;
    double yDistance;
    double xDistance;
    OnScrollChangeListener onScrollChangeListener;


    public WzScrollView(Context context) {
        super(context);
    }

    public WzScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WzScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() > 0) {//解决内部有listview  或者recycleview  时， 自动滚动到下面的bug
            getChildAt(0).setFocusable(true);
            getChildAt(0).setFocusableInTouchMode(true);
        }
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            downX = ev.getX();
//            downY = ev.getY();
//        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
//            yDistance = Math.abs(ev.getY() - downY);
//            xDistance = Math.abs(ev.getX() - downX);
//            if (yDistance > xDistance) {//纵向滑动就不往里面传了
//                return true;
//            }
//        }
//        return super.onInterceptTouchEvent(ev);
//    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChangeListener != null) {
            onScrollChangeListener.onScrollChanged(l, t, oldl, oldt);
        }
    }

    public OnScrollChangeListener getOnScrollChangeListener() {
        return onScrollChangeListener;
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.onScrollChangeListener = onScrollChangeListener;
    }

    public interface OnScrollChangeListener {
        void onScrollChanged(int l, int t, int oldl, int oldt);
    }
}
