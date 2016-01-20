package com.yuecheng.hops.product.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.product.entity.relation.SupplyProductRelation;


@Service
public interface SupplyProductRelationDao extends PagingAndSortingRepository<SupplyProductRelation, Long>, JpaSpecificationExecutor<SupplyProductRelation>
{
    @Query("select u from SupplyProductRelation u where u.productId = :productId")
    List<SupplyProductRelation> queryUpProductRelationByParames(@Param("productId") Long productId);

    @Query("select u from SupplyProductRelation u where u.productId = :productId and u.identityId = :designIdentityId and u.identityId = 'MERCHANT' ")
    List<SupplyProductRelation> queryUpProductRelationByParames(@Param("productId") Long productId,
                                                                @Param("designIdentityId") String designIdentityId);

    @Query("select t from SupplyProductRelation t where t.identityId=:identityid or t.identityName=:identityname")
    public List<SupplyProductRelation> selectProductInfoByIdentity(@Param("identityid") long identityid,
                                                                   @Param("identityname") String identityname);
    
    @Modifying
    @Transactional
    @Query("update SupplyProductRelation t set t.productName=:productName where t.productId=:productId")
    public int updateProductNameById(@Param("productId") Long productId,
                                       @Param("productName") String  productName);
    
    @Modifying
    @Transactional 
    @Query("update SupplyProductRelation spr set spr.status=:status where (spr.carrierName=:carrierName or :carrierName is null) and (spr.province=:province or :province is null) and (spr.city=:city or :city is null) and (spr.identityType=:identityType or :identityType is null) and (spr.identityId=:identityId or :identityId is null) and (spr.businessType=:businessType or :businessType is null)")
    public int updateStatusByParamAll(@Param("status") String status,
                                       @Param("carrierName") String carrierName,
                                       @Param("province") String province,
                                       @Param("city") String city,
                                       @Param("identityType") String identityType,
                                       @Param("identityId") Long identityId,@Param("businessType") Integer businessType);
}
