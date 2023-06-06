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
    cart_id MEDIUMINT     NOT NULL,
    shoe_id MEDIUMINT     NOT NULL,
    price   DECIMAL(9, 2) NOT NULL CHECK (price >= 0),
    amount  INTEGER       NOT NULL DEFAULT 1 CHECK (amount >= 0)
);

ALTER TABLE cart_shoe
    ADD PRIMARY KEY (cart_id, shoe_id),
    ADD INDEX cart_shoe_pk (cart_id ASC) VISIBLE;


CREATE TABLE image
(
    id   MEDIUMINT    NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
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


-- Trigger to update total cost of order after inserting new shoe_order ------------------------------------------------
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

-- Trigger to update total cost of order after updating shoe_order -----------------------------------------------------
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

-- Trigger to update shoe amount after inserting new shoe_order --------------------------------------------------------
DELIMITER |
CREATE TRIGGER subtract_shoe_amount
    AFTER INSERT
    ON shoe_order
    FOR EACH ROW
BEGIN
    UPDATE shoe
    SET amount = amount - NEW.amount
    WHERE id = NEW.shoe_id;
END;
|
DELIMITER ;

-- -------------------------------
DELIMITER //

CREATE TRIGGER return_shoe_amount
    AFTER UPDATE
    ON `order`
    FOR EACH ROW
BEGIN
    IF NEW.status = 'cancelled' THEN
        UPDATE shoe
        SET amount = amount + (SELECT amount
                               FROM shoe_order
                               WHERE order_id = NEW.id)
        WHERE id IN (SELECT shoe_id
                     FROM shoe_order
                     WHERE order_id = NEW.id);
    END IF;
END//

DELIMITER ;


-- USERS------------------------------------------------------------------------
INSERT INTO user (name, surname, password, email, phone_number, role)
VALUES ('client', 'client', '1234', 'client@gmail.com', '+380950000001', 'client'),
       ('Іван', 'Петров', 'password1', 'ivan@example.com', '+1234560789', 'client'),
       ('Марія', 'Сидорова', 'password2', 'maria@example.com', '+9876504321', 'client'),
       ('Олександр', 'Ковальчук', 'password3', 'oleksandr@example.com', '+1112202333', 'client'),
       ('Анна', 'Коваленко', 'password4', 'anna@example.com', '+4445055666', 'client'),
       ('Максим', 'Шевченко', 'password5', 'maxim@example.com', '+7770888999', 'client'),
       ('Олена', 'Кравченко', 'password6', 'olena@example.com', '+1234567089', 'client'),
       ('Віталій', 'Семенов', 'password7', 'vitaliy@example.com', '+9876454321', 'client'),
       ('Юлія', 'Ткачук', 'password8', 'yulia@example.com', '+1112220333', 'client'),
       ('Андрій', 'Коваль', 'password9', 'andriy@example.com', '+4440555666', 'client'),
       ('Наталія', 'Лисенко', 'password10', 'natalia@example.com', '+7077888999', 'client'),
       ('Павло', 'Мельник', 'password11', 'pavlo@example.com', '+1234567809', 'client'),
       ('Ірина', 'Шевчук', 'password12', 'irina@example.com', '+9876543021', 'client'),
       ('John', 'Doe', 'password1', 'johndoe@example.com', '+380234567809', 'client'),
       ('Jane', 'Smith', 'password2', 'janesmith@example.com', '+380987654321', 'client'),
       ('Emily', 'Williams', 'password4', 'emilywilliams@example.com', '+380999999999', 'client'),
       ('Sarah', 'Wilson', 'password8', 'sarahwilson@example.com', '+380223334444', 'client'),
       ('Matthew', 'Anderson', 'password9', 'matthewanderson@example.com', '+380887776666', 'client'),
       ('Olivia', 'Davis', 'password10', 'oliviadavis@example.com', '+380666555444', 'client'),
       ('Mark', 'Johnson', 'password11', 'markjohnson@example.com', '+380111122233', 'client'),
       ('Samantha', 'Wilson', 'password12', 'samanthawilson@example.com', '+380444433322', 'client'),
       ('Emma', 'Thomas', 'password14', 'emmathomas@example.com', '+380777766655', 'client'),
       ('Isabella', 'Lee', 'password18', 'isabellalee@example.com', '+380888877766', 'client'),
       ('James', 'Garcia', 'password19', 'jamesgarcia@example.com', '+380444433377', 'client'),
       ('Ava', 'Scott', 'password20', 'avascott@example.com', '+380999988844', 'client'),
       ('Benjamin', 'Hernandez', 'password21', 'benjaminhernandez@example.com', '+380222233322', 'client'),
       ('Mia', 'Lopez', 'password22', 'mialopez@example.com', '+380555544488', 'client'),
       ('Elijah', 'Gonzalez', 'password23', 'elijahgonzalez@example.com', '+380111122244', 'client'),
       ('Charlotte', 'Clark', 'password24', 'charlotteclark@example.com', '+380888877700', 'client'),
       ('Lucas', 'Young', 'password25', 'lucasyoung@example.com', '+380444433366', 'client'),

       ('Megan', 'Coleman', '1234', 'admin@gmail.com', '+380950000000', 'admin'),
       ('Alice', 'Brown', '5678', 'alice@gmail.com', '+380950900001', 'admin'),
       ('Emily', 'Smith', '9012', 'emily@gmail.com', '+38095000002', 'admin'),
       ('Jennifer', 'Jones', '1234', 'warehouse@gmail.com', '+380445556666', 'warehouse'),
       ('Sophia', 'Johnson', '7890', 'sophia@gmail.com', '+380445556667', 'warehouse'),
       ('Oliver', 'Davis', '2345', 'oliver@gmail.com', '+380445556668', 'warehouse'),
       ('David', 'Brown', '1234', 'packer@gmail.com', '+380112223333', 'packer'),
       ('Ethan', 'Wilson', '0123', 'ethan@gmail.com', '+380112223334', 'packer'),
       ('William', 'Taylor', '4567', 'william@gmail.com', '+380112223335', 'packer'),
       ('John', 'Snow', '1234', 'courier@gmail.com', '+380950000004', 'courier'),
       ('Liam', 'Anderson', '2345', 'liam@gmail.com', '+380950000005', 'courier'),
       ('Noah', 'Martinez', '6789', 'noah@gmail.com', '+380950000006', 'courier');
;

-- IMAGES------------------------------------------------------------------------
INSERT INTO image (name, path)
VALUES ('MensWinterBoots.png', 'src/main/webapp/my-shoestore/public/images/MensWinterBoots.png'),
       ('WomenSandals.png', 'src/main/webapp/my-shoestore/public/images/WomenSandals.png'),
       ('WomenSneakers.png', 'src/main/webapp/my-shoestore/public/images/WomenSneakers.png'),
       ('MenFlipFlops.png', 'src/main/webapp/my-shoestore/public/images/MenFlipFlops.png'),
       ('MensSportsShoes.png', 'src/main/webapp/my-shoestore/public/images/MensSportsShoes.png'),
       ('WomensWinterBoots.png', 'src/main/webapp/my-shoestore/public/images/WomensWinterBoots.png'),
       ('WomensHeels.png', 'src/main/webapp/my-shoestore/public/images/WomensHeels.png'),
       ('WomensFlats.png', 'src/main/webapp/my-shoestore/public/images/WomensFlats.png'),
       ('MensOxfordsShoes.png', 'src/main/webapp/my-shoestore/public/images/MensOxfordsShoes.png'),
       ('MensLoafers.png', 'src/main/webapp/my-shoestore/public/images/MensLoafers.png'),
       ('WomensSneakers.png', 'src/main/webapp/my-shoestore/public/images/WomensSneakers.png'),
       ('MensClassicalLeatherBoots.png', 'src/main/webapp/my-shoestore/public/images/MensClassicalLeatherBoots.png'),
       ('MensStylishAnkleBoots.png', 'src/main/webapp/my-shoestore/public/images/MensStylishAnkleBoots.png'),
       ('MensFormalDressShoes.png', 'src/main/webapp/my-shoestore/public/images/MensFormalDressShoes.png'),
       ('MensCasualSuedeBoots.png', 'src/main/webapp/my-shoestore/public/images/MensCasualSuedeBoots.png'),
       ('WomensElegantSandals.png', 'src/main/webapp/my-shoestore/public/images/WomensElegantSandals.png'),
       ('WomensFashionWedgeSandals.png', 'src/main/webapp/my-shoestore/public/images/WomensFashionWedgeSandals.png'),
       ('WomensComfortableFlipFlops.png', 'src/main/webapp/my-shoestore/public/images/WomensComfortableFlipFlops.png'),
       ('WomensCasulaSlip-onSandals.png', 'src/main/webapp/my-shoestore/public/images/WomensCasulaSlip-onSandals.png'),
       ('WomensLightWeigthSneakers.png', 'src/main/webapp/my-shoestore/public/images/WomensLightWeigthSneakers.png'),
       ('WomensSportyAthleticShoes.png', 'src/main/webapp/my-shoestore/public/images/WomensSportyAthleticShoes.png'),
       ('WomensStylishCanvasSneakers.png',
        'src/main/webapp/my-shoestore/public/images/WomensStylishCanvasSneakers.png'),
       ('WomensRetroHigh-topSneakers.png',
        'src/main/webapp/my-shoestore/public/images/WomensRetroHigh-topSneakers.png'),
       ('MensComfortableFlipFlops.png', 'src/main/webapp/my-shoestore/public/images/MensComfortableFlipFlops.png'),
       ('MensLightweightSlideSandals.png',
        'src/main/webapp/my-shoestore/public/images/MensLightweightSlideSandals.png'),
       ('MensCasualBeachSandals.png', 'src/main/webapp/my-shoestore/public/images/MensCasualBeachSandals.png'),
       ('MensSportyWaterShoes.png', 'src/main/webapp/my-shoestore/public/images/MensSportyWaterShoes.png'),
       ('MensOutdoorHikingShoes.png', 'src/main/webapp/my-shoestore/public/images/MensOutdoorHikingShoes.png'),
       ('MensBreathtakingRunningShoes.png',
        'src/main/webapp/my-shoestore/public/images/MensBreathtakingRunningShoes.png'),
       ('MensAthletictrainingShoes.png', 'src/main/webapp/my-shoestore/public/images/MensAthletictrainingShoes.png');


-- SHOES-------------------------------------------------------------------
INSERT INTO `shoe` (`size`, `color`, `season`, `sex`, `actual_price`, `name`, `amount`, `image_id`)
VALUES (41.5, 'black', 'winter', 'male', 79.99, 'Mens Winter Boots', 10, 1),
       (42.0, 'black', 'demi', 'male', 79.99, 'Mens Winter Boots', 10, 1),
       (43.5, 'black', 'demi', 'male', 79.99, 'Mens Winter Boots', 10, 1),
       (44.0, 'black', 'demi', 'male', 79.99, 'Mens Winter Boots', 10, 1),

       (38.0, 'brown', 'summer', 'female', 99.99, 'Womens Sandals', 15, 2),
       (38.5, 'brown', 'summer', 'female', 99.99, 'Womens Sandals', 15, 2),
       (39.0, 'brown', 'summer', 'female', 99.99, 'Womens Sandals', 15, 2),
       (39.5, 'brown', 'summer', 'female', 99.99, 'Womens Sandals', 15, 2),

       (38.5, 'red', 'demi', 'female', 79.99, 'Womens Sneakers', 5, 3),
       (39.0, 'red', 'demi', 'female', 79.99, 'Womens Sneakers', 5, 3),
       (39.5, 'red', 'demi', 'female', 79.99, 'Womens Sneakers', 5, 3),
       (40.0, 'red', 'demi', 'female', 79.99, 'Womens Sneakers', 5, 3),

       (40.5, 'blue', 'summer', 'male', 49.99, 'Mens Flip Flops', 20, 4),
       (41.0, 'blue', 'summer', 'male', 49.99, 'Mens Flip Flops', 20, 4),
       (42.5, 'blue', 'summer', 'male', 49.99, 'Mens Flip Flops', 20, 4),
       (43.0, 'blue', 'summer', 'male', 49.99, 'Mens Flip Flops', 20, 4),

       (42.5, 'green', 'summer', 'male', 69.99, 'Mens Sports Shoes', 7, 5),
       (43.5, 'green', 'summer', 'male', 69.99, 'Mens Sports Shoes', 7, 5),
       (44.0, 'green', 'summer', 'male', 69.99, 'Mens Sports Shoes', 7, 5),
       (44.5, 'green', 'summer', 'male', 69.99, 'Mens Sports Shoes', 7, 5),

       (38.0, 'black', 'winter', 'female', 89.99, 'Womens Winter Boots', 8, 6),
       (39.0, 'black', 'winter', 'female', 89.99, 'Womens Winter Boots', 8, 6),

       (38.0, 'brown', 'demi', 'female', 109.99, 'Womens Heels', 12, 7),
       (39.0, 'brown', 'demi', 'female', 109.99, 'Womens Heels', 12, 7),

       (38.0, 'blue', 'summer', 'female', 79.99, 'Womens Flats', 10, 8),
       (39.0, 'blue', 'summer', 'female', 79.99, 'Womens Flats', 10, 8),

       (38.0, 'red', 'demi', 'male', 69.99, 'Mens Oxford Shoes', 5, 9),
       (39.0, 'red', 'demi', 'male', 69.99, 'Mens Oxford Shoes', 5, 9),
       (39.5, 'red', 'demi', 'male', 69.99, 'Mens Oxford Shoes', 5, 9),

       (38.0, 'gray', 'summer', 'male', 59.99, 'Mens Loafers', 15, 10),
       (39.0, 'gray', 'summer', 'male', 59.99, 'Mens Loafers', 15, 10),
       (39.5, 'gray', 'summer', 'male', 59.99, 'Mens Loafers', 15, 10),

       (38.0, 'white', 'summer', 'female', 69.99, 'Womens Sneakers', 10, 11),
       (39.0, 'white', 'summer', 'female', 69.99, 'Womens Sneakers', 10, 11),
       (39.5, 'white', 'summer', 'female', 69.99, 'Womens Sneakers', 10, 11),

       (41.0, 'black', 'winter', 'male', 89.99, 'Mens Classic Leather Boots', 10, 12),
       (42.0, 'black', 'winter', 'male', 89.99, 'Mens Classic Leather Boots', 10, 12),
       (43.0, 'black', 'winter', 'male', 89.99, 'Mens Classic Leather Boots', 10, 12),
       (44.0, 'black', 'winter', 'male', 89.99, 'Mens Classic Leather Boots', 10, 12),

       (41.0, 'black', 'winter', 'male', 89.99, 'Mens Stylish Ankle Boots', 10, 13),
       (42.0, 'black', 'winter', 'male', 89.99, 'Mens Stylish Ankle Boots', 10, 13),
       (43.0, 'black', 'winter', 'male', 89.99, 'Mens Stylish Ankle Boots', 10, 13),
       (44.0, 'black', 'winter', 'male', 89.99, 'Mens Stylish Ankle Boots', 10, 13),
       (45.0, 'black', 'winter', 'male', 89.99, 'Mens Stylish Ankle Boots', 10, 13),

       (42.0, 'black', 'winter', 'male', 89.99, 'Mens Formal Dress Shoes', 10, 14),
       (43.0, 'black', 'winter', 'male', 89.99, 'Mens Formal Dress Shoes', 10, 14),
       (44.0, 'black', 'winter', 'male', 89.99, 'Mens Formal Dress Shoes', 10, 14),
       (44.5, 'black', 'winter', 'male', 89.99, 'Mens Formal Dress Shoes', 10, 14),

       (41.0, 'black', 'winter', 'male', 89.99, 'Mens Casual Suede Boots', 10, 15),
       (42.0, 'black', 'winter', 'male', 89.99, 'Mens Casual Suede Boots', 10, 15),
       (43.0, 'black', 'winter', 'male', 89.99, 'Mens Casual Suede Boots', 10, 15),
       (44.0, 'black', 'winter', 'male', 89.99, 'Mens Casual Suede Boots', 10, 15),

       (38.0, 'brown', 'summer', 'female', 99.99, 'Womens Elegant Sandals', 15, 16),
       (38.5, 'brown', 'summer', 'female', 99.99, 'Womens Elegant Sandals', 15, 16),
       (39.0, 'brown', 'summer', 'female', 99.99, 'Womens Elegant Sandals', 15, 16),
       (39.5, 'brown', 'summer', 'female', 99.99, 'Womens Elegant Sandals', 15, 16),
       (40.0, 'brown', 'summer', 'female', 99.99, 'Womens Elegant Sandals', 15, 16),

       (38.0, 'brown', 'summer', 'female', 99.99, 'Womens Fashion Wedge Sandals', 15, 17),
       (39.0, 'brown', 'summer', 'female', 99.99, 'Womens Fashion Wedge Sandals', 15, 17),
       (39.5, 'brown', 'summer', 'female', 99.99, 'Womens Fashion Wedge Sandals', 15, 17),
       (40.0, 'brown', 'summer', 'female', 99.99, 'Womens Fashion Wedge Sandals', 15, 17),

       (39.0, 'brown', 'summer', 'female', 99.99, 'Womens Comfortable Flip Flops', 15, 18),
       (39.5, 'brown', 'summer', 'female', 99.99, 'Womens Comfortable Flip Flops', 15, 18),
       (40.0, 'brown', 'summer', 'female', 99.99, 'Womens Comfortable Flip Flops', 15, 18),
       (40.5, 'brown', 'summer', 'female', 99.99, 'Womens Comfortable Flip Flops', 15, 18),
       (41.0, 'brown', 'summer', 'female', 99.99, 'Womens Comfortable Flip Flops', 15, 18),

       (38.0, 'brown', 'summer', 'female', 99.99, 'Womens Casual Slip-on Sandals', 15, 19),
       (38.5, 'brown', 'summer', 'female', 99.99, 'Womens Casual Slip-on Sandals', 15, 19),
       (39.0, 'brown', 'summer', 'female', 99.99, 'Womens Casual Slip-on Sandals', 15, 19),
       (39.5, 'brown', 'summer', 'female', 99.99, 'Womens Casual Slip-on Sandals', 15, 19),

       (37.0, 'red', 'demi', 'female', 79.99, 'Womens Lightweight Sneakers', 5, 20),
       (38.0, 'red', 'demi', 'female', 79.99, 'Womens Lightweight Sneakers', 5, 20),
       (39.0, 'red', 'demi', 'female', 79.99, 'Womens Lightweight Sneakers', 5, 20),

       (37.0, 'red', 'demi', 'female', 79.99, 'Womens Sporty Athletic Shoes', 5, 21),
       (37.5, 'red', 'demi', 'female', 79.99, 'Womens Sporty Athletic Shoes', 5, 21),
       (38.0, 'red', 'demi', 'female', 79.99, 'Womens Sporty Athletic Shoes', 5, 21),
       (38.5, 'red', 'demi', 'female', 79.99, 'Womens Sporty Athletic Shoes', 5, 21),
       (39.5, 'red', 'demi', 'female', 79.99, 'Womens Sporty Athletic Shoes', 5, 21),
       (40.0, 'red', 'demi', 'female', 79.99, 'Womens Sporty Athletic Shoes', 5, 21),

       (38.0, 'red', 'demi', 'female', 79.99, 'Womens Stylish Canvas Sneakers', 5, 22),
       (38.5, 'red', 'demi', 'female', 79.99, 'Womens Stylish Canvas Sneakers', 5, 22),
       (39.0, 'red', 'demi', 'female', 79.99, 'Womens Stylish Canvas Sneakers', 5, 22),
       (39.5, 'red', 'demi', 'female', 79.99, 'Womens Stylish Canvas Sneakers', 5, 22),
       (40.0, 'red', 'demi', 'female', 79.99, 'Womens Stylish Canvas Sneakers', 5, 22),

       (39.0, 'red', 'demi', 'female', 79.99, 'Womens Retro High-top Sneakers', 5, 23),
       (39.5, 'red', 'demi', 'female', 79.99, 'Womens Retro High-top Sneakers', 5, 23),
       (40.0, 'red', 'demi', 'female', 79.99, 'Womens Retro High-top Sneakers', 5, 23),

       (40.0, 'blue', 'summer', 'male', 49.99, 'Mens Comfortable Flip Flops', 20, 24),
       (40.5, 'blue', 'summer', 'male', 49.99, 'Mens Comfortable Flip Flops', 20, 24),
       (41.0, 'blue', 'summer', 'male', 49.99, 'Mens Comfortable Flip Flops', 20, 24),
       (41.5, 'blue', 'summer', 'male', 49.99, 'Mens Comfortable Flip Flops', 20, 24),
       (42.5, 'blue', 'summer', 'male', 49.99, 'Mens Comfortable Flip Flops', 20, 24),
       (43.5, 'blue', 'summer', 'male', 49.99, 'Mens Comfortable Flip Flops', 20, 24),

       (41.0, 'blue', 'summer', 'male', 49.99, 'Mens Lightweight Slide Sandals', 20, 25),
       (42.0, 'blue', 'summer', 'male', 49.99, 'Mens Lightweight Slide Sandals', 20, 25),
       (43.0, 'blue', 'summer', 'male', 49.99, 'Mens Lightweight Slide Sandals', 20, 25),
       (44.0, 'blue', 'summer', 'male', 49.99, 'Mens Lightweight Slide Sandals', 20, 25),

       (40.0, 'blue', 'summer', 'male', 49.99, 'Mens Casual Beach Sandals', 20, 26),
       (41.5, 'blue', 'summer', 'male', 49.99, 'Mens Casual Beach Sandals', 20, 26),
       (42.0, 'blue', 'summer', 'male', 49.99, 'Mens Casual Beach Sandals', 20, 26),
       (42.5, 'blue', 'summer', 'male', 49.99, 'Mens Casual Beach Sandals', 20, 26),
       (43.0, 'blue', 'summer', 'male', 49.99, 'Mens Casual Beach Sandals', 20, 26),
       (44.5, 'blue', 'summer', 'male', 49.99, 'Mens Casual Beach Sandals', 20, 26),

       (43.0, 'blue', 'summer', 'male', 49.99, 'Mens Sporty Water Shoes', 20, 27),
       (43.5, 'blue', 'summer', 'male', 49.99, 'Mens Sporty Water Shoes', 20, 27),
       (44.5, 'blue', 'summer', 'male', 49.99, 'Mens Sporty Water Shoes', 20, 27),
       (44.0, 'blue', 'summer', 'male', 49.99, 'Mens Sporty Water Shoes', 20, 27),
       (45.0, 'blue', 'summer', 'male', 49.99, 'Mens Sporty Water Shoes', 20, 27),

       (40.0, 'green', 'summer', 'male', 69.99, 'Mens Outdoor Hiking Shoes', 7, 28),
       (41.0, 'green', 'summer', 'male', 69.99, 'Mens Outdoor Hiking Shoes', 7, 28),
       (42.0, 'green', 'summer', 'male', 69.99, 'Mens Outdoor Hiking Shoes', 7, 28),
       (43.0, 'green', 'summer', 'male', 69.99, 'Mens Outdoor Hiking Shoes', 7, 28),
       (44.0, 'green', 'summer', 'male', 69.99, 'Mens Outdoor Hiking Shoes', 7, 28),

       (41.0, 'green', 'summer', 'male', 69.99, 'Mens Breathable Running Shoes', 7, 29),
       (42.0, 'green', 'summer', 'male', 69.99, 'Mens Breathable Running Shoes', 7, 29),
       (43.0, 'green', 'summer', 'male', 69.99, 'Mens Breathable Running Shoes', 7, 29),
       (44.0, 'green', 'summer', 'male', 69.99, 'Mens Breathable Running Shoes', 7, 29),

       (40.0, 'green', 'summer', 'male', 69.99, 'Mens Athletic Training Shoes', 7, 30),
       (45.0, 'green', 'summer', 'male', 69.99, 'Mens Athletic Training Shoes', 7, 30),
       (42.0, 'green', 'summer', 'male', 69.99, 'Mens Athletic Training Shoes', 7, 30),
       (43.0, 'green', 'summer', 'male', 69.99, 'Mens Athletic Training Shoes', 7, 30);

-- ADDRESS-----------------------------------------------------------------------
INSERT INTO address (country, city, street, house_number, entrance, apartment_number)
VALUES ('Україна', 'Львів', 'Нова', '10', '1', '5'),
       ('Україна', 'Київ', 'Центральна', '15', '2', '10'),
       ('Україна', 'Харків', 'Проспектна', '25', '3', '7'),
       ('Україна', 'Одеса', 'Польова', '8', '4', '3'),
       ('Україна', 'Дніпро', 'Велика', '12', '1', '2'),
       ('Україна', 'Запоріжжя', 'Головна', '7', '2', '9'),
       ('Україна', 'Вінниця', 'Перша', '18', '3', '6'),
       ('Україна', 'Хмельницький', 'Соборна', '22', '4', '1'),
       ('Україна', 'Чернівці', 'Лісна', '9', '1', '8'),
       ('Україна', 'Житомир', 'Степова', '14', '2', '4'),
       ('Україна', 'Львів', 'Стара', '5', '1', '5'),
       ('Україна', 'Київ', 'Головна', '10', NULL, NULL),
       ('Україна', 'Харків', 'Сонячна', '20', '2', '7'),
       ('Україна', 'Одеса', 'Морська', '15', NULL, NULL),
       ('Україна', 'Дніпро', 'Центральна', '18', '1', '2'),
       ('Україна', 'Запоріжжя', 'Першотравнева', '7', NULL, NULL),
       ('Україна', 'Вінниця', 'Грушевського', '12', '3', '6'),
       ('Україна', 'Хмельницький', 'Миру', '22', NULL, NULL),
       ('Україна', 'Чернівці', 'Городоцька', '9', '1', '8'),
       ('Україна', 'Житомир', 'Київська', '14', NULL, NULL),
       ('Україна', 'Львів', 'Нова', '10', '2', '4'),
       ('Україна', 'Київ', 'Шевченка', '15', NULL, NULL),
       ('Україна', 'Харків', 'Сумська', '25', '3', '7'),
       ('Україна', 'Одеса', 'Польова', '8', NULL, NULL),
       ('Україна', 'Дніпро', 'Велика', '12', '1', '2'),
       ('Україна', 'Запоріжжя', 'Головна', '7', NULL, NULL),
       ('Україна', 'Вінниця', 'Перша', '18', '2', '6'),
       ('Україна', 'Хмельницький', 'Соборна', '22', NULL, NULL),
       ('Україна', 'Чернівці', 'Лісна', '9', '1', '8'),
       ('Україна', 'Житомир', 'Степова', '14', NULL, NULL),
       ('Україна', 'Львів', 'Вузька', '5', '2', '4'),
       ('Україна', 'Київ', 'Велика Васильківська', '10', NULL, NULL),
       ('Україна', 'Харків', 'Місячна', '20', '2', '7'),
       ('Україна', 'Одеса', 'Гуморна', '15', NULL, NULL);


-- CART--------------------------------------------------------------------------
INSERT INTO cart (client_id)
VALUES (1),
       (2),
       (3),
       (4),
       (5),
       (6),
       (7),
       (8),
       (9),
       (10),
       (11),
       (12),
       (13),
       (14),
       (15),
       (16),
       (17),
       (18);


-- ORDER------------------------------------------------------------------------
INSERT INTO `order` (address_id, status)
VALUES (1, 'processing'),
       (2, 'processing'),
       (3, 'processing'),
       (4, 'processing'),
       (5, 'processing'),
       (6, 'processing'),
       (7, 'processing'),
       (8, 'accepted'),
       (9, 'accepted'),
       (10, 'accepted'),
       (11, 'accepted'),
       (12, 'accepted'),
       (13, 'accepted'),
       (14, 'accepted'),
       (15, 'compiled'),
       (16, 'compiled'),
       (17, 'compiled'),
       (18, 'compiled'),
       (19, 'compiled'),
       (20, 'compiled'),
       (21, 'compiled'),
       (22, 'ready_for_sending'),
       (23, 'ready_for_sending'),
       (24, 'ready_for_sending'),
       (25, 'ready_for_sending'),
       (26, 'ready_for_sending'),
       (27, 'ready_for_sending'),
       (28, 'ready_for_sending'),
       (29, 'ready_for_sending'),
       (30, 'ready_for_sending'),
       (31, 'ready_for_sending'),
       (32, 'ready_for_sending'),
       (33, 'ready_for_sending');

-- SHOE_ORDER------------------------------------------------------------------------
INSERT INTO shoe_order (order_id, shoe_id, price, amount)
VALUES (1, 1, 79.99, 2),
       (2, 5, 99.99, 1),
       (2, 72, 79.99, 2),
       (3, 91, 49.99, 1),
       (3, 66, 99.99, 1),
       (4, 13, 49.99, 1),
       (4, 90, 49.99, 1),
       (5, 17, 69.99, 2),
       (5, 121, 69.99, 1),
       (5, 3, 79.99, 1),
       (6, 19, 69.99, 1),
       (7, 81, 79.99, 1),
       (8, 20, 69.99, 1),
       (9, 24, 109.99, 1),
       (9, 30, 59.99, 1),
       (10, 26, 79.99, 1),
       (11, 25, 79.99, 1),
       (12, 11, 79.99, 1),
       (13, 21, 89.99, 1),
       (14, 29, 69.99, 1),
       (15, 19, 69.99, 1),
       (16, 4, 79.99, 1),
       (17, 29, 69.99, 1),
       (17, 12, 79.99, 1),
       (18, 55, 99.99, 1),
       (19, 28, 69.99, 1),
       (20, 79, 79.99, 1),
       (21, 10, 79.99, 1),
       (22, 6, 99.99, 1),
       (23, 14, 49.99, 1),
       (24, 15, 49.99, 1),
       (25, 114, 69.99, 1),
       (26, 8, 99.99, 1),
       (26, 72, 79.99, 1),
       (27, 26, 79.99, 1),
       (28, 19, 69.99, 1),
       (28, 68, 99.99, 1),
       (29, 24, 109.99, 1),
       (30, 59, 99.99, 1),
       (30, 12, 79.99, 1),
       (31, 24, 109.99, 1),
       (32, 59, 99.99, 1),
       (33, 12, 79.99, 1);

-- USER_ORDER------------------------------------------------------------------------
INSERT INTO user_order (order_id, user_id, description)
VALUES (1, 1, NULL),
       (2, 2, NULL),
       (3, 3, NULL),
       (4, 4, NULL),
       (5, 5, NULL),
       (6, 6, NULL),
       (7, 7, NULL),
-- accepted
       (8, 8, NULL),
       (8, 31, NULL),

       (9, 9, NULL),
       (9, 32, NULL),

       (10, 10, NULL),
       (10, 33, NULL),

       (11, 11, NULL),
       (11, 31, NULL),

       (12, 12, NULL),
       (12, 32, NULL),

       (13, 13, NULL),
       (13, 33, NULL),

       (14, 14, NULL),
       (14, 31, NULL),
       -- compiled
       (15, 15, NULL),
       (15, 31, NULL),
       (15, 34, NULL),

       (16, 16, NULL),
       (16, 32, NULL),
       (16, 35, NULL),

       (17, 17, NULL),
       (17, 33, NULL),
       (17, 36, NULL),

       (18, 18, NULL),
       (18, 31, NULL),
       (18, 34, NULL),

       (19, 19, NULL),
       (19, 32, NULL),
       (19, 35, NULL),

       (20, 20, NULL),
       (20, 33, NULL),
       (20, 36, NULL),

       (21, 21, NULL),
       (21, 31, NULL),
       (21, 34, NULL),
       -- readyForSending
       (22, 22, NULL),
       (22, 31, NULL),
       (22, 34, NULL),
       (22, 37, NULL),

       (23, 23, NULL),
       (23, 32, NULL),
       (23, 35, NULL),
       (23, 38, NULL),

       (24, 24, NULL),
       (24, 33, NULL),
       (24, 36, NULL),
       (24, 39, NULL),

       (25, 25, NULL),
       (25, 31, NULL),
       (25, 34, NULL),
       (25, 37, NULL),

       (26, 26, NULL),
       (26, 32, NULL),
       (26, 35, NULL),
       (26, 38, NULL),
       (26, 40, NULL),

       (27, 27, NULL),
       (27, 33, NULL),
       (27, 36, NULL),
       (27, 39, NULL),
       (27, 41, NULL),

       (28, 28, NULL),
       (28, 31, NULL),
       (28, 34, NULL),
       (28, 37, NULL),
       (28, 42, NULL),

       (29, 29, NULL),
       (29, 32, NULL),
       (29, 35, NULL),
       (29, 38, NULL),
       (29, 40, NULL),

       (30, 30, NULL),
       (30, 33, NULL),
       (30, 36, NULL),
       (30, 39, NULL),
       (30, 34, NULL),

       (31, 29, NULL),
       (31, 32, NULL),
       (31, 35, NULL),
       (31, 38, NULL),
       (31, 40, NULL),

       (32, 29, NULL),
       (32, 32, NULL),
       (32, 35, NULL),
       (32, 38, NULL),
       (32, 40, NULL),

       (33, 29, NULL),
       (33, 32, NULL),
       (33, 35, NULL),
       (33, 38, NULL),
       (33, 40, NULL);



SELECT u.id,
       u.name,
       u.surname,
       uo.order_id,
       SUM(so.price * so.amount) AS total_spent
FROM `user` AS u
         JOIN
     user_order AS uo ON u.id = uo.user_id
         JOIN
     shoe_order AS so ON uo.order_id = so.order_id
WHERE u.role = 'client'
GROUP BY u.id, u.name, u.surname, uo.order_id
ORDER BY u.id, uo.order_id, total_spent DESC;



