package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the t_dhh_user_mst database table.
 * 
 */
@Entity
@Table(name="t_dhh_user_mst")
@NamedQuery(name="TDhhUserMst.findAll", query="SELECT t FROM TDhhUserMst t")
public class TDhhUserMst implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="user_id")
	private Long userId;

	@Column(name="user_flag")
	private String userFlag;

	@Column(name="user_name")
	private String userName;
	
	@Column(name="active_flag")
	private String activeFlag;

	//bi-directional many-to-one association to TDhhSurveyActivity
	@OneToMany(mappedBy="TDhhUserMst")
	private List<TDhhSurveyActivity> TDhhSurveyActivities;

	//bi-directional many-to-one association to TDhhPersonaMst
	@ManyToOne
	@JoinColumn(name="persona_id")
	private TDhhPersonaMst TDhhPersonaMst;

	//bi-directional many-to-one association to TDhhSquadMst
	@ManyToOne
	@JoinColumn(name="squad_id")
	private TDhhSquadMst TDhhSquadMst;

	public TDhhUserMst() {
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserFlag() {
		return this.userFlag;
	}

	public void setUserFlag(String userFlag) {
		this.userFlag = userFlag;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<TDhhSurveyActivity> getTDhhSurveyActivities() {
		return this.TDhhSurveyActivities;
	}

	public void setTDhhSurveyActivities(List<TDhhSurveyActivity> TDhhSurveyActivities) {
		this.TDhhSurveyActivities = TDhhSurveyActivities;
	}
	
	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public TDhhSurveyActivity addTDhhSurveyActivity(TDhhSurveyActivity TDhhSurveyActivity) {
		getTDhhSurveyActivities().add(TDhhSurveyActivity);
		TDhhSurveyActivity.setTDhhUserMst(this);

		return TDhhSurveyActivity;
	}

	public TDhhSurveyActivity removeTDhhSurveyActivity(TDhhSurveyActivity TDhhSurveyActivity) {
		getTDhhSurveyActivities().remove(TDhhSurveyActivity);
		TDhhSurveyActivity.setTDhhUserMst(null);

		return TDhhSurveyActivity;
	}

	public TDhhPersonaMst getTDhhPersonaMst() {
		return this.TDhhPersonaMst;
	}

	public void setTDhhPersonaMst(TDhhPersonaMst TDhhPersonaMst) {
		this.TDhhPersonaMst = TDhhPersonaMst;
	}

	public TDhhSquadMst getTDhhSquadMst() {
		return this.TDhhSquadMst;
	}

	public void setTDhhSquadMst(TDhhSquadMst TDhhSquadMst) {
		this.TDhhSquadMst = TDhhSquadMst;
	}

}