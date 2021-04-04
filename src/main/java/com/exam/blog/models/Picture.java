package com.exam.blog.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 @author Zhurenko Evgeniy
 */


@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@DynamicUpdate
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
    @JoinColumn(name="id_blog", nullable=false)
    Blog blog;
}
