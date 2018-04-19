package com.cp.wode.shezhi.phone;

import com.cp.wode.Data_personinfo_query;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 * 修改登录密码接口
 */

public class Data_phone_binding extends ParentServerData {
    public static void load(String phone, String captcha, final HttpUiCallBack<Data_phone_binding> httpUiCallBack){
        HttpToolAx.urlBase("phone/binding")
                .addQueryParams("country",86)
                .addQueryParams("phone",phone)
                .addQueryParams("captcha",captcha)
                .send(Data_phone_binding.class, new HttpUiCallBack<Data_phone_binding>() {
                    @Override
                    public void onSuccess(Data_phone_binding data) {
                        if(data.isDataOk()){//绑定手机号成功后 需要更新个人信息里面的 手机号信息。。
                            Data_personinfo_query.load(null);
                        }
                        if(httpUiCallBack!=null){
                            httpUiCallBack.onSuccess(data);
                        }
                    }
                });
    }

}
