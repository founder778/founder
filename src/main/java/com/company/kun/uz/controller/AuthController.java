package com.company.kun.uz.controller;

import com.company.kun.uz.dto.profile.ProfileDTO;
import com.company.kun.uz.dto.RegistrationDto;
import com.company.kun.uz.dto.auth.AuthorizationDTO;
import com.company.kun.uz.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Api( tags = "Auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @ApiOperation(value = "Login method",notes = "Sekinroq",nickname = "Nikname")
    public ResponseEntity<ProfileDTO> auth(@RequestBody AuthorizationDTO dto) {
        ProfileDTO response = authService.authorization(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> create(@Valid @RequestBody RegistrationDto dto){
        RegistrationDto registrationDto = authService.create(dto);
        return ResponseEntity.ok(registrationDto);
    }

    @GetMapping("/verification/{jwt}")
    public ResponseEntity<?> ver(@PathVariable("jwt") String id){
        authService.verification(id);
        System.out.println(id);
        return ResponseEntity.ok().build();
    }



//    @GetMapping("update/{email}/{name}")
//    public ResponseEntity<?> updateName(@PathVariable("email") String email,@PathVariable("name") String name){
//        String profile = authService.UpdateNameByEmail(name, email);
//        return ResponseEntity.ok(profile);
//    }

}
