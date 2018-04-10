package utils.tjyutils.common;

import utils.wzutils.common.MathTool;

public class Common {
    public static String getPrice(String price) {
        return MathTool.getNoDotNumStr(price);
    }

    public static String getPrice(double price) {
        return MathTool.getNoDotNumStr(price);
    }

    public static String getPriceRMB(String price) {
        return "¥" + getPrice(price);
    }
    public static String getPriceRMB(double price) {
        return "¥" + getPrice(price);
    }


    public static String getPriceYB(String price) {
        return  getPrice(price)+"元宝";
    }
    public static String getPriceYB(double price) {
        return  getPrice(price)+"元宝";
    }
}
