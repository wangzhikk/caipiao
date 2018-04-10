package utils.tjyutils.parent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import utils.tjyutils.common.TitleTool;
import utils.wzutils.ImgTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.parent.WzParentActivity;
import utils.wzutils.parent.WzParentFragment;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * Created by wz on 2017/5/24.
 */

public abstract class ParentFragment extends WzParentFragment {
    public TitleTool titleTool;

    /**
     * 加载一张图片到imageview , 最简单的模式
     *
     * @param src       图片地址
     * @param imageView 加载给哪个控件
     */
    public static void loadImage(Object src, ImageView imageView) {
        try {
            ImgTool.loadImage(src, imageView);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /**
     * 加载一张图片到imageview , 指定需求宽高
     *
     * @param src       图片地址
     * @param imageView 加载给哪个控件
     * @param width     需要的宽
     * @param height    需要的高
     */
    public static void loadImage(Object src, ImageView imageView, int width, int height) {
        try {
            ImgTool.loadImage(src, imageView, width, height);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /**
     * 加载一张图片到imageview , 最简单的模式
     *
     * @param src 图片地址
     */
    public static void loadImage(Object src, View parent, int resId) {
        try {
            ImgTool.loadImage(src, (ImageView) parent.findViewById(resId));
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /**
     * 加载一张图片到imageview , 指定需求宽高
     *
     * @param src    图片地址
     * @param width  需要的宽
     * @param height 需要的高
     */
    public static void loadImage(Object src, View parent, int resId, int width, int height) {
        try {
            ImgTool.loadImage(src, (ImageView) parent.findViewById(resId), width, height);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    @Override
    public void afterCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.afterCreateView(inflater, container, savedInstanceState);
        titleTool=new TitleTool(getActivity(),parent);
    }

    @Override
    public int initShowContentId() {
        return 0;
    }

    public void showWaitingDialog(String msg) {
        try {
            if (getActivityWz() instanceof WzParentActivity) {
                ((WzParentActivity)getActivityWz()).showWaitingDialog("");
            }else {
                WzParentActivity.showWaitingDialogStac(msg);
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public void hideWaitingDialog() {
        try {
            if (getActivityWz() instanceof WzParentActivity) {
                ((WzParentActivity)getActivityWz()).hideWaitingDialog();
            }else {
                WzParentActivity.hideWaitingDialogStac();
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public void setTextView(View parent, int resId, Object text) {
        try {
            UiTool.setTextView(parent, resId, text);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public void setTextView(TextView textView, Object text) {
        try {
            UiTool.setTextView(textView, text);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 给指定btn 绑定一个fragment
     * @param btn
     * @param fragment
     */
    public void bindFragmentBtn(View btn, final ParentFragment fragment){
        if(btn!=null&&fragment!=null)
            btn.setOnClickListener(new WzViewOnclickListener() {
                @Override
                public void onClickWz(View v) {
                    fragment.go();
                }
            });
    }
    public void go() {
        try {
            go(null);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    public void go(Bundle bundle) {
        try {
            if(bundle!=null)setArguments(bundle);
            new NormalActivity().go(this);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    public void goForResult(ParentFragment parentFragment,int requestCode) {
        try {
            new NormalActivity().goForResult(this,parentFragment,requestCode);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }


    public  String getPageName(){
        return ""+getClass();
    }
}
