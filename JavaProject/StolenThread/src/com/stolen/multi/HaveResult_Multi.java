package com.stolen.multi;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.stolen.Location;
import com.stolen.inter.IHandleGroup;
import com.stolen.inter.imp.AHandleBuz;

public class HaveResult_Multi<T> {
	private AHandleBuz handleBuz;  //业务处理的抽象类变量
	private BlockingQueue<Location> taskQueue; //待处理的业务分割队列
	private List<T> taskList; // 任务清单列表
	private Boolean handleMode; // 执行模式 True:并发模式 False:保护模式
	private int taskCount; //控制任务执行的批次数
	private int poolSize;
	private IHandleGroup handleGroup; //用于对分组数据的处理,默认为原始处理类
	
/*	ExecutorService service = Executors.newCachedThreadPool(); //执行任务的线程池,可缓冲方式有多少任务启动多少线程	
	CompletionService<T> completionService = new ExecutorCompletionService<T>(service);*/	
	
	public HaveResult_Multi(List<T> taskList, AHandleBuz handleBuz,
			IHandleGroup handleGroup, Boolean handleMode,
			BlockingQueue<Location> taskQueue, int taskCount, int poolSize) {
		this.taskList = taskList;
		this.handleBuz = handleBuz;
		this.handleGroup = handleGroup;
		this.handleMode = handleMode;
		this.taskQueue = taskQueue;
		this.taskCount = taskCount;
		this.poolSize = poolSize;
	}
	
	@SuppressWarnings("unchecked")
	public  List<T> execute(){
		ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);	
		CompletionService<T> completionService = new ExecutorCompletionService<T>(threadPool);	
		
		List<Object> cacheResult = new ArrayList<Object>(); //用于返回结果的List对象
		List<Object> group_value = new ArrayList<Object>(); //转存每批次待处理结果
		
		while (taskCount > 0) {
			try {
				/* 取业务定位分割对象信息 */
				Location locSpot = taskQueue.take();
				int first = locSpot.getFirst();
				int last = locSpot.getLast();

				/* 获得本次批次任务,切块为 first 到 Last 范围 */
				for (int i = first; i <= last; i++) {
					final int task = i;
					final AHandleBuz tempbuz = handleBuz;

					completionService.submit(new Callable<T>() {
						@Override
						public T call() throws Exception {
							return handleMode?(T) tempbuz.handleBusiness(taskList.get(task))
									         :(T) tempbuz.handleSingle(taskList.get(task));		
						}
					});
				}
				
				/* 清除上批次处理遗留的结果  */
				group_value.clear(); 

				/* 获得本批次的结果集 */
				for (int times = first; times <= last; times++) {
				 try {
						group_value.add(completionService.take().get());
					 } 
				 catch (ExecutionException e) {
						e.printStackTrace();
					}					
				}
               /* 拦截本批次结果做二次处理 */
				group_value = packResult(group_value);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			/* 把本批次结果封装到cacheResult中  */
			for (Object rs_value : group_value) {
				cacheResult.add(rs_value);
			}

			taskCount--;
		}
		
		/* 关闭使用的线程池对象 */
		threadPool.shutdown();

		return (List<T>) cacheResult;		
	}
	
	/**
	 *  用于对批结果拦截处理方法
	 * @param rs_value
	 * @return 返回批处理结果
	 */
  private List<Object> packResult(List<Object> rs_value){
	 return  handleGroup.handleGroup(rs_value);
  }
  
}
