package com.cp.cp;

import android.support.v4.app.FragmentActivity;

import com.cp.dengluzhuce.DengLuFragment;
import com.cp.im.KeFuMsgTool;
import com.cp.wode.Data_personinfo_query;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.BroadcastReceiverTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.db.MapDB;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 登录接口
 */

public class Data_login_validate extends ParentServerData {
    public String isBindingPhone;
    public String isErrorFive;
    public String token;
    public String uuid;
    public static final String action_login_success="action_login_success";


    public Data_personinfo_query getUserInfo() {
        if(userInfo==null)userInfo=new Data_personinfo_query();
        return userInfo;
    }

    public void setUserInfo(Data_personinfo_query userInfo) {
        this.userInfo = userInfo;
    }

    private Data_personinfo_query userInfo;//个人信息

    public static void load(String name, String pwd, final HttpUiCallBack<Data_login_validate> httpUiCallBack){
        HttpToolAx.urlBase("login/validate")
                .addQueryParams("username",name)
                .addQueryParams("password",pwd)
                .send(Data_login_validate.class, new HttpUiCallBack<Data_login_validate>() {
                    @Override
                    public void onSuccess(Data_login_validate data) {
                        if(data.isDataOkAndToast()){
                            loginSuccess(data);
                            Data_personinfo_query.load(null);
                        }
                        if(httpUiCallBack!=null){
                            httpUiCallBack.onSuccess(data);
                        }
                    }
                });
    }


    private static  Data_login_validate data_login_validate;

    public static Data_login_validate getData_login_validate(){
        if(data_login_validate==null){
            data_login_validate=MapDB.loadObjByDefault("login",Data_login_validate.class,new Data_login_validate());
        }
        return data_login_validate;
    }

    public static boolean isLoginOrGo() {
        if(getData_login_validate()!=null&& StringTool.notEmpty(data_login_validate.token)){//登录成功了
           return true;
        }else {
            new DengLuFragment().go();
        }
        return false;
    }

    /***
     * 绑定 登录成功的操作
     * @param fragmentActivity
     * @param broadCastWork
     */
    public static void bindLoginSuccess(FragmentActivity fragmentActivity, BroadcastReceiverTool.BroadCastWork broadCastWork){
        BroadcastReceiverTool.bindAction(fragmentActivity,broadCastWork,action_login_success);
    }
    public static void loginSuccess(Data_login_validate data){
        if(data!=null&& StringTool.notEmpty(data.token)){//登录成功了
            data_login_validate=data;
            MapDB.saveObj("login",data);
            BroadcastReceiverTool.sendAction(action_login_success);
        }
    }
    //退出登录
    public static void loginOutSuccess() {
        data_login_validate=new Data_login_validate();
        KeFuMsgTool.destory();
        MapDB.saveObj("login","");
    }
}
