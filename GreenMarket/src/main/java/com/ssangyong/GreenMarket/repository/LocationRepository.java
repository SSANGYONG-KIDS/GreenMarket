package com.ssangyong.GreenMarket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.ssangyong.GreenMarket.model.LocationEntity;

public interface LocationRepository extends CrudRepository<LocationEntity, String>, JpaRepository<LocationEntity, String>{

}
