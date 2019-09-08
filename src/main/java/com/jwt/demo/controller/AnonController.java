package com.jwt.demo.controller;

import com.jwt.demo.form.LoginForm;
import com.jwt.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author: zty
 * @date 2019/9/6 上午10:37
 * @description:
 */
@RestController
@RequestMapping("/anon")
@Api(tags = "用户认证接口")
@CrossOrigin
public class AnonController {

    @Autowired
    private UserService userService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public Object login(@Valid LoginForm loginForm, HttpServletResponse response) {
        return userService.login(loginForm, response);
    }
}
