package com.ssafit.model.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ssafit.model.dao.CardDao;
import com.ssafit.model.dto.Card;

@Service
public class CardServiceImpl implements CardService {
	//-----------------------------------------------------------//
	// 멤버 필드
	//-----------------------------------------------------------//
	private final CardDao cardDao;
		
	// 생성자로 의존성 주입
	public CardServiceImpl(CardDao cardDao) {		
		this.cardDao = cardDao;
	}
	//-----------------------------------------------------------//
	// 로직
	//-----------------------------------------------------------//
	/** 1. 카드 수집 -> DB에 카드 등록
	 * @param Card
	 * @return: int형 반환: 성공하면 1, 실패하면 0
	 */
	@Override
	public int postCard(Card card) {
		try {
			// 음수 대입에 대한 처리
			if(card.getScore() < 0) {
				card.setScore(0);
			} 
			
			if(card.getTier() < 0) {
				card.setTier(0);
			}
			
			int isCardPosted = cardDao.postCard(card);
			
			// 데이터가 없거나 가져오는 데에 실패했다면
			if(isCardPosted == 0) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "카드 등록에 실패했습니다.");
			}
			 			
			return isCardPosted;
		}
		catch(Exception e) { throw e; }				
	}

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
	@Override
	public List<Card> getAllCards(int userId) {
		try {
			List<Card> cardList = cardDao.getAllCards(userId);
			
			// 데이터가 없거나 가져오는 데에 실패했다면
			if(cardList == null | cardList.size() == 0) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "저장된 카드가 없습니다!");
			}
			
			return cardList;	
		}
		catch(Exception e) { throw e; }		
	}

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
	@Override
	public List<Card> getRecentCards(int userId, int cardNumber) {
		try {
			if(cardNumber == 0) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "1개 이상의 카드를 선택해야합니다.");
			}
			
			List<Card> cardRecentList = cardDao.getRecentCards(userId, cardNumber);	

			// 데이터가 없거나 가져오는 데에 실패했다면
			if(cardRecentList == null | cardRecentList.size() == 0) {				
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "저장된 카드가 없습니다!");
			}
			
			return cardRecentList;			
		}
		catch(Exception e) { throw e; }	
	}

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
	@Override
	public Card getCardInfo(int userId, int id) {
		try {
			Card cardInfo = cardDao.getCardInfo(userId, id);
			
			// 데이터가 없거나 가져오는 데에 실패했다면
			if(cardInfo == null) {
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "저장된 카드가 없습니다!");
			}
			
			return cardInfo;			
		}
		catch(Exception e) { throw e; }	
	}

}
