package com.proxy;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import com.service.impl.PersonServiceImpl;

/**
 * ͨ����������������ʵ�ֶ�̬����
 * @author long
 */
public class CGlibProxyFactory implements MethodInterceptor {
	private Object targetObject;

	public Object createProxyFactory(Object targetObject) {
		this.targetObject = targetObject;
		Enhancer enhancer = new Enhancer();
		/* ���������������� */
		enhancer.setSuperclass(this.targetObject.getClass());
		/* ���ûص����� */
		enhancer.setCallback(this);
		/* ���ش����Ĵ������ */
		return enhancer.create();
	}

	/**
	 * Object:��������� Method:�����ص��ķ����� 
	 * Object[]:�����Ĳ����� MethodProxy:�����Ĵ������
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		Object result = null;
		/* ����Person���� */
		PersonServiceImpl person = (PersonServiceImpl) targetObject;
		if (person.getUser() != null) {
			result = methodProxy.invoke(targetObject, args);
		}
		return result;
	}
}
