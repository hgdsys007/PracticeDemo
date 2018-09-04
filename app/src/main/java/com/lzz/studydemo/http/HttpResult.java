package com.lzz.studydemo.http;


import com.lzz.studydemo.Bean.BaseBean;

/**
 * TODO:请求返回实体类
 */
public class HttpResult<T> extends BaseBean {

    public static final int SUCCESS = 200;//返回成功
    public static final int SUCCESS_REGISTER = 201;//返回成功

    private int respCode;
    private String respDesc;
    private T attribute;

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public T getData() {
        return attribute;
    }

    public void setData(T data) {
        this.attribute = data;
    }
}
