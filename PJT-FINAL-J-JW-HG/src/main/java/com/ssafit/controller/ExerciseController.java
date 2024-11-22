package com.ssafit.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafit.model.dto.Exercise;
import com.ssafit.model.service.ExerciseService;

@RestController
@RequestMapping("/exercise")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:4173"}) 
public class ExerciseController {
	//-----------------------------------------------------------//
	// 멤버 필드
	//-----------------------------------------------------------//
	private final ExerciseService exerciseService;
	
	// 생성자로 의존성 주입
	public ExerciseController(ExerciseService exerciseService) {		
		this.exerciseService = exerciseService;
	}	
	//-----------------------------------------------------------//
	// 로직	
	//-----------------------------------------------------------//
	/** 1. 임의의 랜덤 운동 조회 
	 * @return List<Exercise>
	 * [{
	 * 	(int) id,
	 * 	(String) part,
	 * 	(String) name,
	 * 	(String) info,
	 * 	(String) time
	 * }] 
	 */
	@GetMapping("/random")
	public ResponseEntity<?> getRandomExercise() {
		// 정상 로직
		try {
			List<Exercise> randomExerciseList = exerciseService.getRandomExercise();
								
			return new ResponseEntity<List<Exercise>>(randomExerciseList, HttpStatus.OK);			
		}
		catch(Exception e) {
			// 예외 처리
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");
			
			// 데이터 조회에 실패 시
			if(e.getMessage().contains("데이터 조회")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
			
			// 다른 모든 예외에 대해
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}
	}
	
	/** 2. 운동 정보 전체 조회
	 * @return List<Exercise>
	 * [{
	 * 	(int) id,
	 * 	(String) part,
	 * 	(String) name,
	 * 	(String) info,
	 * 	(String) time
	 * }]
	 */
	@GetMapping("")
	public ResponseEntity<?> getAllExercise() {
		try {
			List<Exercise> allExerciseList = exerciseService.getAllExercise();
			
			return new ResponseEntity<List<Exercise>>(allExerciseList, HttpStatus.OK);
		}
		catch(Exception e) {
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");

			// 데이터 조회에 실패 시
			if(e.getMessage().contains("데이터 조회")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
			
			// 다른 모든 예외에 대해
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}		
	}
	
	/** 3. 특정 운동 정보 조회
	 * @param (int) exerciseId
	 * @return Exercise
	 * {
	 * 	(int) id,
	 * 	(String) part,
	 * 	(String) name,
	 * 	(String) info,
	 * 	(String) time
	 * }
	 */
	@GetMapping("/{exerciseId}")
	public ResponseEntity<?> getExerciseInfo(@PathVariable int exerciseId) {
		try {
			Exercise exerciseInfo = exerciseService.getExerciseInfo(exerciseId);
					
			return new ResponseEntity<Exercise>(exerciseInfo, HttpStatus.OK);					
		}
		catch(Exception e) {
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");
			
			// 데이터 조회에 실패 시
			if(e.getMessage().contains("데이터 조회")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
			
			// 다른 모든 예외에 대해
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}
	}
	
	
	/** 4. 특정 부위에 대한 운동 조회
	 * @param (String) partName: LIKE 참조라 "손" 만 검색해도 "손목", "손바닥" 등이 결과로 출력됨.
	 * @return List<Exercise>
	 * [{
	 * 	(int) id,
	 * 	(String) part,
	 * 	(String) name,
	 * 	(String) info,
	 * 	(String) time
	 * }]
	 */
	@GetMapping("/part/{partName}")
	public ResponseEntity<?> getExerciseByPart(@PathVariable String partName) {
		try { 
			List<Exercise> partExerciseList = exerciseService.getExerciseByPart(partName);
						
			return new ResponseEntity<List<Exercise>>(partExerciseList, HttpStatus.OK);
		}
		catch(Exception e) {
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");
			
			// 데이터 조회에 실패 시
			if(e.getMessage().contains("데이터 조회")) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
			
			// 다른 모든 예외에 대해
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}
	}
}
