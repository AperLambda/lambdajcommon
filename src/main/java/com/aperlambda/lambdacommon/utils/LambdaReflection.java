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
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.*;

/**
 * An utility class for Java Reflection.
 *
 * @version 1.1.0
 * @since 1.1.0
 */
public class LambdaReflection
{
    private LambdaReflection()
    {
        throw new IllegalStateException("LambdaReflection is a full static class!");
    }

    /*
        Fields
     */

    /**
     * Gets the field by it's name from a specified class.
     *
     * @param clazz     The specified class.
     * @param fieldName The name of the field.
     * @param declared  Whether the field is declared or not.
     * @return The optional field.
     */
    public static @NotNull Optional<Field> getField(@NotNull Class<?> clazz, @NotNull String fieldName, boolean declared)
    {
        try
        {
            Field field = declared ? clazz.getDeclaredField(fieldName) : clazz.getField(fieldName);
            field.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            int modifiers = modifiersField.getInt(field);
            modifiers &= ~Modifier.FINAL;
            modifiersField.setInt(field, modifiers);
            return Optional.of(field);
        }
        catch (NoSuchFieldException | IllegalAccessException e)
        {
            return Optional.empty();
        }
    }

    /**
     * Sets the value of the specified field in the instance of the specified object.
     *
     * @param object The specified object.
     * @param field  The specified field.
     * @param value  The value to set.
     */
    public static void setValue(@NotNull Object object, @NotNull Field field, @Nullable Object value)
    {
        if (!field.isAccessible())
            field.setAccessible(true);
        try
        {
            field.set(object, value);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Gets the value of the specified field in the instance of the specified object.
     *
     * @param object The specified object.
     * @param field  The specified field.
     * @return The optional value.
     */
    public static @Nullable Object getFieldValue(@Nullable Object object, @NotNull Field field)
    {
        if (!field.isAccessible())
            field.setAccessible(true);
        try
        {
            return field.get(object);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the value of the specified field in the instance of the specified object.
     *
     * @param object       The specified object.
     * @param field        The specified field.
     * @param defaultValue The default value if cannot access to the field's value.
     * @return The optional value.
     */
    @SuppressWarnings("unchecked")
    public static <V> @Nullable V getFieldValue(@Nullable Object object, @NotNull Field field, V defaultValue)
    {
        if (!field.isAccessible())
            field.setAccessible(true);
        try
        {
            return (V) field.get(object);
        }
        catch (IllegalAccessException e)
        {
            return defaultValue;
        }
    }

    /*
        Methods
     */
    public static @NotNull Optional<Method> getMethod(@NotNull Class<?> clazz, @NotNull String name, Class<?>... parameterTypes)
    {
        return getMethod(clazz, name, true, parameterTypes);
    }

    /**
     * Gets the method by name of the specified class.
     *
     * @param clazz          The specified class.
     * @param name           The specified name of the method.
     * @param declared       Whether the method is declared or not.
     * @param parameterTypes The parameters of the method.
     * @return The optional method.
     */
    public static @NotNull Optional<Method> getMethod(@NotNull Class<?> clazz, @NotNull String name, boolean declared, Class<?>... parameterTypes)
    {
        try
        {
            return Optional.of(
                    declared ? clazz.getDeclaredMethod(name, parameterTypes) : clazz.getMethod(name, parameterTypes));
        }
        catch (NoSuchMethodException e)
        {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    /**
     * Invokes the specified method with the specified instance of the object.
     *
     * @param object    The specified object.
     * @param method    The specified method to invoke.
     * @param arguments The arguments.
     * @return The result of the method, returns null if the method returns void.
     */
    public static @Nullable Object invokeMethod(@Nullable Object object, @NotNull Method method, Object... arguments)
    {
        try
        {
            return method.invoke(object, arguments);
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /*
        Constructors
     */

    /**
     * Gets the declared constructor with the specified arguments of the specified class.
     *
     * @param clazz     The specified class.
     * @param arguments The arguments of the constructor.
     * @param <T>       The type of the class.
     * @return The optional declared constructor.
     * @see LambdaReflection#getConstructor(Class, boolean, Class[])
     */
    public static <T> @NotNull Optional<Constructor<T>> getConstructor(@NotNull Class<T> clazz, Class<?>... arguments)
    {
        return getConstructor(clazz, true, arguments);
    }

    /**
     * Gets the constructor with the specified arguments of the specified class.
     *
     * @param clazz     The specified class.
     * @param declared  Whether the constructor is declared or not.
     * @param arguments The arguments of the constructor.
     * @param <T>       The type of the class.
     * @return The optional constructor.
     */
    public static <T> @NotNull Optional<Constructor<T>> getConstructor(@NotNull Class<T> clazz, boolean declared, Class<?>... arguments)
    {
        try
        {
            return Optional.of(declared ? clazz.getDeclaredConstructor(arguments) : clazz.getConstructor(arguments));
        }
        catch (NoSuchMethodException e)
        {
            return Optional.empty();
        }
    }

    public static <T> @Nullable T newInstance(@NotNull Constructor<T> constructor, Object... arguments)
    {
        try
        {
            return constructor.newInstance(arguments);
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /*
        Class
     */
    public static @NotNull Optional<Class<?>> getClass(String name)
    {
        try
        {
            return Optional.ofNullable(Class.forName(name));
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static boolean doesClassExist(String name)
    {
        return getClass(name).isPresent();
    }
}