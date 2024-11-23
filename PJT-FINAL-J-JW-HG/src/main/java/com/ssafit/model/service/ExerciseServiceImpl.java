package com.ssafit.model.service;

import java.util.List;

import org.springframework.ai.openai.OpenAiChatModel;
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
	private OpenAiChatModel openAiChatModel;
	
	// 생성자로 의존성 주입
	public ExerciseServiceImpl(ExerciseDao exerciseDao, OpenAiChatModel openAiChatModel) {
		this.exerciseDao = exerciseDao;
		this.openAiChatModel = openAiChatModel;
	}	
	//-----------------------------------------------------------//
	// 로직
	//-----------------------------------------------------------//
	
	// test
	public String getResponse() {
		try {
			String prompt = 
					"""
					<요청>
					당신은 운동 및 건강에 관한 전문가입니다. 하루 종일 컴퓨터 앞에 앉아있는 직장인들을 위해 추천할 수 있는
					아주 간단한 스트레칭 및 운동 3개를 추천해주세요. 각 스트레칭 및 운동에 대한 답변은 다음과 같은 양식으로 부탁드립니다.
					
					<양식>
					{
						'part': '손목',
						'name': '손목 스트레칭',
						'info': '손바닥을 위로 향하게 하고 반대손으로 손가락을 아래로 당기기',
						'time': '10'
					}				
					""";
			
			String response = openAiChatModel.call(prompt);
			
			return response;
		}
		catch(Exception e) {
	           // 에러 발생 시 로깅 및 예외 처리
            e.printStackTrace();
            throw new RuntimeException("Failed to generate response");
		}
	}
	
	
	
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
