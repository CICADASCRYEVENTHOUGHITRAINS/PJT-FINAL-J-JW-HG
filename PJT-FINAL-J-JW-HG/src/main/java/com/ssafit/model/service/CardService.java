package com.ssafit.model.service;

import java.util.List;

import com.ssafit.model.dto.Card;

public interface CardService {
	// 1. 카드 수집 -> DB에 카드 등록
	// int형 반환: 성공하면 1, 실패하면 0
	int postCard(Card card);
	
	// 2. 한 유저가 수집한 전체 카드 조회
	/* return:
	[{ 
		id,
		user_id,
		exercise_id,
		score,
		tier,
		collected_date 
	}] 
	 */
	List<Card> getAllCards(int userId);
	
	// 3. 한 유저가 수집한 최근 카드 n개 조회
	/* return:
	[{ 
		id,
		user_id,
		exercise_id,
		score,
		tier,
		collected_date 
	}]
	 */
	List<Card> getRecentCards();
	
	// 4. 특정 카드 정보 조회
	/* return:
 	{ 
		id,
		user_id,
		exercise_id,
		score,
		tier,
		collected_date 
	}
	 */
	Card getCardInfo(int userId, int id);
}
