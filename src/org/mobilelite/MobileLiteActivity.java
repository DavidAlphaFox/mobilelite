package org.mobilelite;

import org.mobilelite.android.WebPage;
import org.mobilelite.annotation.Service;
import org.mobilelite.annotation.ServiceMethod;

import android.app.Activity;
import android.os.Bundle;
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
    	@ServiceMethod
    	void show(String text) {
    		Toast.makeText(MobileLiteActivity.this, text, 200).show();
    	}
    }
    
}