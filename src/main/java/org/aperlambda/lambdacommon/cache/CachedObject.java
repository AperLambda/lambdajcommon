package org.aperlambda.lambdacommon.cache;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Represents a object which is cached.
 *
 * @param <T> The typename of the stored object.
 * @version 1.5.1
 * @since 1.5.0
 */
public class CachedObject<T>
{
	protected                long                  lastUsed;
	protected @NotNull       T                     object;
	protected final @NotNull Optional<Consumer<T>> onDestroy;

	public CachedObject(@NotNull T object)
	{
		this(object, null);
	}

	public CachedObject(@NotNull T object, @Nullable Consumer<T> onDestroy)
	{
		lastUsed = System.currentTimeMillis();
		this.object = object;
		this.onDestroy = Optional.ofNullable(onDestroy);
	}

	/**
	 * Gets the last used time of the cached object.
	 *
	 * @return The last used time in milliseconds.
	 */
	public long getLastUsed()
	{
		return lastUsed;
	}

	/**
	 * Gets the cached object.
	 *
	 * @return The cached object.
	 */
	public @NotNull T getObject()
	{
		return object;
	}

	/**
	 * Gets the cached object and update the last used time.
	 *
	 * @return The cached object.
	 */
	public @NotNull T update()
	{
		lastUsed = System.currentTimeMillis();
		return getObject();
	}

	/**
	 * The function is called when the cached object is removed.
	 */
	public void destroy()
	{
		onDestroy.ifPresent(func -> func.accept(object));
	}
}