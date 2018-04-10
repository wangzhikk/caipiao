package com.cp.cp;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 */

public class Data_wallet_remain extends ParentServerData {

    public double remain;
    public static void load( HttpUiCallBack<Data_wallet_remain> httpUiCallBack){
        HttpToolAx.urlBase("wallet/remain")
                .send(Data_wallet_remain.class,httpUiCallBack );
    }

}
