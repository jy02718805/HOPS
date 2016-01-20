/*
 * 文件名：Main.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年11月16日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.threadTest;

public class Main
{
    public static void main(String[] args)
    {
        AatchOrderListThread a = new AatchOrderListThread();
        Thread t = new Thread(a);
        //启动
        t.start();
    }
}