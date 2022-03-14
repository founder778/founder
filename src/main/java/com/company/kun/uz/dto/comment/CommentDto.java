package com.company.kun.uz.dto.comment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDto {
    private Integer id;
    @NotNull
    @NotEmpty(message = "content qani")
    private String content;
    private Integer articleId;



    private Integer profileId;
}
