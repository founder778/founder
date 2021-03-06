package com.company.kun.uz.repository;

import com.company.kun.uz.dto.article.ArticleFilterDto;
import com.company.kun.uz.entity.ArticleEntity;
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
public class ArticleCustomRepository {
    @Autowired
    private EntityManager entityManager;

        public PageImpl filter ( int page, int size, ArticleFilterDto filterDTO){
            Map<String, Object> params = new HashMap<>();
//
            StringBuilder builder = new StringBuilder("SELECT a FROM ArticleEntity a ");
            StringBuilder builderCount = new StringBuilder("SELECT count(a) FROM ArticleEntity a ");

            if (filterDTO.getArticleStatus() != null) {
                builder.append("Where status ='" + filterDTO.getArticleStatus().name() + "'");
                builderCount.append("Where status ='" + filterDTO.getArticleStatus().name() + "'");
            } else {
                builder.append("Where status ='PUBLISHED'");
                builderCount.append("Where status ='PUBLISHED'");
            }

            if (filterDTO.getArticleId() != null) {
                builder.append(" and a.id =:id");
                builderCount.append(" and a.id =:id");
                params.put("id", filterDTO.getArticleId());
            }
            if (filterDTO.getTitle() != null && !filterDTO.getTitle().isEmpty()) {
                builder.append(" and a.title =:title");
                builderCount.append(" and a.title =:title");
                params.put("title", filterDTO.getTitle());
            }

            if (filterDTO.getProfileId() != null) {
                builder.append(" and a.profile.id =:profileId");
                builderCount.append(" and a.profile.id =:profileId");
                params.put("profileId", filterDTO.getProfileId());
            }
            if (filterDTO.getFromDate() != null) {
                builder.append(" and a.createdDate >=:fromDate");
                builderCount.append(" and a.createdDate >=:fromDate");
                params.put("fromDate", LocalDateTime.of(filterDTO.getFromDate(), LocalTime.MIN));
            }

            if (filterDTO.getToDate() != null) {
                builder.append(" and a.createdDate <=:toDate");
                builderCount.append(" and a.createdDate <=:toDate");
                params.put("toDate", LocalDateTime.of(filterDTO.getToDate(), LocalTime.MAX));
            }

            Query query = entityManager.createQuery(builder.toString());
            query.setFirstResult((page - 1) * size);
            query.setMaxResults(size);

            for (Map.Entry<String, Object> entrySet : params.entrySet()) {
                query.setParameter(entrySet.getKey(), entrySet.getValue());
            }
            List<ArticleEntity> articleList = query.getResultList();


            query = entityManager.createQuery(builderCount.toString());
            for (Map.Entry<String, Object> entrySet : params.entrySet()) {
                query.setParameter(entrySet.getKey(), entrySet.getValue());
            }
            Long totalCount = (Long) query.getSingleResult();

            return new PageImpl(articleList, PageRequest.of(page, size), totalCount);

    }


    public void filterCriteriaBuilder(){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ArticleEntity> criteriaQuery = criteriaBuilder.createQuery(ArticleEntity.class);
        Root<ArticleEntity> root = criteriaQuery.from(ArticleEntity.class);

        criteriaQuery.select(root);

        Predicate namePre = criteriaBuilder.equal(root.get("id"),4);


        criteriaQuery.where(namePre);
        List<ArticleEntity> articleList = entityManager.createQuery(criteriaQuery).getResultList();
        System.out.println(articleList.get(0).getTitle());
    }

}
