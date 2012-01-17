package org.mobilelite.android;

import android.webkit.WebView;

import com.google.gson.JsonElement;

public interface ServiceBeanInvoker {

	void call(Object target, WebView webView, String methodName, JsonElement jsonParams, String callback);

}
