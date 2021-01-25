package com.wanblog.controller;

import com.wanblog.common.dto.DeleteBlogDto;
import com.wanblog.common.dto.EditBlogDto;
import com.wanblog.common.dto.PublishBlogDto;
import com.wanblog.common.exception.BlogNoEditException;
import com.wanblog.common.exception.BlogNoExistException;
import com.wanblog.common.lang.APICode;
import com.wanblog.common.lang.Result;
import com.wanblog.common.vo.BlogListVo;
import com.wanblog.common.vo.BlogVo;
import com.wanblog.entity.Blog;
import com.wanblog.service.BlogService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        List<BlogListVo> blogList = blogService.blogList(currentPage, size);
        return Result.ok(blogList);
    }

    @GetMapping("/{bid}")
    public Result detail(@PathVariable(name = "bid") Long id) {
        try {
            BlogVo vo = blogService.detail(id);
            return Result.ok(vo);
        } catch (BlogNoExistException blogNoExistException) {
            return Result.error(APICode.BLOG_NO_EXIST_EXCEPTION);
        }
    }

    @RequiresAuthentication
    @PostMapping("/edit")
    public Result edit(@Validated @RequestBody EditBlogDto editBlogDto) {
        try {
            blogService.edit(editBlogDto);
            return Result.ok();
        } catch (BlogNoExistException blogNoExistException) {
            return Result.error(APICode.BLOG_NO_EXIST_EXCEPTION);
        } catch (BlogNoEditException blogNoEditException) {
            return Result.error(APICode.BLOG_NO_EDIT_EXCEPTION);
        }
    }

    @RequiresAuthentication
    @PostMapping("/publish")
    public Result publish(@Validated @RequestBody PublishBlogDto publishBlogDto) {
        blogService.publish(publishBlogDto);
        return Result.ok();
    }

    @GetMapping("/delete")
    public Result delete(@Validated @RequestBody DeleteBlogDto deleteBlogDto) {
        blogService.delete(deleteBlogDto);
        return Result.ok();
    }

}
