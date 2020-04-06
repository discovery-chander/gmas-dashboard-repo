package com.accenture.gmas.repo;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.dao.constant.QueryConstants;
import com.accenture.dao.entity.TDhhQuestionMst;

@Repository
public interface TDhhQuestionMstRepo extends CrudRepository<TDhhQuestionMst, Long> {

	@Query(value=QueryConstants.FETCH_PARENT_QUES_COUNT,nativeQuery=true)
	int fetchparentquescount(@Param("lever_id") int leverid,@Param("tupla_question_id") int tuplaquestionid);
	
	@Query(value=QueryConstants.FETCH_CHILD_QUES_COUNT,nativeQuery=true)
	int fetchchildquescount(@Param("lever_id") int leverid,@Param("tupla_question_id") int tuplaquestionid);

	@Query(value=QueryConstants.FETCH_QUES_MST,nativeQuery=true)
	List<TDhhQuestionMst> getQuestionMst(@Param("leverId") long leverId);
}
