package org.mobilelite.android;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.mobilelite.annotation.ServiceMethod;


public class DefaultServiceBeanInvoker extends AbstractServiceBeanInvoker {

	protected InvokerInterceptor[] interceptors; 

	@Override
	protected Object executeMethod(Object bean, Method method, List<Object> params, ServiceMethod serviceMethod) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		ExecuteAttempt attempt = prepareExcecute(bean, method, params, serviceMethod);
		if(attempt.isSkipExecute())
			return postInvoke(method, params, serviceMethod, attempt.getData());
		return super.executeMethod(bean, method, params, serviceMethod);
	}

	private ExecuteAttempt prepareExcecute(Object bean, Method method, List<Object> params, ServiceMethod serviceMethod) {
		ExecuteAttempt attempt = new ExecuteAttempt();
		if(interceptors != null) {
			for(int i=0; i<interceptors.length; i++) {
				interceptors[i].preHandle(bean, method, params, serviceMethod, attempt);
			}
		}
		return attempt;
	}

	@Override
	protected Object postInvoke(Method method, List<Object> params, ServiceMethod serviceMethod, Object result) {
		if(interceptors != null) {
			for(int i=interceptors.length; i>0; i--) {
				interceptors[i-1].postHandle(params, result, serviceMethod);
			}
		}
		return super.postInvoke(method, params, serviceMethod, result);
	}

	
}
