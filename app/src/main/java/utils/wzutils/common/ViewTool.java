package utils.wzutils.common;

import android.app.Activity;
import android.view.View;

import java.io.Serializable;
import java.lang.reflect.Field;

import utils.wzutils.AppTool;

/**
 * Created by coder on 15/12/25.
 */
public class ViewTool {
    static final int keyBegin = 3 << 24;//用这种方式 可以保证 key>>>24 >2;
    static int key = keyBegin;

    /**
     * 初始化一些 view 的字段
     *
     * @param parentView      parentView (代替这里).findViewByid()
     * @param classObj        要填充的对象
     * @param onClickListener 给每个初始化的View 设置的点击事件
     *                        <p>
     *                        <p>
     *                        -keepclasseswithmembers class * implements java.io.Serializable {
     *                        ;
     *                        }
     */
    public static void initViews(View parentView, Object classObj, View.OnClickListener onClickListener) {
        try {
            if (parentView == null) return;
            initViewsByOtherClass(parentView, classObj.getClass(), classObj, onClickListener);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /**
     * 初始化一些 view 的字段 需要保证 c 是classObj 的父类
     *
     * @param parentView      parentView (代替这里).findViewByid()
     * @param c               添加这个参数的目的是为了满足 可能需要去他父类的 字段来初始化,
     * @param classObj        要填充的对象
     * @param onClickListener 给每个初始化的View 设置的点击事件
     *                        <p>
     *                        -keepclasseswithmembers class * implements java.io.Serializable {
     *                        ;
     *                        }
     */
    public static void initViewsByOtherClass(View parentView, Class c, Object classObj, View.OnClickListener onClickListener) {
        try {
            if (parentView == null) return;
            if (!(classObj instanceof Serializable)) {
                CommonTool.showToast("自动初始化view 对象时, 传入的对象必须  实现 Serializable 接口,不然要被混淆");
                throw new Exception("自动初始化view 对象时, 传入的对象必须  实现 Serializable 接口,不然要被混淆");
            }
            Field[] fields = c.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                try {
                    Field field = fields[i];
                    if (View.class.isAssignableFrom(field.getType()))// 如果这个控件是
                    {
                        int idInt = AppTool.getApplication().getResources().getIdentifier(field.getName(), "id", AppTool.getApplication().getPackageName());
                        View v = parentView.findViewById(idInt);
                        if (v == null) continue;
                        field.setAccessible(true);
                        field.set(classObj, v);
                        if (onClickListener != null) {
                            v.setOnClickListener(onClickListener);
                        }
                    }
                } catch (Exception e) {
                    // LogTool.ex(e);
                }
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    public static void initViewsByActivity(Activity activity) {
        try {
            initViewsByOtherClass(activity.getWindow().getDecorView(),activity.getClass(),activity,null);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
    /**
     * 根据id名字获取id
     *
     * @param name     id 的名称
     * @param resClass 比如android.R.Id.class
     * @return
     */
    public static int getIdByName(String name, Class resClass) {
        int result = 0;
        Field id = null;
        try {
            if (name != null && name.length() > 0) {
                id = resClass.getDeclaredField(name);
                result = id.getInt(null);
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return result;
    }

    /***
     * 根据id 获取对应名称
     * @param id
     * @param resClass
     * @return
     */
    public static String getNameById(int id, Class resClass) {
        try {
            Field[] fields = resClass.getDeclaredFields();
            for (Field field : fields) {
                int idTem = field.getInt(null);
                if (idTem == id) {
                    return field.getName();
                }
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return "";
    }


    public static int initKey() {
        return key++;
    }

    /**
     * 设置view 的tag
     *
     * @param view
     * @param data
     * @param key
     */
    public static void setTag(View view, Object data, int key) {
        if (view != null) {
            if (key < keyBegin) key += keyBegin;
            view.setTag(key, data);
        }
    }

    /**
     * 获取View 的tag
     *
     * @param view
     * @param key
     * @return
     */
    public static Object getTag(View view, int key) {
        if (view != null) {
            if (key < keyBegin) key += keyBegin;
            return view.getTag(key);
        }
        return null;
    }


    /***
     * 查找 孩子的指定类型父类
     *
     * @param child
     * @param parentClass
     */
    public static <T extends View> T getParentIntance(View child, Class<T> parentClass) {
        T result = null;
        if (child == null || parentClass == null) return result;
        try {
            View temParent = null;
            while (child.getParent() != null && child.getParent() instanceof View) {
                temParent = (View) child.getParent();
                if (temParent.getClass().equals(parentClass)) {
                    return (T) temParent;
                }
                child = temParent;
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }


        return result;
    }

}
