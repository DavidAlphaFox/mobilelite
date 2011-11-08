/*
 * Copyright (C) 2011 Tony.Ni, Jim.Jiang http://mobilelite.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mobilelite.android;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
	public Object invoke(String methodName, JsonElement jsonParam) {
		Object result = null;
		Gson gson = new Gson();
		JsonElement je = jsonParam;
		
		JsonArray jaParams = null;
		if(je.isJsonArray()) {
			jaParams = je.getAsJsonArray();
		} else if(je.isJsonObject() || je.isJsonPrimitive()) {
			jaParams = new JsonArray();
			jaParams.add(je);
		} else if(je.isJsonNull()) {
			jaParams = new JsonArray();
		}

		Method[] methods = getBeanMethods(methodName, jaParams.size());
		for (Method method : methods) {
			@SuppressWarnings("rawtypes")
			Class[] paramClasses = method.getParameterTypes();
			List<Object> params = new ArrayList<Object>();
			try {
				for (int i = 0; i < paramClasses.length; i++) {
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
		for (Method method : beanMethods) {
			if (method.getName().equals(methodName) 
					&& method.getParameterTypes().length == paramNum)
				methods.add(method);
		}
		return methods.toArray(new Method[]{});
	}

}
