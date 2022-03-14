package com.company.kun.uz.entity;

import com.company.kun.uz.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String title;
    @Column
    private String content;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "published_date")
    private LocalDateTime publishedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="profil_id")
    private ProfileEntity profile;

    @ManyToOne
    @JoinColumn(name = "region_id")
    private RegionEntity regionEntity;

    @Enumerated(EnumType.STRING)
    @Column
    private ArticleStatus status;


    // sttaus
    // profile
    //

}
