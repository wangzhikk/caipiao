package utils.tjyutils.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

import com.cp.R;

import utils.tjyutils.parent.ParentFragment;

import utils.wzutils.ui.webview.WebViewTool;

/**
 * Created by kk on 2017/3/23.
 */

public class WebFragment extends ParentFragment {

    WebView webview;
    String url;
    String title;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUseCacheView(false);
    }

    @Override
    public int initContentViewId() {
        return R.layout.web;
    }
    @Override
    public void initData() {
        url= ""+ getArgument("url", "");
        title=""+getArgument("title","");
        titleTool.setTitle(title);
        WebViewTool.initWebViewNormalSetting(webview);
        WebViewTool.initWebViewDialog(webview);
        webview.loadUrl(url);
    }

    @Override
    public void initListener() {

    }



    public void go(String url,String title) {
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        bundle.putString("title",title);
        setArguments(bundle);
        super.go();
    }

    public static ParentFragment byData(String url,String title){
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        bundle.putString("title",title);
        WebFragment webFragment=new WebFragment();
        webFragment.setArguments(bundle);
        return webFragment;
    }

}

