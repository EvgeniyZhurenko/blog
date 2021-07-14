package com.exam.blog.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 @author Zhurenko Evgeniy
 */


@Getter
@Setter
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
