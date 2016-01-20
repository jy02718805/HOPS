package com.yuecheng.hops.product.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.product.entity.relation.AgentProductRelation;


@Service
public interface AgentProductRelationDao extends PagingAndSortingRepository<AgentProductRelation, Long>, JpaSpecificationExecutor<AgentProductRelation>
{
    @Query("select dpr from AgentProductRelation dpr where dpr.identityId=:identityId and dpr.identityType=:identityType and dpr.status=:status")
    public List<AgentProductRelation> getAllProductByMerchant(@Param("identityId") Long identityId,
                                                              @Param("identityType") String identityType,
                                                              @Param("status") String status);

    @Modifying
    @Transactional
    @Query("update AgentProductRelation dpr set dpr.isRoot=:isRoot where dpr.productId=:productId")
    public int updateIsRootByProductId(@Param("isRoot") Boolean isRoot,
                                       @Param("productId") Long productId);
    
    @Modifying
    @Transactional
    @Query("update AgentProductRelation dpr set dpr.isRoot=:isRoot , dpr.defValue=:defValue where dpr.productId=:productId")
    public int updateStatusByProductId(@Param("isRoot") Boolean isRoot,@Param("defValue") Boolean defValue,
                                       @Param("productId") Long productId);

    @Query("select t from AgentProductRelation t where t.identityId=:identityid or t.identityName=:identityname")
    public List<AgentProductRelation> selectProductInfoByIdentity(@Param("identityid") long identityid,
                                                                  @Param("identityname") String identityname);
    
    
    @Modifying
    @Transactional
    @Query("update AgentProductRelation dpr set dpr.productName=:productName where dpr.productId=:productId")
    public int updateProductNameById(@Param("productId") Long productId,
                                       @Param("productName") String  productName);
    

    @Modifying
    @Transactional 
    @Query("update AgentProductRelation dpr set dpr.status=:status where (dpr.carrierName=:carrierName or :carrierName is null) and (dpr.province=:province or :province is null) and (dpr.city=:city or :city is null) and (dpr.identityType=:identityType or :identityType is null) and (dpr.identityId=:identityId or :identityId is null) and (dpr.businessType=:businessType or :businessType is null)")
    public int updateStatusByParamAll(@Param("status") String status,
                                       @Param("carrierName") String carrierName,
                                       @Param("province") String province,
                                       @Param("city") String city,
                                       @Param("identityType") String identityType,
                                       @Param("identityId") Long identityId,
                                       @Param("businessType") Integer businessType);
    
}
