package com.cp.wode;

import com.cp.R;
import com.cp.cp.Data_login_validate;
import com.cp.im.IMTool;
import com.cp.wode.qianbao.yinhangka.YinHangKaBindFragment;
import com.cp.wode.shezhi.mima.XiuGaiZiJinMimaFragment;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.http.HttpUiCallBack;

/**
 * abc on 2018/2/24.
 * 分享帮别人注册接口
 */

public class Data_personinfo_query extends ParentServerData {

    static Integer [] gradeResImgs=new Integer[]{
            R.drawable.icon_bet_member_01,
            R.drawable.icon_bet_member_02,
            R.drawable.icon_bet_member_03,
            R.drawable.icon_bet_member_04,
            R.drawable.icon_bet_member_05,
            R.drawable.icon_bet_member_06,
            R.drawable.icon_bet_member_06,
            R.drawable.icon_bet_member_06,
            R.drawable.icon_bet_member_06,
    };
    static String [] gradeStrs=new String[]{
            "普通会员",
            "骑士",
            "子爵",
            "伯爵",
            "公爵",
            "国王",
            "国王",
            "国王"
    };
    public int getGradeResImg(){
        return getGradeResImg(base_grade);
    }
    public static int getGradeResImg(int grade){
        return gradeResImgs[grade];
    }
    public String getGradeText(){
        return getGradeText(base_grade);
    }
    public static String getGradeText(int grade){
        return gradeStrs[grade];
    }

    static Integer [] typeResImgs=new Integer[]{
            0,
            R.drawable.icon_agency_silver,
            R.drawable.icon_agency_golden,
            R.drawable.icon_agency_diamond,
            R.drawable.icon_agency_diamond,
            R.drawable.icon_agency_diamond,
            R.drawable.icon_agency_diamond,
    };
    static String [] typeStrs=new String[]{
            "普通用户",
            "白银代理",
            "黄金代理",
            "钻石代理",
            "钻石代理",
            "钻石代理",
            "钻石代理",
    };

    public int getTypeResImg(){
        return getTypeResImg(base_type);
    }
    public static int getTypeResImg(int base_type){
        return typeResImgs[base_type];
    }
    public String getTypeText(){
        return getTypeText(base_type);
    }
    public static String getTypeText(int base_type){
        return typeStrs[base_type];
    }



    /**
     * code : 16
     * wallet_remain : 100410.0
     * base_nickname : betting
     * base_country : 86
     * base_auth_bank : 0
     * base_autograph : null
     * base_auth_phone : 0
     * base_phone : null
     * base_headImage : null
     * base_auth_thirdpwd : 0
     */

    public double wallet_remain;
    public String base_nickname;
    public int base_country;
    public String base_autograph;
    public String base_phone;
    public String base_headImage;



    public int base_auth_thirdpwd;//是否已经设置资金密码，0－未设置，1－已设置
    public int base_auth_bank;//是否已经设置银行卡

    /**
     * 是否设置银行卡
     * @return
     */
    public boolean hasBank(){
        return base_auth_bank==1;
    }

    /***
     * 是否填写资金密码
     * @return
     */
    public boolean hasMoneyPwd(){
        return base_auth_thirdpwd==1;
    }

    public static boolean checkMoneyPwdAndGo(){
        if(Data_login_validate.getData_login_validate().getUserInfo().hasMoneyPwd()){
            return true;
        }else {
            CommonTool.showToast("请先设置资金密码!");
            new XiuGaiZiJinMimaFragment().go();
            return false;
        }
    }
    public static boolean checkBankAndGo(){
        if(Data_login_validate.getData_login_validate().getUserInfo().hasBank()){
            return true;
        }else {
            CommonTool.showToast("请先绑定银行卡!");
            new YinHangKaBindFragment().go();
            return false;
        }
    }

    public int base_auth_phone;




    public int base_grade;
    public int base_type;









    public static void load(final HttpUiCallBack<Data_personinfo_query> httpUiCallBack){
        HttpToolAx.urlBase("personinfo/query")
                .send(Data_personinfo_query.class, new HttpUiCallBack<Data_personinfo_query>() {
                    @Override
                    public void onSuccess(Data_personinfo_query data) {
                        try {
                            if(data.isDataOk()){
                                Data_login_validate.getData_login_validate().setUserInfo(data);
                                IMTool.getIntance().login();
                            }
                        }catch (Exception e){
                            LogTool.ex(e);
                        }
                        if(httpUiCallBack!=null){
                            httpUiCallBack.onSuccess(data);
                        }
                    }
                });
    }

}
