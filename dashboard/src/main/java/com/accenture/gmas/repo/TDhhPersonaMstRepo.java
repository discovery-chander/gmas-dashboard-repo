package com.accenture.gmas.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.dao.entity.TDhhPersonaMst;

@Repository
public interface TDhhPersonaMstRepo extends CrudRepository<TDhhPersonaMst, Long> {


}
