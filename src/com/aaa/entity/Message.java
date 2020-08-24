package com.aaa.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class Message implements Serializable {

    @JSONField(ordinal = 1)//用来设置把message对象json化的顺序；
    private int code;

    @JSONField(ordinal = 2)
    private String msg;

    @JSONField(ordinal = 3)
    private int count;

    @JSONField(ordinal = 4)
    private Object data;

    public Message(int code, String msg, int count, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
