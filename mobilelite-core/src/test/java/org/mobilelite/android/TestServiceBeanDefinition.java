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

import static org.junit.Assert.*;
import org.junit.Test;
import org.mobilelite.android.ServiceBeanDefinition;
import org.mobilelite.annotation.Service;
import org.mobilelite.annotation.ServiceMethod;

public class TestServiceBeanDefinition {

	@Test
	public void testNewInstanceForNonServiceBean() {
		assertNull(ServiceBeanDefinition.newInstance("beanName", new Object()));
	}
	
	@Test
	public void testNewInstanceForServiceBean() {
		ServiceBeanDefinition definition = ServiceBeanDefinition.newInstance("beanName", new UserService());
		assertNotNull(definition);
		assertEquals("beanName", definition.getName());
		assertEquals(2, definition.getMethodNames().size());
		assertTrue(definition.getMethodNames().contains("addUser"));
		assertTrue(definition.getMethodNames().contains("findUser"));
	}
	
	@Service
	private class UserService {
		
		@ServiceMethod
		@SuppressWarnings("unused")
		public void addUser(Object user) {}
		
		@ServiceMethod
		@SuppressWarnings("unused")
		public void addUser(String username) {}
		
		@ServiceMethod
		@SuppressWarnings("unused")
		public void findUser(String username) {}
		
		@SuppressWarnings("unused")
		public void nonServiceMethod() {}
		
	}
	
}
