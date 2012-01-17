package org.mobilelite.android;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.mobilelite.annotation.ServiceMethod;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

public abstract class AbstractServiceBeanInvoker implements ServiceBeanInvoker {

	@SuppressWarnings("unchecked")
	@Override
	public void call(Object target, WebView webView, String methodName, JsonElement jsonParam, String callback) {
		Gson gson = new Gson();
		JsonElement je = jsonParam;

		JsonArray jaParams = null;
		if (je.isJsonArray()) {
			jaParams = je.getAsJsonArray();
		} else if (je.isJsonObject() || je.isJsonPrimitive()) {
			jaParams = new JsonArray();
			jaParams.add(je);
		} else if (je.isJsonNull()) {
			jaParams = new JsonArray();
		}

		Method[] methods = getBeanMethods(target, methodName, jaParams.size());
		for (Method method : methods) {
			@SuppressWarnings("rawtypes")
			Class[] paramClasses = method.getParameterTypes();
			List<Object> params = new ArrayList<Object>();
			try {
				for (int i = 0; i < paramClasses.length; i++) {
					params.add(gson.fromJson(jaParams.get(i), paramClasses[i]));
				}

				ServiceMethod serviceMethod = method.getAnnotation(ServiceMethod.class);
				if (serviceMethod.showDialog() || serviceMethod.execAsync()) {
					executeMethodInDialog(target, method, params, serviceMethod, webView, callback);
				} else {
					executeMethod(target, method, params, webView, callback);
				}
				break;
			} catch (JsonParseException e) {
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			}
		}

	}

	protected Method[] getBeanMethods(Object bean, String methodName, int paramNum) {
		List<Method> methods = new ArrayList<Method>();
		@SuppressWarnings("rawtypes")
		Class clazz = bean.getClass();
		while (clazz != null) {
			Method[] beanMethods = clazz.getDeclaredMethods();
			for (Method method : beanMethods) {
				if (method.isAnnotationPresent(ServiceMethod.class) && method.getName().equals(methodName) && method.getParameterTypes().length == paramNum)
					methods.add(method);
			}
			clazz = clazz.getSuperclass();
		}
		return methods.toArray(new Method[] {});
	}

	protected void executeMethodInDialog(final Object bean, final Method method, final List<Object> params, final ServiceMethod serviceMethod,
			final WebView webView, final String callback) {
		AsyncTask<Object, Integer, Object> asyncTask = new AsyncTask<Object, Integer, Object>() {
			ProgressDialog progDialog;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				if (serviceMethod.showDialog()) {
					progDialog = ProgressDialog.show(webView.getContext(), serviceMethod.title(), serviceMethod.message(), true);
				}
			}

			@Override
			protected Object doInBackground(Object... arg0) {
				try {
					Object result = method.invoke(bean, params.toArray());
					result = postInvoke(method, params, result);
				} catch (SecurityException e) {
				} catch (JsonSyntaxException e) {
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				}
				return null;
			}

			@Override
			protected void onPostExecute(Object result) {
				super.onPostExecute(result);
				if (serviceMethod.showDialog()) {
					progDialog.dismiss();
				}

				doCallback(result, webView, callback);
			}

		};
		asyncTask.execute();
	}

	protected void doCallback(Object result, final WebView webView, final String callback) {
		if (callback != null) {
			if (result != null) {
				result = (new Gson()).toJson(result);
			}

			final Object data = result;
			webView.post(new Runnable() {
				@Override
				public void run() {
					webView.loadUrl("javascript:mobileLite.doCallback(" + data + ", " + callback + ")");
				}
			});
		}

	}

	protected void executeMethod(Object bean, Method method, List<Object> params, WebView webView, String callback) throws IllegalArgumentException,
			IllegalAccessException, InvocationTargetException {
		Object result = method.invoke(bean, params.toArray());
		result = postInvoke(method, params, result);
		doCallback(result, webView, callback);
	}

	protected Object postInvoke(Method method, List<Object> params, Object result) {
		return result;
	}

}
