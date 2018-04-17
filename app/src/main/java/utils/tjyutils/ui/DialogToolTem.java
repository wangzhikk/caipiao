package utils.tjyutils.ui;

import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import com.cp.R;

import utils.wzutils.common.LayoutInflaterTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.parent.WzViewOnclickListener;
import utils.wzutils.ui.dialog.DialogTool;

public class DialogToolTem {
    public static void showIosDialog(String title, String msg, String queding , final WzViewOnclickListener onclickListenerQueDing){
        try {
            View view= LayoutInflaterTool.getInflater(5, R.layout.dialog_ios_tishi).inflate();
            UiTool.setTextView(view,R.id.tv_ios_dialog_title,title);
            UiTool.setTextView(view,R.id.tv_ios_dialog_message,msg);
            UiTool.setTextView(view,R.id.tv_ios_dialog_queding,queding);
            TextView tv_ios_dialog_queding=view.findViewById(R.id.tv_ios_dialog_queding);

            final Dialog dialog=DialogTool.initNormalDialog(view,60);

            tv_ios_dialog_queding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if(onclickListenerQueDing!=null){
                        onclickListenerQueDing.onClick(v);
                    }
                }
            });
            dialog.show();
        }catch (Exception e){
            LogTool.ex(e);
        }

    }
}
