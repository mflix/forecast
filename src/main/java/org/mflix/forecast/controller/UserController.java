package org.mflix.forecast.controller;

import javax.validation.Valid;

import org.mflix.forecast.component.ResponseComponent;
import org.mflix.forecast.core.BaseRespones;
import org.mflix.forecast.entity.UserEntity;
import org.mflix.forecast.enumeration.StatusEnumeration;
import org.mflix.forecast.param.UserParam;
import org.mflix.forecast.service.UserService;
import org.mflix.forecast.view.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public BaseRespones register(UserEntity user) {
        //判断账号是否存在
        if (!ObjectUtils.isEmpty(userService.getUserByUserName(user.getUsername()))) {
            return BaseRespones.failed("账号已经存在");
        }
        //会员创建
        userService.createUser(user);
        UserParam model = dataChange(user);
        return BaseRespones.ok(model);
    }

    /**
     * 用户列表,供测试和后台管理使用
     *
     * @param
     * @return
     */
    @PostMapping("/list")
    public BaseRespones getList() {
        //判断账号是否存在
        Page<UserEntity> page = userService.getList();
        if (ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(page.toList())) {
            return BaseRespones.ok();
        } else {
            List<UserParam> list = new ArrayList<UserParam>();
            for (UserEntity bean : page.toList()) {
                list.add(dataChange(bean));
            }
            return BaseRespones.ok(list);
        }
    }

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseView> login(UserEntity user) {
        UserEntity userBean = userService.getUserByUserName(user.getUsername());
        return responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, dataChange(userBean));
    }

    /**
     * 密码修改
     *
     * @param username    用户名
     * @param oldpassword 旧密码
     * @param newpassword 新密码
     * @return
     */
    @PostMapping("/modifypassword")
    public BaseRespones changePassword(String username, String oldpassword, String newpassword) {
        Map map = userService.userPasswordChange(username, oldpassword, newpassword);
        if ("ok".equals(map.get("message"))) {
            return BaseRespones.ok(dataChange((UserEntity) map.get("object")));
        } else {
            return BaseRespones.failed(map.get("message").toString());
        }
    }

    /**
     * 信息修改
     *
     * @param id
     * @param sex
     * @param nickname
     * @param file
     * @return
     */
    @PostMapping("/modifyinfo")
    public BaseRespones updateUser(Long id, String sex, String nickname, MultipartFile file) {
        Map map = userService.updateUser(id, sex, nickname, file);
        if ("ok".equals(map.get("message"))) {
            return BaseRespones.ok(dataChange((UserEntity) map.get("object")));
        } else {
            return BaseRespones.failed(map.get("message").toString());
        }
    }

    public UserParam dataChange(UserEntity user) {
        UserParam model = new UserParam();
        model.setId(String.valueOf(user.getId()));
        model.setAvatar(user.getAvatarUrl());
        model.setEmail(user.getEmail());
        model.setNickname(user.getNickname());
        model.setScore(0l);
        model.setSex(user.getSex());
        if (user.getStatus().intValue() == 1) {
            model.setStatus("已激活");
        } else {
            model.setStatus("未激活");
        }
        return model;
    }
}