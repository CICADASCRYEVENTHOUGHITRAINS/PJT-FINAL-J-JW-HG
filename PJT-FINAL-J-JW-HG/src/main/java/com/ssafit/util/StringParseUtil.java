package com.ssafit.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafit.model.dto.Exercise;

@Component
public class StringParseUtil {
	//-----------------------------------------------------------//
	// 멤버 필드
	//-----------------------------------------------------------//
	private final ObjectMapper objectMapper;

	// 생성자로 의존성 주입
	public StringParseUtil(ObjectMapper objectMapper) {
		super();
		this.objectMapper = objectMapper;
	}
	
	//-----------------------------------------------------------//
	// 로직
	//-----------------------------------------------------------//
	/** json형태의 string을 parsing(Jackson 라이브러리 활용)
	 * @param jsonString
	 * @return List<Exercise>
	 */
	public List<Exercise> parseExerciseCards(String jsonString) {
        try {
            // JSON 배열을 Exercise 객체 리스트로 변환
            // objectMapper.readValue() 메서드를 사용하여 JSON 문자열을 Java 객체로 변환
            // 첫 번째 파라미터: 변환할 JSON 문자열
            // 두 번째 파라미터: 변환될 타입 정보를 제공하는 TypeReference
            // TypeReference<>()는 제네릭 타입 정보를 런타임에도 유지하기 위한 방법
            return objectMapper.readValue(jsonString, 
                new TypeReference<List<Exercise>>() {});
        } catch (JsonProcessingException e) {
            // 예외 처리            
            throw new RuntimeException("카드 데이터 처리 중 오류가 발생했습니다.", e);
        }
    }
}
