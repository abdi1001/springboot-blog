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
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;
  private String username;
  private String email;
  private String password;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "my_user_id"),
      inverseJoinColumns = @JoinColumn(name = "my_roles_id"))
  private Set<Role> role = new HashSet<>();

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<Post> posts = new HashSet<>();

  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialsNonExpired;
  private boolean isEnabled;

  public User() {}

  public void addRoleToUser(Role roleInput) {
    role.add(roleInput);
  }

  public void removeRoleFromUser(Role roleInput) {
    role.remove(roleInput);
  }

  public void addPostForUser(Post post) {
    posts.add(post);
  }

  public void removePostForUser(Post post) {
    posts.remove(post);
  }
}
