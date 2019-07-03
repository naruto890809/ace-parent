/*基础信息
    销售区域（添加、审批、导入、导出、删除、编辑）
    部门管理（添加、审批、导入、导出、删除、编辑）
    业务员管理（添加、审批、导入、导出、删除、编辑）
    医院管理（添加、审批、导入、导出、删除、编辑）
    商业公司（添加、审批、导入、导出、删除、编辑、）
    药品管理（添加、审批、导入、导出、删除、编辑）
    价格管理[添加、审批、导入、导出、删除、编辑]
    业务管理（添加、审批、导入、导出、删除、编辑）
    返利设置（添加、审批、导入、导出、删除、编辑）

    流向清单（添加、审批、导入、导出、删除、编辑、导入记录[删除]）（注意：单独页面展示导入记录，且可以撤销   无添加功能）
    注意：导入数据时，检查商业公司与代理药品时间段价格，
          时间不在范围或者价格不等于时间范围内的价格不允许导

报表统计

系统设置
    账号管理
    角色管理
    数据字典（商业公司-销售渠道COMPANY_CHANNEL、医院管理-医院等级|医院性质）HOSPITAL_LEVEL HOSPITAL_LEVEL
             补差性质 REBATE_DIF_TYPE    议价主体 REBATE_PRICE_TOPIC     返利形式 REBATE_STYLE





需要审批的基础数据有：区域、部门、业务员、医院、药品、商业公司、业务管理、返利信息
*/


INSERT INTO `drug`.`t_cms_menu` (`menu_id`, `parent_id`, `default_sub_url`, `en_title`, `icon`, `seq`, `target`, `tip`, `title`, `url`, `corp_code`, `parent_corp_code`, `privilege`, `module`, `create_by`, `create_time`, `last_modify_by`, `last_modify_time`) VALUES ('111lArealb364f9fadea3c18f0661d56', '9436bc99fead4d458a7fd442b87abb51', '', '', '', '6', '', '', '添加', '', '', '', 'add', 'order', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `drug`.`t_cms_menu` (`menu_id`, `parent_id`, `default_sub_url`, `en_title`, `icon`, `seq`, `target`, `tip`, `title`, `url`, `corp_code`, `parent_corp_code`, `privilege`, `module`, `create_by`, `create_time`, `last_modify_by`, `last_modify_time`) VALUES ('222lArealb364f9fadea3c18f0661d55', '9436bc99fead4d458a7fd442b87abb51', '', '', '', '5', '', '', '审批', '', '', '', 'approve', 'order', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `drug`.`t_cms_menu` (`menu_id`, `parent_id`, `default_sub_url`, `en_title`, `icon`, `seq`, `target`, `tip`, `title`, `url`, `corp_code`, `parent_corp_code`, `privilege`, `module`, `create_by`, `create_time`, `last_modify_by`, `last_modify_time`) VALUES ('323lArealb364f9fadea3c18f0661d54', '9436bc99fead4d458a7fd442b87abb51', '', '', '', '4', '', '', '导入', '', '', '', 'import', 'order', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `drug`.`t_cms_menu` (`menu_id`, `parent_id`, `default_sub_url`, `en_title`, `icon`, `seq`, `target`, `tip`, `title`, `url`, `corp_code`, `parent_corp_code`, `privilege`, `module`, `create_by`, `create_time`, `last_modify_by`, `last_modify_time`) VALUES ('424lArealb364f9fadea3c18f0661d33', '9436bc99fead4d458a7fd442b87abb51', '', '', '', '3', '', '', '导出', '', '', '', 'export', 'order', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `drug`.`t_cms_menu` (`menu_id`, `parent_id`, `default_sub_url`, `en_title`, `icon`, `seq`, `target`, `tip`, `title`, `url`, `corp_code`, `parent_corp_code`, `privilege`, `module`, `create_by`, `create_time`, `last_modify_by`, `last_modify_time`) VALUES ('525lArealb364f9fadea3c18f0661d22', '9436bc99fead4d458a7fd442b87abb51', '', '', '', '2', '', '', '删除', '', '', '', 'remove', 'order', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `drug`.`t_cms_menu` (`menu_id`, `parent_id`, `default_sub_url`, `en_title`, `icon`, `seq`, `target`, `tip`, `title`, `url`, `corp_code`, `parent_corp_code`, `privilege`, `module`, `create_by`, `create_time`, `last_modify_by`, `last_modify_time`) VALUES ('626lArealb364f9fadea3c18f0661d11', '9436bc99fead4d458a7fd442b87abb51', '', '', '', '1', '', '', '编辑', '', '', '', 'edit', 'order', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');
INSERT INTO `drug`.`t_cms_menu` (`menu_id`, `parent_id`, `default_sub_url`, `en_title`, `icon`, `seq`, `target`, `tip`, `title`, `url`, `corp_code`, `parent_corp_code`, `privilege`, `module`, `create_by`, `create_time`, `last_modify_by`, `last_modify_time`) VALUES ('626lArealbq64f9fadea3c18f0661d11', '9436bc99fead4d458a7fd442b87abb51', '', '', '', '0', '', '', '导入记录', '', '', '', 'exportRecord', 'order', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');

ALTER TABLE `t_cms_drug`
ADD COLUMN `code`  char(50) NULL COMMENT '药品代码' AFTER `name`;


INSERT INTO `drug`.`t_cms_menu` (`menu_id`, `parent_id`, `default_sub_url`, `en_title`, `icon`, `seq`, `target`, `tip`, `title`, `url`, `corp_code`, `parent_corp_code`, `privilege`, `module`, `create_by`, `create_time`, `last_modify_by`, `last_modify_time`) VALUES ('1ellArea4b364f9fadea3c18f0661777', '2436bc99fead4d458a7fd442b87abb51', '', '', '', '7', '', '', '任务', '', '', '', 'job', 'department', '', '2016-01-25 15:11:37', '', '2016-01-25 15:09:27');


CREATE TABLE `t_cms_department_job` (
  `id` char(32) NOT NULL COMMENT '主键',
  `department_id` char(32) NOT NULL COMMENT '部门id',
  `drug_id` char(32) NOT NULL COMMENT '药品id',
  `job_amount` int NOT NULL COMMENT '任务量',
  `year` INT NOT NULL COMMENT '年份',

  `corp_code` char(50) NOT NULL,
  `create_by` char(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `last_modify_by` char(32) DEFAULT NULL,
  `last_modify_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商业公司药品关联表';




-- -----------------------------------------------------------------------------------
-- ---------------------------------报表-----------------------------------------------
-- -----------------------------------------------------------------------------------

INSERT INTO `drug`.`t_cms_menu` (`menu_id`, `parent_id`, `default_sub_url`, `en_title`, `icon`, `seq`, `target`, `tip`, `title`, `url`, `corp_code`, `parent_corp_code`, `privilege`, `module`, `create_by`, `create_time`, `last_modify_by`, `last_modify_time`) VALUES ('9eac285406d44fe3887edbc48cef8w2c', '1136bc99fead4d458a7fd442b87abb51', '', '', '', '99', '', '', '产品医院报表', 'report.drugHos.do', '', '', '', '', '', '2016-01-20 13:39:13', '', '2016-01-20 13:36:32');
INSERT INTO `drug`.`t_cms_menu` (`menu_id`, `parent_id`, `default_sub_url`, `en_title`, `icon`, `seq`, `target`, `tip`, `title`, `url`, `corp_code`, `parent_corp_code`, `privilege`, `module`, `create_by`, `create_time`, `last_modify_by`, `last_modify_time`) VALUES ('9eac285406d44fe3887edbc48cef8z2c', '1136bc99fead4d458a7fd442b87abb51', '', '', '', '89', '', '', '纯销数据', 'report.cSale.do', '', '', '', '', '', '2016-01-20 13:39:13', '', '2016-01-20 13:36:32');
INSERT INTO `drug`.`t_cms_menu` (`menu_id`, `parent_id`, `default_sub_url`, `en_title`, `icon`, `seq`, `target`, `tip`, `title`, `url`, `corp_code`, `parent_corp_code`, `privilege`, `module`, `create_by`, `create_time`, `last_modify_by`, `last_modify_time`) VALUES ('9eac285406d44fe3887edbc481ef8w2c', '1136bc99fead4d458a7fd442b87abb51', '', '', '', '79', '', '', '新申请医院', 'report.newHos.do', '', '', '', '', '', '2016-01-20 13:39:13', '', '2016-01-20 13:36:32');
INSERT INTO `drug`.`t_cms_menu` (`menu_id`, `parent_id`, `default_sub_url`, `en_title`, `icon`, `seq`, `target`, `tip`, `title`, `url`, `corp_code`, `parent_corp_code`, `privilege`, `module`, `create_by`, `create_time`, `last_modify_by`, `last_modify_time`) VALUES ('9eac285406d44fe3887edbc48cef8j2c', '1136bc99fead4d458a7fd442b87abb51', '', '', '', '69', '', '', '考核医院数量', 'report.hosCnt.do', '', '', '', '', '', '2016-01-20 13:39:13', '', '2016-01-20 13:36:32');


ALTER TABLE `t_cms_order`
ADD COLUMN `coefficient`  int(11) NULL COMMENT '换算后的数量' AFTER `drug_cnt`;


ALTER TABLE `t_cms_drug_spec`
  MODIFY COLUMN `coefficient`  decimal(11,2) NULL DEFAULT NULL COMMENT '系数' AFTER `spec_name`;

ALTER TABLE `t_cms_order`
  MODIFY COLUMN `coefficient`  decimal(11,2) NULL DEFAULT NULL COMMENT '换算后的数量' AFTER `drug_cnt`;

INSERT INTO `drug`.`t_cms_menu` (`menu_id`, `parent_id`, `default_sub_url`, `en_title`, `icon`, `seq`, `target`, `tip`, `title`, `url`, `corp_code`, `parent_corp_code`, `privilege`, `module`, `create_by`, `create_time`, `last_modify_by`, `last_modify_time`) VALUES ('9eac285406d44fe3887edbc48yrammus', '1136bc99fead4d458a7fd442b87abb51', '', '', '', '110', '', '', '销售概况', 'report.sumary.do', '', '', '', '', '', '2016-01-20 13:39:13', '', '2016-01-20 13:36:32');

