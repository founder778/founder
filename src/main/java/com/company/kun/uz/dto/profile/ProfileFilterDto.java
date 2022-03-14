package com.company.kun.uz.dto.profile;

import com.company.kun.uz.enums.ProfileRole;
import com.company.kun.uz.enums.ProfileStatus;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ProfileFilterDto {
    private String name;
    private String surname;
    private String email;
    private ProfileRole role;
    private ProfileStatus status;
    private Integer pro_id;

    private LocalDate fromDate;
    private LocalDate toDate;
}
