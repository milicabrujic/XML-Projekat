INSERT INTO `category` (`name`, `primary_image_url`) VALUES ('Cetinari', 'images/12-2020/cetinari.jpg');
INSERT INTO `category` (`name`, `primary_image_url`) VALUES ('Ruze', 'images/12-2020/abracadabra_6.jpg');


-- kacige
INSERT INTO `category` (`name`, `parent_category_id`, `primary_image_url`) VALUES
('Piramidalni', 1, 'images/12-2020/piramidalni.jpg'),
('Kuglasti', 1, 'images/12-2020/kuglaste.jpg'),
('Zbunasti', 1, 'images/12-2020/zbunasti.jpg'),
('Cajevke', 2, 'images/12-2020/cajevke.jpg'),
('mnogocvetnice', 2, 'images/12-2020/mnogosvetnice.jpg');
