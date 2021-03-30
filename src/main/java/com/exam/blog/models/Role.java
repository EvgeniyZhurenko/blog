package com.exam.blog.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 @author Zhurenko Evgeniy
 */


@Setter
@Getter
@Entity
@Table(name = "role")



public class Role implements GrantedAuthority{

    @Id
    @Column(name = "id_role")
    private Long id;

    private String name;

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    Set<User> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return name;
    }
}
