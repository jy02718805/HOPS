/*
 * 文件名：WorkThread.java
 * 版权：Copyright by www.365haoyou.com
 * 描述：
 * 修改人：Administrator
 * 修改时间：2015年8月25日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.yuecheng.hops.mportal.thread;


public class BatchVerifyOrderWorkThread extends Thread {  
    // 本线程待执行的任务列表，你也可以指为任务索引的起始值  
    private BatchVerifyOrderTask task = null;  
    @SuppressWarnings("unused")  
    private int threadId;  
  
    /** 
     * 构造工作线程，为其指派任务列表，及命名线程 ID 
     *  
     * @param taskList 
     *            欲执行的任务列表 
     * @param threadId 
     *            线程 ID 
     */  
    @SuppressWarnings("unchecked")  
    public BatchVerifyOrderWorkThread(BatchVerifyOrderTask task, int threadId) {  
        this.task = task;  
        this.threadId = threadId;  
    }  
  
    /** 
     * 执行被指派的所有任务 
     */  
    public void run() {  
        task.execute();
    }  
}  