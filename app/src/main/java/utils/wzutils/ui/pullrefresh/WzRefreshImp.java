package utils.wzutils.ui.pullrefresh;//package utils.gfreeutils.ui;//package utils.aixiangutils.ui;//package utils.wzutils.ui.pullrefresh;


import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


public class WzRefreshImp extends WzRefresh {
    static int layout_header;
    static int res_tv_refresh;
    static int res_progressBar;
    /***
     * 默认的刷新动作，
     * @see #setOnPullRefreshListener(OnPullRefreshListener)
     */
    OnPullRefreshListener onPullRefreshListener=new OnPullRefreshListener() {
        @Override
        public void onPullDown() {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopRefresh();
                }
            },1000);
        }

        @Override
        public void onPullUp() {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopRefresh();
                }
            },1000);
        }
    };
    ProgressBar progressBar_header = null;
    View wz_recycle_header;
    View wz_recycle_footer;

    TextView tv_refresh_header;
    String txt_header = "下拉刷新";
    String txt_footer = "上拉刷新";
    ProgressBar progressBar_footer = null;
    TextView tv_refresh_footer;
    boolean useFooter = true;
    boolean useHeader = true;
    public WzRefreshImp(Context context) {
        super(context);
    }


    public WzRefreshImp(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public WzRefreshImp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static void init(int layout_header, int res_tv_refresh, int res_progressBar) {
        WzRefreshImp.layout_header = layout_header;
        WzRefreshImp.res_tv_refresh = res_tv_refresh;
        WzRefreshImp.res_progressBar = res_progressBar;
    }

    /***
     * 设置刷新监听
     * @param onPullRefreshListener
     */
    public void setOnPullRefreshListener(OnPullRefreshListener onPullRefreshListener) {
        this.onPullRefreshListener = onPullRefreshListener;
    }

    @Override
    public void stopRefresh() {
        super.stopRefresh();
        if (progressBar_header != null) progressBar_header.setVisibility(View.INVISIBLE);
        if (progressBar_footer != null) progressBar_footer.setVisibility(View.INVISIBLE);
    }

    public void setUseFooter(boolean useFooter) {
        this.useFooter = useFooter;
        if(useFooter){
            setFooterView(wz_recycle_footer);
        }else {
            setFooterView(null);
        }

    }

    public void setUseHeader(boolean useHeader) {
        this.useHeader = useHeader;
        if(useHeader){
            setHeaderView(wz_recycle_header);
        }else {
            setHeaderView(null);
        }

    }

    @Override
    public void initHeaderAndFooter() {
        final WzRefresh wzRefresh = this;
        if (useHeader&&wz_recycle_header==null) {//不判断空，会导致如果设置了 footer 为null  那么这里也会重新执行， 就会导致 没有measure 显示不出来， 下面footer一样
            wz_recycle_header = LayoutInflater.from(getContext()).inflate(layout_header, null);
            tv_refresh_header = ((TextView) wz_recycle_header.findViewById(res_tv_refresh));
            progressBar_header = ((ProgressBar) wz_recycle_header.findViewById(res_progressBar));
            progressBar_header.setVisibility(View.INVISIBLE);
            wzRefresh.setHeaderView(wz_recycle_header);
        }

        if (useFooter&&wz_recycle_footer==null) {
            wz_recycle_footer = LayoutInflater.from(getContext()).inflate(layout_header, null);
            tv_refresh_footer = ((TextView) wz_recycle_footer.findViewById(res_tv_refresh));
            progressBar_footer = ((ProgressBar) wz_recycle_footer.findViewById(res_progressBar));
            progressBar_footer.setVisibility(View.INVISIBLE);
            wzRefresh.setFooterView(wz_recycle_footer);
        }


        wzRefresh.setRefreshListener(new RefreshListener() {
            @Override
            public void onRefreshPullDown() {
                if (tv_refresh_header != null) {
                    setHeaderText("正在刷新");
                    progressBar_header.setVisibility(View.VISIBLE);
                    if (onPullRefreshListener != null) {
                        onPullRefreshListener.onPullDown();
                    }
                }

            }

            @Override
            public void onRefreshPullUp() {
                if (tv_refresh_footer != null) {
                    setFooterText("正在刷新");
                    progressBar_footer.setVisibility(View.VISIBLE);
                    if (onPullRefreshListener != null) {
                        onPullRefreshListener.onPullUp();
                    }
                }
            }

            @Override
            public void onPullProgressChanged(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int distance = Math.abs(scrollY);
                if (scrollY < 0) {
                    if (wz_recycle_header != null) {
                        if (wzRefresh.isReadyToRefresh()) {
                            String tem = "释放刷新";
                            if (!tem.equals(txt_header)) {
                                setHeaderText(tem);
                            }
                        } else if (wzRefresh.isReadyToBackBegin()) {
                            String tem = "下拉刷新";
                            if (!tem.equals(txt_header)) {
                                setHeaderText(tem);
                            }
                        }
//                        int max=wz_recycle_header.getHeight();
//                        float progress=(distance*1.0f/max);
//                        if(progress>1)progress=1;
//                        wz_recycle_header.setScaleX(progress);
//                        wz_recycle_header.setScaleY(progress);
//                        wz_recycle_header.setAlpha(progress);
                    }

                }
                if (scrollY > 0) {
                    if (wz_recycle_footer != null) {

                        if (wzRefresh.isReadyToRefresh()) {
                            String tem = "释放刷新";
                            if (!tem.equals(txt_footer)) {
                                setFooterText(tem);
                            }
                        } else if (wzRefresh.isReadyToBackBegin()) {
                            String tem = "上拉刷新";
                            if (!tem.equals(txt_footer)) {
                                setFooterText(tem);
                            }
                        }


//                        int max=footer.getHeight();
//                        float progress=(distance*1.0f/max);
//                        if(progress>1)progress=1;
//                        footer.setScaleX(progress);
//                        footer.setScaleY(progress);
//                        footer.setAlpha(progress);
                    }

                }
            }
        });
    }

    void setHeaderText(String text) {
        if (tv_refresh_header != null) {
            txt_header = text;
            tv_refresh_header.setText(text);
        }
    }

    void setFooterText(String text) {
        if (tv_refresh_footer != null) {
            txt_footer = text;
            tv_refresh_footer.setText(text);
        }
    }

    public interface OnPullRefreshListener {
        void onPullDown();

        void onPullUp();
    }

}
