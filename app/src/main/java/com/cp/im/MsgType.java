package com.cp.im;

import java.io.Serializable;

public enum  MsgType implements Serializable{
    INTOROOM,//进入房间
    EXITROOM, //退出房间
    INTOSERVICE,//进入客服
    CHAT, EXITSERVICE,//聊天

}
