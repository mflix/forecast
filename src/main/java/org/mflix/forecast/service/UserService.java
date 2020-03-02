package org.mflix.forecast.service;

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
        return userRepository.findByUsername(username).orElseThrow();
    }
}