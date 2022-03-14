package com.company.kun.uz.repository;

import com.company.kun.uz.dto.article.ArticleFilterDto;
import com.company.kun.uz.dto.comment.CommentFilterDto;
import com.company.kun.uz.entity.ArticleEntity;
import com.company.kun.uz.entity.CommentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CommentCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PageImpl filter (int page, int size, CommentFilterDto filterDTO){
        Map<String, Object> params = new HashMap<>();
//
        StringBuilder builder = new StringBuilder("SELECT c FROM CommentEntity c ");
        StringBuilder builderCount = new StringBuilder("SELECT count(c) FROM CommentEntity c ");

        if (filterDTO.getCom_id() != null) {
            builder.append("Where c.id = " + filterDTO.getCom_id());
            builderCount.append("Where c.id = " + filterDTO.getCom_id());
        } else {
            builder.append("Where id > 0");
            builderCount.append("Where id > 0");
        }

        if (filterDTO.getPro_id() != null) {
            builder.append("and c.profile_id = " + filterDTO.getPro_id());
            builderCount.append("and c.profile_id = " + filterDTO.getPro_id());
            params.put("pro_id", filterDTO.getPro_id());
        }
        if (filterDTO.getArt_id() != null) {
            builder.append("and c.article_id = " + filterDTO.getArt_id());
            builderCount.append("and c.article_id = " + filterDTO.getArt_id());
            params.put("article", filterDTO.getArt_id());
        }

        if (filterDTO.getFromDate() != null) {
            builder.append(" and c.date >=:fromDate");
            builderCount.append(" and c.date >=:fromDate");
            params.put("fromDate", LocalDateTime.of(filterDTO.getFromDate(), LocalTime.MIN));
        }

        if (filterDTO.getToDate() != null) {
            builder.append(" and c.date <=:toDate");
            builderCount.append(" and c.date <=:toDate");
            params.put("toDate", LocalDateTime.of(filterDTO.getToDate(), LocalTime.MAX));
        }

        Query query = entityManager.createQuery(builder.toString());
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);

        for (Map.Entry<String, Object> entrySet : params.entrySet()) {
            query.setParameter(entrySet.getKey(), entrySet.getValue());
        }
        List<CommentEntity> commentList = query.getResultList();


        query = entityManager.createQuery(builderCount.toString());
        for (Map.Entry<String, Object> entrySet : params.entrySet()) {
            query.setParameter(entrySet.getKey(), entrySet.getValue());
        }
        Long totalCount = (Long) query.getSingleResult();

        return new PageImpl(commentList, PageRequest.of(page, size), totalCount);

    }

    public void FilterCriteriaBuilder(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CommentEntity> criteriaQuery = criteriaBuilder.createQuery(CommentEntity.class);

        Root<CommentEntity> root = criteriaQuery.from(CommentEntity.class);

        criteriaQuery.select(root);

        Predicate preName = criteriaBuilder.equal(root.get("id"),1);
        criteriaQuery.where(preName);

        List<CommentEntity> result = entityManager.createQuery(criteriaQuery).getResultList();
        System.out.println(result.get(0).getArticle().getTitle());
    }

}
