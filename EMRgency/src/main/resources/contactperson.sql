CREATE TABLE `contactperson` (
  `contact_id` int(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `cellno` varchar(15) NOT NULL,
  `pemail` varchar(50) DEFAULT NULL,
  `semail` varchar(50) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `lastupdated` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`contact_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;