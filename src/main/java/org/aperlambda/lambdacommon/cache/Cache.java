package org.aperlambda.lambdacommon.cache;

import java.util.List;
import java.util.stream.Stream;

/**
 * Represents a cache.
 *
 * @param <T> The typename of the stored objects.
 * @version 1.5.0
 * @since 1.5.0
 */
public interface Cache<T>
{
	/**
	 * Updates the cache.
	 */
	void update();

	void add(T object);

	/**
	 * Removes an cached object.
	 *
	 * @param cachedObject The cached object to remove.
	 */
	void remove(CachedObject<T> cachedObject);

	List<CachedObject<T>> list();

	default Stream<CachedObject<T>> stream()
	{
		return list().stream();
	}
}