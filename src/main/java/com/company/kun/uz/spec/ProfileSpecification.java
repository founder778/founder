package com.company.kun.uz.spec;

import com.company.kun.uz.entity.CommentEntity;
import com.company.kun.uz.entity.ProfileEntity;
import com.company.kun.uz.enums.CommentStatus;
import com.company.kun.uz.enums.ProfileStatus;
import org.springframework.data.jpa.domain.Specification;

public class ProfileSpecification {
    public static Specification<ProfileEntity> status(ProfileStatus status) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("status"), status);
        });
    }

    public static Specification<ProfileEntity> email(String title) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("title"), title);
        });
    }


    public static Specification<ProfileEntity> equal(String field, String value) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(field), value);
        });
    }
    public static Specification<ProfileEntity> equal(String  field, Integer id) {
        return ((root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get(String.valueOf(field)), id);
        });
    }
}
