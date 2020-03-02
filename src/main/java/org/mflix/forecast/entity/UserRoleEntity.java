package org.mflix.forecast.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class UserRoleEntity {
    @Id
    @GeneratedValue
    private long id;
    private long userId;
    private long roleId;
}