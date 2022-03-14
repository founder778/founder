package com.company.kun.uz.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegionDto {
    private Integer id;
    @NotNull
    @NotEmpty(message = "name qani")
    private String name;
    private String region;
}
