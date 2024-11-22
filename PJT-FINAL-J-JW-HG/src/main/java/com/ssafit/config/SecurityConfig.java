package com.ssafit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//import com.ssafit.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	/**
	 * JwtAuth가 이번 프로젝트에는 필요가 없다는 결론을 내렸기 때문에 주석 처리함.
	 * 대형 프로젝트나 개인정보가 매우 중요해지는 경우에는 프로젝트 첫 시작부터 user -> userDetails -> userDetailsService로 구성되는 
	 * 보안 로직을 짜는게 좋을듯?
	 */
//	private final JwtAuthFilter jwtAuthFilter;
	
//	// 생성자로 의존성 주입
//	public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
//		super();
//		this.jwtAuthFilter = jwtAuthFilter;
//	}

	//-----------------------------------------------------------//
	// Bean 등록
	//-----------------------------------------------------------//
	/** bcrypt 암호화를 위한 encoder
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	/** auth 관련 manager를 통한 로직 설정
	 * @param authConfig
	 * @return
	 * @throws Exception
	 */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) 
            throws Exception {
        return authConfig.getAuthenticationManager();
    }
	
	/** filter들을 이용한 보안 로직 설정
	 * @param http
	 * @return
	 * @throws Exception
	 * Created by Claude 3.5 Sonnet
	 */
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 설정(Cross-Site Request Forgery) 비활성화
        	// REST API는 stateless하므로 CSRF 보호가 필요 없음
            .csrf(csrf -> csrf.disable())
            
            // 세션 관리 설정
            // REST API는 각 요청이 독립적이어야하므로 세션 사용하지 않음
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            // 폼 로그인 비활성화
            // 기본 로그인 폼 비활성화
            // REST API는 폼 로그인 대신 토큰 기반 인증 사용
            .formLogin(login -> login.disable())
            
            // HTTP Basic 인증 비활성화
            // 보안상 취약할 수 있는 기본 인증 방식 사용 X
            .httpBasic(basic -> basic.disable())
            
            // 요청에 대한 인가 설정
            // URL 접근 권한 설정
            // /api/auth/** 경로는 모든 사용자 접근 가능(회원가입, 로그인 등)
            // 그 외 요청은 인증된 사용자만 접근 가능
            .authorizeHttpRequests(authorize -> authorize // http 요청에 대한 인가규칙 설정 시작
                .requestMatchers("**").permitAll() // URL패턴 지정, permitAll()로 인증 없이 모든 사용자 접근 가능 허용
                .anyRequest().authenticated() // 위에서 설정하지 않은 나머지 모든 요청에 대한 설정(항상 마지막에 설정), authenticated()로 인증된, 로그인한 사용자만 접근 가능
            );
            
//            // UsernamePasswordAuthenticationFilter 이전에 먼저 적용될 filter 설정
//            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            
        return http.build();
    }
    
}
