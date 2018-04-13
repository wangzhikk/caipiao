package com.cp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.cp.cp.Data_login_validate;
import com.cp.cp.Data_version;
import com.cp.shouye.ShouYeFragment;
import com.cp.wode.Data_personinfo_query;
import com.cp.wode.WoDeFragment;
import com.cp.xiaoxi.XiaoXiAllFragment;
import com.cp.xiaoxi.XiaoXiListFragment;

import utils.tjyutils.parent.ParentActivity;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.AppTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.common.ViewTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.CommonButtonTool;
import utils.wzutils.update.Version;

public class MainActivity extends ParentActivity {


    View   rb_shouye, rb_wode,rb_xiaoxi;
    public static  MainActivity currMainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currMainActivity=this;
        setContentView(R.layout.activity_home);
        ViewTool.initViews(getWindow().getDecorView(), this, null);
        initListener();
        rb_shouye.performClick();
       // Data_queryversion.checkUpData();
        onNewIntent(getIntent());
        Data_personinfo_query.load(null);
        checkVersion();
    }
    public void checkVersion(){
        Data_version.load(new HttpUiCallBack<Data_version>() {
            @Override
            public void onSuccess(Data_version data) {
                if(data.isDataOk()){
                    Version.checkUpDate(MainActivity.this,data.appVersion);
                }
            }
        });
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        try {
            int page = intent.getIntExtra("page", currPage);
            View view = findViewById(page);
            if (view != null) {
                view.performClick();
            }

          //  PushControlTool.OnNewIntent(this,intent);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }



    CommonButtonTool commonButtonTool = new CommonButtonTool();
    ParentFragment shouYeFragment = new ShouYeFragment();
    ParentFragment xiaoXiFragment = new XiaoXiAllFragment();
    ParentFragment woDeFragment=new WoDeFragment();

    void initBottomBtns() {
        WzViewOnclickListener onclickListenerParent = new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                v.findViewById(R.id.rb_shouye_item).performClick();
            }
        };
        rb_shouye.setOnClickListener(onclickListenerParent);
        rb_xiaoxi.setOnClickListener(onclickListenerParent);
        rb_wode.setOnClickListener(onclickListenerParent);
        initBottomBtn(rb_shouye);
        initBottomBtn(rb_xiaoxi);
        initBottomBtn(rb_wode);


    }

    void initBottomBtn(View v) {
        final CompoundButton compoundButton = (CompoundButton) v.findViewById(R.id.rb_shouye_item);
        compoundButton.setTag(v);
        compoundButton.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View tem) {
                View v = (View) tem.getTag();
                if (v.equals(rb_wode)) {
                     if(Data_login_validate.isLoginOrGo()){
                         currPage = R.id.rb_wode;
                         setFragment(woDeFragment);
                     }else {
                         compoundButton.setChecked(!compoundButton.isChecked());
                     }
                } else if (v.equals(rb_shouye)) {
                         currPage = R.id.rb_shouye;
                         setFragment(shouYeFragment);
                 }else if (v.equals(rb_xiaoxi)) {
                     if(Data_login_validate.isLoginOrGo()){
                         currPage = R.id.rb_xiaoxi;
                         setFragment(xiaoXiFragment);
                         showBottomMsg(rb_xiaoxi,"");
                     }else {
                         compoundButton.setChecked(!compoundButton.isChecked());
                     }

                 }
            }
        });
        commonButtonTool.add(compoundButton);

        int drawable=R.drawable.selector_shouye_shouye;
        String text="首页";
         if (v.equals(rb_shouye)) {
            drawable=R.drawable.selector_shouye_shouye;
            text="首页";
        } else if (v.equals(rb_xiaoxi)) {
            drawable=R.drawable.selector_shouye_xiaoxi;
            text="消息";
        } else if (v.equals(rb_wode)) {
             drawable=R.drawable.selector_shouye_wode;
             text="我的";
         }
        UiTool.setCompoundDrawables(this,compoundButton,0,drawable,0,0);
        UiTool.setTextView(compoundButton,text);
    }

    /***
     * 显示底部按钮信息数量
     * @param v
     * @param count
     */
    public void showBottomMsg(View v, String count){
        TextView tv_shouye_msg= (TextView) v.findViewById(R.id.tv_shouye_msg);
        if(StringTool.notEmpty(count)){
            //  UiTool.setTextView(tv_shouye_msg,""+count);
            tv_shouye_msg.setVisibility(View.VISIBLE);
        }else {
            tv_shouye_msg.setVisibility(View.INVISIBLE);
        }
    }

    public void initListener() {
        initBottomBtns();
    }


    ParentFragment currentFragment;

    void setFragment(ParentFragment fragment) {
        if(currentFragment==fragment)return;
        fragment.setSingleInParent(false);

        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!fragment.isAdded()) {
            transaction.add(R.id.fragment_container, fragment);
        } else {
            transaction.show(fragment);
        }
        if (currentFragment != null && currentFragment != fragment) {
            transaction.hide(currentFragment);
            getCurrFragmentIncludeChild().setOnPause();
        }
        currentFragment = fragment;
        transaction.commit();
        getCurrFragmentIncludeChild().setOnResume();
    }

    /***
     * 获取实际显示的 fragment
     */
    public ParentFragment getCurrFragmentIncludeChild(){
        return currentFragment;
    }
    @Override
    protected void onResume() {
        super.onResume();
        getCurrFragmentIncludeChild().setOnResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        getCurrFragmentIncludeChild().setOnPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        currMainActivity=null;
    }

    @Override
    public void onBackPressed() {
        UiTool.backToQuit("再按一次退出程序");
    }

    int currPage = R.id.rb_shouye;

    public static void goWoDe() {
        Intent intent = new Intent(AppTool.getApplication(), MainActivity.class);
        intent.putExtra("page", R.id.rb_wode);
        AppTool.currActivity.startActivity(intent);
    }

    /***
     * 设置有新的消息
     */
    public static void setHasNewMsg() {
        try {
            if(currMainActivity!=null){
                if(!(currMainActivity.currentFragment instanceof WoDeFragment)){
                    currMainActivity. showBottomMsg(currMainActivity.rb_xiaoxi,"New");
                }
            }
        }catch (Exception e){
            LogTool.ex(e);
        }

    }

    public static void go() {
        Intent intent = new Intent(AppTool.getApplication(), MainActivity.class);
        AppTool.currActivity.startActivity(intent);
        AppTool.currActivity.finish();
    }
}
