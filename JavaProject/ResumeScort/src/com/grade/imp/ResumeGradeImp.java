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
 * ��������ۺ�ƽ�� 
 * @author Joe Email:zoulong@yundasys.com
 * 
 */
public class ResumeGradeImp implements ResumeGrade {
	private String master;
	private String detail;
	private Map<String,Object> m_map, de_map;

	@Override
	public float getGrade(Map<String, String> paramkey) {
		
		// 1.��֤��������ĺϷ���
		if("".equals(this.master)|| this.master==null || "".equals(this.detail)|| this.detail==null){
			return -1;	//�ļ�����û������
		}
		
		if(paramkey==null){
			return -2; //�������������
		}
		
		// 2.��ȡXM��������
		if(m_map==null||de_map==null){
			List<Map<String,Object>> listmaps = GetParam.getMap(this.master, this.detail);
			m_map = listmaps.get(0);
			de_map = listmaps.get(1);
		}		
		if(m_map.size()==0||de_map.size()==0) return -3; //�ļ���ȡ�쳣
		
		// 3.���н������	
		float result = 0f;		
		for(String key :paramkey.keySet()){		
			String mul_a = (String) m_map.get(key); //ȡ�����Ȩֵ		
			
			if(mul_a==null){ 
				System.out.println("�����ֵΪ:"+(key==""?"Ϊ��":key)+"����!");
				return -4; //��������쳣
				} 		
			@SuppressWarnings("unchecked")
			Map<String,String> values = (Map<String,String>) de_map.get(key);	
			
			String mul_b = values.get(paramkey.get(key));
			
			if(mul_b==null){ 
				System.out.println("����:"+key+" ��Ӧ�������ֵΪ:"+(paramkey.get(key)==""?"Ϊ��":paramkey.get(key))+"����!");
				return -5; //��������쳣
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
	 * װ�ؼ�Ȩֵ����
	 * @author Joe
	 * 
	 */
	public static class GetParam {
		/* ������XML������ Map<String Object>���� */
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
		 * ��ȡ�������ȨֵMap<String,String>����
		 * @param xmlfile
		 *  �����ļ�·��
		 * @return
		 * ����Map<String key, String value> ��: map<en,0.02551742> key='en' value=0.02551742
		 */
		private static Map<String, Object> getMaster(String xmlfile) {
			Map<String, Object> master = new HashMap<String, Object>();
			readXMLMaster(xmlfile, master);
			return master;
		}

		/**
		 * ��ø������ϸ��ȨֵMap<String,Object>����, key=
		 * @param xmlfile
		 * �����ļ�·��
		 * @return
		 * ����Map<String key, Object value> ObjectΪÿ���Ӧ����ϸMap<String,String>
		 */
		private static Map<String, Object> getDetail(String xmlfile) {
			Map<String, Object> detail = new HashMap<String, Object>();
			readXMLDetail(xmlfile, detail);
			return detail;
		}

		/**
		 * ��ȡ Master.XML�ļ� ��ϵ���ļ���Map������		 
		 * @param xmlfile
		 * �ļ���
		 */
		private static void readXMLMaster(String xmlfile, Map<String, Object>master) {
			URL xmlpath = getPath(xmlfile);
			if(xmlpath==null){System.out.println(xmlfile+"�ļ���ȡʧ��");}
			else
			try {
				Document doc = new SAXReader().read(xmlpath);
				Element element = doc.getRootElement(); // ��ø��ڵ�
				Iterator<?> iterator = element.elementIterator(); // ���л����ڵ�
				getChirdNode(iterator, master);
			} catch (DocumentException e) {
				System.out.println(xmlfile+"�ļ���ȡʧ��");
				e.printStackTrace();
			}
		}

		/**
		 * ��ȡ Detail.XML�ļ� ����ϵ���ļ���Map������
		 * @param xmlfile
		 * �ļ���
		 */
		private static void readXMLDetail(String xmlfile, Map<String, Object> detail) {
			URL xmlpath = getPath(xmlfile);	
			if(xmlpath==null){System.out.println(xmlfile+"�ļ���ȡʧ��");}
			else
			try {
				Document doc = new SAXReader().read(xmlpath);			
				Element root = doc.getRootElement(); // ȡ�ø��ڵ�
				Iterator<?> child = root.elementIterator(); // ��ø��ڵ�Ķ����ڵ����л�
				while (child.hasNext()) {
					Element childElement = (Element) child.next(); // ��õ�ǰ�ڵ�
					String key = childElement.getName().toString(); // ��ýڵ���
					Iterator<?> iterator = childElement.elementIterator(); // ���л���ǰ�ڵ�
					Map<String, Object> childvalue = new HashMap<String, Object>();
					getChirdNode(iterator, childvalue); //��õ�ǰ�ڵ���ӽڵ�Map����
					detail.put(key, childvalue);
				}
			} catch (DocumentException e) {
				System.out.println(xmlfile+"�ļ���ȡʧ��");
				e.printStackTrace();
			}
		}

		/**
		 * ��װҳ�ӽڵ㵽ָ����Map������
		 * @param iterator
		 * ���л�ҳ�ӽڵ㸸�ڵ�
		 * @param leafMap
		 * ��װ��ҳ�ӽڵ�Map���ϣ�keyΪ�ڵ�����valueΪ�ڵ�ֵ
		 */
		private static void getChirdNode(Iterator<?> iterator,Map<String, Object> leafMap) {
			leafMap.clear();
			while (iterator.hasNext()) {
				Element txtElement = (Element) iterator.next();
				String key = txtElement.getName().toString(); // ��ýڵ���
				String value = txtElement.getTextTrim();// ��ýڵ�ֵ
				leafMap.put(key, value);
			}
		}

		/**
		 * ����ļ�·������		
		 * @param filename
		 *  ��·���µ��ļ���
		 * @return  
		 * URL ��ʽ�ļ�·�����ļ���
		 */
		private static URL getPath(String filename) {
			return GetParam.class.getClassLoader().getResource(filename);
		}
	}
}
