package com.exam.blog.models;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 @author Zhurenko Evgeniy
 */


@Data
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @Column(name = "id_comment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private LocalDateTime dateCreateComment;
    private Boolean banComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_blog")
    Blog blog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    User user;

}
