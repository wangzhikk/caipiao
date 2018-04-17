package utils.tjyutils.ui;

import android.view.View;
import android.widget.TextView;

import com.cp.R;

import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.dialog.DialogTool;

public class DialogToolTem {
    public static void showIosDialog(String title, String msg, String queding , WzViewOnclickListener onclickListenerQueDing){
        View view= LayoutInflaterTool.getInflater(5, R.layout.dialog_ios_tishi).inflate();
        UiTool.setTextView(view,R.id.tv_ios_dialog_title,title);
        UiTool.setTextView(view,R.id.tv_ios_dialog_message,msg);
        UiTool.setTextView(view,R.id.tv_ios_dialog_queding,queding);
        TextView tv_ios_dialog_queding=view.findViewById(R.id.tv_ios_dialog_queding);
        tv_ios_dialog_queding.setOnClickListener(onclickListenerQueDing);
        DialogTool.initNormalDialog(view,60).show();
    }
}
