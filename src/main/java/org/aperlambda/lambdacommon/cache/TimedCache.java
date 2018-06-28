package org.aperlambda.lambdacommon.cache;

import org.aperlambda.lambdacommon.utils.LambdaUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Represents a cache with a lifetime for the stored objects.
 *
 * @param <T> The typename of the stored objects.
 * @version 1.5.2
 * @since 1.5.0
 */
public class TimedCache<T> implements Cache<T>
{
	private final Timer                 timer         = new Timer();
	private final int                   lifetime;
	private final List<CachedObject<T>> cachedObjects = new ArrayList<>();

	public static <T> TimedCache<T> ofLifetime(int lifetime)
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
		var removeQueue = cachedObjects.stream().filter(o -> (o.getLastUsed() + lifetime * 1000) >
				System.currentTimeMillis()).collect(Collectors.toList());
		removeQueue.forEach(this::remove);
	}

	@Override
	public void add(T object, @Nullable Consumer<T> onDestroy)
	{
		cachedObjects.add(new CachedObject<>(object, onDestroy));
	}

	@Override
	public void remove(CachedObject<T> cachedObject)
	{
		cachedObjects.remove(cachedObject);
		cachedObject.destroy();
	}

	@Override
	public List<CachedObject<T>> list()
	{
		return new ArrayList<>(cachedObjects);
	}
}