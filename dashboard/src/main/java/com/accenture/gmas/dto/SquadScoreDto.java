package com.accenture.gmas.dto;

public class SquadScoreDto {
	
	private Long squadId;
	private String squadName;
	private int fte;
	private Double singleSquadMaturityLevel;
	private Double digitalHubMaturityLevel;
	private Float quesScore;
	private Double kpiScore;
	private Double docScore;
	private Double quesScoreAdj;
	private Double kpiScoreAdj;
	private Double docScoreAdj;
	
	public Long getSquadId() {
		return squadId;
	}
	public void setSquadId(Long squadId) {
		this.squadId = squadId;
	}
	public String getSquadName() {
		return squadName;
	}
	public void setSquadName(String squadName) {
		this.squadName = squadName;
	}
	public int getFte() {
		return fte;
	}
	public void setFte(int fte) {
		this.fte = fte;
	}
	public Double getSingleSquadMaturityLevel() {
		return singleSquadMaturityLevel;
	}
	public void setSingleSquadMaturityLevel(Double singleSquadMaturityLevel) {
		this.singleSquadMaturityLevel = singleSquadMaturityLevel;
	}
	public Double getDigitalHubMaturityLevel() {
		return digitalHubMaturityLevel;
	}
	public void setDigitalHubMaturityLevel(Double digitalHubMaturityLevel) {
		this.digitalHubMaturityLevel = digitalHubMaturityLevel;
	}
	public Float getQuesScore() {
		return quesScore;
	}
	public void setQuesScore(Float quesScore) {
		this.quesScore = quesScore;
	}
	public Double getKpiScore() {
		return kpiScore;
	}
	public void setKpiScore(Double kpiScore) {
		this.kpiScore = kpiScore;
	}
	public Double getDocScore() {
		return docScore;
	}
	public void setDocScore(Double docScore) {
		this.docScore = docScore;
	}
	public Double getQuesScoreAdj() {
		return quesScoreAdj;
	}
	public void setQuesScoreAdj(Double quesScoreAdj) {
		this.quesScoreAdj = quesScoreAdj;
	}
	public Double getKpiScoreAdj() {
		return kpiScoreAdj;
	}
	public void setKpiScoreAdj(Double kpiScoreAdj) {
		this.kpiScoreAdj = kpiScoreAdj;
	}
	public Double getDocScoreAdj() {
		return docScoreAdj;
	}
	public void setDocScoreAdj(Double docScoreAdj) {
		this.docScoreAdj = docScoreAdj;
	}	
}
