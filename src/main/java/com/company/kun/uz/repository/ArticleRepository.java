package com.company.kun.uz.repository;


import com.company.kun.uz.entity.ArticleEntity;
import com.company.kun.uz.entity.ProfileEntity;
import com.company.kun.uz.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.w3c.dom.ls.LSException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> , JpaSpecificationExecutor<ArticleEntity> {
    ArticleEntity findByTitle(String title);

    @Modifying
    @Transactional
    @Query("update ArticleEntity a set a.content = ?1 where a.title =?2 ")
    int UpdateArticleByName(String content,String name);



    @Modifying
    @Transactional
    @Query("delete ArticleEntity a where a.title = ?1")
    void DeleteArticleByName(String name);

    @Query("select a from ArticleEntity a where a.createdDate =?1")
    List<ArticleEntity>ArticleByCreateddate(LocalDateTime date);

    @Query("select a from ArticleEntity a where a.profile = ?1")
    List<ArticleEntity> ArticleByProfile(ProfileEntity profile);

    @Query("select a from ArticleEntity a where a.regionEntity=?1")
    List<ArticleEntity>ArticleByregion(RegionEntity regionEntity);

}
