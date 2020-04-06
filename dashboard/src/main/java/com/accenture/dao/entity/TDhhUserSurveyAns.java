package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_dhh_user_survey_ans database table.
 * 
 */
@Entity
@Table(name="t_dhh_user_survey_ans")
@NamedQuery(name="TDhhUserSurveyAns.findAll", query="SELECT t FROM TDhhUserSurveyAns t")
public class TDhhUserSurveyAns implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_survey_ans_id")
	private Long userSurveyAnsId;

	private String answer;
	
	@Column(name="user_comment")
	private String userComment;
	
	@Column(name="active_flag")
	private String activeFlag;
	//bi-directional many-to-one association to TDhhSurveyActivity
	@ManyToOne
	@JoinColumn(name="activity_id")
	private TDhhSurveyActivity TDhhSurveyActivity;

	//bi-directional many-to-one association to TDhhQuestionMst
	@ManyToOne
	@JoinColumn(name="question_id")
	private TDhhQuestionMst TDhhQuestionMst;

	public TDhhUserSurveyAns() {
	}

	public Long getUserSurveyAnsId() {
		return this.userSurveyAnsId;
	}

	public void setUserSurveyAnsId(Long userSurveyAnsId) {
		this.userSurveyAnsId = userSurveyAnsId;
	}

	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public TDhhSurveyActivity getTDhhSurveyActivity() {
		return this.TDhhSurveyActivity;
	}

	public void setTDhhSurveyActivity(TDhhSurveyActivity TDhhSurveyActivity) {
		this.TDhhSurveyActivity = TDhhSurveyActivity;
	}

	public TDhhQuestionMst getTDhhQuestionMst() {
		return this.TDhhQuestionMst;
	}

	public void setTDhhQuestionMst(TDhhQuestionMst TDhhQuestionMst) {
		this.TDhhQuestionMst = TDhhQuestionMst;
	}

	public String getUserComment() {
		return userComment;
	}

	public void setUserComment(String userComment) {
		this.userComment = userComment;
	}
	
}