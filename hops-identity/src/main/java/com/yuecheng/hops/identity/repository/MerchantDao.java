package com.yuecheng.hops.identity.repository;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.common.enump.MerchantLevel;
import com.yuecheng.hops.common.enump.MerchantType;
import com.yuecheng.hops.identity.entity.merchant.Merchant;


public interface MerchantDao extends IdentityDao<Merchant>,MerchantDaoSql
{
    @Query("select m from  Merchant m  where m.merchantType= :type")
    public List<Merchant> listMerchantByType(@Param("type")
    String type);

    @Query("select m from  Merchant m  where m.merchantName like %:merchantName% and m.merchantType= :type")
    public List<Merchant> getMerchantByMerchantName(@Param("merchantName")
    String merchantName, @Param("type")
    MerchantType type);

    @Query("select m from  Merchant m  where m.merchantName like %:merchantName% ")
    public List<Merchant> getAllMerchantByMerchantNameFuzzy(@Param("merchantName")
    String merchantName);

    @Query("select m from  Merchant m  where m.merchantName like %:merchantName% and m.merchantType= :type  and m.merchantLevel!=:level")
    public List<Merchant> getPMerchantByMerchantName(@Param("merchantName")
    String merchantName, @Param("type")
    MerchantType type, @Param("level")
    MerchantLevel level);

    @Query("select m from  Merchant m  where m.parentIdentityId =:parentIdentityId")
    public List<Merchant> getMerchantByParentMerchantId(@Param("parentIdentityId")
    Long parentIdentityId);

    @Query("select m from Merchant m where m.merchantLevel=:merchantLevel and m.identityStatus.status=:status order by m.id")
    public List<Merchant> selectList(@Param("merchantLevel")
    MerchantLevel merchantLevel, @Param("status")
    String status);

    @Query("select m from Merchant m where m.id=:id order by m.id")
    public List<Merchant> selectMerchantListByID(@Param("id")
    Long id);

}
