package com.ssafit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ssafit.model.dto.User;
import com.ssafit.model.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"}) 
public class AuthController {
	// 멤버 필드
	//-----------------------------------------------------------//
	private final UserService userService;
	
	// 생성자로 의존성 주입
	public AuthController(UserService userService) {
		this.userService = userService;
	}
	//-----------------------------------------------------------//
	// 로직
	//-----------------------------------------------------------//
	/** 1. 로그인 
	 * @param User 
	 * {
	 * 	(String) loginId,
	 * 	(String) password
	 * }
	 * @return String
	 * token
	 */
	@PostMapping("/login")
	public ResponseEntity<?> tryLogin(@RequestBody User user) {
		try {
			// input value
			String newUserId = user.getLoginId();
			String newUserPassword = user.getPassword();
			
			// db value
			String accessToken = userService.getInfoForLoginTry(newUserId, newUserPassword);
			
			// token이 제대로 발급되지 않았다면
			if(accessToken == null) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그인이 정상적으로 이루어지지 않았습니다.");
			} 

			return new ResponseEntity<String>(accessToken, HttpStatus.OK);
		}
		catch(Exception e) {
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");
			
			// service에서 던진 Exception에 따라 별도로 처리
			if(e.toString().contains("아이디")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			else if(e.toString().contains("비밀번호")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			
			// 그 외 모든 예외에 대해서
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}
	}
		

	/** 2. 회원가입 
	 * @param User
	 * {
	 * 	(String) loginId,
	 * 	(String) password,
	 * 	(String) userName
	 * }
	 * @return Boolean
	 * true/false
	 */
	@PostMapping("/register")
	public ResponseEntity<?> tryRegister(@RequestBody User user) {
		try {			
			// 서비스 호출
			int isUserRegistered = userService.tryRegister(user);
			
			// 등록 실패 시
			if(isUserRegistered == -1) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록에 실패했습니다.");				
			}
						
			return new ResponseEntity<Boolean>(true, HttpStatus.CREATED);
		}
		catch(Exception e) {
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");
			
			// 아이디 중복 시
			if(e.getMessage().contains("Duplicate")) {
				// 아이디만 unique임
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용중인 아이디입니다.");				
			}
			
			// input 공백 예외 처리
			else if(e.getMessage().contains("아이디")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("아이디를 입력해주세요.");
			}
			else if(e.getMessage().contains("비밀번호")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호를 입력해주세요.");
			}
			else if(e.getMessage().contains("닉네임")) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("닉네임을 입력해주세요.");
			}
			
			// 그 외 모든 예외에 대해
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}
	}
}
