/*
 * Copyright © 2017 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package com.aperlambda.lambdacommon.utils;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Optional<X> implements Serializable
{
    private static final Optional<?> EMPTY = new Optional<>();

    private final X _value;


    Optional()
    {
        _value = null;
    }

    Optional(X value)
    {
        _value = Objects.requireNonNull(value);
    }

    @NotNull
    public static <T> Optional<T> empty()
    {
        @SuppressWarnings("unchecked")
        Optional<T> OwO = (Optional<T>) EMPTY;
        return OwO;
    }

    @NotNull
    public static <T> Optional<T> of(@NotNull T something)
    {
        return new Optional<>(something);
    }

    @NotNull
    public static <T> Optional<T> ofNullable(T somethingNullable)
    {
        return somethingNullable == null ? empty() : of(somethingNullable);
    }

    /**
     * If a value is present in this {@code Optional}, returns the value,
     * otherwise throws {@code NoSuchElementException}.
     *
     * @return The non-null value held by this {@code Optional}.
     * @throws NoSuchElementException If there is no value present.
     * @see com.aperlambda.lambdacommon.utils.Optional#isPresent()
     */
    public X get()
    {
        if (_value == null)
        {
            throw new NoSuchElementException("No value present");
        }
        return _value;
    }


    /**
     * Return the value if present, otherwise return {@code other}.
     *
     * @param other The value to be returned if there is no value present, may
     *              be null.
     * @return The value, if present, otherwise {@code other}.
     */
    public X getOrElse(X other)
    {
        return _value != null ? _value : other;
    }

    /**
     * Return the value if present, otherwise invoke {@code other} and return
     * the result of that invocation.
     *
     * @param other A {@code Supplier} whose result is returned if no value
     *              is present.
     * @return The value if present otherwise the result of {@code other.get()}.
     * @throws NullPointerException If value is not present and {@code other} is
     *                              null.
     */
    public X getOrElseGet(@NotNull Supplier<? extends X> other)
    {
        return _value != null ? _value : other.get();
    }

    /**
     * If a value is present, returns the value, otherwise throws an exception
     * produced by the exception supplying function.
     *
     * @param <T>               Type of the exception to be thrown.
     * @param exceptionSupplier The supplying function that produces an
     *                          exception to be thrown.
     * @return The value, if present.
     * @throws T                    If no value is present.
     * @throws NullPointerException If no value is present and the exception
     *                              supplying function is {@code null}.
     */
    public <T extends Throwable> X getOrElseThrow(@NotNull Supplier<? extends T> exceptionSupplier) throws T
    {
        if (_value != null)
            return _value;
        else
            throw exceptionSupplier.get();
    }

    /**
     * Return {@code true} if there is a value present, otherwise {@code false}.
     *
     * @return {@code true} if there is a value present, otherwise {@code false}.
     */
    public boolean isPresent()
    {
        return _value != null;
    }

    /**
     * If a value is present, invoke the specified consumer with the value,
     * otherwise do nothing.
     *
     * @param consumer Block to be executed if a value is present.
     * @throws NullPointerException If value is present and {@code consumer} is
     *                              null.
     */
    public void ifPresent(@NotNull Consumer<? super X> consumer)
    {
        if (_value != null)
            consumer.accept(_value);
    }

    /**
     * If a value is present, performs the given action with the value,
     * otherwise performs the given empty-based action.
     *
     * @param action      The action to be performed, if a value is present.
     * @param emptyAction The empty-based action to be performed, if no value is
     *                    present.
     * @throws NullPointerException If a value is present and the given action
     *                              is {@code null}, or no value is present and the given empty-based
     *                              action is {@code null}.
     */
    public void ifPresentOrElse(@NotNull Consumer<? super X> action, @NotNull Runnable emptyAction)
    {
        if (_value != null)
            action.accept(_value);
        else
            emptyAction.run();
    }

    /**
     * If a value is present, apply the provided mapping function to it,
     * and if the result is non-null, return an {@code Optional} describing the
     * result.  Otherwise return an empty {@code Optional}.
     *
     * @param <N> The type of the result of the mapping function.
     * @param mapper A mapping function to apply to the value, if present.
     * @return An {@code Optional} describing the result of applying a mapping
     * function to the value of this {@code Optional}, if a value is present,
     * otherwise an empty {@code Optional}.
     * @throws NullPointerException If the mapping function is null.
     */
    @NotNull
    public <N> Optional<N> map(@NotNull Function<? super X, ? extends N> mapper)
    {
        Objects.requireNonNull(mapper);
        if (isPresent())
            return ofNullable(mapper.apply(_value));
        else return empty();
    }

    /**
     * If a value is present, apply the provided mapping function to it,
     * and if the result is non-null, return an {@code OptionalString} describing the
     * result. Otherwise return an empty {@code OptionalString}.
     *
     * @param mapper A mapping function to apply to the value, if present.
     * @return An {@code OptionalString} describing the result of applying a mapping
     * function to the value of this {@code Optional}, if a value is present,
     * otherwise an empty{@code Optional}.
     * @throws NullPointerException If the mapping function is null.
     */
    @NotNull
    public OptionalString mapString(@NotNull Function<? super X, ? extends String> mapper)
    {
        Objects.requireNonNull(mapper);
        if (isPresent())
            return OptionalString.ofNullable(mapper.apply(_value));
        else return OptionalString.empty();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Optional<?> optional = (Optional<?>) o;

        return _value != null ? _value.equals(optional._value) : optional._value == null;
    }

    @Override
    public int hashCode()
    {
        return _value != null ? _value.hashCode() : 0;
    }

    @Override
    public String toString()
    {
        return _value != null ? _value.toString() : "null";
    }
}