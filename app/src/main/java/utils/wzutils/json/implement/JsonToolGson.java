package utils.wzutils.json.implement;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import utils.wzutils.common.LogTool;
import utils.wzutils.json.InterfaceJsonTool;

/**
 * Created by coder on 15/12/25.
 */
public class JsonToolGson implements InterfaceJsonTool {
    Gson gson = new Gson();

    @Override
    public String toJsonStr(Object javaObject) {
        if (javaObject instanceof String) {
            return "" + javaObject;
        } else {
            String str = gson.toJson(javaObject);
            return str;
        }
    }

    @Override
    public <T> T toJava(String jsonStr, Class<T> tClass) {
        T data = gson.fromJson(jsonStr, tClass);
        return data;
    }

    @Override
    public <T> List<T> toJavaList(String jsonstr, Class<T> clazz) {
        ArrayList<T> list = new ArrayList<>();
        try {
            JsonArray array = new JsonParser().parse(jsonstr).getAsJsonArray();
            for (final JsonElement elem : array) {
                list.add(gson.fromJson(elem, clazz));
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return list;
    }


}
