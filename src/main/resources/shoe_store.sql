-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema shoe_store
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `shoe_store` ;

-- -----------------------------------------------------
-- Schema shoe_store
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `shoe_store` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `shoe_store` ;

-- -----------------------------------------------------
-- Table `shoe_store`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`address` ;

CREATE TABLE IF NOT EXISTS `shoe_store`.`address` (
  `address_id` INT NOT NULL AUTO_INCREMENT,
  `country` VARCHAR(45) NOT NULL,
  `city` VARCHAR(45) NOT NULL,
  `street` VARCHAR(45) NOT NULL,
  `house_number` VARCHAR(10) NOT NULL,
  `entrance` INT NULL DEFAULT NULL,
  `apartment_number` INT NULL DEFAULT NULL,
  PRIMARY KEY (`address_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `shoe_store`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`order` ;

CREATE TABLE IF NOT EXISTS `shoe_store`.`order` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `time` TIME NOT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `shoe_store`.`shoe`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`shoe` ;

CREATE TABLE IF NOT EXISTS `shoe_store`.`shoe` (
  `shoe_id` INT NOT NULL AUTO_INCREMENT,
  `size` DECIMAL(10,1) NOT NULL,
  `color` VARCHAR(45) NOT NULL,
  `season` ENUM('winter', 'demi', 'summer') NOT NULL,
  `sex` ENUM('male', 'female') NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`shoe_id`),
  UNIQUE INDEX `shoe_id_UNIQUE` (`shoe_id` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `shoe_store`.`shoes_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`shoes_order` ;

CREATE TABLE IF NOT EXISTS `shoe_store`.`shoes_order` (
  `order_id` INT NOT NULL,
  `shoe_id` INT NOT NULL,
  `shoes_amount` INT NOT NULL,
  PRIMARY KEY (`order_id`, `shoe_id`),
  INDEX `shoe_pk_idx` (`shoe_id` ASC) VISIBLE,
  CONSTRAINT `order_pk1`
    FOREIGN KEY (`order_id`)
    REFERENCES `shoe_store`.`order` (`order_id`),
  CONSTRAINT `shoe_pk`
    FOREIGN KEY (`shoe_id`)
    REFERENCES `shoe_store`.`shoe` (`shoe_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `shoe_store`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`user` ;

CREATE TABLE IF NOT EXISTS `shoe_store`.`user` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `surname` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `role` ENUM('client', 'admin', 'packer', 'warehouse', 'courier') NOT NULL DEFAULT 'client',
  `blocked` TINYINT NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `shoe_store`.`user_address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`user_address` ;

CREATE TABLE IF NOT EXISTS `shoe_store`.`user_address` (
  `user_id` INT NOT NULL,
  `address_id` INT NOT NULL,
  PRIMARY KEY (`user_id`, `address_id`),
  INDEX `address_id_idx` (`address_id` ASC) VISIBLE,
  CONSTRAINT `address_id`
    FOREIGN KEY (`address_id`)
    REFERENCES `shoe_store`.`address` (`address_id`),
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `shoe_store`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `shoe_store`.`user_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`user_order` ;

CREATE TABLE IF NOT EXISTS `shoe_store`.`user_order` (
  `user_id` INT NOT NULL,
  `order_id` INT NOT NULL,
  `status` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`user_id`, `order_id`),
  INDEX `order_pk2_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `order_pk2`
    FOREIGN KEY (`order_id`)
    REFERENCES `shoe_store`.`order` (`order_id`),
  CONSTRAINT `user_pk`
    FOREIGN KEY (`user_id`)
    REFERENCES `shoe_store`.`user` (`user_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
