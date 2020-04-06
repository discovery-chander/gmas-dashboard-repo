package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the t_dhh_persona_mst database table.
 * 
 */
@Entity
@Table(name="t_dhh_persona_mst")
@NamedQuery(name="TDhhPersonaMst.findAll", query="SELECT t FROM TDhhPersonaMst t")
public class TDhhPersonaMst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="persona_id")
	private Long personaId;

	@Column(name="persona_name")
	private String personaName;
	
	@Column(name="active_flag")
	private String activeFlag;
	
	//bi-directional many-to-one association to TDhhSurveyMst
	@ManyToOne
	@JoinColumn(name="survey_id")
	private TDhhSurveyMst TDhhSurveyMst;

	//bi-directional many-to-one association to TDhhQuestionMst
	@OneToMany(mappedBy="TDhhPersonaMst")
	private List<TDhhQuestionMst> TDhhQuestionMsts;

	//bi-directional many-to-one association to TDhhUserMst
	@OneToMany(mappedBy="TDhhPersonaMst")
	private List<TDhhUserMst> TDhhUserMsts;

	public TDhhPersonaMst() {
	}

	public Long getPersonaId() {
		return this.personaId;
	}

	public void setPersonaId(Long personaId) {
		this.personaId = personaId;
	}

	public String getPersonaName() {
		return this.personaName;
	}

	public void setPersonaName(String personaName) {
		this.personaName = personaName;
	}

	public TDhhSurveyMst getTDhhSurveyMst() {
		return this.TDhhSurveyMst;
	}

	public void setTDhhSurveyMst(TDhhSurveyMst TDhhSurveyMst) {
		this.TDhhSurveyMst = TDhhSurveyMst;
	}

	public List<TDhhQuestionMst> getTDhhQuestionMsts() {
		return this.TDhhQuestionMsts;
	}

	public void setTDhhQuestionMsts(List<TDhhQuestionMst> TDhhQuestionMsts) {
		this.TDhhQuestionMsts = TDhhQuestionMsts;
	}
	
	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public TDhhQuestionMst addTDhhQuestionMst(TDhhQuestionMst TDhhQuestionMst) {
		getTDhhQuestionMsts().add(TDhhQuestionMst);
		TDhhQuestionMst.setTDhhPersonaMst(this);

		return TDhhQuestionMst;
	}

	public TDhhQuestionMst removeTDhhQuestionMst(TDhhQuestionMst TDhhQuestionMst) {
		getTDhhQuestionMsts().remove(TDhhQuestionMst);
		TDhhQuestionMst.setTDhhPersonaMst(null);

		return TDhhQuestionMst;
	}

	public List<TDhhUserMst> getTDhhUserMsts() {
		return this.TDhhUserMsts;
	}

	public void setTDhhUserMsts(List<TDhhUserMst> TDhhUserMsts) {
		this.TDhhUserMsts = TDhhUserMsts;
	}

	public TDhhUserMst addTDhhUserMst(TDhhUserMst TDhhUserMst) {
		getTDhhUserMsts().add(TDhhUserMst);
		TDhhUserMst.setTDhhPersonaMst(this);

		return TDhhUserMst;
	}

	public TDhhUserMst removeTDhhUserMst(TDhhUserMst TDhhUserMst) {
		getTDhhUserMsts().remove(TDhhUserMst);
		TDhhUserMst.setTDhhPersonaMst(null);

		return TDhhUserMst;
	}

}