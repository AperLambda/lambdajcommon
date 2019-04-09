package org.aperlambda.lambdacommon.cache;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Represents a object which is cached.
 *
 * @param <T> The typename of the stored object.
 * @version 1.6.0
 * @since 1.5.0
 */
public class CachedObject<T>
{
    protected                long                  last_used;
    protected @NotNull       T                     object;
    protected final @NotNull Optional<Consumer<T>> on_destroy;

    public CachedObject(@NotNull T object)
    {
        this(object, null);
    }

    public CachedObject(@NotNull T object, @Nullable Consumer<T> on_destroy)
    {
        last_used = System.currentTimeMillis();
        this.object = object;
        this.on_destroy = Optional.ofNullable(on_destroy);
    }

    /**
     * Gets the last used time of the cached object.
     *
     * @return The last used time in milliseconds.
     */
    public long get_last_used()
    {
        return this.last_used;
    }

    /**
     * Gets the cached object.
     *
     * @return The cached object.
     */
    public @NotNull T get_object()
    {
        return this.object;
    }

    /**
     * Gets the cached object and update the last used time.
     *
     * @return The cached object.
     */
    public @NotNull T update()
    {
        last_used = System.currentTimeMillis();
        return get_object();
    }

    /**
     * The function is called when the cached object is removed.
     */
    public void destroy()
    {
        this.on_destroy.ifPresent(func -> func.accept(this.object));
    }
}