package com.wanblog.mapper;

import com.wanblog.common.vo.Top3ListVo;
import com.wanblog.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author yuelaiyuehao
 * @since 2021-01-13
 */
@Repository
public interface BlogMapper extends BaseMapper<Blog> {

    List<Top3ListVo> getTop3List();

}
