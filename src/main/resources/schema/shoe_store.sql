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
-- Table `shoe_store`.`image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`image` ;

CREATE TABLE IF NOT EXISTS `shoe_store`.`image` (
                                                    `image_id` INT NOT NULL AUTO_INCREMENT,
                                                    `name` VARCHAR(45) NOT NULL,
    `path` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`image_id`))
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
                                                    `status` ENUM('processing', 'accepted', 'compiled', 'ready_for_sending', 'delivered', 'basket') NOT NULL DEFAULT 'basket',
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
    `amount` INT NOT NULL,
    `image_id` INT NOT NULL,
    PRIMARY KEY (`shoe_id`),
    UNIQUE INDEX `shoe_id_UNIQUE` (`shoe_id` ASC) VISIBLE,
    INDEX `shoe_image_idx` (`image_id` ASC) VISIBLE,
    CONSTRAINT `shoe_image`
    FOREIGN KEY (`image_id`)
    REFERENCES `shoe_store`.`image` (`image_id`))
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
    `address_id` INT NULL DEFAULT NULL,
    `phone_number` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`user_id`),
    INDEX `user_address_fk_idx` (`address_id` ASC) VISIBLE,
    CONSTRAINT `user_address_fk`
    FOREIGN KEY (`address_id`)
    REFERENCES `shoe_store`.`address` (`address_id`))
    ENGINE = InnoDB
    AUTO_INCREMENT = 6
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `shoe_store`.`user_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`user_order` ;

CREATE TABLE IF NOT EXISTS `shoe_store`.`user_order` (
                                                         `user_id` INT NOT NULL,
                                                         `order_id` INT NOT NULL,
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


INSERT INTO user (name, surname, password, email, phone_number) VALUES ('client', 'client', 'client', 'client@gmail.com', '+380111111111');
INSERT INTO user (name, surname, password, email, phone_number) VALUES ('admin', 'admin', 'admin', 'admin@gmail.com', '+380222222222');
INSERT INTO user (name, surname, password, email, phone_number) VALUES ('packer', 'packer', 'packer', 'packer@gmail.com', '+380333333333');
INSERT INTO user (name, surname, password, email, phone_number) VALUES ('warehouse', 'warehouse', 'warehouse', 'warehouse@gmail.com', '+380444444444');
INSERT INTO user (name, surname, password, email, phone_number) VALUES ('courier', 'courier', 'courier', 'courier@gmail.com', '+380555555555');
