package org.mobilelite.android;

import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class LiteWebViewClient extends WebViewClient {
	
	protected PageEventDispatcher dispatcher;

	public LiteWebViewClient(PageEventDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		
		Log.d("onPageFinished", "onPageFinished");
		view.loadUrl("javascript:mobileLite.initBeans(" + dispatcher.getServiceBeanDefinitionJson() + ")");
	}

}
