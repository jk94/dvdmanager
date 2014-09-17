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