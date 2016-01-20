
-- Create table
create table ACCOUNT_REPORT
(
  ACCOUNT_REPORT_ID            NUMBER not null,
  ACCOUNT_ID                   NUMBER,
  IDENTITY_ID                  NUMBER,
  IDENTITY_NAME                VARCHAR2(64),
  TRANSACTION_TYPE             NUMBER,
  PREVIOUS_BALANCE             NUMBER,
  PERIOD_PLUS_SECTION          NUMBER,
  CURRENT_EXPENDITURE          NUMBER,
  PERIOD_BALANCE               NUMBER,
  BEGIN_TIME                   DATE,
  END_TIME                     DATE,
  ACCOUNT_EXPENSES_NUM         NUMBER,
  ACCOUNT_ADD_NUM              NUMBER,
  ACCOUNT_TYPE_ID              NUMBER not null,
  ACCOUNT_TYPE_NAME            VARCHAR2(50),
  IDENTITY_TYPE                VARCHAR2(10),
  IDENTITY_TYPE_NAME           VARCHAR2(20),
  PERIOD_UNAVAILABLE_BALANCE   NUMBER,
  PREVIOUS_UNAVAILABLE_BALANCE NUMBER
);
-- Add comments to the columns 
comment on column ACCOUNT_REPORT.ACCOUNT_REPORT_ID
  is 'ID';
comment on column ACCOUNT_REPORT.ACCOUNT_ID
  is '账户ID';
comment on column ACCOUNT_REPORT.IDENTITY_ID
  is '身份编码';
comment on column ACCOUNT_REPORT.IDENTITY_NAME
  is '身份名称';
comment on column ACCOUNT_REPORT.TRANSACTION_TYPE
  is '业务类型';
comment on column ACCOUNT_REPORT.PREVIOUS_BALANCE
  is '上期余额';
comment on column ACCOUNT_REPORT.PERIOD_PLUS_SECTION
  is '本期加款';
comment on column ACCOUNT_REPORT.CURRENT_EXPENDITURE
  is '本期支出';
comment on column ACCOUNT_REPORT.PERIOD_BALANCE
  is '本期余额';
comment on column ACCOUNT_REPORT.BEGIN_TIME
  is '开始时间';
comment on column ACCOUNT_REPORT.END_TIME
  is '结束时间';
comment on column ACCOUNT_REPORT.ACCOUNT_EXPENSES_NUM
  is '支出数目';
comment on column ACCOUNT_REPORT.ACCOUNT_ADD_NUM
  is '加款数目';
comment on column ACCOUNT_REPORT.ACCOUNT_TYPE_ID
  is '账户类型ID';
comment on column ACCOUNT_REPORT.ACCOUNT_TYPE_NAME
  is '账户类型名称';
comment on column ACCOUNT_REPORT.IDENTITY_TYPE
  is '身份类型';
comment on column ACCOUNT_REPORT.IDENTITY_TYPE_NAME
  is '身份类型名称';
comment on column ACCOUNT_REPORT.PERIOD_UNAVAILABLE_BALANCE
  is '本期不可用余额';
comment on column ACCOUNT_REPORT.PREVIOUS_UNAVAILABLE_BALANCE
  is '上期期不可用余额';
-- Create/Recreate primary, unique and foreign key constraints 
alter table ACCOUNT_REPORT
  add primary key (ACCOUNT_REPORT_ID);



create table ACCOUNT_REPORT_RECORD
(
  REPORT_RECORD_ID NUMBER not null,
  BEGIN_DATE       DATE,
  END_DATE         DATE,
  UPDATE_DATE      DATE,
  REPORT_STATUS    VARCHAR2(2),
  REPORT_DESCRIBE  VARCHAR2(64)
);
comment on column ACCOUNT_REPORT_RECORD.REPORT_RECORD_ID
  is '账户记录id';
comment on column ACCOUNT_REPORT_RECORD.BEGIN_DATE
  is '开始';
comment on column ACCOUNT_REPORT_RECORD.END_DATE
  is '结束';
comment on column ACCOUNT_REPORT_RECORD.UPDATE_DATE
  is '更新';
comment on column ACCOUNT_REPORT_RECORD.REPORT_STATUS
  is '状态 0,1,2';
comment on column ACCOUNT_REPORT_RECORD.REPORT_DESCRIBE
  is '备注';
alter table ACCOUNT_REPORT_RECORD
  add primary key (REPORT_RECORD_ID);

create table AGENT_QUERY_FAKE_RULE
(
  ID            NUMBER not null,
  MERCHANT_ID   NUMBER,
  INTERVAL_TIME NUMBER,
  INTERVAL_UNIT VARCHAR2(5)
);
comment on column AGENT_QUERY_FAKE_RULE.ID
  is '主键';
comment on column AGENT_QUERY_FAKE_RULE.MERCHANT_ID
  is '下游商户号';
comment on column AGENT_QUERY_FAKE_RULE.INTERVAL_TIME
  is '时间间隔量';
comment on column AGENT_QUERY_FAKE_RULE.INTERVAL_UNIT
  is '时间间隔单位';


create table AGENT_TRANSACTION_REPORT
(
  AGENT_TRANSACTION_REPORT_ID NUMBER not null,
  MERCHANT_ID                 NUMBER,
  MERCHANT_NAME               VARCHAR2(32),
  MERCHANT_TYPE               VARCHAR2(10),
  MERCHANT_TYPE_NAME          VARCHAR2(20),
  BEGIN_TIME                  DATE,
  END_TIME                    DATE,
  AGENT_TRANSACTION_NUM       NUMBER,
  REPORTS_STATUS              VARCHAR2(2),
  REPORTS_STATUS_NAME         VARCHAR2(10),
  PRODUCT_ID                  NUMBER,
  PRODUCT_NAME                VARCHAR2(64),
  CARRIER_NO                  VARCHAR2(20),
  CARRIER_NAME                VARCHAR2(64),
  PROVINCE                    VARCHAR2(20),
  PROVINCE_NAME               VARCHAR2(32),
  CITY                        VARCHAR2(20),
  CITY_NAME                   VARCHAR2(32),
  TOTAL_PAR_VALUE             NUMBER(18,2),
  TOTAL_SALES_FEE             NUMBER(18,2),
  PAR_VALUE                   NUMBER(18,2)
);
comment on column AGENT_TRANSACTION_REPORT.AGENT_TRANSACTION_REPORT_ID
  is '代理商户交易报表';
comment on column AGENT_TRANSACTION_REPORT.MERCHANT_ID
  is '商户';
comment on column AGENT_TRANSACTION_REPORT.MERCHANT_NAME
  is '商户名称';
comment on column AGENT_TRANSACTION_REPORT.MERCHANT_TYPE
  is '商户类型';
comment on column AGENT_TRANSACTION_REPORT.MERCHANT_TYPE_NAME
  is '商户类型名称';
comment on column AGENT_TRANSACTION_REPORT.BEGIN_TIME
  is '开始时间';
comment on column AGENT_TRANSACTION_REPORT.END_TIME
  is '结束时间';
comment on column AGENT_TRANSACTION_REPORT.AGENT_TRANSACTION_NUM
  is '总交易量';
comment on column AGENT_TRANSACTION_REPORT.REPORTS_STATUS
  is '报表订单状态';
comment on column AGENT_TRANSACTION_REPORT.REPORTS_STATUS_NAME
  is '报表订单状态名';
comment on column AGENT_TRANSACTION_REPORT.PRODUCT_ID
  is '产品';
comment on column AGENT_TRANSACTION_REPORT.PRODUCT_NAME
  is '产品名';
comment on column AGENT_TRANSACTION_REPORT.CARRIER_NO
  is '运营商';
comment on column AGENT_TRANSACTION_REPORT.CARRIER_NAME
  is '运营商名';
comment on column AGENT_TRANSACTION_REPORT.PROVINCE
  is '省份';
comment on column AGENT_TRANSACTION_REPORT.PROVINCE_NAME
  is '省份名';
comment on column AGENT_TRANSACTION_REPORT.CITY
  is '城市';
comment on column AGENT_TRANSACTION_REPORT.CITY_NAME
  is '城市名';
comment on column AGENT_TRANSACTION_REPORT.TOTAL_PAR_VALUE
  is '总面值';
comment on column AGENT_TRANSACTION_REPORT.TOTAL_SALES_FEE
  is '订单销售金额';
comment on column AGENT_TRANSACTION_REPORT.PAR_VALUE
  is '面值';
alter table AGENT_TRANSACTION_REPORT
  add primary key (AGENT_TRANSACTION_REPORT_ID);

create table ASSIGN_EXCLUDE
(
  ID                   NUMBER not null,
  BUSINESS_NO          VARCHAR2(32),
  MERCHANT_ID          NUMBER not null,
  MERCHANT_TYPE        VARCHAR2(10) not null,
  CARRIER_NO           VARCHAR2(10),
  PROVINCE_NO          VARCHAR2(5),
  CITY_NO              VARCHAR2(5),
  PRODUCT_NO           NUMBER not null,
  RULE_TYPE            NUMBER not null,
  OBJECT_MERCHANT_ID   NUMBER not null,
  OBJECT_MERCHANT_TYPE VARCHAR2(10),
  MERCHANT_NAME        VARCHAR2(20),
  OBJECT_MERCHANT_NAME VARCHAR2(20),
  CARRIER_NAME         VARCHAR2(20),
  PROVINCE_NAME        VARCHAR2(20),
  CITY_NAME            VARCHAR2(20),
  PAR_VALUE            NUMBER,
  PRODUCT_NAME         VARCHAR2(64)
);
comment on column ASSIGN_EXCLUDE.ID
  is '主键';
comment on column ASSIGN_EXCLUDE.BUSINESS_NO
  is '业务编号';
comment on column ASSIGN_EXCLUDE.MERCHANT_ID
  is '商户号';
comment on column ASSIGN_EXCLUDE.MERCHANT_TYPE
  is '商户类型';
comment on column ASSIGN_EXCLUDE.CARRIER_NO
  is '运营商编号';
comment on column ASSIGN_EXCLUDE.PROVINCE_NO
  is '省份代码';
comment on column ASSIGN_EXCLUDE.CITY_NO
  is '城市ID';
comment on column ASSIGN_EXCLUDE.PRODUCT_NO
  is '产品编号';
comment on column ASSIGN_EXCLUDE.RULE_TYPE
  is '规则类型1,指定   2.排除';
comment on column ASSIGN_EXCLUDE.OBJECT_MERCHANT_ID
  is '被作用方商户号';
comment on column ASSIGN_EXCLUDE.OBJECT_MERCHANT_TYPE
  is '被作用方商户类型';
comment on column ASSIGN_EXCLUDE.MERCHANT_NAME
  is '商户名称';
comment on column ASSIGN_EXCLUDE.OBJECT_MERCHANT_NAME
  is '被作用方商户名称';
comment on column ASSIGN_EXCLUDE.CARRIER_NAME
  is '运营商名称';
comment on column ASSIGN_EXCLUDE.PROVINCE_NAME
  is '省份名称';
comment on column ASSIGN_EXCLUDE.CITY_NAME
  is '城市名称';
comment on column ASSIGN_EXCLUDE.PAR_VALUE
  is '面值';
comment on column ASSIGN_EXCLUDE.PRODUCT_NAME
  is '产品名称';
alter table ASSIGN_EXCLUDE
  add primary key (ID);


create table BATCH_ORDER_REQUEST_HANDLER
(
  ID                 NUMBER not null,
  PHONE_NO           VARCHAR2(11),
  ORDER_FEE          NUMBER(18,2),
  ORDER_REQUEST_TIME DATE default sysdate,
  UP_FILE            VARCHAR2(100),
  OPERATOR           VARCHAR2(100),
  ORDER_STATUS       NUMBER,
  REMARK             VARCHAR2(300),
  MERCHANT_ID        NUMBER,
  MERCHANT_ORDER_NO  VARCHAR2(32),
  ORDER_FINISH_TIME  DATE default sysdate,
  MERCHANT_NAME      VARCHAR2(32),
  ORDER_DELETE_TIME  DATE default sysdate
);
comment on table BATCH_ORDER_REQUEST_HANDLER
  is '批量手工补单';
comment on column BATCH_ORDER_REQUEST_HANDLER.ID
  is 'ID，主键';
comment on column BATCH_ORDER_REQUEST_HANDLER.PHONE_NO
  is '手机号码';
comment on column BATCH_ORDER_REQUEST_HANDLER.ORDER_FEE
  is '金额';
comment on column BATCH_ORDER_REQUEST_HANDLER.ORDER_REQUEST_TIME
  is '创建时间，默认sysdate，上传文件时间';
comment on column BATCH_ORDER_REQUEST_HANDLER.UP_FILE
  is '上传文件名';
comment on column BATCH_ORDER_REQUEST_HANDLER.OPERATOR
  is '操作员';
comment on column BATCH_ORDER_REQUEST_HANDLER.ORDER_STATUS
  is '状态';
comment on column BATCH_ORDER_REQUEST_HANDLER.REMARK
  is '备注';
comment on column BATCH_ORDER_REQUEST_HANDLER.MERCHANT_ID
  is '商户号';
comment on column BATCH_ORDER_REQUEST_HANDLER.MERCHANT_ORDER_NO
  is '商户订单号';
comment on column BATCH_ORDER_REQUEST_HANDLER.ORDER_FINISH_TIME
  is '补单时间，默认sysdate，下单时间';
comment on column BATCH_ORDER_REQUEST_HANDLER.MERCHANT_NAME
  is '商户名';
comment on column BATCH_ORDER_REQUEST_HANDLER.ORDER_DELETE_TIME
  is '删除时间';


create table CALC_QUALITY_RULE
(
  ID             NUMBER not null,
  FACTOR         NUMBER,
  ORDER_NUM_LOW  NUMBER,
  ORDER_NUM_HIGH NUMBER
);
comment on table CALC_QUALITY_RULE
  is '计算质量方程
质量分= tanh(成功率/修正值) * 100
 ps:[1~100之间]';
comment on column CALC_QUALITY_RULE.ID
  is '主键';
comment on column CALC_QUALITY_RULE.FACTOR
  is '修正值（1~10）';
comment on column CALC_QUALITY_RULE.ORDER_NUM_LOW
  is '订单最小范围';
comment on column CALC_QUALITY_RULE.ORDER_NUM_HIGH
  is '订单最大范围';
alter table CALC_QUALITY_RULE
  add primary key (ID);


create table DELIVERY_KEY_RULE
(
  MERCHANT_ID NUMBER not null,
  RULE        VARCHAR2(50)
);
comment on table DELIVERY_KEY_RULE
  is '发货订单号生成配置表';
comment on column DELIVERY_KEY_RULE.MERCHANT_ID
  is '上游商户号';
comment on column DELIVERY_KEY_RULE.RULE
  is '规则';
alter table DELIVERY_KEY_RULE
  add primary key (MERCHANT_ID);


create table DELIVERY_QUERY_STATUS_TRANSFER
(
  ID               NUMBER not null,
  ACTION_NAME      VARCHAR2(64),
  OLD_QUERY_STATUS NUMBER,
  NEW_QUERY_STATUS NUMBER
);
comment on column DELIVERY_QUERY_STATUS_TRANSFER.ID
  is 'ID';
comment on column DELIVERY_QUERY_STATUS_TRANSFER.ACTION_NAME
  is '动作名称';
comment on column DELIVERY_QUERY_STATUS_TRANSFER.OLD_QUERY_STATUS
  is '查询标示修改前状态';
comment on column DELIVERY_QUERY_STATUS_TRANSFER.NEW_QUERY_STATUS
  is '查询标示修改后状态';
alter table DELIVERY_QUERY_STATUS_TRANSFER
  add primary key (ID);

create table DELIVERY_STATUS_TRANSFER
(
  ID                  NUMBER not null,
  ACTION_NAME         VARCHAR2(64),
  OLD_DELIVERY_STATUS NUMBER,
  NEW_DELIVERY_STATUS NUMBER
);
comment on column DELIVERY_STATUS_TRANSFER.ID
  is 'ID';
comment on column DELIVERY_STATUS_TRANSFER.ACTION_NAME
  is '动作名称';
comment on column DELIVERY_STATUS_TRANSFER.OLD_DELIVERY_STATUS
  is '发货状态修改前状态';
comment on column DELIVERY_STATUS_TRANSFER.NEW_DELIVERY_STATUS
  is '发货状态修改后状态';
alter table DELIVERY_STATUS_TRANSFER
  add primary key (ID);

create table DOWN_QUERY_HISTORY
(
  ID          NUMBER not null,
  MERCHANT_ID NUMBER,
  TIMES       NUMBER,
  BEGIN_TIME  DATE,
  END_TIME    DATE
);
comment on column DOWN_QUERY_HISTORY.ID
  is '主键';
comment on column DOWN_QUERY_HISTORY.MERCHANT_ID
  is '商户号';
comment on column DOWN_QUERY_HISTORY.TIMES
  is '时间段内的查询次数';
comment on column DOWN_QUERY_HISTORY.BEGIN_TIME
  is '开始时间';
comment on column DOWN_QUERY_HISTORY.END_TIME
  is '结束时间';
alter table DOWN_QUERY_HISTORY
  add primary key (ID);

create table DOWN_QUERY_TACTICS
(
  ID    NUMBER not null,
  COUNT NUMBER
);
comment on table DOWN_QUERY_TACTICS
  is '下游查询策略表';
comment on column DOWN_QUERY_TACTICS.ID
  is '主键';
comment on column DOWN_QUERY_TACTICS.COUNT
  is '次数';
alter table DOWN_QUERY_TACTICS
  add primary key (ID);

create table MERCHANT_LEVEL
(
  ID                    NUMBER not null,
  MERCHANT_LEVEL        NUMBER not null,
  ORDER_PERCENTAGE_LOW  NUMBER not null,
  ORDER_PERCENTAGE_HIGH NUMBER not null,
  MERCHANT_NAME         VARCHAR2(32)
);
alter table MERCHANT_LEVEL
  add primary key (ID);


create table MERCHANT_PRE_SUCCESS_RULE
(
  MERCHANT_ID   NUMBER not null,
  INTERVAL_TIME NUMBER,
  INTERVAL_UNIT VARCHAR2(10),
  FLAG          NUMBER,
  CREATOR       VARCHAR2(10),
  CREATE_TIME   DATE
);
comment on table MERCHANT_PRE_SUCCESS_RULE
  is '商户预成功配置';
comment on column MERCHANT_PRE_SUCCESS_RULE.MERCHANT_ID
  is '下游商户号';
comment on column MERCHANT_PRE_SUCCESS_RULE.INTERVAL_TIME
  is '预成功时间间隔量';
comment on column MERCHANT_PRE_SUCCESS_RULE.INTERVAL_UNIT
  is '时间间隔单位 时、分、秒、天。。。';
comment on column MERCHANT_PRE_SUCCESS_RULE.FLAG
  is '开关标示';
comment on column MERCHANT_PRE_SUCCESS_RULE.CREATOR
  is '创建人';
comment on column MERCHANT_PRE_SUCCESS_RULE.CREATE_TIME
  is '创建时间';
alter table MERCHANT_PRE_SUCCESS_RULE
  add primary key (MERCHANT_ID);

create table MERCHANT_REQUEST
(
  ID                   NUMBER not null,
  MERCHANT_ID          NUMBER,
  INTERFACE_TYPE       VARCHAR2(32),
  INTERVAL_TIME        NUMBER,
  INTERVAL_UNIT        VARCHAR2(5),
  TIME_DIFFERENCE_LOW  NUMBER,
  TIME_DIFFERENCE_HIGH NUMBER
);
comment on table MERCHANT_REQUEST
  is '发货、查询、通知（定义多久以后做）';
comment on column MERCHANT_REQUEST.ID
  is '主键';
comment on column MERCHANT_REQUEST.MERCHANT_ID
  is '商户号';
comment on column MERCHANT_REQUEST.INTERFACE_TYPE
  is '接口类型';
comment on column MERCHANT_REQUEST.INTERVAL_TIME
  is '间隔时间';
comment on column MERCHANT_REQUEST.INTERVAL_UNIT
  is '时间单位';
comment on column MERCHANT_REQUEST.TIME_DIFFERENCE_LOW
  is '时差最小(秒)';
comment on column MERCHANT_REQUEST.TIME_DIFFERENCE_HIGH
  is '时差最大(秒)';
alter table MERCHANT_REQUEST
  add primary key (ID);


create table MERCHANT_RESPONSE
(
  ID                   NUMBER not null,
  MERCHANT_ID          NUMBER,
  INTERFACE_TYPE       VARCHAR2(32),
  ERROR_CODE           VARCHAR2(32),
  MERCHANT_STATUS      VARCHAR2(32),
  MERCHANT_STATUS_INFO VARCHAR2(50),
  STATUS               NUMBER
);
comment on column MERCHANT_RESPONSE.ID
  is '主键';
comment on column MERCHANT_RESPONSE.MERCHANT_ID
  is '商户号';
comment on column MERCHANT_RESPONSE.INTERFACE_TYPE
  is '接口类型';
comment on column MERCHANT_RESPONSE.ERROR_CODE
  is '商户服务结果码';
comment on column MERCHANT_RESPONSE.MERCHANT_STATUS
  is '商户订单状态';
comment on column MERCHANT_RESPONSE.MERCHANT_STATUS_INFO
  is '商户返回码含义';
comment on column MERCHANT_RESPONSE.STATUS
  is '系统状态';
alter table MERCHANT_RESPONSE
  add primary key (ID);


create table NOTIFY_STATUS_TRANSFER
(
  ID                NUMBER not null,
  ACTION_NAME       VARCHAR2(64),
  OLD_NOTIFY_STATUS NUMBER,
  NEW_NOTIFY_STATUS NUMBER
);
comment on column NOTIFY_STATUS_TRANSFER.ID
  is 'ID';
comment on column NOTIFY_STATUS_TRANSFER.ACTION_NAME
  is '动作名称';
comment on column NOTIFY_STATUS_TRANSFER.OLD_NOTIFY_STATUS
  is '通知状态修改前状态';
comment on column NOTIFY_STATUS_TRANSFER.NEW_NOTIFY_STATUS
  is '通知状态修改后状态';
alter table NOTIFY_STATUS_TRANSFER
  add primary key (ID);

create table ORDER_OPERATION_HISTORY
(
  ID            NUMBER not null,
  ORDER_NO      NUMBER,
  OPERATOR      VARCHAR2(20),
  OPERATOR_TIME DATE,
  ACTION        VARCHAR2(50),
  OLD_STATUS    NUMBER,
  NEW_STATUS    NUMBER
);
comment on table ORDER_OPERATION_HISTORY
  is '对订单进行操作的时候记录';
comment on column ORDER_OPERATION_HISTORY.ID
  is '主键';
comment on column ORDER_OPERATION_HISTORY.ORDER_NO
  is '订单号';
comment on column ORDER_OPERATION_HISTORY.OPERATOR
  is '创建者';
comment on column ORDER_OPERATION_HISTORY.OPERATOR_TIME
  is '创建时间';
comment on column ORDER_OPERATION_HISTORY.ACTION
  is '具体动作
1.暂停
2.撤销
3.人工审核成功
4 人工审核失败
5.人工处理下游通知
6.人工处理上游查询';
comment on column ORDER_OPERATION_HISTORY.OLD_STATUS
  is '修改前状态';
comment on column ORDER_OPERATION_HISTORY.NEW_STATUS
  is '修改后状态';
alter table ORDER_OPERATION_HISTORY
  add primary key (ID);


create table ORDER_STATUS_TRANSFER
(
  ID               NUMBER not null,
  ACTION_NAME      VARCHAR2(64),
  OLD_ORDER_STATUS NUMBER,
  NEW_ORDER_STATUS NUMBER
);
comment on column ORDER_STATUS_TRANSFER.ID
  is 'ID';
comment on column ORDER_STATUS_TRANSFER.ACTION_NAME
  is '动作名称';
comment on column ORDER_STATUS_TRANSFER.OLD_ORDER_STATUS
  is '订单修改前状态';
comment on column ORDER_STATUS_TRANSFER.NEW_ORDER_STATUS
  is '订单修改后状态';
alter table ORDER_STATUS_TRANSFER
  add primary key (ID);



create table PROFIT_IMPUTATION
(
  PROFIT_IMPUTATION_ID  NUMBER not null,
  PROFIT_ACCOUNT_ID     NUMBER,
  PROFIT_ACCOUNT_TYPEID NUMBER,
  MIDDLE_ACCOUNT_ID     NUMBER,
  MIDDLE_ACCOUNT_TYPEID NUMBER,
  MERCHANT_ID           NUMBER,
  MERCHANT_NAME         VARCHAR2(64),
  IMPUTATION_PROFIT     NUMBER,
  ACCOUNT_BALANCE       NUMBER,
  IMPUTATION_BEGIN_DATE DATE,
  IMPUTATION_END_DATE   DATE,
  IMPUTATION_STATUS     NUMBER
);
comment on column PROFIT_IMPUTATION.PROFIT_IMPUTATION_ID
  is '归集id';
comment on column PROFIT_IMPUTATION.PROFIT_ACCOUNT_ID
  is '系统商户利润账户';
comment on column PROFIT_IMPUTATION.MIDDLE_ACCOUNT_ID
  is '系统商户中间利润账户';
comment on column PROFIT_IMPUTATION.MERCHANT_ID
  is '商户';
comment on column PROFIT_IMPUTATION.MERCHANT_NAME
  is '商户名称';
comment on column PROFIT_IMPUTATION.IMPUTATION_PROFIT
  is '归集利润';
comment on column PROFIT_IMPUTATION.ACCOUNT_BALANCE
  is '系统商户利润账户余额';
comment on column PROFIT_IMPUTATION.IMPUTATION_BEGIN_DATE
  is '开始归集';
comment on column PROFIT_IMPUTATION.IMPUTATION_END_DATE
  is '结束归集';
comment on column PROFIT_IMPUTATION.IMPUTATION_STATUS
  is '归集状态';
alter table PROFIT_IMPUTATION
  add primary key (PROFIT_IMPUTATION_ID);


create table PROFIT_IMPUTATION_RECORD
(
  IMPUTATION_RECORD_ID NUMBER not null,
  RECORD_BEGIN_DATE    DATE,
  RECORD_END_DATE      DATE,
  RECORD_STATUS        NUMBER,
  RECORD_UPDATE_DATE   DATE,
  RECORD__DESCRIBE     VARCHAR2(64)
);
alter table PROFIT_IMPUTATION_RECORD
  add primary key (IMPUTATION_RECORD_ID);


create table PROFIT_REPORT
(
  PROFIT_REPORT_ID   NUMBER not null,
  MERCHANT_ID        NUMBER,
  MERCHANT_NAME      VARCHAR2(50),
  SUCCESS_FACE       NUMBER(18,2),
  COST_FEE           NUMBER(18,2),
  ORDER_SALES_FEE    NUMBER(18,2),
  PROFIT             NUMBER,
  BEGIN_TIME         DATE,
  END_TIME           DATE,
  PROFIT_NUM         NUMBER,
  PAR_VALUE          NUMBER(18,2),
  PROVINCE           VARCHAR2(10),
  PROVINCE_NAME      VARCHAR2(64),
  CITY               VARCHAR2(20),
  CITY_NAME          VARCHAR2(20),
  CARRIER_NO         VARCHAR2(20),
  CARRIER_NAME       VARCHAR2(32),
  MERCHANT_TYPE      VARCHAR2(10),
  MERCHANT_TYPE_NAME VARCHAR2(20),
  TOTAL_PAR_VALUE    NUMBER(18,2)
);
comment on column PROFIT_REPORT.PROFIT_REPORT_ID
  is 'ID';
comment on column PROFIT_REPORT.MERCHANT_ID
  is '商户ID';
comment on column PROFIT_REPORT.MERCHANT_NAME
  is '商户名称';
comment on column PROFIT_REPORT.SUCCESS_FACE
  is '成功金额';
comment on column PROFIT_REPORT.COST_FEE
  is '成本金额';
comment on column PROFIT_REPORT.ORDER_SALES_FEE
  is '销售金额';
comment on column PROFIT_REPORT.PROFIT
  is '利润';
comment on column PROFIT_REPORT.BEGIN_TIME
  is '开始时间';
comment on column PROFIT_REPORT.END_TIME
  is '结束时间';
comment on column PROFIT_REPORT.PROFIT_NUM
  is '利润统计条数';
comment on column PROFIT_REPORT.PAR_VALUE
  is '面额';
comment on column PROFIT_REPORT.PROVINCE
  is '省';
comment on column PROFIT_REPORT.PROVINCE_NAME
  is '省名称';
comment on column PROFIT_REPORT.CITY
  is '城市';
comment on column PROFIT_REPORT.CITY_NAME
  is '城市名称';
comment on column PROFIT_REPORT.CARRIER_NO
  is '运营商编号';
comment on column PROFIT_REPORT.CARRIER_NAME
  is '运营商';
comment on column PROFIT_REPORT.MERCHANT_TYPE
  is '上下游标示';
comment on column PROFIT_REPORT.MERCHANT_TYPE_NAME
  is '商户类型名称';
comment on column PROFIT_REPORT.TOTAL_PAR_VALUE
  is '总面值';
alter table PROFIT_REPORT
  add primary key (PROFIT_REPORT_ID);


create table PROFIT_REPORT_RECORD
(
  REPORT_RECORD_ID NUMBER not null,
  BEGIN_DATE       DATE,
  END_DATE         DATE,
  UPDATE_DATE      DATE,
  REPORT_STATUS    VARCHAR2(2),
  REPORT_DESCRIBE  VARCHAR2(64),
  MERCHANT_TYPE    VARCHAR2(32)
);

comment on column PROFIT_REPORT_RECORD.REPORT_RECORD_ID
  is '利润记录表';
comment on column PROFIT_REPORT_RECORD.BEGIN_DATE
  is '开始';
comment on column PROFIT_REPORT_RECORD.END_DATE
  is '结束时间';
comment on column PROFIT_REPORT_RECORD.UPDATE_DATE
  is '更新时间';
comment on column PROFIT_REPORT_RECORD.REPORT_STATUS
  is '利润记录状态 0，1，2';
comment on column PROFIT_REPORT_RECORD.REPORT_DESCRIBE
  is '备注';
comment on column PROFIT_REPORT_RECORD.MERCHANT_TYPE
  is '商户类型';
alter table PROFIT_REPORT_RECORD
  add primary key (REPORT_RECORD_ID);


create table QUALITY_WEIGHT_RULE
(
  ID             NUMBER not null,
  SPEED_WEIGHT   NUMBER,
  SUCCESS_WEIGHT NUMBER
);
comment on table QUALITY_WEIGHT_RULE
  is '质量分中 速度与成功率的比重配置';
comment on column QUALITY_WEIGHT_RULE.ID
  is '主键';
comment on column QUALITY_WEIGHT_RULE.SPEED_WEIGHT
  is '速度比重';
comment on column QUALITY_WEIGHT_RULE.SUCCESS_WEIGHT
  is '成功率比重';
alter table QUALITY_WEIGHT_RULE
  add primary key (ID);


create table REBATE_PRODUCT
(
  ID                NUMBER not null,
  REBATE_PRODUCT_ID VARCHAR2(32),
  MERCHANT_ID       NUMBER,
  PRODUCT_ID        NUMBER
);
comment on column REBATE_PRODUCT.ID
  is '主键';
comment on column REBATE_PRODUCT.REBATE_PRODUCT_ID
  is '产品区间ID';
comment on column REBATE_PRODUCT.MERCHANT_ID
  is '商户ID';
comment on column REBATE_PRODUCT.PRODUCT_ID
  is '产品ID';


create table REBATE_RECORD
(
  ID                 NUMBER not null,
  MERCHANT_ID        NUMBER,
  REBATE_DATE        DATE default sysdate,
  REBATE_AMT         NUMBER,
  STATUS             VARCHAR2(32),
  REBATE_RULE_ID     NUMBER,
  REBATE_PRODUCT_ID  VARCHAR2(32),
  REBATE_MERCHANT_ID NUMBER,
  TRANSACTION_VOLUME NUMBER,
  CREATE_DATE        DATE,
  REBATE_TYPE        NUMBER,
  CREATE_USER        VARCHAR2(20),
  UPDATE_USER        VARCHAR2(20),
  UPDATE_DATE        DATE default sysdate,
  MERCHANT_TYPE      VARCHAR2(20)
);
comment on column REBATE_RECORD.ID
  is '主键';
comment on column REBATE_RECORD.MERCHANT_ID
  is '商户ID';
comment on column REBATE_RECORD.REBATE_DATE
  is '清算日期';
comment on column REBATE_RECORD.REBATE_AMT
  is '返佣金额';
comment on column REBATE_RECORD.STATUS
  is '状态,1.等待返佣 2.返佣成功  3.返佣失败';
comment on column REBATE_RECORD.REBATE_RULE_ID
  is '返佣配置ID';
comment on column REBATE_RECORD.REBATE_PRODUCT_ID
  is '返佣产品集合ID';
comment on column REBATE_RECORD.REBATE_MERCHANT_ID
  is '返佣对象商户ID';
comment on column REBATE_RECORD.TRANSACTION_VOLUME
  is '实际交易量';
comment on column REBATE_RECORD.CREATE_DATE
  is '创建时间';
comment on column REBATE_RECORD.REBATE_TYPE
  is '返佣类型  0.比例1.固定值';
comment on column REBATE_RECORD.CREATE_USER
  is '创建人';
comment on column REBATE_RECORD.UPDATE_USER
  is '更新人';
comment on column REBATE_RECORD.UPDATE_DATE
  is '更新时间';
comment on column REBATE_RECORD.MERCHANT_TYPE
  is '返佣商户类型';
alter table REBATE_RECORD
  add primary key (ID);

create table REBATE_RECORD_HISTORY
(
  ID                 NUMBER not null,
  MERCHANT_ID        NUMBER,
  REBATE_START_DATE  DATE,
  REBATE_END_DATE    DATE,
  REBATE_PRODUCT_ID  VARCHAR2(32),
  REBATE_MERCHANT_ID NUMBER,
  MERCHANT_TYPE      VARCHAR2(32),
  TRANSACTION_VOLUME NUMBER,
  REBATE_AMT         NUMBER(18,4),
  ACTUL_AMOUNT       NUMBER(18,4),
  BALANCE            NUMBER(18,4),
  REBATE_STATUS      VARCHAR2(32),
  BALANCE_STATUS     VARCHAR2(32),
  CREATE_USER        VARCHAR2(20),
  CREATE_DATE        DATE default sysdate,
  UPDATE_USER        VARCHAR2(20),
  UPDATE_DATE        DATE default sysdate,
  REBATE_TYPE        NUMBER,
  REMARK             VARCHAR2(500),
  STATUS             VARCHAR2(10) default 1
);
comment on column REBATE_RECORD_HISTORY.ID
  is '主键';
comment on column REBATE_RECORD_HISTORY.MERCHANT_ID
  is '商户ID';
comment on column REBATE_RECORD_HISTORY.REBATE_START_DATE
  is '开始日期';
comment on column REBATE_RECORD_HISTORY.REBATE_END_DATE
  is '结束日期';
comment on column REBATE_RECORD_HISTORY.REBATE_PRODUCT_ID
  is '返佣产品集合ID';
comment on column REBATE_RECORD_HISTORY.REBATE_MERCHANT_ID
  is '返佣商户ID';
comment on column REBATE_RECORD_HISTORY.MERCHANT_TYPE
  is '商户类型';
comment on column REBATE_RECORD_HISTORY.TRANSACTION_VOLUME
  is '实际交易量';
comment on column REBATE_RECORD_HISTORY.REBATE_AMT
  is '应返金额';
comment on column REBATE_RECORD_HISTORY.ACTUL_AMOUNT
  is '已返金额';
comment on column REBATE_RECORD_HISTORY.BALANCE
  is '余额';
comment on column REBATE_RECORD_HISTORY.REBATE_STATUS
  is '返佣状态,0已返，1未返';
comment on column REBATE_RECORD_HISTORY.BALANCE_STATUS
  is '资金状态,0暂无，1已收，2未收';
comment on column REBATE_RECORD_HISTORY.CREATE_USER
  is '创建人';
comment on column REBATE_RECORD_HISTORY.CREATE_DATE
  is '创建时间';
comment on column REBATE_RECORD_HISTORY.UPDATE_USER
  is '更新人';
comment on column REBATE_RECORD_HISTORY.UPDATE_DATE
  is '更新时间';
comment on column REBATE_RECORD_HISTORY.REBATE_TYPE
  is '返佣类型  0.比例1.固定值';
comment on column REBATE_RECORD_HISTORY.REMARK
  is '备注（存储组装返佣数据的id数组）';
comment on column REBATE_RECORD_HISTORY.STATUS
  is '状态 0：正常 2：删除';


create table REBATE_RULE
(
  REBATE_RULE_ID     NUMBER not null,
  REBATE_PRODUCT_ID  VARCHAR2(32),
  REBATE_TIME_TYPE   NUMBER,
  STATUS             VARCHAR2(10) default 1,
  MERCHANT_ID        NUMBER,
  REBATE_MERCHANT_ID NUMBER,
  CREATE_USER        VARCHAR2(32),
  CREATE_DATE        DATE default sysdate,
  REBATE_TYPE        NUMBER,
  MERCHANT_TYPE      VARCHAR2(20),
  UPDATE_USER        VARCHAR2(32),
  UPDATE_DATE        DATE default sysdate
);
comment on column REBATE_RULE.REBATE_RULE_ID
  is '返佣配置ID';
comment on column REBATE_RULE.REBATE_PRODUCT_ID
  is '返佣产品区间ID';
comment on column REBATE_RULE.REBATE_TIME_TYPE
  is '清算周期（固定每天）';
comment on column REBATE_RULE.STATUS
  is '状态 0：开启 1：关闭 2：删除';
comment on column REBATE_RULE.MERCHANT_ID
  is '商户ID';
comment on column REBATE_RULE.REBATE_MERCHANT_ID
  is '返佣商户ID';
comment on column REBATE_RULE.CREATE_USER
  is '创建人';
comment on column REBATE_RULE.CREATE_DATE
  is '创建时间';
comment on column REBATE_RULE.REBATE_TYPE
  is '返佣方式 0 返佣比 1 固定值';
comment on column REBATE_RULE.MERCHANT_TYPE
  is '返佣商户类型';
comment on column REBATE_RULE.UPDATE_USER
  is '更新人';
comment on column REBATE_RULE.UPDATE_DATE
  is '更新时间';


create table REBATE_TRADING_VOLUME
(
  REBATE_TRADING_VOLUME_ID NUMBER,
  REBATE_RULE_ID           NUMBER,
  TRADING_VOLUME_LOW       NUMBER,
  TRADING_VOLUME_HIGH      NUMBER,
  DISCOUNT                 NUMBER
);
comment on column REBATE_TRADING_VOLUME.REBATE_TRADING_VOLUME_ID
  is '交易量设置ID';
comment on column REBATE_TRADING_VOLUME.REBATE_RULE_ID
  is '返佣配置ID';
comment on column REBATE_TRADING_VOLUME.TRADING_VOLUME_LOW
  is '最低交易量';
comment on column REBATE_TRADING_VOLUME.TRADING_VOLUME_HIGH
  is '最高交易量';
comment on column REBATE_TRADING_VOLUME.DISCOUNT
  is '返佣比（%）/返佣金';


create table REPORT_METADATA
(
  REPORT_METADATA_ID    NUMBER not null,
  METADATA_TYPE         VARCHAR2(64),
  METADATA_FIELD        VARCHAR2(64),
  METADATA_FIELD_NAME   VARCHAR2(64),
  METADATA_TYPE_NAME    VARCHAR2(64),
  METADATA_ENTITY_FIELD VARCHAR2(64),
  METADATA_SIGN         VARCHAR2(10)
);
comment on column REPORT_METADATA.REPORT_METADATA_ID
  is '主键';
comment on column REPORT_METADATA.METADATA_TYPE
  is '元数据类型';
comment on column REPORT_METADATA.METADATA_FIELD
  is '元数据字段';
comment on column REPORT_METADATA.METADATA_FIELD_NAME
  is '元数据字段名称';
comment on column REPORT_METADATA.METADATA_TYPE_NAME
  is '元数据名称';
comment on column REPORT_METADATA.METADATA_ENTITY_FIELD
  is '对应实体字段';
comment on column REPORT_METADATA.METADATA_SIGN
  is '标识(可以为查询条件)';
alter table REPORT_METADATA
  add primary key (REPORT_METADATA_ID);


create table REPORT_PROPERTY
(
  REPORT_PROPERTY_ID         NUMBER not null,
  REPORT_TYPE_ID             NUMBER,
  REPORT_PROPERTY_NAME       VARCHAR2(500),
  REPORT_PROPERTY_TYPE       VARCHAR2(50),
  REPORT_PROPERTY_FIELD_NAME VARCHAR2(50),
  REPORT_PROPERTY_NUM        NUMBER
);
comment on table REPORT_PROPERTY
  is '报表属性';
comment on column REPORT_PROPERTY.REPORT_PROPERTY_ID
  is '报表属性id';
comment on column REPORT_PROPERTY.REPORT_TYPE_ID
  is '报表类型id';
comment on column REPORT_PROPERTY.REPORT_PROPERTY_NAME
  is '属性名称';
comment on column REPORT_PROPERTY.REPORT_PROPERTY_TYPE
  is '属性类型';
comment on column REPORT_PROPERTY.REPORT_PROPERTY_FIELD_NAME
  is '属性字段名称';
comment on column REPORT_PROPERTY.REPORT_PROPERTY_NUM
  is '序列';
alter table REPORT_PROPERTY
  add primary key (REPORT_PROPERTY_ID);

create table REPORT_TERM
(
  REPORT_TERM_ID     NUMBER not null,
  REPORT_TYPE_ID     NUMBER,
  REPORT_METADATA_ID NUMBER,
  REPORT_TERM_STATUS VARCHAR2(10)
);
comment on column REPORT_TERM.REPORT_TERM_ID
  is '报表组成条件id';
comment on column REPORT_TERM.REPORT_TYPE_ID
  is '报表类型';
comment on column REPORT_TERM.REPORT_METADATA_ID
  is '对应元数据，条件';
comment on column REPORT_TERM.REPORT_TERM_STATUS
  is '报表条件状态';
alter table REPORT_TERM
  add primary key (REPORT_TERM_ID);


create table REPORT_TYPE
(
  REPORT_TYPE_ID            NUMBER not null,
  REPORT_FILE_NAME          VARCHAR2(100),
  REPORT_TYPE_NAME          VARCHAR2(100),
  REPORT_METADATA_TYPE      VARCHAR2(64),
  REPORT_METADATA_TYPE_NAME VARCHAR2(64)
);
comment on column REPORT_TYPE.REPORT_TYPE_ID
  is '主键';
comment on column REPORT_TYPE.REPORT_FILE_NAME
  is '报表名称';
comment on column REPORT_TYPE.REPORT_TYPE_NAME
  is '报表类型';
comment on column REPORT_TYPE.REPORT_METADATA_TYPE
  is '元数据类型';
comment on column REPORT_TYPE.REPORT_METADATA_TYPE_NAME
  is '元数据类型名称';
alter table REPORT_TYPE
  add primary key (REPORT_TYPE_ID);

create table SPEED_CONTROL
(
  BUSINESS_NO       NUMBER not null,
  MERCHANT_ID       NUMBER,
  PERIOD_START_TIME DATE,
  PERIOD_END_TIME   DATE,
  CARRIER_NO        VARCHAR2(10),
  PROVINCE_NO       VARCHAR2(10),
  MANUAL_FLAG       VARCHAR2(1),
  MANUAL_V          NUMBER(5,2),
  CWND_BASE_V       NUMBER(5,2),
  CWND_V            NUMBER(5,2),
  CWND_MAX_V        NUMBER(5,2),
  CWND_AVG_V        NUMBER(5,2),
  SS_THREST_BASE_V  NUMBER(5,2),
  SS_THREST_V       NUMBER(5,2),
  SS_FACTOR_A       NUMBER(4,2),
  V_SETTING_TIME    DATE,
  STAT_PERIOD       NUMBER(6),
  FC_FATAL_KA_NUM   NUMBER(6),
  FC_PROCESSING_NUM NUMBER(6),
  SCAN_CLEAR_PERIOD NUMBER(6),
  MODIFY_TIME       DATE
);
comment on column SPEED_CONTROL.BUSINESS_NO
  is '业务编号';
comment on column SPEED_CONTROL.MERCHANT_ID
  is '商户号';
comment on column SPEED_CONTROL.PERIOD_START_TIME
  is '周期开始时间';
comment on column SPEED_CONTROL.PERIOD_END_TIME
  is '周期结束时间';
comment on column SPEED_CONTROL.CARRIER_NO
  is '运营商编码';
comment on column SPEED_CONTROL.PROVINCE_NO
  is '省代码';
comment on column SPEED_CONTROL.MANUAL_FLAG
  is '手控标志';
comment on column SPEED_CONTROL.MANUAL_V
  is '手控速率';
comment on column SPEED_CONTROL.CWND_BASE_V
  is '自控最小速率';
comment on column SPEED_CONTROL.CWND_V
  is '自控速率';
comment on column SPEED_CONTROL.CWND_MAX_V
  is '自控最大速率';
comment on column SPEED_CONTROL.CWND_AVG_V
  is '自控周期内平均速率';
comment on column SPEED_CONTROL.SS_THREST_BASE_V
  is '慢启动最小阀值';
comment on column SPEED_CONTROL.SS_THREST_V
  is '慢启动阀值';
comment on column SPEED_CONTROL.SS_FACTOR_A
  is '慢启动最大阀值';
comment on column SPEED_CONTROL.V_SETTING_TIME
  is '手控速率或者自控速率设置时间';
comment on column SPEED_CONTROL.STAT_PERIOD
  is '统计周期长度';
comment on column SPEED_CONTROL.FC_FATAL_KA_NUM
  is '统计周期内严重卡单笔数';
comment on column SPEED_CONTROL.FC_PROCESSING_NUM
  is '允许上游同事充值处理数(不含卡单)';
comment on column SPEED_CONTROL.SCAN_CLEAR_PERIOD
  is '扫描清零周期';
comment on column SPEED_CONTROL.MODIFY_TIME
  is '修改时间';
alter table SPEED_CONTROL
  add primary key (BUSINESS_NO);


create table SUPPLY_TRANSACTION_REPORT
(
  SUPPLY_TRANSACTION_REPORT_ID NUMBER not null,
  MERCHANT_ID                  NUMBER,
  MERCHANT_NAME                VARCHAR2(32),
  MERCHANT_TYPE                VARCHAR2(10),
  MERCHANT_TYPE_NAME           VARCHAR2(20),
  BEGIN_TIME                   DATE,
  END_TIME                     DATE,
  SUPPLY_TRANSACTION_NUM       NUMBER,
  REPORTS_STATUS               VARCHAR2(2),
  REPORTS_STATUS_NAME          VARCHAR2(10),
  PRODUCT_ID                   NUMBER,
  PRODUCT_NAME                 VARCHAR2(64),
  CARRIER_NO                   VARCHAR2(20),
  CARRIER_NAME                 VARCHAR2(64),
  PROVINCE                     VARCHAR2(20),
  PROVINCE_NAME                VARCHAR2(32),
  CITY                         VARCHAR2(20),
  CITY_NAME                    VARCHAR2(32),
  TOTAL_PAR_VALUE              NUMBER(18,2),
  TOTAL_SALES_FEE              NUMBER(18,2),
  PAR_VALUE                    NUMBER(18,2)
);
comment on column SUPPLY_TRANSACTION_REPORT.SUPPLY_TRANSACTION_REPORT_ID
  is '供货商交易量报表编号';
comment on column SUPPLY_TRANSACTION_REPORT.MERCHANT_ID
  is '商户';
comment on column SUPPLY_TRANSACTION_REPORT.MERCHANT_NAME
  is '商户名称';
comment on column SUPPLY_TRANSACTION_REPORT.MERCHANT_TYPE
  is '商户类型';
comment on column SUPPLY_TRANSACTION_REPORT.MERCHANT_TYPE_NAME
  is '商户类型名称';
comment on column SUPPLY_TRANSACTION_REPORT.BEGIN_TIME
  is '开始时间';
comment on column SUPPLY_TRANSACTION_REPORT.END_TIME
  is '结束时间';
comment on column SUPPLY_TRANSACTION_REPORT.SUPPLY_TRANSACTION_NUM
  is '总交易量';
comment on column SUPPLY_TRANSACTION_REPORT.REPORTS_STATUS
  is '报表订单状态';
comment on column SUPPLY_TRANSACTION_REPORT.REPORTS_STATUS_NAME
  is '报表订单状态名';
comment on column SUPPLY_TRANSACTION_REPORT.PRODUCT_ID
  is '产品';
comment on column SUPPLY_TRANSACTION_REPORT.PRODUCT_NAME
  is '产品名';
comment on column SUPPLY_TRANSACTION_REPORT.CARRIER_NO
  is '运营商';
comment on column SUPPLY_TRANSACTION_REPORT.CARRIER_NAME
  is '运营商名';
comment on column SUPPLY_TRANSACTION_REPORT.PROVINCE
  is '省份';
comment on column SUPPLY_TRANSACTION_REPORT.PROVINCE_NAME
  is '省份名';
comment on column SUPPLY_TRANSACTION_REPORT.CITY
  is '城市';
comment on column SUPPLY_TRANSACTION_REPORT.CITY_NAME
  is '城市名';
comment on column SUPPLY_TRANSACTION_REPORT.TOTAL_PAR_VALUE
  is '总面值';
comment on column SUPPLY_TRANSACTION_REPORT.TOTAL_SALES_FEE
  is '订单销售金额';
comment on column SUPPLY_TRANSACTION_REPORT.PAR_VALUE
  is '面值';
alter table SUPPLY_TRANSACTION_REPORT
  add primary key (SUPPLY_TRANSACTION_REPORT_ID);


create table TMALL_TSC
(
  TSC       VARCHAR2(50) not null,
  BRANDID   VARCHAR2(50) default '',
  BRANDNAME VARCHAR2(50) default '',
  FACEID    VARCHAR2(50) default '',
  FACEVALUE VARCHAR2(50) default '',
  AREAID    VARCHAR2(50) default '',
  AREANAME  VARCHAR2(50) default '',
  CITYID    VARCHAR2(50) default '',
  CITYNAME  VARCHAR2(50) default ''
);


create table TRANSACTION_REPORT
(
  TRANSACTION_REPORT_ID NUMBER not null,
  MERCHANT_ID           NUMBER,
  MERCHANT_NAME         VARCHAR2(50),
  BEGIN_TIME            DATE,
  END_TIME              DATE,
  TRANSACTION_NUM       NUMBER,
  REPORTS_STATUS        VARCHAR2(10),
  REPORTS_STATUS_NAME   VARCHAR2(10),
  PAR_VALUE             NUMBER(18,2),
  PROVINCE              VARCHAR2(10),
  PROVINCE_NAME         VARCHAR2(20),
  MERCHANT_TYPE         VARCHAR2(10),
  MERCHANT_TYPE_NAME    VARCHAR2(20),
  CITY                  VARCHAR2(20),
  CITY_NAME             VARCHAR2(20),
  CARRIER_NAME          VARCHAR2(32),
  CARRIER_NO            VARCHAR2(20),
  TOTAL_PAR_VALUE       NUMBER(18,2),
  TOTAL_SALES_FEE       NUMBER(18,2)
);
comment on column TRANSACTION_REPORT.TRANSACTION_REPORT_ID
  is 'ID';
comment on column TRANSACTION_REPORT.MERCHANT_ID
  is '商户ID';
comment on column TRANSACTION_REPORT.MERCHANT_NAME
  is '商户名称';
comment on column TRANSACTION_REPORT.BEGIN_TIME
  is '统计开始时间';
comment on column TRANSACTION_REPORT.END_TIME
  is '统计结束时间';
comment on column TRANSACTION_REPORT.TRANSACTION_NUM
  is '交易条数';
comment on column TRANSACTION_REPORT.REPORTS_STATUS
  is '交易状态';
comment on column TRANSACTION_REPORT.REPORTS_STATUS_NAME
  is '交易状态详情';
comment on column TRANSACTION_REPORT.PAR_VALUE
  is '面值';
comment on column TRANSACTION_REPORT.PROVINCE
  is '省';
comment on column TRANSACTION_REPORT.PROVINCE_NAME
  is '省名称';
comment on column TRANSACTION_REPORT.MERCHANT_TYPE
  is '商户类型';
comment on column TRANSACTION_REPORT.MERCHANT_TYPE_NAME
  is '商户类型名称';
comment on column TRANSACTION_REPORT.CITY
  is '城市';
comment on column TRANSACTION_REPORT.CITY_NAME
  is '城市名称';
comment on column TRANSACTION_REPORT.CARRIER_NAME
  is '运营商';
comment on column TRANSACTION_REPORT.CARRIER_NO
  is '运营商编号';
comment on column TRANSACTION_REPORT.TOTAL_PAR_VALUE
  is '总面值';
comment on column TRANSACTION_REPORT.TOTAL_SALES_FEE
  is '总销售额';


create table TRANSACTION_REPORT_RECORD
(
  REPORT_RECORD_ID NUMBER not null,
  BEGIN_DATE       DATE,
  END_DATE         DATE,
  UPDATE_DATE      DATE,
  REPORT_STATUS    VARCHAR2(2),
  REPORT_DESCRIBE  VARCHAR2(64),
  REPORT_TYPE       VARCHAR2(2),
  MERCHANT_TYPE    VARCHAR2(32)
);
-- Add comments to the columns 

comment on column TRANSACTION_REPORT_RECORD.REPORT_RECORD_ID
  is '报表记录id';
comment on column TRANSACTION_REPORT_RECORD.BEGIN_DATE
  is '开始时间';
comment on column TRANSACTION_REPORT_RECORD.END_DATE
  is '结束时间';
comment on column TRANSACTION_REPORT_RECORD.UPDATE_DATE
  is '更新时间';
comment on column TRANSACTION_REPORT_RECORD.REPORT_STATUS
  is '0（初始化），1,2';
comment on column TRANSACTION_REPORT_RECORD.REPORT_DESCRIBE
  is '备注';
comment on column TRANSACTION_REPORT_RECORD.REPORT_TYPE
  is '1（交易量报表）2（商户交易报表）';
comment on column TRANSACTION_REPORT_RECORD.MERCHANT_TYPE
  is '商户类型';
-- Create/Recreate primary, unique and foreign key constraints 
alter table TRANSACTION_REPORT_RECORD
  add primary key (REPORT_RECORD_ID);



create table UP_MONITOR
(
  ID                 NUMBER not null,
  BUSINESS_NO        NUMBER,
  MERCHANT_ID        NUMBER,
  PROVINCE_NO        VARCHAR2(10),
  CARRIER_NO         VARCHAR2(10),
  STAT_START_TIME    DATE,
  STAT_END_TIME      DATE,
  TOTAL_COUNT        NUMBER,
  FAIL_COUNT         NUMBER,
  SUCCESS_COUNT      NUMBER,
  ING_COUNT          NUMBER,
  ING_1MIN_COUNT     NUMBER,
  ING_5MIN_COUNT     NUMBER,
  ING_10MIN_COUNT    NUMBER,
  FINISH_1MIN_COUNT  NUMBER,
  FINISH_5MIN_COUNT  NUMBER,
  FINISH_10MIN_COUNT NUMBER,
  PRODUCT_ID         NUMBER
);
comment on column UP_MONITOR.ID
  is '主键';
comment on column UP_MONITOR.BUSINESS_NO
  is '业务编号';
comment on column UP_MONITOR.MERCHANT_ID
  is '商户号';
comment on column UP_MONITOR.PROVINCE_NO
  is '省份编码';
comment on column UP_MONITOR.CARRIER_NO
  is '运营商编号';
comment on column UP_MONITOR.STAT_START_TIME
  is '统计周期开始时间';
comment on column UP_MONITOR.STAT_END_TIME
  is '统计周期结束时间';
comment on column UP_MONITOR.TOTAL_COUNT
  is '总比数';
comment on column UP_MONITOR.FAIL_COUNT
  is '总失败数';
comment on column UP_MONITOR.SUCCESS_COUNT
  is '总成功数';
comment on column UP_MONITOR.ING_COUNT
  is '正在充值总数';
comment on column UP_MONITOR.ING_1MIN_COUNT
  is '1分钟内正在充值总数';
comment on column UP_MONITOR.ING_5MIN_COUNT
  is '5分钟内正在充值总数';
comment on column UP_MONITOR.ING_10MIN_COUNT
  is '10分钟内正在充值总数';
comment on column UP_MONITOR.FINISH_1MIN_COUNT
  is '1分钟内完成订单数';
comment on column UP_MONITOR.FINISH_5MIN_COUNT
  is '5分钟内完成订单数';
comment on column UP_MONITOR.FINISH_10MIN_COUNT
  is '10分钟内完成订单数';
comment on column UP_MONITOR.PRODUCT_ID
  is '产品ID';
alter table UP_MONITOR
  add primary key (ID);


create table UP_QUERY_TACTICS
(
  ID                   NUMBER not null,
  MERCHANT_ID          NUMBER,
  INTERVAL_TIME        NUMBER,
  INTERVAL_UNIT        VARCHAR2(5),
  TIME_DIFFERENCE_LOW  NUMBER,
  TIME_DIFFERENCE_HIGH NUMBER
);
comment on table UP_QUERY_TACTICS
  is '上游查询策略表';
comment on column UP_QUERY_TACTICS.ID
  is '主键';
comment on column UP_QUERY_TACTICS.MERCHANT_ID
  is '商户号';
comment on column UP_QUERY_TACTICS.INTERVAL_TIME
  is '时间间隔量';
comment on column UP_QUERY_TACTICS.INTERVAL_UNIT
  is '时间间隔单位';
comment on column UP_QUERY_TACTICS.TIME_DIFFERENCE_LOW
  is '时差最小值';
comment on column UP_QUERY_TACTICS.TIME_DIFFERENCE_HIGH
  is '时差最大值';
alter table UP_QUERY_TACTICS
  add primary key (ID);

create table UP_URL_RULE
(
  MERCHANT_ID   NUMBER not null,
  USER_NAME     VARCHAR2(50),
  PARTNER_ID    VARCHAR2(50),
  MERCHANT_NAME VARCHAR2(50)
);
comment on table UP_URL_RULE
  is '上游：
merchant_id  查询URL  发货URL  通知URL';
comment on column UP_URL_RULE.MERCHANT_ID
  is '上游商户号';
comment on column UP_URL_RULE.USER_NAME
  is '用户名';
comment on column UP_URL_RULE.PARTNER_ID
  is '商户ID帐号';
comment on column UP_URL_RULE.MERCHANT_NAME
  is '上游商户名称';
alter table UP_URL_RULE
  add primary key (MERCHANT_ID);

create table YC_DELIVERY
(
  delivery_id              NUMBER not null,
  order_no                 NUMBER,
  merchant_id              NUMBER,
  merchant_order_no        VARCHAR2(32),
  delivery_start_time      DATE,
  delivery_status          NUMBER(2) default 10 not null,
  product_id               NUMBER,
  product_face             NUMBER,
  product_sale_discount    NUMBER,
  success_fee              NUMBER,
  cost_discount            NUMBER,
  cost_fee                 NUMBER,
  delivery_finish_time     DATE,
  error_code               NUMBER,
  next_query_time          DATE,
  query_flag               NUMBER,
  query_msg                VARCHAR2(128),
  query_times              NUMBER,
  pre_delivery_time        DATE,
  version                  NUMBER,
  delivery_result          CLOB,
  supply_merchant_order_no VARCHAR2(32),
  user_code                VARCHAR2(20)
);
comment on column YC_DELIVERY.delivery_id
  is '发货编号';
comment on column YC_DELIVERY.order_no
  is '订单号';
comment on column YC_DELIVERY.merchant_id
  is '发货商户';
comment on column YC_DELIVERY.merchant_order_no
  is '商户订单号';
comment on column YC_DELIVERY.delivery_start_time
  is '开始发货时间';
comment on column YC_DELIVERY.delivery_status
  is '发货状态
10:无需发货 20:等待发货
30:采购中
1:采购失败
0:采购成功';
comment on column YC_DELIVERY.product_id
  is '商品编号';
comment on column YC_DELIVERY.product_face
  is '商品面额';
comment on column YC_DELIVERY.product_sale_discount
  is '销售折扣（下游）';
comment on column YC_DELIVERY.success_fee
  is '实际成功金额(面值)';
comment on column YC_DELIVERY.cost_discount
  is '采购折扣(上游)';
comment on column YC_DELIVERY.cost_fee
  is '成本金额';
comment on column YC_DELIVERY.delivery_finish_time
  is '发货结束时间';
comment on column YC_DELIVERY.error_code
  is '错误码';
comment on column YC_DELIVERY.next_query_time
  is '下次查询时间';
comment on column YC_DELIVERY.query_flag
  is '查询标志
0-无需查询
1-待查询
2-查询中
3-查询结束';
comment on column YC_DELIVERY.query_msg
  is '最后一次查询结果';
comment on column YC_DELIVERY.query_times
  is '查询次数';
comment on column YC_DELIVERY.pre_delivery_time
  is '预发货时间';
comment on column YC_DELIVERY.version
  is '版本号';
comment on column YC_DELIVERY.supply_merchant_order_no
  is '供货商订单编号';
comment on column YC_DELIVERY.user_code
  is '手机号码';
alter table YC_DELIVERY
  add primary key (DELIVERY_ID);


create table YC_NOTIFY
(
  NOTIFY_ID        NUMBER not null,
  ORDER_NO         NUMBER,
  NOTIFY_URL       VARCHAR2(256),
  CREATE_TIME      DATE default sysdate,
  START_TIME       DATE,
  END_TIME         DATE,
  NOTIFY_CNTR      NUMBER default 0,
  LIMITED_CNTR     NUMBER default 3,
  NOTIFY_STATUS    NUMBER default 10,
  ERROR_CODE       NUMBER,
  NEED_NOTIFY      NUMBER,
  NEXT_NOTIFY_TIME DATE,
  MERCHANT_ID      NUMBER,
  ORDER_TYPE       NUMBER default 0
);
comment on column YC_NOTIFY.NOTIFY_ID
  is '通知编号';
comment on column YC_NOTIFY.ORDER_NO
  is '订单编号';
comment on column YC_NOTIFY.NOTIFY_URL
  is '通知地址（由merchant提供）';
comment on column YC_NOTIFY.CREATE_TIME
  is '创建时间';
comment on column YC_NOTIFY.START_TIME
  is '开始通知时间';
comment on column YC_NOTIFY.END_TIME
  is '完成通知时间';
comment on column YC_NOTIFY.NOTIFY_CNTR
  is '已经通知次数';
comment on column YC_NOTIFY.LIMITED_CNTR
  is '限制通知次数';
comment on column YC_NOTIFY.NOTIFY_STATUS
  is '通知状态：
10:无需通知
20:等待通知 30:正在通知 1:通知失败 0:通知成功';
comment on column YC_NOTIFY.ERROR_CODE
  is '错误码';
comment on column YC_NOTIFY.NEED_NOTIFY
  is '是否需要通知
0:需要,
1：不需要';
comment on column YC_NOTIFY.NEXT_NOTIFY_TIME
  is '下次通知时间';
comment on column YC_NOTIFY.MERCHANT_ID
  is '商户号';
comment on column YC_NOTIFY.ORDER_TYPE
  is '订单类型 0：普通订单 1：淘宝订单';
alter table YC_NOTIFY
  add primary key (NOTIFY_ID);


create table YC_ORDER
(
  ORDER_NO               NUMBER not null,
  ORDER_STATUS           NUMBER default 10,
  NOTIFY_STATUS          NUMBER default 10,
  MANUAL_FLAG            NUMBER default 0,
  PRE_SUCCESS_STATUS     NUMBER default 0,
  MERCHANT_ID            NUMBER,
  MERCHANT_ORDER_NO      VARCHAR2(32),
  USER_CODE              VARCHAR2(20),
  ORDER_TITLE            VARCHAR2(256),
  ORDER_DESC             VARCHAR2(256),
  BUSINESS_TYPE          NUMBER,
  BUSINESS_NO            VARCHAR2(20),
  BUSINESS_CHANNEL       VARCHAR2(3),
  ORDER_FEE              NUMBER(18,2),
  ORDER_SALES_FEE        NUMBER(18,2),
  PRODUCT_NO             VARCHAR2(32),
  PRODUCT_FACE           NUMBER(18,2),
  PRODUCT_SALE_DISCOUNT  NUMBER(6,4),
  PRODUCT_NUM            NUMBER(10),
  ORDER_REQUEST_TIME     DATE default sysdate,
  ORDER_TIMEOUT          DATE,
  ORDER_FINISH_TIME      DATE,
  ORDER_PRE_SUCCESS_TIME DATE,
  EXT1                   VARCHAR2(20),
  EXT2                   VARCHAR2(20),
  EXT3                   VARCHAR2(20),
  EXT4                   VARCHAR2(20),
  CLOSE_REASON           VARCHAR2(20),
  ERROR_CODE             VARCHAR2(20),
  ORDER_REASON           VARCHAR2(20),
  MERCHANT_NAME          VARCHAR2(20),
  PRODUCT_ID             NUMBER,
  ORDER_SUCCESS_FEE      NUMBER(18,2),
  PRE_ORDER_BIND_TIME    DATE,
  BIND_TIMES             NUMBER,
  VERSION                NUMBER,
  LIMIT_BIND_TIMES       NUMBER
);
comment on column YC_ORDER.ORDER_NO
  is '订单号';
comment on column YC_ORDER.ORDER_STATUS
  is '订单状态
10:待付款
20:待发货（如果发货失败，修改订单状态为此状态）
30:发货中
0:成功
1:失败';
comment on column YC_ORDER.NOTIFY_STATUS
  is '通知状态：
10:无需通知
20:等待通知 30:正在通知 1:通知失败 0:通知成功';
comment on column YC_ORDER.MANUAL_FLAG
  is '手工处理标示
0:无需手工处理
1:已转手工处理';
comment on column YC_ORDER.PRE_SUCCESS_STATUS
  is '预成功状态
0:无需预处理成功
1:待处理为预成功
2:预处理为成功';
comment on column YC_ORDER.MERCHANT_ID
  is '商户号';
comment on column YC_ORDER.MERCHANT_ORDER_NO
  is '商户订单号';
comment on column YC_ORDER.USER_CODE
  is '用户编号:话费业务填写手机号';
comment on column YC_ORDER.ORDER_TITLE
  is '订单标题';
comment on column YC_ORDER.ORDER_DESC
  is '订单描述';
comment on column YC_ORDER.BUSINESS_TYPE
  is '业务类型0，话费 1，公用事业业务';
comment on column YC_ORDER.BUSINESS_NO
  is '业务编号';
comment on column YC_ORDER.BUSINESS_CHANNEL
  is '订单渠道';
comment on column YC_ORDER.ORDER_FEE
  is '订单总金额';
comment on column YC_ORDER.ORDER_SALES_FEE
  is '订单销售金额';
comment on column YC_ORDER.PRODUCT_NO
  is '商品编号';
comment on column YC_ORDER.PRODUCT_FACE
  is '商品面额';
comment on column YC_ORDER.PRODUCT_SALE_DISCOUNT
  is '商品销售折扣(下游的)';
comment on column YC_ORDER.PRODUCT_NUM
  is '商品数量';
comment on column YC_ORDER.ORDER_REQUEST_TIME
  is '订单创建时间';
comment on column YC_ORDER.ORDER_TIMEOUT
  is '订单超时时间';
comment on column YC_ORDER.ORDER_FINISH_TIME
  is '订单完成时间';
comment on column YC_ORDER.ORDER_PRE_SUCCESS_TIME
  is '订单预成功时间  ';
comment on column YC_ORDER.EXT1
  is '话费业务填运营商代码  事业编码';
comment on column YC_ORDER.EXT2
  is '话费业务填省代码';
comment on column YC_ORDER.EXT3
  is '话费业务填写城市代码';
comment on column YC_ORDER.EXT4
  is '话费业务代理商产品质量，生活缴费：通知次数';
comment on column YC_ORDER.CLOSE_REASON
  is '关闭原因
0:正常成功关闭
1:正常失败关闭
2:订单超时关闭
3:手工关闭';
comment on column YC_ORDER.ERROR_CODE
  is '失败原因';
comment on column YC_ORDER.ORDER_REASON
  is '下单原因
1.正常
2.系统运维人员内部下单....';
comment on column YC_ORDER.MERCHANT_NAME
  is '商户名称';
comment on column YC_ORDER.PRODUCT_ID
  is '商品ID';
comment on column YC_ORDER.ORDER_SUCCESS_FEE
  is '订单成功金额';
comment on column YC_ORDER.PRE_ORDER_BIND_TIME
  is '订单预绑定时间';
comment on column YC_ORDER.BIND_TIMES
  is '绑定次数';
comment on column YC_ORDER.VERSION
  is '版本号';
comment on column YC_ORDER.LIMIT_BIND_TIMES
  is '最大绑定次数';
alter table YC_ORDER
  add primary key (ORDER_NO);

  
  create table ORDER_APPLY_OPERATE_HISTORY
(
  id            NUMBER not null,
  operator_name VARCHAR2(20),
  create_date   DATE,
  order_no      NUMBER,
  action        VARCHAR2(200)
);
alter table ORDER_APPLY_OPERATE_HISTORY
  add primary key (ID);

create sequence ACCOUNT_REPORT_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 8280
increment by 1
cache 20;


create sequence AGENT_QUERY_FAKE_RULE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 500
increment by 1
cache 20;


create sequence AGENT_REPORT_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 8200
increment by 1
cache 20;


create sequence ASSIGN_EXCLUDE_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 2500
increment by 1
cache 20;


create sequence DELIVERY_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 141609999
increment by 1
cache 20;


create sequence DOWN_QUERY_FAKE_RULE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1700
increment by 1
cache 20;


create sequence DOWN_QUERY_HISTORY_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 900
increment by 1
cache 20;


create sequence DOWN_QUERY_TACTICS_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 100
increment by 1
cache 20;


create sequence HOPS_CONSTANT_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 900
increment by 1
cache 20;


create sequence INTERFACE_DEFINITION_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 2100
increment by 1
cache 20;


create sequence INTERFACE_PARAM_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 3300
increment by 1
cache 20;


create sequence MERCHANT_LEVEL_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 900
increment by 1
cache 20;


create sequence MERCHANT_REQUEST_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 2500
increment by 1
cache 20;


create sequence MERCHANT_RESPONSE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 4100
increment by 1
cache 20;


create sequence MERCHANT_ROBOT_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 900
increment by 1
cache 20;


create sequence NOTIFY_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 704
increment by 1
cache 20;


create sequence ORDER_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 14560999
increment by 1
cache 20;


create sequence ORDER_STATUS_DEFENDERS_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 100
increment by 1
cache 20;

create sequence PROFIT_IMPUTATION_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1401
increment by 1
cache 20;


create sequence PROFIT_REPORT_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 7880
increment by 1
cache 20;


create sequence QUALITY_WEIGHT_RULE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 500
increment by 1
cache 20;


create sequence REPORT_METADATA_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 2500
increment by 1
cache 20;


create sequence REPORT_PROPERTY_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 6180
increment by 1
cache 20;


create sequence REPORT_RECORD_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 11201
increment by 1
cache 20;


create sequence REPORT_TERM_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 5780
increment by 1
cache 20;


create sequence REPORT_TYPE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 6540
increment by 1
cache 20;


create sequence SEQ_ENCRYPTION_FUNCTION
minvalue 1
maxvalue 9999999999999999999999999999
start with 41
increment by 1
cache 20;


create sequence SEQ_INTERFACE_CONSTANT
minvalue 1
maxvalue 9999999999999999999999999999
start with 101
increment by 1
cache 20;


create sequence SEQ_INTERFACE_PACKET_TYPE_CONF
minvalue 1
maxvalue 9999999999999999999999999999
start with 121
increment by 1
cache 20;


create sequence SEQ_INTERFACE_SENDTIMES_CONF
minvalue 1
maxvalue 9999999999999999999999999999
start with 61
increment by 1
cache 20;


create sequence SEQ_REBATE_DATA_CONTROL_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;


create sequence SEQ_REBATE_HISTORY_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 61
increment by 1
cache 20;


create sequence SEQ_REBATE_PRODUCT_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 141
increment by 1
cache 20;


create sequence SEQ_REBATE_RECORD_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 9080
increment by 1
cache 20;

create sequence SEQ_REBATE_RULE_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 101
increment by 1
cache 20;


create sequence SEQ_REBATE_TRADING_VOLUME_ID
minvalue 1
maxvalue 9999999999999999999999999999
start with 141
increment by 1
cache 20;


create sequence STATUS_TRANSAFER_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 100
increment by 1
cache 20;


create sequence SUPPLY_REPORT_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 7800
increment by 1
cache 20;


create sequence TRANSACTION_REPORT_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 7800
increment by 1
cache 20;

create sequence BATCH_ORDERRE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 7800
increment by 1
cache 20;

create sequence UP_QUERY_TACTICS_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1300
increment by 1
cache 20;

create sequence ORDER_OP_HISTORY_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 500
increment by 1
cache 20;

commit;

--报表加唯一索引
create unique index IDX_RECORD_DATE_TYPE on PROFIT_REPORT_RECORD (BEGIN_DATE, MERCHANT_TYPE);


create unique index RECORD_BEGIN_DATE_INDEX on ACCOUNT_REPORT_RECORD (BEGIN_DATE);

create unique index IDX_RECORD_REPORT_DATE_TYPE on TRANSACTION_REPORT_RECORD (BEGIN_DATE, MERCHANT_TYPE, REPORT_TYPE);
--利润归集时间加上唯一索引
create unique index IDX_IMPUTATION_RECORD_DATE on PROFIT_IMPUTATION_RECORD (RECORD_BEGIN_DATE);
commit;

利润归集增加字段描述字段
alter table profit_imputation add  DESCRIBE VARCHAR2(100) null
