package utils.wzutils.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import utils.wzutils.AppTool;
import utils.wzutils.common.thread.ThreadTool;

/**
 * Created by kk on 2015/4/1.
 * <p>
 * 大量加载layout
 */
public class LayoutInflaterTool {
    static HashMap<Integer, LayoutInflaterTool> myInflaterHashMap = new HashMap<Integer, LayoutInflaterTool>();
    LayoutInflater layoutInflater;
    int maxAutoInitSize;//最大容量
    int resource;
    int minAutoInitSize;//不足这么多个，就加载
    String group = "";
    boolean isIniting = false;//是否正在初始化
    boolean canUseInThread = true;//是否可以在线程里面初始化,某些设备上 不能在线程里面用这个
    HashMap<Integer, Queue<View>> views = new HashMap<Integer, Queue<View>>();
    //Context context;
    private LayoutInflaterTool(Context context, int maxAutoInitSize, int resource) {
        if (context == null) {
            context =AppTool.getApplication();
        }
        //this.context=context;
        layoutInflater = LayoutInflater.from(context);
        setMaxAutoInitSize(maxAutoInitSize);
        this.resource = resource;
        this.group = "inflater:" + resource;
        initViews();
    }

    /***
     * @param maxAutoInitSize 容器容量
     * @param resource
     * @return
     */
    public static LayoutInflaterTool getInflater(int maxAutoInitSize, int resource) {
        LayoutInflaterTool myInflater = getInflater(null, maxAutoInitSize, resource);
        return myInflater;
    }

    /***
     * @param maxAutoInitSize 容器容量
     * @param resource
     * @return
     */
    public static LayoutInflaterTool getInflater(Context context, int maxAutoInitSize, int resource) {
//        if (context == null) {
//            context =AppTool.currActivity;
//        }

        LayoutInflaterTool myInflater = myInflaterHashMap.get(resource);
        if (myInflater == null) {
            myInflater = new LayoutInflaterTool(context, maxAutoInitSize, resource);
            myInflaterHashMap.put(resource, myInflater);
        }

//        if(!context.equals(myInflater.context)){//切换了界面 为了保持 所有view 的context 都是 当前界面   （主要是用于glide 加载图片的时候自动获取 当前界面， 这样可以大量节省内存） 必须要重置这个 ，否则 初始化出来的控件的 context 全是第一个activity。。。。
//            myInflater.views.clear();
//            myInflater.layoutInflater=LayoutInflater.from(context);
//        }


        myInflater.setMaxAutoInitSize(maxAutoInitSize);
        return myInflater;
    }

    /***
     * 预加载一些资源
     *
     * @param size
     * @param resIds
     */
    public static void preLoad(final int size, final int... resIds) {
        ThreadTool.execute(new Runnable() {
            @Override
            public void run() {
                if (resIds != null) {
                    for (int i = 0; i < resIds.length; i++) {
                        try {
                            LayoutInflaterTool.getInflater(size, resIds[i]);
                        } catch (Exception e) {
                            LogTool.ex(e);
                        }
                    }
                }
            }
        });

    }

    /***
     * 预加载所有资源文件
     * @param rlayoutClass
     * @param count
     */
    public static void preLoadAll(final Class rlayoutClass, final int count) {
        ThreadTool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Field fields[] = rlayoutClass.getDeclaredFields();
                    for (Field field : fields) {
                        try {
                            int tem = (int) field.get(null);
                            LayoutInflaterTool.getInflater(count, tem);
                        } catch (Exception e) {
                            LogTool.ex(e);
                        }
                    }
                } catch (Exception e) {
                    LogTool.ex(e);
                }
            }
        });
    }

    public static void clearAll() {
        if (myInflaterHashMap != null) myInflaterHashMap.clear();

    }

    public LayoutInflaterTool setMaxAutoInitSize(int maxAutoInitSize) {
        this.maxAutoInitSize = maxAutoInitSize;
        this.minAutoInitSize = maxAutoInitSize / 2;
        return this;
    }

    public View inflate() {
        //
        View view = getQueueViews(resource).poll();
        try {
            if (view == null) {
                view = layoutInflater.inflate(resource, null);
            }
            initViews();
        } catch (Exception e) {
            LogTool.s(e);
            // LogTool.ex(e);
        }
        try {
            if (view == null) {
                view = AppTool.currActivity.getLayoutInflater().inflate(resource, null);
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return view;
    }

    private void initViews() {
        // LogTool.s("layouttool--"+ViewTool.getNameById(resource, R.layout.class));
        if (!canUseInThread) return;
        if (getQueueViews(resource).size() < minAutoInitSize && !isIniting) {//
            isIniting = true;
            ThreadTool.execute(new Runnable() {
                @Override
                public void run() {
                    inflateImp();
                    isIniting = false;
                }

                public void inflateImp() {
                    try {
                        for (int i = 0; i < maxAutoInitSize; i++) {
                            View view = layoutInflater.inflate(resource, null);
                            getQueueViews(resource).offer(view);
                        }
                        if (getQueueViews(resource).size() < maxAutoInitSize) {
                            inflateImp();
                        }
                    } catch (Exception e) {
                        //LogTool.ex(e);
                        canUseInThread = false;
                    }
                }

            });
        }
    }

    private Queue<View> getQueueViews(int resource) {
        Queue<View> views_queue = views.get(resource);
        if (views_queue == null) {
            views_queue = new LinkedBlockingQueue<View>();
            views.put(resource, views_queue);
        }
        return views_queue;
    }
}
