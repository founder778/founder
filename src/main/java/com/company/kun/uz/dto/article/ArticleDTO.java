package com.company.kun.uz.dto.article;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleDTO {
    private Integer id;
    @NotNull
    @NotEmpty(message = "title qani")
    private String title;
    @NotNull
    @NotEmpty(message = "content qani")
    private String content;

    private Integer profileId;
    private Integer regionId;

    // status

    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;

}
