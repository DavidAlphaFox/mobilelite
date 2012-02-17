package org.mobilelite.android.interceptor;

import java.lang.reflect.Method;
import java.util.List;

import org.mobilelite.android.ExecuteAttempt;
import org.mobilelite.android.InvokerInterceptor;
import org.mobilelite.android.cache.Cache;
import org.mobilelite.android.cache.CacheProvider;
import org.mobilelite.annotation.ServiceMethod;

public class CacheInterceptor implements InvokerInterceptor {

	@Override
	public void preHandle(Object bean, Method method, List<Object> params, ServiceMethod serviceMethod, ExecuteAttempt attempt) {
		if(serviceMethod.cache() != null && serviceMethod.cache().length() > 0 ) {
			Cache cache = CacheProvider.getCache(serviceMethod.cache());
			Object cacheObj = cache.getCacheObj(params);
			if(cacheObj != null) {
				attempt.setSkipExecute(true);
				attempt.setData(cacheObj);
			}
		}
	}

	@Override
	public void postHandle(List<Object> params, Object result, ServiceMethod serviceMethod) {
		if(serviceMethod.cache() != null && serviceMethod.cache().length() > 0 ) {
			Cache cache = CacheProvider.getCache(serviceMethod.cache());
			cache.putCacheObj(params, result);
		}
	}

}
