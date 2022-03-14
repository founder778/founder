package com.company.kun.uz.dto.article;

import com.company.kun.uz.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ArticleFilterDto {
    private String title;
    private Integer profileId;
    private Integer articleId;
    private ArticleStatus articleStatus;

    private LocalDate fromDate;
    private LocalDate toDate;
}
