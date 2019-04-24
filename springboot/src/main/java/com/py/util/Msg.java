package com.py.util;

/**
 * 通用的返回的类
 */
public class Msg {

    //状态码   1 成功 ， 2 失败
    private int code;
    //提示信息
    private String msg;

    //返回数据
    private Object data;


    public static Msg success() {
        Msg result = new Msg();
        result.setCode(1);
        result.setMsg("成功");
        return result;
    }

    public static Msg success(Object object) {
        Msg result = new Msg();
        result.setCode(1);
        result.setMsg("成功");
        result.setData(object);
        return result;
    }

    public static Msg fail(String msg) {
        Msg result = new Msg();
        result.setCode(2);
        result.setMsg(msg);
        return result;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}
