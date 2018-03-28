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
 * @param <T> ����List<T>�������ͷ�ʽ
 */
public class NoResult_Multi<T> implements Runnable {
	private AHandleBuz handleBuz;  //ҵ����ĳ��������
	private BlockingQueue<Location> taskQueue; //�������ҵ��ָ����
	private List<T> taskList; // �����嵥�б�
	private Boolean handleMode; // ִ��ģʽ True:����ģʽ False:����ģʽ
	private int taskCount; //��������ִ�е�������
	private CountDownLatch signal;

	/**
	 * ��ʼ���޷��ؽ����
	 * @param taskList �������ҵ������б�
	 * @param handleBuz ��ע���ҵ��(T����)������
	 * @param handleMode ҵ����ģʽ True:����ģʽ False:����ģʽ
	 * @param taskQueue ҵ���зֵķָ�����
	 * @param taskCount ���ָ�������,ִ��������
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
     * Runnable�����Ʒ���
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
