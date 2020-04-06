package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the t_dhh_document_mst database table.
 * 
 */
@Entity
@Table(name = "t_dhh_document_mst")
@NamedQuery(name = "TDhhDocumentMst.findAll", query = "SELECT t FROM TDhhDocumentMst t")
public class TDhhDocumentMst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "document_id")
	private Long documentId;

	@Column(name = "document_desc")
	private String documentDesc;

	@Column(name = "document_group")
	private String documentGroup;

	@Column(name = "active_flag")
	private String activeFlag;

	// bi-directional many-to-one association to TDhhTuplaMst
	@ManyToOne
	@JoinColumn(name = "tupla_id")
	private TDhhTuplaMst TDhhTuplaMst;

	// bi-directional many-to-one association to TDhhTuplaMst
	@ManyToOne
	@JoinColumn(name = "lever_id")
	private TDhhLeverMst tDhhLeverMst;

	public TDhhLeverMst gettDhhLeverMst() {
		return tDhhLeverMst;
	}

	public void settDhhLeverMst(TDhhLeverMst tDhhLeverMst) {
		this.tDhhLeverMst = tDhhLeverMst;
	}

	public TDhhDocumentMst() {
	}

	public Long getDocumentId() {
		return this.documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getDocumentDesc() {
		return this.documentDesc;
	}

	public void setDocumentDesc(String documentDesc) {
		this.documentDesc = documentDesc;
	}

	public String getDocumentGroup() {
		return this.documentGroup;
	}

	public void setDocumentGroup(String documentGroup) {
		this.documentGroup = documentGroup;
	}

	public TDhhTuplaMst getTDhhTuplaMst() {
		return this.TDhhTuplaMst;
	}

	public void setTDhhTuplaMst(TDhhTuplaMst TDhhTuplaMst) {
		this.TDhhTuplaMst = TDhhTuplaMst;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
}