$(document).ready(function(){
	module("Module MobileLiteEngine");
	test("Create Proxy Object", function() {
		delete(window["testProxyBean"]);
		mobileLiteMock.initTest();
		var bean = {
			name: "testProxyBean",
			methodNames: ["testMethod"]
		};
		var mobileLiteEngine = mobileLite.engine;
		mobileLiteEngine.createLiteProxy(bean);
		expect(4);
		ok( (window["testProxyBean"] ), "ProxyBean is correctly created as window variable with given variable name" );
		ok( (window["testProxyBean"] instanceof Object), "ProxyBean is correctly created in Object type" );
		ok( (window["testProxyBean"]["testMethod"]), "testMethod is correctly created as ProxyBean's child with given method name" );
		ok( (window["testProxyBean"]["testMethod"] instanceof Function), "testMethod is correctly created as a ProxyBean's function" );
		delete(window["testProxyBean"]);
	});
	
	test("Invoke Bean Action Method with simple arguments (null, or primitive arguments)", function() {
		var mobileLiteEngine = mobileLiteMock.initTest();
		expect(6);
		var testBean = {
			testCallNoArgs: function() {
				ok( true, "The method with no argument is correctly called" );
			},
			testCallWithOneSimpleArg: function(a) {
				ok( true, "The method with one simple argument is correctly called" );
				equal(a, 1, "the first argument is correctly passed");
			},
			testCallWithSimpleArgs: function(a, b) {
				ok( true, "The method with simple arguments is correctly called" );
				equal(a, "a", "the first argument is correctly passed");
				equal(b, "b", "the second argument is correctly passed");
			}
		};
		mobileLiteMock.definePageBean("test", testBean);
		var mobileLiteEngine = mobileLite.engine;
		mobileLiteEngine.invokeBeanAction("test", "testCallNoArgs", null, null);
		mobileLiteEngine.invokeBeanAction("test", "testCallWithOneSimpleArg", [1], null);
		mobileLiteEngine.invokeBeanAction("test", "testCallWithSimpleArgs", ["a", "b"], null);
	});
	
	test("Invoke Bean Action Method with complex arguments ", function() {
		var mobileLiteEngine = mobileLiteMock.initTest();
		expect(5);
		var obj1 = {
			a: "a",
			b: "b",
			c: 1,
			d: 1.2
		};
		var obj2 = {
			a: "a",
			b: obj1
		};
		var testBean = {
			testCallWithOneComplexArg: function(a) {
				ok( true, "The method with one complex argument is correctly called" );
				deepEqual(a, obj1, "the first argument is correctly passed");
			},
			testCallWithComplexArgs: function(a, b) {
				ok( true, "The method with complex arguments is correctly called" );
				deepEqual(a, obj1, "the first complex argument is correctly passed");
				deepEqual(b, obj2, "the second complex argument is correctly passed");
			}
		};
		mobileLiteMock.definePageBean("test", testBean);
		var mobileLiteEngine = mobileLite.engine;
		mobileLiteEngine.invokeBeanAction("test", "testCallWithOneComplexArg", [obj1], null);
		mobileLiteEngine.invokeBeanAction("test", "testCallWithComplexArgs", [obj1, obj2], null);
	});
	
	test("Invoke Bean Action Method with validating bean property object", function() {
		var mobileLiteEngine = mobileLiteMock.initTest();
		expect(2);
		var obj1 = {
			a: "a",
			b: "b",
			c: 1,
			d: 1.2
		};
		var obj2 = {
			a: "a",
			b: obj1
		};
		var testBean = {
			a: obj1,
			b: obj2,
			testCallWithComplexArgs: function(a, b) {
				deepEqual(a, this.a, "the first complex argument is correctly passed");
				deepEqual(b, this.b, "the second complex argument is correctly passed");
			}
		};
		mobileLiteMock.definePageBean("test", testBean);
		var mobileLiteEngine = mobileLite.engine;
		mobileLiteEngine.invokeBeanAction("test", "testCallWithComplexArgs", [obj1, obj2], null);
	});
	
	module("Module MobileLite");
	test("initBeans", function() {
		var beans = [{
						name: "testBean1",
						methodNames: ["testMethod1"]
					 },
					 {
						name: "testBean2",
						methodNames: ["testMethod1", "testMethod2"]
					 },
					 {
						name: "testBean3",
						methodNames: ["testMethod1", "testMethod2", "testMethod3"]
					 }];
		mobileLite.initBeans(beans);
		ok( (window["testBean1"] ), "testBean1 is correctly created as window variable with given variable name" );
		ok( (window["testBean1"] instanceof Object), "testBean1 is correctly created in Object type" );
		ok( (window["testBean1"]["testMethod1"]), "testMethod1 is correctly created as testBean1's child with given method name" );
		ok( (window["testBean1"]["testMethod1"] instanceof Function), "testMethod1 is correctly created as a testBean1's function" );
		ok( (window["testBean2"] ), "testBean2 is correctly created as window variable with given variable name" );
		ok( (window["testBean2"] instanceof Object), "testBean2 is correctly created in Object type" );
		ok( (window["testBean2"]["testMethod1"]), "testMethod1 is correctly created as testBean2's child with given method name" );
		ok( (window["testBean2"]["testMethod1"] instanceof Function), "testMethod1 is correctly created as a testBean2's function" );
		ok( (window["testBean2"]["testMethod2"]), "testMethod2 is correctly created as testBean2's child with given method name" );
		ok( (window["testBean2"]["testMethod2"] instanceof Function), "testMethod2 is correctly created as a testBean2's function" );
		ok( (window["testBean3"] ), "testBean3 is correctly created as window variable with given variable name" );
		ok( (window["testBean3"] instanceof Object), "testBean3 is correctly created in Object type" );
		ok( (window["testBean3"]["testMethod1"]), "testMethod1 is correctly created as testBean3's child with given method name" );
		ok( (window["testBean3"]["testMethod1"] instanceof Function), "testMethod1 is correctly created as a testBean3's function" );
		ok( (window["testBean3"]["testMethod2"]), "testMethod2 is correctly created as testBean3's child with given method name" );
		ok( (window["testBean3"]["testMethod2"] instanceof Function), "testMethod2 is correctly created as a testBean3's function" );
		ok( (window["testBean3"]["testMethod3"]), "testMethod3 is correctly created as testBean3's child with given method name" );
		ok( (window["testBean3"]["testMethod3"] instanceof Function), "testMethod3 is correctly created as a testBean3's function" );
		delete(window["testBean1"]);
		delete(window["testBean2"]);
		delete(window["testBean3"]);
	});
	
	
	test("Invoke Bean Action Method with simple arguments (null, or primitive arguments)", function() {
		delete(window["testProxyBean"]);
		var mobileLiteEngine = mobileLiteMock.initTest();
		expect(6);
		var testBean = {
			testCallNoArgs: function() {
				ok( true, "The method with no argument is correctly called" );
			},
			testCallWithOneSimpleArg: function(a) {
				ok( true, "The method with one simple argument is correctly called" );
				equal(a, 1, "the first argument is correctly passed");
			},
			testCallWithSimpleArgs: function(a, b) {
				ok( true, "The method with simple arguments is correctly called" );
				equal(a, "a", "the first argument is correctly passed");
				equal(b, "b", "the second argument is correctly passed");
			}
		};
		mobileLiteMock.definePageBean("testProxyBean", testBean);
		mobileLite.initBeans( [{
								name: "testProxyBean",
								methodNames: ["testCallNoArgs", "testCallWithOneSimpleArg", "testCallWithSimpleArgs"]
							 }]);
		testProxyBean.testCallNoArgs();
		testProxyBean.testCallWithOneSimpleArg(1);
		testProxyBean.testCallWithSimpleArgs("a", "b");
	});
	
	test("Invoke Bean Action Method with complex arguments ", function() {
		delete(window["testProxyBean"]);
		var mobileLiteEngine = mobileLiteMock.initTest();
		expect(5);
		var obj1 = {
			a: "a",
			b: "b",
			c: 1,
			d: 1.2
		};
		var obj2 = {
			a: "a",
			b: obj1
		};
		var testBean = {
			testCallWithOneComplexArg: function(a) {
				ok( true, "The method with one complex argument is correctly called" );
				deepEqual(a, obj1, "the first argument is correctly passed");
			},
			testCallWithComplexArgs: function(a, b) {
				ok( true, "The method with complex arguments is correctly called" );
				deepEqual(a, obj1, "the first complex argument is correctly passed");
				deepEqual(b, obj2, "the second complex argument is correctly passed");
			}
		};
		mobileLiteMock.definePageBean("testProxyBean", testBean);
		mobileLite.initBeans( [{
								name: "testProxyBean",
								methodNames: ["testCallWithOneComplexArg", "testCallWithComplexArgs"]
							 }]);
		testProxyBean.testCallWithOneComplexArg(obj1);
		testProxyBean.testCallWithComplexArgs(obj1, obj2);
	});

	
	test("Invoke Bean Action Method with callback ", function() {
		delete(window["testProxyBean"]);
		var mobileLiteEngine = mobileLiteMock.initTest();
		expect(21);
		var obj1 = {
			a: "a",
			b: "b",
			c: 1,
			d: 1.2
		};
		var obj2 = {
			a: "a",
			b: obj1
		};
		var testBean = {
			testCallWithOneComplexArg: function(a) {
				ok( true, "The method with one complex argument is correctly called" );
				deepEqual(a, obj1, "the first argument is correctly passed");
			},
			testCallWithComplexArgs: function(a, b) {
				ok( true, "The method with complex arguments is correctly called" );
				deepEqual(a, obj1, "the first complex argument is correctly passed");
				deepEqual(b, obj2, "the second complex argument is correctly passed");
				return a;
			}
		};
		mobileLiteMock.definePageBean("testProxyBean", testBean);
		mobileLite.initBeans( [{
								name: "testProxyBean",
								methodNames: ["testCallWithOneComplexArg", "testCallWithComplexArgs"]
							 }]);
		testProxyBean.testCallWithOneComplexArg(obj1, function() {
			ok( true, "The callback method is correctly called1" );
		} );
		testProxyBean.testCallWithOneComplexArg(obj1, function() {
			ok( true, "The callback method is correctly called2" );
		} );
		testProxyBean.testCallWithComplexArgs(obj1, obj2,  function(a) {
			ok( true, "The callback method with arguments is correctly called1" );
			deepEqual(a, obj1, "the first complex argument is correctly passed1");
		} );
		testProxyBean.testCallWithComplexArgs(obj1, obj2,  function(a) {
			ok( true, "The callback method with arguments is correctly called2" );
			deepEqual(a, obj1, "the first complex argument is correctly passed2");
		} );
		var cbObj = {
			a: obj1,
			callback: function(a) {
				ok( true, "The callback method within a object is correctly called" );
				deepEqual(a, this.a, "the first complex argument is correctly passed");
			}
		};
		testProxyBean.testCallWithComplexArgs(obj1, obj2, function(a) {
			cbObj.callback(a);
		} );
	});

	module("Module MobileLite for GingerBread");
	
	test("Invoke Bean Action Method with simple arguments (null, or primitive arguments)", function() {
		delete(window["testProxyBean"]);
		var mobileLiteEngine = mobileLiteMock.initGingerBreadTest();
		expect(6);
		var testBean = {
			testCallNoArgs: function() {
				ok( true, "The method with no argument is correctly called" );
			},
			testCallWithOneSimpleArg: function(a) {
				ok( true, "The method with one simple argument is correctly called" );
				equal(a, 1, "the first argument is correctly passed");
			},
			testCallWithSimpleArgs: function(a, b) {
				ok( true, "The method with simple arguments is correctly called" );
				equal(a, "a", "the first argument is correctly passed");
				equal(b, "b", "the second argument is correctly passed");
			}
		};
		mobileLiteMock.definePageBean("testProxyBean", testBean);
		mobileLite.initBeans( [{
								name: "testProxyBean",
								methodNames: ["testCallNoArgs", "testCallWithOneSimpleArg", "testCallWithSimpleArgs"]
							 }]);
		testProxyBean.testCallNoArgs();
		testProxyBean.testCallWithOneSimpleArg(1);
		testProxyBean.testCallWithSimpleArgs("a", "b");
	});
	
	test("Invoke Bean Action Method with complex arguments ", function() {
		delete(window["testProxyBean"]);
		var mobileLiteEngine = mobileLiteMock.initGingerBreadTest();
		expect(5);
		var obj1 = {
			a: "a",
			b: "b",
			c: 1,
			d: 1.2
		};
		var obj2 = {
			a: "a",
			b: obj1
		};
		var testBean = {
			testCallWithOneComplexArg: function(a) {
				ok( true, "The method with one complex argument is correctly called" );
				deepEqual(a, obj1, "the first argument is correctly passed");
			},
			testCallWithComplexArgs: function(a, b) {
				ok( true, "The method with complex arguments is correctly called" );
				deepEqual(a, obj1, "the first complex argument is correctly passed");
				deepEqual(b, obj2, "the second complex argument is correctly passed");
			}
		};
		mobileLiteMock.definePageBean("testProxyBean", testBean);
		mobileLite.initBeans( [{
								name: "testProxyBean",
								methodNames: ["testCallWithOneComplexArg", "testCallWithComplexArgs"]
							 }]);
		testProxyBean.testCallWithOneComplexArg(obj1);
		testProxyBean.testCallWithComplexArgs(obj1, obj2);
	});
	
	test("Invoke Bean Action Method with callback ", function() {
		delete(window["testProxyBean"]);
		var mobileLiteEngine = mobileLiteMock.initGingerBreadTest();
		expect(21);
		var obj1 = {
			a: "a",
			b: "b",
			c: 1,
			d: 1.2
		};
		var obj2 = {
			a: "a",
			b: obj1
		};
		var testBean = {
			testCallWithOneComplexArg: function(a) {
				ok( true, "The method with one complex argument is correctly called" );
				deepEqual(a, obj1, "the first argument is correctly passed");
			},
			testCallWithComplexArgs: function(a, b) {
				ok( true, "The method with complex arguments is correctly called" );
				deepEqual(a, obj1, "the first complex argument is correctly passed");
				deepEqual(b, obj2, "the second complex argument is correctly passed");
				return a;
			}
		};
		mobileLiteMock.definePageBean("testProxyBean", testBean);
		mobileLite.initBeans( [{
								name: "testProxyBean",
								methodNames: ["testCallWithOneComplexArg", "testCallWithComplexArgs"]
							 }]);
		testProxyBean.testCallWithOneComplexArg(obj1, function() {
			ok( true, "The callback method is correctly called1" );
		} );
		testProxyBean.testCallWithOneComplexArg(obj1, function() {
			ok( true, "The callback method is correctly called2" );
		} );
		testProxyBean.testCallWithComplexArgs(obj1, obj2,  function(a) {
			ok( true, "The callback method with arguments is correctly called1" );
			deepEqual(a, obj1, "the first complex argument is correctly passed1");
		} );
		testProxyBean.testCallWithComplexArgs(obj1, obj2,  function(a) {
			ok( true, "The callback method with arguments is correctly called2" );
			deepEqual(a, obj1, "the first complex argument is correctly passed2");
		} );
		var cbObj = {
			a: obj1,
			callback: function(a) {
				ok( true, "The callback method within a object is correctly called" );
				deepEqual(a, this.a, "the first complex argument is correctly passed");
			}
		};
		testProxyBean.testCallWithComplexArgs(obj1, obj2, function(a) {
			cbObj.callback(a);
		} );
	});
});