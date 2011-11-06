package org.mobilelite.android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mobilelite.annotation.Service;

import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class PageEventDispatcher {
	
	/** The web view which need to communicate with js. */
	protected WebView webView;
	
	protected Map<String, ServiceBean> beans = new HashMap<String, ServiceBean>();
	
	protected Gson gson = new Gson();
	
	public PageEventDispatcher(WebView webView) {
		this.webView = webView;
	}
	
	public void invokeBeanAction(String beanName, String methodName, String params, String callback) {
		Log.d("BeanAction", beanName);
		Log.d("param", params);
		Log.d("callback", callback == null? "null" : callback);
		//webView.loadUrl("javascript:liteEngine.dispatchEvent(" +event + "," + serializeData(data) + ")");
		
		ServiceBean bean = beans.get(beanName);
		try {
			Object result = bean.invoke(methodName, params);
			if(callback != null) {
				if(result != null) {
					result = gson.toJson(result);
				}
				webView.loadUrl("javascript:mobileLite.doCallback(" + result + "," + callback + ")");
			}
		} catch (SecurityException e) {
		} catch (JsonSyntaxException e) {
		} catch (IllegalArgumentException e) {
		}
	}

	public void definePageBean(String name, Object bean) {
		if(bean.getClass().isAnnotationPresent(Service.class)) {
			beans.put(name, new ServiceBean(name, bean));
		}
	}
	
	public void onPageReady() {
		//webView.loadUrl("javascript:liteEngine.initBeanProxy(" + gson.toJson(beans.keySet()) + ")");
		Log.d("onPageReady:", gson.toJson(getServiceBeanDefinitions()));
   		//Toast.makeText(webView.getContext(), "test", 200).show();
		webView.loadUrl("javascript:mobileLite.initBeans(" + gson.toJson(getServiceBeanDefinitions()) + ")");
	}
	
	private List<ServiceBeanDefinition> getServiceBeanDefinitions() {
		List<ServiceBeanDefinition> defs = new ArrayList<ServiceBeanDefinition>();
		for (ServiceBean serviceBean : beans.values()) {
			defs.add(serviceBean.getServiceBeanDefinition());
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
