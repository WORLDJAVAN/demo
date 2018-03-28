package com.excel.multi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.hssf.usermodel.HSSFSheet;

import com.excel.Location;
import com.excel.inter.IHandleGroup;
import com.excel.inter.imp.AHandleBuz;

public class HaveResult_Multi {
	private AHandleBuz handleBuz;  //ҵ����ĳ��������
	private BlockingQueue<Location> taskQueue; //�������ҵ��ָ����
	private HSSFSheet sheet; // ��Ҫ������Excel��Sheet����
	private int blockAmount; //��������ִ�е�������
	private Boolean handleMode; // ִ��ģʽ True:����ģʽ False:����ģʽ
	private int poolSize;
	private IHandleGroup handleGroup; //���ڶԷ������ݵĴ���,Ĭ��Ϊԭʼ������
		
	
	public HaveResult_Multi(HSSFSheet sheet, AHandleBuz handleBuz,
			IHandleGroup handleGroup, Boolean handleMode,
			BlockingQueue<Location> taskQueue, int blockAmount, int poolSize) {
		this.sheet = sheet;
		this.handleBuz = handleBuz;
		this.handleGroup = handleGroup;
		this.taskQueue = taskQueue;
		this.blockAmount = blockAmount;
		this.poolSize = poolSize;
	}
	
	@SuppressWarnings("unchecked")
	public  List<Map> execute(){
		ExecutorService threadPool = Executors.newFixedThreadPool(poolSize);	
		CompletionService<Map> completionService = new ExecutorCompletionService<Map>(threadPool);	
		
		List<Map> cacheResult = new ArrayList<Map>(); //���ڷ��ؽ����List����
		List<Map> group_value = new ArrayList<Map>(); //ת��ÿ���δ�������
		
		while (blockAmount > 0) {
			try {
				/* ȡҵ��λ�ָ������Ϣ */
				Location locSpot = taskQueue.take();
				int first = locSpot.getFirst();
				int last = locSpot.getLast();

				/* ��ñ�����������,�п�Ϊ first �� Last ��Χ */
				for (int i = first; i <= last; i++) {
					final int row = i;
					final AHandleBuz tempbuz = handleBuz;

					completionService.submit(new Callable<Map>() {
						@Override
						public Map call() throws Exception {
					
							return (Map) (handleMode? tempbuz.handleBusiness(sheet.getRow(row))
									         : tempbuz.handleSingle(sheet.getRow(row)));		
						}
					});
				}
				
				System.out.println("submit");
				/* ��������δ��������Ľ��  */
				group_value.clear(); 

				/* ��ñ����εĽ���� */
				for (int times = first; times <= last; times++) {
				 try {
					 completionService.take().get();
					//	group_value.add(completionService.take().get());
					 } 
				 catch (ExecutionException e) {
						e.printStackTrace();
					}					
				}
               /* ���ر����ν�������δ��� */
			//	group_value = packResult(group_value);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			/* �ѱ����ν����װ��cacheResult��  */
			/*for (Map rs_value : group_value) {
				cacheResult.add(rs_value);
			}*/

			blockAmount--;
		}
		
		/* �ر�ʹ�õ��̳߳ض��� */
		threadPool.shutdown();

		return (List<Map>) cacheResult;		
	}
	
	/**
	 *  ���ڶ���������ش�����
	 * @param rs_value
	 * @return ������������
	 */
  private List<Map> packResult(List<Map> rs_value){
	 return  handleGroup.handleGroup(rs_value);
  }
  
}
