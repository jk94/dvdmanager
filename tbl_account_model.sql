/*
Navicat SQLite Data Transfer

Source Server         : dvdverleih
Source Server Version : 30714
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30714
File Encoding         : 65001

Date: 2014-09-12 23:25:12
*/

PRAGMA foreign_keys = OFF;

-- ----------------------------
-- Table structure for tbl_account_model
-- ----------------------------
DROP TABLE IF EXISTS "main"."tbl_account_model";
CREATE TABLE "tbl_account_model" (
"KUM_ID"  INTEGER NOT NULL,
"name"  TEXT,
PRIMARY KEY ("KUM_ID")
);

-- ----------------------------
-- Records of tbl_account_model
-- ----------------------------
INSERT INTO "main"."tbl_account_model" VALUES (1, 'Normal');
INSERT INTO "main"."tbl_account_model" VALUES (2, 'Premium');
INSERT INTO "main"."tbl_account_model" VALUES (3, 'Abo 2000');
