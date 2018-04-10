package utils.wzutils.ui.clicp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;

import java.util.Arrays;

import utils.wzutils.common.CommonTool;
import utils.wzutils.ui.WzImageView;


/**
 * 圆角图片
 *
 * @author
 */
public class RoundImageView extends WzImageView {
    private float[] radii = new float[8];   // top-left, top-right, bottom-right, bottom-left
    private Path mClipPath;                 // 剪裁区域路径
    private Paint mPaint;                   // 画笔
    private boolean mRoundAsCircle = true; // 圆形
    private RectF mLayer;                   // 画布图层大小

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImageView(Context context) {
        this(context, null);
        init();
    }
    public void init(){
        setScaleType(ScaleType.CENTER_CROP);
        mClipPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        int round= CommonTool.dip2px(10);
        radii=new float[]{round,round,round,round,round,round,round,round};
    }

    /**
     * 设置圆角
     * @param cornerDp
     */
    public void setRoundCornerDp(int cornerDp) {
        mRoundAsCircle=false;
        Arrays.fill(radii,CommonTool.dip2px(cornerDp));
        refreshSize();
        invalidate();
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        refreshSize();
    }
    public void refreshSize(){
        int w=getWidth();
        int h=getHeight();
        mLayer = new RectF(0, 0, w, h);
        mClipPath.reset();
        if (mRoundAsCircle) {
            PointF center = new PointF(w / 2, h / 2);
            mClipPath.addCircle(center.x, center.y, w/2, Path.Direction.CW);
        } else {
            mClipPath.addRoundRect(mLayer, radii, Path.Direction.CW);
        }
    }

    /***
     * view
     * 就用ondraw ， viewgroup 就用 dispatchDraw
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.saveLayer(mLayer, null, Canvas.ALL_SAVE_FLAG);//必须， 否则效果不对
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));//设置合成模式
        super.onDraw(canvas);//其实不止 imageview ， 所有的view 都可以 实现圆角效果
        canvas.drawPath(mClipPath,mPaint);//不能直接画， 一定用 drawPath
        canvas.restore();
    }



}
