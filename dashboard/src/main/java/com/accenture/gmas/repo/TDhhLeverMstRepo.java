package com.accenture.gmas.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.dao.entity.TDhhLeverMst;

@Repository
public interface TDhhLeverMstRepo extends CrudRepository<TDhhLeverMst, Long> {
	
}
