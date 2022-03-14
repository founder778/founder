package com.company.kun.uz.repository;

import com.company.kun.uz.dto.article.ArticleFilterDto;
import com.company.kun.uz.dto.profile.ProfileDTO;
import com.company.kun.uz.dto.profile.ProfileFilterDto;
import com.company.kun.uz.entity.ArticleEntity;
import com.company.kun.uz.entity.ProfileEntity;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProfileCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public PageImpl filter(int page, int size, ProfileFilterDto filterDTO) {
        Map<String, Object> params = new HashMap<>();
//
        StringBuilder builder = new StringBuilder("SELECT p FROM ProfileEntity p ");
        StringBuilder builderCount = new StringBuilder("SELECT count(p) FROM ProfileEntity p ");

        if (filterDTO.getName() != null) {
            builder.append("Where p.name ='" + filterDTO.getName() + "'");
            builderCount.append("Where p.name ='" + filterDTO.getName() + "'");
        } else {
            builder.append("Where id >0");
            builderCount.append("Where id >0");
        }

        if (filterDTO.getSurname() != null) {
            builder.append(" and p.surname =:surname");
            builderCount.append(" and p.surname =:surname");
            params.put("surname", filterDTO.getSurname());
        }
        if (filterDTO.getEmail() != null) {
            builder.append(" and p.email =:email");
            builderCount.append(" and p.email =:email");
            params.put("email", filterDTO.getEmail());
        }

        if (filterDTO.getRole() != null) {
            builder.append(" and p.role =:role");
            builderCount.append(" and p.role =:role");
            params.put("role", filterDTO.getRole());
        }
        if (filterDTO.getPro_id() != null) {
            builder.append(" and p.id >=:id");
            builderCount.append(" and p.id >=:id");
            params.put("profile", filterDTO.getPro_id());
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

    public void filterPreteria(ProfileDTO dto) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProfileEntity> criteriaQuery = criteriaBuilder.createQuery(ProfileEntity.class);
        Root<ProfileEntity> root = criteriaQuery.from(ProfileEntity.class);

        criteriaQuery.select(root);
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (dto.getName() != null) {
            predicates.add(criteriaBuilder.equal(root.get("name"), dto.getName()));
        } else if (dto.getSurname() != null) {
            predicates.add(criteriaBuilder.equal(root.get("surname"), dto.getSurname()));
        } else if (dto.getEmail() != null) {
            predicates.add(criteriaBuilder.equal(root.get("email"), dto.getEmail()));
        } else if (dto.getRole() != null) {
            predicates.add(criteriaBuilder.equal(root.get("role"), dto.getRole().name()));
        }

        Predicate[] predicates1 = new Predicate[predicates.size()];
        predicates.toArray(predicates1);
        criteriaQuery.where(predicates1);
        criteriaQuery.orderBy(criteriaBuilder.asc(root.get("id")));
        List<ProfileEntity> profileList = entityManager.createQuery(criteriaQuery).getResultList();
        System.out.println(profileList.size());

    }

}
