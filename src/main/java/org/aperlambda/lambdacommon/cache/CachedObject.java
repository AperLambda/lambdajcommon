package org.aperlambda.lambdacommon.cache;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Represents a object which is cached.
 *
 * @param <T> The typename of the stored object.
 * @version 1.5.0
 * @since 1.5.0
 */
public class CachedObject<T>
{
	protected final          long creationTime;
	protected final @NotNull T    object;
	protected final @NotNull Optional<Consumer<T>> onDestroy;

	public CachedObject(@NotNull T object)
	{
		this(object, null);
	}

	public CachedObject(@NotNull T object, @Nullable Consumer<T> onDestroy)
	{
		creationTime = System.currentTimeMillis();
		this.object = object;
		this.onDestroy = Optional.ofNullable(onDestroy);
	}

	/**
	 * Gets the creation time of the cached object.
	 *
	 * @return The creation time in milliseconds.
	 */
	public long getCreationTime()
	{
		return creationTime;
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
	 * The function is called when the cached object is removed.
	 */
	public void destroy()
	{
		onDestroy.ifPresent(func -> func.accept(object));
	}
}