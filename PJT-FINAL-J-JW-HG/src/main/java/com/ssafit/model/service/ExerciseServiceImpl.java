package com.ssafit.model.service;

import java.util.List;

import org.springframework.ai.anthropic.AnthropicChatModel;
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
	private final OpenAiChatModel openAiChatModel;
	private final AnthropicChatModel anthropicChatModel;
	
	private final StringParseUtil stringParseUtil;
	
	
	
	// 생성자로 의존성 주입
	public ExerciseServiceImpl(ExerciseDao exerciseDao, OpenAiChatModel openAiChatModel, StringParseUtil stringParseUtil, AnthropicChatModel anthropicChatModel) {
		this.exerciseDao = exerciseDao;
		this.openAiChatModel = openAiChatModel;
		this.stringParseUtil = stringParseUtil;
		this.anthropicChatModel = anthropicChatModel;
	}	
	//-----------------------------------------------------------//
	// 로직
	//-----------------------------------------------------------//
	/** claude api를 통한 랜덤 운동 생성
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
	public List<Exercise> getResponse() {
		try {
			String prompt = 
					"""
					<요청>
					당신은 운동 및 건강에 관한 정확한 지식을 가지고 있는 전문가입니다.
					하루 종일 컴퓨터 앞에 앉아있는 직장인들을 위한 운동을 추천해주되, 다음 규칙을 반드시 따라주세요:

					1. 부위 선택
					손목, 허리, 어깨, 목, 다리, 전신, 눈, 팔, 허벅지, 등, 종아리, 엉덩이, 복부, 가슴, 얼굴 등
					가능한 부위 중에서 **중복없이 완전한 임의로** 3개를 선택
					

					2. 난이도 구분
					- 각 운동은 초급(스트레칭), 중급(앉아서 가능한 운동), 고급(서서 가능한 운동) 중 임의의 난이도를 골라야 합니다.

					3. 운동 시간
					- 운동 시간은 운동의 난이도에 맞게, 10이상 60이하의 정수를 선택합니다.
					
					<제약 사항>
					각 스트레칭 및 운동에 대한 답변은 다음과 같은 양식에 맞춰 String 형식으로 해야만 합니다.
					json형식을 절대 금지합니다. 단순 String으로 반드시 답변을 보내야합니다.
					양식의 각 부분에 대한 상세 설명은 소괄호를 통해 알려드리겠습니다. 
					양식에 해당하는 답변 이외에는 절대 하지 마시기 바랍니다.

					<양식>
					[
					{
					    "part": "(운동 부위)",
					    "name": "(운동 이름)",
					    "info": "(운동 내용)",
					    "time": "(난이도에 맞는 정수형 시간)"				    
					},
					... (3개의 운동)
					]
					""";
			
			//TODO 1. gen AI api quota 확인
//			String resObj = anthropicChatModel.call(prompt);
//			System.out.println(resObj);
			
			String response = openAiChatModel.call(prompt);			
			System.out.println(response);
			
//			String test =
//				"""
//				[
//					{
//					    "part": "손목",
//					    "name": "손목 스트레칭",
//					    "info": "손바닥를 위로 향하게 하고 반대손으로 손가락을 아래로 당기기",
//					    "time": "10"
//					},
//					{
//					    "part": "목",
//					    "name": "목 스트레칭",
//					    "info": "머리를 전천하 좌우로 기울여 목의 측면을 늘리기",
//					    "time": "20"
//					},
//					{
//					    "part": "어깨",
//					    "name": "어깨 롤링",
//					    "info": "어깨를 천천히 앞뒤로 5회, 위옆으로 5회 돌리기",
//					    "time": "20"
//					}
//				]
//				""";
			
			List<Exercise> responseList = stringParseUtil.parseExerciseCards(response);			
			
			return responseList;
		}
		catch(Exception e) {
	           // 에러 발생 시 로깅 및 예외 처리
            e.printStackTrace();
            throw new RuntimeException("Failed to generate response");
		}
	}
	
	
	/** 생성한 운동 중 선택한 운동 등록
	 *	 
	 */
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
