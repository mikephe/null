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
-- Table structure for table `Player_Scores_Linking`
--

DROP TABLE IF EXISTS `Player_Scores_Linking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Player_Scores_Linking` (
  `idPlayers` int(11) NOT NULL,
  `idScores` int(11) NOT NULL,
  KEY `idPlayers_idx` (`idPlayers`),
  KEY `idScores_idx` (`idScores`),
  CONSTRAINT `idPlayers` FOREIGN KEY (`idPlayers`) REFERENCES `Players` (`idPlayers`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idScores` FOREIGN KEY (`idScores`) REFERENCES `Scores` (`idScores`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Player_Scores_Linking`
--

LOCK TABLES `Player_Scores_Linking` WRITE;
/*!40000 ALTER TABLE `Player_Scores_Linking` DISABLE KEYS */;
INSERT INTO `Player_Scores_Linking` VALUES (1,1),(1,2),(1,3),(2,4),(2,5),(3,6),(3,7),(3,8),(4,9),(4,10),(1,11),(9,12);
/*!40000 ALTER TABLE `Player_Scores_Linking` ENABLE KEYS */;
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
INSERT INTO `Players` VALUES (1,'Brandon','Rossi','ltr','brandon.rossi@sjsu.edu','password1'),(2,'Brian','Lam','blm','brian.lam@sjsu.edu','password2'),(3,'Alexander','Wong','alw','alexander.wong@sjsu.edu','password3'),(4,'Mike','Phe','mip','mike.phe@sjsu.edu','password4'),(5,'Austin','Merritt','AmR','amerritt@test.com','password12345'),(8,'Austin','Merritt','AmT','amerritt@test1.com','password12345'),(9,'Austin','Merritt','amm','bbb@bbb.com','12343647'),(11,'Brandon','Rossi','rtl','brandonrossi@mac.com','123456789'),(12,'bbhbhbhbh','jiejgijeigjiej','zzz','bbbbbb@bbbbb.com','jgiejhgi'),(13,'Brandon','Rossi','iii','bbbbbbbbgggg@gmail.com','12345');
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
  `Score` int(11) NOT NULL,
  PRIMARY KEY (`idScores`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Scores`
--

LOCK TABLES `Scores` WRITE;
/*!40000 ALTER TABLE `Scores` DISABLE KEYS */;
INSERT INTO `Scores` VALUES (1,350),(2,550),(3,150),(4,250),(5,50),(6,25),(7,100),(8,10),(9,50),(10,100),(11,80),(12,900);
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

-- Dump completed on 2015-11-18 14:48:24