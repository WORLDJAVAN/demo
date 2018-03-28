package com.analysis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.annotation.JoeAnotation;

/**
 * �����Զ���ע��
 * 
 * @author long
 */
public class Ano_Analysis {
	private static Logger log = Logger.getLogger(Ano_Analysis.class);
	private static Map<String, Object> primaryKeyMap = new HashMap<String, Object>(); // ���ڱ����ֶ��е�����ֵ��
	private static String tableName = null; // ���ڱ����ֶ��еı���
	private static List<String> listColumn = new ArrayList<String>(); // ���ڱ����ֶ������Ӧֵ
	private static List<String> listTable = new ArrayList<String>(); 
	private static List<String> listCondition = new ArrayList<String>();

	/**
	 * ��ñ���(insert)SQL���
	 * @param object
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws Exception
	 */
	public static String getSaveSQL(Object object) throws SecurityException,
			NoSuchMethodException, Exception {
		String insertSql = null;
		Map<String, Object> valueMap = getEntityData(object); // ���Object�����еļ�ֵ��Map����

		if (!valueMap.isEmpty() && valueMap.size() > 0) { // ������������ֵ
			StringBuffer sql_column = new StringBuffer("insert into " + tableName + " ( ");
			StringBuffer sql_values = new StringBuffer(" values ( "); // ע��ʱ������Ҳ��Ҫ��" ' "

			for (Object key : valueMap.keySet()) {
				sql_column.append(key + ", ");
				if (valueMap.get(key) instanceof String) {
					sql_values.append("'" + valueMap.get(key) + "', ");
					continue;
				} 
				if (valueMap.get(key) instanceof Date) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					sql_values.append("'" + formatter.format(valueMap.get(key)) + "', ");
					continue;
				} 	
				if (valueMap.get(key) instanceof Time) {
					SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
					sql_values.append("'" + formatter.format(valueMap.get(key)) + "', ");
					continue;
				} 
				if (valueMap.get(key) instanceof Timestamp) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					sql_values.append("'" + formatter.format(valueMap.get(key)) + "', ");
					continue;
				} 				
				sql_values.append(valueMap.get(key) + ", ");
			}

			sql_column.deleteCharAt(sql_column.lastIndexOf(",")).append(")");
			sql_values.deleteCharAt(sql_values.lastIndexOf(",")).append(")");

			insertSql = sql_column.append(sql_values).toString();
		}
		return insertSql;
	}

	/**
	 * ����޸�(update)SQL��� 
	 * @param object ����ʵ��
	 * @param condition �Զ�������
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws Exception
	 */
	public static String getUpdateSQL(Object object, String condition)
			throws SecurityException, NoSuchMethodException, Exception {
		StringBuffer updatesql = null;

		/* ���Object�����еļ�ֵ�� Map���� */
		Map<String, Object> valueMap = getEntityData(object);

		if (!valueMap.isEmpty() && valueMap.size() > 0) { // ������������ֵ
			updatesql = new StringBuffer("update " + tableName + " set ");
			for (Object key : valueMap.keySet()) {
				
				if (valueMap.get(key) instanceof String) {
					updatesql.append(key+"='" + valueMap.get(key) + "', ");
					continue;
				} 
				if (valueMap.get(key) instanceof Date) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					updatesql.append(key+"='" + formatter.format(valueMap.get(key)) + "', ");
					continue;
				} 	
				if (valueMap.get(key) instanceof Time) {
					SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
					updatesql.append(key+"='" + formatter.format(valueMap.get(key)) + "', ");
					continue;
				} 
				if (valueMap.get(key) instanceof Timestamp) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					updatesql.append(key+"='" + formatter.format(valueMap.get(key)) + "', ");
					continue;
				} 							
				updatesql.append(key + "=" + valueMap.get(key) + ","); 
			}
			updatesql.deleteCharAt(updatesql.lastIndexOf(",")).append(
					" where 1=1");

		}
		/* ��������ֵ���ж����� */
		if (condition != null && !condition.equals("")) {
			updatesql.append(" and " + condition);
		} 
		/* Ĭ�ϰ�����ֵ����  */
		else if (!primaryKeyMap.isEmpty() && primaryKeyMap.size() > 0) {
			for (Object key : primaryKeyMap.keySet()) {
				if (valueMap.get(key) instanceof String) {
					updatesql.append(" and " +key+"='" + valueMap.get(key) + "', ");
					continue;
				} 
				if (valueMap.get(key) instanceof Date) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
					updatesql.append(" and " +key+"='" + formatter.format(valueMap.get(key)) + "', ");
					continue;
				} 	
				if (valueMap.get(key) instanceof Time) {
					SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
					updatesql.append(" and " +key+"='" + formatter.format(valueMap.get(key)) + "', ");
					continue;
				} 
				if (valueMap.get(key) instanceof Timestamp) {
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					updatesql.append(" and " +key+"='" + formatter.format(valueMap.get(key)) + "', ");
					continue;
				} 							
				updatesql.append(" and " + key + "=" + valueMap.get(key));
			}
		}
		return updatesql.toString();
	}

	/**
	 * ����޸�(update)SQL��� ��������Ĭ������ֵ
	 * @param object
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws Exception
	 */
	public static String getUpdateSQL(Object object) throws SecurityException,
			NoSuchMethodException, Exception {
		return getUpdateSQL(object, null);
	}

    /**
     * ��ϴ���ѯ������� 1.�Զ��������ӷ�ʽ  condition: from table_a A left join on  table_b B..
     *                  2.�Զ���
     * @param clz
     * @param condition
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws Exception
     */
	public static String findSQL(Class<?> clz, String condition, String ordGroCondition )throws SecurityException,NoSuchMethodException, Exception {	
		StringBuffer findsql = new StringBuffer();
		/* װ�����ݱ��ֶ�������� */
		loadAnotation(clz);
		
		/* װ����Ҫ��ѯ���ֶ� */
		if(listColumn !=null && listColumn.size()>0){
			findsql.append(" select ");
			for(String listValue:listColumn){	
				findsql.append(listValue+", ");
			}	
			findsql.deleteCharAt(findsql.lastIndexOf(","));
			
		} else{		
			log.error("û��Ϊ[" + clz.getName()+ "]ʵ���ࡢ���ö�Ӧ�����ݿ����ֶ�����");
			throw new Exception("���ݱ���ֶδ���");			
		}
		
		if(listTable !=null && listTable.size()>0){
			if(condition !=null && !condition.equals("")){
				/* �� from xxx �����û��Լ���д,�����û���д�������Ӳ�ѯ   */
				if(condition.contains("from")){
					findsql.append(condition);	
				} 
				/* �û�ֻ��д where������������  */
				else{ 
					findsql.append(" from ");
					for(String listValue:listTable){	
						findsql.append(listValue+", ");
					}
					
				    findsql.deleteCharAt(findsql.lastIndexOf(","));
				    
					if (!condition.contains("where"))
					findsql.append(" where ");
					findsql.append(condition);					 
					if(listCondition!=null && listCondition.size()>0){
					   for(String listValue:listCondition){	
						  findsql.append(" and "+listValue);
					  }	
				   }				
				}			
			}
			else{ /* �����κ�������ѯ ������ from tableA(, tableB ...) where 1=1, ������ Conditionע��������Ĭ�ϼ����� */
				findsql.append(" from ");
				for(String listValue:listTable){	
					findsql.append(listValue+", ");
				}	
				findsql.deleteCharAt(findsql.lastIndexOf(",")).append(" where 1=1 ");
				
				if(listCondition!=null && listCondition.size()>0){
					for(String listValue:listCondition){	
						findsql.append(" and "+listValue);
					}	
				}
			}			
		}
		else{
			log.error("û��Ϊ[" + clz.getName()+ "]ʵ���ࡢ���ö�Ӧ�����ݿ��������");
			throw new Exception("���ݱ�������");	
		}
	   
		/* �����������  */
	    if(ordGroCondition !=null && !ordGroCondition.equals(""))
		    { findsql.append(" "+ordGroCondition);}
	    
       return findsql.toString();  
    }
	/**
	 * ����������ѯSQL���  ��:select column_a, column_b, ... from table_name 
	 * @param clz
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws Exception
	 */
	public static String findSQL(Class<?> clz)throws SecurityException,NoSuchMethodException, Exception {
		return findSQL(clz,null,null);
	}
	
	/**
	 * ��������ѯSQL���  ��:select column_a, column_b, ... from table_name where condition 
	 * @param clz
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws Exception
	 */
	public static String findSQL(Class<?> clz,String condition)throws SecurityException,NoSuchMethodException, Exception {
		return findSQL(clz,condition,null);
	}
	
	/**
	 * ����������ĸ��д
	 * @param fildeName
	 * @return
	 * @throws Exception
	 */
	private static String getMethodName(String fildeName) throws Exception {
		byte[] items = fildeName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	/**
	 * ȡʵ������ֵ������ֶζ�Ӧ������Map<String column, Object entityValue>��
	 * @param object
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws Exception
	 */
	private static Map<String, Object> getEntityData(Object object) throws SecurityException, NoSuchMethodException, Exception {
		Map<String, Object> valueMap = new HashMap<String, Object>(); // ���ڱ����ֶ������Ӧֵ
		Class<? extends Object> cls = object.getClass();
		Method method = null;
		String fieldName = null;
		Object fieldValue = null;
		JoeAnotation joeField = null;
		for (Field field : cls.getDeclaredFields()) { // ��õ�ǰ��ѯ��������Ժ�ֵ
			fieldName = field.getName();
			if ("serialVersionUID".equals(fieldName))
				continue; /* ���л��ֶ�ֵ ���� */
			joeField = field.getAnnotation(JoeAnotation.class);
			{
				if (joeField != null) // ���ֶδ���ע��
				{
					if (joeField.Id() != null && !joeField.Id().equals("")) { // ���ֶ�Ϊ���ݿ�����
						try {
							method = cls.getMethod("get"
									+ getMethodName(fieldName));
							fieldValue = method.invoke(object); // ��ø��ֶε�ֵ
							valueMap.put(joeField.Id(), fieldValue); // ��ȡ��ֵ��valueMap����

							primaryKeyMap.put(joeField.Id(), fieldValue); // ����������ֵ

						} catch (Exception e) {
							log.error("û��Ϊ[" + object.getClass().getName()
									+ "]������[" + fieldName + "]�ṩget����");
							throw new Exception("���ݱ�������");
						}
					} else if (joeField.column() != null
							&& !joeField.column().equals("")) { // ���ֶ��ϱ�ע������
						try {
							method = cls.getMethod("get"
									+ getMethodName(fieldName));

							fieldValue = method.invoke(object); // ��ø��ֶε�ֵ
							valueMap.put(joeField.column(), fieldValue); // ��ȡ��ֵ��valueMap����
						} catch (Exception e) {
							log.error("û��Ϊ[" + object.getClass().getName()
									+ "]������[" + fieldName + "]�ṩget����");
							throw new Exception("���ݱ�������");
						}
					}
					if (joeField.table() != null
							&& !joeField.table().equals("")) { // ���ֶ��ϱ�ע�б���
						tableName = joeField.table(); // ����ע�͵ı���
					}
				} else {
					log.error("û��Ϊ��ѯ����" + object.getClass().getName() + "��["
							+ fieldName + "]�������ö�Ӧ���ݿ���ֶ�");
					throw new Exception("�����ֶ����ô���");
				}
			}
		}
		return valueMap;
	}
	
	
    /**
     * װ����Ҫ��ѯ����ֶ�
     * @param clz
     */
	private static void loadAnotation(Class<?> clz){
		
		String fieldName = null;
		JoeAnotation joeField = null;	
		
		for (Field field : clz.getDeclaredFields()) { // ��õ�ǰ��ѯ��������Ժ�ֵ
			fieldName = field.getName();
			if ("serialVersionUID".equals(fieldName))
				continue; /* ���л��ֶ�ֵ ���� */

			joeField = field.getAnnotation(JoeAnotation.class);
			{
				if (joeField != null) // ���ֶδ���ע��
				{
					if (joeField.Id() != null && !joeField.Id().equals("")) { // ���ֶ�Ϊ���ݿ�����
						 listColumn.add(joeField.Id());  //����ע�͵��ֶ�
						}					
				    else if (joeField.column() != null && !joeField.column().equals("")) { // ���ֶ��ϱ�ע������
						listColumn.add(joeField.column()); //����ע�͵��ֶ�
					}	
					
					if (joeField.table() != null && !joeField.table().equals("")) { // ���ֶ��ϱ�ע�б���
						listTable.add(joeField.table()); // ����ע�͵ı���
					}
					
					if (joeField.condition() != null && !joeField.condition().equals("")) { // ���ֶ��ϱ�ע�ж����������
						listCondition.add(joeField.condition()); // ����ע�͵ı���������
					}					
				}
			}			
		}
	}	
}
