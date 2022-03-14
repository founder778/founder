package com.company.kun.uz.dto.comment;

import com.company.kun.uz.enums.CommentStatus;
import lombok.Data;

import java.time.LocalDate;
@Data
public class CommentFilterDto {
    private Integer com_id;
    private Integer pro_id;
    private Integer art_id;
    private CommentStatus status;


    private LocalDate fromDate;
    private LocalDate toDate;
}
