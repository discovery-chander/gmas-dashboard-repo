package com.accenture.gmas.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.dao.constant.QueryConstants;
import com.accenture.dao.entity.TDhhSurveyMst;

@Repository
public interface TDhhSurveyMstRepo extends CrudRepository<TDhhSurveyMst, Long> {

	@Query(value=QueryConstants.FETCH_TUPLA_ID,nativeQuery=true)
	int fetchTuplaId(@Param("squad_id") int squadid);

}
