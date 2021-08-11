DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deleted` bit DEFAULT 0,
  `allow_messages` bit DEFAULT 0,
  `allow_tags` bit DEFAULT 0,
  `private_profile` bit DEFAULT 0,
  `address` varchar(255) DEFAULT NUll,
  `city` varchar(255) DEFAULT NUll,
  `country` varchar(255) DEFAULT NUll,
  `phone_number` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) NOT NULL,
  `website` varchar(255) NOT NULL,
  `bio` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

