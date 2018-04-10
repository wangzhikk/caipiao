package utils.tjyutils.common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.cp.R;
import com.cp.shouye.Data_room_queryGame;

import java.util.Calendar;
import java.util.List;

import utils.wzutils.AppTool;
import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.TimeTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.WzSimpleRecycleView;
import utils.wzutils.ui.dialog.DialogTool;
import utils.wzutils.ui.pullrefresh.WzRefreshLayout;

public class TimeTypeChoose {

    public TextView tv_touzhu_jilu_choose_time_start;
    public TextView tv_touzhu_jilu_choose_time_end;
    public View vg_touzhu_jilu_choose_type;
    public TextView tv_touzhu_jilu_choose_type;

    public static class TypeData{
        public String name="";
        public String value="";
        public TypeData(String name,String value){
            this.name=name;
            this.value=value;
        }
    }
    public void init(View parent, final List<TypeData> typeDataList, final OnChooseTimeTypeListener onChooseTimeTypeListener){
        tv_touzhu_jilu_choose_time_start=parent.findViewById(R.id.tv_touzhu_jilu_choose_time_start);
        tv_touzhu_jilu_choose_time_end=parent.findViewById(R.id.tv_touzhu_jilu_choose_time_end);
        vg_touzhu_jilu_choose_type=parent.findViewById(R.id.vg_touzhu_jilu_choose_type);
        tv_touzhu_jilu_choose_type=parent.findViewById(R.id.tv_touzhu_jilu_choose_type);
        final Calendar calendar = Calendar.getInstance();
        UiTool.setTextView(tv_touzhu_jilu_choose_time_start, TimeTool.getShortDayStr(calendar.get(Calendar.YEAR)-1,calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)));
        UiTool.setTextView(tv_touzhu_jilu_choose_time_end, TimeTool.getShortDayStr(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)));

        setType(typeDataList.get(0));

            tv_touzhu_jilu_choose_time_start.setOnClickListener(new WzViewOnclickListener() {
                @Override
                public void onClickWz(View v) {
                    final Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(AppTool.currActivity, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                            UiTool.setTextView(tv_touzhu_jilu_choose_time_start, TimeTool.getShortDayStr(year,month,dayOfMonth));
                            onChoose(onChooseTimeTypeListener);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                    datePickerDialog.show();
                }
            });
            tv_touzhu_jilu_choose_time_end.setOnClickListener(new WzViewOnclickListener() {
                @Override
                public void onClickWz(View v) {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(AppTool.currActivity, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, final int year, final int month, final int dayOfMonth) {
                            UiTool.setTextView(tv_touzhu_jilu_choose_time_end, TimeTool.getShortDayStr(year,month,dayOfMonth));
                            onChoose(onChooseTimeTypeListener);
                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                    datePickerDialog.show();
                }
            });

            vg_touzhu_jilu_choose_type.setOnClickListener(new WzViewOnclickListener() {
                @Override
                public void onClickWz(View v) {

                    View view= LayoutInflaterTool.getInflater(3, R.layout.touzhu_jilu_bottom).inflate();
                    final Dialog dialog=DialogTool.initBottomDialog(view);
                    WzSimpleRecycleView recycleView=view.findViewById(R.id.recycleView);

                    recycleView.setData(typeDataList, R.layout.touzhu_jilu_bottom_item, new WzSimpleRecycleView.WzRecycleAdapter() {
                        @Override
                        public void initData(int position, int type, View itemView) {
                            super.initData(position, type, itemView);
                            final TypeData typeData=typeDataList.get(position);
                            UiTool.setTextView(itemView,R.id.tv_touzhu_jilu_bottom_item,typeData.name);
                            itemView.setOnClickListener(new WzViewOnclickListener() {
                                @Override
                                public void onClickWz(View v) {
                                    setType(typeData);
                                    onChoose(onChooseTimeTypeListener);
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                    dialog.show();
                }
            });
    }
    private void setType(TypeData typeData){
        UiTool.setTextView(tv_touzhu_jilu_choose_type,typeData.name);
        tv_touzhu_jilu_choose_type.setTag(typeData);
    }
    private TypeData getType(){
        Object o= tv_touzhu_jilu_choose_type.getTag();
        if(o!=null &&o instanceof TypeData){
            return (TypeData) o;
        }else {
            return new TypeData("","");
        }
    }

    private void onChoose(OnChooseTimeTypeListener onChooseTimeTypeListener){
        if(onChooseTimeTypeListener!=null)onChooseTimeTypeListener.onChoose(getType(),tv_touzhu_jilu_choose_time_start.getText().toString(),tv_touzhu_jilu_choose_time_end.getText().toString());
    }
    public static interface OnChooseTimeTypeListener{
        public void onChoose(TypeData typeData,String timeStart,String timeEnd);
    }
}
