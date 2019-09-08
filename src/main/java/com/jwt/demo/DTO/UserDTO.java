package com.jwt.demo.DTO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author: LZH
 * @date 2019/9/5 下午4:42
 * @description:
 */

@Data
public class UserDTO {

    @NotNull(message = "不能为空")
    @ApiModelProperty("成员名称")
    private String username;

    @NotNull(message = "不能为空")
    private Integer role;
}
