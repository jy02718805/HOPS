/**
 * @Title: BatchOrderRequestHandlerManagementImpl.java
 * @Package com.yuecheng.hops.transaction.service.order.impl
 * @Description: TODO Copyright: Copyright (c) 2011 Company:湖南跃程网络科技有限公司
 * @author 肖进
 * @date 2014年8月14日 上午10:53:12
 * @version V1.0
 */

package com.yuecheng.hops.batch.service.order.impl;


import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.batch.entity.BatchJobDetail;
import com.yuecheng.hops.batch.repository.order.BatchJobDetailDao;
import com.yuecheng.hops.batch.repository.sql.BatchJobDetailSqlDao;
import com.yuecheng.hops.batch.service.order.BatchJobDetailService;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.common.utils.BeanUtils;


@Service("batchJobDetailService")
public class BatchJobDetailServiceImpl implements BatchJobDetailService
{
    public static Logger logger = LoggerFactory.getLogger(BatchJobDetailServiceImpl.class);

    @Autowired
    private BatchJobDetailDao batchJobDetailDao;

    @Autowired
    private BatchJobDetailSqlDao batchJobDetailSqlDao;

    @Override
    public YcPage<BatchJobDetail> queryPageBatchJobDetail(Map<String, Object> searchParams,
                                                          int pageNumber, int pageSize)
    {
        // TODO Auto-generated method stub

        YcPage<BatchJobDetail> ycPage = batchJobDetailSqlDao.queryPageBatchJobDetail(searchParams,
            pageNumber, pageSize);

        return ycPage;
    }

    @Override
    public boolean saveBatchJobDetails(List<BatchJobDetail> batchJobDetails)
    {
        // TODO Auto-generated method stub
        boolean bl = false;
        try
        {
            batchJobDetails = (List<BatchJobDetail>)batchJobDetailDao.save(batchJobDetails);
            if (BeanUtils.isNotNull(batchJobDetails))
            {
                bl = true;
            }
            else
            {
                bl = false;
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        return bl;
    }

    @Override
    public List<BatchJobDetail> getJobDetailsByBatchId(Long batchId, int startIndex, int endIndex)
    {
        return batchJobDetailDao.getJobDetailsByBatchId(batchId, startIndex, endIndex);
    }

    @Override
    @Transactional
    public List<BatchJobDetail> updateJobDetailsStatus(List<BatchJobDetail> batchJobDetails,
                                                       Integer targeStatus)
    {
        List<BatchJobDetail> batchJobDetailList = new ArrayList<BatchJobDetail>();
        for (int i = 0; i < batchJobDetails.size(); i++ )
        {
            BatchJobDetail batchJobDetail = batchJobDetails.get(i);
            batchJobDetail.setStatus(targeStatus);
            batchJobDetailList.add(batchJobDetail);
        }
        batchJobDetailList = (ArrayList)batchJobDetailDao.save(batchJobDetailList);
        return batchJobDetailList;
    }

    @Override
    @Transactional
    public void updateJobDetail(Long id, Long batchId, Integer targeStatus, Long orderNo,
                                String rmk)
    {
        if (Constant.BatchDetail.SUCCESS == targeStatus)
        {
            batchJobDetailDao.updateBatchJobDetail(id, batchId, targeStatus, orderNo, rmk);
        }
        else
        {
            batchJobDetailDao.updateBatchJobDetail(id, batchId, targeStatus, rmk);
        }
    }
}
