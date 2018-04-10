package com.cp.wode.shezhi.mima;

import com.cp.wode.Data_personinfo_query;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 修改设置 资金密码
 */

public class Data_thirdpassword_reset extends ParentServerData {
    public static void load(String phone, String password, String captcha, final HttpUiCallBack<Data_thirdpassword_reset> httpUiCallBack){
        if(StringTool.isEmpty(phone)){//第一次 用设置 资金密码
            HttpToolAx.urlBase("thirdpassword/set")
                    .addQueryParams("password",password)
                    .send(Data_thirdpassword_reset.class, new HttpUiCallBack<Data_thirdpassword_reset>() {
                        @Override
                        public void onSuccess(Data_thirdpassword_reset data) {
                            try {
                                if(data.isDataOk()){
                                    Data_personinfo_query.load(null);
                                }
                            }catch (Exception e){
                                LogTool.ex(e);
                            }
                            if(httpUiCallBack!=null){
                                httpUiCallBack.onSuccess(data);
                            }
                        }
                    });
        }else {
            HttpToolAx.urlBase("thirdpassword/reset")
                    .addQueryParams("country",86)
                    .addQueryParams("phone",phone)
                    .addQueryParams("password",password)
                    .addQueryParams("captcha",captcha)
                    .send(Data_thirdpassword_reset.class,httpUiCallBack );
        }
    }

}
