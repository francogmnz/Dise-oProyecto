-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: disenio
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
-- Table structure for table `agenda_consultor`
--

DROP TABLE IF EXISTS `agenda_consultor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `agenda_consultor` (
  `OIDAgendaConsultor` varchar(255) NOT NULL,
  `codigo_agenda_consultor` int DEFAULT NULL,
  `fecha_agenda` datetime NOT NULL,
  `fecha_alta_agenda_consultor` datetime NOT NULL,
  `fecha_baja_agenda_consultor` datetime DEFAULT NULL,
  PRIMARY KEY (`OIDAgendaConsultor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `agenda_consultor`
--

LOCK TABLES `agenda_consultor` WRITE;
/*!40000 ALTER TABLE `agenda_consultor` DISABLE KEYS */;
INSERT INTO `agenda_consultor` VALUES ('38b21250-5e35-44ff-a52a-f70299bd1fc8',1,'2024-09-14 00:23:18','2024-09-14 00:23:18',NULL);
/*!40000 ALTER TABLE `agenda_consultor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `articulo`
--

DROP TABLE IF EXISTS `articulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articulo` (
  `OIDArticulo` varchar(255) NOT NULL,
  `codigo` int DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `OIDEstado` varchar(255) NOT NULL,
  `fecha_alta` datetime NOT NULL,
  `fecha_baja` datetime DEFAULT NULL,
  PRIMARY KEY (`OIDArticulo`),
  KEY `FK_1741mfr02uq0ak2mj0bjoolob` (`OIDEstado`),
  CONSTRAINT `FK_1741mfr02uq0ak2mj0bjoolob` FOREIGN KEY (`OIDEstado`) REFERENCES `estado` (`OIDEstado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulo`
--

LOCK TABLES `articulo` WRITE;
/*!40000 ALTER TABLE `articulo` DISABLE KEYS */;
/*!40000 ALTER TABLE `articulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria_tipo_tramite`
--

DROP TABLE IF EXISTS `categoria_tipo_tramite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria_tipo_tramite` (
  `OIDCategoriaTipoTramite` varchar(255) NOT NULL,
  `cod_categoria_tipo_tramite` int DEFAULT NULL,
  `descripcion_categoria_tipo_tramite` varchar(255) DEFAULT NULL,
  `descripcion_web_categoria_tipo_tramite` varchar(255) DEFAULT NULL,
  `fecha_hora_baja_categoria_tipo_tramite` datetime DEFAULT NULL,
  `nombre_categoria_tipo_tramite` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OIDCategoriaTipoTramite`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria_tipo_tramite`
--

LOCK TABLES `categoria_tipo_tramite` WRITE;
/*!40000 ALTER TABLE `categoria_tipo_tramite` DISABLE KEYS */;
INSERT INTO `categoria_tipo_tramite` VALUES ('05908cc8-cd7f-40b0-8f67-90340b31df17',2,'Trámites especializados','Categoria donde se resuelven problemas especiales',NULL,'Categoría B'),('0c083f9e-0018-47a8-9a35-c65431834c4f',1,'Trámites generales','Categoria donde se resuelven problemas',NULL,'Categoría A');
/*!40000 ALTER TABLE `categoria_tipo_tramite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `OIDCliente` varchar(255) NOT NULL,
  `dni_cliente` int DEFAULT NULL,
  `nombre_cliente` varchar(255) DEFAULT NULL,
  `apellido_cliente` varchar(255) DEFAULT NULL,
  `mail_cliente` varchar(255) DEFAULT NULL,
  `fecha_hora_baja_cliente` datetime DEFAULT NULL,
  PRIMARY KEY (`OIDCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES ('77723c36-7d8f-491a-bbf1-dc15bfa53b4a',12345678,'Juan','Pérez','juan.perez@example.com',NULL),('fd7bde98-a8cc-4d1b-ab02-9be701a24a65',87654321,'María','González','maria.gonzalez@example.com',NULL);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conf_tipo_tramite_estado_tramite`
--

DROP TABLE IF EXISTS `conf_tipo_tramite_estado_tramite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conf_tipo_tramite_estado_tramite` (
  `OIDConfTipoTramiteEstadoTramite` varchar(255) NOT NULL,
  `contador_config_tt_et` int DEFAULT NULL,
  `etapa_origen` int DEFAULT NULL,
  `etapa_destino` int DEFAULT NULL,
  `OIDVersion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OIDConfTipoTramiteEstadoTramite`),
  KEY `FK_l1dam0xyfhi9boy303m67k25v` (`OIDVersion`),
  CONSTRAINT `FK_l1dam0xyfhi9boy303m67k25v` FOREIGN KEY (`OIDVersion`) REFERENCES `version` (`OIDVersion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conf_tipo_tramite_estado_tramite`
--

LOCK TABLES `conf_tipo_tramite_estado_tramite` WRITE;
/*!40000 ALTER TABLE `conf_tipo_tramite_estado_tramite` DISABLE KEYS */;
INSERT INTO `conf_tipo_tramite_estado_tramite` VALUES ('2d65e4a8-2d41-4b1e-ae62-f3ebff1982fd',2,2,3,'6120a9e2-dfac-4299-b628-ac63b4fa3dba'),('79cca21a-2657-4a2d-a56b-516ba8609f84',1,1,2,'10a8f047-b459-4a6e-b3ee-b27f3e332fe3');
/*!40000 ALTER TABLE `conf_tipo_tramite_estado_tramite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consultor`
--

DROP TABLE IF EXISTS `consultor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consultor` (
  `OIDConsultor` varchar(255) NOT NULL,
  `legajo_consultor` int DEFAULT NULL,
  `nombre_consultor` varchar(255) DEFAULT NULL,
  `nro_maximo_tramites` int DEFAULT NULL,
  `fecha_hora_baja_consultor` datetime DEFAULT NULL,
  PRIMARY KEY (`OIDConsultor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consultor`
--

LOCK TABLES `consultor` WRITE;
/*!40000 ALTER TABLE `consultor` DISABLE KEYS */;
INSERT INTO `consultor` VALUES ('838c1540-6308-4eed-a960-b049f8e0c21f',1001,'Carlos Fernández',5,NULL),('ba10e5b7-28ee-4139-8dfa-e6f0193bfeaf',1002,'Ana López',3,NULL);
/*!40000 ALTER TABLE `consultor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `consultor_agenda_consultor`
--

DROP TABLE IF EXISTS `consultor_agenda_consultor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `consultor_agenda_consultor` (
  `OIDAgendaConsultor` varchar(255) NOT NULL,
  `OIDConsultor` varchar(255) NOT NULL,
  KEY `FK_6b3igurmnv07ku5x6685e6fbo` (`OIDConsultor`),
  KEY `FK_9tey6mkukv421vwvvdyig6dpl` (`OIDAgendaConsultor`),
  CONSTRAINT `FK_6b3igurmnv07ku5x6685e6fbo` FOREIGN KEY (`OIDConsultor`) REFERENCES `consultor` (`OIDConsultor`),
  CONSTRAINT `FK_9tey6mkukv421vwvvdyig6dpl` FOREIGN KEY (`OIDAgendaConsultor`) REFERENCES `agenda_consultor` (`OIDAgendaConsultor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `consultor_agenda_consultor`
--

LOCK TABLES `consultor_agenda_consultor` WRITE;
/*!40000 ALTER TABLE `consultor_agenda_consultor` DISABLE KEYS */;
INSERT INTO `consultor_agenda_consultor` VALUES ('38b21250-5e35-44ff-a52a-f70299bd1fc8','838c1540-6308-4eed-a960-b049f8e0c21f');
/*!40000 ALTER TABLE `consultor_agenda_consultor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `detallereposicion`
--

DROP TABLE IF EXISTS `detallereposicion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detallereposicion` (
  `OIDDetalleReposicion` varchar(255) NOT NULL,
  `cantidad` int DEFAULT NULL,
  `OIDArticulo` varchar(255) NOT NULL,
  `OIDReposicion` varchar(255) NOT NULL,
  PRIMARY KEY (`OIDDetalleReposicion`),
  KEY `FK_snceb5qter6hv0p83cyl1ffm6` (`OIDArticulo`),
  KEY `FK_nh1dxaixi1bglja8jnqjmy6tj` (`OIDReposicion`),
  CONSTRAINT `FK_nh1dxaixi1bglja8jnqjmy6tj` FOREIGN KEY (`OIDReposicion`) REFERENCES `reposicion` (`OIDReposicion`),
  CONSTRAINT `FK_snceb5qter6hv0p83cyl1ffm6` FOREIGN KEY (`OIDArticulo`) REFERENCES `articulo` (`OIDArticulo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detallereposicion`
--

LOCK TABLES `detallereposicion` WRITE;
/*!40000 ALTER TABLE `detallereposicion` DISABLE KEYS */;
/*!40000 ALTER TABLE `detallereposicion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documentacion`
--

DROP TABLE IF EXISTS `documentacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `documentacion` (
  `OIDDocumentacion` varchar(255) NOT NULL,
  `cod_documentacion` int DEFAULT NULL,
  `descripcion_documentacion` varchar(255) DEFAULT NULL,
  `fecha_hora_baja_documentacion` datetime DEFAULT NULL,
  `nombre_documentacion` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OIDDocumentacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documentacion`
--

LOCK TABLES `documentacion` WRITE;
/*!40000 ALTER TABLE `documentacion` DISABLE KEYS */;
INSERT INTO `documentacion` VALUES ('0196e90a-e5ff-4f4a-9485-65068010b131',2,'Constancia del Código Único de Identificación Laboral',NULL,'Constancia de CUIL'),('64b0ecd7-d4d4-404d-8a6c-c48a57feccfc',1,'Documento Nacional de Identidad',NULL,'DNI');
/*!40000 ALTER TABLE `documentacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado`
--

DROP TABLE IF EXISTS `estado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado` (
  `OIDEstado` varchar(255) NOT NULL,
  `codigo` int DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `fecha_alta` datetime NOT NULL,
  `fecha_baja` datetime DEFAULT NULL,
  PRIMARY KEY (`OIDEstado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado`
--

LOCK TABLES `estado` WRITE;
/*!40000 ALTER TABLE `estado` DISABLE KEYS */;
/*!40000 ALTER TABLE `estado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estado_tramite`
--

DROP TABLE IF EXISTS `estado_tramite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estado_tramite` (
  `OIDEstadoTramite` varchar(255) NOT NULL,
  `cod_estado_tramite` int DEFAULT NULL,
  `nombre_estado_tramite` varchar(255) DEFAULT NULL,
  `descripcion_estado_tramite` varchar(255) DEFAULT NULL,
  `fecha_hora_baja_estado_tramite` datetime DEFAULT NULL,
  `fecha_hora_alta_estado_tramite` datetime DEFAULT NULL,
  `OIDConfTipoTramiteEstadoTramiteOrigen` varchar(255) DEFAULT NULL,
  `OIDConfTipoTramiteEstadoTramiteDestino` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OIDEstadoTramite`),
  KEY `FK_p6f3vq5ajpu4ep6or7rb14lhp` (`OIDConfTipoTramiteEstadoTramiteOrigen`),
  KEY `FK_jupfi5p3cm8tyr8myy4de4i0b` (`OIDConfTipoTramiteEstadoTramiteDestino`),
  CONSTRAINT `FK_jupfi5p3cm8tyr8myy4de4i0b` FOREIGN KEY (`OIDConfTipoTramiteEstadoTramiteDestino`) REFERENCES `conf_tipo_tramite_estado_tramite` (`OIDConfTipoTramiteEstadoTramite`),
  CONSTRAINT `FK_p6f3vq5ajpu4ep6or7rb14lhp` FOREIGN KEY (`OIDConfTipoTramiteEstadoTramiteOrigen`) REFERENCES `conf_tipo_tramite_estado_tramite` (`OIDConfTipoTramiteEstadoTramite`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estado_tramite`
--

LOCK TABLES `estado_tramite` WRITE;
/*!40000 ALTER TABLE `estado_tramite` DISABLE KEYS */;
INSERT INTO `estado_tramite` VALUES ('9cfab95e-4e4a-4284-b2a7-708a8130ef04',1,'Nombre1','Pendiente Doc',NULL,'2024-09-14 00:23:18','2d65e4a8-2d41-4b1e-ae62-f3ebff1982fd',NULL),('e2f4213f-6306-46af-9d27-2794ecdee1bb',2,'Nombre2','Pendiente Consultor',NULL,'2024-09-14 00:23:18',NULL,'2d65e4a8-2d41-4b1e-ae62-f3ebff1982fd');
/*!40000 ALTER TABLE `estado_tramite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lista_precios`
--

DROP TABLE IF EXISTS `lista_precios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lista_precios` (
  `OIDListaPrecios` varchar(255) NOT NULL,
  `cod_lista_precios` int DEFAULT NULL,
  `fecha_hora_baja_lista_precios` datetime DEFAULT NULL,
  `fecha_hora_desde_lista_precios` datetime DEFAULT NULL,
  `fecha_hora_hasta_lista_precios` datetime DEFAULT NULL,
  PRIMARY KEY (`OIDListaPrecios`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lista_precios`
--

LOCK TABLES `lista_precios` WRITE;
/*!40000 ALTER TABLE `lista_precios` DISABLE KEYS */;
INSERT INTO `lista_precios` VALUES ('4b9c8aa4-7306-4439-911c-fc4a70871271',2,NULL,'2024-09-14 00:23:18','2024-09-14 00:23:18'),('cb406b77-24db-4370-939e-ab1a1ea23ce0',1,NULL,'2024-09-14 00:23:18','2024-09-14 00:23:18');
/*!40000 ALTER TABLE `lista_precios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reposicion`
--

DROP TABLE IF EXISTS `reposicion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reposicion` (
  `OIDReposicion` varchar(255) NOT NULL,
  `numero` int DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  PRIMARY KEY (`OIDReposicion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reposicion`
--

LOCK TABLES `reposicion` WRITE;
/*!40000 ALTER TABLE `reposicion` DISABLE KEYS */;
/*!40000 ALTER TABLE `reposicion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_tramite`
--

DROP TABLE IF EXISTS `tipo_tramite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_tramite` (
  `OIDTipoTramite` varchar(255) NOT NULL,
  `cod_tipo_tramite` int DEFAULT NULL,
  `descripcion_tipo_tramite` varchar(255) DEFAULT NULL,
  `descripcion_web_tipo_tramite` varchar(255) DEFAULT NULL,
  `fecha_hora_baja_tipo_tramite` datetime DEFAULT NULL,
  `nombre_tipo_tramite` varchar(255) DEFAULT NULL,
  `plazo_entrega_documentacion_tt` int DEFAULT NULL,
  `OIDCategoriaTipoTramite` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OIDTipoTramite`),
  KEY `FK_sy49txnyv3sg6ixpo98kjjq10` (`OIDCategoriaTipoTramite`),
  CONSTRAINT `FK_sy49txnyv3sg6ixpo98kjjq10` FOREIGN KEY (`OIDCategoriaTipoTramite`) REFERENCES `categoria_tipo_tramite` (`OIDCategoriaTipoTramite`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_tramite`
--

LOCK TABLES `tipo_tramite` WRITE;
/*!40000 ALTER TABLE `tipo_tramite` DISABLE KEYS */;
INSERT INTO `tipo_tramite` VALUES ('65a7037f-8cff-4c6c-b901-947ac177fc02',1,'TipoTramite de prueba 1','TipoTramite de prueba web 1',NULL,'TipoTramite A',10,'0c083f9e-0018-47a8-9a35-c65431834c4f'),('da64b3a9-e086-4038-878e-4190c5154bf6',2,'TipoTramite de prueba 2','TipoTramite de prueba web 2',NULL,'TipoTramite B',10,'05908cc8-cd7f-40b0-8f67-90340b31df17');
/*!40000 ALTER TABLE `tipo_tramite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_tramite_documentacion`
--

DROP TABLE IF EXISTS `tipo_tramite_documentacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_tramite_documentacion` (
  `OIDTipoTramiteDocumentacion` varchar(255) NOT NULL,
  `fecha_desde_ttd` datetime DEFAULT NULL,
  `fecha_hasta_ttd` datetime DEFAULT NULL,
  `fecha_hora_baja_ttd` datetime DEFAULT NULL,
  `OIDDocumentacion` varchar(255) DEFAULT NULL,
  `OIDTipoTramite` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OIDTipoTramiteDocumentacion`),
  KEY `FK_6le22o4b3mnpcaah4twegphsu` (`OIDDocumentacion`),
  KEY `FK_r13wkxfp5nmm8y5wiywoh8u3k` (`OIDTipoTramite`),
  CONSTRAINT `FK_6le22o4b3mnpcaah4twegphsu` FOREIGN KEY (`OIDDocumentacion`) REFERENCES `documentacion` (`OIDDocumentacion`),
  CONSTRAINT `FK_r13wkxfp5nmm8y5wiywoh8u3k` FOREIGN KEY (`OIDTipoTramite`) REFERENCES `tipo_tramite` (`OIDTipoTramite`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_tramite_documentacion`
--

LOCK TABLES `tipo_tramite_documentacion` WRITE;
/*!40000 ALTER TABLE `tipo_tramite_documentacion` DISABLE KEYS */;
INSERT INTO `tipo_tramite_documentacion` VALUES ('9b5d6d0e-23c9-432a-8e62-de7131cf4d85','2024-09-14 00:23:18','2024-09-14 00:23:18',NULL,'0196e90a-e5ff-4f4a-9485-65068010b131','da64b3a9-e086-4038-878e-4190c5154bf6'),('b442fce0-29f0-4f46-b2a1-99749331b0cd','2024-09-14 00:23:18','2024-09-14 00:23:18',NULL,'64b0ecd7-d4d4-404d-8a6c-c48a57feccfc','65a7037f-8cff-4c6c-b901-947ac177fc02');
/*!40000 ALTER TABLE `tipo_tramite_documentacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tipo_tramite_lista_precios`
--

DROP TABLE IF EXISTS `tipo_tramite_lista_precios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tipo_tramite_lista_precios` (
  `OIDTipoTramiteListaPrecios` varchar(255) NOT NULL,
  `precio_tipo_tramite` int DEFAULT NULL,
  `OIDTipoTramite` varchar(255) DEFAULT NULL,
  `OIDListaPrecios` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OIDTipoTramiteListaPrecios`),
  KEY `FK_obmuadviga8cxoaxaxsic1h8s` (`OIDTipoTramite`),
  KEY `FK_4pilufm6iif09q80e6dwvo93e` (`OIDListaPrecios`),
  CONSTRAINT `FK_4pilufm6iif09q80e6dwvo93e` FOREIGN KEY (`OIDListaPrecios`) REFERENCES `lista_precios` (`OIDListaPrecios`),
  CONSTRAINT `FK_obmuadviga8cxoaxaxsic1h8s` FOREIGN KEY (`OIDTipoTramite`) REFERENCES `tipo_tramite` (`OIDTipoTramite`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tipo_tramite_lista_precios`
--

LOCK TABLES `tipo_tramite_lista_precios` WRITE;
/*!40000 ALTER TABLE `tipo_tramite_lista_precios` DISABLE KEYS */;
INSERT INTO `tipo_tramite_lista_precios` VALUES ('59f6aea3-6496-40eb-ad90-40cfd894d26f',700,'da64b3a9-e086-4038-878e-4190c5154bf6','4b9c8aa4-7306-4439-911c-fc4a70871271'),('80c56a64-c28f-430e-9238-891dcd7e05f1',500,'65a7037f-8cff-4c6c-b901-947ac177fc02','cb406b77-24db-4370-939e-ab1a1ea23ce0');
/*!40000 ALTER TABLE `tipo_tramite_lista_precios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tramite`
--

DROP TABLE IF EXISTS `tramite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tramite` (
  `OIDTramite` varchar(255) NOT NULL,
  `fecha_anulacion_tramite` datetime DEFAULT NULL,
  `fecha_fin_tramite` datetime DEFAULT NULL,
  `fecha_inicio_tramite` datetime DEFAULT NULL,
  `fecha_presentacion_total_documentacion` datetime DEFAULT NULL,
  `fecha_recepcion_tramite` datetime DEFAULT NULL,
  `nro_tramite` int DEFAULT NULL,
  `precio_tramite` int DEFAULT NULL,
  `OIDEstadoTramite` varchar(255) DEFAULT NULL,
  `OIDConsultor` varchar(255) DEFAULT NULL,
  `OIDCliente` varchar(255) DEFAULT NULL,
  `OIDVersion` varchar(255) DEFAULT NULL,
  `OIDTipoTramite` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OIDTramite`),
  KEY `FK_pl5ugy12y7jjri6hx23q5mwwb` (`OIDEstadoTramite`),
  KEY `FK_s2kdwx645l0mfijnwyd6yaylq` (`OIDConsultor`),
  KEY `FK_j6fq3da5yulvwko402l8cm25c` (`OIDCliente`),
  KEY `FK_3p3vohdew9ywstc9qh61twrlg` (`OIDVersion`),
  KEY `FK_k70jkqluog7x0ncrhefhnji3a` (`OIDTipoTramite`),
  CONSTRAINT `FK_3p3vohdew9ywstc9qh61twrlg` FOREIGN KEY (`OIDVersion`) REFERENCES `version` (`OIDVersion`),
  CONSTRAINT `FK_j6fq3da5yulvwko402l8cm25c` FOREIGN KEY (`OIDCliente`) REFERENCES `cliente` (`OIDCliente`),
  CONSTRAINT `FK_k70jkqluog7x0ncrhefhnji3a` FOREIGN KEY (`OIDTipoTramite`) REFERENCES `tipo_tramite` (`OIDTipoTramite`),
  CONSTRAINT `FK_pl5ugy12y7jjri6hx23q5mwwb` FOREIGN KEY (`OIDEstadoTramite`) REFERENCES `estado_tramite` (`OIDEstadoTramite`),
  CONSTRAINT `FK_s2kdwx645l0mfijnwyd6yaylq` FOREIGN KEY (`OIDConsultor`) REFERENCES `consultor` (`OIDConsultor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tramite`
--

LOCK TABLES `tramite` WRITE;
/*!40000 ALTER TABLE `tramite` DISABLE KEYS */;
INSERT INTO `tramite` VALUES ('5cc7420b-7744-46ba-9701-894365c05820',NULL,'2024-09-14 00:23:18','2024-09-14 00:23:18','2024-09-14 00:23:18','2024-09-14 00:23:18',1001,1500,'9cfab95e-4e4a-4284-b2a7-708a8130ef04','838c1540-6308-4eed-a960-b049f8e0c21f','77723c36-7d8f-491a-bbf1-dc15bfa53b4a','10a8f047-b459-4a6e-b3ee-b27f3e332fe3','65a7037f-8cff-4c6c-b901-947ac177fc02'),('c1d45a5b-b291-42d2-8388-1101ef3b8e0b',NULL,'2024-09-14 00:23:18','2024-09-14 00:23:18','2024-09-14 00:23:18','2024-09-14 00:23:18',1002,2000,'e2f4213f-6306-46af-9d27-2794ecdee1bb','ba10e5b7-28ee-4139-8dfa-e6f0193bfeaf','fd7bde98-a8cc-4d1b-ab02-9be701a24a65','6120a9e2-dfac-4299-b628-ac63b4fa3dba','da64b3a9-e086-4038-878e-4190c5154bf6');
/*!40000 ALTER TABLE `tramite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tramite_documentacion`
--

DROP TABLE IF EXISTS `tramite_documentacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tramite_documentacion` (
  `OIDTramiteDocumentacion` varchar(255) NOT NULL,
  `archivo_td` varchar(255) DEFAULT NULL,
  `cod_td` int DEFAULT NULL,
  `fecha_entrega_td` datetime DEFAULT NULL,
  `OIDDocumentacion` varchar(255) DEFAULT NULL,
  `OIDTramite` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OIDTramiteDocumentacion`),
  KEY `FK_66j7fk2ruf40c79h4hscmf4ak` (`OIDDocumentacion`),
  KEY `FK_8qutn2xcxs8q1bc6vqirp6fvf` (`OIDTramite`),
  CONSTRAINT `FK_66j7fk2ruf40c79h4hscmf4ak` FOREIGN KEY (`OIDDocumentacion`) REFERENCES `documentacion` (`OIDDocumentacion`),
  CONSTRAINT `FK_8qutn2xcxs8q1bc6vqirp6fvf` FOREIGN KEY (`OIDTramite`) REFERENCES `tramite` (`OIDTramite`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tramite_documentacion`
--

LOCK TABLES `tramite_documentacion` WRITE;
/*!40000 ALTER TABLE `tramite_documentacion` DISABLE KEYS */;
INSERT INTO `tramite_documentacion` VALUES ('3b26c74b-e7c8-4811-bd38-ccdcd6d3fd56','Word',2,NULL,'0196e90a-e5ff-4f4a-9485-65068010b131','c1d45a5b-b291-42d2-8388-1101ef3b8e0b'),('5dc3c707-613e-4de1-9b3c-6741430f804e','excel',1,NULL,'64b0ecd7-d4d4-404d-8a6c-c48a57feccfc','5cc7420b-7744-46ba-9701-894365c05820');
/*!40000 ALTER TABLE `tramite_documentacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tramite_estado_tramite`
--

DROP TABLE IF EXISTS `tramite_estado_tramite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tramite_estado_tramite` (
  `OIDTramiteEstadoTramite` varchar(255) NOT NULL,
  `fecha_hora_alta_tet` datetime DEFAULT NULL,
  `fecha_hora_baja_tet` datetime DEFAULT NULL,
  `OIDEstadoTramite` varchar(255) DEFAULT NULL,
  `OIDTramite` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OIDTramiteEstadoTramite`),
  KEY `FK_omswqk68d392jr2exd59yxeeb` (`OIDEstadoTramite`),
  KEY `FK_ryaqgkriypf987nohaqsnpnwt` (`OIDTramite`),
  CONSTRAINT `FK_omswqk68d392jr2exd59yxeeb` FOREIGN KEY (`OIDEstadoTramite`) REFERENCES `estado_tramite` (`OIDEstadoTramite`),
  CONSTRAINT `FK_ryaqgkriypf987nohaqsnpnwt` FOREIGN KEY (`OIDTramite`) REFERENCES `tramite` (`OIDTramite`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tramite_estado_tramite`
--

LOCK TABLES `tramite_estado_tramite` WRITE;
/*!40000 ALTER TABLE `tramite_estado_tramite` DISABLE KEYS */;
INSERT INTO `tramite_estado_tramite` VALUES ('49dc01e4-95ee-44e5-9a93-d3c50f6824da','2024-09-14 00:23:18',NULL,'9cfab95e-4e4a-4284-b2a7-708a8130ef04','5cc7420b-7744-46ba-9701-894365c05820'),('d2d2453c-d2d6-4fd8-b591-ff3744bf949d','2024-09-14 00:23:18',NULL,'e2f4213f-6306-46af-9d27-2794ecdee1bb','c1d45a5b-b291-42d2-8388-1101ef3b8e0b');
/*!40000 ALTER TABLE `tramite_estado_tramite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `version`
--

DROP TABLE IF EXISTS `version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `version` (
  `OIDVersion` varchar(255) NOT NULL,
  `nro_version` int DEFAULT NULL,
  `descripcion_version` varchar(255) DEFAULT NULL,
  `fecha_baja_version` datetime DEFAULT NULL,
  `fecha_desde_version` datetime DEFAULT NULL,
  `fecha_hasta_version` datetime DEFAULT NULL,
  `OIDTipoTramite` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`OIDVersion`),
  KEY `FK_avkdcbt1ku7o75pulq00go7j8` (`OIDTipoTramite`),
  CONSTRAINT `FK_avkdcbt1ku7o75pulq00go7j8` FOREIGN KEY (`OIDTipoTramite`) REFERENCES `tipo_tramite` (`OIDTipoTramite`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `version`
--

LOCK TABLES `version` WRITE;
/*!40000 ALTER TABLE `version` DISABLE KEYS */;
INSERT INTO `version` VALUES ('10a8f047-b459-4a6e-b3ee-b27f3e332fe3',1,'Versión inicial',NULL,'2024-09-14 00:23:18','2024-09-14 00:23:18','65a7037f-8cff-4c6c-b901-947ac177fc02'),('6120a9e2-dfac-4299-b628-ac63b4fa3dba',2,'Actualización de mitad de año',NULL,'2024-09-14 00:23:18','2024-09-14 00:23:18','da64b3a9-e086-4038-878e-4190c5154bf6');
/*!40000 ALTER TABLE `version` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-13 21:24:53
