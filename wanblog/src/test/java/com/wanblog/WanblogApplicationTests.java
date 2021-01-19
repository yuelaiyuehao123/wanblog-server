package com.wanblog;

import cn.hutool.core.bean.BeanUtil;
import com.wanblog.entity.Blog;
import com.wanblog.service.BlogService;
import com.wanblog.util.ShiroUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class WanblogApplicationTests {

    @Autowired
    BlogService blogService;


    @Test
    void contextLoads() {

        for (int i = 1; i <= 65; i++) {
            Blog blog = new Blog();
            blog.setUserId(1L);
            blog.setCreated(LocalDateTime.now());
            blog.setStatus(0);
            blog.setTitle("title" + i);
            blog.setDescription("description" + i + "description" + i + "description" + i + "description" + i + "description" + i);
            blog.setContent("content" + i + "content" + i + "content" + i + "content" + i + "content" + i);
            blogService.save(blog);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
