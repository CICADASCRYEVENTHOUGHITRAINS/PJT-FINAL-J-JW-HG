package com.ssafit.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ssafit.model.dao.ExerciseDao;
import com.ssafit.model.dto.Exercise;
import com.ssafit.util.StringParseUtil;

@Service
public class ExerciseServiceImpl implements ExerciseService {
	//-----------------------------------------------------------//
	// 멤버 필드
	//-----------------------------------------------------------//
	private final ExerciseDao exerciseDao;
	private OpenAiChatModel openAiChatModel;
	
	private final StringParseUtil stringParseUtil;
	
	// 생성자로 의존성 주입
	public ExerciseServiceImpl(ExerciseDao exerciseDao, OpenAiChatModel openAiChatModel, StringParseUtil stringParseUtil) {
		this.exerciseDao = exerciseDao;
		this.openAiChatModel = openAiChatModel;
		this.stringParseUtil = stringParseUtil;
	}	
	//-----------------------------------------------------------//
	// 로직
	//-----------------------------------------------------------//
	
	@Override
	public List<Exercise> getResponse() {
		try {
			String prompt = 
					"""
					<요청>
					당신은 운동 및 건강에 관한 전문가입니다. 하루 종일 컴퓨터 앞에 앉아있는 직장인들을 위해 추천할 수 있는
					아주 간단한 스트레칭 및 운동 3개를 추천해주세요. 각 스트레칭 및 운동에 대한 답변은 다음과 같은 양식으로 부탁드립니다.
					양식의 각 부분에 대한 상세 설명은 소괄호를 통해 알려드리겠습니다.
					
					<양식>
					{
						'part': '손목', (운동 부위)
						'name': '손목 스트레칭', (운동 이름)
						'info': '손바닥을 위로 향하게 하고 반대손으로 손가락을 아래로 당기기', (운동 내용)
						'time': '10' (운동 지속 시간, **반드시 예시와 같은 정수형으로**)
					}				
					""";
			
			//TODO 1. gpt api quota 확인
//			String response = openAiChatModel.call(prompt);
			
			String test =
				"""
				[
					{
					    "part": "손목",
					    "name": "손목 스트레칭",
					    "info": "손바닥를 위로 향하게 하고 반대손으로 손가락을 아래로 당기기",
					    "time": "10"
					},
					{
					    "part": "목",
					    "name": "목 스트레칭",
					    "info": "머리를 전천하 좌우로 기울여 목의 측면을 늘리기",
					    "time": "20"
					},
					{
					    "part": "어깨",
					    "name": "어깨 롤링",
					    "info": "어깨를 천천히 앞뒤로 5회, 위옆으로 5회 돌리기",
					    "time": "20"
					}
				]
				""";
			
			List<Exercise> responseList = stringParseUtil.parseExerciseCards(test);			
			
			return responseList;
		}
		catch(Exception e) {
	           // 에러 발생 시 로깅 및 예외 처리
            e.printStackTrace();
            throw new RuntimeException("Failed to generate response");
		}
	}
	
	
	// 생성한 운동 중 선택한 운동 등록
	@Override
	public int postExercise(Exercise exercise) {
		try {
			int isExercisePosted = exerciseDao.postExercise(exercise);
			
			if(isExercisePosted == 0) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "운동 등록에 실패했습니다.");
			}
			
			return isExercisePosted;
		}		
		catch(Exception e) { throw e; }
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
