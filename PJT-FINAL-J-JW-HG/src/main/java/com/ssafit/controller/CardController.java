package com.ssafit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.model.dto.Card;
import com.ssafit.model.service.CardService;

@RestController
@RequestMapping("/cards")
@CrossOrigin(origins = {"http://localhost:5173/*", "http://localhost:5174/*"}) // TODO origin 허용할 uri 작성
public class CardController {
	private final CardService cardService;

	// 생성자로 의존성 주입
	public CardController(CardService cardService) {		
		this.cardService = cardService;
	}
	
//////////////////////////////////////////////////////////////	
	
	// 1. 카드 수집 -> DB에 카드 등록
	/**
	 * @param card
	 * @param userId
	 * @return
	 * {
	 * 	id: (int) number
	 * } 
	 */
	@PostMapping("/{userId}")
	// 와일드 카드 사용으로 String과 Map 둘 다 처리할 수 있게	
	public ResponseEntity<?> postCard(@RequestBody Card card, @PathVariable int userId) {
		try {
			card.setUserId(userId);			
			int isSuccess = cardService.postCard(card);
			
			/* 등록에 실패했을 경우 */
			if(isSuccess == -1) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 유저입니다."); 
			}
			
			/* 등록에 성공했을 경우 */			
			// mapper에서 <keyProperty="id" useGeneratedKeys="true"> 처리해놨기 때문에 객체에 자동으로 AUTO_INCREMENT 값 반영
			int cardId = card.getId();
		
			// 보안을 위해서 객체 자체를 반환하는 것 보다는 id만 반환하고 front에서 다른 api를 통해 요청하는 것이 나을 것
			// key: value 형태로 id만 반환
			Map<String, Integer> cardMap = new HashMap<>();
			cardMap.put("id", cardId);
			
			return new ResponseEntity<Map<String,Integer>>(cardMap, HttpStatus.OK);		
		}
		catch(Exception e) {
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}
		
	}
	
	
	// 2. 한 유저가 수집한 전체 카드 조회
	/** @return
	 * @param userId
	[{ 
		id,
		user_id,
		exercise_id,
		score,
		tier,
		collected_date 
	}] 
	 */
	@GetMapping("/{userId}")
	public ResponseEntity<?> getAllCards(@PathVariable int userId) {
		try {
			List<Card> cardList = cardService.getAllCards(userId);
			
			// 조회에 실패했을 경우
			if(cardList == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("카드 데이터가 존재하지 않습니다.");
			}
			
			// 전체 카드를 조회할 때 user table의 ttoal_card_count와 같은 지 검증, 다르면 실제 카드 리스트 size로 수정
			
			return new ResponseEntity<List<Card>>(cardList, HttpStatus.OK);			
		}
		catch(Exception e) {
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}

	}
	
	// 3. 한 유저가 수집한 최근 카드 n개 조회
	/** @return
	 * @param userId
	 * @param cardNumber
	[{ 
		id,
		user_id,
		exercise_id,
		score,
		tier,
		collected_date 
	}]
	 */
	@GetMapping("/{userId}/recent/{cardNumber}")
	public ResponseEntity<?> getRecentCards(@PathVariable int userId, @PathVariable int cardNumber) {
		try {
			List<Card> cardList = cardService.getRecentCards(userId, cardNumber);
			
			// 조회에 실패했을 경우
			if(cardList == null) {
				System.out.println("카드 데이터가 존재하지 않습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("카드 데이터가 존재하지 않습니다.");
			}
			
			// userId에 해당하는 유저가 가지고 있는 card의 개수보다 많은 숫자를 요청해도 최대 개수만 반환되고 예외가 발생하지 않음
			return new ResponseEntity<List<Card>>(cardList, HttpStatus.OK);			
		}
		catch(Exception e) {
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}
	}
	
	// 4. 특정 카드 정보 조회
	/** @return
	 * @param userId
	 * @param cardId
 	{ 
		id,
		user_id,
		exercise_id,
		score,
		tier,
		collected_date 
	}
	 */
	@GetMapping("/{userId}/{cardId}")
	public ResponseEntity<?> getCardInfo(@PathVariable int userId, @PathVariable int cardId) {
		try {
			Card cardInfo = cardService.getCardInfo(userId, cardId);
			
			// 카드 조회에 실패했을 경우
			if(cardInfo == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(cardId + "번 카드의 정보 조회에 실패했습니다.");
			}
			
			return new ResponseEntity<Card>(cardInfo, HttpStatus.OK);			
		}
		catch(Exception e) {
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}
	}
	
}
