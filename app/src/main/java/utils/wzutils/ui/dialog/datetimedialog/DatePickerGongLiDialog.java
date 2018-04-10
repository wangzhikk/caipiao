package utils.wzutils.ui.dialog.datetimedialog;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import utils.wzutils.AppTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.NongLiTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.dialog.DialogTool;

/**
 */
public class DatePickerGongLiDialog extends WzDatePickerDialog {
    public DatePickerGongLiDialog(Context context) {
        super(context);
    }

    public DatePickerGongLiDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DatePickerGongLiDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    public void reshreTitle(){
//        {//可显示农历
//            String title=NongLiTool.getWenDao(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.HOUR_OF_DAY));
//            tvTitle.setText(""+title);
//        }
    }
}