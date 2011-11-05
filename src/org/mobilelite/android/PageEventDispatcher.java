package org.mobilelite.android;

import java.util.HashMap;
import java.util.Map;

import org.mobilelite.MobileLiteActivity;

import com.google.gson.Gson;

import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class PageEventDispatcher {
	
	/** The web view which need to communicate with js. */
	protected WebView webView;
	
	protected Map<String, Object> beans = new HashMap<String, Object>();
	
	protected Gson gson = new Gson();
	
	public PageEventDispatcher(WebView webView) {
		this.webView = webView;
	}
	
	public void invokeBeanAction(String beanName, String method, Object param, String callback) {
		//webView.loadUrl("javascript:liteEngine.dispatchEvent(" +event + "," + serializeData(data) + ")");
	}


	public void definePageBean(String name, Object bean) {
		beans.put(name, bean);
	}
	
	public void onPageReady() {
		//webView.loadUrl("javascript:liteEngine.initBeanProxy(" + gson.toJson(beans.keySet()) + ")");
		Log.d("onPageReady:", gson.toJson(beans.keySet()));
   		//Toast.makeText(webView.getContext(), "test", 200).show();
		webView.loadUrl("javascript:mobileLite.initBeans(" + gson.toJson(beans.keySet()) + ")");
	}

	public void loadUrl(String url) {
		webView.getSettings().setJavaScriptEnabled(true);
		webView.addJavascriptInterface(this, "_mobileLiteProxy_");
		webView.setWebChromeClient(new MainWebChromeClient());
		webView.loadUrl(url);
	}
	
	final class MainWebChromeClient extends WebChromeClient {
		
		@Override
		public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
			Log.d("PageAlert", message);
			result.confirm();
			return true;
		}
		
	}

	
}
