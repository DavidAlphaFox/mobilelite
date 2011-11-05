package org.mobilelite.android;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class BeanDefinition {

	private String name;
	
	private List<String> methodNames = new ArrayList<String>();
	
	private Object bean;
	
	public BeanDefinition(String name, Object bean) {
		this.name = name;
		this.bean = bean;
		initMethodNames();
	}
	
	private void initMethodNames() {
		Method[] methods = bean.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			methodNames.add(methods[i].getName());
		}
	}

	public String getName() {
		return name;
	}

	public List<String> getMethodNames() {
		return methodNames;
	}

}
