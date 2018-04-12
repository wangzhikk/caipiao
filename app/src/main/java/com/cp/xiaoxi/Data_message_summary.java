package com.cp.xiaoxi;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;


public class Data_message_summary extends ParentServerData {

    /**
     * code : 16
     * agent : {"notRead":28,"latest":"代理公告"}
     * message : {"notRead":50,"latest":"指定会员消息1sascdsd"}
     * notice : {"notRead":32,"latest":"所有会员公告213"}
     */

    public AgentBean agent;
    public MessageBean message;
    public NoticeBean notice;

    public static void load(final HttpUiCallBack<Data_message_summary> httpUiCallBack){
        HttpToolAx.urlBase("message/summary")
                .send(Data_message_summary.class, httpUiCallBack);
    }


    public static class AgentBean {

        public int notRead;
        public String latest;
    }

    public static class MessageBean {


        public int notRead;
        public String latest;
    }

    public static class NoticeBean {


        public int notRead;
        public String latest;
    }
}
