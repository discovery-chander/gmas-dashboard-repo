package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_dhh_user_survey_document_ans database table.
 * 
 */
@Entity
@Table(name="t_dhh_user_survey_document_ans")
@NamedQuery(name="TDhhUserSurveyDocumentAns.findAll", query="SELECT t FROM TDhhUserSurveyDocumentAns t")
public class TDhhUserSurveyDocumentAns implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_survey_doc_ans_id")
	private int userSurveyDocAnsId;

	@Column(name="active_flag")
	private String activeFlag;

	private String answer;

	@Column(name="user_comment")
	private String userComment;

	//bi-directional many-to-one association to TDhhSurveyActivity
	@ManyToOne
	@JoinColumn(name="activity_id")
	private TDhhSurveyActivity TDhhSurveyActivity;

	//bi-directional many-to-one association to TDhhDocumentMst
	@ManyToOne
	@JoinColumn(name="document_id")
	private TDhhDocumentMst TDhhDocumentMst;

	public TDhhUserSurveyDocumentAns() {
	}

	public int getUserSurveyDocAnsId() {
		return this.userSurveyDocAnsId;
	}

	public void setUserSurveyDocAnsId(int userSurveyDocAnsId) {
		this.userSurveyDocAnsId = userSurveyDocAnsId;
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

	public TDhhDocumentMst getTDhhDocumentMst() {
		return this.TDhhDocumentMst;
	}

	public void setTDhhDocumentMst(TDhhDocumentMst TDhhDocumentMst) {
		this.TDhhDocumentMst = TDhhDocumentMst;
	}

}