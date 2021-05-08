-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: blogs
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `blog`
--

DROP TABLE IF EXISTS `blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `blog` (
  `id_blog` bigint(20) NOT NULL AUTO_INCREMENT,
  `announcement` varchar(255) DEFAULT NULL,
  `ban_blog` bit(1) DEFAULT NULL,
  `date_create_blog` date DEFAULT NULL,
  `full_text` varchar(20000) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `id_user` bigint(20) NOT NULL,
  `id_ingredient` bigint(20) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_blog`),
  KEY `FK1s49ioo42sqeyiaq27v94qj5w` (`id_user`)
) ENGINE=MyISAM AUTO_INCREMENT=84 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `blog`
--

LOCK TABLES `blog` WRITE;
/*!40000 ALTER TABLE `blog` DISABLE KEYS */;
INSERT INTO `blog` VALUES (1,'Как приготовить аппетитную яичницу-глазунью...','\0','2021-03-01','Нет ни одного человека в мире, который бы хоть раз не приготовил на своей кухне яичницу-глазунью. Такое блюдо готовится за 5-7 минут, оно не требует особых усилий с вашей стороны. Куриные яйца можно приобрести в любом магазине пошаговой доступности или в супермаркете, но лучше всего покупать их у деревенских производителей от домашней птицы – в таких больше пользы и желтки у них более выраженные, красочные.\r\n\r\nПоданная на завтрак яичница-глазунья с яркими желтками не только насыщает организм и утоляет чувство голода, но еще и дарит позитивный настрой на весь день. Кстати, к ней можно подать колбасу, отварное мясо, шкварки, свежую зелень и т.д. В Англии такое блюдо непременно гарнируют обжаренным беконом, фасолью в томатном соусе, румяными тостами, и, конечно же, подают к нему свежесваренный кофе.  \r\n\r\nЯйца куриные\r\n3 шт.\r\nМасло растительное\r\n1 ч.л.\r\nСоль и перец\r\nпо вкусу\r\nЗелень\r\nдля подачи',4.43,'Яишница глазунья',2,0,NULL),(2,'Украинский борщ в полной мере отражает характер кухни страны','\0','2021-04-04','Украинский борщ в полной мере отражает характер кухни страны: суп этот сытный, ароматный, разноцветный, густой, невероятно вкусный. И, кстати, очень полезный, ведь в его состав входит большое количество корнеплодов и овощей. Плюс — зелень с чесноком в самом конце приготовления!  В общем, настоящий подарок и для души, и для тела. Многие не решаются взяться за приготовление украинского борща из-за длительности варки и большого количества тонкостей, которые необходимо учитывать. Спешим успокоить: наш вариант украинского борща довольно прост в исполнении, поэтому с ним справится даже тот, кто делает свои первые шаги к кулинарным вершинам.',2.32,'Украинский борщ',2,0,NULL),(3,'Классический салат Оливье','\0','2021-04-04','Классичексий салат Оливье в советское время готовили исключительно с вареной колбасой, желательно - с  Докторской. Мы не отступили от традиции и приготовили Оливье по канонам  советской гастрономии.ИНГРЕДИЕНТЫ\r\n                                                                                                                                                                                                      \r\n                                                                                                                                                                                                          картофель вареный (желательно не молодой, а старый) – 4 шт. средних\r\n                                                                                                                                                                                                          морковка - 1 шт.\r\n                                                                                                                                                                                                          яйца сваренные вкрутую – 4 шт.\r\n                                                                                                                                                                                                          колбаса вареная \"докторская\" – 300 г\r\n                                                                                                                                                                                                          огурцы маринованные (можно свежие) – 4 шт. средних\r\n                                                                                                                                                                                                          горошек зеленый консервированный – 1 банка весом 200 г\r\n                                                                                                                                                                                                          майонез – 200-300 г\r\n                                                                                                                                                                                                          листья петрушки и укропа по желанию\r\n                                                                                                                                                                                                          соль, свежемолотый черный перец\r\n',0,'Салат Оливье классический советский',2,0,NULL),(6,'Как приготовить аппетитную яичницу-глазунью...Очень вкусно...','\0','2021-04-04','Нет ни одного человека в мире, который бы хоть раз не приготовил на своей кухне яичницу-глазунью. Такое блюдо готовится за 5-7 минут, оно не требует особых усилий с вашей стороны. Куриные яйца можно приобрести в любом магазине пошаговой доступности или в супермаркете, но лучше всего покупать их у деревенских производителей от домашней птицы – в таких больше пользы и желтки у них более выраженные, красочные.\r\n\r\nПоданная на завтрак яичница-глазунья с яркими желтками не только насыщает организм и утоляет чувство голода, но еще и дарит позитивный настрой на весь день. Кстати, к ней можно подать колбасу, отварное мясо, шкварки, свежую зелень и т.д. В Англии такое блюдо непременно гарнируют обжаренным беконом, фасолью в томатном соусе, румяными тостами, и, конечно же, подают к нему свежесваренный кофе.  \r\n\r\nЯйца куриные\r\n3 шт.\r\nМасло растительное\r\n1 ч.л.\r\nСоль и перец\r\nпо вкусу\r\nЗелень\r\nдля подачи',3,'Яишница глазунья',3,0,NULL),(7,'Классический салат Оливье','\0','2021-04-04','Классичексий салат Оливье в советское время готовили исключительно с вареной колбасой, желательно - с  Докторской. Мы не отступили от традиции и приготовили Оливье по канонам  советской гастрономии.ИНГРЕДИЕНТЫ\r\n                                                                                                                                                                                                      \r\n                                                                                                                                                                                                          картофель вареный (желательно не молодой, а старый) – 4 шт. средних\r\n                                                                                                                                                                                                          морковка - 1 шт.\r\n                                                                                                                                                                                                          яйца сваренные вкрутую – 4 шт.\r\n                                                                                                                                                                                                          колбаса вареная \"докторская\" – 300 г\r\n                                                                                                                                                                                                          огурцы маринованные (можно свежие) – 4 шт. средних\r\n                                                                                                                                                                                                          горошек зеленый консервированный – 1 банка весом 200 г\r\n                                                                                                                                                                                                          майонез – 200-300 г\r\n                                                                                                                                                                                                          листья петрушки и укропа по желанию\r\n                                                                                                                                                                                                          соль, свежемолотый черный перец\r\n салат Оливье в советское время готовили исключительно с вареной колбасой, желательно - с  Докторской. Мы не отступили от традиции и приготовили Оливье по канонам  советской гастрономии.',3.63,'Салат Оливье классический советский',3,0,NULL),(8,'Украинский борщ в полной мере отражает характер кухни страны','\0','2021-04-05','Украинский борщ в полной мере отражает характер кухни страны: суп этот сытный, ароматный, разноцветный, густой, невероятно вкусный. И, кстати, очень полезный, ведь в его состав входит большое количество корнеплодов и овощей. Плюс — зелень с чесноком в самом конце приготовления!  В общем, настоящий подарок и для души, и для тела. Многие не решаются взяться за приготовление украинского борща из-за длительности варки и большого количества тонкостей, которые необходимо учитывать. Спешим успокоить: наш вариант украинского борща довольно прост в исполнении, поэтому с ним справится даже тот, кто делает свои первые шаги к кулинарным вершинам.',0,'Украинский борщ',3,0,NULL),(10,'Похожие картофельные блинчики существуют во многих кухнях: в Польше и Словакии это пляцки, в Швейцарии rösti, в Израиле латкес.','\0','2021-04-05','Похожие картофельные блинчики существуют во многих кухнях: в Польше и Словакии это пляцки, в Швейцарии rösti, в Израиле латкес. Ну и, конечно, нельзя забыть о белорусских драниках. Не остались в стороне и украинцы. В Житомирской области во время проведения фестиваля дерунов открылся памятник с такой надписью:\r\n«Деруны, дерунята, дерунчики\r\nЗолотавi, духм’янi, смачнi,\r\nВы окраса полiського столу\r\nСтрава на день i цiлi вiки!»\r\nКажется, и переводить не нужно.\r\nИНГРЕДИЕНТЫ\r\n\r\n    10 средних картофелин\r\n    2 маленькие луковицы\r\n    3 ст. л. муки\r\n    1 яйцо\r\n    растительное или топленое масло для жарки\r\n    растительное или топленое масло для жарки\r\n',0,'Деруны с жареным луком',2,0,NULL),(12,'О самом правильном рецепте супа харчо по-грузински можно спорить бесконечно, как и о способе приготовления любого другого популярного блюда. ','\0','2021-04-05','О самом правильном рецепте супа харчо по-грузински можно спорить бесконечно, как и о способе приготовления любого другого популярного блюда. Мы не претендуем на абсолютную истину, однако представленный здесь вариант можно считать достаточно аутентичным. Присутствие в ингредиентах тклапи — высушенного пюре из мякоти сливы ткемали, немного напоминающего натуральную фруктовую пастилу, а также харчос-сунели (особой смеси специй) обеспечивает блюду яркий кавказский «акцент» и незабываемый аромат. Тарелочка такого харчо по-грузински не только подарит яркие гастрономические впечатления, но и, возможно, вдохновит на планирование путешествия в одну из самых «вкусных» стран мира!',4,'Суп Харчо по-грузински',2,0,NULL),(78,'Рецептов приготовления плова очень-очень много!\r\nЯ предлагаю рецепт, который очень любим в нашей семье. Так готовит плов мой папа.','\0','2021-05-03','Рецептов приготовления плова очень-очень много!\r\nЯ предлагаю рецепт, который очень любим в нашей семье. Так готовит плов мой папа.\r\nПлов получается ароматный, рассыпчатый, сочный.\r\nЯ затрудняюсь сказать точно, сколько получается порций, но его получается много.\r\nСъедается он быстро.\r\nК плову можно подать овощной салат, или крупно нарезанные помидоры с луком.\r\n\r\n    1.5 кг мяса (баранины, свинины или говядины)\r\n    1 кг риса (в идеале сорта девзира)\r\n    600 г моркови\r\n    500 г лука\r\n    1 ст.л. зиры\r\n    1 ст.л. барбариса\r\n    ½ ст.л. куркумы\r\n    1 головка чеснока\r\n    красный молотый перец\r\n    черный молотый перец\r\n    соль\r\n    растительное масло',0,'Плов',67,0,NULL);
/*!40000 ALTER TABLE `blog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id_comment` bigint(20) NOT NULL AUTO_INCREMENT,
  `ban_comment` bit(1) DEFAULT NULL,
  `date_create_comment` datetime DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `id_blog` bigint(20) NOT NULL,
  `id_user` bigint(20) NOT NULL,
  PRIMARY KEY (`id_comment`),
  KEY `FKd88fbtv2v04mfv8bwah9714p` (`id_blog`),
  KEY `FK3xl9c4qhiqfaqybv4pb94tevv` (`id_user`)
) ENGINE=MyISAM AUTO_INCREMENT=66 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (3,'','2021-04-07 00:57:34','ываывфмячм',1,1),(4,'\0','2021-04-07 00:57:41','1234',1,1),(5,'\0','2021-04-07 00:58:12','ывппфвап',1,1),(6,'\0','2021-04-07 01:00:28','фалорпдлф',1,1),(7,'\0','2021-04-07 01:02:20','ываывпыфвп',1,1),(8,'\0','2021-04-07 01:16:22','asffsdgdfz',1,1),(9,'\0','2021-04-07 22:38:17','aaaaa',1,3),(10,'\0','2021-04-07 22:48:06','очень хороший рецепт\r\n',6,3),(11,'','2021-04-08 09:03:40','Приятно читать',2,3),(12,'\0','2021-04-09 08:11:52','123',2,3),(14,'\0','2021-04-09 08:36:18','qwerty',2,3),(15,'\0','2021-04-09 08:44:24','qwerty',2,3),(16,'\0','2021-04-09 08:49:39','qwerty 3',2,3),(17,'\0','2021-04-09 08:54:20','qwerty 4',2,3),(18,'\0','2021-04-09 08:57:27','qwerty 5',2,3),(20,'\0','2021-04-10 19:29:07','Дуже смачно',6,1),(21,'\0','2021-04-10 19:47:31','Умммм... Вкуснотище))',7,1),(22,'\0','2021-04-10 21:22:11','рлоравлорывл',1,1),(45,'\0','2021-04-24 13:55:25','То что искал))',7,1),(46,'\0','2021-04-24 13:57:31','вкусняша!!',7,1),(47,'\0','2021-04-24 13:59:10','Очень интересно...',12,3),(48,'\0','2021-04-24 14:01:55','можно было бы и по детальнее',12,3),(65,'\0','2021-05-03 01:38:07','qwertyuio',1,1);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (1);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `persistent_logins` (
  `series` varchar(255) NOT NULL,
  `last_used` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`series`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `picture`
--

DROP TABLE IF EXISTS `picture`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `picture` (
  `id_picture` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `url_image` varchar(255) DEFAULT NULL,
  `id_blog` bigint(20) NOT NULL,
  PRIMARY KEY (`id_picture`),
  KEY `FKl0rfolfgtcspfqr005v56f63y` (`id_blog`)
) ENGINE=MyISAM AUTO_INCREMENT=91 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `picture`
--

LOCK TABLES `picture` WRITE;
/*!40000 ALTER TABLE `picture` DISABLE KEYS */;
INSERT INTO `picture` VALUES (1,'глазунья','images/blogs_images/2/1/яишница.jpg',1),(3,'оливье','images/blogs_images/2/3/olivye.jpg',3),(2,'борщь','images/blogs_images/2/2/borsch.jpg',2),(4,'яишница','images/blogs_images/3/6/yaichnica-glazuniya-770x513.jpg',6),(5,'оливье','images/blogs_images/3/7/olivye.jpg',7),(6,'яишница','images/blogs_images/3/8/borsch.jpg',8),(8,'деруны','images/blogs_images/2/10/деруны.jpg',10),(10,'харчо','images/blogs_images/2/12/харчо.jpg',12),(85,'Плов','images/blogs_images/67/78/118_0134bxr_2961_6hi.jpg',78);
/*!40000 ALTER TABLE `picture` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id_role` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id_role`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id_user` bigint(20) NOT NULL AUTO_INCREMENT,
  `ban_user` bit(1) DEFAULT NULL,
  `born` date DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `facebook` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `foto` varchar(255) DEFAULT NULL,
  `git_hub` varchar(255) DEFAULT NULL,
  `instagram` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `twiter` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `activation_code` varchar(255) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=MyISAM AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'','1978-02-27','Васильков','Украина','evgeniy.zhurenko@gmail.com','https://www.facebook.com/','Евгений','images/profiles_images/2/cyberpunk-2077-kiberpank-572.jpg',NULL,'https://www.instagram.com/?hl=ru','Журенко','$2a$10$r7DlZy3mYMKt6j9hrR1HVOhugAxDltOrsokFkF7JxA648UdulC84y','+380672241028','https://twitter.com/?lang=ru','milman','','\0'),(3,'\0','1987-01-25','Васильков','Украина','qwe@qwe.com','https://www.facebook.com','Ольга','images/profiles_images/3/wallpaperbetter.com_3072x1920.jpg',NULL,'https://www.instagram.com/?hl=ru','Журенко','$2a$10$r7DlZy3mYMKt6j9hrR1HVOhugAxDltOrsokFkF7JxA648UdulC84y','044 432 45 763','https://twitter.com/?lang=ru','olga','','\0'),(1,'\0',NULL,NULL,NULL,NULL,NULL,'guest',NULL,NULL,NULL,'guest',NULL,NULL,NULL,'guest','','\0'),(4,'\0',NULL,'Киев','Украина','admin@admin','https://www.facebook.com','admin','images/profiles_images/4/051770-kosmos-galaktika-tumannost.jpg',NULL,'https://www.instagram.com/?hl=ru','admin','$2a$10$P1xjzs9kIq.U5iyqEJ4BIOrI6UVToalQq09eh/JELohWMzWRuoU6a','+380509693243','https://twitter.com/?lang=ru','admin','','\0'),(67,'\0',NULL,'','','dmitryboyko1qq@gmail.com','','qwe','images/profiles_images/67/_пустыня.jpg',NULL,'','qwe','$2a$10$XK4V.3l.uiMwb2em97ySMOcSfg1zP6AC7WY9j82XlUb6xwi7dukaa','044 432 45 763','','qwe','d96234c8-043d-4c37-a7fa-f5012aa1fa7f','');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `id_role` bigint(20) NOT NULL,
  `id_user` bigint(20) NOT NULL,
  PRIMARY KEY (`id_user`,`id_role`),
  KEY `FKrcmv344t6l0beetcs8u4xhpd` (`id_role`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES (2,2),(2,3),(1,4),(2,67);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-05-04 19:02:41
