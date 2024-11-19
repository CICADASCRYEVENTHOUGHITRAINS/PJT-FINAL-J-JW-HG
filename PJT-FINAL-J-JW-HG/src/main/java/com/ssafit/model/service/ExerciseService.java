package com.ssafit.model.service;

import java.util.List;

import com.ssafit.model.dto.Exercise;

public interface ExerciseService {
	// 1. 임의의 랜덤 운동 조회
	List<Exercise> getRandomExercise();
	
	/////////////////////////////////////
	// 2. 운동 정보 전체 조회
	List<Exercise> getAllExercise();
	
	// 3. 특정 운동 정보 조회
	Exercise getExerciseInfo(int exerciseId);	
	
	/////////////////////////////////////	
	// 4. 특정 부위에 대한 운동 조회
	List<Exercise> getExerciseByPart(String partName);
}
