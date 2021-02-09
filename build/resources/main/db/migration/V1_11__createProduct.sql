DROP TABLE IF EXISTS `product`;

CREATE TABLE `product`(
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `created_by` varchar(255) DEFAULT NULL,
    `date_created` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `deleted` bit DEFAULT 0,
    `date_deleted` datetime DEFAULT NULL,
    `last_modified_by` varchar(255) DEFAULT NULL,
    `date_updated` datetime DEFAULT NULL,
    `name` varchar(255) NOT NULL,
    `description` text DEFAULT NULL,
    `product_category_id` bigint(20) DEFAULT NULL,
    `brand_id` bigint(20) DEFAULT NULL,
    `price` double DEFAULT NULL,
    `active` boolean DEFAULT TRUE,
    `new_added` boolean DEFAULT FALSE,
    `on_sale` boolean DEFAULT FALSE,
    `primary_image_url` varchar(255) DEFAULT NULL,
    `product_position` varchar(255) DEFAULT NULL,
    `discount` varchar(255) DEFAULT NULL,
    `discount_product_position` varchar(255) DEFAULT NULL,
    PRIMARY KEY(`id`)
)DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;