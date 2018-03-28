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
 * 解析自定义注解
 * 
 * @author long
 */
public class Ano_Analysis {
	private static Logger log = Logger.getLogger(Ano_Analysis.class);
	private static Map<String, Object> primaryKeyMap = new HashMap<String, Object>(); // 用于保存字段中的主键值对
	private static String tableName = null; // 用于保存字段中的表名
	private static List<String> listColumn = new ArrayList<String>(); // 用于保存字段名与对应值
	private static List<String> listTable = new ArrayList<String>(); 
	private static List<String> listCondition = new ArrayList<String>();

	/**
	 * 获得保存(insert)SQL语句
	 * @param object
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws Exception
	 */
	public static String getSaveSQL(Object object) throws SecurityException,
			NoSuchMethodException, Exception {
		String insertSql = null;
		Map<String, Object> valueMap = getEntityData(object); // 获得Object对像中的键值对Map集合

		if (!valueMap.isEmpty() && valueMap.size() > 0) { // 表明里面存的有值
			StringBuffer sql_column = new StringBuffer("insert into " + tableName + " ( ");
			StringBuffer sql_values = new StringBuffer(" values ( "); // 注意时间类型也需要加" ' "

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
	 * 获得修改(update)SQL语句 
	 * @param object 更新实体
	 * @param condition 自定义条件
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws Exception
	 */
	public static String getUpdateSQL(Object object, String condition)
			throws SecurityException, NoSuchMethodException, Exception {
		StringBuffer updatesql = null;

		/* 获得Object对像中的键值对 Map集合 */
		Map<String, Object> valueMap = getEntityData(object);

		if (!valueMap.isEmpty() && valueMap.size() > 0) { // 表明里面存的有值
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
		/* 加入主键值的判断条件 */
		if (condition != null && !condition.equals("")) {
			updatesql.append(" and " + condition);
		} 
		/* 默认按主键值更新  */
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
	 * 获得修改(update)SQL语句 、条件按默认主键值
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
     * 组合带查询条的语句 1.自定义表的联接方式  condition: from table_a A left join on  table_b B..
     *                  2.自定义
     * @param clz
     * @param condition
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws Exception
     */
	public static String findSQL(Class<?> clz, String condition, String ordGroCondition )throws SecurityException,NoSuchMethodException, Exception {	
		StringBuffer findsql = new StringBuffer();
		/* 装载数据表字段与表名称 */
		loadAnotation(clz);
		
		/* 装载需要查询的字段 */
		if(listColumn !=null && listColumn.size()>0){
			findsql.append(" select ");
			for(String listValue:listColumn){	
				findsql.append(listValue+", ");
			}	
			findsql.deleteCharAt(findsql.lastIndexOf(","));
			
		} else{		
			log.error("没有为[" + clz.getName()+ "]实体类、设置对应的数据库表的字段属性");
			throw new Exception("数据表的字段错误");			
		}
		
		if(listTable !=null && listTable.size()>0){
			if(condition !=null && !condition.equals("")){
				/* 从 from xxx 都由用户自己填写,便于用户填写左右连接查询   */
				if(condition.contains("from")){
					findsql.append(condition);	
				} 
				/* 用户只填写 where后面条件部分  */
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
			else{ /* 不加任何条件查询 、补齐 from tableA(, tableB ...) where 1=1, 如多表有 Condition注释条件、默认加条件 */
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
			log.error("没有为[" + clz.getName()+ "]实体类、设置对应的数据库表名属性");
			throw new Exception("数据表名错误");	
		}
	   
		/* 分组条件添加  */
	    if(ordGroCondition !=null && !ordGroCondition.equals(""))
		    { findsql.append(" "+ordGroCondition);}
	    
       return findsql.toString();  
    }
	/**
	 * 不带条件查询SQL语句  如:select column_a, column_b, ... from table_name 
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
	 * 带条件查询SQL语句  如:select column_a, column_b, ... from table_name where condition 
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
	 * 将属性首字母大写
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
	 * 取实体属性值与表中字段对应、放入Map<String column, Object entityValue>中
	 * @param object
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws Exception
	 */
	private static Map<String, Object> getEntityData(Object object) throws SecurityException, NoSuchMethodException, Exception {
		Map<String, Object> valueMap = new HashMap<String, Object>(); // 用于保存字段名与对应值
		Class<? extends Object> cls = object.getClass();
		Method method = null;
		String fieldName = null;
		Object fieldValue = null;
		JoeAnotation joeField = null;
		for (Field field : cls.getDeclaredFields()) { // 获得当前查询对象的属性和值
			fieldName = field.getName();
			if ("serialVersionUID".equals(fieldName))
				continue; /* 序列化字段值 忽略 */
			joeField = field.getAnnotation(JoeAnotation.class);
			{
				if (joeField != null) // 该字段存在注解
				{
					if (joeField.Id() != null && !joeField.Id().equals("")) { // 该字段为数据库主键
						try {
							method = cls.getMethod("get"
									+ getMethodName(fieldName));
							fieldValue = method.invoke(object); // 获得该字段的值
							valueMap.put(joeField.Id(), fieldValue); // 把取得值放valueMap集合

							primaryKeyMap.put(joeField.Id(), fieldValue); // 保存主键的值

						} catch (Exception e) {
							log.error("没有为[" + object.getClass().getName()
									+ "]的属性[" + fieldName + "]提供get方法");
							throw new Exception("数据表名错误");
						}
					} else if (joeField.column() != null
							&& !joeField.column().equals("")) { // 该字段上标注有列名
						try {
							method = cls.getMethod("get"
									+ getMethodName(fieldName));

							fieldValue = method.invoke(object); // 获得该字段的值
							valueMap.put(joeField.column(), fieldValue); // 把取得值放valueMap集合
						} catch (Exception e) {
							log.error("没有为[" + object.getClass().getName()
									+ "]的属性[" + fieldName + "]提供get方法");
							throw new Exception("数据表名错误");
						}
					}
					if (joeField.table() != null
							&& !joeField.table().equals("")) { // 该字段上标注有表名
						tableName = joeField.table(); // 保存注释的表名
					}
				} else {
					log.error("没有为查询对象：" + object.getClass().getName() + "的["
							+ fieldName + "]属性配置对应数据库表字段");
					throw new Exception("数据字段设置错误");
				}
			}
		}
		return valueMap;
	}
	
	
    /**
     * 装载需要查询类的字段
     * @param clz
     */
	private static void loadAnotation(Class<?> clz){
		
		String fieldName = null;
		JoeAnotation joeField = null;	
		
		for (Field field : clz.getDeclaredFields()) { // 获得当前查询对象的属性和值
			fieldName = field.getName();
			if ("serialVersionUID".equals(fieldName))
				continue; /* 序列化字段值 忽略 */

			joeField = field.getAnnotation(JoeAnotation.class);
			{
				if (joeField != null) // 该字段存在注解
				{
					if (joeField.Id() != null && !joeField.Id().equals("")) { // 该字段为数据库主键
						 listColumn.add(joeField.Id());  //保存注释的字段
						}					
				    else if (joeField.column() != null && !joeField.column().equals("")) { // 该字段上标注有列名
						listColumn.add(joeField.column()); //保存注释的字段
					}	
					
					if (joeField.table() != null && !joeField.table().equals("")) { // 该字段上标注有表名
						listTable.add(joeField.table()); // 保存注释的表名
					}
					
					if (joeField.condition() != null && !joeField.condition().equals("")) { // 该字段上标注有多表连接条件
						listCondition.add(joeField.condition()); // 保存注释的表连接条件
					}					
				}
			}			
		}
	}	
}
