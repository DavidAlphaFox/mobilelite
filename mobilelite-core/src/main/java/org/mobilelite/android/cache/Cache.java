package org.mobilelite.android.cache;

import java.util.List;

public interface Cache {

	Object getCacheObj(List<Object> params);

	void putCacheObj(List<Object> params, Object result);

}
