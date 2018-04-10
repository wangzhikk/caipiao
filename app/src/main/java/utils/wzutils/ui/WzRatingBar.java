package utils.wzutils.ui;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import utils.wzutils.common.LogTool;


public class WzRatingBar extends LinearLayout {
    int max = 0;
    int rating = 0;
    int minRating=1;
    boolean canControl=true;
    int resStarDrawableIdEmpty = android.R.drawable.star_off;
    int resStarDrawableId = android.R.drawable.star_on;
    public WzRatingBar(Context context) {
        super(context);
        init();
    }

    public WzRatingBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public WzRatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setMax(int max) {
        if(max<1)max=1;
        if(this.max!=max){
            this.max = max;
            setOrientation(HORIZONTAL);
            removeAllViews();
            for (int i = 0; i < max; i++) {
                ImageView imageView = new ImageView(getContext());
                addView(imageView);
            }
        }
    }
    public void setCanControl(boolean canControl) {
        this.canControl = canControl;
        if(canControl){
            setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    float width=getWidth();
                    float currX=event.getX();
                    float oneWidth=width/max;
                    if(oneWidth!=0){
                        int rating=(int) (currX/oneWidth+0.5f);
                        setRating(rating,true);
                    }
                    return true;
                }
            });
        }else {
            setOnTouchListener(null);
        }
    }

    public void init() {
        setMax(5);
        setRating(2);
        setCanControl(canControl);

    }
    public void setResStarDrawableIdEmpty(int resStarDrawableIdEmpty) {
        this.resStarDrawableIdEmpty = resStarDrawableIdEmpty;
        setRatingImp(rating,false);
    }

    public void setResStarDrawableId(int resStarDrawableId) {
        this.resStarDrawableId = resStarDrawableId;
        setRatingImp(rating,false);
    }

    public  interface OnWzRatingBarChangeListener{
        public void onRatingChanged(WzRatingBar ratingBar, float rating, boolean fromUser) ;
    }

    public void setOnWzRatingBarChangeListener(OnWzRatingBarChangeListener onWzRatingBarChangeListener) {
        this.onWzRatingBarChangeListener = onWzRatingBarChangeListener;
    }

    OnWzRatingBarChangeListener onWzRatingBarChangeListener;


    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        setRating(rating,false);
    }
    void setRating(int rating,boolean fromUser) {
        if(rating<minRating)rating=minRating;
        if(rating>max)rating=max;
        if(this.rating!=rating){
            setRatingImp(rating,fromUser);
        }
    }
    void setRatingImp(int rating,boolean fromUser){
        this.rating = rating;
        for (int i = 0; i < getChildCount(); i++) {
            ImageView imageView = (ImageView) getChildAt(i);
            if(i<rating){
                imageView.setImageResource(resStarDrawableId);
            }else {
                imageView.setImageResource(resStarDrawableIdEmpty);
            }
        }
        if(onWzRatingBarChangeListener!=null){
            onWzRatingBarChangeListener.onRatingChanged(this,rating,fromUser);
        }
    }
}
