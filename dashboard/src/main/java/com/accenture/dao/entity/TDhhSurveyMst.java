package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the t_dhh_survey_mst database table.
 * 
 */
@Entity
@Table(name="t_dhh_survey_mst")
@NamedQuery(name="TDhhSurveyMst.findAll", query="SELECT t FROM TDhhSurveyMst t")
public class TDhhSurveyMst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="survey_id")
	private Long surveyId;

	@Column(name="document_adj")
	private Double documentAdj;

	@Column(name="document_threshold")
	private Double documentThreshold;

	@Column(name="kpi_adj")
	private Double kpiAdj;

	@Column(name="kpi_threshold")
	private Double kpiThreshold;

	@Column(name="question_adj")
	private Double questionAdj;

	@Column(name="question_threshold")
	private Double questionThreshold;

	@Column(name="survey_name")
	private String surveyName;

	@Column(name="validity_end_date")
	private String validityEndDate;

	@Column(name="validity_start_date")
	private String validityStartDate;
	
	@Column(name="survey_started")
	private String surveyStarted;
	
	public String getSurveyStarted() {
		return surveyStarted;
	}

	public void setSurveyStarted(String surveyStarted) {
		this.surveyStarted = surveyStarted;
	}

	@Column(name="active_flag")
	private String activeFlag;
	
	//bi-directional many-to-one association to TDhhPersonaMst
	@OneToMany(mappedBy="TDhhSurveyMst")
	private List<TDhhPersonaMst> TDhhPersonaMsts;

	//bi-directional many-to-one association to TDhhSquadMst
	@OneToMany(mappedBy="TDhhSurveyMst")
	private List<TDhhSquadMst> TDhhSquadMsts;

	//bi-directional many-to-one association to TDhhSurveyActivity
	@OneToMany(mappedBy="TDhhSurveyMst")
	private List<TDhhSurveyActivity> TDhhSurveyActivities;

	//bi-directional many-to-one association to TDhhClientMst
	@ManyToOne
	@JoinColumn(name="client_id")
	private TDhhClientMst TDhhClientMst;

	//bi-directional many-to-one association to TDhhTuplaMst
	@ManyToOne
	@JoinColumn(name="tupla_id")
	private TDhhTuplaMst TDhhTuplaMst;

	public TDhhSurveyMst() {
	}

	public Long getSurveyId() {
		return this.surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}	

	public Double getDocumentAdj() {
		return documentAdj;
	}

	public void setDocumentAdj(Double documentAdj) {
		this.documentAdj = documentAdj;
	}

	public Double getDocumentThreshold() {
		return documentThreshold;
	}

	public void setDocumentThreshold(Double documentThreshold) {
		this.documentThreshold = documentThreshold;
	}

	public Double getKpiAdj() {
		return kpiAdj;
	}

	public void setKpiAdj(Double kpiAdj) {
		this.kpiAdj = kpiAdj;
	}

	public Double getKpiThreshold() {
		return kpiThreshold;
	}

	public void setKpiThreshold(Double kpiThreshold) {
		this.kpiThreshold = kpiThreshold;
	}

	public Double getQuestionAdj() {
		return questionAdj;
	}

	public void setQuestionAdj(Double questionAdj) {
		this.questionAdj = questionAdj;
	}

	public Double getQuestionThreshold() {
		return questionThreshold;
	}

	public void setQuestionThreshold(Double questionThreshold) {
		this.questionThreshold = questionThreshold;
	}

	public String getSurveyName() {
		return this.surveyName;
	}

	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public String getValidityEndDate() {
		return this.validityEndDate;
	}

	public void setValidityEndDate(String validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	public String getValidityStartDate() {
		return this.validityStartDate;
	}

	public void setValidityStartDate(String validityStartDate) {
		this.validityStartDate = validityStartDate;
	}

	public List<TDhhPersonaMst> getTDhhPersonaMsts() {
		return this.TDhhPersonaMsts;
	}

	public void setTDhhPersonaMsts(List<TDhhPersonaMst> TDhhPersonaMsts) {
		this.TDhhPersonaMsts = TDhhPersonaMsts;
	}	
	
	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public TDhhPersonaMst addTDhhPersonaMst(TDhhPersonaMst TDhhPersonaMst) {
		getTDhhPersonaMsts().add(TDhhPersonaMst);
		TDhhPersonaMst.setTDhhSurveyMst(this);

		return TDhhPersonaMst;
	}

	public TDhhPersonaMst removeTDhhPersonaMst(TDhhPersonaMst TDhhPersonaMst) {
		getTDhhPersonaMsts().remove(TDhhPersonaMst);
		TDhhPersonaMst.setTDhhSurveyMst(null);

		return TDhhPersonaMst;
	}

	public List<TDhhSquadMst> getTDhhSquadMsts() {
		return this.TDhhSquadMsts;
	}

	public void setTDhhSquadMsts(List<TDhhSquadMst> TDhhSquadMsts) {
		this.TDhhSquadMsts = TDhhSquadMsts;
	}

	public TDhhSquadMst addTDhhSquadMst(TDhhSquadMst TDhhSquadMst) {
		getTDhhSquadMsts().add(TDhhSquadMst);
		TDhhSquadMst.setTDhhSurveyMst(this);

		return TDhhSquadMst;
	}

	public TDhhSquadMst removeTDhhSquadMst(TDhhSquadMst TDhhSquadMst) {
		getTDhhSquadMsts().remove(TDhhSquadMst);
		TDhhSquadMst.setTDhhSurveyMst(null);

		return TDhhSquadMst;
	}

	public List<TDhhSurveyActivity> getTDhhSurveyActivities() {
		return this.TDhhSurveyActivities;
	}

	public void setTDhhSurveyActivities(List<TDhhSurveyActivity> TDhhSurveyActivities) {
		this.TDhhSurveyActivities = TDhhSurveyActivities;
	}

	public TDhhSurveyActivity addTDhhSurveyActivity(TDhhSurveyActivity TDhhSurveyActivity) {
		getTDhhSurveyActivities().add(TDhhSurveyActivity);
		TDhhSurveyActivity.setTDhhSurveyMst(this);

		return TDhhSurveyActivity;
	}

	public TDhhSurveyActivity removeTDhhSurveyActivity(TDhhSurveyActivity TDhhSurveyActivity) {
		getTDhhSurveyActivities().remove(TDhhSurveyActivity);
		TDhhSurveyActivity.setTDhhSurveyMst(null);

		return TDhhSurveyActivity;
	}

	public TDhhClientMst getTDhhClientMst() {
		return this.TDhhClientMst;
	}

	public void setTDhhClientMst(TDhhClientMst TDhhClientMst) {
		this.TDhhClientMst = TDhhClientMst;
	}

	public TDhhTuplaMst getTDhhTuplaMst() {
		return this.TDhhTuplaMst;
	}

	public void setTDhhTuplaMst(TDhhTuplaMst TDhhTuplaMst) {
		this.TDhhTuplaMst = TDhhTuplaMst;
	}

}