package com.ssafit.interceptor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import com.ssafit.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor{
	//-----------------------------------------------------------//
	// 멤버 필드
	//-----------------------------------------------------------//
	private final JwtUtil jwtUtil;
	
	// 생성자로 의존성 주입
	public JwtInterceptor(JwtUtil jwtUtil) {
		super();
		this.jwtUtil = jwtUtil;
	}
	//-----------------------------------------------------------//
	// AOP 로직
	//-----------------------------------------------------------//
	/** prehandle: servlet에서 controller로 가기전
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		 // OPTIONS 요청 시 인터셉터를 통과
	    if (request.getMethod().equals("OPTIONS")) {
	        return true;
	    }
		String token = request.getHeader("Authorization");
		
		// token이 header에 존재하는 경우
		// Bearer 접두어를 통해 토큰 기반 인증이라는 것을 명시되어 있을 때
		if(token != null && token.startsWith("Bearer ")) {
			String jwt = token.substring(7); // front-end에서 보내주는 양식에 맞춤
			
			// 토큰 유효성 검사
			if(jwtUtil.verifyToken(jwt)) {
				// path variable 추출
				@SuppressWarnings("unchecked") // Object -> Map cast
				Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
				
				// path variable이 있고, userId가 포함되어 있을 때
				if(pathVariables != null && pathVariables.containsKey("userId")) {
					// path variable과 token에 있는 userId 추출
					int pathUserId = Integer.valueOf(pathVariables.get("userId"));
					int tokenUserId = jwtUtil.getUserId(jwt);
					
					System.out.println("=== interceptor ===");
					System.out.println(pathUserId);
					System.out.println(tokenUserId);
					System.out.println("=== interceptor ===");
										
					// 검증결과 불일치 시
					if(pathUserId != tokenUserId) {
						throw new ResponseStatusException(HttpStatus.FORBIDDEN, "다른 사람의 정보에 접근할 수 없습니다.");
					}
				}		
				
				// controller로 가도 좋다.
				return true;
				
			}
		}
		
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
	}
}
