package org.mobilelite.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.gson.Gson;

public class PageEventDispatcher {
	
	/** The web view which need to communicate with js. */
	protected WebView webView;
	
	protected Map<String, Object> beans = new HashMap<String, Object>();
	
	protected Gson gson = new Gson();
	
	public PageEventDispatcher(WebView webView) {
		this.webView = webView;
	}
	
	public void invokeBeanAction(String beanName, String method, String param, String callback) {
		Log.d("BeanAction", beanName);
		Log.d("param", param);
		Log.d("callback", callback == null? "null": callback);
		//webView.loadUrl("javascript:liteEngine.dispatchEvent(" +event + "," + serializeData(data) + ")");
	}


	public void definePageBean(String name, Object bean) {
		beans.put(name, bean);
	}
	
	public void onPageReady() {
		//webView.loadUrl("javascript:liteEngine.initBeanProxy(" + gson.toJson(beans.keySet()) + ")");
		Log.d("onPageReady:", gson.toJson(getBeanDefinitions()));
   		//Toast.makeText(webView.getContext(), "test", 200).show();
		webView.loadUrl("javascript:mobileLite.initBeans(" + gson.toJson(getBeanDefinitions()) + ")");
	}
	
	private List<BeanDefinition> getBeanDefinitions() {
		List<BeanDefinition> defs = new ArrayList<BeanDefinition>();
		for (Entry<String, Object> entry : beans.entrySet()) {
			BeanDefinition def = new BeanDefinition(entry.getKey(), entry.getValue());
			defs.add(def);
		}
		return defs;
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
