package com.yuecheng.hops.transaction.service.order.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.exception.ExceptionUtil;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.transaction.basic.entity.Delivery;
import com.yuecheng.hops.transaction.basic.entity.Order;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderExportVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderNativeVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderParameterVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderStatisticsVo;
import com.yuecheng.hops.transaction.basic.entity.vo.OrderVo;
import com.yuecheng.hops.transaction.basic.repository.DeliveryJpaDao;
import com.yuecheng.hops.transaction.basic.repository.OrderDao;
import com.yuecheng.hops.transaction.basic.repository.OrderJpaDao;
import com.yuecheng.hops.transaction.service.delivery.DeliveryManagement;
import com.yuecheng.hops.transaction.service.order.OrderPageQuery;


@Service("orderPageQuery")
public class OrderPageQueryImpl implements OrderPageQuery
{

    private static Logger logger = LoggerFactory.getLogger(OrderPageQueryImpl.class);

    @Autowired
    private OrderJpaDao orderJpaDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private DeliveryManagement deliveryManagement;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private DeliveryJpaDao deliveryJpaDao;

    @Override
    public YcPage<OrderNativeVo> queryOrders(int pageNumber, int pageSize,
                                             OrderParameterVo orderParameter)
    {
        YcPage<OrderNativeVo> ycPage = orderDao.queryOrders(pageNumber, pageSize, orderParameter);
        return ycPage;
    }

    @Override
    public YcPage<OrderVo> queryPartSuccessOrders(OrderParameterVo orderParameter, int pageNumber,
                                                  int pageSize)
    {
        YcPage<OrderVo> ycPage = orderDao.queryPartSuccessOrders(orderParameter, pageNumber,
            pageSize);
        return ycPage;
    }

    @Override
    public YcPage<OrderNativeVo> queryTimeOutOrders(OrderParameterVo orderParameter, int pageNumber,
                                              int pageSize)
    {
        YcPage<OrderNativeVo> ycPage = orderDao.queryTimeOutOrders(orderParameter, pageNumber, pageSize);
        return ycPage;
    }

    @Override
    public YcPage<OrderVo> queryFakeOrders(Map<String, Object> searchParams, boolean isFake,
                                           int pageNumber, int pageSize)
    {
        YcPage<Order> page = orderDao.queryFakeOrders(searchParams, pageNumber, pageSize);
        YcPage<OrderVo> ycPage = new YcPage<OrderVo>();
        List<Order> orderList = page.getList();
        List<OrderVo> result = new ArrayList<OrderVo>();
        if (isFake)
        {
            result = changeOrderToFakeOrder(orderList);
        }
        else
        {
            result = orderDao.converDeliveryInfoToOrders(orderList);
        }

        ycPage.setList(result);
        ycPage.setCountTotal((int)page.getCountTotal());
        ycPage.setPageTotal(page.getPageTotal());
        return ycPage;
    }

    @Override
    public YcPage<OrderVo> queryManualAuditOrders(OrderParameterVo orderParameter, int pageNumber,
                                                  int pageSize)
    {

        YcPage<OrderVo> ycPage = orderDao.queryManualAuditOrders(orderParameter, pageNumber,
            pageSize);
        return ycPage;
    }

    /*
     * API、客服、商户PORTAL 将正常订单转换成预成功订单所对应的状态
     */
    public List<OrderVo> changeOrderToFakeOrder(List<Order> orderList)
    {
        List<OrderVo> result = new ArrayList<OrderVo>();
        try
        {
            for (Order order : orderList)
            {
                OrderVo orderVo = new OrderVo();
                BeanUtils.copyProperties(orderVo, order);
                orderVo.setOrderRequestTime(order.getOrderRequestTime());
                orderVo.setOrderFinishTime(order.getOrderFinishTime());
                if (Constant.OrderStatus.PRE_SUCCESS_STATUS_WAIT == order.getPreSuccessStatus())
                {
                    orderVo.setOrderStatus(order.getOrderStatus());
                    orderVo.setOrderWaitFee(order.getProductFace().subtract(
                        order.getOrderSuccessFee()));
                    orderVo.setOrderSuccessFee(order.getOrderSuccessFee());
                }
                else if (Constant.OrderStatus.PRE_SUCCESS_STATUS_DONE == order.getPreSuccessStatus())
                {
                    orderVo.setOrderStatus(Constant.OrderStatus.SUCCESS);
                    orderVo.setOrderWaitFee(new BigDecimal("0"));
                    orderVo.setOrderSuccessFee(order.getProductFace());
                }

                List<Delivery> deliverys = deliveryManagement.findDeliveryByOrderNo(order.getOrderNo());
                if (deliverys.size() > 0)
                {
                    Merchant merchant = merchantService.queryMerchantById(deliverys.get(0).getMerchantId());
                    orderVo.setSupplierName(merchant.getMerchantName());
                }
                result.add(orderVo);
            }
        }
        catch (Exception e)
        {
            logger.error("changeOrderToFakeOrder 订单转换失败！" + ExceptionUtil.getStackTraceAsString(e));
        }
        return result;
    }

    @Override
    public OrderStatisticsVo statisticsOrderInfo(OrderParameterVo orderParameter, String type)
    {
        OrderStatisticsVo orderStatisticsVo = orderDao.statisticsOrderInfo(orderParameter, type);

        return orderStatisticsVo;
    }

    @Override
    public BigDecimal getOrderAmtSumByIdentityId(Long identityId, int orderStatus,
                                                 int deliveryStatus, Date beginTime, Date endTime)
    {
        // TODO Auto-generated method stub
        BigDecimal amt = orderDao.getOrderAmtSumByIdentityId(identityId, orderStatus,
            deliveryStatus, beginTime, endTime);
        return amt;
    }

    @Override
    public YcPage<OrderExportVo> queryOrderForExport(Map<String, Object> searchParams,
                                                     int pageNumber, int pageSize,
                                                     String carrierInfo, String province,
                                                     String parValue, String downMerchant,
                                                     Integer status, String sortType,
                                                     String beginDate, String endDate,
                                                     Integer notifyStatus,
                                                     String preSuccessStatus, String usercode,
                                                     String orderNo, String merchantOrderNo,
                                                     String businessType, boolean isFake)
    {
        YcPage<OrderExportVo> ycPage = orderDao.queryOrderForExport(searchParams, pageNumber,
            pageSize, carrierInfo, province, parValue, downMerchant, status, sortType, beginDate,
            endDate, notifyStatus, preSuccessStatus, usercode, orderNo, merchantOrderNo,
            businessType, isFake);
        return ycPage;
    }

}