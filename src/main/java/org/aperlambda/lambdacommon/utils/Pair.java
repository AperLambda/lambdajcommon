/*
 * Copyright © 2020 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.utils;

import com.google.gson.*;
import org.aperlambda.lambdacommon.utils.function.PairFunction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A container object which contain a key and may or may not contain a value.
 *
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 * @version 1.8.0
 */
public final class Pair<K, V> implements Serializable
{
    /**
     * Represents the key of this pair.
     */
    public final K key;
    /**
     * Represents the value of this pair.
     */
    public final V value;

    public Pair(@NotNull K key, V value)
    {
        this.key = key;
        this.value = value;
    }

    /**
     * Creates a new pair from a key and a value.
     *
     * @param key   The key of the pair.
     * @param value The value of the pair.
     * @param <K>   The type of the key.
     * @param <V>   The type of the value.
     * @return The new pair.
     */
    public static <K, V> Pair<K, V> of(@NotNull K key, @Nullable V value)
    {
        return new Pair<>(key, value);
    }

    /**
     * Creates a new pair from an Entry.
     *
     * @param entry The Entry.
     * @param <K>   The key of the pair.
     * @param <V>   The value of the pair.
     * @return A new pair.
     */
    public static <K, V> Pair<K, V> fromEntry(Map.Entry<K, V> entry)
    {
        return new Pair<>(entry.getKey(), entry.getValue());
    }

    /**
     * Creates a new list of pair from a Map.
     *
     * @param map The Map.
     * @param <K> The key of the pair.
     * @param <V> The value of the pair.
     * @return A new pair's list.
     */
    public static <K, V> List<Pair<K, V>> newListFromMap(Map<K, V> map)
    {
        List<Pair<K, V>> list = new ArrayList<>();
        map.forEach((key, value) -> list.add(new Pair<>(key, value)));
        return list;
    }

    /**
     * If a value is present, apply the provided mapping function to it,
     * and if the result is non-null, return an {@code Optional} describing the
     * result.  Otherwise return an empty {@code Optional}.
     *
     * @param <M>    The type of the key result of the mapping function.
     * @param <N>    The type of the value result of the mapping function.
     * @param mapper A mapping function to apply to the value, if present.
     * @return An {@code Optional} describing the result of applying a mapping
     * function to the value of this {@code Optional}, if a value is present,
     * otherwise an empty {@code Optional}.
     * @throws NullPointerException If the mapping function is null.
     */
    @NotNull
    public <M, N> Pair<? extends M, ? extends N> map(@NotNull PairFunction<K, V, ? extends M, ? extends N> mapper)
    {
        Objects.requireNonNull(mapper);
        return mapper.apply(this);
    }

    /**
     * If a value is present, apply the provided mapping function to it,
     * and if the result is non-null, return an {@code Optional} describing the
     * result.  Otherwise return an empty {@code Optional}.
     *
     * @param <N>    The type of the result of the mapping function.
     * @param mapper A mapping function to apply to the value, if present.
     * @return An {@code Optional} describing the result of applying a mapping
     * function to the value of this {@code Optional}, if a value is present,
     * otherwise an empty {@code Optional}.
     * @throws NullPointerException If the mapping function is null.
     * @see Optional#map(Function)
     */
    @NotNull
    public <N> N mapValue(@NotNull Function<? super V, ? extends N> mapper)
    {
        Objects.requireNonNull(mapper);
        return mapper.apply(value);
    }

    /**
     * Returns a sequential {@link Stream} containing only that {@code Pair}.
     *
     * @return The {@code Pair} as a {@code Stream}.
     * @since 1.4.5
     */
    public Stream<Pair<K, V>> stream()
    {
        return Stream.of(this);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        return Objects.equals(this.key, pair.key) && Objects.equals(this.value, pair.value);
    }

    @Override
    public int hashCode()
    {
        int result = this.key.hashCode();
        result = 31 * result + (this.value != null ? this.value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString()
    {
        return "Pair{key: " + this.key + ", value: " + this.value + '}';
    }

    /**
     * Represents the JSON serializer and deserializer of {@link Pair}.
     */
    public static class JsonPairSerializer implements JsonSerializer<Pair<?, ?>>, JsonDeserializer<Pair<?, ?>>
    {
        @Override
        public JsonElement serialize(Pair<?, ?> src, Type typeOfSrc, JsonSerializationContext context)
        {
            JsonObject json = new JsonObject();
            JsonObject key = new JsonObject();
            key.addProperty("type", src.key.getClass().getName());
            key.add("data", context.serialize(src.key));
            json.add("key", key);
            Object value = src.value;
            if (value != null) {
                JsonObject jsonValue = new JsonObject();
                jsonValue.addProperty("type", value.getClass().getName());
                jsonValue.add("data", context.serialize(value));
                json.add("value", jsonValue);
            }
            return json;
        }

        @Override
        public Pair<?, ?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
        {
            if (!(json instanceof JsonObject))
                throw new JsonParseException("Cannot parse Pair<?, ?>: the json must be an object!");
            JsonObject obj = (JsonObject) json;
            if (!obj.has("key") || !obj.get("key").isJsonObject())
                throw new JsonParseException("Key is not present or is malformed.");
            JsonObject jsonKey = obj.getAsJsonObject("key");
            try {
                Class<?> type = Class.forName(jsonKey.get("type").getAsString());
                Object key = context.deserialize(jsonKey.get("data"), type);

                if (!obj.has("value"))
                    return Pair.of(key, null);
                JsonObject jsonValue = obj.getAsJsonObject("value");
                Class<?> valueType = Class.forName(jsonValue.get("type").getAsString());
                Object value = context.deserialize(jsonValue.get("data"), valueType);
                return Pair.of(key, value);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }
    }
}
