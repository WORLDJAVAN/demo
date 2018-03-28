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
	private AHandleBuz handleBuz;  //业务处理的抽象类变量
	private BlockingQueue<Location> taskQueue; //待处理的业务分割队列
	private HSSFSheet sheet; // 需要解析的Excel中Sheet对象
	private int blockAmount; //控制任务执行的批次数
	private Boolean handleMode; // 执行模式 True:并发模式 False:保护模式
	private int poolSize;
	private IHandleGroup handleGroup; //用于对分组数据的处理,默认为原始处理类
		
	
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
		
		List<Map> cacheResult = new ArrayList<Map>(); //用于返回结果的List对象
		List<Map> group_value = new ArrayList<Map>(); //转存每批次待处理结果
		
		while (blockAmount > 0) {
			try {
				/* 取业务定位分割对象信息 */
				Location locSpot = taskQueue.take();
				int first = locSpot.getFirst();
				int last = locSpot.getLast();

				/* 获得本次批次任务,切块为 first 到 Last 范围 */
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
				/* 清除上批次处理遗留的结果  */
				group_value.clear(); 

				/* 获得本批次的结果集 */
				for (int times = first; times <= last; times++) {
				 try {
					 completionService.take().get();
					//	group_value.add(completionService.take().get());
					 } 
				 catch (ExecutionException e) {
						e.printStackTrace();
					}					
				}
               /* 拦截本批次结果做二次处理 */
			//	group_value = packResult(group_value);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			/* 把本批次结果封装到cacheResult中  */
			/*for (Map rs_value : group_value) {
				cacheResult.add(rs_value);
			}*/

			blockAmount--;
		}
		
		/* 关闭使用的线程池对象 */
		threadPool.shutdown();

		return (List<Map>) cacheResult;		
	}
	
	/**
	 *  用于对批结果拦截处理方法
	 * @param rs_value
	 * @return 返回批处理结果
	 */
  private List<Map> packResult(List<Map> rs_value){
	 return  handleGroup.handleGroup(rs_value);
  }
  
}
