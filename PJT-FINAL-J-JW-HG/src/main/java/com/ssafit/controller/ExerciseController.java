package com.ssafit.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exercise")
@CrossOrigin(origins = {"http://localhost:5173/*", "http://localhost:5174/*"}) // TODO origin 허용할 uri 작성
public class ExerciseController {
	// 1. 임의 운동 조회
	
	
	
	/////////////////////////////////////
	// 2. 운동 정보 전체 조회
	
	
	// 3. 특정 부위에 대한 운동 조회
}
