package com.exam.blog.models;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;


/**
 @author Zhurenko Evgeniy
 */


@Data
@Entity
@Table(name = "blog")
public class Blog {


    @Id
    @Column(name = "id_blog")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String full_text;
    private String announcement;
    private LocalDateTime date_create_blog;
    private Integer rating;
    private Boolean ban_blog;

    @OneToMany(targetEntity = Comment.class, mappedBy = "blog",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Comment> comments;

    @OneToMany(targetEntity = Picture.class, mappedBy = "blog",
               fetch = FetchType.LAZY,
               cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Picture> pictures;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    User user;
}
