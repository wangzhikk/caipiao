package com.cp.wode.qianbao.yinhangka;

import android.view.View;

import com.cp.cp.Data_login_validate;
import com.cp.wode.Data_personinfo_query;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.LogTool;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 */

public class Data_bank_replace extends ParentServerData {

    /***
     *    name	是	String	姓名
     bankCode	是	String	银行代码，例：ABC
     province	是	String	省份
     city	是	String	城市
     subbranch	是	String	开户行名称
     account	是	String	银行账号
     password
     * @param name
     * @param bankCode
     * @param province
     * @param city
     * @param subbranch
     * @param account
     * @param password
     * @param httpUiCallBack
     */
    public static void load(String name, String bankCode, String account, String province, String city, String subbranch, String password, final HttpUiCallBack<Data_bank_replace> httpUiCallBack){

        HttpUiCallBack<Data_bank_replace> httpUiCallBackLast=httpUiCallBack;
        String method="bank/replace";
        if(Data_login_validate.getData_login_validate().getUserInfo().hasBank()){//已经绑定银行卡
            method="bank/replace";
        }else {
            method="bank/binding";
            httpUiCallBackLast=new HttpUiCallBack<Data_bank_replace>() {
                @Override
                public void onSuccess(Data_bank_replace data) {
                    try {
                        if(data.isDataOk()){
                            Data_personinfo_query.load(null);
                        }
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                    httpUiCallBack.onSuccess(data);
                }
            };
        }
        HttpToolAx.urlBase(method)
                .addQueryParams("name",name)
                .addQueryParams("bankCode",bankCode)
                .addQueryParams("province",province)
                .addQueryParams("city",city)
                .addQueryParams("subbranch",subbranch)
                .addQueryParams("account",account)
                .addQueryParams("password",password)
                .send(Data_bank_replace.class,httpUiCallBackLast );
    }

}
