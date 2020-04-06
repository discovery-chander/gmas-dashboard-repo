package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the t_dhh_tupla_mst database table.
 * 
 */
@Entity
@Table(name="t_dhh_tupla_mst")
@NamedQuery(name="TDhhTuplaMst.findAll", query="SELECT t FROM TDhhTuplaMst t")
public class TDhhTuplaMst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="tupla_id")
	private Long tuplaId;
	
	@Column(name="active_flag")
	private String activeFlag;
	
	@Column(name="tupla_name")
	private String tuplaName;
	
	//bi-directional many-to-one association to TDhhDocumentMst
	@OneToMany(mappedBy="TDhhTuplaMst")
	private List<TDhhDocumentMst> TDhhDocumentMsts;

	//bi-directional many-to-one association to TDhhKpiMst
	@OneToMany(mappedBy="TDhhTuplaMst")
	private List<TDhhKpiMst> TDhhKpiMsts;

	//bi-directional many-to-one association to TDhhSurveyMst
	@OneToMany(mappedBy="TDhhTuplaMst")
	private List<TDhhSurveyMst> TDhhSurveyMsts;

	public TDhhTuplaMst() {
	}

	public Long getTuplaId() {
		return this.tuplaId;
	}

	public void setTuplaId(Long tuplaId) {
		this.tuplaId = tuplaId;
	}

	public List<TDhhDocumentMst> getTDhhDocumentMsts() {
		return this.TDhhDocumentMsts;
	}

	public void setTDhhDocumentMsts(List<TDhhDocumentMst> TDhhDocumentMsts) {
		this.TDhhDocumentMsts = TDhhDocumentMsts;
	}	

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	public String getTuplaName() {
		return tuplaName;
	}

	public void setTuplaName(String tuplaName) {
		this.tuplaName = tuplaName;
	}

	public TDhhDocumentMst addTDhhDocumentMst(TDhhDocumentMst TDhhDocumentMst) {
		getTDhhDocumentMsts().add(TDhhDocumentMst);
		TDhhDocumentMst.setTDhhTuplaMst(this);

		return TDhhDocumentMst;
	}

	public TDhhDocumentMst removeTDhhDocumentMst(TDhhDocumentMst TDhhDocumentMst) {
		getTDhhDocumentMsts().remove(TDhhDocumentMst);
		TDhhDocumentMst.setTDhhTuplaMst(null);

		return TDhhDocumentMst;
	}

	public List<TDhhKpiMst> getTDhhKpiMsts() {
		return this.TDhhKpiMsts;
	}

	public void setTDhhKpiMsts(List<TDhhKpiMst> TDhhKpiMsts) {
		this.TDhhKpiMsts = TDhhKpiMsts;
	}

	public TDhhKpiMst addTDhhKpiMst(TDhhKpiMst TDhhKpiMst) {
		getTDhhKpiMsts().add(TDhhKpiMst);
		TDhhKpiMst.setTDhhTuplaMst(this);

		return TDhhKpiMst;
	}

	public TDhhKpiMst removeTDhhKpiMst(TDhhKpiMst TDhhKpiMst) {
		getTDhhKpiMsts().remove(TDhhKpiMst);
		TDhhKpiMst.setTDhhTuplaMst(null);

		return TDhhKpiMst;
	}

	public List<TDhhSurveyMst> getTDhhSurveyMsts() {
		return this.TDhhSurveyMsts;
	}

	public void setTDhhSurveyMsts(List<TDhhSurveyMst> TDhhSurveyMsts) {
		this.TDhhSurveyMsts = TDhhSurveyMsts;
	}

	public TDhhSurveyMst addTDhhSurveyMst(TDhhSurveyMst TDhhSurveyMst) {
		getTDhhSurveyMsts().add(TDhhSurveyMst);
		TDhhSurveyMst.setTDhhTuplaMst(this);

		return TDhhSurveyMst;
	}

	public TDhhSurveyMst removeTDhhSurveyMst(TDhhSurveyMst TDhhSurveyMst) {
		getTDhhSurveyMsts().remove(TDhhSurveyMst);
		TDhhSurveyMst.setTDhhTuplaMst(null);

		return TDhhSurveyMst;
	}

}