package com.cp.cp;

import com.cp.im.IMTool;

import java.net.MalformedURLException;
import java.net.URL;

import utils.tjyutils.http.HttpConfigAx;
import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.LogTool;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.update.Version;

/**
 * abc on 2018/2/24.
 */

public class Data_msgserver extends ParentServerData {
    public String msgserver;
    public static void load(final HttpUiCallBack<Data_msgserver> httpUiCallBack){
        HttpToolAx.urlBase("msgserver")
                .send(Data_msgserver.class, new HttpUiCallBack<Data_msgserver>() {
                    @Override
                    public void onSuccess(Data_msgserver data) {
                        if(data.isDataOkWithOutCheckLogin()){
                            try {
//                                URL url=new URL(data.msgserver);
                                String[] tem=data.msgserver.split(":");
                                if(tem.length==2){
                                    String ip=tem[0];
                                    int port=Integer.valueOf(tem[1]);
                                    HttpConfigAx.imIp=ip;
                                    HttpConfigAx.imPort=port;
                                }

                            } catch (Exception e) {
                                LogTool.ex(e);
                            }
                        }
                        if(httpUiCallBack!=null){
                            httpUiCallBack.onSuccess(data);
                        }
                    }
                });
    }

}
