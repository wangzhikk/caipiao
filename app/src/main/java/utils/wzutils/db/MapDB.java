package utils.wzutils.db;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import utils.wzutils.AppTool;
import utils.wzutils.JsonTool;
import utils.wzutils.common.LogTool;

/**
 * Created by kk on 2016/5/11.
 * 键值对数据库， 主要用于非常简单的操作
 */
public class MapDB {
    private static String dbName="mapdb";

    public static void init(boolean isDebug){
        setDbName("mapdb"+isDebug);
    }
    /***
     * 保存一个对象到本地
     *
     * @param key
     * @param value
     */
    public static void saveObj(String key, Object value) {
        try {
            String valueSave = JsonTool.toJsonStr(value);
            getShare().edit().putString(key, valueSave).apply();//apply 异步提交， commit 同步提交
            LogTool.s("保存了一个对象到本地： key= " + key);
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }

    /***
     * 从本地加载一个对象
     *
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T loadObj(String key, Class<T> tClass) {
        return loadObjByDefault(key,tClass,null);
    }
    public static <T> T loadObjByDefault(String key, Class<T> tClass,T defaultObj) {
        T t = null;
        try {
            String value = getShare().getString(key, null);
            t = JsonTool.toJava(value, tClass);
            LogTool.s("从本地读取了一个对象: key=" + key);
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return t==null?defaultObj:t;
    }
    /***
     * 从本地加载一个对象
     *
     * @param key
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> loadObjList(String key, Class<T> tClass) {
        List<T> t = null;
        try {
            String value = getShare().getString(key, null);
            t = JsonTool.toJavaList(value, tClass);
            LogTool.s("从本地读取了一个对象: key=" + key);
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return t;
    }

    private static SharedPreferences getShare() {
        return AppTool.getApplication().getSharedPreferences(getDbName(), Context.MODE_PRIVATE);
    }

    public static String getDbName() {
        return dbName;
    }

    public static void setDbName(String dbName) {
        MapDB.dbName = dbName;
    }

}
