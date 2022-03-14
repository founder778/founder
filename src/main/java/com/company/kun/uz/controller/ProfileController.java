package com.company.kun.uz.controller;


import com.company.kun.uz.dto.article.ArticleFilterDto;
import com.company.kun.uz.dto.profile.ProfilJwtDto;
import com.company.kun.uz.dto.profile.ProfileDTO;
import com.company.kun.uz.dto.profile.ProfileFilterDto;
import com.company.kun.uz.entity.ArticleEntity;
import com.company.kun.uz.entity.ProfileEntity;
import com.company.kun.uz.enums.ProfileRole;
import com.company.kun.uz.service.ProfileService;
import com.company.kun.uz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody ProfileDTO dto,
                                             HttpServletRequest request) {
        ProfilJwtDto profile = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        ProfileDTO response = profileService.create(dto);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/create/{id}")
    public ResponseEntity<?> createProfile(@RequestBody ProfileDTO dto,@PathVariable("id") Integer id){
        ProfileDTO profileDTO = profileService.create(dto);
        if(profileDTO == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(profileDTO);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id ,
                                     HttpServletRequest request){
        ProfilJwtDto profilJwtDto = JwtUtil.getProfile(request);
        ProfileEntity response = profileService.getByid(id);
        return ResponseEntity.ok(response);

    }
    @GetMapping("/delete/{email}")
    public ResponseEntity<?> deleteById(@PathVariable("email") String email,
                                        HttpServletRequest request){
        ProfilJwtDto profilJwtDto = JwtUtil.getProfile(request,ProfileRole.ADMIN_ROLE);
        String s = profileService.deleteProfilByEmail(email);
        return ResponseEntity.ok(s);
    }

    @PostMapping("/{size}/{page}")
    public ResponseEntity<?> getAll(@PathVariable("size") Integer size,
                                    @PathVariable("page") Integer page,
                                    @RequestBody ProfileFilterDto profileFilterDto){
        PageImpl<ProfileEntity> Entities = profileService.filterSpec(page, size, profileFilterDto);
        return ResponseEntity.ok(Entities);
    }

    // registration
    // authorization
}
