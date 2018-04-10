package utils.wzutils.common;

import java.util.List;

/**
 * Created by wz on 2017/8/14.
 */

public class CollectionsTool {
    /***
     * 列表是否为空
     * @param list
     * @return
     */
    public static boolean isEmptyList(List list){
        if(list==null||list.size()<1){
            return true;
        }
        return false;
    }

    public static boolean NotEmptyList(List list){
        return !isEmptyList(list);
    }
}
