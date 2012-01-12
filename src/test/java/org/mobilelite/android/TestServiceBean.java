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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mobilelite.android.ServiceBean;
import org.mobilelite.annotation.Service;
import org.mobilelite.annotation.ServiceMethod;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Ignore
public class TestServiceBean {
	
	JsonParser jsonParser;
	JsonElement je;
	ServiceBean serviceBean;
	
	@Before
	public void setUp() {
		jsonParser = new JsonParser();
		serviceBean = new ServiceBean("bean", new UserService());
	}
	
	/*
	@Test
	public void testInvokeFindUserByString() {
		je = jsonParser.parse("jim");
		Object result = serviceBean.invoke("findUser", je);
		assertTrue(result instanceof String);
		assertEquals("new-jim", result);
	}
	
	@Test
	public void testInvokeFindUserByObject() {
		je = jsonParser.parse("{username:\"jim\", age:18}");
		Object result = serviceBean.invoke("findUser", je);
		assertTrue(result instanceof User);
		assertEquals("new-jim", ((User) result).getUsername());
		assertEquals(18, ((User) result).getAge());
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testInvokeFindUserBy2Parameters() {
		je = jsonParser.parse("[{username:\"jim\", age:18}, 2]");
		Object result = serviceBean.invoke("findUser", je);
		assertTrue(result instanceof List);
		assertEquals(2, ((List<User>) result).size());
		assertEquals("new-jim1", ((List<User>) result).get(0).getUsername());
		assertEquals(18, ((List<User>) result).get(0).getAge());
		assertEquals("new-jim2", ((List<User>) result).get(1).getUsername());
		assertEquals(18, ((List<User>) result).get(1).getAge());
	}
	
	@Test
	@Ignore
	@SuppressWarnings("unchecked")
	public void testInvokeFindUserByList() {
		// TODO:
		// now it doesn't support List as parameter, because the parser doesn't know the exact generic type of this list
		je = jsonParser.parse("[{username:\"jim1\"}, {username:\"jim2\"}]");
		Object result = serviceBean.invoke("findUser", je);
		assertTrue(result instanceof List);
		assertEquals(2, ((List<User>) result).size());
		assertEquals("new-jim1", ((List<User>) result).get(0).getUsername());
		assertEquals("new-jim2", ((List<User>) result).get(1).getUsername());
	}
	
	@Test
	public void testInvokeNonExistMethod() {
		je = jsonParser.parse("aMethod");
		Object result = serviceBean.invoke("aMethod", je);
		assertNull(result);
	}
	*/
	
	@Service
	public class UserService {
		
		@ServiceMethod
		public String findUser(String username) {
			return "new-" + username;
		}
		
		@ServiceMethod
		public User findUser(User user) {
			return new User("new-" + user.getUsername(), user.getAge());
		}
		
		@ServiceMethod
		public List<User> findUser(User user, int count) {
			List<User> results = new ArrayList<User>();
			for (int i = 0; i < count; i++) {
				results.add(new User("new-" + user.getUsername() + (i + 1), user.getAge()));
			}
			return results;
		}
		
		@ServiceMethod
		public List<User> findUser(List<User> users) {
			List<User> results = new ArrayList<User>();
			for (User user : users) {
				results.add(new User("new-" + user.getUsername(), user.getAge()));
			}
			return results;
		}
		
	}
	
	public class User {
		
		private String username;
		
		private int age;

		public User(String username, int age) {
			this.username = username;
			this.age = age;
		}

		public String getUsername() {
			return username;
		}

		public int getAge() {
			return age;
		}

	}
	
}
