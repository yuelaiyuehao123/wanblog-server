package com.wanblog.controller;


import com.wanblog.common.dto.LoginDto;
import com.wanblog.common.dto.SignUpDto;
import com.wanblog.common.exception.PasswordException;
import com.wanblog.common.exception.UserExistException;
import com.wanblog.common.exception.UserNoExistException;
import com.wanblog.common.lang.APICode;
import com.wanblog.common.lang.Result;
import com.wanblog.common.vo.LoginVo;
import com.wanblog.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        try {
            userService.signUp(signUpDto);
            return Result.ok();
        } catch (UserExistException userExistException) {
            return Result.error(APICode.USERNAME_EXIST_EXCEPTION);
        }
    }

    @PostMapping("/login")
    public Result login(@Validated @RequestBody LoginDto loginDto) {
        try {
            LoginVo loginVo = userService.login(loginDto);
            return Result.ok(loginVo);
        } catch (UserNoExistException userNoExistException) {
            return Result.error(APICode.USER_NO_EXIST_EXCEPTION);
        } catch (PasswordException passwordException) {
            return Result.error(APICode.PASSWORD_EXCEPTION);
        }
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    public Result logout() {
        userService.logout();
        return Result.ok();
    }

}
