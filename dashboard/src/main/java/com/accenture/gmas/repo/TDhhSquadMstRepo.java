package com.accenture.gmas.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.dao.constant.QueryConstants;
import com.accenture.dao.entity.TDhhSquadMst;

@Repository
public interface TDhhSquadMstRepo extends CrudRepository<TDhhSquadMst, Long> {
	
	@Query(value=QueryConstants.FETCH_SQUAD_FTE_SCORE,nativeQuery=true)
	int fetchallsquadfte();
	
	@Query(value=QueryConstants.FETCH_QUES_ADJ_THR_VALUES,nativeQuery=true)
	Object fetchaquesadj(@Param("squad_id") int squadid);
	
	@Query(value=QueryConstants.FETCH_KPIS_ADJ_THR_VALUES,nativeQuery=true)
	Object fetchakpiadj(@Param("squad_id") int squadid);
	
	@Query(value=QueryConstants.FETCH_DOCS_ADJ_THR_VALUES,nativeQuery=true)
	Object fetchadocadj(@Param("squad_id") int squadid);
	
	@Query(value=QueryConstants.FETCH_SURVEY_FTE,nativeQuery=true)
	int fetchSurveyfte(@Param("survey_id") long surveyid);
}
