package utils.wzutils.ui.webview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;

import java.util.ArrayList;

import utils.wzutils.AppTool;
import utils.wzutils.ImgTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.ui.bigimage.PicViewActivity;

/**
 * Created by wz on 2016/12/13.
 * 注意  这个方法 用了  setWebChromeClient, 所以外面不要用了。。。。
 */
public class WebViewBigImg extends WebView {
    static final String tuWenPreStr = "932847187yewqoiufaiuas8327riueh";
    ArrayList<String> tuwenSrcList = new ArrayList<>();

    public WebViewBigImg(Context context) {
        super(context);
        init();
    }

    public WebViewBigImg(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public WebViewBigImg(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public WebViewBigImg(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        initShowBigImg();
    }

    public void initShowBigImg() {
        setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                try {
                    String src = message.replace(tuWenPreStr, "");
                    // ParentFragment.showBigImage(tuwenSrcList.indexOf(src),tuwenSrcList);
                    ImageView imageView = new ImageView(AppTool.currActivity);
                    ImgTool.setUrlTag(src, imageView);
                    PicViewActivity.go(imageView);
                } catch (Exception e) {
                    LogTool.ex(e);
                }
                result.cancel();
                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                result.cancel();
                return true;
            }
        });
    }

    @Override
    public void loadData(String data, String mimeType, String encoding) {
        data = handData(data);
        super.loadData(data, mimeType, encoding);
    }

    @Override
    public void loadDataWithBaseURL(String baseUrl, String data, String mimeType, String encoding, String historyUrl) {
        data = handData(data);
        super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    public String handData(String data) {
        data = "" + data;
        data = data.replace("<img", "<img onclick=\"alert('" + tuWenPreStr + "'+this.src)\"");
        data = data.replace("<IMG", "<img onclick=\"alert('" + tuWenPreStr + "'+this.src)\"");
        tuwenSrcList = StringTool.getImgSrc(data);

        //初始化
        WebViewTool.initWebViewNormalSetting(this);
        initShowBigImg();
        return data;
    }

    public ArrayList<String> getTuwenSrcList() {
        return tuwenSrcList;
    }
}
