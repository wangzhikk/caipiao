package utils.wzutils.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.parent.WzParentFragment;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * abc on 2017/5/27.
 * 用于tab类型的title
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * if(tabTitleTool==null){
 * String []labs=new String[]{"未使用","已使用","已过期"};
 * List<TabTitleTool.TabData> tabDatas=new ArrayList<>();
 * for(String tem:labs){
 * TabTitleTool.TabData tabData=new TabTitleTool.TabData(tem,tem,YouHuiQuanListFragment.getIntance(tem),R.layout.fragment_youhuiquan_title_item,R.id.cb_btn);
 * tabDatas.add(tabData);
 * }
 * tabTitleTool=new TabTitleTool(getChildFragmentManager(),vg_title_tab,viewPager,tabDatas);
 * tabTitleTool.click(0);
 * }
 */

public class TabTitleTool {
    ViewGroup tabParent;
    CommonButtonTool commonButtonTool;
    ViewPager viewPager;
    FragmentManager fragmentManager;


    List<TabData> tabDatas = new ArrayList<>();

    /***
     *
     * @param tabParent  用于放tab 的 view
     * @param viewPager  用于放内容的 viewpager
     */
    public TabTitleTool(FragmentManager fragmentManager, ViewGroup tabParent, ViewPager viewPager, List<TabData> dataList) {
        this.fragmentManager = fragmentManager;
        this.tabParent = tabParent;
        this.viewPager = viewPager;
        setTabDatas(dataList);
    }
    public List<TabData> getTabDatas() {
        if(tabDatas==null)tabDatas=new ArrayList<>();
        return tabDatas;
    }

    public void setTabDatas(List<TabData> tabDatasIn) {
        tabDatas.clear();
        tabParent.removeAllViews();
        if (tabDatasIn != null) {
            for (TabData tabData : tabDatasIn) {
                addTab(tabData);
            }
        }
        initViewPager();
    }

     void addTab(final TabData tabData) {
        try {
            View item = LayoutInflaterTool.getInflater(5, tabData.layoutItemId).inflate();
            tabParent.addView(item, tabData.tablayoutParams);
            if(tabData.deviderView!=null){
                tabParent.addView(tabData.deviderView);
            }
            if (commonButtonTool == null) {
                commonButtonTool = new CommonButtonTool();
            }
            tabDatas.add(tabData);
            CompoundButton compoundButton = (CompoundButton) item.findViewById(tabData.commonBtnId);
            compoundButton.setText(tabData.tabText);
            commonButtonTool.add(compoundButton);

            compoundButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        commonButtonTool.setChecked(buttonView);
                        viewPager.setCurrentItem(commonButtonTool.getAllButtons().indexOf(buttonView));
                        buttonView.getPaint().setFakeBoldText(true);
                    }else {
                        buttonView.getPaint().setFakeBoldText(false);
                    }
                }
            });
            item.setOnClickListener(new WzViewOnclickListener() {
                @Override
                public void onClickWz(View v) {
                    try {
                        CompoundButton cb_btn = (CompoundButton) v.findViewById(tabData.commonBtnId);
                        cb_btn.performClick();
                    } catch (Exception e) {
                        LogTool.ex(e);
                    }
                }
            });
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public void initViewPager() {
        try {
            viewPager.setOffscreenPageLimit(tabDatas.size());
            viewPager.setAdapter(new FragmentPagerAdapter(fragmentManager) {
                @Override
                public Fragment getItem(int position) {
                    return tabDatas.get(position).fragment;
                }

                @Override
                public int getCount() {
                    return tabDatas.size();
                }

            });

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }


                @Override
                public void onPageSelected(int position) {
                    try {
                        if(oldFragment!=null){
                            oldFragment.setOnPause();
                        }
                        commonButtonTool.getAllButtons().get(position).performClick();
                        tabDatas.get(position).fragment.setOnResume();
                        oldFragment= tabDatas.get(position).fragment;
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            oldFragment=tabDatas.get(0).fragment;
            oldFragment.setOnResume();
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }
    WzParentFragment oldFragment;
    public void click(int position) {
        try {
            commonButtonTool.getAllButtons().get(position).performClick();
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    public TabData getCurrTabData(){
        try {
            for(int i=0;i<commonButtonTool.getAllButtons().size();i++){
                if(commonButtonTool.getAllButtons().get(i).isChecked()){
                    return tabDatas.get(i);
                }
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        LogTool.ex(new Throwable("出错了， 没找到当前界面"));
        return null;
    }

    public static class TabData {
        public String tabText;//标题
        public Serializable type;//类型
        public WzParentFragment fragment;//内容fargment
        public int layoutItemId;//tab 标题的 资源文件
        public int commonBtnId;//按钮
        public ViewGroup.LayoutParams tablayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        public View deviderView;
        public void setDeviderView(View deviderView){
            this.deviderView=deviderView;
        }
        public TabData(
                String tabText,
                Serializable type,
                WzParentFragment fragment,
                int layoutItemId,
                int commonBtnId,
                ViewGroup.LayoutParams tablayoutParams

        ) {
            this.tabText = tabText;
            this.type = type;
            this.fragment = fragment;
            this.layoutItemId = layoutItemId;
            this.commonBtnId = commonBtnId;
            this.tablayoutParams = tablayoutParams;
            init();
        }

        public TabData(
                String tabText,
                Serializable type,
                WzParentFragment fragment,
                int layoutItemId,
                int commonBtnId

        ) {
            this.tabText = tabText;
            this.type = type;
            this.fragment = fragment;
            this.layoutItemId = layoutItemId;
            this.commonBtnId = commonBtnId;
            init();
        }
        public void init(){
            fragment.setSingleInParent(false);
        }
    }
}
