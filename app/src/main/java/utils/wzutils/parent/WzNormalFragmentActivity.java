package utils.wzutils.parent;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import utils.wzutils.AppTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.ViewTool;

/**


  <?xml version="1.0" encoding="utf-8"?>
  <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:app="http://schemas.android.com/apk/res-auto"
  xmlns:tools="http://schemas.android.com/tools"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  android:orientation="vertical"
  android:background="@color/bg"
  android:id="@+id/fragment_container"
  >
  </RelativeLayout>

 */
public abstract class WzNormalFragmentActivity extends WzParentActivity {

    Fragment currentFragment = null;
    /***
     * 重新设置返回事件
     */
    OnBackPressedListener onBackPressedListener;

    /***
     * 跳转到一个Fragment
     */
    private void go(WzParentFragment fragment, boolean inCurrActivity) {
        if (inCurrActivity) {//就在当前页面 ， 不跳转
            if (AppTool.currActivity instanceof WzNormalFragmentActivity) {
                ((WzNormalFragmentActivity) AppTool.currActivity).setFragment(fragment);
            }
        } else {
            go(fragment);
        }

    }

    /***
     * 跳转到一个Fragment
     */
    public void go(WzParentFragment fragment) {
        go(fragment, null);
    }

    /***
     * 跳转到一个Fragment
     */
    public void go(WzParentFragment fragment, Intent intent) {
        if (intent == null) {
            intent = new Intent();
        }
        intent.setClass(AppTool.currActivity, getClass());
        intent.putExtra("fragment", fragment);
        intent.putExtra("arguments", fragment.getArguments());
        AppTool.currActivity.startActivityForResult(intent, 1);
    }

    /***
     * @param fragment     要去向的fragment
     * @param fromFragment 由哪个 fragment 启动的
     */
    public void goForResult(WzParentFragment fragment, WzParentFragment fromFragment, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(AppTool.currActivity, getClass());
        intent.putExtra("fragment", fragment);
        intent.putExtra("arguments", fragment.getArguments());
        fromFragment.startActivityForResult(intent, requestCode);
    }

    /***
     * 返回当前界面资源文件
     * @return
     */
    public abstract int getContentViewId();

    /***
     * 返回当前界面用于替换fragment 的id
     * @return
     */
    public abstract int getContentFragmentId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ViewTool.initViews(getWindow().getDecorView(), this, null);

        try {
            Fragment fragment = (Fragment) getIntent().getSerializableExtra("fragment");
            Bundle arguments = getIntent().getBundleExtra("arguments");
            fragment.setArguments(arguments);
            setFragment(fragment);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    void setFragment(Fragment fragment) {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (currentFragment != null) {
            transaction.hide(currentFragment);
        }
        if (!fragment.isAdded()) {
            transaction.add(getContentFragmentId(), fragment);
        } else {
            transaction.show(fragment);
        }
        currentFragment = fragment;
        transaction.addToBackStack("" + fragment);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if(currentFragment!=null&&currentFragment instanceof WzParentFragment){
            if(((WzParentFragment) currentFragment).onBackPressed()){
                return;
            }
        }
        if (getOnBackPressedListener() != null) {
            if (getOnBackPressedListener().onBackPressed()) {
                return;
            }
        }


        if (getSupportFragmentManager().getBackStackEntryCount() < 2) {
            finish();
        } else {
            super.onBackPressed();
        }

    }

    public OnBackPressedListener getOnBackPressedListener() {
        return onBackPressedListener;
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    public interface OnBackPressedListener {
        boolean onBackPressed();
    }


}
