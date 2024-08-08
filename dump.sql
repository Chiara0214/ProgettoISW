CREATE DATABASE  IF NOT EXISTS `progetto` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `progetto`;
-- MySQL dump 10.13  Distrib 8.0.39, for Linux (x86_64)
--
-- Host: localhost    Database: progetto
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `BIGLIETTO`
--

DROP TABLE IF EXISTS `BIGLIETTO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `BIGLIETTO` (
  `id_biglietto` int NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `categoria` varchar(20) NOT NULL,
  `zona` varchar(14) NOT NULL,
  `fila` int NOT NULL,
  `palco` int DEFAULT NULL,
  `numero_posto` int NOT NULL,
  `id_replica` int NOT NULL,
  `id_utente` int NOT NULL,
  `deleted` tinyint NOT NULL,
  PRIMARY KEY (`id_biglietto`),
  UNIQUE KEY `id_biglietto_UNIQUE` (`id_biglietto`),
  UNIQUE KEY `index_posto` (`zona`,`fila`,`palco`,`numero_posto`),
  KEY `fk_BIGLIETTO_1_idx` (`id_replica`),
  KEY `fk_BIGLIETTO_2_idx` (`id_utente`),
  CONSTRAINT `fk_BIGLIETTO_1` FOREIGN KEY (`id_replica`) REFERENCES `REPLICA` (`id_replica`) ON UPDATE CASCADE,
  CONSTRAINT `fk_BIGLIETTO_2` FOREIGN KEY (`id_utente`) REFERENCES `UTENTE` (`id_utente`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BIGLIETTO`
--

LOCK TABLES `BIGLIETTO` WRITE;
/*!40000 ALTER TABLE `BIGLIETTO` DISABLE KEYS */;
INSERT INTO `BIGLIETTO` VALUES (1,'Anna Rita','Bisinella','ridotto over 65','platea',1,NULL,5,1,2,0),(2,'Giulia','Fabris','ridotto under 20','palco centrale',1,18,2,21,1,0),(3,'Michela','Chirilli','intero','palco laterale',1,31,2,5,3,0),(4,'Accursio','Brutti','ridotto over 65','galleria',2,NULL,58,15,4,0),(5,'Giulia','Fabris','ridotto under 30','platea',4,NULL,2,4,1,0),(6,'Maria Rosaria','Pelella','intero','palco laterale',2,1,1,12,7,0),(7,'Christian','Conti','ridotto under 20','galleria',1,NULL,65,10,8,0),(8,'Basilio','Viceconte','intero','platea',10,NULL,18,3,9,0),(9,'Raoul','Guidolin','ridotto over 65','palco centrale',2,15,1,17,10,0),(10,'Vittoria','Bianco','ridotto under 30','galleria',1,NULL,24,16,5,0),(11,'Andrea','Ognibene','intero','palco laterale',1,1,3,2,6,0),(12,'Riccardo','Pelella','ridotto over 65','platea',14,NULL,1,6,7,0),(13,'Christian','Conti','intero','palco centrale',1,2,4,8,8,0),(14,'Raoul','Guidolin','ridotto under 20','loggione',1,NULL,10,1,10,0),(15,'Giorgia','Viceconte','intero','loggione',2,NULL,23,10,9,0);
/*!40000 ALTER TABLE `BIGLIETTO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `COUPON`
--

DROP TABLE IF EXISTS `COUPON`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `COUPON` (
  `id_coupon` int NOT NULL,
  `sconto` int NOT NULL,
  `genere` varchar(20) NOT NULL,
  `data_inizio` date NOT NULL,
  `data_fine` date NOT NULL,
  `deleted` tinyint NOT NULL,
  PRIMARY KEY (`id_coupon`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COUPON`
--

LOCK TABLES `COUPON` WRITE;
/*!40000 ALTER TABLE `COUPON` DISABLE KEYS */;
INSERT INTO `COUPON` VALUES (1,20,'prosa','2024-01-24','2025-01-24',0),(2,10,'concerti','2024-05-19','2025-05-19',0),(3,50,'prosa','2024-08-10','2025-08-10',0),(4,30,'altro','2024-02-01','2025-02-01',0),(5,15,'tutti','2024-05-20','2025-05-20',0),(6,20,'danza','2024-07-01','2025-07-01',0);
/*!40000 ALTER TABLE `COUPON` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `REPLICA`
--

DROP TABLE IF EXISTS `REPLICA`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `REPLICA` (
  `id_replica` int NOT NULL,
  `inizio` datetime NOT NULL,
  `id_spettacolo` int NOT NULL,
  `deleted` tinyint NOT NULL,
  PRIMARY KEY (`id_replica`),
  UNIQUE KEY `id_replica_UNIQUE` (`id_replica`),
  UNIQUE KEY `inizio_UNIQUE` (`inizio`),
  KEY `fk_REPLICA_1_idx` (`id_spettacolo`),
  CONSTRAINT `fk_REPLICA_1` FOREIGN KEY (`id_spettacolo`) REFERENCES `SPETTACOLO` (`id_spettacolo`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `REPLICA`
--

LOCK TABLES `REPLICA` WRITE;
/*!40000 ALTER TABLE `REPLICA` DISABLE KEYS */;
INSERT INTO `REPLICA` VALUES (1,'2024-10-25 20:30:00',1,0),(2,'2024-10-27 16:00:00',1,0),(3,'2025-01-24 20:30:00',2,0),(4,'2025-01-31 20:30:00',3,0),(5,'2025-04-04 20:30:00',4,0),(6,'2025-04-06 16:00:00',4,0),(7,'2025-04-25 20:30:00',5,0),(8,'2024-09-14 12:00:00',6,0),(9,'2024-09-14 17:00:00',6,0),(10,'2024-09-15 20:30:00',7,0),(11,'2024-09-29 17:00:00',8,0),(12,'2024-10-23 20:30:00',9,0),(13,'2024-09-19 20:00:00',10,0),(14,'2024-11-14 17:00:00',11,0),(15,'2024-11-14 20:30:00',11,0),(16,'2025-01-23 20:30:00',12,0),(17,'2024-11-21 20:30:00',13,0),(18,'2025-01-20 20:30:00',14,0),(19,'2025-01-22 20:30:00',14,0),(20,'2024-09-14 20:30:00',15,0),(21,'2025-03-10 20:30:00',16,0),(22,'2024-02-04 17:30:00',17,0),(23,'2024-02-05 17:00:00',17,0),(24,'2024-02-05 20:30:00',17,0);
/*!40000 ALTER TABLE `REPLICA` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SPETTACOLO`
--

DROP TABLE IF EXISTS `SPETTACOLO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `SPETTACOLO` (
  `id_spettacolo` int NOT NULL,
  `nome` varchar(255) NOT NULL,
  `genere` varchar(20) NOT NULL,
  `compagnia` varchar(255) DEFAULT NULL,
  `descrizione` varchar(1000) DEFAULT NULL,
  `deleted` tinyint NOT NULL,
  PRIMARY KEY (`id_spettacolo`),
  UNIQUE KEY `id_spettacolo_UNIQUE` (`id_spettacolo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SPETTACOLO`
--

LOCK TABLES `SPETTACOLO` WRITE;
/*!40000 ALTER TABLE `SPETTACOLO` DISABLE KEYS */;
INSERT INTO `SPETTACOLO` VALUES (1,'La Locandiera','prosa','Teatro Stabile dell\'Umbria',NULL,0),(2,'L\'ispettore generale','prosa','Teatro Stabile di Bolzano',NULL,0),(3,'Pirandello trilogia di un visionario','prosa','Goldenart Production',NULL,0),(4,'Sei personaggi in cerca d\'autore','prosa','Teatro Bellini',NULL,0),(5,'Pignasecca e Pignaverde','prosa','Teatro Sociale di Camogli',NULL,0),(6,'Come neve','danza','Körper – Centro Nazionale di Produzione della danza',NULL,0),(7,'Don Juan | The Carnival Party','danza','Compagnia Aterballetto',NULL,0),(8,'Divina Commedia','danza','Compagnia Aterballetto',NULL,0),(9,'Mont Ventoux','danza','Collettivo Kor’sia',NULL,0),(10,'Il Seicento Ferrarese','concerti','Associazione Ferrara Musica',NULL,0),(11,'Concerto per violoncello e orchestra op.104','concerti','Orchestra Sinfonica Toscanini',NULL,0),(12,'MENDELSSOHN Concerto per violino e orchestra in mi minore','concerti','Budapest Festival Orchestra',NULL,0),(13,'Le nozze di Figaro','concerti','Orchestra del Conservatorio Frescobaldi di Ferrara',NULL,0),(14,'Norma','concerti','Operiamo - Casa Della Musica E Delle Arti',NULL,0),(15,'Astolfo sulla Luna','altro','Operiamo - Casa Della Musica E Delle Arti',NULL,0),(16,'Mordere il Cielo','altro','Paolo Crepet',NULL,0),(17,'Personaggi','altro','Antonio Albanese',NULL,0);
/*!40000 ALTER TABLE `SPETTACOLO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USA_COUPON`
--

DROP TABLE IF EXISTS `USA_COUPON`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USA_COUPON` (
  `id_utente` int NOT NULL,
  `id_coupon` int NOT NULL,
  `deleted` tinyint NOT NULL,
  PRIMARY KEY (`id_utente`,`id_coupon`),
  KEY `fk_USA_COUPON_2_idx` (`id_coupon`),
  CONSTRAINT `fk_USA_COUPON_1` FOREIGN KEY (`id_utente`) REFERENCES `UTENTE` (`id_utente`) ON UPDATE CASCADE,
  CONSTRAINT `fk_USA_COUPON_2` FOREIGN KEY (`id_coupon`) REFERENCES `COUPON` (`id_coupon`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USA_COUPON`
--

LOCK TABLES `USA_COUPON` WRITE;
/*!40000 ALTER TABLE `USA_COUPON` DISABLE KEYS */;
INSERT INTO `USA_COUPON` VALUES (1,1,0),(1,4,0),(2,5,0),(3,2,0),(6,5,0);
/*!40000 ALTER TABLE `USA_COUPON` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `UTENTE`
--

DROP TABLE IF EXISTS `UTENTE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `UTENTE` (
  `id_utente` int NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `password` varchar(45) NOT NULL,
  `privilegi` tinyint NOT NULL,
  `deleted` tinyint NOT NULL,
  PRIMARY KEY (`id_utente`),
  UNIQUE KEY `id_utente_UNIQUE` (`id_utente`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `telefono_UNIQUE` (`telefono`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `UTENTE`
--

LOCK TABLES `UTENTE` WRITE;
/*!40000 ALTER TABLE `UTENTE` DISABLE KEYS */;
INSERT INTO `UTENTE` VALUES (1,'Giulia','Fabris','giulia2@gmail.com','0532829516','gf2',0,0),(2,'Anna Rita','Bisinella','bisi2@hotmail.it','0532856109','arb2',0,0),(3,'Michela','Chirilli','micky13@outlook.com','3247920571','mc13',0,0),(4,'Accursio','Brutti','accursiob@gmail.com','3711043277','ab1',0,0),(5,'Vittoria','Bianco','vitto7@hotmail.com','3541890476','vb7',0,0),(6,'Andrea','Ognibene','andreaognibene@outlook.com','0542935657','ao1',0,0),(7,'Maria Rosaria','Pelella','mariapelella@gmail.com','3913898974','mrp1',0,0),(8,'Christian','Conti','conti123@outlook.com','0532315585','cc123',0,0),(9,'Basilio','Viceconte','vicecontebasilio@gmail.com','3815133338','bv1',0,0),(10,'Raoul','Guidolin','guidolin2@gmail.com','0426657031','rg2',0,0),(11,'Teatro','Ferrara','admin@gmail.com','0527589312','admin',1,0);
/*!40000 ALTER TABLE `UTENTE` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-08 19:01:26
