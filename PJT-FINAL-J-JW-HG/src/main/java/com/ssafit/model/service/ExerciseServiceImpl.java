package com.ssafit.model.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ssafit.model.dao.ExerciseDao;
import com.ssafit.model.dto.Exercise;

@Service
public class ExerciseServiceImpl implements ExerciseService {
	//-----------------------------------------------------------//
	// 멤버 필드
	//-----------------------------------------------------------//
	private final ExerciseDao exerciseDao;
	
	// 생성자로 의존성 주입
	public ExerciseServiceImpl(ExerciseDao exerciseDao) {
		this.exerciseDao = exerciseDao;
	}	
	//-----------------------------------------------------------//
	// 로직
	//-----------------------------------------------------------//
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
	@Override
	public List<Exercise> getRandomExercise() {
		try {
			List<Exercise> randomExerciseList = exerciseDao.getRandomExercise();
			
			// 데이터가 없거나 가져오는 데에 실패했다면
			if(randomExerciseList == null | randomExerciseList.size() == 0) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "데이터 조회에 실패했습니다.");
			}
			
			return randomExerciseList;			
		}
		catch(Exception e) { throw e; }	

	}
	
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
	@Override
	public List<Exercise> getAllExercise() {
		try {
			List<Exercise> allExerciseList = exerciseDao.getAllExercise();
			
			// 데이터가 없거나 가져오는 데에 실패했다면
			if(allExerciseList == null | allExerciseList.size() == 0) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "데이터 조회에 실패했습니다.");
			}
			
			return allExerciseList;
		}
		catch(Exception e) { throw e; }
	}

	
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
	@Override
	public Exercise getExerciseInfo(int exerciseId) {
		try {
			Exercise exerciseInfo = exerciseDao.getExerciseInfo(exerciseId);
			
			if(exerciseInfo == null) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "데이터 조회에 실패했습니다.");
			}
			
			return exerciseInfo;
		}
		catch(Exception e) { throw e; }
	}

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
	@Override
	public List<Exercise> getExerciseByPart(String partName) {
		try {
			List<Exercise> partExerciseList = exerciseDao.getExerciseByPart(partName);
			
			// 데이터가 없거나 가져오는 데에 실패했다면
			if(partExerciseList == null || partExerciseList.size() == 0) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "데이터 조회에 실패했습니다.");
			}
			
			return partExerciseList;
		}
		catch(Exception e) { throw e; }
	}
	
}
