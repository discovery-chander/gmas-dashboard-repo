package com.accenture.gmas.dto;

public class DocumentAnsDto {
	private int documentId;
	private String answer;
	private String userName;
	private int leverId;
	private int squadId;
	private String comment;
	
	public int getDocumentId() {
		return documentId;
	}
	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getLeverId() {
		return leverId;
	}
	public void setLeverId(int leverId) {
		this.leverId = leverId;
	}
	public int getSquadId() {
		return squadId;
	}
	public void setSquadId(int squadId) {
		this.squadId = squadId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
