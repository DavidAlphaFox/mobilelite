(function(){

var MobileLiteEngine = function(mobileLite) {
	};

MobileLiteEngine.prototype = {
	constructor: MobileLiteEngine,
	createLiteProxy: function (obj) {
		window[obj] = {
			engine: this,
			__noSuchMethod__: function(id, args) {
				this[id] = function(args) {
					this.engine.invokeBeanAction(id, args);
				}
				this[id](args);
			}
		};
	},
	invokeBeanAction: function(id, args) {
		alert( "id:" + id + ", args:" + args );
	}
};

var mobileLite = {
		engine: new MobileLiteEngine(),
		initBeans: function(beans) {
			for ( bean in beans) {
				this.engine.createLiteProxy(beans[bean]);
			}
		}
	};

if (typeof exports !== 'undefined') exports.mobileLite = mobileLite;
else window.mobileLite = mobileLite;

})();

$(document).ready(function() {
	//_mobileLiteProxy_.onPageReady();
	mobileLite.initBeans(["bean"]);
});

