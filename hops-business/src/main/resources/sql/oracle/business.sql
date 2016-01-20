

--
-- Creating table AGENT_PRODUCT_RELATION
-- =====================================
--
create table  AGENT_PRODUCT_RELATION
(
  ID              NUMBER not null,
  IDENTITY_ID     NUMBER,
  IDENTITY_TYPE   VARCHAR2(20),
  IDENTITY_NAME   VARCHAR2(50),
  PRODUCT_ID      NUMBER,
  PRODUCT_NAME    VARCHAR2(50),
  DISCOUNT        NUMBER,
  DISCOUNT_WEIGHT NUMBER,
  QUALITY         NUMBER,
  QUALITY_WEIGHT  NUMBER,
  PRICE           NUMBER,
  STATUS          VARCHAR2(10),
  PROVINCE        VARCHAR2(10),
  PAR_VALUE       VARCHAR2(10),
  CARRIER_NAME    VARCHAR2(64),
  CITY            VARCHAR2(10),
  DEF_VALUE       NUMBER,
  IS_ROOT         NUMBER
);

comment on column  AGENT_PRODUCT_RELATION.ID
  is '主键';
comment on column  AGENT_PRODUCT_RELATION.IDENTITY_ID
  is '用户ID';
comment on column  AGENT_PRODUCT_RELATION.IDENTITY_TYPE
  is '用户类型';
comment on column  AGENT_PRODUCT_RELATION.IDENTITY_NAME
  is '用户名';
comment on column  AGENT_PRODUCT_RELATION.PRODUCT_ID
  is '产品ID';
comment on column  AGENT_PRODUCT_RELATION.PRODUCT_NAME
  is '产品名称';
comment on column  AGENT_PRODUCT_RELATION.DISCOUNT
  is '折扣';
comment on column  AGENT_PRODUCT_RELATION.DISCOUNT_WEIGHT
  is '折扣权重？';
comment on column  AGENT_PRODUCT_RELATION.QUALITY
  is '质量';
comment on column  AGENT_PRODUCT_RELATION.QUALITY_WEIGHT
  is '质量权重？';
comment on column  AGENT_PRODUCT_RELATION.PRICE
  is '销售金额';
comment on column  AGENT_PRODUCT_RELATION.STATUS
  is '状态';
comment on column  AGENT_PRODUCT_RELATION.PROVINCE
  is '省份';
comment on column  AGENT_PRODUCT_RELATION.PAR_VALUE
  is '面值';
comment on column  AGENT_PRODUCT_RELATION.CARRIER_NAME
  is '运营商';
comment on column  AGENT_PRODUCT_RELATION.CITY
  is '城市';
comment on column  AGENT_PRODUCT_RELATION.DEF_VALUE
  is '选定  >=1,未选  0';
comment on column  AGENT_PRODUCT_RELATION.IS_ROOT
  is '>=1是    0否';
alter table  AGENT_PRODUCT_RELATION
  add primary key (ID);

--
-- Creating table AIRTIME_PRODUCT
-- ==============================
--
create table  AIRTIME_PRODUCT
(
  PRODUCT_ID        NUMBER not null,
  PRODUCT_NAME      VARCHAR2(64),
  PRODUCT_TYPE      NUMBER,
  PRODUCT_STATUS    VARCHAR2(10),
  PROVINCE          VARCHAR2(10),
  PAR_VALUE         VARCHAR2(10),
  CARRIER_NAME      VARCHAR2(64),
  CITY              VARCHAR2(10),
  PARENT_PRODUCT_ID NUMBER,
  PRODUCT_NO        VARCHAR2(20)
);
 
comment on column  AIRTIME_PRODUCT.PRODUCT_NAME
  is '产品名';
comment on column  AIRTIME_PRODUCT.PRODUCT_TYPE
  is '产品类型';
comment on column  AIRTIME_PRODUCT.PRODUCT_STATUS
  is '产品状态';
comment on column  AIRTIME_PRODUCT.PROVINCE
  is '省份';
comment on column  AIRTIME_PRODUCT.PAR_VALUE
  is '面值';
comment on column  AIRTIME_PRODUCT.CARRIER_NAME
  is '运营商';
comment on column  AIRTIME_PRODUCT.CITY
  is '城市';
comment on column  AIRTIME_PRODUCT.PARENT_PRODUCT_ID
  is '父节点ID （根节点此字段为本身PRODUCT_ID）';
comment on column  AIRTIME_PRODUCT.PRODUCT_NO
  is '产品编码:类型（HF）2+运营商（YD）2+省份（全国 *）3+地市（全省 *）4+面值（均缩写）8';
alter table  AIRTIME_PRODUCT
  add primary key (PRODUCT_ID);

--
-- Creating table CARRIER_INFO
-- ===========================
--
create table  CARRIER_INFO
(
  CARRIER_NO   VARCHAR2(3) not null,
  CARRIER_NAME VARCHAR2(32) not null,
  CARRIER_TYPE NUMBER(1) not null,
  UPDATE_USER  VARCHAR2(20),
  UPDATE_TIME  DATE default sysdate not null,
  STATUS       VARCHAR2(2) default 0 not null,
  CREATE_TIME  DATE default sysdate
);
 
comment on table  CARRIER_INFO
  is '运营商信息';
comment on column  CARRIER_INFO.CARRIER_NO
  is '运营商编号';
comment on column  CARRIER_INFO.CARRIER_NAME
  is '运营商名称';
comment on column  CARRIER_INFO.CARRIER_TYPE
  is '运营商类型话费，游戏，水电';
comment on column  CARRIER_INFO.UPDATE_USER
  is '操作人';
comment on column  CARRIER_INFO.UPDATE_TIME
  is '操作时间';
comment on column  CARRIER_INFO.STATUS
  is '状态:0:上架1:下架';
alter table  CARRIER_INFO
  add primary key (CARRIER_NO);

--
-- Creating table CITY
-- ===================
--
create table  CITY
(
  CITY_ID     VARCHAR2(10) not null,
  CITY_NAME   VARCHAR2(40),
  STATUS      NUMBER(1) default 0,
  PROVINCE_ID VARCHAR2(4)
);
 
comment on column  CITY.CITY_ID
  is '市编号';
comment on column  CITY.CITY_NAME
  is '市名称';
comment on column  CITY.STATUS
  is '状态';
comment on column  CITY.PROVINCE_ID
  is '省Id';
alter table  CITY
  add primary key (CITY_ID);

--
-- Creating table ERROR_CODE
-- =========================
--
create table  ERROR_CODE
(
  CODE VARCHAR2(10) not null,
  MSG  VARCHAR2(100)
);
 
comment on column  ERROR_CODE.CODE
  is '错误码';
comment on column  ERROR_CODE.MSG
  is '错误码注解';
alter table  ERROR_CODE
  add constraint SYS_ERROR_CODE_PK primary key (CODE);

--
-- Creating table INTERFACE_CONSTANT
-- =================================
--
create table  INTERFACE_CONSTANT
(
  ID            NUMBER,
  IDENTITY_ID   NUMBER,
  IDENTITY_TYPE VARCHAR2(32),
  KEY           VARCHAR2(32),
  VALUE         VARCHAR2(256),
  IDENTITY_NAME VARCHAR2(132)
);
 
comment on column  INTERFACE_CONSTANT.ID
  is '主键';
comment on column  INTERFACE_CONSTANT.IDENTITY_ID
  is '商户ID';
comment on column  INTERFACE_CONSTANT.IDENTITY_TYPE
  is '商户类型';
comment on column  INTERFACE_CONSTANT.KEY
  is '关键字';
comment on column  INTERFACE_CONSTANT.VALUE
  is '值';
comment on column  INTERFACE_CONSTANT.IDENTITY_NAME
  is '商户名称';
alter table  INTERFACE_CONSTANT
  add primary key (ID);
--
-- Creating table INTERFACE_PACKETS_DEFINITION
-- ===========================================
--
create table INTERFACE_PACKETS_DEFINITION
(
  id              NUMBER not null,
  is_conf         NUMBER,
  merchant_id     NUMBER,
  interface_type  VARCHAR2(40),
  in_or_out       VARCHAR2(3),
  encoding        VARCHAR2(10),
  connection_type VARCHAR2(10),
  request_url     VARCHAR2(100),
  entity_name     VARCHAR2(20),
  status          VARCHAR2(5)
);
comment on table INTERFACE_PACKETS_DEFINITION
  is '接口定义';
comment on column INTERFACE_PACKETS_DEFINITION.id
  is '主键';
comment on column INTERFACE_PACKETS_DEFINITION.is_conf
  is '是否配置';
comment on column INTERFACE_PACKETS_DEFINITION.merchant_id
  is '商户ID';
comment on column INTERFACE_PACKETS_DEFINITION.interface_type
  is '接口类型名称';
comment on column INTERFACE_PACKETS_DEFINITION.in_or_out
  is '接入接出接口标示';
comment on column INTERFACE_PACKETS_DEFINITION.encoding
  is '接口编码';
comment on column INTERFACE_PACKETS_DEFINITION.connection_type
  is '连接方式(http/https/socket/sockets)';
comment on column INTERFACE_PACKETS_DEFINITION.request_url
  is '请求地址';
comment on column INTERFACE_PACKETS_DEFINITION.entity_name
  is '实体名称(param_conf表里面初始化)';
comment on column INTERFACE_PACKETS_DEFINITION.status
  is '状态：
关闭(close)
开启(open)';
alter table INTERFACE_PACKETS_DEFINITION
  add primary key (ID);

--
-- Creating table INTERFACE_PACKET_TYPE_CONF
-- =========================================
--
create table  INTERFACE_PACKET_TYPE_CONF
(
  ID                NUMBER,
  MERCHANT_ID       NUMBER,
  INTERFACE_TYPE    VARCHAR2(40),
  CONNECTION_MODULE VARCHAR2(32),
  PACKET_TYPE       VARCHAR2(32)
);
 
comment on column  INTERFACE_PACKET_TYPE_CONF.ID
  is '主键';
comment on column  INTERFACE_PACKET_TYPE_CONF.MERCHANT_ID
  is '商户ID';
comment on column  INTERFACE_PACKET_TYPE_CONF.INTERFACE_TYPE
  is '接口类型';
comment on column  INTERFACE_PACKET_TYPE_CONF.CONNECTION_MODULE
  is '连接模型：request、response';
comment on column  INTERFACE_PACKET_TYPE_CONF.PACKET_TYPE
  is '数据包类型：xml、pain_txt';
alter table  INTERFACE_PACKET_TYPE_CONF
  add primary key (ID);
--
-- Creating table INTERFACE_PARAM
-- ==============================
--
create table  INTERFACE_PARAM
(
  ID                      NUMBER not null,
  INTERFACE_DEFINITION_ID NUMBER,
  SEQUENCE                NUMBER,
  DATA_TYPE               VARCHAR2(10),
  PARAM_TYPE              VARCHAR2(10),
  INPUT_PARAM_NAME        VARCHAR2(30),
  OUT_PARAM_NAME          VARCHAR2(30),
  ENCRYPTION_FUNCTION     VARCHAR2(20),
  ENCRYPTION_PARAM_NAMES  VARCHAR2(256),
  CONNECTION_MODULE       VARCHAR2(10),
  IN_BODY                 VARCHAR2(10),
  IS_CAPITAL              VARCHAR2(10),
  FORMAT_TYPE             VARCHAR2(20),
  RESPONSE_RESULT         VARCHAR2(10)
);
 
comment on table  INTERFACE_PARAM
  is '接口参数定义';
comment on column  INTERFACE_PARAM.ID
  is '主键';
comment on column  INTERFACE_PARAM.INTERFACE_DEFINITION_ID
  is '接口定义ID';
comment on column  INTERFACE_PARAM.SEQUENCE
  is '排序';
comment on column  INTERFACE_PARAM.DATA_TYPE
  is '数据类型(字符串、日期)';
comment on column  INTERFACE_PARAM.PARAM_TYPE
  is '参数类型';
comment on column  INTERFACE_PARAM.INPUT_PARAM_NAME
  is '输入参数名称';
comment on column  INTERFACE_PARAM.OUT_PARAM_NAME
  is '输出参数名称';
comment on column  INTERFACE_PARAM.ENCRYPTION_FUNCTION
  is '加密类型';
comment on column  INTERFACE_PARAM.ENCRYPTION_PARAM_NAMES
  is '加密参数';
comment on column  INTERFACE_PARAM.CONNECTION_MODULE
  is 'request请求/response接收';
comment on column  INTERFACE_PARAM.IN_BODY
  is '是否在报文体';
comment on column  INTERFACE_PARAM.IS_CAPITAL
  is '大小写：0大写，1小写';
comment on column  INTERFACE_PARAM.FORMAT_TYPE
  is '格式化类型：如果参数类型是日期或者金额，则启用。';
comment on column  INTERFACE_PARAM.RESPONSE_RESULT
  is '返回成功失败标示 成功(success) 失败(fail)';
alter table  INTERFACE_PARAM
  add primary key (ID);



--
-- Creating table MERCHANT_REQUEST
-- ===============================
--
create table  MERCHANT_REQUEST
(
  ID                   NUMBER not null,
  MERCHANT_ID          NUMBER,
  INTERFACE_TYPE       VARCHAR2(32),
  INTERVAL_TIME        NUMBER,
  INTERVAL_UNIT        VARCHAR2(5),
  TIME_DIFFERENCE_LOW  NUMBER,
  TIME_DIFFERENCE_HIGH NUMBER
);
 
comment on table  MERCHANT_REQUEST
  is '发货、查询、通知（定义多久以后做）';
comment on column  MERCHANT_REQUEST.ID
  is '主键';
comment on column  MERCHANT_REQUEST.MERCHANT_ID
  is '商户号';
comment on column  MERCHANT_REQUEST.INTERFACE_TYPE
  is '接口类型';
comment on column  MERCHANT_REQUEST.INTERVAL_TIME
  is '间隔时间';
comment on column  MERCHANT_REQUEST.INTERVAL_UNIT
  is '时间单位';
comment on column  MERCHANT_REQUEST.TIME_DIFFERENCE_LOW
  is '时差最小(秒)';
comment on column  MERCHANT_REQUEST.TIME_DIFFERENCE_HIGH
  is '时差最大(秒)';
alter table  MERCHANT_REQUEST
  add primary key (ID);

--
-- Creating table MERCHANT_RESPONSE
-- ================================
--
create table  MERCHANT_RESPONSE
(
  ID                   NUMBER not null,
  MERCHANT_ID          NUMBER,
  INTERFACE_TYPE       VARCHAR2(32),
  ERROR_CODE           VARCHAR2(32),
  MERCHANT_STATUS      VARCHAR2(32),
  MERCHANT_STATUS_INFO VARCHAR2(50),
  STATUS               NUMBER
);
 
comment on column  MERCHANT_RESPONSE.ID
  is '主键';
comment on column  MERCHANT_RESPONSE.MERCHANT_ID
  is '商户号';
comment on column  MERCHANT_RESPONSE.INTERFACE_TYPE
  is '接口类型';
comment on column  MERCHANT_RESPONSE.ERROR_CODE
  is '商户服务结果码';
comment on column  MERCHANT_RESPONSE.MERCHANT_STATUS
  is '商户订单状态';
comment on column  MERCHANT_RESPONSE.MERCHANT_STATUS_INFO
  is '商户返回码含义';
comment on column  MERCHANT_RESPONSE.STATUS
  is '系统状态';
alter table  MERCHANT_RESPONSE
  add primary key (ID) ;

--
-- Creating table NUM_SECTION
-- ==========================
--
create table  NUM_SECTION
(
  SECTION_ID  VARCHAR2(20) not null,
  CARRIER_NO  VARCHAR2(10),
  PROVINCE_ID VARCHAR2(4),
  CITY_ID     VARCHAR2(10),
  MOBILE_TYPE VARCHAR2(50),
  PRIORITY    NUMBER(20) default 0,
  USED_TIMES  NUMBER(20) default 0,
  CREATE_TIME DATE default sysdate not null
);
 
comment on table  NUM_SECTION
  is '号码段信息';
comment on column  NUM_SECTION.SECTION_ID
  is '号码段';
comment on column  NUM_SECTION.CARRIER_NO
  is '运营商';
comment on column  NUM_SECTION.PROVINCE_ID
  is '省Id';
comment on column  NUM_SECTION.CITY_ID
  is '市Id';
comment on column  NUM_SECTION.MOBILE_TYPE
  is '电话类型';
comment on column  NUM_SECTION.PRIORITY
  is '优先级';
comment on column  NUM_SECTION.USED_TIMES
  is '使用的次数';
comment on column  NUM_SECTION.CREATE_TIME
  is '创建时间';
alter table  NUM_SECTION
  add primary key (SECTION_ID);

--
-- Creating table PARAMETER_CONFIGURATION
-- ======================================
--
create table  PARAMETER_CONFIGURATION
(
  ID                  NUMBER not null,
  CONSTANT_VALUE      VARCHAR2(50),
  CONSTANT_NAME       VARCHAR2(50),
  CONSTANT_UNIT_VALUE VARCHAR2(30),
  CONSTANT_UNIT_NAME  VARCHAR2(50),
  EXT1                VARCHAR2(20),
  EXT2                VARCHAR2(20)
);
comment on table  PARAMETER_CONFIGURATION
  is '上游查询策略表';
comment on column  PARAMETER_CONFIGURATION.ID
  is '主键';
comment on column  PARAMETER_CONFIGURATION.CONSTANT_VALUE
  is '常数值';
comment on column  PARAMETER_CONFIGURATION.CONSTANT_NAME
  is '常数名称';
comment on column  PARAMETER_CONFIGURATION.CONSTANT_UNIT_VALUE
  is '单位值';
comment on column  PARAMETER_CONFIGURATION.CONSTANT_UNIT_NAME
  is '单位名称';
comment on column  PARAMETER_CONFIGURATION.EXT1
  is '扩展字段';
alter table  PARAMETER_CONFIGURATION
  add primary key (ID);

--
-- Creating table PRODUCT_IDENTITY_RELATION
-- ========================================
--
create table  PRODUCT_IDENTITY_RELATION
(
  ID             NUMBER not null,
  IDENTITY_ID    VARCHAR2(64),
  IDENTITY_NAME  VARCHAR2(64),
  IDENTITY_TYPE  VARCHAR2(64),
  PRODUCT_ID     NUMBER,
  PRODUCT_NAME   VARCHAR2(64),
  PRICE_STRATEGY VARCHAR2(64),
  PERCENTAGE     VARCHAR2(10),
  PRICE          VARCHAR2(30)
);

comment on column  PRODUCT_IDENTITY_RELATION.IDENTITY_ID
  is '商户';
comment on column  PRODUCT_IDENTITY_RELATION.IDENTITY_NAME
  is '商户名';
comment on column  PRODUCT_IDENTITY_RELATION.IDENTITY_TYPE
  is '类型';
comment on column  PRODUCT_IDENTITY_RELATION.PRODUCT_ID
  is '商品';
comment on column  PRODUCT_IDENTITY_RELATION.PRODUCT_NAME
  is '商品名';
comment on column  PRODUCT_IDENTITY_RELATION.PRICE_STRATEGY
  is '定价';
comment on column  PRODUCT_IDENTITY_RELATION.PERCENTAGE
  is '百分比';
comment on column  PRODUCT_IDENTITY_RELATION.PRICE
  is '价格';

--
-- Creating table PRODUCT_OPERATION_DETAIL
-- =======================================
--
create table  PRODUCT_OPERATION_DETAIL
(
  ID                           NUMBER not null,
  PRODUCT_OPERATION_HISTORY_ID NUMBER,
  PRODUCT_RELATION_ID          NUMBER,
  PRODUCT_RELATION_STATUS      VARCHAR2(10),
  MERCHANT_TYPE                VARCHAR2(20)
);
 
comment on column  PRODUCT_OPERATION_DETAIL.ID
  is '主键';
comment on column  PRODUCT_OPERATION_DETAIL.PRODUCT_OPERATION_HISTORY_ID
  is '产品操作记录ID';
comment on column  PRODUCT_OPERATION_DETAIL.PRODUCT_RELATION_ID
  is '产品编号';
comment on column  PRODUCT_OPERATION_DETAIL.PRODUCT_RELATION_STATUS
  is '商户产品状态';
comment on column  PRODUCT_OPERATION_DETAIL.MERCHANT_TYPE
  is '上下游类型';
alter table  PRODUCT_OPERATION_DETAIL
  add primary key (ID);

--
-- Creating table PRODUCT_OPERATION_HISTORY
-- ========================================
--
create table  PRODUCT_OPERATION_HISTORY
(
  ID             NUMBER not null,
  OPERATION_NAME VARCHAR2(50),
  STATUS         VARCHAR2(10),
  CARRIER_NAME   VARCHAR2(64),
  PAR_VALUE      NUMBER,
  PROVINCE       VARCHAR2(10),
  CITY           VARCHAR2(10),
  MERCHANT_TYPE  VARCHAR2(20),
  CREATE_DATE    DATE,
  OPERATION_FLAG VARCHAR2(10)
);
 
comment on column  PRODUCT_OPERATION_HISTORY.ID
  is '主键';
comment on column  PRODUCT_OPERATION_HISTORY.OPERATION_NAME
  is '操作名称';
comment on column  PRODUCT_OPERATION_HISTORY.STATUS
  is '状态';
comment on column  PRODUCT_OPERATION_HISTORY.CARRIER_NAME
  is '运营商';
comment on column  PRODUCT_OPERATION_HISTORY.PAR_VALUE
  is '面值';
comment on column  PRODUCT_OPERATION_HISTORY.PROVINCE
  is '省份';
comment on column  PRODUCT_OPERATION_HISTORY.CITY
  is '城市';
comment on column  PRODUCT_OPERATION_HISTORY.MERCHANT_TYPE
  is '上下游类型';
comment on column  PRODUCT_OPERATION_HISTORY.CREATE_DATE
  is '创建时间';
comment on column  PRODUCT_OPERATION_HISTORY.OPERATION_FLAG
  is '操作 打开、关闭';
alter table  PRODUCT_OPERATION_HISTORY
  add primary key (ID);

--
-- Creating table PRODUCT_PROPERTY
-- ===============================
--
create table  PRODUCT_PROPERTY
(
  PRODUCT_PROPERTY_ID NUMBER not null,
  PARAM_NAME          VARCHAR2(64),
  ATTRIBUTE           VARCHAR2(64),
  MIN_LENGTH          NUMBER,
  MAX_LENGTH          NUMBER,
  VALUE               VARCHAR2(400),
  PARAM_ENGLISH_NAME  VARCHAR2(64)
);
 
comment on column  PRODUCT_PROPERTY.PRODUCT_PROPERTY_ID
  is 'id';
comment on column  PRODUCT_PROPERTY.PARAM_NAME
  is '字段名称';
comment on column  PRODUCT_PROPERTY.ATTRIBUTE
  is '字段属性类型';
comment on column  PRODUCT_PROPERTY.MIN_LENGTH
  is '字段最小长度';
comment on column  PRODUCT_PROPERTY.MAX_LENGTH
  is '字段最大长度';
comment on column  PRODUCT_PROPERTY.VALUE
  is '待选值，当attribute为select的时候用。';
comment on column  PRODUCT_PROPERTY.PARAM_ENGLISH_NAME
  is '英文字段名称';

--
-- Creating table PRODUCT_PROPERTY_RELATION
-- ========================================
--
create table  PRODUCT_PROPERTY_RELATION
(
  ID                  NUMBER not null,
  PRODUCT_PROPERTY_ID NUMBER,
  PRODUCT_TPYE_ID     NUMBER
);

comment on column  PRODUCT_PROPERTY_RELATION.PRODUCT_PROPERTY_ID
  is '商品属性';
comment on column  PRODUCT_PROPERTY_RELATION.PRODUCT_TPYE_ID
  is '商品类型';

--
-- Creating table PRODUCT_TYPE
-- ===========================
--
create table  PRODUCT_TYPE
(
  TYPE_ID             NUMBER not null,
  PRODUCT_TYPE_NAME   VARCHAR2(64),
  PRODUCT_TYPE_STATUS VARCHAR2(10)
);
 
comment on column  PRODUCT_TYPE.PRODUCT_TYPE_NAME
  is '商品类型名';
comment on column  PRODUCT_TYPE.PRODUCT_TYPE_STATUS
  is '商品类型状态';

--
-- Creating table PRODUCT_TYPE_RELATION
-- ====================================
--
create table  PRODUCT_TYPE_RELATION
(
  ID                  NUMBER not null,
  PRODUCT_PROPERTY_ID NUMBER,
  TYPE_ID             NUMBER
);
 
comment on column  PRODUCT_TYPE_RELATION.PRODUCT_PROPERTY_ID
  is '商品属性';
comment on column  PRODUCT_TYPE_RELATION.TYPE_ID
  is '类型';

--
-- Creating table PROVINCE
-- =======================
--
create table  PROVINCE
(
  ID            NUMBER not null,
  PROVINCE_ID   VARCHAR2(4) not null,
  PROVINCE_NAME VARCHAR2(20) not null,
  STATUS        NUMBER(1) not null,
  SORT_ID       NUMBER(2) default 1 not null
);
 
comment on column  PROVINCE.PROVINCE_ID
  is '省份';
comment on column  PROVINCE.PROVINCE_NAME
  is '省份名称';
comment on column  PROVINCE.STATUS
  is '状态';
comment on column  PROVINCE.SORT_ID
  is '类别';
alter table  PROVINCE
  add primary key (ID);

--
-- Creating table RESPONSE_CODE_TRANSLATION
-- ========================================
--
create table RESPONSE_CODE_TRANSLATION
(
  id                NUMBER not null,
  interface_type    VARCHAR2(32),
  error_code        VARCHAR2(4),
  coop_order_status VARCHAR2(20),
  failed_code       VARCHAR2(4),
  msg               VARCHAR2(200)
);
comment on table RESPONSE_CODE_TRANSLATION
  is '订单返回码对照翻译表';
comment on column RESPONSE_CODE_TRANSLATION.id
  is '主键';
comment on column RESPONSE_CODE_TRANSLATION.interface_type
  is '接口类型';
comment on column RESPONSE_CODE_TRANSLATION.error_code
  is '错误码';
comment on column RESPONSE_CODE_TRANSLATION.coop_order_status
  is '淘宝状态';
comment on column RESPONSE_CODE_TRANSLATION.failed_code
  is '淘宝错误码';
comment on column RESPONSE_CODE_TRANSLATION.msg
  is '描述信息';
alter table RESPONSE_CODE_TRANSLATION
  add primary key (ID);


--
-- Creating table SUPPLY_PRODUCT_RELATION
-- ======================================
--
create table  SUPPLY_PRODUCT_RELATION
(
  ID             NUMBER not null,
  IDENTITY_ID    NUMBER,
  IDENTITY_TYPE  VARCHAR2(20),
  IDENTITY_NAME  VARCHAR2(50),
  PRODUCT_ID     NUMBER,
  PRODUCT_NAME   VARCHAR2(50),
  DISCOUNT       NUMBER,
  QUALITY        NUMBER,
  PRICE          NUMBER,
  STATUS         VARCHAR2(10),
  MERCHANT_LEVEL NUMBER,
  PROVINCE       VARCHAR2(10),
  PAR_VALUE      VARCHAR2(10),
  CARRIER_NAME   VARCHAR2(10),
  CITY           VARCHAR2(10)
);
 
comment on column  SUPPLY_PRODUCT_RELATION.ID
  is '主键';
comment on column  SUPPLY_PRODUCT_RELATION.IDENTITY_ID
  is '用户ID';
comment on column  SUPPLY_PRODUCT_RELATION.IDENTITY_TYPE
  is '用户类型';
comment on column  SUPPLY_PRODUCT_RELATION.IDENTITY_NAME
  is '用户名';
comment on column  SUPPLY_PRODUCT_RELATION.PRODUCT_ID
  is '产品ID';
comment on column  SUPPLY_PRODUCT_RELATION.PRODUCT_NAME
  is '产品名称';
comment on column  SUPPLY_PRODUCT_RELATION.DISCOUNT
  is '折扣';
comment on column  SUPPLY_PRODUCT_RELATION.QUALITY
  is '质量';
comment on column  SUPPLY_PRODUCT_RELATION.PRICE
  is '金额';
comment on column  SUPPLY_PRODUCT_RELATION.STATUS
  is '状态';
comment on column  SUPPLY_PRODUCT_RELATION.MERCHANT_LEVEL
  is '上游级别';
comment on column  SUPPLY_PRODUCT_RELATION.PROVINCE
  is '省份';
comment on column  SUPPLY_PRODUCT_RELATION.PAR_VALUE
  is '面值';
comment on column  SUPPLY_PRODUCT_RELATION.CARRIER_NAME
  is '运营商';
comment on column  SUPPLY_PRODUCT_RELATION.CITY
  is '城市';
alter table  SUPPLY_PRODUCT_RELATION
  add primary key (ID);

--
-- Creating table URI_TRANSACTION_MAPPING
-- ======================================
--
create table  URI_TRANSACTION_MAPPING
(
  ID               NUMBER not null,
  HOST_IP          VARCHAR2(20) not null,
  HOST_PORT        VARCHAR2(4) default 80 not null,
  ACTION_NAME      VARCHAR2(100),
  MERCHANT_ID      NUMBER,
  INTERFACE_TYPE   VARCHAR2(30),
  TRANSACTION_CODE VARCHAR2(20)
);
 
alter table  URI_TRANSACTION_MAPPING
  add primary key (ID) ;

--
-- Creating sequence AGENT_PRODUCT_ID_SEQ
-- ======================================
--
create sequence  AGENT_PRODUCT_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1700
increment by 20
cache 20;

--
-- Creating sequence INTERFACE_DEFINITION_ID_SEQ
-- =============================================
--
create sequence  INTERFACE_DEFINITION_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 2100
increment by 20
cache 20;

--
-- Creating sequence INTERFACE_PARAM_ID_SEQ
-- ========================================
--
create sequence  INTERFACE_PARAM_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 3300
increment by 20
cache 20;

--
-- Creating sequence MERCHANT_REQUEST_ID_SEQ
-- =========================================
--
create sequence  MERCHANT_REQUEST_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 2500
increment by 20
cache 20;

--
-- Creating sequence MERCHANT_RESPONSE_ID_SEQ
-- ==========================================
--
create sequence  MERCHANT_RESPONSE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 4100
increment by 20
cache 20;

--
-- Creating sequence PARAMETER_CONFIGURATION_ID_SEQ
-- ================================================
--
create sequence  PARAMETER_CONFIGURATION_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 900
increment by 20
cache 20;

--
-- Creating sequence PRODUCT_IDENTITY_ID_SEQ
-- =========================================
--
create sequence  PRODUCT_IDENTITY_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 520
increment by 20
cache 20;

--
-- Creating sequence PRODUCT_ID_SEQ
-- ================================
--
create sequence  PRODUCT_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 2960
increment by 20
cache 20;

--
-- Creating sequence PRODUCT_PROPERTYVALUE_ID_SEQ
-- ==============================================
--
create sequence  PRODUCT_PROPERTYVALUE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 300
increment by 20
cache 20;

--
-- Creating sequence PRODUCT_PROPERTY_ID_SEQ
-- =========================================
--
create sequence  PRODUCT_PROPERTY_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 3400
increment by 20
cache 20;

--
-- Creating sequence PRODUCT_RELATION_ID_SEQ
-- =========================================
--
create sequence  PRODUCT_RELATION_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 500
increment by 20
cache 20;

--
-- Creating sequence PRODUCT_TYPE_ID_SEQ
-- =====================================
--
create sequence  PRODUCT_TYPE_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 3480
increment by 20
cache 20;

--
-- Creating sequence SUPPLY_PRODUCT_ID_SEQ
-- =======================================
--
create sequence  SUPPLY_PRODUCT_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1300
increment by 20
cache 20;

-- Create sequence  g
create sequence PRODUCT_TYPE_REL_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 3520
increment by 20
cache 20;

-- Create sequence 
create sequence PRODUCT_O_DETAIL_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1300
increment by 20
cache 20;


-- Create sequence 
create sequence PRODUCT_O_HISTORY_ID_SEQ
minvalue 1
maxvalue 9999999999999999999999999999
start with 1300
increment by 20
cache 20;
-- Create sequence 
create sequence SEQ_INTERFACE_PACKET_TYPE_CONF
minvalue 1
maxvalue 9999999999999999999999999999
start with 121
increment by 1
cache 20;

-- Create sequence 
create sequence SEQ_INTERFACE_SENDTIMES_CONF
minvalue 1
maxvalue 9999999999999999999999999999
start with 61
increment by 1
cache 20;

-- Create sequence 
create sequence SEQ_INTERFACE_CONSTANT
minvalue 1
maxvalue 9999999999999999999999999999
start with 121
increment by 1
cache 20;


--产品添加常用字段
alter table airtime_product
add (IS_COMMON_USE NUMBER);
