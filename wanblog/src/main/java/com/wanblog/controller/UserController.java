package com.wanblog.controller;


import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wanblog.common.dto.LoginDto;
import com.wanblog.common.dto.SignUpDto;
import com.wanblog.common.lang.Result;
import com.wanblog.common.vo.LoginVo;
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

    @PostMapping("/signUp")
    public Result signUp(@Validated @RequestBody SignUpDto signUpDto) {
        userService.signUp(signUpDto);
        return Result.succ(null);
    }

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto) {
        LoginVo loginVo = userService.login(loginDto);
        return Result.succ(loginVo);
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        userService.logout();
        return Result.succ(null);
    }

}
