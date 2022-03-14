package com.company.kun.uz.dto.profile;

import com.company.kun.uz.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class ProfileDTO {
    private Integer id;
    @NotNull
    @NotEmpty(message = "name qani")
    private String name;
    @NotNull
    @NotEmpty(message = "surname qani")
    private String surname;
    @NotNull
    @NotEmpty(message = "login qani")
    private String login;
    private String pswd;
    @NotNull
    @NotEmpty(message = "email qani")
    private String email;
    private String jwt;
    private Integer aid;
    private ProfileRole role;
}
