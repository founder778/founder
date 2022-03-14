package com.company.kun.uz.repository;

import com.company.kun.uz.entity.ArticleEntity;
import com.company.kun.uz.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Integer>, JpaSpecificationExecutor<ProfileEntity> {
    Optional<ProfileEntity> findByLoginAndPswd(String login, String pswd);


    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByLogin(String login);

    @Modifying
    @Transactional
    @Query("update ProfileEntity p set p.name = ?2 where p.email =?1 ")
    int UpdateNameByEmail(String email, String name);

    @Modifying
    @Transactional
    @Query("update ProfileEntity p set p.surname = ?2 where p.email =?1 ")
    int UpdateSurnameByEmail(String email, String surname);

    @Query("select p from ProfileEntity  p where p.type='admin'  and p.id=?1")
    Optional<ProfileEntity> findAdminid(Integer id);

    @Modifying
    @Transactional
    @Query("delete ProfileEntity p where p.email = ?1")
    void DeleteProfileByEmail(String email);






}
