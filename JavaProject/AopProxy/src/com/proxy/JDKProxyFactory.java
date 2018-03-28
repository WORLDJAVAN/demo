package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import com.service.impl.PersonServiceImpl;

/**
 * ͨ���������Ľӿ���ʵ�ֶ�̬����
 * @author long
 */
public class JDKProxyFactory implements InvocationHandler {
	private Object targetObject; /* ���ڱ����Ŀ����� */
	private Object runClass; /* ָ��Ҫ����ִ�е��ط������� */

	public Object createProxyIntance(Object targetobject , Object runClass) {
		this.targetObject = targetobject;
		this.runClass = runClass;
		/* ��ô������ */
		return Proxy.newProxyInstance(this.targetObject.getClass()
				.getClassLoader(),
				this.targetObject.getClass().getInterfaces(), this); 
		        /*this ָ���ǵ�ǰ������� invoke�ص����� �ȼ������²�����
				
				 new InvocationHander(){
				   public Object invoke(Object proxy, Method method, Object[] args){
				    return method.invoke(targetobject, args);  //ִ�е�Ŀ�꼰��������Ӧ�����Ƕ�̬��������
				   }
				 }
				 */
	}

	/**
	 * �ص�����
	 * Object:��������� Method:�����ص��ķ�����Object[]:�����Ĳ���
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable { // �����������Կ��ɻ���֪ͨ	
		runClass.wait(); //Լ������ʵ��Ľӿ���Ϊ����������
		PersonServiceImpl person = (PersonServiceImpl) targetObject; /* ����Person���� */
		Object result = null;
		if (person.getUser() != null) {
			// ... advice() --> ǰ��֪ͨ
			try {
				result = method.invoke(targetObject, args); //�˴�Ϊ�������ô���ķ���
				// ... afteradvice() --> ����֪ͨ
			} catch (Exception e) {
				// ... exceptionadvice() --> ����֪ͨ
				e.printStackTrace();
			} finally {
				// ... finallyadvice() --> ����֪ͨ
			}
		}
		return result;
	}
}
