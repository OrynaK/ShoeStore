-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema shoe_store
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema shoe_store
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `shoe_store` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `shoe_store`;

-- -----------------------------------------------------
-- Table `shoe_store`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`address`;

CREATE TABLE IF NOT EXISTS `shoe_store`.`address`
(
    `address_id`       INT         NOT NULL AUTO_INCREMENT,
    `country`          VARCHAR(45) NOT NULL,
    `city`             VARCHAR(45) NOT NULL,
    `street`           VARCHAR(45) NOT NULL,
    `house_number`     VARCHAR(10) NOT NULL,
    `entrance`         INT         NULL DEFAULT NULL,
    `apartment_number` INT         NULL DEFAULT NULL,
    PRIMARY KEY (`address_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 2
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `shoe_store`.`image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`image`;

CREATE TABLE IF NOT EXISTS `shoe_store`.`image`
(
    `image_id` INT          NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(45)  NOT NULL,
    `path`     VARCHAR(255) NOT NULL,
    PRIMARY KEY (`image_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 2
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `shoe_store`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`user`;

CREATE TABLE IF NOT EXISTS `shoe_store`.`user`
(
    `user_id`      INT                                                        NOT NULL AUTO_INCREMENT,
    `name`         VARCHAR(45)                                                NOT NULL,
    `surname`      VARCHAR(45)                                                NOT NULL,
    `password`     VARCHAR(45)                                                NOT NULL,
    `email`        VARCHAR(45)                                                NOT NULL,
    `role`         ENUM ('client', 'admin', 'packer', 'warehouse', 'courier') NOT NULL DEFAULT 'client',
    `phone_number` VARCHAR(45)                                                NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 15
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `shoe_store`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`order`;

CREATE TABLE IF NOT EXISTS `shoe_store`.`order`
(
    `order_id`     INT                                                                                     NOT NULL AUTO_INCREMENT,
    `date`         DATE                                                                                    NOT NULL,
    `time`         TIME                                                                                    NOT NULL,
    `status`       ENUM ('processing', 'accepted', 'compiled', 'ready_for_sending', 'delivered', 'basket') NOT NULL DEFAULT 'basket',
    `client_id`    INT                                                                                     NOT NULL,
    `address_id`   INT                                                                                     NOT NULL,
    `admin_id`     INT                                                                                     NOT NULL,
    `packer_id`    INT                                                                                     NOT NULL,
    `warehouse_id` INT                                                                                     NOT NULL,
    `courier_id`   INT                                                                                     NOT NULL,
    PRIMARY KEY (`order_id`),
    INDEX `order_address_fk_idx` (`address_id` ASC) VISIBLE,
    INDEX `order_admin_fk_idx` (`client_id` ASC) VISIBLE,
    INDEX `order_admin_fk_idx1` (`admin_id` ASC) VISIBLE,
    INDEX `order_packer_fk_idx` (`packer_id` ASC) VISIBLE,
    INDEX `order_warehouse_fk_idx` (`warehouse_id` ASC) VISIBLE,
    INDEX `order_courier_fk_idx` (`courier_id` ASC) VISIBLE,
    CONSTRAINT `order_address_fk`
        FOREIGN KEY (`address_id`)
            REFERENCES `shoe_store`.`address` (`address_id`),
    CONSTRAINT `order_client_fk`
        FOREIGN KEY (`client_id`)
            REFERENCES `shoe_store`.`user` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `order_admin_fk`
        FOREIGN KEY (`admin_id`)
            REFERENCES `shoe_store`.`user` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `order_packer_fk`
        FOREIGN KEY (`packer_id`)
            REFERENCES `shoe_store`.`user` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `order_warehouse_fk`
        FOREIGN KEY (`warehouse_id`)
            REFERENCES `shoe_store`.`user` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `order_courier_fk`
        FOREIGN KEY (`courier_id`)
            REFERENCES `shoe_store`.`user` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `shoe_store`.`shoe`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`shoe`;

CREATE TABLE IF NOT EXISTS `shoe_store`.`shoe`
(
    `shoe_id`      INT                               NOT NULL AUTO_INCREMENT,
    `size`         DECIMAL(10, 1)                    NOT NULL,
    `color`        VARCHAR(45)                       NOT NULL,
    `season`       ENUM ('winter', 'demi', 'summer') NOT NULL,
    `sex`          ENUM ('male', 'female')           NOT NULL,
    `actual_price` DECIMAL(10, 2)                    NOT NULL,
    `name`         VARCHAR(45)                       NOT NULL,
    `amount`       INT                               NOT NULL,
    `image_id`     INT                               NOT NULL DEFAULT '1',
    PRIMARY KEY (`shoe_id`),
    UNIQUE INDEX `shoe_id_UNIQUE` (`shoe_id` ASC) VISIBLE,
    INDEX `shoe_image_idx` (`image_id` ASC) VISIBLE,
    CONSTRAINT `shoe_image`
        FOREIGN KEY (`image_id`)
            REFERENCES `shoe_store`.`image` (`image_id`)
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 56
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `shoe_store`.`shoes_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `shoe_store`.`shoes_order`;

CREATE TABLE IF NOT EXISTS `shoe_store`.`shoes_order`
(
    `order_id`     INT            NOT NULL,
    `shoe_id`      INT            NOT NULL,
    `shoes_amount` INT            NOT NULL,
    `shoe_price`   DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY (`order_id`, `shoe_id`),
    INDEX `shoe_pk_idx` (`shoe_id` ASC) VISIBLE,
    CONSTRAINT `order_pk1`
        FOREIGN KEY (`order_id`)
            REFERENCES `shoe_store`.`order` (`order_id`),
    CONSTRAINT `shoe_pk`
        FOREIGN KEY (`shoe_id`)
            REFERENCES `shoe_store`.`shoe` (`shoe_id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;


INSERT INTO shoe_store.address VALUE (1, 'ukraine', 'kharkiv', 'tselinogradska', '58', 1, '23');
INSERT INTO shoe_store.user VALUE (DEFAULT, 'admin', 'admin', '1234', 'admin@gmail.com', 'admin', '+380950000000');
INSERT INTO shoe_store.user VALUE (DEFAULT, 'client', 'client', '12345', 'client@gmail.com', 'client', '+380951212000');
INSERT INTO shoe_store.image VALUE (1, '1', '1');
INSERT INTO `shoe` (`size`, `color`, `season`, `sex`, `actual_price`, `name`, `amount`, `image_id`)
VALUES (33.5, 'black', 'winter', 'male', 79.99, 'Mens Winter Boots', 10, 1),
       (33.0, 'black', 'demi', 'male', 79.99, 'Mens Winter Boots', 10, 1),
       (33.5, 'black', 'demi', 'male', 79.99, 'Mens Winter Boots', 10, 1),
       (33.0, 'black', 'demi', 'male', 79.99, 'Mens Winter Boots', 10, 1),
       (35.0, 'brown', 'summer', 'female', 99.99, 'Womens Sandals', 15, 1),
       (34.5, 'brown', 'summer', 'female', 99.99, 'Womens Sandals', 15, 1),
       (35.0, 'brown', 'summer', 'female', 99.99, 'Womens Sandals', 15, 1),
       (34.5, 'brown', 'summer', 'female', 99.99, 'Womens Sandals', 15, 1),
       (37.5, 'red', 'demi', 'female', 79.99, 'Womens Sneakers', 5, 1),
       (36.5, 'red', 'demi', 'female', 79.99, 'Womens Sneakers', 5, 1),
       (37.5, 'red', 'demi', 'female', 79.99, 'Womens Sneakers', 5, 1),
       (36.5, 'red', 'demi', 'female', 79.99, 'Womens Sneakers', 5, 1),
       (38.0, 'blue', 'summer', 'male', 49.99, 'Mens Flip Flops', 20, 1),
       (37.0, 'blue', 'summer', 'male', 49.99, 'Mens Flip Flops', 20, 1),
       (38.0, 'blue', 'summer', 'male', 49.99, 'Mens Flip Flops', 20, 1),
       (37.0, 'blue', 'summer', 'male', 49.99, 'Mens Flip Flops', 20, 1),
       (40.0, 'green', 'summer', 'male', 69.99, 'Mens Sports Shoes', 7, 1),
       (39.5, 'green', 'summer', 'male', 69.99, 'Mens Sports Shoes', 7, 1),
       (40.0, 'green', 'summer', 'male', 69.99, 'Mens Sports Shoes', 7, 1),
       (39.5, 'green', 'summer', 'male', 69.99, 'Mens Sports Shoes', 7, 1);