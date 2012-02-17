package org.mobilelite.android;

import java.lang.reflect.Method;
import java.util.List;

import org.mobilelite.annotation.ServiceMethod;

public interface InvokerInterceptor {

	void preHandle(Object bean, Method method, List<Object> params, ServiceMethod serviceMethod, ExecuteAttempt attempt);

	void postHandle(List<Object> params, Object result, ServiceMethod serviceMethod);


}
