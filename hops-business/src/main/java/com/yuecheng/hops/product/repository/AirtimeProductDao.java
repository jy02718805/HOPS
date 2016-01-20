package com.yuecheng.hops.product.repository;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.product.entity.airtimes.AirtimeProduct;


@Service
public interface AirtimeProductDao extends PagingAndSortingRepository<AirtimeProduct, Long>, JpaSpecificationExecutor<AirtimeProduct>
{
    // 修改话费产品的状态
    @Modifying
    @Transactional
    @Query("update AirtimeProduct a set a.productStatus=:status where a.productId=:productId")
    public int updateStatusByProductId(@Param("status") String status,
                                       @Param("productId") Long productId);

    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public AirtimeProduct queryAirtimeProductByProperty(@Param("carrierName") String carrierName);

    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.province=null and air.city=null and air.parValue=:parValue and air.businessType=:businessType and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public AirtimeProduct queryAirtimeProductByProperty(@Param("carrierName") String carrierName,
                                                        @Param("parValue") BigDecimal parValue,
                                                        @Param("businessType") int businessType);

    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.province = :province and air.city=null and air.parValue=:parValue and air.businessType=:businessType and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public AirtimeProduct queryAirtimeProductByProperty(@Param("carrierName") String carrierName,
                                                        @Param("province") String province,
                                                        @Param("parValue") BigDecimal parValue,
                                                        @Param("businessType") int businessType);

    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.province = :province and air.parValue=:parValue and air.businessType=:businessType and air.city=:city and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public AirtimeProduct queryAirtimeProductByProperty(@Param("carrierName") String carrierName,
                                                        @Param("province") String province,
                                                        @Param("parValue") BigDecimal parValue,
                                                        @Param("city") String city,
                                                        @Param("businessType") int businessType);



    @Query("select air from AirtimeProduct air where air.parentProductId=:parentProductId and air.parentProductId != air.productId and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> getChildProductByParentId(@Param("parentProductId") Long parentProductId);

    @Query("select air from AirtimeProduct air where air.parentProductId = air.productId and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> getAllRootProducts();

    @Query("select air from AirtimeProduct air where air.province =:province and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> getProductsByProvince(@Param("province") String province);


    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.parValue=:parValue and air.province = null and air.city=null and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> queryAirtimeProductsByProperty(@Param("carrierName") String carrierName,
                                                               @Param("parValue") BigDecimal parValue);

    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.province = :province and air.parValue=:parValue and air.city=null and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> queryAirtimeProductsByProperty(@Param("carrierName") String carrierName,
                                                               @Param("province") String province,
                                                               @Param("parValue") BigDecimal parValue);

    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.province = :province and air.parValue=:parValue and air.city=:city and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> queryAirtimeProductsByProperty(@Param("carrierName") String carrierName,
                                                               @Param("province") String province,
                                                               @Param("parValue") BigDecimal parValue,
                                                               @Param("city") String city);
    
    
    
    
    
    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.parValue=:parValue and air.province = null and air.city=null and air.businessType=:businessType and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> queryAirtimeProductsByPropertyByBusinessType(@Param("carrierName") String carrierName,
                                                               @Param("parValue") BigDecimal parValue, @Param("businessType") Integer businessType);

    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.province = :province and air.parValue=:parValue and air.city=null and air.businessType=:businessType and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> queryAirtimeProductsByPropertyByBusinessType(@Param("carrierName") String carrierName,
                                                               @Param("province") String province,
                                                               @Param("parValue") BigDecimal parValue, @Param("businessType") Integer businessType);

    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.province = :province and air.parValue=:parValue and air.city=:city and air.businessType=:businessType and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> queryAirtimeProductsByPropertyByBusinessType(@Param("carrierName") String carrierName,
                                                               @Param("province") String province,
                                                               @Param("parValue") BigDecimal parValue,
                                                               @Param("city") String city, @Param("businessType") Integer businessType);
    



    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> fuzzyQueryAirtimeProductsByProperty(@Param("carrierName") String carrierName);
    
    @Query("select air from AirtimeProduct air where air.parValue=:parValue and air.province=:province and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> fuzzyQueryAirtimeProductsByProperty(@Param("parValue") BigDecimal parValue, @Param("province") String province);
    
    @Query("select air from AirtimeProduct air where air.parValue=:parValue and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> fuzzyQueryAirtimeProductsByProperty(@Param("parValue") BigDecimal parValue);

    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.parValue=:parValue and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> fuzzyQueryAirtimeProductsByProperty(@Param("carrierName") String carrierName,
                                                                    @Param("parValue") BigDecimal parValue);

    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.province = :province and air.parValue=:parValue and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> fuzzyQueryAirtimeProductsByProperty(@Param("carrierName") String carrierName,
                                                                    @Param("province") String province,
                                                                    @Param("parValue") BigDecimal parValue);

    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.province = :province and air.parValue=:parValue and air.city=:city and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> fuzzyQueryAirtimeProductsByProperty(@Param("carrierName") String carrierName,
                                                                    @Param("province") String province,
                                                                    @Param("parValue") BigDecimal parValue,
                                                                    @Param("city") String city);

    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.province = :province and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> fuzzyQueryAirtimeProductsByProperty(@Param("carrierName") String carrierName,
                                                                    @Param("province") String province);

    @Query("select air from AirtimeProduct air where air.carrierName=:carrierName and air.province = :province and air.city=:city and air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> fuzzyQueryAirtimeProductsByProperty(@Param("carrierName") String carrierName,
                                                                    @Param("province") String province,
                                                                    @Param("city") String city);
    

    
    @Query("select air from AirtimeProduct air where air.productId =:productId and air.productStatus!='"+Constant.Product.PRODUCT_DELETE +"'")
    public AirtimeProduct getProductById(@Param("productId") Long productId);

    //取状态不为删除的产品
    @Query("select air from AirtimeProduct air where air.productStatus!='"+Constant.Product.PRODUCT_DELETE+"'")
    public List<AirtimeProduct> findAllProduct();
    
    @Query("select air from AirtimeProduct air where air.productId =:productId and air.productStatus='"+Constant.Product.PRODUCT_DELETE +"'")
    public AirtimeProduct getProductByDeleteId(@Param("productId") Long productId);
    
}
