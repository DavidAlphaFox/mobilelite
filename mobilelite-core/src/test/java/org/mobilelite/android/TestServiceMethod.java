package org.mobilelite.android;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import junit.framework.Assert;

import org.junit.Test;
import org.mobilelite.annotation.Service;
import org.mobilelite.annotation.ServiceMethod;

public class TestServiceMethod {

	@Service
	public class ObjService {

		@ServiceMethod
		public void test1() {
		}

		@ServiceMethod(showDialog = true, title = "Demo Title", message = "Demo Message")
		public void test2() {
		}
	}

	@Test
	public void testAnnotationForMethod1() throws SecurityException, NoSuchMethodException {
		Method method = ObjService.class.getMethod("test1", null);
		ServiceMethod serviceMethod = method.getAnnotation(ServiceMethod.class);
		Assert.assertFalse(serviceMethod.showDialog());
		Assert.assertEquals("no title", serviceMethod.title());
		Assert.assertEquals("default message", serviceMethod.message());
	}

	@Test
	public void testAnnotationForMethod2() throws SecurityException, NoSuchMethodException {
		Method method = ObjService.class.getMethod("test2", null);
		ServiceMethod serviceMethod = method.getAnnotation(ServiceMethod.class);
		Assert.assertTrue(serviceMethod.showDialog());
		Assert.assertEquals("Demo Title", serviceMethod.title());
		Assert.assertEquals("Demo Message", serviceMethod.message());
	}
}
