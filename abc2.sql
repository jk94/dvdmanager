/*
Navicat SQLite Data Transfer

Source Server         : dvdverleih
Source Server Version : 30714
Source Host           : :0

Target Server Type    : SQLite
Target Server Version : 30714
File Encoding         : 65001

Date: 2014-09-16 19:01:11
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
-- Table structure for tbl_ausleihe
-- ----------------------------
DROP TABLE IF EXISTS "main"."tbl_ausleihe";
CREATE TABLE "tbl_ausleihe" (
"AUS_ID"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"KU_ID"  INTEGER NOT NULL,
"DVD_ID"  INTEGER NOT NULL,
"begindate"  INTEGER NOT NULL,
"enddate"  INTEGER NOT NULL,
"returned"  INTEGER,
CONSTRAINT "Kunde" FOREIGN KEY ("KU_ID") REFERENCES "tbl_user" ("KU_ID"),
CONSTRAINT "DVD" FOREIGN KEY ("DVD_ID") REFERENCES "tbl_dvd" ("DVD_ID")
);

-- ----------------------------
-- Table structure for tbl_charts
-- ----------------------------
DROP TABLE IF EXISTS "main"."tbl_charts";
CREATE TABLE "tbl_charts" (
"CH_ID"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"FI_ID"  INTEGER NOT NULL,
"addDate"  INTEGER NOT NULL,
"added_by"  INTEGER NOT NULL,
"enabled"  INTEGER NOT NULL DEFAULT 1,
CONSTRAINT "Film" FOREIGN KEY ("FI_ID") REFERENCES "tbl_film" ("FI_ID"),
CONSTRAINT "HinzugefügtVon" FOREIGN KEY ("added_by") REFERENCES "tbl_mitarbeiter" ("MA_ID")
);

-- ----------------------------
-- Table structure for tbl_dvd
-- ----------------------------
DROP TABLE IF EXISTS "main"."tbl_dvd";
CREATE TABLE "tbl_dvd" (
"DVD_ID"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"art_nr"  TEXT NOT NULL,
"FI_ID"  INTEGER NOT NULL,
"lent"  INTEGER NOT NULL,
"notice"  TEXT(255),
"last_edit_by"  INTEGER,
"last_edit"  INTEGER,
CONSTRAINT "Film" FOREIGN KEY ("FI_ID") REFERENCES "tbl_film" ("FI_ID"),
CONSTRAINT "LastEditBy" FOREIGN KEY ("last_edit_by") REFERENCES "tbl_mitarbeiter" ("MA_ID")
);

-- ----------------------------
-- Table structure for tbl_film
-- ----------------------------
DROP TABLE IF EXISTS "main"."tbl_film";
CREATE TABLE "tbl_film" (
"FI_ID"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"title"  TEXT(80) NOT NULL,
"subtitle"  TEXT(80),
"desc"  TEXT(700),
"fsk"  INTEGER,
"rating"  TEXT(7),
"cover"  TEXT,
"trailer"  TEXT(255),
"actor"  TEXT(255),
"regie"  TEXT(127),
"release_date"  BLOB,
"duration"  INTEGER,
"awards"  TEXT(255),
"preis"  REAL,
"last_edit_by"  INTEGER,
"last_edit"  INTEGER,
CONSTRAINT "FSK" FOREIGN KEY ("fsk") REFERENCES "tbl_fsk" ("FSK_ID"),
CONSTRAINT "LastEditBy" FOREIGN KEY ("last_edit_by") REFERENCES "tbl_mitarbeiter" ("MA_ID")
);

-- ----------------------------
-- Table structure for tbl_fsk
-- ----------------------------
DROP TABLE IF EXISTS "main"."tbl_fsk";
CREATE TABLE "tbl_fsk" (
"FSK_ID"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"bez"  TEXT,
"alter"  INTEGER
);

-- ----------------------------
-- Table structure for tbl_genre
-- ----------------------------
DROP TABLE IF EXISTS "main"."tbl_genre";
CREATE TABLE "tbl_genre" (
"GE_ID"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT(80)
);

-- ----------------------------
-- Table structure for tbl_genre_zuordnung
-- ----------------------------
DROP TABLE IF EXISTS "main"."tbl_genre_zuordnung";
CREATE TABLE "tbl_genre_zuordnung" (
"GEZ_ID"  INTEGER NOT NULL,
"FI_ID"  INTEGER NOT NULL,
"GE_ID"  INTEGER NOT NULL,
PRIMARY KEY ("GEZ_ID" ASC),
CONSTRAINT "Genre" FOREIGN KEY ("GE_ID") REFERENCES "tbl_genre" ("GE_ID"),
CONSTRAINT "Film" FOREIGN KEY ("FI_ID") REFERENCES "tbl_film" ("FI_ID")
);

-- ----------------------------
-- Table structure for tbl_kunde
-- ----------------------------
DROP TABLE IF EXISTS "main"."tbl_kunde";
CREATE TABLE "tbl_kunde" (
"KU_ID"  INTEGER NOT NULL,
"U_ID"  INTEGER,
"date_of_joining"  INTEGER,
"account_balance"  REAL,
"account_model"  INTEGER,
PRIMARY KEY ("KU_ID" ASC),
CONSTRAINT "AccountModel" FOREIGN KEY ("account_model") REFERENCES "tbl_account_model" ("KUM_ID"),
CONSTRAINT "User" FOREIGN KEY ("U_ID") REFERENCES "tbl_user" ("U_ID")
);

-- ----------------------------
-- Table structure for tbl_mitarbeiter
-- ----------------------------
DROP TABLE IF EXISTS "main"."tbl_mitarbeiter";
CREATE TABLE "tbl_mitarbeiter" (
"MA_ID"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"U_ID"  INTEGER NOT NULL,
"permission"  INTEGER NOT NULL,
CONSTRAINT "User" FOREIGN KEY ("U_ID") REFERENCES "tbl_user" ("U_ID"),
CONSTRAINT "Permissions" FOREIGN KEY ("permission") REFERENCES "tbl_permissions" ("PER_ID")
);

-- ----------------------------
-- Table structure for tbl_permissions
-- ----------------------------
DROP TABLE IF EXISTS "main"."tbl_permissions";
CREATE TABLE "tbl_permissions" (
"PER_ID"  INTEGER NOT NULL,
"bez"  TEXT,
PRIMARY KEY ("PER_ID")
);

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS "main"."tbl_user";
CREATE TABLE "tbl_user" (
"U_ID"  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
"name"  TEXT NOT NULL,
"surname"  TEXT NOT NULL,
"street"  TEXT NOT NULL,
"street_nr"  INTEGER NOT NULL,
"zip_code"  TEXT NOT NULL,
"location"  TEXT NOT NULL,
"password"  TEXT NOT NULL,
"birthdate"  INTEGER NOT NULL,
"email"  TEXT NOT NULL,
"accountnumber"  TEXT(10)
);

-- ----------------------------
-- Indexes structure for table tbl_film
-- ----------------------------
CREATE UNIQUE INDEX "main"."FilmID"
ON "tbl_film" ("FI_ID" ASC);

-- ----------------------------
-- Indexes structure for table tbl_user
-- ----------------------------
CREATE UNIQUE INDEX "main"."Accountnr"
ON "tbl_user" ("accountnumber" ASC);
CREATE UNIQUE INDEX "main"."Email"
ON "tbl_user" ("email" ASC);
