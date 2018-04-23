package utils.wzutils.parent;

import android.view.View;

import utils.wzutils.common.LogTool;

/**
 * Created by kk on 2017/3/22.
 */

public abstract class WzViewOnclickListener implements View.OnClickListener {
    static  long timePreClick=0;
    @Override
    public void onClick(View v) {
        try {
            long time=System.currentTimeMillis();
            if(time-timePreClick<500)return;//500毫秒内只能点击一次
            onClickWz(v);
            timePreClick=time;
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public abstract void onClickWz(View v);
}
