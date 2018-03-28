package com.test;

public  class  DiscFactory implements DiscInteface {	
    private static DiscInteface Instance = null;  //具体实现类接口
 
    /**
     * 
     * @param name 要实现的光盘类别名称
     * @return 具体类别事例对象
     */
	public static DiscInteface getInstance(String name) {
		try {
			Instance = (DiscInteface) Class.forName(name).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Instance; 
	}

	public void sell() {	
		Instance.sell();
	}
}
