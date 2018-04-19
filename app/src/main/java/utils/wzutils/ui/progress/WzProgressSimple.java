package utils.wzutils.ui.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;

import utils.wzutils.common.CommonTool;

/**
 * abc on 2017/7/21.
 */

public class WzProgressSimple extends android.support.v7.widget.AppCompatImageView {
    public WzProgressSimple(Context context) {
        super(context);
        init();
    }

    public WzProgressSimple(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        paintBg.setColor(color_bg);
        paintHuan.setColor(color_huan);
        paintHuanBg.setColor(color_huan_bg);
    }
    int huanKuan=20;
    /***
     *
     * @param color_bg_str  背景颜色
     * @param color_huan_str  环的颜色
     * @param color_huan_bg_str 环的背景颜色
     * @param huanKuan  环的宽度， dp
     */
    public void init(String color_bg_str,String color_huan_str,String color_huan_bg_str,int huanKuan,int progress){
        color_bg=Color.parseColor(color_bg_str);
        color_huan=Color.parseColor(color_huan_str);
        color_huan_bg=Color.parseColor(color_huan_bg_str);
        this.huanKuan= CommonTool.dip2px(huanKuan);
        this.currProgess=progress;
        init();
    }
     int color_bg=Color.parseColor("#ff0000");
     int color_huan=Color.parseColor("#00ff00");
     int color_huan_bg=Color.parseColor("#0000ff");
    int currProgess=0;
    int beginProgress=-90;


    int maxProgress=100;
    boolean showProgress=true;//是否显示
    Paint paintHuan=new Paint(Paint.ANTI_ALIAS_FLAG);//环
    Paint paintHuanBg=new Paint(Paint.ANTI_ALIAS_FLAG);//环的背景
    Paint paintBg=new Paint(Paint.ANTI_ALIAS_FLAG);//背景
    @Override
    protected void onDraw(Canvas canvas) {
        if(showProgress){
            int w=getWidth();
            int center=w/2;
            //画环背景
            canvas.drawCircle(center,center,center,paintHuanBg);
            //画环
            RectF rectF=new RectF(0,0,w,w);
            canvas.drawArc(rectF,beginProgress,currProgess * 360 / maxProgress,true,paintHuan);//画扇形
            //画环里面内圆的填充
            canvas.drawCircle(center,center,center-huanKuan,paintBg);
        }
        super.onDraw(canvas);

    }

    /***
     * 是否显示环形
     * @param showProgress
     */
    public void setShowProgress(boolean showProgress){
        this.showProgress=showProgress;
        invalidate();
    }
    /***
     * 设置角度
     * @param progress 最大100
     */
    public void setProgress(int progress){
        if(progress>maxProgress)progress=maxProgress;
        this.currProgess=progress;
        invalidate();
    }

    /***
     * 设置最大进度
     * @param maxProgress
     */
    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }
}
