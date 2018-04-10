package utils.wzutils.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ishare on 2016/9/18.
 */
public class WzViewPager extends ViewPager {
    private boolean canScroll = true;

    public WzViewPager(Context context) {
        super(context);
    }


    public WzViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (!canScroll) {
            return false;
        }

        return super.onInterceptTouchEvent(ev);
    }

    public boolean isCanScroll() {
        return canScroll;
    }

    public void setCanScroll(boolean canScroll) {
        this.canScroll = canScroll;
    }
}
