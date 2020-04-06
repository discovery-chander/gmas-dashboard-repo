package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the t_dhh_survey_activity database table.
 * 
 */
@Entity
@Table(name="t_dhh_survey_activity")
@NamedQuery(name="TDhhSurveyActivity.findAll", query="SELECT t FROM TDhhSurveyActivity t")
public class TDhhSurveyActivity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="activity_id")
	private Long activityId;
	
	@Column(name="active_flag")
	private String activeFlag;
	
	//bi-directional many-to-one association to TDhhSurveyMst
	@ManyToOne
	@JoinColumn(name="survey_id")
	private TDhhSurveyMst TDhhSurveyMst;

	//bi-directional many-to-one association to TDhhUserMst
	@ManyToOne
	@JoinColumn(name="user_id")
	private TDhhUserMst TDhhUserMst;

	//bi-directional many-to-one association to TDhhUserSurveyAn
	@OneToMany(mappedBy="TDhhSurveyActivity")
	private List<TDhhUserSurveyAns> TDhhUserSurveyAns;

	public TDhhSurveyActivity() {
	}

	public Long getActivityId() {
		return this.activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public TDhhSurveyMst getTDhhSurveyMst() {
		return this.TDhhSurveyMst;
	}

	public void setTDhhSurveyMst(TDhhSurveyMst TDhhSurveyMst) {
		this.TDhhSurveyMst = TDhhSurveyMst;
	}

	public TDhhUserMst getTDhhUserMst() {
		return this.TDhhUserMst;
	}

	public void setTDhhUserMst(TDhhUserMst TDhhUserMst) {
		this.TDhhUserMst = TDhhUserMst;
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
		TDhhUserSurveyAn.setTDhhSurveyActivity(this);

		return TDhhUserSurveyAn;
	}

	public TDhhUserSurveyAns removeTDhhUserSurveyAn(TDhhUserSurveyAns TDhhUserSurveyAn) {
		getTDhhUserSurveyAns().remove(TDhhUserSurveyAn);
		TDhhUserSurveyAn.setTDhhSurveyActivity(null);

		return TDhhUserSurveyAn;
	}

}