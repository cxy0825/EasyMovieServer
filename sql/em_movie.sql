/*
 Navicat Premium Data Transfer

 Source Server         : root
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : em_movie

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 10/12/2022 19:50:26
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for cinema
-- ----------------------------
DROP TABLE IF EXISTS `cinema`;
CREATE TABLE `cinema`  (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `cinema_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '电影院名字',
  `x` decimal(10, 6) NOT NULL COMMENT '经度',
  `y` decimal(10, 6) NOT NULL COMMENT '纬度',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `is_delete` int NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '电影院信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of cinema
-- ----------------------------
INSERT INTO `cinema` VALUES (1, '绍兴市越城区鲁迅电影城(县前街店)', 30.007630, 120.589020, '2022-11-29 11:40:07', '2022-11-29 11:40:07', 0);
INSERT INTO `cinema` VALUES (2, '绍兴市柯桥区绍兴蓝天国际影城(群贤路店)', 30.088890, 120.497500, '2022-11-29 11:42:38', '2022-11-29 11:42:38', 0);
INSERT INTO `cinema` VALUES (3, '绍兴市柯桥区万达影城(万达广场绍兴柯桥店)', 30.098710, 120.517770, '2022-11-29 11:44:03', '2022-11-29 11:44:03', 0);
INSERT INTO `cinema` VALUES (4, '绍兴市越城区绍兴咸亨国际影城(鲁迅中路店)', 29.999600, 120.588290, '2022-11-29 11:44:25', '2022-11-29 11:44:25', 0);

-- ----------------------------
-- Table structure for film
-- ----------------------------
DROP TABLE IF EXISTS `film`;
CREATE TABLE `film`  (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `cinema_ID` bigint NULL DEFAULT NULL COMMENT '电影院ID',
  `film_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '影片名字',
  `about` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '影片简介',
  `release_time` datetime(0) NULL DEFAULT NULL COMMENT '电影上映时间',
  `duration` double NULL DEFAULT NULL COMMENT '电影时长',
  `score` double NULL DEFAULT 0 COMMENT '电影评分',
  `credate_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `is_delete` int NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `cinema_ID`(`cinema_ID`) USING BTREE,
  CONSTRAINT `cinema_ID` FOREIGN KEY (`cinema_ID`) REFERENCES `cinema` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '电影影片信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of film
-- ----------------------------
INSERT INTO `film` VALUES (1, 2, '名侦探柯南：万圣节的新娘 （名探偵コナン ハロウィンの花嫁）', '涩谷的万圣节狂欢庆典竟然变成了一场可怕的灾难！柯南和安室透深陷危机，佐藤刑警和高木刑警的婚礼现场惊现炸弹客！一桩关于三年前警校五人组的迷案即将揭开…', '2022-11-18 18:00:00', 110, 9.1, '2022-11-29 13:39:21', '2022-12-10 19:39:56', 0);
INSERT INTO `film` VALUES (2, 1, '万里归途', '电影根据真实事件改编。 努米亚共和国爆发战乱，前驻地外交官宗大伟（张译 饰）与外交部新人成朗（王俊凯 饰）受命前往协助撤侨。任务顺利结束，却得知还有一批被困同胞，正在白婳（殷桃 饰）的带领下，前往边境撤离点。情急之下，两人放弃了回家机会，逆行进入战区。赤手空拳的外交官，穿越战火和荒漠，面对反叛军的枪口，如何带领同胞走出一条回家之路……', '2022-09-30 18:00:00', 137, 9.6, '2022-11-29 13:40:26', '2022-12-10 13:40:06', 0);
INSERT INTO `film` VALUES (3, 1, '扫黑行动\r\nTHE TIPPING POINT', '改编自真实案件！周一围秦海璐张智霖联袂出演，扫黑除恶，重拳出击！女大学生离奇坠楼，刚调任到岗的刑侦支队副队长成锐（周一围 饰）被副局长杜于林（王劲松 饰）指派追查背后隐情，发现案件与套路贷、暴力催讨等黑势力犯罪有关，本市企业家安亦明（曾志伟 饰）被列为重点侦查对象，成锐想进一步调查却被要求尽快结案，究竟是谁在充当“保护伞”？城市危机四伏，安亦明之妻周彤（秦海璐 饰）、经济学教授赵羡鱼（张智霖 饰）纷纷身陷其中，为了还人民安宁，成锐和同事们突破难关追踪到底，却不想背后还有更大阴谋。真相究竟为何？他们能否将隐藏在黑暗中的罪恶一扫而尽？', '2022-11-11 18:00:00', 99, 8.1, '2022-11-29 13:52:14', '2022-12-10 13:40:07', 0);
INSERT INTO `film` VALUES (4, 3, '阿凡达：水之道\r\nAvatar ：The Way Of Water', '《阿凡达：水之道》的剧情承接自第一部的5年之后。曾经的地球残疾军人杰克·萨利，如今已经是潘多拉星球纳美族一方部族的族长，并且与爱妻娜塔莉共同育有一对可爱的儿女，日子过得平淡而充实。然而某天，有个部族的兄弟在海岸附近巡逻时遭到利器割喉身亡。通过现场勘查，以及作为前海军陆战队员的敏锐直觉，杰克判断已经有人类的阿凡达混入了部落……', '2022-12-16 00:00:00', 190, 9, '2022-11-29 13:53:59', '2022-12-10 13:40:09', 0);

-- ----------------------------
-- Table structure for film_info
-- ----------------------------
DROP TABLE IF EXISTS `film_info`;
CREATE TABLE `film_info`  (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `film_ID` bigint NULL DEFAULT NULL COMMENT '电影ID',
  `video_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '宣传片地址',
  `poster_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '海报地址',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `is_delete` int NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of film_info
-- ----------------------------
INSERT INTO `film_info` VALUES (1, 1, 'http://localhost:8920/video/7dd2b0616ec6421c8ad8d4dc41aabad6.mp4', 'http://localhost:8920/image/8085138d899b4e1d8edcbb54c9f00d42.png', '2022-11-29 13:48:47', '2022-12-10 19:32:38', 0);
INSERT INTO `film_info` VALUES (2, 2, 'https://vod.pipi.cn/fec9203cvodtransbj1251246104/74f1e8af387702306416447219/v.f42906.mp4', 'https://p0.pipi.cn/mmdb/25bfd6ddb53c7e5015d23c5bc24d876c03d41.jpg?imageView2/1/w/464/h/644', '2022-11-29 13:50:49', '2022-11-29 13:50:49', 0);
INSERT INTO `film_info` VALUES (3, 3, 'https://vod.pipi.cn/fec9203cvodtransbj1251246104/d2ef572f243791575471001765/v.f42905.mp4', 'https://p0.pipi.cn/mmdb/25bfd6922ffc7e50c8af3397dee2d43a3e265.jpg?imageView2/1/w/464/h/644', '2022-11-29 13:52:37', '2022-11-29 13:52:37', 0);
INSERT INTO `film_info` VALUES (4, 4, 'https://vod.pipi.cn/fec9203cvodtransbj1251246104/9907a177243791576334091279/v.f42906.mp4', 'https://p0.pipi.cn/mmdb/25bfd692e7a51b16bd39dd00dc148aa4d88c4.jpg?imageView2/1/w/464/h/644', '2022-11-29 13:54:35', '2022-11-29 13:54:35', 0);

-- ----------------------------
-- Table structure for movie_set
-- ----------------------------
DROP TABLE IF EXISTS `movie_set`;
CREATE TABLE `movie_set`  (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `movie_house_id` bigint NOT NULL COMMENT '电影放映厅ID',
  `film_id` bigint NOT NULL COMMENT '影片ID',
  `cinema_ID` bigint NOT NULL COMMENT '电影院ID',
  `movie_start_time` datetime(0) NOT NULL COMMENT '电影开始时间',
  `movie_end_time` datetime(0) NOT NULL COMMENT '电影结束时间',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `is_delete` int NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `影片ID`(`film_id`) USING BTREE,
  INDEX `放映厅ID`(`movie_house_id`) USING BTREE,
  INDEX `电影院ID`(`cinema_ID`) USING BTREE,
  CONSTRAINT `影片ID` FOREIGN KEY (`film_id`) REFERENCES `film` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `放映厅ID` FOREIGN KEY (`movie_house_id`) REFERENCES `moviehouse` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `电影院ID` FOREIGN KEY (`cinema_ID`) REFERENCES `cinema` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '排片信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of movie_set
-- ----------------------------
INSERT INTO `movie_set` VALUES (1, 1, 1, 1, '2022-11-30 14:02:32', '2022-11-30 15:52:32', '2022-11-29 14:01:56', '2022-12-09 15:48:06', 0);
INSERT INTO `movie_set` VALUES (2, 1, 2, 1, '2022-11-29 14:02:25', '2022-11-30 14:02:31', '2022-11-29 14:02:35', '2022-12-09 14:18:08', 0);
INSERT INTO `movie_set` VALUES (3, 2, 4, 1, '2022-11-29 14:03:27', '2022-11-29 14:03:29', '2022-11-29 14:03:33', '2022-11-29 22:12:14', 0);
INSERT INTO `movie_set` VALUES (4, 9, 2, 2, '2022-11-29 14:03:48', '2022-11-29 14:03:50', '2022-11-29 14:03:51', '2022-11-29 14:03:51', 0);
INSERT INTO `movie_set` VALUES (5, 13, 4, 3, '2022-11-29 14:04:03', '2022-11-29 14:04:06', '2022-11-29 14:04:08', '2022-11-29 14:04:08', 0);
INSERT INTO `movie_set` VALUES (6, 16, 4, 4, '2022-11-29 14:04:22', '2022-11-29 14:04:26', '2022-11-29 14:04:39', '2022-11-29 14:04:39', 0);
INSERT INTO `movie_set` VALUES (7, 14, 4, 3, '2022-11-29 14:05:24', '2022-11-29 14:05:26', '2022-11-29 14:05:28', '2022-11-29 14:05:28', 0);
INSERT INTO `movie_set` VALUES (8, 7, 1, 2, '2022-11-22 14:05:49', '2022-11-30 14:05:52', '2022-11-29 14:05:55', '2022-11-29 14:05:55', 0);
INSERT INTO `movie_set` VALUES (9, 16, 2, 4, '2022-11-29 14:06:33', '2022-11-29 14:06:36', '2022-11-29 14:06:37', '2022-11-29 14:06:37', 0);
INSERT INTO `movie_set` VALUES (10, 16, 3, 4, '2022-11-29 14:06:51', '2022-11-29 16:06:54', '2022-11-29 14:07:00', '2022-11-29 14:07:00', 0);
INSERT INTO `movie_set` VALUES (11, 1, 1, 1, '2022-12-09 14:02:32', '2022-12-09 15:52:32', '2022-12-09 15:58:23', '2022-12-09 15:58:23', 0);

-- ----------------------------
-- Table structure for moviehouse
-- ----------------------------
DROP TABLE IF EXISTS `moviehouse`;
CREATE TABLE `moviehouse`  (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `cinema_ID` bigint NOT NULL COMMENT '电影院ID',
  `movie_house_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '放映厅名字',
  `row_num` int NULL DEFAULT NULL COMMENT '有多少排',
  `col_num` int NULL DEFAULT NULL COMMENT '有多少列',
  `state` int NULL DEFAULT NULL COMMENT '放映厅状态 0表示停用 1表示可以使用 2表示维修中',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `is_delete` int NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `电影院ID_2`(`cinema_ID`) USING BTREE,
  CONSTRAINT `电影院ID_2` FOREIGN KEY (`cinema_ID`) REFERENCES `cinema` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '电影放映厅表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of moviehouse
-- ----------------------------
INSERT INTO `moviehouse` VALUES (1, 1, '测试修改323', 10, 10, 1, '2022-11-29 11:16:39', '2022-12-06 09:46:49', 0);
INSERT INTO `moviehouse` VALUES (2, 1, '电影放映厅2', 11, 10, 1, '2022-11-29 11:17:18', '2022-12-10 13:38:33', 0);
INSERT INTO `moviehouse` VALUES (3, 1, '电影放映厅3', 10, 10, 1, '2022-11-29 11:17:18', '2022-12-10 13:38:35', 0);
INSERT INTO `moviehouse` VALUES (4, 1, '电影放映厅4', 11, 10, 1, '2022-11-29 11:17:18', '2022-12-10 13:38:37', 0);
INSERT INTO `moviehouse` VALUES (5, 1, '电影放映厅5', 10, 10, 1, '2022-11-29 11:17:18', '2022-12-04 16:13:42', 0);
INSERT INTO `moviehouse` VALUES (6, 1, '电影放映厅6', 6, 10, 1, '2022-11-29 11:17:18', '2022-12-04 16:13:43', 0);
INSERT INTO `moviehouse` VALUES (7, 2, '电影放映厅7', 8, 10, 1, '2022-11-29 11:17:18', '2022-11-30 18:30:46', 0);
INSERT INTO `moviehouse` VALUES (8, 2, '电影放映厅8', 10, 10, 1, '2022-11-29 11:17:18', '2022-11-30 18:30:26', 0);
INSERT INTO `moviehouse` VALUES (9, 2, '电影放映厅9', 10, 10, 1, '2022-11-29 11:17:18', '2022-11-30 18:30:26', 0);
INSERT INTO `moviehouse` VALUES (10, 2, '电影放映厅10', 12, 10, 1, '2022-11-29 11:17:18', '2022-11-30 18:30:50', 0);
INSERT INTO `moviehouse` VALUES (11, 2, '电影放映厅11', 10, 10, 1, '2022-11-29 11:17:18', '2022-11-30 18:30:26', 0);
INSERT INTO `moviehouse` VALUES (12, 2, '电影放映厅12', 10, 10, 0, '2022-11-29 11:17:18', '2022-12-04 15:31:26', 0);
INSERT INTO `moviehouse` VALUES (13, 3, '电影放映厅13', 5, 10, 0, '2022-11-29 11:17:18', '2022-12-04 15:31:24', 0);
INSERT INTO `moviehouse` VALUES (14, 3, '电影放映厅14', 10, 10, 1, '2022-11-29 11:17:18', '2022-11-30 18:30:26', 0);
INSERT INTO `moviehouse` VALUES (15, 2, '电影放映厅15', 9, 10, 1, '2022-11-29 11:17:18', '2022-11-30 18:30:57', 0);
INSERT INTO `moviehouse` VALUES (16, 4, '电影放映厅16', 10, 10, 0, '2022-11-29 11:17:18', '2022-12-04 15:31:22', 0);
INSERT INTO `moviehouse` VALUES (17, 1, '这个是给ID为1的电影院增加的影厅', 10, 10, 1, '2022-11-30 16:13:39', '2022-12-05 19:57:20', 0);
INSERT INTO `moviehouse` VALUES (18, 1, 'jisoa', 8, 8, 1, '2022-12-05 20:08:20', '2022-12-05 20:08:20', 0);
INSERT INTO `moviehouse` VALUES (19, 1, '22', 2, 2, 1, '2022-12-06 18:36:52', '2022-12-06 18:36:52', 0);
INSERT INTO `moviehouse` VALUES (20, 1, '33', 2, 3, 1, '2022-12-06 18:37:03', '2022-12-06 18:37:03', 0);
INSERT INTO `moviehouse` VALUES (21, 1, '44', 2, 3, 1, '2022-12-06 18:37:11', '2022-12-06 18:37:11', 0);

-- ----------------------------
-- Table structure for performer
-- ----------------------------
DROP TABLE IF EXISTS `performer`;
CREATE TABLE `performer`  (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `performer_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '演员名字',
  `performer_pic_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'https://www.maoyan.com/films/celebrity/992090' COMMENT '演员照片地址',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '演员出生日期',
  `performer_about` varchar(999) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '演员简介',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `is_delete` int NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 18 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '演员信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of performer
-- ----------------------------
INSERT INTO `performer` VALUES (1, '\r\n高山南\r\n\r\nMinami Takayama', 'https://www.maoyan.com/films/celebrity/992090', '1964-05-05 00:00:00', '高山南， 日本著名女性声优，东京都足立区出身。血型B型。与日本歌手 永野椎菜合组歌唱组合 TWO-MIX。声优代表作品有《 名侦探柯南》 江户川柯南、《 乱马1/2》 天道靡、《 剑勇传说》 铁剑、《 忍者乱太郎》 猪名寺乱太郎、《 通灵王》 麻仓好、《 数码兽组合战争》 工藤大器、《 超级弹丸论破2》 日向创、 神座出流、《战姬绝唱》天羽奏、《 仙乐传说》 米特斯、《 阴阳师》 跳跳弟弟。2005年5月5日，高山南于生日当天与《 名侦探柯南》的作者 青山刚昌结婚，后于2007年12月9日离婚。', '2022-11-29 14:11:21', '2022-11-29 14:11:21', 0);
INSERT INTO `performer` VALUES (2, '\r\n山崎和佳奈\r\n\r\nWakana Yamazaki', 'https://www.maoyan.com/films/celebrity/992090', '1965-03-21 00:00:00', '山崎和佳奈，1965年3月21日出生于日本神奈川县横滨市，在京都府长大，日本女性声优，隶属青二Production。代表配音作品有《 名侦探柯南》 毛利兰、《橘子酱男孩》秋月茗子、《 GS美神极乐大作战》玛丽亚、《 格斗美神武龙》毛兰以及《海贼王》诺琪高等', '2022-11-29 14:11:49', '2022-11-29 14:11:49', 0);
INSERT INTO `performer` VALUES (3, '\r\n小山力也\r\n\r\nRikiya Koyama', 'https://www.maoyan.com/films/celebrity/992090', '1963-12-18 00:00:00', '小山力也，1963年12月18日出生于京都府 京都市，日本男性配音演员、演员，毕业于 立命馆大学、 桐朋学园大学，所属事务所为 俳优座。代表作有《 第一神拳》鹰村守、《 传颂之物》哈克奥罗、《 名侦探柯南》 毛利小五郎（第二代）、《 Fate/Zero》 卫宫切嗣、《 王者天下》 王骑、《 血界战线》克劳斯·V·莱因赫兹、《 潮与虎》 阿虎、《 刃牙》 烈海王、《 宇宙人形17》D·D等。', '2022-11-29 14:14:18', '2022-11-29 14:13:29', 0);
INSERT INTO `performer` VALUES (6, '三木真一郎\r\n\r\nShinichirô Miki', 'https://www.maoyan.com/films/celebrity/992090', '1968-03-18 00:00:00', '三木真一郎（みき しんいちろう，Miki Shin-ichiro），日本男性声优，东京都出身，所属事务所为 81 PRODUCE。代表作品有《 头文字D》 藤原拓海、《 死神》 浦原喜助、《宝可梦》 小次郎、《 Fate/stay night》 Assassin、《 机动战士敢达00》 洛克昂·史特拉托斯等。', '2022-11-29 14:14:37', '2022-11-29 14:14:14', 0);
INSERT INTO `performer` VALUES (7, '张译\r\n\r\nYi Zhang', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7acf3e16bd90c6aef91ca3c86.jpg?imageView2/1/w/464/h/644', '1978-02-17 00:00:00', '张译，1997年至2006年服役于北京军区政治部战友话剧团。2006年，主演电视剧《士兵突击》。2009年，主演抗战剧《我的团长我的团》，并凭借该角色获得2009中国电视榜“最深入人心电视形象”的荣誉；9月，主演孔笙执导的抗战剧《生死线》。2014年9月，主演打拐题材电影《亲爱的》，并凭借饰演的韩德忠一角获得第30届中国电影金鸡奖最佳男配角奖。2015年5月，主演电影《山河故人》。2016年9月，主演悬疑影片《追凶者也》，并凭借该片获得第八届中国电影导演协会年度男演员奖。2017年6月，凭借电视剧《鸡毛飞上天》获第23届上海电视节白玉兰奖最佳男主角奖。2018年2月16日，主演中国首部现代化海军题材电影《红海行动》全国上映，获得36亿的票房成绩。2019年1月，主演的剧情片《一秒钟》入围柏林电影节主竞赛单元，电影《攀登者》《我和我的祖国》于国庆档上映 。', '2022-11-29 14:17:35', '2022-11-29 14:17:35', 0);
INSERT INTO `performer` VALUES (8, '王俊凯\r\n\r\nJunkai Wang', 'https://p0.pipi.cn/basicdata/25bfd63351b0e12ff757e2e3daea18436094b.jpg?imageView2/1/w/464/h/644', '1999-09-21 00:00:00', '王俊凯，中国内地流行乐男歌手、影视演员、TFBOYS队长。2013年8月6日，与王源、易烊千玺三人组成组合TFBOYS正式出道，陆续发行《青春修炼手册》等单曲。2015年6月22日以在2014年9月21日发表的生日博文获得吉尼斯世界纪录里转发量最多的一条微博。后进军影视界，参演了张艺谋导演的中美合拍电影《长城》，出演小皇帝一角；2017年4月首个个人综艺《高能少年团》开播；6月受邀出席2018春夏米兰男装周，并担任Dolce&Gabbana品牌史上第一个为走秀开场并领衔闭幕的明星；8月22日，由韩杰执导改编自日本著名作家东野圭吾的奇幻电影《解忧杂货店》宣布王俊凯饰演男主角小波；9月登上《时尚芭莎》九月刊下慈善双封，成为其史上年龄最小的单人封面人物创下三大纪录；发行十八岁单曲《焕蓝未来·KarryOn》，并成立“焕蓝未来公益金”。9月，进入北京电影学院表演系本科班就读。12月6日，被宣布担任电视剧《天坑鹰猎》的男主角张保庆，同月荣获《中国新闻周刊》“影响中国”2017年度演艺人物。2018年4月，被联合国环境规划署任命为“联合国环境署亲善大使” ；8月，主演的自然题材励志剧《天坑鹰猎》在优酷网播出，他在剧中饰演张保庆。该剧在优酷网播放总量突破41亿，并于同年9月在东方卫视上星播出。 2019年，王俊凯在北京凯迪拉克中心举办首场“无边界”个人演唱会 ；同年，担任“2019年APEC未来之声青年大使” ，并凭借奇幻片《解忧杂货店》中小波一角，获得第17届中国电影表演艺术学会奖新人奖 。2020年1月，登上2020年中央电视台春节联欢晚会 。2021年2月11日，参加《2021年中央广播电视总台春节联欢晚会》，演唱歌曲《瑞雪平安图》。 2022年2月2日，参加《百花迎春》晚会，演唱歌曲《灯火里的中国》 。', '2022-11-29 14:18:04', '2022-11-29 14:18:04', 0);
INSERT INTO `performer` VALUES (9, '殷桃\r\n\r\nYin Tao', 'https://p0.pipi.cn/basicdata/25bfd6d7537c69af33e5bc5b93ff82339da4c.jpg?imageView2/1/w/464/h/644', '1979-12-06 00:00:00', '殷桃，演员、主持人，毕业于解放军艺术学院戏剧系，中国人民解放军空军政治部电视艺术中心演员。2002年，因主演《我在天堂等你》荣获第五届中国话剧金狮奖表演奖、第八届中国戏剧节曹禺戏剧奖优秀表演奖、第十五届上海白玉兰奖优秀女主角奖等多个奖项。2003年，出演电视剧《历史的天空》。2004年，因在《搭错车》中饰演阿美一角，入围金鹰奖优秀女演员。2006年，主演《幸福像花儿一样》赢得关注。2009年，主演尤小刚导演作品《杨贵妃秘史》。2011年，在《武则天秘史》中饰演青年武则天。2013年12月26日，在第29届飞天奖颁奖盛典中因电视剧《温州一家人》《延安爱情》获第29届飞天奖优秀女演员大奖。2014年，主演中国版话剧《罗密欧与朱丽叶》。2015年4月16日，主演的都市生活喜剧《我为儿孙当北漂》播出；8月10日，主演的古装历史剧《大宋传奇之赵匡胤》播出。2017年3月，主演的都市商业剧《鸡毛飞上天》播出，并凭借该剧获得第23届上海电视节白玉兰奖最佳女演员，完成电视剧三大奖项“视后”满贯；11月21日，主演的电影《不成问题的问题》上映；同年，其出演的电影《临时演员》全国上映。2018年1月1日，获得2017安徽卫视国剧盛典年度匠心剧星奖；5月8日，主演的年代情感剧《爱情的边疆》播出；9月3日，主演的民国风情电影《那些女人》上映。其他作品有《大道通天》《苍穹之昴》《假如幸福来临》《人民检察官》等。', '2022-11-29 14:19:03', '2022-11-29 14:19:03', 0);
INSERT INTO `performer` VALUES (10, '成泰燊\r\n\r\nTaishen Cheng', 'https://p0.pipi.cn/basicdata/25bfd6d753706dddd211e5283a86d97028638.jpg?imageView2/1/w/464/h/644', '1971-06-12 00:00:00', '成泰燊（shēn），1971年6月12日出生于山西省吕梁市交城县，毕业于 中央戏剧学院，中国内地男演员。 1997年，成泰燊毕业后分配到 西安电影制片厂导演室工作。2001年，凭借主演的首部电影《 海鲜》获得法国 南特三大洲电影节最佳男演员。2004年，因主演电影《 世界》而提名 第61届威尼斯国际电影节最佳男主角。2005年，出演中芬首部合拍电影《 玉战士》。2006年，主演的电影《 完美生活》入围 第65届威尼斯国际电影节地平线单元。2007年，凭借电影《 左右》提名 第58届柏林国际电影节最佳男主角。2009年，出演的影片《 美错》入围 第83届奥斯卡金像奖最佳外语片和 第68届美国电影电视金球奖最佳外语片。2010年6月，主演的两部电影《大地》和《 马文的战争》同时入围 第十三届上海国际电影节传媒大奖；同年，主演的古装历史剧《 天地民心》播出。2012年，凭借主演的电影《 爱的替身》提名 第60届圣塞巴斯蒂安国际电影节最佳男主角。2014年，参演青春文艺电影《 左耳》。2015年，凭借前史体裁影片《 黄克功案件》获得 第十五届电影表演艺术学会奖。2016年，领衔主演廉政历史剧《 于成龙》。2017年6月，参演的动作犯罪电影《 引爆者》成为第20届上海国际电影节闭幕片；12月18日，出演的古装权谋剧《 琅琊榜之风起长林》播出；12月22日，出演的古装奇幻悬疑电影《 妖猫传》上映。2018年，主演近代传奇剧《 远大前程》。2020年，主演电视剧《 巡回检察组》播出。2022年，主演的现实主义题材大剧《 人世间》播出。', '2022-11-29 14:19:36', '2022-11-29 14:19:36', 0);
INSERT INTO `performer` VALUES (11, '萨姆·沃辛顿\r\n\r\nSam Worthington', 'https://p0.pipi.cn/basicdata/25bfd6d7537c69cbae07accc616d0e32cbac4.jpg?imageView2/1/w/464/h/644', '1976-08-02 00:00:00', '萨姆·沃辛顿，演员、制片人、导演、编剧、摄影师、作曲、监制、配音，毕业于澳大利亚国家戏剧艺术学院。2000年，因出演个人第一部电影《踢到铁板》而正式进入娱乐圈。2002年，出演战争片《哈特的战争》。2004年，主演剧情片《孤零飘落燕》，并凭借该片获得澳大利亚电影学院最佳男主角奖。2007年，主演科幻片《阿凡达》。2008年，凭借科幻动作片《终结者2018》获得第11届青少年选择奖最佳电影男新人提名。2010年，凭借奇幻冒险片《诸神之战》获得第12届青少年选择奖最佳奇幻电影男演员提名。2012年，主演动作片《诸神之怒》。2016年，主演历史战争片《血战钢锯岭》。2018年10月12日，主演的电影《超能泰坦》在中国上映。其他作品有《杀手的祷告》《陋室》《独活》等。', '2022-11-29 14:20:58', '2022-11-29 14:20:58', 0);
INSERT INTO `performer` VALUES (12, '佐伊·索尔达娜\r\n\r\nZoe Saldana', 'https://p0.pipi.cn/basicdata/25bfd6d753706d339ed7c3b6fd747e0b24e47.jpg?imageView2/1/w/464/h/644', '1978-06-19 00:00:00', '佐伊·索尔达娜，多米尼加共和国移民，1978年生于美国新泽西州，后在纽约皇后区长大的佐伊·索尔达娜拥有芭蕾女生的优雅气质，却在2003年凭借《加勒比海盗：黑珍珠号的诅咒》中性格强硬的女海盗Anamaria一角而为观众所熟悉。 佐伊·索尔达娜的父亲在她九岁时由于车祸去世，之后她同母亲搬回了多米尼加并从那时开始学习芭蕾，进入了该国最具盛名的一所舞蹈学校，高二之际回到美国后她开始把精力转移到表演领域。 在小有名气后，她又接下了《幸福终点站》、《下流高校》以及《男生女生黑白配》等电影。2010年，她凭借科幻电影《阿凡达》获得第36届土星奖最佳女主角奖 ；2012年，凭借动作电影《致命黑兰》获得第14届青少年选择奖最佳动作电影女演员奖；2014年，主演科幻动作电影《银河护卫队》并凭借该片入围第20届评论家选择奖最佳动作片女演员奖 ；2016年，由其出演的电影《星际迷航3：超越星辰》上映 ；2018年，出演科幻动作电影《复仇者联盟3：无限战争》；2019年4月26日，由其出演的电影《复仇者联盟4：终局之战》上映。', '2022-11-29 14:21:50', '2022-11-29 14:21:50', 0);
INSERT INTO `performer` VALUES (13, '西格妮·韦弗\r\n\r\nSigourney Weaver', 'https://p0.pipi.cn/basicdata/25bfd6d7537c6957e23ba3cc98a7146556543.jpg?imageView2/1/w/464/h/644', '1949-10-08 00:00:00', '西格妮·韦弗（Sigourney Weaver），1949年10月8日生于美国 纽约州纽约市的 曼哈顿，美国影视女演员、制作人。主要作品有《 异形》。 2022年8月20日，凭借《珍妮热线》获得 第12届北京国际电影节最佳女配角奖。', '2022-11-29 14:22:16', '2022-11-29 14:22:16', 0);
INSERT INTO `performer` VALUES (14, '史蒂芬·朗\r\n\r\nStephen Lang', 'https://p0.pipi.cn/basicdata/25bfd6d7537c69e5bc8d33a2515529275ce80.jpg?imageView2/1/w/464/h/644', '1952-07-11 00:00:00', '史蒂芬·朗（Stephen Lang），1952年7月11日生于美国纽约，美国影视男演员，代表作品《 孽欲杀人夜》等。 1983年，一直默默无闻的史蒂芬在 达斯汀·霍夫曼获奖舞台剧《推销员之死》中脱颖而出；2009年史蒂芬·朗的成绩颇为亮眼，他在《 公众之敌》、《 以眼杀人》及超级大片《 阿凡达》中都有演出。', '2022-11-29 14:22:40', '2022-11-29 14:22:40', 0);
INSERT INTO `performer` VALUES (15, '周一围\r\n\r\nYiwei Zhou', 'https://p0.pipi.cn/basicdata/25bfd69251b923c7ed5015671e7a3959dd1c3.jpg?imageView2/1/w/464/h/644', '1982-08-24 00:00:00', '周一围，出生于湖南省湘西土家族苗族自治州吉首市，中国内地男演员，毕业于北京电影学院2000级表演高职班。2004年，因在海岩剧《阳光像花一样绽放》中饰演男一号刘川而被观众熟识。2006年，主演情感剧《谢谢你曾经爱过我》。2007年，获新浪网络盛典年度新人 。2008年，主演农村剧《八百里洞庭我的家》。2010年，与林心如、李依晓共同参演都市情感剧《独家披露》。2012年，与马苏共同主演年代悬疑剧《烈焰》。2013年，出演内地首部律政作品《金牌律师》；同年，出演电影《绣春刀》中“丁修”一角，深入人心。2015年，主演傅东育执导的历史传奇剧《少林问道》。2016年，参演由曹盾执导的东方魔幻史诗巨制《九州·海上牧云记》。2017年6月，其主演的电影《我不是药神》杀青；同年，参加浙江卫视《演员的诞生》节目，摘得年度总冠军；同年，出席米兰时装周；同年，参演电视剧《海上牧云记》和电影《建军大业》。2018年，主演改编自马伯庸小说的电视剧《长安十二时辰》。2019年，与钟汉良、钟楚曦等主演电影《解放·终局营救》；同年，与章子怡等主演电视剧《上阳赋》；11月，与袁姗姗、曹曦文、迟嘉主演的网剧《焕脸》在云南开机。2019年，与舒淇主演电影《痞子爱人》 ；同年，出演现实题材电影《勇敢的你》 。2017年9月28日，朱丹在微博晒出一张婴儿小脚丫照，宣布与周一围宝宝降生。', '2022-11-29 14:23:38', '2022-11-29 14:23:38', 0);
INSERT INTO `performer` VALUES (16, '秦海璐\r\n\r\nHailu Qin', 'https://p0.pipi.cn/basicdata/25bfd6d753706dddd207ac3488c640d4734ac.jpg?imageView2/1/w/464/h/644', '1978-08-11 00:00:00', '秦海璐，出生在辽宁省营口市，中国内地影视女演员，国家一级演员。1990年，在营口学习京剧表演，主攻刀马旦。2000年，凭借《榴莲飘飘》的表演获得第38届台湾电影金马奖最佳新人奖及最佳女主角奖、第20届香港电影金像奖最佳新演员奖、香港电影金紫荆奖最佳女主角。2011年凭借《钢的琴》获得第8届电影频道传媒大奖最佳女主角； 同年，凭借电影《到阜阳600里》获得第48届台湾电影金马奖最佳原著剧本奖。2012年，因其在话剧《四世同堂》中的演出获得金狮奖演员奖。2014年1月23日与王新军结婚；6月11日，受英国驻华使馆以及英国旅游局邀请，出任中英电影大使以及英国旅游形象大使身份。2017年，主演情感剧《你迟到的许多年》。同年，受邀担任第54届台湾电影金马奖评委。2018年，担任第21届上海国际电影节金爵奖评委会主竞赛单元评委。2019年，主演卫国战争电视剧《河山》；同年11月，担任第41届开罗国际电影节评委；出演的电影《开国将帅授勋1955》开机；同年，首次执导的电影《拂乡心》入围上海国际电影节金爵奖。2020年9月16日，主演的家庭都市剧《亲爱的，你在哪里》首播，在剧中饰演女主角何雪琳；同月，凭借电视剧《老酒馆》获得第32届中国电视剧飞天奖优秀女演员奖；11月，主演的都市剧《装台》播出。2021年，主演的谍战题材电影《悬崖之上》上映 ；同年，主演的家庭伦理剧《小敏家》播出。2022年7月，担任第十二届北京国际电影节“天坛奖”国际评委会评委。', '2022-11-29 14:24:11', '2022-11-29 14:24:11', 0);
INSERT INTO `performer` VALUES (17, '张智霖\r\n\r\nJulian Cheung', 'https://p0.pipi.cn/basicdata/25bfd6d753706d339e0e139cdc14e4ee93d89.jpg?imageView2/1/w/128/h/170', '1971-08-27 00:00:00', '张智霖，香港著名演员、歌手，1991年，发行个人首张专辑《现代爱情故事》，以歌手身份出道，并凭此获得第10届十大劲歌金曲最受欢迎新人奖铜奖 。1996年，参演TVB剧集《天地男儿》和《射雕英雄传》，人气急升。后又在内地和台湾先后拍摄了《白发魔女传》《飞刀，又见飞刀》《草民县令》等影视剧。2001年2月8日，与袁咏仪在美国登记结婚。2004年，开始将工作重心转向内地，并凭借主演的古装武侠剧《逆水寒》受到更多关注。2006年，出演古装武侠剧《陆小凤传奇》，凭此系列电影中的陆小凤一角获得第七届百合奖观众最喜欢的十佳男演员及第九届电影频道数字电影百合奖电视电影十年系列片观众最喜爱男演员奖；11月12日，儿子张慕童出生。2009年，回归歌坛，并发行粤语专辑《I Am Chilam》，其中歌曲《你太善良》获得新城劲爆卡拉ok歌曲奖。2011年，在香港连续举办两场\"我系外星人”演唱会。2012年，歌曲《恋上外星人》获得第34届十大中文金曲奖。2013年，更凭借《冲上云霄Ⅱ》中的“Cool魔”顾夏阳一角而再度爆红，因而人气急升，并夺得万千星辉颁奖典礼获得全民选出的最受欢迎电视男角色。2015年，参加综艺节目《一路上有你》；同年，凭借主演爱情电影《十月初五的月光》获得金票根圆梦盛典演绎传神奖和浪漫爱情奖。2016年3月，荣获首届金羊奖澳门国际电影节港澳台地区杰出贡献奖艺人奖；9月14日，主演动作悬疑电影《反贪风暴2》，并饰演一位不修边幅的痞子警察。2017年4月，其参演的青春偶像剧《求婚大作战》播出；7月6日，其主演的电影《京城81号2》上映。2018年6月15日，参演的电影《泄密者》在中国上映。其他作品有《龙门镖局之为2归来》《白狐》《西关大少》《谈判专家》《鱼跃在花见》《冲上云霄》《反贪风暴2》等；参与的节目有《一路上有你 第二季》《来吧冠军 第一季》等。', '2022-11-29 14:24:52', '2022-11-29 14:24:52', 0);
INSERT INTO `performer` VALUES (18, '王劲松\r\n\r\nChin-sung Wang', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7a67cb57e2ff120b087311f7a.jpg?imageView2/1/w/464/h/644', '1967-11-15 00:00:00', '王劲松，2010年《借枪》《李白》30集《现场铁证》（案发现场3）饰演陈元《子弹上膛》饰演雷克明2009年34集《我是老板》饰演马一平2008年30集《桃花深处》饰演姜云阳30集《金色黄昏》饰演马亦光2007年30集《明日已太远》饰演卫仁40集《血色湘西》饰演瞿先生33集《锦衣卫》饰演朱由检36集《大嫂》饰演范志远23集《恰同学少年》饰演汤芗茗2006年46集《大明王朝1566》饰演杨金水2005年26集《破天荒》饰演李开夫45集《东方朔》饰演县官以纵', '2022-11-29 14:25:17', '2022-11-29 14:25:17', 0);

-- ----------------------------
-- Table structure for performer_index
-- ----------------------------
DROP TABLE IF EXISTS `performer_index`;
CREATE TABLE `performer_index`  (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `film_ID` bigint NULL DEFAULT NULL COMMENT '电影ID',
  `performer_ID` bigint NULL DEFAULT NULL COMMENT '演员ID',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `is_delete` int NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `电影ID_2`(`film_ID`) USING BTREE,
  INDEX `演员ID_1`(`performer_ID`) USING BTREE,
  CONSTRAINT `演员ID_1` FOREIGN KEY (`performer_ID`) REFERENCES `performer` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `电影ID_2` FOREIGN KEY (`film_ID`) REFERENCES `film` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '演员和影片之间的关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of performer_index
-- ----------------------------
INSERT INTO `performer_index` VALUES (1, 1, 1, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (2, 1, 2, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (3, 1, 3, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (4, 1, 6, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (5, 2, 7, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (6, 2, 8, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (7, 2, 9, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (8, 2, 10, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (9, 3, 11, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (10, 3, 12, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (11, 3, 13, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (12, 3, 14, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (13, 4, 15, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (14, 4, 16, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (15, 4, 17, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);
INSERT INTO `performer_index` VALUES (16, 4, 18, '2022-11-30 16:02:34', '2022-11-30 16:02:34', 0);

-- ----------------------------
-- Table structure for repair
-- ----------------------------
DROP TABLE IF EXISTS `repair`;
CREATE TABLE `repair`  (
  `ID` bigint NOT NULL AUTO_INCREMENT COMMENT '维修订单号',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '报修标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '报修内容',
  `room_ID` bigint NULL DEFAULT NULL COMMENT '放映厅ID',
  `person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '负责人',
  `state` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '报修状态 0未完成 1进行中 2放弃维修 3维修终止 4维修完成',
  `cost` decimal(10, 2) NULL DEFAULT NULL COMMENT '报修花费金额',
  `createTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `updataTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `isDelete` int NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '报修表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of repair
-- ----------------------------

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag`  (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签名称',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `is_delete` int NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES (1, '热血', '2022-11-29 13:55:09', '2022-12-10 19:49:33', 0);
INSERT INTO `tag` VALUES (2, '柯南', '2022-11-29 13:55:14', '2022-12-10 19:49:38', 0);
INSERT INTO `tag` VALUES (3, '恐怖', '2022-11-29 13:55:22', '2022-12-10 19:49:42', 0);
INSERT INTO `tag` VALUES (4, '暴力', '2022-11-29 13:55:26', '2022-12-10 19:49:47', 0);
INSERT INTO `tag` VALUES (5, 'IKUN', '2022-11-29 13:55:43', '2022-12-10 19:49:51', 0);
INSERT INTO `tag` VALUES (6, '鸡你太美', '2022-11-29 13:55:47', '2022-12-10 19:49:52', 0);
INSERT INTO `tag` VALUES (7, '小黑子', '2022-11-29 13:55:56', '2022-12-10 19:50:01', 0);
INSERT INTO `tag` VALUES (8, '军事', '2022-12-07 21:20:44', '2022-12-10 19:49:29', 0);
INSERT INTO `tag` VALUES (10, '科幻', '2022-12-07 21:24:56', '2022-12-10 19:49:25', 0);
INSERT INTO `tag` VALUES (12, '动画', '2022-12-10 19:43:20', '2022-12-10 19:49:17', 0);

-- ----------------------------
-- Table structure for tag_index
-- ----------------------------
DROP TABLE IF EXISTS `tag_index`;
CREATE TABLE `tag_index`  (
  `ID` bigint NOT NULL AUTO_INCREMENT,
  `film_ID` bigint NULL DEFAULT NULL COMMENT '电影ID',
  `tag_ID` bigint NULL DEFAULT NULL COMMENT '标签ID',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `is_delete` int NULL DEFAULT 0,
  PRIMARY KEY (`ID`) USING BTREE,
  INDEX `标签ID`(`tag_ID`) USING BTREE,
  INDEX `标签索引_影片ID`(`film_ID`) USING BTREE,
  CONSTRAINT `标签ID` FOREIGN KEY (`tag_ID`) REFERENCES `tag` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `标签索引_影片ID` FOREIGN KEY (`film_ID`) REFERENCES `film` (`ID`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '电影和标签关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tag_index
-- ----------------------------
INSERT INTO `tag_index` VALUES (3, 2, 5, '2022-11-29 13:57:41', '2022-11-29 13:57:41', 0);
INSERT INTO `tag_index` VALUES (4, 2, 6, '2022-11-29 13:57:56', '2022-11-29 13:57:56', 0);
INSERT INTO `tag_index` VALUES (5, 3, 1, '2022-11-29 13:58:11', '2022-11-29 13:58:11', 0);
INSERT INTO `tag_index` VALUES (6, 3, 2, '2022-11-29 13:58:16', '2022-11-29 13:58:16', 0);
INSERT INTO `tag_index` VALUES (7, 4, 2, '2022-11-29 13:58:36', '2022-11-29 13:58:36', 0);
INSERT INTO `tag_index` VALUES (8, 4, 3, '2022-11-29 13:58:46', '2022-11-29 13:58:53', 0);
INSERT INTO `tag_index` VALUES (9, 4, 4, '2022-11-29 13:58:59', '2022-11-29 13:58:59', 0);
INSERT INTO `tag_index` VALUES (37, 1, 8, '2022-12-10 18:02:36', '2022-12-10 18:02:36', 0);
INSERT INTO `tag_index` VALUES (39, 1, 3, '2022-12-10 18:04:10', '2022-12-10 18:04:10', 0);
INSERT INTO `tag_index` VALUES (40, 1, 10, '2022-12-10 18:05:26', '2022-12-10 18:05:26', 0);
INSERT INTO `tag_index` VALUES (42, 2, 8, '2022-12-10 19:37:04', '2022-12-10 19:37:04', 0);

SET FOREIGN_KEY_CHECKS = 1;
