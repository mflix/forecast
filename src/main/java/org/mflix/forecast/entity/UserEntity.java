package org.mflix.forecast.entity;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
@Entity
public class UserEntity implements UserDetails {
    private static final long serialVersionUID = 7286614557063497011L;
    @Id
    @GeneratedValue
    private long id;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    @ElementCollection
    private Set<GrantedAuthority> authorities;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private String password;
    @Column(unique = true)
    private String username;
    private Date date;

    private String email;
    private String nickname;
    private String gender;
    private String avatarUrl;

    public UserEntity() {
    }

    public UserEntity(boolean accountNonExpired, boolean accountNonLocked, Set<GrantedAuthority> authorities,
            boolean credentialsNonExpired, boolean enabled, String password, String username, Date date) {
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = authorities;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
        this.password = password;
        this.username = username;
        this.date = date;
    }
}