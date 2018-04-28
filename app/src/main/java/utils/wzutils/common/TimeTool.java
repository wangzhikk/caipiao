package utils.wzutils.common;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * Created by coder on 15/12/25.
 */
public class TimeTool {
    static long oneDayTime = 24 * 60 * 60 * 1000l;

    /***
     * 获取指定格式 的time  毫秒数
     *
     * @param dataStr 日期
     * @param format  格式
     * @return
     */
    public static long getTimes(String dataStr, String format) {
        long time = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            time = simpleDateFormat.parse(dataStr).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /***
     * 返回 天， 小时，分钟， 秒 的数组
     * @param timeCha
     * @return
     */
    public static int[] splitTimes(long timeCha) {
        int[] splitTime = new int[4];
        try {

            long miao = 1000;
            long fen = miao * 60;
            long shi = fen * 60;
            long tian = shi * 24;


            int day = (int) (timeCha / tian);
            int hour = (int) ((timeCha % tian) / shi);
            int minute = (int) ((timeCha % shi) / fen);
            int second = (int) ((timeCha % fen) / miao);
            splitTime[0] = day;
            splitTime[1] = hour;
            splitTime[2] = minute;
            splitTime[3] = second;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return splitTime;
    }

    public static int[] daoJiShi(String dateString) {
        int[] splitTime = new int[4];
        try {
            long times = TimeTool.getTimesDefault(dateString);
            times = times - new Date().getTime();
            if (times > 0) {
                splitTime = TimeTool.splitTimes(times);
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return splitTime;
    }

    /***
     * 获取指定格式 的time  毫秒数
     *
     * @param dataStr 日期
     * @return
     */
    public static long getTimesDefault(String dataStr) {
        long time = 0;
        try {
            String format = "yyyy-MM-dd HH:mm:ss";
            time = getTimes(dataStr, format);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     *
     *
     * @param timeLong
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getLongTimeStr(String timeLong) {
        try {
            return getLongTimeStr(Long.valueOf(timeLong));
        }catch (Exception e){
            LogTool.ex(e);
        }
        return "";
    }
    public static String getLongTimeStr(long timeLong) {
        String add_time = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            add_time = formatter.format(timeLong );
        } catch (Exception e) {
            //LogTool.ex(e);
        }
        return add_time;
    }
    public static String getShortTimeStr(long timeLong) {
        String add_time = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "HH:mm:ss");
            add_time = formatter.format(timeLong );
        } catch (Exception e) {
            //LogTool.ex(e);
        }
        return add_time;
    }

    /***
     * 不要秒
     * @param timeLong
     * @return
     */
    public static String getShortTimeStrWithOutSecond(long timeLong) {
        String add_time = "";
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(
                    "HH:mm");
            add_time = formatter.format(timeLong );
        } catch (Exception e) {
            //LogTool.ex(e);
        }
        return add_time;
    }
    /**
     * 获取当前时间
     *
     * @return
     */
    public static String nowTimeString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
                .format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String nowTimeStringLong() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
                .format(new Date());
    }

    /***
     * @param dateStr 2016-05-28 10:00:00
     * @return
     */
    public static String getShortByDateStr(String dateStr) {
        String result = dateStr;
        try {
            result = getDateStr(dateStr, "yyyy-MM-dd HH:mm:ss", "MM月dd日 HH:mm");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * @param dateStr 2016-05-28 10:00:00
     * @return
     */
    public static String getShortDayStr(String dateStr) {
        String result = dateStr;
        try {
            result = getDateStr(dateStr, "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String getShortDayStr(int nian,int yue,int ri) {
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.YEAR,nian);
        calendar.set(Calendar.MONTH,yue);
        calendar.set(Calendar.DAY_OF_MONTH,ri);
       return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }
    /***
     * @param dateStr 2016-05-28 10:00:00
     * @return
     */
    public static String getDateStr(String dateStr, String formatSrc, String formatDest) {
        String result = dateStr;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatSrc);
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(formatDest);
            Date date = simpleDateFormat.parse(dateStr);
            result = simpleDateFormat2.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /***
     * 获取今天最开始的时间 比如 2016-2-24 0:00:00
     *
     * @return
     */
    public static Date

    getTodayDateBegin() {
        Calendar calendar = Calendar.getInstance();
        return getDateBegin(calendar);
    }

    /***
     * 获取今天最开始的时间 比如 2016-2-24 0:00:00
     *
     * @return
     */
    public static Date getDateBegin(Calendar calendarIn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendarIn.getTimeInMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /***
     * 获取今天结束的时间 比如 2016-2-24 23:59:59
     *
     * @return
     */
    public static Date getDateEnd(Calendar calendarIn) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(calendarIn.getTimeInMillis());

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取连个日期时间 之间的间隔天数
     *
     * @param startDateStr
     * @param endDateStr
     * @return
     */
    public static double dateAfterInt(String startDateStr, String endDateStr, String fromatIn) {
        try {
            String format = "yyyy-MM-dd HH:mm:ss";
            if (!StringTool.isEmpty(fromatIn)) {
                format = fromatIn;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            Date startDate = simpleDateFormat.parse(startDateStr);
            Date endDate = simpleDateFormat.parse(endDateStr);

            double day = ((endDate.getTime() - startDate.getTime()) * 1.0 / (oneDayTime));
            return day;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }

    /***
     * 显示选择日期的弹出框
     * @param activity
     * @param btn
     * @param tvShow
     */
    public static void showChooseDate(final Activity activity, View btn, final TextView tvShow) {
        try {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            UiTool.setTextView(tvShow, "" + year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);//小米1s 开始日期必须小于当前日期
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(30));
                    datePickerDialog.show();
                }
            });
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 显示选择时间的弹出框
     * @param activity
     * @param btn
     * @param tvShow
     */
    public static void showChooseTime(final Activity activity, View btn, final TextView tvShow) {
        try {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Calendar calendar = Calendar.getInstance();
                    TimePickerDialog timePickerDialog = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            UiTool.setTextView(tvShow, String.format("%02d:%02d", hourOfDay, minute));
                        }
                    }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                    timePickerDialog.show();
                }
            });
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }


    /**
     * 一个月第一天
     *
     * @param year
     * @param month 正常的月份， 1月就是1月
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /**
     * 一个月最后一天
     *
     * @param year
     * @param month 正常的月份， 1月就是1月
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }

    /***
     * 获取当前时间
     * @return
     */
    public static String getNowTime() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
    }
    /***
     * 获取当前日期
     * @return
     */
    public static String getNowDate() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }
    private static final long ONE_MINUTE = 60*1000;
    private static final long ONE_HOUR = 60*ONE_MINUTE;
    private static final long ONE_DAY = 24*ONE_HOUR;
    private static final long ONE_MONTH = 30*ONE_DAY;
    private static final long ONE_YEAR = 365*ONE_DAY;
    /***
     * 多少时间以前
     * @return
     */
    public static String getAgoTime(long time){
        long cha=System.currentTimeMillis()-time;
        if(cha<ONE_MINUTE){
            return cha/1000+"秒前";
        }else if(cha<ONE_HOUR){
            return cha/ONE_MINUTE+"分钟前";
        }else if(cha<ONE_DAY){
            return cha/ONE_HOUR+"小时前";
        }else if(cha<ONE_MONTH){
            return cha/ONE_DAY+"天前";
        }else if(cha<ONE_YEAR){
            return cha/ONE_MONTH+"月前";
        }else {
            return  cha/ONE_YEAR+"年前";
        }
    }

    /***
     *  如果是当天以前的消息则显示年/月/日 时:分:秒
     如果是当天的消息则只显示时:分:秒
     * @param timestamp
     * @return
     */
    public static String getTimeStrLongAndShort(long timestamp){
        String result="";
        Calendar calendar=Calendar.getInstance();
        int day=calendar.get(Calendar.DAY_OF_MONTH);
        calendar.setTimeInMillis(timestamp);
        if(calendar.get(Calendar.DAY_OF_MONTH)==day){//是今天的
            result=new SimpleDateFormat("HH:mm:ss").format(calendar.getTime());
        }else {
            result=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(calendar.getTime());
        }
        return result;
    }

}
