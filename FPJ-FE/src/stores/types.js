/** 운동 정보 객체
 * @typedef {Object} Exercise
 * @property {number} id - 운동별 고유 id
 * @property {string} part - 운동 부위
 * @property {string} name - 운동명
 * @property {string} info - 운동의 간단한 정보
 * @property {number} time - 운동 수행 시간 */

/** 카드 정보 객체
 * @typeof {Object} Card
 * @property {number} id - 카드 고유 id
 * @property {number} score - 카드별 점수
 * @property {number} tier - 카드 획득 시 유저 티어
 * @property {string} collectedDate - 획득 일자
 */

/** 유저 정보 객체
 * @typeof {Object} User
 * @property {string} loginId - 사용자가 등록한 로그인 id
 * @property {string} password - 사용자 패스워드
 * @property {string } userName - 사용자 닉네임
 * @property {number} score - 사용자의 건강력 총점
 * @property {number} totalCardCount - 사용자가 수집한 카드의 수
 * @property {number} tier - 사용자 티어
 */
