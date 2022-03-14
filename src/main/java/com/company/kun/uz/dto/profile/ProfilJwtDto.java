package com.company.kun.uz.dto.profile;

import com.company.kun.uz.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProfilJwtDto {
    private Integer id;
    private ProfileRole role;
}
