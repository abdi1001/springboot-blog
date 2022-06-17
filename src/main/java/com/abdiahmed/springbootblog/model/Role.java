package com.abdiahmed.springbootblog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "my_roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
  @JoinTable(
          name = "role_authorities",
          joinColumns = @JoinColumn(name = "role_id"),
          inverseJoinColumns = @JoinColumn(name = "authorities_id_id"))
  private List<Authorities> authorities = new ArrayList<>();
  @JsonIgnore
  @ManyToMany(mappedBy = "role",cascade = CascadeType.MERGE)
  private List<User> users = new ArrayList<>();

  public void addAuthority(Authorities authority) {
    authorities.add(authority);
  }
  public void removeAuthority(Authorities authority) {
    authorities.remove(authority);
  }

  public void addUser(User user) {
      users.add(user);}
  public void removeUser(User user) {
      users.remove(user);}
}
