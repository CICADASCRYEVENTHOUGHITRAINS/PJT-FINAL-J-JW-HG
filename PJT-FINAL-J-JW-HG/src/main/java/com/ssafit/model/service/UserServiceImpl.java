package com.ssafit.model.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.ssafit.model.dao.UserDao;
import com.ssafit.model.dto.User;
import com.ssafit.util.JwtUtil;

@Service
public class UserServiceImpl implements UserService {
	//-----------------------------------------------------------//
	// 멤버 필드
	//-----------------------------------------------------------//
	private final UserDao userDao;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtUtil jwtUtil;
	
	// 생성자로 의존성 주입
	public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder, JwtUtil jwtUtil) {
		super();
		this.userDao = userDao;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtUtil = jwtUtil;
	}
	//-----------------------------------------------------------//
	// 로직 - UserController
	//-----------------------------------------------------------//	
	/** 1. 특정 유저 정보 전체 조회
	 * @param (int) userId
	 * @return: User
	 * {
	 * (int) id,
	 * (String) loginId,
	 * (String) userName,
	 * (int) score,
	 * (int) totalCardCount,
	 * (int) tier
	 * }
	 */
	@Override
	public User getUserInfo(int userId) {
		try {
			// 정상 로직
			User userInfo = userDao.getUserInfo(userId);
			
			// loginId, password는 db에서 가져오지 않음
			// password는 절대 안되고 loginId는 필요시 가져오기 가능
	
			// 조회에 실패하면 null이 반환됨
			// mapper의 resultType이 Object 이기 때문에 존재하지 않는 유저라도 null 반환할수도 있음.
			if(userInfo == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, userId + " 유저를 찾을 수 없습니다.");
			}
			
			// 본인이 아닌 다른 사람의 정보를 조회할 경우
			if(userInfo.getId() != userId) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "다른 사람의 정보를 조회하려 합니다.");					
			}		
			
			return userInfo;
		}
		catch(Exception e) { throw e; }
	}


	/** 2. 유저의 건강력 조회
	 * @param (int) userId
	 * @return: int
	 */
	@Override
	public int getUserScore(int userId) {
		try {
			// 정상 로직
			int userScore = userDao.getUserScore(userId);
			
			return userScore; 
		}
		catch(Exception e) { throw e; }
	}

	
	/** 3. 유저가 건강 관리한 연속 일수
	 * @param (int) userId
	 * @return: int
	 */
	@Override
	public int getUserStreak(int userId) {
		try {
			int userStreak = userDao.getUserStreak(userId);	
			
			return userStreak;
		}
		catch(Exception e) { throw e; }			
	}

	
	/** 4. 유저의 등급 조회 
	 * @param (int) userId
	 * @return: int
	 */
	@Override
	public int getUserTier(int userId) {
		try {
			// 정상 로직
			int userTier = userDao.getUserTier(userId);
			
			return userTier;
		}
		catch(Exception e) { throw e; }	
	}
		
	/** 5. 유저가 획득한 총 카드 수 조회
	 * @param (int) userId
	 * @return: int
	 */
	@Override
	public int getUserTotalCardCount(int userId) {
		try {
			// 정상 로직
			int userTotalCardCount = userDao.getUserTotalCardCount(userId);
			
			return userTotalCardCount;					
		}
		catch(Exception e) { throw e; }	
	}

	/** 6. 유저가 획득한 총 카드 수 업데이트
	 * @param (int) userId
	 * @param (int) newTotalCardCount
	 * @return: int (0/1)
	 */
	@Override
	public int updateUserTotalCardCount(int userId, int newTotalCardCount) {
		try {
			int isTotalCardCountUpdated = userDao.updateUserTotalCardCount(userId, newTotalCardCount);
			
			// update가 성공한 column 개수를 반환 -> 정상 로직 동작시 1, 비정상시 0 반환
			if(isTotalCardCountUpdated == 0) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "다른 사람의 정보에 접근하려 합니다.");
			}
			
			return isTotalCardCountUpdated;
		}
		catch(Exception e) { throw e; }
	}

	/** 7. 건강 점수 업데이트
	 * @param (int) userId
	 * @param (int) newUserScore
	 * @return: int (0/1)
	 */
	@Override
	public int updateUserScore(int userId, int newUserScore) {
		try {
			int isScoreUpdated = userDao.updateUserScore(userId, newUserScore);
			
			// update가 성공한 column 개수를 반환 -> 정상 로직 동작시 1, 비정상시 0 반환
			if(isScoreUpdated == 0) {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, "다른 사람의 정보에 접근하려 합니다.");
			}
			
			return isScoreUpdated;
		}
		catch(Exception e) { throw e; }
	}
	
	//-----------------------------------------------------------//
	// 로직 - AccountController
	//-----------------------------------------------------------//
	// 8. 로그인 시도에 따른 특정 유저의 비밀번호 조회
	/**
	 * @param (String) loginId	 
	 * @param (String) password
	 * @return (String) token
	 */
	@Override
	public String getInfoForLoginTry(String loginId, String password) {
		try {
			// service에서 비즈니스 로직 처리
			// user id를 토대로 db에서 그 id에 해당하는 유저 조회
			User dbUser = userDao.getInfoForLoginTry(loginId);
			
			// 해당하는 유저가 없을 시
			if(dbUser == null) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, loginId + " 아이디를 찾을 수 없습니다.");				
			}
			
			String dbPassword = dbUser.getPassword();			
				
			// 비밀번호가 일치하지 않는다면
			if(!bCryptPasswordEncoder.matches(password, dbPassword)) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");
			}
			
			// password 뺴고 등록
			String accessToken = jwtUtil.createAccessToken(dbUser);
			
			return accessToken;
		}
		catch(Exception e) { throw e; }
	}

	/** 9. 회원가입 시도
	 * @param user
	 * @return: int (0/1)
	 */
	@Transactional
	@Override
	public int tryRegister(User user) {
		try {
			user.setLoginId(user.getLoginId().trim());
			user.setUserName(user.getUserName().trim());
			
			// service에서 비즈니스 로직 처리
			// front에서도 공백 체크 해주겠지만... back에서도 한 번 더 체크
			if(user.getLoginId().equals("")) {				
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "아이디를 입력해주세요.");
			}
			else if(user.getPassword().trim().equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "비밀번호를 입력해주세요.");
			}
			else if(user.getUserName().equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "닉네임을 입력해주세요.");
			}
			
			// - 비밀번호 암호화
			String newUserPassword = user.getPassword();
			String bCryptedPassword = bCryptPasswordEncoder.encode(newUserPassword);
			
			user.setPassword(bCryptedPassword);
			
			// dao 호출 및 db 통신 시도
			int isUserRegisted = userDao.tryRegister(user);
			
			// 등록 실패 시
			if(isUserRegisted == 0) {
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , "유저 등록에 실패했습니다.");
			}
			
			return isUserRegisted;
		}
		catch(Exception e) { throw e; }	
	}
}
 