package utils.tjyutils.ui.dizhi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utils.wzutils.AppTool;
import utils.wzutils.JsonTool;
import utils.wzutils.common.FileTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.thread.ThreadTool;

/**
 * Created by ishare on 2016/6/23.
 */

public class DiZhi implements Comparable, Serializable {
    public static void main(String []args){
        System.out.println("asdafdssdf");

        String path="D:\\国家.txt";





    }
    private static DiZhi diZhi = new DiZhi();
    public List<DiZhi> list = new ArrayList<>();
    public int id;
    public int parentId;
    public String name = "";
    public int sort;
    public DiZhi parent=this;

    /***
     * 从本地读取
     *
     * @return
     */
    public static synchronized DiZhi loadFromLocal() {
        try {
            long time = System.currentTimeMillis();
            if (diZhi.list != null && diZhi.list.size() > 0) {
            } else {
                try {
                    String dizhiStr = FileTool.readAllString(AppTool.getApplication().getAssets().open("dizhi_quanqiu.txt"));
                    diZhi = JsonTool.toJava(dizhiStr, DiZhi.class);
                    setParent(diZhi);
//                    for (int shengIndx = 0; shengIndx < diZhi.list.size(); shengIndx++) {
//                        DiZhi sheng = diZhi.list.get(shengIndx);
//                        sheng.parent = diZhi;
//
//                        for (int shiIndx = 0; shiIndx < sheng.list.size(); shiIndx++) {
//                            DiZhi shi = sheng.list.get(shiIndx);
//                            shi.parent = sheng;
//                            for (int xianIndx = 0; xianIndx < shi.list.size(); xianIndx++) {
//                                DiZhi xian = shi.list.get(xianIndx);
//                                xian.parent = shi;
//
//                                for (int lastIndx = 0; lastIndx < xian.list.size(); lastIndx++) {
//                                    DiZhi last = xian.list.get(lastIndx);
//                                    last.parent = xian;
//                                }
//                            }
//                        }
//                    }

                } catch (Exception e) {
                    LogTool.ex(e);
                }

            }
            LogTool.s("加载完地址： 耗时" + (System.currentTimeMillis() - time) + "  加载数据：" + diZhi.list.size());
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return diZhi;
    }

    public static void setParent(DiZhi diZhi){
        if(diZhi.list.size()>0){
            for(DiZhi tem:diZhi.list){
                tem.parent=diZhi;
                tem.parentId=diZhi.id;
                setParent(tem);
            }
        }
    }


    public static void preLoad() {
        ThreadTool.execute(new Runnable() {
            @Override
            public void run() {
                DiZhi.loadFromLocal();//预加载  地址数据
            }
        });
    }

    public int getDep(){

        if(id==-1){
            return 1;
        }if(parent.id==-1){
            return 2;
        }if(parent.parent.id==-1){
            return 3;
        }if(parent.parent.parent.id==-1){
            return 4;
        }if(parent.parent.parent.parent.id==-1){
            return 5;
        }
        return 0;
    }
    @Override
    public String toString() {
        return name + "--" + id + "---" + parentId;
    }

    @Override
    public int compareTo(Object o) {
        try {
            if (o != null) {
                DiZhi des = (DiZhi) o;
                return (sort - des.sort);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


}
