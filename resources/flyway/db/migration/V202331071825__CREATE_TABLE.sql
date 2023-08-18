-- Adminer 4.8.1 MySQL 8.0.34 dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

SET NAMES utf8mb4;

CREATE TABLE `leisure_item` (
  `leisure_item_id` bigint NOT NULL AUTO_INCREMENT,
  `item` varchar(255) NOT NULL,
  `uuid` binary(16) NOT NULL,
  PRIMARY KEY (`leisure_item_id`),
  UNIQUE KEY `UK_constraint_leisure_item_item` (`item`),
  UNIQUE KEY `UK_constraint_leisure_item_uuid` (`uuid`),
  KEY `idx_leisure_item_entity` (`leisure_item_id`,`uuid`,`item`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `property` (
  `property_id` bigint NOT NULL AUTO_INCREMENT,
  `building_area` int NOT NULL,
  `city` varchar(100) NOT NULL,
  `complement` varchar(100) DEFAULT NULL,
  `condominium_price` decimal(38,2) DEFAULT NULL,
  `country` varchar(100) NOT NULL,
  `description` varchar(255) NOT NULL,
  `garage` int NOT NULL,
  `house_number` int NOT NULL,
  `is_condominium` bit(1) DEFAULT NULL,
  `is_furnished` bit(1) DEFAULT NULL,
  `is_rent` bit(1) DEFAULT NULL,
  `is_sale` bit(1) DEFAULT NULL,
  `land_size` int NOT NULL,
  `neighborhood` varchar(150) DEFAULT NULL,
  `property_kind` varchar(255) NOT NULL,
  `registration` varchar(255) NOT NULL,
  `rent_price` decimal(38,2) DEFAULT NULL,
  `rooms` int NOT NULL,
  `sale_price` decimal(38,2) DEFAULT NULL,
  `state` varchar(2) NOT NULL,
  `street_name` varchar(255) NOT NULL,
  `taxes` decimal(38,2) NOT NULL,
  `uuid` binary(16) NOT NULL,
  `zipcode` varchar(50) NOT NULL,
  PRIMARY KEY (`property_id`),
  UNIQUE KEY `UK_constraint_property_registration` (`registration`),
  UNIQUE KEY `UK_constraint_property_uuid` (`uuid`),
  KEY `idx_property_entity` (`property_id`,`uuid`,`registration`)
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `property_leisure_item` (
  `property_id` bigint NOT NULL,
  `leisure_item_id` bigint NOT NULL,
  PRIMARY KEY (`property_id`,`leisure_item_id`),
  KEY `FK_property_leisure_item_id` (`leisure_item_id`),
  CONSTRAINT `FK_property_leisure_item_id` FOREIGN KEY (`leisure_item_id`) REFERENCES `leisure_item` (`leisure_item_id`),
  CONSTRAINT `FK_property_property_id` FOREIGN KEY (`property_id`) REFERENCES `property` (`property_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `condominium_leisure_item` (
  `property_id` bigint NOT NULL,
  `leisure_item_id` bigint NOT NULL,
  PRIMARY KEY (`property_id`,`leisure_item_id`),
  KEY `FK_condominium_leisure_item_id` (`leisure_item_id`),
  CONSTRAINT `FK_condominium_leisure_item_id` FOREIGN KEY (`leisure_item_id`) REFERENCES `leisure_item` (`leisure_item_id`),
  CONSTRAINT `FK_condominium_property_id` FOREIGN KEY (`property_id`) REFERENCES `property` (`property_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
