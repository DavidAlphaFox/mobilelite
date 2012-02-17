package org.mobilelite.android.cache;

import java.util.List;


public class EmptyCache implements Cache {

	@Override
	public Object getCacheObj(List<Object> params) {
		return null;
	}

	@Override
	public void putCacheObj(List<Object> params, Object result) {
		
	}

}
