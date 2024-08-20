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
  `biglietto_nome` varchar(50) NOT NULL,
  `biglietto_cognome` varchar(50) NOT NULL,
  `categoria` varchar(20) NOT NULL,
  `zona` varchar(14) NOT NULL,
  `fila` int NOT NULL,
  `palco` int DEFAULT NULL,
  `numero_posto` int NOT NULL,
  `id_replica` int NOT NULL,
  `id_utente` int NOT NULL,
  `biglietto_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_biglietto`),
  UNIQUE KEY `id_biglietto_UNIQUE` (`id_biglietto`),
  UNIQUE KEY `index_posto` (`zona`,`fila`,`palco`,`numero_posto`,`id_replica`),
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
  `codice` varchar(10) NOT NULL,
  `sconto` int NOT NULL,
  `coupon_genere` varchar(20) NOT NULL,
  `data_inizio` date NOT NULL,
  `data_fine` date NOT NULL,
  `coupon_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_coupon`),
  UNIQUE KEY `codice_UNIQUE` (`codice`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `COUPON`
--

LOCK TABLES `COUPON` WRITE;
/*!40000 ALTER TABLE `COUPON` DISABLE KEYS */;
INSERT INTO `COUPON` VALUES (1,'CODICE1',20,'Prosa','2024-01-24','2025-01-24',0),(2,'CODICE2',10,'Concerti','2024-05-19','2025-05-19',0),(3,'CODICE3',50,'Prosa','2024-08-10','2025-08-10',0),(4,'CODICE4',30,'Altro','2024-02-01','2025-02-01',0),(5,'CODICE5',15,'Tutti','2024-05-20','2025-05-20',0),(6,'CODICE6',20,'Danza','2024-07-01','2025-07-01',0);
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
  `replica_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_replica`),
  UNIQUE KEY `id_replica_UNIQUE` (`id_replica`),
  UNIQUE KEY `index4` (`inizio`,`id_spettacolo`),
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
  `id_spettacolo` int NOT NULL AUTO_INCREMENT,
  `spettacolo_nome` varchar(255) NOT NULL,
  `genere` varchar(20) NOT NULL,
  `compagnia` varchar(255) DEFAULT NULL,
  `descrizione` varchar(1000) DEFAULT NULL,
  `immagine` varchar(45) DEFAULT NULL,
  `spettacolo_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_spettacolo`),
  UNIQUE KEY `id_spettacolo_UNIQUE` (`id_spettacolo`),
  UNIQUE KEY `immagine_UNIQUE` (`immagine`)
) ENGINE=InnoDB AUTO_INCREMENT=74 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SPETTACOLO`
--

LOCK TABLES `SPETTACOLO` WRITE;
/*!40000 ALTER TABLE `SPETTACOLO` DISABLE KEYS */;
INSERT INTO `SPETTACOLO` VALUES (1,'La Locandiera','Prosa','Teatro Stabile dell\'Umbria','Un’apparente, spensierata commedia amorosa in cui il non detto, il non desiderato, il non voluto diventano parole schiette, desideri e voglie, che le danno un carattere universale e squisitamente moderno.','locandiera',0),(2,'L\'ispettore generale','Prosa','Teatro Stabile di Bolzano','L’ispettore generale è una commedia satirica estremamente divertente che si prende gioco delle piccolezze morali di chi detiene un potere e si ritiene intoccabile.\nChlestakov è un frivolo viaggiatore di passaggio in un remoto paesino che viene scambiato per un alto funzionario dello Stato spedito dallo zar ad indagare sulla condotta dei funzionari cittadini. Il malinteso scatena conseguenze nefaste per i “notabili” del piccolo villaggio – primo tra tutti per il Podestà – che si troveranno a vivere il giorno più lungo e tragico della propria esistenza, col timore di venire smascherati.','ispettore',0),(3,'Pirandello trilogia di un visionario','Prosa','Goldenart Production','Pirandello rimane oggi uno degli scrittori e drammaturghi più interessanti e complessi da riportare in scena. Andiamo a intrecciare L’uomo dal Fiore in bocca (atto unico, figlio della novella La morte addosso), La Carriola (dalla raccolta Novelle per un anno) e una parte della corrispondenza tra Marta Abba e Pirandello riadattandoli ad una messa in scena nuova.\nCreiamo un fil rouge tra personaggi che improvvisamente si estraniano da sé, si guardano da fuori, si ritrovano, come dice Giovanni Macchi, a poggiare l’uno sull’altro.','pirandello',0),(4,'Sei personaggi in cerca d\'autore','Prosa','Teatro Bellini','Nel grande capolavoro di Luigi Pirandello Valerio Binasco ritrova gli elementi che caratterizzano la propria poetica: arte e vita, umanità e maschere si fondono in un nucleo di interrogativi e riflessioni sul valore della rappresentazione e della nostra identità. Con questa vicenda, apparentemente scontata, di una famiglia dilaniata, Binasco intercetta i sottili e fragili fili che reggono i rapporti umani, rimandando alla vera sostanza dell’essere umano, e così a quella dell’attore, che da millenni cerca di rappresentare la più intima essenza della collettività.','seipersonaggi',0),(5,'Pignasecca e Pignaverde','Prosa','Teatro Sociale di Camogli','Tullio Solenghi torna a trasformare anima e corpo nella maschera goviana: una scelta registica e interpretativa a suo modo estrema.\nPignasecca e Pignaverde, di Emerico Valentinetti, ha il valore aggiunto di una drammaturgia elaborata e ambiziosa, incentrata su un tema classico del teatro comico: l’avarizia. Un piccolo Molière “alla genovese”.','pignasecca',0),(6,'Come neve','Danza','Körper – Centro Nazionale di Produzione della danza','“Sono partito dall’immagine della neve che si osserva quando si è piccoli. I danzatori, con corpo e movimento, danno vita a qualcosa di unico, come un fiocco di neve che cade al suolo. Ho deciso di coinvolgere nel progetto Il club dell’uncinetto perché si racconta che questa pratica nacque quando una signora, rimanendo affascinata dallo spettacolo dei fiocchi di neve che cadevano sul suo davanzale, cercò di riprodurne la bellezza con un filo di cotone e un grosso ago ricurvo.”\nAdriano Bolognino','comeneve',0),(7,'Don Juan | The Carnival Party','Danza','Compagnia Aterballetto','Lo spettatore, indossando un visore per la realtà virtuale, si troverà immerso all’interno di una scena “centrale” dello spettacolo, ovvero l’inganno di Don Juan a Donna Anna e Don Ottavio.\nLa performance si sviluppa attraversando le sale della Pinacoteca Nazionale a Palazzo dei Diamanti, per un viaggio emozionante e indimenticabile dove tecnologia e arte si fondono per creare una nuova modalità di esperienza artistica.','donjuan',0),(8,'Divina Commedia','Danza','Compagnia Aterballetto','Le donne e gli uomini, terrestri e divini, mortali e immortali, che Dante racconta nella Divina Commedia non sono corpi. Ma intelligenze, memorie, visioni, desideri, idee: anime. E le anime non pesano.\nQuesta intuizione fisica e poetica è il punto di appoggio dal quale prende, letteralmente, il volo l’allestimento di Emiliano Pellisari.','divinacommedia',0),(9,'Mont Ventoux','Danza','Collettivo Kor’sia','Con Mount Ventoux, il Collettivo Kor’sia rivisita l’opera che Francesco Petrarca scrisse nel 1336, Ascesa al Monte Ventoso. Un viaggio ascensionale per l’umanità per lasciarsi alle spalle gli anni bui del Medioevo; portando un cambiamento paradigmatico al mondo a venire, l’umanesimo.\nCome percepisce il Collettivo Kor’sia, questa storia, ci offre ancora la possibilità di imparare dal passato, che può essere trasformato in una migliore esperienza del presente, e quindi nella costruzione di un futuro migliore per tutti e tutte.','montventoux',0),(10,'Il Seicento Ferrarese','Prosa','Associazione Ferrara Musica','LUCA GIARDINI VIOLINO\nCRISTINA ALBERTI VIOLINO\nFILIPPO PANTIERI CLAVICEMBALO\nFrescobaldi Day\nIl Seicento Ferrarese\nmusiche di FRESCOBALDI, BASSANI, CAZZATI, MARINI, BONONCINI, LEGRENZI, VERACINI','seicento',0),(11,'Concerto per violoncello e orchestra op.104','Concerti','Orchestra Sinfonica Toscanini','ORCHESTRA SINFONICA TOSCANINI\nANDREY BOREYKO DIRETTORE\nMISCHA MAISKY VIOLONCELLO','maisky',0),(12,'MENDELSSOHN Concerto per violino e orchestra in mi minore','Concerti','Budapest Festival Orchestra','MAHLER CHAMBER ORCHESTRA\nELIM CHAN DIRETTRICE\nMARIA JOÃO PIRES PIANOFORTE\nRICK STOTIJN CONTRABBASSO','mendelsshon',0),(13,'Le nozze di Figaro','Opera','Orchestra del Conservatorio Frescobaldi di Ferrara','L’ambiente scelto è quello della misera periferia romana degli anni Sessanta del secolo scorso. Come nel capolavoro di Ettore Scola Brutti, sporchi e cattivi, «i personaggi di questa commedia – spiega il regista Schvarzstein – si mescolano in un continuo andirivieni di vicende che sono il frutto di una tensione sociale, non riescono a realizzarsi e vogliono coinvolgere gli altri nel loro insuccesso».\nIl regista spagnolo-argentino riprende le provocazioni di Beaumarchais e le porta in scena: l’opera mozartiana diventa così l’opportunità per «sorridere delle convenzioni, e soprattutto delle diverse classi sociali che oggi, effettivamente, non hanno alcun senso».','nozze',0),(14,'Norma','Opera','Operiamo - Casa Della Musica E Delle Arti','Nel settimo centenario dalla morte di Dante Alighieri l’opera belliniana rievoca in sé la Commedia dantesca permettendo ad ognuno di riconoscere l’umanità che circonda entrambe le opere.\nL’inferno è vivo, umano, tangibile, violento e passionale così come il mondo che troviamo in Norma. Questo è stato lo spunto per gli elementi scenografici narrativi: un grande albero rievoca la metamorfosi dei suicidi che avendo rifiutato il loro corpo, sono costretti ad essere una inferiore forma di vita (vermo reo che l’mondo fora): risale dagli inferi, caratterizzata dalle sue tre facce, una corporeità prorompente con grandi ali scure tali da inglobare il bene quale ribaltamento di tutti i valori.','norma',0),(15,'Astolfo sulla Luna','Opera','Operiamo - Casa Della Musica E Delle Arti','Rimane l’attualità eterna di questi versi dell’Orlando furioso, che sanno sorridere di molte vanità umane, “dell’ozio lungo d’uomini ignoranti”, delle bugie degli amanti, della fama scambiata per la gloria, dell’insipienza umana alla costante ricerca di beni effimeri, che si mostrano ingannevoli, ma con un tono di infinita comprensione della dissennatezza, e che non giudica ma compatisce.\nAbbiamo bisogno di autori nelle cui parole si riscopra ancora oggi la gioia dei sensi e il gioco del caso, che non abbiano solo una visione trascendente della vita, che non lascia spazio al vivere terreno ma lo infirma di inautenticità.\nGeni come l’Ariosto, come Giovanni Boccaccio, come Wolfgang Amedeus Mozart e Gioachino Rossini sono tra i pochi che hanno parlato della gioia di vivere, libera dall’ombra della morte, persuasa che la vita abbia in sé il suo valore e il suo limite.','astolfo',0),(16,'Mordere il Cielo','Altro','Paolo Crepet','Per tornare a “mordere il cielo” occorre ritrovare il coraggio di nuove eresie, rinnovare ribellioni per inseguire le nostre unicità, diffidando di quella grigia normalità dietro la quale si nasconde il sinistro rumore della neutralizzazione dell’anima.','crepet',0),(17,'Personaggi','Altro','Antonio Albanese','Un recital che racconta, con corrosiva comicità e ritmo serrato, un mondo popolato da personaggi tipici del nostro tempo, dal pensiero contemporaneo interpretato con dirompente fisicità.','albanese',0);
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
  `deleted` tinyint NOT NULL DEFAULT '0',
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
  `utente_nome` varchar(50) NOT NULL,
  `utente_cognome` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `telefono` varchar(10) NOT NULL,
  `password` varchar(45) NOT NULL,
  `privilegi` tinyint NOT NULL,
  `utente_deleted` tinyint NOT NULL DEFAULT '0',
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

--
-- Table structure for table `counter`
--

DROP TABLE IF EXISTS `counter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `counter` (
  `counterId` varchar(20) NOT NULL,
  `counterValue` int DEFAULT NULL,
  PRIMARY KEY (`counterId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `counter`
--

LOCK TABLES `counter` WRITE;
/*!40000 ALTER TABLE `counter` DISABLE KEYS */;
INSERT INTO `counter` VALUES ('bigliettoId',16),('couponId',7),('replicaId',25),('spettacoloId',18);
/*!40000 ALTER TABLE `counter` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-08-21  1:51:25
