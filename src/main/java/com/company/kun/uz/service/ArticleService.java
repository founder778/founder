package com.company.kun.uz.service;


import com.company.kun.uz.dto.article.ArticleDTO;
import com.company.kun.uz.dto.article.ArticleFilterDto;
import com.company.kun.uz.entity.ArticleEntity;
import com.company.kun.uz.entity.CommentEntity;
import com.company.kun.uz.entity.ProfileEntity;
import com.company.kun.uz.entity.RegionEntity;
import com.company.kun.uz.enums.ArticleStatus;
import com.company.kun.uz.expections.BadRequestExeption;
import com.company.kun.uz.repository.ArticleCustomRepository;
import com.company.kun.uz.repository.ArticleRepository;
import com.company.kun.uz.repository.RegionRepository;
import com.company.kun.uz.spec.ArticleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.swing.text.html.HTMLDocument;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    ProfileService profileService;
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    ArticleCustomRepository articleCustomRepository;

    public ArticleDTO create(ArticleDTO dto, Integer userid) {
        ProfileEntity profile = profileService.getByid(userid);
        if(dto.getTitle()==null || dto.getTitle().isEmpty()){
            throw new BadRequestExeption("tittle can not null");
        }
        if (!userid.equals(3)) {
            return null;
        }
        Optional<RegionEntity> region = regionRepository.findById(dto.getRegionId());
        ArticleEntity articleEntity = new ArticleEntity();
        CommentEntity comment = new CommentEntity();
        articleEntity.setContent(dto.getContent());
        articleEntity.setTitle(dto.getTitle());
        articleEntity.setCreatedDate(LocalDateTime.now());
        articleEntity.setProfile(profile);
        articleEntity.setRegionEntity(region.get());
        articleRepository.save(articleEntity);
        dto.setId(articleEntity.getId());

        return dto;
    }

    public String UpdateArticleByName(String content, String name) {
        Optional<ArticleEntity> article = Optional.ofNullable(articleRepository.findByTitle(name));
        if (!article.isPresent()) {
            return "not found";
        }

        articleRepository.UpdateArticleByName(content, name);
        return "succesfully";
    }

    public String DeleteArticleByName(String title) {
        Optional<ArticleEntity> article = Optional.ofNullable(articleRepository.findByTitle(title));
        if (!article.isPresent()) {
            return "not found";
        }
        articleRepository.DeleteArticleByName(title);
        return "succesfully";
    }

    public ArticleDTO GetArticleById(Integer id) {
        Optional<ArticleEntity> article = articleRepository.findById(id);
        if (article.isPresent()) {
            return toDto(article.get());

        }
        return null;
    }

    public List<ArticleDTO> getAllArticle() {
        List<ArticleDTO> articles = new LinkedList<>();
        List<ArticleEntity> all = articleRepository.findAll();
        for (ArticleEntity a : all) {
            articles.add(toDto(a));
        }
        return articles;
    }


    public List<ArticleDTO> ArticleByCreatedDate(LocalDateTime date) {
        List<ArticleEntity> entities = articleRepository.ArticleByCreateddate(date);
        List<ArticleDTO> todolist = new LinkedList<>();
        for (ArticleEntity entity : entities) {
            todolist.add(toDto(entity));
        }
        return todolist;
    }

    public List<ArticleDTO> ArticleByProfile(Integer id) {
        ProfileEntity profile = profileService.getByid(id);
        List<ArticleDTO> todolist = new LinkedList<>();
        List<ArticleEntity> entities = articleRepository.ArticleByProfile(profile);
        for (ArticleEntity entity : entities) {
            todolist.add(toDto(entity));
        }
        return todolist;
    }

    public List<ArticleDTO> ArticleByRegion(Integer id) {
        Optional<RegionEntity> region = regionRepository.findById(id);
        List<ArticleDTO> articles = new LinkedList<>();
        if (!region.isPresent()) {
            return null;
        }
        List<ArticleEntity> entities = articleRepository.ArticleByregion(region.get());
        for (ArticleEntity entity : entities) {
            articles.add(toDto(entity));

        }
        return articles;
    }


    public ArticleDTO toDto(ArticleEntity entity) {
        ArticleDTO dTO = new ArticleDTO();
        dTO.setId(entity.getId());
        dTO.setTitle(entity.getTitle());
        dTO.setContent(entity.getContent());
        dTO.setCreatedDate(entity.getCreatedDate());
        dTO.setProfileId(entity.getProfile().getId());
        return dTO;
    }

    public PageImpl<ArticleDTO> filter(int page, int size, ArticleFilterDto filterDTO) {
        PageImpl<ArticleEntity> entityPage = articleCustomRepository.filter(page,size,filterDTO);

        List<ArticleDTO> articleDTOList = entityPage.stream().map(articleEntity -> {
            ArticleDTO dto = new ArticleDTO();
            dto.setId(articleEntity.getId());
            //
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(articleDTOList, entityPage.getPageable(), entityPage.getTotalElements());
    }


    public PageImpl<ArticleDTO> filterSpe(int page, int size, ArticleFilterDto filterDTO){
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        Specification<ArticleEntity> title = ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("title"), filterDTO.getTitle());
        });

        Specification<ArticleEntity> idSpec = ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("id"), filterDTO.getArticleId());
        });

        Specification<ArticleEntity> spec = Specification.where(title);
        spec.and(idSpec);



        Page<ArticleEntity> articlePage = articleRepository.findAll(spec, pageable);
        System.out.println(articlePage.getTotalElements());
        return null;
    }

    public PageImpl<ArticleEntity> filterSpec(int page, int size, ArticleFilterDto filterDTO){
        Specification<ArticleEntity> spec = null;
        Pageable pageable = PageRequest.of(page,size,Sort.Direction.ASC,"id");
        if (filterDTO.getArticleStatus() != null) {
            spec = Specification.where(ArticleSpecification.status(filterDTO.getArticleStatus()));
        } else {
            spec = Specification.where(ArticleSpecification.status(ArticleStatus.PUBLISH));
        }

        if (filterDTO.getTitle() != null) {
            spec.and(ArticleSpecification.title(filterDTO.getTitle()));
        }
        if (filterDTO.getArticleId() != null) {
            spec.and(ArticleSpecification.equal("id", filterDTO.getArticleId()));
        }
        if (filterDTO.getProfileId() != null) {
            spec.and(ArticleSpecification.equal("profile.id", filterDTO.getProfileId()));
        }
        Page<ArticleEntity> articlePage = articleRepository.findAll(spec,pageable);
        return (PageImpl<ArticleEntity>) articlePage;
    }

}
