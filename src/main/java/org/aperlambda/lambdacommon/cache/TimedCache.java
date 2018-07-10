package org.aperlambda.lambdacommon.cache;

import org.aperlambda.lambdacommon.utils.LambdaUtils;
import org.aperlambda.lambdacommon.utils.Pair;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a cache with a lifetime for the stored objects.
 *
 * @param <T> The typename of the stored objects.
 * @version 1.5.3
 * @since 1.5.0
 */
public class TimedCache<K, T> implements Cache<K, T>
{
	private final Timer                    timer         = new Timer();
	private final int                      lifetime;
	private final HashMap<K, CachedObject<T>> cachedObjects = new HashMap<>();

	public static <K, T> TimedCache<K, T> ofLifetime(int lifetime)
	{
		return new TimedCache<>(lifetime);
	}

	public TimedCache(int lifetime)
	{
		if (lifetime <= 0)
			throw new IllegalArgumentException("Lifetime cannot be negative or null.");
		this.lifetime = lifetime;
		timer.scheduleAtFixedRate(LambdaUtils.newTimerTaskFromLambda(this::update), lifetime * 1000, lifetime * 1000);
	}

	/**
	 * Gets the lifetime of the cached objects in seconds.
	 *
	 * @return The lifetime in seconds.
	 */
	public long getLifetime()
	{
		return lifetime;
	}

	@Override
	public void update()
	{
		var removeQueue = stream().filter(o -> (o.getValue().getLastUsed() + lifetime * 1000) >
				System.currentTimeMillis()).map(Pair::getKey).collect(Collectors.toList());
		removeQueue.forEach(this::remove);
	}

	@Override
	public void add(K key, T object, @Nullable Consumer<T> onDestroy)
	{
		cachedObjects.put(key, new CachedObject<>(object, onDestroy));
	}

	@Override
	public boolean has(K key)
	{
		return cachedObjects.containsKey(key);
	}

	@Override
	public void remove(K key)
	{
		cachedObjects.remove(key);
	}

	@Override
	public List<CachedObject<T>> list()
	{
		return new ArrayList<>(cachedObjects.values());
	}

	@Override
	public Stream<Pair<K, CachedObject<T>>> stream()
	{
		var list = Pair.newListFromMap(cachedObjects);
		return list.stream();
	}
}