package com.company.kun.uz.service;

import com.company.kun.uz.dto.RegionDto;
import com.company.kun.uz.entity.RegionEntity;
import com.company.kun.uz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    @Autowired
    RegionRepository regionRepository;

    public RegionDto create(RegionDto regionDto) {
        RegionEntity regionEntity = new RegionEntity();
        regionEntity.setName(regionDto.getName());
        regionEntity.setRegion(regionDto.getRegion());
        regionRepository.save(regionEntity);
        regionDto.setId(regionEntity.getId());
        return regionDto;
    }

    public RegionDto UpdateRegionById(Integer id,String name){
        Optional<RegionEntity> region = regionRepository.findById(id);
        if(!region.isPresent()){
            return null;
        }

        regionRepository.UpdateRegionById(id,name);
        return todo(region.get());
    }


    public String deleteRegionById(Integer id){
        Optional<RegionEntity> region = regionRepository.findById(id);
        if(!region.isPresent()){
            return "not found";
        }
//        regionRepository.deleteRegionById(id);
        return "succesfully";
    }
    public RegionDto getById(Integer id){
        Optional<RegionEntity> region = regionRepository.findById(id);
        if(!region.isPresent()){
            return null;
        }

        return todo(region.get());
    }

    public List<RegionDto> allRegion (){
        Iterable<RegionEntity> all = regionRepository.findAll();
        Iterator<RegionEntity> iterator = all.iterator();
        List<RegionDto> regions = new LinkedList<>();
        while (iterator.hasNext()){
            regions.add(todo(iterator.next()));
        }
        return regions;
    }

    public RegionDto todo(RegionEntity regionEntity){
        RegionDto regionDto = new RegionDto();
        regionDto.setId(regionEntity.getId());
        regionDto.setName(regionEntity.getName());
        regionDto.setRegion(regionEntity.getRegion());
        return regionDto;
    }



}
