package com.jwt.demo.controller;


import com.jwt.demo.DTO.UserDTO;
import com.jwt.demo.accessctro.RoleContro;
import com.jwt.demo.enums.RoleEnum;
import com.jwt.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: zty
 * @date 2019/9/5 下午5:06
 * @description:
 */
@RequestMapping("/admin")
@RestController
@Api(tags = "管理员接口")
@CrossOrigin
public class AdminController {

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    @ApiOperation("添加用户")
    //@RoleContro(role = RoleEnum.ADMIN)
    public Object addUser(UserDTO userDTO) {
        return userService.addUser(userDTO);
    }
}
