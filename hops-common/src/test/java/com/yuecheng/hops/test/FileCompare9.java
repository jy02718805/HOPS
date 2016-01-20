/*
 * 文件名：FileCompare.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年8月19日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FileCompare9
{
    public static void main(String[] args) throws Exception
    {
        BufferedReader b1 = new BufferedReader(new FileReader("F:\\a.txt"));
        BufferedReader b2 = new BufferedReader(new FileReader("F:\\b.txt"));
        BufferedWriter bw = new BufferedWriter(new FileWriter("F:\\ab.txt"));

        Map<String,Integer> map1 = new HashMap<String,Integer>();
        Map<String,Integer> map2 = new HashMap<String,Integer>();
        
        String s1 = "";
        String s2 = "";
        
        while((s1=b1.readLine())!=null){
            if(map1.containsKey(s1)){
                Integer temp = map1.get(s1);
                map1.put(s1, temp + 1);
            }else{
                map1.put(s1, 1);
            }
        }
        
        while((s2=b2.readLine())!=null){
            if(map2.containsKey(s2)){
                Integer temp = map2.get(s2);
                map2.put(s2, temp + 1);
            }else{
                map2.put(s2, 1);
            }
        }
        
//        for (Map.Entry<String, Integer> entry1 : map1.entrySet()) {
//            if(map2.containsKey(entry1.getKey())){
//                bw.write(entry1.getKey()+"\r\n");
//            }
//        }
        
        for (Map.Entry<String, Integer> entry2 : map2.entrySet()) {
            Integer mapTimes = map1.get(entry2.getKey());
            if(mapTimes != null){
                Integer flag = mapTimes - entry2.getValue();
                if(flag <= 0){
                    map1.remove(entry2.getKey());
                }
            }
        }
        
        for (Map.Entry<String, Integer> entry1 : map1.entrySet()) {
            bw.write(entry1.getKey()+" | " + entry1.getValue() +"\r\n");
        }
        bw.close();
        b2.close();
        b1.close();
    }
}
