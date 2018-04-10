package utils.wzutils.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import utils.wzutils.AppTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;

/**
 * Created by kk on 2016/5/13.
 */
public class ListDialogTool {
    public static Dialog showListDialog(String title, CharSequence[] data, DialogInterface.OnClickListener onItemClickListener) {

        Dialog dialog = null;
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(AppTool.currActivity);
            if (!StringTool.isEmpty(title)) {
                builder.setTitle(title);
            }
            dialog = builder.setItems(data, onItemClickListener).create();

            dialog.show();

        } catch (Exception e) {
            LogTool.ex(e);
        }
        return dialog;
    }
}
