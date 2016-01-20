package com.yuecheng.hops.mportal.web.identity;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.EntityConstant;
import com.yuecheng.hops.common.enump.MerchantLevel;
import com.yuecheng.hops.common.utils.BeanUtils;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.merchant.MerchantService;
import com.yuecheng.hops.mportal.web.BaseControl;


public class MerchantControllerService extends BaseControl
{

    @Autowired
    MerchantService merchantService;

    public ModelMap getMerchantList(ModelMap model)
    {
        List<Merchant> merchantlist0 = merchantService.queryMerchantList(MerchantLevel.Zero,
            Constant.MerchantStatus.ENABLE);
        List<Merchant> merchantlist1 = merchantService.queryMerchantList(MerchantLevel.One,
            Constant.MerchantStatus.ENABLE);
        List<Merchant> merchantlist2 = merchantService.queryMerchantList(MerchantLevel.Two,
            Constant.MerchantStatus.ENABLE);
        List<Merchant> merchantlist3 = merchantService.queryMerchantList(MerchantLevel.Three,
            Constant.MerchantStatus.ENABLE);
        model.addAttribute("merchantlist0", merchantlist0);
        model.addAttribute("merchantlist1", merchantlist1);
        model.addAttribute("merchantlist2", merchantlist2);
        model.addAttribute("merchantlist3", merchantlist3);
        return model;
    }

    public List<Merchant> changeListProt(List<Merchant> merchantList)
    {
        List<Merchant> merchantResultList = new ArrayList<Merchant>();
        int i = merchantList.size();
        while (i > 0)
        {
            i-- ;
            merchantResultList.add(merchantList.get(i));
        }
        return merchantResultList;
    }

    public boolean checkMerchantCode(Merchant merchant)
    {
        List<Merchant> merList = merchantService.queryMerchantByColumn(new Object[] {
            EntityConstant.Merchant.MERCHANT_CODE, merchant.getMerchantCode()});
        if (!BeanUtils.isNullOrEmpty(merList))
        {
            return true;
        }
        return false;
    }

    public boolean checkMerchantName(Merchant merchant)
    {
        List<Merchant> merList = merchantService.queryMerchantByColumn(new Object[] {
            EntityConstant.Merchant.MERCHANT_NAME, merchant.getMerchantName()});
        if (!BeanUtils.isNullOrEmpty(merList))
        {
            return true;
        }
        return false;
    }

}