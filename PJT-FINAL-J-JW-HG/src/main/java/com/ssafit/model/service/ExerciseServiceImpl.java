package com.ssafit.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafit.model.dao.ExerciseDao;
import com.ssafit.model.dto.Exercise;

@Service
public class ExerciseServiceImpl implements ExerciseService {
	private final ExerciseDao exerciseDao;
	// 생성자로 의존성 주입
	public ExerciseServiceImpl(ExerciseDao exerciseDao) {
		this.exerciseDao = exerciseDao;
	}
	
	//////////////////////////////////////////////////////////////
	// 1. 임의의 랜덤 운동 조회
	@Override
	public List<Exercise> getRandomExercise() {
		List<Exercise> randomExerciseList = exerciseDao.getRandomExercise();
		
		// 데이터가 없거나 가져오는 데에 실패했다면
		if(randomExerciseList == null | randomExerciseList.size() == 0) {
			System.out.println("Service에서의 통신. List를 불러오는 데 실패했습니다.");
			return null;
		}
		
		return randomExerciseList;
	}
	
	/////////////////////////////////////
	// 2. 운동 정보 전체 조회
	
	
	// 3. 특정 부위에 대한 운동 조회
}
