package com.cp.wode.qianbao.chongzhi;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 *
 */

public class Data_recharge_online extends ParentServerData {

    /**
     * code : 21
     * serial : 18041943544542
     * url : https://myun.tenpay.com/mqq/pay/qrcode.html?_wv=1027&_bid=2183&t=6V359d5157d71af367797d0df77cdb6b
     */

    public String serial;
    public String url;

    public static void load(String amount, String type, final HttpUiCallBack<Data_recharge_online> httpUiCallBack){
        HttpToolAx.urlBase("recharge/online")
                .addQueryParams("amount",amount)
                .addQueryParams("type",type)
                .send(Data_recharge_online.class, httpUiCallBack);
    }



}
