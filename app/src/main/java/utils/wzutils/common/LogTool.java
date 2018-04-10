package utils.wzutils.common;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * **
 * 用来打印日志的
 *
 * @author wz
 */
public class LogTool {
    public static boolean debug = true;//App.isDebug;
    static String tag = "wzlog";
    static final String kongge="\t";
    /**
     * **
     * 控制台打印一个对象
     *
     * @param obj
     */
    public static void s(Object... obj) {
        if (!debug) return;
        try {
            StringBuilder sb=new StringBuilder();
            for (int i = 0; i < obj.length; i++) {
                sb.append(obj[i]+kongge);
            }
            Log.v(tag,sb.toString()+"\n");

        } catch (Exception e) {
        }

    }

    public static String getStackTrace() {
        String s = "";
        int k = 4;

        StackTraceElement[] ss = Thread.currentThread().getStackTrace();
        s = ss[k].getClassName() + "---" + ss[k].getMethodName() + "  :";
        if (!s.startsWith("android") && !s.startsWith("java")) {
            return s;
        }

        return "";
    }

    /**
     * **
     * 打印一个异常
     *
     * @param
     */
    public static void ex(Throwable e) {
        if (!debug) return;
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        e.printStackTrace(printWriter);
        try {
            Log.e(tag, writer.toString());
            try {
                String filePath = FileTool.getCacheDir("log").getAbsolutePath() + "/log.txt";
                File file = new File(filePath);
                if (file.exists() && file.length() > 10 * 1024 * 1024) {
                    file.delete();
                }
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(filePath, true);
                fileWriter.append(TimeTool.nowTimeStringLong() + "\r\n" + writer.toString() + "\r\n");
                fileWriter.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        } catch (Exception e2) {
        }

    }

    public static void printClassLine(String pre, Object activity) {
        String s = "printClassLine---" + pre + ":(" + activity.getClass().getSimpleName() + ".java:1)\n";
        LogTool.s(s);
    }
}
