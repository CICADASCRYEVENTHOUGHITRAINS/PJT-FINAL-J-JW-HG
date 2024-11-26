package com.ssafit.model.dao;

import java.util.List;

import com.ssafit.model.dto.Card;
import com.ssafit.model.dto.CollectedCard;

public interface CardDao {
	//-----------------------------------------------------------//
	// 로직
	//-----------------------------------------------------------//
	/** 1. 카드 수집 -> DB에 카드 등록
	 * @param Card
	 * @return: int형 반환: 성공하면 1, 실패하면 0
	 */
	int postCard(Card card);
	
	/** 2. 한 유저가 수집한 전체 카드 조회
	 * @param userId
	 * @return: List<Card>
	 * [{
	 * (int) id,
	 * (int) userId,
	 * (int) exerciseId,
	 * (int) score,
	 * (int) tier,
	 * (String) collectedDate
	 * }] 
	 */
	List<CollectedCard> getAllCards(int userId);
	
	/** 3. 한 유저가 수집한 최근 카드 n개 조회
	 * @param userId
	 * @param cardNumber
	 * @return: List<Card>
	 * [{
	 * (int) id,
	 * (int) user_id,
	 * (int) exercise_id,
	 * (int) score,
	 * (int) tier,
	 * (String) collected_date
	 * }]
	 */
	List<Card> getRecentCards(int userId, int cardNumber);
	
	/** 4. 특정 카드 정보 조회
	 * @param (int) userId
	 * @param (int) id
	 * @return: Card
	 * {
	 * (int) id,
	 * (int) user_id,
	 * (int) exercise_id,
	 * (int) score,
	 * (int) tier,
	 * (String) collected_date
	 * }
	 */
	Card getCardInfo(int userId, int id);

}
