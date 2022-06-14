package com.abdiahmed.springbootblog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "my_user")
public class MyUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String username;
  private String email;
  private String password;

  public MyUser() {
    this.myRoles = new HashSet<>();
    this.posts = new HashSet<>();
  }

  @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<MyRoles> myRoles = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true,fetch = FetchType.EAGER)
  private Set<Post> posts = new HashSet<>();

  public void addRoleToUser(MyRoles role) {
    myRoles.add(role);
  }

  public void removeRoleToUser(MyRoles role) {
    myRoles.remove(role);
  }

  public void addPostForUser(Post post) {
    posts.add(post);
  }

  public void removePostForUser(Post post) {
    posts.remove(post);
  }



  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialsNonExpired;
  private boolean isEnabled;


}
