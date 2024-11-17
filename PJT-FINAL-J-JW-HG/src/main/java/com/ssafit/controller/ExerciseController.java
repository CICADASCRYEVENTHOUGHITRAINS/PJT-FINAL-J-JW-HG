package com.ssafit.controller;

import java.util.List;

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
@CrossOrigin(origins = {"*"}) // TODO origin 허용할 uri 작성
public class ExerciseController {
	private final ExerciseService exerciseService;
	// 생성자로 의존성 주입
	public ExerciseController(ExerciseService exerciseService) {		
		this.exerciseService = exerciseService;
	}
	
//////////////////////////////////////////////////////////////
	// methods //
	
	// 1. 임의의 랜덤 운동 조회
	@GetMapping("/random")
	public ResponseEntity<?> getRandomExercise() {
		List<Exercise> randomExerciseList = exerciseService.getRandomExercise();
		System.out.println("test");
		// 조회에 실패했을 경우
		if(randomExerciseList == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("운동 데이터가 존재하지 않습니다.");
		}
				
		return new ResponseEntity<List<Exercise>>(randomExerciseList, HttpStatus.OK);
	}
	
	
	/////////////////////////////////////
	// 2. 운동 정보 전체 조회
	
	
	// 3. 특정 부위에 대한 운동 조회
}
