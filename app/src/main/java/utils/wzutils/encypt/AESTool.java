package utils.wzutils.encypt;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESTool {

    public static final String key = "ChangHongMeiLing";
    public static final byte[] iv = new byte[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    public static final IvParameterSpec ivParameter = new IvParameterSpec(iv);//iv 向量

    public static String encrypt(String input, String key) {
        byte[] crypted = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skey, ivParameter);
            crypted = cipher.doFinal(input.getBytes());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        String result = Base64.getEncoder().encodeToString(crypted);
        return result;
    }

    public static String decrypt(String input, String key) {
        byte[] output = null;
        try {
            SecretKeySpec skey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skey, ivParameter);
            output = cipher.doFinal(Base64.getDecoder().decode(Base64.getDecoder().decode(input)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(output);
    }

    public static void main(String[] args) {
        String key = "ChangHongMeiLing";
        String data = "{\"key\":\"eb81255248ee0188297d22b00cd59cdc\",\"timestamp\":\"1453253042\",\"version_no\":\"v4.3.1_5\",\"type\":\"android\"}";
        String tem = "NQtnHuF9n7KCr4UoMZV+lQEM88iq6ptqMet2qB9Rhmo3EEGutErwQYbLC7k1j+dq/Xv/JnhOS5pgZcYpq6+RjRM3J1lbgag0Ph/QEIiqJXkNhxXlGY4vBOPuFv4IOaTbXiIxYynBj+8AIZTwkTGY/A==";
        tem = "RFPy4LrNZkYB9RN5tDD4jN23VGUT/+LaFimA0/wH+jHJQv1MlPI01YLwmLWbT0cswaDeNQDyiXg7Ef5m7qaArQ==";
        tem = "YvyVa1Fxy+4UdWfprd6PmkhLx2xngbP4lkfTbJfJqxY=";
        try {
            System.out.println(encrypt(data, key));
            System.out.println(decrypt(tem, key));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}