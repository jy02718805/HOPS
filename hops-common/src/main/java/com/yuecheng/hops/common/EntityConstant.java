package com.yuecheng.hops.common;

public class EntityConstant
{
    public class InterfaceSendtimesConf
    {

        public static final String ID = "id";

        public static final String MERCHANT_ID = "merchantId";

        public static final String INTERFACE_TYPE = "interfaceType";

    }

    public class Operator
    {

        public static final String OPERATOR_ID = "operatorId";

        public static final String OWNER_IDENTITY_ID = "ownerIdentityId";

        public static final String DISPLAY_NAME = "displayName";

        public static final String OPERATOR_NAME = "operatorName";

    }

    public class InterfaceConstant
    {

        public static final String ID = "id";

        public static final String IDENTITY_ID = "identityId";

        public static final String IDENTITY_TYPE = "identityType";

        public static final String KEY = "key";

    }

    public class RebateHistory
    {
        public static final String PRODUCT_ID = "productId";

        public static final String MONTH = "month";

        public static final String STATUS = "status";

        public static final String MERCHANT_ID = "merchantId";

        public static final String REBATE_RULE_ID = "rebateRuleId";

    }

    public class RebateRecordHistory
    {
        public static final String ID = "id";

        public static final String MERCHANT_ID = "merchantId";

        public static final String REBATE_MERCHANT_ID = "rebateMerchantId";

        public static final String REBATE_TYPE = "rebateType";

        public static final String REBATE_STATUS = "rebateStatus";

        public static final String BALANCE_STATUS = "balanceStatus";

        public static final String STATUS = "status";

        public static final String REBATE_PRODUCT_ID = "rebateProductId";

        public static final String CREATE_DATE = "createDate";

        public static final String REBATE_START_DATE = "rebateStartDate";

        public static final String REBATE_END_DATE = "rebateEndDate";

    }

    public class RebateRule
    {
        public static final String PRODUCT_ID = "productId";

        public static final String DISCOUNT = "discount";

        public static final String REBATE_MERCHANT_ID = "rebateMerchantId";

        public static final String MERCHANT_ID = "merchantId";

        public static final String REBATE_RULE_ID = "rebateRuleId";

        public static final String REBATE_TYPE = "rebateType";

        public static final String REBATE_TIME_TYPE = "rebateTimeType";

        public static final String DEL_STATUS = "delStatus";

        public static final String STATUS = "status";
    }

    /**
     * 发货entity
     * 
     * @author Administrator
     */
    public class Delivery
    {
        public static final String DELIVERY_ID = "deliveryId";

        public static final String DELIVERY_STATUS = "deliveryStatus";

        public static final String QUERY_FLAG = "queryFlag";

        public static final String ORDER_NO = "orderNo";

        public static final String MERCHANT_ID = "merchantId";

        public static final String SUCCESS_FEE = "successFee";

        public static final String DELIVERY_START_TIME = "deliveryStartTime";

        public static final String PRE_DELIVERY_TIME = "preDeliveryTime";

        public static final String ORDER_FINISH_TIME = "orderFinishTime";

        public static final String NEXT_QUERY_TIME = "nextQueryTime";

        public static final String MERCHANT_ORDER_NO = "merchantOrderNo";

        public static final String PRODUCT_FACE = "productFace";

        public static final String PRODUCT_SALE_DISCOUNT = "productSaleDiscount";

        public static final String COST_DISCOUNT = "costDiscount";

        public static final String COST_FEE = "costFee";

        public static final String USER_CODE = "userCode";

        public static final String PRODUCT_NAME = "productName";

        public static final String BEGIN_DATE = "beginDate";

        public static final String END_DATE = "endDate";

        public static final String PRODUCT_ID = "productId";

        public static final String COOPORDERSUCCESSTIME = "orderFinishTime";

        public static final String FILED_CODE = "errorCode";

        public static final String FILED_REASON = "closeReason";

        public static final String VERSION = "version";

        public static final String DISPLAYVALUE = "displayValue";

        public static final String PRODUCT_TYPE = "productType";

        public static final String CP = "cp";

        public static final String CITY_CODE = "cityCode";
    }

    public class Order
    {
        public static final String ORDER_NO = "orderNo";

        public static final String ORDER_NO2 = "order_no";

        public static final String ORDER_STATUS = "orderStatus";

        public static final String MERCHANT_ID = "merchantId";

        public static final String PRE_SUCCESS_STATUS = "preSuccessStatus";

        public static final String ORDER_PRE_SUCCESS_TIME = "orderPreSuccessTime";

        public static final String EXT1 = "ext1";

        public static final String EXT2 = "ext2";

        public static final String ORDER_FEE = "orderFee";

        public static final String ORDER_REQUEST_TIME = "orderRequestTime";

        public static final String ORDER_REQUEST_TIME2 = "order_request_time";

        public static final String NOTIFY_STATUS = "notifyStatus";

        public static final String USER_CODE = "userCode";

        public static final String MERCHANT_ORDER_NO = "merchantOrderNo";

        public static final String BIND_TIMES = "bindTimes";

        public static final String PRE_ORDER_BIND_TIME = "preOrderBindTime";

        public static final String ORDER_FINISH_TIME = "orderFinishTime";

        public static final String ORDER_FINISH_TIME2 = "order_finish_time";

        public static final String PRODUCT_NAME = "productName";

        public static final String PORDUCT_NUM = "productNum";

        public static final String BUSINESS_TYPE = "businessType";

        public static final String ORDER_DESC = "orderDesc";

        public static final String ORDER_SUCCESS_FEE = "orderSuccessFee";

        public static final String RESULT = "result";

        public static final String ERROR_CODE = "errorCode";

        public static final String MSG = "msg";

        public static final String MERCHANT_STATUS = "merchantStatus";

        public static final String RESPONSE_STR = "responseStr";

        public static final String INFO1 = "info1";

        public static final String INFO2 = "info2";

        public static final String INFO3 = "info3";

        public static final String BEGIN_DATE = "beginDate";

        public static final String END_DATE = "endDate";
    }

    public class Notify
    {
        public static final String NOTIFY_ID = "notifyId";

        public static final String ORDER_NO = "orderNo";

        public static final String NOTIFY_URL = "notifyUrl";
    }

    public class MerchantProductLevel
    {
        public static final String MERCHANT_LEVEL = "merchantLevel";

        public static final String ORDER_PERCENTAGE_LOW = "orderPercentagelow";

        public static final String ORDER_PERCENTAGE_HIGH = "orderPercentageHigh";
    }

    public class AssignExclude
    {
        public static final String ID = "id";

        public static final String MERCHANT_ID = "merchantId";

        public static final String MERCHANT_TYPE = "merchantType";

        public static final String RULE_TYPE = "ruleType";

        public static final String PRODUCT_NO = "productNo";

        public static final String CARRIER_NO = "carrierNo";

        public static final String PROVICE_NO = "provinceNo";

        public static final String CITY_NO = "cityNo";

        public static final String OBJECT_MERCHANT_ID = "objectMerchantId";

        public static final String PAR_VALUE = "parValue";
    }

    public class MerchantResponse
    {
        public static final String ORDER_NO = "orderNo";

        public static final String MERCHANT_ID = "merchantId";

        public static final String MERCHANT_CODE = "merchantCode";

        public static final String DELIVERY_ID = "deliveryId";

        public static final String SERVICE_TYPE = "serviceType";

        public static final String ERROR_CODE = "errorCode";

        public static final String INTERFACE_TYPE = "interfaceType";

        public static final String MERCHANT_STATUS = "merchantStatus";

        public static final String MSG = "msg";

        public static final String ORDER_SUCCESS_FEE = "orderSuccessFee";

        public static final String SUPPLY_MERCHANT_ORDER_NO = "supplyMerchantOrderNo";

        public static final String RESPONSE_STR = "responseStr";

        public static final String REQUESTID = "requestId";

        public static final String DELIVERY_RESULT = "deliveryResult";

        public static final String REQUEST_STR = "requestStr";

        public static final String QUERY_MSG = "未知状态";
    }

    public class AgentQueryFakeRule
    {
        public static final String ID = "id";

        public static final String MERCHANT_ID = "merchantId";

    }

    public class DownQueryHistory
    {
        public static final String MERCHANT_ID = "merchantId";
    }

    public class MerchantRobot
    {
        public static final String MERCHANT_ID = "merchantId";

        public static final String MERCHANT_NAME = "merchantName";

        public static final String ID = "id";
    }

    public class AgentProductRelation
    {
        public static final String ID = "id";

        public static final String PRODUCT_ID = "productId";

        public static final String IDENTITY_ID = "identityId";

        public static final String IDENTITY_TYPE = "identityType";

        public static final String STATUS = "status";

        public static final String ISROOT = "isRoot";

        public static final String MERCHANT = "MERCHANT";

        public static final String CARRIER_NAME = "carrierName";

        public static final String PROVINCE = "province";

        public static final String CITY = "city";

        public static final String PAR_VALUE = "parValue";

        public static final String PRICE = "price";

        public static final String AGENT_PROD_ID = "agentProdId";

        public static final String BUSINESSTYPE = "businessType";

        public static final String DISCOUNT = "discount";

        public static final String MAX_DISCOUNT = "discount2";
    }

    public class ProductProperty
    {
        public static final String PARAM_NAME = "paramName";

        public static final String PRODUCT_PROPERTY_ID = "productPropertyId";
    }

    public class AirtimeProduct
    {
        public static final String PROVINCE = "province";

        public static final String PARVALUE = "parValue";

        public static final String BUSINESS_TYPE = "businessType";

        public static final String CARRIER_NAME = "carrierName";

        public static final String CITY = "city";

        public static final String PRODUCT_ID = "productId";

        public static final String PRODUCT_STATUS = "productStatus";

        public static final String HF = "HF";

        public static final String FLOW = "LL";

        public static final String FIXED = "GH";

        public static final String PARENT_PRODUCT_ID = "parentProductId";

        public static final String PRODUCT_NO = "productNo";

        public static final String IS_COMMON_USE = "isCommonUse";

        public static final String BUSINESSTYPE = "businessType";
    }

    public class ProductType
    {
        public static final String TYPE_ID = "typeId";
    }

    public class SupplyProductRelation
    {
        public static final String ID = "id";

        public static final String IDENTITY_ID = "identityId";

        public static final String IDENTITY_ID2 = "identity_Id";

        public static final String IDENTITY_ID_SQL = "identity_Id";

        public static final String IDENTITY_TYPE = "identityType";

        public static final String IDENTITY_TYPE_SQL = "identity_Type";

        public static final String STATUS = "status";

        public static final String PRODUCT_ID = "productId";

        public static final String PRODUCT_ID2 = "product_id";

        public static final String MERCHANT_LEVEL = "merchantLevel";

        public static final String MERCHANT_LEVEL2 = "merchant_level";

        public static final String QUALITY = "quality";

        public static final String DISCOUNT = "discount";

        public static final String CARRIER_NAME = "carrierName";

        public static final String PROVINCE = "province";

        public static final String CITY = "city";

        public static final String PRICE = "price";

        public static final String PAR_VALUE = "parValue";

        public static final String SUPPLY_PROD_ID = "supplyProdId";

        public static final String BUSINESSTYPE = "businessType";

        public static final String MAX_DISCOUNT = "discount2";
    }

    public class SupplyMonitor
    {
        public static final String PRODUCT_ID = "productId";

        public static final String MERCHANT_ID = "merchantId";
    }

    public class CalcQualityRule
    {
        public static final String ORDER_NUM_LOW = "orderNumLow";

        public static final String ORDER_NUM_HIGH = "orderNumHigh";
    }

    public class SupplyQueryTactics
    {
        public static final String ID = "id";

        public static final String MERCHANT_ID = "merchantId";

        public static final String TIME_DIFFERENCE_LOW = "timeDifferenceLow";

        public static final String TIME_DIFFERENCE_HIGH = "timeDifferenceHigh";
    }

    public class ParameterConfiguration
    {
        public static final String ID = "id";

        public static final String CONSTANT_VALUE = "constantValue";

        public static final String CONSTANT_NAME = "constantName";
    }

    public class Customer
    {
        public static final String CUSTOMER_NAME = "customerName";

        public static final String STATUS = "identityStatus.status";

        public static final String SEX = "person.sex";

        public static final String DISPLAY_NAME = "displayName";

        public static final String CREATE_TIME = "createTime";

    }

    public class Account
    {

        public static final String ACCOUNT_ID = "accountId";

        public static final String ACCOUNT_TYPE = "accountType";

        public static final String IDENTITY_NAME = "identityName";
    }

    public class PageResource
    {

        public static final String PAGE_RESOURCE_NAME = "pageResourceName";

        public static final String CREATE_TIME = "createTime";

        public static final String STATUS = "status";
    }

    public class Role
    {

        public static final String ROLE_NAME = "roleName";

        public static final String ROLE_TYPE = "roleType";

        public static final String CREATE_TIME = "createTime";
    }

    public class Privilege
    {

        public static final String PRIVILEGE_NAME = "privilegeName";

        public static final String PERMISSION_NAME = "permissionName";

        public static final String PARENT_PRIVILEGE_ID = "parentPrivilegeId";
    }

    public class Session
    {

        public static final String LOGIN_SESSION_NAME = "loginUser";
    }

    public class Merchant
    {

        public static final String MERCHANT_TYPE = "merchantType";

        public static final String MERCHANT_CODE = "merchantCode";

        public static final String MERCHANT_CODE_CODE = "merchantCode.code";

        public static final String MERCHANT_NAME = "merchantName";

        public static final String IS_REBATE = "isRebate";

        public static final String STATUS = "identityStatus.status";

        public static final String ORGANIZATION_ID = "organization.organizationId";
    }

    public class Identity
    {
        public static final String IDENTITY_ID = "identityId";

        public static final String IDENTITY_TYPE = "identityType";

        public static final String OWNER_IDENTITY_TYPE = "ownerIdentityType";

        public static final String IDENTITY_NAME = "person.userName";

        public static final String OWNER_IDENTITY_ID = "ownerIdentityId";

    }

    public class Organization
    {

        public static final String ORGANIZATION_ID = "organizationId";

        public static final String ORGANIZATION_NAME = "organizationName";

    }

    public class TransactionHistory
    {
        public static final String TRANSACTION_ID = "transactionId";

        public static final String TRANSACTION_NO = "transactionNo";// 订单ID

        public static final String PAYER_ACCOUNT_ID = "payerAccountId";// 付款方账户ID

        public static final String PAYER_TYPE_MODEL = "payerTypeModel";// 付款方账户类型

        public static final String PAYEE_ACCOUNT_ID = "payeeAccountId";// 收款方账户ID

        public static final String PAYEE_TYPE_MODEL = "payeeTypeModel";// 收款方账户类型

        public static final String AMT = "amt";// 交易金额

        public static final String CREATE_DATE = "createDate";// 创建时间

        public static final String BEGIN_DATE = "beginDate";

        public static final String END_DATE = "endDate";

        public static final String PAYER_IDENTITY_NAME = "payerIdentityName";

        public static final String PAYEE_IDENTITY_NAME = "payeeIdentityName";

        public static final String PAYER_ACCOUNT_TYPE = "payerAccountType";

        public static final String PAYEE_ACCOUNT_TYPE = "payeeAccountType";

        public static final String IS_REFUND = "isRefund";

        public static final String TYPE = "type";
    }

    public class CurrencyAccount
    {
        public static final String ACCOUNT_ID = "accountId";

        public static final String ACCOUNT_TYPE_ID = "accountType.accountTypeId";

        public static final String ACCOUNT_TYPE_ID2 = "ACCOUNT_TYPE_ID";
    }

    public class SecurityProperty
    {
        public static final String SECURITY_PROPERTY_ID = "securityPropertyId";

        public static final String SECURITY_PROPERTY_NAME = "securityPropertyName";
    }

    public class Page
    {
        public static final String PAGE_SIZE = "pageSize";

        public static final String PAGE_NUMBER = "pageNumber";

    }

    public class SecurityRule
    {
        public static final String RULE_NAME = "ruleName";

        public static final String LETTER = "letter";

        public static final String FIGURE = "figure";

        public static final String SPECIAL_CHARACTER = "specialCharacter";

        public static final String STATUS = "status";
    }

    public class SecurityType
    {
        public static final String TYPE_NAME = "typeName";

        public static final String MODEL_TYPE = "modelType";

        public static final String ENCRYPT_TYPE = "encryptType";

        public static final String SECURITY_RULE_ID = "secutityRuleId";

        public static final String STATUS = "status";
    }

    public class SecurityCredential
    {
        public static final String IDENTITY_ID = "identityId";

        public static final String IDENTITY_NAME = "identityName";

        public static final String IDENTITY_TYPE = "identityType";

        public static final String SECURITY_ID = "securityId";

        public static final String SECURITY_TYPE_ID = "securityType.securityTypeId";

        public static final String SECURITY_TYPE = "securityType";

        public static final String SECURITY_VALUE = "securityValue";

        public static final String STATUS = "status";

        public static final String CREATE_DATE = "createDate";

        public static final String CREATE_USER = "createUser";

        public static final String UPDATE_DATE = "updateDate";

        public static final String UPDATE_USER = "updateUser";

        public static final String VALIDITY_DATE = "validityDate";
    }

    public class TransactionReport
    {
        public static final String PROVINCE = "province";

        public static final String PROVINCE_NAME = "provinceName";

        public static final String PAR_VALUE = "parValue";

        public static final String TOTAL_PAR_VALUE = "totalParValue";

        public static final String TOTAL_SALES_FEE = "totalSalesFee";

        public static final String MERCHANT_ID = "merchantId";

        public static final String MERCHANT_NAME = "merchantName";

        public static final String REPORTS_STATUS = "reportsStatus";

        public static final String BEGIN_TIME = "beginTime";

        public static final String END_TIME = "endTime";

        public static final String TRANSACTION_NUM = "transactionNum";

        public static final String MERCHANT_TYPE = "merchantType";

        public static final String CITY = "city";

        public static final String CARRIER_NAME = "carrierName";

        public static final String REPORTS_STATUS_NAME = "reportsStatusName";

        public static final String CARRIER_NO = "carrierNo";

        public static final String CITY_NAME = "cityName";

        public static final String MERCHANT_TYPE_NAME = "merchantTypeName";

        public static final String USER_CODE = "USERCODE";

        public static final String BUSINESS_TYPE = "businessType";

        public static final String MERCHANT_ORDER_NO = "merchantOrderNo";
    }

    public class InterfacePacketsDefinition
    {
        public static final String MERCHANT_ID = "merchantId";

        public static final String ENCODING = "encoding";

        public static final String INTERFACE_TYPE = "interfaceType";

        public static final String PACKET_TYPE = "packetType";

        public static final String CONNECTION_TYPE = "connectionType";

        public static final String STATUS = "status";
    }

    public class InterfaceParam
    {
        public static final String INTERFACE_DEFINITION_ID = "interfaceDefinitionId";

        public static final String SEQUENCE = "sequence";

        public static final String CONNECTION_MODULE = "connectionModule";

        public static final String RESPONSE_RESULT = "responseResult";
    }

    public class AccountType
    {
        public static final String IDENTITY_TYPE = "identityType";

        public static final String ACCOUNT_TYPE_STATUS = "accountTypeStatus";

        public static final String TYPE_MODEL = "typeModel";
    }

    public class TransactionReports
    {
        public static final String PRODUCT_ID = "productId";

        public static final String PROVINCE = "province";

        public static final String PRODUCT_NAME = "productName";

        public static final String PROVINCE_NAME = "provinceName";

        public static final String PAR_VALUE = "parValue";

        public static final String MERCHANT_NAME = "merchantName";

        public static final String REPORTS_STATUS = "reportsStatus";

        public static final String BEGIN_TIME = "beginTime";

        public static final String END_TIME = "endTime";

        public static final String MERCHANT_TYPE = "merchantType";

        public static final String MERCHANT_TYPE_NAME = "merchantTypeName";

        public static final String CITY = "city";

        public static final String CARRIER_NO = "carrierNo";

    }

    public class ProfitReports
    {
        public static final String PRODUCT_ID = "productId";

        public static final String PROVINCE = "province";

        public static final String PRODUCT_NAME = "productName";

        public static final String PROVINCE_NAME = "provinceName";

        public static final String PAR_VALUE = "parValue";

        public static final String MERCHANT_NAME = "merchantName";

        public static final String MERCHANT_ID = "merchantId";

        public static final String BEGIN_TIME = "beginTime";

        public static final String END_TIME = "endTime";

        public static final String MERCHANT_TYPE = "merchantType";

        public static final String MERCHANT_TYPE_NAME = "merchantTypeName";

        public static final String CITY = "city";

        public static final String CARRIER_NO = "carrierNo";

        public static final String CITY_NAME = "cityName";

        public static final String CARRIER_NAME = "carrierName";
    }

    public class AccountReport
    {
        public static final String IDENTITY_TYPE = "identityType";

        public static final String IDENTITY_NAME = "identityName";

        public static final String ACCOUNT_ID = "accountId";

        public static final String ACCOUNT_TYPE_ID = "accountTypeId";

        public static final String ACCOUNT_TYPE_NAME = "accountTypeName";

        public static final String BEGIN_TIME = "beginTime";

        public static final String END_TIME = "endTime";

        public static final String IDENTITY_TYPE_NAME = "identityTypeName";
    }

    public class ReportType
    {
        public static final String REPORT_TYPE_ID = "reportTypeId";

        public static final String REPORT_FILE_NAME = "reportFileName";

        public static final String REPORT_TYPE_NAME = "reportTypeName";

        public static final String REPORT_PROPERTY_NAME = "reportPropertyName";

        public static final String REPORT_PROPERTY_FIELD_NAME = "reportPropertyFieldName";

        public static final String REPORT_PROPERTY_TYPE = "reportPropertyType";

        public static final String REPORT_PROPERTY_NUM = "reportPropertyNum";
    }

    public class AccountSettlement
    {
        public static final String STATUS = "status";

        public static final String ACCOUNT_ID = "accountId";

        public static final String ACCOUNT_TYPE = "accountTypeTd";

        public static final String MERCHANT_ID = "merchantId";

        public static final String MERCHANT_NAME = "merchantName";

        public static final String SETTLEMENT_BEGIN_DATE = "settlementBeginDate";

        public static final String CREATE_DATE = "createDate";
    }

    public class RebateProduct
    {
        public static final String REBATE_PRODUCT_ID = "rebateProductId";

        public static final String MERCHANT_ID = "merchantId";
    }

    public class RebateTradingVolume
    {
        public static final String REBATE_TRADING_VOLUME_ID = "rebateTradingVolumeID";

        public static final String REBATE_RULE_ID = "rebateRuleId";

        public static final String TRADING_VOLUME_LOW = "tradingVolumeLow";

        public static final String TRADING_VOLUME_HIGH = "tradingVolumeHigh";
    }

    public class RebateRecord
    {
        public static final String ID = "id";

        public static final String MERCHANT_ID = "merchantId";

        public static final String REBATE_MERCHANT_ID = "rebateMerchantId";

        public static final String REBATE_TYPE = "rebateType";

        public static final String STATUS = "status";

        public static final String REBATE_PRODUCT_ID = "rebateProductId";

        public static final String REBATE_RULE_ID = "rebateRuleId";

        public static final String CREATE_DATE = "createDate";

        public static final String REBATE_DATE = "rebateDate";

        public static final String BEGIN_DATE = "beginDate";

        public static final String END_DATE = "endDate";
    }

    public class InterfacePacketTypeConf
    {
        public static final String MERCHANT_ID = "merchantId";

        public static final String INTERFACE_TYPE = "interfaceType";

        public static final String CONNECTION_MODULE = "connectionModule";
    }

    public class MerchantRequest
    {
        public static final String MERCHANT_ID = "merchantId";

        public static final String INTERFACE_TYPE = "interfaceType";

        public static final String TIME_DIFFERENCE_LOW = "timeDifferenceLow";

        public static final String TIME_DIFFERENCE_HIGH = "timeDifferenceHigh";
    }

    public class ProductOperationDetail
    {
        public static final String PRODUCT_OPERATION_HISTORY_ID = "productOperationHistoryId";

        public static final String MERCHANT_TYPE = "merchantType";
    }

    public class OrderStatusTransfer
    {
        public static final String ACTION_NAME = "actionName";

        public static final String OLD_ORDER_STATUS = "oldOrderStatus";

        public static final String NEW_ORDER_STATUS = "newOrderStatus";
    }

    public class DeliveryQueryStatusTransfer
    {
        public static final String OLD_QUERY_STATUS = "oldQueryStatus";

        public static final String NEW_QUERY_STATUS = "newQueryStatus";
    }

    public class DeliveryStatusTransfer
    {
        public static final String OLD_DELIVERY_STATUS = "oldDeliveryStatus";

        public static final String NEW_DELIVERY_STATUS = "newDeliveryStatus";
    }

    public class NotifyStatusTransfer
    {
        public static final String OLD_NOTIFY_STATUS = "oldNotifyStatus";

        public static final String NEW_NOTIFY_STATUS = "newNotifyStatus";
    }

    public class AccountStatusDefenders
    {
        public static final String TYPE_MODEL = "typeModel";

        public static final String ORIGINAL_ACCOUNT_STATUS = "originalAccountStatus";

        public static final String TARGET_ACCOUNT_STATUS = "targetAccountStatus";
    }

    public class BatchOrderRequestHandler
    {
        public static final String ORDER_REQUEST_TIME = "orderRequestTime";

        public static final String BORH_ID = "id";

        public static final String ORDER_STATUS = "orderStatus";

        public static final String UP_FILE = "upFile";
    }

    public class ProfitImputation
    {
        public static final String ACCOUNT_ID = "accountId";

        public static final String IMPUTATION_BEGIN_DATE = "imputationBeginDate";

        public static final String IMPUTATION_END_DATE = "imputationEndDate";

        public static final String UPDATE_BEGIN_DATE = "updateBeginDate";

        public static final String UPDATE_END_DATE = "updateEndDate";

        public static final String IMPUTATION_STATUS = "imputationStatus";

        public static final String PROFITACCOUNTID = "profitAccountId";

        public static final String MERCHANT_NAME = "merchantName";

        public static final String PROFITIMPUTATIONID = "profitImputationId";
    }

    public class ResponseCodeTranslation
    {
        public static final String INTERFACE_TYPE = "interfaceType";

        public static final String ERROR_CODE = "errorCode";
    }

    public class AgentTransactionReport
    {
        public static final String PRODUCT_ID = "productId";

        public static final String PROVINCE = "province";

        public static final String PRODUCT_NAME = "productName";

        public static final String PROVINCE_NAME = "provinceName";

        public static final String PAR_VALUE = "parValue";

        public static final String MERCHANT_NAME = "merchantName";

        public static final String MERCHANT_ID = "merchantId";

        public static final String BEGIN_TIME = "beginTime";

        public static final String END_TIME = "endTime";

        public static final String MERCHANT_TYPE = "merchantType";

        public static final String CITY = "city";

        public static final String CARRIER_NO = "carrierNo";
    }

    public class SupplyTransactionReport
    {
        public static final String PRODUCT_ID = "productId";

        public static final String PROVINCE = "province";

        public static final String PRODUCT_NAME = "productName";

        public static final String PROVINCE_NAME = "provinceName";

        public static final String PAR_VALUE = "parValue";

        public static final String MERCHANT_NAME = "merchantName";

        public static final String MERCHANT_ID = "merchantId";

        public static final String BEGIN_TIME = "beginTime";

        public static final String END_TIME = "endTime";

        public static final String MERCHANT_TYPE = "merchantType";

        public static final String CITY = "city";

        public static final String CARRIER_NO = "carrierNo";

        public static final String REPORTS_STATUS = "reportsStatus";
    }

    public class IdentityStatusTransfer
    {
        public static final String IDENTITY_STATUS_OLD = "oldIdentityStatus";

        public static final String IDENTITY_STATUS_NEW = "newIdentityStatus";

        public static final String IDENTITY_TYPE = "identityType";
    }

    public class SecurityCredentialStatusTransfer
    {
        public static final String IDENTITY_OLD = "oldStatus";

        public static final String IDENTITY_NEW = "newStatus";
    }

    public class IdentityAccountRole
    {
        public static final String RELATION = "relation";

        public static final String IDENTITY_ID = "identityId";

        public static final String IDENTITY_TYPE = "identityType";

        public static final String ACCOUNT_ID = "accountId";

        public static final String ACCOUNT_TYPE = "accountType";
    }

    public class CardAccount
    {
        public static final String STATUS = "status";
    }

    public class StatusEventControl
    {
        public static final String ACTIVE_TRANSFER_TYPE = "activeTransferType";

        public static final String ACTIVE_STATUS_TRANSAFER_ID = "activeStatusTransferId";
    }

    public class UriTransactionMapping
    {
        public static final String ACTION_NAME = "actionName";
    }

    public class CurrencyAccountAddCashRecord
    {
        public static final String ID = "id";

        public static final String MERCHANT_ID = "merchantId";

        public static final String MERCHANT_NAME = "merchantName";

        public static final String OPERATOR_NAME = "operatorName";

        public static final String VERIFY_TIME = "verifyTime";

        public static final String APPLY_TIME = "applyTime";

        public static final String APPLY_TIME_SQL = "apply_time";

        public static final String VERIFY_STATUS = "verifyStatus";
    }

    public class CurrencyAccountBalanceHistory
    {
        public static final String TRANSACTION_ID = "transactionId";

        public static final String TRANSACTION_NO = "transactionNO";

        public static final String TYPE = "type";

        public static final String ACCOUNT_ID = "accountId";

        public static final String ACCOUNT_TYPE = "accountType";

        public static final String IDENTITY_NAME = "identityName";

        public static final String BEGIN_DATE = "beginDate";

        public static final String END_DATE = "endDate";

    }

    public class AccountFundChange
    {
        public static final String TRANSACTION_NO = "transactionNo";

        public static final String ACCOUNT_CHANGE_TYPE = "accountChangeType";

        public static final String TRANSACTION_CHANGE_TYPE = "transactionChangeType";

        public static final String CHANGEDATE = "changeDate";

        public static final String ACCOUNT_TYPE = "accountType";

        public static final String IDENTITY_ID = "identityId";

        public static final String IDENTITY_NAME = "identityName";

        public static final String BEGIN_DATE = "beginDate";

        public static final String END_DATE = "endDate";

        public static final String TRANSACTION_TYPE = "transactionType";

        public static final String RELATION = "relation";

        public static final String LOGTYPE = "logType";

        public static final String ACCOUNT_ID = "accountId";

    }

    public class SPAccount
    {

        public static final String ACCOUNT_ID = "accountId";

        public static final String ACCOUNT_TYPE = "accountType";

        public static final String IDENTITY_NAME = "identityName";

        public static final String IDENTITY_TYPE = "identityType";

        public static final String SP_NAME = "spName";

        public static final String MERCHANT_NAME = "merchantName";

        public static final String TYPE_MODEL = "typeModel";

        public static final String RELATION = "relation";

    }

    public class NumSection
    {
        public static final String SECTION_ID = "sectionId";
    }

    public class Blacklist
    {
        public static final String BLACKLIST_ID = "blacklistId";

        public static final String BLACKLIST_NO = "blacklistNo";

        public static final String BUSINESS_TYPE = "businessType";
    }

    public class RefundReport
    {
        public static final String PRODUCT_ID = "productId";

        public static final String PROVINCE = "province";

        public static final String PRODUCT_NAME = "productName";

        public static final String PROVINCE_NAME = "provinceName";

        public static final String PAR_VALUE = "parValue";

        public static final String MERCHANT_NAME = "merchantName";

        public static final String MERCHANT_ID = "merchantId";

        public static final String BEGIN_TIME = "beginTime";

        public static final String END_TIME = "endTime";

        public static final String MERCHANT_TYPE = "merchantType";

        public static final String MERCHANT_TYPE_NAME = "merchantTypeName";

        public static final String CITY = "city";

        public static final String CARRIER_NO = "carrierNo";

        public static final String CITY_NAME = "cityName";

        public static final String CARRIER_NAME = "carrierName";

        public static final String REFUND_ORDER_NO = "refundOrderNo";

        public static final String MANUAL_AUDIT_DATE = "manualAuditDate";
    }

    public class MerchantBindTimes
    {
        public static final String MERCHANT_ID = "merchantId";
    }

    public class OrderApplyOperateHistory
    {
        // public static final String _OPERATION_TYPE = "1";
        // public static final String OPERATION_TYPE = "2";
        // public static final String OPERATION_TYPE = "3";
        // public static final String OPERATION_TYPE = "4";
        // public static final String OPERATION_TYPE = "5";
        // public static final String OPERATION_TYPE = "6";

    }

    public class BatchJob
    {
        public static final String FILENAME = "fileName";
        public static final String STATUS = "status";
    }

    public class BatchJobDetail
    {
        public static final String ORDER_NO = "order_no";
        public static final String BATCH_ID = "batch_id";
        public static final String SERIAL_NUM = "serial_num";
        public static final String STATUS = "status";
        
    }
}
