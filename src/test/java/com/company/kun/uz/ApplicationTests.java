package com.company.kun.uz;

import com.company.kun.uz.dto.article.ArticleDTO;
import com.company.kun.uz.dto.article.ArticleFilterDto;
import com.company.kun.uz.dto.comment.CommentFilterDto;
import com.company.kun.uz.dto.profile.ProfileDTO;
import com.company.kun.uz.dto.profile.ProfileFilterDto;
import com.company.kun.uz.entity.ArticleEntity;
import com.company.kun.uz.entity.CommentEntity;
import com.company.kun.uz.entity.ProfileEntity;
import com.company.kun.uz.enums.CommentStatus;
import com.company.kun.uz.enums.ProfileRole;
import com.company.kun.uz.enums.ProfileStatus;
import com.company.kun.uz.repository.ArticleCustomRepository;
import com.company.kun.uz.repository.CommentCustomRepository;
import com.company.kun.uz.repository.ProfileCustomRepository;
import com.company.kun.uz.service.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;

@SpringBootTest
class ApplicationTests {
    @Autowired
    ProfileService profileService;
    @Autowired
    ArticleService articleService;
    @Autowired
    AuthService authService;
    @Autowired
    RegionService regionService;
    @Autowired
    ArticleCustomRepository articleCustomRepository;
    @Autowired
    ProfileCustomRepository profileCustomRepository;
    @Autowired
    CommentCustomRepository commentCustomRepository;
    @Autowired
    CommentService commentService;

    @Test

    public void Profile() {
//		authService.UpdateNameByEmail("beki", "Asadbek@gmail.com");
//		authService.UpdateSurnameByEmail("qorailon","Asadbek@gmail.com");
//		ProfileDTO profileDTO =new ProfileDTO();
//		profileDTO.setName("admin");
//		profileDTO.setSurname("adminjon");
//		profileDTO.setEmail("admin@gmail.com");
//		profileDTO.setPswd("71111");
//		profileDTO.setLogin("bekiii");
//		profileDTO.setRole(ProfileRole.ADMIN_ROLE);
//		profileService.create(profileDTO,1);
//		profileService.UpdateProfileByEmail(profileDTO,1);
// profileService.deleteProfilByEmail("Behruz@gmail.com",1);
    }

    @Test
    public void Article() {
//		ArticleDTO articleDTO =new ArticleDTO();
//		articleDTO.setContent("yana maktablarda karantin elon qilinishi mumkin");
//		articleDTO.setProfileId(1);
//		articleDTO.setTitle("covid");
//		articleDTO.setRegionId(1);
//		articleService.create(articleDTO,3);

//		articleService.UpdateArticleByName("Rossiya va Ukraina kelishuv tuzmoqda","Rossiya va Ukraina");

//articleService.DeleteArticleByName("Rossiya va Ukraina");

//		System.out.println(articleService.GetArticleById(5));

//		System.out.println(articleService.getAllArticle());

//		System.out.println(articleService.ArticleByCreatedDate(LocalDateTime.now()));

//		System.out.println(articleService.ArticleByProfile(1));
//		System.out.println(articleService.ArticleByRegion(1));

    }

    @Test
    public void Region() {
//		RegionDto regionDto =new RegionDto();
//		regionDto.setRegion("uzbekistan");
//		regionDto.setName("namangan");
//		regionService.create(regionDto);

//		System.out.println(regionService.UpdateRegionById(1,"Andijon"));

//		regionService.deleteRegionById(1)
//
//		ProfileDTO dto = new ProfileDTO();
//		dto.setName("beki");
//		dto.setSurname("Abdullayev");
//		dto.setRole(ProfileRole.USER_ROLE);
//		System.out.println(dto);
//		profileCustomRepository.filterPreteria(dto);
//		commentCustomRepository.FilterCriteriaBuilder();
//		ArticleFilterDto articleFilterDto = new ArticleFilterDto();
//		articleFilterDto.setProfileId(1);
//		PageImpl<ArticleEntity> articleDTOS = articleService.filterSpec(1, 1, articleFilterDto);
//		System.out.println(articleDTOS.getContent().get(0).getTitle());

//		ProfileFilterDto profileFilterDto = new ProfileFilterDto();
////		profileFilterDto.setName("bek");
//		profileFilterDto.setEmail("erjon5@gmail.com");
//		profileFilterDto.setStatus(ProfileStatus.REGISTRATION);
//		PageImpl<ProfileEntity> profileEntities = profileService.filterSpec(1, 1, profileFilterDto);
//		System.out.println(profileEntities.getContent().get(0).getName());
////		System.out.println(profileEntities.getContent().get(1).getName());


        CommentFilterDto commentFilterDto = new CommentFilterDto();
        commentFilterDto.setPro_id(1);
        PageImpl<CommentEntity> commentEntities = commentService.filterSpec(1, 1, commentFilterDto);
        System.out.println(commentEntities.getContent().get(0).getArticle().getTitle());


    }


}
