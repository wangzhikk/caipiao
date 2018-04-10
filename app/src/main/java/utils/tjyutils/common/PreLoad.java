package utils.tjyutils.common;

import com.cp.R;
import com.cp.shouye.Data_index_query;
import com.cp.shouye.Data_room_queryGame;

import utils.wzutils.common.LayoutInflaterTool;

public class PreLoad {
    public static void preLoad(){
        LayoutInflaterTool.preLoadAll(R.layout.class,3);
        Data_index_query.load(null);//首页数据
        //游戏房间数据
        Data_room_queryGame.load(Data_room_queryGame.YouXiEnum.BJ28,null);
        Data_room_queryGame.load(Data_room_queryGame.YouXiEnum.CQSSC_CX,null);
    }
}
