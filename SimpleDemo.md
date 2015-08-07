#This a simple demo to show how to use the mobilelite in a android project

# Introduction #
This demo is simple android application, it has a web page with two table like below:
### Requirement: ###
When click the "Echo" button, display user a toast message with "hello ${input}"
<img src='http://mobilelite.googlecode.com/svn/trunk/mobilelite-demo/assets/screen/hello_word_html.png'>

<h3>Requirement:</h3>
When click the "Config Info" button, display the content read from assets/config.properties.<br>
<img src='http://mobilelite.googlecode.com/svn/trunk/mobilelite-demo/assets/screen/config_info_html.png'>

<h1><a href='http://mobilelite.googlecode.com/svn/trunk/mobilelite-demo/'>Application</a> Construction:</h1>
<ul>
<li><a href='http://mobilelite.googlecode.com/svn/trunk/mobilelite-demo/AndroidManifest.xml'>AndroidManifest.xml</a></li>
<li><a href='http://mobilelite.googlecode.com/svn/trunk/mobilelite-demo/res/layout/main.xml'>res/layout/main.xml</a></li>
<li><a href='http://mobilelite.googlecode.com/svn/trunk/mobilelite-demo/src/org/mobilelite/MobileLiteActivity.java'>MobileLiteActivity.java</a></li>
<li><a href='http://mobilelite.googlecode.com/svn/trunk/mobilelite-demo/assets/config.properties'>assets/config.properties</a></li>
<li><a href='http://mobilelite.googlecode.com/svn/trunk/mobilelite-demo/assets/demo.html'>assets/demo.html</a></li>
<li><a href='http://mobilelite.googlecode.com/files/mobilelite-core-0.0.4.jar'>mobilelite.jar</a> (need to be included in your classpath)</li>
<li><a href='http://mobilelite.googlecode.com/files/mobilelite.js'>mobilelite.js</a> (need to be included in your html "head")</li>
<br>

<h3><a href='http://mobilelite.googlecode.com/svn/trunk/mobilelite-demo/src/org/mobilelite/MobileLiteActivity.java'>MobileLiteActivity</a>:</h3>
<pre><code>public class MobileLiteActivity extends Activity {<br>
	<br>
    /** Called when the activity is first created. */<br>
    @Override<br>
    public void onCreate(Bundle savedInstanceState) {<br>
        super.onCreate(savedInstanceState);<br>
        setContentView(R.layout.main);<br>
        <br>
        /*<br>
         * When an activity is created, we need do below things:<br>
         * 1. obtain the webview defined in res folder, e.g. the below R.id.webView is <br>
         *    defined in res/layout/main.xml<br>
         * 2. create a MobileLite WebPage instance here<br>
         * 3. add the service object as a page bean, then javascript can access the bean <br>
         *    directly with the given name as a javascript variable. <br>
         * 4. ask webpage to load the html<br>
         */<br>
        WebView webView = (WebView) findViewById(R.id.webView);<br>
        WebPage webPage = new WebPage(webView);<br>
        webPage.definePageBean("bean", new BusinessService()); <br>
        webPage.loadUrl("file:///android_asset/demo.html"); <br>
    }<br>
    <br>
    <br>
    /**<br>
     * This is a demo service bean to be called in webpage. Service bean can be used to <br>
     * handle many thing, e.g. read data from local storage or remote webservice, start<br>
     * a new intent for another activity <br>
     * <br>
     * &lt;b&gt;Please note the service bean must be annotated with @Service and method to be<br>
     * exposed in webview need to be annotated with @ServiceMethod&lt;/b&gt; <br>
     *  <br>
     * @author Tony Ni<br>
     * @author Jim Jiang<br>
     */<br>
    @Service<br>
    private class BusinessService {<br>
    	<br>
    	/**<br>
    	 * There will be a name input on web page, when user click the "echo" button <br>
    	 * the javascript will pass the inputed name to this method and this method<br>
    	 * will show a Toast message<br>
    	 * <br>
    	 * With annotation @ServiceMethod, this method is declared to be used in webview<br>
    	 * <br>
    	 * Javascript consume @code <br>
    	 * 	$("#helloBtn").click(function() {<br>
    	 *  	bean.hello($("#name").val());<br>
    	 *  });<br>
    	 *  <br>
    	 * @param name<br>
    	 */<br>
		@SuppressWarnings("unused")<br>
		@ServiceMethod<br>
    	public void hello(String name) {<br>
    		Toast.makeText(MobileLiteActivity.this, "hello " + name + "!", 200).show();<br>
    	}<br>
    	<br>
    	/**<br>
    	 * There will be a button with id "infoBtn" on web page, when user click the button <br>
    	 * the javascript will call readConfig and display the returned configuration info<br>
    	 * into result zone on the page.<br>
    	 * <br>
    	 * With annotation @ServiceMethod, this method is declared to be used in webview<br>
    	 * <br>
    	 * Javascript consume @code <br>
    	 * 	$("#infoBtn").click(function() {<br>
    	 * 		bean.readConfig(function(config) {<br>
    	 * 			$("#result").html(config.info);<br>
    	 * 		});<br>
    	 * 	});<br>
    	 *  <br>
    	 * @param name<br>
    	 */<br>
		@SuppressWarnings("unused")<br>
		@ServiceMethod<br>
    	public Config readConfig() {<br>
		Config result = new Config("no config read");<br>
			<br>
		try {<br>
			InputStreamReader is = new InputStreamReader(getAssets().open("config.properties"));<br>
			char[] buf = new char[128];<br>
			int flag = is.read(buf);<br>
			StringBuffer content = new StringBuffer();<br>
			while(flag != -1) {<br>
				content.append(buf, 0, flag);<br>
				flag = is.read(buf);<br>
			}<br>
			result.setInfo(content.toString());<br>
		} catch (IOException e) {<br>
			Log.e("MobileLite Demo", "Error open config file", e);<br>
		}<br>
			<br>
		return result;<br>
    	}<br>
    	<br>
    }<br>
    <br>
}<br>
</code></pre>

<a href='http://mobilelite.googlecode.com/svn/trunk/mobilelite-demo/'>entire code</a> <a href='http://mobilelite.googlecode.com/files/mobileLite-demo.apk'>installation package</a>