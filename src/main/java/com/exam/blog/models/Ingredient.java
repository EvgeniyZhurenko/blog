package com.exam.blog.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 @author Zhurenko Evgeniy
 */

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @Column(name = "id_ingredient")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_blog", nullable=false)
    Blog blog;
}
