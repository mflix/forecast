package org.mflix.forecast.service;

import java.util.*;
import java.util.stream.Collectors;

import org.mflix.forecast.component.RoleComponent;
import org.mflix.forecast.core.BaseRespones;
import org.mflix.forecast.entity.UserEntity;
import org.mflix.forecast.enumeration.RoleEnumeration;
import org.mflix.forecast.properties.ApplicationProperties;
import org.mflix.forecast.repository.UserRepository;
import org.mflix.forecast.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class UserService implements UserDetailsService {
    private final RoleComponent roleComponent;
    private final ApplicationProperties applicationProperties;
    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(RoleComponent roleComponent, ApplicationProperties applicationProperties,
                       UserRepository userRepository) {
        this.roleComponent = roleComponent;
        this.applicationProperties = applicationProperties;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity model = userRepository.findByUsername(username).orElseThrow();
        if (ObjectUtils.isEmpty(model)) {
            throw new UsernameNotFoundException("user + " + username + "not found.");
        }
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        UserDetails bean = new org.springframework.security.core.userdetails.User(
                model.getUsername(), model.getPassword(), authorities);
        return bean;
    }

    /**
     * 创建会员
     *
     * @param user
     * @return
     */
    public UserEntity createUser(UserEntity user) {
        user.setCreateTimel(new Date());
        user.setUpdateTime(new Date());
        user.setStatus(1);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    public Page<UserEntity> getList() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(0, 20, sort);
        Page<UserEntity> list = userRepository.findAll(pageable);
        return list;
    }

    /**
     * 根据会员名称获取会员
     *
     * @param username
     * @return
     */
    public UserEntity getUserByUserName(String username) {
        UserEntity bean = userRepository.findByUsername(username).orElse(null);
        return bean;
    }

    public Map<String, Object> userPasswordChange(String username, String oldpassword, String newpassword) {
        UserEntity userBean = getUserByUserName(username);
        Map map = new HashMap();
        if (ObjectUtils.isEmpty(userBean)) {
            map.put("message", "用户账号不存在");
            return map;
        }
        if (!passwordEncoder.matches(oldpassword, userBean.getPassword())) {
            map.put("message", "原始密码错误");
            return map;
        }
        userBean.setPassword(passwordEncoder.encode(newpassword));
        userRepository.save(userBean);
        map.put("object", userBean);
        map.put("message", "ok");
        return map;
    }

    public Map<String, Object> updateUser(Long id, String sex, String nickname, MultipartFile file) {
        UserEntity userBean = userRepository.findById(id).orElse(null);
        Map map = new HashMap();
        if (ObjectUtils.isEmpty(userBean)) {
            map.put("message", "用户账号不存在");
            return map;
        }
        userBean.setSex(sex);
        userBean.setNickname(nickname);
        userRepository.save(userBean);
        map.put("object", userBean);
        map.put("message", "ok");
        return map;
    }
}