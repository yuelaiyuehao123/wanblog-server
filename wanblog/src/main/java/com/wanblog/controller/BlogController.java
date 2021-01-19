package com.wanblog.controller;


import cn.hutool.core.bean.BeanUtil;
import com.wanblog.common.dto.DeleteBlogDto;
import com.wanblog.common.dto.EditBlogDto;
import com.wanblog.common.dto.PublishBlogDto;
import com.wanblog.common.lang.Result;
import com.wanblog.entity.Blog;
import com.wanblog.service.BlogService;
import com.wanblog.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


/**
 * @author yuelaiyuehao
 * @since 2021-01-13
 */
@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @GetMapping("/list")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "5") Integer size) {
        List<Blog> blogList = blogService.blogList(currentPage, size);
        return Result.succ(blogList);
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable(name = "id") Long id) {
        Blog blog = blogService.getById(id);
        Assert.notNull(blog, "该博客已被删除");

        return Result.succ(blog);
    }

    @RequiresAuthentication
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody EditBlogDto editBlogDto) {
        Blog temp;
        temp = blogService.getById(editBlogDto.getId());
        // 只能编辑自己的文章
        System.out.println(ShiroUtil.getProfile().getId());
        Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");
        BeanUtil.copyProperties(editBlogDto, temp, "id", "userId", "created", "status");
        blogService.updateById(temp);
        return Result.succ(null);
    }

    @RequiresAuthentication
    @PostMapping("/publish")
    public Result publish(@Validated @RequestBody PublishBlogDto publishBlogDto) {
        Blog blog = new Blog();
        blog.setUserId(ShiroUtil.getProfile().getId());
        blog.setCreated(LocalDateTime.now());
        blog.setStatus(0);
        BeanUtil.copyProperties(publishBlogDto, blog);
        blogService.save(blog);
        return Result.succ(null);
    }

    @GetMapping("/delete")
    public Result delete(@Validated @RequestBody DeleteBlogDto deleteBlogDto) {
        Blog blog = blogService.getById(deleteBlogDto.getId());
        Assert.notNull(blog, "该博客已被删除");
        boolean result = blogService.removeById(deleteBlogDto.getId());
        return result ? Result.succ(null) : Result.fail(null);
    }

}
