package com.stolen;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.stolen.inter.IHandleGroup;
import com.stolen.inter.imp.AHandleBuz;
import com.stolen.inter.imp.HandleGroupImp;
import com.stolen.multi.HaveResult_Multi;
import com.stolen.multi.NoResult_Multi;

/**
 * 
 * @author long
 *
 * @param <T> T:任务类型泛式,具实际业务定义
 */
public class TaskHandle<T> {
	private int taskCount = 1; // 任务欲切分块的数量
	private int taskSize; // 任务被切分块的大小 taskList.size()/taskCount;
	private Boolean handleMode = false; // 执行模式 True:并发模式 False:保护模式
	private Boolean cutWay = false; // 文件切分方式 True:按块切分 False:按大小切分
	private int poolSize = 10; // 设置需要运行的池子大小
	private Boolean islegal = true; // 参数合法性校验

	private Class<? extends AHandleBuz> handleBuzClass;
	private IHandleGroup handleGroup; // 用于对分组数据的处理,默认为原始处理类
	final BlockingQueue<Location> taskQueue = new ArrayBlockingQueue<Location>(10);
	private List<Object> cacheResult = new ArrayList<Object>(); // 用于返回结果的List对象
	private List<T> taskList = new ArrayList<T>(); // 需要执行的任务列表
	
	/**
	 * 设置运行处理类   (False:保护模式,无任务拦截)
	 * @param taskList 需要执行任务列表
	 * @param handleBuzClass 执行具体业务实现类
	 * @param handleMode 务执行模式  True:并发模式  False:保护模式
	 */
	public void setTask(List<T> taskList,
			Class<? extends AHandleBuz> handleBuzClass) {
		setTask(taskList, handleBuzClass, new HandleGroupImp(), false);
	}
	
	/**
	 * 设置运行处理类   (False:保护模式)
	 * @param taskList 需要执行任务列表
	 * @param handleBuzClass 执行具体业务实现类
	 * @param handleGroup 执行分组拦截实现类
	 * @param handleMode 务执行模式  True:并发模式  False:保护模式
	 */
	public void setTask(List<T> taskList,
			Class<? extends AHandleBuz> handleBuzClass, IHandleGroup handleGroup) {	
		setTask(taskList, handleBuzClass, handleGroup, false);
	}
	
	/**
	 * 设置运行处理类   (无任务拦截)
	 * @param taskList 需要执行任务列表
	 * @param handleBuzClass 执行具体业务实现类
	 * @param handleMode 务执行模式  True:并发模式  False:保护模式
	 */
	public void setTask(List<T> taskList,
			Class<? extends AHandleBuz> handleBuzClass, Boolean handleMode) {
		setTask(taskList, handleBuzClass, new HandleGroupImp(), handleMode);
	}
	
	/**
	 * 设置运行处理类
	 * @param taskList 需要执行任务列表
	 * @param handleBuzClass 执行具体业务实现类
	 * @param handleGroup 执行分组拦截实现类
	 * @param handleMode 务执行模式  True:并发模式  False:保护模式
	 */
	public void setTask(List<T> taskList,
			Class<? extends AHandleBuz> handleBuzClass,
			IHandleGroup handleGroup, Boolean handleMode) {
		this.taskList = taskList;
		this.handleBuzClass = handleBuzClass;
		this.handleGroup = handleGroup;
		this.handleMode = handleMode;
	}
	
	/**
	 * 设置启动参数  默认整块
     * @param taskCount 切分数量 
	 */
	public void setParams(int taskCount){
		setParams(taskCount,false,10);
	}
	
	/**
	 * 设置启动参数 默认线程池 10
	 * @param taskCount 切分数量 
	 * @param cutWay 切分方式
	 */
	public void setParams(int taskCount, Boolean cutWay){
		setParams(taskCount,cutWay,10);
	}
	
	/**
	 * 设置启动参数  默认切分方式 按块分
	 * @param taskCount 切分数量 
	 * @param cutWay 切分方式
	 * @param poolSize 需要执行线程池的大小
	 */
	public void setParams(int taskCount, int poolSize){
		setParams(taskCount,false,poolSize);
	}
	
	/**
	 * 设置启动参数
	 * @param taskCount 切分数量 
	 * @param cutWay 切分方式
	 * @param poolSize 需要执行线程池的大小
	 */
	public void setParams(int taskCount, Boolean cutWay, int poolSize){
		this.taskCount = taskCount;	
		this.cutWay = cutWay;
		this.poolSize = poolSize;
	}
	
	/**
	 * 启动有返回结果的任务
	 * @return 任务的执行结果对象
	 */
	public List<Object> startResult(){
		execute("ResultMulti");
		return islegal ? cacheResult : null;
	}
	
	/**
	 * 启动无返回结果的任务
	 */
	public void startNoResult(){
		execute("NoResultMulti");
	}	

	/**
	 * 启动任务执行操作
	 * @param sigle  NoResultMulti:无返回结果任务   ResultMulti:有返回结果任务
	 */
	private void execute(String sigle){
		if (checkParams()) {
			/* 计算任务块的大小及切分方式 */
			taskSize = taskList.size() / taskCount;

			/* True:按块切分 False:按大小切分(count与size相反) */
			if (!cutWay) {

				int temp = taskCount;
				taskCount = taskSize;
				taskSize = temp;

				if ((taskList.size() % taskSize) > 0)
					taskCount = taskCount + 1;
			}

			/* 执行任务切分动作存放到阻塞队列中 */
			new Thread() {
				@Override
				public void run() {
					putTaskQueue(taskCount, taskSize);
				}
			}.start();

			/* 执行任务切分动作存放到阻塞队列中 */
			if (sigle.equals("NoResultMulti"))
				getNoResultMulti(this.poolSize);
			else
				getResultMulti(this.poolSize);
		}
	}
	
	/**
	 * 用户参数合法性校验
	 * @return 不可执行时,设置返回 False
	 */
	private Boolean checkParams(){
		if ((taskList.size() <= 0) || (taskCount <= 0) || (poolSize <= 0))
			islegal = false;
		return islegal;
	}
		
	/**
	 * 执行任务切分操作
	 * @param count 任务切分后的数量
	 * @param size 计算出的任务块大小
	 */
	private void putTaskQueue(int count, int size){
		Location locSpot;
		/* 开始执行任务切分 */
		for (int i = 0; i < count; i++) {
			if (i == count - 1)
				locSpot = new Location(size * i, taskList.size() - 1);
			else
				locSpot = new Location(size * i, size * (i + 1) - 1);

			/* 存放任务的位置信息点 */
			try {
				taskQueue.put(locSpot);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
  /* target_Class.getConstructor(param.class..).newInstance(param_val..) */
	
	/**
	 * 执行无返回结果任务实现方法
	 * @param poolSize 任务池的大小
	 */
	private void getNoResultMulti(int poolSize){
		/* 执行任务的线程池 */
		ExecutorService service = Executors.newFixedThreadPool(poolSize);
		CountDownLatch signal = new CountDownLatch(1);
		try {
			AHandleBuz handlebuz = (AHandleBuz) handleBuzClass.newInstance();
			NoResult_Multi<T> noResultMulti = new NoResult_Multi<T>(taskList,
					handlebuz, handleMode, taskQueue, taskCount, signal);

			for (int count = 0; count < poolSize; count++) {
				service.submit(noResultMulti);
			}
			signal.await();
			service.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 执行有返回结果任务实现方法
	 * @param poolSize 任务池的大小
	 */
	@SuppressWarnings("unchecked")
	private void getResultMulti(int poolSize){
		try {
			AHandleBuz handlebuz = (AHandleBuz) handleBuzClass.newInstance();
			HaveResult_Multi<T> resultMulti = new HaveResult_Multi<T>(taskList,
					handlebuz, handleGroup, handleMode, taskQueue, taskCount, poolSize);
			cacheResult=(List<Object>) resultMulti.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	} 
}
