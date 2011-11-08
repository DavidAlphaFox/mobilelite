(function() {

var MobileLiteEngine = function(mobileLite) {
};

MobileLiteEngine.prototype = {
	constructor: MobileLiteEngine,
	createLiteProxy: function(obj) {
		window[obj.name] = {
			name: obj.name,
			engine: this
		};
		for (methodName in obj.methodNames) {
			methodName = obj.methodNames[methodName];
			window[obj.name][methodName] = function() {
				var args = Array.prototype.slice.call(arguments);
				var callback = null;
				if(args.length >0 && args[args.length - 1] instanceof Function) {
					callback = args[args.length - 1];
					args = args.slice(0, args.length - 1);
				}
				
				this.engine.invokeBeanAction(this.name, arguments.callee.methodName, args, callback);
			}
			window[obj.name][methodName].methodName = methodName;
		}
	},
	invokeBeanAction: function(bean, methodName, args, callback) {
		if(callback)
			callback = callback.toString();
		_mobileLiteProxy_.invokeBeanAction(bean, methodName, JSON.stringify(args), callback);
		//alert( "bean:" + bean + ", method:" + methodName + ", args:" + args + ", callback:" + callback );
	}
};

var mobileLite = {
	engine: new MobileLiteEngine(),
	initBeans: function(beans) {
		alert("initBeans:start");
		if(!window["_mobileLiteProxy_"]) {
			this.engine.invokeBeanAction = function (beanName, methodName, args, callback) {
				if(callback)
					callback = callback.toString();
				_mobileLiteProxy_.invokeBeanAction(beanName, methodName, args, callback);
			};
			window._mobileLiteProxy_ = {
				invokeBeanAction: function (beanName, methodName, args, callback) {
					var obj = {
						bean: beanName,
						method: methodName,
						params: args,
						callback: callback
					};
					
					//var encodeRequest = JSON.stringify(obj).replace(/\\/g, "%5c");
					var encodeRequest = JSON.stringify(obj);
					alert(encodeRequest);
					
					//window.location = "mobilelite:" + encodeRequest;
					alert("mobilelite:" + encodeRequest);
				}
			};
		}
		for (bean in beans) {
			this.engine.createLiteProxy(beans[bean]);
		}
		alert("initBeans:end");
	},
	doCallback: function(result, callback) {
		var cbFun = eval(callback);
		cbFun(result);
	}
};

if (typeof exports !== 'undefined') exports.mobileLite = mobileLite;
else window.mobileLite = mobileLite;

//_mobileLiteProxy_.onPageReady();
//mobileLite.initBeans([{"bean":{}, "methodNames":["queryContact", "show"], "name":"bean"}]);
}) ();

