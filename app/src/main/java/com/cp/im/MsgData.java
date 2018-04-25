package com.cp.im;


import android.graphics.Rect;
import android.view.View;

import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.cp.shouye.Data_room_queryGame;
import com.cp.touzhu.Data_cqssc_top10;
import com.cp.wode.Data_personinfo_query;

import java.util.Calendar;
import java.util.List;

import utils.tjyutils.common.Common;
import utils.tjyutils.common.TitleTool;
import utils.wzutils.JsonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.TimeTool;
import utils.wzutils.common.UiTool;

public class MsgData {
    public MsgData(String fingerPrintOfProtocal, String userid, String dataContent, int typeu) {
        this.fingerPrintOfProtocal = fingerPrintOfProtocal;
        this.userid = userid;
        this.dataContent = dataContent;
        this.typeu = typeu;
        try {
            msgDetailData = JsonTool.toJava(dataContent, MsgData.MsgDetailData.class);
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public String fingerPrintOfProtocal;
    public String userid;
    public String dataContent;
    public int typeu;

    public MsgDetailData msgDetailData;

    /***
     * 如果是当天以前的消息则显示年/月/日 时:分:秒
     如果是当天的消息则只显示时:分:秒
     * @param msgDataList
     */
    public static void initShowTimeStamp(final List<MsgData> msgDataList){
        long preTimeStamp=0;
        for(MsgData msgData:msgDataList){
            msgData.msgDetailData.showTimeStamp="";
            if(Math.abs(msgData.msgDetailData.timestamp-preTimeStamp)>60*1000){//大于一分钟就显示
                if(msgData.msgDetailData.isTouZhuMsg()||msgData.msgDetailData.isChatMsg()||msgData.msgDetailData.isServiceError()){//这些才显示
                    msgData.msgDetailData.showTimeStamp=TimeTool.getTimeStrLongAndShort(msgData.msgDetailData.timestamp);
                    preTimeStamp=msgData.msgDetailData.timestamp;
                }
            }
        }
    }
    public static class MsgDetailData {

        public String msgType;//消息类型
        public long timestamp;//消息时间

        public String showTimeStamp;//是否显示消息时间， 非服务端数据


        public String msg;//错误消息
        public String uuid;//用户id
        public Object headImage;//用户头像图片
        public int grade;//用户会员级别
        public String nickname;//用户昵称

        public String msgContent;//聊天的 消息内容


        public int code;


        public String lottery;//彩种
        public String issue;//消息期数

        public int state=-1;//状态，1－可以开始下注，3－停止下注，封盘
        public int countDown;//倒计时秒数
        public int isShow=1;//是否显示 ，






        public String betting;//投注内容
        public String amount;//投注数量

        public  Attach attach;
        public  Content content;

        /**
         * 是否是聊天消息
         * @return
         */
        public boolean isChatMsg() {
            return "CHAT".equals(msgType);
        }

        /**
         * 客服系统消息
         * @return
         */
        public boolean isServiceError() {
            return "SERVER".equals(msgType)&&code==4;
        }

        /**
         * 是否是投注消息
         * @return
         */
        public boolean isTouZhuMsg() {
            return "BETTING".equals(msgType);
        }

        /***
         * 是否状态消息
         * @return
         */
        public boolean isZhuangTaiMsg() {
            return "STATE".equals(msgType);
        }
        public boolean isXinJinYongHu() {
            return "SYSTEM".equals(msgType);
        }
        private String getLanSeTv(Object text){
            return "<font color='#4491FE'>"+text+"</font>";
        }

        public String getZhuangTaiMsg(View itemView,Data_room_queryGame.InfoBean.RoomLevelsBean roomsBean){
            String msg="";
            if(isZhuangTaiMsg()){
                msg="";
                if(state==0){//开奖结果
                    msg=getLanSeTv("【"+attach.issue+"期】")+"["+ TimeTool.getShortTimeStrWithOutSecond(attach.timestamp)+"]开奖结果：<br>"+ Data_cqssc_top10.CQSSCBean.getShowStr("",attach.num);
                }else if(state==1){// 可以开始下注
                    msg=getLanSeTv("【"+issue+"期】")+"单注"+roomsBean.bettingMin+"起，"+roomsBean.bettingMax+"封顶，总注"+roomsBean.bettingLimit+"封顶";
                    try {
                        if(itemView!=null){
                            UiTool.setTextView(itemView,R.id.im_vg_center2,"★★现在可以开始下注★★");
                            itemView.findViewById(R.id.im_vg_center2).setVisibility(View.VISIBLE);
                        }
                    }catch (Exception e){
                        LogTool.ex(e);
                    }
                    //msg+="<br/>★★现在可以开始下注★★";
                }else if(state==2){// 距封盘剩下60秒时，提醒下注
                    msg=getLanSeTv("【"+issue+"期】")+"距封盘还有60秒,请抓紧时间下注";
                }else if(state==3){// 停止下注，封盘
                    msg=getLanSeTv("【"+issue+"期】")+"已封盘，下注结果以系统开奖为标准，如有异议，请及时联系客服";
                }else if(state==4){//当天开奖停止
                    msg="当前时间不能进行游戏，本游戏销售时间为"+attach.timeRange+"，谢谢您的支持";
                }
            }else if(isTouZhuMsg()){
               // int icon= Data_login_validate.getData_login_validate().uuid.equals(uuid)?R.drawable.icon_bet_stopwatch_white:R.drawable.icon_bet_stopwatch_black;

                int icon=R.drawable.icon_bet_stopwatch_white;


                //下面是为了等长显示
                int max=200;
                Rect rect=UiTool.getTextWidthHeight(betting,max/2);
                int num=(max-rect.right+30)/50;
                String tem="";
                while (num>0){
                    tem+="&ensp;";
                    num--;
                }
                String bettingStr=tem+betting;


                msg="<img src='res:" +icon +"'  > "+issue+"期&emsp;投注类型:&emsp;"+bettingStr+" <br>金额："+ Common.getPriceYB(amount);
            }else if(isXinJinYongHu()){
                msg="欢迎"+" <img src='res:" + Data_personinfo_query.getGradeResImg(content.grade) +"'  > "+getLanSeTv(content.nickname)+" 进入房间";
            }
            return msg;
        }


        public static class Attach {
            public long timestamp;//最近开奖的时间戳
            public String issue;//最近开奖的期号
            public String num;//最近开奖的号码
            public String timeRange;//游戏未开始 要显示的 开始时间
        }
        public static class Content {
            public int grade;
            public String nickname;
            public String timestamp;
        }

    }
}
