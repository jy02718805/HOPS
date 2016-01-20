/*
 * 文件名：ErrorCoderFinder.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年8月24日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class ErrorCoderFinder
{
    public static void main(String[] args) throws Exception
    {
        BufferedReader b1 = new BufferedReader(new FileReader("F:\\cmpay_delivery_fail_detal.txt"));
        String s1 = "";
        String keyWords = "resultno=9";
        StringBuffer sb = new StringBuffer();
        while((s1=b1.readLine())!=null){
            if(!s1.contains(keyWords)){
                sb.append(s1 +"\r\n");
            }
        }
        b1.close();
        BufferedWriter bw = new BufferedWriter(new FileWriter( "F:\\cmpay_delivery_fail_detal.txt"));
        bw.write(sb.toString());
        bw.close();
    }
}
