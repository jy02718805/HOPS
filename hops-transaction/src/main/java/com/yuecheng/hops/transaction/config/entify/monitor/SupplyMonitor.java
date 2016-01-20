package com.yuecheng.hops.transaction.config.entify.monitor;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "up_monitor")
public class SupplyMonitor implements Serializable
{

    private static final long serialVersionUID = -5692703934466148771L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UpMonitorIdSeq")
    @SequenceGenerator(name = "UpMonitorIdSeq", sequenceName = "UP_MONITOR_ID_SEQ")
    @Column(name = "id")
    private Long              id;

    @Column(name = "product_id")
    private Long              productId;

    @Column(name = "business_no")
    private Long              businessNo;

    @Column(name = "merchant_id")
    private Long              merchantId;

    @Column(name = "province_no", length = 10)
    private String            provinceNo;

    @Column(name = "carrier_no", length = 10)
    private String            carrierNo;

    @Column(name = "stat_start_time")
    private Date              stat_start_time;

    @Column(name = "stat_end_time")
    private Date              stat_end_time;

    @Column(name = "total_count")
    private Long              total_count;

    @Column(name = "fail_count")
    private Long              fail_count;

    @Column(name = "success_count")
    private Long              success_count;

    @Column(name = "ing_count")
    private Long              ing_count;

    @Column(name = "ing_1min_count")
    private Long              ing_1min_count;

    @Column(name = "ing_5min_count")
    private Long              ing_5min_count;

    @Column(name = "ing_10min_count")
    private Long              ing_10min_count;

    @Column(name = "finish_1min_count")
    private Long              finish_1min_count;

    @Column(name = "finish_5min_count")
    private Long              finish_5min_count;

    @Column(name = "finish_10min_count")
    private Long              finish_10min_count;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getBusinessNo()
    {
        return businessNo;
    }

    public void setBusinessNo(Long businessNo)
    {
        this.businessNo = businessNo;
    }

    public Long getMerchantId()
    {
        return merchantId;
    }

    public void setMerchantId(Long merchantId)
    {
        this.merchantId = merchantId;
    }

    public String getProvinceNo()
    {
        return provinceNo;
    }

    public void setProvinceNo(String provinceNo)
    {
        this.provinceNo = provinceNo;
    }

    public String getCarrierNo()
    {
        return carrierNo;
    }

    public void setCarrierNo(String carrierNo)
    {
        this.carrierNo = carrierNo;
    }

    public Date getStat_start_time()
    {
        return stat_start_time;
    }

    public void setStat_start_time(Date stat_start_time)
    {
        this.stat_start_time = stat_start_time;
    }

    public Date getStat_end_time()
    {
        return stat_end_time;
    }

    public void setStat_end_time(Date stat_end_time)
    {
        this.stat_end_time = stat_end_time;
    }

    public Long getTotal_count()
    {
        return total_count;
    }

    public void setTotal_count(Long total_count)
    {
        this.total_count = total_count;
    }

    public Long getFail_count()
    {
        return fail_count;
    }

    public void setFail_count(Long fail_count)
    {
        this.fail_count = fail_count;
    }

    public Long getSuccess_count()
    {
        return success_count;
    }

    public void setSuccess_count(Long success_count)
    {
        this.success_count = success_count;
    }

    public Long getIng_count()
    {
        return ing_count;
    }

    public void setIng_count(Long ing_count)
    {
        this.ing_count = ing_count;
    }

    public Long getIng_1min_count()
    {
        return ing_1min_count;
    }

    public void setIng_1min_count(Long ing_1min_count)
    {
        this.ing_1min_count = ing_1min_count;
    }

    public Long getIng_5min_count()
    {
        return ing_5min_count;
    }

    public void setIng_5min_count(Long ing_5min_count)
    {
        this.ing_5min_count = ing_5min_count;
    }

    public Long getIng_10min_count()
    {
        return ing_10min_count;
    }

    public void setIng_10min_count(Long ing_10min_count)
    {
        this.ing_10min_count = ing_10min_count;
    }

    public Long getFinish_1min_count()
    {
        return finish_1min_count;
    }

    public void setFinish_1min_count(Long finish_1min_count)
    {
        this.finish_1min_count = finish_1min_count;
    }

    public Long getFinish_5min_count()
    {
        return finish_5min_count;
    }

    public void setFinish_5min_count(Long finish_5min_count)
    {
        this.finish_5min_count = finish_5min_count;
    }

    public Long getFinish_10min_count()
    {
        return finish_10min_count;
    }

    public void setFinish_10min_count(Long finish_10min_count)
    {
        this.finish_10min_count = finish_10min_count;
    }

    @Override
    public String toString()
    {
        return "UpMonitor [id=" + id + ", businessNo=" + businessNo + ", merchantId=" + merchantId
               + ", provinceNo=" + provinceNo + ", carrierNo=" + carrierNo + ", stat_start_time="
               + stat_start_time + ", stat_end_time=" + stat_end_time + ", total_count="
               + total_count + ", fail_count=" + fail_count + ", success_count=" + success_count
               + ", ing_count=" + ing_count + ", ing_1min_count=" + ing_1min_count
               + ", ing_5min_count=" + ing_5min_count + ", ing_10min_count=" + ing_10min_count
               + ", finish_1min_count=" + finish_1min_count + ", finish_5min_count="
               + finish_5min_count + ", finish_10min_count=" + finish_10min_count + "]";
    }

}
