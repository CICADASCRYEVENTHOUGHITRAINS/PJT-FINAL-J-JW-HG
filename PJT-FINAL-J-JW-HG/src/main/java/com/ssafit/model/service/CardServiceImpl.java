package com.ssafit.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafit.model.dao.CardDao;
import com.ssafit.model.dto.Card;

@Service
public class CardServiceImpl implements CardService {
	private final CardDao cardDao;
		
	// 생성자로 의존성 주입
	public CardServiceImpl(CardDao cardDao) {		
		this.cardDao = cardDao;
	}

	// 1. 카드 수집 -> DB에 카드 등록
	// int형 반환: 성공하면 1, 실패하면 0
	@Override
	public int postCard(Card card) {
		return cardDao.postCard(card);		
	}

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
	@Override
	public List<Card> getAllCards(int userId) {
		List<Card> cardList = cardDao.getAllCards(userId);
		// 데이터가 없거나 가져오는 데에 실패했다면
		if(cardList == null | cardList.size() == 0) {
			System.out.println("service에서 통신. 카드 리스트를 불러올 수 없습니다.");
			return null;
		}
		
		return cardList;
	}

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
	@Override
	public List<Card> getRecentCards(int userId, int cardNumber) {
		List<Card> cardRecentList = cardDao.getRecentCards(userId, cardNumber);
		
		// 데이터가 없거나 가져오는 데에 실패했다면
		if(cardRecentList == null | cardRecentList.size() == 0) {
			System.out.println("service에서 통신. 카드 리스트를 불러올 수 없습니다.");
			return null;
		}
		return cardRecentList;
	}

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
	@Override
	public Card getCardInfo(int userId, int id) {
		Card cardInfo = cardDao.getCardInfo(userId, id);
		
		// 데이터가 없거나 가져오는 데에 실패했다면
		if(cardInfo == null) {
			System.out.println("service에서 통신. " + userId + "번 유저의 " + id + "번 카드 정보를 찾을 수 없습니다.");
			return null;
		}
		
		return cardInfo;
	}

}
