package utils.wzutils.ui;

import android.app.ProgressDialog;

import utils.wzutils.AppTool;
import utils.wzutils.common.LogTool;

/**
 * Created by kk on 2016/5/13.
 */
public class WaitingDialogTool {
    static ProgressDialog progressDialog;

    public static void showWaitingDialog() {
        try {
            hideWaitingDialog();
            progressDialog = ProgressDialog.show(AppTool.currActivity, "", "请稍等...", false, false);
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }

    public static void hideWaitingDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }
}
