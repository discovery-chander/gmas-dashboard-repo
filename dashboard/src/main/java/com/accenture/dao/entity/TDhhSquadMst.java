package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the t_dhh_squad_mst database table.
 * 
 */
@Entity
@Table(name="t_dhh_squad_mst")
@NamedQuery(name="TDhhSquadMst.findAll", query="SELECT t FROM TDhhSquadMst t")
public class TDhhSquadMst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="squad_id")
	private Long squadId;

	private int fte;

	@Column(name="squad_name")
	private String squadName;
	
	@Column(name="active_flag")
	private String activeFlag;
	

	//bi-directional many-to-one association to TDhhGeoMst
	@ManyToOne
	@JoinColumn(name="geo_id")
	private TDhhGeoMst TDhhGeoMst;

	//bi-directional many-to-one association to TDhhSurveyMst
	@ManyToOne
	@JoinColumn(name="survey_id")
	private TDhhSurveyMst TDhhSurveyMst;

	//bi-directional many-to-one association to TDhhUserMst
	@OneToMany(mappedBy="TDhhSquadMst")
	private List<TDhhUserMst> TDhhUserMsts;

	public TDhhSquadMst() {
	}

	public Long getSquadId() {
		return this.squadId;
	}

	public void setSquadId(Long squadId) {
		this.squadId = squadId;
	}

	public int getFte() {
		return this.fte;
	}

	public void setFte(int fte) {
		this.fte = fte;
	}

	public String getSquadName() {
		return this.squadName;
	}

	public void setSquadName(String squadName) {
		this.squadName = squadName;
	}

	public TDhhGeoMst getTDhhGeoMst() {
		return this.TDhhGeoMst;
	}

	public void setTDhhGeoMst(TDhhGeoMst TDhhGeoMst) {
		this.TDhhGeoMst = TDhhGeoMst;
	}

	public TDhhSurveyMst getTDhhSurveyMst() {
		return this.TDhhSurveyMst;
	}

	public void setTDhhSurveyMst(TDhhSurveyMst TDhhSurveyMst) {
		this.TDhhSurveyMst = TDhhSurveyMst;
	}

	public List<TDhhUserMst> getTDhhUserMsts() {
		return this.TDhhUserMsts;
	}

	public void setTDhhUserMsts(List<TDhhUserMst> TDhhUserMsts) {
		this.TDhhUserMsts = TDhhUserMsts;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public TDhhUserMst addTDhhUserMst(TDhhUserMst TDhhUserMst) {
		getTDhhUserMsts().add(TDhhUserMst);
		TDhhUserMst.setTDhhSquadMst(this);

		return TDhhUserMst;
	}

	public TDhhUserMst removeTDhhUserMst(TDhhUserMst TDhhUserMst) {
		getTDhhUserMsts().remove(TDhhUserMst);
		TDhhUserMst.setTDhhSquadMst(null);

		return TDhhUserMst;
	}

}