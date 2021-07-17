package com.exam.blog.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 @author Zhurenko Evgeniy
 */

@Getter
@Setter
@Entity
@Table(name = "activation_code")
public class ActivationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code")
    private String code;
    private String username;
    private String password;
    private String email;
    private String phone;
}
