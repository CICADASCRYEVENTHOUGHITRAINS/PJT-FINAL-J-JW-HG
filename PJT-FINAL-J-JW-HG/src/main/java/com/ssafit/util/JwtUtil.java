package com.ssafit.util;

import java.security.Key;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafit.model.dto.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
/*
 * JWT 발행, 토큰 생성, 토큰 검증 등의 역할을 하는 클래스 
 */
public class JwtUtil {
	//-----------------------------------------------------------//
	// 멤버 필드
	//-----------------------------------------------------------//
    private final Key secretKey; // signWith에서 사용하기 위한 key 객체
    private static final Long expiration = 1000L * 60 * 60 * 24; // 밀리세컨드 기준 일단 하루

    /**
     * 생성자로 application.properties 설정 값 주입 
     * @param (String) secret
     * @param (Long) expiration
     */
	public JwtUtil(@Value("${jwt.secret}") String secret) {
		byte[] keyBytes = Decoders.BASE64.decode(secret); // Key 객체로 만들기 위해 byte로 디코딩
		this.secretKey = Keys.hmacShaKeyFor(keyBytes); // Keys를 통해 Key 객체로 변환
	}
	
	//-----------------------------------------------------------//
	// JWT Building
	//-----------------------------------------------------------//
	
	/** JWT 생성
	 * @param user
	 * @param expiration
	 * @return
	 */
	private String createToken(User user, long expiration) {
		Claims claims = Jwts.claims();
		// jwt에 key: value 형태로 저장
		// TODO 1. token에 노출하지 않을 정보 삭제하기
		claims.put("userId", user.getId());
		claims.put("userName", user.getUserName());
		claims.put("score", user.getScore());
		claims.put("tier", user.getTier());
		claims.put("totalCardCount", user.getTotalCardCount());
		
		// 한국 시간만이면 ZoneId.of("Asia/Seoul")을 now()의 인자로
		ZonedDateTime now = ZonedDateTime.now(); // 지금 시간
		ZonedDateTime tokenValidity = now.plusSeconds(expiration); // 지금에 만료 시간 더한 값
		
		// jwt 반환
		return Jwts.builder()
				.setClaims(claims) // claim 담기							
				.setIssuedAt(Date.from(now.toInstant())) // jwt가 발급된 때
				.setExpiration(Date.from(tokenValidity.toInstant())) // 만료일
				.signWith(secretKey, SignatureAlgorithm.HS256) // sign
				.compact();
	}

	//-----------------------------------------------------------//
	// Generate Tokens
	//-----------------------------------------------------------//
	/** Access Token 생성
	 * @param (User) user
	 * @return (String) Access Token
	 */
    public String createAccessToken(User user) {
        return createToken(user, expiration);
    }

	//-----------------------------------------------------------//
	// Verifying Token
	//-----------------------------------------------------------//
    /** token 유효성 검사
     * @param token
     * @return Boolean
     * true/false
     */
    public boolean verifyToken(String token) {
        try {
        	// parsing
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true; 
        }
        
        // 예외 처리
        // 1. 유효하지 않음
        catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
        	System.out.println("Invalid JWT Token");
        	e.printStackTrace();
        } 
        // 2. 만료됨
        catch (ExpiredJwtException e) {
        	System.out.println("Expired JWT Token");
        	e.printStackTrace();
        } 
        // 3. 지원하지 않음
        catch (UnsupportedJwtException e) {
        	System.out.println("Unsupported JWT Token");
        	e.printStackTrace();
        } 
        // 4. 빈 값임
        catch (IllegalArgumentException e) {
            System.out.println("JWT claims string is empty.");
            e.printStackTrace();
        }
        return false;
        // 출처: https://sjh9708.tistory.com/170 [데굴데굴 개발자의 기록:티스토리]
    }
    
	//-----------------------------------------------------------//
	// Extract Information
	//-----------------------------------------------------------//
    /** Access Token에서 JWT Claims 추출
     * @param (String) accessToken
     * @return (Claims) JWT Claims
     */
    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
            		.setSigningKey(secretKey)
            		.build()
            		.parseClaimsJws(accessToken)
            		.getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
    
    /** Token에서 User Id 추출
     * @param (String) token
     * @return (int) userId
     */
    public int getUserId(String token) {
    	return parseClaims(token).get("userId", Integer.class);
    }
    
    /** token에서 userName 추출
     * @param token
     * @return (String) userName
     */
    public String getUserName(String token) {
    	return parseClaims(token).get("userName", String.class);
    }
    
}
