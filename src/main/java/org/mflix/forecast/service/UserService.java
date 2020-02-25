package org.mflix.forecast.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.mflix.forecast.component.RoleComponent;
import org.mflix.forecast.entity.UserEntity;
import org.mflix.forecast.enumeration.RoleEnumeration;
import org.mflix.forecast.repository.UserRepository;
import org.mflix.forecast.view.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleComponent roleComponent;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public UserView createByView(UserView userView) {
        var date = new Date();
        var authorities = roleComponent.generate(RoleEnumeration.GUEST);
        var password = passwordEncoder.encode(userView.getPassword());
        var username = userView.getUsername();

        var userEntity = new UserEntity(true, true, authorities, true, true, password, username, date);
        userEntity = userRepository.save(userEntity);

        userView.setId(userEntity.getId());
        userView.setAuthorities(userEntity.getAuthorities());
        userView.setDate(userEntity.getDate());
        userView.setPassword(null);
        return userView;
    }

    public List<UserView> readAll() {
        return userRepository.findAll().stream().map((userEntity) -> {
            return new UserView(userEntity.getId(), userEntity.getAuthorities(), userEntity.getDate(),
                    userEntity.getUsername());
        }).collect(Collectors.toList());
    }

    public Page<UserView> readAllWithPage(Pageable pageable) {
        var userViewList = userRepository.findAll(pageable).map((userEntity) -> {
            return new UserView(userEntity.getId(), userEntity.getAuthorities(), userEntity.getDate(),
                    userEntity.getUsername());
        }).toList();
        return new PageImpl<>(userViewList, pageable, userViewList.size());
    }

    public UserView readById(long id) {
        var userEntity = userRepository.findById(id).orElseThrow();
        return new UserView(userEntity.getId(), userEntity.getAuthorities(), userEntity.getDate(),
                userEntity.getUsername());
    }

    public UserView updateByIdAndView(long id, UserView userView) {
        var userEntity = userRepository.findById(id).orElseThrow();

        userEntity.setPassword(userView.getPassword());
        userEntity = userRepository.save(userEntity);

        userView.setId(userEntity.getId());
        userView.setAuthorities(userEntity.getAuthorities());
        userView.setDate(userEntity.getDate());
        userView.setPassword(null);
        return userView;
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }
}