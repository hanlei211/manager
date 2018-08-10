package com.hlc.manager.util;

import java.io.Serializable;

/**
 * @Author rjyx
 * @Description 返回结果
 * @Date create in 2018/8/10
 * @Modify by
 */
public class Result implements Serializable {
    private static final long serialVersionUID = 7285065610386199394L;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    private String msg;
    private Object object;

    public Result() {
        this.code = IStatusMessage.SystemStatus.SUCCESS.getCode();
        this.msg = IStatusMessage.SystemStatus.SUCCESS.getMessage();
    }

    public Result(IStatusMessage statusMessage) {
        this.code = statusMessage.getCode();
        this.msg = statusMessage.getMessage();
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", object=" + object +
                '}';
    }
}
