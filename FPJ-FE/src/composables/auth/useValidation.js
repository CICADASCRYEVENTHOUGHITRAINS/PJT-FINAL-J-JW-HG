export const useValidation = () => {
  const decodeJWT = (token) => {
    const payload = token.split('.')[1]
    return JSON.parse(atob(payload))
  }

  // 사용자 입력 데이터의 유효성 검증.
  // 특정 입력 형식에 대한 규칙 정의(예: 이메일, 비밀번호, 이름 등).
  // 폼 필드별 에러 메시지 관리.
  // 클라이언트에서 기본적인 검증 로직 처리.
  // 포함할 로직
  //
  // 필수 입력값 확인:
  // 값이 비어 있지 않은지 확인.
  // 공백체크
  // 특정 형식 검증:
  // 이메일, 비밀번호, 전화번호 등 정규식을 활용한 형식 검증.
  // 비즈니스 로직 검증:
  // 두 값이 동일한지 확인(예: 비밀번호와 비밀번호 확인).
  // 폼 상태 관리:
  // 에러 메시지와 검증 상태 관리.

  // 계정 존재 여부를 확인
  // 패스워드가 기준을 충족하는지 확인
  // 계정 & 패스워드 유효성 확인
  //
  //
  //
  //
  // watch 와 debounce를 이용해 실시간 확인
  //
  //
  return { decodeJWT }
}