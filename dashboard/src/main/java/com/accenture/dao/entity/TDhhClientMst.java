package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the t_dhh_client_mst database table.
 * 
 */
@Entity
@Table(name="t_dhh_client_mst")
@NamedQuery(name="TDhhClientMst.findAll", query="SELECT t FROM TDhhClientMst t")
public class TDhhClientMst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="client_id")
	private Long clientId;

	@Column(name="client_desc")
	private String clientDesc;

	@Column(name="client_name")
	private String clientName;
	
	@Column(name="active_flag")
	private String activeFlag;

	//bi-directional many-to-one association to TDhhSurveyMst
	@OneToMany(mappedBy="TDhhClientMst")
	private List<TDhhSurveyMst> TDhhSurveyMsts;

	public TDhhClientMst() {
	}

	public Long getClientId() {
		return this.clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public String getClientDesc() {
		return this.clientDesc;
	}

	public void setClientDesc(String clientDesc) {
		this.clientDesc = clientDesc;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public List<TDhhSurveyMst> getTDhhSurveyMsts() {
		return this.TDhhSurveyMsts;
	}

	public void setTDhhSurveyMsts(List<TDhhSurveyMst> TDhhSurveyMsts) {
		this.TDhhSurveyMsts = TDhhSurveyMsts;
	}	
	
	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public TDhhSurveyMst addTDhhSurveyMst(TDhhSurveyMst TDhhSurveyMst) {
		getTDhhSurveyMsts().add(TDhhSurveyMst);
		TDhhSurveyMst.setTDhhClientMst(this);

		return TDhhSurveyMst;
	}

	public TDhhSurveyMst removeTDhhSurveyMst(TDhhSurveyMst TDhhSurveyMst) {
		getTDhhSurveyMsts().remove(TDhhSurveyMst);
		TDhhSurveyMst.setTDhhClientMst(null);

		return TDhhSurveyMst;
	}

}