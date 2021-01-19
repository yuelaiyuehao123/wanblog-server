package com.wanblog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wanblog.entity.Blog;
import com.wanblog.mapper.BlogMapper;
import com.wanblog.service.BlogService;
import org.springframework.stereotype.Service;

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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Override
    public List<Blog> blogList(int currentPage, int size) {
        Page<Blog> page = new Page<>(currentPage, size);
        Page<Blog> blogPage = baseMapper.selectPage(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return blogPage.getRecords();
    }
}
