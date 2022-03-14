package com.company.kun.uz.dto;

import lombok.Data;
import net.bytebuddy.implementation.bind.annotation.Empty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegistrationDto {
    @NotNull
    @NotEmpty(message = "kalla ism qani")
    private String name;
    @NotNull
    @NotEmpty(message = "kalla surname qani")
    private String surname;
    @NotNull
    @NotEmpty(message = "kalla email qani")
    private String email;
    @NotNull
    @NotEmpty(message = "kalla login qani")
    private String login;
    @NotNull
    @NotEmpty(message = "kalla parol qani")
    private String password;
}
