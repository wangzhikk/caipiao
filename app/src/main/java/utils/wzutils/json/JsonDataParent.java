package utils.wzutils.json;


import java.io.Serializable;

import utils.wzutils.db.MapDB;
import utils.wzutils.http.HttpUiCallBack;

/**
 * Created by coder on 15/12/25.
 * 所有的Json  Java 对象必须是这个的子类
 */
public abstract class JsonDataParent implements Serializable {
    public int code = 9999;
    public String msg = "";
}
