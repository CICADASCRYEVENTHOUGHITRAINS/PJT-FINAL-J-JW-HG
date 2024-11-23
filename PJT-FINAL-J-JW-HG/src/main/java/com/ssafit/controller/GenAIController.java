package com.ssafit.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ssafit.model.service.ExerciseService;
import com.ssafit.model.service.ExerciseServiceImpl;

@RestController
@RequestMapping("/ai")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"}) 
public class GenAIController {
	//-----------------------------------------------------------//
	// 멤버 필드
	//-----------------------------------------------------------//
	private final ExerciseServiceImpl exerciseService;
	
	public GenAIController(ExerciseServiceImpl exerciseService) {
		super();
		this.exerciseService = exerciseService;
	}


	//-----------------------------------------------------------//
	// 로직
	//-----------------------------------------------------------//
	@GetMapping("/{userId}")
	public ResponseEntity<?> getAnswer() {
		try {
			Map<String, String> responses = new HashMap<>();
			String gptResponse = exerciseService.getResponse();
			
			System.out.println(gptResponse);
			
			return new ResponseEntity<String>(gptResponse, HttpStatus.OK);
		} 
		catch(Exception e) {
			System.out.println("===userController===");
			e.printStackTrace();
			System.out.println("===userController===");
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("비정상적인 접근입니다.");
		}
	}
	
}
