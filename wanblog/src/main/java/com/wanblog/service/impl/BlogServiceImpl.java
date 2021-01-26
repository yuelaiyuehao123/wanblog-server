package com.wanblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wanblog.common.dto.DeleteBlogDto;
import com.wanblog.common.dto.EditBlogDto;
import com.wanblog.common.dto.PublishBlogDto;
import com.wanblog.common.exception.BlogNoEditException;
import com.wanblog.common.exception.BlogNoExistException;
import com.wanblog.common.vo.BlogListVo;
import com.wanblog.common.vo.BlogVo;
import com.wanblog.common.vo.Top3ListVo;
import com.wanblog.entity.Blog;
import com.wanblog.entity.User;
import com.wanblog.mapper.BlogMapper;
import com.wanblog.mapper.UserMapper;
import com.wanblog.service.BlogService;
import com.wanblog.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuelaiyuehao
 * @since 2021-01-13
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<BlogListVo> blogList(int currentPage, int size) {
        Page<Blog> page = new Page<>(currentPage, size);
        Page<Blog> blogPage = blogMapper.selectPage(page, new QueryWrapper<Blog>().orderByDesc("created"));
        List<Blog> blogList = blogPage.getRecords();
        ArrayList<BlogListVo> blogListVoList = new ArrayList<>();
        for (Blog blog : blogList) {
            Long userId = blog.getUserId();
            User user = userMapper.selectById(userId);
            BlogListVo vo = new BlogListVo();
            vo.setBid(blog.getId());
            vo.setUid(userId);
            vo.setUsername(user.getUsername());
            vo.setAvatar(user.getAvatar());
            vo.setTitle(blog.getTitle());
            vo.setDescription(blog.getDescription());
            blogListVoList.add(vo);
        }
        return blogListVoList;
    }

    @Override
    public BlogVo detail(Long id) {
        Blog blog = blogMapper.selectById(id);
        if (blog == null) {
            throw new BlogNoExistException();
        }
        BlogVo vo = new BlogVo();
        vo.setUid(blog.getUserId());
        vo.setBid(blog.getId());
        BeanUtil.copyProperties(blog, vo);
        return vo;
    }

    @Override
    public void edit(EditBlogDto editBlogDto) {
        Blog temp = blogMapper.selectById(editBlogDto.getBid());
        if (temp == null) {
            throw new BlogNoExistException();
        }
        if (temp.getUserId().longValue() != ShiroUtil.getProfile().getId().longValue()) {
            throw new BlogNoEditException();
        }
        BeanUtil.copyProperties(editBlogDto, temp, "id", "userId", "created", "status");
        blogMapper.updateById(temp);
    }

    @Override
    public void publish(PublishBlogDto publishBlogDto) {
        Blog blog = new Blog();
        blog.setUserId(ShiroUtil.getProfile().getId());
        blog.setCreated(LocalDateTime.now());
        blog.setStatus(0);
        BeanUtil.copyProperties(publishBlogDto, blog);
        blogMapper.insert(blog);
    }

    @Override
    public void delete(DeleteBlogDto deleteBlogDto) {
        blogMapper.deleteById(deleteBlogDto.getBid());
    }

    @Override
    public List<Top3ListVo> top3List() {
        List<Top3ListVo> list = blogMapper.getTop3List();
        return list;
    }
}
