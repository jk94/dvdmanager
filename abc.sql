/*
Navicat SQLite Data Transfer

Source Server         : dvdverleih
Source Server Version : 30714
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30714
File Encoding         : 65001

Date: 2014-09-16 18:12:39
*/

-- ----------------------------
-- Table structure for tbl_account_model
-- ----------------------------
DROP TABLE IF EXISTS `tbl_account_model`;
CREATE TABLE `tbl_account_model` (
`KUM_ID`  int NOT NULL,
`name`  varchar(255),
PRIMARY KEY (`KUM_ID`)
);

-- ----------------------------
-- Records of tbl_account_model
-- ----------------------------
INSERT INTO `tbl_account_model` VALUES (1, 'Normal');
INSERT INTO `tbl_account_model` VALUES (2, 'Premium');
INSERT INTO `tbl_account_model` VALUES (3, 'Abo 2000');
-- ----------------------------
-- Table structure for tbl_ausleihe
-- ----------------------------
DROP TABLE IF EXISTS `tbl_ausleihe`;
CREATE TABLE `tbl_ausleihe` (
`AUS_ID`  int PRIMARY KEY AUTO_INCREMENT NOT NULL,
`KU_ID`  int NOT NULL,
`DVD_ID`  int NOT NULL,
`begindate`  int NOT NULL,
`enddate`  int NOT NULL,
`returned`  int
);

-- ----------------------------
-- Records of tbl_ausleihe
-- ----------------------------
INSERT INTO `tbl_ausleihe` VALUES (1, 1, 1, 1410106018, 1410365236, 0);

-- ----------------------------
-- Table structure for tbl_charts
-- ----------------------------
DROP TABLE IF EXISTS `tbl_charts`;
CREATE TABLE `tbl_charts` (
`CH_ID`  int PRIMARY KEY AUTO_INCREMENT NOT NULL,
`FI_ID`  int NOT NULL,
`addDate`  int NOT NULL,
`added_by`  int NOT NULL,
`enabled`  int NOT NULL DEFAULT 1
);

-- ----------------------------
-- Records of tbl_charts
-- ----------------------------
INSERT INTO `tbl_charts` VALUES (2, 1, 361631565, 1, 1);

-- ----------------------------
-- Table structure for tbl_dvd
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dvd`;
CREATE TABLE `tbl_dvd` (
`DVD_ID`  int PRIMARY KEY AUTO_INCREMENT NOT NULL,
`art_nr`  varchar(10) NOT NULL,
`FI_ID`  int NOT NULL,
`lent`  int NOT NULL,
`notice`  varchar(255),
`last_edit_by`  int,
`last_edit`  int
);

-- ----------------------------
-- Records of tbl_dvd
-- ----------------------------
INSERT INTO `tbl_dvd` VALUES (1, 1235, 1, 0, '-', null, null);

-- ----------------------------
-- Table structure for tbl_film
-- ----------------------------
DROP TABLE IF EXISTS `tbl_film`;
CREATE TABLE `tbl_film` (
`FI_ID`  int PRIMARY KEY AUTO_INCREMENT NOT NULL,
`title`  varchar(80) NOT NULL,
`subtitle`  varchar(80),
`desc`  varchar(700),
`fsk`  int,
`rating`  varchar(7),
`cover`  varchar(255),
`trailer`  varchar(255),
`actor`  varchar(255),
`regie`  varchar(127),
`release_date`  date,
`duration`  int,
`awards`  varchar(255),
`preis`  double,
`last_edit_by`  int,
`last_edit`  int
);

-- ----------------------------
-- Records of tbl_film
-- ----------------------------
INSERT INTO `tbl_film` VALUES (1, 'Forrest Gump', '', 'Forrest Gump ist eine US-amerikanische Literaturverfilmung des gleichnamigen Romans von Winston Groom unter der Regie von Robert Zemeckis aus dem Jahr 1994. Für die Darstellung der Figur Forrest Gump wurde Tom Hanks mit einem Oscar als bester Hauptdarsteller ausgezeichnet. Dem Film wurden insgesamt sechs Oscars und drei Golden Globes verliehen. Der Film war in den USA im Kinojahr 1994 der Film mit dem höchsten Einspielergebnis von über 329 Millionen US-Dollar, dem ein geschätztes Budget von rund 55 Millionen US-Dollar gegenübersteht. In Deutschland kam er 1994 auf 7,6 Millionen Besucher und war damit der erfolgreichste Film des Jahres. Über den Filmverleih wurden allein in Nordamerika weitere 156 Millionen US-Dollar eingespielt.', 3, 88, 'forestgump.png', 'http://www.imdb.com/video/screenplay/vi3567517977/?ref_=tt_ov_vi', 'Tom Hanks; Robin Wright; Gary Sinise', 'Robert Zemeckis', '06071994', 142, 'Best Picture', 0.5, 1, null);
INSERT INTO `tbl_film` VALUES (2, 3, null, 'fadsfasfasdf', 3, 70, 'adlkjfds.png', 'adlskfjadslkfjadslf', 'alsdkf; dsflkajdflkad; dslkfa', 'jadlkfdslf', 456425651, 154, 'dafdasfadfj', 0.5, 1, null);
INSERT INTO `tbl_film` VALUES (3, 'Star Wars: Episode I', 'Die dunkle Bedrohung', 'Two Jedi Knights escape a hostile blockade to find allies and come across a young boy who may bring balance to the Force, but the long dormant Sith resurface to reclaim their old glory.', 2, 66, null, 'http://www.youtube.com/watch?v=bI24citu_uY', 'Ewan McGregor, Liam Neeson, Natalie Portman', 'George Lucas', 934927200000, 136, null, 0.5, null, null);
INSERT INTO `tbl_film` VALUES (4, 'Star Wars: Episode II', 'Angriff der Klonkrieger', 'Ten years after initially meeting, Anakin Skywalker shares a forbidden romance with Padmé, while Obi-Wan investigates an assassination attempt on the Senator and discovers a secret clone army crafted for the Jedi.', 3, 68, null, 'http://www.youtube.com/watch?v=CO2OLQ2kiq8', 'Hayden Christensen, Natalie Portman, Ewan McGregor', 'George Lucas', 1021413600000, 142, null, 0.5, null, null);
INSERT INTO `tbl_film` VALUES (5, 'Der Herr der Ringe', 'Die Gefährten', 'A meek hobbit of the Shire and eight companions set out on a journey to Mount Doom to destroy the One Ring and the dark lord Sauron.', 4, 88, null, 'http://www.youtube.com/watch?v=dysvOlKkZYE', 'Elijah Wood, Ian McKellen, Orlando Bloom', 'Peter Jackson', 1008630000000, 178, null, 0.5, null, null);
INSERT INTO `tbl_film` VALUES (6, 'Der Herr der Ringe', 'Die zwei Türme', 'While Frodo and Sam edge closer to Mordor with the help of the shifty Gollum, the divided fellowship makes a stand against Sauron''s new ally, Saruman, and his hordes of Isengard.', 3, 88, null, 'http://www.youtube.com/watch?v=YpvecCQ-GA4', 'Elijah Wood, Ian McKellen, Viggo Mortensen', 'Peter Jackson', 1040079600000, 179, null, 0.5, null, null);
INSERT INTO `tbl_film` VALUES (7, 'Der Herr der Ringe', 'Die Rückkehr des Königs', 'Gandalf and Aragorn lead the World of Men against Sauron''s army to draw his gaze from Frodo and Sam as they approach Mount Doom with the One Ring.', 3, 89, null, 'http://www.youtube.com/watch?v=qPgYw2vKq8o', 'Elijah Wood, Viggo Mortensen, Ian McKellen', 'Peter Jackson', 1071529200000, 201, null, 0.5, null, null);
INSERT INTO `tbl_film` VALUES (8, 'Twins - Zwillinge', null, 'A physically perfect but innocent man goes in search of his long-lost twin brother, who is a short small-time crook.', 3, 59, null, 'http://www.youtube.com/watch?v=Zr6tGoWSxJM', 'Arnold Schwarzenegger, Danny DeVito, Kelly Preston', 'Ivan Reitman', 606524400000, 107, null, 0.5, null, null);
INSERT INTO `tbl_film` VALUES (9, 'Crank', null, 'Professional assassin Chev Chelios learns his rival has injected him with a poison that will kill him if his heart rate drops.', 5, 70, null, 'http://www.imdb.com/video/screenplay/vi3364028953/?ref_=tt_ov_vi', 'Jason Statham, Amy Smart, Carlos Sanz', 'Mark Neveldine,  Brian Taylor', 1158703200000, 88, null, 0.5, null, null);
INSERT INTO `tbl_film` VALUES (10, 'Crank 2', 'High Voltage', 'Chelios faces a Chinese mobster who has stolen his nearly indestructible heart and replaced it with a battery-powered ticker that requires regular jolts of electricity to keep working.', 5, 62, null, 'http://www.youtube.com/watch?v=HPJKHwCXcOM', 'Jason Statham, Amy Smart, Clifton Collins Jr.', 'Mark Neveldine,  Brian Taylor', 1239746400000, 96, null, 0.5, null, null);
INSERT INTO `tbl_film` VALUES (11, 'Madagascar ', null, 'Spoiled by their upbringing with no idea what wild life is really like, four animals from New York Central Zoo escape, unwittingly assisted by four absconding penguins, and find themselves in Madagascar, among a bunch of merry lemurs', 1, 69, null, 'http://www.imdb.com/video/screenplay/vi2392260889/?ref_=tt_ov_vi', 'Chris Rock, Ben Stiller, David Schwimmer', 'Eric Darnell, Tom McGrath', 1118613600000, 86, null, 0.5, null, null);
INSERT INTO `tbl_film` VALUES (12, 'Inglourious Basterds', null, 'In Nazi-occupied France during World War II, a plan to assassinate Nazi leaders by a group of Jewish U.S. soldiers coincides with a theatre owner''s vengeful plans for the same.', 4, 83, null, 'http://www.imdb.com/video/imdb/vi3738173977/?ref_=tt_ov_vi', 'Brad Pitt, Diane Kruger, Eli Roth', 'Quentin Tarantino, Eli Roth', 1250632800000, 153, null, 0.5, null, null);

-- ----------------------------
-- Table structure for tbl_fsk
-- ----------------------------
DROP TABLE IF EXISTS `tbl_fsk`;
CREATE TABLE `tbl_fsk` (
`FSK_ID`  int PRIMARY KEY AUTO_INCREMENT NOT NULL,
`bez`  varchar(255),
`age`  int
);

-- ----------------------------
-- Records of tbl_fsk
-- ----------------------------
INSERT INTO `tbl_fsk` VALUES (1, 'keine FSK', 0);
INSERT INTO `tbl_fsk` VALUES (2, 'FSK 6', 6);
INSERT INTO `tbl_fsk` VALUES (3, 'FSK 12', 12);
INSERT INTO `tbl_fsk` VALUES (4, 'FSK 16', 16);
INSERT INTO `tbl_fsk` VALUES (5, 'FSK 18', 18);

-- ----------------------------
-- Table structure for tbl_genre
-- ----------------------------
DROP TABLE IF EXISTS `tbl_genre`;
CREATE TABLE `tbl_genre` (
`GE_ID`  int PRIMARY KEY AUTO_INCREMENT NOT NULL,
`name`  varchar(80)
);

-- ----------------------------
-- Records of tbl_genre
-- ----------------------------
INSERT INTO `tbl_genre` VALUES (1, 'Romanze');
INSERT INTO `tbl_genre` VALUES (2, 'Drama');
INSERT INTO `tbl_genre` VALUES (3, 'Action');
INSERT INTO `tbl_genre` VALUES (4, 'Thriller');
INSERT INTO `tbl_genre` VALUES (5, 'Komödie');

-- ----------------------------
-- Table structure for tbl_genre_zuordnung
-- ----------------------------
DROP TABLE IF EXISTS `tbl_genre_zuordnung`;
CREATE TABLE `tbl_genre_zuordnung` (
`GEZ_ID`  int NOT NULL,
`FI_ID`  int NOT NULL,
`GE_ID`  int NOT NULL,
PRIMARY KEY (`GEZ_ID` ASC)
);

-- ----------------------------
-- Records of tbl_genre_zuordnung
-- ----------------------------
INSERT INTO `tbl_genre_zuordnung` VALUES (1, 1, 1);
INSERT INTO `tbl_genre_zuordnung` VALUES (2, 1, 2);
INSERT INTO `tbl_genre_zuordnung` VALUES (3, 2, 5);

-- ----------------------------
-- Table structure for tbl_kunde
-- ----------------------------
DROP TABLE IF EXISTS `tbl_kunde`;
CREATE TABLE `tbl_kunde` (
`KU_ID`  int NOT NULL,
`U_ID`  int,
`date_of_joining`  int,
`account_balance`  double,
`account_model`  int,
PRIMARY KEY (`KU_ID` ASC)
);

-- ----------------------------
-- Records of tbl_kunde
-- ----------------------------
INSERT INTO `tbl_kunde` VALUES (1, 1, 100000000, '12,00', 1);

-- ----------------------------
-- Table structure for tbl_mitarbeiter
-- ----------------------------
DROP TABLE IF EXISTS `tbl_mitarbeiter`;
CREATE TABLE `tbl_mitarbeiter` (
`MA_ID`  int PRIMARY KEY AUTO_INCREMENT NOT NULL,
`U_ID`  int NOT NULL,
`permission`  int NOT NULL
);

-- ----------------------------
-- Records of tbl_mitarbeiter
-- ----------------------------
INSERT INTO `tbl_mitarbeiter` VALUES (1, 1, 3);

-- ----------------------------
-- Table structure for tbl_permissions
-- ----------------------------
DROP TABLE IF EXISTS `tbl_permissions`;
CREATE TABLE `tbl_permissions` (
`PER_ID`  int NOT NULL,
`bez`  varchar(255),
PRIMARY KEY (`PER_ID`)
);

-- ----------------------------
-- Records of tbl_permissions
-- ----------------------------
INSERT INTO `tbl_permissions` VALUES (0, 'Not Logged In');
INSERT INTO `tbl_permissions` VALUES (1, 'Logged In');
INSERT INTO `tbl_permissions` VALUES (2, 'Mitarbeiter');
INSERT INTO `tbl_permissions` VALUES (3, 'Administrator');

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
`U_ID`  int PRIMARY KEY AUTO_INCREMENT NOT NULL,
`name`  varchar(255) NOT NULL,
`surname`  varchar(255) NOT NULL,
`street`  varchar(255) NOT NULL,
`street_nr`  int NOT NULL,
`zip_code`  varchar(255) NOT NULL,
`location`  varchar(255) NOT NULL,
`password`  varchar(255) NOT NULL,
`birthdate`  int NOT NULL,
`email`  varchar(255) NOT NULL,
`accountnumber`  varchar(10)
);

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES (1, 'Koschke', 'Jan', 'Erbastraße', 26, 70736, 'Fellbach', 'start123', 767484000000, 'jankoschke@googlemail.com', '0000000027');

-- ----------------------------
-- Indexes structure for table tbl_film
-- ----------------------------
CREATE UNIQUE INDEX `FilmID`
ON `tbl_film` (`FI_ID` ASC);

-- ----------------------------
-- Indexes structure for table tbl_user
-- ----------------------------
CREATE UNIQUE INDEX `Accountnr`
ON `tbl_user` (`accountnumber` ASC);
CREATE UNIQUE INDEX `Email`
ON `tbl_user` (`email` ASC);
