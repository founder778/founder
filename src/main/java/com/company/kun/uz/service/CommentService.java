package com.company.kun.uz.service;

import com.company.kun.uz.dto.article.ArticleDTO;
import com.company.kun.uz.dto.article.ArticleFilterDto;
import com.company.kun.uz.dto.comment.CommentDto;
import com.company.kun.uz.dto.comment.CommentFilterDto;
import com.company.kun.uz.entity.ArticleEntity;
import com.company.kun.uz.entity.CommentEntity;
import com.company.kun.uz.entity.ProfileEntity;
import com.company.kun.uz.enums.ArticleStatus;
import com.company.kun.uz.enums.CommentStatus;
import com.company.kun.uz.expections.BadRequestExeption;
import com.company.kun.uz.expections.ItemNotFoundExeption;
import com.company.kun.uz.repository.ArticleRepository;
import com.company.kun.uz.repository.CommentCustomRepository;
import com.company.kun.uz.repository.CommentRepository;
import com.company.kun.uz.spec.ArticleSpecification;
import com.company.kun.uz.spec.CommentSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    ProfileService profileService;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    CommentCustomRepository commentCustomRepository;

    public CommentDto create(CommentDto dto,Integer userId) {
        ProfileEntity profile = profileService.getByid(dto.getProfileId());
        Optional<ArticleEntity> article = articleRepository.findById(dto.getArticleId());
        if (profile == null || !article.isPresent()) {
            throw new ItemNotFoundExeption("not found");
        }
        CommentEntity comment = new CommentEntity();
        comment.setContent(dto.getContent());
        comment.setArticle(article.get());
        comment.setProfile(profile);
        comment.setDate(new Date());
        commentRepository.save(comment);
        dto.setId(comment.getId());
        return dto;

    }

    public CommentDto getById(Integer id,Integer userId) {
        Optional<CommentEntity> coment = commentRepository.findById(id);
        if (!coment.isPresent()) {
            throw new ItemNotFoundExeption("not found");
        }
        else if(!coment.get().getProfile().getId().equals(userId)){
            throw new BadRequestExeption("not owner");
        }
        return todto(coment.get());

    }

    public void delete(Integer userid, Integer comentid) {
        Optional<CommentEntity> coment = commentRepository.findById(comentid);
        if (!coment.isPresent()) {
            throw new ItemNotFoundExeption("not found");
        }
        else if(!coment.get().getProfile().getId().equals(userid)){
            throw new BadRequestExeption("not owner");
        }
        commentRepository.deleteById(comentid);

    }

    public void Update(Integer userid, CommentDto commentDto, Integer commentid) {
       Optional <CommentEntity> coment = commentRepository.findById(commentid);
        if (!coment.isPresent()) {
            throw new ItemNotFoundExeption("not found");
        }
        else if(!coment.get().getProfile().getId().equals(userid)){
            throw new BadRequestExeption("not owner");
        }
       coment.get().setContent(commentDto.getContent());
        commentRepository.save(coment.get());
    }

    public List<CommentDto> getAllComentById(Integer userid) {
        List<CommentEntity> coments = commentRepository.findallcomentById(userid);
        List<CommentDto> c = new LinkedList<>();
        for (CommentEntity coment : coments) {
            c.add(todto(coment));

        }
        return c;
    }


    public CommentDto todto(CommentEntity comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setContent(comment.getContent());
        commentDto.setArticleId(comment.getProfile().getId());
        commentDto.setProfileId(comment.getProfile().getId());
        return commentDto;
    }
    public PageImpl<CommentDto> filter(int page, int size, CommentFilterDto filterDTO) {
        PageImpl<CommentEntity> entityPage = commentCustomRepository.filter(page,size,filterDTO);

        List<CommentDto> commentDTOList = entityPage.stream().map(commentEntity -> {
            CommentDto dto = new CommentDto();
            dto.setId(commentEntity.getId());
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(commentDTOList, entityPage.getPageable(), entityPage.getTotalElements());
    }

    public PageImpl<CommentEntity> filterSpec(int page, int size, CommentFilterDto filterDTO){
        Specification<CommentEntity> spec = null;
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.ASC,"id");
        if (filterDTO.getStatus() != null) {
            spec = Specification.where(CommentSpecification.status(filterDTO.getStatus()));
        } else {
            spec = Specification.where(CommentSpecification.status(CommentStatus.ARTICLE));
        }

        if (filterDTO.getPro_id() != null) {
            spec.and(CommentSpecification.equal("profile_id",filterDTO.getPro_id()));
        }
        if (filterDTO.getArt_id()!= null) {
            spec.and(CommentSpecification.equal("article_id",filterDTO.getArt_id()));
        }
        Page<CommentEntity> articlePage = commentRepository.findAll(spec,pageable);
        return (PageImpl<CommentEntity>) articlePage;
    }
}
