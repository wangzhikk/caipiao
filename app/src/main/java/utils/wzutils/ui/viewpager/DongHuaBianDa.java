package utils.wzutils.ui.viewpager;

import android.support.v4.view.ViewPager;
import android.view.View;

import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;

/**
 * Created by wz on 2017/9/8.
 */

public class DongHuaBianDa implements ViewPager.PageTransformer {
    private static final float MIN_SCALE = 0.9F;
    /**
     * position参数指明给定页面相对于屏幕中心的位置。它是一个动态属性，会随着页面的滚动而改变。
     * 当一个页面（page)填充整个屏幕时，positoin值为0； 当一个页面（page)刚刚离开屏幕右(左）侧时，position值为1（-1）；
     * 当两个页面分别滚动到一半时，其中一个页面是-0.5，另一个页面是0.5。
     * 基于屏幕上页面的位置，通过诸如setAlpha()、setTranslationX
     * ()或setScaleY()方法来设置页面的属性，创建自定义的滑动动画。
     */
    @Override
    public void transformPage(View view, float position) {
        // TODO Auto-generated method stub
        float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));

        float transla = 40 * Math.abs(position);



        if (position > 0) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setTranslationX(-transla);
        } else {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
            view.setTranslationX(transla);
        }
    }
}
