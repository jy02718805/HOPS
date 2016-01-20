create table ACCOUNT_OPERATION_HISTORY
(
  ID          NUMBER not null,
  ACCOUNT_ID  NUMBER,
  TYPE_MODEL  VARCHAR2(10),
  OLD_STATUS  VARCHAR2(10),
  NEW_STATUS  VARCHAR2(10),
  DESC_STR    VARCHAR2(100),
  CREATE_DATE DATE,
  CREATOR     VARCHAR2(50)
);
comment on column ACCOUNT_OPERATION_HISTORY.ID
  is '主键';
comment on column ACCOUNT_OPERATION_HISTORY.ACCOUNT_ID
  is '账户ID';
comment on column ACCOUNT_OPERATION_HISTORY.TYPE_MODEL
  is '账户类型';
comment on column ACCOUNT_OPERATION_HISTORY.OLD_STATUS
  is '操作前状态';
comment on column ACCOUNT_OPERATION_HISTORY.NEW_STATUS
  is '操作后状态';
comment on column ACCOUNT_OPERATION_HISTORY.DESC_STR
  is '操作描述';
comment on column ACCOUNT_OPERATION_HISTORY.CREATE_DATE
  is '创建时间';
comment on column ACCOUNT_OPERATION_HISTORY.CREATOR
  is '操作人(名字)';
alter table ACCOUNT_OPERATION_HISTORY
  add primary key (ID);

create table ACCOUNT_OPERATION_LOG
(
  ACCOUNT_OPERATION_LOG VARCHAR2(32) not null,
  ACCOUNT_ID            VARCHAR2(32),
  ACTION                VARCHAR2(200),
  LAST_UPDATE_USER      VARCHAR2(20),
  LAST_UPDATE_DATE      DATE,
  RMK                   VARCHAR2(200)
);
comment on table ACCOUNT_OPERATION_LOG
  is '账户操作记录表';
comment on column ACCOUNT_OPERATION_LOG.ACCOUNT_OPERATION_LOG
  is '主键';
comment on column ACCOUNT_OPERATION_LOG.ACCOUNT_ID
  is '账户ID';
comment on column ACCOUNT_OPERATION_LOG.ACTION
  is '操作描述';
comment on column ACCOUNT_OPERATION_LOG.LAST_UPDATE_USER
  is '最后更新用户名';
comment on column ACCOUNT_OPERATION_LOG.LAST_UPDATE_DATE
  is '最后更新时间';
comment on column ACCOUNT_OPERATION_LOG.RMK
  is '备注';
alter table ACCOUNT_OPERATION_LOG
  add primary key (ACCOUNT_OPERATION_LOG);

create table ACCOUNT_SETTLEMENT
(
  ACCOUNT_ID            NUMBER,
  MERCHANT_ID           NUMBER,
  MERCHANT_NAME         VARCHAR2(32),
  BALANCE               NUMBER,
  CREATE_DATE           DATE,
  STATUS                VARCHAR2(32),
  SETTLEMENT_BEGIN_DATE DATE,
  ACCOUNT_SETTLEMENT_ID NUMBER not null,
  SETTLEMENT_END_DATE   DATE
);
comment on column ACCOUNT_SETTLEMENT.ACCOUNT_ID
  is '账户';
comment on column ACCOUNT_SETTLEMENT.MERCHANT_ID
  is '商户';
comment on column ACCOUNT_SETTLEMENT.MERCHANT_NAME
  is '商户名称';
comment on column ACCOUNT_SETTLEMENT.BALANCE
  is '金额';
comment on column ACCOUNT_SETTLEMENT.CREATE_DATE
  is '创建日期';
comment on column ACCOUNT_SETTLEMENT.STATUS
  is '状态      true:已归集   false 未归集';
comment on column ACCOUNT_SETTLEMENT.SETTLEMENT_BEGIN_DATE
  is '清算月份';
comment on column ACCOUNT_SETTLEMENT.ACCOUNT_SETTLEMENT_ID
  is 'id';
comment on column ACCOUNT_SETTLEMENT.SETTLEMENT_END_DATE
  is '清算月份';
alter table ACCOUNT_SETTLEMENT
  add primary key (ACCOUNT_SETTLEMENT_ID);

create table ACCOUNT_STATUS_TRANSFER
(
  ID                      NUMBER not null,
  TYPE_MODEL              VARCHAR2(10),
  ACTION_NAME             VARCHAR2(64),
  ORIGINAL_ACCOUNT_STATUS NUMBER,
  TARGET_ACCOUNT_STATUS   NUMBER
);
comment on column ACCOUNT_STATUS_TRANSFER.ID
  is 'ID';
comment on column ACCOUNT_STATUS_TRANSFER.ACTION_NAME
  is '动作名称';
comment on column ACCOUNT_STATUS_TRANSFER.ORIGINAL_ACCOUNT_STATUS
  is '账户原始状态';
comment on column ACCOUNT_STATUS_TRANSFER.TARGET_ACCOUNT_STATUS
  is '账户目标状态';
alter table ACCOUNT_STATUS_TRANSFER
  add primary key (ID);


create table ACCOUNT_TYPE
(
  ACCOUNT_TYPE_ID     NUMBER not null,
  ACCOUNT_TYPE_NAME   VARCHAR2(50),
  TYPE                VARCHAR2(10),
  SCOPE               VARCHAR2(10),
  DIRECTORY           VARCHAR2(10),
  TYPE_MODEL          VARCHAR2(10),
  CCY                 VARCHAR2(10),
  IDENTITY_TYPE       VARCHAR2(50),
  ACCOUNT_TYPE_STATUS NUMBER
);
comment on column ACCOUNT_TYPE.ACCOUNT_TYPE_ID
  is '账户类型ID';
comment on column ACCOUNT_TYPE.ACCOUNT_TYPE_NAME
  is '账户类型名称';
comment on column ACCOUNT_TYPE.TYPE
  is '支持类型账户：可用(isavaible support)、不可用(unavaible support)、授信(creditable support)';
comment on column ACCOUNT_TYPE.SCOPE
  is '业务属性 一般、结算...normol';
comment on column ACCOUNT_TYPE.DIRECTORY
  is '进、出 debit Credit';
comment on column ACCOUNT_TYPE.TYPE_MODEL
  is 'CCY or Card';
comment on column ACCOUNT_TYPE.CCY
  is '币种';
comment on column ACCOUNT_TYPE.IDENTITY_TYPE
  is '可使用用户类型 ';
comment on column ACCOUNT_TYPE.ACCOUNT_TYPE_STATUS
  is '状态 1可用，0不可用。';
alter table ACCOUNT_TYPE
  add primary key (ACCOUNT_TYPE_ID);

create table ACCOUNT_VALUE_CHANGE_LOG
(
  ACCOUNT_VALUE_CHANGE_LOG_ID VARCHAR2(32) not null,
  ACCOUNT_ID                  VARCHAR2(32),
  CHANGE_TYPE                 VARCHAR2(2),
  BEFORE_DEDUCTIONS           NUMBER(20,3),
  AFTER_DEDUCTIONS            NUMBER(20,3),
  RMK                         VARCHAR2(200)
);
comment on table ACCOUNT_VALUE_CHANGE_LOG
  is '账户余额变化表';
comment on column ACCOUNT_VALUE_CHANGE_LOG.ACCOUNT_VALUE_CHANGE_LOG_ID
  is '主键';
comment on column ACCOUNT_VALUE_CHANGE_LOG.ACCOUNT_ID
  is '账户ID';
comment on column ACCOUNT_VALUE_CHANGE_LOG.CHANGE_TYPE
  is '扣款/加款';
comment on column ACCOUNT_VALUE_CHANGE_LOG.BEFORE_DEDUCTIONS
  is '扣款前余额';
comment on column ACCOUNT_VALUE_CHANGE_LOG.AFTER_DEDUCTIONS
  is '扣款后余额';
comment on column ACCOUNT_VALUE_CHANGE_LOG.RMK
  is '备注';
alter table ACCOUNT_VALUE_CHANGE_LOG
  add primary key (ACCOUNT_VALUE_CHANGE_LOG_ID);

create table APPLYADDACCOUNT
(
  ACCOUNT_ID        NUMBER,
  STATUS            VARCHAR2(10),
  AVAILABLE_BALANCE VARCHAR2(30),
  ACCOUNT_TYPE_ID   NUMBER,
  RMK               VARCHAR2(3000),
  AUDITUSER         VARCHAR2(50),
  AUDITDATE         DATE,
  CREATOR           VARCHAR2(50),
  CREATE_DATE       DATE,
  ID                NUMBER not null,
  MERCHANTNAME      VARCHAR2(30),
  CCY               VARCHAR2(10),
  ACCOUNT_TYPE_NAME VARCHAR2(50),
  AUDITSTATUS       VARCHAR2(10)
);
comment on table APPLYADDACCOUNT
  is '申请加款记录表';
comment on column APPLYADDACCOUNT.ACCOUNT_ID
  is '账户ID';
comment on column APPLYADDACCOUNT.STATUS
  is '状态';
comment on column APPLYADDACCOUNT.AVAILABLE_BALANCE
  is '可用余额';
comment on column APPLYADDACCOUNT.ACCOUNT_TYPE_ID
  is '账户类型ID';
comment on column APPLYADDACCOUNT.RMK
  is '备注';
comment on column APPLYADDACCOUNT.AUDITUSER
  is '审核人';
comment on column APPLYADDACCOUNT.AUDITDATE
  is '审核时间';
comment on column APPLYADDACCOUNT.CREATOR
  is '创建人';
comment on column APPLYADDACCOUNT.CREATE_DATE
  is '创建时间';
comment on column APPLYADDACCOUNT.ID
  is 'ID';
comment on column APPLYADDACCOUNT.MERCHANTNAME
  is '商户';
comment on column APPLYADDACCOUNT.CCY
  is '币种';
comment on column APPLYADDACCOUNT.ACCOUNT_TYPE_NAME
  is '账户类型名称';
comment on column APPLYADDACCOUNT.AUDITSTATUS
  is '状态:0代审核；1审核';
alter table APPLYADDACCOUNT
  add primary key (ID);

create table CARD_ACCOUNT
(
  ACCOUNT_ID       NUMBER not null,
  IDENTITYID       VARCHAR2(32),
  IDENTITYTYPE     VARCHAR2(32),
  STATUS           VARCHAR2(10),
  VALUE            VARCHAR2(30),
  BALANCE          VARCHAR2(30),
  ACCOUNT_TYPE_ID  NUMBER,
  RMK              VARCHAR2(200),
  LAST_UPDATE_USER VARCHAR2(50),
  LAST_UPDATE_DATE DATE,
  CREATOR          VARCHAR2(50),
  CREATE_DATE      DATE,
  SIGN             VARCHAR2(64),
  VERSION          NUMBER
);
comment on column CARD_ACCOUNT.ACCOUNT_ID
  is '账户ID';
comment on column CARD_ACCOUNT.IDENTITYID
  is '用户ID';
comment on column CARD_ACCOUNT.IDENTITYTYPE
  is '用户类型';
comment on column CARD_ACCOUNT.STATUS
  is '状态';
comment on column CARD_ACCOUNT.VALUE
  is '数值';
comment on column CARD_ACCOUNT.BALANCE
  is '价值';
comment on column CARD_ACCOUNT.ACCOUNT_TYPE_ID
  is '账户类型ID';
comment on column CARD_ACCOUNT.RMK
  is '备注';
comment on column CARD_ACCOUNT.LAST_UPDATE_USER
  is '最后更新人';
comment on column CARD_ACCOUNT.LAST_UPDATE_DATE
  is '最后更新时间';
comment on column CARD_ACCOUNT.CREATOR
  is '创建人';
comment on column CARD_ACCOUNT.CREATE_DATE
  is '创建时间';
comment on column CARD_ACCOUNT.SIGN
  is '签名';
comment on column CARD_ACCOUNT.VERSION
  is '版本';
alter table CARD_ACCOUNT
  add primary key (ACCOUNT_ID);

create table CARD_ACCOUNT_BALANCE_HISTORY
(
  ID             NUMBER not null,
  TRANSACTION_ID NUMBER,
  ACCOUNT_ID     NUMBER,
  OLD_VALUE      VARCHAR2(30),
  OLD_BALANCE    VARCHAR2(30),
  NEW_VALUE      VARCHAR2(30),
  NEW_BALANCE    VARCHAR2(30),
  CREATE_DATE    DATE,
  TYPE           VARCHAR2(5),
  DESC_STR       VARCHAR2(800)
);
comment on column CARD_ACCOUNT_BALANCE_HISTORY.ID
  is '主键';
comment on column CARD_ACCOUNT_BALANCE_HISTORY.TRANSACTION_ID
  is '交易ID';
comment on column CARD_ACCOUNT_BALANCE_HISTORY.ACCOUNT_ID
  is '账户ID';
comment on column CARD_ACCOUNT_BALANCE_HISTORY.OLD_VALUE
  is '修改前总数';
comment on column CARD_ACCOUNT_BALANCE_HISTORY.OLD_BALANCE
  is '修改钱总价值';
comment on column CARD_ACCOUNT_BALANCE_HISTORY.NEW_VALUE
  is '修改后总数';
comment on column CARD_ACCOUNT_BALANCE_HISTORY.NEW_BALANCE
  is '修改后总价值';
comment on column CARD_ACCOUNT_BALANCE_HISTORY.CREATE_DATE
  is '创建时间';
comment on column CARD_ACCOUNT_BALANCE_HISTORY.TYPE
  is '日志类型';
comment on column CARD_ACCOUNT_BALANCE_HISTORY.DESC_STR
  is '日志说明';
alter table CARD_ACCOUNT_BALANCE_HISTORY
  add primary key (ID);

create table CCY_ACCOUNT
(
  ACCOUNT_ID           NUMBER not null,
  STATUS               VARCHAR2(10),
  AVAILABLE_BALANCE    VARCHAR2(30),
  UNAVAILABLE_BANLANCE VARCHAR2(30),
  CREDITABLE_BANLANCE  VARCHAR2(30),
  ACCOUNT_TYPE_ID      NUMBER,
  RMK                  VARCHAR2(200),
  LAST_UPDATE_USER     VARCHAR2(50),
  LAST_UPDATE_DATE     DATE,
  CREATOR              VARCHAR2(50),
  CREATE_DATE          DATE,
  SIGN                 VARCHAR2(64),
  VERSION              NUMBER,
  HISTORY_ID    NUMBER
);
comment on column CCY_ACCOUNT.ACCOUNT_ID
  is '账户ID';
comment on column CCY_ACCOUNT.STATUS
  is '状态';
comment on column CCY_ACCOUNT.AVAILABLE_BALANCE
  is '可用余额';
comment on column CCY_ACCOUNT.UNAVAILABLE_BANLANCE
  is '不可用余额';
comment on column CCY_ACCOUNT.CREDITABLE_BANLANCE
  is '授信余额';
comment on column CCY_ACCOUNT.ACCOUNT_TYPE_ID
  is '账户类型ID';
comment on column CCY_ACCOUNT.RMK
  is '备注';
comment on column CCY_ACCOUNT.LAST_UPDATE_USER
  is '最后更新人';
comment on column CCY_ACCOUNT.LAST_UPDATE_DATE
  is '最后更新时间';
comment on column CCY_ACCOUNT.CREATOR
  is '创建人';
comment on column CCY_ACCOUNT.CREATE_DATE
  is '创建时间';
comment on column CCY_ACCOUNT.SIGN
  is '签名';
comment on column CCY_ACCOUNT.VERSION
  is '版本';
comment on column CCY_ACCOUNT.HISTORY_ID
  is '流水号';
  
alter table CCY_ACCOUNT
  add primary key (ACCOUNT_ID);

create table CCY_ACCOUNT_BALANCE_HISTORY
(
  ID                       NUMBER not null,
  TRANSACTION_ID           NUMBER,
  ACCOUNT_ID               NUMBER,
  NEW_AVAILABLE_BALANCE    NUMBER,
  NEW_UNAVAILABLE_BANLANCE NUMBER,
  NEW_CREDITABLE_BANLANCE  NUMBER,
  CREATE_DATE              DATE,
  TYPE                     VARCHAR2(5),
  DESC_STR                 VARCHAR2(800),
  IDENTITY_NAME            VARCHAR2(32),
  CHANGE_AMOUNT       NUMBER(18,4)
);
comment on column CCY_ACCOUNT_BALANCE_HISTORY.ID
  is '主键';
comment on column CCY_ACCOUNT_BALANCE_HISTORY.TRANSACTION_ID
  is '交易ID';
comment on column CCY_ACCOUNT_BALANCE_HISTORY.ACCOUNT_ID
  is '账户ID';
comment on column CCY_ACCOUNT_BALANCE_HISTORY.NEW_AVAILABLE_BALANCE
  is '修改后可用余额';
comment on column CCY_ACCOUNT_BALANCE_HISTORY.NEW_UNAVAILABLE_BANLANCE
  is '修改后不可用余额';
comment on column CCY_ACCOUNT_BALANCE_HISTORY.NEW_CREDITABLE_BANLANCE
  is '修改后授信余额';
comment on column CCY_ACCOUNT_BALANCE_HISTORY.CREATE_DATE
  is '创建时间';
comment on column CCY_ACCOUNT_BALANCE_HISTORY.TYPE
  is '日志类型';
comment on column CCY_ACCOUNT_BALANCE_HISTORY.DESC_STR
  is '日志说明';
comment on column CCY_ACCOUNT_BALANCE_HISTORY.IDENTITY_NAME
  is '用户名称';
comment on column CCY_ACCOUNT_BALANCE_HISTORY.CHANGE_AMOUNT
  is '变动金额';
alter table CCY_ACCOUNT_BALANCE_HISTORY
  add primary key (ID);

create table CONNECTIONINFOMATION
(
  MIRROR_ID               VARCHAR2(32),
  CONNECTIONINFOMATION_ID VARCHAR2(32) not null,
  PHONE                   VARCHAR2(20),
  EMAIL                   VARCHAR2(200),
  MOBILE_PHONE            VARCHAR2(20)
);
comment on table CONNECTIONINFOMATION
  is '组织联系信息表';
comment on column CONNECTIONINFOMATION.PHONE
  is '电话号码';
comment on column CONNECTIONINFOMATION.EMAIL
  is '邮箱地址';
comment on column CONNECTIONINFOMATION.MOBILE_PHONE
  is '移动电话号码';
alter table CONNECTIONINFOMATION
  add primary key (CONNECTIONINFOMATION_ID);

create table CUSTOMER
(
  IDENTITY_ID   NUMBER not null,
  PERSON_ID     NUMBER not null,
  CREATE_TIME   DATE default sysdate not null,
  UPDATE_NAME   VARCHAR2(20),
  UPDATE_TIME   DATE default sysdate not null,
  STATUS        VARCHAR2(2),
  USER_TYPE     VARCHAR2(20),
  REMARK        VARCHAR2(200),
  CUSTOMER_NAME VARCHAR2(20),
  DISPLAY_NAME  VARCHAR2(20)
);
comment on table CUSTOMER
  is '用户表';
comment on column CUSTOMER.IDENTITY_ID
  is '主键';
comment on column CUSTOMER.PERSON_ID
  is '用户ID';
comment on column CUSTOMER.CREATE_TIME
  is '创建时间';
comment on column CUSTOMER.UPDATE_NAME
  is '最后更新用户名';
comment on column CUSTOMER.UPDATE_TIME
  is '最后更新时间';
comment on column CUSTOMER.STATUS
  is '状态';
comment on column CUSTOMER.USER_TYPE
  is '用户类型
HuFei：话费系统，SHJF：生活缴费系统';
comment on column CUSTOMER.REMARK
  is '备注';
comment on column CUSTOMER.CUSTOMER_NAME
  is '用户名称';
comment on column CUSTOMER.DISPLAY_NAME
  is '显示名称';
alter table CUSTOMER
  add primary key (IDENTITY_ID);

create table MENU
(
  MENU_ID          NUMBER not null,
  MENU_NAME        VARCHAR2(30),
  DISPLAY_ORDER    INTEGER,
  PAGE_RESOURCE_ID VARCHAR2(200),
  PARENT_MENU_ID   NUMBER,
  STATUS           VARCHAR2(2),
  CREATE_TIME      DATE,
  UPDATE_NAME      VARCHAR2(30),
  UPDATE_TIME      DATE,
  REMARK           VARCHAR2(200),
  CREATE_USER      VARCHAR2(20),
  MENU_LEVEL       VARCHAR2(30),
  PORTALTYPE       VARCHAR2(20)
);
comment on table MENU
  is '菜单资源权限表';
comment on column MENU.MENU_ID
  is '菜单ID';
comment on column MENU.MENU_NAME
  is '菜单名称';
comment on column MENU.DISPLAY_ORDER
  is '显示顺序';
comment on column MENU.PAGE_RESOURCE_ID
  is '对应页面资源ID';
comment on column MENU.PARENT_MENU_ID
  is '父节点';
comment on column MENU.STATUS
  is '状态';
comment on column MENU.CREATE_TIME
  is '创建时间';
comment on column MENU.UPDATE_NAME
  is '修改人';
comment on column MENU.UPDATE_TIME
  is '修改时间';
comment on column MENU.REMARK
  is '备注';
comment on column MENU.CREATE_USER
  is '创建人';
comment on column MENU.MENU_LEVEL
  is '功能级别';
comment on column MENU.PORTALTYPE
  is '后台类型：PORTAL、APORTAL、MPORTAL';
alter table MENU
  add primary key (MENU_ID);

create table FUNCTION
(
  FUNCTION_ID   NUMBER not null,
  FUNCTION_NAME VARCHAR2(2),
  MENU_ID       NUMBER,
  CREATE_TIME   DATE,
  UPDATE_TIME   DATE,
  UPDATE_USER   VARCHAR2(30),
  STATUS        INTEGER,
  REMARK        VARCHAR2(200)
);
comment on table FUNCTION
  is '功能权限表';
comment on column FUNCTION.FUNCTION_ID
  is '功能权限Id';
comment on column FUNCTION.FUNCTION_NAME
  is '功能名称';
comment on column FUNCTION.MENU_ID
  is '所属菜单';
comment on column FUNCTION.CREATE_TIME
  is '创建时间';
comment on column FUNCTION.UPDATE_TIME
  is '更新时间';
comment on column FUNCTION.UPDATE_USER
  is '更新人';
comment on column FUNCTION.STATUS
  is '状态';
comment on column FUNCTION.REMARK
  is '备注';
alter table FUNCTION
  add primary key (FUNCTION_ID);
alter table FUNCTION
  add constraint FK_FUNCTION_REFERENCE_MENU_PRI foreign key (MENU_ID)
  references MENU (MENU_ID);

create table IDENTITY_ACCOUNT
(
  IDENTITY_ACCOUNT_ID VARCHAR2(32) not null,
  IDENTITY_ID         VARCHAR2(32),
  ACCOUNT_ID          VARCHAR2(32),
  RMK                 VARCHAR2(200)
);
comment on table IDENTITY_ACCOUNT
  is '用户账户表';
comment on column IDENTITY_ACCOUNT.IDENTITY_ACCOUNT_ID
  is '主键';
comment on column IDENTITY_ACCOUNT.IDENTITY_ID
  is '用户ID';
comment on column IDENTITY_ACCOUNT.ACCOUNT_ID
  is '账户ID';
comment on column IDENTITY_ACCOUNT.RMK
  is '备注';
alter table IDENTITY_ACCOUNT
  add primary key (IDENTITY_ACCOUNT_ID);

create table IDENTITY_ACCOUNT_ROLE
(
  ID            NUMBER not null,
  IDENTITY_ID   NUMBER,
  IDENTITY_TYPE VARCHAR2(64),
  ACCOUNT_ID    NUMBER,
  ACCOUNT_TYPE  VARCHAR2(64),
  RELATION      VARCHAR2(10)
);
comment on column IDENTITY_ACCOUNT_ROLE.ID
  is 'ID';
comment on column IDENTITY_ACCOUNT_ROLE.IDENTITY_ID
  is '用户ID';
comment on column IDENTITY_ACCOUNT_ROLE.IDENTITY_TYPE
  is '用户类型';
comment on column IDENTITY_ACCOUNT_ROLE.ACCOUNT_ID
  is '账户ID';
comment on column IDENTITY_ACCOUNT_ROLE.ACCOUNT_TYPE
  is '账户类型';
comment on column IDENTITY_ACCOUNT_ROLE.RELATION
  is '用户与账户之间的关系own:所属，use:使用';
alter table IDENTITY_ACCOUNT_ROLE
  add primary key (ID);

create table ROLE
(
  ROLE_ID     NUMBER not null,
  ROLE_NAME   VARCHAR2(20) not null,
  CREATE_TIME DATE,
  UPDATE_USER VARCHAR2(20),
  UPDATE_TIME DATE,
  STATUS      VARCHAR2(2),
  REMARK      VARCHAR2(200),
  ROLE_TYPE   VARCHAR2(20)
);
comment on table ROLE
  is '角色表';
comment on column ROLE.ROLE_ID
  is '角色ID';
comment on column ROLE.ROLE_NAME
  is '角色名称';
comment on column ROLE.CREATE_TIME
  is '创建时间';
comment on column ROLE.UPDATE_USER
  is '更新用户名';
comment on column ROLE.UPDATE_TIME
  is '更新时间';
comment on column ROLE.STATUS
  is '状态';
comment on column ROLE.REMARK
  is '备注';
comment on column ROLE.ROLE_TYPE
  is '角色类型';
alter table ROLE
  add primary key (ROLE_ID);

create table IDENTITY_ROLE
(
  IDENTITY_ROLE_ID NUMBER not null,
  IDENTITY_ID      NUMBER,
  IDENTITY_TYPE    VARCHAR2(20),
  ROLE_ID          NUMBER,
  CREATE_TIME      DATE,
  UPDATE_NAME      VARCHAR2(20),
  UPDATE_TIME      DATE,
  STATUS           VARCHAR2(2),
  REMARK           VARCHAR2(200)
);
comment on table IDENTITY_ROLE
  is '用户角色表';
comment on column IDENTITY_ROLE.IDENTITY_ROLE_ID
  is '主键';
comment on column IDENTITY_ROLE.IDENTITY_ID
  is '用户ID';
comment on column IDENTITY_ROLE.IDENTITY_TYPE
  is '用户类型';
comment on column IDENTITY_ROLE.ROLE_ID
  is '角色ID';
comment on column IDENTITY_ROLE.CREATE_TIME
  is '创建时间';
comment on column IDENTITY_ROLE.UPDATE_NAME
  is '更新用户名';
comment on column IDENTITY_ROLE.UPDATE_TIME
  is '更新时间';
comment on column IDENTITY_ROLE.STATUS
  is '状态';
comment on column IDENTITY_ROLE.REMARK
  is '备注';
alter table IDENTITY_ROLE
  add primary key (IDENTITY_ROLE_ID);
alter table IDENTITY_ROLE
  add constraint FK_IDENTITY_REFERENCE_ROLE foreign key (ROLE_ID)
  references ROLE (ROLE_ID);

create table IDENTITY_STATUS_TRANSFER
(
  ID                  NUMBER not null,
  OLD_IDENTITY_STATUS VARCHAR2(20),
  NEW_IDENTITY_STATUS VARCHAR2(20),
  ACTION_NAME         VARCHAR2(100),
  IDENTITY_TYPE       VARCHAR2(20)
);
comment on column IDENTITY_STATUS_TRANSFER.ID
  is '主键';
comment on column IDENTITY_STATUS_TRANSFER.OLD_IDENTITY_STATUS
  is '原始状态';
comment on column IDENTITY_STATUS_TRANSFER.NEW_IDENTITY_STATUS
  is '修改状态';
comment on column IDENTITY_STATUS_TRANSFER.ACTION_NAME
  is '修改原由';
comment on column IDENTITY_STATUS_TRANSFER.IDENTITY_TYPE
  is '用户类型';
alter table IDENTITY_STATUS_TRANSFER
  add constraint IDENTITYSTATUSDEFENDERSID primary key (ID);

create table MERCHANT
(
  ORGANIZATION_ID    VARCHAR2(32),
  IDENTITY_ID        NUMBER not null,
  MERCHANT_NAME      VARCHAR2(20),
  MERCHANT_TYPE      VARCHAR2(20),
  STATUS             INTEGER,
  UPDATE_USER        VARCHAR2(20),
  UPDATE_TIME        DATE,
  CREATE_USER        VARCHAR2(20),
  CREATE_TIME        DATE,
  MERCHANT_CODE      VARCHAR2(20),
  PARENT_IDENTITY_ID NUMBER,
  DISCRIPTION        VARCHAR2(200),
  MERCHANT_LEVEL     VARCHAR2(20),
  IS_REBATE          INTEGER default 1 not null
);
comment on table MERCHANT
  is '商户表';
comment on column MERCHANT.ORGANIZATION_ID
  is '组织ID';
comment on column MERCHANT.IDENTITY_ID
  is '主键/渠道编号';
comment on column MERCHANT.MERCHANT_NAME
  is '渠道名称';
comment on column MERCHANT.MERCHANT_TYPE
  is '类型';
comment on column MERCHANT.STATUS
  is '状态';
comment on column MERCHANT.UPDATE_USER
  is '最后更新人';
comment on column MERCHANT.UPDATE_TIME
  is '最后更新日期';
comment on column MERCHANT.CREATE_USER
  is '创建人';
comment on column MERCHANT.CREATE_TIME
  is '创建时间';
comment on column MERCHANT.MERCHANT_CODE
  is '商户编号';
comment on column MERCHANT.PARENT_IDENTITY_ID
  is '商户父级编号';
comment on column MERCHANT.DISCRIPTION
  is '描述';
comment on column MERCHANT.MERCHANT_LEVEL
  is '商户层级';
comment on column MERCHANT.IS_REBATE
  is '是否返佣 0是 1否';
alter table MERCHANT
  add primary key (IDENTITY_ID);

create table OPERATOR
(
  IDENTITY_ID         NUMBER not null,
  PERSON_ID           NUMBER,
  OWNER_IDENTITY_ID   NUMBER,
  LAST_UPDATE_USER    VARCHAR2(20),
  LAST_UPDATE_DATE    DATE,
  STATUS              VARCHAR2(2),
  OPERATOR_TYPE       VARCHAR2(20),
  REMARK              VARCHAR2(200),
  OWNER_IDENTITY_TYPE VARCHAR2(16),
  OPERATOR_NAME       VARCHAR2(20),
  DISPLAY_NAME        VARCHAR2(20)
);
comment on table OPERATOR
  is '商户操作员';
comment on column OPERATOR.IDENTITY_ID
  is '主键';
comment on column OPERATOR.PERSON_ID
  is '用户ID';
comment on column OPERATOR.OWNER_IDENTITY_ID
  is '所属商户编号';
comment on column OPERATOR.LAST_UPDATE_USER
  is '最后更新用户名';
comment on column OPERATOR.LAST_UPDATE_DATE
  is '最后更新时间';
comment on column OPERATOR.STATUS
  is '状态';
comment on column OPERATOR.OPERATOR_TYPE
  is ' 用户类型
1.普通用户
2.子系统管理员
3.超级系统管理员';
comment on column OPERATOR.REMARK
  is '备注';
comment on column OPERATOR.OWNER_IDENTITY_TYPE
  is '所属商户类型（merchant、sp）';
comment on column OPERATOR.OPERATOR_NAME
  is '操作员名称';
comment on column OPERATOR.DISPLAY_NAME
  is '显示名称';
alter table OPERATOR
  add primary key (IDENTITY_ID);

create table ORGANIZATION
(
  ORGANIZATION_ID                NUMBER not null,
  ORGANIZATION_NAME              VARCHAR2(64),
  ORGANIZATION_REGISTRATION_NO   VARCHAR2(64),
  ORGANIZATION_REGISTRATION_ADDR VARCHAR2(200),
  ORGANIZATION_INDUSTRY          VARCHAR2(64),
  ORGANIZATION_WEBSITE           VARCHAR2(64),
  LEGAL                          VARCHAR2(20),
  POSTCODE                       VARCHAR2(20),
  REGDATE                        DATE,
  ENDDATE                        DATE,
  OPEN_TYPE                      VARCHAR2(2),
  ORGANIZATION_LEVEL             VARCHAR2(20),
  COPURL                         VARCHAR2(64),
  AREA_CODE                      VARCHAR2(64),
  AREA_NAME                      VARCHAR2(64)
);
comment on column ORGANIZATION.ORGANIZATION_NAME
  is '组织名称';
comment on column ORGANIZATION.ORGANIZATION_REGISTRATION_NO
  is '工商注册号';
comment on column ORGANIZATION.ORGANIZATION_REGISTRATION_ADDR
  is '组织注册地址';
comment on column ORGANIZATION.ORGANIZATION_INDUSTRY
  is '组织行业';
comment on column ORGANIZATION.ORGANIZATION_WEBSITE
  is '组织网站';
comment on column ORGANIZATION.LEGAL
  is '法人';
comment on column ORGANIZATION.POSTCODE
  is '邮编';
comment on column ORGANIZATION.REGDATE
  is '注册日期';
comment on column ORGANIZATION.ENDDATE
  is '使用截止日期';
comment on column ORGANIZATION.OPEN_TYPE
  is '来源1:手工新增2:在线注册';
comment on column ORGANIZATION.ORGANIZATION_LEVEL
  is '组织级别';
comment on column ORGANIZATION.COPURL
  is '组织网址';
comment on column ORGANIZATION.AREA_CODE
  is '地区编码';
comment on column ORGANIZATION.AREA_NAME
  is '地区名称';
alter table ORGANIZATION
  add primary key (ORGANIZATION_ID);

create table PAGE_RESOURCE
(
  RESOURCE_ID        NUMBER not null,
  PAGE_RESOURCE_NAME VARCHAR2(30) not null,
  PAGE_URL           VARCHAR2(200) not null,
  STATUS             VARCHAR2(2),
  CREATE_TIME        DATE,
  UPDATE_NAME        VARCHAR2(20),
  UPDATE_TIME        DATE,
  REMARK             VARCHAR2(200),
  CREATE_NAME        VARCHAR2(20)
);
comment on table PAGE_RESOURCE
  is '页面资源权限表';
comment on column PAGE_RESOURCE.RESOURCE_ID
  is '页面资源ID';
comment on column PAGE_RESOURCE.PAGE_RESOURCE_NAME
  is '页面名称';
comment on column PAGE_RESOURCE.PAGE_URL
  is '页面路径';
comment on column PAGE_RESOURCE.STATUS
  is '状态';
comment on column PAGE_RESOURCE.CREATE_TIME
  is '创建时间';
comment on column PAGE_RESOURCE.UPDATE_NAME
  is '更新用户名';
comment on column PAGE_RESOURCE.UPDATE_TIME
  is '更新时间';
comment on column PAGE_RESOURCE.REMARK
  is '备注';
comment on column PAGE_RESOURCE.CREATE_NAME
  is '创建人';
alter table PAGE_RESOURCE
  add primary key (RESOURCE_ID);

create table PAPERWORK
(
  ORGNIAZTION_ID VARCHAR2(32),
  PAPERWORK_ID   VARCHAR2(32) not null,
  PERSON_ID      NUMBER,
  PAPER_TYPE     VARCHAR2(20),
  PAPER_NAME     VARCHAR2(20),
  PAPER_VALUE    VARCHAR2(20),
  RMK            VARCHAR2(200)
);
comment on table PAPERWORK
  is '凭证表';
comment on column PAPERWORK.ORGNIAZTION_ID
  is '组织ID';
comment on column PAPERWORK.PAPERWORK_ID
  is '主键';
comment on column PAPERWORK.PERSON_ID
  is '用户ID';
comment on column PAPERWORK.PAPER_TYPE
  is '凭证类型';
comment on column PAPERWORK.PAPER_NAME
  is '凭证名称';
comment on column PAPERWORK.PAPER_VALUE
  is '凭证对应参数';
comment on column PAPERWORK.RMK
  is '备注';
alter table PAPERWORK
  add primary key (PAPERWORK_ID);

create table PERSON
(
  PERSON_ID   NUMBER not null,
  UPDATE_USER VARCHAR2(20),
  UPDATE_TIME DATE,
  SEX         VARCHAR2(2),
  BIRTHDAY    VARCHAR2(20),
  CREATE_TIME DATE,
  REMARK      VARCHAR2(200),
  EMAIL       VARCHAR2(200),
  PHONE       VARCHAR2(20),
  QQ          VARCHAR2(20)
);
comment on table PERSON
  is '用户表';
comment on column PERSON.PERSON_ID
  is '用户ID';
comment on column PERSON.UPDATE_USER
  is '最后更新用户名';
comment on column PERSON.UPDATE_TIME
  is '最后更新时间';
comment on column PERSON.SEX
  is '性别';
comment on column PERSON.BIRTHDAY
  is '生日';
comment on column PERSON.CREATE_TIME
  is '创建时间';
comment on column PERSON.REMARK
  is '备注';
comment on column PERSON.EMAIL
  is '邮箱地址';
comment on column PERSON.PHONE
  is '手机号码';
comment on column PERSON.QQ
  is 'QQ号码';
alter table PERSON
  add primary key (PERSON_ID);

create table PRIVILEGE
(
  PRIVILEGE_ID        NUMBER,
  PRIVILEGE_NAME      VARCHAR2(50),
  PERMISSION_NAME     VARCHAR2(50),
  PARENT_PRIVILEGE_ID NUMBER,
  CREATE_TIME         DATE,
  CREATE_USER         VARCHAR2(20),
  UPDATE_USER         VARCHAR2(20),
  UPDATE_TIME         DATE,
  REMARK              VARCHAR2(200)
);
comment on column PRIVILEGE.PRIVILEGE_ID
  is '权限ID';
comment on column PRIVILEGE.PRIVILEGE_NAME
  is '功能标识名称';
comment on column PRIVILEGE.PERMISSION_NAME
  is '权限名称（shiro定义名）';
comment on column PRIVILEGE.PARENT_PRIVILEGE_ID
  is '父级权限ID';
comment on column PRIVILEGE.CREATE_TIME
  is '创建时间';
comment on column PRIVILEGE.CREATE_USER
  is '创建人';
comment on column PRIVILEGE.UPDATE_USER
  is '修改人';
comment on column PRIVILEGE.UPDATE_TIME
  is '修改时间';
comment on column PRIVILEGE.REMARK
  is '备注';
alter table PRIVILEGE
  add primary key (PRIVILEGE_ID);

create table PROFIT_IMPUTATION
(
  PROFIT_IMPUTATION_ID     NUMBER not null,
  PROFIT_ACCOUNT_ID        NUMBER,
  MIDDLE_PROFIT_ACCOUNT_ID NUMBER,
  MERCHANT_ID              NUMBER,
  MERCHANT_NAME            VARCHAR2(64),
  IMPUTATION_PROFIT        NUMBER,
  PROFIT_ACCOUNT_BALANCE   NUMBER,
  IMPUTATION_BEGIN_DATE    DATE,
  IMPUTATION_END_DATE      DATE,
  IMPUTATION_STATUS        NUMBER
);
comment on column PROFIT_IMPUTATION.PROFIT_IMPUTATION_ID
  is '归集id';
comment on column PROFIT_IMPUTATION.PROFIT_ACCOUNT_ID
  is '系统商户利润账户';
comment on column PROFIT_IMPUTATION.MIDDLE_PROFIT_ACCOUNT_ID
  is '系统商户中间利润账户';
comment on column PROFIT_IMPUTATION.MERCHANT_ID
  is '商户';
comment on column PROFIT_IMPUTATION.MERCHANT_NAME
  is '商户名称';
comment on column PROFIT_IMPUTATION.IMPUTATION_PROFIT
  is '归集利润';
comment on column PROFIT_IMPUTATION.PROFIT_ACCOUNT_BALANCE
  is '系统商户利润账户余额';
comment on column PROFIT_IMPUTATION.IMPUTATION_BEGIN_DATE
  is '开始归集';
comment on column PROFIT_IMPUTATION.IMPUTATION_END_DATE
  is '结束归集';
comment on column PROFIT_IMPUTATION.IMPUTATION_STATUS
  is '归集状态';
alter table PROFIT_IMPUTATION
  add primary key (PROFIT_IMPUTATION_ID);

create table ROLE_MENU
(
  ROLE_MENU_ID    NUMBER not null,
  ROLE_ID         NUMBER,
  MENU_ID         NUMBER,
  STATUS          VARCHAR2(2),
  CREATE_TIME     DATE,
  UPDATE_USER     VARCHAR2(30),
  UPDATE_TIME     DATE,
  REMARK          VARCHAR2(200),
  CREATE_USER     VARCHAR2(20),
  ROLE_MENU_LEVEL VARCHAR2(30)
);
comment on table ROLE_MENU
  is '角色权限表';
comment on column ROLE_MENU.ROLE_MENU_ID
  is '角色权限ID';
comment on column ROLE_MENU.ROLE_ID
  is '角色ID';
comment on column ROLE_MENU.MENU_ID
  is '权限ID';
comment on column ROLE_MENU.STATUS
  is '状态';
comment on column ROLE_MENU.CREATE_TIME
  is '创建时间';
comment on column ROLE_MENU.UPDATE_USER
  is '更新人';
comment on column ROLE_MENU.UPDATE_TIME
  is '更新时间';
comment on column ROLE_MENU.REMARK
  is '备注';
comment on column ROLE_MENU.CREATE_USER
  is '创建人';
comment on column ROLE_MENU.ROLE_MENU_LEVEL
  is '角色菜单级别';
alter table ROLE_MENU
  add primary key (ROLE_MENU_ID);

create table ROLE_PRIVILEGE
(
  ROLE_PRIVILEGE_ID NUMBER not null,
  ROLE_ID           NUMBER,
  STATUS            VARCHAR2(2),
  CREATE_TIME       DATE,
  UPDATE_USER       VARCHAR2(30),
  UPDATE_TIME       DATE,
  REMARK            VARCHAR2(200),
  CREATE_USER       VARCHAR2(20),
  PRIVILEGE_ID      NUMBER
);
comment on table ROLE_PRIVILEGE
  is '角色权限表';
comment on column ROLE_PRIVILEGE.ROLE_PRIVILEGE_ID
  is '角色权限ID';
comment on column ROLE_PRIVILEGE.ROLE_ID
  is '角色ID';
comment on column ROLE_PRIVILEGE.STATUS
  is '状态';
comment on column ROLE_PRIVILEGE.CREATE_TIME
  is '创建时间';
comment on column ROLE_PRIVILEGE.UPDATE_USER
  is '更新人';
comment on column ROLE_PRIVILEGE.UPDATE_TIME
  is '更新时间';
comment on column ROLE_PRIVILEGE.REMARK
  is '备注';
comment on column ROLE_PRIVILEGE.CREATE_USER
  is '创建人';
comment on column ROLE_PRIVILEGE.PRIVILEGE_ID
  is '权限ID';
alter table ROLE_PRIVILEGE
  add primary key (ROLE_PRIVILEGE_ID);

create table SECURITY_CREDENTIAL
(
  SECURITY_ID      NUMBER not null,
  STATUS           VARCHAR2(2),
  IDENTITY_ID      NUMBER,
  IDENTITY_TYPE    VARCHAR2(20),
  SECURITY_NAME    VARCHAR2(50),
  SECURITY_VALUE   VARCHAR2(500),
  SECURITY_TYPE_ID NUMBER,
  CREATE_DATE      DATE default sysdate,
  CREATE_USER      VARCHAR2(20),
  UPDATE_DATE      DATE default sysdate,
  UPDATE_USER      VARCHAR2(20),
  VALIDITY_DATE    DATE default sysdate not null
);
comment on table SECURITY_CREDENTIAL
  is '密钥表';
comment on column SECURITY_CREDENTIAL.SECURITY_ID
  is 'ID';
comment on column SECURITY_CREDENTIAL.STATUS
  is '状态 0启用，禁用1';
comment on column SECURITY_CREDENTIAL.IDENTITY_ID
  is '用户ID';
comment on column SECURITY_CREDENTIAL.IDENTITY_TYPE
  is '用户类型';
comment on column SECURITY_CREDENTIAL.SECURITY_NAME
  is '密钥名称';
comment on column SECURITY_CREDENTIAL.SECURITY_VALUE
  is '密钥值';
comment on column SECURITY_CREDENTIAL.SECURITY_TYPE_ID
  is '密钥类型';
comment on column SECURITY_CREDENTIAL.CREATE_DATE
  is '创建时间';
comment on column SECURITY_CREDENTIAL.CREATE_USER
  is '创建人';
comment on column SECURITY_CREDENTIAL.UPDATE_DATE
  is '更新时间';
comment on column SECURITY_CREDENTIAL.UPDATE_USER
  is '更新人';
comment on column SECURITY_CREDENTIAL.VALIDITY_DATE
  is '有效时间';
alter table SECURITY_CREDENTIAL
  add primary key (SECURITY_ID);

create table SECURITY_CREDENTIAL_RULE
(
  SECURITY_RULE_ID   NUMBER not null,
  SECURITY_RULE_NAME VARCHAR2(50),
  LETTER             VARCHAR2(10),
  FIGURE             VARCHAR2(10),
  SPECIAL_CHARACTER  VARCHAR2(10),
  STATUS             NUMBER,
  IS_LOWERCASE       VARCHAR2(10),
  IS_UPPERCASE       VARCHAR2(10)
);
comment on table SECURITY_CREDENTIAL_RULE
  is '创建规则';
comment on column SECURITY_CREDENTIAL_RULE.SECURITY_RULE_ID
  is '密钥创建规则ID';
comment on column SECURITY_CREDENTIAL_RULE.SECURITY_RULE_NAME
  is '密钥创建规则名称';
comment on column SECURITY_CREDENTIAL_RULE.LETTER
  is '字母：0需要，1不需要';
comment on column SECURITY_CREDENTIAL_RULE.FIGURE
  is '数字：0需要，1不需要';
comment on column SECURITY_CREDENTIAL_RULE.SPECIAL_CHARACTER
  is '特殊字符: 0需要，1不需要';
comment on column SECURITY_CREDENTIAL_RULE.STATUS
  is '状态：0启用、1禁用';
comment on column SECURITY_CREDENTIAL_RULE.IS_LOWERCASE
  is '小写：0需要，1不需要';
comment on column SECURITY_CREDENTIAL_RULE.IS_UPPERCASE
  is '大写：0需要，1不需要';
alter table SECURITY_CREDENTIAL_RULE
  add primary key (SECURITY_RULE_ID);

create table SECURITY_CREDENTIAL_TYPE
(
  SECURITY_TYPE_ID   NUMBER not null,
  SECURITY_TYPE_NAME VARCHAR2(50),
  MODEL_TYPE         VARCHAR2(10),
  ENCRYPT_TYPE       VARCHAR2(10),
  MIN_LENGTH         NUMBER,
  MAX_LENGTH         NUMBER,
  VALIDITY           NUMBER,
  STATUS             NUMBER,
  IDENTITY_TYPE      VARCHAR2(100),
  SECURITY_RULE_ID   NUMBER
);
comment on table SECURITY_CREDENTIAL_TYPE
  is '密钥类型';
comment on column SECURITY_CREDENTIAL_TYPE.SECURITY_TYPE_ID
  is '密钥类型ID';
comment on column SECURITY_CREDENTIAL_TYPE.SECURITY_TYPE_NAME
  is '密钥类型名称（登录密码、代理商MD5Key、供货商MD5Key、系统MD5Key、供货商公钥）';
comment on column SECURITY_CREDENTIAL_TYPE.MODEL_TYPE
  is '密钥类型属性：Password、MD5Key、RSA公钥';
comment on column SECURITY_CREDENTIAL_TYPE.ENCRYPT_TYPE
  is '加密类型：MD5、3DES、RSA';
comment on column SECURITY_CREDENTIAL_TYPE.MIN_LENGTH
  is '最小长度';
comment on column SECURITY_CREDENTIAL_TYPE.MAX_LENGTH
  is '最大长度';
comment on column SECURITY_CREDENTIAL_TYPE.VALIDITY
  is '有效期（0：永久，30天，100天）';
comment on column SECURITY_CREDENTIAL_TYPE.STATUS
  is '状态：0启用、1禁用、2删除';
comment on column SECURITY_CREDENTIAL_TYPE.IDENTITY_TYPE
  is '可使用Identity类型：Operator、Customer、Sp、Merchant（Agent、Supply）';
comment on column SECURITY_CREDENTIAL_TYPE.SECURITY_RULE_ID
  is '密钥创建规则ID';
alter table SECURITY_CREDENTIAL_TYPE
  add primary key (SECURITY_TYPE_ID);

create table SECURITY_STATUS_TRANSFER
(
  ID          NUMBER not null,
  OLD_STATUS  VARCHAR2(20),
  NEW_STATUS  VARCHAR2(20),
  ACTION_NAME VARCHAR2(100)
);
comment on column SECURITY_STATUS_TRANSFER.ID
  is '主键';
comment on column SECURITY_STATUS_TRANSFER.OLD_STATUS
  is '原始状态';
comment on column SECURITY_STATUS_TRANSFER.NEW_STATUS
  is '修改状态';
comment on column SECURITY_STATUS_TRANSFER.ACTION_NAME
  is '修改原由';
alter table SECURITY_STATUS_TRANSFER
  add primary key (ID);

create table SP
(
  IDENTITY_ID NUMBER not null,
  SP_NAME     VARCHAR2(50),
  UPDATE_USER VARCHAR2(20),
  UPDATE_DATE DATE,
  STATUS      VARCHAR2(2),
  RMK         VARCHAR2(200),
  BUSINESS    VARCHAR2(16)
);
comment on table SP
  is '系统管理员';
comment on column SP.IDENTITY_ID
  is '主键';
comment on column SP.SP_NAME
  is '名称';
comment on column SP.UPDATE_USER
  is '最后更新用户名';
comment on column SP.UPDATE_DATE
  is '最后更新时间';
comment on column SP.STATUS
  is '状态';
comment on column SP.RMK
  is '备注';
comment on column SP.BUSINESS
  is '业务类型';
alter table SP
  add primary key (IDENTITY_ID);

create table SUPPLY_INTERFACE_INFO
(
  INTERFACE_INFO_ID NUMBER not null,
  IDENTITY_ID       NUMBER,
  REQUEST_URL       VARCHAR2(200),
  QUERY_URL         VARCHAR2(200),
  NOTIFY_URL        VARCHAR2(200),
  REQUEST_ACTION    VARCHAR2(100),
  QUERY_ACTION      VARCHAR2(100),
  STATUS            CHAR(1),
  UPDATE_USER       VARCHAR2(32),
  UPDATE_DATE       DATE
);
comment on column SUPPLY_INTERFACE_INFO.INTERFACE_INFO_ID
  is '记录编号';
comment on column SUPPLY_INTERFACE_INFO.IDENTITY_ID
  is '供货商编号';
comment on column SUPPLY_INTERFACE_INFO.REQUEST_URL
  is '发货地址';
comment on column SUPPLY_INTERFACE_INFO.QUERY_URL
  is '查询地址';
comment on column SUPPLY_INTERFACE_INFO.NOTIFY_URL
  is '结果接受地址';
comment on column SUPPLY_INTERFACE_INFO.REQUEST_ACTION
  is '发货业务单元';
comment on column SUPPLY_INTERFACE_INFO.QUERY_ACTION
  is '查询业务单元';
comment on column SUPPLY_INTERFACE_INFO.STATUS
  is '状态';
comment on column SUPPLY_INTERFACE_INFO.UPDATE_USER
  is '更新人';
comment on column SUPPLY_INTERFACE_INFO.UPDATE_DATE
  is '更新日期';
alter table SUPPLY_INTERFACE_INFO
  add primary key (INTERFACE_INFO_ID);
  
create table TRANSACTION_HISTORY
(
  TRANSACTION_ID      NUMBER not null,
  TRANSACTION_NO      VARCHAR2(40),
  PAYER_ACCOUNT_ID    NUMBER,
  PAYER_TYPE_MODEL    VARCHAR2(10),
  PAYEE_ACCOUNT_ID    NUMBER,
  PAYEE_TYPE_MODEL    VARCHAR2(10),
  AMT                 NUMBER,
  DESC_STR            VARCHAR2(800),
  CREATE_DATE         DATE,
  CREATOR             VARCHAR2(32),
  TYPE                VARCHAR2(10),
  PAYER_IDENTITY_NAME VARCHAR2(32),
  PAYEE_IDENTITY_NAME VARCHAR2(32),
  IS_REFUND           NUMBER
);
comment on column TRANSACTION_HISTORY.TRANSACTION_ID
  is '交易ID';
comment on column TRANSACTION_HISTORY.TRANSACTION_NO
  is '流水ID';
comment on column TRANSACTION_HISTORY.PAYER_ACCOUNT_ID
  is '账户A ID';
comment on column TRANSACTION_HISTORY.PAYER_TYPE_MODEL
  is '账户A类型';
comment on column TRANSACTION_HISTORY.PAYEE_ACCOUNT_ID
  is '账户B ID';
comment on column TRANSACTION_HISTORY.PAYEE_TYPE_MODEL
  is '账户B类型';
comment on column TRANSACTION_HISTORY.AMT
  is '交易金额';
comment on column TRANSACTION_HISTORY.DESC_STR
  is '交易描述';
comment on column TRANSACTION_HISTORY.CREATE_DATE
  is '创建时间';
comment on column TRANSACTION_HISTORY.CREATOR
  is '操作人';
comment on column TRANSACTION_HISTORY.TYPE
  is '日志类型';
comment on column TRANSACTION_HISTORY.PAYER_IDENTITY_NAME
  is '付款方用户名称';
comment on column TRANSACTION_HISTORY.PAYEE_IDENTITY_NAME
  is '收款方用户名称';
comment on column TRANSACTION_HISTORY.IS_REFUND
  is '是否退款 0.未退款  1.已经退款';
alter table TRANSACTION_HISTORY
  add primary key (TRANSACTION_ID);

  create table CCY_ACCOUNT_ADD_CASH_RECORD
(
  ID            NUMBER not null,
  MERCHANT_ID   NUMBER,
  MERCHANT_NAME VARCHAR2(20),
  OPERATOR_NAME VARCHAR2(20),
  APPLY_TIME    DATE,
  VERIFY_TIME   DATE,
  AMT           NUMBER,
  VERIFY_STATUS NUMBER,
  ACCOUNT_ID    NUMBER,
  RMK           VARCHAR2(200)
)
;
comment on table CCY_ACCOUNT_ADD_CASH_RECORD
  is '现金账户加款审核登记表';
comment on column CCY_ACCOUNT_ADD_CASH_RECORD.ID
  is '主键';
comment on column CCY_ACCOUNT_ADD_CASH_RECORD.MERCHANT_ID
  is '商户ID';
comment on column CCY_ACCOUNT_ADD_CASH_RECORD.MERCHANT_NAME
  is '商户名称';
comment on column CCY_ACCOUNT_ADD_CASH_RECORD.OPERATOR_NAME
  is '操作员名称';
comment on column CCY_ACCOUNT_ADD_CASH_RECORD.APPLY_TIME
  is '申请日期';
comment on column CCY_ACCOUNT_ADD_CASH_RECORD.VERIFY_TIME
  is '审核日期';
comment on column CCY_ACCOUNT_ADD_CASH_RECORD.AMT
  is '加款金额';
comment on column CCY_ACCOUNT_ADD_CASH_RECORD.VERIFY_STATUS
  is '审核状态
1.待审核
2.审核成功
3.审核失败';
comment on column CCY_ACCOUNT_ADD_CASH_RECORD.ACCOUNT_ID
  is '账户ID';
comment on column CCY_ACCOUNT_ADD_CASH_RECORD.RMK
  is '备注';
alter table CCY_ACCOUNT_ADD_CASH_RECORD
  add primary key (ID);
  
create sequence ACCOUNT_SETTLEMENT_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 900
increment by 20
cache 20;

create sequence AC_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 2100
increment by 20
cache 20;

create sequence AC_OPERATION_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 3700
increment by 20
cache 20;

create sequence AC_TYPE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1300
increment by 20
cache 20;

create sequence AC_UNIT_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 500
increment by 20
cache 20;

create sequence AC_VALUE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 500
increment by 20
cache 20;

create sequence APPLYADDACCOUNT_SEQ
minvalue 1
maxvalue 9999999999999999999999
start with 21
increment by 1
cache 20;

create sequence CARD_AC_BALANCE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 100
increment by 20
cache 20;

create sequence CCY_AC_BALANCE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 25400
increment by 20
cache 20;

create sequence CCY_AC_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 500
increment by 20
cache 20;

create sequence CONNECTION_INFOMATION_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence CUSTOMER_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence FUNCTION_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence IDENTITY_AC_ROLE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 4160
increment by 20
cache 20;

create sequence IDENTITY_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 268
increment by 1
cache 20;

create sequence IDENTITY_ROLE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 161
increment by 1
cache 20;

create sequence IDENTITY_STATUS_TRANSFER_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 900
increment by 20
cache 20;

create sequence MENU_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 225
increment by 1
cache 20;

create sequence ORG_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 91
increment by 1
cache 10;

create sequence PAGE_RESOURCE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 221
increment by 1
cache 20;

create sequence PERSON_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 246
increment by 1
cache 20;

create sequence PRIVILEGE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 2920
increment by 20
cache 20;

create sequence PROFIT_IMPUTATION_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 2100
increment by 20
cache 20;

create sequence RESOURCE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 241
increment by 1
cache 20;

create sequence ROLE_FUNCTION_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

create sequence ROLE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 101
increment by 1
cache 20;

create sequence ROLE_MENU_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 304
increment by 1
cache 20;

create sequence ROLE_PRIVILEGE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 3540
increment by 20
cache 20;

create sequence SECURITY_CREDENTIAL_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 226
increment by 1
cache 20;

create sequence SECURITY_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 900
increment by 20
cache 20;

create sequence SECURITY_PROPERTY_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 900
increment by 20
cache 20;

create sequence SECURITY_RULE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999
start with 22
increment by 1
cache 20;

create sequence SECURITY_STATUS_TRANSFER_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 900
increment by 20
cache 20;

create sequence SECURITY_TYPE_SEQ
minvalue 1
maxvalue 99999999999999999999999
start with 63
increment by 1
cache 20;

create sequence SS_SEQ_MERCHANT
minvalue 1
maxvalue 9999999999999999999999999999
start with 500
increment by 20
cache 20;

create sequence SS_SEQ_TASK
minvalue 1
maxvalue 9999999999999999999999999999
start with 900
increment by 20
cache 20;

create sequence SS_SEQ_USER
minvalue 1
maxvalue 9999999999999999999999999999
start with 500
increment by 20
cache 20;

create sequence TRANSACTION_HIS_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 13380
increment by 20
cache 20;

-- Create sequence 
create sequence AC_ADD_CASH_R_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1760
increment by 20
cache 20;