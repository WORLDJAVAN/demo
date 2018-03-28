package com.proxy;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import com.service.impl.PersonServiceImpl;

/**
 * 通过代理对像的子类来实现动态代理
 * @author long
 */
public class CGlibProxyFactory implements MethodInterceptor {
	private Object targetObject;

	public Object createProxyFactory(Object targetObject) {
		this.targetObject = targetObject;
		Enhancer enhancer = new Enhancer();
		/* 创建代理对像的子类 */
		enhancer.setSuperclass(this.targetObject.getClass());
		/* 设置回调函数 */
		enhancer.setCallback(this);
		/* 返回创建的代理对像 */
		return enhancer.create();
	}

	/**
	 * Object:代理对像本身、 Method:被拦截到的方法、 
	 * Object[]:方法的参数、 MethodProxy:方法的代理对像
	 */
	@Override
	public Object intercept(Object proxy, Method method, Object[] args,
			MethodProxy methodProxy) throws Throwable {
		Object result = null;
		/* 测试Person代理 */
		PersonServiceImpl person = (PersonServiceImpl) targetObject;
		if (person.getUser() != null) {
			result = methodProxy.invoke(targetObject, args);
		}
		return result;
	}
}
