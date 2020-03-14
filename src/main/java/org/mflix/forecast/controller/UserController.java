package org.mflix.forecast.controller;

import javax.validation.Valid;

import org.mflix.forecast.component.ResponseComponent;
import org.mflix.forecast.entity.UserEntity;
import org.mflix.forecast.enumeration.StatusEnumeration;
import org.mflix.forecast.service.UserService;
import org.mflix.forecast.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final ResponseComponent responseComponent;
    private final UserService userService;

    @Autowired
    public UserController(ResponseComponent responseComponent, UserService userService) {
        this.responseComponent = responseComponent;
        this.userService = userService;
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseView> register(UserEntity user) {
        //判断账号是否存在
        if (!ObjectUtils.isEmpty(userService.getUserByUserName(user.getUsername()))) {
            return responseComponent.generate(4, "此账号已经存在", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        //会员创建
        userService.createUser(user);
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, user);
    }
    @PostMapping("/login")
    public ResponseEntity<ResponseView> login(UserEntity user) {
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, user);
    }
}