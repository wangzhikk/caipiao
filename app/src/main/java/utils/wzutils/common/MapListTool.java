package utils.wzutils.common;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * 一对多的数据结构
 *
 * @author Administrator
 *         <p/>
 *         如果在useWeakReference = true    map 值里面不要保存 key 的引用，否则不能自动删除
 *         比如   key=A   value=b   那么b中不能包含 A的引用
 */
public class MapListTool<T> implements Serializable {
    public static final String allGroup = "allGroup";
    private Map<Comparable, List<T>> maps;

    public <T> MapListTool() {
        getMaps();
    }

    public Map<Comparable, List<T>> getMaps() {
        if (maps == null) {
            maps = new TreeMap<>();
        }
        return maps;
    }

    //是否包含一个组
    public boolean containsGroup(Comparable group) {
        return getMaps().containsKey(group);
    }

    /**
     * **把一组对象加入到一个组里面*
     */
    public synchronized void putList(Comparable group, List<T> datas) {
        if (datas == null || group == null) return;
        getMaps().put(group, datas);
    }

    /**
     * **把一个对象加入到一个组里面*
     */
    public synchronized void putArray(Comparable group, T... data) {
        if (data != null) {
            for (int i = 0; i < data.length; i++) {
                putOne(group, data[i]);
            }
        }
    }

    /**
     * **把一个对象加入到一个组里面*
     */
    public synchronized void putOne(Comparable group, T data) {
        putOne(group, data, -1);
    }

    /**
     * **把一个对象加入到一个组里面*
     */
    public synchronized void putOne(Comparable group, T data, int index) {
        if (data == null || group == null) return;
        List<T> list = getMaps().get(group);
        if (list == null) {
            list = new LinkedList<T>();
            getMaps().put(group, list);
        }
        if (!list.contains(data)) {
            if (index < 0) {
                list.add(data);
            } else if (list.size() >= index)
                list.add(index, data);
        }
    }

    public synchronized void clear() {
        maps.clear();
    }


    /**
     * **把一个对象从一个组里面删除*
     */
    public synchronized void removeOne(Object group, T data) {
        if (data == null || group == null) return;
        List<T> list = getMaps().get(group);
        if (list != null) list.remove(data);
    }

    public synchronized List<T> getList(Object group) {
        if (allGroup.equals(group)) {
            LinkedList<T> result = new LinkedList<T>();
            Iterator<Map.Entry<Comparable, List<T>>> iterator = getMaps().entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<Comparable, List<T>> entry = iterator.next();
                List<T> list = entry.getValue();
                if (list != null) {
                    result.addAll(list);
                }
            }
            return result;
        }

        if (group == null) return new LinkedList<T>();
        List<T> list = getMaps().get(group);
        if (list == null) return new LinkedList<T>();
        return list;
    }

    /**
     * **根据数据获取所属组*
     */
    public synchronized Object getGroupByData(T data) {
        Iterator<Map.Entry<Comparable, List<T>>> iterator = getMaps().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Comparable, List<T>> entry = iterator.next();
            List<T> list = entry.getValue();
            if (list != null && list.contains(data)) return entry.getKey();
        }
        return "";
    }
}
