/*
 * Copyright © 2017 AperLambda <aper.entertainment@gmail.com>
 *
 * This file is part of λjcommon.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package com.aperlambda.lambdacommon.resources;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Represents a resource location.
 */
public class ResourceLocation
{
    protected final String domain;
    protected final String path;

    public ResourceLocation(String resource)
    {
        String[] res = getInTwoParts(resource);
        domain = res[0];
        path = res[1];
    }

    public ResourceLocation(String domain, String path)
    {
        this.domain = domain;
        this.path = path;
    }

    public ResourceLocation(ResourceLocation location, String path)
    {
        this(location.domain, mergePath(location.path, path));
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
            throw new IllegalArgumentException("The given string isn't a valid ResourceLocation!");
        return new String[]{o.substring(0, separatorIndex), o.substring(separatorIndex + 1)};
    }

    public String getDomain()
    {
        return domain;
    }

    public String getPath()
    {
        return path;
    }

    public ResourceLocation sub(String path)
    {
        return new ResourceLocation(this, path);
    }

    @Override
    public String toString()
    {
        return domain + ':' + path;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourceLocation that = (ResourceLocation) o;

        return (domain != null ? domain.equals(that.domain) : that.domain == null) &&
               (path != null ? path.equals(that.path) : that.path == null);
    }

    @Override
    public int hashCode()
    {
        int result = domain != null ? domain.hashCode() : 0;
        result = 31 * result + (path != null ? path.hashCode() : 0);
        return result;
    }

    public static class Serializer implements JsonDeserializer<ResourceLocation>, JsonSerializer<ResourceLocation>
    {
        public ResourceLocation deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws
                                                                                                             JsonParseException
        {
            if (json.isJsonPrimitive())
                return new ResourceLocation(json.getAsString());
            else
                throw new JsonSyntaxException(
                        "Expected resource location to be a string, was " + json.getClass().getSimpleName());
        }

        public JsonElement serialize(ResourceLocation resourceLocation, Type type, JsonSerializationContext context)
        {
            return new JsonPrimitive(resourceLocation.toString());
        }
    }
}