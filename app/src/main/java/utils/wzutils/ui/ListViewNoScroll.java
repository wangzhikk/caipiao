package utils.wzutils.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;


/**
 * Created by kk on 2016/5/16.
 */
public class ListViewNoScroll extends ListView {
    public ListViewNoScroll(Context context) {
        super(context);
    }

    public ListViewNoScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewNoScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int h = MeasureSpec.makeMeasureSpec(999999, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, h);
    }
}
