CREATE TABLE `MOVIE_EPISODE_POSTER` (
  `posterId` int(11) NOT NULL,
  `posterDescription` varchar(255) DEFAULT NULL,
  `posterTags` varchar(255) DEFAULT NULL,
  `posterUrl` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`posterId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `MOVIE_EPISODE_REQUEST` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `airDate` varchar(45) DEFAULT NULL,
  `releaseYear` varchar(45) DEFAULT NULL,
  `posterUrlId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `posterUrlId` FOREIGN KEY (`id`) REFERENCES `MOVIE_EPISODE_POSTER` (`posterId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
