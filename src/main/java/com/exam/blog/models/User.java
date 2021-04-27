package com.exam.blog.models;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


/**
 @author Zhurenko Evgeniy
 */


@Getter
@Setter
@NoArgsConstructor
@Entity
@DynamicUpdate
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Table(name = "user")

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

    @Column(name = "activation_code")
    private String activationCode;

    private String foto;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Date born;

    private String twiter;
    private String facebook;
    private String instagram;
    private String git_hub;

    public User(String username) {
        this.username = username;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    @ManyToMany
    @JoinTable (name="user_roles",
            joinColumns=@JoinColumn (name="id_role"),
            inverseJoinColumns=@JoinColumn(name="id_user"))
    Set<Role> roles ;

    @OneToMany(mappedBy = "user",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY )
    List<Blog> blogs;

    @OneToMany(mappedBy = "user",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    List<Comment> comments;

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
