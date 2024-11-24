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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.model.dto.Card;
import com.ssafit.model.service.CardService;

@RestController
@RequestMapping("users/{userId}/cards")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"}) 
public class CardsController {
	//-----------------------------------------------------------//
	// 멤버 필드
	//-----------------------------------------------------------//
	private final CardService cardService;

	// 생성자로 의존성 주입
	public CardsController(CardService cardService) {		
		this.cardService = cardService;
	}
	//-----------------------------------------------------------//
	// 로직
	//-----------------------------------------------------------//
	// 1. 카드 수집 -> DB에 카드 등록
	/**
	 * @param Card
	 * {
	 * 	(int) exerciseId,
	 * 	(int) score,
	 * 	(int) tier 
	 * }
	 * @param (int) userId
	 * @return Map
	 * {
	 * 	id: (int) id
	 * } 
	 */
	@PostMapping("")
	// 와일드 카드 사용으로 String과 Map 둘 다 처리할 수 있게	
	public ResponseEntity<?> postCard(@RequestBody Card card, @PathVariable int userId) {
		try {
			card.setUserId(userId);			
			int isSuccess = cardService.postCard(card);
			
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
			
			// exerciseId 값이 비정상적일 경우
			if(e.getMessage().contains("fk_exercise_id")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("존재하지 않는 운동입니다.");
			}
			
			else if(e.getMessage().contains("fk_user_id")) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("다른 사람의 정보에 접근할 수 없습니다.");
			}
			
			// 그 외 모든 예외에 대해서
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}
		
	}
	
	
	// 2. 한 유저가 수집한 전체 카드 조회
	
	/** 
	 * @param (int) userId 
	 * @return List<Card>	 
	 * [{
	 * (int) id,
	 * (String) userId,
	 * (String) exerciseId,
	 * (int) score,
	 * (int) tier,
	 * (String) collected_date 
	 * }]
	 */
	@GetMapping("")
	public ResponseEntity<?> getAllCards(@PathVariable int userId) {
		try {
			List<Card> cardList = cardService.getAllCards(userId);
			
			// 조회에 실패했을 경우
			if(cardList == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userId + "유저의 카드 데이터가 존재하지 않습니다.");
			}
			
			// 전체 카드를 조회할 때 user table의 ttoal_card_count와 같은 지 검증, 다르면 실제 카드 리스트 size로 수정
			
			return new ResponseEntity<List<Card>>(cardList, HttpStatus.OK);			
		}
		catch(Exception e) {
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");
			
			if(e.getMessage().contains("다른 사람")) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
			}
			
			// 다른 모든 예외에 대해
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}

	}
	
	// 3. 한 유저가 수집한 최근 카드 n개 조회
	/** 
	 * @param (int) userId
	 * @param (int) cardNumber: 조회할 카드 개수
	 * @return List<Card>
	 * [{
	 * (int) id,
	 * (String) userId,
	 * (String) exerciseId,
	 * (int) score,
	 * (int) tier,
	 * (String) collected_date 
	 * }]
	 */
	@GetMapping("/recent?limit={cardNumber}")
	public ResponseEntity<?> getRecentCards(@PathVariable int userId, @RequestParam int cardNumber) {
		try {
			List<Card> cardList = cardService.getRecentCards(userId, cardNumber);
			
			// 조회에 실패했을 경우
			if(cardList == null) {
				System.out.println("카드 데이터가 존재하지 않습니다.");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userId + "유저의 카드 데이터가 존재하지 않습니다.");
			}
			
			// userId에 해당하는 유저가 가지고 있는 card의 개수보다 많은 숫자를 요청해도 최대 개수만 반환되고 예외가 발생하지 않음
			return new ResponseEntity<List<Card>>(cardList, HttpStatus.OK);			
		}
		catch(Exception e) {
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");
			
			if(e.getMessage().contains("다른 사람")) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
			}
			else if(e.getMessage().contains("1개 이상")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
			
			// 다른 모든 예외에 대해
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}
	}
	
	// 4. 특정 카드 정보 조회
	/** 
	 * @param (int) userId
	 * @param (int) cardId
	 * @return Card
	 * {
	 * (int) id,
	 * (String) userId,
	 * (String) exerciseId,
	 * (int) score,
	 * (int) tier,
	 * (String) collectedDate 
	 * }
	 */
	@GetMapping("/{cardId}")
	public ResponseEntity<?> getCardInfo(@PathVariable int userId, @PathVariable int cardId) {
		try {
			Card cardInfo = cardService.getCardInfo(userId, cardId);
			
			// 카드 조회에 실패했을 경우
			if(cardInfo == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userId + "유저의 " + cardId + "번 카드가 존재하지 않습니다.");
			}
			
			return new ResponseEntity<Card>(cardInfo, HttpStatus.OK);			
		}
		catch(Exception e) {
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");
			
			if(e.getMessage().contains("다른 사람")) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
			}
			
			// 다른 모든 예외에 대해
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}
	}
	
}
