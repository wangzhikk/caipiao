package utils.wzutils.common;

import java.lang.reflect.Field;


/**
 * Created by wz on 2017/12/22.
 */

public class SafeTool {

    public static Object  getPrivateField(Object parent,Class c,String filedStr){
        try {
            //获取Bean类的INT_VALUE字段
            Field field = c.getDeclaredField(filedStr);
//将字段的访问权限设为true：即去除private修饰符的影响
            field.setAccessible(true);
            return field.get(parent);
        }catch (Exception e){
            LogTool.ex(e);
        }
        return null;
    }

    /***
     *
     * 给指定的 对象设置 属性，  可用于private static final
     * @param clzz      类名
     * @param fieldStr  字段名称
     * @param parent    对象名称
     * @param objSet    需要设置的 属性对象
     */
    public static void setField(Class clzz, String fieldStr, Object parent,Object objSet) {
        try {
            //获取Bean类的INT_VALUE字段
            Field field = clzz.getDeclaredField(fieldStr);
            //将字段的访问权限设为true：即去除private修饰符的影响
            field.setAccessible(true);

            /*去除final修饰符的影响，将字段设为可修改的*/
            Field modifiersField = Field.class.getDeclaredField("accessFlags");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, 1);
            //把字段值设为200
            field.set(parent, objSet);
        }catch (Exception e){
            LogTool.ex(e);
        }

    }
}
