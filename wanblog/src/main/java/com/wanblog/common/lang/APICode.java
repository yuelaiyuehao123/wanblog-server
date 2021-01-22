package com.wanblog.common.lang;

/***
 * 错误码和错误信息定义类
 */
public enum APICode {

    SUCCESS(200, "操作成功"),
    REQUEST_EXCEPTION(1001, "请求错误"),
    USER_NO_EXIST_EXCEPTION(2001, "用户不存在"),
    USERNAME_EXIST_EXCEPTION(2002, "用户名已存在"),
    PASSWORD_EXCEPTION(2003, "密码错误"),
    TOKEN_EXCEPTION(2004, "token错误"),
    BLOG_NO_EXIST_EXCEPTION(3001, "没有该博客"),
    BLOG_NO_EDIT_EXCEPTION(3002, "没有权限修改");

    private int code;
    private String msg;

    APICode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
