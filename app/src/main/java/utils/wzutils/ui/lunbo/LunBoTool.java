package utils.wzutils.ui.lunbo;

/**
 * Created by kk on 2017/3/23.
 */

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import utils.wzutils.AppTool;
import utils.wzutils.ImgTool;
import utils.wzutils.JsonTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.TestData;
import utils.wzutils.common.ViewTool;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzImageView;
import utils.wzutils.ui.bigimage.PinchImageView;

/***
 * 用于轮播的
 *
 */
public class LunBoTool {
    public static final int maxCount = 3000;
    public static final int beginPosition = maxCount / 2;
    static final int key = ViewTool.initKey();

    /********轮播小红点布局
     *
     *
     * <?xml version="1.0" encoding="utf-8"?>
     <LinearLayout
     xmlns:android="http://schemas.android.com/apk/res/android"
     android:layout_width="wrap_content" android:orientation="horizontal"
     android:layout_height="wrap_content">
     <CheckBox
     android:id="@+id/cb_dot"
     android:layout_width="10dp"
     android:layout_height="10dp"
     android:button="@drawable/selector_lunbo_dot"
     />
     <View
     android:layout_width="3dp"
     android:layout_height="1dp"></View>
     </LinearLayout>
     */


    public static void initAds(final View parent, final int adsContainerId, final int vg_viewpager_btnId, final int dotBtnLayoutResId, final int dotBtnCompoundButtonResId, final int autoPlayDuration, List<LunBoData> lunBoDatas) {
        try {
            ViewPager adsContainer = (ViewPager) parent.findViewById(adsContainerId);
            LinearLayout vg_viewpager_btn = (LinearLayout) parent.findViewById(vg_viewpager_btnId);
            initAds(adsContainer, vg_viewpager_btn, dotBtnLayoutResId, dotBtnCompoundButtonResId, autoPlayDuration, lunBoDatas, false);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 查看大图用的
     * @param parent
     * @param adsContainerId
     * @param vg_viewpager_btnId
     * @param dotBtnLayoutResId
     * @param dotBtnCompoundButtonResId
     * @param autoPlayDuration
     * @param lunBoDatas
     */
    public static void initAdsBigImage(final View parent, final int adsContainerId, final int vg_viewpager_btnId, final int dotBtnLayoutResId, final int dotBtnCompoundButtonResId, final int autoPlayDuration, List<LunBoData> lunBoDatas) {
        try {
            ViewPager adsContainer = (ViewPager) parent.findViewById(adsContainerId);
            LinearLayout vg_viewpager_btn = (LinearLayout) parent.findViewById(vg_viewpager_btnId);
            initAds(adsContainer, vg_viewpager_btn, dotBtnLayoutResId, dotBtnCompoundButtonResId, 0, lunBoDatas, true);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     *
     * @param adsContainer                  ViewPager   放轮播图片的
     * @param vg_viewpager_btn              LinearLayout   放轮播图片下面的 小红点的
     * @param dotBtnCompoundButtonResId     dotBtnCompoundButtonResId  小红点里面 的CompoundButton id ， 这个用于显示当前选中的
     * @param lunBoDatas                    轮播数据
     * @param autoPlayDuration              自动播放间隔时间， 大于0 并且 有超过1页的数据就自动播放，
     *
     * @param  imageCanScale 查看大图用的
     *
     */
    public static void initAds(final ViewPager adsContainer, final LinearLayout vg_viewpager_btn, final int dotBtnLayoutResId, final int dotBtnCompoundButtonResId, final int autoPlayDuration, List<LunBoData> lunBoDatas, final boolean imageCanScale) {
        try {
            if (lunBoDatas == null) lunBoDatas = new ArrayList<>();
            if (lunBoDatas.size() < 1) {
                lunBoDatas.add(new LunBoData("",null));
            }
            final List<LunBoData> datasList = lunBoDatas;

            adsContainer.setOffscreenPageLimit(1);
            /**
             * 添加底部小点
             */
            {
                String data = JsonTool.toJsonStr(lunBoDatas);
                String oldData = "" + adsContainer.getTag();
                if (oldData.equals(data)) {
                    return;
                } else {
                    adsContainer.setTag(data);
                }
                vg_viewpager_btn.removeAllViews();
                for (int i = 0; i < lunBoDatas.size(); i++) {
                    View dotBtn = LayoutInflaterTool.getInflater(20, dotBtnLayoutResId).inflate();
                    vg_viewpager_btn.addView(dotBtn);
                }
            }

            final PagerAdapter adAdapter = new PagerAdapter() {
                @Override
                public int getCount() {
                    if (datasList == null) return 0;
                    if (datasList.size() == 1) return 1;//1个的时候禁止无限滑动
                    if (imageCanScale) return datasList.size();
                    return maxCount;
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int positionIn) {
                    if (!imageCanScale) {
                        positionIn = CommonTool.loopPosition(datasList.size(), beginPosition, positionIn);
                    }
                    final LunBoData lunBoData = datasList.get(positionIn);
                    ImageView imageView = new WzImageView(container.getContext());//不能用 curractivity
                    if (imageCanScale) {
                        imageView = new PinchImageView(container.getContext());
                    }
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    container.addView(imageView);
                    ImgTool.loadImage(datasList.get(positionIn).imageUrl, imageView);
                    if(lunBoData.lunBoClickListener!=null){
                        imageView.setOnClickListener(new WzViewOnclickListener() {
                            @Override
                            public void onClickWz(View v) {
                                if (lunBoData.lunBoClickListener != null){
                                    lunBoData.lunBoClickListener.onClickLunBo(v, lunBoData);
                                }
                            }
                        });
                    }

                    return imageView;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    try {
                        container.removeView((View) object);
                    } catch (Exception e) {
                        LogTool.ex(e);
                    }
                }
            };
            adsContainer.setAdapter(adAdapter);
            {//设置自动轮播
                if (autoPlayDuration > 0 && datasList.size() > 1) {
                    if (ViewTool.getTag(adsContainer, key) == null) {
                        Runnable runnableSwitch = new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (adsContainer.isAttachedToWindow()) {//必须要在界面中才执行
                                        Object runableObj = ViewTool.getTag(adsContainer, key);
                                        if (this.equals(runableObj)) {
                                            if (adsContainer.isShown()) {
                                                int count = adAdapter.getCount();
                                                int current = adsContainer.getCurrentItem();
                                                current++;
                                                if (current >= count) {
                                                    current = 0;
                                                }
                                                adsContainer.setCurrentItem(current);
                                            }
                                            adsContainer.postDelayed(this, autoPlayDuration);
                                            return;
                                        }
                                    }
                                } catch (Exception e) {
                                    LogTool.ex(e);
                                }
                            }
                        };
                        ViewTool.setTag(adsContainer, runnableSwitch, key);
                        adsContainer.postDelayed(runnableSwitch, autoPlayDuration);
                    }
                }

            }


            adsContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int positionIn) {
                    try {


                        if (!imageCanScale)
                            positionIn = CommonTool.loopPosition(datasList.size(), beginPosition, positionIn);
                        for (int i = 0; i < vg_viewpager_btn.getChildCount(); i++) {
                            CompoundButton rb = (CompoundButton) vg_viewpager_btn.getChildAt(i).findViewById(dotBtnCompoundButtonResId);
                            if (positionIn == i) {
                                rb.setChecked(true);
                            } else {
                                rb.setChecked(false);
                            }
                        }
                    } catch (Exception e) {
                        LogTool.ex(e);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
            adsContainer.setCurrentItem(beginPosition, false);
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }

    /*** 轮播布局
     *
     *
     *
     * <?xml version="1.0" encoding="utf-8"?>
     <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
     xmlns:app="http://schemas.android.com/apk/res-auto"
     xmlns:tools="http://schemas.android.com/tools"
     android:layout_width="match_parent"
     android:layout_height="match_parent"
     android:contentDescription="广告"
     android:id="@+id/vg_guanggao"
     >

     <utils.wzutils.ui.WzViewPager
     android:id="@+id/adsContainer"
     android:layout_width="match_parent"
     android:layout_height="match_parent"/>
     <LinearLayout
     android:id="@+id/vg_viewpager_btn"
     android:layout_width="wrap_content"
     android:layout_height="wrap_content"
     android:layout_alignParentBottom="true"
     android:layout_centerHorizontal="true"
     android:layout_marginBottom="10dp"
     android:orientation="horizontal"></LinearLayout>
     </RelativeLayout>
     *
     *
     *
     *
     *
     *
     */

    public static class LunBoData {
        public Object imageUrl = "";
        LunBoClickListener lunBoClickListener;

        public LunBoData(Object imageUrl) {
            this.imageUrl =  imageUrl;
        }

        public LunBoData(Object imageUrl, LunBoClickListener lunBoClickListener) {
            this.imageUrl =  imageUrl;
            this.lunBoClickListener = lunBoClickListener;
        }

        public static List<LunBoData> getTest() {
            List<LunBoData> lunBoDatas = new ArrayList<>();
            lunBoDatas.add(new LunBoData(TestData.getTestImgUrl(1)));
            lunBoDatas.add(new LunBoData(TestData.getTestImgUrl(2)));
            lunBoDatas.add(new LunBoData(TestData.getTestImgUrl(3)));
            lunBoDatas.add(new LunBoData(TestData.getTestImgUrl(4)));
            return lunBoDatas;
        }

        public static List<LunBoData> initData(Object... strings) {
            List<LunBoData> lunBoDatas = new ArrayList<>();
            for (Object str : strings) {
                lunBoDatas.add(new LunBoData( str));
            }
            return lunBoDatas;
        }
    }

    public static abstract class LunBoClickListener extends WzViewOnclickListener {

        public abstract void onClickLunBo(View v, LunBoData lunBoData);

        @Override
        public void onClickWz(View v) {

        }
    }
}
