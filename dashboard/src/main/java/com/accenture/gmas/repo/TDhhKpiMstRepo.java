package com.accenture.gmas.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.accenture.dao.constant.QueryConstants;
import com.accenture.dao.entity.TDhhKpiMst;

@Repository
public interface TDhhKpiMstRepo extends CrudRepository<TDhhKpiMst, Long> {
	
	@Query(value=QueryConstants.FETCH_ALL_KPI_QUES,nativeQuery=true)
	int fetchkpiquescount(@Param("lever_id") int leverid,@Param("tupla_id") int tupladocid);
	
	
}
