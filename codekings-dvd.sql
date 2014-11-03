/*
Navicat MySQL Data Transfer

Source Server         : Lokale Verbindung (root)
Source Server Version : 50620
Source Host           : 127.0.0.1:3306
Source Database       : codekings-dvd

Target Server Type    : MYSQL
Target Server Version : 50620
File Encoding         : 65001

Date: 2014-11-03 10:33:33
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tbl_ausleihe
-- ----------------------------
DROP TABLE IF EXISTS `tbl_ausleihe`;
CREATE TABLE `tbl_ausleihe` (
  `AUS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `KU_ID` int(11) NOT NULL,
  `DVD_ID` int(11) NOT NULL,
  `begindate` int(11) NOT NULL,
  `enddate` int(11) NOT NULL,
  `returned` int(11) DEFAULT NULL,
  PRIMARY KEY (`AUS_ID`),
  KEY `Kunde` (`KU_ID`),
  KEY `DVD` (`DVD_ID`),
  CONSTRAINT `DVD` FOREIGN KEY (`DVD_ID`) REFERENCES `tbl_dvd` (`DVD_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Kunde` FOREIGN KEY (`KU_ID`) REFERENCES `tbl_kunde` (`KU_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_ausleihe
-- ----------------------------
INSERT INTO `tbl_ausleihe` VALUES ('1', '1', '1', '1410106018', '1410365236', '0');

-- ----------------------------
-- Table structure for tbl_charts
-- ----------------------------
DROP TABLE IF EXISTS `tbl_charts`;
CREATE TABLE `tbl_charts` (
  `CH_ID` int(11) NOT NULL AUTO_INCREMENT,
  `FI_ID` int(11) NOT NULL,
  `addDate` int(11) NOT NULL,
  `added_by` int(11) NOT NULL,
  `enabled` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`CH_ID`),
  KEY `Film` (`FI_ID`),
  KEY `HinzugefuegtVon` (`added_by`),
  CONSTRAINT `Film` FOREIGN KEY (`FI_ID`) REFERENCES `tbl_film` (`FI_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `HinzugefuegtVon` FOREIGN KEY (`added_by`) REFERENCES `tbl_mitarbeiter` (`MA_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_charts
-- ----------------------------
INSERT INTO `tbl_charts` VALUES ('2', '1', '361631565', '1', '1');

-- ----------------------------
-- Table structure for tbl_dvd
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dvd`;
CREATE TABLE `tbl_dvd` (
  `DVD_ID` int(11) NOT NULL AUTO_INCREMENT,
  `art_nr` varchar(10) NOT NULL,
  `FI_ID` int(11) NOT NULL,
  `lent` int(11) NOT NULL,
  `notice` varchar(255) DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  `last_edit` int(11) DEFAULT NULL,
  PRIMARY KEY (`DVD_ID`),
  KEY `FI_ID` (`FI_ID`),
  KEY `last_edit_by` (`last_edit_by`),
  CONSTRAINT `LastEditBy` FOREIGN KEY (`last_edit_by`) REFERENCES `tbl_mitarbeiter` (`MA_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_dvd
-- ----------------------------
INSERT INTO `tbl_dvd` VALUES ('1', '1235', '1', '0', '-', null, null);

-- ----------------------------
-- Table structure for tbl_film
-- ----------------------------
DROP TABLE IF EXISTS `tbl_film`;
CREATE TABLE `tbl_film` (
  `FI_ID` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(80) NOT NULL,
  `subtitle` varchar(80) DEFAULT NULL,
  `desc` varchar(1500) DEFAULT NULL,
  `fsk` int(11) DEFAULT NULL,
  `rating` int(3) DEFAULT NULL,
  `cover` varchar(255) DEFAULT NULL,
  `trailer` varchar(255) DEFAULT NULL,
  `actor` varchar(255) DEFAULT NULL,
  `regie` varchar(127) DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `preis` double DEFAULT NULL,
  `last_edit_by` int(11) DEFAULT NULL,
  `last_edit` date DEFAULT NULL,
  `removed` int(1) DEFAULT '0',
  PRIMARY KEY (`FI_ID`),
  KEY `FSK` (`fsk`),
  CONSTRAINT `FSK` FOREIGN KEY (`fsk`) REFERENCES `tbl_fsk` (`FSK_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_film
-- ----------------------------
INSERT INTO `tbl_film` VALUES ('1', 'Forrest Gump', '', 'Forrest Gump ist eine US-amerikanische Literaturverfilmung des gleichnamigen Romans von Winston Groom unter der Regie von Robert Zemeckis aus dem Jahr 1994. Für die Darstellung der Figur Forrest Gump wurde Tom Hanks mit einem Oscar als bester Hauptdarsteller ausgezeichnet. Dem Film wurden insgesamt sechs Oscars und drei Golden Globes verliehen. Der Film war in den USA im Kinojahr 1994 der Film mit dem höchsten Einspielergebnis von über 329 Millionen US-Dollar, dem ein geschätztes Budget von rund 55 Millionen US-Dollar gegenübersteht. In Deutschland kam er 1994 auf 7,6 Millionen Besucher und war damit der erfolgreichste Film des Jahres. Über den Filmverleih wurden allein in Nordamerika weiter', '3', '88', 'NoCover.jpg', 'http://www.imdb.com/video/screenplay/vi3567517977/?ref_=tt_ov_vi', 'Tom Hanks; Robin Wright; Gary Sinise', 'Robert Zemeckis', '1994-06-23', '142', '0.5', '1', '2014-10-08', '0');
INSERT INTO `tbl_film` VALUES ('3', 'Star Wars: Episode I', 'Die dunkle Bedrohung', 'Zehn Jahre sind seit den Ereignissen von \"Star Wars: Episode I - Die dunkle Bedrohung\" vergangen. Es hat sich einiges getan in der Galaxie: Die politische Situation driftet immer mehr in Richtung Bürgerkrieg, seit eine mit der Republik unzufriedene und vom mysteriösen Count Dooku (Christopher Lee) angeführte Separatistenbewegung an Stärke gewinnt. Anakin Skywalker (Hayden Christensen), einst ein machtbegabter kleiner Junge, ist zu einem hitzköpfigen Padawan herangewachsen, der seinem Lehrer Obi-Wan (Ewan McGregor) einige Schwierigkeiten bereitet. Beide zusammen bekommen den Auftrag, Senatorin Amidala (Natalie Portman) zu beschützen, deren Leben in Gefahr ist. Anakin entwickelt bald tiefere Gefühle für die schöne Ex-Königin. Dabei interessiert ihn wenig, dass der Jedi-Kodex Liebesbeziehungen untersagt... ', '2', '53', 'StarWars-Episode1.jpg', 'http://www.moviepilot.de/movies/star-wars-episode-i-die-dunkle-bedrohung/trailer', 'Liam Neeson;\r\nEwan McGregor;\r\nNatalie Portman;\r\nJake Lloyd;\r\nPernilla August;\r\nFrank Oz', 'George Lucas', '1999-08-19', '136', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('4', 'Star Wars: Episode II', 'Angriff der Klonkrieger', 'Die Republik wird immer noch von Konflikten und Chaos erschüttert. Eine Separatistenbewegung, der sich bereits Hunderte von Planeten angeschlossen haben, sowie mächtige Unternehmensbündnisse stellen eine neue Bedrohung für die Galaxis dar. Und die Jedi haben dem nichts entgegenzusetzen. Die Ereignisse, durch eine bislang unbekannte Macht von langer Hand geplant, lösen schließlich die Klonkriege aus – und damit den Anfang vom Ende der Republik. Um dieser Bedrohung entgegenzuwirken und seine Macht zu festigen, autorisiert der Oberste Kanzler der Republik, Palpatine (Ian McDiarmid), die Aufstellung einer ‘Großen Armee der Republik’, die den überforderten Jedi zur Seite stehen soll. Zum ersten Mal seit dem Konflikt zwischen der Handelsföderation und Naboo, dem Heimatplaneten von Padmé Amidala (Natalie Portman), treffen unsere vertrauten Helden Obi-Wan Kenobi (Ewan McGregor), Amidala und Anakin Skywalker (Hayden Christensen) wieder aufeinander…', '3', '56', 'StarWars-Episode2.jpg', 'http://www.moviepilot.de/movies/star-wars-episode-ii-angriff-der-klonkrieger/trailer', 'Ewan McGregor;\r\nNatalie Portman;\r\nHayden Christensen;\r\nChristopher Lee;\r\nSamuel L. Jackson;\r\nFrank Oz', 'George Lucas', '2002-05-17', '142', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('5', 'Der Herr der Ringe', 'Die Gefährten', 'Am 111. Geburtstag seines Onkels Bilbo (Ian Holm) ändert sich das Leben des jungen Hobbit Frodo (Elijah Wood) auf dramatische Weise. Nicht nur muss er sein geliebtes Auenland verlassen, ihm wird auch die Bürde auferlegt, den Einen Ring zu zerstören, mit dem der böse Sauron ganz Mittelerde ins Verderben stürzen will. Auf seiner gefährlichen und abenteuerlichen Reise erfährt Frodo Unterstützung von nicht minder abenteuerlichen Gefährten. Neben seinem besten Freund Sam (Sean Astin) sind auch der Zauberer Gandalf (Ian McKellen), der geheimnisvolle Waldläufer Aragorn (Viggo Mortensen), der kämpferische Boromir (Sean Bean), der Elbe Legolas (Orlando Bloom), der Zwerg Gimli (John Rhys-Davies) sowie die beiden Hobbits Pippin (Billy Boyd) und Merry (Dominic Monaghan) Teil der Gefährten. Diese stellen schnell fest, dass es all ihren Mut erfordert, um den Ring ans Ziel zu bringen. Denn nicht nur Sauron hat es auf den Ring abgesehen, überall lauern Gefahren auf die Gefährten.', '4', '80', 'DerHerrDerRinge-DieGefaehrten.jpg', 'http://www.moviepilot.de/movies/der-herr-der-ringe-die-gefaehrten/trailer', 'Elijah Wood;\r\nIan Holm;\r\nSean Astin;\r\nIan McKellen;\r\nLiv Tyler;\r\nBilly Boyd', 'Peter Jackson', '2001-12-19', '178', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('6', 'Der Herr der Ringe', 'Die zwei Türme', 'Nachdem sich die Gefährten aus Der Herr der Ringe: Die Gefährten trennen mussten, treffen der Ringträger Frodo (Elijah Wood) und Sam (Sean Astin) auf das Wesen Gollum (Andy Serkis), welcher einst dem Einen Ring verfallen war. Dieser bietet Frodo und Sam an, ihnen den Weg nach Mordor zu zeigen, wo der Ring zerstört werden soll. In der Zwischenzeit lässt Saruman (Christopher Lee) sein kolossales Herr zur Festung Helms Klam marschieren, wo die Menschen Mittelerdes Zuflucht gefunden haben. Die Gefährten Aragorn (Viggo Mortensen), Gimli (John Rhys-Davies) und Legolas (Orlando Bloom bereiten sich mit den Menschen auf die große Schlacht vor. Als sich die Menschen der Übermacht von Sarumans Heer bewusst werden, sinkt ihr Mut, nur ein Wunder kann sie nun noch vor dem Tod bewahren. Die Schlacht um Mittelerde beginnt.', '3', '81', 'DerHerrDerRinge-DieZweiTuerme.jpg', 'http://www.moviepilot.de/movies/der-herr-der-ringe-die-zwei-tuerme/trailer', 'Elijah Wood;\r\nIan McKellen;\r\nViggo Mortensen;\r\nOrlando Bloom;\r\nChristopher Lee;\r\nJohn Rhys-Davies', 'Peter Jackson', '2002-12-18', '179', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('7', 'Der Herr der Ringe', 'Die Rückkehr des Königs', 'Während Mittelerde sich noch von den letzten Angriffen Saurons erholt, macht sich bereits neues Unheil breit. Saurons Armee marschiert gen Minas Tirith, der Hauptstadt Gondors, dessen wahrer König Aragorn (Viggo Mortensen) ist. Dieser muss all seinen Mut und seine ganze Kraft zusammen nehmen, um diesen letzten großen Angriff stand zu halten. Gleichzeitig muss auch Sauron von den Ereignissen am und im Schicksalsberg abgelenkt werden, wo sich Frodo (Elijah Wood) und Sam (Sean Astin) kurz vor der Erfüllung ihrer Mission befinden, den Einen Ring zu zerstören. Also sammeln sich die Gefährten zu einer letzten großen Schlacht, während Frodo den schwersten Gang der gesamten Reise antritt, denn die Macht des Ringes hat schon längst von ihm Besitz ergriffen. Am Ende steht der Untergang Mittelerdes – oder ein neuer Anfang.', '3', '80', 'DerHerrDerRinge-DieRueckkehrDesKoenigs.jpg', 'http://www.moviepilot.de/movies/der-herr-der-ringe-die-rueckkehr-des-koenigs/trailer', 'Elijah Wood;\r\nIan McKellen;\r\nViggo Mortensen;\r\nOrlando Bloom;\r\nSean Astin;\r\nBernard Hill', 'Peter Jackson', '2003-12-17', '200', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('8', 'Twins', 'Zwillinge', 'Die beiden Zwillinge Julius (Arnold Schwarzenegger) und Benedict (Danny DeVito) sind das Ergebnis eines Versuchs, das perfekte Kind zu zeugen. Während Julius in der Obhut der Wissenschaftler zu einem intelligenten Superman heranwuchs, verdient sich der kleine Ganove Benedict seine Brötchen mit geklauten Autos. Als die beiden erwachsenen Männer sich zum ersten Mal begegnen, nimmt das vergnügliche Chaos seinen Lauf', '3', '50', 'Twins.jpg', 'http://www.moviepilot.de/movies/twins-zwillinge/trailer', 'Arnold Schwarzenegger;\r\nDanny DeVito;\r\nKelly Preston;\r\nChloe Webb;\r\nBonnie Bartlett;\r\nTrey Wilson', 'Ivan Reitman', '1989-03-23', '105', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('9', 'Crank', '', 'Das ist nicht Chev Chelios’ (Jason Statham) Tag: Da er sich entschlossen hat, seine Karriere als Auftragskiller an den Nagel zu hängen, wurde ihm von seinem Konkurrenten Victor eine Dosis tödlichen Gifts verabreicht. Alles, was Chev nun tun kann, um seinen sofortigen Tod zu verhindern, ist seinen Adrenalinspiegel auf ausgesprochen hohem Niveau zu halten. Als hilfreich erweist sich hier, dass ihm eine ganze Menge übler Schergen auf den Fersen sind. Seine Freundin Eve (Amy Smart) scheut sich auch vor öffentlichem Sex nicht, um ihm die lebenswichtige Aufregung zu verschaffen. Und schließlich gibt es noch eine Rechnung mit Victor zu begleichen.', '5', '66', 'Crank.jpg', 'http://www.moviepilot.de/movies/crank/trailer', 'Jason Statham;\r\nEfren Ramirez;\r\nAmy Smart;\r\nDwight Yoakam;\r\nJose Pablo Cantillo;\r\nCarlos Sanz', 'Mark Neveldine;\r\nBrian Taylor', '2006-09-21', '88', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('10', 'Crank 2', 'High Voltage', 'Der coole Gangster und Profikiller Chev Chelios (Jason Statham) dachte er hat es geschafft: Nachdem er in Crank vergiftet wurde und nur durch ständige Adrenalinzufuhr am Leben bleiben konnte, erhielt er endlich ein neues Herz. Doch die Freude währt nur kurz, denn der Triadenganove Johnny Vang (Art Hsu) hat sein eigentliches Spenderorgan geklaut und ihm ein Kunstherz eingepflanzt. Mit dem Organ will er seinem schwächelnden Oberboss zu neuer Lebenskraft verhelfen, doch er hat die Rechnung ohne Chev gemacht.\r\n\r\nAls er bei seiner Jagd nach Johhny die Batterie für sein Herz verliert, muss er zu einem ungewöhnlichen Mittel greifen: Wann immer der Stromstand seines Herzes zu versiegen droht, muss er sich an einer Stromquelle aufladen: Ob es nun Autobatterien, Steckdosen, oder Starkstromleitungen sind. Was andere Menschen umbringen würde, macht Chev nur noch stärker.', '5', '56', 'Crank2.jpg', 'http://www.moviepilot.de/movies/crank-2-high-voltage-2/trailer', 'Jason Statham;\r\nAmy Smart;\r\nLing Bai;\r\nDwight Yoakam;\r\nJulanne Chidi Hill;\r\nEfren Ramirez', 'Mark Neveldine;\r\nBrian Taylor', '2009-04-16', '96', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('11', 'Madagascar ', '', 'Der selbstverliebte Löwe Alex, das vorlaute Zebra Marty, die ständig panische Giraffe Melman und die divenhafte Nilpferd-Dame Gloria sind die absoluten Stars im Zoo des New Yorker Central Parks. Doch Marty ist traurig: Zu sehr beschäftigt ihn der Gedanke an die Freiheit. Als er eines Tages zufällig den Fluchtversuch einiger Pinguine belauscht, ist er kurz darauf verschwunden. Nach einer wilden Verfolgungsjagd im New Yorker U-Bahn-Dschungel werden alle vier Freunde in Kisten verpackt auf ein Schiff nach Afrika verfrachtet. Doch sie stranden schiffbrüchig auf der Insel Madagaskar. Hier müssen die eingefleischten New Yorker am eigenen Leib erfahren, was es wirklich heißt “wild” zu sein…', '1', '59', 'Madagascar.jpg', 'http://www.moviepilot.de/movies/madagascar/trailer', 'Chris Rock;\r\nBen Stiller;\r\nJada Pinkett Smith;\r\nDavid Schwimmer;\r\nChris Miller;\r\nBastian Pastewka', 'Tom McGrath;\r\nEric Darnell', '2005-07-14', '86', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('12', 'Inglourious Basterds', '', 'Im von Nazis besetzten Frankreich muss Shosanna Dreyfus (Mélanie Laurent) mit ansehen, wie ihre Familie durch den Nazi-Oberst Hans Landa (Christoph Waltz) brutal hingerichtet wird. Nur knapp kann sie entkommen und flieht nach Paris, wo sie sich als Kinobesitzerin eine neue Identität und Existenz aufbaut.\r\n\r\nZur gleichen Zeit formt Offizier Aldo Raine (Brad Pitt) eine Elitetruppe aus jüdischen Soldaten (u. A. Eli Roth), die gezielte Vergeltungsschläge gegen Nazis und Kollaborateure durchführen soll. Gemeinsam mit seinen acht Männern wird er in Frankreich abgesetzt, um dort unterzutauchen und in Guerilla-Einsätzen Nazis zu jagen und töten. Schon bald werden sie von den Deutschen als ‘Die Bastarde’ gefürchtet. Als der Plan reift Adolf Hitler persönlich auszuschalten, nimmt Raines Einheit Kontakt zu der deutschen Schauspielerin und Undercover-Agentin des Widerstands Bridget von Hammersmark (Diane Kruger) auf, die entscheidend für das Gelingen des Anschlags ist. Die gemeinsame Mission führt sie schließlich alle in das Pariser Kino von Shosanna, die allerdings seit langem ihre eigenen Rachepläne verfolgt.', '4', '80', 'InglouriousBastards.jpg', 'www.moviepilot.de/movies/inglourious-basterds-2/trailer', 'Brad Pitt;\r\nMélanie Laurent;\r\nChristoph Waltz;\r\nEli Roth;\r\nMichael Fassbender;\r\nDiane Kruger', 'Quentin Tarantino', '2009-08-20', '154', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('13', 'Fight Club ', '', 'Eine ganze Generation von Männern, die Zweitgeborenen der Geschichte, wanken durch ihr Leben auf der Suche nach einem Sinn, einer Aufgabe, einer Erfüllung ihrer selbst. Doch ein Ziel scheint es in der deprimierenden Konsumgesellschaft nicht zu geben. Auch der von Edward Norton verkörperte Protagonist und gleichzeitiger Erzähler gehört zu diesen verlorenen Seelen, die ungelenkt ihr Dasein fristen. Als er jedoch eines Tages Tyler Durden (Brad Pitt) kennenlernt, soll sich alles ändern. Der von beiden Junggesellen ins Leben gerufene Fight Club, entfesselt ungeahnte Möglichkeiten, die jedoch ebenso unkontrollierbares Ausmaß annehmen. Aus der Rache an der modernen Zivilisation wird schnell ein Kleinkrieg, der seine Opfer fordert. Nicht einmal die verruchte Marla Singer (Helena Bonham Carter) vermag sich diesem unaufhaltsame Treiben zu entziehen.', '5', '87', 'Fightclub.jpg', 'http://www.moviepilot.de/movies/fight-club-2/trailer', 'Brad Pitt;\r\nEdward Norton;\r\nHelena Bonham Carter;\r\nMeat Loaf;\r\nZach Grenier;\r\nDavid Andrews', 'David Fincher', '1999-11-11', '139', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('14', 'Pulp Fiction', '', 'Was braucht man für ein gutes Stück Pulp Fiction (zu deutsch: Schundliteratur)? Ein Gauner-Pärchen, zwei Auftragskiller, eine Uhr, einen Koffer geheimnisvollen gold-glänzenden Inhalts, eine Menge Adrenalin in Form einer Spritze, Gespräche über das europäische metrische System von Fastfood und die Gefährlichkeit gewisser Fußmassagen, ein Bibelzitat (Ezekiel 25:17), einen versehentlichen Kopfschuss und einen Cleaner, einen Boxer auf der Flucht und die perverse Begegnung mit einem roten Gummiball, göttliche Vorsehung und eine Läuterung. Und das ist nur ein Bruchteil der Charaktere und Geschichten, die dem Publikum hier auf fundamentale Weise näher gebracht werden. Das Ganze wird auf geschickte Weise miteinander verwoben (gerne auch ohne die zeitliche Abfolge zu sehr zu berücksichtigen) und untermalt von einem groovenden Soundtrack. Heraus kommt ein Film, der direkt zum Klassiker wurde. Eben ein gutes Stück Pulp Fiction…', '4', '87', 'PulpFiction.jpg', 'http://www.moviepilot.de/movies/pulp-fiction/trailer', 'John Travolta;\r\nSamuel L. Jackson;\r\nBruce Willis;\r\nUma Thurman;\r\nTim Roth;\r\nAmanda Plummer', 'Quentin Tarantino', '1994-11-03', '154', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('15', 'Der Pate', '', 'Es ist eine ausgelassene Hochzeitsfeier, die Connie (Talia Shire) mit Carlo Rizzi (Gianni Russo) feiert. Nur einer steht im verborgenen Dunkel, schreitet ab und zu in die feiernde Menge und kehrt dann in seinen finsteren Raum zurück, drückt die Lamellen seines Rollos auseinander und beobachtet das bunte Treiben von innen: Don Vito Corleone (Marlon Brando). Der Pate hält Gericht, empfängt Bittsteller, die für ihr Anliegen Gefälligkeiten aller Art anbieten oder danach fragen, welcher Art sie sein sollen. Don Vito ist der Herr, nicht nur im Haus, sondern in der ganzen Welt, die Francis Ford Coppola uns in seinem Klassiker vorführt. Es ist eine Welt, in der die Familienbande alles ist und Verrat keinerlei Duldung erfährt... ', '4', '88', 'DerPate.jpg', 'http://www.moviepilot.de/movies/der-pate/trailer', 'Marlon Brando;\r\nAl Pacino;\r\nJames Caan;\r\nRichard S. Castellano;\r\nRobert Duvall;\r\nSterling Hayden', 'Francis Ford Coppola', '1972-08-24', '175', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('16', 'The Dark Knight', '', 'Noch immer spielt Bruce Wayne (Christian Bale) tagsüber den verantwortungslosen Milliardär, während er nachts als Batman das Verbrechen in Gotham city bekämpft. Unterstützt von Lieutenant Jim Gordon (Gary Oldman) und Staatsanwalt Harvey Dent (Aaron Eckhart) setzt Batman sein Vorhaben fort, das organisierte Verbrechen in Gotham endgültig zu zerschlagen. Doch das schlagkräftige Dreiergespann sieht sich bald einem genialen, immer mächtiger werdenden Kriminellen gegenübergestellt, der als Joker (Heath Ledger) bekannt ist: Er stürzt Gotham ins Chaos und zwingt den Dunklen Ritter immer näher an die Grenze zwischen Gerechtigkeit und Rache. Dent, der inzwischen mit Bruce Waynes Jugendliebe Rachel (Maggie Gyllenhaal) liiert ist, rückt in das Fadenkreuz des Jokers…', '4', '84', 'TheDarkKnight.jpg', 'http://www.moviepilot.de/movies/the-dark-knight-2/trailer', 'Christian Bale;\r\nHeath Ledger;\r\nAaron Eckhart;\r\nMichael Caine;\r\nMaggie Gyllenhaal;\r\nGary Oldman', 'Christopher Nolan', '2008-08-21', '152', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('17', 'Die Verurteilten', '', 'Der Banker Andrew Dufresne (Tim Robbins) wird Anfang der 40er Jahre für den Mord an seiner Frau und ihrem Liebhaber verurteilt, obwohl er unschuldig ist. Er wird ins Gefängnis nach Shawshank überwiesen, wo er als Bänker nicht gerade gerne gesehen ist. Die ersten Wochen und Monate sind hart, doch er findet auch Freunde an diesem ungewöhnlichen Ort. Allen voran lernt er Red (Morgan Freeman) kennen, der schon seit gefühlten Ewigkeiten hier einsitzt und das Gefängnis kennt wie seine Westentasche. Durch seine Talente mach sich Andrew im Laufe der Jahrzehnte sogar unentbehrlich, sowohl bei seinen Mitgefangenen, für die er einiges bewegt, als auch bei den Wärtern und dem Gefängnisdirektor, denen er mit den Erfahrungen in seinem Beruf viel Arbeit und Geld sparen kann.\r\n\r\nTrotz einiger Previlegien leidet Andrew unt dem korrupten Gefängnis-Direktor Norton (Bob Gunton), der gemeinsam mit seinem sadistischen Oberwärter Headley (Clancy Brown) ein brutales Regiment führt, das keinen Widerspruch duldet. Für Andrew steht fest, dass seine einzige Chance zu Überleben die Flucht ist.', '3', '83', 'DieVerurteilten.jpg', 'http://www.moviepilot.de/movies/die-verurteilten/trailer', 'William Sadler;\r\nTim Robbins;\r\nMorgan Freeman;\r\nClancy Brown;\r\nBob Gunton;\r\nMark Rolston', 'Frank Darabont', '1995-03-09', '142', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('18', 'Sieben', '', 'Nur noch eine Woche dauert es, dann will sich Detective Summerset (Morgan Freeman) in den wohlverdienten Ruhestand begeben und Platz für seinen Nachfolger, den jungen Detective Mills (Brad Pitt), machen. Der ist gerade zusammen mit seiner Ehefrau Trace (Gwyneth Paltrow) in die Stadt gezogen und wird gleich an seinem ersten Tag mit der Untersuchung einer skurrilen, grausamen Mordserie beauftragt. Das erste Opfer ist ein 400 Pfund schwerer Mann und wird, das Gesicht im Essen vergraben, in seiner Wohnung aufgefunden. Es stellt sich heraus, dass er gezwungen wurde, zu essen, bis er an inneren Blutungen starb. Die Morde scheinen einem ganz bestimmten Muster zu folgen. Jedes Opfer repräsentiert eine der sieben Todsünden: Habsucht, Hochmut, Neid, Zorn, Wollust, Trägheit und Maßlosigkeit. ', '4', '84', 'Sieben.jpg', 'http://www.moviepilot.de/movies/sieben/trailer', 'Brad Pitt;\r\nMorgan Freeman;\r\nKevin Spacey;\r\nGwyneth Paltrow;\r\nJohn C. McGinley;\r\nR. Lee Ermey', 'David Fincher', '1995-11-23', '126', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('19', 'Taxi Driver', '', 'Taxi Driver ist das beklemmende Psychogramm des Vietnam-Veteranen Travis Bickle (Robert De Niro), der sich gegen die Unmoral des Großstadtlebens New Yorks aufbäumt. Als traumatisierter Ex-Marine kann er nachts nicht mehr schlafen und heuert daher als Taxifahrer an, dem die Abgründe und Schattenseiten der Metropole jeden Abend auf der Straße begegnet. Martin Scorseses Regiearbeit gilt als eines der großartigsten und am meisten diskutierten Werke der amerikanischen Filmgeschichte und als ein zentraler Film im New Hollywood.', '4', '87', 'TaxiDriver.jpg', 'http://www.moviepilot.de/movies/taxi-driver-2/trailer', 'Robert De Niro;\r\nJodie Foster;\r\nCybill Shepherd;\r\nHarvey Keitel;\r\nLeonard Harris;\r\nPeter Boyle', 'Martin Scorsese', '2006-07-13', '113', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('20', 'Inception', '', 'Inception ist eine Mischung aus Science-Fiction-Thriller und Heist-Movie: Dank modernster Technologie ist es in naher Zukunft möglich, in Träume und somit in das Unterbewusstsein von Menschen einzusteigen. Das Einsteigen bedeutet mithin auch die Möglichkeit des Stehlens fremder und bisher ureigenster Ideen. Ein Meisterdieb auf dem neuesten Gebiet der Firmenspionage ist Dom Cobb (Leonardo DiCaprio), was ihn nicht nur im positiven Sinn zu einem besonders gefragten Mann macht. Um endlich wieder ein normales Leben führen zu können, muss er nur noch den einen letzten Job erledigen: Diesmal soll er aber keine Idee stehlen, sondern eine Idee in das Unterbewusstsein eines Opfers einpflanzen. Was sich an den Einsatz anschließt, geht über sein Vorstellungsvermögen und das des Publikums bei weitem hinaus.', '3', '80', 'Inception.jpg', 'http://www.moviepilot.de/movies/inception/trailer', 'Leonardo DiCaprio;\r\nJoseph Gordon-Levitt;\r\nEllen Page;\r\nTom Hardy;\r\nKen Watanabe;\r\nDileep Rao', 'Christopher Nolan', '2010-07-29', '148', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('21', 'Spiel mir das Lied vom Tod', '', 'Der skrupellose Eisenbahnunternehmer Morton (Gabriele Ferzetti) will entlang einer Eisenbahnstrecke zum Pazifik einen neuen Bahnhof bauen. Dafür engagiert er den Farmer McBain (Frank Wolff), der für ihn die Drecksarbeit erledigen soll. Falls er es nicht in der vorgegebenen Zeit schaffen sollte, wird Morton dessen kompletter Besitz mitsamt der Farm zugesprochen. Einen Tag, bevor McBains Ehefrau Jill (Claudia Cardinale), an der Farm ankommt, werden McBain und seine drei Kinder von dem Killer Frank (Henry Fonda) erschossen. Jill, die ihren Mann kaum kannte, muss nun sein Erbe antreten und die Bahnstation fertig bauen. Zunächst glauben alle, dass Cheyenne (Jason Robards) der Mörder des Farmbesitzers ist, doch ein geheimnisvoller Mundharmonikaspieler (Charles Bronson), der in der Stadt auftaucht, bringt Licht in das Dunkel. Während er mit der Hilfe Cheyennes Jill hilft, den Bahnhof zu vollenden, führt ihn die Suche nach dem wahren Mörder in seine eigene Vergangenheit.', '4', '86', 'SpielMirDasLiedVomTod.jpg', 'http://www.moviepilot.de/movies/spiel-mir-das-lied-vom-tod/trailer', 'Charles Bronson;\r\nHenry Fonda;\r\nClaudia Cardinale;\r\nJason Robards;\r\nGabriele Ferzetti;\r\nPaolo Stoppa', 'daw', '1969-08-14', '165', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('22', 'Blade Runner', '', 'In der Zukunft im Jahr 2019 sieht die Erde nicht mehr so aus, wie wir sie heute kennen. Die Menschheit wird von Dauerregen heimgesucht und die Städte sind dreckig und viel zu dicht besiedelt. Tiere gibt es fast keine mehr, dafür umso mehr künstliche Wesen. Einen Lichtschimmer bringt der Ausblick auf das Leben auf einem fremden Planeten, der bald bevölkert werden soll und durch sogenannte Replikanten gefunden wurde. Diese verfügen über große Kräfte und entwickeln von alleine Gefühle, sodass sie nur bis zu vier Jahre alt werden können. Außerdem ist es ihnen verboten, die Erde zu betreten. Inmitten dieser Welt lebt Rick Deckard (Harrison Ford), der als Blade Runner die Aufgabe hat, Replikanten aufzuspüren und sie anschließend auszulöschen. Eigentlich hat Deckard vor, seinen Job aufzugeben, doch muss er noch einen letzten Auftrag ausführen. Fünf Replikanten unter der Führung von Roy Batty (Rutger Hauer) und dessen Freundin Pris (Daryl Hannah) befinden sich in einem gestohlenen Raumschiff auf dem Weg zur Erde. Zusammen mit seinem zwielichtigen Kollegen Gaff (Edward James Olmos) soll er sie finden und ‘in den Ruhestand versetzen’. Während seinen Ermittlungen trifft Deckard auf Rachael (Sean Young) und verliebt sich in sie. Doch dann kommt er dahinter, dass sie selbst auch eine Replikantin ist.', '4', '87', 'BladeRunner.jpg', 'http://www.moviepilot.de/movies/blade-runner/trailer', 'Harrison Ford;\r\nRutger Hauer;\r\nSean Young;\r\nEdward James Olmos;\r\nM. Emmet Walsh;\r\nDaryl Hannah', 'Ridley Scott', '1982-10-14', '112', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('23', 'Stirb Langsam', '', 'Der New Yorker Polizist John McClane (Bruce Willis) fliegt am Heligabend nach Los Angeles, um mit seiner dort lebenden Familie die Feiertage zu verbringen. Er hofft auch, sich mit seiner Frau Holly (Bonnie Bedelia) zu versöhnen, welche sich vor einem halben Jahr von ihm getrennt hatte, nachdem sie aus beruflichen Gründen nach Los Angeles gezogen ist und er ihr nicht folgen wollte.\r\n\r\nEr trifft sich mit Hollyzu einer Weihnachtsfeier an ihrem Arbeitsplatz, im 30. Stock des Nakatomi Plaza, einem riesigen Büroturm. Doch die Feier nimmt ein abruptes Ende, als die Feier von Terroristen (im Original ehemalige Stasi-Agenten) um Hans Gruber (Alan Rickman) gestürmt wird. Nur McClane gelingt es im Chaos unbemerkt zu entkommen. Barfuss und lediglich mit seiner Polizeipistole bewaffnet, muss er sich allein den Terroristen stellen und versuchen, Kontakt mit der Polizei aufzunehmen. Ein kampf um Leben und Tod beginnt.', '4', '85', 'StirbLangsam.jpg', 'http://www.moviepilot.de/movies/stirb-langsam/trailer', 'Bruce Willis;\r\nBonnie Bedelia;\r\nReginald VelJohnson;\r\nPaul Gleason;\r\nAlan Rickman;\r\nDe\'Voreaux White', 'John McTiernan', '1988-11-10', '126', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('24', 'Apocalypse Now', '', 'Auf dem Höhepunkt des Vietnamkrieges erhält der Militärpolizist Captain Willard (Martin Sheen) einen waghalsigen Auftrag. Gemeinsam mit einer kleinen Truppe Soldaten (u.a Laurence Fishburne) begibt er sich in Richtung kambodschanische Grenze, um einen hochrangigen US-Militär, Colonel Kurtz (Marlon Brando), zu liquidieren. Kurtz, der sich mit einer folgsamen Einheit im Dschungel verschanzt hat, gehorcht keinerlei Militärbefehlen mehr und sorgt für Angst und Schrecken. Dem soll Captain Willard nun ein Ende bereiten. Auf ihrem Kanonenboot dringen sie immer Tiefer in den düsteren Albtraum des vietnamesischen Dschungels ein.', '4', '87', 'ApocalypseNow.jpg', 'http://www.moviepilot.de/movies/apocalypse-now/trailer', 'Martin Sheen;\r\nRobert Duvall;\r\nMarlon Brando;\r\nSam Bottoms;\r\nLaurence Fishburne;\r\nFrederic Forrest', 'Francis Ford Coppola', '2001-10-18', '153', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('25', 'Psycho', '', 'Nachdem die attraktive Sekretärin Marion Crane (Janet Leigh) ihren Boss um 40.000 Dollar erleichtert hat, flieht sie überstürzt Richtung Kalifornien zu ihrem Liebhaber, dem geschiedenen Eisenwarenhändler Sam Loomis. Aus Angst bereits von der Polizei verfolgt zu werden, verirrt sich Marion in Dunkelheit und bei Regen und landet bei einem kleinen abgelegenen Motel. Marion ist der einzige Gast und wird von dem zurückhaltenden, leicht verklemmt wirkenden, Besitzer des Motels, Norman Bates (Anthony Perkins), zum Abendessen eingeladen. Ungewollt überhört sie durch das offene Fenster ein Streitgespräch zwischen Bates und seiner alten eifersüchtigen Mutter, die ihm den Umgang mit Frauen wie Marion verbietet. Nach dem gemeinsamen Essen entschließt sich Marion, das gestohlene Geld zurückzubringen und nimmt erleichtert durch ihre Entscheidung eine Dusche. Plötzlich reißt eine Person in Frauenkleidern den Vorhang beiseite und sticht brutal mit einem Messer auf sie ein…', '4', '86', 'Psycho.jpg', 'http://www.moviepilot.de/movies/psycho-2/trailer', 'Anthony Perkins;\r\nJanet Leigh;\r\nVera Miles;\r\nJohn Gavin;\r\nMartin Balsam;\r\nJohn McIntire', 'Alfred Hitchcock', '1960-10-07', '108', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('26', 'Heat', '', 'Neil McCauley (Robert De Niro) ist einer der besten Verbrecher in L.A. Seine Pläne sind so durchdacht, dass er immer spurlos davon kommen kann. Vincent Hanna (Al Pacino) ist ein akribischer Cop und einer der erfolgreichsten obendrein. Beide Männer sind besessen vom Verbrechen. Nur das Gesetz trennt sie.\r\n\r\nMcCauley plant mit seinem Team (Val Kilmer, Danny Trejo, Tom Sizemore) noch einen großen Coup, doch als er sich in Eady (Amy Brenneman) verliebt, fällt es ihm schwer das Wesentliche im Auge zu behalten. Vincent Hanna steht kurz vor seiner dritten Scheidung und zu seiner Stieftochter (Natalie Portman) hat er keinen Draht. In Wirklichkeit ist Hanna mit dem Job verheiratet, seine wahre Familie sind die Cops (Mykelti Williamson, Wes Studi).\r\n\r\nNach intensiven Ermittlungen bekommt Hanna eine Spur und zieht die Schlinge immer enger zu. Ein Katz- und Mausspiel entwickelt sich, das nur einer gewinnen kann…', '4', '83', 'Heat.jpg', 'http://www.moviepilot.de/movies/heat-3--3/trailer', 'Al Pacino;\r\nRobert De Niro;\r\nVal Kilmer;\r\nJon Voight;\r\nTom Sizemore;\r\nAshley Judd', 'Michael Mann', '1996-02-29', '172', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('27', 'Matrix', '', 'Thomas A. Anderson (Reeves) arbeitet als Programmierer und führt nebenbei unter dem Pseudonym Neo Jobs als professioneller Hacker aus. Immer wieder beschleicht ihn das Gefühl, dass etwas Unvorstellbares und Geheimnisvolles sein Leben lenkt. Das Gefühl wird zur Gewissheit als die Hackerin Trinity ihm den mächtigen Anführer einer Untergrundorganisation Morpheus (Laurence Fishburne) vorstellt. Morpheus bietet Neo die Möglichkeit eine Wahrheit kennenzulernen, welche die Grenzen seiner Fantasie überschreitet. Neo wird zum Grenzgänger und aus seinem alten Leben, der Matrix, befreit.\r\n\r\nEr erwacht in einer für ihn bis dahin unvorstellbaren Realität: Die Entwicklung der künstlichen Intelligenz ist aus dem Ruder gelaufen. Maschinen haben die Weltherrschaft übernommen und die Menschheit unterworfen. Normalen Menschen wird in ihrem Leben nur eine Scheinrealität vorgespielt. Tatsächlich werden sie von intelligenten Maschinen in riesigen Zuchtanlagen gehalten und dort als lebende Energiequellen missbraucht. Ihre physischen Hüllen sind an eine komplexe Computersimulation, die Matrix, angeschlossen. Die Simulation halten die Menschen für das echte Leben. Nur in der unterirdischen Stadt Zion leben einige wenige Menschen, die sich aus der Matrix befreien konnten. Als Neo zu ihnen stößt, erkennen sie in ihm den Auserwählten, der den bevorstehenden Kampf gegen die übermächtige Maschinerie anführen soll…', '3', '80', 'Matrix.jpg', 'http://www.moviepilot.de/movies/matrix/trailer', 'Keanu Reeves;\r\nLaurence Fishburne;\r\nCarrie-Anne Moss;\r\nHugo Weaving;\r\nGloria Foster;\r\nJoe Pantoliano', 'Andy Wachowski;\r\nLana Wachowski', '1999-06-17', '144', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('28', 'Das Schweigen der Lämmer', '', 'Der jungen FBI-Auszubildenden Clarice Starling (Jodie Foster) wird die Mitarbeit in einem schwierigen Fall anvertraut. Schon seit langem ist das FBI auf der Suche nach dem Serienkiller Buffalo Bill (Ted Levine), der es jedoch hervorragend versteht, sich zu verstecken. Als letzte Möglichkeit soll Starling nun in Gesprächen mit Dr. Hannibal Lecter (Anthony Hopkins), seineszeichen Psychiater und Liebhaber von Menschenfleisch, mehr über den Täter herausfinden. Denn Lecter vermag es, sich in den Mörder hinein zu versetzen und womöglich auch Anhaltspunkte zu seinem Aufenthaltsort zu geben. Doch Lecter will Gegenleistungen für seine Hilfe und bringt Clarice Starling dazu, ihm persönliches aus ihrem Leben zu erzählen. Das psychologische Katz-und-Maus-Spiel nimmt seinen Lauf, während Buffalo Bill bereits ein weiteres Opfer gefunden hat – die Tochter der Senatorin.', '4', '79', 'DasSchweigenDerLämmer.jpg', 'http://www.moviepilot.de/movies/das-schweigen-der-laemmer/trailer', 'Jodie Foster;\r\nAnthony Hopkins;\r\nScott Glenn;\r\nAnthony Heald;\r\nTed Levine;\r\nFrankie Faison', 'Jonathan Demme', '1991-04-11', '118', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('29', 'Sin City', '', 'Korrupte Cops, geschmierte Politiker, Huren, die nach Gerechtigkeit dürsten, und die tägliche Suche nach Erlösung in einer Stadt, in der alles möglich scheint – nur nicht das. Das ist Sin City. In dem dreckigen Großstadtmoloch Basin City kreuzen sich die Wege verschiedener Charaktere, die sich bemühen hier ihr Glück zu finden oder einfach nur über die Runden zu kommen.\r\n\r\nDa ist Harrigan (Bruce Willis), der letzte ehrliche Cop der Stadt. Einst von seinem Partner angeschossen und verraten, bemüht er sich das Leben einer Stripperin (Jessica Alba) vor einem Psychopathen zu retten, dem Sohn eines mächtigen Senators. Dwight (Clive Owen) will ‘Jackie Boy’ (Benicio del Toro) daran hindern sich an Prostituierten zu vergehen. Diese können sich, angeführt von der so patenten wie schönen Gail (Rosario Dawson), allerdings ganz gut selbst helfen. Doch ahnen weder sie noch Dwight, dass es sich bei Jackie Boy um einen Polizisten handelt… Schließlich ist da noch Marv (Mickey Rourke), äußerlich eine Bestie von Mann, der den Mörder der schönen Goldie (Jaime King) finden will. Sie kam eines Nachts und gestand ihm ihre Liebe, doch dann wurde sie ermordet. Marv wird nicht ruhen, bevor er herausgefunden hat, wer ihre Mörder sind…', '5', '79', 'SinCity.jpg', 'http://www.moviepilot.de/movies/sin-city-4/trailer', 'Bruce Willis;\r\nJessica Alba;\r\nRosario Dawson;\r\nBenicio del Toro;\r\nClive Owen;\r\nMickey Rourke', 'Robert Rodriguez;\r\nFrank Miller;\r\nQuentin Tarantino', '2005-08-11', '119', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('30', 'Kill Bill', 'Volume 1', '‘Rache ist ein Gericht, das am besten kalt serviert wird.’ – Am Tag ihrer Hochzeit wird Die Braut (Uma Thurman) nicht nur von ein paar alten Bekannten, sondern vor allem von ihrer Vergangenheit heimgesucht. Das Attentatskommando Tödliche Viper, angeführt von Bill (David Carradine) stürmt die Zeremonie und veranstaltet ein Blutbad, bei dem die Braut sterbend zurückgelassen wird. Als sie nach vier Jahren aus dem Koma erwacht, beschließt sie Rache zu nehmen. Sie erstellt eine Todesliste mit fünf Namen – Vernita Green (Vivica A. Fox), Elle Driver (Daryl Hannah), O-Ren Ishii (Lucy Liu), Budd (Michael Madsen), Bill (David Carradine) – flieht aus dem Krankenhaus und sucht ihre Widersacher nach der Reihe auf.', '5', '80', 'KillBill.jpg', 'http://www.moviepilot.de/movies/kill-bill-volume-1-2/trailer', 'Uma Thurman;\r\nLucy Liu;\r\nVivica A. Fox;\r\nDaryl Hannah;\r\nDavid Carradine;\r\nMichael Madsen', 'Quentin Tarantino', '2003-10-16', '107', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('31', 'Django Unchained', '', 'Die Geschichte von Django Unchained ist eine Odyssee voller Entbehrungen und Sehnsucht. Wir befinden uns in den Südstaaten, kurz vorm Bürgerkrieg; Sklaverei ist hier noch eine Alltagserscheinung. Als das Sklavenpaar Django (Jamie Foxx) und seine Frau Broomhilda (Kerry Washington) bei einer Auktion getrennt werden, hat Django nur noch ein Ziel vor Augen: seine Frau wiederzufinden. Die Gelegenheit ergibt sich für den in Fesseln Geschlagenen schneller als erwartet, als er vom früheren Zahnarzt und jetzigen Kopfgeldjäger Dr. Schultz (Christoph Waltz) befreit wird, damit er ihm bei der Identifikation steckbrieflich gesuchter Verbrecher behilflich sei. Zum Dank will Dr. Schultz ihn zum Kopfgeldjäger ausbilden und ihm helfen, seine Frau ausfindig zu machen. Die Zeit von Django Unchained ist gekommen!\r\n\r\nAuf der Suche nach Broomhilda hinterlassen die beiden eine Schneise der Gewalt. Während ihrer Odyssee treffen die beiden Kopfgeldjäger auf Gesindel und rassistische Großgrundbesitzer wie Spencer Gordon Bennet (Don Johnson). Schließlich führt Django und Dr. Schultz die Spur nach Candyland, wo Broomhilda in den Besitz des charismatischen und überaus gefährlichen Calvin Candie (Leonardo DiCaprio) übergegangen ist. Sie erschleichen sich erfolgreich das Vertrauen von Candie. Doch der Haussklave Stephen (Samuel L. Jackson) durchschaut ihr Spiel…', '4', '74', 'DjangoUnchained.jpg', 'http://www.moviepilot.de/movies/django-unchained/trailer', 'Jamie Foxx;\r\nChristoph Waltz;\r\nLeonardo DiCaprio;\r\nKerry Washington;\r\nSamuel L. Jackson;\r\nWalton Goggins', 'Quentin Tarantino', '2013-01-17', '165', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('32', 'Full Metal Jacket', '', 'Parris Island, South Carolina, in den USA der 60er Jahre: Eine Gruppe junger Marines wird für ihren Einsatz in Vietnam ausgebildet und dabei von ihrem sadistischen Ausbilder Sergeant Hartman (R. Lee Ermey) gedemütigt und schikaniert. Full Metal Jacket folgt dem jungen Private Joker (Matthew Modine) durch seine brutale Ausbildung, während der er bereits einen grausamen Vorgeschmack auf das bekommt, was ihn später in Vietnam erwartet.', '4', '79', 'FullMetalJacket.jpg', 'http://www.moviepilot.de/movies/full-metal-jacket/trailer', 'Matthew Modine;\r\nAdam Baldwin;\r\nVincent D\'Onofrio;\r\nR. Lee Ermey;\r\nDorian Harewood;\r\nKevyn Major Howard', 'Stanley Kubrick', '1987-10-08', '116', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('33', 'Aliens', 'Die Rückkehr', 'Nach 57 Jahren im Kälteschlaf wird Ellen Ripley (Sigourney Weaver) endlich gerettet, nachdem sie einst das Alien aus dem ersten Teil erfolgreich besiegt hatte. Doch niemand glaubt ihr ihre Geschichte und nun muss sie in Aliens – Die Rückkehr erfahren, dass sich auf dem Planeten, auf dem sie einst die Alieneier fand, eine Kolonie der Menschen befindet. Erst als plötzlich der Kontakt zur Kolonie abreißt, fängt man an ihr Glauben zu schenken und schickt eine Rettungseinheit zu dem besagtem Planeten um die Aliens ein für allemal zu erledigen. Ripley ist als Expertin natürlich mit von der Partie.\r\n\r\nAnders als beim ersten Teil Alien – Das unheimliche Wesen aus einer fremden Welt, hat nun James Cameron die Regie für Aliens – Die Rückkehr (OT: Aliens) übernommen und einen Film geschaffen, der oft nicht nur als bestes Sequel gefeiert wurde, sondern sogar noch den finanziellen Erfolg des ersten Teils übertreffen konnte. Aliens – Die Rückkehr spielte weltweit über 131 Millionen US-Dollar ein. Außerdem hagelte es geradezu Preise für Aliens – Die Rückkehr, zum Beispiel zwei Oscars und weitere Oscar-Nominierungen, unter anderem für Sigourney Weaver als beste Hauptdarstellerin.', '4', '82', 'Aliens-DieRuekkehr.jpg', 'http://www.moviepilot.de/movies/aliens-die-rueckkehr/trailer', 'Sigourney Weaver;\r\nCarrie Henn;\r\nMichael Biehn;\r\nLance Henriksen;\r\nPaul Reiser;\r\nBill Paxton', 'James Cameron', '1986-11-13', '137', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('34', 'From Dusk Till Dawn', '', 'Die beiden kriminellen Brüder Seth (George Clooney) und Richard Gecko (Quentin Tarantino) befinden sich nach einem blutigen Raubüberfall auf der Flucht. Ihr Ziel ist das angrenzende Mexiko, wo sie vor dem Zugriff der amerikanischen Polizei sicher sein werden. Den stark bewachten Grenzübergang passieren die Brüder mit Hilfe des Geistlichen (Harvey Keitel) samt dessen Kindern (Juliette Lewis und Ernest Liu), die die Geckos als Geiseln nehmen. Da Seth und Richard erst am nächsten Morgen von dem Unterweltboss Carlos (Cheech Marin) abgeholt werden, beschließen sie, in der Titty Twister Bar Rast zu machen, die From Dusk Till Dawn geöffnet ist. Den Geiseln versprechen sie die Freiheit, sobald Carlos sie abgeholt hat. Doch zu später Stunde entpuppen sich die anderen Barbesucher als blutgierige Kreaturen und die Fünf müssen sich als Gruppe beweisen, um diese Nacht zu überleben.', '5', '76', 'FromDuskTillDawn.jpg', 'http://www.moviepilot.de/movies/from-dusk-till-dawn/trailer', 'George Clooney;\r\nQuentin Tarantino;\r\nHarvey Keitel;\r\nJuliette Lewis;\r\nSalma Hayek;\r\nCheech Marin', 'Robert Rodriguez', '1996-07-04', '108', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('35', 'Fluch der Karibik', '', 'Eine Freizeitparkattraktion als Inspiration für einen Film? Nichts scheint im Land der unbegrenzten Möglichkeiten zu abwegig, um nicht doch realisiert zu werden. So geschehen bei Fluch der Karibik: Pirates of the Caribbean ist ursprünglich nämlich eine Themenfahrt im kalifornischen Disneyland.\r\n\r\nIm Mittelpunkt von Fluch der Karibik steht Will Hunter (Orlando Bloom), der vor Jahren als Schiffbrüchiger auf offener See vom Gouverneur der Insel Port Royal und seiner Tochter Elizabeth Swann (Keira Knightley) gerettet wurde. Will ist im Besitz einer Münze, die aus einem verfluchten Piratenschatz stammt. Hinter dieser Münze sind nicht nur die zum ewigen Leben verdammten Piraten um Captain Barbossa (Geoffrey Rush) her, sondern auch der extravagant-trickreiche Jack Sparrow (Johnny Depp), ehemaliger Captain des Piratenschiffs Black Pearl. Als Elizabeth von Barbossas Schergen entführt wird, um durch ihr Blutopfer den Fluch der Karibik zu brechen, bilden Will und Jack Sparrow eine Zweckgemeinschaft, um Elizabeth und die Black Pearl zu befreien.', '3', '75', 'FluchDerKaribik.jpg', 'http://www.moviepilot.de/movies/fluch-der-karibik/trailer', 'Johnny Depp;\r\nGeoffrey Rush;\r\nOrlando Bloom;\r\nKeira Knightley;\r\nJonathan Pryce;\r\nJack Davenport', 'Gore Verbinski', '2003-09-02', '143', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('36', 'Terminator', '', 'Wir schreiben das Jahr 2029. Intelligente Maschinen haben die Herrschaft über die Erde übernommen, die Menschheit ist nahezu eliminiert. Unter Führung des charismatischen Rebellen John Connor leisten die Überlebenden verzweifelten Widerstand. Um diesen ein für allemal zu brechen, entwickeln die Maschinen einen Erfolg versprechenden Plan:\r\n\r\nEin Cyborg aus der Zukunft (Arnold Schwarzenegger), genannt Terminator, wird auf eine Killer-Mission in die Gegenwart geschickt. Er soll Sarah Connor (Linda Hamilton) töten, die junge Frau, die eine entscheidende Rolle für die Menschheit spielen soll, indem sie dem zukünftigen Rebellenführer Leben schenken wird. D.h. wenn sie nicht vorher von der Killermaschine terminiert wird. Sie hat nur eine Chance zu überleben: Kyle Reese (Michael Biehn), einen Freiheitskämpfer, der ebenfalls aus der Zukunft gesandt wurde, um sie zu beschützen. Gemeinsam müssen sie den schier aussichtslosen Kampf gegen den Terminator aufnehmen, soll die zukünftige Menschheit nicht von den Maschinen endgültig ausgelöscht werden.', '4', '80', 'Terminator.jpg', 'http://www.moviepilot.de/movies/the-terminator/trailer', 'Arnold Schwarzenegger; Linda Hamilton; Michael Biehn; Lance Henriksen; Earl Boen; Paul Winfield', 'James Cameron', '1985-03-15', '108', '0.5', '2', '2014-09-21', '0');
INSERT INTO `tbl_film` VALUES ('39', 'Crank Vicky', 'sadflkasdjflkasdjflk', 'Das ist nicht Chev Chelios’ (Jason Statham) Tag: Da er sich entschlossen hat, seine Karriere als Auftragskiller an den Nagel zu hängen, wurde ihm von seinem Konkurrenten Victor eine Dosis tödlichen Gifts verabreicht. Alles, was Chev nun tun kann, um seinen sofortigen Tod zu verhindern, ist seinen Adrenalinspiegel auf ausgesprochen hohem Niveau zu halten. Als hilfreich erweist sich hier, dass ihm eine ganze Menge übler Schergen auf den Fersen sind. Seine Freundin Eve (Amy Smart) scheut sich auch vor öffentlichem Sex nicht, um ihm die lebenswichtige Aufregung zu verschaffen. Und schließlich gibt es noch eine Rechnung mit Victor zu begleichen.', '4', '66', 'NoCover.jpg', 'http://www.moviepilot.de/movies/crank/trailer', 'Jason Statham; Efren Ramirez; Amy Smart; Dwight Yoakam; Jose Pablo Cantillo; Carlos Sanz', 'Mark Neveldine;Brian Taylor', '1970-01-01', '88', '0.5', '1', '1970-01-01', '1');
INSERT INTO `tbl_film` VALUES ('40', 'Pretty Woman', '', 'Der Corporate Raider Edward Lewis, dessen Geliebte ihn gerade verlassen hat, fährt mit dem Lotus Esprit seines Freundes und Anwalts Phil durch Hollywood, um in Beverly Hills im hocheleganten Regent Beverly Wilshire abzusteigen. Da er normalerweise chauffiert wird, und sich daher verfährt, hält er auf dem Hollywood Boulevard an und fragt die Prostituierte Vivian Ward nach dem Weg. Diese glaubt, einen zahlungskräftigen Kunden gefunden zu haben, und weist ihm gegen Geld den Weg. Schließlich lässt Lewis, der mit dem Schaltgetriebe des Sportwagens überfordert ist, sich von Vivian sogar zum Hotel fahren. Dort angekommen, nimmt er sie spontan mit auf sein Zimmer.', '2', '69', '', 'http://www.youtube.de', 'Julia Roberts; Richard Gere; Laura San Giacomo; Hector Elizondo', 'Garry Marshall', '1970-01-01', '119', '0.5', '1', '1970-01-01', '0');
INSERT INTO `tbl_film` VALUES ('41', 'Fickcasting ', 'Spezial', 'Unsere Castingmädels sind die Referenz in Sachen Ficktest! Sie wissen worauf es ankommt und worauf sie achten müssen. Sie notieren sich alles ganz genau, ihnen entgeht kein Detail und wenn ihre Kandidaten mal \'nen Hänger haben, kennen sie die Lösung!', '5', '42', '', '', 'Miss Sackwarze; Erik Specht-Nippel; Agathe Huber', 'Klaus Lümmel', '1970-01-01', '109', '0.5', '1', '1970-01-01', '0');
INSERT INTO `tbl_film` VALUES ('46', 'adsfasdf', 'sadfasdfasdf', 'Noch immer spielt Bruce Wayne (Christian Bale) tagsüber den verantwortungslosen Milliardär, während er nachts als Batman das Verbrechen in Gotham city bekämpft. Unterstützt von Lieutenant Jim Gordon (Gary Oldman) und Staatsanwalt Harvey Dent (Aaron Eckhart) setzt Batman sein Vorhaben fort, das organisierte Verbrechen in Gotham endgültig zu zerschlagen. Doch das schlagkräftige Dreiergespann sieht sich bald einem genialen, immer mächtiger werdenden Kriminellen gegenübergestellt, der als Joker (Heath Ledger) bekannt ist: Er stürzt Gotham ins Chaos und zwingt den Dunklen Ritter immer näher an die Grenze zwischen Gerechtigkeit und Rache. Dent, der inzwischen mit Bruce Waynes Jugendliebe Rachel (Maggie Gyllenhaal) liiert ist, rückt in das Fadenkreuz des Jokers…', '4', '84', '', 'http://www.moviepilot.de/movies/the-dark-knight-2/trailer', 'Christian Bale; Heath Ledger; Aaron Eckhart; Michael Caine; Maggie Gyllenhaal; Gary Oldman', 'Christopher Nolan', '1970-01-01', '152', '0.5', '1', '2014-11-03', '0');
INSERT INTO `tbl_film` VALUES ('48', 'Also ein neuer Film', 'Subtitel', 'asdkjfasdfjöklas', '4', '7', '', 'asdfasdf', 'Erd; Berd; Gollum', 'Jan', '1970-01-01', '123', '0.5', '1', '2014-11-03', '0');

-- ----------------------------
-- Table structure for tbl_fsk
-- ----------------------------
DROP TABLE IF EXISTS `tbl_fsk`;
CREATE TABLE `tbl_fsk` (
  `FSK_ID` int(11) NOT NULL AUTO_INCREMENT,
  `bez` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`FSK_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_fsk
-- ----------------------------
INSERT INTO `tbl_fsk` VALUES ('1', 'keine FSK', '0');
INSERT INTO `tbl_fsk` VALUES ('2', 'FSK 6', '6');
INSERT INTO `tbl_fsk` VALUES ('3', 'FSK 12', '12');
INSERT INTO `tbl_fsk` VALUES ('4', 'FSK 16', '16');
INSERT INTO `tbl_fsk` VALUES ('5', 'FSK 18', '18');

-- ----------------------------
-- Table structure for tbl_genre
-- ----------------------------
DROP TABLE IF EXISTS `tbl_genre`;
CREATE TABLE `tbl_genre` (
  `GE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`GE_ID`),
  UNIQUE KEY `genrebezeichnung` (`name`) USING HASH
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_genre
-- ----------------------------
INSERT INTO `tbl_genre` VALUES ('10', 'Abenteuer');
INSERT INTO `tbl_genre` VALUES ('3', 'Action');
INSERT INTO `tbl_genre` VALUES ('12', 'Animation');
INSERT INTO `tbl_genre` VALUES ('2', 'Drama');
INSERT INTO `tbl_genre` VALUES ('17', 'Erotik');
INSERT INTO `tbl_genre` VALUES ('6', 'Fantasy');
INSERT INTO `tbl_genre` VALUES ('14', 'Horror');
INSERT INTO `tbl_genre` VALUES ('5', 'Komödie');
INSERT INTO `tbl_genre` VALUES ('13', 'Kriegsfilm');
INSERT INTO `tbl_genre` VALUES ('7', 'Kriminalfilm');
INSERT INTO `tbl_genre` VALUES ('18', 'Porno');
INSERT INTO `tbl_genre` VALUES ('1', 'Romanze');
INSERT INTO `tbl_genre` VALUES ('8', 'Science Fiction');
INSERT INTO `tbl_genre` VALUES ('4', 'Thriller');
INSERT INTO `tbl_genre` VALUES ('11', 'Tragikomödie');
INSERT INTO `tbl_genre` VALUES ('9', 'Western');

-- ----------------------------
-- Table structure for tbl_genre_zuordnung
-- ----------------------------
DROP TABLE IF EXISTS `tbl_genre_zuordnung`;
CREATE TABLE `tbl_genre_zuordnung` (
  `GEZ_ID` int(11) NOT NULL AUTO_INCREMENT,
  `FI_ID` int(11) NOT NULL,
  `GE_ID` int(11) NOT NULL,
  PRIMARY KEY (`GEZ_ID`),
  KEY `FI_ID` (`FI_ID`),
  KEY `GE_ID` (`GE_ID`),
  CONSTRAINT `tbl_genre_zuordnung_ibfk_1` FOREIGN KEY (`FI_ID`) REFERENCES `tbl_film` (`FI_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `tbl_genre_zuordnung_ibfk_2` FOREIGN KEY (`GE_ID`) REFERENCES `tbl_genre` (`GE_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
-- Records of tbl_genre_zuordnung
-- ----------------------------
INSERT INTO `tbl_genre_zuordnung` VALUES ('1', '1', '1');
INSERT INTO `tbl_genre_zuordnung` VALUES ('2', '1', '2');
INSERT INTO `tbl_genre_zuordnung` VALUES ('3', '3', '6');
INSERT INTO `tbl_genre_zuordnung` VALUES ('4', '3', '8');
INSERT INTO `tbl_genre_zuordnung` VALUES ('5', '3', '10');
INSERT INTO `tbl_genre_zuordnung` VALUES ('6', '4', '6');
INSERT INTO `tbl_genre_zuordnung` VALUES ('7', '4', '8');
INSERT INTO `tbl_genre_zuordnung` VALUES ('8', '4', '10');
INSERT INTO `tbl_genre_zuordnung` VALUES ('9', '5', '6');
INSERT INTO `tbl_genre_zuordnung` VALUES ('10', '5', '10');
INSERT INTO `tbl_genre_zuordnung` VALUES ('11', '5', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('12', '6', '6');
INSERT INTO `tbl_genre_zuordnung` VALUES ('13', '6', '10');
INSERT INTO `tbl_genre_zuordnung` VALUES ('14', '6', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('15', '7', '6');
INSERT INTO `tbl_genre_zuordnung` VALUES ('16', '7', '10');
INSERT INTO `tbl_genre_zuordnung` VALUES ('17', '7', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('18', '8', '11');
INSERT INTO `tbl_genre_zuordnung` VALUES ('19', '8', '5');
INSERT INTO `tbl_genre_zuordnung` VALUES ('20', '9', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('21', '9', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('22', '10', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('23', '10', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('24', '11', '12');
INSERT INTO `tbl_genre_zuordnung` VALUES ('25', '11', '10');
INSERT INTO `tbl_genre_zuordnung` VALUES ('26', '11', '5');
INSERT INTO `tbl_genre_zuordnung` VALUES ('27', '12', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('28', '12', '13');
INSERT INTO `tbl_genre_zuordnung` VALUES ('29', '13', '2');
INSERT INTO `tbl_genre_zuordnung` VALUES ('30', '13', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('31', '14', '7');
INSERT INTO `tbl_genre_zuordnung` VALUES ('32', '14', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('33', '15', '2');
INSERT INTO `tbl_genre_zuordnung` VALUES ('34', '15', '7');
INSERT INTO `tbl_genre_zuordnung` VALUES ('35', '16', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('36', '16', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('37', '16', '2');
INSERT INTO `tbl_genre_zuordnung` VALUES ('38', '17', '2');
INSERT INTO `tbl_genre_zuordnung` VALUES ('39', '18', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('40', '18', '7');
INSERT INTO `tbl_genre_zuordnung` VALUES ('41', '19', '2');
INSERT INTO `tbl_genre_zuordnung` VALUES ('42', '19', '7');
INSERT INTO `tbl_genre_zuordnung` VALUES ('43', '20', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('44', '20', '8');
INSERT INTO `tbl_genre_zuordnung` VALUES ('45', '21', '9');
INSERT INTO `tbl_genre_zuordnung` VALUES ('46', '22', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('47', '22', '6');
INSERT INTO `tbl_genre_zuordnung` VALUES ('48', '22', '8');
INSERT INTO `tbl_genre_zuordnung` VALUES ('49', '23', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('50', '23', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('51', '24', '13');
INSERT INTO `tbl_genre_zuordnung` VALUES ('52', '25', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('53', '25', '14');
INSERT INTO `tbl_genre_zuordnung` VALUES ('54', '26', '2');
INSERT INTO `tbl_genre_zuordnung` VALUES ('55', '26', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('56', '26', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('57', '26', '7');
INSERT INTO `tbl_genre_zuordnung` VALUES ('58', '27', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('59', '27', '8');
INSERT INTO `tbl_genre_zuordnung` VALUES ('60', '28', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('61', '28', '14');
INSERT INTO `tbl_genre_zuordnung` VALUES ('62', '29', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('63', '29', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('64', '29', '7');
INSERT INTO `tbl_genre_zuordnung` VALUES ('65', '30', '2');
INSERT INTO `tbl_genre_zuordnung` VALUES ('66', '30', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('67', '30', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('68', '31', '9');
INSERT INTO `tbl_genre_zuordnung` VALUES ('69', '32', '2');
INSERT INTO `tbl_genre_zuordnung` VALUES ('70', '32', '13');
INSERT INTO `tbl_genre_zuordnung` VALUES ('71', '33', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('72', '33', '6');
INSERT INTO `tbl_genre_zuordnung` VALUES ('73', '33', '8');
INSERT INTO `tbl_genre_zuordnung` VALUES ('74', '33', '14');
INSERT INTO `tbl_genre_zuordnung` VALUES ('75', '34', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('76', '34', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('77', '34', '5');
INSERT INTO `tbl_genre_zuordnung` VALUES ('78', '34', '6');
INSERT INTO `tbl_genre_zuordnung` VALUES ('79', '34', '14');
INSERT INTO `tbl_genre_zuordnung` VALUES ('80', '35', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('81', '35', '6');
INSERT INTO `tbl_genre_zuordnung` VALUES ('82', '35', '10');
INSERT INTO `tbl_genre_zuordnung` VALUES ('83', '36', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('84', '36', '8');
INSERT INTO `tbl_genre_zuordnung` VALUES ('85', '40', '1');
INSERT INTO `tbl_genre_zuordnung` VALUES ('86', '40', '5');
INSERT INTO `tbl_genre_zuordnung` VALUES ('97', '46', '3');
INSERT INTO `tbl_genre_zuordnung` VALUES ('98', '46', '2');
INSERT INTO `tbl_genre_zuordnung` VALUES ('99', '46', '4');
INSERT INTO `tbl_genre_zuordnung` VALUES ('101', '46', '2');
INSERT INTO `tbl_genre_zuordnung` VALUES ('103', '48', '10');
INSERT INTO `tbl_genre_zuordnung` VALUES ('104', '48', '12');
INSERT INTO `tbl_genre_zuordnung` VALUES ('105', '48', '17');

-- ----------------------------
-- Table structure for tbl_kunde
-- ----------------------------
DROP TABLE IF EXISTS `tbl_kunde`;
CREATE TABLE `tbl_kunde` (
  `KU_ID` int(11) NOT NULL,
  `U_ID` int(11) DEFAULT NULL,
  `date_of_joining` date DEFAULT NULL,
  `account_balance` double DEFAULT NULL,
  PRIMARY KEY (`KU_ID`),
  KEY `User` (`U_ID`),
  CONSTRAINT `User` FOREIGN KEY (`U_ID`) REFERENCES `tbl_user` (`U_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_kunde
-- ----------------------------
INSERT INTO `tbl_kunde` VALUES ('0', '4', '2014-10-27', '0');
INSERT INTO `tbl_kunde` VALUES ('1', '1', '2010-00-00', '12');
INSERT INTO `tbl_kunde` VALUES ('2', '5', '2014-11-01', '1000');

-- ----------------------------
-- Table structure for tbl_mitarbeiter
-- ----------------------------
DROP TABLE IF EXISTS `tbl_mitarbeiter`;
CREATE TABLE `tbl_mitarbeiter` (
  `MA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `U_ID` int(11) NOT NULL,
  `permission` int(11) NOT NULL,
  PRIMARY KEY (`MA_ID`),
  KEY `MAUser` (`U_ID`),
  KEY `Permission` (`permission`),
  CONSTRAINT `MAUser` FOREIGN KEY (`U_ID`) REFERENCES `tbl_user` (`U_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `Permission` FOREIGN KEY (`permission`) REFERENCES `tbl_permissions` (`PER_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_mitarbeiter
-- ----------------------------
INSERT INTO `tbl_mitarbeiter` VALUES ('1', '1', '3');
INSERT INTO `tbl_mitarbeiter` VALUES ('2', '2', '2');
INSERT INTO `tbl_mitarbeiter` VALUES ('3', '4', '2');
INSERT INTO `tbl_mitarbeiter` VALUES ('4', '5', '2');

-- ----------------------------
-- Table structure for tbl_permissions
-- ----------------------------
DROP TABLE IF EXISTS `tbl_permissions`;
CREATE TABLE `tbl_permissions` (
  `PER_ID` int(11) NOT NULL,
  `bez` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`PER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_permissions
-- ----------------------------
INSERT INTO `tbl_permissions` VALUES ('0', 'Not Logged In');
INSERT INTO `tbl_permissions` VALUES ('1', 'Logged In');
INSERT INTO `tbl_permissions` VALUES ('2', 'Mitarbeiter');
INSERT INTO `tbl_permissions` VALUES ('3', 'Administrator');

-- ----------------------------
-- Table structure for tbl_reservierung
-- ----------------------------
DROP TABLE IF EXISTS `tbl_reservierung`;
CREATE TABLE `tbl_reservierung` (
  `RES_ID` int(11) NOT NULL,
  `KU_ID` int(11) NOT NULL,
  `DVD_ID` int(11) NOT NULL,
  `reservierungsdatum` date DEFAULT NULL,
  `gueltig` int(11) DEFAULT NULL,
  PRIMARY KEY (`RES_ID`),
  KEY `reservierungKunde` (`KU_ID`),
  KEY `dieDVD` (`DVD_ID`),
  CONSTRAINT `dieDVD` FOREIGN KEY (`DVD_ID`) REFERENCES `tbl_dvd` (`DVD_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `reservierungKunde` FOREIGN KEY (`KU_ID`) REFERENCES `tbl_kunde` (`KU_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_reservierung
-- ----------------------------

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `U_ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `street` varchar(255) NOT NULL,
  `street_nr` int(11) NOT NULL,
  `zip_code` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `birthdate` date NOT NULL,
  `email` varchar(255) NOT NULL,
  `accountnumber` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`U_ID`),
  UNIQUE KEY `Email` (`email`),
  UNIQUE KEY `Accountnr` (`accountnumber`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tbl_user
-- ----------------------------
INSERT INTO `tbl_user` VALUES ('1', 'Koschke', 'Jan', 'Erbastraße', '26', '70736', 'Fellbach', 'cc03e747a6afbbcbf8be7668acfebee5', '0000-00-00', 'jan', '0000000027');
INSERT INTO `tbl_user` VALUES ('2', 'Brambor', 'Heiko', '-', '0', '-', '-', 'cc03e747a6afbbcbf8be7668acfebee5', '0000-00-00', 'heiko.brambor@t-online.de', '0000000028');
INSERT INTO `tbl_user` VALUES ('3', 'Strobel', 'Simon', '-', '0', '-', '-', 'cc03e747a6afbbcbf8be7668acfebee5', '0000-00-00', 'strobesim@googlemail.com', '0000000029');
INSERT INTO `tbl_user` VALUES ('4', 'Koschke', 'Susanne', 'Erbastraße', '26', '70736', 'Fellbach', 'cc03e747a6afbbcbf8be7668acfebee5', '1965-10-01', 's-koschke@t-online.de', '0000000030');
INSERT INTO `tbl_user` VALUES ('5', 'Lohrer', 'Viktoria', 'Nelly-Sachs-Straße', '26', '70736', 'Fellbach', 'cc03e747a6afbbcbf8be7668acfebee5', '1996-12-18', 'vicky', '0000000040');
