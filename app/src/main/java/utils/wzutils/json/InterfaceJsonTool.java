package utils.wzutils.json;

import java.util.List;

/**
 * Created by coder on 15/12/25.
 */
public interface InterfaceJsonTool {
    /**
     * 把java 对象转为jsons
     *
     * @param javaObject java对象
     * @return 返回转换后的字符串
     */
    String toJsonStr(Object javaObject);

    /**
     * Json字符串转java 对象
     *
     * @param jsonStr 输入json字符串
     */
    <T> T toJava(String jsonStr, Class<T> tClass);

    /**
     * Json 转list
     *
     * @param jsonStr
     * @param tClass
     * @param <T>
     * @return
     */
    <T> List<T> toJavaList(String jsonStr, Class<T> tClass);
}
