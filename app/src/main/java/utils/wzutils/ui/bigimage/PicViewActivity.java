package utils.wzutils.ui.bigimage;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import utils.wzutils.AppTool;
import utils.wzutils.ImgTool;


/***
 *  这个activity 的
 * <style name="translucent" parent="Theme.AppCompat.Light.NoActionBar">
 <item name="android:windowBackground">@android:color/transparent</item>
 <item name="android:windowIsTranslucent">true</item>
 <item name="android:windowAnimationStyle">@null</item>
 <item name="android:windowTranslucentStatus">true</item>
 </style>
 *
 *
 */
public class PicViewActivity extends Activity {

    private static final long ANIM_TIME = 200;
    public static ImageView src;
    private RectF mThumbMaskRect;
    private Matrix mThumbImageMatrix;
    private ObjectAnimator mBackgroundAnimator;
    private View mBackground;
    private PinchImageView mImageView;

    public static void go(ImageView v) {
        if (v.getDrawable() == null) return;

        PicViewActivity.src = v;
        Intent intent = new Intent(AppTool.getApplication(), PicViewActivity.class);
        AppTool.currActivity.startActivity(intent);
        AppTool.currActivity.overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setBackgroundColor(Color.parseColor("#00000000"));

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        final Rect rect = new Rect();
        src.getGlobalVisibleRect(rect);

        final ImageView.ScaleType scaleType = src.getScaleType();

        mBackground = new ImageView(this);
        mBackground.setBackgroundColor(Color.parseColor("#000000"));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mBackground.setLayoutParams(layoutParams);


        final int srcW = rect.width();
        final int srcH = rect.height();


        mImageView = new PinchImageView(this);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mImageView.setLayoutParams(layoutParams2);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.addView(mBackground);
        relativeLayout.addView(mImageView);

        setContentView(relativeLayout);


        ImgTool.loadImage(ImgTool.getUrlTag(src), mImageView);


        mImageView.post(new Runnable() {
            @Override
            public void run() {
                mImageView.setAlpha(1f);

                //背景动画
                mBackgroundAnimator = ObjectAnimator.ofFloat(mBackground, "alpha", 0f, 1f);
                mBackgroundAnimator.setDuration(ANIM_TIME);
                mBackgroundAnimator.start();

                //status bar高度修正
                Rect tempRect = new Rect();
                mImageView.getGlobalVisibleRect(tempRect);
                rect.top = rect.top - tempRect.top;
                rect.bottom = rect.bottom - tempRect.top;

                //mask动画
                mThumbMaskRect = new RectF(rect);
                RectF bigMaskRect = new RectF(0, 0, mImageView.getWidth(), mImageView.getHeight());
                mImageView.zoomMaskTo(mThumbMaskRect, 0);
                mImageView.zoomMaskTo(bigMaskRect, ANIM_TIME);


                //图片放大动画
                RectF thumbImageMatrixRect = new RectF();
                PinchImageView.MathUtils.calculateScaledRectInContainer(new RectF(rect), srcW, srcH, scaleType, thumbImageMatrixRect);
                RectF bigImageMatrixRect = new RectF();
                PinchImageView.MathUtils.calculateScaledRectInContainer(new RectF(0, 0, mImageView.getWidth(), mImageView.getHeight()), srcW, srcH, ImageView.ScaleType.FIT_CENTER, bigImageMatrixRect);
                mThumbImageMatrix = new Matrix();
                PinchImageView.MathUtils.calculateRectTranslateMatrix(bigImageMatrixRect, thumbImageMatrixRect, mThumbImageMatrix);
                mImageView.outerMatrixTo(mThumbImageMatrix, 0);
                mImageView.outerMatrixTo(new Matrix(), ANIM_TIME);
            }
        });
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mImageView.playSoundEffect(SoundEffectConstants.CLICK);
                finish();
            }
        });
    }

    @Override
    public void finish() {
        if ((mBackgroundAnimator != null && mBackgroundAnimator.isRunning())) {
            return;
        }

        //背景动画
        mBackgroundAnimator = ObjectAnimator.ofFloat(mBackground, "alpha", mBackground.getAlpha(), 0f);
        mBackgroundAnimator.setDuration(ANIM_TIME);
        mBackgroundAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                PicViewActivity.super.finish();
                overridePendingTransition(0, 0);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mBackgroundAnimator.start();

        //mask动画
        mImageView.zoomMaskTo(mThumbMaskRect, ANIM_TIME);

        //图片缩小动画
        mImageView.outerMatrixTo(mThumbImageMatrix, ANIM_TIME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PicViewActivity.src = null;
    }
}