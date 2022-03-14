package com.company.kun.uz.service;
import com.company.kun.uz.dto.article.ArticleFilterDto;
import com.company.kun.uz.dto.profile.ProfileDTO;
import com.company.kun.uz.dto.profile.ProfileFilterDto;
import com.company.kun.uz.entity.ArticleEntity;
import com.company.kun.uz.entity.ProfileEntity;
import com.company.kun.uz.enums.ArticleStatus;
import com.company.kun.uz.enums.ProfileStatus;
import com.company.kun.uz.expections.ItemNotFoundExeption;
import com.company.kun.uz.repository.ProfileCustomRepository;
import com.company.kun.uz.repository.ProfileRepository;
import com.company.kun.uz.spec.ArticleSpecification;
import com.company.kun.uz.spec.ProfileSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileCustomRepository profileCustomRepository;

    public ProfileDTO create(ProfileDTO dto) {
            ProfileEntity entity = new ProfileEntity();
            entity.setName(dto.getName());
            entity.setSurname(dto.getSurname());
            entity.setLogin(dto.getLogin());
            entity.setRole(dto.getRole());
            entity.setEmail(dto.getEmail());
            entity.setPswd(dto.getPswd());
            profileRepository.save(entity);
            dto.setId(entity.getId());
            return dto;
    }
    public ProfileEntity getByid(Integer id){
        return profileRepository.findById(id).orElseThrow(() -> new ItemNotFoundExeption("Profile not found"));
    }

    public ProfileDTO UpdateProfileByEmail(ProfileDTO profileDTO,Integer id){
        Optional<ProfileEntity> result = profileRepository.findAdminid(id);
        if(!result.isPresent()){
            return null;
        }
        profileRepository.UpdateNameByEmail(profileDTO.getEmail(),profileDTO.getName());
        return profileDTO;

    }


     public ProfileDTO todto(ProfileEntity profile){
        ProfileDTO profileDTO =new ProfileDTO();
        profileDTO.setId(profile.getId());
        profileDTO.setName(profile.getName());
        profileDTO.setSurname(profile.getSurname());
        profileDTO.setLogin(profile.getLogin());
        profileDTO.setPswd(profile.getPswd());
        profileDTO.setEmail(profile.getEmail());
        return profileDTO;
     }

     public String deleteProfilByEmail(String email){
         profileRepository.DeleteProfileByEmail(email);
         return "delete";
     }
    public PageImpl<ProfileDTO> filter(int page, int size, ProfileFilterDto filterDTO) {
        PageImpl<ProfileEntity> entityPage = profileCustomRepository.filter(page,size,filterDTO);

        List<ProfileDTO> profiletDTOList = entityPage.stream().map(profileEntity -> {
            ProfileDTO dto = new ProfileDTO();
            dto.setId(profileEntity.getId());
            return dto;
        }).collect(Collectors.toList());

        return new PageImpl<>(profiletDTOList, entityPage.getPageable(), entityPage.getTotalElements());
    }

    public PageImpl<ProfileEntity> filterSpec(int page, int size, ProfileFilterDto filterDTO){
        Specification<ProfileEntity> spec = null;
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.ASC,"id");
        if (filterDTO.getStatus() != null) {
            spec = Specification.where(ProfileSpecification.status(filterDTO.getStatus()));
        } else {
            spec = Specification.where(ProfileSpecification.status(ProfileStatus.ACTIVE));
        }

        if (filterDTO.getEmail() != null) {
            spec.and(ProfileSpecification.email(filterDTO.getEmail()));
        }
        if (filterDTO.getName() != null) {
            spec.and(ProfileSpecification.equal("name", filterDTO.getName()));
        }
        if (filterDTO.getSurname() != null) {
            spec.and(ProfileSpecification.equal("surname", filterDTO.getSurname()));
        }
        if (filterDTO.getPro_id() != null) {
            spec.and(ProfileSpecification.equal("pro_id", filterDTO.getPro_id()));
        }
        Page<ProfileEntity> profillePage = profileRepository.findAll(spec,pageable);
        return (PageImpl<ProfileEntity>) profillePage;
    }


}
