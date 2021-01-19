package com.wanblog.service;

import com.wanblog.common.dto.LoginDto;
import com.wanblog.common.dto.SignUpDto;
import com.wanblog.common.vo.LoginVo;
import com.wanblog.entity.User;

/**
 * @author yuelaiyuehao
 * @since 2021-01-13
 */
public interface UserService {

    void signUp(SignUpDto signUpDto);

    LoginVo login(LoginDto loginDto);

    void logout();

    User getById(Long userId);
}
