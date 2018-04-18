package utils.wzutils.common;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import utils.wzutils.AppTool;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzToast;
import utils.wzutils.ui.bigimage.PicViewActivity;
import utils.wzutils.ui.bigimage.WzBigImgListFragment;

/**
 * Created by kk on 2016/5/10.
 */
public class UiTool {
    static long preBackToQuitTime = 0;
    static Toast toast;

    /**
     * ***打电话*
     */
    public static void tellPhone(final String phoneNum, final Activity activity) {
        try {
            if (phoneNum == null || phoneNum.length() < 1) return;
            if (activity == null) return;

            initSimpleDilog("是否拨打 " + phoneNum, "拨打", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        String phone = phoneNum.replace("-", "").replace(" ", "");
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri//进入拨号界面不拨打   ACTION_CALL 这个是直接拨打，但是可能没权限
                                .parse("tel:" + phone));
                        activity.startActivity(intent);
                    } catch (Exception e) {
                        LogTool.ex(e);
                    }
                }
            }, "取消", null).show();

        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 双击退出程序
     */
    public static void backToQuit(String textToast) {
        try {
            long timeNow = System.currentTimeMillis();
            if (timeNow - preBackToQuitTime < 2000) {
                //AppTool.currActivity.moveTaskToBack(true);

                AppTool.exitApp();
            } else {
                CommonTool.showToast(textToast);
            }
            preBackToQuitTime = timeNow;
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }



    /***
     * 生成一个dialog
     *
     * @param msg
     * @param queding
     * @param quedingListener
     * @param quxiao
     * @param quxiaoListener
     * @return
     */
    public static Dialog initSimpleDilog(String msg, String queding, DialogInterface.OnClickListener quedingListener, String quxiao, DialogInterface.OnClickListener quxiaoListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(AppTool.currActivity)
                .setMessage(msg)
                .setPositiveButton(queding, quedingListener)
                .setNegativeButton(quxiao, quxiaoListener)
                .create();
        return alertDialog;
    }

    public static void showToast(int resId) {
        try {
            showToast(AppTool.getApplication().getResources().getText(resId));
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 主要是  华为部分手机  禁用 通知权限后 toast 没法弹出
     *
     * @param toastText
     */
    public static void showToast(final Object toastText) {
        try {
            WzToast wzToast = new WzToast();
            wzToast.showWzToast(toastText);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static void showToastLong(final Object toastText) {
        try {
            WzToast wzToast = new WzToast();
            wzToast.showWzToastLong(toastText);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 设置输入 钱。。。
     * @param editText
     */
    public static void setPricePoint(final EditText editText) {
        editText.setInputType(8194);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public static void setCompoundDrawables(Activity activity, TextView textView, int resIdLeft, int resIdTop, int resIdRight, int resIdBottom) {
        try {
            Drawable drawableLeft = null;
            Drawable drawableTop = null;
            Drawable drawableRight = null;
            Drawable drawableBottom = null;
            if (resIdLeft > 0) {
                drawableLeft = activity.getResources().getDrawable(resIdLeft);
                drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
            }
            if (resIdTop > 0) {
                drawableTop = activity.getResources().getDrawable(resIdTop);
                drawableTop.setBounds(0, 0, drawableTop.getMinimumWidth(), drawableTop.getMinimumHeight());
            }
            if (resIdRight > 0) {
                drawableRight = activity.getResources().getDrawable(resIdRight);
                drawableRight.setBounds(0, 0, drawableRight.getMinimumWidth(), drawableRight.getMinimumHeight());
            }
            if (resIdBottom > 0) {
                drawableBottom = activity.getResources().getDrawable(resIdBottom);
                drawableBottom.setBounds(0, 0, drawableBottom.getMinimumWidth(), drawableBottom.getMinimumHeight());
            }
            textView.setCompoundDrawables(drawableLeft, drawableTop, drawableRight, drawableBottom);
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }

    /***
     * 给一个文本设置字符串
     *
     * @param parent 文本所在容器
     * @param resId  文本 资源id
     * @param text   要设置的值
     */
    public static void setTextView(View parent, int resId, Object text) {
        try {
            TextView textView = (TextView) parent.findViewById(resId);
            setTextView(textView, text);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static void setTextView(TextView textView, Object text) {
        try {
            if(textView!=null)textView.setText(StringTool.getNotNullText(text));
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static void setTextColor(TextView textView, int colorResId) {
        try {
            textView.setTextColor(getColorByResId(colorResId));
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static void setTextColor(View parent, int resId, int colorResId) {
        try {
            TextView textView = (TextView) parent.findViewById(resId);
            setTextColor(textView, colorResId);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    public static void setBoldText(View textView){
        try {
           if(textView !=null&&textView instanceof TextView){
               ((TextView) textView).getPaint().setFakeBoldText(true);
           }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static int getColorByResId(int colorResId){
        return AppTool.currActivity.getResources().getColor(colorResId);
    }
    /***
     * 给控件设置w h
     * @param view
     * @param w
     * @param h
     */
    public static void setWH(View view, int w, int h) {
        if (view == null) return;
        try {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp == null) {
                lp = new ViewGroup.LayoutParams(0, 0);
            }
            if(w>-9)lp.width = w;
            if(h>-9)lp.height = h;
            view.setLayoutParams(lp);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    public static void setWHDp(View view, int w, int h) {
        setWH(view,CommonTool.dip2px(w),CommonTool.dip2px(h));
    }
    public static void setMargin(View view, int left,int top,int right,int bottom) {
        if (view == null) return;
        try {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (lp == null) {
                lp = new ViewGroup.MarginLayoutParams(0, 0);
            }
            lp.leftMargin=left;
            lp.rightMargin=right;
            lp.topMargin=top;
            lp.bottomMargin=bottom;
            view.setLayoutParams(lp);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }










    /***
     * text Span color
     * @param textView
     * @param colorResId
     * @param begin
     * @param end
     */
    public static void setTextSpanColor(TextView textView,int colorResId,int begin,int end){
        int color=getColorByResId(colorResId);
        CharSequence text=textView.getText();
        ForegroundColorSpan colorSpan=new ForegroundColorSpan(color);
        SpannableString s=new SpannableString(text);
        s.setSpan(colorSpan,begin,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(s);
    }

    /***
     * 设置字体大小
     * @param textView
     * @param textSizeDp
     * @param begin
     * @param end
     */
    public static void setTextSpanSize(TextView textView,int textSizeDp,int begin,int end){
        CharSequence text=textView.getText();
        AbsoluteSizeSpan absoluteSizeSpan=new AbsoluteSizeSpan(textSizeDp,true);
        SpannableString s=new SpannableString(text);
        s.setSpan(absoluteSizeSpan,begin,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(s);
    }
    /***
     * text Span color
     * @param textView
     * @param begin
     * @param end
     */
    public static void setTextSpanClick(TextView textView, final WzViewOnclickListener wzViewOnclickListener, int begin, int end){
        CharSequence text=textView.getText();
        ClickableSpan clickableSpan=new ClickableSpan(){
            @Override
            public void onClick(View widget) {
                try {
                    wzViewOnclickListener.onClickWz(widget);
                }catch (Exception e){
                    LogTool.ex(e);
                }
            }
        };
        SpannableString s=new SpannableString(text);
        s.setSpan(clickableSpan,begin,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(s);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    /***
     * 设置不要下划线
     *
     * @param tv_huangye_phone
     */
    public static void setTextSpanNotUnderLine(final TextView tv_huangye_phone) {
        UnderlineSpan mNoUnderlineSpan = new UnderlineSpan() {
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
               // ds.setColor(tv_huangye_phone.getTextColors().getDefaultColor());
            }
        };
        if (tv_huangye_phone.getText() instanceof Spannable) {
            Spannable s = (Spannable) tv_huangye_phone.getText();
            s.setSpan(mNoUnderlineSpan, 0, s.length(), Spanned.SPAN_MARK_MARK);
        }
    }

    /***
     * 获取textview  是否有省略号
     * tv_zhuye_jianjie.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { 在这个里面用
     */
    public static boolean getTextViewIsEllipsis(TextView tv) {
        try {
            return getTextViewIsEllipsisImp(tv,0);
        }catch (Exception e){
            LogTool.ex(e);
        }
        return  false;
    }
    /***
     * 获取textview  是否有省略号 用于item里面，需要传入textview 的宽度
     */
    public static boolean getTextViewIsEllipsis(TextView tv,int width) {
        try {
            return getTextViewIsEllipsisImp(tv,View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.AT_MOST));
        }catch (Exception e){
            LogTool.ex(e);
        }
        return  false;
    }
    /***
     * 获取textview  是否有省略号
     */
    public static boolean getTextViewIsEllipsisImp(TextView tv,int widthMeasure) {
        try {
            Layout l = tv.getLayout();
            if(l==null){
                tv.measure( widthMeasure,0);
                l = tv.getLayout();
            }
            if (l != null) {
                int lines = l.getLineCount();
                if (lines > 0) {
                    if (l.getEllipsisCount(lines - 1) > 0) {
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }catch (Exception e){
            LogTool.ex(e);
        }
        return  false;
    }

    public static ViewGroup getParentView(View child) {
        if(child!=null){
            return (ViewGroup) child.getParent();
        }
        return new RelativeLayout(AppTool.currActivity);
    }
    public static Rect getTextWidthHeight(String text,int textSize){
        text=StringTool.getNotNullText(text);
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect;
    }
    /***
     * 获取状态栏高度
     * @return
     */
    public static int getStatusBarHeight() {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight = -1;
//获取status_bar_height资源的ID
        int resourceId = AppTool.getApplication().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = AppTool.getApplication().getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight;

    }

    public static void setSoftInputModeSpan(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
    public static void setSoftInputModeReSize(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }
    /***
     * 绑定 复制按钮
     * @param btnFuZhi
     * @param textView
     */
    public static void bindFuZhi(View btnFuZhi, final TextView textView){
        if(btnFuZhi==null||textView==null)return;
        bindFuZhi(btnFuZhi,textView.getText().toString());
    }
    /***
     * 绑定 复制按钮
     * @param btnFuZhi
     * @param textView
     */
    public static void bindFuZhi(View btnFuZhi, final String text){
        if(btnFuZhi==null||StringTool.isEmpty(text))return;
        btnFuZhi.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                ClipboardManager cm = (ClipboardManager) AppTool.getApplication().getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(text);
                CommonTool.showToast("复制成功！");
            }
        });
    }
}
