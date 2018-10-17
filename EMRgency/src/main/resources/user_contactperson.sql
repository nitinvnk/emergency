CREATE TABLE `user_contactperson` (
  `user_id` int(6) unsigned NOT NULL,
  `contact_id` int(6) unsigned NOT NULL,
  KEY `FK_USER` (`user_id`),
  KEY `FK_CONTACT` (`contact_id`),
  CONSTRAINT `FK_CONTACT` FOREIGN KEY (`contact_id`) REFERENCES `contactperson` (`contact_id`),
  CONSTRAINT `FK_USER` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
