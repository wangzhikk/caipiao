package utils.wzutils.encypt.tea;


import java.io.UnsupportedEncodingException;


public class Tea {
    private static int n = 6;

    public Tea() {
    }

    private static int[] en(int[] v, int[] k, int rounds) {
        int y = v[0];
        int z = v[1];
        int sum = 0;
        int delta = -1640531527;
        int a = k[0];
        int b = k[1];
        int c = k[2];
        int d = k[3];

        int[] o;
        for (o = new int[2]; rounds-- > 0; z += (y << 4) + c ^ y + sum ^ (y >>> 5) + d) {
            sum += delta;
            y += (z << 4) + a ^ z + sum ^ (z >>> 5) + b;
        }

        o[0] = y;
        o[1] = z;
        return o;
    }

    private static int[] de(int[] v, int[] k, int rounds) {
        int y = v[0];
        int z = v[1];
        boolean sum = false;
        int delta = -1640531527;
        int a = k[0];
        int b = k[1];
        int c = k[2];
        int d = k[3];
        int var12;
        if (rounds == 32) {
            var12 = -957401312;
        } else {
            var12 = -478700656;
        }

        int[] o;
        for (o = new int[2]; rounds-- > 0; var12 -= delta) {
            z -= (y << 4) + c ^ y + var12 ^ (y >>> 5) + d;
            y -= (z << 4) + a ^ z + var12 ^ (z >>> 5) + b;
        }

        o[0] = y;
        o[1] = z;
        return o;
    }

    private static byte[] flag(byte[] orignal) {
        byte[] o = new byte[orignal.length + 8];
        byte[] flag = new byte[]{(byte) 125, (byte) 125, (byte) 125, (byte) 125, (byte) 125, (byte) 125, (byte) 125, (byte) 125};
        System.arraycopy(flag, 0, o, 0, flag.length);
        System.arraycopy(orignal, 0, o, flag.length, orignal.length);
        return o;
    }

    private static byte[] unflag(byte[] orignal) {
        byte[] o = new byte[orignal.length - 8];
        System.arraycopy(orignal, 8, o, 0, orignal.length - 8);
        return o;
    }

    private static int flag_compare(byte[] content) {
        byte[] flag = new byte[]{(byte) 125, (byte) 125, (byte) 125, (byte) 125, (byte) 125, (byte) 125, (byte) 125, (byte) 125};

        for (int i = 0; i < 8; ++i) {
            if (content[i] != flag[i]) {
                return 0;
            }
        }

        return 1;
    }

    public static byte[] encrypt(byte[] content, byte[] key, int rounds) {
        if (content != null && key != null && content.length != 0 && key.length != 0) {
            Object result = null;
            int resultLength = content.length;
            int mol = resultLength % 8;
            if (mol != 0) {
                resultLength = resultLength + 8 - mol;
            }

            int[] k = validateKey3(key);
            int[] v = new int[2];
            Object o = null;
            byte[] result1 = new byte[resultLength];
            int convertTimes = resultLength - 8;
            boolean next = false;

            int times;
            int[] o1;
            int next1;
            for (times = 0; times < convertTimes; times += 8) {
                next1 = times + 4;
                v[0] = byte2int(content, times);
                v[1] = byte2int(content, next1);
                o1 = en(v, k, rounds);
                int2byte(o1[0], result1, times);
                int2byte(o1[1], result1, next1);
            }

            next1 = times + 4;
            if (mol != 0) {
                byte[] tmp = new byte[8];
                System.arraycopy(content, times, tmp, 0, mol);
                v[0] = byte2int(tmp, 0);
                v[1] = byte2int(tmp, 4);
                o1 = en(v, k, rounds);
                int2byte(o1[0], result1, times);
                int2byte(o1[1], result1, next1);
            } else {
                v[0] = byte2int(content, times);
                v[1] = byte2int(content, next1);
                o1 = en(v, k, rounds);
                int2byte(o1[0], result1, times);
                int2byte(o1[1], result1, next1);
            }

            return flag(result1);
        } else {
            return null;
        }
    }

    public static byte[] decrypt(byte[] scontent, byte[] key, int rounds) {
        if (flag_compare(scontent) != 1) {
            return scontent;
        } else {
            byte[] content = unflag(scontent);
            if (content != null && key != null && content.length != 0 && key.length != 0) {
                if (content.length % 8 != 0) {
                    throw new IllegalArgumentException("Content cannot be decypted!");
                } else {
                    Object result = null;
                    int[] k = validateKey3(key);
                    int[] v = new int[2];
                    Object o = null;
                    byte[] var12 = new byte[content.length];
                    int convertTimes = content.length;
                    boolean next = false;

                    int times;
                    for (times = 0; times < convertTimes; times += 8) {
                        int var14 = times + 4;
                        v[0] = byte2int(content, times);
                        v[1] = byte2int(content, var14);
                        int[] var13 = de(v, k, rounds);
                        int2byte(var13[0], var12, times);
                        int2byte(var13[1], var12, var14);
                    }

                    convertTimes -= 8;

                    for (times = convertTimes + 1; times < content.length && var12[times] != 0; ++times) {
                    }

                    byte[] tmp = var12;
                    var12 = new byte[times];
                    System.arraycopy(tmp, 0, var12, 0, times);
                    return var12;
                }
            } else {
                return content;
            }
        }
    }

    private static int byte2int(byte[] buf, int offset) {
        return buf[offset + 3] & 255 | (buf[offset + 2] & 255) << 8 | (buf[offset + 1] & 255) << 16 | (buf[offset] & 255) << 24;
    }

    private static void int2byte(int integer, byte[] buf, int offset) {
        buf[offset] = (byte) (integer >> 24);
        buf[offset + 1] = (byte) (integer >> 16);
        buf[offset + 2] = (byte) (integer >> 8);
        buf[offset + 3] = (byte) integer;
    }

    private static int[] validateKey3(byte[] key) {
        byte[] tempkey = new byte[16];
        int k1;
        int[] var3;
        if (key.length - n + 1 < 8) {
            System.arraycopy(key, n - 1, tempkey, 0, key.length - n + 1);

            for (k1 = 8; k1 < 16; ++k1) {
                tempkey[k1] = (byte) (127 - n - k1);
            }

            var3 = new int[]{byte2int(tempkey, 0), byte2int(tempkey, 4), byte2int(tempkey, 8), byte2int(tempkey, 12)};
            return var3;
        } else {
            System.arraycopy(key, n - 1, tempkey, 0, 8);

            for (k1 = 8; k1 < 16; ++k1) {
                tempkey[k1] = (byte) (127 - n - k1);
            }

            var3 = new int[]{byte2int(tempkey, 0), byte2int(tempkey, 4), byte2int(tempkey, 8), byte2int(tempkey, 12)};
            return var3;
        }
    }

    public static String hex_en(byte[] content, byte[] key, int rounds) {
        return Hex2byte.bytetohex(encrypt(content, key, rounds));
    }

    public static String hex_de(String content, byte[] key, int rounds) throws UnsupportedEncodingException {
        return new String(decrypt(Hex2byte.hextobyte(content), key, rounds), "utf-8");
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String initstr = "asdasdasdasdadadsasdasdadadadsa数";
        String key = "thisisjiao";
        byte rounds = 32;
        String hexs = hex_en(initstr.getBytes("utf-8"), key.getBytes("utf-8"), rounds);
        System.out.println("hex=" + hexs);
        String hexss = hex_de(hexs, key.getBytes("utf-8"), rounds);
        System.out.println("rcoberhex=" + hexss);
    }
}