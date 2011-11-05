package org.mobilelite.android;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	protected Map<String, Object> beans = new HashMap<String, Object>();
	
	protected Gson gson = new Gson();
	
	public PageEventDispatcher(WebView webView) {
		this.webView = webView;
	}
	
	public void invokeBeanAction(String beanName, String methodName, String params, String callback) {
		Log.d("BeanAction", beanName);
		Log.d("param", params);
		Log.d("callback", callback == null? "null" : callback);
		//webView.loadUrl("javascript:liteEngine.dispatchEvent(" +event + "," + serializeData(data) + ")");
		
		Object bean = beans.get(beanName);
		try {
			Method beanMethod = bean.getClass().getDeclaredMethod(methodName, String.class);
			
			//Type collectionType = new TypeToken<Collection<String>>(){}.getType();
			Object result = beanMethod.invoke(bean, gson.fromJson(params,  String[].class));
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void definePageBean(String name, Object bean) {
		beans.put(name, bean);
	}
	
	public void onPageReady() {
		//webView.loadUrl("javascript:liteEngine.initBeanProxy(" + gson.toJson(beans.keySet()) + ")");
		Log.d("onPageReady:", gson.toJson(getServiceBeanDefinitions()));
   		//Toast.makeText(webView.getContext(), "test", 200).show();
		webView.loadUrl("javascript:mobileLite.initBeans(" + gson.toJson(getServiceBeanDefinitions()) + ")");
	}
	
	private List<ServiceBeanDefinition> getServiceBeanDefinitions() {
		List<ServiceBeanDefinition> defs = new ArrayList<ServiceBeanDefinition>();
		for (Entry<String, Object> entry : beans.entrySet()) {
			if (entry.getValue().getClass().isAnnotationPresent(Service.class)) {
				defs.add(ServiceBeanDefinition.newInstance(entry.getKey(), entry.getValue()));
			}
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
