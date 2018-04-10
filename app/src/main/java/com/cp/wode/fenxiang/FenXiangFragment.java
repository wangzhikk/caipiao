package com.cp.wode.fenxiang;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.cp.wode.Data_personinfo_query;

import java.util.ArrayList;
import java.util.List;

import utils.tjyutils.common.Common;
import utils.tjyutils.parent.ParentFragment;
import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.TabTitleTool;
import utils.wzutils.ui.WzViewPager;

/**
 * Created by kk on 2017/3/23.
 */

public class FenXiangFragment extends ParentFragment {


    ViewGroup vg_tab_fenxiang;
    WzViewPager viewPager_fenxiang;

    TextView tv_fenxiang_shouyi_nickname,tv_fenxiang_shouyi_amount,tv_fenxiang_shouyi_num,tv_fenxiang_shouyi_dengji;
    ImageView imgv_fenxiang_dengji;
    @Override
    public int initContentViewId() {
        return R.layout.fenxiang;
    }
    @Override
    public void initData() {

        UiTool.setSoftInputModeReSize(getActivityWz());
        titleTool.setTitle("代理中心");
        titleTool.setTitleRightTv("代理说明", new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                new FenXiangGuiZeFragment().go();
            }
        });

        TabTitleTool.TabData tabData_kaihu=new TabTitleTool.TabData("开户分享","",new FenXiangKaiHuFragment(),R.layout.fenxiang_tab_item,R.id.cb_tab);
        tabData_kaihu.setDeviderView(LayoutInflaterTool.getInflater(3,R.layout.fenxiang_tab_item_line).inflate());

        TabTitleTool.TabData tabData_shouyi=new TabTitleTool.TabData("代理收益","",new WoDeShouYiFragment(),R.layout.fenxiang_tab_item,R.id.cb_tab);
        tabData_shouyi.setDeviderView(LayoutInflaterTool.getInflater(3,R.layout.fenxiang_tab_item_line).inflate());

        TabTitleTool.TabData tabData_lianjie=new TabTitleTool.TabData("我的分享","",new WoDeFenXiangFragment(),R.layout.fenxiang_tab_item,R.id.cb_tab);

        List<TabTitleTool.TabData> tabDataList=new ArrayList<>();
        tabDataList.add(tabData_kaihu);
        tabDataList.add(tabData_shouyi);
        tabDataList.add(tabData_lianjie);
        TabTitleTool tabTitleTool=new TabTitleTool(getChildFragmentManager(),vg_tab_fenxiang,viewPager_fenxiang,tabDataList);
        tabTitleTool.click(0);

        loadData();
    }

    public void loadData(){
        Data_share_info.load(new HttpUiCallBack<Data_share_info>() {
            @Override
            public void onSuccess(Data_share_info data) {
                if(data.isDataOkAndToast()){

                    imgv_fenxiang_dengji.setImageResource(Data_login_validate.getData_login_validate().getUserInfo().getTypeResImg());
                    setTextView(tv_fenxiang_shouyi_nickname, Data_login_validate.getData_login_validate().getUserInfo().base_nickname);
                    setTextView(tv_fenxiang_shouyi_dengji, Data_login_validate.getData_login_validate().getUserInfo().getTypeText());
                    setTextView(tv_fenxiang_shouyi_amount, data.shareBonus);
                    setTextView(tv_fenxiang_shouyi_num, data.recommendTotal  );


                }
            }
        });

    }
    @Override
    public void initListener() {



    }



}
