-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jul 24, 2023 at 12:35 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `shopmedb`
--

-- --------------------------------------------------------

--
-- Table structure for table `brands`
--

CREATE TABLE `brands` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `image` varchar(128) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `brands`
--

INSERT INTO `brands` (`id`, `name`, `image`) VALUES
(1, 'Canon', 'Canon.png'),
(2, 'Fujifilm', 'Fujifilm.png'),
(3, 'Sony', 'Sony.png'),
(4, 'HP', 'HP.png'),
(5, 'SanDisk', 'SanDisk.png'),
(6, 'Western Digital', 'Western Digital.png'),
(7, 'Panasonic', 'Panasonic.png'),
(8, 'Pelican', 'Pelican.png'),
(9, 'Apple', 'Apple.png'),
(10, 'Samsung', 'Samsung.png'),
(11, 'Olympus', 'Olympus.png'),
(12, 'CADeN', 'Caden.png'),
(13, 'AmazonBasics', 'amazonbasics.png'),
(14, 'Nikon', 'Nikon.png'),
(15, 'Neewer', 'Neewer.png'),
(16, 'Sigma', 'Sigma.png'),
(17, 'Bosch', 'Bosch.png'),
(18, 'Joby', 'Joby.png'),
(19, 'GoPro', 'GoPro.png'),
(20, 'Manfrotto', 'Manfrotto.png'),
(21, 'Google', 'Google.png'),
(22, 'LG', 'LG.png'),
(23, 'Motorola', 'Motorola.png'),
(24, 'Pantech', 'Pantech.png'),
(25, 'Huawei', 'Huawei.png'),
(26, 'Xiaomi', 'Xiaomi.png'),
(27, 'HOVAMP', 'Hovamp.png'),
(28, 'Aioneus', 'Aioneus.png'),
(29, 'XIAE', 'XIAE.png'),
(30, 'Everyworth', 'Everyworth.png'),
(31, 'Lexar', 'Lexar.png'),
(32, 'Nulaxy', 'Nulaxy.png'),
(33, 'Fitfort', 'Fitfort.png'),
(34, 'PopSockets', 'PopSocket.png'),
(35, 'SHAWE', 'SHAWE.png'),
(36, 'Lenovo', 'Lenovo.png'),
(37, 'Acer', 'Acer.png'),
(38, 'Dell', 'Dell.png'),
(39, 'Intel', 'Intel.png'),
(40, 'ASUS', 'ASUS.png'),
(41, 'Microsoft', 'Microsoft.png'),
(42, 'DragonTouch', 'DragonTouch.png'),
(43, 'AMD', 'AMD.png'),
(44, 'XFX', 'XFX.png'),
(45, 'MSI', 'MSI.png'),
(46, 'Seagate', 'Seagate.png'),
(47, 'Cosair', 'Corsair.png'),
(48, 'Thermaltake', 'Thermaltake.png'),
(49, 'Kingston', 'Kingston.png'),
(50, 'Creative', 'Creative.png'),
(51, 'Crucial', 'Crucial.png'),
(52, 'HyperX', 'HyperX.png'),
(53, 'Gigabyte', 'Gigabyte.png'),
(54, 'TP-Link', 'TP-Link.png');

-- --------------------------------------------------------

--
-- Table structure for table `brands_categories`
--

CREATE TABLE `brands_categories` (
  `brand_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `brands_categories`
--

INSERT INTO `brands_categories` (`brand_id`, `category_id`) VALUES
(1, 2),
(1, 10),
(1, 11),
(1, 12),
(2, 2),
(3, 2),
(3, 10),
(3, 11),
(4, 5),
(4, 6),
(5, 18),
(5, 24),
(5, 27),
(5, 29),
(5, 31),
(6, 1),
(6, 24),
(6, 27),
(6, 29),
(7, 2),
(7, 10),
(8, 2),
(8, 9),
(9, 4),
(9, 5),
(9, 6),
(9, 7),
(9, 14),
(9, 15),
(10, 5),
(10, 6),
(10, 7),
(10, 14),
(10, 15),
(10, 18),
(10, 24),
(10, 25),
(10, 27),
(10, 29),
(11, 10),
(12, 9),
(13, 9),
(14, 10),
(14, 11),
(14, 12),
(15, 11),
(16, 12),
(17, 13),
(18, 13),
(19, 13),
(20, 13),
(20, 19),
(21, 14),
(21, 15),
(22, 14),
(22, 25),
(23, 14),
(23, 15),
(24, 14),
(25, 15),
(26, 15),
(27, 17),
(28, 17),
(29, 17),
(30, 17),
(31, 18),
(32, 19),
(33, 19),
(34, 19),
(35, 19),
(36, 5),
(36, 6),
(36, 7),
(37, 5),
(37, 6),
(37, 7),
(38, 5),
(38, 6),
(38, 7),
(39, 5),
(39, 22),
(40, 5),
(40, 6),
(40, 7),
(40, 25),
(40, 26),
(40, 28),
(40, 30),
(41, 7),
(42, 7),
(43, 22),
(43, 23),
(44, 23),
(45, 23),
(45, 30),
(46, 24),
(47, 26),
(47, 29),
(48, 26),
(49, 27),
(49, 29),
(50, 28),
(51, 29),
(52, 29),
(53, 30),
(54, 31);

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(128) NOT NULL,
  `alias` varchar(64) NOT NULL,
  `image` varchar(128) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `all_parent_ids` varchar(256) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`, `alias`, `image`, `enabled`, `parent_id`, `all_parent_ids`) VALUES
(1, 'Electronics', 'electronics', 'electronics.png', b'1', NULL, NULL),
(2, 'Camera & Photo', 'camera', 'camera.jpg', b'1', 1, NULL),
(3, 'Computers', 'computers', 'computers.png', b'1', 1, NULL),
(4, 'Cell Phones & Accessories', 'cellphones', 'cellphones.png', b'1', 1, NULL),
(5, 'Desktops', 'desktop_computers', 'desktop computers.png', b'1', 3, NULL),
(6, 'Laptops', 'laptop_computers', 'laptop computers.png', b'1', 3, NULL),
(7, 'Tablets', 'tablet_computers', 'tablets.png', b'1', 3, NULL),
(8, 'Computer Components', 'computer_components', 'computer components.png', b'1', 3, NULL),
(9, 'Bags & Cases', 'camera_bags_cases', 'bags_cases.png', b'1', 2, NULL),
(10, 'Digital Cameras', 'digital_cameras', 'digital cameras.png', b'1', 2, NULL),
(11, 'Flashes', 'camera_flashes', 'flashes.png', b'1', 2, NULL),
(12, 'Lenses', 'camera_lenses', 'lenses.png', b'1', 2, NULL),
(13, 'Tripods & Monopods', 'camera_tripods_monopods', 'tripods_monopods.png', b'1', 2, NULL),
(14, 'Carrier Cell Phones', 'carrier_cellphones', 'carrier cellphones.png', b'1', 4, NULL),
(15, 'Unlocked Cell Phones', 'unlocked_cellphones', 'unlocked cellphones.png', b'1', 4, NULL),
(16, 'Accessories', 'cellphone_accessories', 'cellphone accessories.png', b'1', 4, NULL),
(17, 'Cables & Adapters', 'cellphone_cables_adapters', 'cables and adapters.png', b'1', 16, NULL),
(18, 'MicroSD Cards', 'microsd_cards', 'microsd cards.png', b'1', 16, NULL),
(19, 'Stands', 'cellphone_stands', 'cellphone_stands.png', b'1', 16, NULL),
(20, 'Cases', 'cellphone_cases', 'cellphone cases.png', b'1', 16, NULL),
(21, 'Headphones', 'headphones', 'headphones.png', b'1', 16, NULL),
(22, 'CPU Processors Unit', 'computer_processors', 'computer processors.png', b'1', 8, NULL),
(23, 'Graphic Cards', 'computer_graphic_cards', 'graphic cards.png', b'1', 8, NULL),
(24, 'Internal Hard Drives', 'hard_drive', 'internal hard drive.png', b'1', 8, NULL),
(25, 'Internal Optical Drives', 'computer_optical_drives', 'internal optical drives.png', b'1', 8, NULL),
(26, 'Power Supplies', 'computer_power_supplies', 'power supplies.png', b'1', 8, NULL),
(27, 'Solid State Drives', 'solid_state_drives', 'solid state drives.png', b'1', 8, NULL),
(28, 'Sound Cards', 'computer_sound_cards', 'sound cards.png', b'1', 8, NULL),
(29, 'Memory', 'computer_memory', 'computer memory.png', b'1', 8, NULL),
(30, 'Motherboard', 'computer_motherboard', 'motherboards.png', b'1', 8, NULL),
(31, 'Network Cards', 'computer_network_cards', 'network cards.png', b'1', 8, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(256) NOT NULL,
  `alias` varchar(256) NOT NULL,
  `short_description` varchar(512) NOT NULL,
  `full_description` varchar(4096) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `in_stock` bit(1) DEFAULT NULL,
  `cost` float NOT NULL,
  `price` float NOT NULL,
  `discount_percent` float DEFAULT NULL,
  `length` float NOT NULL,
  `weight` float NOT NULL,
  `height` float NOT NULL,
  `width` float NOT NULL,
  `category_id` int(11) DEFAULT NULL,
  `brand_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(40) NOT NULL,
  `description` varchar(150) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`, `description`) VALUES
(1, 'Admin', 'manage everything'),
(2, 'Salesperson', 'manage product price, customers, shippin'),
(3, 'Editor', 'manage categories, brands, products, art'),
(4, 'Shipper', 'view products, view orders and update or'),
(5, 'Assistant', 'manage questions and reviews');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `email` varchar(128) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `password` varchar(64) NOT NULL,
  `image` varchar(128) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `password`, `image`) VALUES
(1, 'nam@codejava.net', b'1', 'Nam', 'Ha Minh', '$2a$10$bDqskP9Z/y6BIZnFLgJ8HuwMYaZXD9w2jVk2pYHXgn1k6N4nckleu', 'namhm.png'),
(2, 'kanna.allada@gmail.com', b'1', 'Allada', 'Pavan', '$2a$10$zRa/rmQ8JarpYG2bNKftyelKnsUhsHwGB.xmCKTWJClsB7O9wzTnG', 'Allada Pavan.png'),
(3, 'aecllc.bnk@gmail.com', b'1', 'Bruce', 'Kitchell', '$2a$10$GINThwCjVZAbGnmOe9BIeuDuvDlyfuwZrg/Rsmrjs1Lsq2pnXtO/S', 'Bruce Kitchell.png'),
(4, 'muhammad.evran13@gmail.com', b'1', 'Muhammad', 'Evran', '$2a$10$UcHWHC72azPVZJb5Ky.Yy.X695WGf1ZkkGMS3WL3B9WqWf2dQD04G', 'Muhammad Evran.png'),
(5, 'kent.hervey8@gmail.com', b'1', 'Kent', 'Hervey', '$2a$10$YHXRsZ07/Btv.qCgGht.7u2PW.GLWzpVB7eabfgH1mhTVVXffDT6K', 'KentHervey.png'),
(6, 'alfredephraim26@gmail.com', b'1', 'Alfred', 'Ephraim', '$2a$10$1jl3q3r/Fh9ZBv6ziM4XhuxCi2GMFWcfHUrxsesXAEwnsiV/NJKbq', 'Alfred.png'),
(7, 'nathihsa@gmail.com', b'1', 'Nathi', 'Sangweni', '$2a$10$WyHmQiXCSYuHcGeg8eFWvOScqzSgg88MmqpajPdzSkLsvZjT3tKC.', 'Nathi_Sangweni.png'),
(8, 'ammanollashirisha2020@gmail.com', b'1', 'Ammanolla', 'Shirisha', '$2a$10$N1eE87eXFB2XQ5nmWKaTXOofnrPn8koeNvZhEpleJzO49i55e/Vk.', 'Ammanolla.png'),
(9, 'bfeeny238@hotmail.com', b'1', 'Bill', 'Feeny', '$2a$10$3sH0v..zpjwA8ux5/q.OAeu0HgmSdMj8VzMWzhwwBDkE8wOISsUyi', 'Bill Feeny.png'),
(10, 'redsantosph@gmail.com', b'1', 'Frederick', 'delos Santos', '$2a$10$KXHmKpE6YB/0sjiy3fkMv.muKyxqvOXE6jVeaPu8KEaExx62ZmmNe', 'Frederick Santos.png'),
(11, 'ro_anamaria@mail.ru', b'1', 'Ana', 'Maria', '$2a$10$sz0CHOHAY1Xjt2ajIZgnG.L2KBQ4SsQkOGsPYue.C5gr6j.KMDdqS', 'Anna Maria.jpg'),
(12, 'maytassatya@hotmail.com', b'1', 'Satya', 'Narayana', '$2a$10$R7EJcaYijjJo/IVk6c1CieBML2uP3RAKMVlCxylPAePlCfJsX7OOy', 'Satya Narayana.png'),
(13, 'matthewefoli@gmail.com', b'1', 'Matthew', 'Efoli', '$2a$10$ECNnxXSVArnwS9KCet3yguQ1qHKyBIhh2G8c4F9CYgvp/Hadl8OS6', 'Matthew.png'),
(14, 'davekumara2@gmail.com', b'1', 'Dave', 'Kumar', '$2a$10$5ebeZu18V5RheieYqpl/LORCN41E3H7yvxKqEwtq5Zq2JVw.E9dva', 'Dave Kumar.png'),
(15, 'jackkbruce@yahoo.com', b'1', 'Jack', 'Bruce', '$2a$10$a6iyIHRj8DNpu15obVHTSOGcLe4IfpBcD4iEEJesWaFpBQvRoF2da', 'Jack Bruce.png'),
(16, 'zirri.mohamed@gmail.com', b'1', 'Mohamed', 'Zirri', '$2a$10$TmvyH1AoyDqRmQ4uC8NAZOOV29CPEDGuxVsHLP1cJoHQGr78L4kjW', 'Mohamed Zirri.jpg'),
(17, 'mk.majumdar009@hotmail.com', b'1', 'Mithun', 'Kumar Majumdar', '$2a$10$Y6SEy2INN0Rk/vhLHHJUYO6IMqNW3Ar.jVe9o0W1lpBRX8xr2Itui', 'Mithun Kumar Majumdar.png'),
(18, 'aliraza.arain.28@gmail.com', b'1', 'Ali', 'Raza', '$2a$10$PISZ2KitSbhE4/Z3dtIGk.WUi2ILiDl4PzRUDEQSp5BJIxcdcPq4G', 'Ali Raza.png'),
(19, 'isaachenry2877@gmail.com', b'1', 'Isaac', 'Henry', '$2a$10$CtmhrOz/AhDoCpKbeYl8O.0ngCFMukcznNZq7.YcHrkRyKpBG8Zca', 'Isaac Henry.jpg'),
(20, 's.stasovska82@hotmail.com', b'1', 'Svetlana', 'Stasovska', '$2a$10$fcN2cNa7vB.78QnmzfNZEeUBkrwUaM.bVK3iDB.KFQlR15DwL7QZy', 'Svetlana Stasovska.png'),
(21, 'mikegates2012@gmail.com', b'1', 'Mike', 'Gates', '$2a$10$zIO1tygsw6cB2ymiR.WX0ulr9NKdTlZHqu7w/W/LLwk8HhK7nVnH.', 'Mike Gates.png'),
(22, 'pedroquintero67@gmail.com', b'1', 'Pedro', 'Quintero', '$2a$10$UPX5EwZw0MyBvbe.7mxg2u8GOl/4KgaUU40iSjr1PLFYvhu35fMmu', 'Pedro Quintero.png'),
(23, 'amina.elshal2@yahoo.com', b'1', 'Amina', 'Elshal', '$2a$10$J1yoyqG5vWNe5N663PkgY.h53gfJtTR7Bb8E8u3sXdNbOZxhXgHu.', 'Amina Elshal.png'),
(102, 'ixazj@shopme.com', b'1', 'Admin', 'Admin', '$2a$10$n.ey43cgxYx3vvFOn/8A8uVX9ixzA01GIc4nEr5ynEhLkGYKJbrAy', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users_roles`
--

CREATE TABLE `users_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users_roles`
--

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3),
(5, 3),
(5, 5),
(6, 3),
(7, 3),
(8, 3),
(9, 2),
(10, 2),
(11, 2),
(11, 3),
(12, 2),
(13, 2),
(14, 4),
(14, 5),
(15, 3),
(15, 4),
(16, 4),
(17, 4),
(18, 3),
(18, 4),
(19, 2),
(19, 5),
(20, 2),
(20, 3),
(20, 5),
(21, 5),
(22, 5),
(23, 5),
(102, 1),
(102, 2),
(102, 3);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `brands`
--
ALTER TABLE `brands`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_oce3937d2f4mpfqrycbr0l93m` (`name`);

--
-- Indexes for table `brands_categories`
--
ALTER TABLE `brands_categories`
  ADD PRIMARY KEY (`brand_id`,`category_id`),
  ADD KEY `FK6x68tjj3eay19skqlhn7ls6ai` (`category_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKsaok720gsu4u2wrgbk10b5n8d` (`parent_id`);

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_8qwq8q3hk7cxkp9gruxupnif5` (`alias`),
  ADD UNIQUE KEY `UK_o61fmio5yukmmiqgnxf8pnavn` (`name`),
  ADD KEY `FKa3a4mpsfdf4d2y6r8ra3sc8mv` (`brand_id`),
  ADD KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ofx66keruapi6vyqpv6f2or37` (`name`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- Indexes for table `users_roles`
--
ALTER TABLE `users_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `brands`
--
ALTER TABLE `brands`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=55;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=47;

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `brands_categories`
--
ALTER TABLE `brands_categories`
  ADD CONSTRAINT `FK58ksmicdguvu4d7b6yglgqvxo` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  ADD CONSTRAINT `FK6x68tjj3eay19skqlhn7ls6ai` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `categories`
--
ALTER TABLE `categories`
  ADD CONSTRAINT `FKsaok720gsu4u2wrgbk10b5n8d` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `FKa3a4mpsfdf4d2y6r8ra3sc8mv` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  ADD CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

--
-- Constraints for table `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
