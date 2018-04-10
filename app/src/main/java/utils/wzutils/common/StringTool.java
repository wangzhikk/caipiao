package utils.wzutils.common;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by coder on 16/1/18.
 */
public class StringTool {
    /**
     * 字符串转换unicode
     */
    public static String string2Unicode(String string) {

        StringBuffer unicode = new StringBuffer();

        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            String tem = Integer.toHexString(c);
            if (tem.length() == 2) {
                tem = "00" + tem;
            }
            // 转换为unicode
            unicode.append("\\u" + tem);
        }

        return unicode.toString();
    }

    /**
     * unicode 转字符串
     */
    public static String unicode2String(String unicode) {

        StringBuffer string = new StringBuffer();

        String[] hex = unicode.split("\\\\u");
        string.append(hex[0]);
        for (int i = 1; i < hex.length; i++) {

            try {
                // 转换出每一个代码点
                int data = Integer.parseInt(hex[i].substring(0, 4), 16);

                // 追加成string
                string.append((char) data);
                if (hex[i].length() > 4) {
                    string.append(hex[i].substring(4));
                }
            } catch (Exception e) {
                string.append("\\u" + hex[i]);
            }

        }

        return string.toString();
    }


    /***
     * 获取一个不是空的 字符串
     *
     * @param text
     * @return
     */
    public static String getNotNullText(Object text) {
        String result = "" + text;
        if (isEmpty(result)) result = "";
        return result;
    }

    public static boolean isEmpty(String text) {
        boolean result = false;

        if (text == null) {
            result = true;
        } else {
            text = text.trim();

            if (text.length() < 1) {
                result = true;
            } else if ("null".equals(text.toLowerCase())) {
                result = true;
            }

        }

        return result;
    }

    public static boolean notEmpty(String text) {
        return !isEmpty(text);
    }

    /***
     * 设置带颜色的  text
     *
     * @param spannableString
     * @param colorText
     * @param color
     */
    public static void setTextColor(SpannableString spannableString, String colorText, int color) {
        try {
            int beginIndex = spannableString.toString().indexOf(colorText);
            int endIndex = beginIndex + colorText.length();
            spannableString.setSpan(new ForegroundColorSpan(color), beginIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 设置文本大小  text
     *
     * @param spannableString
     */
    public static void setTextSize(SpannableString spannableString, String sizeText, int sizeDp) {
        try {
            int beginIndex = spannableString.toString().indexOf(sizeText);
            int endIndex = beginIndex + sizeText.length();
            spannableString.setSpan(new AbsoluteSizeSpan(CommonTool.dip2px(sizeDp)), beginIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static void main(String[] args) {
//        String test = "最代码网站地址:www.zuidaima.com";
//
//        String unicode = string2Unicode(test);
//
//        String string = unicode2String(unicode);
//
//        System.out.println(unicode2String("{\"code\":200,\"login\":\"0\",\"datas\":{\"error\":\"\\u8bf7\\u767b\\u5f55\"}}"));
//        System.out.println(unicode);
//        System.out.println(unicode2String("\\u8876nihao\\u6546as我dfl\\u我"));
//        System.out.println(string);
        System.out.println(getPhonePassword("21312"));

    }

    /**
     * 获取img标签中的src值
     *
     * @param content
     * @return
     */
    public static ArrayList<String> getImgSrc(String content) {

        ArrayList<String> list = new ArrayList<String>();
        try {
            //目前img标签标示有3种表达式
            //<img alt="" src="1.jpg"/>   <img alt="" src="1.jpg"></img>     <img alt="" src="1.jpg">
            //开始匹配content中的<img />标签
            Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
            Matcher m_img = p_img.matcher(content);
            boolean result_img = m_img.find();
            if (result_img) {
                while (result_img) {
                    //获取到匹配的<img />标签中的内容
                    String str_img = m_img.group(2);

                    //开始匹配<img />标签中的src
                    Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
                    Matcher m_src = p_src.matcher(str_img);
                    if (m_src.find()) {
                        String str_src = m_src.group(3);
                        list.add(str_src);
                    }
                    //结束匹配<img />标签中的src

                    //匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
                    result_img = m_img.find();
                }
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }

        return list;
    }

    /**
     * @param phone
     * @return 135*****8892;
     */
    public static String getPhonePassword(String phone) {
        String result = phone;
        phone = "" + phone;
        if (phone.length() > 7) {
            result = phone.substring(0, 3) + phone.replaceAll(".", "*").substring(3, phone.length() - 4) + phone.substring(phone.length() - 4, phone.length());
        }
        return result;

    }

    /***
     * 将天加 的item 用jiange  分开
     * 1,2,3,4
     */
    public static class StringItems {
        String jiange;
        StringBuffer sb = new StringBuffer("");

        public StringItems(String jiange) {
            this.jiange = jiange;
        }

        public StringItems addItem(String item) {
            sb.append(jiange + item);
            return this;
        }

        public StringItems addItems(String... items) {
            if(items==null)return this;
            for (int i = 0; i < items.length; i++) {
                addItem(items[i]);
            }
            return this;
        }
        public StringItems addItems(List<String> items) {
            if(items==null)return this;
            for (int i = 0; i < items.size(); i++) {
                addItem(items.get(i));
            }
            return this;
        }
        public static List<String> parse(String src, String jiange){
            src=""+src;
            return Arrays.asList( src.split(jiange));
        }
        @Override
        public String toString() {
            String result = sb.toString();
            if (result.startsWith(jiange)) {
                result = result.substring(1);
            }
            return result;
        }
    }
}

