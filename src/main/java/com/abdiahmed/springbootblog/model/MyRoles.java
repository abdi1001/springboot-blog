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
public class MyRoles {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  @OneToMany(mappedBy = "roles", fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
  private List<MyAuthorities> authorities = new ArrayList<>();
  @JsonIgnore
  @ManyToMany(mappedBy = "myRoles",cascade = CascadeType.MERGE)
  private List<MyUser> myUsers = new ArrayList<>();

  public void addAuthority(MyAuthorities authority) {
    authorities.add(authority);
  }
  public void removeAuthority(MyAuthorities authority) {
    authorities.remove(authority);
  }

  public void addUser(MyUser user) {myUsers.add(user);}
  public void removeUser(MyUser user) {myUsers.remove(user);}
}
