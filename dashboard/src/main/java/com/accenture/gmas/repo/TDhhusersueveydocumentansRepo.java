package com.accenture.gmas.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.dao.constant.QueryConstants;
import com.accenture.dao.entity.TDhhUserSurveyDocumentAns;

@Repository
public interface TDhhusersueveydocumentansRepo extends CrudRepository<TDhhUserSurveyDocumentAns, Long> {
	
	
	@Query(value=QueryConstants.FETCH_ALL_DOCUMENT_ANSWERS,nativeQuery=true)
	List<Object> fetchAllAnswers();
	
	@Query(value=QueryConstants.FETCH_DOCS_ANS,nativeQuery=true)
	List<Object> getDocumentAns(@Param("surveyId") Long surveyId);
	
}
