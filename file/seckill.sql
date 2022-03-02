/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : seckill

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 25/02/2022 18:23:01
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `goods_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品标题',
  `goods_img` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品的图片',
  `goods_detail` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '商品的详情介绍',
  `goods_price` decimal(10, 2) NULL COMMENT '商品单价',
  `goods_stock` int(11) NULL DEFAULT 0 COMMENT '商品库存，-1表示没有限制',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (1, 'iphoneX', 'Apple iPhone X (A1865) 64GB 银色 移动联通电信4G手机', '/img/iphonex.png', 'Apple iPhone X (A1865) 64GB 银色 移动联通电信4G手机', 8765.00, 10000);
INSERT INTO `goods` VALUES (2, '华为Meta9', '华为 Mate 9 4GB+32GB版 月光银 移动联通电信4G手机 双卡双待', '/img/meta10.png', '华为 Mate 9 4GB+32GB版 月光银 移动联通电信4G手机 双卡双待', 3212.00, 1000);
INSERT INTO `goods` VALUES (3, 'iphone8', 'Apple iPhone 8 (A1865) 64GB 银色 移动联通电信4G手机', '/img/iphone8.png', 'Apple iPhone 8 (A1865) 64GB 银色 移动联通电信4G手机', 5589.00, 10000);
INSERT INTO `goods` VALUES (4, '小米6', '小米6 4GB+32GB版 月光银 移动联通电信4G手机 双卡双待', '/img/mi6.png', '小米6 4GB+32GB版 月光银 移动联通电信4G手机 双卡双待', 3212.00, 10000);

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `goods_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  `delivery_addr_id` bigint(20) NULL DEFAULT NULL COMMENT '收获地址ID',
  `goods_name` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冗余过来的商品名称',
  `goods_count` int(11) NULL DEFAULT 0 COMMENT '商品数量',
  `goods_price` decimal(10, 2) NULL COMMENT '商品单价',
  `order_channel` tinyint(4) NULL DEFAULT 0 COMMENT '1-pc，2-android，3-ios',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '订单的创建时间',
  `pay_date` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 694648065218449409 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_info
-- ----------------------------

-- ----------------------------
-- Table structure for seckill_goods
-- ----------------------------
DROP TABLE IF EXISTS `seckill_goods`;
CREATE TABLE `seckill_goods`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '秒杀的商品表',
  `goods_id` bigint(20) NULL DEFAULT NULL COMMENT '商品Id',
  `seckill_price` decimal(10, 2) NULL COMMENT '秒杀价',
  `stock_count` int(11) NULL DEFAULT NULL COMMENT '库存数量',
  `start_date` datetime(0) NULL DEFAULT NULL COMMENT '秒杀开始时间',
  `end_date` datetime(0) NULL DEFAULT NULL COMMENT '秒杀结束时间',
  `version` int(11) NULL DEFAULT 0 COMMENT '版本号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seckill_goods
-- ----------------------------
INSERT INTO `seckill_goods` VALUES (1, 1, 0.01, 2000, '2022-01-19 21:51:23', '2022-07-08 21:51:27', 0);
INSERT INTO `seckill_goods` VALUES (2, 2, 0.01, 4995, '2022-01-19 21:51:23', '2022-07-08 21:51:27', 0);
INSERT INTO `seckill_goods` VALUES (3, 3, 0.01, 4994, '2022-01-19 21:51:23', '2022-07-08 21:51:27', 0);
INSERT INTO `seckill_goods` VALUES (4, 4, 0.01, 4994, '2022-01-19 21:51:23', '2022-07-08 21:51:27', 0);

-- ----------------------------
-- Table structure for seckill_order
-- ----------------------------
DROP TABLE IF EXISTS `seckill_order`;
CREATE TABLE `seckill_order`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NULL DEFAULT NULL COMMENT '用户ID',
  `order_id` bigint(20) NULL DEFAULT NULL COMMENT '订单ID',
  `goods_id` bigint(20) NULL DEFAULT NULL COMMENT '商品ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `u_uid_gid`(`user_id`, `goods_id`) USING BTREE COMMENT '定义联合索引'
) ENGINE = InnoDB AUTO_INCREMENT = 1497150503133700098 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seckill_order
-- ----------------------------

-- ----------------------------
-- Table structure for seckill_user
-- ----------------------------
DROP TABLE IF EXISTS `seckill_user`;
CREATE TABLE `seckill_user`  (
  `UUID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键编号,用户id',
  `phone` bigint(20) NOT NULL COMMENT '手机号码',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `password` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MD5(MD5(password明文+固定salt) + salt)',
  `salt` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐值',
  `head` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像，云存储的id',
  `register_date` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `last_login_date` datetime(0) NULL DEFAULT NULL COMMENT '上次登录时间',
  `login_count` int(11) NULL DEFAULT 0 COMMENT '登录次数',
  PRIMARY KEY (`UUID`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35005 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '秒杀用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seckill_user
-- ----------------------------
INSERT INTO `seckill_user` VALUES (2, 19872773008, 'Noodle', 'bc75d95da7ee283bbd15cb5280d4c377', '1a2b3c4d', NULL, NULL, NULL, 0);
INSERT INTO `seckill_user` VALUES (3, 18312053086, '哈哈', 'e437e76586037f74a27e50c3bd237077', '1a2b3c4d', 'test12', '2022-01-18 16:34:52', NULL, 0);
INSERT INTO `seckill_user` VALUES (4, 17372773008, '小菜', '225e3b6990ef1fa3b43a1a80cc69b0cd', '1a2b3c4d', 'admin', '2022-01-18 18:25:15', NULL, 0);

SET FOREIGN_KEY_CHECKS = 1;
