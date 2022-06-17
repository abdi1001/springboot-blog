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
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "my_authorities")
public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "authorities",cascade = CascadeType.DETACH)
    @JsonIgnore
    private Set<Role> role = new HashSet<>();

    public void addRoleToAuthorities(Role roleInput) {
        role.add(roleInput);
    }

    public void removeRoleToAuthorities(Role roleInput) {
        role.remove(roleInput);
    }

}
