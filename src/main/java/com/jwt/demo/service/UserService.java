package com.jwt.demo.service;

import com.jwt.demo.DTO.UserDTO;
import com.jwt.demo.entity.User;
import com.jwt.demo.form.LoginForm;
import com.jwt.demo.vo.ResultVO;

import javax.servlet.http.HttpServletResponse;

/**
 * @author: LZH
 * @date 2019/9/5 下午4:40
 * @description: 用户逻辑层
 */

public interface UserService {

    /**
     * 添加用户
     * @param userDTO
     * @return
     */
    ResultVO addUser(UserDTO userDTO);

    /**
     * 获取当前用户
     * @return
     */
    User getCurrentUser();

    /**
     * 通过用户名获取用户
     * @param userName
     * @return
     */
    User getUserByUsername(String userName);

    ResultVO login(LoginForm loginForm, HttpServletResponse response);

}
