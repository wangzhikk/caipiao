package utils.wzutils.encypt;


import utils.wzutils.encypt.blowfish.BlowfishUtil;

/**
 * Created by coder on 15/11/23.
 */
public class BlowFishTool {
    static final String keyString = "LoGjq55JVmV0aBz";

    public static void main(String[] args) {

        String str = "phoneExist";
        System.out.println(encryptString(str, null));

        System.out.println(decryptString("77521a97b7f1c84c68b0c36b8813f8ff3d6ddabac219c140786b010c0eceb40b", null));
    }

    public static String encryptString(String content, String key) {
        if (key == null) key = keyString;
        BlowfishUtil crypt = new BlowfishUtil(key);
        String tempStr = crypt.encryptString(content);
        return tempStr;
    }

    public static String decryptString(String content, String key) {
        if (key == null) key = keyString;
        BlowfishUtil crypt = new BlowfishUtil(key);
        String tempStr = crypt.decryptString(content);
        return tempStr;
    }
}
