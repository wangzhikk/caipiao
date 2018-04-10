package utils.wzutils.http;

import android.content.Context;

import java.io.Serializable;

/**
 * Created by coder on 15/12/25.
 * 网络请求接口
 */
public interface InterfaceHttpTool {
    /**
     * 初始化
     *
     * @param context
     */
    void init(Context context);

    void init(Context context, String... crts);


    <T extends Serializable> T request(final HttpRequest request, final Class<T> clzz);

    <T extends Serializable> void request(final HttpRequest request, final Class<T> clzz, final HttpUiCallBack callBack);


}
