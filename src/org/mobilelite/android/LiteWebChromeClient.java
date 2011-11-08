package org.mobilelite.android;

import android.util.Log;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class LiteWebChromeClient extends WebChromeClient {
	
	@Override
	public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
		Log.d("PageAlert", message);
		result.confirm();
		return true;
	}

}
