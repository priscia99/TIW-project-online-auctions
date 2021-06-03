CREATE DATABASE  IF NOT EXISTS `TIW-Project` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `TIW-Project`;
-- MySQL dump 10.13  Distrib 8.0.20, for macos10.15 (x86_64)
--
-- Host: localhost    Database: TIW-Project
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `auction`
--

DROP TABLE IF EXISTS `auction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auction` (
  `id_auction` int NOT NULL AUTO_INCREMENT,
  `id_item` int NOT NULL,
  `id_seller` int NOT NULL,
  `minimum_rise` float unsigned NOT NULL,
  `starting_price` float unsigned NOT NULL,
  `end` timestamp NOT NULL,
  `creation` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `open` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_auction`),
  KEY `id_seller_idx` (`id_seller`),
  KEY `id_item_idx` (`id_item`),
  CONSTRAINT `id_item` FOREIGN KEY (`id_item`) REFERENCES `item` (`id_item`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_seller` FOREIGN KEY (`id_seller`) REFERENCES `user` (`id_user`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `auction_BEFORE_INSERT` BEFORE INSERT ON `auction` FOR EACH ROW BEGIN
	IF(NEW.end <= CURRENT_TIMESTAMP()) THEN
		SIGNAL SQLSTATE '45000' SET message_text = 'Auction end must be in the future';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Temporary view structure for view `auction_item`
--

DROP TABLE IF EXISTS `auction_item`;
/*!50001 DROP VIEW IF EXISTS `auction_item`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `auction_item` AS SELECT 
 1 AS `id_auction`,
 1 AS `id_item`,
 1 AS `id_seller`,
 1 AS `minimum_rise`,
 1 AS `starting_price`,
 1 AS `end`,
 1 AS `creation`,
 1 AS `open`,
 1 AS `name`,
 1 AS `description`,
 1 AS `image`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `auctions_details`
--

DROP TABLE IF EXISTS `auctions_details`;
/*!50001 DROP VIEW IF EXISTS `auctions_details`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `auctions_details` AS SELECT 
 1 AS `id_auction`,
 1 AS `minimum_rise`,
 1 AS `starting_price`,
 1 AS `end`,
 1 AS `creation`,
 1 AS `open`,
 1 AS `id_seller`,
 1 AS `id_item`,
 1 AS `name`,
 1 AS `description`,
 1 AS `image`,
 1 AS `id_bid`,
 1 AS `id_bidder`,
 1 AS `price`,
 1 AS `bid_time`,
 1 AS `bidder_name`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `auctions_summary`
--

DROP TABLE IF EXISTS `auctions_summary`;
/*!50001 DROP VIEW IF EXISTS `auctions_summary`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `auctions_summary` AS SELECT 
 1 AS `id_auction`,
 1 AS `id_item`,
 1 AS `id_seller`,
 1 AS `minimum_rise`,
 1 AS `starting_price`,
 1 AS `end`,
 1 AS `creation`,
 1 AS `open`,
 1 AS `name`,
 1 AS `description`,
 1 AS `image`,
 1 AS `id_max_bid`,
 1 AS `id_max_bidder`,
 1 AS `max_price`,
 1 AS `max_bid_time`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `bid`
--

DROP TABLE IF EXISTS `bid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bid` (
  `id_bid` int NOT NULL AUTO_INCREMENT,
  `id_auction` int NOT NULL,
  `id_bidder` int NOT NULL,
  `price` float unsigned NOT NULL,
  `bid_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_bid`),
  KEY `id_seller_idx` (`id_bidder`),
  KEY `id_auction_idx` (`id_auction`),
  CONSTRAINT `id_auction` FOREIGN KEY (`id_auction`) REFERENCES `auction` (`id_auction`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_bidder` FOREIGN KEY (`id_bidder`) REFERENCES `user` (`id_user`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = '' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `bid_BEFORE_INSERT` BEFORE INSERT ON `bid` FOR EACH ROW BEGIN 
	DECLARE bids INT;
    SET bids = (SELECT COUNT(*) FROM bid WHERE id_auction = NEW.id_auction);
    IF bids > 0 THEN
    IF ( 
		NEW.price 
			< 
		(SELECT price FROM max_bids WHERE id_auction = NEW.id_auction) 
			+ 
		(SELECT A.minimum_rise FROM auction AS A WHERE A.id_auction = NEW.id_auction) 
    ) THEN 
		SIGNAL SQLSTATE '45000' SET message_text = 'Bid price must be greater or equal than the current maximum bid plus the minimum rise'; 
    END IF; 
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id_item` int NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL,
  `description` text NOT NULL,
  `image` longblob NOT NULL,
  PRIMARY KEY (`id_item`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary view structure for view `max_bids`
--

DROP TABLE IF EXISTS `max_bids`;
/*!50001 DROP VIEW IF EXISTS `max_bids`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `max_bids` AS SELECT 
 1 AS `id_bid`,
 1 AS `id_auction`,
 1 AS `id_bidder`,
 1 AS `price`,
 1 AS `bid_time`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `password` binary(64) NOT NULL,
  `name` varchar(64) NOT NULL,
  `surname` varchar(64) NOT NULL,
  `email` varchar(64) NOT NULL,
  `address_street` varchar(64) NOT NULL,
  `address_town` varchar(64) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping events for database 'TIW-Project'
--

--
-- Dumping routines for database 'TIW-Project'
--
/*!50003 DROP FUNCTION IF EXISTS `login` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `login`(username VARCHAR(64), password VARCHAR(64)) RETURNS int
    DETERMINISTIC
BEGIN
	DECLARE id INT;
	SELECT 
		id_user
	INTO id FROM
		user AS U
	WHERE
		U.username = username AND U.password = SHA2(password, 256);
    RETURN id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `signup` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `signup`(IN username VARCHAR(64), IN password VARCHAR(64), IN name VARCHAR(64), IN surname VARCHAR(64), IN email VARCHAR(64), IN address_street VARCHAR(64), IN address_town VARCHAR(64))
BEGIN
	INSERT INTO user (username, password, name, surname, email, address_street, address_town) 
    VALUE (username, sha2(password, 256), name, surname, email, address_street, address_town);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Final view structure for view `auction_item`
--

/*!50001 DROP VIEW IF EXISTS `auction_item`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `auction_item` AS select `A`.`id_auction` AS `id_auction`,`A`.`id_item` AS `id_item`,`A`.`id_seller` AS `id_seller`,`A`.`minimum_rise` AS `minimum_rise`,`A`.`starting_price` AS `starting_price`,`A`.`end` AS `end`,`A`.`creation` AS `creation`,`A`.`open` AS `open`,`I`.`name` AS `name`,`I`.`description` AS `description`,`I`.`image` AS `image` from (`auction` `A` join `item` `I` on((`A`.`id_item` = `I`.`id_item`))) order by `A`.`end` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `auctions_details`
--

/*!50001 DROP VIEW IF EXISTS `auctions_details`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `auctions_details` AS select `ai`.`id_auction` AS `id_auction`,`ai`.`minimum_rise` AS `minimum_rise`,`ai`.`starting_price` AS `starting_price`,`ai`.`end` AS `end`,`ai`.`creation` AS `creation`,`ai`.`open` AS `open`,`ai`.`id_seller` AS `id_seller`,`ai`.`id_item` AS `id_item`,`ai`.`name` AS `name`,`ai`.`description` AS `description`,`ai`.`image` AS `image`,`B`.`id_bid` AS `id_bid`,`B`.`id_bidder` AS `id_bidder`,`B`.`price` AS `price`,`B`.`bid_time` AS `bid_time`,`U`.`username` AS `bidder_name` from ((`auction_item` `AI` left join `bid` `B` on((`ai`.`id_auction` = `B`.`id_auction`))) left join `user` `U` on((`U`.`id_user` = `B`.`id_bidder`))) order by `B`.`bid_time` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `auctions_summary`
--

/*!50001 DROP VIEW IF EXISTS `auctions_summary`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `auctions_summary` AS select `ai`.`id_auction` AS `id_auction`,`ai`.`id_item` AS `id_item`,`ai`.`id_seller` AS `id_seller`,`ai`.`minimum_rise` AS `minimum_rise`,`ai`.`starting_price` AS `starting_price`,`ai`.`end` AS `end`,`ai`.`creation` AS `creation`,`ai`.`open` AS `open`,`ai`.`name` AS `name`,`ai`.`description` AS `description`,`ai`.`image` AS `image`,`mb`.`id_bid` AS `id_max_bid`,`mb`.`id_bidder` AS `id_max_bidder`,`mb`.`price` AS `max_price`,`mb`.`bid_time` AS `max_bid_time` from (`auction_item` `AI` left join `max_bids` `MB` on((`ai`.`id_auction` = `mb`.`id_auction`))) order by `ai`.`end` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `max_bids`
--

/*!50001 DROP VIEW IF EXISTS `max_bids`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `max_bids` AS select `B`.`id_bid` AS `id_bid`,`B`.`id_auction` AS `id_auction`,`B`.`id_bidder` AS `id_bidder`,`B`.`price` AS `price`,`B`.`bid_time` AS `bid_time` from `bid` `B` where (`B`.`price` = (select max(`B2`.`price`) from `bid` `B2` where (`B2`.`id_auction` = `B`.`id_auction`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-06-03 15:40:13
