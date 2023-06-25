-- MySQL dump 10.13  Distrib 5.7.26, for Linux (x86_64)
--
-- Host: localhost    Database: ExaminationMonitoring
-- ------------------------------------------------------
-- Server version	5.7.26-0ubuntu0.18.10.1

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
-- Table structure for table `Active_Login`
--

DROP TABLE IF EXISTS `Active_Login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Active_Login` (
  `tid` int(10) NOT NULL,
  `userid` varchar(50) NOT NULL,
  `ldate` date NOT NULL,
  `ltime` varchar(50) NOT NULL,
  `COper` varchar(50) NOT NULL,
  `socketObjectPosition` int(10) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Active_Login`
--

LOCK TABLES `Active_Login` WRITE;
/*!40000 ALTER TABLE `Active_Login` DISABLE KEYS */;
INSERT INTO `Active_Login` VALUES (3,'17010102','2019-11-04','2:30 PM','ROHIT KESARWANI Solving Question ',5);
/*!40000 ALTER TABLE `Active_Login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qdetails`
--

DROP TABLE IF EXISTS `qdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qdetails` (
  `course` varchar(20) DEFAULT NULL,
  `syear` varchar(20) DEFAULT NULL,
  `sem` varchar(4) DEFAULT NULL,
  `time` int(5) DEFAULT NULL,
  `subject` varchar(200) DEFAULT NULL,
  `noq` int(5) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qdetails`
--

LOCK TABLES `qdetails` WRITE;
/*!40000 ALTER TABLE `qdetails` DISABLE KEYS */;
INSERT INTO `qdetails` VALUES ('B.Tech','2019-20','5',10,'C++',10,'efjeej');
/*!40000 ALTER TABLE `qdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionsefjeej`
--

DROP TABLE IF EXISTS `questionsefjeej`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionsefjeej` (
  `qid` int(5) DEFAULT NULL,
  `ques` varchar(300) DEFAULT NULL,
  `A` varchar(200) DEFAULT NULL,
  `B` varchar(200) DEFAULT NULL,
  `C` varchar(200) DEFAULT NULL,
  `D` varchar(200) DEFAULT NULL,
  `rans` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionsefjeej`
--

LOCK TABLES `questionsefjeej` WRITE;
/*!40000 ALTER TABLE `questionsefjeej` DISABLE KEYS */;
INSERT INTO `questionsefjeej` VALUES (0,'Which of the following is the correct syntax of including a user defined header files in C++?','#include<userdefined.h>','#include<userdefined>','#include\"userdefined\"','#include[userdefined]','C'),(1,'Which of the following is a correct identifier in C++?','7var_name','7VARNAME','VAR_1234','$var_name','C'),(2,'Which of the following is called address operator?','*','&','_','%','B'),(3,'Which of the following is used for comments in C++?','// comment','/* comment */','both // comment or /* comment */','// comment */','C'),(4,'What are the actual parameters in C++?','Parameters with which functions are called','Parameters which are used in the definition of function','Variables other than passed parameters in function','Variables that are never used in the function','A'),(5,'Who created C++?','Bjarne Stroustrup','Dennis Ritchie','Ken Thompson','Brian Kernighan','A'),(6,'Which of the following is called insertion/put operator','<<','>>','>','<','A'),(8,'Which function is used to write a single character to console in C++?','write(ch)','cout.putline(ch)','cout.put(ch)','printf(ch)','C'),(9,'Function is user to hold screen in Turbo C++?','getchar()','getch()','getc()','ge_string()','B');
/*!40000 ALTER TABLE `questionsefjeej` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `questionsjbjbhc`
--

DROP TABLE IF EXISTS `questionsjbjbhc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `questionsjbjbhc` (
  `qid` int(5) DEFAULT NULL,
  `ques` varchar(300) DEFAULT NULL,
  `A` varchar(200) DEFAULT NULL,
  `B` varchar(200) DEFAULT NULL,
  `C` varchar(200) DEFAULT NULL,
  `D` varchar(200) DEFAULT NULL,
  `rans` varchar(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `questionsjbjbhc`
--

LOCK TABLES `questionsjbjbhc` WRITE;
/*!40000 ALTER TABLE `questionsjbjbhc` DISABLE KEYS */;
INSERT INTO `questionsjbjbhc` VALUES (0,'Prime Minister','Narendra Modi','dank','fdsankn','sdnknkf','A'),(1,'HOme Minister','dfasdfas','dflsnkl','mgdfl','mdklmfklm','B'),(2,'Chief Minister','djfkjaskn','nkjfnakjn','nknkdnfkjnak','ndknka','D'),(3,'Food fnsaknk','ndkjfnk','nkdnkjnk','nknkdnkn','nkfnks','B'),(4,'District','kdnfknk','nfkdn','ndfkgnjn','nknfkdnk','C');
/*!40000 ALTER TABLE `questionsjbjbhc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `s_registration`
--

DROP TABLE IF EXISTS `s_registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `s_registration` (
  `course` varchar(20) DEFAULT NULL,
  `syear` varchar(20) DEFAULT NULL,
  `sem` varchar(4) DEFAULT NULL,
  `uroll` varchar(20) NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `mname` varchar(50) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `photo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uroll`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `s_registration`
--

LOCK TABLES `s_registration` WRITE;
/*!40000 ALTER TABLE `s_registration` DISABLE KEYS */;
INSERT INTO `s_registration` VALUES ('B.Tech','2019-20','5','17010101','VISHWASH KUMAR','ABC','FGH','./upload/17010101.png'),('B.Tech','2019-20','5','17010102','ROHIT KESARWANI','ABC','FGH','./upload/17010102.png'),('B.Tech','2019-20','5','17010103','HRITIK KUMAR','ABC','FGH','./upload/17010103.png'),('B.Tech','2019-20','5','17010104','NAMAN SINGHAL','ABC','FGH','./upload/17010104.png'),('B.Tech','2019-20','5','17010105','PRADYUMN SETH','ABC','FGH','./upload/17010105.png'),('B.Tech','2019-20','5','17010107','ANKIT TRIPATHI','ABC','FGH','./upload/17010107.png'),('B.Tech','2019-20','5','17010108','SHASHANK VERMA','ABC','FGH','./upload/17010108.png');
/*!40000 ALTER TABLE `s_registration` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studresultefjeej`
--

DROP TABLE IF EXISTS `studresultefjeej`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `studresultefjeej` (
  `uroll` varchar(20) DEFAULT NULL,
  `atques` int(5) DEFAULT NULL,
  `dot` date DEFAULT NULL,
  `per` varchar(10) DEFAULT NULL,
  `tques` int(5) DEFAULT NULL,
  `trans` int(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studresultefjeej`
--

LOCK TABLES `studresultefjeej` WRITE;
/*!40000 ALTER TABLE `studresultefjeej` DISABLE KEYS */;
INSERT INTO `studresultefjeej` VALUES ('17010103',0,'2019-11-04','0.0',10,0);
/*!40000 ALTER TABLE `studresultefjeej` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-04 15:58:36
