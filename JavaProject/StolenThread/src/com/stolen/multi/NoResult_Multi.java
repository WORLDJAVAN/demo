package com.stolen.multi;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

import com.stolen.Location;
import com.stolen.inter.imp.AHandleBuz;

/**
 * 
 * @author JOE
 *
 * @param <T> 传入List<T>对象类型泛式
 */
public class NoResult_Multi<T> implements Runnable {
	private AHandleBuz handleBuz;  //业务处理的抽象类变量
	private BlockingQueue<Location> taskQueue; //待处理的业务分割队列
	private List<T> taskList; // 任务清单列表
	private Boolean handleMode; // 执行模式 True:并发模式 False:保护模式
	private int taskCount; //控制任务执行的批次数
	private CountDownLatch signal;

	/**
	 * 初始化无返回结果类
	 * @param taskList 待处理的业务对象列表
	 * @param handleBuz 待注入的业务(T对象)处理类
	 * @param handleMode 业务处理模式 True:并发模式 False:保护模式
	 * @param taskQueue 业务切分的分割点队列
	 * @param taskCount 被分割后组的数,执行批次数
	 */
	public NoResult_Multi(List<T> taskList, AHandleBuz handleBuz,
			Boolean handleMode, BlockingQueue<Location> taskQueue, int taskCount, CountDownLatch signal) {
		this.taskList = taskList;
		this.handleBuz = handleBuz;
		this.handleMode = handleMode;
		this.taskQueue = taskQueue;
		this.taskCount = taskCount;
		this.signal = signal;
	}
    /**
     * Runnable主控制方法
     */
	public void run() {
		try {		
			while (taskCount > 0) {
				Location locSpot = taskQueue.take();
				int first = locSpot.getFirst();
				int last = locSpot.getLast();

				for (int i = first; i <= last; i++) {
					if (handleMode)
						handleBuz.handleBusiness(taskList.get(i));
					else
						handleBuz.handleSingle(taskList.get(i));
				}
				taskCount--;
			}
			
			signal.countDown();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
   
}
