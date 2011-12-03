package org.mobilelite;

import java.io.IOException;
import java.io.InputStreamReader;

import org.mobilelite.android.WebPage;
import org.mobilelite.annotation.Service;
import org.mobilelite.annotation.ServiceMethod;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

public class MobileLiteActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*
         * When an activity is created, we need do below things:
         * 1. obtain the webview defined in res folder, e.g. the below R.id.webView is 
         *    defined in res/layout/main.xml
         * 2. create a MobileLite WebPage instance here
         * 3. add the service object as a page bean, then javascript can access the bean 
         *    directly with the given name as a javascript variable. 
         * 4. ask webpage to load the html
         */
        WebView webView = (WebView) findViewById(R.id.webView);
        WebPage webPage = new WebPage(webView);
        webPage.definePageBean("bean", new BusinessService()); 
        webPage.loadUrl("file:///android_asset/demo.html"); 
    }
    
    
    /**
     * This is a demo service bean to be called in webpage. Service bean can be used to 
     * handle many thing, e.g. read data from local storage or remote webservice, start
     * a new intent for another activity 
     *  
     * @author Tony Ni
     * @author Jim Jiang
     */
    @Service
    private class BusinessService {
    	
    	/**
    	 * There will be a name input on web page, when user click the "echo" button 
    	 * the javascript will pass the inputed name to this method and this method
    	 * will show a Toast message
    	 * 
    	 * The javascript code:
    	 * 	$("#helloBtn").click(function() {
    	 *  	bean.hello($("#name").val());
    	 *  });
    	 *  
    	 * @param name
    	 */
		@SuppressWarnings("unused")
		@ServiceMethod
    	public void hello(String name) {
    		Toast.makeText(MobileLiteActivity.this, "hello " + name + "!", 200).show();
    	}
    	
    	/**
    	 * There will be a button with id "infoBtn" on web page, when user click the button 
    	 * the javascript will call readConfig and display the returned configuration info
    	 * into result zone on the page.
    	 * 
    	 * The javascript code:
    	 * 	$("#infoBtn").click(function() {
    	 * 		bean.readConfig(function(config) {
    	 * 			$("#result").html(config.info);
    	 * 		});
    	 * 	});
    	 *  
    	 * @param name
    	 */
		@SuppressWarnings("unused")
		@ServiceMethod
    	public Config readConfig() {
			Config result = new Config("no config read");
			
			try {
				InputStreamReader is = new InputStreamReader(getAssets().open("config.properties"));
				char[] buf = new char[128];
				int flag = is.read(buf);
				StringBuffer content = new StringBuffer();
				while(flag != -1) {
					content.append(buf, 0, flag);
					flag = is.read(buf);
				}
				result.setInfo(content.toString());
			} catch (IOException e) {
				Log.e("MobileLite Demo", "Error open config file", e);
			}
			
			return result;
    	}
    	
    }
    
}