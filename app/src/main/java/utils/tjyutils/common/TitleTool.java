package utils.tjyutils.common;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cp.KeFu.KeFuFragment;

import java.io.Serializable;

import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.common.ViewTool;
import utils.wzutils.parent.WzViewOnclickListener;

public class TitleTool implements Serializable {

    public TextView title_title_tv,title_right_tv;
    public ImageView title_back_img,title_right_img,title_right_right_img;
    Activity activity;
    public View vg_title_right;

    public TitleTool(final Activity activity, View parent) {
        this.activity = activity;
        ViewTool.initViews(parent, this, null);
        initBack();
    }

    public void initBack() {
        if(title_back_img!=null)title_back_img.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                activity.onBackPressed();
            }
        });
    }
    public void hideBack() {
        if(title_back_img!=null)title_back_img.setVisibility(View.GONE);
    }
    public void setTitle(String title) {
        UiTool.setTextView(title_title_tv, title);
    }

    public void setTitleRight(int imgResId,WzViewOnclickListener onclickListener){
        try {
            if(title_right_img!=null){
                title_right_img.setVisibility(View.VISIBLE);
                title_right_img.setImageResource(imgResId);
                title_right_img.setOnClickListener(onclickListener);
            }

        }catch (Exception e){
            LogTool.ex(e);
        }

    }
    public void showService(){
        try {
            if(title_right_right_img!=null){
                title_right_right_img.setVisibility(View.VISIBLE);
                title_right_right_img.setOnClickListener(new WzViewOnclickListener() {
                    @Override
                    public void onClickWz(View v) {
                        new KeFuFragment().go();
                    }
                });
            }

        }catch (Exception e){
            LogTool.ex(e);
        }

    }
    public void setTitleRightTv(String text,WzViewOnclickListener onclickListener){
        try {
           if(title_right_tv!=null){
               if(StringTool.isEmpty(text)){
                   title_right_tv.setVisibility(View.GONE);
                   return;
               }
               title_right_tv.setVisibility(View.VISIBLE);
               UiTool.setTextView(title_right_tv,text);
               title_right_tv.setOnClickListener(onclickListener);
           }
        }catch (Exception e){
            LogTool.ex(e);
        }

    }


}
