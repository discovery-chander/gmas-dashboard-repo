package com.accenture.gmas.dto;

public class LeverSquadScoresDto {
	private int leverid;
	private int squadid;
	private float sq_le_score;
	private float sq_le_score_adj;
	public int getLeverid() {
		return leverid;
	}
	public void setLeverid(int leverid) {
		this.leverid = leverid;
	}
	public int getSquadid() {
		return squadid;
	}
	public void setSquadid(int squadid) {
		this.squadid = squadid;
	}
	public float getSq_le_score() {
		return sq_le_score;
	}
	public void setSq_le_score(float sq_le_score) {
		this.sq_le_score = sq_le_score;
	}
	public float getSq_le_score_adj() {
		return sq_le_score_adj;
	}
	public void setSq_le_score_adj(float sq_le_score_adj) {
		this.sq_le_score_adj = sq_le_score_adj;
	}
	
}
