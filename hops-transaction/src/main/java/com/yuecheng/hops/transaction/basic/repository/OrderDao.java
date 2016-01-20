/*
 * 文件名：OrderSqlDao.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator 修改时间：2014年11月24日
 * 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.transaction.basic.repository;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderExportVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderNativeVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderParameterVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderStatisticsVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderVo;


public interface OrderDao
{
    /**
     * 查询订单
     */
    YcPage<OrderNativeVo> queryOrders(int pageNumber, int pageSize, OrderParameterVo orderParameter);

    /**
     * 查询部分成功
     */

    YcPage<OrderVo> queryPartSuccessOrders(OrderParameterVo orderParameter, int pageNumber,
                                           int pageSize);

    YcPage<OrderNativeVo> queryTimeOutOrders(OrderParameterVo orderParameter, int pageNumber,
                                       int pageSize);

    YcPage<OrderVo> queryManualAuditOrders(OrderParameterVo orderParameter, int pageNumber,
                                           int pageSize);

    List<OrderVo> converDeliveryInfoToOrders(List<Order> orderList);

    List<Order> findOrdersByParams(Integer orderStatus);

    YcPage<Order> queryFakeOrders(Map<String, Object> searchParams, int pageNumber, int pageSize);

    OrderStatisticsVo statisticsOrderInfo(OrderParameterVo orderParameter, String type);

    BigDecimal getOrderAmtSumByIdentityId(Long identityId, int orderStatus, int deliveryStatus,
                                          Date beginTime, Date endTime);

    YcPage<OrderExportVo> queryOrderForExport(Map<String, Object> searchParams, int pageNumber,
                                              int pageSize, String carrierInfo, String province,
                                              String parValue, String downMerchant,
                                              Integer status, String sortType, String beginDate,
                                              String endDate, Integer notifyStatus,
                                              String preSuccessStatus, String usercode,
                                              String orderNo, String merchantOrderNo,
                                              String businessType, boolean isFake);
}
