package com.wanblog;

import cn.hutool.core.bean.BeanUtil;
import com.wanblog.common.vo.Top3ListVo;
import com.wanblog.entity.Blog;
import com.wanblog.service.BlogService;
import com.wanblog.util.ShiroUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class WanblogApplicationTests {

    @Autowired
    BlogService blogService;


    @Test
    void contextLoads() {
        List<Top3ListVo> top3ListVos = blogService.top3List();
    }

}
