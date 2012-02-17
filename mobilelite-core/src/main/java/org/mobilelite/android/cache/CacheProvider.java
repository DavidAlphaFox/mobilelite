package org.mobilelite.android.cache;


public class CacheProvider {
	
	private static final Cache emptyCache = new EmptyCache();

	public static Cache getCache(String cache) {
		return emptyCache;
	}

}
