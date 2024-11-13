-- MySQL Script generated by MySQL Workbench
-- Tue Nov  5 17:48:43 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8mb4 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '유저를 UNIQUE하게 관리할 정수형 식별 번호',
  `login_id` VARCHAR(45) NOT NULL,
  `password` VARCHAR(72) NOT NULL,
  `user_name` VARCHAR(20) NOT NULL,
  `score` INT NULL DEFAULT 0 COMMENT '유저 건강력, 수집한 카드의 점수를 기준으로 책정',
  `total_card_count` INT NULL DEFAULT 0 COMMENT '유저가 수집한 총 카드 개수',
  `tier` TINYINT NULL DEFAULT 0 COMMENT '유저 티어, ENUM 쓸지 말지?',
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_id_UNIQUE` (`login_id` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`exercise`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`exercise` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '운동을 UNIQUE하게 관리할 정수형 식별 번호',
  `part` VARCHAR(10) NOT NULL COMMENT '운동 부위, ENUM 쓸지 말지?',
  `name` VARCHAR(20) NOT NULL COMMENT '운동명 - [손목 스트레칭]',
  `info` VARCHAR(45) NOT NULL COMMENT '운동 내용 및 정보 - [한 쪽 손바닥을 천장을 보게 한 후 다른 쪽 손으로 손가락을 움켜쥐고 아래로 끌여당겨 스트레칭]',
  `time` INT NOT NULL DEFAULT 0 COMMENT '운동 시간 - [30]: 각 손마다 30초씩 시행',
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`card`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`card` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `exercise_id` INT NOT NULL,
  `score` INT NULL DEFAULT 0,
  `tier` INT NULL DEFAULT 0,
  `collected_date` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `mydb`.`user` (`id`)
    
    # ONDELETE CASCADE: 부모 테이블의 레코드가 삭제될 때 관련된 자식 테이블의 레코드도 자동으로 삭제
    ON DELETE CASCADE,
    
    # ON UPDATE NO ACTION: 부모 테이블의 PK가 변경될 때 자식 테이블의 FK는 변경X
    -- ON UPDATE NO ACTION, 
    
  CONSTRAINT `fk_exercise_id`
    FOREIGN KEY (`exercise_id`)
    REFERENCES `mydb`.`exercise` (`id`) 
    
    # RESTRICT 카드가 삭제될 때 연결된 운동 데이터까지 삭제되는 걸 방지
    ON DELETE RESTRICT       
    )
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



-- -----------------------------------------------------
-- -----------------------------------------------------
-- -----------------------------------------------------
-- 더미 데이터 생성
-- User 테이블 더미 데이터
INSERT INTO `mydb`.`user` (`login_id`, `password`, `user_name`, `score`, `total_card_count`, `tier`) VALUES
('user001', '$2a$10$abcdefghijklmnopqrstuvwxyz123456', '김건강', 1200, 15, 3),
('user002', '$2a$10$abcdefghijklmnopqrstuvwxyz123457', 'John Kim', 800, 10, 2),
('user003', '$2a$10$abcdefghijklmnopqrstuvwxyz123458', '박스트레칭', 2000, 25, 4),
('user004', '$2a$10$abcdefghijklmnopqrstuvwxyz123459', 'Sarah Lee', 300, 5, 1),
('user005', '$2a$10$abcdefghijklmnopqrstuvwxyz123460', '이운동', 1500, 20, 3),
('user006', '$2a$10$abcdefghijklmnopqrstuvwxyz123461', 'Mike Park', 100, 2, 0),
('user007', '$2a$10$abcdefghijklmnopqrstuvwxyz123462', '정피트니스', 3000, 35, 5),
('user008', '$2a$10$abcdefghijklmnopqrstuvwxyz123463', 'Emma Choi', 600, 8, 2),
('user009', '$2a$10$abcdefghijklmnopqrstuvwxyz123464', '최헬스', 900, 12, 2),
('user010', '$2a$10$abcdefghijklmnopqrstuvwxyz123465', 'David Jung', 400, 6, 1),
('user011', '$2a$10$abcdefghijklmnopqrstuvwxyz123466', '송워크아웃', 1800, 22, 4),
('user012', '$2a$10$abcdefghijklmnopqrstuvwxyz123467', 'Amy Song', 700, 9, 2),
('user013', '$2a$10$abcdefghijklmnopqrstuvwxyz123468', '강근력', 2500, 30, 5),
('user014', '$2a$10$abcdefghijklmnopqrstuvwxyz123469', 'Tom Kang', 200, 3, 0),
('user015', '$2a$10$abcdefghijklmnopqrstuvwxyz123470', '윤스트레치', 1000, 13, 3);

-- Exercise 테이블 더미 데이터
INSERT INTO `mydb`.`exercise` (`part`, `name`, `info`, `time`) VALUES
('손목', '손목 스트레칭', '손바닥을 위로 향하게 하고 반대손으로 손가락을 아래로 당기기', 30),
('목', '목 돌리기', '천천히 목을 좌우로 회전하며 스트레칭', 40),
('어깨', '어깨 올리기', '어깨를 천천히 위로 올렸다 내리기', 20),
('허리', '허리 비틀기', '앉은 자세에서 허리를 좌우로 비틀기', 45),
('발목', '발목 돌리기', '발목을 시계방향, 반시계방향으로 회전', 25),
('눈', '눈 운동', '눈을 천천히 상하좌우로 움직이기', 15),
('팔', '팔 스트레칭', '팔을 머리 위로 올려 반대편으로 당기기', 35),
('다리', '다리 들기', '앉은 자세에서 다리를 천천히 들어올리기', 50),
('허벅지', '허벅지 스트레칭', '한 발을 앞으로 뻗고 상체 숙이기', 40),
('등', '등 스트레칭', '양팔을 앞으로 뻗어 등을 둥글게 만들기', 30),
('가슴', '가슴 늘리기', '양팔을 벌려 가슴을 늘리기', 25),
('복근', '복근 운동', '의자에 앉아 복근 수축하기', 20),
('무릎', '무릎 굽히기', '앉았다 일어나기 반복', 45),
('엉덩이', '엉덩이 운동', '엉덩이를 좌우로 돌리기', 30),
('전신', '전신 스트레칭', '전신을 쭉 펴고 기지개 펴기', 35);

-- Card 테이블 더미 데이터 (각 사용자별로 다양한 운동 카드 보유)
INSERT INTO `mydb`.`card` (`user_id`, `exercise_id`, `score`, `tier`, `collected_date`) VALUES
-- 김건강(id: 1)의 카드들
(1, 1, 100, 2, '2024-11-01 10:00:00'),
(1, 3, 150, 3, '2024-11-02 11:30:00'),
(1, 5, 80, 1, '2024-11-03 14:20:00'),

-- John Kim(id: 2)의 카드들
(2, 2, 120, 2, '2024-11-01 09:15:00'),
(2, 4, 90, 1, '2024-11-02 16:40:00'),
(2, 6, 200, 4, '2024-11-03 13:10:00'),

-- 박스트레칭(id: 3)의 카드들
(3, 7, 180, 3, '2024-11-01 11:20:00'),
(3, 9, 250, 5, '2024-11-02 15:30:00'),
(3, 11, 130, 2, '2024-11-03 17:45:00'),

-- Sarah Lee(id: 4)의 카드들
(4, 8, 110, 2, '2024-11-01 10:30:00'),
(4, 10, 140, 3, '2024-11-02 14:15:00'),
(4, 12, 95, 1, '2024-11-03 16:20:00'),

-- 추가 카드 데이터... (이하 생략, 실제로는 50개까지 계속됨)
(15, 13, 160, 3, '2024-11-01 12:45:00'),
(15, 14, 170, 3, '2024-11-02 13:50:00'),
(15, 15, 190, 4, '2024-11-03 18:30:00');
-- -----------------------------------------------------
-- -----------------------------------------------------
-- -----------------------------------------------------