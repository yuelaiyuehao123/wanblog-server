package com.wanblog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanblog.common.dto.LoginDto;
import com.wanblog.common.dto.SignUpDto;
import com.wanblog.common.vo.LoginVo;
import com.wanblog.entity.User;
import com.wanblog.mapper.UserMapper;
import com.wanblog.service.UserService;
import com.wanblog.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author yuelaiyuehao
 * @since 2021-01-13
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    public void signUp(SignUpDto signUpDto) {
        User dbUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", signUpDto.getUsername()));
        Assert.isNull(dbUser, "用户已存在");
        User user = new User();
        user.setPassword(SecureUtil.md5(signUpDto.getPassword()));
        user.setUsername(signUpDto.getUsername());
        user.setStatus(0);
        user.setCreated(LocalDateTime.now());
        userMapper.insert(user);
    }

    @Override
    public LoginVo login(LoginDto loginDto) {
        User dbUser = userMapper.selectOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(dbUser, "用户不存在");
        Assert.isTrue(dbUser.getPassword().equals(SecureUtil.md5(loginDto.getPassword())), "密码错误");
        String token = jwtUtils.generateToken(dbUser.getId());
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        BeanUtil.copyProperties(dbUser, loginVo);
        return loginVo;
    }

    @Override
    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    @Override
    public User getById(Long userId) {
        return userMapper.selectById(userId);
    }

}
