package com.cp.im;


import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.cp.shouye.Data_room_queryGame;
import com.cp.touzhu.Data_cqssc_top10;
import com.cp.wode.Data_personinfo_query;

import utils.tjyutils.common.Common;
import utils.wzutils.JsonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.TimeTool;

public class MsgDataSend {
    public MsgType msgType;
    public String roomNo;

    public long timestamp;//消息时间

    public String uuid;//用户id
    public String headImage;//用户头像图片
    public int grade;//用户会员级别
    public String nickname;//用户昵称
    public String msgContent;//聊天的 消息内容
    public int state=-1;//状态，1－可以开始下注，3－停止下注，封盘  10文本，11 图片

    public String toJsonStr(){
        return JsonTool.toJsonStr(this);
    }
    /**
     * 进入房间消息
     * @return
     * @param no
     */
    public static String getIntoRoomMsg(String no) {
        MsgDataSend msgDataSend=new MsgDataSend();
        msgDataSend.msgType=MsgType.INTOROOM;
        msgDataSend.roomNo=no;
        LogTool.s("构建进入房间 消息");
        return JsonTool.toJsonStr(msgDataSend);
    }



    //退出房间
    public static String getEXITROOMMsg() {
        MsgDataSend msgDataSend=new MsgDataSend();
        msgDataSend.msgType=MsgType.EXITROOM;
        LogTool.s("构建进入客服聊天 消息");
        return JsonTool.toJsonStr(msgDataSend);
    }


    /***
     * 发送普通消息
     * @param text
     * @param toId
     */
    public static void sendChatMsg(String text, String toId, IMTool.SendMsgListener sendMsgListener) {
        MsgDataSend msgDataSend=new MsgDataSend();
        msgDataSend.msgType=MsgType.CHAT;
        msgDataSend.timestamp=System.currentTimeMillis();
        msgDataSend.state=10;
        Data_personinfo_query data_personinfo_query=Data_login_validate.getData_login_validate().getUserInfo();
        msgDataSend.uuid=Data_login_validate.getData_login_validate().uuid;
        msgDataSend.grade=data_personinfo_query.base_grade;
        msgDataSend.nickname=data_personinfo_query.base_nickname;
        msgDataSend.headImage=data_personinfo_query.base_headImage;

        msgDataSend.msgContent=text;

        IMTool.getIntance().sendMsg(JsonTool.toJsonStr(msgDataSend),toId,sendMsgListener);
    }

    public static void sendExitService() {
        MsgDataSend msgDataSend=new MsgDataSend();
        msgDataSend.msgType=MsgType.EXITSERVICE;
        IMTool.getIntance().sendMsg(msgDataSend.toJsonStr(),"0",null);
    }
    public static void sendInToService() {
        MsgDataSend msgDataSend=new MsgDataSend();
        msgDataSend.msgType=MsgType.INTOSERVICE;
        IMTool.getIntance().sendMsg(msgDataSend.toJsonStr(),"0",null);
    }
}
