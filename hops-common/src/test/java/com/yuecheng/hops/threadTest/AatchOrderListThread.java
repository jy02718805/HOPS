// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space
// Source File Name: AatchOrderListThread.java

package com.yuecheng.hops.threadTest;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class AatchOrderListThread implements Runnable
{
    public void run(){
        BufferedReader b1 = null;
        List taskList = new ArrayList();
        try
        {
            b1 = new BufferedReader(new FileReader("F:\\a.txt"));
            // 初始化要执行的任务列表
            String s1 = "";
            int j = 0;
            while((s1=b1.readLine())!=null){
                taskList.add(new Task(j, s1));
                j++;
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        // 设定要启动的工作线程数为 4 个
        int threadCount = 10;
        List[] taskListPerThread = distributeTasks(taskList, threadCount);
        System.out.println("实际要启动的工作线程数：" + taskListPerThread.length);
        for (int i = 0; i < taskListPerThread.length; i++ )
        {
            Thread workThread = new WorkThread(taskListPerThread[i], i);
            workThread.start();
        }
    }
    
    /**
     * 把 List 中的任务分配给每个线程，先平均分配，剩于的依次附加给前面的线程 返回的数组有多少个元素 (List) 就表明将启动多少个工作线程
     * 
     * @param taskList
     *            待分派的任务列表
     * @param threadCount
     *            线程数
     * @return 列表的数组，每个元素中存有该线程要执行的任务列表
     */
    @SuppressWarnings("unchecked")
    public static List[] distributeTasks(List taskList, int threadCount)
    {
        // 每个线程至少要执行的任务数,假如不为零则表示每个线程都会分配到任务
        int minTaskCount = taskList.size() / threadCount;
        // 平均分配后还剩下的任务数，不为零则还有任务依个附加到前面的线程中
        int remainTaskCount = taskList.size() % threadCount;
        // 实际要启动的线程数,如果工作线程比任务还多
        // 自然只需要启动与任务相同个数的工作线程，一对一的执行
        // 毕竟不打算实现了线程池，所以用不着预先初始化好休眠的线程
        int actualThreadCount = minTaskCount > 0 ? threadCount : remainTaskCount;
        // 要启动的线程数组，以及每个线程要执行的任务列表
        List[] taskListPerThread = new List[actualThreadCount];
        int taskIndex = 0;
        // 平均分配后多余任务，每附加给一个线程后的剩余数，重新声明与 remainTaskCount
        // 相同的变量，不然会在执行中改变 remainTaskCount 原有值，产生麻烦
        int remainIndces = remainTaskCount;
        for (int i = 0; i < taskListPerThread.length; i++ )
        {
            taskListPerThread[i] = new ArrayList();
            // 如果大于零，线程要分配到基本的任务
            if (minTaskCount > 0)
            {
                for (int j = taskIndex; j < minTaskCount + taskIndex; j++ )
                {
                    taskListPerThread[i].add(taskList.get(j));
                }
                taskIndex += minTaskCount;
            }
            // 假如还有剩下的，则补一个到这个线程中
            if (remainIndces > 0)
            {
                taskListPerThread[i].add(taskList.get(taskIndex++ ));
                remainIndces-- ;
            }
        }
        // 打印任务的分配情况
        for (int i = 0; i < taskListPerThread.length; i++ )
        {
            System.out.println("线程 "
                               + i
                               + " 的任务数："
                               + taskListPerThread[i].size()
                               + " 区间["
                               + ((Task)taskListPerThread[i].get(0)).getTaskId()
                               + ","
                               + ((Task)taskListPerThread[i].get(taskListPerThread[i].size() - 1)).getTaskId()
                               + "]");
        }
        return taskListPerThread;
    }
}
