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

package com.yuecheng.hops.batch.thread;

import java.util.List;

import org.springframework.context.ApplicationContext;

import com.yuecheng.hops.batch.service.order.BatchJobService;
import com.yuecheng.hops.common.utils.SpringUtils;

public class WorkThread extends Thread {  
    // 本线程待执行的任务列表，你也可以指为任务索引的起始值  
    private List<Task> taskList = null;  
    @SuppressWarnings("unused")  
    private int threadId;
    
    @SuppressWarnings("unused")  
    private Long batchId;
    
    private ApplicationContext ctx = SpringUtils.getApplicationContext();
    
    private BatchJobService batchJobService=(BatchJobService)ctx.getBean("batchJobService");
  
    /** 
     * 构造工作线程，为其指派任务列表，及命名线程 ID 
     *  
     * @param taskList 
     *            欲执行的任务列表 
     * @param threadId 
     *            线程 ID 
     */  
    @SuppressWarnings("unchecked")  
    public WorkThread(List<Task> taskList, int threadId, Long batchId) {  
        this.taskList = taskList;  
        this.threadId = threadId;  
        this.batchId = batchId;
    }  
  
    /** 
     * 执行被指派的所有任务 
     */  
    public void run() {
        for (Task task : taskList) {
        	try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            task.execute();
        }
        //计数器thread_num - 1
        batchJobService.subThreadNum(batchId);
    }  
}  