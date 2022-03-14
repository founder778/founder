package com.company.kun.uz.repository;

import com.company.kun.uz.entity.ArticleEntity;
import com.company.kun.uz.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<CommentEntity,Integer>, JpaSpecificationExecutor<CommentEntity> {
    @Query("select c from CommentEntity c where c.id =?1 and c.profile.id=?2")
    CommentEntity findComentIdandProfileId(Integer cid,Integer pid);
    @Query("select c from CommentEntity c where c.profile.id=?1")
    List<CommentEntity> findallcomentById(Integer pId);
}
