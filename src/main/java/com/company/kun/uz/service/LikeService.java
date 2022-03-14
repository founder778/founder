package com.company.kun.uz.service;
import com.company.kun.uz.dto.LikeDto;
import com.company.kun.uz.entity.LikeEntity;
import com.company.kun.uz.entity.ProfileEntity;
import com.company.kun.uz.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
@Service
public class LikeService {
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    ProfileService profileService;

    public LikeDto created(LikeDto dto,Integer userId){
        ProfileEntity profile = profileService.getByid(userId);
        LikeEntity likeEntity = new LikeEntity();
        likeEntity.setProfile(profile);
        likeEntity.setStatus(dto.getStatus());
        likeEntity.setType(dto.getType());
        likeEntity.setAction_id(dto.getActionId());
        likeEntity.setCreatedDate(LocalDateTime.now());
        Optional<LikeEntity> Entity= likeRepository.getByActionIdAndProfileAndType(dto.getActionId(),
                profile, dto.getType());
        if(Entity.isPresent()){
            LikeEntity like =Entity.get();
            like.setStatus(dto.getStatus());
            likeRepository.save(like);
            dto.setId(like.getId());
            return dto;

        }

        likeRepository.save(likeEntity);
        dto.setId(likeEntity.getId());
        return dto;

//        likeRepository.save(likeEntity);
//        dto.setId(likeEntity.getId());
//        return dto;


    }


}
