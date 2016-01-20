package com.yuecheng.hops.privilege.service;


/**
 * 菜单表逻辑访问层
 * 
 * @author：Jinger
 * @date：2013-09-17
 */

import java.util.List;
import java.util.Map;

import com.yuecheng.hops.common.query.BSort;
import com.yuecheng.hops.common.query.YcPage;
import com.yuecheng.hops.privilege.entity.Menu;


public interface MenuService
{
    /**
     * 保存菜单
     * 
     * @param menu
     * @return
     */
    public Menu saveMenu(Menu menu);

    /**
     * 根据ID删除菜单
     * 
     * @param menuId
     */
    public void deleteMenu(Long menuId);

    /**
     * 根据ID查询菜单
     * 
     * @param menuId
     * @return
     */
    public Menu queryMenuById(Long menuId);

    /**
     * 根据页面路径查询菜单
     * 
     * @param pageUrl
     * @return
     */
    public Menu queryMenuByPageUrl(String pageUrl);

    /**
     * 根据页面路径ID查询菜单
     * 
     * @param pageId
     * @return
     */
    public Menu queryMenuByPageId(Long pageId);
    /**
     * 获取所有的菜单权限
     * 
     * @return
     */
    public List<Menu> queryAllMenu();

    /**
     * 根据菜单级别获取所有菜单
     * 
     * @param menuLevel
     *            菜单级别
     * @return
     */
    public List<Menu> queryMenuByLevel(String menuLevel);

    /**
     * 根据富ID和菜单级别获取所有的菜单列表
     * 
     * @param parentId
     * @param menuLevel
     *            菜单级别
     * @return
     */
    public List<Menu> queryMenuByParams(Long parentId, String menuLevel);

    /**
     * 根也查询菜单
     * 
     * @param searchParams
     * @param pageNumber
     * @param pageSize
     * @param bsort
     * @return
     */
    public YcPage<Menu> queryPageMenu(Map<String, Object> searchParams, int pageNumber,
                                      int pageSize, BSort bsort);
}
