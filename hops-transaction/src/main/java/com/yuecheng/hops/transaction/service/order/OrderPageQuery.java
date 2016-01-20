package com.yuecheng.hops.transaction.service.order;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderExportVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderNativeVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderParameterVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderStatisticsVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderVo;


public interface OrderPageQuery
{

    public YcPage<OrderNativeVo> queryOrders(int pageNumber, int pageSize,
                                             OrderParameterVo orderParameter);

    public YcPage<OrderVo> queryPartSuccessOrders(OrderParameterVo orderParameter, int pageNumber,
                                                  int pageSize);

    public YcPage<OrderNativeVo> queryTimeOutOrders(OrderParameterVo orderParameter,
                                                    int pageNumber, int pageSize);

    public YcPage<OrderVo> queryFakeOrders(Map<String, Object> searchParams, boolean isFake,
                                           int pageNumber, int pageSize);

    public YcPage<OrderVo> queryManualAuditOrders(OrderParameterVo orderParameter, int pageNumber,
                                                  int pageSize);

    public OrderStatisticsVo statisticsOrderInfo(OrderParameterVo orderParameter, String type);

    public BigDecimal getOrderAmtSumByIdentityId(Long identityId, int orderStatus,
                                                 int deliveryStatus, Date beginTime, Date endTime);

    YcPage<OrderExportVo> queryOrderForExport(Map<String, Object> searchParams, int pageNumber,
                                              int pageSize, String carrierInfo, String province,
                                              String parValue, String downMerchant,
                                              Integer status, String sortType, String beginDate,
                                              String endDate, Integer notifyStatus,
                                              String preSuccessStatus, String usercode,
                                              String orderNo, String merchantOrderNo,
                                              String businessType, boolean isFake);

}