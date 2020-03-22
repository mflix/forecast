package org.mflix.forecast.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.mflix.forecast.component.ResponseComponent;
import org.mflix.forecast.core.BaseRespones;
import org.mflix.forecast.entity.UserEntity;
import org.mflix.forecast.param.UserParam;
import org.mflix.forecast.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service("authenticationSuccessHandler")
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final ResponseComponent responseComponent;
    private final UserService userService;

    @Autowired
    public AuthenticationSuccessHandler(ResponseComponent responseComponent, UserService userService) {
        this.responseComponent = responseComponent;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException {
        UserEntity userBean = userService.getUserByUserName(request.getParameter("username"));
        logger.info("User: " + request.getParameter("username") + " Login successfully.");
        this.returnJson(response, dataChange(userBean));

    }

    private void returnJson(HttpServletResponse response, UserParam object) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONObject.toJSONString(BaseRespones.ok(object),SerializerFeature.WriteNullStringAsEmpty));
    }

    public UserParam dataChange(UserEntity user) {
        UserParam model = new UserParam();
        model.setId(String.valueOf(user.getId()));
        model.setAvatar(user.getAvatarUrl());
        model.setEmail(user.getEmail());
        model.setNickname(user.getNickname());
        model.setScore(0l);
        model.setSex(user.getSex());
        if ("1".equals(user.getSex())) {
            model.setStatus("已激活");
        } else {
            model.setStatus("未激活");
        }
        return model;
    }
}
