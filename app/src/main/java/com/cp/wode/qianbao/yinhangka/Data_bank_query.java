package com.cp.wode.qianbao.yinhangka;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 查询绑定的银行卡信息
 */

public class Data_bank_query extends ParentServerData {

    /**
     * code : 16
     * base_name : tanghao
     * base_bank_province : 四川省
     * base_bank_city : 成都市
     * base_bank_subbranch : etresrtertertret
     * base_bank_account : 3453453453453
     * base_bank_name : 中国建设银行
     */

    public String base_name;
    public String base_bank_province;
    public String base_bank_city;
    public String base_bank_subbranch;
    public String base_bank_account;
    public String base_bank_name;

    public static void load(HttpUiCallBack<Data_bank_query> httpUiCallBack){
        HttpToolAx.urlBase("bank/query")
                .send(Data_bank_query.class,httpUiCallBack );
    }


}
