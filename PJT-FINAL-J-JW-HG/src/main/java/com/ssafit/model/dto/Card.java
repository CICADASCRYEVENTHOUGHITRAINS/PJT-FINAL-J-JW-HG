package com.ssafit.model.dto;

import java.io.Serializable;

public class Card implements Serializable{
	private static final long serialVersionUID = 1L; // 객체 직렬화 id
	
	// 멤버 필드
	private int id; // 카드 id(고유번호)
	private int userId; // 유저 id
	private int exerciseId; // 운동 id
	private int score; // 카드 점수
	private int tier; // 카드 티어
	private String collectedDate; // 수집된 날

	// 기본 생성자
	public Card() {}
	// 멤버 생성자
	public Card(int id, int userId, int exerciseId, int score, int tier, String collectedDate) {
		super();
		this.id = id;
		this.userId = userId;
		this.exerciseId = exerciseId;
		this.score = score;
		this.tier = tier;
		this.collectedDate = collectedDate;
	}
	
	// getters and setters //
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getExerciseId() {
		return exerciseId;
	}
	public void setExerciseId(int exerciseId) {
		this.exerciseId = exerciseId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getTier() {
		return tier;
	}
	public void setTier(int tier) {
		this.tier = tier;
	}
	public String getCollectedDate() {
		return collectedDate;
	}
	public void setCollectedDate(String collectedDate) {
		this.collectedDate = collectedDate;
	}
	// getters and setters //
	
	
	@Override
	public String toString() {
		return "Card [id=" + id + ", userId=" + userId + ", exerciseId=" + exerciseId + ", score=" + score + ", tier="
				+ tier + ", collectedDate=" + collectedDate + "]";
	}	
	
}
