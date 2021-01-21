package com.wanblog.common.lang;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result implements Serializable {

    private int code;
    private String msg;
    private Object data;

    public static Result ok() {
        return ok(null);
    }

    public static Result ok(Object data) {
        return ok(APICode.SUCCESS.getCode(), APICode.SUCCESS.getMsg(), data);
    }

    public static Result ok(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

    public static Result error(String msg) {
        return error(400, msg, null);
    }

    public static Result error(int code, String msg) {
        return error(code, msg, null);
    }

    public static Result error(String msg, Object data) {
        return error(400, msg, data);
    }

    public static Result error(APICode apiCode) {
        Result r = new Result();
        r.setCode(apiCode.getCode());
        r.setMsg(apiCode.getMsg());
        r.setData(null);
        return r;
    }

    public static Result error(int code, String msg, Object data) {
        Result r = new Result();
        r.setCode(code);
        r.setMsg(msg);
        r.setData(data);
        return r;
    }

}
