package org.mobilelite.android;

import java.net.URLDecoder;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import android.util.Log;
import android.webkit.WebView;

public class GingerbreadWebViewClient extends LiteWebViewClient {

	public GingerbreadWebViewClient(PageEventDispatcher dispatcher) {
		super(dispatcher);
	}
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if(url.startsWith(MobileLiteConstants.PROTOCOL_MOBILELITE)) {
			Log.d("request", url);
			String request = URLDecoder.decode(url.substring(MobileLiteConstants.PROTOCOL_MOBILELITE.length()));
			Log.d("decoded request", request);
			
			JsonParser jsonParser = new JsonParser();
			JsonElement jsonParam = jsonParser.parse(request);
			
			if(jsonParam.isJsonNull() || jsonParam.isJsonPrimitive()) {
				Log.e("Invoke Bean Action", "request should be in format {bean:'beanName', method:'methodName', params:[], callback:'callback string' }");
				return true;
			}
			
			String beanName = null, methodName = null, callback = null;
			JsonElement params = null;
			boolean requestParsed = true;
			if(jsonParam.isJsonArray()) {
				JsonArray ja = jsonParam.getAsJsonArray();
				if(ja.size()  == 4) {
					beanName = ja.get(1).getAsString();
					methodName = ja.get(2).getAsString();
					params = ja.get(3);
					callback = ja.get(4).getAsString();
				}
				else {
					Log.e("Invoke Bean Action", "request should have 4 element");
					requestParsed = false;
				}
			}
			else if(jsonParam.isJsonObject()) {
				JsonObject jo = jsonParam.getAsJsonObject(); 
				if(jo.has(MobileLiteConstants.PARAM_KEY_BEAN)) 
					beanName = jsonParam.getAsJsonObject().get(MobileLiteConstants.PARAM_KEY_BEAN).getAsString();
				else {
					Log.e("Invoke Bean Action", "request should have 'bean' element");
					requestParsed = false;
				}

				if(jo.has(MobileLiteConstants.PARAM_KEY_METHOD)) 
					methodName = jsonParam.getAsJsonObject().get(MobileLiteConstants.PARAM_KEY_METHOD).getAsString();
				else {
					Log.e("Invoke Bean Action", "request should have 'bean' element");
					requestParsed = false;
				}

				if(jo.has(MobileLiteConstants.PARAM_KEY_PARAMS)) 
					params = jsonParam.getAsJsonObject().get(MobileLiteConstants.PARAM_KEY_PARAMS);
				else {
					Log.e("Invoke Bean Action", "request should have 'bean' element");
					requestParsed = false;
				}

				if(jo.has(MobileLiteConstants.PARAM_KEY_METHOD)) 
					methodName = jsonParam.getAsJsonObject().get(MobileLiteConstants.PARAM_KEY_METHOD).getAsString();
				else {
					Log.e("Invoke Bean Action", "request should have 'bean' element");
					requestParsed = false;
				}
			}
			
			if(requestParsed) {
				dispatcher._invokeBeanAction(beanName, methodName, params, callback);
			}
			
			return true;
		}
		return super.shouldOverrideUrlLoading(view, url);
	}

}
