package com.artcources.artistica.model.entity;

import com.artcources.artistica.model.enums.UserRoleEnum;

import javax.persistence.*;

@Entity
@Table(name ="user_roles")
public class UserRoleEntity extends BaseEntity{


  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRoleEnum userRole;

  @Column(columnDefinition = "TEXT", nullable = true)
  private String description;

  public UserRoleEntity() {
  }

  public String getDescription() {
    return description;
  }

  public UserRoleEntity setDescription(String description) {
    this.description = description;
    return this;
  }

  public UserRoleEnum getUserRole() {
    return userRole;
  }

  public UserRoleEntity setUserRole(UserRoleEnum userRole) {
    this.userRole = userRole;
    return this;
  }
}
