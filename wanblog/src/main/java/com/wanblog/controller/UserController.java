package com.wanblog.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanblog.common.dto.LoginDto;
import com.wanblog.common.dto.SignUpDto;
import com.wanblog.common.lang.Result;
import com.wanblog.entity.User;
import com.wanblog.service.UserService;
import com.wanblog.util.JwtUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @author yuelaiyuehao
 * @since 2021-01-13
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtUtils jwtUtils;

    @RequiresAuthentication
    @GetMapping("/index")
    public Result index() {
        User user = userService.getById(1L);
        return Result.succ(user);
    }

    @PostMapping("/signUp")
    public Result signUp(@Validated @RequestBody SignUpDto signUpDto) {
        User dbUser = userService.getOne(new QueryWrapper<User>().eq("username", signUpDto.getUsername()));
        Assert.isNull(dbUser, "用户已存在");
        User user = new User();
        user.setPassword(SecureUtil.md5(signUpDto.getPassword()));
        user.setUsername(signUpDto.getUsername());
        user.setStatus(0);
        user.setCreated(LocalDateTime.now());
        boolean result = userService.save(user);
        return result ? Result.succ(null) : Result.fail(null);
    }

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto) {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", loginDto.getUsername()));
        Assert.notNull(user, "用户不存在");
        if (!user.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))) {
            return Result.fail("密码不正确");
        }
        String jwt = jwtUtils.generateToken(user.getId());
        return Result.succ(MapUtil.builder()
                .put("id", user.getId())
                .put("username", user.getUsername())
                .put("email", user.getEmail())
                .put("token", jwt)
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.succ(null);
    }

}
