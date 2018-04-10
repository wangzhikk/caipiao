package com.cp.shouye;

import com.cp.R;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import utils.tjyutils.http.HttpToolAx;
import utils.tjyutils.parent.ParentServerData;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by wz on 2018/2/24.
 * 游戏房间
 */

public class Data_room_queryGame extends ParentServerData {
    static HashMap<String ,Data_room_queryGame> gameHashMap=new HashMap<>();
    /***
     * 必须要加载后才能使用
     * @return
     */
    public static Data_room_queryGame getLocalByGame(String youxiValue){
        return gameHashMap.get(youxiValue);
    }





    /**
     * game : CQSSC_CX
     * code : 16
     * info : {"cnName":"重庆时时彩-创新版","name":"CQSSC_CX","playMethods":[{"game":"CQSSC_CX","name":"猜大小","bgColor":"#00F","methods":[{"type":"小","typeColor":"#FF0","desc":"中奖和值：[0,1,2,3,4,5,6,7,8]","oddsRate":"1:2.00"},{"type":"中","typeColor":"#F0F","desc":"中奖和值：[7,8,9,10,11]","oddsRate":"1:2.00"},{"type":"大","typeColor":"#0F0","desc":"中奖和值：[10,11,12,13,14,15,16,17,18]","oddsRate":"1:2.00"}]},{"game":"CQSSC_CX","name":"猜单双","bgColor":"#F00","methods":[{"type":"单","typeColor":"#00F","desc":"中奖和值：[1,3,5,7,9,11,13,15,17]","oddsRate":"1:1.80"},{"type":"双","typeColor":"#FF0","desc":"中奖和值：[2,4,6,8,10,12,14,16,18]","oddsRate":"1:1.80"}]},{"game":"CQSSC_CX","name":"猜和值","bgColor":"#0F0","methods":[{"type":"0","typeColor":"#FFF","desc":"中奖和值：[0]","oddsRate":"1:90.0"},{"type":"1","typeColor":"#FFF","desc":"中奖和值：[1]","oddsRate":"1:45.0"},{"type":"2","typeColor":"#FFF","desc":"中奖和值：[2]","oddsRate":"1:30.0"},{"type":"3","typeColor":"#FFF","desc":"中奖和值：[3]","oddsRate":"1:22.5"},{"type":"4","typeColor":"#FFF","desc":"中奖和值：[4]","oddsRate":"1:18.0"},{"type":"5","typeColor":"#FFF","desc":"中奖和值：[5]","oddsRate":"1:15.0"},{"type":"6","typeColor":"#FFF","desc":"中奖和值：[6]","oddsRate":"1:12.5"},{"type":"7","typeColor":"#FFF","desc":"中奖和值：[7]","oddsRate":"1:11.0"},{"type":"8","typeColor":"#FFF","desc":"中奖和值：[8]","oddsRate":"1:10.0"},{"type":"9","typeColor":"#FFF","desc":"中奖和值：[9]","oddsRate":"1:9.0"},{"type":"10","typeColor":"#FFF","desc":"中奖和值：[10]","oddsRate":"1:10.0"},{"type":"11","typeColor":"#FFF","desc":"中奖和值：[11]","oddsRate":"1:11.0"},{"type":"12","typeColor":"#FFF","desc":"中奖和值：[12]","oddsRate":"1:12.5"},{"type":"13","typeColor":"#FFF","desc":"中奖和值：[13]","oddsRate":"1:15.0"},{"type":"14","typeColor":"#FFF","desc":"中奖和值：[14]","oddsRate":"1:18.0"},{"type":"15","typeColor":"#FFF","desc":"中奖和值：[15]","oddsRate":"1:22.5"},{"type":"16","typeColor":"#FFF","desc":"中奖和值：[16]","oddsRate":"1:30.0"},{"type":"17","typeColor":"#FFF","desc":"中奖和值：[17]","oddsRate":"1:45.0"},{"type":"18","typeColor":"#FFF","desc":"中奖和值：[18]","oddsRate":"1:90.0"}]}],"roomLevels":[{"game":"CQSSC_CX","level":1,"name":"初级房","explain":"回水1~3%","condition":"0","odds":"\n\t\t\t\t\t【回水说明】：暂未开放 \n\t\t\t\t\t(1)和值大小竞猜：0-8为小，10-18为大，赔率1赔2，和值9，大小通吃；\n\t\t\t\t\t(2)和值中竞猜：7-11为中，赔率1赔2，和值9不通吃和值中；\n\t\t\t\t\t(3)和值单双竞猜：和值1、3、5、7、9、11、13、15、17为单，和值0、2、4、6、8、10、12、14、16、18为双赔率1赔1.8\n\t\t\t\t\t\n\t\t\t\t","bettingMin":10,"bettingMax":20000,"roomList":[{"no":"CQSSC_CX-1-001","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_01.png","name":"VIP 房间 1","total":0},{"no":"CQSSC_CX-1-002","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_02.png","name":"VIP 房间 2","total":0},{"no":"CQSSC_CX-1-003","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_03.png","name":"VIP 房间 3","total":0},{"no":"CQSSC_CX-1-004","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_04.png","name":"VIP 房间 4","total":0}],"total":0},{"game":"CQSSC_CX","level":2,"name":"中级房","explain":"回水3~5%","condition":"0","odds":"\n\t\t\t\t\t【回水说明】：暂未开放 \n\t\t\t\t\t(1)和值大小竞猜：0-8为小，10-18为大，赔率1赔2，和值9，大小通吃；\n\t\t\t\t\t(2)和值中竞猜：7-11为中，赔率1赔2，和值9不通吃和值中；\n\t\t\t\t\t(3)和值单双竞猜：和值1、3、5、7、9、11、13、15、17为单，和值0、2、4、6、8、10、12、14、16、18为双赔率1赔1.8\n\t\t\t\t\t\n\t\t\t\t","bettingMin":50,"bettingMax":30000,"roomList":[{"no":"CQSSC_CX-2-001","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_01.png","name":"VIP 房间 1","total":0},{"no":"CQSSC_CX-2-002","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_02.png","name":"VIP 房间 2","total":0},{"no":"CQSSC_CX-2-003","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_03.png","name":"VIP 房间 3","total":0},{"no":"CQSSC_CX-2-004","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_04.png","name":"VIP 房间 4","total":0}],"total":0},{"game":"CQSSC_CX","level":3,"name":"高级房","explain":"回水5~10%","condition":"100000","odds":"\n\t\t\t\t\t【回水说明】：暂未开放 \n\t\t\t\t\t(1)和值大小竞猜：0-8为小，10-18为大，赔率1赔2，和值9，大小通吃；\n\t\t\t\t\t(2)和值中竞猜：7-11为中，赔率1赔2，和值9不通吃和值中；\n\t\t\t\t\t(3)和值单双竞猜：和值1、3、5、7、9、11、13、15、17为单，和值0、2、4、6、8、10、12、14、16、18为双赔率1赔1.8\n\t\t\t\t\t\n\t\t\t\t","bettingMin":500,"bettingMax":200000,"roomList":[{"no":"CQSSC_CX-3-001","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_01.png","name":"VIP 房间 1","total":0},{"no":"CQSSC_CX-3-002","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_02.png","name":"VIP 房间 2","total":0},{"no":"CQSSC_CX-3-003","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_03.png","name":"VIP 房间 3","total":0},{"no":"CQSSC_CX-3-004","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_04.png","name":"VIP 房间 4","total":0}],"total":0}]}
     */

    public String game;
    public InfoBean info;

    public static enum  YouXiEnum implements Serializable{
        BJ28("北京28","BJ28","bj28"),CQSSC_CX("重庆时时彩创新版","CQSSC_CX","cqssc"),CQSSC_JD("重庆时时彩－经典版","CQSSC_JD","cqssc");
        public String name;
        public String value;
        public String method;
        private YouXiEnum(String name,String value,String method){
            this.name=name;
            this.value=value;
            this.method=method;
        }
        public int getImgBgChu(){
            int imgId=R.drawable.img_class_10yuan;
            if(this==BJ28){
                imgId=R.drawable.img_class_10yuan;
            }else if(this==CQSSC_CX){
                imgId= R.drawable.img_class_chu;
            }
            return imgId;
        }
        public int getImgBgZhong(){
            int imgId=R.drawable.img_class_50yuan;
            if(this==BJ28){
                imgId=R.drawable.img_class_50yuan;
            }else if(this==CQSSC_CX){
                imgId= R.drawable.img_class_zhong;
            }
            return imgId;
        }
        public int getImgBgGao(){
            int imgId=R.drawable.img_class_100yuan;
            if(this==BJ28){
                imgId=R.drawable.img_class_100yuan;
            }else if(this==CQSSC_CX){
                imgId= R.drawable.img_class_gao;
            }
            return imgId;
        }


    }

    public static void load(final YouXiEnum youXiEnum, final HttpUiCallBack<Data_room_queryGame> httpUiCallBack){
        HttpToolAx.urlBase("room/queryGame")
                .addQueryParams("game",youXiEnum.value)
                .send(Data_room_queryGame.class, new HttpUiCallBack<Data_room_queryGame>() {
                    @Override
                    public void onSuccess(Data_room_queryGame data) {
                        if(data.isDataOkWithOutCheckLogin()){
                            gameHashMap.put(youXiEnum.value,data);
                        }
                        if(httpUiCallBack!=null)httpUiCallBack.onSuccess(data);

                    }
                });
    }


    public static class InfoBean implements Serializable{
        /**
         * cnName : 重庆时时彩-创新版
         * name : CQSSC_CX
         * playMethods : [{"game":"CQSSC_CX","name":"猜大小","bgColor":"#00F","methods":[{"type":"小","typeColor":"#FF0","desc":"中奖和值：[0,1,2,3,4,5,6,7,8]","oddsRate":"1:2.00"},{"type":"中","typeColor":"#F0F","desc":"中奖和值：[7,8,9,10,11]","oddsRate":"1:2.00"},{"type":"大","typeColor":"#0F0","desc":"中奖和值：[10,11,12,13,14,15,16,17,18]","oddsRate":"1:2.00"}]},{"game":"CQSSC_CX","name":"猜单双","bgColor":"#F00","methods":[{"type":"单","typeColor":"#00F","desc":"中奖和值：[1,3,5,7,9,11,13,15,17]","oddsRate":"1:1.80"},{"type":"双","typeColor":"#FF0","desc":"中奖和值：[2,4,6,8,10,12,14,16,18]","oddsRate":"1:1.80"}]},{"game":"CQSSC_CX","name":"猜和值","bgColor":"#0F0","methods":[{"type":"0","typeColor":"#FFF","desc":"中奖和值：[0]","oddsRate":"1:90.0"},{"type":"1","typeColor":"#FFF","desc":"中奖和值：[1]","oddsRate":"1:45.0"},{"type":"2","typeColor":"#FFF","desc":"中奖和值：[2]","oddsRate":"1:30.0"},{"type":"3","typeColor":"#FFF","desc":"中奖和值：[3]","oddsRate":"1:22.5"},{"type":"4","typeColor":"#FFF","desc":"中奖和值：[4]","oddsRate":"1:18.0"},{"type":"5","typeColor":"#FFF","desc":"中奖和值：[5]","oddsRate":"1:15.0"},{"type":"6","typeColor":"#FFF","desc":"中奖和值：[6]","oddsRate":"1:12.5"},{"type":"7","typeColor":"#FFF","desc":"中奖和值：[7]","oddsRate":"1:11.0"},{"type":"8","typeColor":"#FFF","desc":"中奖和值：[8]","oddsRate":"1:10.0"},{"type":"9","typeColor":"#FFF","desc":"中奖和值：[9]","oddsRate":"1:9.0"},{"type":"10","typeColor":"#FFF","desc":"中奖和值：[10]","oddsRate":"1:10.0"},{"type":"11","typeColor":"#FFF","desc":"中奖和值：[11]","oddsRate":"1:11.0"},{"type":"12","typeColor":"#FFF","desc":"中奖和值：[12]","oddsRate":"1:12.5"},{"type":"13","typeColor":"#FFF","desc":"中奖和值：[13]","oddsRate":"1:15.0"},{"type":"14","typeColor":"#FFF","desc":"中奖和值：[14]","oddsRate":"1:18.0"},{"type":"15","typeColor":"#FFF","desc":"中奖和值：[15]","oddsRate":"1:22.5"},{"type":"16","typeColor":"#FFF","desc":"中奖和值：[16]","oddsRate":"1:30.0"},{"type":"17","typeColor":"#FFF","desc":"中奖和值：[17]","oddsRate":"1:45.0"},{"type":"18","typeColor":"#FFF","desc":"中奖和值：[18]","oddsRate":"1:90.0"}]}]
         * roomLevels : [{"game":"CQSSC_CX","level":1,"name":"初级房","explain":"回水1~3%","condition":"0","odds":"\n\t\t\t\t\t【回水说明】：暂未开放 \n\t\t\t\t\t(1)和值大小竞猜：0-8为小，10-18为大，赔率1赔2，和值9，大小通吃；\n\t\t\t\t\t(2)和值中竞猜：7-11为中，赔率1赔2，和值9不通吃和值中；\n\t\t\t\t\t(3)和值单双竞猜：和值1、3、5、7、9、11、13、15、17为单，和值0、2、4、6、8、10、12、14、16、18为双赔率1赔1.8\n\t\t\t\t\t\n\t\t\t\t","bettingMin":10,"bettingMax":20000,"roomList":[{"no":"CQSSC_CX-1-001","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_01.png","name":"VIP 房间 1","total":0},{"no":"CQSSC_CX-1-002","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_02.png","name":"VIP 房间 2","total":0},{"no":"CQSSC_CX-1-003","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_03.png","name":"VIP 房间 3","total":0},{"no":"CQSSC_CX-1-004","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_04.png","name":"VIP 房间 4","total":0}],"total":0},{"game":"CQSSC_CX","level":2,"name":"中级房","explain":"回水3~5%","condition":"0","odds":"\n\t\t\t\t\t【回水说明】：暂未开放 \n\t\t\t\t\t(1)和值大小竞猜：0-8为小，10-18为大，赔率1赔2，和值9，大小通吃；\n\t\t\t\t\t(2)和值中竞猜：7-11为中，赔率1赔2，和值9不通吃和值中；\n\t\t\t\t\t(3)和值单双竞猜：和值1、3、5、7、9、11、13、15、17为单，和值0、2、4、6、8、10、12、14、16、18为双赔率1赔1.8\n\t\t\t\t\t\n\t\t\t\t","bettingMin":50,"bettingMax":30000,"roomList":[{"no":"CQSSC_CX-2-001","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_01.png","name":"VIP 房间 1","total":0},{"no":"CQSSC_CX-2-002","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_02.png","name":"VIP 房间 2","total":0},{"no":"CQSSC_CX-2-003","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_03.png","name":"VIP 房间 3","total":0},{"no":"CQSSC_CX-2-004","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_04.png","name":"VIP 房间 4","total":0}],"total":0},{"game":"CQSSC_CX","level":3,"name":"高级房","explain":"回水5~10%","condition":"100000","odds":"\n\t\t\t\t\t【回水说明】：暂未开放 \n\t\t\t\t\t(1)和值大小竞猜：0-8为小，10-18为大，赔率1赔2，和值9，大小通吃；\n\t\t\t\t\t(2)和值中竞猜：7-11为中，赔率1赔2，和值9不通吃和值中；\n\t\t\t\t\t(3)和值单双竞猜：和值1、3、5、7、9、11、13、15、17为单，和值0、2、4、6、8、10、12、14、16、18为双赔率1赔1.8\n\t\t\t\t\t\n\t\t\t\t","bettingMin":500,"bettingMax":200000,"roomList":[{"no":"CQSSC_CX-3-001","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_01.png","name":"VIP 房间 1","total":0},{"no":"CQSSC_CX-3-002","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_02.png","name":"VIP 房间 2","total":0},{"no":"CQSSC_CX-3-003","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_03.png","name":"VIP 房间 3","total":0},{"no":"CQSSC_CX-3-004","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_04.png","name":"VIP 房间 4","total":0}],"total":0}]
         */

        public String cnName;
        public String name;
        public String playHtmlSrc;//玩法说明html
        public List<PlayMethodsBean> playMethods;
        public List<RoomLevelsBean> roomLevels;

        public static class PlayMethodsBean implements Serializable{
            /**
             * game : CQSSC_CX
             * name : 猜大小
             * bgColor : #00F
             * methods : [{"type":"小","typeColor":"#FF0","desc":"中奖和值：[0,1,2,3,4,5,6,7,8]","oddsRate":"1:2.00"},{"type":"中","typeColor":"#F0F","desc":"中奖和值：[7,8,9,10,11]","oddsRate":"1:2.00"},{"type":"大","typeColor":"#0F0","desc":"中奖和值：[10,11,12,13,14,15,16,17,18]","oddsRate":"1:2.00"}]
             */

            public String game;
            public String name;
            public String bgColor;
            public List<MethodsBean> methods;

            public static class MethodsBean implements Serializable{
                /**
                 * type : 小
                 * typeColor : #FF0
                 * desc : 中奖和值：[0,1,2,3,4,5,6,7,8]
                 * oddsRate : 1:2.00
                 */

                public String type;
                public String typeColor;
                public String desc;
                public String oddsRate;
            }
        }

        public static class RoomLevelsBean implements Serializable{
            /**
             * game : CQSSC_CX
             * level : 1
             * name : 初级房
             * explain : 回水1~3%
             * condition : 0
             * odds :
             【回水说明】：暂未开放
             (1)和值大小竞猜：0-8为小，10-18为大，赔率1赔2，和值9，大小通吃；
             (2)和值中竞猜：7-11为中，赔率1赔2，和值9不通吃和值中；
             (3)和值单双竞猜：和值1、3、5、7、9、11、13、15、17为单，和值0、2、4、6、8、10、12、14、16、18为双赔率1赔1.8


             * bettingMin : 10
             * bettingMax : 20000
             * roomList : [{"no":"CQSSC_CX-1-001","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_01.png","name":"VIP 房间 1","total":0},{"no":"CQSSC_CX-1-002","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_02.png","name":"VIP 房间 2","total":0},{"no":"CQSSC_CX-1-003","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_03.png","name":"VIP 房间 3","total":0},{"no":"CQSSC_CX-1-004","image":"http://192.168.88.54:8080/caipiao_api/images/room/img_room_04.png","name":"VIP 房间 4","total":0}]
             * total : 0
             */

            public String game;
            public int level;
            public String name;
            public String explain;
            public long condition;
            public String odds;
            public int bettingMin;
            public int bettingMax;
            public  int bettingLimit;//同一期所有投注最高金额
            public String fastBettingAmount;
            public int total;
            public List<RoomListBean> roomList;
            public int bg;

            public void goPeiLv() {
                ShuoMingFragment.byData(new ShuoMingFragment.ShuoMingData("说明",odds)).go();
            }
            public Data_room_queryGame getGameData(){
                Data_room_queryGame gameData=getLocalByGame(game);


                return gameData;
            }

            public static class RoomListBean implements Serializable{
                /**
                 * no : CQSSC_CX-1-001
                 * image : http://192.168.88.54:8080/caipiao_api/images/room/img_room_01.png
                 * name : VIP 房间 1
                 * total : 0
                 */

                public String no;
                public String image;
                public String name;
                public int total;
            }
        }
    }
}
