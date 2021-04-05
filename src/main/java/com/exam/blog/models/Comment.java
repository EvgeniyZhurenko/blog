package com.exam.blog.models;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 @author Zhurenko Evgeniy
 */


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@DynamicUpdate
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



    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name="id_blog", nullable=false)
    Blog blog;

    @ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.PERSIST)
    @JoinColumn(name="id_user", nullable=false)
    User user;

}
