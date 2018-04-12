package com.cp.xiaoxi;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;


public class Data_message_detail extends ParentServerData {

    /**
     * code : 16
     * detail : {"id":6,"message_category":0,"message_title":"Test","message_content":"Test","message_time":"2017-09-17 20:00:00.0","message_viewtimes":3}
     */

    public MessageDetailBean detail;

    public static void load(String id, final HttpUiCallBack<Data_message_detail> httpUiCallBack){
        HttpToolAx.urlBase("message/detail")
                .addQueryParams("id",id)
                .send(Data_message_detail.class, httpUiCallBack);
    }

    public static class MessageDetailBean extends ParentServerData{

        public String id;
        public int message_category;
        public String message_title;
        public String message_content;
        public String message_time;
        public int message_viewtimes;
        public int read_state;
    }
}
