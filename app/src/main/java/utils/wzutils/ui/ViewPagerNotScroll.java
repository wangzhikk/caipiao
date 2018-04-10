package utils.wzutils.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by ishare on 2016/9/18.
 */
public class ViewPagerNotScroll extends ViewPager {
    public ViewPagerNotScroll(Context context) {
        super(context);
    }

    public ViewPagerNotScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
