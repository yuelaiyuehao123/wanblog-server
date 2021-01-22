package com.wanblog.common.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BlogListVo implements Serializable {

    private Long blog_id;

    private Long user_id;

    private String title;

    private String description;

    private String username;

    private String avatar;

}
