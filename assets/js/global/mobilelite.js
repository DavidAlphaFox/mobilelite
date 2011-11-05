(function() {

var MobileLiteEngine = function(mobileLite) {
};

MobileLiteEngine.prototype = {
	constructor: MobileLiteEngine,
	guid: 1, 
	createLiteProxy: function(obj) {
		window[obj.name] = {
			name: obj.name,
			engine: this
		};
		for (methodName in obj.methodNames) {
			window[obj.name][obj.methodNames[methodName]] = function() {
				var args = Array.prototype.slice.call(arguments);
				var callback = null;
				if(args.length >0 && args[args.length - 1] instanceof Function) {
					callback = args[args.length - 1];
					args = args.slice(0, args.length - 1);
				}
				
				if(callback && !(callback.guid)) {
					callback.guid = this.engine.guid++;
					mobileLite.callback[callback.guid] = callback;
				}
				
				this.engine.invokeBeanAction(this.name, obj.methodNames[methodName], args, callback);
			}
		}
	},
	invokeBeanAction: function(bean, methodName, args, callback) {
		//alert( "bean:" + bean + ", method:" + methodName + ", args:" + args );
		if(callback)
			callback = callback.toString();
		_mobileLiteProxy_.invokeBeanAction(bean, methodName, JSON.stringify(args), callback);
		//alert( "bean:" + bean + ", method:" + methodName + ", args:" + args + ", callback:" + callback );
	}
};

var mobileLite = {
	engine: new MobileLiteEngine(),
	initBeans: function(beans) {
		for (bean in beans) {
			this.engine.createLiteProxy(beans[bean]);
		}
	},
	callback: {}
};

if (typeof exports !== 'undefined') exports.mobileLite = mobileLite;
else window.mobileLite = mobileLite;

}) ();

$(document).ready(function() {
	_mobileLiteProxy_.onPageReady();
	//mobileLite.initBeans(["bean"]);
});