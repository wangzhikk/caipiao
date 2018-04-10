package utils.wzutils.ui.pullrefresh;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;


/***
 *    compile 'com.scwang.smartrefresh:SmartRefreshLayout:1.0.5.1'
 compile 'com.scwang.smartrefresh:SmartRefreshHeader:1.0.5.1'//没有使用特殊Header，可以不加这行
 */
public class WzRefreshLayout extends SmartRefreshLayout {
    public WzRefreshLayout(Context context) {
        super(context);
    }

    public WzRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WzRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void stopRefresh(PageControl pageControl) {
        finishRefresh();
        finishLoadMore();
//        if(pageControl.getCurrPage()==1){
//            finishRefresh();
//        }else {
//            finishLoadMore();
//        }
    }




//    public SmartRefreshLayout finishLoadMore(int delayed, final boolean success, final boolean noMoreData) {
//        return super.finishLoadMore(0,success,noMoreData);
//    }
//
    @Override
    public SmartRefreshLayout finishRefresh(int delayed, boolean success) {
        return super.finishRefresh(0, success);
    }











    static {
         final int tvColor=Color.parseColor("#979797");
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setHeaderHeight(50);
                layout.setFooterHeight(50);
                layout.setEnableClipHeaderWhenFixedBehind(true);

                ClassicsHeader classics=new ClassicsHeader(context);
                classics.setAccentColor(tvColor);
                classics.setPrimaryColor(Color.WHITE);
                classics.setSpinnerStyle(SpinnerStyle.FixedBehind);
                classics.setTextSizeTitle(13);
                classics.setDrawableSize(18);
                classics.setFinishDuration(0);//设置刷新完成显示的停留时间
                classics.setDrawableArrowSize(15);
                classics.setEnableLastTime(false);
                return classics;//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                ClassicsFooter classics=new ClassicsFooter(context);
                classics.setAccentColor(tvColor);
                classics.setPrimaryColor(Color.WHITE);
                classics.setTextSizeTitle(13);
                classics.setDrawableSize(18);
                classics.setFinishDuration(0);//设置刷新完成显示的停留时间
                return classics;
            }
        });
    }

    public void bindLoadData(final LoadListDataInterface loadListDataInterface, final PageControl pageControl) {
        setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                loadListDataInterface.loadData(pageControl.getNextPageNum());
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                loadListDataInterface.loadData(pageControl.init());
            }
        });
        autoRefresh();
    }

    public static interface LoadListDataInterface{
        public void loadData(final  int page);
    }

}
