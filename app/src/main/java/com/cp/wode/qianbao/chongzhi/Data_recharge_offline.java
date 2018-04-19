package com.cp.wode.qianbao.chongzhi;

import java.io.Serializable;
import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 * 登录接口
 */

public class Data_recharge_offline extends ParentServerData {


    /***
     *     amount	是	String	充值金额
     name	是	String	打款人姓名
     bankName	是	String	打款银行名称，如果是支付宝则写：“支付宝”，如果是微信则写：“微信”
     type	是	String	打款方式，Bank-银行卡，Alipay-支付宝，Weixin-微信
     account	是	String	打款账号
     * @param amount
     * @param name
     * @param bankName
     * @param type
     * @param account
     * @param httpUiCallBack
     */
    public static void load(String amount, String name,String bankName,String type,String account,final HttpUiCallBack<Data_recharge_offline> httpUiCallBack){
        HttpToolAx.urlBase("recharge/offline")
                .addQueryParams("amount",amount)
                .addQueryParams("name",name)
                .addQueryParams("bankName",bankName)
                .addQueryParams("type",type)
                .addQueryParams("account",account)
                .send(Data_recharge_offline.class, httpUiCallBack);
    }


}
