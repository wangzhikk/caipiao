package com.cp.im;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.cp.cp.Data_login_validate;

import net.openmob.mobileimsdk.android.ClientCoreSDK;
import net.openmob.mobileimsdk.android.conf.ConfigEntity;
import net.openmob.mobileimsdk.android.core.KeepAliveDaemon;
import net.openmob.mobileimsdk.android.core.LocalUDPDataSender;
import net.openmob.mobileimsdk.android.event.ChatBaseEvent;
import net.openmob.mobileimsdk.android.event.ChatTransDataEvent;
import net.openmob.mobileimsdk.android.event.MessageQoSEvent;
import net.openmob.mobileimsdk.server.protocal.ErrorCode;
import net.openmob.mobileimsdk.server.protocal.Protocal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import utils.tjyutils.http.HttpConfigAx;
import utils.tjyutils.http.HttpToolAx;
import utils.wzutils.AppTool;
import utils.wzutils.common.BroadcastReceiverTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.ui.textview.HtmlTool;

/**
 * Created by wz on 2018/2/6.
 */

public class IMTool {
    private IMTool(){

        timer.schedule(timerTask,0,6*1000);

    }
    Timer timer=new Timer();
    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            try {
                if(needLogin){
                    IMTool.getIntance().checkKeepAliveAndLogin();
                }
            }catch (Exception e){
                LogTool.ex(e);
            }
        }
    };
    boolean needLogin=false;
    Context context;


    List<MsgHandlerListener> msgHandlerListeners=new ArrayList<>();

    public synchronized void checkKeepAliveAndLogin() {
        try {
            KeepAliveDaemon keepAliveDaemon= KeepAliveDaemon.getInstance(AppTool.getApplication());
            Field field=KeepAliveDaemon.class.getDeclaredField("lastGetKeepAliveResponseFromServerTimstamp");
            field.setAccessible(true);
            long time= (long) field.get(keepAliveDaemon);

            if(System.currentTimeMillis()-time>5*1000){//超过5秒没心跳
                LogTool.s("心跳没了，重新登录");
                loginImp();
                if(!ClientCoreSDK.getInstance().isConnectedToServer()){
                    LogTool.s("没连接上服务器， 重新登录");
                }
            }
        }catch (Exception e){
            LogTool.ex(e);
        }

    }

    public static abstract class MsgHandlerListener{
        public abstract boolean handError(int errorCode, String errorMsg);
        public abstract boolean handMsgRecive(MsgData msgData);
        public boolean messagesLost(ArrayList<Protocal> lostMessages) {
            return false;
        }
        public boolean messagesBeReceived(String theFingerPrint) {
            return false;
        }
    }
    /**
     * 添加一个消息监听
     * @param msgHandlerListener
     */
    public synchronized void addMsgListener(MsgHandlerListener msgHandlerListener){
        if(msgHandlerListener!=null&&!msgHandlerListeners.contains(msgHandlerListener))
        {
            msgHandlerListeners.add(msgHandlerListener);
            log("添加了收到消息监听");
        }
    }
    public synchronized void removeMsgListener(MsgHandlerListener msgHandlerListener){
        if(msgHandlerListener!=null){
            msgHandlerListeners.remove(msgHandlerListener);
            log("删除了收到消息监听");
        }
    }

    public static final String action_login_im_success="action_login_im_success";

    public synchronized void init(Context contextIn){
       // release();

        context=contextIn;
        ConfigEntity.appKey = "5418023dfd98c579b6001741";
        // 设置服务器ip和服务器端口
        ConfigEntity.serverIP = HttpConfigAx.imIp;
        ConfigEntity.serverUDPPort = 7901;
       // ConfigEntity.serverUDPPort = 80;
        // MobileIMSDK核心IM框架的敏感度模式设置
        ConfigEntity.setSenseMode(ConfigEntity.SenseMode.MODE_3S);
        // 开启/关闭DEBUG信息输出
        ClientCoreSDK.DEBUG = true;


        ClientCoreSDK.getInstance().init(context);
        KeFuMsgTool.init();
        ClientCoreSDK.getInstance().setChatBaseEvent(new ChatBaseEvent() {
            @Override
            public void onLoginMessage(int i) {
                if(i==0){
                    BroadcastReceiverTool.sendAction(action_login_im_success);
                    log("登陆成功"+ClientCoreSDK.getInstance().getInstance().getCurrentLoginUserId());
                    boolean connectedToServer = ClientCoreSDK.getInstance().isConnectedToServer();
                }else {
                   // CommonTool.showToast("登录消息服务器失败");
                    log("登录消息服务器失败");
                }
            }

            @Override
            public void onLinkCloseMessage(int i) {
                log("断开连接");
            }
        });
        ClientCoreSDK.getInstance().setChatTransDataEvent(new ChatTransDataEvent() {
            @Override
            public void onTransBuffer(String fingerPrintOfProtocal, String userid, String dataContent, int typeu) {
                KeepAliveDaemon.getInstance(AppTool.getApplication()).updateGetKeepAliveResponseFromServerTimstamp();
                log( "【DEBUG_UI】[typeu="+typeu+"]收到来自用户"+userid+"的消息:"+dataContent);
                MsgData msgData=new MsgData(fingerPrintOfProtocal,userid,dataContent,typeu);
                for(MsgHandlerListener msgHandlerListener:msgHandlerListeners){
                    try {
                        if(msgHandlerListener.handMsgRecive(msgData)){
                            return;
                        }
                    }catch (Exception e){
                        log(e);
                    }
                }
            }

            @Override
            public void onErrorResponse(int errorCode, String errorMsg) {
                log( "【DEBUG_UI】收到服务端错误消息，errorCode="+errorCode+", errorMsg="+errorMsg);
                if(errorCode ==  ErrorCode.ForS.RESPONSE_FOR_UNLOGIN)
                    log("服务端会话已失效，自动登陆/重连将启动! ("+errorCode+")");
                else
                    log("Server反馈错误码："+errorCode+",errorMsg="+errorMsg);

                for(MsgHandlerListener msgHandlerListener:msgHandlerListeners){
                    try {
                        if( msgHandlerListener.handError(errorCode,errorMsg)){
                            return;
                        }
                    }catch (Exception e){
                        log(e);
                    }
                }
            }
        });
        ClientCoreSDK.getInstance().setMessageQoSEvent(new MessageQoSEvent() {
            @Override
            public void messagesLost(ArrayList<Protocal> lostMessages) {
                log( "【DEBUG_UI】收到系统的未实时送达事件通知，当前共有"+lostMessages.size()+"个包QoS保证机制结束，判定为【无法实时送达】！");

                for(MsgHandlerListener msgHandlerListener:msgHandlerListeners){
                    try {
                        if( msgHandlerListener.messagesLost(lostMessages)){
                            return;
                        }
                    }catch (Exception e){
                        log(e);
                    }
                }
            }

            @Override
            public void messagesBeReceived(String theFingerPrint) {
                if(theFingerPrint != null)
                {
                    log("【DEBUG_UI】收到对方已收到消息事件的通知，fp="+theFingerPrint);
                    for(MsgHandlerListener msgHandlerListener:msgHandlerListeners){
                        try {
                            if( msgHandlerListener.messagesBeReceived(theFingerPrint)){
                                return;
                            }
                        }catch (Exception e){
                            log(e);
                        }
                    }
                }
            }
        });
    }
    public synchronized void sendMsg( String content, final String friendId, final SendMsgListener sendMsgListener){
        content= HtmlTool.delHTMLTag(content);
        // 发送消息（Android系统要求必须要在独立的线程中发送哦）
        LogTool.s("发送消息：----   "+friendId+"  ：  "+content);
        final String finalContent = content;
        AppTool.uiHandler.post(new Runnable() {
            @Override
            public void run() {
                new LocalUDPDataSender.SendCommonDataAsync(context, finalContent, friendId)
                {
                    @Override
                    protected void onPostExecute(Integer code)
                    {
                        try {
                            if(sendMsgListener!=null){
                                sendMsgListener.sendMsgResult(finalContent,friendId,code);
                            }
                        }catch (Exception e){
                            LogTool.ex(e);
                        }

                        if(code == 0){
                            log("数据已成功发出");
                        }
                        else
                            log("数据发送失败。错误码是："+code+"！");
                    }
                }.execute();
            }
        });

    }
    public synchronized void sendMsg(final String content, final String friendId){
       sendMsg(content,friendId,null);
    }
    public static interface SendMsgListener{
        void sendMsgResult(String content,String friendId,int code);
    }
    public synchronized void  release(){
        try {
            ClientCoreSDK.getInstance().release();
        }catch (Exception e){
            LogTool.ex(e);
        }
    }
    String name;
    String pwd;
    String extra;
    public synchronized void login(final String name, final String pwd, String extra){
        this.name=name;
        this.pwd=pwd;
        this.extra=extra;
        loginImp();
    }
    public synchronized void login( ){
        this.name= Data_login_validate.getData_login_validate().uuid;
        this.pwd=Data_login_validate.getData_login_validate().token;
        this.extra="";
        loginImp();
    }
    public synchronized void loginImp(){
        needLogin=true;
        release();
        init(AppTool.getApplication());
        log("登录消息服务器：name="+name+"    pwd="+pwd+"  extra="+extra);
        new LocalUDPDataSender.SendLoginDataAsync(context,name,pwd,extra){
            @Override
            protected void fireAfterSendLogin(int code) {
                super.fireAfterSendLogin(code);
                try {
                    if(code == 0)
                    {
                        log("登陆/连接信息已成功发出！");
                    }
                    else
                    {
                        log("登陆/连接信息数据发送失败。错误码是："+code+"！");
                        AppTool.uiHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                log("重新登陆中！");
                                login(name,pwd,extra);

                            }
                        },1000);
                    }
                }catch (Exception e){
                    LogTool.ex(e);
                }

            }
        }.execute();
    }
    public synchronized void logout()
    {
        needLogin=false;
        // 发出退出登陆请求包（Android系统要求必须要在独立的线程中发送哦）
        new AsyncTask<Object, Integer, Integer>(){
            @Override
            protected Integer doInBackground(Object... params)
            {
                int code = -1;
                try{
                    code = LocalUDPDataSender.getInstance(context).sendLoginout();
                }
                catch (Exception e){
                    log(e);
                }

                return code;
            }
            @Override
            protected void onPostExecute(Integer code)
            {
                if(code == 0)
                    log( "注销登陆请求已完成！");
                else
                    log("注销登陆请求发送失败。错误码是："+code+"！");
            }
        }.execute();
    }




    static  IMTool imTool=new IMTool();
    public static IMTool getIntance(){
        return imTool;
    }

    public  void log(Object o){
        LogTool.s(o);
    }
}
