package com.ny.client.util.result;

/**
 * @Auther: Administrator
 * @Date: 2018-11-07 14:09
 * @Description: 响应前端
 */
public class ResponseData {

    private Object data;

    private int code;

    private String Message;

    public ResponseData(int code, String message) {
        this.code = code;
        Message = message;
    }

    public ResponseData(Object data, int code, String message) {
        this.data = data;
        this.code = code;
        Message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
