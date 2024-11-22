package com.ssafit.interceptor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

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
	// 로직
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
				// controller로 가도 좋다.
				return true;
			}
		}
		
		throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.");
	}
}
