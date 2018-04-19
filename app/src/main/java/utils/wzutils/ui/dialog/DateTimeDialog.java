package utils.wzutils.ui.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import utils.wzutils.AppTool;

/**
 * abc on 2017/9/5.
 */

public class DateTimeDialog {
    public static void showDateAndTimeDialog(final OnDateTimeChooseListener onDateTimeChooseListener){
        showDateAndTimeDialog(onDateTimeChooseListener,null,null);
    }
    public static void showDateAndTimeDialog(final OnDateTimeChooseListener onDateTimeChooseListener ,Calendar max,Calendar min){
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog datePickerDialog=new DatePickerDialog(AppTool.currActivity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                new TimePickerDialog(AppTool.currActivity, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar calendar=Calendar.getInstance();
                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        String dateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
                        if(onDateTimeChooseListener!=null)onDateTimeChooseListener.onDataTimeChoose(dateTime);
                    }
                },0,0,true).show();
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));

        if(max!=null)datePickerDialog.getDatePicker().setMaxDate(max.getTimeInMillis());
        if(min!=null)datePickerDialog.getDatePicker().setMinDate(min.getTimeInMillis());
        datePickerDialog.show();
    }
    public static interface  OnDateTimeChooseListener{
        void onDataTimeChoose(String dateTime);
    }
}
