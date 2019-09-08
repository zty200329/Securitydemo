package com.jwt.demo.service.serviceImpl;

import com.jwt.demo.DTO.UserDTO;
import com.jwt.demo.dao.UserMapper;
import com.jwt.demo.entity.User;
import com.jwt.demo.enums.ResultEnum;
import com.jwt.demo.form.LoginForm;
import com.jwt.demo.security.JwtProperties;
import com.jwt.demo.security.JwtUserDetailServiceImpl;

import com.jwt.demo.service.UserService;
import com.jwt.demo.util.JwtTokenUtil;
import com.jwt.demo.util.ResultVOUtil;
import com.jwt.demo.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: zty
 * @date 2019/9/5 下午4:49
 * @description:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final String DEFAULT_PASSWORD = "123456";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtUserDetailServiceImpl jwtUserDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        String key = "anonymousUser";
        if (!userName.equals(key)) {
            return getUserByUsername(userName);
        }
        return null;
    }

    @Override
    public User getUserByUsername(String userName) {
        return userMapper.selectUserByUsername(userName);
    }

    @Override
    public ResultVO login(LoginForm loginForm, HttpServletResponse response) {
        User user = userMapper.selectUserByUsername(loginForm.getUsername());
        if (user == null) {
            return ResultVOUtil.error(ResultEnum.USER_NOT_EXIST);
        }
        UserDetails userDetails = jwtUserDetailService.loadUserByUsername(loginForm.getUsername());
        if (!(new BCryptPasswordEncoder().matches(loginForm.getPassword(), userDetails.getPassword()))) {
            return ResultVOUtil.error(ResultEnum.PASSWORD_ERROR);
        }
        Authentication token = new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword(), userDetails.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String realToken = jwtTokenUtil.generateToken(userDetails);
        response.addHeader(jwtProperties.getTokenName(), realToken);

        Map map = new HashMap();
        map.put("role", user.getRole());
        map.put("token", realToken);
        return ResultVOUtil.success(map);
    }

    @Override
    public ResultVO addUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        log.info("用户信息" + user);
        int result = userMapper.insert(user);
        if (result != 1) {
            return ResultVOUtil.error(ResultEnum.SQL_ERROR);
        }
        return ResultVOUtil.success(userDTO);
    }
}
