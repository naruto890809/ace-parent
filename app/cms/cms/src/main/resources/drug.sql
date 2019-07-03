/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50623
Source Host           : 127.0.0.1:3306
Source Database       : drug

Target Server Type    : MYSQL
Target Server Version : 50623
File Encoding         : 65001

Date: 2018-09-25 00:13:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_cms_account
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_account`;
CREATE TABLE `t_cms_account` (
  `account_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '商家账户id',
  `employee_code` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `account_name` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '账户名称',
  `gender` tinyint(1) NOT NULL DEFAULT '0',
  `recharge_psd` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `login_psd` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '登录密码',
  `account_status` char(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'ACTIVATED' COMMENT '商家账户状态，默认ACTIVATED（激活的），FROZEN(冻结的)',
  `phone` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '联系电话',
  `email` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL COMMENT '邮箱',
  `face_url` char(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `signature` char(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `self_introduction` char(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `wei_xin` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `qq` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `work_year` int(11) DEFAULT NULL,
  `corp_code` char(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `create_by` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `u_cms_account_corp_code_name` (`corp_code`,`account_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of t_cms_account
-- ----------------------------
INSERT INTO `t_cms_account` VALUES ('c760b3afb874493a806cf1d2834ed1cf', '93a18e13d1174070b0904829dbe33a21', 'www', '0', '', 'c2266fc8c67a43ee87a8846b5573ce70E8D60667C843FDBD2DEBE4B7A3EA26E5', 'ACTIVATED', '18705595165', '', '', null, null, '', '', '', null, 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:16:18', 'default44e184b0e868314253cbadmin', '2018-09-06 22:16:18');
INSERT INTO `t_cms_account` VALUES ('ce2b577ef43f47abbe2924aeacd7b4b5', '93a18e13d1174070b0904829dbe33a21', 'wzw', '0', '', 'c1123fde29654108a13e43d4094cd9e9EB7A249BA0358AE048629892062C6FF0', 'ACTIVATED', '18705595166', '', '', null, null, '', '', '', null, 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 21:36:59', 'default44e184b0e868314253cbadmin', '2018-09-06 21:36:59');
INSERT INTO `t_cms_account` VALUES ('default44e184b0e868314253cbadmin', 'president', 'admin', '0', 'f2717e39a8ee45a59e0bd45e56b60b804CB159704A6756090917B7331ABD55F4', 'a266c9f3a70b4cb0aea479af7217f5ca08F814B360A57F55911E5EBB50FDB9D4', 'ACTIVATED', '18705598888', '', '', '', null, '', '', '', null, 'default', '4e17c9fccd364613814c4754c8d5331a', '2016-01-29 10:17:54', '4e17c9fccd364613814c4754c8d5331a', '2016-01-29 10:17:54');

-- ----------------------------
-- Table structure for t_cms_biz
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_biz`;
CREATE TABLE `t_cms_biz` (
  `id` char(32) NOT NULL COMMENT '主键',
  `salesman_id` char(32) NOT NULL COMMENT '业务员id',
  `hospital_id` char(32) NOT NULL COMMENT '医院id',
  `drug_id` char(32) NOT NULL COMMENT '药品id',
  `spec_drug_id` char(32) NOT NULL COMMENT '药品规格id',
  `sale_date` date NOT NULL COMMENT '销售时间段',
  `trace_start_date` date NOT NULL COMMENT '跟踪开始时间',
  `trace_end_date` date NOT NULL COMMENT '跟踪结束时间（默认开始加六个月）',
  `note` char(100) DEFAULT NULL COMMENT '备注',
  `approve` char(20) NOT NULL DEFAULT 'APPROVING' COMMENT '审核状态 审核中、通过（PASSED）',
  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务管理';

-- ----------------------------
-- Records of t_cms_biz
-- ----------------------------
INSERT INTO `t_cms_biz` VALUES ('6a6f0632434944fea07a59ce9c6e4ddf', 'baadd79b98ef4a0f8c0131074cbda5e6', 'c187bb21a8f1450592f26d1f661b8888', 'e2919294308943d2b1f399d510bdc027', '684a5fcceb3b4950a42288dc3b803adb', '2018-01-01', '2018-01-01', '2018-12-31', '买完', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 19:31:07', 'default44e184b0e868314253cbadmin', '2018-09-24 23:15:57');

-- ----------------------------
-- Table structure for t_cms_company
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_company`;
CREATE TABLE `t_cms_company` (
  `id` char(32) NOT NULL COMMENT '主键',
  `name` char(50) NOT NULL COMMENT '名称',
  `code` char(50) DEFAULT NULL COMMENT '代码',
  `alias` char(100) DEFAULT NULL COMMENT '别名，多个以逗号分隔',
  `channel` char(32) NOT NULL COMMENT '销售渠道 如：直销、分销（来自数据字典，编号为COMPANY_CHANNEL）',
  `sell_area_id` char(32) NOT NULL COMMENT '所属区域id',
  `address` char(100) DEFAULT NULL COMMENT '详细地址',
  `person` char(20) DEFAULT NULL COMMENT '联系人',
  `contacts` char(100) DEFAULT NULL COMMENT '联系方式',
  `pid` char(32) DEFAULT NULL COMMENT '总公司id',
  `approve` char(20) NOT NULL DEFAULT 'APPROVING' COMMENT '审核状态 审核中、通过（PASSED）',
  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商业公司';

-- ----------------------------
-- Records of t_cms_company
-- ----------------------------
INSERT INTO `t_cms_company` VALUES ('02fa11bc10584993ad23aeb8b0469146', '繁星科技', '1', '繁星', '8347aabe66b54bf78f4276d9b5b8a494', '43f3e6b4c54847dfa8f819a98317b3a1', '合肥市蜀山区创新大道1001号1908', '吴志伟', '18705595165', '', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 20:15:10', 'default44e184b0e868314253cbadmin', '2018-09-24 23:12:18');
INSERT INTO `t_cms_company` VALUES ('bcb7c84dc80545559f27715b2c11b2d7', '鹏声科技', 'ps989', '', 'e59f788f200a4da689f5f5dc2e84d65b', '43f3e6b4c54847dfa8f819a98317b3a1', '', '', '', '', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-24 23:57:22', 'default44e184b0e868314253cbadmin', '2018-09-24 23:57:35');

-- ----------------------------
-- Table structure for t_cms_company_drug
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_company_drug`;
CREATE TABLE `t_cms_company_drug` (
  `id` char(32) NOT NULL COMMENT '主键',
  `company_id` char(32) NOT NULL COMMENT '商业公司id',
  `drug_id` char(32) NOT NULL COMMENT '药品id',
  `spec_drug_id` char(32) NOT NULL COMMENT '药品规格id',
  `start_time` datetime NOT NULL COMMENT '开始时间',
  `end_time` datetime NOT NULL COMMENT '结束时间',
  `bidding_price` decimal(11,2) NOT NULL COMMENT '中标价',
  `deduction_rate` decimal(11,2) NOT NULL COMMENT '扣率(1-100)',
  `billing_price` decimal(11,2) NOT NULL COMMENT '开票价',
  `approve` char(20) NOT NULL DEFAULT 'APPROVING' COMMENT '审核状态 审核中、通过（PASSED）',
  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商业公司药品关联表';

-- ----------------------------
-- Records of t_cms_company_drug
-- ----------------------------
INSERT INTO `t_cms_company_drug` VALUES ('c9ba6ecf38524cc2a038f5f53d18bf19', '02fa11bc10584993ad23aeb8b0469146', 'e2919294308943d2b1f399d510bdc027', '684a5fcceb3b4950a42288dc3b803adb', '2018-08-01 00:00:00', '2018-12-01 00:00:00', '2.22', '10.00', '3.33', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 22:38:25', 'default44e184b0e868314253cbadmin', '2018-09-24 23:15:02');

-- ----------------------------
-- Table structure for t_cms_department
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_department`;
CREATE TABLE `t_cms_department` (
  `id` char(32) NOT NULL COMMENT '主键',
  `name` char(50) NOT NULL COMMENT '部门名称',
  `description` char(100) DEFAULT NULL COMMENT '描述',
  `approve` char(20) NOT NULL DEFAULT 'APPROVING' COMMENT '审核状态 审核中、通过（PASSED）',
  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门';

-- ----------------------------
-- Records of t_cms_department
-- ----------------------------
INSERT INTO `t_cms_department` VALUES ('12762a0d7a2d4e56b691a06613481f12', '杭州一部', '杭州一部', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-12 23:52:48', 'default44e184b0e868314253cbadmin', '2018-09-12 23:53:36');
INSERT INTO `t_cms_department` VALUES ('2159352954604432adb15f98a55b114a', '南京玄武部', '南京玄武部', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-12 23:45:27', 'default44e184b0e868314253cbadmin', '2018-09-12 23:53:36');

-- ----------------------------
-- Table structure for t_cms_dic
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_dic`;
CREATE TABLE `t_cms_dic` (
  `id` char(32) NOT NULL COMMENT '主键',
  `code` char(50) NOT NULL COMMENT '所属业务类型编号',
  `name` char(50) NOT NULL COMMENT '名称',
  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

-- ----------------------------
-- Records of t_cms_dic
-- ----------------------------
INSERT INTO `t_cms_dic` VALUES ('111abbf561eb4c2da909c68aad96ab55', 'REBATE_STYLE', '现金', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:45:54', '', '2018-09-15 01:45:54');
INSERT INTO `t_cms_dic` VALUES ('341632ed57e14ad3a1560c3976d53deb', 'REBATE_STYLE', ' 折让', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:45:54', '', '2018-09-15 01:45:54');
INSERT INTO `t_cms_dic` VALUES ('3de7d6faf4054cd1bf89e73a9aa78d9f', 'REBATE_PRICE_TOPIC', ' 地区', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:45:32', '', '2018-09-15 01:45:32');
INSERT INTO `t_cms_dic` VALUES ('3e96c3d9708f4c1d82a5984519e3c126', 'HOSPITAL_TYPE', ' 私立', 'default', 'default44e184b0e868314253cbadmin', '2018-09-24 23:11:14', '', '2018-09-24 23:11:14');
INSERT INTO `t_cms_dic` VALUES ('5e62236004d34b42887beb085c0da5d8', 'COMPANY_CHANNEL', ' 一级代理', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:43:13', '', '2018-09-15 01:43:13');
INSERT INTO `t_cms_dic` VALUES ('6dc95bfcdd2d4c78a7a51c53e66d9902', 'HOSPITAL_TYPE', ' 医院', 'default', 'default44e184b0e868314253cbadmin', '2018-09-24 23:11:14', '', '2018-09-24 23:11:14');
INSERT INTO `t_cms_dic` VALUES ('7745e6ed96f44054a0b496b972471cd5', 'REBATE_PRICE_TOPIC', ' 商业公司', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:45:32', '', '2018-09-15 01:45:32');
INSERT INTO `t_cms_dic` VALUES ('8347aabe66b54bf78f4276d9b5b8a494', 'COMPANY_CHANNEL', ' 直营', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:42:51', '', '2018-09-15 01:42:51');
INSERT INTO `t_cms_dic` VALUES ('89350fcddfec4913882bb2d95b88b648', 'REBATE_DIF_TYPE', ' 暗', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:45:01', '', '2018-09-15 01:45:01');
INSERT INTO `t_cms_dic` VALUES ('93082fd883b84fc7835d45b4ffd13320', 'HOSPITAL_TYPE', ' 诊所', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:44:19', '', '2018-09-15 01:44:19');
INSERT INTO `t_cms_dic` VALUES ('981b8623134d4b4ab1baab25b1519548', 'REBATE_DIF_TYPE', ' 明+暗', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:45:01', '', '2018-09-15 01:45:01');
INSERT INTO `t_cms_dic` VALUES ('99f2749305f34da88217f66af0572ed7', 'HOSPITAL_LEVEL', '三甲', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:43:51', '', '2018-09-15 01:43:51');
INSERT INTO `t_cms_dic` VALUES ('a334dc4b97284b0ea0b2d452d98f2210', 'HOSPITAL_LEVEL', ' 三乙', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:43:51', '', '2018-09-15 01:43:51');
INSERT INTO `t_cms_dic` VALUES ('ae64c5ac2e2c4cb6ba338966ff70769d', 'REBATE_DIF_TYPE', '明', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:45:01', '', '2018-09-15 01:45:01');
INSERT INTO `t_cms_dic` VALUES ('bf2b3c477622443f84f92fa53fcb02a7', 'REBATE_PRICE_TOPIC', '单体医院', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:45:32', '', '2018-09-15 01:45:32');
INSERT INTO `t_cms_dic` VALUES ('e59f788f200a4da689f5f5dc2e84d65b', 'COMPANY_CHANNEL', ' 二级代理', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:43:13', '', '2018-09-15 01:43:13');
INSERT INTO `t_cms_dic` VALUES ('e5f905e1544e4897abd704949c34a30e', 'COMPANY_CHANNEL', ' 三级代理', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 01:43:13', '', '2018-09-15 01:43:13');

-- ----------------------------
-- Table structure for t_cms_drug
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_drug`;
CREATE TABLE `t_cms_drug` (
  `id` char(32) NOT NULL COMMENT '主键',
  `name` char(50) NOT NULL COMMENT '药品名称',
  `code` char(50) DEFAULT NULL COMMENT '药品代码',
  `alias` char(50) DEFAULT NULL COMMENT '药品别名',
  `approve` char(20) NOT NULL DEFAULT 'APPROVING' COMMENT '审核状态 审核中、通过（PASSED）',
  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='药品';

-- ----------------------------
-- Records of t_cms_drug
-- ----------------------------
INSERT INTO `t_cms_drug` VALUES ('e2919294308943d2b1f399d510bdc027', '白加黑', '111', '黑加黑、特别黑', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-18 23:01:36', 'default44e184b0e868314253cbadmin', '2018-09-24 23:12:50');

-- ----------------------------
-- Table structure for t_cms_drug_spec
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_drug_spec`;
CREATE TABLE `t_cms_drug_spec` (
  `id` char(32) NOT NULL COMMENT '主键',
  `spec_name` char(50) NOT NULL COMMENT '规格名称',
  `drug_id` char(32) NOT NULL COMMENT '所属产品',
  `code` char(50) DEFAULT NULL COMMENT '产品代码',
  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='药品规格';

-- ----------------------------
-- Records of t_cms_drug_spec
-- ----------------------------
INSERT INTO `t_cms_drug_spec` VALUES ('3e0aaeb1e911444ebe449959cbc27711', '11D', 'e2919294308943d2b1f399d510bdc027', '', 'default', 'default44e184b0e868314253cbadmin', '2018-09-18 23:01:36', '', '2018-09-18 23:01:36');
INSERT INTO `t_cms_drug_spec` VALUES ('4b282c26d774415ca298a3fecf3c221a', '2010ML', '1d2a0d91c06c4f5d9d83730b883b9f4c', '', 'default', 'default44e184b0e868314253cbadmin', '2018-09-18 23:01:20', '', '2018-09-18 23:01:20');
INSERT INTO `t_cms_drug_spec` VALUES ('53223006bfa14ef38d1c1be49ca4f3b2', '111D', '1d2a0d91c06c4f5d9d83730b883b9f4c', '', 'default', 'default44e184b0e868314253cbadmin', '2018-09-18 23:01:20', '', '2018-09-18 23:01:20');
INSERT INTO `t_cms_drug_spec` VALUES ('684a5fcceb3b4950a42288dc3b803adb', '200ML', 'e2919294308943d2b1f399d510bdc027', '', 'default', 'default44e184b0e868314253cbadmin', '2018-09-18 23:01:36', '', '2018-09-18 23:01:36');

-- ----------------------------
-- Table structure for t_cms_export_record
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_export_record`;
CREATE TABLE `t_cms_export_record` (
  `id` char(32) NOT NULL COMMENT '主键',
  `file_name` char(100) NOT NULL COMMENT '文件名',
  `export_cnt` int(11) NOT NULL COMMENT '导入条数|操作人|操作时间',
  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='导入记录';

-- ----------------------------
-- Records of t_cms_export_record
-- ----------------------------

-- ----------------------------
-- Table structure for t_cms_hospital
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_hospital`;
CREATE TABLE `t_cms_hospital` (
  `id` char(32) NOT NULL COMMENT '主键',
  `name` char(50) NOT NULL COMMENT '名称',
  `alias` char(200) DEFAULT NULL COMMENT '别名，多个以逗号分隔',
  `level` char(32) NOT NULL COMMENT '等级，如：三甲 来自字典表，编号是：HOSPITAL_LEVEL',
  `type` char(32) NOT NULL COMMENT '医院性质，如：医院、卫生院 来自字典表，编号是：HOSPITAL_LEVEL',
  `department_id` char(32) NOT NULL COMMENT '所属部门id',
  `province` char(50) DEFAULT NULL COMMENT '省',
  `city` char(50) DEFAULT NULL COMMENT '市',
  `area` char(50) DEFAULT NULL COMMENT '区、县',
  `address` char(100) DEFAULT NULL COMMENT '详细地址',
  `approve` char(20) NOT NULL DEFAULT 'APPROVING' COMMENT '审核状态 审核中、通过（PASSED）',
  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='医院';

-- ----------------------------
-- Records of t_cms_hospital
-- ----------------------------
INSERT INTO `t_cms_hospital` VALUES ('7b360f923fb04fe7b4a9e5fe0512737d', '安医附院', '', '99f2749305f34da88217f66af0572ed7', '6dc95bfcdd2d4c78a7a51c53e66d9902', '12762a0d7a2d4e56b691a06613481f12', '安徽省', '合肥市', '市辖区', '', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-24 23:58:21', 'default44e184b0e868314253cbadmin', '2018-09-24 23:58:25');
INSERT INTO `t_cms_hospital` VALUES ('c187bb21a8f1450592f26d1f661b8888', '安徽省立医院', '省立医院，省医院', '99f2749305f34da88217f66af0572ed7', '6dc95bfcdd2d4c78a7a51c53e66d9902', '12762a0d7a2d4e56b691a06613481f12', '安徽省', '合肥市', '蜀山区', '东流路32号', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-15 02:42:32', 'default44e184b0e868314253cbadmin', '2018-09-24 23:12:03');

-- ----------------------------
-- Table structure for t_cms_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_menu`;
CREATE TABLE `t_cms_menu` (
  `menu_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键id',
  `parent_id` char(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `default_sub_url` char(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '模块',
  `en_title` char(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `icon` char(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '图标',
  `seq` int(11) DEFAULT NULL COMMENT '排序',
  `target` char(10) COLLATE utf8mb4_bin DEFAULT NULL,
  `tip` char(15) COLLATE utf8mb4_bin DEFAULT NULL,
  `title` char(15) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '标题',
  `url` char(100) COLLATE utf8mb4_bin DEFAULT NULL,
  `corp_code` char(50) COLLATE utf8mb4_bin NOT NULL,
  `parent_corp_code` char(50) COLLATE utf8mb4_bin NOT NULL,
  `privilege` char(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `module` char(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_by` char(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_cms_menu
-- ----------------------------
INSERT INTO `t_cms_menu` VALUES ('0a33b7bc72074d7ca82ae5a5c1df6df0', '9eac285406d44fe3887edbc48cef8d2c', '', '', '', '0', '', '', '查看', '', '', '', 'view', 'account', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('0b0dcd3fb2284b838ac7a915faebbc29', 'a436bc99fead4d458a7fd442b87abb51', '', '', '', '0', '', '', '权限设置', 'menu.index.do', '', '', null, null, '', '2016-01-21 17:01:31', '', '2016-01-21 16:59:05');
INSERT INTO `t_cms_menu` VALUES ('0da868414b364f9fadea3c18f0661d53', '9eac285406d44fe3887edbc48cef8d2c', '', '', '', '0', '', '', '添加', '', '', '', 'add', 'account', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('1036bc99fead4d458a7fd442b87abb51', 'a436bc99fead4d458a7fd442b87abb51', '', '', 'menu-icon fa  fa-cog', '10', '', '', '数据字典', 'dic.index.do', '', '', null, null, '', '2016-01-20 13:34:50', '', '2016-01-20 13:32:09');
INSERT INTO `t_cms_menu` VALUES ('1111Area4b364f9faddacc18f066wd56', '5436bc99fead4d458a7fd442b87price', '', '', '', '6', '', '', '添加', '', '', '', 'add', 'companyDrug', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('1111Area4b364f9fadea3c18f0661d56', '7436bc99fead4d458a7fd442b87abb51', '', '', '', '6', '', '', '添加', '', '', '', 'add', 'hospital', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('1111Area4b364f9fadea3c18f0661ddd', '1036bc99fead4d458a7fd442b87abb51', '', '', '', '6', '', '', '添加', '', '', '', 'add', 'dic', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('1111Area4b364f9fadea3c18f066wd56', '5436bc99fead4d458a7fd442b87abb51', '', '', '', '6', '', '', '添加', '', '', '', 'add', 'biz', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('1111Area4b364f9fadeacc18f066wd56', '6436bc99fead4d458a7fd442b87abb51', '', '', '', '6', '', '', '添加', '', '', '', 'add', 'company', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('1111Areawb364f9faddacc18f066wd56', '8436bc99fead4d458a7fd442b87abb51', '', '', '', '6', '', '', '添加', '', '', '', 'add', 'rebate', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('111lArea4b364f9fadea3c18f0661d56', '4436bc99fead4d458a7fd442b87abb51', '', '', '', '6', '', '', '添加', '', '', '', 'add', 'drug', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('111lArealb364f9fadea3c18f0661d56', '9436bc99fead4d458a7fd442b87abb51', '', '', '', '6', '', '', '添加', '', '', '', 'add', 'order', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('1136bc99fead4d458a7fd442b87abb51', '', '', '', 'menu-icon fa  fa-bar-chart-o', '10', '', '', '报表统计', 'report.UI.do', '', '', null, null, '', '2016-01-20 13:34:50', '', '2016-01-20 13:32:09');
INSERT INTO `t_cms_menu` VALUES ('11llArea4b364f9fadea3c18f0661d56', '3436bc99fead4d458a7fd442b87abb51', '', '', '', '6', '', '', '添加', '', '', '', 'add', 'salesman', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('1436bc99fead4d458a7fd442b87abb51', '', '', '', 'menu-icon fa  fa-cloud', '100', '', '', '销售区域', 'sellArea.index.do', '', '', null, null, '', '2016-01-20 13:34:50', '', '2016-01-20 13:32:09');
INSERT INTO `t_cms_menu` VALUES ('1dc932a0fe154408ada020f727a3657f', '9eac285406d44fe3887edbc48cef8d2c', '', '', '', '0', '', '', '冻结&激活', '', '', '', 'freeze&activate', 'account', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('1ellArea4b364f9fadea3c18f0661d56', '2436bc99fead4d458a7fd442b87abb51', '', '', '', '6', '', '', '添加', '', '', '', 'add', 'department', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('2222Area4b364f9faddacc18f066wd55', '5436bc99fead4d458a7fd442b87price', '', '', '', '5', '', '', '审批', '', '', '', 'approve', 'companyDrug', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('2222Area4b364f9fadea3c18f0661d55', '7436bc99fead4d458a7fd442b87abb51', '', '', '', '5', '', '', '审批', '', '', '', 'approve', 'hospital', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('2222Area4b364f9fadea3c18f066wd55', '5436bc99fead4d458a7fd442b87abb51', '', '', '', '5', '', '', '审批', '', '', '', 'approve', 'biz', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('2222Area4b364f9fadeacc18f066wd55', '6436bc99fead4d458a7fd442b87abb51', '', '', '', '5', '', '', '审批', '', '', '', 'approve', 'company', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('2222Areawb364f9faddacc18f066wd55', '8436bc99fead4d458a7fd442b87abb51', '', '', '', '5', '', '', '审批', '', '', '', 'approve', 'rebate', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('222lArea4b364f9fadea3c18f0661d55', '4436bc99fead4d458a7fd442b87abb51', '', '', '', '5', '', '', '审批', '', '', '', 'approve', 'drug', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('22llArea4b364f9fadea3c18f0661d55', '3436bc99fead4d458a7fd442b87abb51', '', '', '', '5', '', '', '审批', '', '', '', 'approve', 'salesman', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('2436bc99fead4d458a7fd442b87abb51', '', '', '', 'menu-icon fa  fa-desktop', '90', '', '', '部门管理', 'department.index.do', '', '', null, null, '', '2016-01-20 13:34:50', '', '2016-01-20 13:32:09');
INSERT INTO `t_cms_menu` VALUES ('2ellArea4b364f9fadea3c18f0661d55', '2436bc99fead4d458a7fd442b87abb51', '', '', '', '5', '', '', '审批', '', '', '', 'approve', 'department', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('3233Area4b364f9faddacc18f066wd54', '5436bc99fead4d458a7fd442b87price', '', '', '', '4', '', '', '导入', '', '', '', 'import', 'companyDrug', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('3233Area4b364f9fadea3c18f0661d54', '7436bc99fead4d458a7fd442b87abb51', '', '', '', '4', '', '', '导入', '', '', '', 'import', 'hospital', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('3233Area4b364f9fadea3c18f066wd54', '5436bc99fead4d458a7fd442b87abb51', '', '', '', '4', '', '', '导入', '', '', '', 'import', 'biz', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('3233Area4b364f9fadeacc18f066wd54', '6436bc99fead4d458a7fd442b87abb51', '', '', '', '4', '', '', '导入', '', '', '', 'import', 'company', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('3233Areawb364f9faddacc18f066wd54', '8436bc99fead4d458a7fd442b87abb51', '', '', '', '4', '', '', '导入', '', '', '', 'import', 'rebate', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('323lArea4b364f9fadea3c18f0661d54', '4436bc99fead4d458a7fd442b87abb51', '', '', '', '4', '', '', '导入', '', '', '', 'import', 'drug', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('323lArealb364f9fadea3c18f0661d54', '9436bc99fead4d458a7fd442b87abb51', '', '', '', '4', '', '', '导入', '', '', '', 'import', 'order', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('32llArea4b364f9fadea3c18f0661d54', '3436bc99fead4d458a7fd442b87abb51', '', '', '', '4', '', '', '导入', '', '', '', 'import', 'salesman', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('3436bc99fead4d458a7fd442b87abb51', '', '', '', 'menu-icon fa  fa-users', '80', '', '', '业务员管理', 'salesman.index.do', '', '', null, null, '', '2016-01-20 13:34:50', '', '2016-01-20 13:32:09');
INSERT INTO `t_cms_menu` VALUES ('3ellArea4b364f9fadea3c18f0661d54', '2436bc99fead4d458a7fd442b87abb51', '', '', '', '4', '', '', '导入', '', '', '', 'import', 'department', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('4244Area4b364f9faddacc18f066wd33', '5436bc99fead4d458a7fd442b87price', '', '', '', '3', '', '', '导出', '', '', '', 'export', 'companyDrug', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('4244Area4b364f9fadea3c18f0661d33', '7436bc99fead4d458a7fd442b87abb51', '', '', '', '3', '', '', '导出', '', '', '', 'export', 'hospital', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('4244Area4b364f9fadea3c18f066wd33', '5436bc99fead4d458a7fd442b87abb51', '', '', '', '3', '', '', '导出', '', '', '', 'export', 'biz', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('4244Area4b364f9fadeacc18f066wd33', '6436bc99fead4d458a7fd442b87abb51', '', '', '', '3', '', '', '导出', '', '', '', 'export', 'company', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('4244Areawb364f9faddacc18f066wd33', '8436bc99fead4d458a7fd442b87abb51', '', '', '', '3', '', '', '导出', '', '', '', 'export', 'rebate', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('424lArea4b364f9fadea3c18f0661d33', '4436bc99fead4d458a7fd442b87abb51', '', '', '', '3', '', '', '导出', '', '', '', 'export', 'drug', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('424lArealb364f9fadea3c18f0661d33', '9436bc99fead4d458a7fd442b87abb51', '', '', '', '3', '', '', '导出', '', '', '', 'export', 'order', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('42llArea4b364f9fadea3c18f0661d33', '3436bc99fead4d458a7fd442b87abb51', '', '', '', '3', '', '', '导出', '', '', '', 'export', 'salesman', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('4436bc99fead4d458a7fd442b87abb51', '', '', '', 'menu-icon fa  fa-coffee', '70', '', '', '药品管理', 'drug.index.do', '', '', null, null, '', '2016-01-20 13:34:50', '', '2016-01-20 13:32:09');
INSERT INTO `t_cms_menu` VALUES ('45403cc964144aab9dd07fa8a34996b0', '9eac285406d44fe3887edbc48cef8d2c', '', '', '', '0', '', '', '重置密码', '', '', '', 'resetaccountpsd', 'account', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('4ellArea4b364f9fadea3c18f0661d33', '2436bc99fead4d458a7fd442b87abb51', '', '', '', '3', '', '', '导出', '', '', '', 'export', 'department', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('5255Area4b364f9faddacc18f066wd22', '5436bc99fead4d458a7fd442b87price', '', '', '', '2', '', '', '删除', '', '', '', 'remove', 'companyDrug', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('5255Area4b364f9fadea3c18f0661d22', '7436bc99fead4d458a7fd442b87abb51', '', '', '', '2', '', '', '删除', '', '', '', 'remove', 'hospital', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('5255Area4b364f9fadea3c18f066wd22', '5436bc99fead4d458a7fd442b87abb51', '', '', '', '2', '', '', '删除', '', '', '', 'remove', 'biz', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('5255Area4b364f9fadeacc18f066wd22', '6436bc99fead4d458a7fd442b87abb51', '', '', '', '2', '', '', '删除', '', '', '', 'remove', 'company', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('5255Areawb364f9faddacc18f066wd22', '8436bc99fead4d458a7fd442b87abb51', '', '', '', '2', '', '', '删除', '', '', '', 'remove', 'rebate', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('525lArea4b364f9fadea3c18f0661d22', '4436bc99fead4d458a7fd442b87abb51', '', '', '', '2', '', '', '删除', '', '', '', 'remove', 'drug', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('525lArealb364f9fadea3c18f0661d22', '9436bc99fead4d458a7fd442b87abb51', '', '', '', '2', '', '', '删除', '', '', '', 'remove', 'order', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('52llArea4b364f9fadea3c18f0661d22', '3436bc99fead4d458a7fd442b87abb51', '', '', '', '2', '', '', '删除', '', '', '', 'remove', 'salesman', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('5436bc99fead4d458a7fd442b87abb51', '', '', '', 'menu-icon fa   fa-glass', '60', '', '', '业务管理', 'biz.index.do', '', '', null, null, '', '2016-01-20 13:34:50', '', '2016-01-20 13:32:09');
INSERT INTO `t_cms_menu` VALUES ('5436bc99fead4d458a7fd442b87price', '', '', '', 'menu-icon fa 	fa-key', '65', '', '', '价格管理', 'companyDrug.index.do', '', '', null, null, '', '2016-01-20 13:34:50', '', '2016-01-20 13:32:09');
INSERT INTO `t_cms_menu` VALUES ('5ellArea4b364f9fadea3c18f0661d22', '2436bc99fead4d458a7fd442b87abb51', '', '', '', '2', '', '', '删除', '', '', '', 'remove', 'department', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('6266Area4b364f9faddacc18f066wd11', '5436bc99fead4d458a7fd442b87price', '', '', '', '1', '', '', '编辑', '', '', '', 'edit', 'companyDrug', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('6266Area4b364f9fadea3c18f0661d11', '7436bc99fead4d458a7fd442b87abb51', '', '', '', '1', '', '', '编辑', '', '', '', 'edit', 'hospital', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('6266Area4b364f9fadea3c18f0661ddd', '1036bc99fead4d458a7fd442b87abb51', '', '', '', '1', '', '', '编辑', '', '', '', 'edit', 'dic', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('6266Area4b364f9fadea3c18f066wd11', '5436bc99fead4d458a7fd442b87abb51', '', '', '', '1', '', '', '编辑', '', '', '', 'edit', 'biz', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('6266Area4b364f9fadeacc18f066wd11', '6436bc99fead4d458a7fd442b87abb51', '', '', '', '1', '', '', '编辑', '', '', '', 'edit', 'company', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('6266Areawb364f9faddacc18f066wd11', '8436bc99fead4d458a7fd442b87abb51', '', '', '', '1', '', '', '编辑', '', '', '', 'edit', 'rebate', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('626lArea4b364f9fadea3c18f0661d11', '4436bc99fead4d458a7fd442b87abb51', '', '', '', '1', '', '', '编辑', '', '', '', 'edit', 'drug', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('626lArealb364f9fadea3c18f0661d11', '9436bc99fead4d458a7fd442b87abb51', '', '', '', '1', '', '', '编辑', '', '', '', 'edit', 'order', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('626lArealbq64f9fadea3c18f0661d11', '9436bc99fead4d458a7fd442b87abb51', '', '', '', '0', '', '', '导入记录', '', '', '', 'exportRecord', 'order', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('62llArea4b364f9fadea3c18f0661d11', '3436bc99fead4d458a7fd442b87abb51', '', '', '', '1', '', '', '编辑', '', '', '', 'edit', 'salesman', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('6436bc99fead4d458a7fd442b87abb51', '', '', '', 'menu-icon fa  fa-book', '74', '', '', '商业公司', 'company.index.do', '', '', null, null, '', '2016-01-20 13:34:50', '', '2016-01-20 13:32:09');
INSERT INTO `t_cms_menu` VALUES ('6ellArea4b364f9fadea3c18f0661d11', '2436bc99fead4d458a7fd442b87abb51', '', '', '', '1', '', '', '编辑', '', '', '', 'edit', 'department', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('7436bc99fead4d458a7fd442b87abb51', '', '', '', 'menu-icon fa  fa-heart', '75', '', '', '医院管理', 'hospital.index.do', '', '', null, null, '', '2016-01-20 13:34:50', '', '2016-01-20 13:32:09');
INSERT INTO `t_cms_menu` VALUES ('8436bc99fead4d458a7fd442b87abb51', '', '', '', 'menu-icon fa  fa-exchange', '30', '', '', '返利设置', 'rebate.index.do', '', '', null, null, '', '2016-01-20 13:34:50', '', '2016-01-20 13:32:09');
INSERT INTO `t_cms_menu` VALUES ('9436bc99fead4d458a7fd442b87abb51', '', '', '', 'menu-icon fa  fa-bell', '20', '', '', '流向清单', 'order.index.do', '', '', null, null, '', '2016-01-20 13:34:50', '', '2016-01-20 13:32:09');
INSERT INTO `t_cms_menu` VALUES ('9eac285406d44fe3887edbc48cef8d2c', 'a436bc99fead4d458a7fd442b87abb51', '', '', '', '99', '', '', '用户管理', 'account.accountManageIndex.do', '', '', '', '', '', '2016-01-20 13:39:13', '', '2016-01-20 13:36:32');
INSERT INTO `t_cms_menu` VALUES ('a436bc99fead4d458a7fd442b87abb51', '', '', '', 'menu-icon fa  fa-cog', '0', '', '', '系统设置', '', '', '', null, null, '', '2016-01-20 13:34:50', '', '2016-01-20 13:32:09');
INSERT INTO `t_cms_menu` VALUES ('c7e8d0e6e6b94aca99edbca285a43f5b', '9eac285406d44fe3887edbc48cef8d2c', '', '', '', '0', '', '', '编辑', '', '', '', 'update', 'account', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('sellArea4b364f9fadea3c18f0661d11', '1436bc99fead4d458a7fd442b87abb51', '', '', '', '1', '', '', '编辑', '', '', '', 'edit', 'sellArea', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('sellArea4b364f9fadea3c18f0661d22', '1436bc99fead4d458a7fd442b87abb51', '', '', '', '2', '', '', '删除', '', '', '', 'remove', 'sellArea', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('sellArea4b364f9fadea3c18f0661d33', '1436bc99fead4d458a7fd442b87abb51', '', '', '', '3', '', '', '导出', '', '', '', 'export', 'sellArea', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('sellArea4b364f9fadea3c18f0661d54', '1436bc99fead4d458a7fd442b87abb51', '', '', '', '4', '', '', '导入', '', '', '', 'import', 'sellArea', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('sellArea4b364f9fadea3c18f0661d55', '1436bc99fead4d458a7fd442b87abb51', '', '', '', '5', '', '', '审批', '', '', '', 'approve', 'sellArea', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `t_cms_menu` VALUES ('sellArea4b364f9fadea3c18f0661d56', '1436bc99fead4d458a7fd442b87abb51', '', '', '', '6', '', '', '添加', '', '', '', 'add', 'sellArea', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');

-- ----------------------------
-- Table structure for t_cms_order
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_order`;
CREATE TABLE `t_cms_order` (
  `id` char(32) NOT NULL COMMENT '主键',
  `drug_name` char(50) DEFAULT NULL COMMENT '药品',
  `spec_name` char(50) DEFAULT NULL COMMENT '规格',
  `drug_spec_name` char(50) DEFAULT NULL COMMENT '品规',
  `drug_num` char(50) DEFAULT NULL COMMENT '批号',
  `drug_cnt` int(11) DEFAULT NULL COMMENT '数量',
  `hospital_name` char(50) DEFAULT NULL COMMENT '医院名称',
  `order_date` datetime DEFAULT NULL COMMENT '日期',
  `price` decimal(11,2) DEFAULT NULL COMMENT '单价（终端开票价）',
  `company_name` char(50) DEFAULT NULL COMMENT '商业公司',
  `branch_company_name` char(50) DEFAULT NULL COMMENT '分公司',
  `year` int(11) DEFAULT NULL COMMENT '年',
  `month` int(11) DEFAULT NULL COMMENT '月',
  `day` int(11) DEFAULT NULL COMMENT '日',
  `actual_price` decimal(11,2) DEFAULT NULL COMMENT '实际结算价【(中标价格-返利单价)*(1-商业扣率)】',
  `total_money` decimal(11,2) DEFAULT NULL COMMENT '销售金额【实际结算价格*数量】',
  `company_area` char(50) DEFAULT NULL COMMENT '商业公司所属销售区域',
  `department_name` char(50) DEFAULT NULL COMMENT '医院所属推广部名称',
  `hospital_area` char(50) DEFAULT NULL COMMENT '医院所在行政区域（省市区|县）',
  `hospital_lavel` char(50) DEFAULT NULL COMMENT '医院等级',
  `hospital_type` char(50) DEFAULT NULL COMMENT '医院性质',
  `salesman_name` char(50) DEFAULT NULL COMMENT '业务员姓名',
  `deduction_rate` decimal(11,2) DEFAULT NULL COMMENT '商业公司药品关联表扣率',
  `bidding_price` decimal(11,2) DEFAULT NULL COMMENT '商业公司药品关联表中标价（医保支付价）',
  `billing_price` decimal(11,2) DEFAULT NULL COMMENT '商业公司药品关联表开票价（开票价）',
  `rebate_rate` decimal(11,2) DEFAULT NULL COMMENT '返利信息表-返利点数',
  `bright_price` decimal(11,2) DEFAULT NULL COMMENT '返利信息表-明返单价',
  `dark_price` decimal(11,2) DEFAULT NULL COMMENT '返利信息表-暗返单价',
  `rebate_price` decimal(11,2) DEFAULT NULL COMMENT '返利信息表-返利单价（明+暗）',
  `drug_id` char(32) DEFAULT NULL COMMENT '药品Id',
  `spec_id` char(32) DEFAULT NULL COMMENT '规格Id',
  `hospital_id` char(32) DEFAULT NULL COMMENT '医院ID',
  `deptment_id` char(32) DEFAULT NULL COMMENT '医院所属推广部id',
  `company_id` char(32) DEFAULT NULL COMMENT '商业公司ID',
  `company_sell_area_id` char(32) DEFAULT NULL COMMENT '商业公司所属销售区域Id',
  `salesman_id` char(32) DEFAULT NULL COMMENT '业务员id（根据医院、药品、规格id及跟踪时间段从业务表查出）',
  `company_drug_id` char(32) DEFAULT NULL COMMENT '商业公司药品关联表id（根据商业公司、药品、规格id及有效时间段从业务表查出）',
  `rebate_id` char(32) DEFAULT NULL COMMENT '返利信息表id（根据商业公司、药品、规格、医院id从返利表查出）',
  `status` char(50) DEFAULT NULL COMMENT '状态，正常、医保支付价异常、终端开票价异常',
  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='流向清单';

-- ----------------------------
-- Records of t_cms_order
-- ----------------------------

-- ----------------------------
-- Table structure for t_cms_rebate
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_rebate`;
CREATE TABLE `t_cms_rebate` (
  `id` char(32) NOT NULL COMMENT '主键',
  `company_id` char(32) NOT NULL COMMENT '商业公司id',
  `hospital_id` char(32) NOT NULL COMMENT '医院id',
  `drug_id` char(32) NOT NULL COMMENT '药品id',
  `spec_drug_id` char(32) NOT NULL COMMENT '药品规格id',
  `dif_type` char(50) NOT NULL COMMENT '补差性质 来自字典表，编号是：REBATE_DIF_TYPE',
  `price_topic` char(50) NOT NULL COMMENT '议价主体  来自字典表，编号是：REBATE_PRICE_TOPIC',
  `bidding_price` decimal(11,2) DEFAULT NULL COMMENT '中标价（根据品规从t_cms_company_drug表中查出）',
  `execute_price` decimal(11,2) NOT NULL COMMENT '执行价',
  `bright_price` decimal(11,2) DEFAULT NULL COMMENT '明返单价',
  `bright_execute_date` date DEFAULT NULL COMMENT '明返执行日期',
  `dark_price` decimal(11,2) DEFAULT NULL COMMENT '暗返单价',
  `dark_execute_date` date DEFAULT NULL COMMENT '暗返执行日期',
  `rebate_price` decimal(11,2) DEFAULT NULL COMMENT '返利单价',
  `rebate_rate` decimal(11,2) DEFAULT NULL COMMENT '返利点数',
  `rebate_style` char(50) NOT NULL COMMENT '返利形式  来自字典表，编号是：REBATE_STYLE',
  `approve` char(20) NOT NULL DEFAULT 'APPROVING' COMMENT '审核状态 审核中、通过（PASSED）',
  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='返利设置';

-- ----------------------------
-- Records of t_cms_rebate
-- ----------------------------
INSERT INTO `t_cms_rebate` VALUES ('89b4b934be804b789ed4bd9861d89aba', '02fa11bc10584993ad23aeb8b0469146', 'c187bb21a8f1450592f26d1f661b8888', 'e2919294308943d2b1f399d510bdc027', '684a5fcceb3b4950a42288dc3b803adb', '明', 'bf2b3c477622443f84f92fa53fcb02a7', '2.22', '2.60', '0.20', '2018-09-01', null, null, '0.20', '9.00', '111abbf561eb4c2da909c68aad96ab55', 'APPROVING', 'default', 'default44e184b0e868314253cbadmin', '2018-09-24 23:38:05', '', '2018-09-24 23:38:05');
INSERT INTO `t_cms_rebate` VALUES ('a3a6a7e54dc64de3bb389ba07a1b0eca', '02fa11bc10584993ad23aeb8b0469146', 'c187bb21a8f1450592f26d1f661b8888', 'e2919294308943d2b1f399d510bdc027', '684a5fcceb3b4950a42288dc3b803adb', '明+暗', '7745e6ed96f44054a0b496b972471cd5', '2.22', '2.22', '0.01', '2018-09-01', '0.10', '2018-09-19', '0.11', '5.00', '341632ed57e14ad3a1560c3976d53deb', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-24 23:19:03', 'default44e184b0e868314253cbadmin', '2018-09-24 23:36:23');

-- ----------------------------
-- Table structure for t_cms_role
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_role`;
CREATE TABLE `t_cms_role` (
  `role_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '角色id',
  `role_name` char(50) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '角色名称',
  `role_code` char(50) COLLATE utf8mb4_bin NOT NULL,
  `parent_corp_code` char(50) COLLATE utf8mb4_bin DEFAULT NULL,
  `visible` tinyint(4) DEFAULT '1',
  `corp_code` char(50) COLLATE utf8mb4_bin NOT NULL,
  `create_by` char(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_cms_role
-- ----------------------------
INSERT INTO `t_cms_role` VALUES ('93a18e13d1174070b0904829dbe33a21', '管理员', '93a18e13d1174070b0904829dbe33a21', 'default', '1', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:10:34', '', '2018-09-06 22:10:34');
INSERT INTO `t_cms_role` VALUES ('president', '超级管理员', 'president', 'default', '1', 'default', '8eb8db4c62044221b77a37f0eb261f24', '2015-12-16 09:40:19', '8eb8db4c62044221b77a37f0eb261f24', '2015-12-16 09:40:22');

-- ----------------------------
-- Table structure for t_cms_role_menu_rel
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_role_menu_rel`;
CREATE TABLE `t_cms_role_menu_rel` (
  `role_menu_rel_id` char(32) COLLATE utf8mb4_bin NOT NULL COMMENT '主键id',
  `menu_id` char(32) COLLATE utf8mb4_bin NOT NULL,
  `role_id` char(32) COLLATE utf8mb4_bin NOT NULL,
  `menu` tinyint(1) DEFAULT '1' COMMENT '1 是菜单  0 是权限',
  `corp_code` char(50) COLLATE utf8mb4_bin NOT NULL,
  `parent_corp_code` char(50) COLLATE utf8mb4_bin NOT NULL,
  `create_by` char(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) COLLATE utf8mb4_bin DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_menu_rel_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of t_cms_role_menu_rel
-- ----------------------------
INSERT INTO `t_cms_role_menu_rel` VALUES ('252bf61fa2724f4d868a154e8d9b5521', '9eac285406d44fe3887edbc48cef8d2c', 'president', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('2566c3abfdad42a28d9c16afd441eae0', '8436bc99fead4d458a7fd442b87abb51', 'president', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('2b3aa39ca88742299d3fc2ad15fef148', 'a436bc99fead4d458a7fd442b87abb51', 'president', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('2ca15f6afc9045f0aa0d7474401032f4', '1dc932a0fe154408ada020f727a3657f', 'president', '0', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('34b63ff3bfda42f2a08de999066cfad7', '0da868414b364f9fadea3c18f0661d53', 'president', '0', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('41afbf7941bd4ea4a80149ba7f58de00', '45403cc964144aab9dd07fa8a34996b0', 'president', '0', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('4ac7779dbcc648a3945eb3e8cca0884e', '1036bc99fead4d458a7fd442b87abb51', 'president', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('4c3838e43386408481298bbeff9c97b2', '0a33b7bc72074d7ca82ae5a5c1df6df0', '93a18e13d1174070b0904829dbe33a21', '0', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:10:52', '', '2018-09-06 22:10:52');
INSERT INTO `t_cms_role_menu_rel` VALUES ('60c3bdecca544c12b878927d03bc31d2', '5436bc99fead4d458a7fd442b87abb51', 'president', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('6216e681a84549739ea879faef749d76', '0b0dcd3fb2284b838ac7a915faebbc29', 'president', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('6c43d705087f4c709e57a524701139f5', '9436bc99fead4d458a7fd442b87abb51', 'president', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('6fbc3dc7ae5a4719936b4ebf423307a2', 'c7e8d0e6e6b94aca99edbca285a43f5b', '93a18e13d1174070b0904829dbe33a21', '0', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:10:52', '', '2018-09-06 22:10:52');
INSERT INTO `t_cms_role_menu_rel` VALUES ('6fc98a117c79421db5f8efba396025dc', '3436bc99fead4d458a7fd442b87abb51', 'president', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('8f0571a0949e4130ba06bbfbd3351050', '0a33b7bc72074d7ca82ae5a5c1df6df0', 'president', '0', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('99a3f5d6c752423e822fac91519f4ec6', '4436bc99fead4d458a7fd442b87abb51', 'president', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('ad975ebc48544c57b4aae5fb5731a7e8', 'a436bc99fead4d458a7fd442b87abb51', '93a18e13d1174070b0904829dbe33a21', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:10:52', '', '2018-09-06 22:10:52');
INSERT INTO `t_cms_role_menu_rel` VALUES ('b0b18e872d1343418368cd00b6670038', '1436bc99fead4d458a7fd442b87abb51', 'president', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('b9399956a7484097b127ab973d5b8f2f', '0da868414b364f9fadea3c18f0661d53', '93a18e13d1174070b0904829dbe33a21', '0', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:10:52', '', '2018-09-06 22:10:52');
INSERT INTO `t_cms_role_menu_rel` VALUES ('bc1c3e0002cd4a179486f422c70fc2e0', '45403cc964144aab9dd07fa8a34996b0', '93a18e13d1174070b0904829dbe33a21', '0', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:10:52', '', '2018-09-06 22:10:52');
INSERT INTO `t_cms_role_menu_rel` VALUES ('bc62f14154944121ab7e866b6b7d1089', '6436bc99fead4d458a7fd442b87abb51', 'president', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('c98c4ab20cbe4666a188f813cb3c0d93', '9eac285406d44fe3887edbc48cef8d2c', '93a18e13d1174070b0904829dbe33a21', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:10:52', '', '2018-09-06 22:10:52');
INSERT INTO `t_cms_role_menu_rel` VALUES ('ce1099b707f447e097a4cb60e10f2ef4', '1dc932a0fe154408ada020f727a3657f', '93a18e13d1174070b0904829dbe33a21', '0', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:10:52', '', '2018-09-06 22:10:52');
INSERT INTO `t_cms_role_menu_rel` VALUES ('df9313972217442ca160830459714355', '7436bc99fead4d458a7fd442b87abb51', 'president', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('eb09087aa7e743dcbd5217a3aed306fc', '2436bc99fead4d458a7fd442b87abb51', 'president', '1', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');
INSERT INTO `t_cms_role_menu_rel` VALUES ('fe4e4a418d7b402d9e0de6afb1b4c516', 'c7e8d0e6e6b94aca99edbca285a43f5b', 'president', '0', 'default', 'default', 'default44e184b0e868314253cbadmin', '2018-09-06 22:30:39', '', '2018-09-06 22:30:39');

-- ----------------------------
-- Table structure for t_cms_salesman
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_salesman`;
CREATE TABLE `t_cms_salesman` (
  `id` char(32) NOT NULL COMMENT '主键',
  `name` char(20) NOT NULL COMMENT '姓名',
  `department_id` char(32) NOT NULL COMMENT '部门',
  `contact` char(50) DEFAULT NULL COMMENT '联系方式',
  `approve` char(20) NOT NULL DEFAULT 'APPROVING' COMMENT '审核状态 审核中、通过（PASSED）',
  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='业务员';

-- ----------------------------
-- Records of t_cms_salesman
-- ----------------------------
INSERT INTO `t_cms_salesman` VALUES ('baadd79b98ef4a0f8c0131074cbda5e6', '吴志伟', '12762a0d7a2d4e56b691a06613481f12', '18705595165', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-24 23:10:30', 'default44e184b0e868314253cbadmin', '2018-09-24 23:13:02');

-- ----------------------------
-- Table structure for t_cms_sell_area
-- ----------------------------
DROP TABLE IF EXISTS `t_cms_sell_area`;
CREATE TABLE `t_cms_sell_area` (
  `id` char(32) NOT NULL COMMENT '主键',
  `province` char(50) NOT NULL COMMENT '省',
  `city` char(50) NOT NULL COMMENT '市',
  `area` char(50) NOT NULL COMMENT '区（手动输入，不与省市联动）',
  `approve` char(20) NOT NULL DEFAULT 'APPROVING' COMMENT '审核状态 审核中、通过（PASSED）',
  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售区域';

-- ----------------------------
-- Records of t_cms_sell_area
-- ----------------------------
INSERT INTO `t_cms_sell_area` VALUES ('0ef530a666bb4197b5c8530b15347a02', '北京市', '市辖区', '3', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-12 23:03:40', 'default44e184b0e868314253cbadmin', '2018-09-15 20:14:00');
INSERT INTO `t_cms_sell_area` VALUES ('43f3e6b4c54847dfa8f819a98317b3a1', '安徽省', '合肥市', '蜀山区', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-12 23:28:48', 'default44e184b0e868314253cbadmin', '2018-09-15 20:14:00');
INSERT INTO `t_cms_sell_area` VALUES ('6890c136d5b24b488cd4f34dd3b2c5c9', '浙江省', '杭州市', '云溪小镇', 'APPROVING', 'default', 'default44e184b0e868314253cbadmin', '2018-09-18 21:27:36', 'default44e184b0e868314253cbadmin', '2018-09-24 23:09:56');
INSERT INTO `t_cms_sell_area` VALUES ('8493d5d969b84da79b25b208aa73efbe', '北京市', '市辖区', '1', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-12 23:03:40', 'default44e184b0e868314253cbadmin', '2018-09-15 20:14:00');
INSERT INTO `t_cms_sell_area` VALUES ('adea268279f4440ea5f10ce507f45409', '河北省', '秦皇岛市', '去去去', 'PASSED', 'default', 'default44e184b0e868314253cbadmin', '2018-09-12 23:03:40', 'default44e184b0e868314253cbadmin', '2018-09-15 20:14:00');
