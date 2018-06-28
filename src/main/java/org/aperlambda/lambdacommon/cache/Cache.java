package org.aperlambda.lambdacommon.cache;

import java.util.List;
import java.util.stream.Stream;

/**
 * Represents a cache.
 *
 * @param <T> The typename of the stored objects.
 * @version 1.5.1
 * @since 1.5.0
 */
public interface Cache<T>
{
	/**
	 * Updates the cache.
	 */
	void update();

	/**
	 * Adds an object to cache.
	 * @param object The object to cache.
	 */
	void add(T object);

	/**
	 * Checks whether the cache has the specified object.
	 * @param object The object to check.
	 * @return True if the cache contains the object, else false.
	 */
	default boolean has(T object)
	{
		return stream().anyMatch(o -> o.equals(object));
	}

	/**
	 * Removes a cached object.
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