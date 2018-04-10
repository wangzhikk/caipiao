package utils.wzutils.ui.textview;


import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by wz on 2017/11/16.
 */

public class BoldTextView extends android.support.v7.widget.AppCompatTextView {

    public BoldTextView(Context context) {
        super(context);
        init();
    }

    public BoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
        getPaint().setFakeBoldText(true);
    }

}
