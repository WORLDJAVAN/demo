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
	private AHandleBuz handleBuz;  //ҵ����ĳ��������
	private BlockingQueue<Location> taskQueue; //�������ҵ��ָ����
	private List<T> taskList; // �����嵥�б�
	private Boolean handleMode; // ִ��ģʽ True:����ģʽ False:����ģʽ
	private int taskCount; //��������ִ�е�������
	private int poolSize;
	private IHandleGroup handleGroup; //���ڶԷ������ݵĴ���,Ĭ��Ϊԭʼ������
	
/*	ExecutorService service = Executors.newCachedThreadPool(); //ִ��������̳߳�,�ɻ��巽ʽ�ж����������������߳�	
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
		
		List<Object> cacheResult = new ArrayList<Object>(); //���ڷ��ؽ����List����
		List<Object> group_value = new ArrayList<Object>(); //ת��ÿ���δ�������
		
		while (taskCount > 0) {
			try {
				/* ȡҵ��λ�ָ������Ϣ */
				Location locSpot = taskQueue.take();
				int first = locSpot.getFirst();
				int last = locSpot.getLast();

				/* ��ñ�����������,�п�Ϊ first �� Last ��Χ */
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
				
				/* ��������δ��������Ľ��  */
				group_value.clear(); 

				/* ��ñ����εĽ���� */
				for (int times = first; times <= last; times++) {
				 try {
						group_value.add(completionService.take().get());
					 } 
				 catch (ExecutionException e) {
						e.printStackTrace();
					}					
				}
               /* ���ر����ν�������δ��� */
				group_value = packResult(group_value);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			/* �ѱ����ν����װ��cacheResult��  */
			for (Object rs_value : group_value) {
				cacheResult.add(rs_value);
			}

			taskCount--;
		}
		
		/* �ر�ʹ�õ��̳߳ض��� */
		threadPool.shutdown();

		return (List<T>) cacheResult;		
	}
	
	/**
	 *  ���ڶ���������ش�����
	 * @param rs_value
	 * @return ������������
	 */
  private List<Object> packResult(List<Object> rs_value){
	 return  handleGroup.handleGroup(rs_value);
  }
  
}
