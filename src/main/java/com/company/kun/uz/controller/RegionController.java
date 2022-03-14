package com.company.kun.uz.controller;

import com.company.kun.uz.dto.profile.ProfilJwtDto;
import com.company.kun.uz.dto.RegionDto;
import com.company.kun.uz.enums.ProfileRole;
import com.company.kun.uz.service.RegionService;
import com.company.kun.uz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping("/")
    public ResponseEntity<RegionDto> create(@Valid @RequestBody RegionDto dto,
                                            HttpServletRequest request) {
        ProfilJwtDto profile = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        RegionDto response = regionService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("")
    public ResponseEntity<?> getAll(HttpServletRequest request){
        ProfilJwtDto profile = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        List<RegionDto> response = regionService.allRegion();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id,
                                     HttpServletRequest request){
        ProfilJwtDto profile = JwtUtil.getProfile(request, ProfileRole.ADMIN_ROLE);
        RegionDto response = regionService.getById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id,
                                        HttpServletRequest request){
        ProfilJwtDto profilJwtDto = JwtUtil.getProfile(request,ProfileRole.ADMIN_ROLE);
        String s = regionService.deleteRegionById(id);
        return ResponseEntity.ok(s);
    }
}
