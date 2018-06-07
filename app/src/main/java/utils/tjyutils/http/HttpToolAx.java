package utils.tjyutils.http;


import com.cp.cp.Data_login_validate;

import java.util.Map;
import java.util.TreeMap;

import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.encypt.Md5Tool;
import utils.wzutils.http.HttpRequest;
import utils.wzutils.http.HttpUiCallBack;
import utils.wzutils.json.JsonDataParent;

/**
 * abc on 2016/5/12.
 */
public class HttpToolAx extends HttpRequest {
    /**
     * 构造一个请求
     *
     * @param url
     * @return
     */
    public static HttpToolAx url(String url) {
        HttpToolAx httpRequestAx = new HttpToolAx();
        httpRequestAx.setUrlStr(url);
        httpRequestAx.addQueryParams("timestamp", System.currentTimeMillis());
        httpRequestAx.addQueryParams("token", Data_login_validate.getData_login_validate().token);
        httpRequestAx.addQueryParams("deviceType", "android");
        httpRequestAx.addQueryParams("device", CommonTool.getDeviceId());
        httpRequestAx.post();
        return httpRequestAx;
    }

    static String[] cacheArray=new String[]{
            "room/queryGame",//游戏房间
            "index/query",//首页
            "cqssc/top10",//重庆时时彩最新10期开奖
            "bj28/top10",//最新10期开奖
            "canada28/top10",//最新10期开奖
            "bank/querybank",//可用银行
            "share/link",//分享信息
            "personinfo/query",//个人信息
            "index/dynamic",//首页滚动动画
            "wallet/types",//钱包明细  类型
            "msgserver"//消息服务器

    };

    @Override
    public  String[] getCacheList() {
        return cacheArray;
    }
    /**
     * 自带前缀构造一个请求
     *
     * @return
     */
    public static HttpToolAx urlBase(String urlStr) {
        return url(HttpConfigAx.getBaseUrl() + urlStr);
    }

    public HttpToolAx sign() {
        addQueryParams("sign", sign(getQueryMap()));
        return this;
    }
    public static String sign(Map<String, Object> map) {
        String result = "";
        try {
            if (map != null && map.size() > 0) {
                StringBuffer sb = new StringBuffer();
                TreeMap<String, Object> treeMap = new TreeMap<>();
                treeMap.putAll(map);
                for (Map.Entry<String, Object> entry : treeMap.entrySet()) {
                    sb.append("&");
                    sb.append(entry.getKey() + "=" + entry.getValue());
                }
                result = sb.substring(1, sb.length()) + "*SHOP*";
                result = Md5Tool.md5(result);
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return result;
    }


    @Override
    public HttpToolAx setUseCache() {
        super.setUseCache("timestamp","sign");
        return this;
    }

    public <T extends JsonDataParent> HttpRequest send(Class<T> clzz, HttpUiCallBack<T> callBack) {
        sign();
        return super.send(clzz, callBack);
    }

    /**
     * 设置分页  大小
     *
     * @param pagesize
     * @return
     */
    public HttpToolAx setPageSize(int pagesize) {
        return addQueryParams("pageSize", pagesize);
    }

    /***
     * 设置当前页数
     *
     * @param pagenum
     * @return
     */
    public HttpToolAx setPageNum(int pagenum) {
        return addQueryParams("page", pagenum);
    }

    @Override
    public HttpToolAx addQueryParams(String queryKey, Object queryValue) {
        super.addQueryParams(queryKey, queryValue);
        return this;
    }

    @Override
    public HttpToolAx removeParams(String queryKey) {
        super.removeParams(queryKey);
        return this;
    }

    @Override
    public HttpToolAx get() {
        super.get();
        return this;
    }

    @Override
    public HttpToolAx post() {
        super.post();
        return this;
    }


}
