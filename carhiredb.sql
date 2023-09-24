CREATE DATABASE  IF NOT EXISTS `carhire` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `carhire`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: carhire
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `car`
--

DROP TABLE IF EXISTS `car`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car` (
  `idCar` varchar(6) NOT NULL,
  `Brand` varchar(45) NOT NULL,
  `Model` varchar(45) NOT NULL,
  `Vehicle_Number` varchar(45) NOT NULL,
  `year` varchar(9) NOT NULL,
  `Price_per_day` double(6,2) NOT NULL,
  `category_id` varchar(245) NOT NULL,
  `availability` varchar(20) NOT NULL,
  PRIMARY KEY (`idCar`),
  UNIQUE KEY `Vehicle_Number_UNIQUE` (`Vehicle_Number`),
  KEY `category_id_idx` (`category_id`),
  CONSTRAINT `category_id` FOREIGN KEY (`category_id`) REFERENCES `car_category` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='category_id';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car`
--

LOCK TABLES `car` WRITE;
/*!40000 ALTER TABLE `car` DISABLE KEYS */;
INSERT INTO `car` VALUES ('CA001','Nissan','GTR','ZXC 2314','2020',6000.00,'Sport','Not Available'),('CA002','Toyota','Supra','DF 2345','2003',5000.63,'Sport','Not Available'),('CA003','Tesla','Model X','ZXA 2871','2021',8000.56,'Sedan','Available');
/*!40000 ALTER TABLE `car` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `car_category`
--

DROP TABLE IF EXISTS `car_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `car_category` (
  `idCar_Category` varchar(6) NOT NULL,
  `name` varchar(245) NOT NULL,
  PRIMARY KEY (`idCar_Category`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `car_category`
--

LOCK TABLES `car_category` WRITE;
/*!40000 ALTER TABLE `car_category` DISABLE KEYS */;
INSERT INTO `car_category` VALUES ('C003','Convertible'),('C001','Hach Back'),('C004','Sedan'),('C002','Sport');
/*!40000 ALTER TABLE `car_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer`
--

DROP TABLE IF EXISTS `customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `customer` (
  `idCustomer` varchar(6) NOT NULL,
  `name` varchar(245) NOT NULL,
  `nic` varchar(13) NOT NULL,
  `adress` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  PRIMARY KEY (`idCustomer`),
  UNIQUE KEY `nic_UNIQUE` (`nic`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer`
--

LOCK TABLES `customer` WRITE;
/*!40000 ALTER TABLE `customer` DISABLE KEYS */;
INSERT INTO `customer` VALUES ('CUS001','Yasiru Ravidith','200333411340','Matara, Sri lanka, Earth, Milky Way','0711514979'),('CUS002','Nimal','1233444556','Matara, sri lanka','0714567899');
/*!40000 ALTER TABLE `customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rent`
--

DROP TABLE IF EXISTS `rent`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rent` (
  `idRent` varchar(6) NOT NULL,
  `from_date` date NOT NULL,
  `to_date` date NOT NULL,
  `Advanced_payment` double(9,2) NOT NULL,
  `refundable_payment` double(10,2) NOT NULL,
  `is_retern` varchar(4) NOT NULL,
  `perDay_rent` double(6,2) NOT NULL,
  `Total` double(9,2) NOT NULL,
  `Balance` double(9,2) NOT NULL,
  `cust_id` varchar(6) NOT NULL,
  `car_id` varchar(6) NOT NULL,
  `Extra` double(9,2) NOT NULL,
  PRIMARY KEY (`idRent`),
  KEY `cust_id_idx` (`cust_id`),
  KEY `car_id_idx` (`car_id`),
  CONSTRAINT `car_id` FOREIGN KEY (`car_id`) REFERENCES `car` (`idCar`),
  CONSTRAINT `cust_id` FOREIGN KEY (`cust_id`) REFERENCES `customer` (`idCustomer`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rent`
--

LOCK TABLES `rent` WRITE;
/*!40000 ALTER TABLE `rent` DISABLE KEYS */;
INSERT INTO `rent` VALUES ('R001','2023-09-21','2023-09-24',6000.00,15000.00,'No',6000.00,18000.00,13700.00,'CUS001','CA001',1000.00),('R002','2023-09-21','2023-10-05',7000.00,20000.00,'Yes',5000.63,70008.82,63008.82,'CUS002','CA002',0.00),('R003','2023-09-21','2023-09-22',7000.00,20000.00,'Yes',5000.63,70008.82,67008.82,'CUS002','CA002',4000.00),('R004','2023-09-24','2023-09-28',2000.00,10000.00,'No',5000.63,20002.52,18002.52,'CUS002','CA002',0.00);
/*!40000 ALTER TABLE `rent` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `iduser` varchar(6) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(10) NOT NULL,
  `email` varchar(255) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `mobile_UNIQUE` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('U001','Ravidith','0711111112','ravi@gmail.com','ravidith','ravi2003'),('U002','Yasiru Ravidith','0715149792','yasiruravidith123@gmail.com','yasiru','yasiru2003');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-24 13:56:58
