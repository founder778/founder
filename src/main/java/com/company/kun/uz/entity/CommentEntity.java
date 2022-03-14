package com.company.kun.uz.entity;

import com.company.kun.uz.enums.CommentStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//    id,content,articleId,Profileid,CreatedDate
    private String content;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity article;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;
    private CommentStatus status;
    private Date date;

}
