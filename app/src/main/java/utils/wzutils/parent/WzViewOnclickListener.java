package utils.wzutils.parent;

import android.view.View;

import utils.wzutils.common.LogTool;

/**
 * Created by kk on 2017/3/22.
 */

public abstract class WzViewOnclickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        try {
            onClickWz(v);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public abstract void onClickWz(View v);
}
