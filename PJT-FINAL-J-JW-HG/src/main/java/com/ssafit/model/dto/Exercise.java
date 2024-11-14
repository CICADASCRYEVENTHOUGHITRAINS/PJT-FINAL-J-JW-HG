package com.ssafit.model.dto;

import java.io.Serializable;

public class Exercise implements Serializable{
	// 객체 직렬화 번호
	private static final long serialVersionUID = 1L;
	
	// 멤버 필드
	private int id; // 운동을 UNIQUE하게 관리할 정수형 식별 번호
	private String part; // 운동 부위
	private String name; // 운동명 e.g.) [손목 스트레칭]
	private String info; // 운동 내용 및 정보 e.g.) [한 쪽 손바닥을 천장을 보게 한 후 다른 쪽 손으로 손가락을 움켜쥐고 아래로 끌여당겨 스트레칭]
	private String time; // 운동 시간 e.g.) [30]: 각 손마다 30초씩 시행
			
	// 생성자
	public Exercise() {} // 기본
	// 매개변수 받는 생성자
	public Exercise(int id, String part, String name, String info, String time) {
		this.id = id;
		this.part = part;
		this.name = name;
		this.info = info;
		this.time = time;
	}
		
	// 게터 세터
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPart() {
		return part;
	}
	public void setPart(String part) {
		this.part = part;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// 메소드
	@Override
	public String toString() {
		return "Exercise [id=" + id + ", part=" + part + ", name=" + name + ", info=" + info + ", time=" + time + "]";
	}
}
