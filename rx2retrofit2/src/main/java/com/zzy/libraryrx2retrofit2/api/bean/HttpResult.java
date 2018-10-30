package com.zzy.libraryrx2retrofit2.api.bean;

/**
 * 请求结果抽象
 * @param <T>
 */
public class HttpResult<T> {
    /*
    {
        "errorCode":0,
        "errorMsg":"Return Successd!",
        "data":{
            "name":"张三"
            "age":3
        }
    }
    */
    private int errorCode;// 请求成功or失败码，0代表请求成功
    private String errorMsg;// 说明
    private T data;// 具体内容

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
