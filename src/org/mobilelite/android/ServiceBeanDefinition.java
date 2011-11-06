package org.mobilelite.android;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.mobilelite.annotation.Service;
import org.mobilelite.annotation.ServiceMethod;

public class ServiceBeanDefinition {

	private String name;
	
	private List<String> methodNames = new ArrayList<String>();
	
	private Object bean;
	
	public static ServiceBeanDefinition newInstance(String name, Object bean) {
		if (bean.getClass().isAnnotationPresent(Service.class)) {
			// is service object, getting its bean definition
			return new ServiceBeanDefinition(name, bean);
		} else {
			return null;
		}
	}
	
	private ServiceBeanDefinition(String name, Object bean) {
		this.name = name;
		this.bean = bean;
		initMethodNames();
	}
	
	private void initMethodNames() {
		Method[] methods = bean.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].isAnnotationPresent(ServiceMethod.class)) {
				// is service method, need to be exposed to definition
				String methodName = methods[i].getName();
				if(!methodNames.contains(methodName)) {
					methodNames.add(methods[i].getName());
				}
			}
		}
	}

	public String getName() {
		return name;
	}

	public List<String> getMethodNames() {
		return methodNames;
	}

}
