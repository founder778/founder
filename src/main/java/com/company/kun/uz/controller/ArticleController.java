package com.company.kun.uz.controller;

import com.company.kun.uz.dto.article.ArticleDTO;
import com.company.kun.uz.dto.article.ArticleFilterDto;
import com.company.kun.uz.dto.profile.ProfilJwtDto;
import com.company.kun.uz.entity.ArticleEntity;
import com.company.kun.uz.enums.ProfileRole;
import com.company.kun.uz.service.ArticleService;
import com.company.kun.uz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody ArticleDTO dto,
                                             HttpServletRequest request) {
           ProfilJwtDto jwtDto = JwtUtil.getProfile(request,ProfileRole.MODERATOR_ROLE);
           ArticleDTO response = articleService.create(dto, jwtDto.getId());
           return ResponseEntity.ok(response);

    }

    @PutMapping("/publish/{id}")
    public ResponseEntity<?> publish(@PathVariable("id") Integer id,
                                     HttpServletRequest request){
        return ResponseEntity.ok(request);
    }

    @PostMapping ("/{size}/{page}")
    public ResponseEntity<?> getAll(@PathVariable("size") Integer size,
                                    @PathVariable("page") Integer page,
                                    @RequestBody ArticleFilterDto articleFilterDto){
        PageImpl<ArticleEntity> articleEntities = articleService.filterSpec(page, size, articleFilterDto);
        return ResponseEntity.ok(articleEntities);
    }

}
