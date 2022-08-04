package com.artcources.artistica.model.entity;

import com.artcources.artistica.model.enums.UserRoleEnum;

import javax.persistence.*;

@Entity
@Table(name ="user_roles")
public class UserRoleEntity extends BaseEntity{


  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private UserRoleEnum name;

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

  public UserRoleEnum getName() {
    return name;
  }

  public UserRoleEntity setName(UserRoleEnum userRole) {
    this.name = userRole;
    return this;
  }
}
