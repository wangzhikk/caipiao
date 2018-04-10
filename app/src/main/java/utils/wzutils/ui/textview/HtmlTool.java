package utils.wzutils.ui.textview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;
import android.util.Base64;
import android.view.Gravity;
import android.widget.TextView;

import org.xml.sax.XMLReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.wzutils.AppTool;
import utils.wzutils.common.LogTool;

public class HtmlTool {
    public static final String protocol_sd="sd:";
    public static final String protocol_res="res:";
    public static final String protocol_http="http:";
    public static final String protocol_https="https:";
    /***
     * 协议 ：
     *
     * sd:
     * res:
     * http:
     *
     *
     *
     * @param textView
     * @param htmlText
     */
    public  static void setHtmlText(final TextView textView, String htmlText){
        try {
            if(textView==null)return;
            textView.setGravity(Gravity.CENTER_VERTICAL);
            textView.setText(Html.fromHtml(htmlText, new Html.ImageGetter() {
                @Override
                public Drawable getDrawable(String source) {

                    Drawable drawable=null;
                    if(source.startsWith(protocol_sd)){
                        source=source.replaceFirst(protocol_sd,"");
                        drawable=Drawable.createFromPath(source); drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    }else if(source.startsWith(protocol_res)){
                        source=source.replaceFirst(protocol_res,"");
                        int rId=Integer.parseInt(source);
                        drawable=textView.getResources().getDrawable(rId);

                        int w= (int) drawable.getIntrinsicWidth();
                        int h= (int) drawable.getIntrinsicHeight();
                        drawable.setBounds(0, 0, w, h);
                        return drawable;
                    }
                    return drawable;
                }
            }, new Html.TagHandler() {
                @Override
                public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {

                }
            }));
        }catch (Exception e){
            LogTool.ex(e);
        }

    }

    /***
     * 过滤html 标签
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr){
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE);
        Matcher m_script=p_script.matcher(htmlStr);
        htmlStr=m_script.replaceAll(""); //过滤script标签

        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
        Matcher m_style=p_style.matcher(htmlStr);
        htmlStr=m_style.replaceAll(""); //过滤style标签

        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE);
        Matcher m_html=p_html.matcher(htmlStr);
        htmlStr=m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }
//    /**
//     * 加载网络图片
//     * @param s
//     */
//    private void initDrawable(final String s) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final Drawable drawable = Glide.with(MainActivity.this).load(s).submit().get();
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (drawable != null) {
//                                drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
//                                //多张图片情况下进行存储：drawableMap.put(s,drawable);
//                                MainActivity.this.drawable = drawable;
//                                if (Build.VERSION.SDK_INT >= 24)
//                                    text.setText(Html.fromHtml(getString(R.string.content),Html.FROM_HTML_MODE_COMPACT,imageGetter,null));
//                                else
//                                    text.setText(Html.fromHtml(getString(R.string.content),imageGetter,null));
//                            }
//                        }
//                    });
//                } catch (InterruptedException | ExecutionException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }
}
