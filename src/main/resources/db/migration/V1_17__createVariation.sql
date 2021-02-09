DROP TABLE IF EXISTS `variation`;

CREATE TABLE `variation`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_by` varchar(255) DEFAULT NULL,
    `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` bit DEFAULT 0,
    `date_deleted` datetime DEFAULT NULL,
    `last_modified_by` varchar(255) DEFAULT NULL,
    `date_updated` datetime DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    `product_id` bigint(20) DEFAULT NULL,
    `price` double DEFAULT NULL,
    `primary_image_url` varchar(255) DEFAULT NULL,
    `active` boolean DEFAULT FALSE,
    PRIMARY KEY(`id`)
)DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;