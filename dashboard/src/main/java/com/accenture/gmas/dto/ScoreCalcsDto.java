package com.accenture.gmas.dto;

public class ScoreCalcsDto {
	private int qid;
	private float qans;
	private int squadid;
	private int leverid;
	private String username;
	private String comment;
	
	public float getQans() {
		return qans;
	}
	public void setQans(float qans) {
		this.qans = qans;
	}
	public int getSquadid() {
		return squadid;
	}
	public void setSquadid(int squadid) {
		this.squadid = squadid;
	}
	public int getLeverid() {
		return leverid;
	}
	public void setLeverid(int leverid) {
		this.leverid = leverid;
	}
	public int getQid() {
		return qid;
	}
	public void setQid(int qid) {
		this.qid = qid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}	

}
