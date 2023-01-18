CREATE DATABASE  IF NOT EXISTS `garage` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `garage`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: garage
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `auto`
--

DROP TABLE IF EXISTS `auto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auto` (
  `id_auto` int NOT NULL AUTO_INCREMENT,
  `targa` varchar(45) NOT NULL,
  `categoria_auto` varchar(45) DEFAULT NULL,
  `tipologia_emissione` varchar(45) DEFAULT NULL,
  `orario_entrata` varchar(45) DEFAULT NULL,
  `orario_uscita` varchar(45) DEFAULT NULL,
  `is_deleted` int DEFAULT NULL,
  `posto_id` int DEFAULT NULL,
  PRIMARY KEY (`id_auto`,`targa`),
  KEY `fk_versoPosto_idx` (`posto_id`),
  KEY `fk_targa` (`targa`),
  CONSTRAINT `fk_versoPosto` FOREIGN KEY (`posto_id`) REFERENCES `posto` (`id_posto`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auto`
--

LOCK TABLES `auto` WRITE;
/*!40000 ALTER TABLE `auto` DISABLE KEYS */;
INSERT INTO `auto` VALUES (76,'AA111','auto normale','benzina','12:00','13:00',0,14),(77,'AA222','auto normale','benzina','19:00','20:00',0,14),(78,'AA345','auto van','diesel','15:00','16:00',0,11),(79,'AA039','auto van','benzina','18:00','20:00',0,24),(80,'AA800','auto lusso','gpl','09:00','20:00',0,3),(81,'AA322','auto lusso','diesel','14:00','0',1,9),(82,'AA884','auto lusso','diesel','12:00','13:00',0,16),(83,'AA559','auto normale','gpl','05:00','10:00',0,2),(84,'AA231','auto normale','benzina','05:00','09:00',0,7),(85,'AA258','auto normale','benzina','05:00','20:00',0,8),(88,'AA354','auto normale','gpl','19:00','22:00',0,1),(89,'AA244','auto van','diesel','16:00',NULL,1,4),(90,'AA555','auto van','diesel','18:00','21:00',0,18),(91,'AA898','auto van','benzina','10:00','12:00',0,12),(92,'AA999','auto van','benzina','10:00','12:00',0,24),(93,'AA569','auto van','benzina','20:00',NULL,1,21),(94,'AA400','auto lusso','diesel','17:00',NULL,1,13),(95,'AA159','auto normale','elettrica','15:00',NULL,1,14),(96,'AA771','auto lusso','benzina','12:00',NULL,1,15),(97,'AA228','auto van','elettrica','12:00',NULL,1,24);
/*!40000 ALTER TABLE `auto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria_posto`
--

DROP TABLE IF EXISTS `categoria_posto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria_posto` (
  `id_categoria_posto` int NOT NULL,
  `nome_categoria` varchar(45) DEFAULT NULL,
  `prezzo_mensile` double DEFAULT NULL,
  `prezzo_orario` double DEFAULT NULL,
  `penale` int DEFAULT NULL,
  PRIMARY KEY (`id_categoria_posto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria_posto`
--

LOCK TABLES `categoria_posto` WRITE;
/*!40000 ALTER TABLE `categoria_posto` DISABLE KEYS */;
INSERT INTO `categoria_posto` VALUES (1,'Auto Normale',150,5,1),(2,'Auto Lusso',500,9,3),(3,'Auto Van',400,7,5);
/*!40000 ALTER TABLE `categoria_posto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `livello`
--

DROP TABLE IF EXISTS `livello`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `livello` (
  `numero_livello` int NOT NULL,
  `numero_posti` int DEFAULT NULL,
  PRIMARY KEY (`numero_livello`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `livello`
--

LOCK TABLES `livello` WRITE;
/*!40000 ALTER TABLE `livello` DISABLE KEYS */;
INSERT INTO `livello` VALUES (1,6),(2,6),(3,6),(4,6);
/*!40000 ALTER TABLE `livello` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `posto`
--

DROP TABLE IF EXISTS `posto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `posto` (
  `id_posto` int NOT NULL,
  `occupato` tinyint DEFAULT NULL,
  `num_livello` int DEFAULT NULL,
  `categoria_posto_id` int DEFAULT NULL,
  PRIMARY KEY (`id_posto`),
  KEY `fk_versoLivello_idx` (`num_livello`),
  KEY `fk_versoCategoriaPosto_idx` (`categoria_posto_id`),
  CONSTRAINT `fk_versoCategoriaPosto` FOREIGN KEY (`categoria_posto_id`) REFERENCES `categoria_posto` (`id_categoria_posto`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_versoLivello` FOREIGN KEY (`num_livello`) REFERENCES `livello` (`numero_livello`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `posto`
--

LOCK TABLES `posto` WRITE;
/*!40000 ALTER TABLE `posto` DISABLE KEYS */;
INSERT INTO `posto` VALUES (1,0,1,1),(2,0,1,1),(3,0,1,2),(4,1,1,2),(5,0,1,3),(6,0,1,3),(7,0,2,1),(8,0,2,1),(9,1,2,2),(10,0,2,2),(11,0,2,3),(12,0,2,3),(13,1,3,1),(14,1,3,1),(15,1,3,2),(16,0,3,2),(17,0,3,3),(18,0,3,3),(19,1,4,1),(20,0,4,1),(21,1,4,2),(22,0,4,2),(23,0,4,3),(24,1,4,3);
/*!40000 ALTER TABLE `posto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proprietario`
--

DROP TABLE IF EXISTS `proprietario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `proprietario` (
  `cf` varchar(45) NOT NULL,
  `targa_auto` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`cf`),
  KEY `fk_versoAuto_idx` (`targa_auto`),
  CONSTRAINT `fk_versoAuto` FOREIGN KEY (`targa_auto`) REFERENCES `auto` (`targa`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proprietario`
--

LOCK TABLES `proprietario` WRITE;
/*!40000 ALTER TABLE `proprietario` DISABLE KEYS */;
/*!40000 ALTER TABLE `proprietario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-18 18:05:11
