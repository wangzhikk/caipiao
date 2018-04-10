package utils.wzutils.parent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;

import utils.wzutils.AppTool;
import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.ViewTool;

/**
 * Created by ishare on 2016/7/7.
 */
public abstract class WzParentFragment extends Fragment implements Serializable {
    public static Fragment currentFragment;
    public View parent;
    public View vg_page_content;
    boolean isUseCacheView = true;



    boolean isSingleInParent=true;//是否父类只有这一个可显示的界面  自定义生命周期用的， 和只有一个的分开
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentFragment = this;
        LogTool.printClassLine("onCreate", this);
    }

    @Override
    public void onStart() {
        super.onStart();
        currentFragment = this;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (currentFragment == this) {
            currentFragment = null;
        }
        LogTool.printClassLine("onDestroy", this);
    }
    public Activity getActivityWz(){
        if(getActivity()!=null)return getActivity();
        if(AppTool.currActivity!=null)return AppTool.currActivity;
        return null;
    }
    /***
     * 只有 wzNormalFragmentActivity 里面才会调用这个
     */
    public boolean onBackPressed() {
        return false;
    }

    public void setUseCacheView(boolean useCacheView) {
        isUseCacheView = useCacheView;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            try {
                parent = initContentView();
                if (parent == null) {
                    if (isUseCacheView) {
                        parent = LayoutInflaterTool.getInflater(8, initContentViewId()).inflate();//inflater.inflate(initContentViewId(),null);
                    } else {
                        parent = getActivity().getLayoutInflater().inflate(initContentViewId(), null);
                    }
                }
                ViewTool.initViews(parent, this, null);
                initParentView();
                afterCreateView(inflater, container, savedInstanceState);
            } catch (Exception e) {
                LogTool.ex(e);
            }
            try {
                initData();
            } catch (Exception e) {
                LogTool.ex(e);
            }
            try {
                initListener();
            } catch (Exception e) {
                LogTool.ex(e);
            }


        } catch (Exception e) {
            LogTool.ex(e);
        }
        return parent;
    }

    /**
     * 默认hide  的控件id
     *
     * @return
     */
    public int initShowContentId() {
        return 0;
    }

    public void initParentView() {
        vg_page_content = parent.findViewById(initShowContentId());
        hideCotent();
    }

    public abstract int initContentViewId();

    public View initContentView() {
        return null;
    }

    public void afterCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    }

    public abstract void initData();

    public abstract void initListener();

    public void showContent() {
        if (vg_page_content != null) {
            vg_page_content.setVisibility(View.VISIBLE);
        }
    }

    public void hideCotent() {
        if (vg_page_content != null) {
            vg_page_content.setVisibility(View.INVISIBLE);
        }
    }


    public Object getArgument(String key, Object defaultObj) {
        if (getArguments() != null) {
            Object o = getArguments().get(key);
            if (o == null) {
                return defaultObj;
            } else {
                return o;
            }
        }
        return defaultObj;
    }

    /***
     * 是否父类只有这一个可显示的界面
     * @param singleInParent
     */
    public void setSingleInParent(boolean singleInParent) {
        isSingleInParent = singleInParent;
    }

    public boolean isSingleInParent() {
        return isSingleInParent;
    }
    @Override
    public void onResume() {
        super.onResume();
        currentFragment = this;
        if(isSingleInParent())setOnResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        if(isSingleInParent())setOnPause();
    }
    public void setOnResume(){
    }
    public void setOnPause(){
    }
}
