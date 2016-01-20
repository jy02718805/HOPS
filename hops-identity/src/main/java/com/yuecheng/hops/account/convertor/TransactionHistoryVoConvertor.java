/*
 * 文件名：AccountVoConvertor.java 版权：Copyright by www.365haoyou.com 描述： 修改人：Administrator
 * 修改时间：2014年10月15日 跟踪单号： 修改单号： 修改内容：
 */

package com.yuecheng.hops.account.convertor;


import com.yuecheng.hops.account.entity.TransactionHistory;
import com.yuecheng.hops.account.entity.vo.TransactionHistoryVo;


/**
 * 交易历史记录对象转换接口
 * 
 * @author Administrator
 * @version 2014年10月16日
 * @see AccountVoConvertor
 * @since
 */
public interface TransactionHistoryVoConvertor
{
    /**
     * 转换
     * 
     * @param th
     * @return
     * @see
     */
    TransactionHistoryVo execute(TransactionHistory th);
}
