/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

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
 * @version 1.7.0
 * @since 1.5.0
 */
public class TimedCache<K, T> implements Cache<K, T>
{
    private final Timer                       timer          = new Timer();
    private final int                         lifetime;
    private final HashMap<K, CachedObject<T>> cached_objects = new HashMap<>();

    public static <K, T> TimedCache<K, T> of_lifetime(int lifetime)
    {
        return new TimedCache<>(lifetime);
    }

    public TimedCache(int lifetime)
    {
        if (lifetime <= 0)
            throw new IllegalArgumentException("Lifetime cannot be negative or null.");
        this.lifetime = lifetime;
        timer.scheduleAtFixedRate(LambdaUtils.new_timer_task_from_lambda(this::update), lifetime * 1000, lifetime * 1000);
    }

    /**
     * Gets the lifetime of the cached objects in seconds.
     *
     * @return The lifetime in seconds.
     */
    public long get_lifetime()
    {
        return lifetime;
    }

    @Override
    public void update()
    {
        List<K> remove_queue = stream().filter(o -> (o.get_value().get_last_used() + lifetime * 1000) > System.currentTimeMillis()).map(Pair::get_key).collect(Collectors.toList());
        remove_queue.forEach(this::remove);
    }

    @Override
    public void add(K key, T object, @Nullable Consumer<T> onDestroy)
    {
        cached_objects.put(key, new CachedObject<>(object, onDestroy));
    }

    @Override
    public boolean has(K key)
    {
        return cached_objects.containsKey(key);
    }

    @Override
    public void remove(K key)
    {
        cached_objects.remove(key);
    }

    @Override
    public List<CachedObject<T>> list()
    {
        return new ArrayList<>(cached_objects.values());
    }

    @Override
    public Stream<Pair<K, CachedObject<T>>> stream()
    {
        List<Pair<K, CachedObject<T>>> list = Pair.new_list_from_map(cached_objects);
        return list.stream();
    }
}
