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

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mobilelite.annotation.Service;
import org.mobilelite.annotation.ServiceMethod;

import android.webkit.WebView;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class TestBeanActionDispatcher {

	BeanActionDispatcher dispatcher;
	
	WebView mockWebView;
	
	@Before
	public void setUp() {
		mockWebView = createMock(WebView.class);
		dispatcher = new BeanActionDispatcher(mockWebView);
	}
	
	@Test
	public void testDefinePageBeanByNonServiceBean() {
		dispatcher.definePageBean("bean", new Object());
		assertNull(dispatcher.beans.get("bean"));
	}
	
	@Test
	public void testDefinePageBeanByServiceBean() {
		dispatcher.definePageBean("bean", new UserService());
		assertNotNull(dispatcher.beans.get("bean"));
	}
	
	@Test(expected=RuntimeException.class)
	public void testInvokeBeanActionBeanNotExists() {
		replay(mockWebView);
		dispatcher._invokeBeanAction("bean", "findUser", null, "jsShow");
		verify(mockWebView);
	}
	
	@Test
	public void testInvokeBeanActionWithoutCallback() {
		dispatcher.definePageBean("userService", new UserService());
		JsonParser jsonParser = new JsonParser();
		JsonElement je = jsonParser.parse("jim");
		
		replay(mockWebView);
		dispatcher._invokeBeanAction("userService", "findUser", je, null);
		verify(mockWebView);
	}
	
	public void testInvokeBeanActionWithCallback() {
		dispatcher.definePageBean("userService", new UserService());
		JsonParser jsonParser = new JsonParser();
		JsonElement je = jsonParser.parse("jim");
		
		mockWebView.loadUrl("javascript:mobileLite.doCallback(\"jim\", showName(name) {alert(name);})");
		
		replay(mockWebView);
		dispatcher._invokeBeanAction("userService", "findUser", je, "showName(name) {alert(name);}");
		verify(mockWebView);
	}
	
	@Service
	public class UserService {
		
		@ServiceMethod
		public String findUser(String id) {
			return id;
		}
		
	}
	
}
