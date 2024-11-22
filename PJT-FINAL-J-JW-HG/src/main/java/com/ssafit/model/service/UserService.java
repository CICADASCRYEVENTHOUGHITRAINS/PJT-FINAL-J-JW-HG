package com.ssafit.model.service;

import com.ssafit.model.dto.User;

public interface UserService {
	//-----------------------------------------------------------//
	// 로직 - UserController
	//-----------------------------------------------------------//
	/** 1. 특정 유저 정보 전체 조회
	 * @param userId
	 * @return: User
	 * {
	 * (int) id,
	 * (String) loginId,
	 * (String) userName,
	 * (int) score,
	 * (int) totalCardCount,
	 * (int) tier
	 * }
	 */
	User getUserInfo(int userId);
	
	/** 2. 유저의 건강력 조회
	 * @param userId
	 * @return: int
	 */
	int getUserScore(int userId);
	
	/** 3. 유저가 건강 관리한 연속 일수
	 * @param userId
	 * @return: int
	 */
	int getUserStreak(int userId);
	
	/** 4. 유저의 등급 조회 
	 * @param userId
	 * @return: int
	 */
	int getUserTier(int userId);
	
	/** 5. 유저가 획득한 총 카드 수 조회
	 * @param userId
	 * @return: int
	 */
	int getUserTotalCardCount(int userId);
	
	/** 6. 유저가 획득한 총 카드 수 업데이트
	 * @param userId
	 * @param newTotalCardCount
	 * @return: int (0/1)
	 */
	int updateUserTotalCardCount(int userId, int newTotalCardCount);
	
	/** 7. 건강 점수 업데이트
	 * @param userId
	 * @param newUserScore
	 * @return: int (0/1)
	 */
	int updateUserScore(int userId, int newUserScore);
	
	//-----------------------------------------------------------//
	// 로직 - AccountController
	//-----------------------------------------------------------//
	/** 8. 로그인 시도에 따른 특정 유저의 비밀번호 조회
	 * @param loginId
	 * @param password
	 * @return: User
	 * {
	 * (int) id,
	 * (String) password,
	 * (String) loginId,
	 * (String) userName,
	 * (int) score,
	 * (int) totalCardCount,
	 * (int) tier
	 * }
	 */
	String getInfoForLoginTry(String loginId, String password);
	
	/** 9. 회원가입 시도
	 * @param user
	 * @return: int (0/1)
	 */
	int tryRegister(User user);
}
