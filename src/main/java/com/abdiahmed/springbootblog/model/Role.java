package com.abdiahmed.springbootblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "my_roles")
public class  Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinTable(
      name = "role_authorities",
      joinColumns = @JoinColumn(name = "my_roles_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "my_authorities_id", referencedColumnName = "id"))
  private Set<Authorities> authorities = new HashSet<>();

  @JsonIgnore
  @ManyToMany(mappedBy = "role")
  private Set<User> users = new HashSet<>();

  public void addAuthority(Authorities authority) {
    authorities.add(authority);
  }

  public void removeAuthority(Authorities authority) {
    authorities.remove(authority);
  }

  public void addUser(User user) {
    users.add(user);
  }

  public void removeUser(User user) {
    users.remove(user);
  }
}
