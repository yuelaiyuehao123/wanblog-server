package com.wanblog.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Top3ListVo implements Serializable {

    private Long uid;

    private String username;

    private Long count;

}
