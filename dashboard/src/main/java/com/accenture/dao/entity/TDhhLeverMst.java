package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the t_dhh_lever_mst database table.
 * 
 */
@Entity
@Table(name = "t_dhh_lever_mst")
@NamedQuery(name = "TDhhLeverMst.findAll", query = "SELECT t FROM TDhhLeverMst t")
public class TDhhLeverMst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "lever_id")
	private Long leverId;

	@Column(name = "lever_desc")
	private String leverDesc;

	@Column(name = "lever_name")
	private String leverName;

	@Column(name = "active_flag")
	private String activeFlag;

	// bi-directional many-to-one association to TDhhQuestionMst
	@OneToMany(mappedBy = "TDhhLeverMst")
	private List<TDhhQuestionMst> TDhhQuestionMsts;

	// bi-directional many-to-one association to TDhhQuestionMst
	@OneToMany(mappedBy = "tDhhLeverMst")
	private List<TDhhKpiMst> tdhhKpiMsts;

	// bi-directional many-to-one association to TDhhQuestionMst
	@OneToMany(mappedBy = "tDhhLeverMst")
	private List<TDhhDocumentMst> tDhhDocMsts;
	
	

	public List<TDhhDocumentMst> gettDhhDocMsts() {
		return tDhhDocMsts;
	}

	public void settDhhDocMsts(List<TDhhDocumentMst> tDhhDocMsts) {
		this.tDhhDocMsts = tDhhDocMsts;
	}

	public List<TDhhKpiMst> getTdhhKpiMsts() {
		return tdhhKpiMsts;
	}

	public void setTdhhKpiMsts(List<TDhhKpiMst> tdhhKpiMsts) {
		this.tdhhKpiMsts = tdhhKpiMsts;
	}

	public TDhhLeverMst() {
	}

	public Long getLeverId() {
		return this.leverId;
	}

	public void setLeverId(Long leverId) {
		this.leverId = leverId;
	}

	public String getLeverDesc() {
		return this.leverDesc;
	}

	public void setLeverDesc(String leverDesc) {
		this.leverDesc = leverDesc;
	}

	public String getLeverName() {
		return this.leverName;
	}

	public void setLeverName(String leverName) {
		this.leverName = leverName;
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
		TDhhQuestionMst.setTDhhLeverMst(this);

		return TDhhQuestionMst;
	}

	public TDhhQuestionMst removeTDhhQuestionMst(TDhhQuestionMst TDhhQuestionMst) {
		getTDhhQuestionMsts().remove(TDhhQuestionMst);
		TDhhQuestionMst.setTDhhLeverMst(null);

		return TDhhQuestionMst;
	}

}