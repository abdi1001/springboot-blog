-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: myblog
-- ------------------------------------------------------
-- Server version	8.0.29

--
-- Table structure for table `my_user`
--

CREATE TABLE `my_user` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `email` varchar(255) DEFAULT NULL,
                           `is_account_non_expired` bit(1) NOT NULL,
                           `is_account_non_locked` bit(1) NOT NULL,
                           `is_credentials_non_expired` bit(1) NOT NULL,
                           `is_enabled` bit(1) NOT NULL,
                           `name` varchar(255) DEFAULT NULL,
                           `password` varchar(255) DEFAULT NULL,
                           `username` varchar(255) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `post`
--

CREATE TABLE `post` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `body` varchar(255) DEFAULT NULL,
                        `title` varchar(255) DEFAULT NULL,
                        `my_user_id` bigint NOT NULL,
                        PRIMARY KEY (`id`),
                        KEY `fk_post_my_user1_idx` (`my_user_id`),
                        CONSTRAINT `fk_post_my_user1` FOREIGN KEY (`my_user_id`) REFERENCES `my_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `comment`
--

CREATE TABLE `comment` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `comment` varchar(255) DEFAULT NULL,
                           `my_user_id` bigint NOT NULL,
                           `post_id` bigint NOT NULL,
                           PRIMARY KEY (`id`),
                           KEY `fk_comment_my_user1_idx` (`my_user_id`),
                           KEY `fk_comment_post1_idx` (`post_id`),
                           CONSTRAINT `fk_comment_my_user1` FOREIGN KEY (`my_user_id`) REFERENCES `my_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                           CONSTRAINT `fk_comment_post1` FOREIGN KEY (`post_id`) REFERENCES `post` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `my_roles`
--

CREATE TABLE `my_roles` (
                            `id` bigint NOT NULL AUTO_INCREMENT,
                            `name` varchar(255) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `user_role`
--

CREATE TABLE `user_role` (
                             `my_user_id` bigint NOT NULL,
                             `my_roles_id` bigint NOT NULL,
                             KEY `fk_user_role_my_user1_idx` (`my_user_id`),
                             KEY `fk_user_role_my_roles1_idx` (`my_roles_id`),
                             CONSTRAINT `fk_user_role_my_roles1` FOREIGN KEY (`my_roles_id`) REFERENCES `my_roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                             CONSTRAINT `fk_user_role_my_user1` FOREIGN KEY (`my_user_id`) REFERENCES `my_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `my_authorities`
--

CREATE TABLE `my_authorities` (
                                  `id` bigint NOT NULL AUTO_INCREMENT,
                                  `name` varchar(255) DEFAULT NULL,
                                  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `role_authorities`
--

CREATE TABLE `role_authorities` (
                                    `my_roles_id` bigint NOT NULL,
                                    `my_authorities_id` bigint NOT NULL,
                                    KEY `fk_role_authorities_my_roles1_idx` (`my_roles_id`),
                                    KEY `fk_role_authorities_my_authorities1_idx` (`my_authorities_id`),
                                    CONSTRAINT `fk_role_authorities_my_authorities1` FOREIGN KEY (`my_authorities_id`) REFERENCES `my_authorities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                    CONSTRAINT `fk_role_authorities_my_roles1` FOREIGN KEY (`my_roles_id`) REFERENCES `my_roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Dump completed on 2022-06-23  8:55:28
