package com.yuecheng.hops.mportal.web.filter;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.yuecheng.hops.common.EntityConstant;


public class SessionFilter extends OncePerRequestFilter
{

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain)
        throws ServletException, IOException
    {

        String uri = request.getRequestURI();
        boolean needfilter = true;
        if (uri.length() >= 19 && uri.substring(0, 19).equals("/hops-mportal/login"))
        {
            needfilter = false;
        }

        if (uri.length() >= 23 && uri.substring(0, 23).equals("/hops-mportal/template/"))
        {
            needfilter = false;
        }

        if (needfilter)
        {
            Object obj = request.getSession().getAttribute(
                EntityConstant.Session.LOGIN_SESSION_NAME);
            if (obj == null)
            {

                request.getRequestDispatcher("/login").forward(request, response);
            }
        }
        else
        {

        }

        filterChain.doFilter(request, response);

    }

}
