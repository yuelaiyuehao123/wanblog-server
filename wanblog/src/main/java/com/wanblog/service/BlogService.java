package com.wanblog.service;

import com.wanblog.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author yuelaiyuehao
 * @since 2021-01-13
 */
public interface BlogService extends IService<Blog> {

    List<Blog> blogList(int currentPage, int size);

}
