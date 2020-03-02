package org.mflix.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.Data;

@Data
@Entity
public class RoleEntity implements GrantedAuthority {
    private static final long serialVersionUID = 4872026358112358618L;

    @Id
    @GeneratedValue
    private long id;
    private String authority;
}