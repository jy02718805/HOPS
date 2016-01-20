package com.yuecheng.hops.mportal.web.filter;


/**
 * 权限模块过滤器
 * 
 * @author：Jinger
 * @date：2013-11-01
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dubbo.rpc.RpcContext;
import com.yuecheng.hops.common.Constant;
import com.yuecheng.hops.common.hopscache.HopsCacheUtil;
import com.yuecheng.hops.identity.entity.operator.Operator;
import com.yuecheng.hops.privilege.service.MenuService;


public class PrivilegeFilter implements Filter
{
    private MenuService menuService;

    public void setMenuService(MenuService menuService)
    {
        this.menuService = menuService;
    }

    public void destroy()
    {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {

        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String url = httpRequest.getRequestURI();
        Object object = httpRequest.getSession().getAttribute("loginUser");

        if ((url.contains("/login")||url.contains("/template")) && object == null)
        {
            chain.doFilter(request, response);
            return;
        }
        if (object != null)
        {
            Operator operator = (Operator)object;
            RpcContext.getContext().set("operator_id", operator.getId());
            url = url.substring(1, url.length());
            String menu_key = url.substring(url.indexOf("/") + 1, url.length());

            Object menu = HopsCacheUtil.get(Constant.Common.CACHE,
                operator.getOperatorName() + "|" + menu_key);
            if (menu == null)
            {
                menu = menuService.queryMenuByPageUrl(menu_key);
                HopsCacheUtil.put(Constant.Common.CACHE, operator.getOperatorName() + "|"
                                                         + menu_key, menu);
            }
            request.setAttribute("menu", menu);
        }
        else
        {
            HttpServletResponse httpresponse = (HttpServletResponse)response;
            httpresponse.sendRedirect("/hops-mportal/login");
            return;
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig)
        throws ServletException
    {

    }

}