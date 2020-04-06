package com.accenture.dao.constant;

public class QueryConstants {

	public static final String FETCH_ALL_ANSWERS = "select distinct t1.question_id,(case  when t1.answer='Yes' then 4 when t1.answer='Partial' then 3 when t1.answer='No' then 2 when t1.answer='N/A' then 1 end) answer,t2.squad_id,t3.lever_id from t_dhh_user_survey_ans t1,t_dhh_user_mst t2,t_dhh_question_mst t3,t_dhh_survey_activity t4 where t1.activity_id=t4.activity_id and t4.user_id=t2.user_id and t1.question_id=t3.question_id";
	
	public static final String FETCH_TUPLA_ID = "select tupla_id from t_dhh_survey_mst where survey_id=(select survey_id from t_dhh_squad_mst where squad_id=:squad_id)";
	public static final String FETCH_PARENT_QUES_COUNT = "select count(*) from t_dhh_question_mst where lever_id=:lever_id and tupla_question_id=:tupla_question_id";
	public static final String FETCH_CHILD_QUES_COUNT = "select count(*) from t_dhh_question_mst where tupla_question_id in(select question_id from t_dhh_question_mst where lever_id=:lever_id and tupla_question_id=:tupla_question_id) and lever_id=:lever_id";
	
	public static final String FETCH_SQUAD_LEVER_SCORE = "select * from t_dhh_squad_lever_score where squad_id=:squad_id and lever_id=:lever_id";
	
	public static final String FETCH_SQUAD_FTE_SCORE = "select sum(fte) from t_dhh_squad_mst";
	
	public static final String FETCH_ALL_DOCUMENT_ANSWERS = "select distinct t1.document_id,(case  when t1.answer='Yes' then 4 when t1.answer='Partial' then 3 when t1.answer='No' then 2 when t1.answer='N/A' then 1 end) answer,t2.squad_id,t3.lever_id from t_dhh_user_survey_document_ans t1,t_dhh_user_mst t2,t_dhh_document_mst t3,t_dhh_survey_activity t4 where t1.activity_id=t4.activity_id and t4.user_id=t2.user_id and t1.document_id=t3.document_id";
	
	public static final String FETCH_DOCUMENT_COUNT = "select count(*) from t_dhh_document_mst where tupla_id=:tupla_id and lever_id=:lever_id";

	public static final String FETCH_ALL_KPI_ANSWERS = "select distinct t1.kpi_id,t1.kpi_score,t2.squad_id,t3.lever_id from t_dhh_user_survey_kpi_ans t1,t_dhh_user_mst t2,t_dhh_kpi_mst t3,t_dhh_survey_activity t4 where t1.activity_id=t4.activity_id and t4.user_id=t2.user_id and t1.kpi_id=t3.kpi_id";
	
	public static final String FETCH_ALL_KPI_QUES = "select count(*) from t_dhh_kpi_mst where tupla_id=:tupla_id and lever_id=:lever_id"; 
	
	public static final String FETCH_SQUAD_LEVER_SCORE_BY_SURVEYID = "select * from t_dhh_squad_lever_score where squad_id in (select squad_id from t_dhh_squad_mst where survey_id=:surveyid and active_flag='Y') and active_flag='Y' order by lever_id";
	public static final String FETCH_QUES_ADJ_THR_VALUES = "select t1.question_adj,t1.question_threshold from t_dhh_survey_mst t1,t_dhh_squad_mst t2 where t2.squad_id=:squad_id and t1.survey_id=t2.survey_id";
	
	public static final String FETCH_KPIS_ADJ_THR_VALUES = "select t1.kpi_adj,t1.kpi_threshold from t_dhh_survey_mst t1,t_dhh_squad_mst t2 where t2.squad_id=:squad_id and t1.survey_id=t2.survey_id";
	
	public static final String FETCH_DOCS_ADJ_THR_VALUES = "select t1.document_adj,t1.document_threshold from t_dhh_survey_mst t1,t_dhh_squad_mst t2 where t2.squad_id=:squad_id and t1.survey_id=t2.survey_id";
	
	public static final String FETCH_ALL_SURVEY_LEVER_VALUES = "select distinct  t1.kpi_id,t1.answer,t2.squad_id,t3.lever_id,t2.user_name,t1.user_comment from t_dhh_user_survey_kpi_ans t1,t_dhh_user_mst t2,t_dhh_kpi_mst t3,t_dhh_survey_activity t4 where t4.survey_id=:survey_id and t1.activity_id=t4.activity_id and t2.squad_id in(select squad_id from t_dhh_squad_mst where survey_id=:survey_id and active_flag='Y') and t4.user_id=t2.user_id and t1.kpi_id=t3.kpi_id order by t3.lever_id";


	public static final String FETCH_SURVEY_LEVER_QUESTIONANS = "select distinct q.question_id,q.answer,squad.squad_id,qmst.lever_id,user.user_name,q.user_comment from t_dhh_user_mst user,t_dhh_survey_activity survey,t_dhh_squad_mst squad,t_dhh_user_survey_ans q,t_dhh_question_mst qmst where user.squad_id = squad.squad_id and user.squad_id in (select squad_id from t_dhh_squad_mst mst where survey_id=:surveyid) and q.activity_id = survey.activity_id and q.activity_id in (select activity_id from t_dhh_survey_activity where survey_id=:surveyid) and user.user_id = survey.user_id and qmst.question_id = q.question_id and squad.active_flag = 'Y' order by qmst.lever_id";

	public static final String FETCH_DOCS_ANS = "select distinct doc.document_id,doc.answer,user.user_name,docmst.lever_id,squad.squad_id,doc.user_comment from t_dhh_user_mst user,t_dhh_survey_activity survey,t_dhh_squad_mst squad,t_dhh_user_survey_document_ans doc,t_dhh_document_mst docmst\r\n" + 
			"where user.squad_id = squad.squad_id and user.squad_id in (\r\n" + 
			"select squad_id from t_dhh_squad_mst mst where survey_id=:surveyId) and doc.activity_id = survey.activity_id and doc.activity_id in (\r\n" + 
			"select activity_id from t_dhh_survey_activity where survey_id=:surveyId) and user.user_id = survey.user_id\r\n" + 
			"and docmst.document_id = doc.document_id and squad.active_flag = 'Y' order by docmst.lever_id";

	public static final String FETCH_QUES_MST = "SELECT * FROM t_dhh_question_mst where lever_id = :leverId";
	
	public static final String FETCH_SURVEY_FTE = "select sum(fte) from t_dhh_squad_mst where squad_id in(select squad_id from t_dhh_squad_mst where survey_id=:survey_id)";
}
