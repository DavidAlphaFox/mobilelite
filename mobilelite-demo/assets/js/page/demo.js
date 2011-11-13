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

MOBILELITE.libs.readyBody = function() {
	$("#button").click(function() {
		bean.show("你好Tony!", function() {alert('aaa')});
		bean.show("你好Tony!!");
		bean.queryContact({id:'q1', name:'qJim'}, function(contact) {
			alert(contact);
			alert("query result: id-" + contact.id + ", name-" + contact.name);
			$("#result").html(contact.description);
		});
	});
}