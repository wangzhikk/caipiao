package com.cp.shouye;

import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.common.CommonTool;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 登录接口
 */

public class Data_index_query extends ParentServerData {

    /**
     * code : 16
     * totalProfit : 3744052
     * totalUser : 10007
     * profitRate : 98
     * banners : [{"id":3,"ads_ab":"index_banner","ads_name":"ads2","ads_images":"images/dd-e-small-img.png","ads_links":"www.baidu.com","ads_time":1502861989000},{"id":1,"ads_ab":"index_banner","ads_name":"ads1","ads_images":"images/dd-e-big-img.jpg","ads_links":"www.baidu.com","ads_time":1502861987000}]
     */

    public int totalProfit;
    public int totalUser;
    public int profitRate;
    public List<BannersBean> banners;
    /**
     * games : {"BJ28":{"playHtmlSrc":"bj28wf"},"CQSSC_CX":{"playHtmlSrc":"cqssccxwf"}}
     */

    public GamesBean games=new GamesBean();

    public static void load(final HttpUiCallBack<Data_index_query> httpUiCallBack){
        HttpToolAx.urlBase("index/query")

                .send(Data_index_query.class, httpUiCallBack);
    }


    public static class BannersBean {
        /**
         * id : 3
         * ads_ab : index_banner
         * ads_name : ads2
         * ads_images : images/dd-e-small-img.png
         * ads_links : www.baidu.com
         * ads_time : 1502861989000
         */

        public int id;
        public String ads_ab;
        public String ads_name;
        public String ads_images;
        public String ads_links;
        public long ads_time;
        public int ads_type ;//banner类型：0－只有广告图片，1－超链接，2－抽奖，3－…
        public void onClick() {

            CommonTool.showToast(ads_images);
        }
    }


    public static class GamesBean {
        public BJ28Bean BJ28=new BJ28Bean();
        public CQSSCCXBean CQSSC_CX=new CQSSCCXBean();

        public static class BJ28Bean {
            public String playHtmlSrc="bj28wf";
        }
        public static class CQSSCCXBean {
            public String playHtmlSrc="cqssccxwf";
        }
    }
}
