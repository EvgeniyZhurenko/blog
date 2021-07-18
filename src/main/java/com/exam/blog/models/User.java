package com.exam.blog.models;

import javax.validation.constraints.*;

import com.exam.blog.config.anotation.UserBorn;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
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

    @NonNull
    @NotEmpty(message = "Поле \"Ваш логин\" не должно быть пустым")
    @Size(min = 6, max = 12, message = "Username должно быть в диапазоне от 6 до 12 символов")
    private String username;

    @NotEmpty(message = "Поле password не должно быть пустым")
    private String password;

    @NotEmpty(message = "Поле \"Ваше имя\" не должно быть пустым")
    private String first_name;

    @NotEmpty(message = "Поле \"Ваша фамилия\" не должно быть пустым")
    private String last_name;

    @Pattern(regexp = "^(\\+38\\(0)\\d{2}\\)\\d{7}$", message = "Поле \"Ваш телефон\" должно соответствовать формату +38(067)1234567")
    private String phone;

    @NotEmpty(message = "Поле \"Ваш email\" не должно быть пустым")
//    @Email(message = "Email должен быть : username@gmail.com")
    private String email;
    private String city;
    private String country;
    private Boolean ban_user;
    private Boolean enabled;

    private String foto;

    @UserBorn
//    @Past(message = "Дата рождения введена не корректно, она должна быть в прошлом времени")
//    @Min(value = 0, message = "Возраст не должен быть отрицательным")
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
            joinColumns=@JoinColumn (name="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_role"))
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
