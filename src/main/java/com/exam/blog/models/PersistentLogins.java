package com.exam.blog.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


/**
* @author Zhurenko Evgeniy
*/


@Setter
@Getter
@Entity
@Table(name = "persistent_logins")
public class PersistentLogins {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    String series;
    @NotNull
    String username;
    @NotNull
    String token;
    @NotNull
    Date last_used;
}
