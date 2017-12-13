/*
 * Copyright © 2017 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package com.aperlambda.lambdacommon.resources;

import com.aperlambda.lambdacommon.utils.Nameable;
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

    public ResourceName(String domain, String name)
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
        int separatorIndex = o.indexOf(':');
        if (separatorIndex <= 0)
            throw new IllegalArgumentException("The given string isn't a valid resource name!");
        return new String[]{o.substring(0, separatorIndex), o.substring(separatorIndex + 1)};
    }

    public String getDomain()
    {
        return domain;
    }

    @Override
    public @NotNull String getName()
    {
        return name;
    }

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

    public static class Serializer implements JsonDeserializer<ResourceName>, JsonSerializer<ResourceName>
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