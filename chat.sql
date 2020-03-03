

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for chatgroup
-- ----------------------------
DROP TABLE IF EXISTS `chatgroup`;
CREATE TABLE `chatgroup`  (
  `groupid` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `descption` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `pub` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `createid` int(11) NULL DEFAULT NULL,
  `rdate` datetime(0) NOT NULL,
  `count` int(11) NULL DEFAULT 1,
  PRIMARY KEY (`groupid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for friend
-- ----------------------------
DROP TABLE IF EXISTS `friend`;
CREATE TABLE `friend`  (
  `groupid` int(11) NULL DEFAULT NULL,
  `friendid` int(11) NULL DEFAULT NULL,
  `notename` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of friend
-- ----------------------------
INSERT INTO `friend` VALUES (100, 10001, NULL);
INSERT INTO `friend` VALUES (100, 10002, NULL);
INSERT INTO `friend` VALUES (100, 10003, NULL);
INSERT INTO `friend` VALUES (101, 10000, NULL);
INSERT INTO `friend` VALUES (101, 10003, NULL);
INSERT INTO `friend` VALUES (100, 10004, NULL);
INSERT INTO `friend` VALUES (100, 10005, NULL);

-- ----------------------------
-- Table structure for offlinemsg
-- ----------------------------
DROP TABLE IF EXISTS `offlinemsg`;
CREATE TABLE `offlinemsg`  (
  `fromid` int(11) NULL DEFAULT NULL,
  `toid` int(11) NULL DEFAULT NULL,
  `msg` varchar(400) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `mdate` datetime(0) NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `sign` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `sex` varchar(4) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `addr` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `rdate` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10006 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (10000, 'testaaa', '无愁无泪看似一出戏', 'chenhua', '15284152325', 'man', 'pyp', '2020-03-03 09:30:55');
INSERT INTO `user` VALUES (10001, 'test', 'test', 'test', '18584176', 'man', 'pyp', '2020-03-03 09:30:55');
INSERT INTO `user` VALUES (10002, 'test2', 'test2', 'chenshusa', '18318176', 'man', 'pyp', '2020-03-03 09:30:55');
INSERT INTO `user` VALUES (10003, 'test3', 'test3', 'chenhua', '183128551', 'man', 'pyp', '2020-03-03 09:30:55');
INSERT INTO `user` VALUES (10004, 'test4', 'test4', 'chenhua', '188525415', 'man', 'pyp', '2020-03-03 09:30:55');
INSERT INTO `user` VALUES (10005, 'test5', 'test5', 'chenhua', '1531854852', 'man', 'pyp', '2020-03-03 09:30:55');

-- ----------------------------
-- Table structure for usergroup
-- ----------------------------
DROP TABLE IF EXISTS `usergroup`;
CREATE TABLE `usergroup`  (
  `groupid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NULL DEFAULT NULL,
  `friendcount` int(11) NULL DEFAULT 0,
  `groupname` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`groupid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 106 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of usergroup
-- ----------------------------
INSERT INTO `usergroup` VALUES (100, 10000, 5, '我的好友');
INSERT INTO `usergroup` VALUES (101, 10001, 2, '我的好友');
INSERT INTO `usergroup` VALUES (102, 10002, 0, '我的好友');
INSERT INTO `usergroup` VALUES (103, 10003, 0, '我的好友');
INSERT INTO `usergroup` VALUES (104, 10004, 0, '我的好友');
INSERT INTO `usergroup` VALUES (105, 10005, 0, '我的好友');

-- ----------------------------
-- Table structure for vgroup
-- ----------------------------
DROP TABLE IF EXISTS `vgroup`;
CREATE TABLE `vgroup`  (
  `groupid` int(11) NULL DEFAULT NULL,
  `userid` int(11) NULL DEFAULT NULL,
  `iscreater` int(11) NOT NULL,
  `ismanager` int(11) NOT NULL,
  `joindate` datetime(0) NOT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Triggers structure for table chatgroup
-- ----------------------------
DROP TRIGGER IF EXISTS `InsertChatgroup`;
delimiter ;;
CREATE TRIGGER `InsertChatgroup` AFTER INSERT ON `chatgroup` FOR EACH ROW Begin

    insert into vgroup values(new.groupid,new.createid,0,0,now());
end
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table chatgroup
-- ----------------------------
DROP TRIGGER IF EXISTS `DeleteChatgroup`;
delimiter ;;
CREATE TRIGGER `DeleteChatgroup` AFTER DELETE ON `chatgroup` FOR EACH ROW Begin
	delete from vgroup where groupid = old.groupid;
end
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table friend
-- ----------------------------
DROP TRIGGER IF EXISTS `InsertFriend`;
delimiter ;;
CREATE TRIGGER `InsertFriend` AFTER INSERT ON `friend` FOR EACH ROW Begin

	 
	update usergroup set friendcount=(select count(*) from friend where groupid = new.groupid ) where groupid = new.groupid;
    
end
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table friend
-- ----------------------------
DROP TRIGGER IF EXISTS `DeleteFriend`;
delimiter ;;
CREATE TRIGGER `DeleteFriend` AFTER DELETE ON `friend` FOR EACH ROW Begin

	 
	update usergroup set friendcount=(select count(*) from friend where groupid = old.groupid ) where groupid = old.groupid;
    
end
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `InsertUser`;
delimiter ;;
CREATE TRIGGER `InsertUser` AFTER INSERT ON `user` FOR EACH ROW Begin

    insert into usergroup values(null,new.id,0,'我的好友');
end
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table user
-- ----------------------------
DROP TRIGGER IF EXISTS `DeleteUser`;
delimiter ;;
CREATE TRIGGER `DeleteUser` BEFORE DELETE ON `user` FOR EACH ROW Begin

    delete from usergroup where userid = old.id;
    delete from chatgroup where createid = old.id;
    
end
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table usergroup
-- ----------------------------
DROP TRIGGER IF EXISTS `DeleteGroup`;
delimiter ;;
CREATE TRIGGER `DeleteGroup` BEFORE DELETE ON `usergroup` FOR EACH ROW Begin

    delete from friend where groupid = old.groupid;
end
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table vgroup
-- ----------------------------
DROP TRIGGER IF EXISTS `InsertVgroup`;
delimiter ;;
CREATE TRIGGER `InsertVgroup` AFTER INSERT ON `vgroup` FOR EACH ROW Begin

   
    declare co int;
 	select count from chatgroup where groupid = new.groupid into co;  
    set co = co + 1;
    update chatgroup set count=co where groupid = new.groupid;
    
end
;;
delimiter ;

-- ----------------------------
-- Triggers structure for table vgroup
-- ----------------------------
DROP TRIGGER IF EXISTS `DeleteVgroup`;
delimiter ;;
CREATE TRIGGER `DeleteVgroup` AFTER DELETE ON `vgroup` FOR EACH ROW Begin
    declare co int;
 	select count from chatgroup where groupid = old.groupid into co;  
    set co = co - 1;
    update chatgroup set count=co where groupid = old.groupid;
    
end
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
