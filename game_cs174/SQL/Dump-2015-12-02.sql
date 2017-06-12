CREATE DATABASE  IF NOT EXISTS `bruteforce` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `bruteforce`;
-- MySQL dump 10.13  Distrib 5.6.24, for osx10.8 (x86_64)
--
-- Host: 127.0.0.1    Database: bruteforce
-- ------------------------------------------------------
-- Server version	5.6.25

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Friends`
--

DROP TABLE IF EXISTS `Friends`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Friends` (
  `idPlayer` int(11) NOT NULL,
  `idFriend` int(11) NOT NULL,
  KEY `idPlayer_FK_idx` (`idPlayer`),
  KEY `idFriend_FK_idx` (`idFriend`),
  CONSTRAINT `idFriend_FK` FOREIGN KEY (`idFriend`) REFERENCES `Players` (`idPlayers`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idPlayer_FK` FOREIGN KEY (`idPlayer`) REFERENCES `Players` (`idPlayers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Friends`
--

LOCK TABLES `Friends` WRITE;
/*!40000 ALTER TABLE `Friends` DISABLE KEYS */;
/*!40000 ALTER TABLE `Friends` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Players`
--

DROP TABLE IF EXISTS `Players`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Players` (
  `idPlayers` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `gamerTag` varchar(3) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `photo_path` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idPlayers`),
  UNIQUE KEY `gamerTag` (`gamerTag`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Players`
--

LOCK TABLES `Players` WRITE;
/*!40000 ALTER TABLE `Players` DISABLE KEYS */;
INSERT INTO `Players` VALUES (1,'Brandon','Rossi','ltr','brandon.rossi@sjsu.edu','password1',NULL),(2,'Brian','Lam','blm','brian.lam@sjsu.edu','password2',NULL),(3,'Alexander','Wong','alw','alexander.wong@sjsu.edu','password3',NULL),(4,'Mike','Phe','mip','mike.phe@sjsu.edu','password4',NULL),(5,'Austin','Merritt','AmR','amerritt@test.com','password12345',NULL),(8,'Austin','Merritt','AmT','amerritt@test1.com','password12345',NULL),(9,'Austin','Merritt','amm','bbb@bbb.com','12343647',NULL),(11,'Brandon','Rossi','rtl','brandonrossi@mac.com','123456789',NULL),(12,'bbhbhbhbh','jiejgijeigjiej','zzz','bbbbbb@bbbbb.com','jgiejhgi',NULL),(13,'Brandon','Rossi','iii','bbbbbbbbgggg@gmail.com','12345',NULL);
/*!40000 ALTER TABLE `Players` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Scores`
--

DROP TABLE IF EXISTS `Scores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Scores` (
  `idScores` int(11) NOT NULL AUTO_INCREMENT,
  `idPlayer` int(11) NOT NULL,
  `score` int(11) NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`idScores`),
  KEY `player_fk_idx` (`idPlayer`),
  CONSTRAINT `player_fk` FOREIGN KEY (`idPlayer`) REFERENCES `Players` (`idPlayers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Scores`
--

LOCK TABLES `Scores` WRITE;
/*!40000 ALTER TABLE `Scores` DISABLE KEYS */;
INSERT INTO `Scores` VALUES (13,1,500,'2015-11-30 19:41:22'),(14,1,300,'2015-11-30 19:41:22'),(15,1,500,'2015-11-30 19:41:22'),(16,1,300,'2015-11-30 19:41:22'),(17,1,10,'2015-11-30 19:41:22'),(18,2,550,'2015-11-30 19:41:22'),(19,2,320,'2015-11-30 19:41:22'),(20,2,100,'2015-11-30 19:41:22'),(21,3,460,'2015-11-30 19:41:22'),(22,3,390,'2015-11-30 19:41:22'),(23,3,190,'2015-11-30 19:41:22'),(24,4,80,'2015-11-30 19:41:22'),(25,4,700,'2015-11-30 19:41:22'),(26,4,15,'2015-11-30 19:41:22'),(27,5,34,'2015-11-30 19:41:22'),(28,5,30,'2015-11-30 19:41:22'),(29,5,500,'2015-11-30 19:41:22'),(30,1,500,'2015-11-30 19:41:22'),(31,1,300,'2015-11-30 19:41:22'),(32,1,10,'2015-11-30 19:41:22'),(33,13,50000,'2015-11-30 19:41:22'),(34,1,10,'2015-11-30 19:41:22'),(35,1,15,'2015-11-30 19:41:39'),(36,1,20,'2015-11-30 19:42:01');
/*!40000 ALTER TABLE `Scores` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-12-02 12:06:20
