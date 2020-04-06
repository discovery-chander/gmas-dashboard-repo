package com.accenture.gmas.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.dao.entity.TDhhSurveyActivity;

@Repository
public interface TDhhSurveyActivityRepo extends CrudRepository<TDhhSurveyActivity, Long> {
	

}
