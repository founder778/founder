package com.company.kun.uz.controller;

import com.company.kun.uz.dto.article.ArticleFilterDto;
import com.company.kun.uz.dto.comment.CommentDto;
import com.company.kun.uz.dto.comment.CommentFilterDto;
import com.company.kun.uz.dto.profile.ProfilJwtDto;
import com.company.kun.uz.entity.ArticleEntity;
import com.company.kun.uz.entity.CommentEntity;
import com.company.kun.uz.service.CommentService;
import com.company.kun.uz.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/coment")
public class CommentController {
    @Autowired
    CommentService commentService;
    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody CommentDto commentDto,
                                    HttpServletRequest request){
        ProfilJwtDto jwtDto = JwtUtil.getProfile(request);
        CommentDto comment = commentService.create(commentDto,jwtDto.getId());
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Integer id ,
                                     HttpServletRequest request){
        ProfilJwtDto profilJwtDto = JwtUtil.getProfile(request);
        CommentDto response = commentService.getById(id,profilJwtDto.getId());
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer comentId,
                                     HttpServletRequest request){
        ProfilJwtDto profilJwtDto = JwtUtil.getProfile(request);
        commentService.delete(comentId,profilJwtDto.getId());
        return ResponseEntity.ok().build();

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody CommentDto commentDto,
                                    @PathVariable("id") Integer id,
                                    HttpServletRequest request){
        ProfilJwtDto profilJwtDto = JwtUtil.getProfile(request);
        commentService.Update(id,commentDto,profilJwtDto.getId());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/all/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                    HttpServletRequest request){
        ProfilJwtDto profilJwtDto = JwtUtil.getProfile(request);
        List<CommentDto> allComentById = commentService.getAllComentById(id);
        return ResponseEntity.ok(allComentById);
    }

    @PostMapping("/{size}/{page}")
    public ResponseEntity<?> getAll(@PathVariable("size") Integer size,
                                    @PathVariable("page") Integer page,
                                    @RequestBody CommentFilterDto commentFilterDto){
        PageImpl<CommentEntity> Entities = commentService.filterSpec(page, size, commentFilterDto);
        return ResponseEntity.ok(Entities);
    }

}
