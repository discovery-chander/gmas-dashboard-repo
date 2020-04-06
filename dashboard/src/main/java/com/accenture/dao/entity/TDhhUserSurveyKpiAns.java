package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_dhh_user_survey_kpi_ans database table.
 * 
 */
@Entity
@Table(name="t_dhh_user_survey_kpi_ans")
@NamedQuery(name="TDhhUserSurveyKpiAns.findAll", query="SELECT t FROM TDhhUserSurveyKpiAns t")
public class TDhhUserSurveyKpiAns implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_survey_kpi_ans_id")
	private int userSurveyKpiAnsId;

	@Column(name="active_flag")
	private String activeFlag;

	private String answer;

	@Column(name="user_comment")
	private String userComment;

	//bi-directional many-to-one association to TDhhSurveyActivity
	@ManyToOne
	@JoinColumn(name="activity_id")
	private TDhhSurveyActivity TDhhSurveyActivity;

	//bi-directional many-to-one association to TDhhKpiMst
	@ManyToOne
	@JoinColumn(name="kpi_id")
	private TDhhKpiMst TDhhKpiMst;

	public TDhhUserSurveyKpiAns() {
	}

	public int getUserSurveyKpiAnsId() {
		return this.userSurveyKpiAnsId;
	}

	public void setUserSurveyKpiAnsId(int userSurveyKpiAnsId) {
		this.userSurveyKpiAnsId = userSurveyKpiAnsId;
	}

	public String getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getUserComment() {
		return this.userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}

	public TDhhSurveyActivity getTDhhSurveyActivity() {
		return this.TDhhSurveyActivity;
	}

	public void setTDhhSurveyActivity(TDhhSurveyActivity TDhhSurveyActivity) {
		this.TDhhSurveyActivity = TDhhSurveyActivity;
	}

	public TDhhKpiMst getTDhhKpiMst() {
		return this.TDhhKpiMst;
	}

	public void setTDhhKpiMst(TDhhKpiMst TDhhKpiMst) {
		this.TDhhKpiMst = TDhhKpiMst;
	}

}