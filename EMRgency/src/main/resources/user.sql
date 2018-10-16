CREATE TABLE `user` (
  `user_id` int(6) unsigned NOT NULL AUTO_INCREMENT,
  `firstname` varchar(30) NOT NULL,
  `lastname` varchar(30) NOT NULL,
  `cellno` varchar(15) NOT NULL,
  `imei1` varchar(20) DEFAULT NULL,
  `imei2` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `created` timestamp NULL DEFAULT NULL,
  `lastupdated` timestamp NULL DEFAULT NULL,
  `primary_user` tinyblob,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `cellno` (`cellno`),
  UNIQUE KEY `imei1` (`imei1`),
  UNIQUE KEY `imei2` (`imei2`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;