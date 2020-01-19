/*
 * Copyright © 2020 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon;

import org.aperlambda.lambdacommon.utils.Nameable;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Represents an identifier.
 */
public class Identifier implements Nameable
{
    protected final String domain;
    protected final String name;

    public Identifier(String resource)
    {
        String[] res = get_in_two_parts(resource);
        domain = res[0];
        name = res[1];
    }

    public Identifier(String domain, @NotNull String name)
    {
        this.domain = domain;
        this.name = name;
    }

    public Identifier(Identifier identifier, String name)
    {
        this(identifier.domain, merge_path(identifier.name, name));
    }

    private static String merge_path(String parent, String child)
    {
        String merged = parent;
        if (parent.endsWith("/") || child.startsWith("/"))
            merged += child;
        else
            merged += '/' + child;
        return merged;
    }

    private static String[] get_in_two_parts(String o)
    {
        int separator_index = o.indexOf(':');
        if (separator_index <= 0)
            throw new IllegalArgumentException("The given string isn't a valid resource name!");
        return new String[]{o.substring(0, separator_index), o.substring(separator_index + 1)};
    }

    /**
     * Gets the namespace of this identifier.
     *
     * @return The namespace of this identifier.
     */
    public String get_namespace()
    {
        return domain;
    }

    @Override
    public @NotNull String get_name()
    {
        return name;
    }

    /**
     * Creates a new {@code Identifier} from this resource location.
     *
     * @param path The path to append.
     * @return The new {@code Identifier} with the appended path.
     */
    public Identifier sub(String path)
    {
        return new Identifier(this, path);
    }

    @Override
    public String toString()
    {
        return domain + ':' + name;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Identifier that = (Identifier) o;

        return (Objects.equals(domain, that.domain)) && (Objects.equals(name, that.name));
    }

    @Override
    public int hashCode()
    {
        int result = domain != null ? domain.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public static final Identifier IDENTIFIER_INVALID   = new Identifier("common", "invalid");
    public static final Identifier IDENTIFIER_NOT_FOUND = new Identifier("common", "404");

    /**
     * Represents the JSON serializer and deserializer of {@link Identifier}.
     */
    public static class IdentifierJsonSerializer implements JsonDeserializer<Identifier>, JsonSerializer<Identifier>
    {
        public Identifier deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws
                JsonParseException
        {
            if (json.isJsonPrimitive())
                return new Identifier(json.getAsString());
            else
                throw new JsonSyntaxException(
                        "Expected resource name to be a string, was " + json.getClass().getSimpleName());
        }

        public JsonElement serialize(Identifier identifier, Type type, JsonSerializationContext context)
        {
            return new JsonPrimitive(identifier.toString());
        }
    }
}
