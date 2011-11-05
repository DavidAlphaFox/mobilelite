package org.mobilelite.android;

import android.webkit.WebView;

import com.google.inject.Singleton;

@Singleton
public class EventDispatcherFactory {

	public PageEventDispatcher get(WebView view) {
		PageEventDispatcher dispatcher = new PageEventDispatcher(view);
		return dispatcher;
	}

}
