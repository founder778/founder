package com.company.kun.uz.repository;

import com.company.kun.uz.entity.RegionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {
    @Modifying
    @Transactional
    @Query("update RegionEntity r set r.name = ?2 where r.id =?1")
    int UpdateRegionById(Integer id, String name);

    @Modifying
    @Transactional
    @Query("delete RegionEntity r where r.id =?1")
    void deleteRegionById(Integer id);
}
