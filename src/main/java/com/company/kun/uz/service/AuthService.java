package com.company.kun.uz.service;

import com.company.kun.uz.dto.profile.ProfileDTO;
import com.company.kun.uz.dto.RegistrationDto;
import com.company.kun.uz.dto.auth.AuthorizationDTO;
import com.company.kun.uz.entity.ProfileEntity;
import com.company.kun.uz.enums.ProfileRole;
import com.company.kun.uz.enums.ProfileStatus;
import com.company.kun.uz.expections.BadRequestExeption;
import com.company.kun.uz.repository.ProfileRepository;
import com.company.kun.uz.util.JwtUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    ProfileRepository profileRepository;
    @Autowired
    EmailService emailService;

    public ProfileDTO authorization(AuthorizationDTO dto) {
        String pswd = DigestUtils.md5Hex(dto.getPassword());
        Optional<ProfileEntity> optional = profileRepository
                .findByLoginAndPswd(dto.getLogin(), pswd);
        if (!optional.isPresent()) {
            throw new RuntimeException("Login or Password incorrect");
        }
        else if(!optional.get().getStatus().equals(ProfileStatus.ACTIVE)){
            throw new BadRequestExeption("not allowed");
        }

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(optional.get().getName());
        profileDTO.setSurname(optional.get().getSurname());
        profileDTO.setJwt(JwtUtil.create(optional.get().getId(), optional.get().getRole()));
        return profileDTO;
    }

    public RegistrationDto create(RegistrationDto dto) {
        Optional<ProfileEntity> byEmail = profileRepository.findByEmail(dto.getEmail());
        Optional<ProfileEntity> byLogin = profileRepository.findByLogin(dto.getLogin());
        if (byEmail.isPresent()) {
            throw new BadRequestExeption("mazgi email");
        } else if (byLogin.isPresent()) {
            throw new BadRequestExeption("mazgi login");
        }
        String pswd = DigestUtils.md5Hex(dto.getPassword());
        ProfileEntity profile = new ProfileEntity();
        profile.setEmail(dto.getEmail());
        profile.setName(dto.getName());
        profile.setSurname(dto.getSurname());
        profile.setLogin(dto.getLogin());
        profile.setPswd(pswd);
        profile.setRole(ProfileRole.USER_ROLE);
        profile.setStatus(ProfileStatus.REGISTRATION);
        profileRepository.save(profile);
        String jwt = JwtUtil.create(profile.getId());
        StringBuilder builder = new StringBuilder();
//        builder.append("salom jigar qalaysan\n");
//        builder.append("agar bu sen bo`lsang shu linkni bos ");
//        builder.append("http://localhost:8080/auth/verification/" + jwt);
//
//        emailService.sendEmail(dto.getEmail(),"Registration KunUz test",builder.toString());

        return dto;
    }


    public String UpdateNameByEmail(String name, String email) {
        Optional<ProfileEntity> profile = Optional.ofNullable(profileRepository.findByEmail(email).get());
        if (profile.isPresent()) {
            profileRepository.UpdateNameByEmail(email, name);
            return "succesfully";
        }
        return "not found";
    }

    public String UpdateSurnameByEmail(String surname, String email) {
        Optional<ProfileEntity> profile = Optional.ofNullable(profileRepository.findByEmail(email).get());
        if (profile.isPresent()) {
            profileRepository.UpdateSurnameByEmail(email, surname);
            return "succesfully";
        }
        return "not found";
    }

    public void verification(String id) {
        Integer integer = JwtUtil.decodeandGetId(id);
        ProfileEntity entity = profileRepository.getById(integer);
        entity.setStatus(ProfileStatus.ACTIVE);
        profileRepository.save(entity);

    }
}
