package org.mflix.forecast.view;

import java.util.Date;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
public class UserView {
    private long id;
    private Set<GrantedAuthority> authorities;
    private Date date;
    @NotBlank
    private String password;
    @NotBlank
    private String username;

    public UserView() {
    }

    public UserView(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public UserView(long id, Set<GrantedAuthority> authorities, Date date, @NotBlank String username) {
        this.id = id;
        this.authorities = authorities;
        this.date = date;
        this.username = username;
    }
}