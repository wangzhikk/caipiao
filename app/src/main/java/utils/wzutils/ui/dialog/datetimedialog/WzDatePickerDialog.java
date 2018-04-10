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
import utils.wzutils.common.UiTool;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.dialog.DialogTool;

/**
 *
 */
public class WzDatePickerDialog extends RelativeLayout {
    public DatePickerView dp_nian;
    public DatePickerView dp_yue;
    public DatePickerView dp_ri;
    public DatePickerView dp_shi;
    public DatePickerView dp_fen;
    public TextView tvTitle;
    int colorSelected=Color.parseColor("#e42b45");
    int colorNormal=Color.parseColor("#737373");
    public Calendar calendar=Calendar .getInstance();
    Calendar maxCalendar=Calendar .getInstance();//最大时间
    Calendar minCalendar=Calendar .getInstance();//最小时间
    public  void show(){
        DialogTool.initBottomDialog(this).show();
    }

    public WzDatePickerDialog(Context context) {
        super(context);
        init();
    }

    public WzDatePickerDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WzDatePickerDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){
         int currYear=calendar.get(Calendar.YEAR);
         maxCalendar.set(currYear+130,11,31,23,59);
         minCalendar.set(currYear-130,0,1,0,0);

        ViewGroup.LayoutParams layoutParams=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setLayoutParams(layoutParams);
        addContent();
    }


    /***
     * 主要的 限制 在这里，可子类覆盖
     * @param linearLayout
     */
    public void addPickerDateItemsImp(LinearLayout linearLayout){
        List<String> nianDatas=getList(minCalendar.get(Calendar.YEAR),maxCalendar.get(Calendar.YEAR));
        dp_nian=addPickerDateItem(linearLayout,nianDatas);
        dp_nian.setSelected(""+calendar.get(Calendar.YEAR));
        dp_yue=addPickerDateItem(linearLayout,getList(1,12));
        dp_yue.setSelected(""+(calendar.get(Calendar.MONTH)+1));
        dp_ri=addPickerDateItem(linearLayout,getList(1,31));
        dp_ri.setSelected(""+(calendar.get(Calendar.DAY_OF_MONTH)));
        dp_shi=addPickerDateItem(linearLayout,getList(0,23));
        dp_shi.setSelected(""+calendar.get(Calendar.HOUR_OF_DAY));
        dp_fen=addPickerDateItem(linearLayout,getList(0,59));
        dp_fen.setSelected(""+calendar.get(Calendar.MINUTE));

        dp_nian.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                calendar.set(Calendar.YEAR,Integer.valueOf(text));
                checkYue();
                checkRi();
                checkShi();
                checkFen();
            }
        });
        dp_yue.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                int newMonth=Integer.valueOf(text)-1;
                //处理2月问题  由于 如果日期当前是31号，  month 设置为2月的时候 那么month 就默认变为3月了。。，所以这里 不让超过28.
                {
                    int day=calendar.get(Calendar.DAY_OF_MONTH);
                    if(newMonth==1&&day>=28){//2月
                        calendar.set(Calendar.DAY_OF_MONTH,28);
                    }
                    dp_ri.setSelected("28");
                }
                calendar.set(Calendar.MONTH,newMonth);
                checkRi();
                checkShi();
                checkFen();
            }
        });
        dp_ri.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                calendar.set(Calendar.DAY_OF_MONTH,Integer.valueOf(text));
                checkShi();
                checkFen();
            }
        });
        dp_shi.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                calendar.set(Calendar.HOUR_OF_DAY,Integer.valueOf(text));
                checkFen();
            }
        });
    }

    /**
     * 限制分钟显示
     */
    public void checkFen(){
        {//验证分
            String fen=dp_fen.getCurrSelected();
            if(calendar.get(Calendar.YEAR)==minCalendar.get(Calendar.YEAR)
                    &&calendar.get(Calendar.MONTH)==minCalendar.get(Calendar.MONTH)
                    &&calendar.get(Calendar.DAY_OF_MONTH)==minCalendar.get(Calendar.DAY_OF_MONTH)
                    &&calendar.get(Calendar.HOUR_OF_DAY)==minCalendar.get(Calendar.HOUR_OF_DAY)
                    ){//最小的一年
                dp_fen.setData(getList(minCalendar.get(Calendar.MINUTE),59));
            }else if(calendar.get(Calendar.YEAR)==maxCalendar.get(Calendar.YEAR)
                    &&calendar.get(Calendar.MONTH)==maxCalendar.get(Calendar.MONTH)
                    &&calendar.get(Calendar.DAY_OF_MONTH)==maxCalendar.get(Calendar.DAY_OF_MONTH)
                    &&calendar.get(Calendar.HOUR_OF_DAY)==maxCalendar.get(Calendar.HOUR_OF_DAY)
                    ){//最大的一年
                dp_fen.setData(getList(0,maxCalendar.get(Calendar.MINUTE)));
            }else {
                dp_fen.setData(getList(0,59));
            }
            dp_fen.setSelected(fen);
        }
    }
    /**
     * 显示小时显示
     */
    public void checkShi(){
        {//验证时
            String shi=dp_shi.getCurrSelected();
            if(calendar.get(Calendar.YEAR)==minCalendar.get(Calendar.YEAR)
                    &&calendar.get(Calendar.MONTH)==minCalendar.get(Calendar.MONTH)
                    &&calendar.get(Calendar.DAY_OF_MONTH)==minCalendar.get(Calendar.DAY_OF_MONTH)
                    ){//最小的一年
                dp_shi.setData(getList(minCalendar.get(Calendar.HOUR_OF_DAY),23));
            }else if(calendar.get(Calendar.YEAR)==maxCalendar.get(Calendar.YEAR)
                    &&calendar.get(Calendar.MONTH)==maxCalendar.get(Calendar.MONTH)
                    &&calendar.get(Calendar.DAY_OF_MONTH)==maxCalendar.get(Calendar.DAY_OF_MONTH)
                    ){//最大的一年
                dp_shi.setData(getList(0,maxCalendar.get(Calendar.HOUR_OF_DAY)));
            }else {
                dp_shi.setData(getList(0,23));
            }
            dp_shi.setSelected(shi);
        }
    }

    /**
     * 限制 天 的显示
     */
    public void checkRi(){
        {//验证日
            String ri=dp_ri.getCurrSelected();
            int max=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);//这个月最大数，  如果选农历 可改为不限制了
            if(calendar.get(Calendar.YEAR)==minCalendar.get(Calendar.YEAR)
                    &&calendar.get(Calendar.MONTH)==minCalendar.get(Calendar.MONTH)
                    ){//最小的一年
                dp_ri.setData(getList(minCalendar.get(Calendar.DAY_OF_MONTH),max));
            }else if(calendar.get(Calendar.YEAR)==maxCalendar.get(Calendar.YEAR)
                    &&calendar.get(Calendar.MONTH)==maxCalendar.get(Calendar.MONTH)
                    ){//最大的一年
                dp_ri.setData(getList(1,maxCalendar.get(Calendar.DAY_OF_MONTH)));
            }else {
                dp_ri.setData(getList(1,max));
            }
            dp_ri.setSelected(ri);
        }
    }

    /***
     * 限制月的显示
     */
    public void checkYue(){
        {//验证月
            String yue=dp_yue.getCurrSelected();
            if(calendar.get(Calendar.YEAR)==minCalendar.get(Calendar.YEAR)){//最小的一年
                dp_yue.setData(getList(minCalendar.get(Calendar.MONTH)+1,12));
            }else if(calendar.get(Calendar.YEAR)==maxCalendar.get(Calendar.YEAR)){//最大的一年
                dp_yue.setData(getList(1,maxCalendar.get(Calendar.MONTH)+1));
            }else {
                dp_yue.setData(getList(1,12));
            }
            dp_yue.setSelected(yue);
        }
    }


    public void addContent(){
        LinearLayout linearLayout=new LinearLayout(getContext());
        linearLayout.setBackgroundColor(Color.WHITE);
        LayoutParams lp=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, -2);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        linearLayout.setLayoutParams(lp);
        linearLayout.setOrientation(LinearLayout.VERTICAL);


        addTitle(linearLayout);
        addLineH(linearLayout);
        addTitleDate(linearLayout);
        addLineH(linearLayout);
        addPickerDate(linearLayout);
        addLineH(linearLayout);
        addBtnControl(linearLayout);
        addView(linearLayout);
    }

    public void addTitle(ViewGroup viewGroup){
        tvTitle=new TextView(getContext());
        UiTool.setWH(tvTitle,ViewGroup.LayoutParams.MATCH_PARENT,CommonTool.dip2px(40));
        tvTitle.setText("选择时间");
        tvTitle.setGravity(Gravity.CENTER);
        tvTitle.setTextColor(colorNormal);
        viewGroup.addView(tvTitle);
    }

    public void addLineH(ViewGroup viewGroup){
        View view=new View(getContext());
        UiTool.setWH(view, ViewGroup.LayoutParams.MATCH_PARENT,1);
        view.setBackgroundColor(Color.parseColor("#dedede"));
        viewGroup.addView(view);
    }
    public void addLineV(ViewGroup viewGroup){
        View view=new View(getContext());
        UiTool.setWH(view, 1,-1);
        view.setBackgroundColor(Color.parseColor("#dedede"));
        viewGroup.addView(view);
    }

    public void addTitleDate(ViewGroup viewGroup){
        LinearLayout linearLayout=new LinearLayout(getContext());
        UiTool.setWH(linearLayout,ViewGroup.LayoutParams.MATCH_PARENT,CommonTool.dip2px(40));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addTitleDateText(linearLayout,"年");
        addTitleDateText(linearLayout,"月");
        addTitleDateText(linearLayout,"日");
        addTitleDateText(linearLayout,"时");
        addTitleDateText(linearLayout,"分");
        viewGroup.addView(linearLayout);
    }

    public void addTitleDateText(LinearLayout viewGroup,String text){
        TextView textView=new TextView(getContext());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,-1,1);
        textView.setLayoutParams(lp);
        textView.setText(""+text);
        textView.setTextColor(colorSelected);
        textView.setGravity(Gravity.CENTER);
        viewGroup.addView(textView);
    }



    public void addPickerDate(ViewGroup viewGroup){
        LinearLayout linearLayout=new LinearLayout(getContext());
        UiTool.setWH(linearLayout,ViewGroup.LayoutParams.MATCH_PARENT,CommonTool.dip2px(130));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addPickerDateItemsImp(linearLayout);
        viewGroup.addView(linearLayout);
    }
    /***
     * 字段拾取的控件
     * @param viewGroup
     */
    public DatePickerView addPickerDateItem(ViewGroup viewGroup,List<String> datas){
        DatePickerView datePickerView=new DatePickerView(getContext(),null);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,-1,1);
        datePickerView.setLayoutParams(lp);
        datePickerView.setData(datas);
        viewGroup.addView(datePickerView);
        return  datePickerView;
    }
    public void addBtnControl(ViewGroup viewGroup){
        LinearLayout linearLayout=new LinearLayout(getContext());
        UiTool.setWH(linearLayout,ViewGroup.LayoutParams.MATCH_PARENT,CommonTool.dip2px(40));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        addBtnControlItem(linearLayout,"取消",null);
        addLineV(linearLayout);
        addBtnControlItem(linearLayout, "确定", new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                int nian=Integer.valueOf(dp_nian.getCurrSelected());
                int yue=Integer.valueOf(dp_yue.getCurrSelected());
                int ri=Integer.valueOf(dp_ri.getCurrSelected());
                int shi=Integer.valueOf(dp_shi.getCurrSelected());
                int fen=Integer.valueOf(dp_fen.getCurrSelected());
                Calendar calendar=Calendar.getInstance();
                calendar.set(Calendar.YEAR,nian);
                calendar.set(Calendar.MONTH,yue);
                calendar.set(Calendar.DATE,ri);
                calendar.set(Calendar.HOUR,shi);
                calendar.set(Calendar.MINUTE,fen);
                if(getOnChooseDateTimeListener()!=null){
                    getOnChooseDateTimeListener().onChoose(calendar);
                }
                CommonTool.showToast(""+nian+" "+yue+" "+ri+" "+shi+" "+fen);
            }
        });
        viewGroup.addView(linearLayout);
    }

    public void addBtnControlItem(ViewGroup viewGroup, String text, WzViewOnclickListener wzViewOnclickListener){
        TextView textView=new TextView(getContext());
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,-1,1);
        textView.setLayoutParams(lp);
        textView.setText(""+text);
        textView.setTextColor(colorNormal);
        textView.setGravity(Gravity.CENTER);
        viewGroup.addView(textView);
        if(wzViewOnclickListener!=null){
            textView.setTextColor(colorSelected);
            textView.setOnClickListener(wzViewOnclickListener);
        }
    }

    public OnChooseDateTimeListener getOnChooseDateTimeListener() {
        return onChooseDateTimeListener;
    }
    public void setOnChooseDateTimeListener(OnChooseDateTimeListener onChooseDateTimeListener) {
        this.onChooseDateTimeListener = onChooseDateTimeListener;
    }
    OnChooseDateTimeListener onChooseDateTimeListener;
    public static interface  OnChooseDateTimeListener{
        void onChoose(Calendar calendar);
    }
    public List<String> getList(int min,int max){
        List<String>  list=new ArrayList<>();
        for(int i=min;i<=max;i++){
            list.add(""+i);
        }
        return list;
    }

}