package com.accenture.gmas.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.dao.entity.TDhhTuplaMst;

@Repository
public interface TDhhTuplaMstRepo extends CrudRepository<TDhhTuplaMst, Long> {

}
