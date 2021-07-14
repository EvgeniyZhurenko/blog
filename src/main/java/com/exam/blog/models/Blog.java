package com.exam.blog.models;


import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;


/**
 @author Zhurenko Evgeniy
 */


@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@DynamicUpdate
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
    private LocalDate date_create_blog;
    private Float rating;
    private Boolean ban_blog;

    @OneToMany(mappedBy = "blog",
                cascade = CascadeType.ALL,
                fetch = FetchType.LAZY)
    List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "blog",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY)
    List<Comment> comments;

    @OneToMany(mappedBy = "blog",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY )
    List<Picture> pictures;

    @ManyToOne
    @JoinColumn(name="id_user", referencedColumnName="id_user", nullable=false)
    User user;

    public List<String> compareDate(LocalDate date_creat_blog){

        List<String> listData = new ArrayList<>();
        long res = ChronoUnit.DAYS.between(date_creat_blog, LocalDate.now());

        if(res > 30) {
            if(res > 365) {
                res = ChronoUnit.YEARS.between(date_creat_blog, LocalDate.now());
                if(res > 4) {
                    listData.add(String.valueOf(LocalDate.of(0, 1, 1)
                            .plusYears(res).getYear()));
                    listData.add("л назад");
                } else {
                    listData.add(String.valueOf(LocalDate.of(0, 1, 1)
                            .plusYears(res).getYear()));
                    listData.add("г назад");
                }
                return listData;
            }
           res = ChronoUnit.MONTHS.between(date_creat_blog, LocalDate.now());
           listData.add(String.valueOf(LocalDate.of(0,1,1).plusMonths(res - 1).getMonthValue()));
           listData.add("мес назад");
           return listData;
        }

        listData.add(String.valueOf(LocalDate.of(0,1,1)
                .plusDays(res).getDayOfMonth()));
        listData.add("дн назад");
        return listData;
    }

}
