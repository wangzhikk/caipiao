package utils.wzutils.common;

import java.util.ArrayList;
import java.util.List;

/**
 * abc on 2017/3/24.
 */

public class TestData {
    /***
     * 获取测试图片地址
     * @param index
     * @return
     */
    public static String getTestImgUrl(int index) {
        String result = "http://img95.588ku.com/photo/00001/" + (1000 + index) + ".jpg_wh300.jpg!h300";
        return result;
    }

    /***
     * 获取测试字符串列表
     * @param count
     * @return
     */
    public static List<String> getTestStrList(int count) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            stringList.add("" + i);
        }
        return stringList;
    }
}
