package com.accenture.gmas.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.dao.constant.QueryConstants;
import com.accenture.dao.entity.TDhhUserSurveyAns;

@Repository
public interface TDhhusersurveyansRepo extends CrudRepository<TDhhUserSurveyAns, Long> {
	
	
	@Query(value=QueryConstants.FETCH_ALL_ANSWERS,nativeQuery=true)
	List<Object> fetchAllAnswers();
	
	@Query(value=QueryConstants.FETCH_SURVEY_LEVER_QUESTIONANS,nativeQuery=true)
	List<Object> fetchSurveyQuestionAns(@Param("surveyid") Long surveyid);

}
