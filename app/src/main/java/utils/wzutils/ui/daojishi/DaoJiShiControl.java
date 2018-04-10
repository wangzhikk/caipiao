package utils.wzutils.ui.daojishi;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import utils.wzutils.common.LogTool;

/**
 * Created by ishare on 2016/11/16.
 * 倒计时  控制器， 主要是 验证码可以用
 */
public class DaoJiShiControl extends CountDownTimer {
    View clickBtn;
    TextView tvShow;
    long currentTime = 0;
    String endStr = "";

    public DaoJiShiControl(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public void setView(View clickBtn, TextView tvShow, String endStr) {
        this.clickBtn = clickBtn;
        this.tvShow = tvShow;
        this.endStr = "" + endStr;
        if (currentTime > 0) {
            onTick(currentTime);
        }
    }

    @Override
    public void onFinish() {
        currentTime = -1;
        if (clickBtn != null && tvShow != null) {
            clickBtn.setClickable(true);
            tvShow.setText(endStr);
        }
    }

    @Override
    public void onTick(long millisUntilFinished) {
        try {
            currentTime = millisUntilFinished;
            //LogTool.s(currentTime/1000);
            if (clickBtn != null && tvShow != null) {
                clickBtn.setClickable(false);
                tvShow.setText(millisUntilFinished / 1000 + "s");
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    /***
     * 停止倒计时
     */
    public void stop() {
        cancel();
        onFinish();
    }
}
