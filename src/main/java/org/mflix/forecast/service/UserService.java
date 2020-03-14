package org.mflix.forecast.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.mflix.forecast.component.RoleComponent;
import org.mflix.forecast.entity.UserEntity;
import org.mflix.forecast.enumeration.RoleEnumeration;
import org.mflix.forecast.properties.ApplicationProperties;
import org.mflix.forecast.repository.UserRepository;
import org.mflix.forecast.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        UserEntity model=userRepository.findByUsername(username).orElseThrow();
        if(ObjectUtils.isEmpty(model)) {
            throw new UsernameNotFoundException("user + " + username + "not found.");
        }
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        UserDetails bean=new org.springframework.security.core.userdetails.User(
                model.getUsername(),model.getPassword(),authorities);
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
}