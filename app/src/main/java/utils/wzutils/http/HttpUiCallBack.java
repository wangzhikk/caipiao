package utils.wzutils.http;


import android.os.Looper;
import android.util.Log;

import utils.wzutils.AppTool;
import utils.wzutils.common.CommonTool;
import utils.wzutils.common.LogTool;
import utils.wzutils.common.StringTool;
import utils.wzutils.common.thread.ThreadTool;
import utils.wzutils.json.JsonDataParent;

public abstract class HttpUiCallBack<T> {
    public abstract void onSuccess(T data);
    public void onError(HttpRequest result) {
       // CommonTool.showToast(getErrorState().getMsg());
    }

    State getErrorState() {
        State state = State.stateOnNetLocalError;
        if (!CommonTool.isNetworkAvailable()) {
            state = State.stateOnNetLocalError;
        } else {
            state = State.stateOnNetServerError;
        }
        return state;
    }

    private void notifyStateInMessage(final State state, final HttpRequest httpRequest) {
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                T resultData = null;
                boolean userResultData=false;//最后是否使用  resultData通知ui
                try {

                    try {
                        if (State.stateOnCache.equals(state)) {//使用缓存
                            resultData = (T) httpRequest.getCacheData();
                            userResultData=true;
                            return;
                        } else {//网络请求返回的
                            httpRequest.printLog();
                            if(httpRequest.isUseCache()&&StringTool.notEmpty(httpRequest.getCacheStr())){//本地有缓存
                                if(httpRequest.getResponseDataStr().equals(httpRequest.getCacheStr())//和缓存一样
                                        ||StringTool.isEmpty(httpRequest.getResponseDataStr())//没接收到数据
                                        ){
                                    LogTool.s("----------------------使用的缓存数据，网络请求：----------------------");
                                    return;
                                }
                            }

                            {//本地没有缓存，或者和缓存的数据不一样 那必须返回数据
                                userResultData=true;
                                resultData = (T) httpRequest.getResponseData();
                                httpRequest.saveToLocalCache();
                            }
                        }
                    }catch (Exception e){
                        LogTool.ex(e);
                    }

                } catch (Exception e) {
                    LogTool.ex(e);
                }finally {
                    if(userResultData){
                        try {//不管结局咋样，  必须返回数据
                            final T finalResultData = resultData;
                            AppTool.uiHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        onSuccess(finalResultData);
                                    }catch (Exception e){
                                        LogTool.ex(e);
                                    }
                                }
                            });
                        } catch (Exception e) {
                            LogTool.ex(e);
                        }
                    }

                }
            }
        };

        if(Looper.getMainLooper()==Looper.myLooper()){//这里可以保证 解析数据是在非ui 线程里面做的
            ThreadTool.execute(runnable,999);
        }else {
            runnable.run();
        }

    }

    /***
     * 通知成功
     */
    public void notifyState(State state, HttpRequest httpRequest, Class<T> clzz) {
        try {
            int stateInt=state.getValue();
            if (stateInt == State.stateOnSuccess.value) {
                notifyStateInMessage(State.stateOnSuccess, httpRequest);
            } else if (stateInt == State.stateOnCache.value) {
                notifyStateInMessage(State.stateOnCache, httpRequest);
            } else if (stateInt == State.stateOnNetLocalError.value || stateInt == State.stateOnNetServerError.value) {
                onError(httpRequest);
                notifyStateInMessage(State.stateOnNetLocalError, httpRequest);
            } else if (stateInt == State.stateOnCancelled.value) {
                notifyStateInMessage(State.stateOnCancelled, httpRequest);
            } else if (stateInt == State.stateOnFinish.value) {

            }
        } catch (Exception e) {
            LogTool.ex(e);
        }
    }

    public enum State {
        stateOnCache(3, "获取缓存数据成功"),
        stateOnSuccess(1, "解析数据成功"),//解析成功
        stateOnNetServerError(-1001, "获取数据失败"),//网络问题解析失败
        stateOnNetLocalError(-1002, "获取数据失败请检查网络"),
        stateOnDataError(-1004, "解析数据出错"),//数据格式错误
        stateOnCancelled(-1003, "取消了"),//取消了
        stateOnFinish(2, "完成了");

        int value = 4;
        String msg = "";


        State(int valueIn, String msg) {
            this.value = valueIn;
            this.msg = msg;
        }

        public int getValue() {
            return value;
        }

        public String getMsg() {
            return msg;
        }
    }


}
