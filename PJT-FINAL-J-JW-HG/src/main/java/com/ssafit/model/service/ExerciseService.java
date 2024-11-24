package com.ssafit.model.service;

import java.util.List;

import com.ssafit.model.dto.Exercise;

public interface ExerciseService {
	//-----------------------------------------------------------//
	// 로직
	//-----------------------------------------------------------//
	// 0. gpt에서 임의 운동 생성
	List<Exercise> getResponse();
	
	// 생성한 운동 중 선택한 운동 등록
	int postExercise(Exercise exercise);	
	
	/** 1. 임의의 랜덤 운동 조회
	 * @return:List<Exercise> 
	 * [{
	 * (int) id,
	 * (String) part,
	 * (String) name,
	 * (String) info,
	 * (String) time
	 * }]
	 */
	List<Exercise> getRandomExercise();
	
	/** 2. 운동 정보 전체 조회
	 * @return:List<Exercise> 
	 * [{
	 * (int) id,
	 * (String) part,
	 * (String) name,
	 * (String) info,
	 * (String) time
	 * }]
	 */
	List<Exercise> getAllExercise();
	
	/** 3. 특정 운동 정보 조회
	 * @param exerciseId
	 * @return:List<Exercise> 
	 * [{
	 * (int) id,
	 * (String) part,
	 * (String) name,
	 * (String) info,
	 * (String) time
	 * }]
	 */
	Exercise getExerciseInfo(int exerciseId);	
	
	/** 4. 특정 부위에 대한 운동 조회
	 * @param partName
	 * @return:List<Exercise> 
	 * [{
	 * (int) id,
	 * (String) part,
	 * (String) name,
	 * (String) info,
	 * (String) time
	 * }]
	 */
	List<Exercise> getExerciseByPart(String partName);
}
