/*
 * Copyright © 2019 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package org.aperlambda.lambdacommon.resources;

import org.aperlambda.lambdacommon.utils.Nameable;
import com.google.gson.*;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

/**
 * Represents a resource location.
 */
public class ResourceName implements Nameable
{
    protected final String domain;
    protected final String name;

    public ResourceName(String resource)
    {
        String[] res = getInTwoParts(resource);
        domain = res[0];
        name = res[1];
    }

    public ResourceName(String domain, @NotNull String name)
    {
        this.domain = domain;
        this.name = name;
    }

    public ResourceName(ResourceName resourceName, String name)
    {
        this(resourceName.domain, mergePath(resourceName.name, name));
    }

    private static String mergePath(String parent, String child)
    {
        String merged = parent;
        if (parent.endsWith("/") || child.startsWith("/"))
            merged += child;
        else
            merged += '/' + child;
        return merged;
    }

    private static String[] getInTwoParts(String o)
    {
        int separator_index = o.indexOf(':');
        if (separator_index <= 0)
            throw new IllegalArgumentException("The given string isn't a valid resource name!");
        return new String[]{o.substring(0, separator_index), o.substring(separator_index + 1)};
    }

    /**
     * Gets the domain of the resource.
     *
     * @return The domain of the resource.
     */
    public String get_domain()
    {
        return domain;
    }

    @Override
    public @NotNull String get_name()
    {
        return name;
    }

    /**
     * Creates a new {@code ResourceName} from this resource location.
     *
     * @param path The path to append.
     * @return The new {@code ResourceName} with the appended path.
     */
    public ResourceName sub(String path)
    {
        return new ResourceName(this, path);
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

        ResourceName that = (ResourceName) o;

        return (domain != null ? domain.equals(that.domain) : that.domain == null) &&
                (name != null ? name.equals(that.name) : that.name == null);
    }

    @Override
    public int hashCode()
    {
        int result = domain != null ? domain.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public static final ResourceName RESOURCE_INVALID   = new ResourceName("common", "invalid");
    public static final ResourceName RESOURCE_NOT_FOUND = new ResourceName("common", "404");

    /**
     * Represents the JSON serializer and deserializer of {@link ResourceName}.
     */
    public static class ResourceNameJsonSerializer implements JsonDeserializer<ResourceName>, JsonSerializer<ResourceName>
    {
        public ResourceName deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws
                JsonParseException
        {
            if (json.isJsonPrimitive())
                return new ResourceName(json.getAsString());
            else
                throw new JsonSyntaxException(
                        "Expected resource name to be a string, was " + json.getClass().getSimpleName());
        }

        public JsonElement serialize(ResourceName resourceName, Type type, JsonSerializationContext context)
        {
            return new JsonPrimitive(resourceName.toString());
        }
    }
}
