package com.accenture.gmas.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.dao.entity.TDhhClientMst;

@Repository
public interface TDhhclientmstRepo extends CrudRepository<TDhhClientMst, Long> {
	

}
