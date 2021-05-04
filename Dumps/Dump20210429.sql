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
  `id` int NOT NULL AUTO_INCREMENT,
  `end` datetime NOT NULL,
  `minimum_rise` float unsigned NOT NULL,
  `starting_price` float unsigned NOT NULL,
  `open` tinyint NOT NULL DEFAULT '1',
  `id_item` int NOT NULL,
  `id_seller` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_seller_idx` (`id_seller`),
  KEY `id_item_idx` (`id_item`),
  CONSTRAINT `id_item` FOREIGN KEY (`id_item`) REFERENCES `item` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_seller` FOREIGN KEY (`id_seller`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table structure for table `auctions_bids`
--

DROP TABLE IF EXISTS `auctions_bids`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auctions_bids` (
  `id_auction` int DEFAULT NULL,
  `id_bidder` int DEFAULT NULL,
  `bidder_username` int DEFAULT NULL,
  `id_bid` int DEFAULT NULL,
  `bid_price` int DEFAULT NULL,
  `bid_time` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auctions_bids`
--

LOCK TABLES `auctions_bids` WRITE;
/*!40000 ALTER TABLE `auctions_bids` DISABLE KEYS */;
/*!40000 ALTER TABLE `auctions_bids` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auctions_data`
--

DROP TABLE IF EXISTS `auctions_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auctions_data` (
  `id_auction` int DEFAULT NULL,
  `auction_open` int DEFAULT NULL,
  `auction_end` int DEFAULT NULL,
  `auction_starting_price` int DEFAULT NULL,
  `auction_minimum_rise` int DEFAULT NULL,
  `auction_seller` int DEFAULT NULL,
  `id_item` int DEFAULT NULL,
  `item_name` int DEFAULT NULL,
  `item_description` int DEFAULT NULL,
  `item_image_filename` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auctions_data`
--

LOCK TABLES `auctions_data` WRITE;
/*!40000 ALTER TABLE `auctions_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `auctions_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bid`
--

DROP TABLE IF EXISTS `bid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bid` (
  `id` int NOT NULL AUTO_INCREMENT,
  `price` float unsigned NOT NULL,
  `bid_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_auction` int NOT NULL,
  `id_bidder` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_seller_idx` (`id_bidder`),
  KEY `id_auction_idx` (`id_auction`),
  CONSTRAINT `id_auction` FOREIGN KEY (`id_auction`) REFERENCES `auction` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_bidder` FOREIGN KEY (`id_bidder`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
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
-- Table structure for table `close_auctions`
--

DROP TABLE IF EXISTS `close_auctions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `close_auctions` (
  `id_item` int DEFAULT NULL,
  `item_name` int DEFAULT NULL,
  `price` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `close_auctions`
--

LOCK TABLES `close_auctions` WRITE;
/*!40000 ALTER TABLE `close_auctions` DISABLE KEYS */;
/*!40000 ALTER TABLE `close_auctions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `description` text NOT NULL,
  `image_filename` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
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
 1 AS `bid_price`,
 1 AS `bid_time`,
 1 AS `id_bidder`,
 1 AS `id_auction`,
 1 AS `id_seller`,
 1 AS `id_item`,
 1 AS `auction_end`,
 1 AS `auction_open`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `open_auctions`
--

DROP TABLE IF EXISTS `open_auctions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `open_auctions` (
  `id_item` int DEFAULT NULL,
  `item_name` int DEFAULT NULL,
  `item_description` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  `time_left` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `open_auctions`
--

LOCK TABLES `open_auctions` WRITE;
/*!40000 ALTER TABLE `open_auctions` DISABLE KEYS */;
/*!40000 ALTER TABLE `open_auctions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` binary(64) NOT NULL,
  `password` binary(64) NOT NULL,
  `address_street` varchar(45) NOT NULL,
  `address_town` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,_binary 'cedf0f04d7b3b3d88e334e05e9ce926755f70128a41d8bcc0047315f18e82e19',_binary '5a5aaf5b94c8752d10ede32cd8bd4c0dd7cd4b22d9c0ed6270d73a914ca89b0c','Via C. Colombo, 4','Abbiategrasso (MI), 20081, Italy'),(2,_binary '253387e8620ee1896c76809782406e139bce35860aaa0d61991965319ba0dc32',_binary '4e20bc09461fd50a6b646cba4690c48597b545f1dd3bcdb7549cc7838bc49af4','Via A. Brombeis, 5','Napoli (NA), Italy');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `winners`
--

DROP TABLE IF EXISTS `winners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `winners` (
  `id_auction` int DEFAULT NULL,
  `auction_open` int DEFAULT NULL,
  `auction_end` int DEFAULT NULL,
  `auction_starting_price` int DEFAULT NULL,
  `auction_minimum_rise` int DEFAULT NULL,
  `auction_seller` int DEFAULT NULL,
  `id_item` int DEFAULT NULL,
  `item_name` int DEFAULT NULL,
  `item_description` int DEFAULT NULL,
  `item_image_filename` int DEFAULT NULL,
  `winner_username` int DEFAULT NULL,
  `final_price` int DEFAULT NULL,
  `winner_address_street` int DEFAULT NULL,
  `winner_address_town` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `winners`
--

LOCK TABLES `winners` WRITE;
/*!40000 ALTER TABLE `winners` DISABLE KEYS */;
/*!40000 ALTER TABLE `winners` ENABLE KEYS */;
UNLOCK TABLES;

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
/*!50001 VIEW `max_bids` AS select `b`.`id` AS `id_bid`,`b`.`price` AS `bid_price`,`b`.`bid_time` AS `bid_time`,`b`.`id_bidder` AS `id_bidder`,`a`.`id` AS `id_auction`,`a`.`id_seller` AS `id_seller`,`a`.`id_item` AS `id_item`,`a`.`end` AS `auction_end`,`a`.`open` AS `auction_open` from (`auction` `a` join `bid` `b` on((`a`.`id` = `b`.`id_auction`))) group by `a`.`id` having (0 <> max(`b`.`price`)) */;
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

-- Dump completed on 2021-04-29 20:16:35
