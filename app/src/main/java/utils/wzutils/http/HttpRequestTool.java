package utils.wzutils.http;


import java.lang.reflect.Field;

import utils.wzutils.common.LogTool;

/**
 * abc on 2017/8/29.
 */

public class HttpRequestTool {
    public static void initRequestByData(HttpRequest httpRequest,Class requestDataClass,Object requestData){
        try {
            if(httpRequest==null)return;
            if(requestDataClass==null)return;
            if(requestData==null)return;
            Field[] fields=requestDataClass.getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                httpRequest.addQueryParams(field.getName(),""+field.get(requestData));
            }
        }catch (Exception e){
            LogTool.ex(e);
        }

    }
}
