/*
 * Copyright © 2018 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.utils;

import org.aperlambda.lambdacommon.utils.function.PairFunction;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A container object which contain a key and may or may not contain a value.
 *
 * @param <K> The type of the key.
 * @param <V> The type of the value.
 * @version 1.4.9
 */
public final class Pair<K, V> implements Serializable
{
	private K           _key;
	private Optional<V> _value;

	public Pair(@NotNull K key, V value)
	{
		_key = key;
		_value = Optional.ofNullable(value);
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
	 * Gets the key for this pair.
	 *
	 * @return Key for this pair.
	 */
	public K getKey()
	{
		return _key;
	}

	/**
	 * Gets the value for this pair.
	 *
	 * @return Value for this pair.
	 */
	public Optional<V> getValue()
	{
		return _value;
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
	public <N> Optional<N> mapValue(@NotNull Function<? super V, ? extends N> mapper)
	{
		Objects.requireNonNull(mapper);
		return _value.map(mapper);
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

		return (_key != null ? _key.equals(pair._key) : pair._key == null) &&
				(_value != null ? _value.equals(pair._value) : pair._value == null);
	}

	@Override
	public int hashCode()
	{
		int result = _key != null ? _key.hashCode() : 0;
		result = 31 * result + (_value != null ? _value.hashCode() : 0);
		return result;
	}

	@Override
	public String toString()
	{
		return "Pair{" +
				"key: " + _key +
				", value: " + _value +
				'}';
	}
}