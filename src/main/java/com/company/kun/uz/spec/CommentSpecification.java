package com.company.kun.uz.spec;

import com.company.kun.uz.entity.ArticleEntity;
import com.company.kun.uz.entity.CommentEntity;
import com.company.kun.uz.enums.ArticleStatus;
import com.company.kun.uz.enums.CommentStatus;
import org.springframework.data.jpa.domain.Specification;

public class CommentSpecification {
    public static Specification<CommentEntity> status(CommentStatus status) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), status);
        });
    }

    public static Specification<CommentEntity> title(String title) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("title"), title);
        });
    }

    public static Specification<CommentEntity> equal(String field, Integer id) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(field), id);
        });
    }
    public static Specification<CommentEntity> equal(Integer field, Integer id) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(String.valueOf(field)), id);
        });
    }
}
