insert into ACCOUNT_STATUS_TRANSFER (ID, TYPE_MODEL, ACTION_NAME, ORIGINAL_ACCOUNT_STATUS, TARGET_ACCOUNT_STATUS)
values (1, 'FUNDS', '状态开启', -1, 1);
insert into ACCOUNT_STATUS_TRANSFER (ID, TYPE_MODEL, ACTION_NAME, ORIGINAL_ACCOUNT_STATUS, TARGET_ACCOUNT_STATUS)
values (2, 'FUNDS', '状态关闭', 1, -1);
commit;
insert into account_type (ACCOUNT_TYPE_ID, ACCOUNT_TYPE_NAME, TYPE, SCOPE, DIRECTORY, TYPE_MODEL, CCY, IDENTITY_TYPE, ACCOUNT_TYPE_STATUS, TABLE_NAME, SUB_FLAG, SUB_NUMBER)
values (204000, '系统中间利润户', 'AB,UB,CB', 'normol', 'DEBIT', 'FUNDS', 'RMB', 'SP', 1, 'ccy_account', 0, 0);
insert into account_type (ACCOUNT_TYPE_ID, ACCOUNT_TYPE_NAME, TYPE, SCOPE, DIRECTORY, TYPE_MODEL, CCY, IDENTITY_TYPE, ACCOUNT_TYPE_STATUS, TABLE_NAME, SUB_FLAG, SUB_NUMBER)
values (204001, '系统利润户', 'AB,UB,CB', 'normol', 'DEBIT', 'FUNDS', 'RMB', 'SP', 1, 'ccy_account', 0, 0);
insert into account_type (ACCOUNT_TYPE_ID, ACCOUNT_TYPE_NAME, TYPE, SCOPE, DIRECTORY, TYPE_MODEL, CCY, IDENTITY_TYPE, ACCOUNT_TYPE_STATUS, TABLE_NAME, SUB_FLAG, SUB_NUMBER)
values (149000, '商户贷记账户', 'AB,UB,CB', 'normol', 'CREDIT', 'FUNDS', 'RMB', 'MERCHANT', 1, 'merchant_credit_account', 0, 0);
insert into account_type (ACCOUNT_TYPE_ID, ACCOUNT_TYPE_NAME, TYPE, SCOPE, DIRECTORY, TYPE_MODEL, CCY, IDENTITY_TYPE, ACCOUNT_TYPE_STATUS, TABLE_NAME, SUB_FLAG, SUB_NUMBER)
values (149001, '系统借记账户', 'AB,UB,CB', 'normol', 'DEBIT', 'FUNDS', 'RMB', 'SP', 1, 'system_debit_account', 1, 10);
insert into account_type (ACCOUNT_TYPE_ID, ACCOUNT_TYPE_NAME, TYPE, SCOPE, DIRECTORY, TYPE_MODEL, CCY, IDENTITY_TYPE, ACCOUNT_TYPE_STATUS, TABLE_NAME, SUB_FLAG, SUB_NUMBER)
values (148001, '商户系统利润账户', 'AB,UB,CB', 'normol', 'DEBIT', 'FUNDS', 'RMB', 'SP', 1, 'ccy_account', 0, 0);
insert into account_type (ACCOUNT_TYPE_ID, ACCOUNT_TYPE_NAME, TYPE, SCOPE, DIRECTORY, TYPE_MODEL, CCY, IDENTITY_TYPE, ACCOUNT_TYPE_STATUS, TABLE_NAME, SUB_FLAG, SUB_NUMBER)
values (148000, '商户借记账户', 'AB,UB,CB', 'normol', 'DEBIT', 'FUNDS', 'RMB', 'MERCHANT', 1, 'merchant_debit_account', 0, 0);
insert into account_type (ACCOUNT_TYPE_ID, ACCOUNT_TYPE_NAME, TYPE, SCOPE, DIRECTORY, TYPE_MODEL, CCY, IDENTITY_TYPE, ACCOUNT_TYPE_STATUS, TABLE_NAME, SUB_FLAG, SUB_NUMBER)
values (45000, '返佣应付账户', 'AB,UB,CB', 'normol', 'DEBIT', 'FUNDS', 'RMB', 'SP', 1, 'ccy_account', 0, 0);
insert into account_type (ACCOUNT_TYPE_ID, ACCOUNT_TYPE_NAME, TYPE, SCOPE, DIRECTORY, TYPE_MODEL, CCY, IDENTITY_TYPE, ACCOUNT_TYPE_STATUS, TABLE_NAME, SUB_FLAG, SUB_NUMBER)
values (45001, '供货商利润账户', 'AB,UB,CB', 'normol', 'DEBIT', 'FUNDS', 'RMB', 'SP', 1, 'ccy_account', 0, 0);
commit;
insert into ROLE (ROLE_ID, ROLE_NAME, CREATE_TIME, UPDATE_USER, UPDATE_TIME, STATUS, REMARK, ROLE_TYPE)
values (300, '商户操作员', to_date('17-09-2013 17:55:08', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('03-06-2014 14:08:12', 'dd-mm-yyyy hh24:mi:ss'), '0', '测试', 'MERCHANT');
insert into ROLE (ROLE_ID, ROLE_NAME, CREATE_TIME, UPDATE_USER, UPDATE_TIME, STATUS, REMARK, ROLE_TYPE)
values (1100, '普通管理员', to_date('01-10-2013 10:11:49', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('28-10-2013 11:47:27', 'dd-mm-yyyy hh24:mi:ss'), '0', '备注', 'SP');
insert into ROLE (ROLE_ID, ROLE_NAME, CREATE_TIME, UPDATE_USER, UPDATE_TIME, STATUS, REMARK, ROLE_TYPE)
values (1250, '超级管理员', to_date('03-10-2013 16:55:43', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('27-03-2014 14:49:56', 'dd-mm-yyyy hh24:mi:ss'), '0', null, 'SP');
insert into ROLE (ROLE_ID, ROLE_NAME, CREATE_TIME, UPDATE_USER, UPDATE_TIME, STATUS, REMARK, ROLE_TYPE)
values (2250, '商户管理员', to_date('23-04-2014 10:45:26', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-04-2014 16:15:26', 'dd-mm-yyyy hh24:mi:ss'), '0', '商户管理员', 'MERCHANT');
insert into ROLE (ROLE_ID, ROLE_NAME, CREATE_TIME, UPDATE_USER, UPDATE_TIME, STATUS, REMARK, ROLE_TYPE)
values (1200, '系统操作员', to_date('01-10-2013 10:11:49', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('27-03-2014 14:30:46', 'dd-mm-yyyy hh24:mi:ss'), '0', null, 'SP');
insert into ROLE (ROLE_ID, ROLE_NAME, CREATE_TIME, UPDATE_USER, UPDATE_TIME, STATUS, REMARK, ROLE_TYPE)
values (3050, '普通用户', to_date('07-07-2014 16:42:28', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('07-07-2014 16:42:28', 'dd-mm-yyyy hh24:mi:ss'), '0', '普通用户', 'CUSTOMER');
commit;
insert into IDENTITY_ROLE (IDENTITY_ROLE_ID, IDENTITY_ID, IDENTITY_TYPE, ROLE_ID, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, STATUS, REMARK)
values (2150, 2452, 'OPERATOR', 1250, to_date('12-04-2014 12:38:09', 'dd-mm-yyyy hh24:mi:ss'), 'system', to_date('12-04-2014 12:38:09', 'dd-mm-yyyy hh24:mi:ss'), '0', null);
commit;
insert into IDENTITY_STATUS_TRANSFER (ID, OLD_IDENTITY_STATUS, NEW_IDENTITY_STATUS, ACTION_NAME, IDENTITY_TYPE)
values (1004, '0', '1', '禁用', 'OPERATOR');
insert into IDENTITY_STATUS_TRANSFER (ID, OLD_IDENTITY_STATUS, NEW_IDENTITY_STATUS, ACTION_NAME, IDENTITY_TYPE)
values (1005, '1', '0', '启用', 'OPERATOR');
insert into IDENTITY_STATUS_TRANSFER (ID, OLD_IDENTITY_STATUS, NEW_IDENTITY_STATUS, ACTION_NAME, IDENTITY_TYPE)
values (1001, '2', '0', '初始化启用', 'MERCHANT');
insert into IDENTITY_STATUS_TRANSFER (ID, OLD_IDENTITY_STATUS, NEW_IDENTITY_STATUS, ACTION_NAME, IDENTITY_TYPE)
values (1002, '0', '1', '禁用', 'MERCHANT');
insert into IDENTITY_STATUS_TRANSFER (ID, OLD_IDENTITY_STATUS, NEW_IDENTITY_STATUS, ACTION_NAME, IDENTITY_TYPE)
values (1003, '1', '0', '启用', 'MERCHANT');
commit;
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (7251, '加款申请', 10, '12200', 200, '0', to_date('27-08-2014 13:51:01', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('23-09-2014 14:37:19', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'APORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (9250, '返佣数据统计', 8, '10050', 150, '0', to_date('07-10-2014 15:19:36', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('07-10-2014 15:19:36', 'dd-mm-yyyy hh24:mi:ss'), '返佣数据统计', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (6251, '批量手工补单', 9, '7050', 3416, '0', to_date('15-08-2014 11:23:03', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-08-2014 12:40:41', 'dd-mm-yyyy hh24:mi:ss'), '批量手工补单（分商户）', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3417, '全部订单', 1, '3161', 3416, '0', to_date('19-04-2014 13:06:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('23-09-2014 14:02:55', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'PORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (5400, '商户操作员', 5, '6151', 751, '0', to_date('09-07-2014 11:14:51', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('22-09-2014 17:48:52', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'PORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (9300, '代理商交易量统计', 5, '10100', 4706, '0', to_date('11-10-2014 11:44:10', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('11-10-2014 11:44:10', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (9301, '供货商交易量统计', 6, '10101', 4706, '0', to_date('11-10-2014 11:44:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('11-10-2014 11:44:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (8550, '密钥管理', 9, '9300', 751, '0', to_date('15-09-2014 10:08:56', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:08:56', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (8300, '密钥类型', 11, '9100', 751, '0', to_date('04-09-2014 09:14:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('04-09-2014 09:14:13', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (150, '商户管理', 2, '1302', 0, '0', to_date('17-10-2013 09:26:20', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('19-04-2014 14:38:30', 'dd-mm-yyyy hh24:mi:ss'), '商户管理', null, 'Zero', 'PORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (400, '账户类型', 1, '1354', 200, '0', to_date('17-10-2013 14:29:06', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('30-10-2013 12:10:53', 'dd-mm-yyyy hh24:mi:ss'), '账户类型', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (5253, '资金变动', 9, '6052', 200, '0', to_date('04-07-2014 17:53:37', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('22-09-2014 10:08:44', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'APORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (751, '角色权限', 7, '1200', 0, '0', to_date('28-10-2013 10:50:53', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('19-04-2014 14:39:13', 'dd-mm-yyyy hh24:mi:ss'), '角色管理', null, 'Zero', 'PORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3418, '部分成功订单', 2, '4254', 3416, '0', to_date('19-04-2014 13:14:05', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('19-04-2014 13:14:38', 'dd-mm-yyyy hh24:mi:ss'), '部分成功订单列表', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3419, '超时订单', 3, '4255', 3416, '0', to_date('19-04-2014 13:15:08', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('19-04-2014 13:15:08', 'dd-mm-yyyy hh24:mi:ss'), '超时订单列表', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (5252, '产品信息', 6, '6053', 201, '0', to_date('04-07-2014 17:49:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('22-09-2014 10:08:17', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'APORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (758, '角色管理', 1, '1359', 751, '0', to_date('28-10-2013 11:13:04', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('28-10-2013 11:13:04', 'dd-mm-yyyy hh24:mi:ss'), '角色列表', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3420, '人工审核订单', 4, '4256', 3416, '0', to_date('19-04-2014 13:15:26', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('22-04-2014 09:58:49', 'dd-mm-yyyy hh24:mi:ss'), '人工审核订单', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3501, '产品等级分配', 6, '3154', 201, '0', to_date('22-04-2014 09:18:18', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('22-04-2014 09:18:18', 'dd-mm-yyyy hh24:mi:ss'), '产品等级分配', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4250, '系统账户管理', 4, '4800', 200, '0', to_date('30-04-2014 13:15:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('30-04-2014 13:15:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4600, '返佣数据管理', 7, '5400', 150, '0', to_date('30-05-2014 16:11:30', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('07-10-2014 15:13:19', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4451, '返佣设置', 6, '5000', 150, '0', to_date('28-05-2014 11:19:28', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('11-07-2014 13:33:35', 'dd-mm-yyyy hh24:mi:ss'), '返佣比配置列表', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4550, '手工补单', 7, '5050', 3416, '0', to_date('29-05-2014 14:33:40', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-05-2014 14:33:40', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4706, '报表管理', 8, '5250', 0, '0', to_date('04-06-2014 17:46:26', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-06-2014 15:33:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'Zero', 'PORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4707, '交易量统计', 2, '5206', 4706, '0', to_date('05-06-2014 14:59:08', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('25-06-2014 17:43:31', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (200, '账户管理', 3, '1301', 0, '0', to_date('17-10-2013 09:30:51', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('19-04-2014 14:39:03', 'dd-mm-yyyy hh24:mi:ss'), '账户管理', null, 'Zero', 'PORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (201, '产品管理', 4, '1300', 0, '0', to_date('17-10-2013 09:40:35', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('19-04-2014 14:39:27', 'dd-mm-yyyy hh24:mi:ss'), '产品管理', null, 'Zero', 'PORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (5250, '接口配置', 2, '6050', 4800, '0', to_date('01-07-2014 09:35:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-07-2014 09:35:52', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (2351, '交易配置', 5, '3153', 0, '0', to_date('27-03-2014 11:10:51', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('19-04-2014 14:39:33', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'Zero', 'PORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (5303, '接口常量配置', 4, '6100', 4800, '0', to_date('01-07-2014 16:02:31', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-07-2014 16:02:31', 'dd-mm-yyyy hh24:mi:ss'), '接口常量配置', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (2353, '质量权重设置', 1, '3152', 2351, '0', to_date('27-03-2014 11:12:06', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('08-07-2014 14:21:44', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4850, '利润归集', 8, '5350', 200, '0', to_date('16-06-2014 15:02:32', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('04-07-2014 10:28:14', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3408, '菜单管理', 2, '1360', 751, '0', to_date('19-04-2014 10:40:36', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('23-04-2014 11:56:51', 'dd-mm-yyyy hh24:mi:ss'), '菜单权限列表', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (2300, '产品属性管理', 3, '3100', 201, '0', to_date('25-03-2014 15:54:32', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('25-03-2014 15:54:32', 'dd-mm-yyyy hh24:mi:ss'), '产品属性列表', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3511, '代理商预成功配置', 5, '4300', 150, '0', to_date('22-04-2014 09:26:06', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('22-04-2014 10:22:08', 'dd-mm-yyyy hh24:mi:ss'), '代理商预成功配置', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (2400, '代理商管理', 4, '3200', 150, '0', to_date('29-03-2014 15:44:42', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('22-04-2014 10:22:02', 'dd-mm-yyyy hh24:mi:ss'), '代理商管理', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3250, '指定排除配置管理', 3, '4051', 2351, '0', to_date('01-04-2014 14:10:51', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('08-07-2014 14:22:06', 'dd-mm-yyyy hh24:mi:ss'), '指定排除配置管理', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3409, '页面资源管理', 3, '1361', 751, '0', to_date('19-04-2014 10:41:10', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('19-04-2014 10:41:10', 'dd-mm-yyyy hh24:mi:ss'), '页面资源列表', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3405, '号段管理', 5, '1362', 2351, '0', to_date('19-04-2014 10:27:24', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('08-07-2014 14:22:23', 'dd-mm-yyyy hh24:mi:ss'), '号段管理', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3416, '订单管理', 1, '4253', 0, '0', to_date('19-04-2014 13:04:54', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('19-04-2014 14:39:18', 'dd-mm-yyyy hh24:mi:ss'), '订单管理', null, 'Zero', 'PORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3451, '供货商账户管理', 2, '4350', 200, '0', to_date('21-04-2014 13:57:37', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('21-04-2014 15:17:25', 'dd-mm-yyyy hh24:mi:ss'), '供货商账户列表', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3452, '代理商账户管理', 3, '4301', 200, '0', to_date('21-04-2014 14:00:55', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('21-04-2014 14:01:08', 'dd-mm-yyyy hh24:mi:ss'), '代理商账户列表', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3500, '发货记录', 6, '4450', 3416, '0', to_date('21-04-2014 23:06:36', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('23-05-2014 10:02:30', 'dd-mm-yyyy hh24:mi:ss'), '发货记录', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3700, '参数管理', 2, '4700', 2351, '0', to_date('24-04-2014 16:11:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('08-07-2014 14:21:57', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4700, '手动充值', 8, '5200', 3416, '0', to_date('04-06-2014 10:18:10', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('22-09-2014 10:00:56', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'APORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4750, '报表类型配置', 1, '4900', 4706, '0', to_date('06-06-2014 14:05:08', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('20-06-2014 10:17:06', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4800, '接入配置', 6, '5300', 0, '0', to_date('12-06-2014 10:33:30', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('12-06-2014 10:34:19', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'Zero', 'PORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3650, '账户日志', 6, '4600', 200, '0', to_date('23-04-2014 13:19:25', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('23-04-2014 13:19:25', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4400, '订单查询（预）', 5, '4850', 3416, '0', to_date('23-05-2014 10:02:18', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('23-05-2014 10:02:35', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (5350, '系统操作员', 4, '6150', 751, '0', to_date('09-07-2014 11:11:07', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('09-07-2014 11:11:07', 'dd-mm-yyyy hh24:mi:ss'), '系统操作员管理', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3413, '供货商产品管理', 4, '4250', 201, '0', to_date('19-04-2014 12:02:07', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('19-04-2014 12:46:23', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3415, '代理商产品管理', 5, '4252', 201, '0', to_date('19-04-2014 12:46:13', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('19-04-2014 12:46:13', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4713, '利润统计', 4, '5212', 4706, '0', to_date('05-06-2014 16:10:05', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('25-06-2014 17:44:14', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4716, '账户统计', 5, '5215', 4706, '0', to_date('05-06-2014 16:12:29', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('25-06-2014 17:44:05', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (300, '组织机构管理', 1, '1351', 150, '0', to_date('17-10-2013 11:22:28', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('11-07-2014 13:30:36', 'dd-mm-yyyy hh24:mi:ss'), '组织机构管理', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (500, '产品类型管理', 2, '1363', 201, '0', to_date('17-10-2013 15:56:01', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('19-04-2014 10:59:40', 'dd-mm-yyyy hh24:mi:ss'), '产品类型列表', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (501, '产品管理', 1, '1364', 201, '0', to_date('17-10-2013 15:59:46', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('19-04-2014 10:59:47', 'dd-mm-yyyy hh24:mi:ss'), '产品列表', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (502, '供货商管理', 2, '1352', 150, '0', to_date('17-10-2013 16:13:01', 'dd-mm-yyyy hh24:mi:ss'), 'JingerUpdate', to_date('29-03-2014 11:02:10', 'dd-mm-yyyy hh24:mi:ss'), '供货商管理', null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (3453, '交易日志', 5, '4400', 200, '0', to_date('21-04-2014 16:43:10', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('07-07-2014 09:56:49', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (4900, '用户管理', 0, '1358', 751, '0', to_date('19-06-2014 16:01:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('19-06-2014 16:01:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (5351, '产品批量操作', 8, '6152', 201, '0', to_date('09-07-2014 14:10:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('09-07-2014 14:10:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (8600, '接口证书列表', 4, '9350', 4800, '0', to_date('16-09-2014 14:55:47', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('16-09-2014 14:55:47', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (8250, '密钥规则', 10, '9050', 751, '0', to_date('04-09-2014 09:13:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('04-09-2014 09:13:53', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (7300, '加款审核', 11, '12150', 200, '0', to_date('27-08-2014 13:54:38', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('23-09-2014 14:36:13', 'dd-mm-yyyy hh24:mi:ss'), null, null, 'One', 'MPORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (7400, '权限列表', 3, '8100', 751, '0', to_date('29-08-2014 16:51:54', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-08-2014 16:52:56', 'dd-mm-yyyy hh24:mi:ss'), '权限列表信息', null, 'One', 'MPORTAL');
commit;
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (11300, '账户充值', 3, '12100', 200, '0', to_date('05-11-2014 09:48:42', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-11-2014 09:50:19', 'dd-mm-yyyy hh24:mi:ss'), '账户充值（静态界面）', null, 'One', 'APORTAL');
insert into MENU (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (11250, '用户充值', 3, '12050', 3416, '0', to_date('05-11-2014 09:38:10', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-11-2014 09:38:10', 'dd-mm-yyyy hh24:mi:ss'), '用户充值（静态界面）', null, 'One', 'APORTAL');
commit;
insert into OPERATOR (IDENTITY_ID, PERSON_ID, OWNER_IDENTITY_ID, LAST_UPDATE_USER, LAST_UPDATE_DATE, STATUS, OPERATOR_TYPE, REMARK, OWNER_IDENTITY_TYPE, OPERATOR_NAME, DISPLAY_NAME)
values (2452, 3252, 2333, null, null, '0', 'SP_OPERATOR', null, 'SP', 'admin', 'admin');
commit;
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (9101, '权限分配树', 'role_addPrivilege', '1', to_date('28-10-2013 16:28:47', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 16:28:47', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (8150, '编辑权限', 'privilege_edit', '1', to_date('29-08-2014 15:31:37', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-08-2014 15:31:37', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (7050, '批量手工补单', 'Batch/orderrequesthandlerlist', '1', to_date('15-08-2014 11:09:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-08-2014 11:21:49', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (10050, '返佣数据统计列表', 'rebateRecordHistory/rebateRecordHistoryList', '1', to_date('07-10-2014 15:14:38', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('07-10-2014 15:14:38', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin');
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (11052, '阿萨德发', 'remark', '1', to_date('29-10-2014 16:57:38', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:57:38', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin');
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (10100, '代理商交易量统计', 'report/agentTransactionReports', '1', to_date('11-10-2014 11:42:08', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('11-10-2014 11:42:08', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin');
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (10101, '供货商交易量统计', 'report/supplyTransactionReports', '1', to_date('11-10-2014 11:43:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('11-10-2014 11:43:00', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin');
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (9150, '权限分配树', 'Role/role_addtreemenu', '1', to_date('04-09-2014 16:18:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('04-09-2014 16:19:07', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin');
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (9300, '密钥管理', 'security/securityCredentialList', '1', to_date('15-09-2014 10:08:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:08:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin');
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1302, '商户管理', 'merchant', '1', to_date('11-10-2013 13:59:44', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('28-10-2013 09:52:23', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1300, '产品管理', 'product', '1', to_date('11-10-2013 13:59:22', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('28-10-2013 09:53:15', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1301, '账户管理', 'account', '1', to_date('11-10-2013 13:59:34', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('28-10-2013 09:52:59', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1351, '组织机构管理', 'Organization/organizationList', '1', to_date('28-10-2013 09:55:35', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('11-07-2014 15:29:20', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1352, '供应商管理', 'Merchant/supplylist', '1', to_date('28-10-2013 09:56:00', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('29-03-2014 15:42:43', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1353, '商户管理员维护', 'Operator/list', '1', to_date('28-10-2013 09:56:16', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('31-10-2013 16:46:14', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1354, '账户类型', 'accountType/accountTypeList', '1', to_date('28-10-2013 09:56:30', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 09:56:30', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1358, '用户管理', 'Customer/list', '1', to_date('28-10-2013 09:57:24', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 09:57:24', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1359, '角色管理', 'Role/list', '1', to_date('28-10-2013 09:57:36', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 09:57:36', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1360, '菜单权限列表', 'Menu/listtree', '1', to_date('28-10-2013 09:57:50', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-09-2014 11:47:29', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1361, '页面资源列表', 'PageResource/list', '1', to_date('28-10-2013 09:58:02', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 09:58:02', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1362, '号段列表', 'NumSection/list', '1', to_date('28-10-2013 09:58:15', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 09:58:15', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1363, '产品类型列表', 'product/productTypeList', '1', to_date('28-10-2013 09:58:27', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 09:58:27', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1364, '产品列表', 'product/productList', '1', to_date('28-10-2013 09:58:38', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 09:58:38', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1450, '查看角色', 'role_view', '1', to_date('28-10-2013 16:26:52', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 16:26:52', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1451, '编辑角色', 'role_edit', '1', to_date('28-10-2013 16:28:09', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 16:28:09', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1452, '删除角色', 'role_delete', '1', to_date('28-10-2013 16:28:27', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 16:28:27', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1454, '查看页面资源', 'pageresource_view', '1', to_date('28-10-2013 16:29:17', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 16:29:17', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1455, '编辑页面资源', 'pageresource_edit', '1', to_date('28-10-2013 16:29:31', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 16:29:31', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1456, '删除页面资源', 'pageresource_delete', '1', to_date('28-10-2013 16:29:50', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 16:29:50', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1457, '编辑号段', 'numsection_edit', '1', to_date('28-10-2013 16:30:43', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 16:30:43', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1458, '删除页面资源', 'numsection_delete', '1', to_date('28-10-2013 16:30:56', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 16:30:56', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (3152, '质量权重列表', 'transaction/qualityWeightRuleList', '1', to_date('27-03-2014 11:01:00', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('27-03-2014 11:06:27', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4200, '联系我们', '#', '1', to_date('17-04-2014 13:53:34', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('17-04-2014 13:53:34', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4254, '部分成功订单', 'transaction/orderPartSuccessList', '1', to_date('19-04-2014 13:11:29', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('19-04-2014 13:11:29', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4255, '超时订单', 'transaction/orderTimeOutList', '1', to_date('19-04-2014 13:11:48', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('19-04-2014 13:11:48', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4256, '人工审核订单列表', 'transaction/manualAuditOrderList', '1', to_date('19-04-2014 13:12:10', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('19-04-2014 13:12:10', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4350, '供货商账户列表', 'Merchant/allSupplyAccountList', '1', to_date('21-04-2014 13:56:57', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('21-04-2014 15:30:27', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4505, '供货商账户详情', 'account/toShowAccount', '1', to_date('22-04-2014 17:13:09', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('22-04-2014 17:30:32', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4506, '供货商解锁锁定', 'account/updateAccountStatus', '1', to_date('22-04-2014 17:13:42', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('22-04-2014 17:30:26', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4507, '供货商加款', 'account/toCurrencyAccountDebit', '1', to_date('22-04-2014 17:14:03', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('22-04-2014 17:30:20', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4508, '供货商授信加款', 'account/toAddCreditableBanlance', '1', to_date('22-04-2014 17:14:31', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('22-04-2014 17:30:10', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4509, '代理商详情', '/account/toShowAccount', '1', to_date('22-04-2014 17:31:20', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-04-2014 15:01:31', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4510, '代理商解锁锁定', '/account/updateAccountStatus', '1', to_date('22-04-2014 17:35:04', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('22-04-2014 17:35:28', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4800, '系统类型账户列表', 'account/showSPCrrencyAccountList', '1', to_date('30-04-2014 13:13:54', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('30-04-2014 13:25:14', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5000, '返佣比配置列表界面', 'RebateRule/list', '1', to_date('28-05-2014 11:17:18', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('28-05-2014 11:17:51', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (6150, '系统操作员管理', 'Operator/sysOperatorList', '1', to_date('09-07-2014 10:56:55', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('09-07-2014 10:56:55', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5050, '手工补单', 'transaction/toOrderRequestHandler', '1', to_date('29-05-2014 14:32:59', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-05-2014 14:32:59', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5101, '返佣', 'rebateRebateHistory', '1', to_date('30-05-2014 16:10:41', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('30-05-2014 16:10:41', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5201, '删除返佣数据', 'deleteRebateHistory', '1', to_date('04-06-2014 15:18:54', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('04-06-2014 15:18:54', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5206, '每日交易量', 'report/transactionReports', '1', to_date('04-06-2014 17:50:20', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('11-06-2014 14:54:31', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5203, '开关返佣配置', 'changeRebateRuleStatus', '1', to_date('04-06-2014 15:22:49', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('04-06-2014 15:22:49', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5204, '编辑返佣配置', 'editRebateRule', '1', to_date('04-06-2014 15:23:04', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('04-06-2014 15:23:04', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (6053, '产品信息', 'product/agentProductRelationInfoList', '1', to_date('04-07-2014 17:52:35', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-07-2014 13:24:39', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (6151, '商户操作员管理', 'Operator/merchantOperatorList', '1', to_date('09-07-2014 10:57:19', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('09-07-2014 10:57:19', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5209, '每日已成交易量', 'report/toEveryDaySuccessTransactionList', '1', to_date('04-06-2014 18:08:01', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('12-06-2014 17:27:01', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5400, '返佣记录', 'rebateRecord/rebateRecordList', '1', to_date('18-06-2014 13:55:51', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('18-06-2014 13:55:51', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5350, '利润归档', 'profitImputation/profitImputationList', '1', to_date('16-06-2014 15:01:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('17-09-2014 11:32:28', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5212, '每日利润统计', 'report/profitReports', '1', to_date('04-06-2014 19:10:18', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-06-2014 15:37:27', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5215, '每日账户统计', 'report/accountReports', '1', to_date('04-06-2014 19:12:27', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-06-2014 15:36:53', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1402, '删除用户', 'customer_delete', '1', to_date('28-10-2013 14:56:25', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 14:56:25', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1403, '分配角色', 'addCustomerRole', '1', to_date('28-10-2013 14:56:46', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 14:56:46', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1400, '查看用户', 'customer_view', '1', to_date('28-10-2013 14:55:52', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 14:55:52', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1401, '编辑用户', 'customer_edit', '1', to_date('28-10-2013 14:56:10', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-10-2013 14:56:10', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (3153, '交易管理', 'transaction', '1', to_date('27-03-2014 11:10:28', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('27-03-2014 15:54:43', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4051, '指定排除配置列表', 'AssignExclude/listAssignExclude', '1', to_date('01-04-2014 14:08:19', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('01-04-2014 14:09:50', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4050, '编辑指定排除配置', 'assignExclude_edit', '1', to_date('01-04-2014 14:08:20', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('01-04-2014 14:09:42', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4052, '删除指定排除配置', 'assignExclude_delete', '1', to_date('01-04-2014 14:09:26', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('01-04-2014 14:09:46', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4451, '供货商查询规则', 'transaction/setRuleList', '1', to_date('22-04-2014 10:20:48', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('25-06-2014 11:57:44', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4500, '组织机构详情', 'organization_view', '1', to_date('22-04-2014 13:38:53', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('22-04-2014 13:39:25', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4501, '编辑组织机构', 'organization_edit', '1', to_date('22-04-2014 13:39:16', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('22-04-2014 13:39:21', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4502, '编辑预成功配置', 'toEditAgentQueryFakeRule', '1', to_date('22-04-2014 14:07:42', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('22-04-2014 14:07:42', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4503, '删除预成功配置', 'doDeleteAgentQueryFakeRule', '1', to_date('22-04-2014 14:08:00', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('22-04-2014 14:08:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4550, '设置查询规则', 'showSetrule', '1', to_date('22-04-2014 17:01:08', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('22-04-2014 17:01:40', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4600, '账户日志查询', 'accountHistory/accountBalanceHistoryList', '1', to_date('23-04-2014 13:18:25', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('22-07-2014 17:15:18', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (6050, '接口管理', 'interface/interfaceConfList', '1', to_date('01-07-2014 09:35:23', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-07-2014 09:35:23', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5250, '报表', 'report', '1', to_date('05-06-2014 15:19:44', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-06-2014 15:31:39', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5300, '接入配置', 'interface', '1', to_date('12-06-2014 10:34:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('12-06-2014 10:34:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (6100, '接口常量配置', 'InterfaceConstant/list', '1', to_date('01-07-2014 15:54:59', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-07-2014 15:56:10', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (6102, '编辑接口常量', 'editinterfaceconstant', '1', to_date('01-07-2014 15:56:46', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-07-2014 15:56:46', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (6103, '删除接口常量', 'deleteinterfaceconstant', '1', to_date('01-07-2014 15:57:05', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('01-07-2014 15:57:05', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (3100, '产品属性列表', 'product/productPropertyList', '1', to_date('25-03-2014 15:52:06', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('25-03-2014 15:52:06', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (3154, '商户分组设置', 'MerchantLevel/list', '1', to_date('27-03-2014 14:53:10', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('27-03-2014 14:57:28', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (3155, '删除商户分组页面', 'merchantlevel_delete', '1', to_date('27-03-2014 14:55:35', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('27-03-2014 15:02:57', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (3200, '代理商管理', 'Merchant/agentlist', '1', to_date('29-03-2014 15:43:07', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('29-03-2014 15:43:17', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4252, '代理商产品管理', 'product/allAgentProductRelation', '1', to_date('19-04-2014 12:45:01', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('19-04-2014 12:45:01', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4253, '订单管理', 'order', '1', to_date('19-04-2014 13:05:14', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('19-04-2014 13:05:14', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4300, '预成功配置', 'agentQueryFakeRule/agentQueryFakeRuleList', '1', to_date('21-04-2014 10:01:20', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('21-04-2014 10:01:20', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4301, '代理商账户列表', 'Merchant/allAgentAccountList', '1', to_date('21-04-2014 13:56:20', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('21-04-2014 14:44:29', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4400, '账户交易日志', 'accountHistory/accountTransactionLogList', '1', to_date('21-04-2014 16:42:05', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('22-07-2014 17:14:49', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4450, '发货记录', 'transaction/deliveryRecordList', '1', to_date('21-04-2014 23:05:13', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('21-04-2014 23:05:13', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4504, '删除查询规则', 'delSetRule', '1', to_date('22-04-2014 17:01:32', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('22-04-2014 17:01:32', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4551, '供货商减款', 'account/toCurrencyAccountCredit', '1', to_date('22-04-2014 17:14:16', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('22-04-2014 17:30:15', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4552, '供货商授信减款', 'account/toSubCreditableBanlance', '1', to_date('22-04-2014 17:14:46', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('22-04-2014 17:30:06', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4553, '代理商加款', '/account/toCurrencyAccountDebit', '1', to_date('22-04-2014 17:35:18', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-04-2014 15:01:38', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4554, '代理商减款', '/account/toCurrencyAccountCredit', '1', to_date('22-04-2014 17:35:41', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-04-2014 15:02:35', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4555, '代理商授信加款', '/account/toAddCreditableBanlance', '1', to_date('22-04-2014 17:35:58', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-04-2014 15:02:32', 'dd-mm-yyyy hh24:mi:ss'), null, null);
commit;
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4556, '代理商授信减款', '/account/toSubCreditableBanlance', '1', to_date('22-04-2014 17:36:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-04-2014 15:02:29', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4700, '参数配置管理', 'transaction/queryParameterConfiguration', '1', to_date('24-04-2014 16:10:17', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-04-2014 16:39:56', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (3150, '商户角色分配', 'addMerchantRole', '1', to_date('25-03-2014 17:32:38', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('25-03-2014 17:32:38', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (3161, '订单列表页面', 'transaction/orderList', '1', to_date('28-03-2014 17:40:02', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('28-03-2014 17:40:02', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (3162, '上游商户错误码配置', 'MerchantResponse/list', '1', to_date('29-03-2014 11:20:09', 'dd-mm-yyyy hh24:mi:ss'), null, to_date('29-03-2014 11:20:09', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (3163, '编辑上游商户错误码', 'merchantresponse_edit', '1', to_date('29-03-2014 11:21:04', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('29-03-2014 14:51:54', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (3164, '删除上游商户错误码', 'merchantresponse_delete', '1', to_date('29-03-2014 11:21:27', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('29-03-2014 14:51:46', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4250, '供货商产品列表', 'product/allSupplyProductRelation', '1', to_date('19-04-2014 12:01:20', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('19-04-2014 12:45:16', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4650, '产品等级分配列表编辑', 'editMerchantLevel', '1', to_date('23-04-2014 13:35:44', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-04-2014 15:02:22', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4850, '预成功订单查询', 'transaction/fakeOrderList', '1', to_date('23-05-2014 10:01:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('23-05-2014 10:01:22', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (9100, '密钥类型', 'securitytype/securitytypepagelist', '1', to_date('04-09-2014 09:12:02', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('04-09-2014 09:12:02', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin');
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4900, '报表类型配置', 'report/reportTypeList', '1', to_date('23-05-2014 16:09:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('06-06-2014 11:56:33', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (4950, '密钥属性列表', 'security/securityPropertyList', '1', to_date('27-05-2014 11:18:46', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('27-05-2014 11:18:46', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (5200, '商户手动充值', 'transaction/toMOrderRequestHandler', '1', to_date('04-06-2014 10:17:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('04-06-2014 10:26:34', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (1200, '角色管理', 'role', '1', to_date('11-10-2013 10:17:49', 'dd-mm-yyyy hh24:mi:ss'), 'Jinger', to_date('28-10-2013 09:54:00', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (6052, '资金变动', 'transaction/fundchangelist', '1', to_date('04-07-2014 17:45:12', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-07-2014 13:24:05', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (6152, '产品批量操作列表', 'productOperation/productOperationList', '1', to_date('09-07-2014 14:09:41', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('09-07-2014 14:09:41', 'dd-mm-yyyy hh24:mi:ss'), null, null);
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (9050, '密钥规则', 'securityrule/securityrulepagelist', '1', to_date('04-09-2014 09:11:32', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('04-09-2014 09:11:32', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin');
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (9350, '接口证书列表', 'certfile/certfilelist', '1', to_date('16-09-2014 14:54:51', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('16-09-2014 14:54:51', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin');
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (8100, '权限列表', 'privilege/privilege_list', '1', to_date('29-08-2014 15:30:47', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-08-2014 15:30:47', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin');
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (8101, '删除权限', 'privilege_delete', '1', to_date('29-08-2014 15:31:58', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-08-2014 15:31:58', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin');
commit;
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (12100, '账户充值', 'recharge/accountrecharge', '1', to_date('05-11-2014 09:49:57', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-11-2014 10:04:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin');
insert into PAGE_RESOURCE (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (12050, '用户充值', 'recharge/userinput', '1', to_date('05-11-2014 09:37:11', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-11-2014 10:03:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin');
commit;
insert into page_resource (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (12200, '申请加款列表', 'account/aportalAccountAddCashRecordList', '1', to_date('08-11-2014 12:49:05', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('08-11-2014 12:49:05', 'dd-mm-yyyy hh24:mi:ss'), '', 'admin');
insert into page_resource (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (12150, '加款申请审核', 'account/accountAddCashRecordList', '1', to_date('07-11-2014 17:03:28', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('07-11-2014 17:03:28', 'dd-mm-yyyy hh24:mi:ss'), '', 'admin');
commit;
insert into PERSON (PERSON_ID, UPDATE_USER, UPDATE_TIME, SEX, BIRTHDAY, CREATE_TIME, REMARK, EMAIL)
values (3252, 'admin', to_date('12-04-2014 12:37:51', 'dd-mm-yyyy hh24:mi:ss'), '01', '2014-04-08', to_date('12-04-2014 12:37:51', 'dd-mm-yyyy hh24:mi:ss'), null, null);
commit;
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (72000, '删除预成功配置', 'fakerule:delete', 66016, to_date('05-09-2014 18:08:42', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 18:09:08', 'dd-mm-yyyy hh24:mi:ss'), '删除代理商预成功配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (72001, '返佣设置', 'rebate:list', 0, to_date('05-09-2014 18:15:23', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 18:15:23', 'dd-mm-yyyy hh24:mi:ss'), '返佣设置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (72002, '修改返佣配置状态', 'rebate:changestatus_show', 72001, to_date('05-09-2014 18:16:04', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 18:16:04', 'dd-mm-yyyy hh24:mi:ss'), '修改返佣配置状态');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (72003, '删除返佣配置', 'rebate:delete', 72001, to_date('05-09-2014 18:16:43', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 18:16:43', 'dd-mm-yyyy hh24:mi:ss'), '删除返佣配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (88001, '指定排除管理', 'assignExclude:list', 0, to_date('10-09-2014 16:21:03', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:21:03', 'dd-mm-yyyy hh24:mi:ss'), '指定排除管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (88002, '编辑指定排除', 'assignExclude:edit_show', 88001, to_date('10-09-2014 16:22:49', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:22:49', 'dd-mm-yyyy hh24:mi:ss'), '编辑指定排除');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (88003, '删除指定排除', 'assignExclude:delete', 88001, to_date('10-09-2014 16:23:06', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:23:06', 'dd-mm-yyyy hh24:mi:ss'), '删除指定排除');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (88004, '编辑产品等级', 'merchantLevel:edit_show', 89001, to_date('10-09-2014 16:30:51', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:30:51', 'dd-mm-yyyy hh24:mi:ss'), '编辑产品等级');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (88005, '接入配置', 'interface:list', 0, to_date('10-09-2014 16:32:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:32:15', 'dd-mm-yyyy hh24:mi:ss'), '接入配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (88006, '添加接口常量', 'interfaceConstant:add_show', 88005, to_date('10-09-2014 16:33:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:33:15', 'dd-mm-yyyy hh24:mi:ss'), '添加接口常量配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (88008, '删除接口常量', 'interfaceConstant:delete', 88005, to_date('10-09-2014 16:36:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:36:00', 'dd-mm-yyyy hh24:mi:ss'), '删除接口常量配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (67001, '添加权限', 'privilege:add_show', 68001, to_date('05-09-2014 16:18:23', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:18:23', 'dd-mm-yyyy hh24:mi:ss'), '添加权限');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (67000, '查看角色信息', 'role:view', 66003, to_date('05-09-2014 16:14:18', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:14:18', 'dd-mm-yyyy hh24:mi:ss'), '查看角色信息');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (68001, '权限管理', 'privilege:list', 0, to_date('05-09-2014 16:18:05', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:18:05', 'dd-mm-yyyy hh24:mi:ss'), '权限管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (68002, '删除权限', 'privilege:delete', 68001, to_date('05-09-2014 16:19:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:19:16', 'dd-mm-yyyy hh24:mi:ss'), '删除权限');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (68003, '编辑页面资源', 'pageresource:edit_view', 66007, to_date('05-09-2014 16:22:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:22:52', 'dd-mm-yyyy hh24:mi:ss'), '编辑页面资源');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (68004, '删除页面资源', 'pageresource:delete', 66007, to_date('05-09-2014 16:24:23', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:24:23', 'dd-mm-yyyy hh24:mi:ss'), '删除页面资源');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (69000, '修改系统操作员密码', 'soperator:changepwd_view', 87000, to_date('05-09-2014 16:53:31', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:53:31', 'dd-mm-yyyy hh24:mi:ss'), '修改系统操作员密码');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (82000, '代理商账户加款', 'agentAccount:debitCurrency_show', 78000, to_date('10-09-2014 11:41:08', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 11:41:08', 'dd-mm-yyyy hh24:mi:ss'), '代理商账户加款');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (82001, '代理商账户减款', 'agentAccount:creditCurrency_show', 78000, to_date('10-09-2014 11:41:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 11:41:33', 'dd-mm-yyyy hh24:mi:ss'), '代理商账户减款');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (68000, '分配菜单', 'role:addmenu_show', 66003, to_date('05-09-2014 16:16:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:16:16', 'dd-mm-yyyy hh24:mi:ss'), '分配菜单');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (79000, '供货商管理', 'smerchant:list', 0, to_date('10-09-2014 11:25:02', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 11:25:02', 'dd-mm-yyyy hh24:mi:ss'), '供货商管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (106000, '生成数据', 'rebateRecord:bulid_show', 72001, to_date('07-10-2014 16:00:46', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('07-10-2014 16:00:46', 'dd-mm-yyyy hh24:mi:ss'), '生成返佣控制数据');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (106001, '删除返佣统计数据', 'rebateHistory:del_show', 72001, to_date('07-10-2014 16:04:08', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('07-10-2014 16:04:08', 'dd-mm-yyyy hh24:mi:ss'), '删除返佣统计数据');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (106002, '重新清算', 'rebate:rebuild_show', 72001, to_date('07-10-2014 16:37:24', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('07-10-2014 16:37:24', 'dd-mm-yyyy hh24:mi:ss'), '重新清算');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (90000, '创建接口配置', 'interfacePackets:add_show', 88005, to_date('10-09-2014 17:01:36', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 17:01:36', 'dd-mm-yyyy hh24:mi:ss'), '创建接口配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (90001, '编辑接口配置', 'interfacePackets:edit_show', 88005, to_date('10-09-2014 17:01:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 17:01:53', 'dd-mm-yyyy hh24:mi:ss'), '编辑接口配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (85000, '供货商账户锁定', 'supplyAccount:block_show', 83001, to_date('10-09-2014 14:07:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 14:07:21', 'dd-mm-yyyy hh24:mi:ss'), '供货商账户锁定');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (85001, '供货商加款', 'supplyAccount:debitCurrency_show', 83001, to_date('10-09-2014 14:08:37', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 14:08:37', 'dd-mm-yyyy hh24:mi:ss'), '供货商加款');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (85002, '供货商授信加款', 'supplyAccount:debitCreditable_show', 83001, to_date('10-09-2014 14:09:17', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 14:09:17', 'dd-mm-yyyy hh24:mi:ss'), '供货商授信加款');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89001, '产品管理', 'product:list', 0, to_date('10-09-2014 17:06:55', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 17:06:55', 'dd-mm-yyyy hh24:mi:ss'), '产品管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (65000, '用户管理', 'customer:list', 0, to_date('05-09-2014 15:56:44', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 15:56:44', 'dd-mm-yyyy hh24:mi:ss'), '用户列表管理界面');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (65001, '添加用户信息', 'customer:add_show', 65000, to_date('05-09-2014 15:57:20', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:00:19', 'dd-mm-yyyy hh24:mi:ss'), '添加用户信息');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (65002, '编辑用户信息', 'customer:edit_show', 65000, to_date('05-09-2014 15:58:07', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 15:58:07', 'dd-mm-yyyy hh24:mi:ss'), '编辑用户信息');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (65003, '删除用户信息', 'customer:delete', 65000, to_date('05-09-2014 15:59:10', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 15:59:10', 'dd-mm-yyyy hh24:mi:ss'), '用户信息删除');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (65004, '给用户分配角色', 'customer:addroles_show', 65000, to_date('05-09-2014 16:02:19', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:02:19', 'dd-mm-yyyy hh24:mi:ss'), '给用户分配角色');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (65005, '编辑角色', 'role:edit_show', 66003, to_date('05-09-2014 16:14:42', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:14:42', 'dd-mm-yyyy hh24:mi:ss'), '编辑角色');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (65006, '查看页面资源', 'pageresource:view', 66007, to_date('05-09-2014 16:22:05', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:22:05', 'dd-mm-yyyy hh24:mi:ss'), '查看页面资源');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (65007, '添加商户操作员', 'moperator:add_view', 66009, to_date('05-09-2014 16:54:42', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:54:42', 'dd-mm-yyyy hh24:mi:ss'), '添加商户操作员');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (65008, '编辑商户操作员', 'moperator:edit_view', 66009, to_date('05-09-2014 16:55:04', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:55:04', 'dd-mm-yyyy hh24:mi:ss'), '编辑商户操作员');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (83000, '代理商信用减款', 'agentAccount:creditCreditable_show', 78000, to_date('10-09-2014 11:42:17', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 11:42:17', 'dd-mm-yyyy hh24:mi:ss'), '代理商信用减款');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (83001, '供货商账户管理', 'saccount:list', 0, to_date('10-09-2014 11:43:10', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 11:43:10', 'dd-mm-yyyy hh24:mi:ss'), '供货商账户管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (127000, '删除角色', 'role:delete', 66003, to_date('29-10-2014 16:48:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('29-10-2014 16:48:47', 'dd-mm-yyyy hh24:mi:ss'), '删除角色功能啊');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (81000, '代理商账户锁定', 'agentAccount:block_show', 78000, to_date('10-09-2014 11:40:45', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 11:40:45', 'dd-mm-yyyy hh24:mi:ss'), '代理商账户锁定解锁');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92046, '批量关闭代理商产品', 'agentProduct:closelist_execute', 92024, to_date('11-09-2014 16:52:48', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:52:48', 'dd-mm-yyyy hh24:mi:ss'), '批量关闭代理商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92047, '批量修改供货商产品折扣', 'supplyProduct:editlist_execute', 92025, to_date('11-09-2014 16:58:27', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:58:27', 'dd-mm-yyyy hh24:mi:ss'), '批量修改供货商产品折扣');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92048, '批量开启供货商产品', 'supplyProduct:opentlist_execute', 92025, to_date('11-09-2014 16:59:50', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:59:50', 'dd-mm-yyyy hh24:mi:ss'), '批量开启供货商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92049, '批量关闭供货商产品', 'supplyProduct:closelist_execute', 92025, to_date('11-09-2014 17:01:02', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:01:02', 'dd-mm-yyyy hh24:mi:ss'), '批量关闭供货商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93000, '创建批量开关产品任务', 'productoperation:addlist_show', 89001, to_date('11-09-2014 17:17:05', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:17:05', 'dd-mm-yyyy hh24:mi:ss'), '创建批量开关产品任务');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93001, '启动产品开关任务', 'productoperation:start_show', 89001, to_date('11-09-2014 17:17:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:17:33', 'dd-mm-yyyy hh24:mi:ss'), '启动产品开关任务');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93002, '还原产品开关任务', 'productoperation:recover_show', 89001, to_date('11-09-2014 17:18:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:18:00', 'dd-mm-yyyy hh24:mi:ss'), '还原产品开关任务');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93003, '删除产品开关任务', 'productoperation:delete_show', 89001, to_date('11-09-2014 17:18:28', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:18:28', 'dd-mm-yyyy hh24:mi:ss'), '删除产品开关任务');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93004, '创建产品属性', 'productProperty:add_show', 89001, to_date('11-09-2014 17:20:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:20:15', 'dd-mm-yyyy hh24:mi:ss'), '创建产品属性');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93013, '订单管理', 'order:list', 0, to_date('12-09-2014 10:01:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 10:01:52', 'dd-mm-yyyy hh24:mi:ss'), '订单管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93014, '手工补单管理', 'supplyOrder:list', 0, to_date('12-09-2014 10:02:32', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 10:02:32', 'dd-mm-yyyy hh24:mi:ss'), '手工补单管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93015, '批量导入', 'supplyOrder:importlist_show', 93014, to_date('12-09-2014 10:03:09', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 10:03:09', 'dd-mm-yyyy hh24:mi:ss'), '批量导入');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93016, '批量充值', 'supplyOrder:rechargelist_show', 93014, to_date('12-09-2014 10:03:23', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 10:08:58', 'dd-mm-yyyy hh24:mi:ss'), '批量充值');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93017, '上传并导入', 'supplyOrder:upload_show', 93016, to_date('12-09-2014 10:03:47', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 10:03:47', 'dd-mm-yyyy hh24:mi:ss'), '上传并导入');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93018, '审核数据', 'supplyOrder:checkdata_show', 93014, to_date('12-09-2014 10:08:51', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 10:08:51', 'dd-mm-yyyy hh24:mi:ss'), '审核数据');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93019, '删除数据', 'supplyOrder:deletedata_show', 93014, to_date('12-09-2014 10:09:17', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 10:09:17', 'dd-mm-yyyy hh24:mi:ss'), '删除数据');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93020, '预成功强制失败', 'fakeOrder:closeorder_show', 93013, to_date('12-09-2014 10:16:59', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 10:16:59', 'dd-mm-yyyy hh24:mi:ss'), '预成功强制失败');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93021, '预成功强制成功', 'fakeOrder:successorder_show', 93013, to_date('12-09-2014 10:17:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 10:17:21', 'dd-mm-yyyy hh24:mi:ss'), '预成功强制成功');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93005, '报表管理', 'reportConfig:list', 0, to_date('11-09-2014 17:33:25', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:33:25', 'dd-mm-yyyy hh24:mi:ss'), '报表管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93006, '新增报表配置', 'reportConfig:add_show', 93005, to_date('11-09-2014 17:33:50', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:33:50', 'dd-mm-yyyy hh24:mi:ss'), '新增报表配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93007, '修改报表配置', 'reportConfig:edit_show', 93005, to_date('11-09-2014 17:34:32', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:34:32', 'dd-mm-yyyy hh24:mi:ss'), '修改报表配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93008, '导出交易量报表', 'transactionReport:export_show', 93005, to_date('11-09-2014 17:36:36', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:36:36', 'dd-mm-yyyy hh24:mi:ss'), '导出交易量报表');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93009, '导出账户报表', 'accountReport:export_show', 93005, to_date('11-09-2014 17:40:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:40:21', 'dd-mm-yyyy hh24:mi:ss'), '导出账户报表');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93010, '导出利润报表', 'profitReport:export_show', 93005, to_date('11-09-2014 17:42:06', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:42:06', 'dd-mm-yyyy hh24:mi:ss'), '导出利润报表');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93011, '添加报表属性', 'reportType:add_show', 93005, to_date('11-09-2014 17:44:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:44:16', 'dd-mm-yyyy hh24:mi:ss'), '添加报表属性');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93012, '删除报表属性', 'reportType:delete', 93005, to_date('11-09-2014 17:44:31', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 17:44:31', 'dd-mm-yyyy hh24:mi:ss'), '删除报表属性');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92021, '创建产品类型', 'productType:add_show', 89001, to_date('11-09-2014 15:56:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 15:56:14', 'dd-mm-yyyy hh24:mi:ss'), '创建产品类型');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92023, '删除产品类型', 'productType:delete', 89001, to_date('11-09-2014 15:56:46', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 15:56:46', 'dd-mm-yyyy hh24:mi:ss'), '删除产品类型');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92025, '供货商产品管理', 'supplyProduct:list', 0, to_date('11-09-2014 15:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 15:57:52', 'dd-mm-yyyy hh24:mi:ss'), '供货商产品管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92027, '创建商户产品', 'merchantProduct:add_show', 92026, to_date('11-09-2014 16:06:44', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:06:44', 'dd-mm-yyyy hh24:mi:ss'), '创建商户产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92028, '设置商户默认产品', 'merchantProduct:adddefault_show', 92026, to_date('11-09-2014 16:07:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:07:15', 'dd-mm-yyyy hh24:mi:ss'), '设置商户默认产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92029, '编辑商户产品', 'merchantProduct:edit_show', 92026, to_date('11-09-2014 16:07:34', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:07:34', 'dd-mm-yyyy hh24:mi:ss'), '编辑商户产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92032, '添加代理商产品', 'agentProduct:add_show', 92024, to_date('11-09-2014 16:11:08', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:11:08', 'dd-mm-yyyy hh24:mi:ss'), '添加代理商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92035, '编辑代理商产品', 'agentProduct:edit_show', 92024, to_date('11-09-2014 16:12:11', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:12:11', 'dd-mm-yyyy hh24:mi:ss'), '编辑代理商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92036, '修改代理商产品状态', 'agentProduct:changestatus_show', 92024, to_date('11-09-2014 16:12:40', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:12:40', 'dd-mm-yyyy hh24:mi:ss'), '修改代理商产品状态');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92037, '删除代理商产品', 'agentProduct:delete', 92024, to_date('11-09-2014 16:13:01', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:13:01', 'dd-mm-yyyy hh24:mi:ss'), '删除代理商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92040, '批量修改供货商产品', 'supplyProduct:editlist_show', 92025, to_date('11-09-2014 16:16:11', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:16:11', 'dd-mm-yyyy hh24:mi:ss'), '批量修改供货商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92041, '编辑供货商产品', 'supplyProduct:edit_show', 92025, to_date('11-09-2014 16:16:31', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:16:31', 'dd-mm-yyyy hh24:mi:ss'), '编辑供货商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92042, '修改供货商产品状态', 'supplyProduct:changestatus_show', 92025, to_date('11-09-2014 16:16:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:16:52', 'dd-mm-yyyy hh24:mi:ss'), '修改供货商产品状态');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92043, '删除供货商产品', 'supplyProduct:delete', 92025, to_date('11-09-2014 16:17:12', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:17:12', 'dd-mm-yyyy hh24:mi:ss'), '删除供货商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (78001, '查看代理商账户详情', 'agentAccount:view', 78000, to_date('10-09-2014 11:39:39', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 11:39:51', 'dd-mm-yyyy hh24:mi:ss'), '查看代理商账户详情');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92030, '修改商户产品状态', 'merchantProduct:changestatus_show', 92026, to_date('11-09-2014 16:07:57', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:07:57', 'dd-mm-yyyy hh24:mi:ss'), '修改商户产品状态');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92044, '批量修改代理商产品折扣', 'agentProduct:editlist_execute', 92024, to_date('11-09-2014 16:36:02', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:36:02', 'dd-mm-yyyy hh24:mi:ss'), '批量修改代理商产品折扣');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66024, '添加组织机构', 'organization:add_show', 80000, to_date('05-09-2014 17:58:18', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:58:18', 'dd-mm-yyyy hh24:mi:ss'), '添加组织机构');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92022, '修改产品类型', 'productType:edit_show', 89001, to_date('11-09-2014 15:56:31', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 15:56:31', 'dd-mm-yyyy hh24:mi:ss'), '修改产品类型');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92024, '代理商产品管理', 'agentProduct:list', 0, to_date('11-09-2014 15:57:23', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 15:57:23', 'dd-mm-yyyy hh24:mi:ss'), '代理商产品管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66000, '查看用户详情', 'customer:view', 65000, to_date('05-09-2014 15:58:30', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 15:58:30', 'dd-mm-yyyy hh24:mi:ss'), '查看用户详情');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66013, '修改商户操作员密码', 'moperator:changepwd_view', 66009, to_date('05-09-2014 16:55:36', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:55:36', 'dd-mm-yyyy hh24:mi:ss'), '修改商户操作员密码');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66014, '商户操作员分配角色', 'moperator:addrolse_view', 66009, to_date('05-09-2014 16:56:04', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:56:04', 'dd-mm-yyyy hh24:mi:ss'), '商户操作员分配角色');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66015, '修改系统操作员状态', 'soperator:changestatus_view', 87000, to_date('05-09-2014 16:59:23', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:59:23', 'dd-mm-yyyy hh24:mi:ss'), '修改系统操作员状态');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66016, '代理商管理', 'amerchant:list', 0, to_date('05-09-2014 17:10:07', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 11:24:39', 'dd-mm-yyyy hh24:mi:ss'), '代理商管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66017, '添加代理商', 'amerchant:add_show', 66016, to_date('05-09-2014 17:10:37', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:10:37', 'dd-mm-yyyy hh24:mi:ss'), '添加代理商');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66018, '代理商账户管理', 'amerchant:accountmanage_show', 66016, to_date('05-09-2014 17:13:26', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:13:26', 'dd-mm-yyyy hh24:mi:ss'), '代理商账户管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66019, '代理商产品管理', 'amerchant:productmanage_show', 66016, to_date('05-09-2014 17:13:46', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:13:46', 'dd-mm-yyyy hh24:mi:ss'), '代理商产品管理');
commit;
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66020, '修改供货商状态', 'smerchant:changestatus_show', 79000, to_date('05-09-2014 17:20:59', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:20:59', 'dd-mm-yyyy hh24:mi:ss'), '修改供货商状态');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66021, '供货商产品管理', 'smerchant:productmanage_show', 79000, to_date('05-09-2014 17:22:02', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:22:02', 'dd-mm-yyyy hh24:mi:ss'), '供货商产品管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92026, '商户产品管理', 'merchantProduct:list', 0, to_date('11-09-2014 16:06:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:06:21', 'dd-mm-yyyy hh24:mi:ss'), '商户产品管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92031, '删除商户产品', 'merchantProduct:delete', 92026, to_date('11-09-2014 16:08:18', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:08:18', 'dd-mm-yyyy hh24:mi:ss'), '删除商户产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92034, '批量修改代理商产品', 'agentProduct:editlist_show', 92024, to_date('11-09-2014 16:11:47', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:11:47', 'dd-mm-yyyy hh24:mi:ss'), '批量修改代理商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92045, '批量开启代理商产品', 'agentProduct:openlist_execute', 92024, to_date('11-09-2014 16:36:36', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:36:36', 'dd-mm-yyyy hh24:mi:ss'), '批量开启代理商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (78002, '代理商信用加款', 'agentAccount:debitCreditable_show', 78000, to_date('10-09-2014 11:41:59', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 11:41:59', 'dd-mm-yyyy hh24:mi:ss'), '代理商信用加款');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (78000, '代理商账户管理', 'aaccount:list', 0, to_date('10-09-2014 10:28:28', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 11:38:59', 'dd-mm-yyyy hh24:mi:ss'), '代理商账户管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (107000, '确认返佣', 'rebateHistory:actrulRebate_show', 72001, to_date('07-10-2014 16:04:23', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('07-10-2014 16:04:23', 'dd-mm-yyyy hh24:mi:ss'), '确认返佣');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (107001, '余额收回', 'rebateHistory:getBalance_show', 72001, to_date('07-10-2014 16:04:47', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('07-10-2014 16:04:47', 'dd-mm-yyyy hh24:mi:ss'), '余额收回');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (107002, '佣金收回', 'rebateHistory:getRebateBalance_show', 72001, to_date('07-10-2014 16:05:04', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('07-10-2014 16:05:04', 'dd-mm-yyyy hh24:mi:ss'), '佣金收回');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66003, '角色管理', 'role:list', 0, to_date('05-09-2014 16:12:28', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:12:28', 'dd-mm-yyyy hh24:mi:ss'), '角色管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66004, '添加角色', 'role:add_show', 66003, to_date('05-09-2014 16:13:43', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:13:43', 'dd-mm-yyyy hh24:mi:ss'), '添加角色');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66005, '分配权限', 'role:addprivilege_show', 66003, to_date('05-09-2014 16:15:56', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:15:56', 'dd-mm-yyyy hh24:mi:ss'), '分配权限');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66006, '编辑权限', 'privilege:edit_show', 68001, to_date('05-09-2014 16:18:55', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:19:27', 'dd-mm-yyyy hh24:mi:ss'), '编辑权限');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66007, '菜单管理', 'menu:list', 0, to_date('05-09-2014 16:21:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:21:21', 'dd-mm-yyyy hh24:mi:ss'), '菜单管理和资源管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66008, '添加页面资源', 'pageresource:add_view', 66007, to_date('05-09-2014 16:21:42', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:21:42', 'dd-mm-yyyy hh24:mi:ss'), '添加页面资源');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66009, '商户操作员管理', 'moperator:list', 0, to_date('05-09-2014 16:51:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:13:30', 'dd-mm-yyyy hh24:mi:ss'), '商户操作员管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66010, '添加系统操作员', 'soperator:add_view', 87000, to_date('05-09-2014 16:52:24', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:52:24', 'dd-mm-yyyy hh24:mi:ss'), '添加系统操作员');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66011, '编辑系统操作员', 'soperator:edit_view', 87000, to_date('05-09-2014 16:52:57', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:52:57', 'dd-mm-yyyy hh24:mi:ss'), '编辑系统操作员');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66012, '系统操作员分配角色', 'soperator:addrolse_view', 87000, to_date('05-09-2014 16:54:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:54:14', 'dd-mm-yyyy hh24:mi:ss'), '系统操作员分配角色');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66022, '添加号段', 'numsection:add_show', 70002, to_date('05-09-2014 17:52:17', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:52:17', 'dd-mm-yyyy hh24:mi:ss'), '添加号段');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66023, '编辑号段', 'numsection:eidt_show', 70002, to_date('05-09-2014 17:52:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:52:33', 'dd-mm-yyyy hh24:mi:ss'), '编辑号段');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66025, '编辑预成功配置', 'fakerule:edit_show', 66016, to_date('05-09-2014 18:08:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 18:09:03', 'dd-mm-yyyy hh24:mi:ss'), '编辑代理商预成功配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66026, '添加返佣配置', 'rebate:add_show', 72001, to_date('05-09-2014 18:15:40', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 18:15:40', 'dd-mm-yyyy hh24:mi:ss'), '添加返佣配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (66027, '编辑返佣配置', 'rebate:edit_show', 72001, to_date('05-09-2014 18:16:20', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 18:16:27', 'dd-mm-yyyy hh24:mi:ss'), '编辑返佣配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (105004, '利润归集详情', 'ProfitImputation:view', 92005, to_date('20-09-2014 19:13:28', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('20-09-2014 19:13:28', 'dd-mm-yyyy hh24:mi:ss'), null);
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (105000, '删除密钥', 'security:delete', 89006, to_date('20-09-2014 19:05:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('20-09-2014 19:05:31', 'dd-mm-yyyy hh24:mi:ss'), null);
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (105001, '添加密钥', 'security:add_show', 89006, to_date('20-09-2014 19:06:54', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('20-09-2014 19:06:54', 'dd-mm-yyyy hh24:mi:ss'), null);
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (105002, '修改密钥状态', 'security:changestatus', 89006, to_date('20-09-2014 19:07:47', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('20-09-2014 19:07:47', 'dd-mm-yyyy hh24:mi:ss'), null);
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (105003, '更新密钥', 'security:update_show', 89006, to_date('20-09-2014 19:08:45', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('20-09-2014 19:08:45', 'dd-mm-yyyy hh24:mi:ss'), null);
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (70004, '查看组织机构', 'organization:view', 80000, to_date('05-09-2014 17:58:41', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:58:41', 'dd-mm-yyyy hh24:mi:ss'), '查看组织机构');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (70005, '编辑组织机构', 'organization:edit_show', 80000, to_date('05-09-2014 17:58:57', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:58:57', 'dd-mm-yyyy hh24:mi:ss'), '编辑组织机构');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (70001, '查看代理商密钥', 'amerchant:viewsecurity', 66016, to_date('05-09-2014 17:14:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:14:13', 'dd-mm-yyyy hh24:mi:ss'), '查看代理商密钥');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (70000, '编辑代理商', 'amerchant:edit_show', 66016, to_date('05-09-2014 17:12:47', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:12:47', 'dd-mm-yyyy hh24:mi:ss'), '编辑代理商');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (70002, '号段管理', 'numsection:list', 0, to_date('05-09-2014 17:50:56', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:50:56', 'dd-mm-yyyy hh24:mi:ss'), '号段管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (70003, '删除号段', 'numsection:delete_show', 70002, to_date('05-09-2014 17:52:49', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:52:49', 'dd-mm-yyyy hh24:mi:ss'), null);
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (70006, '添加预成功配置', 'fakerule:add_show', 66016, to_date('05-09-2014 18:07:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 18:08:56', 'dd-mm-yyyy hh24:mi:ss'), '添加代理商预成功配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93022, '人工重试', 'order:artificialretry_show', 93013, to_date('12-09-2014 10:40:36', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 10:40:36', 'dd-mm-yyyy hh24:mi:ss'), '人工重试');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93023, '人工重绑', 'order:artificialretie_show', 93013, to_date('12-09-2014 10:41:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 10:41:00', 'dd-mm-yyyy hh24:mi:ss'), '人工重绑');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93024, '订单列表强制关闭', 'order:closeorder_show', 93013, to_date('12-09-2014 10:41:30', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 10:41:30', 'dd-mm-yyyy hh24:mi:ss'), '订单列表强制关闭');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93025, '订单列表强制成功', 'order:successorder_show', 93013, to_date('12-09-2014 10:41:50', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 10:41:50', 'dd-mm-yyyy hh24:mi:ss'), '订单列表强制成功');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93026, '手工补单', 'supplyOrder:supply_execute', 93014, to_date('12-09-2014 11:06:20', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 11:06:20', 'dd-mm-yyyy hh24:mi:ss'), '手工补单');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93027, '超时订单强制成功', 'orderTimeOut:successOrder_show', 93013, to_date('12-09-2014 11:08:47', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 11:08:47', 'dd-mm-yyyy hh24:mi:ss'), '超时订单强制成功');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93028, '超时订单强制失败', 'orderTimeOut:closeOrder_show', 93013, to_date('12-09-2014 11:09:03', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 11:09:03', 'dd-mm-yyyy hh24:mi:ss'), '超时订单强制失败');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93029, '交易配置', 'transaction:list', 0, to_date('12-09-2014 11:10:32', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 11:10:32', 'dd-mm-yyyy hh24:mi:ss'), '交易配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93030, '添加参数配置', 'paramConf:add_show', 93029, to_date('12-09-2014 11:12:32', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 11:12:32', 'dd-mm-yyyy hh24:mi:ss'), '添加参数配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93031, '编辑参数配置', 'paramConf:edit_show', 93029, to_date('12-09-2014 11:12:59', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 11:12:59', 'dd-mm-yyyy hh24:mi:ss'), '编辑参数配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (93032, '编辑质量权重', 'qualityWeightRule:edit_show', 93029, to_date('12-09-2014 11:17:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('12-09-2014 11:17:53', 'dd-mm-yyyy hh24:mi:ss'), '编辑质量权重');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (71000, '代理商返佣设置', 'amerchant:rebate_show', 66016, to_date('05-09-2014 17:14:31', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:14:31', 'dd-mm-yyyy hh24:mi:ss'), '代理商返佣设置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (71001, '添加供货商', 'smerchant:add_show', 79000, to_date('05-09-2014 17:20:36', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:20:36', 'dd-mm-yyyy hh24:mi:ss'), '添加供货商');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (71002, '编辑供货商', 'smerchant:edit_show', 79000, to_date('05-09-2014 17:21:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:21:16', 'dd-mm-yyyy hh24:mi:ss'), '编辑供货商');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (71003, '供货商账户管理', 'smerchant:accountmanage_show', 79000, to_date('05-09-2014 17:21:43', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:21:43', 'dd-mm-yyyy hh24:mi:ss'), '供货商账户管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (86000, '供货商减款', 'supplyAccount:creditCurrency_show', 83001, to_date('10-09-2014 14:08:57', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 14:08:57', 'dd-mm-yyyy hh24:mi:ss'), '供货商减款');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (86001, '供货商授信减款', 'supplyAccount:creditCreditable_show', 83001, to_date('10-09-2014 14:09:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 14:09:42', 'dd-mm-yyyy hh24:mi:ss'), '供货商授信减款');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89016, '创建产品', 'product:add_show', 89001, to_date('10-09-2014 17:47:30', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 17:47:30', 'dd-mm-yyyy hh24:mi:ss'), '创建产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89017, '查看产品树形结构', 'product:tree_show', 89001, to_date('10-09-2014 17:47:50', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 17:47:50', 'dd-mm-yyyy hh24:mi:ss'), '查看产品树形结构');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89018, '修改产品状态', 'product:updateStatus', 89001, to_date('10-09-2014 17:48:07', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 17:48:07', 'dd-mm-yyyy hh24:mi:ss'), '修改产品状态');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89019, '删除产品', 'product:delete', 89001, to_date('10-09-2014 17:48:28', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 17:48:28', 'dd-mm-yyyy hh24:mi:ss'), '删除产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89020, '修改产品信息', 'product:edit_show', 89001, to_date('10-09-2014 17:48:43', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 17:48:43', 'dd-mm-yyyy hh24:mi:ss'), '修改产品信息');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89021, '查看权限树形结构', 'privilege:tree_show', 68001, to_date('10-09-2014 17:49:29', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 17:49:29', 'dd-mm-yyyy hh24:mi:ss'), '查看权限树形结构');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92033, '批量创建代理商产品', 'agentProduct:addlist_show', 92024, to_date('11-09-2014 16:11:31', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:11:31', 'dd-mm-yyyy hh24:mi:ss'), '批量创建代理商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92001, '加款审核', 'agentAccount:addAccountCheck_show', 78000, to_date('11-09-2014 10:37:42', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 10:37:42', 'dd-mm-yyyy hh24:mi:ss'), '加款审核');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92038, '添加供货商产品', 'supplyProduct:add_show', 92025, to_date('11-09-2014 16:15:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:15:33', 'dd-mm-yyyy hh24:mi:ss'), '添加供货商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92000, '加款申请', 'agentAccount:addAccount_show', 78000, to_date('11-09-2014 10:33:40', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 10:33:40', 'dd-mm-yyyy hh24:mi:ss'), '代理商加款申请');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92039, '批量创建供货商产品', 'supplyProduct:addlist_show', 92025, to_date('11-09-2014 16:15:50', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 16:15:50', 'dd-mm-yyyy hh24:mi:ss'), '批量创建供货商产品');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (65009, '修改商户操作员状态', 'moperator:changestatus_view', 66009, to_date('05-09-2014 16:58:48', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 16:58:48', 'dd-mm-yyyy hh24:mi:ss'), '修改商户操作员状态');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89000, '添加指定排除', 'assignExclude:add_show', 88001, to_date('10-09-2014 16:22:31', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:22:31', 'dd-mm-yyyy hh24:mi:ss'), '添加指定排除');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89002, '保存产品等级', 'merchantLevel:add_show', 89001, to_date('10-09-2014 16:31:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:31:14', 'dd-mm-yyyy hh24:mi:ss'), '保存产品等级');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89003, '编辑接口常量', 'interfaceConstant:edit_show', 88005, to_date('10-09-2014 16:33:36', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:33:54', 'dd-mm-yyyy hh24:mi:ss'), '编辑接口常量配置');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89006, '密钥管理', 'security:list', 0, to_date('10-09-2014 16:53:25', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:53:25', 'dd-mm-yyyy hh24:mi:ss'), '密钥管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89007, '添加密钥规则', 'securityrule:add_show', 89006, to_date('10-09-2014 16:53:54', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:53:54', 'dd-mm-yyyy hh24:mi:ss'), '添加密钥规则');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89008, '编辑密钥规则', 'securityrule:edit_show', 89006, to_date('10-09-2014 16:54:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:54:16', 'dd-mm-yyyy hh24:mi:ss'), '编辑密钥规则');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89009, '删除密钥规则', 'securityrule:delete', 89006, to_date('10-09-2014 16:54:32', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:54:32', 'dd-mm-yyyy hh24:mi:ss'), '删除密钥规则');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89010, '修改密钥规则状态', 'securityrule:changestatus', 89006, to_date('10-09-2014 16:56:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:56:00', 'dd-mm-yyyy hh24:mi:ss'), '修改密钥规则状态');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89011, '添加密钥类型', 'securitytype:add_show', 89006, to_date('10-09-2014 16:56:23', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:56:23', 'dd-mm-yyyy hh24:mi:ss'), '添加密钥类型');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89012, '修改密钥类型状态', 'securitytype:changestatus', 89006, to_date('10-09-2014 16:56:47', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:56:47', 'dd-mm-yyyy hh24:mi:ss'), '修改密钥类型状态');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89013, '编辑密钥类型', 'securitytype:edit_show', 89006, to_date('10-09-2014 16:57:07', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:57:07', 'dd-mm-yyyy hh24:mi:ss'), '编辑密钥类型');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89014, '删除密钥类型', 'securitytype:delete', 89006, to_date('10-09-2014 16:57:24', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 16:57:24', 'dd-mm-yyyy hh24:mi:ss'), '删除密钥类型');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (89015, '执行返佣', 'rebate:rebate_show', 72001, to_date('10-09-2014 17:06:38', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 17:06:38', 'dd-mm-yyyy hh24:mi:ss'), '执行返佣');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92002, '取消加款审核', 'agentAccount:addAccountCancel_show', 78000, to_date('11-09-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 10:40:46', 'dd-mm-yyyy hh24:mi:ss'), '取消加款审核');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92003, '删除加款申请', 'agentAccount:addAccountDel_show', 78000, to_date('11-09-2014 10:41:09', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 10:41:09', 'dd-mm-yyyy hh24:mi:ss'), '删除加款申请');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92004, '恢复加款申请', 'agentAccount:addAccountRecover_show', 78000, to_date('11-09-2014 10:41:28', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 10:41:28', 'dd-mm-yyyy hh24:mi:ss'), '恢复加款申请');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92005, '系统账户管理', 'sysAccount:list', 0, to_date('11-09-2014 10:53:59', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 10:54:34', 'dd-mm-yyyy hh24:mi:ss'), '系统账户管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92006, '查看系统账户详情', 'sysAccount:view', 92005, to_date('11-09-2014 10:54:27', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 10:54:27', 'dd-mm-yyyy hh24:mi:ss'), '查看系统账户类型');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92007, '商户账户管理', 'merchantAccount:list', 0, to_date('11-09-2014 10:57:57', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 11:03:12', 'dd-mm-yyyy hh24:mi:ss'), '商户账户管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92008, '查看商户账户详情', 'merchantAccount:view', 92007, to_date('11-09-2014 11:04:20', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 11:04:20', 'dd-mm-yyyy hh24:mi:ss'), '查看商户列表账户管理的账户详情');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92009, '商户账户锁定', 'merchantAccount:block_show', 92007, to_date('11-09-2014 11:07:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 11:07:13', 'dd-mm-yyyy hh24:mi:ss'), '商户列表账户管理中的锁定');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92010, '商户账户加款', 'merchantAccount:debitCurrency_show', 92007, to_date('11-09-2014 11:07:32', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 11:07:32', 'dd-mm-yyyy hh24:mi:ss'), '商户账户加款');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92011, '商户账户减款', 'merchantAccount:creditCurrency_show', 92007, to_date('11-09-2014 11:07:49', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 11:07:49', 'dd-mm-yyyy hh24:mi:ss'), '商户账户减款');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92012, '商户账户信用加款', 'merchantAccount:debitCreditable_show', 92007, to_date('11-09-2014 11:08:11', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 11:08:11', 'dd-mm-yyyy hh24:mi:ss'), '商户账户信用加款');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (92013, '商户账户信用减款', 'merchantAccount:creditCreditable_show', 92007, to_date('11-09-2014 11:08:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-09-2014 11:08:53', 'dd-mm-yyyy hh24:mi:ss'), '商户账户信用减款');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (95000, '证书上传', 'certfile:upload_show', 94000, to_date('16-09-2014 15:34:30', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('16-09-2014 15:34:30', 'dd-mm-yyyy hh24:mi:ss'), '证书上传');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (87000, '系统操作员管理', 'soperator:list', 0, to_date('10-09-2014 15:31:44', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 15:31:44', 'dd-mm-yyyy hh24:mi:ss'), '系统操作员管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (94000, '证书管理', 'certfile:list', 0, to_date('16-09-2014 11:20:50', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('16-09-2014 11:20:50', 'dd-mm-yyyy hh24:mi:ss'), '证书列表、证书添加、证书删除');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (94001, '添加证书', 'certfile:add_show', 94000, to_date('16-09-2014 11:21:57', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('16-09-2014 11:21:57', 'dd-mm-yyyy hh24:mi:ss'), '添加证书');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (94002, '删除证书', 'certfile:delete_show', 94000, to_date('16-09-2014 11:23:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('16-09-2014 11:23:00', 'dd-mm-yyyy hh24:mi:ss'), '删除证书');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (69001, '修改代理商状态', 'amerchant:changestatus_show', 66016, to_date('05-09-2014 17:11:18', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:11:18', 'dd-mm-yyyy hh24:mi:ss'), '修改代理商状态');
commit;
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (69002, '查看代理商详情', 'amerchant:view', 66016, to_date('05-09-2014 17:12:02', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('05-09-2014 17:12:02', 'dd-mm-yyyy hh24:mi:ss'), '查看代理商详情');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (80000, '组织机构管理', 'organization:list', 0, to_date('10-09-2014 11:26:56', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 11:26:56', 'dd-mm-yyyy hh24:mi:ss'), '组织机构管理');
insert into PRIVILEGE (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (84000, '查看供货商账户详情', 'supplyAccount:view', 83001, to_date('10-09-2014 14:06:58', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-09-2014 14:06:58', 'dd-mm-yyyy hh24:mi:ss'), '查看供货商账户详情');
commit;
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14200, 1250, 3416, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14201, 1250, 3417, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14202, 1250, 3418, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14203, 1250, 3419, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14204, 1250, 3420, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14205, 1250, 4400, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14206, 1250, 3500, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14207, 1250, 4550, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14208, 1250, 6251, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14209, 1250, 150, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14210, 1250, 300, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14211, 1250, 502, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14212, 1250, 2400, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14213, 1250, 3511, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14214, 1250, 4451, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14215, 1250, 4600, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14216, 1250, 9250, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14217, 1250, 200, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14218, 1250, 400, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14219, 1250, 3451, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14220, 1250, 3452, '0', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:21', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14221, 1250, 4250, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14222, 1250, 3453, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14223, 1250, 3650, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14224, 1250, 4850, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14225, 1250, 7300, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14226, 1250, 201, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14227, 1250, 501, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14228, 1250, 500, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14229, 1250, 2300, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14230, 1250, 3413, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14231, 1250, 3415, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14232, 1250, 3501, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14233, 1250, 5351, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14234, 1250, 2351, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14235, 1250, 2353, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14236, 1250, 3700, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14237, 1250, 3250, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14238, 1250, 3405, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14239, 1250, 4800, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14240, 1250, 5250, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14241, 1250, 8600, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14242, 1250, 5303, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14243, 1250, 751, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14244, 1250, 4900, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14245, 1250, 758, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14246, 1250, 3408, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14247, 1250, 7400, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14248, 1250, 3409, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14249, 1250, 5350, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14250, 1250, 5400, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14251, 1250, 8550, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14252, 1250, 8250, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14253, 1250, 8300, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14254, 1250, 4706, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14255, 1250, 4750, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14256, 1250, 4707, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14257, 1250, 4713, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14258, 1250, 9300, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14259, 1250, 4716, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (14260, 1250, 9301, '0', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('31-10-2014 14:24:22', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
commit;
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15401, 2250, 11300, '0', to_date('05-11-2014 09:51:01', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-11-2014 09:51:01', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into ROLE_MENU (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15402, 2250, 11250, '0', to_date('05-11-2014 09:51:01', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-11-2014 09:51:01', 'dd-mm-yyyy hh24:mi:ss'), null, null, null);
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (16200, 300, 3416, '0', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (16201, 300, 3417, '0', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (16202, 300, 200, '0', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (16203, 300, 7251, '0', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (16204, 300, 201, '0', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (16205, 300, 5252, '0', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (16206, 300, 751, '0', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('22-11-2014 15:05:00', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15403, 2250, 3416, '0', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15404, 2250, 3417, '0', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15405, 2250, 11250, '0', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15406, 2250, 4700, '0', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15407, 2250, 200, '0', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15408, 2250, 11300, '0', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15409, 2250, 5253, '0', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15410, 2250, 7251, '0', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15411, 2250, 201, '0', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15412, 2250, 5252, '0', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15413, 2250, 751, '0', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15414, 2250, 5400, '0', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('14-11-2014 10:20:33', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');
commit;
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (82046, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 72001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (82047, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 72002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (82048, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 72003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83000, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66026);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83001, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66027);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83002, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89015);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83003, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83004, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83005, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83006, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83007, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88005);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83008, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88006);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83010, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88008);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83011, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 90000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83012, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 90001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83013, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83016, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 68001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83017, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 67001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83018, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 68002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83019, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66006);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83020, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 79000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83021, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66020);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83022, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66021);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83023, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 71001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83024, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 71002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83025, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 71003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83026, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83027, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88004);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83028, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83029, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83030, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83031, 3050, '0', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:52', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83032, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83033, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65004);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83034, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83035, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 83001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83036, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 85000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83037, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 85001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83038, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 85002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83039, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 86000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83040, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 86001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83041, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 84000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83042, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66016);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83043, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 72000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83044, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66017);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83045, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66018);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83046, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66019);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83047, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66025);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83048, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 70001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (83049, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 70000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84000, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 70006);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84001, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 71000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84002, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 69001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84003, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 69002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84004, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 78000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84005, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 82000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84006, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 82001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84007, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 83000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84008, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 81000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84009, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 78001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84010, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 78002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84011, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84012, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 67000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84013, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 68000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84014, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65005);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84015, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66004);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84016, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66005);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84017, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66007);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84018, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 68003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84019, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 68004);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84020, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65006);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84021, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66008);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84022, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66009);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84023, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65007);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84024, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65008);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84025, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66013);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84026, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66014);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84027, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65009);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84028, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 70002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84029, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66022);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84030, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66023);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84031, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 70003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84032, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89006);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84033, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89007);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84034, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89008);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84035, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89009);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84036, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89010);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84037, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89011);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84038, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89012);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84039, 3050, '0', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:53', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89013);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84040, 3050, '0', to_date('15-09-2014 10:57:54', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:54', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89014);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84041, 3050, '0', to_date('15-09-2014 10:57:54', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:54', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 87000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84042, 3050, '0', to_date('15-09-2014 10:57:54', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:54', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 69000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84043, 3050, '0', to_date('15-09-2014 10:57:54', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:54', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66015);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84044, 3050, '0', to_date('15-09-2014 10:57:54', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:54', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66010);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (84045, 3050, '0', to_date('15-09-2014 10:57:54', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('15-09-2014 10:57:54', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66011);
commit;
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (160046, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 72002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (160047, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 106001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (160049, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 107000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162000, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 107001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162001, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66026);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162002, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66027);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162003, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89015);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162004, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162005, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162006, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88006);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162008, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88008);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162010, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 68001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162011, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 68002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162012, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66006);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162013, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89021);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162014, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66021);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162015, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 71001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162016, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162017, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162018, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162019, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162020, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92021);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162021, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92023);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162022, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89017);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162023, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89020);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162024, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162025, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162026, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162027, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162028, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93020);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162029, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93022);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162030, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93025);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162031, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93014);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162032, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93015);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162033, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93016);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162034, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93026);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162035, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93005);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162036, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93010);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162037, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93011);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162038, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93012);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162039, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92025);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162040, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92048);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162041, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92049);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162042, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92042);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162043, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92038);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162044, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92024);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162045, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92046);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162046, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92032);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162047, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92037);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162048, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92044);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (162049, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92034);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164000, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92033);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164001, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66017);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164002, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66019);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164003, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66025);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164004, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 71000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164005, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 69001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164006, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92028);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164007, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92030);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164008, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 78000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164009, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 82001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164010, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 83000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164011, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 81000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164012, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 78002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164013, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164014, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92004);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164015, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164016, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66004);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164017, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66005);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164018, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 68004);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164019, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65006);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164020, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66009);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164021, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65007);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164022, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65008);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164023, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66023);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164024, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93031);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164025, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 105001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164026, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 105002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164027, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89007);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164028, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89008);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164029, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89009);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164030, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89014);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164031, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92006);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164032, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92007);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164033, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92008);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164034, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92009);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164035, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92010);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164036, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92011);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164037, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 87000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164038, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 69000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164039, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66015);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164040, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66010);
commit;
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164041, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66011);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164042, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66012);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164043, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 95000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164044, 1250, '0', to_date('29-10-2014 16:49:17', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:17', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 94001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164045, 1250, '0', to_date('29-10-2014 16:49:17', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:17', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 94002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164046, 1250, '0', to_date('29-10-2014 16:49:17', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:17', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 80000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (164047, 1250, '0', to_date('29-10-2014 16:49:17', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:17', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 70004);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (10000, 10000, '0', to_date('05-09-2014 15:56:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('05-09-2014 15:56:00', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 72000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161008, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 72001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161009, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 72003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161010, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 106000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161011, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 106002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161012, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 107002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161013, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161014, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161015, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88005);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161016, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 90000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161017, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 90001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161018, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161020, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 67001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161021, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 79000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161022, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66020);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161023, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 71002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161024, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 71003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161025, 1250, '0', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:13', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 88004);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161026, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161027, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93004);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161028, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92022);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161029, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89016);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161030, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89018);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161031, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89019);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161032, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161033, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65004);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161034, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161035, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 83001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161036, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 85000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161037, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 85001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161038, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 85002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161039, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 86000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161040, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 86001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161041, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 84000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161042, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93013);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161043, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93021);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161044, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93023);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161045, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93024);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161046, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93027);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161047, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93028);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161048, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93017);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (161049, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93018);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163000, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93019);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163001, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93006);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163002, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93007);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163003, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93008);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163004, 1250, '0', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:14', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93009);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163005, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92047);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163006, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92040);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163007, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92041);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163008, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92043);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163009, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92039);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163010, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92035);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163011, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92036);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163012, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92045);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163013, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66016);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163014, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 72000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163015, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66018);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163016, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 70001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163017, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 70000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163018, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 70006);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163019, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 69002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163020, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92026);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163021, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92027);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163022, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92029);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163023, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92031);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163024, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 82000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163025, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 78001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163026, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92001);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163027, 1250, '0', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:15', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163028, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163029, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 67000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163030, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 68000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163031, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65005);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163032, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 127000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163033, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66007);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163034, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 68003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163035, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66008);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163036, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66013);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163037, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66014);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163038, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 65009);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163039, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 70002);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163040, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66022);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163041, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 70003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163042, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93029);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163043, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93030);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163044, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 93032);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163045, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89006);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163046, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 105000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163047, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 105003);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163048, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89010);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (163049, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89011);
commit;
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (165000, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89012);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (165001, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 89013);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (165002, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92005);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (165003, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 105004);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (165004, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92012);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (165005, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 92013);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (165006, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 94000);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (165007, 1250, '0', to_date('29-10-2014 16:49:17', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:17', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 66024);
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (165008, 1250, '0', to_date('29-10-2014 16:49:17', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:17', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 70005);
commit;
insert into SECURITY_CREDENTIAL (SECURITY_ID, STATUS, IDENTITY_ID, IDENTITY_TYPE, SECURITY_NAME, SECURITY_VALUE, SECURITY_TYPE_ID, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER, VALIDITY_DATE)
values (2152, '0', 2452, 'OPERATOR', '超级管理员_登录密钥', 'e102d37e56658aca37a37e8fd6563ddc', 50, to_date('24-09-2014 11:55:47', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('04-11-2014 10:02:22', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('30-12-9999 23:59:59', 'dd-mm-yyyy hh24:mi:ss'));
insert into SECURITY_CREDENTIAL (SECURITY_ID, STATUS, IDENTITY_ID, IDENTITY_TYPE, SECURITY_NAME, SECURITY_VALUE, SECURITY_TYPE_ID, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER, VALIDITY_DATE)
values (8101, '0', 2452, 'SP', '系统MD5Key1', '067231C1C73F02A8', 150, to_date('19-09-2014 10:21:44', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('19-09-2014 10:21:44', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('30-12-9999 23:59:59', 'dd-mm-yyyy hh24:mi:ss'));
insert into SECURITY_CREDENTIAL (SECURITY_ID, STATUS, IDENTITY_ID, IDENTITY_TYPE, SECURITY_NAME, SECURITY_VALUE, SECURITY_TYPE_ID, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER, VALIDITY_DATE)
values (8102, '0', 2452, 'SP', '系统MD5Key2', 'ED2241132BE08691', 150, to_date('19-09-2014 10:22:28', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('19-09-2014 10:22:28', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('30-12-9999 23:59:59', 'dd-mm-yyyy hh24:mi:ss'));
insert into SECURITY_CREDENTIAL (SECURITY_ID, STATUS, IDENTITY_ID, IDENTITY_TYPE, SECURITY_NAME, SECURITY_VALUE, SECURITY_TYPE_ID, CREATE_DATE, CREATE_USER, UPDATE_DATE, UPDATE_USER, VALIDITY_DATE)
values (8051, '0', 2452, 'SP', 'Key3Des', '986980F866D077F14A0F3810B79B6680F5D56DDB85E9C90AC08822376A98F872D2F4346E76CB7F0E38022FE28F58D2CA4658633C7A5273091A9D8936543200F8D9CFD7FF2A50092C27CA37DF1527F2446388D07A16D6784BE33C8D6EEA329ADEE1CDE1C63A72CB0E3C0EE0366B167A8445409640A7A08B0BE6854385AA786C19', 1051, to_date('18-09-2014 19:02:26', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('18-09-2014 19:02:26', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('30-12-9999 23:59:59', 'dd-mm-yyyy hh24:mi:ss'));
commit;
insert into SECURITY_CREDENTIAL_RULE (SECURITY_RULE_ID, SECURITY_RULE_NAME, LETTER, FIGURE, SPECIAL_CHARACTER, STATUS, IS_LOWERCASE, IS_UPPERCASE)
values (200, '密钥规则名称', '0', '1', '0', 0, '1', '0');
commit;
insert into SECURITY_CREDENTIAL_TYPE (SECURITY_TYPE_ID, SECURITY_TYPE_NAME, MODEL_TYPE, ENCRYPT_TYPE, MIN_LENGTH, MAX_LENGTH, VALIDITY, STATUS, IDENTITY_TYPE, SECURITY_RULE_ID)
values (2050, 'SUPPLYPUBLICKEY', 'RSA公钥', '3DES', 0, 0, 0, 0, 'SupplyMerchant', null);
insert into SECURITY_CREDENTIAL_TYPE (SECURITY_TYPE_ID, SECURITY_TYPE_NAME, MODEL_TYPE, ENCRYPT_TYPE, MIN_LENGTH, MAX_LENGTH, VALIDITY, STATUS, IDENTITY_TYPE, SECURITY_RULE_ID)
values (150, 'SPMD5KEY', 'MD5Key', '3DES', 0, 0, 0, 0, 'Operator,SP', null);
insert into SECURITY_CREDENTIAL_TYPE (SECURITY_TYPE_ID, SECURITY_TYPE_NAME, MODEL_TYPE, ENCRYPT_TYPE, MIN_LENGTH, MAX_LENGTH, VALIDITY, STATUS, IDENTITY_TYPE, SECURITY_RULE_ID)
values (1051, 'DESKEY', '3DES', 'RSA', 0, 0, 0, 0, 'SP', null);
insert into SECURITY_CREDENTIAL_TYPE (SECURITY_TYPE_ID, SECURITY_TYPE_NAME, MODEL_TYPE, ENCRYPT_TYPE, MIN_LENGTH, MAX_LENGTH, VALIDITY, STATUS, IDENTITY_TYPE, SECURITY_RULE_ID)
values (50, 'PASSWORD', 'Password', 'MD5', 1, 8, 0, 0, null, 200);
insert into SECURITY_CREDENTIAL_TYPE (SECURITY_TYPE_ID, SECURITY_TYPE_NAME, MODEL_TYPE, ENCRYPT_TYPE, MIN_LENGTH, MAX_LENGTH, VALIDITY, STATUS, IDENTITY_TYPE, SECURITY_RULE_ID)
values (51, 'AGENTMD5KEY', 'MD5Key', '3DES', 0, 0, 0, 0, 'AgentMerchant', null);
insert into SECURITY_CREDENTIAL_TYPE (SECURITY_TYPE_ID, SECURITY_TYPE_NAME, MODEL_TYPE, ENCRYPT_TYPE, MIN_LENGTH, MAX_LENGTH, VALIDITY, STATUS, IDENTITY_TYPE, SECURITY_RULE_ID)
values (2150, 'SUPPLYMD5KEY', 'MD5Key', '3DES', 0, 0, 0, 0, 'SUPPLYMERCHANT', null);
commit;
insert into SECURITY_STATUS_TRANSFER (ID, OLD_STATUS, NEW_STATUS, ACTION_NAME)
values (10000, '0', '1', '禁用');
insert into SECURITY_STATUS_TRANSFER (ID, OLD_STATUS, NEW_STATUS, ACTION_NAME)
values (10001, '1', '0', '启用');
insert into SECURITY_STATUS_TRANSFER (ID, OLD_STATUS, NEW_STATUS, ACTION_NAME)
values (10002, '1', '2', '删除');
commit;
insert into SP (IDENTITY_ID, SP_NAME, UPDATE_USER, UPDATE_DATE, STATUS, RMK, BUSINESS)
values (2333, '跃程系统', 'system', to_date('14-03-2014', 'dd-mm-yyyy'), '0', null, 'airtime');
commit;

insert into CCY_ACCOUNT (ACCOUNT_ID, STATUS, AVAILABLE_BALANCE, UNAVAILABLE_BANLANCE, CREDITABLE_BANLANCE, ACCOUNT_TYPE_ID, RMK, LAST_UPDATE_USER, LAST_UPDATE_DATE, CREATOR, CREATE_DATE, SIGN, VERSION)
values (203004, '1', '10000000', '0', '0', 204000, '注册商户自动生成', null, null, null, null, 'dd61e2981eaa4b0dc537625dc665a4da', 9);
insert into CCY_ACCOUNT (ACCOUNT_ID, STATUS, AVAILABLE_BALANCE, UNAVAILABLE_BANLANCE, CREDITABLE_BANLANCE, ACCOUNT_TYPE_ID, RMK, LAST_UPDATE_USER, LAST_UPDATE_DATE, CREATOR, CREATE_DATE, SIGN, VERSION)
values (203005, '1', '10000000', '0', '0', 204001, '注册商户自动生成', null, null, null, null, '279124f34f1a7e3680fa2f06d264747a', 2);
insert into ccy_account (ACCOUNT_ID, STATUS, AVAILABLE_BALANCE, UNAVAILABLE_BANLANCE, CREDITABLE_BANLANCE, ACCOUNT_TYPE_ID, RMK, LAST_UPDATE_USER, LAST_UPDATE_DATE, CREATOR, CREATE_DATE, SIGN, VERSION)
values (65000, '1', '0', '0', '0', 45000, '', '', null, '', null, '2d262e510f42c69ebe7d21111ee8d03f', 2);
insert into ccy_account (ACCOUNT_ID, STATUS, AVAILABLE_BALANCE, UNAVAILABLE_BANLANCE, CREDITABLE_BANLANCE, ACCOUNT_TYPE_ID, RMK, LAST_UPDATE_USER, LAST_UPDATE_DATE, CREATOR, CREATE_DATE, SIGN, VERSION)
values (66000, '1', '0', '0', '0', 45001, '', '', null, '', null, '5229de7d230f13e121a1815154ef552b', 2);
commit;
insert into identity_account_role (ID, IDENTITY_ID, IDENTITY_TYPE, ACCOUNT_ID, ACCOUNT_TYPE, RELATION, TABLE_NAME)
values (82006, 2333, 'SP', 203004, '204000', 'own', 'ccy_account');
insert into identity_account_role (ID, IDENTITY_ID, IDENTITY_TYPE, ACCOUNT_ID, ACCOUNT_TYPE, RELATION, TABLE_NAME)
values (82007, 2333, 'SP', 203005, '204001', 'own', 'ccy_account');
insert into identity_account_role (ID, IDENTITY_ID, IDENTITY_TYPE, ACCOUNT_ID, ACCOUNT_TYPE, RELATION, TABLE_NAME)
values (168000, 2333, 'SP', 65000, '45000', 'own', 'ccy_account');
insert into identity_account_role (ID, IDENTITY_ID, IDENTITY_TYPE, ACCOUNT_ID, ACCOUNT_TYPE, RELATION, TABLE_NAME)
values (169000, 2333, 'SP', 66000, '45001', 'own', 'ccy_account');
commit;

--add privilege 2014/12/11
insert into privilege (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (146000, '密码重置', 'security:init', 89006, to_date('11-12-2014 19:22:07', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('11-12-2014 19:22:07', 'dd-mm-yyyy hh24:mi:ss'), '密码重置');
insert into ROLE_PRIVILEGE (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (220012, 1250, '0', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-10-2014 16:49:16', 'dd-mm-yyyy hh24:mi:ss'), null, 'admin', 146000);
commit;
--add doConfirmProfit 2014/12/24
insert into page_resource (RESOURCE_ID, PAGE_RESOURCE_NAME, PAGE_URL, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_NAME)
values (13050, '确认利润', 'account/toConfirmProfit', '1', to_date('12-11-2014 14:29:33', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('12-11-2014 14:29:33', 'dd-mm-yyyy hh24:mi:ss'), '', 'admin');

insert into menu (MENU_ID, MENU_NAME, DISPLAY_ORDER, PAGE_RESOURCE_ID, PARENT_MENU_ID, STATUS, CREATE_TIME, UPDATE_NAME, UPDATE_TIME, REMARK, CREATE_USER, MENU_LEVEL, PORTALTYPE)
values (12250, '确认利润', 12, '13050', 200, '0', to_date('12-11-2014 14:30:15', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('12-11-2014 14:30:59', 'dd-mm-yyyy hh24:mi:ss'), '', '', 'One', 'MPORTAL');

insert into role_menu (ROLE_MENU_ID, ROLE_ID, MENU_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, ROLE_MENU_LEVEL)
values (15326, 1250, 12250, '0', to_date('13-11-2014 19:00:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('13-11-2014 19:00:13', 'dd-mm-yyyy hh24:mi:ss'), '', '', '');

commit;

--add privilege 2014/12/30
insert into privilege (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (267000, '订单详情交易链接', 'order:transactionHistory_view', 93013, to_date('30-12-2014 15:19:20', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('30-12-2014 15:19:20', 'dd-mm-yyyy hh24:mi:ss'), '订单详情交易链接');

insert into privilege (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (247000, '订单详情', 'order:orderdetail_view', 93013, to_date('29-12-2014 19:14:08', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('29-12-2014 19:14:08', 'dd-mm-yyyy hh24:mi:ss'), '查看订单详情');

insert into privilege (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (207006, '删除菜单', 'menu:del_menu', 66007, to_date('25-12-2014 15:22:24', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('25-12-2014 15:22:24', 'dd-mm-yyyy hh24:mi:ss'), '删除菜单信息');

insert into privilege (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (207005, '编辑菜单', 'menu:edit_menu', 66007, to_date('25-12-2014 15:22:01', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('25-12-2014 15:22:01', 'dd-mm-yyyy hh24:mi:ss'), '编辑菜单信息');

insert into privilege (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (207004, '添加同级', 'menu:add_menu', 66007, to_date('25-12-2014 15:20:42', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('25-12-2014 15:20:42', 'dd-mm-yyyy hh24:mi:ss'), '添加同级菜单');

insert into privilege (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (207003, '添加子级', 'menu:add_childmenu', 66007, to_date('25-12-2014 15:20:13', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('25-12-2014 15:20:13', 'dd-mm-yyyy hh24:mi:ss'), '添加子级菜单');

insert into privilege (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (207002, '查看日志', 'supplyAccount:view_logs', 83001, to_date('25-12-2014 13:36:35', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('25-12-2014 13:36:35', 'dd-mm-yyyy hh24:mi:ss'), '查看供货商账户管理');

insert into privilege (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (207001, '查看日志', 'sysAccount:view_logs', 92005, to_date('25-12-2014 13:35:52', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('25-12-2014 13:35:52', 'dd-mm-yyyy hh24:mi:ss'), '查看系统账户日志');

insert into privilege (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (207000, '查看日志', 'agentAccount:view_logs', 78000, to_date('25-12-2014 11:53:07', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('25-12-2014 11:53:07', 'dd-mm-yyyy hh24:mi:ss'), '查看商户账户日志');

commit;
insert into role_privilege (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (272032, 1250, '0', to_date('29-12-2014 20:31:46', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-12-2014 20:31:46', 'dd-mm-yyyy hh24:mi:ss'), '', 'admin', 207002);

insert into role_privilege (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (274028, 1250, '0', to_date('29-12-2014 20:31:48', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-12-2014 20:31:48', 'dd-mm-yyyy hh24:mi:ss'), '', 'admin', 207004);

insert into role_privilege (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (274029, 1250, '0', to_date('29-12-2014 20:31:48', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-12-2014 20:31:48', 'dd-mm-yyyy hh24:mi:ss'), '', 'admin', 207006);

insert into role_privilege (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (274048, 1250, '0', to_date('29-12-2014 20:31:48', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-12-2014 20:31:48', 'dd-mm-yyyy hh24:mi:ss'), '', 'admin', 207001);

insert into role_privilege (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (273031, 1250, '0', to_date('29-12-2014 20:31:47', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-12-2014 20:31:47', 'dd-mm-yyyy hh24:mi:ss'), '', 'admin', 247000);

insert into role_privilege (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (275017, 1250, '0', to_date('29-12-2014 20:31:47', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-12-2014 20:31:47', 'dd-mm-yyyy hh24:mi:ss'), '', 'admin', 207000);

insert into role_privilege (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (275023, 1250, '0', to_date('29-12-2014 20:31:48', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-12-2014 20:31:48', 'dd-mm-yyyy hh24:mi:ss'), '', 'admin', 207003);

insert into role_privilege (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (275024, 1250, '0', to_date('29-12-2014 20:31:48', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('29-12-2014 20:31:48', 'dd-mm-yyyy hh24:mi:ss'), '', 'admin', 207005);

insert into role_privilege (ROLE_PRIVILEGE_ID, ROLE_ID, STATUS, CREATE_TIME, UPDATE_USER, UPDATE_TIME, REMARK, CREATE_USER, PRIVILEGE_ID)
values (298014, 1250, '0', to_date('30-12-2014 15:19:43', 'dd-mm-yyyy hh24:mi:ss'), 'admin', to_date('30-12-2014 15:19:43', 'dd-mm-yyyy hh24:mi:ss'), '', 'admin', 267000);

commit;

--add privilege 15/01/10
insert into privilege (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (267002, '利润归集', 'profitImputation:imputation_show', 92005, to_date('10-01-2015 15:53:54', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-01-2015 15:54:22', 'dd-mm-yyyy hh24:mi:ss'), '');

insert into privilege (PRIVILEGE_ID, PRIVILEGE_NAME, PERMISSION_NAME, PARENT_PRIVILEGE_ID, CREATE_TIME, CREATE_USER, UPDATE_USER, UPDATE_TIME, REMARK)
values (267001, '利润重新清算', 'profitImputation:reImputation_show', 92005, to_date('10-01-2015 15:52:40', 'dd-mm-yyyy hh24:mi:ss'), 'admin', 'admin', to_date('10-01-2015 15:53:12', 'dd-mm-yyyy hh24:mi:ss'), '');


