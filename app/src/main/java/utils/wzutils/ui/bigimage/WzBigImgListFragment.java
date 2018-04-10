package utils.wzutils.ui.bigimage;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import utils.wzutils.AppTool;
import utils.wzutils.ImgTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.parent.WzParentFragment;
import utils.wzutils.ui.lunbo.LunBoTool;

/**
 * Created by wz on 2017/9/15.
 *
 * WzBigImgListFragment.init(R.layout.include_lunbo_container,R.id.adsContainer,R.id.vg_viewpager_btn,R.layout.include_lunbo_dot_kuang,R.id.cb_dot);
 * 同轮播的初始化
 *
 */

public class WzBigImgListFragment extends WzParentFragment {
    List<String> stringArrayList = new ArrayList<>();
    int currentIndex;

    public static int include_lunbo_container;
    public static int adsContainer;
    public  static int vg_viewpager_btn;
    public static int include_lunbo_dot_kuang;
    public  static int cb_dot;

    public static void init(int include_lunbo_container,int adsContainer,int vg_viewpager_btn,int include_lunbo_dot_kuang,int cb_dot){
        WzBigImgListFragment.include_lunbo_container=include_lunbo_container;
        WzBigImgListFragment.adsContainer=adsContainer;
        WzBigImgListFragment.vg_viewpager_btn=vg_viewpager_btn;
        WzBigImgListFragment.include_lunbo_dot_kuang=include_lunbo_dot_kuang;
        WzBigImgListFragment.cb_dot=cb_dot;
    };


    @Override
    public int initContentViewId() {
        return include_lunbo_container;
    }

    @Override
    public void initData() {
        parent.setBackgroundColor(Color.BLACK);
        if(getArguments()!=null){
            stringArrayList = getArguments().getStringArrayList("stringArrayList");
            currentIndex = getArguments().getInt("currentIndex", 0);
        }

        ArrayList<LunBoTool.LunBoData> lunBoDataArrayList=new ArrayList<>();
        for(String tem:stringArrayList){
            LunBoTool.LunBoData lunBoData=new LunBoTool.LunBoData(tem);
            lunBoDataArrayList.add(lunBoData);
        }

        LunBoTool.initAdsBigImage(parent,adsContainer, vg_viewpager_btn,include_lunbo_dot_kuang, cb_dot, 0, lunBoDataArrayList);
        ((ViewPager) parent.findViewById(adsContainer)).setCurrentItem(currentIndex);
    }
    @Override
    public void initListener() {

    }


    public void go(int currentIndex, String... src) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (int i = 0; i < src.length; i++) {
            stringArrayList.add(src[i]);
        }
        go(currentIndex, stringArrayList);
    }


    public void go(int currentIndex, List<String> stringArrayList) {
        ArrayList <String> arrayList=new ArrayList<>(stringArrayList);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("stringArrayList", arrayList);
        bundle.putInt("currentIndex", currentIndex);
        setArguments(bundle);
      //  super.go();
    }
}
