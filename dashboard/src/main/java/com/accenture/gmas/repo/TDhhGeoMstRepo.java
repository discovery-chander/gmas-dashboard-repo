package com.accenture.gmas.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.dao.entity.TDhhGeoMst;

@Repository
public interface TDhhGeoMstRepo extends CrudRepository<TDhhGeoMst, Long> {


}
