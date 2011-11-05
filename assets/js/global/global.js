var MOBILELITE = {
	ready: function(){
		for(func in this.ints){
			if($(func)[0]){
				this.libs[this.ints[func]]();
			}
		};
	},
	ints: {
		'body': 'readyBody'
	},
	libs: {
	}
};

String.prototype.unescapeHtml = function () {
    var temp = document.createElement("div");
    temp.innerHTML = this;
    var result = temp.childNodes[0].nodeValue;
    temp.removeChild(temp.firstChild);
    return result;
} 

$(document).ready(function() {
	// on top navigation item click
	MOBILELITE.ready();
	
});