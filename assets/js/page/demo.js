MOBILELITE.libs.readyBody = function() {
	$("#topNavPassengerInfo").click(function() {
		bean.show("你好Tony!", function() {alert('aaa')});
		bean.show("你好Tony!!");
		bean.queryContact("{id:'q1', name:'qJim'}", function(jsonStr) {
			alert(jsonStr);
//			var contact = eval("(" + unescape(jsonStr) + ")");
//			alert("query result: id-" + contact.id + ", name-" + contact.name);
		});
	});
}

