-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: tiw-project
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
  `creation` timestamp NOT NULL,
  `open` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_auction`),
  KEY `id_seller_idx` (`id_seller`),
  KEY `id_item_idx` (`id_item`),
  CONSTRAINT `id_item` FOREIGN KEY (`id_item`) REFERENCES `item` (`id_item`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_seller` FOREIGN KEY (`id_seller`) REFERENCES `user` (`id_user`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auction`
--

LOCK TABLES `auction` WRITE;
/*!40000 ALTER TABLE `auction` DISABLE KEYS */;
/*!40000 ALTER TABLE `auction` ENABLE KEYS */;
UNLOCK TABLES;

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
 1 AS `image_filename`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `auctions_close_details`
--

DROP TABLE IF EXISTS `auctions_close_details`;
/*!50001 DROP VIEW IF EXISTS `auctions_close_details`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `auctions_close_details` AS SELECT 
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
 1 AS `image_filename`,
 1 AS `id_winner_bid`,
 1 AS `id_winner_bidder`,
 1 AS `winner_price`,
 1 AS `winner_bid_time`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `auctions_open_details`
--

DROP TABLE IF EXISTS `auctions_open_details`;
/*!50001 DROP VIEW IF EXISTS `auctions_open_details`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `auctions_open_details` AS SELECT 
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
 1 AS `image_filename`,
 1 AS `id_bid`,
 1 AS `id_bidder`,
 1 AS `price`,
 1 AS `bid_time`*/;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bid`
--

LOCK TABLES `bid` WRITE;
/*!40000 ALTER TABLE `bid` DISABLE KEYS */;
/*!40000 ALTER TABLE `bid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `close_auction`
--

DROP TABLE IF EXISTS `close_auction`;
/*!50001 DROP VIEW IF EXISTS `close_auction`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `close_auction` AS SELECT 
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
 1 AS `image_filename`,
 1 AS `id_max_bid`,
 1 AS `id_max_bidder`,
 1 AS `max_price`,
 1 AS `max_bid_time`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id_item` int NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL,
  `description` text NOT NULL,
  `image_filename` varchar(32) NOT NULL,
  PRIMARY KEY (`id_item`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

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
-- Temporary view structure for view `open_auctions`
--

DROP TABLE IF EXISTS `open_auctions`;
/*!50001 DROP VIEW IF EXISTS `open_auctions`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `open_auctions` AS SELECT 
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
 1 AS `image_filename`,
 1 AS `id_max_bid`,
 1 AS `id_max_bidder`,
 1 AS `max_price`,
 1 AS `max_bid_time`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `username` varchar(32) NOT NULL,
  `password` binary(64) NOT NULL,
  `name` varchar(32) NOT NULL,
  `surname` varchar(32) NOT NULL,
  `email` varchar(64) NOT NULL,
  `address_street` varchar(32) NOT NULL,
  `address_town` varchar(32) NOT NULL,
  PRIMARY KEY (`id_user`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'pizza',_binary '3323469a03f23afab163e37863c39565931da4b1e6fee504c861e93fc0670cd6','Giacomo','Pizzamiglio','giacomo1.pizzamiglio@mail.polimi.it','Via C. Colombo, 4','Abbiategrasso (MI), Italy');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

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
/*!50013 DEFINER=`workbench`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `auction_item` AS select `a`.`id_auction` AS `id_auction`,`a`.`id_item` AS `id_item`,`a`.`id_seller` AS `id_seller`,`a`.`minimum_rise` AS `minimum_rise`,`a`.`starting_price` AS `starting_price`,`a`.`end` AS `end`,`a`.`creation` AS `creation`,`a`.`open` AS `open`,`i`.`name` AS `name`,`i`.`description` AS `description`,`i`.`image_filename` AS `image_filename` from (`auction` `a` join `item` `i` on((`a`.`id_item` = `i`.`id_item`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `auctions_close_details`
--

/*!50001 DROP VIEW IF EXISTS `auctions_close_details`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`workbench`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `auctions_close_details` AS select `ai`.`id_auction` AS `id_auction`,`ai`.`id_item` AS `id_item`,`ai`.`id_seller` AS `id_seller`,`ai`.`minimum_rise` AS `minimum_rise`,`ai`.`starting_price` AS `starting_price`,`ai`.`end` AS `end`,`ai`.`creation` AS `creation`,`ai`.`open` AS `open`,`ai`.`name` AS `name`,`ai`.`description` AS `description`,`ai`.`image_filename` AS `image_filename`,`mb`.`id_bid` AS `id_winner_bid`,`mb`.`id_bidder` AS `id_winner_bidder`,`mb`.`price` AS `winner_price`,`mb`.`bid_time` AS `winner_bid_time` from (`auction_item` `ai` join `max_bids` `mb` on((`ai`.`id_auction` = `mb`.`id_auction`))) where (`ai`.`open` = false) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `auctions_open_details`
--

/*!50001 DROP VIEW IF EXISTS `auctions_open_details`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`workbench`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `auctions_open_details` AS select `ai`.`id_auction` AS `id_auction`,`ai`.`id_item` AS `id_item`,`ai`.`id_seller` AS `id_seller`,`ai`.`minimum_rise` AS `minimum_rise`,`ai`.`starting_price` AS `starting_price`,`ai`.`end` AS `end`,`ai`.`creation` AS `creation`,`ai`.`open` AS `open`,`ai`.`name` AS `name`,`ai`.`description` AS `description`,`ai`.`image_filename` AS `image_filename`,`b`.`id_bid` AS `id_bid`,`b`.`id_bidder` AS `id_bidder`,`b`.`price` AS `price`,`b`.`bid_time` AS `bid_time` from (`auction_item` `ai` join `bid` `b` on((`ai`.`id_auction` = `b`.`id_auction`))) where (`ai`.`open` = true) group by `ai`.`id_auction` order by `b`.`bid_time` desc */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `close_auction`
--

/*!50001 DROP VIEW IF EXISTS `close_auction`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`workbench`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `close_auction` AS select `ai`.`id_auction` AS `id_auction`,`ai`.`id_item` AS `id_item`,`ai`.`id_seller` AS `id_seller`,`ai`.`minimum_rise` AS `minimum_rise`,`ai`.`starting_price` AS `starting_price`,`ai`.`end` AS `end`,`ai`.`creation` AS `creation`,`ai`.`open` AS `open`,`ai`.`name` AS `name`,`ai`.`description` AS `description`,`ai`.`image_filename` AS `image_filename`,`mb`.`id_bid` AS `id_max_bid`,`mb`.`id_bidder` AS `id_max_bidder`,`mb`.`price` AS `max_price`,`mb`.`bid_time` AS `max_bid_time` from (`max_bids` `mb` join `auction_item` `ai` on((`mb`.`id_auction` = `ai`.`id_auction`))) where (`ai`.`open` = false) group by `ai`.`id_seller` order by `ai`.`end` */;
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
/*!50013 DEFINER=`workbench`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `max_bids` AS select `bid`.`id_bid` AS `id_bid`,`bid`.`id_auction` AS `id_auction`,`bid`.`id_bidder` AS `id_bidder`,`bid`.`price` AS `price`,`bid`.`bid_time` AS `bid_time` from `bid` group by `bid`.`id_auction` having (`bid`.`price` = max(`bid`.`price`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `open_auctions`
--

/*!50001 DROP VIEW IF EXISTS `open_auctions`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`workbench`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `open_auctions` AS select `ai`.`id_auction` AS `id_auction`,`ai`.`id_item` AS `id_item`,`ai`.`id_seller` AS `id_seller`,`ai`.`minimum_rise` AS `minimum_rise`,`ai`.`starting_price` AS `starting_price`,`ai`.`end` AS `end`,`ai`.`creation` AS `creation`,`ai`.`open` AS `open`,`ai`.`name` AS `name`,`ai`.`description` AS `description`,`ai`.`image_filename` AS `image_filename`,`mb`.`id_bid` AS `id_max_bid`,`mb`.`id_bidder` AS `id_max_bidder`,`mb`.`price` AS `max_price`,`mb`.`bid_time` AS `max_bid_time` from (`max_bids` `mb` join `auction_item` `ai` on((`mb`.`id_auction` = `ai`.`id_auction`))) where (`ai`.`open` = true) group by `ai`.`id_seller` order by `ai`.`end` */;
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

-- Dump completed on 2021-05-06  2:44:42
