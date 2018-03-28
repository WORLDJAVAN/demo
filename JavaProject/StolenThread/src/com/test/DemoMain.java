package com.test;

import java.util.ArrayList;
import java.util.List;

import com.stolen.TaskHandle;

public class DemoMain {

	public static void main(String[] args) {
		List<String> buz_job = new ArrayList<String>();

		for (int i = 0; i < 400000; i++) {
			buz_job.add("buz_data" + i);
		}

		TaskHandle<String> taskSteal = new TaskHandle<String>();	
		taskSteal.setTask(buz_job, DHandleBuz.class, new DHandleGroup(),false);
		taskSteal.setParams(3,5);	
		List<Object> tt = taskSteal.startResult();
		
		System.out.println("==========½á¹û============");		
		/*for (int i = 0; i < tt.size(); i++) {
			System.out.println(tt.get(i));
		}*/
	}
}
