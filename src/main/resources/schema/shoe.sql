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
);

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
    id         MEDIUMINT                                                                                  NOT NULL AUTO_INCREMENT,
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
);

CREATE INDEX XIE1shoe_name ON shoe (name);


CREATE TABLE shoe_order
(
    order_id MEDIUMINT     NOT NULL,
    shoe_id  MEDIUMINT     NOT NULL,
    price    DECIMAL(9, 2) NOT NULL CHECK (price >= 0),
    amount   INTEGER       NOT NULL DEFAULT 1 CHECK (amount >= 0)
);

ALTER TABLE shoe_order
    ADD PRIMARY KEY (order_id, shoe_id),
    ADD INDEX shoe_order_pk (order_id ASC) VISIBLE;


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
);

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
    ADD INDEX user_order_pk (order_id ASC) VISIBLE;


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
    ADD FOREIGN KEY fk_shoe_order_order_id (order_id) REFERENCES shoe_store.`order` (id)
        ON DELETE CASCADE;

ALTER TABLE shoe_order
    ADD FOREIGN KEY fk_shoe_order_shoe_id (shoe_id) REFERENCES shoe (id);

ALTER TABLE user_order
    ADD FOREIGN KEY fk_user_order_order_id (order_id) REFERENCES shoe_store.`order` (id)
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
    UPDATE `order` SET total_cost = total WHERE id = NEW.order_id;
END;
|
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
    UPDATE `order` SET total_cost = total WHERE id = NEW.order_id;
END;
|
DELIMITER ;

-- USERS------------------------------------------------------------------------
INSERT INTO shoe_store.user VALUE (DEFAULT, 'client', 'client', '1234', 'client@gmail.com', '+380950000001', 'client');
INSERT INTO shoe_store.user VALUE (DEFAULT, 'admin', 'admin', '1234', 'admin@gmail.com', '+380950000000', 'admin');
INSERT INTO shoe_store.user VALUE (DEFAULT, 'warehouse', 'warehouse', '1234', 'warehouse@gmail.com', '+380950000002',
                                   'warehouse');
INSERT INTO shoe_store.user VALUE (DEFAULT, 'packer', 'packer', '1234', 'packer@gmail.com', '+380950000003', 'packer');
INSERT INTO shoe_store.user VALUE (DEFAULT, 'courier', 'courier', '1234', 'courier@gmail.com', '+380950000004',
                                   'courier');
INSERT INTO user (name, surname, password, email, phone_number, role)
VALUES
    ('John', 'Doe', 'password1', 'johndoe@example.com', '+380234567890', 'client'),
    ('Jane', 'Smith', 'password2', 'janesmith@example.com', '+380987654321', 'client'),
    ('Michael', 'Johnson', 'password3', 'michaeljohnson@example.com', '+380551234567', 'admin'),
    ('Emily', 'Williams', 'password4', 'emilywilliams@example.com', '+380999999999', 'client'),
    ('David', 'Brown', 'password5', 'davidbrown@example.com', '+380112223333', 'packer'),
    ('Jennifer', 'Jones', 'password6', 'jenniferjones@example.com', '+380445556666', 'warehouse'),
    ('Daniel', 'Taylor', 'password7', 'danieltaylor@example.com', '+380778889999', 'courier'),
    ('Sarah', 'Wilson', 'password8', 'sarahwilson@example.com', '+380223334444', 'client'),
    ('Matthew', 'Anderson', 'password9', 'matthewanderson@example.com', '+380887776666', 'client'),
    ('Olivia', 'Davis', 'password10', 'oliviadavis@example.com', '+380666555444', 'client');

-- IMAGES------------------------------------------------------------------
INSERT INTO shoe_store.image VALUE (1, '1', '1');

-- SHOES-------------------------------------------------------------------
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

-- ADDRESS-----------------------------------------------------------------------
INSERT INTO shoe_store.address VALUE (1, 'Ukraine', 'Kharkiv', 'Tselinogradska', '58', 1, '23');
INSERT INTO address (country, city, street, house_number, entrance, apartment_number)
VALUES
    ('Ukraine', 'Kyiv', 'Main Street', '123', 1, 1),
    ('Ukraine', 'Lviv', 'Central Avenue', '456', 2, 5),
    ('Ukraine', 'Kharkiv', 'Freedom Square', '789', NULL, 10),
    ('Ukraine', 'Odessa', 'Deribasovskaya Street', '321', NULL, NULL),
    ('Ukraine', 'Dnipro', 'Shevchenko Boulevard', '555', 3, 7),
    ('Ukraine', 'Zaporizhzhia', 'Lenin Avenue', '777', 1, 3),
    ('Ukraine', 'Vinnytsia', 'Soborna Street', '999', NULL, 15),
    ('Ukraine', 'Kherson', 'Heroes of Stalingrad Street', '222', 4, 8),
    ('Ukraine', 'Poltava', 'Pushkin Street', '444', 2, 6),
    ('Ukraine', 'Lviv', 'Rynok Square', '666', 1, 2);

-- IMAGES------------------------------------------------------------------------
INSERT INTO image (name, path)
VALUES
    ('Winter Boot Image', '/images/winter-boot.jpg'),
    ('Sandals Image', '/images/sandals.jpg'),
    ('Sneakers Image', '/images/sneakers.jpg'),
    ('Flip Flops Image', '/images/flip-flops.jpg'),
    ('Sports Shoes Image', '/images/sports-shoes.jpg');

-- CART--------------------------------------------------------------------------
INSERT INTO cart (client_id) VALUES (1), (6),(7),(9),(13);

-- CART_SHOE---------------------------------------------------------------------
INSERT INTO cart_shoe (shoe_id, cart_id, price, amount)
VALUES
    (1, 1, 79.99, 2),
    (3, 1, 79.99, 1),
    (5, 2, 99.99, 1),
    (7, 2, 99.99, 2),
    (9, 3, 79.99, 1),
    (11, 3, 79.99, 3),
    (13, 4, 49.99, 4),
    (15, 4, 49.99, 1),
    (17, 5, 69.99, 2),
    (19, 5, 69.99, 1);

-- ORDER------------------------------------------------------------------------
INSERT INTO `order` (address_id, status)
VALUES
    (1, 'processing'),
    (2, 'accepted'),
    (3, 'compiled'),
    (4, 'ready_for_sending'),
    (5, 'delivered');

-- SHOE_ORDER------------------------------------------------------------------------
INSERT INTO shoe_order (order_id, shoe_id, price, amount)
VALUES
    (1, 1, 79.99, 2),
    (1, 3, 79.99, 1),
    (2, 5, 99.99, 1),
    (2, 7, 99.99, 2),
    (3, 9, 79.99, 1),
    (3, 11, 79.99, 3),
    (4, 13, 49.99, 4),
    (4, 15, 49.99, 1),
    (5, 17, 69.99, 2),
    (5, 19, 69.99, 1);

-- USER_ORDER------------------------------------------------------------------------
INSERT INTO user_order (order_id, user_id, description)
VALUES
    (1, 1, 'Order for John Doe'),
    (2, 1, 'Order for John Doe'),
    (3, 2, 'Order for Jennifer Jones'),
    (4, 3, 'Order for Michael Johnson'),
    (5, 4, 'Order for Emily Williams');

SELECT * FROM `order`;
SELECT * from `order` WHERE status='processing';

SELECT
    a.*,
    o.id AS order_id,
    s.*,
    u.role
FROM
    `order` o
        INNER JOIN address a ON o.address_id = a.id
        INNER JOIN user_order uo ON o.id = uo.order_id
        INNER JOIN user u ON uo.user_id = u.id
        INNER JOIN shoe_order so ON o.id = so.order_id
        INNER JOIN shoe s ON so.shoe_id = s.id
WHERE
        o.status = 'processing'



