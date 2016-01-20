package com.yuecheng.hops.identity.service.merchant;


import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.enump.MerchantLevel;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.identity.entity.merchant.Merchant;
import com.yuecheng.hops.identity.service.IdentityService;


/**
 * 商户实体服务接口
 * 
 * @author xwj
 */
public interface MerchantService extends IdentityService
{

    /**
     * 根据商户类型和状态获取商户列表
     * 
     * @param type
     *            商户类型
     * @param status
     *            商户状态
     * @return
     */
    public List<Merchant> queryAllMerchant(MerchantType type, String status);

    /*
     * 根据商户某一列的值 检索商户列表
     */
    public List<Merchant> queryMerchantByColumn(Object[] columnAndValue);

    /*
     * 根据商户名称查询商户
     */
    public Merchant queryMerchantByMerchantCode(String name);

    /**
     * 根据商户名称模糊搜索查询商户
     * 
     * @param merchantName
     * @return
     */
    public List<Merchant> queryMerchantByMerchantNameFuzzy(String merchantName,
                                                           MerchantType merchantType,
                                                           MerchantLevel level);

    /**
     * 根据商户名称模糊搜索查询商户
     * 
     * @param merchantName
     * @return
     */
    public List<Merchant> queryAllMerchantByMerchantNameFuzzy(String merchantName,MerchantType merchantType);

    /**
     * 根据商户ID获取所有父级
     * 
     * @param merchantName
     * @return
     */
    public List<Merchant> queryParentMerchantByMerchantId(Long merchantId);

    /**
     * 根据商户ID获取所有子级
     * 
     * @param merchantName
     * @return
     */
    public List<Merchant> queryChildMerchantByMerchantId(Long merchantId);

    /**
     * 根据商户级别和状态获取商户列表
     * 
     * @param merchantLevel
     * @param status
     * @return
     */
    public List<Merchant> queryMerchantList(MerchantLevel merchantLevel, String status);

    /**
     * 模糊查询商户
     */
    public List<Merchant> queryMerchantList(Map<String, Object> searchParams);

    /**
     * 通过ID查询商户
     * 
     * @param merchantId
     * @return
     */
    public Merchant queryMerchantById(Long merchantId);

    /**
     * 保存商户
     * 
     * @param merchant
     * @param updateUser
     * @return
     */
    public Merchant saveMerchant(Merchant merchant, String updateUser);

    /**
     * 根据组织ID查询商户列表
     * 
     * @param organizationId
     * @param type
     * @return
     */
    public List<Merchant> queryMerchantByOrganizationId(Long organizationId,
                                                        MerchantType merchantType);

    /**
     * 根据商户名称和商户类型查询商户列表
     * 
     * @param merchantName
     * @param merchantType
     * @return
     */
    public List<Merchant> queryMerchantByMerchantName(String merchantName,
                                                      MerchantType merchantType, String status);
    
    /**
     * 模糊查询商户名称
     */
    public List<Merchant> queryMerchantsByName(String merchantName);
}
