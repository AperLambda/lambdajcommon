/*
 * Copyright © 2018 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.utils;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	 * @return A new pair.
	 */
	public static <K, V> Pair<K, V> fromEntry(Map.Entry<K, V> entry)
	{
		return new Pair<>(entry.getKey(), entry.getValue());
	}

	/**
	 * Creates a new pair from JavaFX's pair.
	 *
	 * @param javaFXPair The JavaFX's pair.
	 * @return A new pair.
	 */
	public static <K, V> Pair<K, V> fromJavaFX(javafx.util.Pair<K, V> javaFXPair)
	{
		return new Pair<>(javaFXPair.getKey(), javaFXPair.getValue());
	}

	/**
	 * Creates a new list of pair from a Map.
	 *
	 * @param map The Map.
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