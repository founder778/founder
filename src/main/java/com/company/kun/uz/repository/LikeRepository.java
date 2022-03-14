package com.company.kun.uz.repository;

import com.company.kun.uz.entity.LikeEntity;
import com.company.kun.uz.entity.ProfileEntity;
import com.company.kun.uz.enums.LikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface LikeRepository extends JpaRepository<LikeEntity,Integer> {
     @Query("select l from LikeEntity l where l.action_id=?1 and l.profile=?2 and l.type=?3")
    Optional<LikeEntity> getByActionIdAndProfileAndType(Integer actionId, ProfileEntity profile, LikeType type);
}
