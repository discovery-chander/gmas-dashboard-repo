package com.accenture.gmas.dto;

import java.util.List;

public class LeverScoreDto {
	private Long leverId;
	private String leverName;
	private Double aggregation;
	private List<SquadScoreDto> squadScoreList;	
	
	public Long getLeverId() {
		return leverId;
	}
	public void setLeverId(Long leverId) {
		this.leverId = leverId;
	}
	public String getLeverName() {
		return leverName;
	}
	public void setLeverName(String leverName) {
		this.leverName = leverName;
	}
	public Double getAggregation() {
		return aggregation;
	}
	public void setAggregation(Double aggregation) {
		this.aggregation = aggregation;
	}
	public List<SquadScoreDto> getSquadScoreList() {
		return squadScoreList;
	}
	public void setSquadScoreList(List<SquadScoreDto> squadScoreList) {
		this.squadScoreList = squadScoreList;
	}	
}
