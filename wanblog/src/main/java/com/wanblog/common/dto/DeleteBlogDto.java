package com.wanblog.common.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DeleteBlogDto implements Serializable {

    @NotNull(message = "id不能为空")
    private Long bid;

}
