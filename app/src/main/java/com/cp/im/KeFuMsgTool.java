package com.cp.im;

import com.cp.cp.Data_login_validate;

import java.util.ArrayList;
import java.util.List;

import utils.wzutils.common.LogTool;
import utils.wzutils.db.MapDB;

public class KeFuMsgTool {
    public static IMTool.MsgHandlerListener msgHandlerListener;

    public static List<MsgData> getMsgDataList() {
        if (msgDataList == null || msgDataList.size() == 0) {
            loadMsgDataList();
        }
        return new ArrayList<>(msgDataList);
    }

    private static List<MsgData> msgDataList = new ArrayList<>();
    public static void destory(){
        msgDataList=null;//清除内存中的数据
    }
    public static void init() {
        destory();
        getMsgDataList();

        if (msgHandlerListener == null) {
            msgHandlerListener = new IMTool.MsgHandlerListener() {
                @Override
                public boolean handError(int errorCode, String errorMsg) {
                    return false;
                }

                @Override
                public boolean handMsgRecive(MsgData msgData) {
                    addMsgData(msgData);
                    return false;
                }

                @Override
                public boolean messagesBeReceived(String theFingerPrint) {
                    return super.messagesBeReceived(theFingerPrint);
                }
            };
            IMTool.getIntance().addMsgListener(msgHandlerListener);
        }
    }
    public static void addMsgData(MsgData msgData) {
        try {
            if (msgData.msgDetailData.isChatMsg() || msgData.msgDetailData.isServiceError()) {
                msgDataList.add(msgData);
                saveMsgDataList();
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public static void saveMsgDataList() {
        if (msgDataList.size() > 100) {
            msgDataList = msgDataList.subList(msgDataList.size() - 100, msgDataList.size());
        }

        List<MsgData> list = new ArrayList<>(msgDataList);
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size()) {
                MsgData msgData = list.get(i);
                if (msgData.msgDetailData.isServiceError()) {//错误信息不存储
                    list.remove(msgData);
                    i--;
                }
            }
        }
        if(msgDataList!=null&&msgDataList.size()>0){
            MapDB.saveObj("kefu"+ Data_login_validate.getData_login_validate().uuid, list);
        }
    }

    ;

    public static void loadMsgDataList() {
        msgDataList = MapDB.loadObjList("kefu"+Data_login_validate.getData_login_validate().uuid, MsgData.class);
    }


}
