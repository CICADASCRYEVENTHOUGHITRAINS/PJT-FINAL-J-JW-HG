package com.ssafit.model.service;

import com.ssafit.model.dto.User;

public interface UserService {
	// 1. 특정 유저 정보 전체 조회
	User getUserInfo(int userId);
	
	// 2. 유저의 건강력 조회
	int getUserScore(int userId);
	
	// 3. 유저가 건강 관리한 연속 일수
	int getUserStreak(int userId);
	
	// 4. 유저의 등급 조회
	int getUserTier(int userId);
	
	// 5. 쥬어가 획득한 총 카드 수 조회
	int getUserTotalCardCount(int userId);
}
