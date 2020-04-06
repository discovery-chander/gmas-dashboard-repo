package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the t_dhh_question_mst database table.
 * 
 */
@Entity
@Table(name="t_dhh_question_mst")
@NamedQuery(name="TDhhQuestionMst.findAll", query="SELECT t FROM TDhhQuestionMst t")
public class TDhhQuestionMst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="question_id")
	private Long questionId;

	private String question;

	@Column(name="question_group")
	private String questionGroup;
	
	@Column(name="parent_type")
	private String parentType;

	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	private String topten;

	@Column(name="tupla_question_id")
	private int tuplaQuestionId;

	//bi-directional many-to-one association to TDhhGeoMst
	@ManyToOne
	@JoinColumn(name="geo_id")
	private TDhhGeoMst TDhhGeoMst;

	//bi-directional many-to-one association to TDhhLeverMst
	@ManyToOne
	@JoinColumn(name="lever_id")
	private TDhhLeverMst TDhhLeverMst;
	
	@Column(name="active_flag")
	private String activeFlag;
	
	//bi-directional many-to-one association to TDhhPersonaMst
	@ManyToOne
	@JoinColumn(name="persona_id")
	private TDhhPersonaMst TDhhPersonaMst;

	//bi-directional many-to-one association to TDhhUserSurveyAn
	@OneToMany(mappedBy="TDhhQuestionMst")
	private List<TDhhUserSurveyAns> TDhhUserSurveyAns;

	public TDhhQuestionMst() {
	}

	public Long getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getQuestionGroup() {
		return this.questionGroup;
	}

	public void setQuestionGroup(String questionGroup) {
		this.questionGroup = questionGroup;
	}

	public String getTopten() {
		return this.topten;
	}

	public void setTopten(String topten) {
		this.topten = topten;
	}

	public int getTuplaQuestionId() {
		return this.tuplaQuestionId;
	}

	public void setTuplaQuestionId(int tuplaQuestionId) {
		this.tuplaQuestionId = tuplaQuestionId;
	}

	public TDhhGeoMst getTDhhGeoMst() {
		return this.TDhhGeoMst;
	}

	public void setTDhhGeoMst(TDhhGeoMst TDhhGeoMst) {
		this.TDhhGeoMst = TDhhGeoMst;
	}

	public TDhhLeverMst getTDhhLeverMst() {
		return this.TDhhLeverMst;
	}

	public void setTDhhLeverMst(TDhhLeverMst TDhhLeverMst) {
		this.TDhhLeverMst = TDhhLeverMst;
	}

	public TDhhPersonaMst getTDhhPersonaMst() {
		return this.TDhhPersonaMst;
	}

	public void setTDhhPersonaMst(TDhhPersonaMst TDhhPersonaMst) {
		this.TDhhPersonaMst = TDhhPersonaMst;
	}

	public List<TDhhUserSurveyAns> getTDhhUserSurveyAns() {
		return this.TDhhUserSurveyAns;
	}

	public void setTDhhUserSurveyAns(List<TDhhUserSurveyAns> TDhhUserSurveyAns) {
		this.TDhhUserSurveyAns = TDhhUserSurveyAns;
	}
	
	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public TDhhUserSurveyAns addTDhhUserSurveyAn(TDhhUserSurveyAns TDhhUserSurveyAn) {
		getTDhhUserSurveyAns().add(TDhhUserSurveyAn);
		TDhhUserSurveyAn.setTDhhQuestionMst(this);

		return TDhhUserSurveyAn;
	}

	public TDhhUserSurveyAns removeTDhhUserSurveyAn(TDhhUserSurveyAns TDhhUserSurveyAn) {
		getTDhhUserSurveyAns().remove(TDhhUserSurveyAn);
		TDhhUserSurveyAn.setTDhhQuestionMst(null);

		return TDhhUserSurveyAn;
	}

}