package utils.wzutils.ui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.common.ViewTool;
import utils.wzutils.parent.ParentRecycleViewWz;

/**
 * Created by ishare on 2016/6/7.
 * 主要是用于简化 列表操作的
 * <p>
 * recycle  如果加了 item type  好像效率不理想
 */
public class WzSimpleRecycleView<E> extends ParentRecycleViewWz {
    /***
     * 默认的空界面
     */
    public static int defaultEmptyResId;
    AdapterRecycle adapter;
    List<E> datas = new ArrayList<E>();
    int[] types = new int[0];
    int[] viewsResId = new int[0];
    WzRecycleAdapter wzRecycleAdapter;
    Class<WzViewHolder> holderClass;
    /***
     * 设置 缓存的 layout 的个数， 和recyle cache 不一样， LayoutInflaterTool 这个用的
     */
    int layoutCacheCount = 20;

    public WzSimpleRecycleView(Context context) {
        super(context);
    }

    public WzSimpleRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WzSimpleRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public int getLayoutCacheCount() {
        return layoutCacheCount;
    }

    public void setLayoutCacheCount(int layoutCacheCount) {
        this.layoutCacheCount = layoutCacheCount;
    }

    @Override
    public void init() {
        super.init();
        setOverScrollMode(OVER_SCROLL_NEVER);
        //setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false)); //这个在父类里面设置了
        adapter = new AdapterRecycle(this);
        setEmptyResId(defaultEmptyResId);
    }

    public void setData(List<E> datas, int[] types, int[] viewsResId, WzRecycleAdapter wzRecycleAdapter) {
        setDataImp(datas, null, types, viewsResId, wzRecycleAdapter);
    }

    public void setData(List<E> datas, int resId, WzRecycleAdapter wzRecycleAdapter) {
        setData(datas, null, resId, wzRecycleAdapter);
    }

    public void setData(List<E> datas, Class<WzViewHolder> holderClass, int resId, WzRecycleAdapter wzRecycleAdapter) {
        types = new int[]{0};
        viewsResId = new int[]{resId};
        setDataImp(datas, holderClass, types, viewsResId, wzRecycleAdapter);
    }

    protected void setDataImp(List<E> datas, Class<WzViewHolder> holderClass, int[] types, int[] viewsResId, WzRecycleAdapter wzRecycleAdapter) {
        if(getAdapter()==null)setAdapter(adapter);//放在这里设置的目的是  因为有了默认空界面后， 设置了这个就会显示空数据， 所以放在这里



        if (datas == null) datas = new ArrayList<>();
        this.datas = datas;

        if (types != null && viewsResId != null && types.length == viewsResId.length) {
            this.types = types;
            this.viewsResId = viewsResId;
        }
        this.holderClass = holderClass;
        if (wzRecycleAdapter != null) this.wzRecycleAdapter = wzRecycleAdapter;
        adapter.notifyDataSetChanged();

    }

    public void setEmptyResId(int emptyResId) {
        try {
            if (emptyResId != 0) {
                setEmptyView(LayoutInflaterTool.getInflater(3, emptyResId).inflate());
            } else {
                setEmptyView(null);
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public void setEmptyView(View emptyView) {
        adapter.emptyView = emptyView;
    }

    @Override
    public View getEmptyView(){
        return adapter.emptyView;
    }

    public static class AdapterRecycle extends Adapter {
        public static final int emptyType = -999;
        public View emptyView;
        WzSimpleRecycleView wzRecycleView;

        public AdapterRecycle(WzSimpleRecycleView wzRecycleView) {
            this.wzRecycleView = wzRecycleView;
            setHasStableIds(true);//getItemId  配合这个 ，保证 同一个position  使用同一个 view ， 这样就可以防止刷新的时候闪烁。。 goole 是坑，不知道默认返回position
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int resId = 0;
            View itemView = null;
            if (emptyView != null && emptyType == viewType) {//显示空控件
                UiTool.setWH(emptyView, wzRecycleView.getMeasuredWidth(), wzRecycleView.getMeasuredHeight());
                itemView = emptyView;
            } else {
                for (int i = 0; i < wzRecycleView.types.length; i++) {
                    if (viewType == wzRecycleView.types[i]) {
                        resId = wzRecycleView.viewsResId[i];
                    }
                }
                if (wzRecycleView.getLayoutCacheCount() > 0) {
                    itemView = LayoutInflaterTool.getInflater(wzRecycleView.getLayoutCacheCount(), resId).inflate();
                } else {
                    itemView = LayoutInflater.from(wzRecycleView.getContext()).inflate(resId, null);
                }
            }

            WzViewHolder holder = null;
            if (wzRecycleView.holderClass != null) {
                try {
                    holder = (WzViewHolder) wzRecycleView.holderClass.getConstructor(WzSimpleRecycleView.class, View.class).newInstance(wzRecycleView, itemView);
                } catch (Exception e) {
                    LogTool.ex(e);
                }
            }
            if (holder == null) holder = new WzViewHolder(wzRecycleView, itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder instanceof WzViewHolder) {
                ((WzViewHolder) holder).initData(position);
            }
        }

        @Override
        public int getItemCount() {
            if (emptyView != null && wzRecycleView.datas.size() == 0) {//如果当前要显示空的控件并且当前数据就是空的， 那么就返回1
                return 1;
            }
            return wzRecycleView.datas.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (emptyView != null && wzRecycleView.datas.size() == 0) {//如果当前要显示空的控件并且当前数据就是空的， 那么就返回 空控件
                return emptyType;
            }
            return wzRecycleView.wzRecycleAdapter.getItemViewType(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }


    public static class WzViewHolder extends ViewHolder implements Serializable {
        public static final int keyHolderTag = 1;
        WzSimpleRecycleView wzRecycleView;

        public WzViewHolder(WzSimpleRecycleView wzRecycleView, View itemView) {
            super(itemView);
            this.wzRecycleView = wzRecycleView;
            ViewTool.setTag(itemView, this, keyHolderTag);
            ViewTool.initViews(itemView, this, null);
        }

        public void initData(int position) {
            try {
                int type=getItemViewType();
                if(wzRecycleView.wzRecycleAdapter.isEmptyType(type)){
                    wzRecycleView.wzRecycleAdapter.initEmptyData(position,type,itemView);
                }else {
                    wzRecycleView.wzRecycleAdapter.initData(position, type, itemView);
                    wzRecycleView.wzRecycleAdapter.initData(position, type, itemView, this);
                }
            } catch (Exception e) {
                LogTool.ex(e);
            }
        }
    }

    public static abstract class WzRecycleAdapter {
        public int getItemViewType(int position) {
            return 0;
        }

        public void initData(int position, int type, View itemView) {
        }

        public void initData(int position, int type, View itemView, WzViewHolder wzViewHolder) {

        }

        /***
         * 初始化 空数据显示
         * @param position
         * @param type
         * @param itemView
         */
        public void initEmptyData(int position, int type, View itemView) {

        }
        public boolean isEmptyType(int type) {
            return type == AdapterRecycle.emptyType;
        }
    }
}
