package utils.wzutils.encypt.tea;


public class Hex2byte {
    public Hex2byte() {
    }

    public static String bytetohex(byte[] bt) {
        int len = bt.length;
        char[] out = new char[2 * bt.length];
        int i = 0;

        for (int j = 0; j < len; ++j) {
            char temps1 = (char) (48 + ((bt[j] & 240) >> 4));
            char temps2 = (char) (48 + (bt[j] & 15));
            if (temps1 > 57 || temps1 < 48) {
                switch (temps1 - 57) {
                    case 1:
                        temps1 = 65;
                        break;
                    case 2:
                        temps1 = 66;
                        break;
                    case 3:
                        temps1 = 67;
                        break;
                    case 4:
                        temps1 = 68;
                        break;
                    case 5:
                        temps1 = 69;
                        break;
                    case 6:
                        temps1 = 70;
                }
            }

            if (temps2 > 57 || temps2 < 48) {
                switch (temps2 - 57) {
                    case 1:
                        temps2 = 65;
                        break;
                    case 2:
                        temps2 = 66;
                        break;
                    case 3:
                        temps2 = 67;
                        break;
                    case 4:
                        temps2 = 68;
                        break;
                    case 5:
                        temps2 = 69;
                        break;
                    case 6:
                        temps2 = 70;
                }
            }

            out[i] = temps1;
            out[i + 1] = temps2;
            i += 2;
        }

        return new String(out);
    }

    public static byte[] hextobyte(String hex) {
        byte[] out = new byte[hex.length() / 2];
        int s = 0;
        int s1 = 0;
        int j = 0;

        for (int i = 0; i < hex.length() / 2; j += 2) {
            if (48 <= hex.charAt(j) && hex.charAt(j) <= 57) {
                s = hex.charAt(j) - 48;
            } else {
                switch (hex.charAt(j)) {
                    case 'A':
                        s = 10;
                        break;
                    case 'B':
                        s = 11;
                        break;
                    case 'C':
                        s = 12;
                        break;
                    case 'D':
                        s = 13;
                        break;
                    case 'E':
                        s = 14;
                        break;
                    case 'F':
                        s = 15;
                }
            }

            if (48 <= hex.charAt(j + 1) && hex.charAt(j + 1) <= 57) {
                s1 = hex.charAt(j + 1) - 48;
            } else {
                switch (hex.charAt(j + 1)) {
                    case 'A':
                        s1 = 10;
                        break;
                    case 'B':
                        s1 = 11;
                        break;
                    case 'C':
                        s1 = 12;
                        break;
                    case 'D':
                        s1 = 13;
                        break;
                    case 'E':
                        s1 = 14;
                        break;
                    case 'F':
                        s1 = 15;
                }
            }

            out[i] = (byte) ((s & 15) << 4 | s1 & 15);
            ++i;
        }

        return out;
    }
}