package com.accenture.dao.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_dhh_squad_lever_score database table.
 * 
 */
@Entity
@Table(name="t_dhh_squad_lever_score")
@NamedQuery(name="TDhhSquadLeverScore.findAll", query="SELECT t FROM TDhhSquadLeverScore t")
public class TDhhSquadLeverScore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="squad_lever_score_id")
	private int squadLeverScoreId;

	@Column(name="active_flag")
	private String activeFlag;

	@Column(name="digital_hub_maturity_level")
	private double digitalHubMaturityLevel;

	@Column(name="doc_score")
	private double docScore;

	@Column(name="doc_score_adj")
	private double docScoreAdj;

	@Column(name="kpi_score")
	private double kpiScore;

	@Column(name="kpi_score_adj")
	private double kpiScoreAdj;

	@Column(name="ques_score")
	private float quesScore;

	@Column(name="ques_score_adj")
	private double quesScoreAdj;

	@Column(name="single_squad_maturity_level")
	private double singleSquadMaturityLevel;

	//bi-directional many-to-one association to TDhhLeverMst
	@ManyToOne
	@JoinColumn(name="lever_id")
	private TDhhLeverMst TDhhLeverMst;

	//bi-directional many-to-one association to TDhhSquadMst
	@ManyToOne
	@JoinColumn(name="squad_id")
	private TDhhSquadMst TDhhSquadMst;

	public TDhhSquadLeverScore() {
	}

	public int getSquadLeverScoreId() {
		return this.squadLeverScoreId;
	}

	public void setSquadLeverScoreId(int squadLeverScoreId) {
		this.squadLeverScoreId = squadLeverScoreId;
	}

	public String getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public double getDigitalHubMaturityLevel() {
		return this.digitalHubMaturityLevel;
	}

	public void setDigitalHubMaturityLevel(double digitalHubMaturityLevel) {
		this.digitalHubMaturityLevel = digitalHubMaturityLevel;
	}

	public double getDocScore() {
		return this.docScore;
	}

	public void setDocScore(double docScore) {
		this.docScore = docScore;
	}

	public double getDocScoreAdj() {
		return this.docScoreAdj;
	}

	public void setDocScoreAdj(double docScoreAdj) {
		this.docScoreAdj = docScoreAdj;
	}

	public double getKpiScore() {
		return this.kpiScore;
	}

	public void setKpiScore(double kpiScore) {
		this.kpiScore = kpiScore;
	}

	public double getKpiScoreAdj() {
		return this.kpiScoreAdj;
	}

	public void setKpiScoreAdj(double kpiScoreAdj) {
		this.kpiScoreAdj = kpiScoreAdj;
	}

	
	public float getQuesScore() {
		return quesScore;
	}

	public void setQuesScore(float quesScore) {
		this.quesScore = quesScore;
	}

	public double getQuesScoreAdj() {
		return this.quesScoreAdj;
	}

	public void setQuesScoreAdj(double quesScoreAdj) {
		this.quesScoreAdj = quesScoreAdj;
	}

	public double getSingleSquadMaturityLevel() {
		return this.singleSquadMaturityLevel;
	}

	public void setSingleSquadMaturityLevel(double singleSquadMaturityLevel) {
		this.singleSquadMaturityLevel = singleSquadMaturityLevel;
	}

	public TDhhLeverMst getTDhhLeverMst() {
		return this.TDhhLeverMst;
	}

	public void setTDhhLeverMst(TDhhLeverMst TDhhLeverMst) {
		this.TDhhLeverMst = TDhhLeverMst;
	}

	public TDhhSquadMst getTDhhSquadMst() {
		return this.TDhhSquadMst;
	}

	public void setTDhhSquadMst(TDhhSquadMst TDhhSquadMst) {
		this.TDhhSquadMst = TDhhSquadMst;
	}

}