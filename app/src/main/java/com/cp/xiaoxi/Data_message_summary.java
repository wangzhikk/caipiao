package com.cp.xiaoxi;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.BroadcastReceiverTool;
import utils.wzutils.common.LogTool;
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
    public static int xiaoxi_num;
    public static final String action_xiaoxi_change="action_xiaoxi_change";
    public static void load(final HttpUiCallBack<Data_message_summary> httpUiCallBack){
        HttpToolAx.urlBase("message/summary")
                .send(Data_message_summary.class, new HttpUiCallBack<Data_message_summary>() {
                    @Override
                    public void onSuccess(Data_message_summary data) {
                        try {
                            if(data.isDataOkWithOutCheckLogin()){
                                int curr_xiaoxi_num=0;
                                if(data.notice!=null){
                                    curr_xiaoxi_num+=data.notice.notRead;
                                }
                                if(data.agent!=null){
                                    curr_xiaoxi_num+=data.agent.notRead;
                                }
                                if(data.message!=null){
                                    curr_xiaoxi_num+=data.message.notRead;
                                }
                                if(xiaoxi_num!=curr_xiaoxi_num){
                                    xiaoxi_num=curr_xiaoxi_num;
                                    BroadcastReceiverTool.sendAction(action_xiaoxi_change,xiaoxi_num);
                                }
                            }
                        }catch (Exception e){
                            LogTool.ex(e);
                        }

                        if(httpUiCallBack!=null)httpUiCallBack.onSuccess(data);
                    }
                });
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
