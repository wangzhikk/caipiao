package utils.wzutils.encypt;


import java.security.MessageDigest;

import utils.wzutils.common.LogTool;

/**
 * Created by coder on 15/12/25.
 */
public class Md5Tool {

    public static String md5(String str) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return result;
    }

    /**
     * 取后16位的MD5码
     */
    public static String md5_16(String str) {
        try {
            String result = md5(str);
            if (result.length() > 30) ;
            return result.substring(8, 24);
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return str;
    }
}
