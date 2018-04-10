package utils.wzutils.http;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import utils.wzutils.HttpTool;
import utils.wzutils.JsonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.db.MapDB;
import utils.wzutils.encypt.Md5Tool;
import utils.wzutils.json.JsonDataParent;
import utils.wzutils.ui.pullrefresh.PageControl;

/**
 * Created by coder on 15/12/25.
 * http 请求的参数
 */
public class HttpRequest {
    public long timeBeginRequest = 0;//开始请求的时间
    public String cacheStr = "";
    private String urlStr = "";
    private String charset = "UTF-8";
    private boolean useCookie = true; // 是否在请求过程中启用cookie
    private boolean cancelFast = true; // 是否可以被立即停止, true: 为请求创建新的线程, 取消时请求线程被立即中断.
    private boolean useCache = false;//是否启用缓存
    private RequestMethod requestMethod = RequestMethod.GET;
    private Map<String, Object> queryMap = new TreeMap<String, Object>();
    private Map<String, String> headerMap = new TreeMap<String, String>();
    private String bodyCountent = "";//发送内容就是一个字符串
    private Class responseClass;//返回数据的class
    /**
     * 返回的数据
     */
    private Object responseData;
    /**
     * 返回的字符串
     */
    private String responseDataStr;

    /**
     * 构造一个请求
     *
     * @param url
     * @return
     */
    public static HttpRequest url(String url) {
        HttpRequest httpRequest = new HttpRequest();
        httpRequest.urlStr = url;
        return httpRequest;
    }

    public boolean isUseCache() {
        return useCache;
    }

    public HttpRequest setUseCache() {
        return setUseCache(excludeCacheParams);
    }
    String[]excludeCacheParams;
    public HttpRequest setUseCache(String...excludeCacheParams) {
        this.useCache = true;
        this.excludeCacheParams=excludeCacheParams;
        return this;
    }
    /**
     * 放入一个请求参数
     *
     * @param queryKey
     * @param queryValue
     * @return
     */
    public HttpRequest addQueryParams(String queryKey, Object queryValue) {
        if (queryValue != null && !StringTool.isEmpty("" + queryValue))
            this.queryMap.put(queryKey, queryValue);
        return this;
    }

    /***
     * 放入很多参数
     * @param params
     * @return
     */
    public HttpRequest addQueryParamsAll(Map<String ,String> params) {
        if(params!=null&&params.size()>0){
            for(Map.Entry<String ,String> entry:params.entrySet()){
                addQueryParams(entry.getKey(),entry.getValue());
            }
        }
        return this;
    }
    /***
     * 删除一个指定的参数
     *
     * @param queryKey
     * @return
     */
    public HttpRequest removeParams(String queryKey) {
        this.queryMap.remove(queryKey);
        return this;
    }

    /**
     * 放入一个header
     *
     * @return
     */
    public HttpRequest addHeader(String headKey, String headerValue) {
        this.headerMap.put(headKey, headerValue);
        return this;
    }

    public HttpRequest get() {
        requestMethod = RequestMethod.GET;
        return this;
    }

    public HttpRequest post() {
        requestMethod = RequestMethod.POST;
        return this;
    }

    public String getBodyCountent() {
        return bodyCountent;
    }

    public void setBodyCountent(String bodyCountent) {
        this.bodyCountent = bodyCountent;
    }

    public String getUrlStr() {
        return urlStr;
    }

    public HttpRequest setUrlStr(String urlStr) {
        this.urlStr = urlStr;
        return this;
    }

    public boolean isUseCookie() {
        return useCookie;
    }

    public void setUseCookie(boolean useCookie) {
        this.useCookie = useCookie;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Map<String, Object> getQueryMap() {
        return queryMap;
    }

    public void setQueryMap(Map<String, Object> queryMap) {
        this.queryMap = queryMap;
    }

    public Map<String, String> getHeaderMap() {
        return headerMap;
    }

    public void setHeaderMap(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isCancelFast() {
        return cancelFast;
    }

    public void setCancelFast(boolean cancelFast) {
        this.cancelFast = cancelFast;
    }

    public Object getResponseData() {
        if(responseData==null){
            if(getResponseClass()!=null){
                setResponseData(JsonTool.toJava(responseDataStr, getResponseClass()));
            }
        }
        return responseData;
    }

    public void setResponseData(Object responseData) {
        this.responseData = responseData;
    }

    public String getResponseDataStr() {
        return "" + responseDataStr;
    }

    public void setResponseDataStr(String responseDataStr, Class clzz) {
        responseDataStr = "" + responseDataStr;
        //LogTool.s("未转unicode的返回数据: " + responseDataStr);
       // responseDataStr = StringTool.unicode2String(responseDataStr);

        this.responseDataStr = responseDataStr;
    }

    public Class getResponseClass() {
        return responseClass;
    }

    public void setResponseClass(Class responseClass) {
        this.responseClass = responseClass;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer(getRequestMethod() + "     " + getUrlStr());
        if (!getUrlStr().contains("?")) {
            sb.append("?");
        }

        Iterator iterator = getQueryMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (RequestMethod.GET.equals(getRequestMethod())) {
                sb.append("&" + entry.getKey() + "=" + entry.getValue());
            } else {
                sb.append("\n");
                sb.append(entry.getKey() + "=" + entry.getValue());
            }
        }


        sb.append("\n");
        sb.append("返回数据:    ");
        sb.append(getResponseDataStr());
        sb.append("\n");

        return sb.toString();
    }

    public void printLogGet() {
        StringBuffer sb = new StringBuffer(getRequestMethod() + "\t:" + getUrlStr());
        if (!getUrlStr().contains("?")) {
            sb.append("?");
        }
        Iterator iterator = getQueryMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            sb.append("&" + entry.getKey() + "=" + entry.getValue());
        }
        LogTool.s("请求完成了: " + sb.toString());
    }

    public void printLogPost() {
        if(requestMethod!=RequestMethod.POST)return;
        LogTool.s("请求完成了" + getRequestMethod() + "\t  耗时：" + (System.currentTimeMillis() - timeBeginRequest) + "\t:" + getUrlStr());
        LogTool.s("请求参数: ");
        Iterator iterator = getQueryMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            LogTool.s(entry.getKey() + "=" + entry.getValue());
        }
    }

    public void printLog() {
        printLogGet();
        printLogPost();
        LogTool.s("请求返回了: " + getResponseDataStr());
    }

    public void readySendRequest() {
        timeBeginRequest = System.currentTimeMillis();
    }

    private String getCacheKey() {
        try {

            Map<String, Object> queryMap=new HashMap(getQueryMap());
            if(excludeCacheParams!=null){
                for(String param:excludeCacheParams){
                    queryMap.remove(param);
                }
            }
            String keyTem = getUrlStr() + getBodyCountent() + JsonTool.toJsonStr(getHeaderMap()) + JsonTool.toJsonStr(queryMap);
            String key = Md5Tool.md5(keyTem);
            return key;
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return "";
    }

    public String getCacheStr() {
        try {
            String key = getCacheKey();
            if (!StringTool.isEmpty(key)) {
                if (!StringTool.isEmpty(cacheStr)) {
                    return cacheStr;
                } else {
                    cacheStr = MapDB.loadObj(getCacheKey(), String.class);
                    return cacheStr;
                }

            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return "";
    }

    public void setCacheStr(String cacheStr) {
        try {
            String key = getCacheKey();
            if (!StringTool.isEmpty(key)) {
                this.cacheStr = cacheStr;
                MapDB.saveObj(getCacheKey(), cacheStr);
                LogTool.s("保存了接口缓存：");
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public void saveToLocalCache() {
        if (isUseCache() && StringTool.notEmpty(getResponseDataStr())) {
            setCacheStr(getResponseDataStr());
        }
    }

    public Object getCacheData() {
        try {
            if (getResponseClass() != null) {
                return JsonTool.toJava(getCacheStr(), getResponseClass());
            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
        return null;
    }

    /***
     * 使用缓存的列表， 子类覆盖
     * @return
     */
    public String[] getCacheList(){
        return null;
    }

    /***
     * 初始化是否使用缓存
     */
    void initUseCache(){
        if(getCacheList()==null)return;
        String url=getUrlStr();
        String[] list=getCacheList();
        for(String tem:list){
            if(url.contains(tem)){
                setUseCache();
                return;
            }
        }
    }
    /*******************
     * 下面是方便qin
     ***********************************/


    public <T extends JsonDataParent> HttpRequest send(Class<T> clzz, HttpUiCallBack<T> callBack) {
        try {//没有更多数据了 的判断 如果不要可注释
            if((""+PageControl.noMoreDataPage).equals(""+getQueryMap().get("page"))){
                JsonDataParent jsonDataParent=clzz.newInstance();
                jsonDataParent.msg="没有更多数据了";
                callBack.onSuccess((T) jsonDataParent);
                return this;
            }
        }catch (Exception e){
            LogTool.ex(e);
        }
        initUseCache();




        setResponseClass(clzz);
        if (callBack == null) callBack = new HttpUiCallBack<T>() {
            @Override
            public void onSuccess(T data) {

            }
        };
        if (isUseCache()) {
            String cacheStr = getCacheStr();
            if (!StringTool.isEmpty(cacheStr)) {
                callBack.notifyState(HttpUiCallBack.State.stateOnCache, this, clzz);
            }
        }
        HttpTool.request(this, clzz, callBack);
        return this;
    }

    /***
     * 取消当前请求
     *
     * 需要在http 实现里面添加  setCacelImp()
     */
    public void cancel(){
        if(cacelImp!=null){
            try {
                cacelImp.run();
            }catch (Exception e){
                LogTool.ex(e);
            }
        }
    }


     Runnable cacelImp;

    /***
     * 需要在http 实现里面添加
     * @param cacelImp
     */
    public void setCacelImp(Runnable cacelImp) {
        this.cacelImp = cacelImp;
    }
    public enum RequestMethod {
        GET("GET"), POST("POST");

        String value = "GET";

        RequestMethod(String valueIn) {
            this.value = valueIn;
        }

        @Override
        public String toString() {
            return value;
        }
    }

}
