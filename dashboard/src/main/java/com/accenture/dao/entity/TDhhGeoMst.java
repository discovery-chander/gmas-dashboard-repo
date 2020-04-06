package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the t_dhh_geo_mst database table.
 * 
 */
@Entity
@Table(name="t_dhh_geo_mst")
@NamedQuery(name="TDhhGeoMst.findAll", query="SELECT t FROM TDhhGeoMst t")
public class TDhhGeoMst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="geo_id")
	private Long geoId;

	@Column(name="geo_name")
	private String geoName;
	
	@Column(name="active_flag")
	private String activeFlag;
	
	//bi-directional many-to-one association to TDhhQuestionMst
	@OneToMany(mappedBy="TDhhGeoMst")
	private List<TDhhQuestionMst> TDhhQuestionMsts;

	//bi-directional many-to-one association to TDhhSquadMst
	@OneToMany(mappedBy="TDhhGeoMst")
	private List<TDhhSquadMst> TDhhSquadMsts;

	public TDhhGeoMst() {
	}

	public Long getGeoId() {
		return this.geoId;
	}

	public void setGeoId(Long geoId) {
		this.geoId = geoId;
	}

	public String getGeoName() {
		return this.geoName;
	}

	public void setGeoName(String geoName) {
		this.geoName = geoName;
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
		TDhhQuestionMst.setTDhhGeoMst(this);

		return TDhhQuestionMst;
	}

	public TDhhQuestionMst removeTDhhQuestionMst(TDhhQuestionMst TDhhQuestionMst) {
		getTDhhQuestionMsts().remove(TDhhQuestionMst);
		TDhhQuestionMst.setTDhhGeoMst(null);

		return TDhhQuestionMst;
	}

	public List<TDhhSquadMst> getTDhhSquadMsts() {
		return this.TDhhSquadMsts;
	}

	public void setTDhhSquadMsts(List<TDhhSquadMst> TDhhSquadMsts) {
		this.TDhhSquadMsts = TDhhSquadMsts;
	}

	public TDhhSquadMst addTDhhSquadMst(TDhhSquadMst TDhhSquadMst) {
		getTDhhSquadMsts().add(TDhhSquadMst);
		TDhhSquadMst.setTDhhGeoMst(this);

		return TDhhSquadMst;
	}

	public TDhhSquadMst removeTDhhSquadMst(TDhhSquadMst TDhhSquadMst) {
		getTDhhSquadMsts().remove(TDhhSquadMst);
		TDhhSquadMst.setTDhhGeoMst(null);

		return TDhhSquadMst;
	}

}