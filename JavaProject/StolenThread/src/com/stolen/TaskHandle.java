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
 * @param <T> T:�������ͷ�ʽ,��ʵ��ҵ����
 */
public class TaskHandle<T> {
	private int taskCount = 1; // �������зֿ������
	private int taskSize; // �����зֿ�Ĵ�С taskList.size()/taskCount;
	private Boolean handleMode = false; // ִ��ģʽ True:����ģʽ False:����ģʽ
	private Boolean cutWay = false; // �ļ��зַ�ʽ True:�����з� False:����С�з�
	private int poolSize = 10; // ������Ҫ���еĳ��Ӵ�С
	private Boolean islegal = true; // �����Ϸ���У��

	private Class<? extends AHandleBuz> handleBuzClass;
	private IHandleGroup handleGroup; // ���ڶԷ������ݵĴ���,Ĭ��Ϊԭʼ������
	final BlockingQueue<Location> taskQueue = new ArrayBlockingQueue<Location>(10);
	private List<Object> cacheResult = new ArrayList<Object>(); // ���ڷ��ؽ����List����
	private List<T> taskList = new ArrayList<T>(); // ��Ҫִ�е������б�
	
	/**
	 * �������д�����   (False:����ģʽ,����������)
	 * @param taskList ��Ҫִ�������б�
	 * @param handleBuzClass ִ�о���ҵ��ʵ����
	 * @param handleMode ��ִ��ģʽ  True:����ģʽ  False:����ģʽ
	 */
	public void setTask(List<T> taskList,
			Class<? extends AHandleBuz> handleBuzClass) {
		setTask(taskList, handleBuzClass, new HandleGroupImp(), false);
	}
	
	/**
	 * �������д�����   (False:����ģʽ)
	 * @param taskList ��Ҫִ�������б�
	 * @param handleBuzClass ִ�о���ҵ��ʵ����
	 * @param handleGroup ִ�з�������ʵ����
	 * @param handleMode ��ִ��ģʽ  True:����ģʽ  False:����ģʽ
	 */
	public void setTask(List<T> taskList,
			Class<? extends AHandleBuz> handleBuzClass, IHandleGroup handleGroup) {	
		setTask(taskList, handleBuzClass, handleGroup, false);
	}
	
	/**
	 * �������д�����   (����������)
	 * @param taskList ��Ҫִ�������б�
	 * @param handleBuzClass ִ�о���ҵ��ʵ����
	 * @param handleMode ��ִ��ģʽ  True:����ģʽ  False:����ģʽ
	 */
	public void setTask(List<T> taskList,
			Class<? extends AHandleBuz> handleBuzClass, Boolean handleMode) {
		setTask(taskList, handleBuzClass, new HandleGroupImp(), handleMode);
	}
	
	/**
	 * �������д�����
	 * @param taskList ��Ҫִ�������б�
	 * @param handleBuzClass ִ�о���ҵ��ʵ����
	 * @param handleGroup ִ�з�������ʵ����
	 * @param handleMode ��ִ��ģʽ  True:����ģʽ  False:����ģʽ
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
	 * ������������  Ĭ������
     * @param taskCount �з����� 
	 */
	public void setParams(int taskCount){
		setParams(taskCount,false,10);
	}
	
	/**
	 * ������������ Ĭ���̳߳� 10
	 * @param taskCount �з����� 
	 * @param cutWay �зַ�ʽ
	 */
	public void setParams(int taskCount, Boolean cutWay){
		setParams(taskCount,cutWay,10);
	}
	
	/**
	 * ������������  Ĭ���зַ�ʽ �����
	 * @param taskCount �з����� 
	 * @param cutWay �зַ�ʽ
	 * @param poolSize ��Ҫִ���̳߳صĴ�С
	 */
	public void setParams(int taskCount, int poolSize){
		setParams(taskCount,false,poolSize);
	}
	
	/**
	 * ������������
	 * @param taskCount �з����� 
	 * @param cutWay �зַ�ʽ
	 * @param poolSize ��Ҫִ���̳߳صĴ�С
	 */
	public void setParams(int taskCount, Boolean cutWay, int poolSize){
		this.taskCount = taskCount;	
		this.cutWay = cutWay;
		this.poolSize = poolSize;
	}
	
	/**
	 * �����з��ؽ��������
	 * @return �����ִ�н������
	 */
	public List<Object> startResult(){
		execute("ResultMulti");
		return islegal ? cacheResult : null;
	}
	
	/**
	 * �����޷��ؽ��������
	 */
	public void startNoResult(){
		execute("NoResultMulti");
	}	

	/**
	 * ��������ִ�в���
	 * @param sigle  NoResultMulti:�޷��ؽ������   ResultMulti:�з��ؽ������
	 */
	private void execute(String sigle){
		if (checkParams()) {
			/* ���������Ĵ�С���зַ�ʽ */
			taskSize = taskList.size() / taskCount;

			/* True:�����з� False:����С�з�(count��size�෴) */
			if (!cutWay) {

				int temp = taskCount;
				taskCount = taskSize;
				taskSize = temp;

				if ((taskList.size() % taskSize) > 0)
					taskCount = taskCount + 1;
			}

			/* ִ�������зֶ�����ŵ����������� */
			new Thread() {
				@Override
				public void run() {
					putTaskQueue(taskCount, taskSize);
				}
			}.start();

			/* ִ�������зֶ�����ŵ����������� */
			if (sigle.equals("NoResultMulti"))
				getNoResultMulti(this.poolSize);
			else
				getResultMulti(this.poolSize);
		}
	}
	
	/**
	 * �û������Ϸ���У��
	 * @return ����ִ��ʱ,���÷��� False
	 */
	private Boolean checkParams(){
		if ((taskList.size() <= 0) || (taskCount <= 0) || (poolSize <= 0))
			islegal = false;
		return islegal;
	}
		
	/**
	 * ִ�������зֲ���
	 * @param count �����зֺ������
	 * @param size �������������С
	 */
	private void putTaskQueue(int count, int size){
		Location locSpot;
		/* ��ʼִ�������з� */
		for (int i = 0; i < count; i++) {
			if (i == count - 1)
				locSpot = new Location(size * i, taskList.size() - 1);
			else
				locSpot = new Location(size * i, size * (i + 1) - 1);

			/* ��������λ����Ϣ�� */
			try {
				taskQueue.put(locSpot);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
  /* target_Class.getConstructor(param.class..).newInstance(param_val..) */
	
	/**
	 * ִ���޷��ؽ������ʵ�ַ���
	 * @param poolSize ����صĴ�С
	 */
	private void getNoResultMulti(int poolSize){
		/* ִ��������̳߳� */
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
	 * ִ���з��ؽ������ʵ�ַ���
	 * @param poolSize ����صĴ�С
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
