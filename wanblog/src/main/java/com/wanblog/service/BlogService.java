package com.wanblog.service;

import com.wanblog.common.dto.DeleteBlogDto;
import com.wanblog.common.dto.EditBlogDto;
import com.wanblog.common.dto.PublishBlogDto;
import com.wanblog.common.vo.BlogListVo;
import com.wanblog.entity.Blog;

import java.util.List;

/**
 * @author yuelaiyuehao
 * @since 2021-01-13
 */
public interface BlogService {

    List<BlogListVo> blogList(int currentPage, int size);

    Blog detail(Long id);

    void edit(EditBlogDto editBlogDto);

    void publish(PublishBlogDto publishBlogDto);

    void delete(DeleteBlogDto deleteBlogDto);
}
