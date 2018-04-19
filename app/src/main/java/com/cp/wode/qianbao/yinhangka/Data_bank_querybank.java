package com.cp.wode.qianbao.yinhangka;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.LogTool;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 */

public class Data_bank_querybank extends ParentServerData {
    public List<BanksBean> banks;

    public static void load(HttpUiCallBack<Data_bank_querybank> httpUiCallBack){
        HttpToolAx.urlBase("bank/querybank")
                .send(Data_bank_querybank.class,httpUiCallBack );
    }
    public String[] getBankNams(){
        String[]bankNams=new String[banks.size()];
        for(int i=0;i<banks.size();i++){
            bankNams[i]=banks.get(i).bankName;
        }
       return bankNams;
    }
    public String getBankCode(int index){
        try {
            if(banks!=null&&index<banks.size()){
                return banks.get(index).bankCode;
            }
        }catch (Exception e){
            LogTool.ex(e);
        }

        return "";

    }
    public static class BanksBean {
        public String bankCode;
        public String bankName;
    }
}
