package utils.wzutils.ui.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wz on 2017/4/6.
 */

public class WzProgressCircle extends View {
    int maxProgess = 100;
    int currProgess = 67;
    int colorBg = Color.parseColor("#ffffff");
    int colorBgIn = Color.parseColor("#dcdcdc");
    int colorBegin = Color.parseColor("#e61f5b");
    int colorEnd = Color.parseColor("#f19d40");
    Paint paintIn;
    Paint paintClear;
    Paint paintHuan;
    int xCenter = 0;
    int yCenter = 0;
    int neiYuanWidth = 20;
    int waiyuanWidth = 40;

    public WzProgressCircle(Context context) {
        super(context);
        init();
    }

    public WzProgressCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public WzProgressCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
    }

    public void initPaint() {
        paintIn = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintIn.setColor(colorBgIn);

        paintClear = new Paint();
        paintClear.setColor(colorBg);

        paintHuan = new Paint(Paint.ANTI_ALIAS_FLAG);
        Shader mShader = new LinearGradient(0, 0, 0, getWidth(), new int[]{colorBegin, colorEnd}, null, Shader.TileMode.CLAMP);
        paintHuan.setShader(mShader);


        if (getWidth() > 0) {
            neiYuanWidth = getWidth() / 21;
            waiyuanWidth = getWidth() / 13;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth();
        xCenter = w / 2;
        yCenter = w / 2;

        if (paintClear == null) {
            initPaint();
        }

        //清屏
        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), paintClear);

        //画内圆
        canvas.drawCircle(xCenter, yCenter, xCenter - (waiyuanWidth - neiYuanWidth) / 2, paintIn);
        canvas.drawCircle(xCenter, yCenter, xCenter - (waiyuanWidth - neiYuanWidth) / 2 - neiYuanWidth, paintClear);

        //画外园
        canvas.drawArc(new RectF(0, 0, w, w), -90, currProgess * 360 / maxProgess, true, paintHuan);
        canvas.drawCircle(xCenter, yCenter, xCenter - waiyuanWidth, paintClear);
    }

    public void setProgress(int currProgess) {
        this.currProgess = currProgess;
        postInvalidate();
    }
}
