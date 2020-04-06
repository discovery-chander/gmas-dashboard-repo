package com.accenture.gmas.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.dao.constant.QueryConstants;
import com.accenture.dao.entity.TDhhDocumentMst;

@Repository
public interface TDhhDocumentMstRepo extends CrudRepository<TDhhDocumentMst, Long> {
	
	@Query(value=QueryConstants.FETCH_DOCUMENT_COUNT,nativeQuery=true)
	int fetchparentquescount(@Param("lever_id") int leverid,@Param("tupla_id") int tupladocid);
	
	
}
