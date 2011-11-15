package org.mobilelite;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.mobilelite.android.WebPage;
import org.mobilelite.annotation.Service;
import org.mobilelite.annotation.ServiceMethod;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

public class MobileLiteActivity extends Activity {
	
	private WebView webView;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        webView = (WebView) findViewById(R.id.webView);
        WebPage webPage = new WebPage(webView);
        webPage.definePageBean("bean", new BusinessService());
        webPage.loadUrl("file:///android_asset/demo.html");
        //webPage.loadUrl("file:///android_asset/a.html");
    }
    
    @Service
    private class BusinessService {
    	
		@SuppressWarnings("unused")
		@ServiceMethod
    	public void hello(String name) {
    		Toast.makeText(MobileLiteActivity.this, "hello " + name + "!", 200).show();
    	}
    	
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