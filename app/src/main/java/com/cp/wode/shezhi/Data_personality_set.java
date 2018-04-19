package com.cp.wode.shezhi;

import android.support.v4.app.FragmentActivity;

import com.cp.dengluzhuce.DengLuFragment;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.BroadcastReceiverTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.db.MapDB;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 * 登录接口
 */

public class Data_personality_set extends ParentServerData {

    public static void load(String nickname, String autograph, final HttpUiCallBack<Data_personality_set> httpUiCallBack){
        HttpToolAx.urlBase("personality/set")
                .addQueryParams("nickname",nickname)
                .addQueryParams("autograph",autograph)
                .send(Data_personality_set.class, httpUiCallBack);
    }


}
