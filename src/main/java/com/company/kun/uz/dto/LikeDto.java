package com.company.kun.uz.dto;

import com.company.kun.uz.enums.LikeStatus;
import com.company.kun.uz.enums.LikeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LikeDto {
    private Integer id;
    private Integer actionId;
    private LikeStatus status;
    private LikeType type;

}