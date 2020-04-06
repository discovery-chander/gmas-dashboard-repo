package com.accenture.gmas.dto;

import java.util.List;

public class LeverMaturityScoreDto {
	
	private Double maturityLevel;
	private List<LeverScoreDto> leverScoreList;
	public Double getMaturityLevel() {
		return maturityLevel;
	}
	public void setMaturityLevel(Double maturityLevel) {
		this.maturityLevel = maturityLevel;
	}
	public List<LeverScoreDto> getLeverScoreList() {
		return leverScoreList;
	}
	public void setLeverScoreList(List<LeverScoreDto> leverScoreList) {
		this.leverScoreList = leverScoreList;
	}
}
