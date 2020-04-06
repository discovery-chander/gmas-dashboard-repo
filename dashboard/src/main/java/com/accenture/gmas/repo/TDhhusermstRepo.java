package com.accenture.gmas.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.dao.entity.TDhhUserMst;

@Repository
public interface TDhhusermstRepo extends CrudRepository<TDhhUserMst, Long> {
	
	

}
