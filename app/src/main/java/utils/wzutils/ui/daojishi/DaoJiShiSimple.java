package utils.wzutils.ui.daojishi;

import android.view.View;
import android.widget.TextView;

import java.util.HashMap;

import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.parent.WzParentActivity;
import utils.wzutils.parent.WzViewOnclickListener;

/**
 * abc on 2017/6/6.
 *
  new DaoJiShiSimple().initDaoJiShiWithPhoneCheck(group, 0, et_shouji_shoujihao, tv_huoqu_yanzhengma, tv_huoqu_yanzhengma, "", new DaoJiShiSimple.OnHuoQuYanZhengMa() {
@Override
public boolean onHuoQuYanZhengMa(final DaoJiShiSimple.OnHuoQuYanZhengMaSuccess onHuoQuYanZhengMaSuccess) {

if (et_shouji_shoujihao.isEnabled()) {
String phone = et_shouji_shoujihao.getText().toString().trim();
Data_sendphonelogin.loadNewPhone(phone, new HttpUiCallBack<Data_sendphonelogin>() {
@Override
public void onSuccess(Data_sendphonelogin data) {
onHuoQuYanZhengMaSuccess.onHuoQuYanZhengMaSuccess(data.isDataOk(), data.getMsg(), data.time);
}
});
} else {
Data_sendphonelogin.loadOldPhone(new HttpUiCallBack<Data_sendphonelogin>() {
@Override
public void onSuccess(Data_sendphonelogin data) {
onHuoQuYanZhengMaSuccess.onHuoQuYanZhengMaSuccess(data.isDataOk(), data.getMsg(), data.time);
}
});
}


return true;
}
});
 */

public class DaoJiShiSimple extends DaoJiShiControl {
    private static HashMap<String, DaoJiShiSimple> daoJiShiMap = new HashMap<>();
    DaoJiShiSimple currDaoJiShi;
    View clickBtn;
    TextView tvShow, tvPhone;
    String endStr;
    String group;//是否全局使用
    OnHuoQuYanZhengMa onHuoQuYanZhengMa;
    public DaoJiShiSimple(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
    }

    public DaoJiShiSimple() {
        super(90 * 1000, 1000);
    }

    public static String checkPhone(String phone) {
        String errorMsg = "";
        if (StringTool.isEmpty(phone) || phone.length() < 6) {
            errorMsg = "请输入正确的手机号码";
        }
        CommonTool.showToast(errorMsg);
        return errorMsg;
    }

    public void initDaoJiShi(String group, long currTime, View clickBtn, TextView tvShow, String endStr, OnHuoQuYanZhengMa onHuoQuYanZhengMa) {
        initDaoJiShiWithPhoneCheck(group, currTime, null, clickBtn, tvShow, endStr, onHuoQuYanZhengMa);
    }

    /***
     * 包含了 tvPhone 验证的
     * @param currTime
     * @param tvPhone
     * @param clickBtn
     * @param tvShow
     * @param endStr
     * @param onHuoQuYanZhengMa
     */
    public void initDaoJiShiWithPhoneCheck(String group, long currTime, final TextView tvPhone, View clickBtn, TextView tvShow, String endStr, OnHuoQuYanZhengMa onHuoQuYanZhengMa) {
        this.clickBtn = clickBtn;
        this.tvShow = tvShow;
        this.endStr = endStr;
        this.tvPhone = tvPhone;
        this.group = group;
        this.onHuoQuYanZhengMa = onHuoQuYanZhengMa;


        DaoJiShiSimple daoJiShiSimple = daoJiShiMap.get(group);
        if (daoJiShiSimple == null) {
            daoJiShiSimple = new DaoJiShiSimple(currTime, 1000);
            daoJiShiMap.put(group, daoJiShiSimple);
        } else {
            if (currTime > 0) {
                daoJiShiSimple.stop();
                daoJiShiSimple = new DaoJiShiSimple(currTime, 1000);
                daoJiShiMap.put(group, daoJiShiSimple);
            }
        }
        currDaoJiShi = daoJiShiSimple;

        if (StringTool.isEmpty(endStr)) {
            endStr = "获取验证码";
        }
        currDaoJiShi.setView(clickBtn, tvShow, endStr);
        clickBtn.setOnClickListener(new WzViewOnclickListener() {
            @Override
            public void onClickWz(View v) {
                if (tvPhone != null) {
                    String phone = tvPhone.getText().toString().trim();
                    String msg = checkPhone(phone);
                    if (StringTool.isEmpty(msg)) {
                        huoquyanzhengma();
                    }
                } else {
                    huoquyanzhengma();
                }
            }
        });
    }

    public void huoquyanzhengma() {
        OnHuoQuYanZhengMaSuccess onHuoQuYanZhengMaSuccess = new OnHuoQuYanZhengMaSuccess() {
            @Override
            public void onHuoQuYanZhengMaSuccess(boolean isDataOk, String msg, long time) {
                WzParentActivity.hideWaitingDialogStac();
                if (StringTool.notEmpty(msg)) {
                    CommonTool.showToast(msg);
                }
                if (isDataOk) {
                    initDaoJiShiWithPhoneCheck(group, time * 1000, tvPhone, clickBtn, tvShow, endStr, onHuoQuYanZhengMa);
                    currDaoJiShi.start();
                } else {
                    currDaoJiShi.stop();
                }
            }
        };
        if (onHuoQuYanZhengMa != null) {
            boolean sendOk = onHuoQuYanZhengMa.onHuoQuYanZhengMa(onHuoQuYanZhengMaSuccess);
            if (sendOk) WzParentActivity.showWaitingDialogStac("");
        } else {
            LogTool.s("onHuoQuYanZhengMa---------------不能为空");
        }
    }

    public static interface OnHuoQuYanZhengMa {
        /***
         * 是否需要显示加载进度框
         * @param onHuoQuYanZhengMaSuccess
         * @return
         */
        boolean onHuoQuYanZhengMa(OnHuoQuYanZhengMaSuccess onHuoQuYanZhengMaSuccess);
    }

    public static interface OnHuoQuYanZhengMaSuccess {
        void onHuoQuYanZhengMaSuccess(boolean isDataOk, String msg, long time);
    }

}
