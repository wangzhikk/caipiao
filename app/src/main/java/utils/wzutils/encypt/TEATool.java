package utils.wzutils.encypt;


import java.io.UnsupportedEncodingException;

import utils.wzutils.encypt.tea.Tea;


/**
 * TEA加解密工具类
 *
 * @author xuan.chen
 */
public class TEATool {

    // 密钥
    private static final String KEY = "chusercloud";

    // 加密轮数
    private static final int ROUND = 16;

    // 编码
    private static final String CHARSET = "UTF-8";

    /**
     * 加密
     *
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        try {
            byte[] strByte = str.getBytes(CHARSET);
            byte[] keyByte = KEY.getBytes(CHARSET);
            return Tea.hex_en(strByte, keyByte, ROUND);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 解密
     *
     * @param str
     * @return
     */
    public static String decrypt(String str) {
        try {
            byte[] keyByte = KEY.getBytes(CHARSET);
            return Tea.hex_de(str, keyByte, ROUND);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(encrypt("phoneExist"));
        System.out.println(decrypt("77521a97b7f1c84c68b0c36b8813f8ff3d6ddabac219c140786b010c0eceb40b"));


    }
}
