package org.mobilelite.android;

import com.google.inject.Inject;

import android.webkit.WebView;

public class WebPage {
	private @Inject EventDispatcherFactory factory;
	private PageEventDispatcher dispatcher;

	public WebPage(WebView view) {
		//dispatcher = factory.get(view);
		dispatcher = new PageEventDispatcher(view);
	}

	public void definePageBean(String name, Object bean) {
		dispatcher.definePageBean(name, bean);
	}
	
	public void loadUrl(String url) {
		dispatcher.loadUrl(url);
	}

}
