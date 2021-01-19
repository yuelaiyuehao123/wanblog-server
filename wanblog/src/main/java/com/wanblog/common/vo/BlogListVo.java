package com.wanblog.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BlogListVo implements Serializable {

    private Long id;

    private String title;

    private String description;

    private String username;

    private String avatar;

}
