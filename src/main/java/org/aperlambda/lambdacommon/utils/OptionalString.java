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

import java.util.function.Consumer;

/**
 * A container object which may or may not contain a non-{@code null} value.
 * <p>The value is a string.</p>
 *
 * @see Optional
 */
public final class OptionalString extends Optional<String>
{
	private static final OptionalString EMPTY = new OptionalString();

	private OptionalString()
	{
	}

	private OptionalString(String value)
	{
		super(value);
	}

	@SuppressWarnings("unchecked")
	@NotNull
	public static OptionalString empty()
	{
		return EMPTY;
	}

	@NotNull
	public static OptionalString of(@NotNull String something)
	{
		return new OptionalString(something);
	}

	@NotNull
	public static OptionalString ofNullable(String somethingNullable)
	{
		return somethingNullable == null ? empty() : of(somethingNullable);
	}

	@Override
	public boolean isPresent()
	{
		return super.isPresent() && !get().isEmpty();
	}

	@Override
	public void ifPresent(@NotNull Consumer<? super String> consumer)
	{
		if (isPresent())
			super.ifPresent(consumer);
	}
}