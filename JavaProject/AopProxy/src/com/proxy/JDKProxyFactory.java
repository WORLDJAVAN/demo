package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import com.service.impl.PersonServiceImpl;

/**
 * 通过代理对像的接口来实现动态代理
 * @author long
 */
public class JDKProxyFactory implements InvocationHandler {
	private Object targetObject; /* 用于保存的目标对像 */
	private Object runClass; /* 指需要附加执行的载方法对像 */

	public Object createProxyIntance(Object targetobject , Object runClass) {
		this.targetObject = targetobject;
		this.runClass = runClass;
		/* 获得代理对像 */
		return Proxy.newProxyInstance(this.targetObject.getClass()
				.getClassLoader(),
				this.targetObject.getClass().getInterfaces(), this); 
		        /*this 指的是当前代理类的 invoke回调方法 等价与如下操作：
				
				 new InvocationHander(){
				   public Object invoke(Object proxy, Method method, Object[] args){
				    return method.invoke(targetobject, args);  //执行的目标及参数，对应方法是对态拦截所得
				   }
				 }
				 */
	}

	/**
	 * 回调函数
	 * Object:代理对像本身、 Method:被拦截到的方法、Object[]:方法的参数
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable { // 整个方法可以看成环绕通知	
		runClass.wait(); //约定具体实类的接口做为参数来传递
		PersonServiceImpl person = (PersonServiceImpl) targetObject; /* 测试Person代理 */
		Object result = null;
		if (person.getUser() != null) {
			// ... advice() --> 前置通知
			try {
				result = method.invoke(targetObject, args); //此处为真正调用代理的方法
				// ... afteradvice() --> 后置通知
			} catch (Exception e) {
				// ... exceptionadvice() --> 例外通知
				e.printStackTrace();
			} finally {
				// ... finallyadvice() --> 最终通知
			}
		}
		return result;
	}
}
