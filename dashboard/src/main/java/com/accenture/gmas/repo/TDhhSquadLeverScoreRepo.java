package com.accenture.gmas.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.dao.constant.QueryConstants;
import com.accenture.dao.entity.TDhhSquadLeverScore;

@Repository
public interface TDhhSquadLeverScoreRepo extends CrudRepository<TDhhSquadLeverScore, Long> {
	
	
	@Query(value=QueryConstants.FETCH_SQUAD_LEVER_SCORE,nativeQuery=true)
	TDhhSquadLeverScore fetchsquadlever(@Param("lever_id") int leverid,@Param("squad_id") int squadid);

	@Query(value=QueryConstants.FETCH_SQUAD_LEVER_SCORE_BY_SURVEYID,nativeQuery=true)
	List<TDhhSquadLeverScore> fetchSquadLeverScoreBySurveyId (@Param("surveyid") Long surveyid);

}
