package utils.wzutils.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by ishare on 2016/6/23.
 */

public class MathTool {

    public static BigDecimal getBig(double d) {
        return new BigDecimal(Double.toString(d));
    }

    /**
     * double 相加
     */
    public static double jia(double d1, double d2) {
        return getBig(d1).add(getBig(d2)).doubleValue();
    }

    /**
     * double 相减
     */
    public static double jian(double d1, double d2) {
        return getBig(d1).subtract(getBig(d2)).doubleValue();
    }

    /**
     * double 相乘
     */
    public static double cheng(double d1, double d2) {
        return getBig(d1).multiply(getBig(d2)).doubleValue();
    }

    /**
     * double 相除
     */
    public static double chu(double d1, double d2) {
        return getBig(d1).divide(getBig(d2)).doubleValue();
    }


    /***
     * 获取两位有效数字
     *
     * @return
     */
    public static String get2num(double price) {
        String result = "";
        try {
            result = new DecimalFormat("0.00").format(price);
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return result;
    }

    /***
     * 获取两位有效数字
     *
     * @return
     */
    public static String get2num(String price) {
        String result = "";
        try {
            result = get2num(Double.valueOf(price));
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return result;
    }

    /**
     * 格式化数字， 如果小数位没有就返回整数
     * @param num
     * @return
     */
    public static String getNoDotNumStr(double num) {
        if(num==(int)num)return ""+(int)num;
        return ""+get2num(num);
    }
    public static String getNoDotNumStr(String num) {
        String result = "";
        try {
            result = getNoDotNumStr(Double.valueOf(num));
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return result;
    }
}
