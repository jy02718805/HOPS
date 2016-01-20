package com.yuecheng.hops.privilege.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.yuecheng.hops.privilege.entity.Menu;


/**
 * 菜单权限表数据访问层
 * 
 * @author：Jinger
 * @date：2013-09-17
 */

public interface MenuDao extends PagingAndSortingRepository<Menu, Long>, JpaSpecificationExecutor<Menu>
{
    @Query("select m from Menu m")
    List<Menu> selectAll();

    @Query("select m from Menu m where m.menuLevel=:menuLevel order by m.displayOrder")
    List<Menu> selectList(@Param("menuLevel")
    String menuLevel);

    @Query("select m from Menu m where m.parentMenuId=:parentId and m.menuLevel=:menuLevel and m.status=:status order by m.displayOrder")
    List<Menu> selectList(@Param("parentId")
    Long parentId, @Param("menuLevel")
    String menuLevel, @Param("status")
    String status);

    @Query("select m from Menu m where m.pageResource.resourceId=:pageResourceId")
    Menu getMenuByPageUrl(@Param("pageResourceId")
    Long pageResourceId);
}
