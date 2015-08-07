# Mission #

|"Provide a reliable, fast, lightwieght way to maxium leverage HTML/CSS/JAVASCRIPT technology and make the work of developing mobile application easier. Let the webview do the UI job and mobilelite smooth the work of call platform service with javascript in its natural way. "|
|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|

<br>
<h1>Introduction</h1>

Today in the world, there are billions mobile device running. And mobile application becomes very important channel that people aquire information from . 53% of smart mobile devices are based on Android, 28% are based on iOS. The android platform is a free platform for developer, so it's a good choice if you want to dive into mobile application development.<br>
<br>
Mobile application is cool! People can use it every where. You can invent an application that help people draw their innovative idea or connect to each other. But you may concern that mobile development is kind of traditional Swing development, the miserable memory from huge UI work of developing Swing in the past will prevent you from the invention. If you have experience of web development and enjoy the fun of html/css/javascript, then a good message for you that your past experience of web can be totally utilized in the mobile application development. Both Android and iOS provide "web view" which let you to draw the user interface in HTML/CSS and capture user action with javascript. This is important for a project and developer, especially when you are developing a application for a client. The user interface is important for client because they don't understand how difficult to draw a button in their idea, they just want a beatiful button with cool click animation response. With HTML/CSS (especially we now have HTML5/CSS3 supported in web view), you can easily do this.<br>
<br>
Is there still something block us to do our developement? Yes, if you want to call something of backend service like make a webservice call or make phone dial, how can you implement that? Is there a way javascript can call the backend service? Fortunately, the android and iOS both support to expose backend service object as javascript object in "web view". The problem is you can only invoke service object's method as void method in android "web view". To get the result you must make some tricky code. That will be very uncomfortable for the developer like us. We should pay our attention to the business and the logic client really care for. Why should it disturb our thinking? It's absolutely not considered in their API design. And another serious problem is that in android 2.3.x release the application will crashed when you call method on javascript exposed backend service object. We definitely don't want people fail to use our application because they are using a 2.3.x device.<br>
<br>
To solve these problems and make our development more efficiently and get more focused on client's needs, we develop the mobilelite framework. It's very light, a 21K jar and a 4K js with detail unit testing and fully integration testing in all version of android release. It only costs 2ms to make a call in testing, but will make you feel very convenient in programming. You can find how it works in the <a href='http://code.google.com/p/mobilelite/wiki/SimpleDemo'>demo code</a>.<br>
<br>
<h1>Quick Guide</h1>
To use mobilelite framework, you need add mobilelite-core.jar into your project's classpath and include mobilelite.js in the your html pages.<br>
You can find more detail in <a href='http://code.google.com/p/mobilelite/wiki/SimpleDemo'>demo code</a>.<br>
<br>
<h1>Latest Release</h1>
<b><a href='http://mobilelite.googlecode.com/files/mobilelite-core-0.0.4.jar'>0.0.4-jar</a></b> <a href='http://mobilelite.googlecode.com/svn/trunk/mobilelite-js/scripts/mobilelite.js'>mobilelite.js</a>
<b><a href='http://mobilelite.googlecode.com/svn/trunk/mobilelite-demo/assets/demo.html'>demo page</a></b>

<h1>What's new in 0.0.4</h1>
<ul>
<li>fix the bug crashed application when pass service method result back to webview   </li>
</ul>