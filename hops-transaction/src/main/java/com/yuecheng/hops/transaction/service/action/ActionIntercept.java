///*
// * 文件名：ActionIntercept.java
// * 版权：Copyright by www.365haoyou.com
// * 描述：
// * 修改人：Administrator
// * 修改时间：2014年11月14日
// * 跟踪单号：
// * 修改单号：
// * 修改内容：
// */
//
//package com.yuecheng.hops.transaction.service.action;
//
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
//import com.yuecheng.hops.transaction.TransactionContextUtil;
//import com.yuecheng.hops.transaction.service.action.context.ActionContextUtil;
//
///**
// * 
// * @author Administrator
// * 通过aop拦截后执行具体操作
// */
//@Aspect
//@Component
//public class ActionIntercept
//{
//    @Pointcut("execution(* com.yuecheng.hops.transaction.service.action.ActionChainDefinerFactory.*(..))")
//    public void doAction(){}
//    
//    @Before("doAction()")
//    public void before() {
//        ActionContextUtil.setActionContext(TransactionContextUtil.getTransactionContextLocal());
//    }
//     
//    @After("doAction()")
//    public void after() {
//        TransactionContextUtil.setTransactionContext(ActionContextUtil.getActionContextLocal());
//        ActionContextUtil.getActionContextLocal().clear();
//    }
//    
//}
