package com.wanblog.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVo implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String token;

}