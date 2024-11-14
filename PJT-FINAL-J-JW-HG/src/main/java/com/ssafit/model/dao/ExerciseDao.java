package com.ssafit.model.dao;

import java.util.List;

import com.ssafit.model.dto.Exercise;

public interface ExerciseDao {
	// 1. 임의의 랜덤 운동 조회
	List<Exercise> getRandomExercise();
	
	/////////////////////////////////////
	// 2. 운동 정보 전체 조회
	
	
	// 3. 특정 부위에 대한 운동 조회
}
