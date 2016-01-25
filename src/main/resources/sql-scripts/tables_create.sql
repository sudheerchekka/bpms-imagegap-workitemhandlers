CREATE TABLE `MOVIE_EPISODE_POSTER` (
  `posterId` int(11) NOT NULL AUTO_INCREMENT,
  `posterDescription` varchar(255) DEFAULT NULL,
  `posterTags` varchar(255) DEFAULT NULL,
  `posterUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`posterId`),
  KEY `idx_MOVIE_EPISODE_POSTER_posterId` (`posterId`)
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8;

CREATE TABLE `MOVIE_EPISODE_REQUEST` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `airDate` varchar(45) DEFAULT NULL,
  `releaseYear` varchar(45) DEFAULT NULL,
  `posterId` int(11) DEFAULT NULL,
  `country` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_MOVIE_EPISODE_REQUEST_id` (`id`),
  KEY `idx_MOVIE_EPISODE_REQUEST_posterUrlId` (`posterId`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
select * from MOVIE_EPISODE_REQUEST

