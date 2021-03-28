package com.exam.blog.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 @author Zhurenko Evgeniy
 */


@Data
@Entity
@NoArgsConstructor
@Table(name = "role")



public class Role implements GrantedAuthority{

    @Id
    @Column(name = "id_role")
    private Long id;

    private String name;

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
