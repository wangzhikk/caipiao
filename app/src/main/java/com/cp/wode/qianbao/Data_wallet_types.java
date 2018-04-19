package com.cp.wode.qianbao;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 * 登录接口
 */

public class Data_wallet_types extends ParentServerData {

    public List<TypesBean> types;
    public static void load( final HttpUiCallBack<Data_wallet_types> httpUiCallBack){
        HttpToolAx.urlBase("wallet/types")
                .send(Data_wallet_types.class, httpUiCallBack);
    }

    public static class TypesBean {
        public String name;
        public String value;
    }
}
