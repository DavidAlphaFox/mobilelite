(function() {

var MobileLiteEngine = function(mobileLite) {
};

MobileLiteEngine.prototype = {
	constructor: MobileLiteEngine,
	createLiteProxy: function(obj) {
		window[obj.name] = {
			name: obj.name,
			engine: this
//			__noSuchMethod__: function(methodName, args) {
//				this[methodName] = function(args) {
//					this.engine.invokeBeanAction(this.name, methodName, args);
//				}
//				this[methodName](args);
//			}
		};
		for (methodName in obj.methodNames) {
			window[obj.name][obj.methodNames[methodName]] = function(args) {
				this.engine.invokeBeanAction(this.name, obj.methodNames[methodName], args);
			}
		}
	},
	invokeBeanAction: function(bean, methodName, args) {
		alert( "bean:" + bean + ", method:" + methodName + ", args:" + args );
	}
};

var mobileLite = {
	engine: new MobileLiteEngine(),
	initBeans: function(beans) {
		for (bean in beans) {
			this.engine.createLiteProxy(beans[bean]);
		}
	}
};

if (typeof exports !== 'undefined') exports.mobileLite = mobileLite;
else window.mobileLite = mobileLite;

}) ();

$(document).ready(function() {
	_mobileLiteProxy_.onPageReady();
	//mobileLite.initBeans(["bean"]);
});