package com.test;

public  class  DiscFactory implements DiscInteface {	
    private static DiscInteface Instance = null;  //����ʵ����ӿ�
 
    /**
     * 
     * @param name Ҫʵ�ֵĹ����������
     * @return ���������������
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
