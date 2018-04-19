package utils.tjyutils.parent;




import android.app.Dialog;
import android.content.DialogInterface;

import com.cp.MainActivity;
import com.cp.cp.Data_login_validate;
import com.cp.dengluzhuce.DengLuFragment;

import java.util.HashMap;
import java.util.Map;

import utils.wzutils.AppTool;
import utils.wzutils.ImgTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.json.JsonDataParent;
import utils.wzutils.ui.dialog.DialogTool;

/**
 * abc on 2016/5/13.
 */
public class ParentServerData extends JsonDataParent {
    static Map<String, String> dialogMap = new HashMap<>();

    public String imgSrc = "";

    public String getMsg() {
        return msg;
    }

    public boolean isDataOk() {
        checkLogin();
        return isDataOkWithOutCheckLogin();
    }

    public boolean isDataOkAndToast() {
        boolean ok = isDataOk();
        if (!ok && msg != null && msg.length() > 0) {
            CommonTool.showToast(msg);
        }
        return ok;
    }

    public boolean isDataOkWithOutCheckLogin() {
        if (!StringTool.isEmpty(imgSrc)) {
            ImgTool.defaultPreStr = imgSrc;
        }
        return code < 100;
    }

    public void checkLogin() {
        try {
            if (code == 401) {
                Data_login_validate.loginOutSuccess();//退出登录
                final String key = AppTool.currActivity.toString();
                if (dialogMap.get(key) == null) {
                    Dialog dialog = DialogTool.initNormalQueDingDialog("", "请登录", "登录", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new DengLuFragment().go();
                            dialog.dismiss();
                        }
                    }, "取消");
                    dialog.show();
                    dialogMap.put(key, "1");
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            dialogMap.remove(key);
                            if (AppTool.currActivity != null && (!(AppTool.currActivity instanceof MainActivity)))
                                AppTool.currActivity.finish();
                        }
                    });
                }
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }

    }


    /***
     * 自己构造数据的时候采用， 设置当前数据是正常的
     */
    public void setDataOk() {
        code = 0;
    }
}
