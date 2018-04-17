package com.cp.touzhu;

import android.view.View;

import com.cp.shouye.Data_room_queryGame;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.UiTool;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 */

public class Data_cqssc_top10 extends ParentServerData {

    /**
     * code : 16
     * top10 : [{"lottery_issue":170917058,"lottery_num":"03235","lottery_time":1505634000000},{"lottery_issue":170917057,"lottery_num":"77462","lottery_time":1505633400000},{"lottery_issue":170917056,"lottery_num":"80399","lottery_time":1505632800000},{"lottery_issue":170917055,"lottery_num":"92408","lottery_time":1505632200000},{"lottery_issue":170917054,"lottery_num":"59258","lottery_time":1505631600000},{"lottery_issue":170917053,"lottery_num":"49174","lottery_time":1505631000000},{"lottery_issue":170917052,"lottery_num":"51030","lottery_time":1505630400000},{"lottery_issue":170917051,"lottery_num":"53958","lottery_time":1505629800000},{"lottery_issue":170917050,"lottery_num":"41385","lottery_time":1505629200000},{"lottery_issue":170917049,"lottery_num":"06813","lottery_time":1505628600000}]
     */

    public List<CQSSCBean> top10;
    public static void load(Data_room_queryGame.YouXiEnum youXiEnum,HttpUiCallBack<Data_cqssc_top10> httpUiCallBack){
        HttpToolAx.urlBase(youXiEnum.method+"/top10")
                .send(Data_cqssc_top10.class,httpUiCallBack );
    }


    public static class CQSSCBean {
        /**
         * lottery_issue : 170917058
         * lottery_num : 03235
         * lottery_time : 1505634000000
         */

        public int lottery_issue;
        public String lottery_num;
        public long lottery_time;
        public int getResult(){
            return getResult(lottery_num);
        }
        public static  int getResult(String lottery_num){
            if(lottery_num.length()==5){
                char num3=lottery_num.charAt(3);
                char num4=lottery_num.charAt(4);
                int result=Integer.valueOf(""+num3)+Integer.valueOf(""+num4);
                return result;
            }else if(lottery_num.length()==3){
                char num0=lottery_num.charAt(0);
                char num1=lottery_num.charAt(1);
                char num2=lottery_num.charAt(2);
                int result=Integer.valueOf(""+num0)+Integer.valueOf(""+num1)+Integer.valueOf(""+num2);
                return result;
            }
            return 0;
        }
        public static String getShowStr(String lottery_issue,String lottery_num){
            StringBuffer sb=new StringBuffer();

            if(StringTool.notEmpty(lottery_num)){
                if(StringTool.notEmpty(lottery_issue)){
                    sb.append("第");
                    sb.append("<font color='#4491FE'>"+lottery_issue+"</font> ");
                    sb.append("期     ");
                }

                if(lottery_num.length()==5){
                    char num0=lottery_num.charAt(0);
                    char num1=lottery_num.charAt(1);
                    char num2=lottery_num.charAt(2);
                    char num3=lottery_num.charAt(3);
                    char num4=lottery_num.charAt(4);

                    sb.append(num0+" ");
                    sb.append(num1+" ");
                    sb.append(num2+" ");
                    sb.append("<font color='#4491FE'>"+num3+"</font> ");
                    sb.append("<font color='#4491FE'>"+num4+"</font> ");
                    sb.append("(和:");
                    sb.append("<font color='#4491FE'>"+getAllResultStr(lottery_num)+"</font> ");
                    sb.append(")");
                }else if(lottery_num.length()==3){
                    char num0=lottery_num.charAt(0);
                    char num1=lottery_num.charAt(1);
                    char num2=lottery_num.charAt(2);

                    sb.append("<font color='#4491FE'>");
                    sb.append(num0+" +");
                    sb.append(num1+" +");
                    sb.append(num2+" =");
                    sb.append(getResult(lottery_num));
                    sb.append("(");
                    sb.append(getAllResultStr(lottery_num));
                    sb.append(")");
                    sb.append("</font> ");
                }

            }else {
                sb.append("未开奖");
            }
            return sb.toString();
        }

        private static String getAllResultStr(String lottery_num){
            String resultStr="";
            int result=getResult(lottery_num);
            if(lottery_num.length()==5){
                resultStr+=result+",";
                if(result<10){
                    resultStr+="小,";
                }else {
                    resultStr+="大,";
                }
//                if(result>9){
//                    resultStr+="大,";
//                }
//                if(result>6&&result<12){
//                    resultStr+="中,";
//                }


                if(result%2==0){
                    resultStr+="双";
                }else {
                    resultStr+="单";
                }
            }else if(lottery_num.length()==3){
                if(result>13){
                    resultStr+="大,";
                }else {
                    resultStr+="小,";
                }

                if(result%2==0){
                    resultStr+="双";
                }else {
                    resultStr+="单";
                }
            }

            return resultStr;
        }


        /***
         * 投注记录里面用的
         * @param lottery_num
         * @return
         */
        public static String getShortResultStr(String lottery_num){
            StringBuffer sb=new StringBuffer();
            if(StringTool.notEmpty(lottery_num)){
                if(lottery_num.length()==5){
                    char num0=lottery_num.charAt(0);
                    char num1=lottery_num.charAt(1);
                    char num2=lottery_num.charAt(2);
                    char num3=lottery_num.charAt(3);
                    char num4=lottery_num.charAt(4);
                    int result=Integer.valueOf(""+num3)+Integer.valueOf(""+num4);

                    sb.append(num0+" ");
                    sb.append(num1+" ");
                    sb.append(num2+" ");
                    sb.append("<font color='#4491FE'>"+num3+"</font> ");
                    sb.append("<font color='#4491FE'>"+num4+"</font> ");
                    sb.append("(和:");
                    sb.append("<font color='#4491FE'>"+result+"</font> ");
                    sb.append(")");
                }else if(lottery_num.length()==3){
                    char num0=lottery_num.charAt(0);
                    char num1=lottery_num.charAt(1);
                    char num2=lottery_num.charAt(2);

                    sb.append("<font color='#4491FE'>");
                    sb.append(num0+" +");
                    sb.append(num1+" +");
                    sb.append(num2+" =");
                    sb.append(getResult(lottery_num));
                }

            }else {
                return "未开奖";
            }

            return sb.toString();
        }
    }
}
