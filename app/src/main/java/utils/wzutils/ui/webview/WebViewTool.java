package utils.wzutils.ui.webview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.GeolocationPermissions;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import utils.wzutils.AppTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.parent.WzParentActivity;

/**
 * Created by coder on 16/1/18.
 */
public class WebViewTool {
    public static void initWebViewNormalSetting(final WebView mWebView) {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setAllowFileAccess(true);//设置启用或禁止访问文件数据
        webSettings.setJavaScriptEnabled(true); //设置是否支持JavaScript
        webSettings.setBlockNetworkImage(false);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAllowContentAccess(true);


        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setDefaultTextEncodingName("UTF-8");



        {// 4.4以上的 webview不能同时加载  http  和https ，需要加下面的代码
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            }
        }

        //启用数据库
        webSettings.setDatabaseEnabled(true);
        String dir = mWebView.getContext().getDir("database", Context.MODE_PRIVATE).getPath();
        //启用地理定位
        webSettings.setGeolocationEnabled(true);
        //设置定位的数据库路径
        webSettings.setGeolocationDatabasePath(dir);
        //最重要的方法，一定要设置，这就是出不来的主要原因
        webSettings.setDomStorageEnabled(true);


        mWebView.setWebChromeClient(new WebChromeClient() {
            //配置权限（同样在WebChromeClient中实现）
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });


        mWebView.setWebViewClient(new WebViewClientDefault());
    }

    public static void initWebViewDialog(WebView webView) {
        webView.setWebViewClient(new WebViewClientDefault(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                WzParentActivity.showWaitingDialogStac("");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                WzParentActivity.hideWaitingDialogStac();
            }
        });
    }

    public static class WebViewClientDefault extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                LogTool.s("url:  " + url);
                if (!url.startsWith("http:") && !url.startsWith("https:")) {//这里可以打开 非http协议的， 比如 微信里面的某些
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    AppTool.currActivity.startActivity(intent);
                    return true;
                }

            } catch (Exception e) {
                LogTool.ex(e);
            }
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            //super.onReceivedSslError(view, handler, error);
            handler.proceed();
        }
    }




    /***
     * 图文详情
     * <img alt=""   style="width: 100%;" 必须加width  100% ， 才能自适应
     */
    public static void initTuWenXiangQing(WebView webView, String content) {
        try {
            initWebViewNormalSetting(webView);
            content = content.replaceAll("<img", "<img width=100%");
            content = "<html><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><body>" + content;
            content += "</body></html>";

            if (!StringTool.isEmpty(content)) {
                webView.loadData(content, "text/html; charset=UTF-8", "UTF-8");
            }
        }catch (Exception e){
            LogTool.ex(e);
        }
    }
}

