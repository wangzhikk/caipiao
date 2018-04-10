package utils.wzutils;

import android.content.Context;

import java.io.Serializable;

import utils.wzutils.http.HttpRequest;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.http.InterfaceHttpTool;
import utils.wzutils.http.implement.HttpToolXutils;

/**
 * Created by coder on 15/12/24.
 * 网络请求工具类
 */
public class HttpTool {
    private static InterfaceHttpTool httpTool = new HttpToolXutils();

    /**
     * 初始化
     *
     * @param context
     */
    public static void init(Context context) {
        httpTool.init(context);
    }

    /***
     * @param context
     * @param crts    证书
     */
    public static void init(Context context, String... crts) {
        httpTool.init(context, crts);
    }

    public static <T extends Serializable> T request(HttpRequest request, Class<T> clzz) {
        request.setResponseClass(clzz);
        return httpTool.request(request, clzz);
    }

    public static <T extends Serializable> void request(HttpRequest request, Class<T> clzz, HttpUiCallBack<T> callBack) {
        request.setResponseClass(clzz);
        httpTool.request(request, clzz, callBack);
    }


}
