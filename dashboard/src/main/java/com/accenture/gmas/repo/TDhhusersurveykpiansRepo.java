package com.accenture.gmas.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.dao.constant.QueryConstants;
import com.accenture.dao.entity.TDhhUserSurveyKpiAns;

@Repository
public interface TDhhusersurveykpiansRepo extends CrudRepository<TDhhUserSurveyKpiAns, Long> {
	
	
	@Query(value=QueryConstants.FETCH_ALL_KPI_ANSWERS,nativeQuery=true)
	List<Object> fetchAllKPIAnswers();
	
	@Query(value=QueryConstants.FETCH_ALL_SURVEY_LEVER_VALUES,nativeQuery=true)
	List<Object> fetchAllKPISuvreyAnswers(@Param("survey_id") Long surveyid);

}
