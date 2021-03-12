INSERT INTO `category` (`name`, `primary_image_url`) VALUES ('Ruže', 'images/3-2021/1d32f8c3217a3da4788aa18871f77983.png');
INSERT INTO `category` (`name`, `primary_image_url`) VALUES ('Ruze Stablašice', 'images/3-2021/aa4803d2f64a023cfb5bebcd76680202.png');
INSERT INTO `category` (`name`, `primary_image_url`) VALUES ('Četinari', 'images/3-2021/5a78205980977a33d336f886a13c707a.png');
INSERT INTO `category` (`name`, `primary_image_url`) VALUES ('Lišćari', 'images/3-2021/13a496477df9929cf96e38dcbc0a8c5.png');
INSERT INTO `category` (`name`, `primary_image_url`) VALUES ('Topijarne forme', 'images/3-2021/6289538144ab785d93cc6f37816cc0e2.png');
INSERT INTO `category` (`name`, `primary_image_url`) VALUES ('Ukrasne trave i perene', 'images/3-2021/11707358adff0c7263f6bb37282e9133.png');
INSERT INTO `category` (`name`, `primary_image_url`) VALUES ('Specijalna selekcija', 'images/3-2021/1fbc85ccc45fbb201b8d538db484a495.png');
INSERT INTO `category` (`name`, `primary_image_url`) VALUES ('Klasično voće', 'images/3-2021/878a3a43dbf1e62d0984db4bdaa884eb.png');
INSERT INTO `category` (`name`, `primary_image_url`) VALUES ('Bobičasto voće', 'images/3-2021/ede3e8d82c9f0cf332a82a247fb2b53d.png');

-- Ruže
INSERT INTO `category` (`name`, `parent_category_id`, `primary_image_url`) VALUES
('Čajevke', 1, 'images/12-2020/piramidalni.jpg'),
('Mnogocvetnice', 1, 'images/12-2020/kuglaste.jpg'),
('Penjačice', 1, 'images/12-2020/zbunasti.jpg'),
('Grmolike', 1, 'images/12-2020/cajevke.jpg'),
('Minijaturne', 1, 'images/12-2020/mnogosvetnice.jpg'),
('Polegle', 1, 'images/12-2020/mnogosvetnice.jpg'),
('Engleske', 1, 'images/12-2020/mnogosvetnice.jpg');


-- Ruže Stablašice
INSERT INTO `category` (`name`, `parent_category_id`, `primary_image_url`) VALUES
('Kuglaste', 2, 'images/12-2020/piramidalni.jpg'),
('Padajuće', 2, 'images/12-2020/kuglaste.jpg'),
('Mini', 2, 'images/12-2020/zbunasti.jpg');


-- Cetinari
INSERT INTO `category` (`name`, `parent_category_id`, `primary_image_url`) VALUES
('Piramidalni', 3, 'images/12-2020/piramidalni.jpg'),
('Kuglasti', 3, 'images/12-2020/kuglaste.jpg'),
('Polegli', 3, 'images/12-2020/zbunasti.jpg'),
('Žbunasti', 3, 'images/12-2020/zbunasti.jpg');

-- Lišćari
INSERT INTO `category` (`name`, `parent_category_id`, `primary_image_url`) VALUES
('Ukrasno žbunje', 4, 'images/3-2021/ede3e8d82c9f0cf332a82a247fb2b53d.png'),
('Japanski javori', 4, 'images/3-2021/a6275f98e57476d3d12885de9e4b9b14.png'),
('Rododendroni', 4, 'images/3-2021/ab8622221a0d0180f1e25f95c1d6fc94.png'),
('Hortenzije', 4, 'images/3-2021/b2571f6dc8bcc05b051e98decaea9a83.png'),
('Klematisi', 4, 'images/3-2021/13f739a25c566c9f35e610677722ea32.png'),
('Magnolije', 4, 'images/3-2021/5836331ade3ed6cfd459d932c5367357.png'),
('Puzavice', 4, 'images/3-2021/4aabd8cd8517bb81ebac7bd77e606bcc.png'),
('Drvenasti Božuri', 4, 'images/12-2020/zbunasti.jpg');


-- Topijarne forme
INSERT INTO `category` (`name`, `parent_category_id`, `primary_image_url`) VALUES
('Kalem na štapu', 5, 'images/3-2021/84b9109ac0a906052a0fce12b7b152d8.png'),
('Spirale', 5, 'images/3-2021/844f19305240879851f22637c99c49ca.png'),
('Bonsai', 5, 'images/3-2021/c74078d0c99d66198336f250ca703086.png'),
('Ostali Topijarni oblici', 5, 'images/3-2021/d2740e7bfc93dbdc78ef0f0d592fcdc.png');


-- Ukrasne trave i perene
INSERT INTO `category` (`name`, `parent_category_id`, `primary_image_url`) VALUES
('Bambusi', 6, 'images/3-2021/91c54626f75c840468270375658202ce.png'),
('Ukrasne trave', 6, 'images/3-2021/11707358adff0c7263f6bb37282e9133.png'),
('Perene', 6, 'images/3-2021/11707358adff0c7263f6bb37282e9133.png');



-- Specijalna selekcija
INSERT INTO `category` (`name`, `parent_category_id`, `primary_image_url`) VALUES
('Starinske sorte voća', 7, 'images/3-2021/3ddfa32b66b6ff854b02cde25ab4dd25.png'),
('Stubasto voće', 7, 'images/3-2021/3ddfa32b66b6ff854b02cde25ab4dd25.png'),
('Citrusi', 7, 'images/3-2021/2eb5e9f890aa4a16be6d7450d8860bd.png'),
('Patuljasto voće', 7, 'images/3-2021/61ed5d9f52500c5f6f0587ab66fc21bf.png');


-- Klasično voće
INSERT INTO `category` (`name`, `parent_category_id`, `primary_image_url`) VALUES
('Jabuke', 8, 'images/3-2021/c93284f301b6c0b99d458eb24e913e43.png'),
('Krušle', 8, 'images/3-2021/3db65902a34e0236b830f06186d6492b.png'),
('Trešnje i Višnje', 8, 'images/3-2021/413d7693c236096dd6628dfe0570afaa.png'),
('Breskve i Kajsije', 8, 'images/3-2021/863392cd413fde2e573ad01d40263796.png'),
('Šljive', 8, 'images/3-2021/af6e2506c58661c542dace894be98c13.png'),
('Orah i lešnik', 8, 'images/3-2021/8edbe4e9bb911e97ae1c7228af4ed1e1.png'),
('Dunja i Džanarika', 8, 'images/3-2021/7b776f3658639e69b5c95c0085786c59.png');


-- Bobičasto voće
INSERT INTO `category` (`name`, `parent_category_id`, `primary_image_url`) VALUES
('Vinova loza', 9, 'images/3-2021/4d583d3950236839c79d21daf95bd02d.png'),
('Jagode', 9, 'images/3-2021/7da7a645e12076449f6710b1e25516a7.png'),
('Ogrozd', 9, 'images/3-2021/f9b3b9c293a2a488a3d03b511a709fda.png'),
('Ribizla', 9, 'images/3-2021/75764f2187832443984ded978b139c8.png'),
('Borovnica i brusnica', 9, 'images/3-2021/d38b50cd1d320536b7020bc61d0c7943.png'),
('Aronija i Godži', 9, 'images/3-2021/e774ffbd7dbd090d2521c7798863bd5e.png'),
('Kupina i Malina', 9, 'images/3-2021/9ba1061713fc8ceac5033ff2e89fd0f6.png');