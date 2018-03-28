package com.grade.imp;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.grade.ResumeGrade;

/**
 * 计算简历综合平分 
 * @author Joe Email:zoulong@yundasys.com
 * 
 */
public class ResumeGradeImp implements ResumeGrade {
	private String master;
	private String detail;
	private Map<String,Object> m_map, de_map;

	@Override
	public float getGrade(Map<String, String> paramkey) {
		
		// 1.验证输入参数的合法性
		if("".equals(this.master)|| this.master==null || "".equals(this.detail)|| this.detail==null){
			return -1;	//文件参数没有设置
		}
		
		if(paramkey==null){
			return -2; //输入参数有问题
		}
		
		// 2.获取XM配置数据
		if(m_map==null||de_map==null){
			List<Map<String,Object>> listmaps = GetParam.getMap(this.master, this.detail);
			m_map = listmaps.get(0);
			de_map = listmaps.get(1);
		}		
		if(m_map.size()==0||de_map.size()==0) return -3; //文件读取异常
		
		// 3.进行结果运算	
		float result = 0f;		
		for(String key :paramkey.keySet()){		
			String mul_a = (String) m_map.get(key); //取得类别权值		
			
			if(mul_a==null){ 
				System.out.println("主类键值为:"+(key==""?"为空":key)+"错误!");
				return -4; //主类参数异常
				} 		
			@SuppressWarnings("unchecked")
			Map<String,String> values = (Map<String,String>) de_map.get(key);	
			
			String mul_b = values.get(paramkey.get(key));
			
			if(mul_b==null){ 
				System.out.println("主类:"+key+" 对应的子类键值为:"+(paramkey.get(key)==""?"为空":paramkey.get(key))+"错误!");
				return -5; //子类参数异常
				}			
			result = accumulate(mul_a,mul_b,result);
		}	
		return result*200;
	}	

	@Override
	public void setXMLFileName(String master, String detail) {
		this.master = master;
		this.detail = detail;		
	}
	
	private float accumulate(String mul_a,String mul_b, float result){
		float a,b;
		 a=Float.parseFloat(mul_a);	 
		 b=Float.parseFloat(mul_b);		
		return result = result + a*b;
	}

	/**
	 * 装载加权值参数
	 * @author Joe
	 * 
	 */
	public static class GetParam {
		/* 定义存放XML参数的 Map<String Object>属性 */
		private static Map<String, Object> master,detail;
		
		public static List<Map<String,Object>> getMap(String xmlmaster, String xmldetail){		
			List<Map<String,Object>> listmaps = new ArrayList<Map<String,Object>>();
			if(master==null){
				master = getMaster(xmlmaster);
				detail = getDetail(xmldetail);
			}	
			    listmaps.add(master);
				listmaps.add(detail);				
		 	return listmaps;
		}
		
		/**
		 * 获取类别主加权值Map<String,String>集合
		 * @param xmlfile
		 *  配置文件路径
		 * @return
		 * 返回Map<String key, String value> 如: map<en,0.02551742> key='en' value=0.02551742
		 */
		private static Map<String, Object> getMaster(String xmlfile) {
			Map<String, Object> master = new HashMap<String, Object>();
			readXMLMaster(xmlfile, master);
			return master;
		}

		/**
		 * 获得各类别明细加权值Map<String,Object>集合, key=
		 * @param xmlfile
		 * 配置文件路径
		 * @return
		 * 返回Map<String key, Object value> Object为每项对应的明细Map<String,String>
		 */
		private static Map<String, Object> getDetail(String xmlfile) {
			Map<String, Object> detail = new HashMap<String, Object>();
			readXMLDetail(xmlfile, detail);
			return detail;
		}

		/**
		 * 读取 Master.XML文件 主系数文件到Map集合中		 
		 * @param xmlfile
		 * 文件名
		 */
		private static void readXMLMaster(String xmlfile, Map<String, Object>master) {
			URL xmlpath = getPath(xmlfile);
			if(xmlpath==null){System.out.println(xmlfile+"文件读取失败");}
			else
			try {
				Document doc = new SAXReader().read(xmlpath);
				Element element = doc.getRootElement(); // 获得根节点
				Iterator<?> iterator = element.elementIterator(); // 序列化根节点
				getChirdNode(iterator, master);
			} catch (DocumentException e) {
				System.out.println(xmlfile+"文件读取失败");
				e.printStackTrace();
			}
		}

		/**
		 * 读取 Detail.XML文件 各子系数文件到Map集合中
		 * @param xmlfile
		 * 文件名
		 */
		private static void readXMLDetail(String xmlfile, Map<String, Object> detail) {
			URL xmlpath = getPath(xmlfile);	
			if(xmlpath==null){System.out.println(xmlfile+"文件读取失败");}
			else
			try {
				Document doc = new SAXReader().read(xmlpath);			
				Element root = doc.getRootElement(); // 取得根节点
				Iterator<?> child = root.elementIterator(); // 获得根节点的二级节点序列化
				while (child.hasNext()) {
					Element childElement = (Element) child.next(); // 获得当前节点
					String key = childElement.getName().toString(); // 获得节点名
					Iterator<?> iterator = childElement.elementIterator(); // 序列化当前节点
					Map<String, Object> childvalue = new HashMap<String, Object>();
					getChirdNode(iterator, childvalue); //获得当前节点的子节点Map集合
					detail.put(key, childvalue);
				}
			} catch (DocumentException e) {
				System.out.println(xmlfile+"文件读取失败");
				e.printStackTrace();
			}
		}

		/**
		 * 封装页子节点到指定的Map集合中
		 * @param iterator
		 * 序列化页子节点父节点
		 * @param leafMap
		 * 封装的页子节点Map集合，key为节点名，value为节点值
		 */
		private static void getChirdNode(Iterator<?> iterator,Map<String, Object> leafMap) {
			leafMap.clear();
			while (iterator.hasNext()) {
				Element txtElement = (Element) iterator.next();
				String key = txtElement.getName().toString(); // 获得节点名
				String value = txtElement.getTextTrim();// 获得节点值
				leafMap.put(key, value);
			}
		}

		/**
		 * 获得文件路径处理		
		 * @param filename
		 *  类路径下的文件名
		 * @return  
		 * URL 格式文件路径含文件名
		 */
		private static URL getPath(String filename) {
			return GetParam.class.getClassLoader().getResource(filename);
		}
	}
}
