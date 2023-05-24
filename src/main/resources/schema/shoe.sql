DROP SCHEMA IF EXISTS shoe_store;

CREATE SCHEMA IF NOT EXISTS shoe_store DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE shoe_store;

DROP TABLE IF EXISTS user_order;

DROP TABLE IF EXISTS cart_shoe;

DROP TABLE IF EXISTS cart;

DROP TABLE IF EXISTS `user`;

DROP TABLE IF EXISTS shoe_order;

DROP TABLE IF EXISTS shoe;

DROP TABLE IF EXISTS image;

DROP TABLE IF EXISTS `order`;

DROP TABLE IF EXISTS address;


CREATE TABLE address
(
    id               MEDIUMINT   NOT NULL AUTO_INCREMENT,
    country          VARCHAR(45) NOT NULL,
    city             VARCHAR(45) NOT NULL,
    street           VARCHAR(45) NOT NULL,
    house_number     VARCHAR(10) NOT NULL,
    entrance         INTEGER     NULL DEFAULT NULL CHECK (entrance >= 0),
    apartment_number INTEGER     NULL DEFAULT NULL CHECK (apartment_number >= 0),
    PRIMARY KEY (id),
    UNIQUE INDEX address_id_UNIQUE (id ASC) VISIBLE
)
    AUTO_INCREMENT = 10;

CREATE INDEX XIE1address_city_street_country ON address
    (city, street, country);


CREATE TABLE cart
(
    id        MEDIUMINT NOT NULL AUTO_INCREMENT,
    client_id MEDIUMINT NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX cart_id_UNIQUE (id ASC) VISIBLE
);


CREATE TABLE cart_shoe
(
    shoe_id MEDIUMINT     NOT NULL,
    cart_id MEDIUMINT     NOT NULL,
    price   DECIMAL(9, 2) NOT NULL CHECK (price >= 0),
    amount  INTEGER       NOT NULL DEFAULT 1 CHECK (amount >= 0)
);

ALTER TABLE cart_shoe
    ADD PRIMARY KEY (shoe_id, cart_id),
    ADD INDEX shoe_pk (shoe_id ASC) VISIBLE;


CREATE TABLE image
(
    id   MEDIUMINT    NOT NULL AUTO_INCREMENT,
    name VARCHAR(30)  NOT NULL,
    path VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX image_id_UNIQUE (id ASC) VISIBLE
);

CREATE UNIQUE INDEX XAK1image_path ON image
    (path);

CREATE INDEX XIE1image_name ON image
    (name);


CREATE TABLE `order`
(
    id         MEDIUMINT                                                                                  NOT NULL DEFAULT 0,
    address_id MEDIUMINT                                                                                  NOT NULL,
    datetime   TIMESTAMP                                                                                  NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_cost DECIMAL(9, 2)                                                                              NOT NULL DEFAULT 0 CHECK (total_cost >= 0),
    status     ENUM ('processing', 'accepted', 'compiled', 'ready_for_sending', 'delivered', 'cancelled') NOT NULL DEFAULT 'processing',
    PRIMARY KEY (id),
    UNIQUE INDEX order_id_UNIQUE (id ASC) VISIBLE
);

CREATE INDEX XIE2order_address_id ON `order`
    (address_id);


CREATE TABLE shoe
(
    id           MEDIUMINT                         NOT NULL AUTO_INCREMENT,
    name         VARCHAR(30)                       NOT NULL,
    size         DECIMAL(10, 1)                    NOT NULL,
    color        VARCHAR(45)                       NOT NULL,
    image_id     MEDIUMINT                         NOT NULL DEFAULT 1,
    amount       INTEGER                           NOT NULL DEFAULT 0 CHECK (amount >= 0),
    actual_price DECIMAL(9, 2)                     NOT NULL,
    season       ENUM ('winter', 'demi', 'summer') NOT NULL,
    sex          ENUM ('male', 'female')           NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX shoe_id_UNIQUE (id ASC) VISIBLE
)
    AUTO_INCREMENT = 36;

CREATE INDEX XIE1shoe_name ON shoe (name);


CREATE TABLE shoe_order
(
    shoe_id  MEDIUMINT     NOT NULL,
    order_id MEDIUMINT     NOT NULL,
    price    DECIMAL(9, 2) NOT NULL CHECK (price >= 0),
    amount   INTEGER       NOT NULL DEFAULT 1 CHECK (amount >= 0)
);

ALTER TABLE shoe_order
    ADD PRIMARY KEY (shoe_id, order_id),
    ADD INDEX shoe_pk (shoe_id ASC) VISIBLE;


CREATE TABLE user
(
    id           MEDIUMINT                                                  NOT NULL AUTO_INCREMENT,
    name         VARCHAR(30)                                                NOT NULL,
    surname      VARCHAR(30)                                                NOT NULL,
    password     VARCHAR(45)                                                NOT NULL,
    email        VARCHAR(30)                                                NOT NULL,
    phone_number VARCHAR(13)                                                NOT NULL,
    role         ENUM ('client', 'admin', 'packer', 'warehouse', 'courier') NOT NULL DEFAULT 'client',
    PRIMARY KEY (id),
    UNIQUE INDEX user_id_UNIQUE (id ASC) VISIBLE
)
    AUTO_INCREMENT = 20;

CREATE UNIQUE INDEX XAK1user_email ON user
    (email);

CREATE UNIQUE INDEX XAK2user_phone_number ON user
    (phone_number);

CREATE INDEX XIE1user_name ON user
    (name, surname);


CREATE TABLE user_order
(
    order_id    MEDIUMINT    NOT NULL,
    user_id     MEDIUMINT    NOT NULL,
    description VARCHAR(255) NULL,
    datetime    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

ALTER TABLE user_order
    ADD PRIMARY KEY (order_id, user_id),
    ADD INDEX order_pk (order_id ASC) VISIBLE;


ALTER TABLE cart
    ADD FOREIGN KEY fk_cart_client_id (client_id) REFERENCES user (id);

ALTER TABLE cart_shoe
    ADD FOREIGN KEY fk_cart_shoe_cart_id (cart_id) REFERENCES cart (id)
        ON DELETE CASCADE;

ALTER TABLE cart_shoe
    ADD FOREIGN KEY fk_cart_shoe_shoe_id (shoe_id) REFERENCES shoe (id);

ALTER TABLE `order`
    ADD FOREIGN KEY fk_order_address_id (address_id) REFERENCES address (id)
        ON DELETE CASCADE;

ALTER TABLE shoe
    ADD FOREIGN KEY fk_shoe_image_id (image_id) REFERENCES image (id);

ALTER TABLE shoe_order
    ADD FOREIGN KEY fk_shoe_order_order_id (order_id) REFERENCES `order` (id)
        ON DELETE CASCADE;

ALTER TABLE shoe_order
    ADD FOREIGN KEY fk_shoe_order_shoe_id (shoe_id) REFERENCES shoe (id);

ALTER TABLE user_order
    ADD FOREIGN KEY fk_user_order_order_id (order_id) REFERENCES `order` (id)
        ON DELETE CASCADE;

ALTER TABLE user_order
    ADD FOREIGN KEY fk_user_order_user_id (user_id) REFERENCES user (id);


-- Trigger to update total cost of order after inserting new shoe_order
DELIMITER |
CREATE TRIGGER shoe_order_after_insert
    AFTER INSERT
    ON shoe_order
    FOR EACH ROW
BEGIN
    DECLARE total DECIMAL(9, 2);
    SELECT SUM(price * amount) INTO total FROM shoe_order WHERE order_id = NEW.order_id;
    UPDATE `order` SET total_cost = total WHERE order_id = NEW.order_id;
END;
DELIMITER ;

-- Trigger to update total cost of order after updating shoe_order
DELIMITER |
CREATE TRIGGER shoe_order_after_update
    AFTER UPDATE
    ON shoe_order
    FOR EACH ROW
BEGIN
    DECLARE total DECIMAL(9, 2);
    SELECT SUM(price * amount) INTO total FROM shoe_order WHERE order_id = NEW.order_id;
    UPDATE `order` SET total_cost = total WHERE order_id = NEW.order_id;
END;
DELIMITER ;


INSERT INTO shoe_store.address VALUE (1, 'Ukraine', 'Kharkiv', 'Tselinogradska', '58', 1, '23');
INSERT INTO shoe_store.user VALUE (DEFAULT, 'client', 'client', '1234', 'client@gmail.com', '+380950000001', 'client');
INSERT INTO shoe_store.user VALUE (DEFAULT, 'admin', 'admin', '1234', 'admin@gmail.com', '+380950000000', 'admin');
INSERT INTO shoe_store.user VALUE (DEFAULT, 'warehouse', 'warehouse', '1234', 'warehouse@gmail.com', '+380950000002',
                                   'warehouse');
INSERT INTO shoe_store.user VALUE (DEFAULT, 'packer', 'packer', '1234', 'packer@gmail.com', '+380950000003', 'packer');
INSERT INTO shoe_store.user VALUE (DEFAULT, 'courier', 'courier', '1234', 'courier@gmail.com', '+380950000004',
                                   'courier');
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
