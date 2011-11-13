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

MockMobileLiteProxy = function() {
};
MockMobileLiteProxy.prototype = {
	beans: {},
	invokeBeanAction: function(beanName, methodName, args, callback) {
		if(!(this.beans[beanName]))
			throw "Bean:" + beanName + "  doesn't exist";
		if(!(this.beans[beanName][methodName]) || !(this.beans[beanName][methodName] instanceof Function))
			throw "Method:" + methodName + "  doesn't exist";
		var argObj = {};
		try {
				argObj = eval(args);
		}
		catch(e) {
			throw "Args is not in valid format:" + e;
		}
		var result = this.beans[beanName][methodName].apply(this.beans[beanName], argObj);
		if(callback) {
			mobileLite.doCallback(result,  callback);
		}
	},
	definePageBean: function(beanName, bean) {
		this.beans[beanName] = bean;
	}
};

mobileLiteMock = {
	winAlert: window.alert,
	isGingerBreadOn: false,
	mobileLiteProxy: {},
	initTest: function() {
		window["_mobileLiteProxy_"] = new MockMobileLiteProxy ();
		if(this.isGingerBreadOn) 
			window.alert = this.winAlert;
		this.isGingerBreadOn = false;
	},
	initGingerBreadTest: function() {
		if(window["_mobileLiteProxy_"])
			delete window["_mobileLiteProxy_"];
		this.isGingerBreadOn = true;
		this.mobileLiteProxy = new MockMobileLiteProxy();
		var that = this;
		window.alert = function( arg ) {
			if(arg.indexOf("mobilelite:") === 0) {
				var jsonData = {};
				try {
					jsonData = eval('(' + arg.substring("mobilelite:".length) + ')');
				}
				catch(e) {
					throw "Args is not in valid format:" + e;
				}
				if(!jsonData["bean"])
					throw "beanName is not defined in call";
				if(!jsonData["method"])
					throw "beanMethod is not defined in call";
				
				that.mobileLiteProxy.invokeBeanAction(jsonData.bean, jsonData.method, jsonData.params, jsonData.callback);
			} 
			else
				that.winAlert.apply(this, arguments);
		}
	},
	definePageBean: function(beanName, bean) {
		if(this.isGingerBreadOn) 
			this.mobileLiteProxy.definePageBean(beanName, bean);
		else
			window["_mobileLiteProxy_"].definePageBean(beanName, bean);
	}
	
};

