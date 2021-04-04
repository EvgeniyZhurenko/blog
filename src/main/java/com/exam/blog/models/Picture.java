package com.exam.blog.models;

import lombok.Data;

import javax.persistence.*;

/**
 @author Zhurenko Evgeniy
 */


@Data
@Entity
@Table(name = "picture")
public class Picture {

    @Id
    @Column(name = "id_picture")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url_image;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_blog")
    Blog blog;
}
