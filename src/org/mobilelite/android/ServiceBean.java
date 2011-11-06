package org.mobilelite.android;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class ServiceBean {
	
	private String name;
	private Object bean;

	public ServiceBean(String name, Object bean) {
		this.name = name;
		this.bean = bean;
	}

	public ServiceBeanDefinition getServiceBeanDefinition() {
		return ServiceBeanDefinition.newInstance(name, bean);
	}

	@SuppressWarnings("unchecked")
	public Object invoke(String methodName, String jsonParam) {
		Object result = null;
		Gson gson = new Gson();
		JsonParser jsonParser = new JsonParser();
		JsonElement je = jsonParser.parse(jsonParam);
		
		JsonArray jaParams = null;
		if(je.isJsonArray()) {
			jaParams = je.getAsJsonArray();
		}
		else if(je.isJsonObject() || je.isJsonPrimitive()) {
			jaParams = new JsonArray();
			jaParams.add(je);
		}
		else if(je.isJsonNull()) {
			jaParams = new JsonArray();
		}

		Method[] methods = getBeanMethods(name, jaParams.size());
		for ( Method method : methods ) {
			@SuppressWarnings("rawtypes")
			Class[] paramClasses = method.getParameterTypes();
			List<Object> params = new ArrayList<Object>();
			try {
				for(int i=0; i<paramClasses.length; i++) {
					params.add(gson.fromJson(jaParams.get(i), paramClasses[i]));
				}
				
				result = method.invoke(bean, params.toArray());
				break;
			} catch (JsonSyntaxException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}

		return result;
	}

	private Method[] getBeanMethods(String methodName, int paramNum) {
		List<Method> methods = new ArrayList<Method>();
		Method[] beanMethods = bean.getClass().getDeclaredMethods();
		for(Method method : beanMethods) {
			if(method.getParameterTypes().length == paramNum)
				methods.add(method);
		}
		return methods.toArray(new Method[]{});
	}

}
