package org.mflix.forecast.controller;

import javax.validation.Valid;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
import org.springframework.web.bind.annotation.GetMapping;
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
    public String register(UserEntity user) {
        //判断账号是否存在
        if (!ObjectUtils.isEmpty(userService.getUserByUserName(user.getUsername()))) {
            return JSONObject.toJSONString(BaseRespones.failed("账号已经存在"),SerializerFeature.WriteNullStringAsEmpty);
        }
        //会员创建
        userService.createUser(user);
        UserParam model = dataChange(user);
        return JSONObject.toJSONString(BaseRespones.ok(model),SerializerFeature.WriteNullStringAsEmpty);
    }

    /**
     * 用户列表,供测试和后台管理使用
     *
     * @param
     * @return
     */
    @GetMapping("/list")
    public String getList() {
        //判断账号是否存在
        Page<UserEntity> page = userService.getList();
        if (ObjectUtils.isEmpty(page) || ObjectUtils.isEmpty(page.toList())) {
            return JSONObject.toJSONString(BaseRespones.ok(),SerializerFeature.WriteNullStringAsEmpty);
        } else {
            List<UserParam> list = new ArrayList<UserParam>();
            for (UserEntity bean : page.toList()) {
                list.add(dataChange(bean));
            }
            return JSONObject.toJSONString(BaseRespones.ok(list),SerializerFeature.WriteNullStringAsEmpty);
        }
    }

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public String login(UserEntity user) {
        UserEntity userBean = userService.getUserByUserName(user.getUsername());
        return JSONObject.toJSONString(responseComponent.generate(StatusEnumeration.S0, HttpStatus.OK, dataChange(userBean)),SerializerFeature.WriteNullStringAsEmpty);
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
    public String changePassword(String username, String oldpassword, String newpassword) {
        Map map = userService.userPasswordChange(username, oldpassword, newpassword);
        if ("ok".equals(map.get("message"))) {
            return JSONObject.toJSONString(BaseRespones.ok(dataChange((UserEntity) map.get("object"))),SerializerFeature.WriteNullStringAsEmpty);
        } else {
            return JSONObject.toJSONString(BaseRespones.failed(map.get("message").toString()),SerializerFeature.WriteNullStringAsEmpty);
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
    public String updateUser(Long id, String sex, String nickname, MultipartFile file) {
        Map map = userService.updateUser(id, sex, nickname, file);
        if ("ok".equals(map.get("message"))) {
            return JSONObject.toJSONString(BaseRespones.ok(dataChange((UserEntity) map.get("object"))), SerializerFeature.WriteNullStringAsEmpty);
        } else {
            return JSONObject.toJSONString(BaseRespones.failed(map.get("message").toString()),SerializerFeature.WriteNullStringAsEmpty);
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