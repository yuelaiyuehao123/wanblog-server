package com.wanblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanblog.common.dto.DeleteBlogDto;
import com.wanblog.common.dto.EditBlogDto;
import com.wanblog.common.dto.PublishBlogDto;
import com.wanblog.common.vo.BlogListVo;
import com.wanblog.entity.Blog;
import com.wanblog.entity.User;
import com.wanblog.mapper.BlogMapper;
import com.wanblog.mapper.UserMapper;
import com.wanblog.service.BlogService;
import com.wanblog.util.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
            vo.setUsername(user.getUsername());
            vo.setAvatar(user.getAvatar());
            BeanUtil.copyProperties(blog, vo);
            blogListVoList.add(vo);
        }
        return blogListVoList;
    }

    @Override
    public Blog detail(Long id) {
        Blog blog = blogMapper.selectById(id);
        Assert.notNull(blog, "该博客已被删除");
        return blog;
    }

    @Override
    public void edit(EditBlogDto editBlogDto) {
        Blog temp = blogMapper.selectById(editBlogDto.getId());
        // 只能编辑自己的文章
        System.out.println(ShiroUtil.getProfile().getId());
        Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getProfile().getId().longValue(), "没有权限编辑");
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
        Blog blog = blogMapper.selectById(deleteBlogDto.getId());
        Assert.notNull(blog, "该博客已被删除");
        blogMapper.deleteById(deleteBlogDto.getId());
    }
}
