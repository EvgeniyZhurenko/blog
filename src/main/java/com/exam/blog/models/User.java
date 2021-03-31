package com.exam.blog.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


/**
 @author Zhurenko Evgeniy
 */


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")

//implements UserDetails

public class User implements UserDetails {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private String city;
    private String country;
    private Boolean ban_user;

    private String foto;
    private LocalDateTime born;
    private String twiter;
    private String facebook;
    private String instagram;
    private String git_hub;

    public User(String username, String password, String born) {
        this.username = username;
        this.password = password;
        this.born = LocalDateTime.parse(born, DateTimeFormatter.ofPattern( "dd/MM/uuuu" ));
    }


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, targetEntity = Role.class)
    Set<Role> roles = new HashSet<>();

    @OneToMany( targetEntity = Blog.class, mappedBy = "user",
                cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Blog> blogs;

    @OneToMany(targetEntity = Comment.class, mappedBy = "user", fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true )
    Set<Comment> comments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
